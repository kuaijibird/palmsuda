package com.mialab.palmsuda.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mialab.palmsuda.common.Constants;

import android.content.Context;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;



/** 
 * @author  mialab 
 * @date 创建时间：2015-9-3 下午7:53:37 
 *  
 */
public class CrashHandler implements UncaughtExceptionHandler {
	public static final String TAG = Constants.APP_TAG;
	private Thread.UncaughtExceptionHandler mDefaultHandler;
	// CrashHandler实例
	private static CrashHandler INSTANCE = new CrashHandler();

	// 用于格式化日期,作为日志文件名的一部分
	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
		return INSTANCE;
	}

	public void init(Context context) {
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		android.util.Log.e(Constants.APP_TAG, "uncaughtException", ex);
		try {
			String oldexp = PalmSudaApp.getInstance().getSaveString("EXPECTIONAPP");
			String exp = getExceptionStr(ex);
			String subExp="";
			if(TextUtils.isEmpty(oldexp)){
				oldexp="";
			}
			if(!TextUtils.isEmpty(exp)&&exp.length()>30){
				subExp=exp.substring(0,30);
			}else {
				subExp="";
			}
			if (oldexp.equals(subExp)) {
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(1);
			} else {
				PalmSudaApp.getInstance().setSaveString("EXPECTIONAPP", subExp);
				if (!handleException(exp) && mDefaultHandler != null) {
					mDefaultHandler.uncaughtException(thread, ex);
				} else {
					android.os.Process.killProcess(android.os.Process.myPid());
					System.exit(1);
				}
			}
		} catch (Exception e) {
			android.util.Log.e(Constants.APP_TAG, "uncaughtException",e);
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(1);
		}
	}

	/**
	 * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
	 * 
	 * @param ex
	 * @return true:如果处理了该异常信息;否则返回false.
	 */
	private boolean handleException(final String ex) {
		if (ex == null) {
			return false;
		}
		new Thread() {
			@Override
			public void run() {
				saveCrashInfo2File(ex);
				if (Constants.DEBUG_MODE) {
					Looper.prepare();
					PalmSudaApp.getInstance().showToast("程序遇到问题，我们将会尽快处理。");
					Looper.loop();
				}
			}
		}.start();
		return true;
	}
	
	private String getExceptionStr(Throwable ex) {
		StringBuffer sb = new StringBuffer();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		ex.printStackTrace(printWriter);
		Throwable cause = ex.getCause();
		while (cause != null) {
			cause.printStackTrace(printWriter);
			cause = cause.getCause();
		}
		printWriter.close();
		String result = writer.toString();
		sb.append(result);
		return sb.toString();
	}

	/**
	 * 保存错误信息到文件中
	 * 
	 * @param ex
	 * @return 返回文件名称
	 */
	private String saveCrashInfo2File(String ex) {
		FileOutputStream fos = null;
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(formatter.format(new Date())).append("\n");

			sb.append("" + ex).append("\n");
			String fileName = "WCitycrash.log";
			String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + Constants.APP_DIR + "/"
					+ fileName;
			File file = new File(path);
			file.getParentFile().mkdirs();
			fos = new FileOutputStream(file, false);
			fos.write(sb.toString().getBytes());
			return path;
		} catch (Exception e) {
			Log.e(TAG, "saveCrashInfo2File...", e);
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}


}
