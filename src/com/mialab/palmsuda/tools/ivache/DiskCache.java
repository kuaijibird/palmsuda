package com.mialab.palmsuda.tools.ivache;

import java.io.File;

import com.mialab.palmsuda.tools.util.FileUtils;
import com.mialab.palmsuda.tools.util.FunctionUtil;

import android.util.Log;

/**
 * @author mialab
 * @date 创建时间：2015-8-4 下午12:48:50
 * 
 */
public class DiskCache {
	public static final String DISK_CACHE_DIR = "ivcache";

	private final File mCacheDir;

	private DiskCache(File cacheDir) {
		mCacheDir = cacheDir;
	}

	public static DiskCache openCache() {
		File cacheDir = getDiskCacheDir(DISK_CACHE_DIR);
		
		if (!cacheDir.exists()) {
			cacheDir.mkdirs();
		}
		
		Log.d("hand", "cacheDir.isDirectory()的值为：" + cacheDir.isDirectory());
		Log.d("hand", "cacheDir.canWrite()的值为：" + cacheDir.canWrite());
		
		if (cacheDir.isDirectory() && cacheDir.canWrite()) {
			return new DiskCache(cacheDir);
		}
		
		return null;
	}

	public static File getDiskCacheDir(String uniqueName) {
		final String cachePath = FileUtils.getSDPath();
		return new File(cachePath + File.separator + uniqueName);

	}

	/**
	 * Checks if a specific key exist in the cache.
	 * 
	 * @param key
	 *            The unique identifier for the bitmap
	 * @return true if found, false otherwise
	 */
	public boolean containsKey(String key) {
		final String existingFile = createFilePath(mCacheDir, key);
		File file = new File(existingFile);
		if (file != null && file.exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Removes all disk cache entries from this instance cache dir
	 */
	public void clearCache() {
		DiskCache.clearCache(mCacheDir);
	}

	private static void clearCache(File cacheDir) {
		final File[] files = cacheDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}

	public static String getFilePath(String url) {
		return FunctionUtil.EncoderByMd5(url) + ".cac";
	}

	public String createFilePath(File cacheDir, String key) {
		return cacheDir.getAbsolutePath() + File.separator + getFilePath(key);

	}

	/**
	 * Create a constant cache file path using the current cache directory and
	 * an image key.
	 * 
	 * @param key
	 * @return
	 */
	public String createFilePath(String key) {
		return createFilePath(mCacheDir, key);
	}

}
