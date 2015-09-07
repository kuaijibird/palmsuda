package com.mialab.palmsuda.tools.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.mialab.palmsuda.common.Constants;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

/**
 * @author mialab
 * @date 创建时间：2015-8-4 下午8:18:30
 * 
 */
public class FileUtils {
	private static int FILESIZE = 4 * 1024;

	public FileUtils() {
	}

	/**
	 * 在SD卡上创建文件
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	public static File createSDFile(String fileName) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		return file;
	}

	/**
	 * 在SD卡上创建目录
	 * 
	 * @param dirName
	 * @return
	 */
	public static File createSDDir(String dirName) {
		File dir = new File(dirName);
		dir.mkdirs();
		return dir;
	}

	/**
	 * 判断SD卡上的文件夹是否存在
	 * 
	 * @param fileName
	 * @return
	 */
	public boolean isFileExist(String fileName) {
		File file = new File(FileUtils.getSDPath() + fileName);
		return file.exists();
	}

	public boolean delExistFile(String fileName) {
		File file = new File(FileUtils.getSDPath() + fileName);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	public static boolean delFile(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			return file.delete();
		}
		return false;
	}

	public static String getSDPath() {
		String path = "";
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath()
					+ "/" + Constants.APP_DIR;// 获取根目录
		} else {
			path = "D:/" + Constants.APP_DIR;
		}
		FileUtils.createSDDir(path);
		return path;
	}
	
	public static File write2SDFromByte(String fileName, byte[] buffer) {

		File file = null;
		OutputStream output = null;
		try {
			File dir = (new File(fileName)).getParentFile();
			createSDDir(dir.getAbsolutePath());
			file = createSDFile(fileName);
			output = new FileOutputStream(file);
			output.write(buffer);
			output.flush();
		} catch (Exception e) {
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				
			}
		}
		return file;
	}
	
	//用户文件是否存在
		public static boolean isFileExist(Context ct,String fileName) {
			File file = new File(ct.getFilesDir() + "/" + fileName);
			return file.exists();
		}
	
	public static String getNativeFile(Context ctx, String filename) {
		String src = "";
		if (isFileExist(ctx, filename)) {
			InputStream in = null;
			BufferedReader reader = null;
			try {
				in = ctx.openFileInput(filename);
				reader = new BufferedReader(new InputStreamReader(in));
				String line = "";
				StringBuffer buffer = new StringBuffer();
				while ((line = reader.readLine()) != null) {
					buffer.append(line);
				}
				src = buffer.toString();
			} catch (Exception e) {
				Log.e(Constants.APP_TAG, "getNativeFile", e);
			} finally {
				try {
					if (reader != null)
						reader.close();
					if (in != null)
						in.close();
				} catch (Exception e) {
				}
			}
		}
		return src;
	}
	
	//保存文件到 应用本地
		public static void saveData2Native(Context ctx,String filename, byte[] bt) {
			FileOutputStream fout = null;
			try {
				fout = ctx.openFileOutput(filename, 0);
				fout.write(bt);
				fout.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (fout != null) {
						fout.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

}
