package com.mialab.palmsuda.service;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.mialab.palmsuda.main.PalmSudaApp;

/**
 * @author mialab
 * @date 创建时间：2015-8-23 上午5:49:43
 * 
 */
public class BackService extends Service {
	public String TAG = "BackService";

	private Timer timer;

	boolean ischeck = false;

	private Handler mHandler = new Handler();
	SharedPreferences spsetting;

	@Override
	public void onCreate() {
		Log.d(TAG, "BackService onCreate................");

		spsetting = PalmSudaApp.getInstance().getSettings();
		try {
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					checkVersionChange();
				}
			}, 15000);
		} catch (Exception e) {
		}
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("hand", "BackService onStartCommand................");
		flags = START_STICKY;
		handleCommand(intent);
		return super.onStartCommand(intent, flags, startId);
	}

	private void handleCommand(Intent intent) {
		try {
			if (intent == null) {
				return;
			}
			String action = "" + intent.getAction();
			if (!TextUtils.isEmpty(action)) {
				// 根据intent中的不同action处理各种不同事务
			}
		} catch (Exception e) {
			return;
		}
	}

	public void checkVersionChange() {
	}

	@Override
	public void onDestroy() {
		Log.d(TAG, "BackService onDestroy................");
		if (timer != null) {
			timer.cancel();
		}
		timer = null;
	}

	public class BackServiceBinder extends Binder {
		public BackService getService() {
			return BackService.this;
		}
	}

	private BackServiceBinder mBinder = new BackServiceBinder();

	@Override
	public IBinder onBind(Intent intent) {
		Log.d(TAG, "BackService onBind................");
		return mBinder;
	}

	public void getNetFlow() {
	}

	public void pullWeatherDataFromCloud() {
	}

	public void pullDataFromCloud() {
	}

	private void startFloatNotice() {
	}

	private void updateFloatCircleInfo() {
	}

	class RefreshTask extends TimerTask {
		@Override
		public void run() {
		}
	}
}
