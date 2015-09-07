package com.mialab.palmsuda.tools.ivache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.mialab.palmsuda.tools.ivache.ImageCache.ImageCacheParams;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

/**
 * @author mialab
 * @date 创建时间：2015-8-4 下午12:07:24
 * 
 */
public enum ImageWorker {
	INSTANCE;
	private ImageCache mImageCache;

	private int loadingResId = 0, errorResId = 0, bgResId = 0;

	private volatile boolean onScreen = true;
	public static int ScreenWeith = 480;
	private ExecutorService searchThreadPool;
	private HashMap<String, ImageCacheParams> params;
	private Handler mHandler;
	public static final int IO_BUFFER_SIZE = 8 * 1024;
	private OnHandleCacheListener mIHandleCache;

	public static ImageWorker newInstance() {
		return INSTANCE;
	}

	private ImageWorker() {
		mHandler = new Handler();
		mImageCache = ImageCache.createCache();
		searchThreadPool = Executors.newFixedThreadPool(8);
		mIHandleCache = new OnHandleCacheListener() {
			@Override
			public void onSetImage(final ImageView imageView,
					final Bitmap bitmap) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						imageView.setImageBitmap(bitmap);
						// imageView.setBackgroundResource(0);
						AsyncDrawable aDrawable = (AsyncDrawable) imageView
								.getTag();
						if (aDrawable != null) {
							imageView.setBackgroundResource(aDrawable
									.getBgResID());
						}
					}
				});
			}

			@Override
			public void onError(final ImageView imageView) {
				mHandler.post(new Runnable() {
					@Override
					public void run() {
						if (imageView != null) {
							AsyncDrawable aDrawable = (AsyncDrawable) imageView
									.getTag();
							imageView
									.setImageResource(aDrawable != null ? aDrawable
											.getErrorResID() : errorResId);
						}
					}
				});
			}
		};
	}

	public void setCommonResID(int loadId, int errId) {
		loadingResId = loadId;
		errorResId = errId;
	}

	public void loadBitmap(final String path, final ImageView imageView,
			int loadingRes) {
		loadBitmap(path, imageView, ScreenWeith / 3, ScreenWeith / 3,
				loadingRes, errorResId, bgResId);
	}
	
	public void loadBitmap(final String path, final ImageView imageView, int w, int h) {
		loadBitmap(path, imageView, w, h, loadingResId, errorResId, bgResId);
	}

	public void loadBitmap(final String path, final ImageView imageView, int w,
			int h, int loadingRes, int errRes, int bgRes) {
		Bitmap result = mImageCache.getBitmapFromMem(path);
		imageView.setImageResource(0);
		imageView.setBackgroundResource(bgRes > 0 ? bgRes : 0);
		if (result != null && !result.isRecycled()) {
			mIHandleCache.onSetImage(imageView, result);
		} else if (cancelWork(imageView, path)) {
			final SearchTask task = new SearchTask(path, imageView, w, h,
					mIHandleCache);
			final AsyncDrawable asyncDrawable = new AsyncDrawable(loadingRes,
					errRes, bgRes, task);
			imageView.setTag(asyncDrawable);
			imageView.setBackgroundResource(asyncDrawable.getDefaultResID());
			if (!searchThreadPool.isTerminated()
					&& !searchThreadPool.isShutdown()) {
				searchThreadPool.execute(task);
			}
		}
	}	

	protected boolean cancelWork(final ImageView view, final String path) {
		SearchTask task = getSearchTask(view);
		if (task != null) {
			final String taskPath = task.getPath();
			if (TextUtils.isEmpty(taskPath) || !taskPath.equals(path)) {
				task.cancelWork();
			} else {
				return false;
			}
		}
		return true;
	}

	public static void cancelWork(final ImageView imageView) {
		final SearchTask bitmapWorkerTask = getSearchTask(imageView);
		if (bitmapWorkerTask != null) {
			bitmapWorkerTask.cancelWork();
		}
	}

	public static SearchTask getSearchTask(final ImageView imageView) {
		if (imageView != null) {
			AsyncDrawable asyncDrawable = (AsyncDrawable) imageView.getTag();
			if (asyncDrawable != null) {
				return asyncDrawable.getTask();
			}
		}
		return null;
	}

	/**
	 * Set a new CacheParams.
	 */
	public void addParams(String tag, ImageCacheParams cacheParams) {
		if (params == null) {
			params = new HashMap<String, ImageCache.ImageCacheParams>();
		}
		params.put(tag, cacheParams);
		mImageCache.setCacheParams(cacheParams);
	}

	/**
	 * Get a CacheParams by flag.
	 */
	public ImageCacheParams getParams(String tag) {
		return params.get(tag);
	}

	public class AsyncDrawable {
		private final WeakReference<SearchTask> task;
		private int defaultId = 0;
		private int errorResId = 0;
		private int bgresId = 0;

		public AsyncDrawable(int loadingRes, int errResId, int bgResid,
				SearchTask searchTask) {
			task = new WeakReference<SearchTask>(searchTask);
			defaultId = loadingRes;
			errorResId = errResId;
			bgresId = bgResid;
		}

		public int getDefaultResID() {
			return defaultId;
		}

		public int getErrorResID() {
			return errorResId;
		}

		public int getBgResID() {
			return bgresId;
		}

		public SearchTask getTask() {
			return task.get();
		}
	}

	public class SearchTask implements Runnable {

		String path;
		volatile boolean stop = false;
		OnHandleCacheListener mIHandleCache;
		int reqW = 0;
		int reqH = 0;
		private WeakReference<ImageView> mImageViewReference;

		// 停止掉任务
		public void cancelWork() {
			stop = true;
		}

		public SearchTask(final String path, final ImageView imageView, int w,
				int h, final OnHandleCacheListener mIHandleCache) {
			this.path = path;
			reqW = w;
			reqH = h;
			mImageViewReference = new WeakReference<ImageView>(imageView);
			this.mIHandleCache = mIHandleCache;
		}

		public String getPath() {
			return path;
		}

		@Override
		public void run() {
			Bitmap bitmap = null;
			if (mImageCache != null && !stop && getAttachedImageView() != null
					&& onScreen) {
				bitmap = mImageCache.getBitmapFromDiskCache(path, reqW, reqH);
			}
			if (bitmap == null && mImageCache != null && !stop
					&& getAttachedImageView() != null && onScreen) {
				try {
					File file = downloadBitmap(path);
					bitmap = mImageCache.getBitmapFromDiskCache(file, reqW,
							reqH);
					if (bitmap == null) {
						mIHandleCache.onError(getAttachedImageView());
					}
				} catch (IOException e) {
					mIHandleCache.onError(getAttachedImageView());
					e.printStackTrace();
				}

			}

			if (bitmap != null && mImageCache != null && !stop && onScreen) {
				ImageView imageView = getAttachedImageView();
				mImageCache.addBitmapToCache(path, bitmap);
				if (imageView != null && !stop) {
					mIHandleCache.onSetImage(imageView, bitmap);
				} else {
					bitmap.recycle();
					bitmap = null;

				}
			}

		}

		/**
		 * Returns the ImageView associated with this task as long as the
		 * ImageView's task still points to this task as well. Returns null
		 * otherwise.
		 */
		private ImageView getAttachedImageView() {
			final ImageView imageView = mImageViewReference.get();
			final SearchTask bitmapWorkerTask = ImageWorker
					.getSearchTask(imageView);

			if (this == bitmapWorkerTask) {
				return imageView;
			}

			return null;
		}
	}

	public void setOnScreen(String tag, boolean onScreen) {
		this.onScreen = onScreen;
		if (!onScreen) {
			mImageCache.clearCaches();
			shutdownThreadPool();
		} else {
			restartThreadPool();
			mImageCache.setCacheParams(getParams(tag));
		}
	}

	private void restartThreadPool() {
		synchronized (searchThreadPool) {
			if (searchThreadPool.isTerminated()
					|| searchThreadPool.isShutdown()) {
				searchThreadPool = null;
				searchThreadPool = Executors.newFixedThreadPool(8);
			}
		}
	}

	private void shutdownThreadPool() {
		searchThreadPool.shutdown();
	}

	private File downloadBitmap(String urlString) throws IOException {
		DiskCache cache = DiskCache.openCache();

		Log.d("hand", "打印cache值为：" + cache);
		Log.d("hand", "打印urlString值为：" + urlString);

		final File cacheFile = new File(cache.createFilePath(urlString));

		if (cache.containsKey(urlString)) {
			return cacheFile;
		}

		HttpURLConnection urlConnection = null;
		BufferedOutputStream out = null;
		try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setReadTimeout(6 * 1000);
			urlConnection.setConnectTimeout(6 * 1000);
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream(), IO_BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(cacheFile),
					IO_BUFFER_SIZE);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			cacheFile.setLastModified(System.currentTimeMillis());
		} catch (IOException e) {
			throw e;
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return cacheFile;
	}
}
