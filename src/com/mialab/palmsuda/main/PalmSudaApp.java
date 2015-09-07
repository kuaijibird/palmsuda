package com.mialab.palmsuda.main;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.common.Params;
import com.mialab.palmsuda.service.ServiceManager;
import com.mialab.palmsuda.service.StartServiceRecevier;
import com.mialab.palmsuda.syncauth.AuthenticationUtil;
import com.mialab.palmsuda.syncauth.PalmAccount;

//import com.mialab.push_client.ServiceManager;

/**
 * @author mialab
 * @date 创建时间：2015-8-22 上午9:40:48
 * 
 */
public class PalmSudaApp extends Application {
	private static final String TAG = Constants.APP_TAG;
	private static PalmSudaApp app = null;
	private SharedPreferences settings;
	private ServiceManager serviceManager;

	public static String versionName = "";

	// public static final int PRODUCT_ID = 100;
	// public static final int WORK_PRODUCT_ID = 100;
	// private BatteryReceiver mbreceiver;
	// public static final int appid = PRODUCT_ID;
	private PendingIntent sender;
	public static String contentId = "";

	public static int SCREEN_WEIDTH = 0;
	public static int SCREEN_HEIGTH = 0;

	public static PalmSudaApp getInstance() {
		return app;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		Log.d(TAG, "This is PalmSuda App.....");
		Log.d(TAG, "Git查看文件修改内容99999.....");

		CrashHandler crashHandler = CrashHandler.getInstance();
		crashHandler.init(getApplicationContext());
		settings = getSharedPreferences("APP_SETTING", Application.MODE_PRIVATE);

		SCREEN_WEIDTH = getResources().getDisplayMetrics().widthPixels;
		SCREEN_HEIGTH = getResources().getDisplayMetrics().heightPixels;

		serviceManager = new ServiceManager(this);
		serviceManager.setNotificationIcon(R.drawable.notification);
		serviceManager.startService();
		bindReceiver(20);
	}

	public SharedPreferences getSettings() {
		if (settings == null) {
			settings = getSharedPreferences("APP_SETTING",
					Application.MODE_PRIVATE);
		}
		return settings;
	}

	public void setSaveString(String key, String value) {
		if (settings == null) {
			settings = getSharedPreferences("APP_SETTING",
					Application.MODE_PRIVATE);
		}
		settings.edit().putString(key, value).commit();
	}

	public String getSaveString(String key) {
		if (settings == null) {
			settings = getSharedPreferences("APP_SETTING",
					Application.MODE_PRIVATE);
		}
		return settings.getString(key, "");
	}

	public void bindReceiver(int minutes) {
		Log.d("bindReceiver", "##bindReceiver: repeat minutes=" + minutes);
		Intent intent = new Intent(PalmSudaApp.getInstance(),
				StartServiceRecevier.class);
		intent.setAction("start.service.receiver.action");
		AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		if (sender != null) {
			am.cancel(sender);
		} else {
			sender = PendingIntent.getBroadcast(PalmSudaApp.getInstance(), 0,
					intent, PendingIntent.FLAG_UPDATE_CURRENT);
		}
		long trigerTime = System.currentTimeMillis() + 300000
				+ (long) (300000 * Math.random());
		if (Constants.TEST_MODE || Constants.DEBUG_MODE) {
			minutes = 3;
			trigerTime = System.currentTimeMillis() + 90000;
		}
		am.setRepeating(AlarmManager.RTC_WAKEUP, trigerTime, minutes * 60000,
				sender);
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
		try {
			System.gc();
		} catch (Exception e) {
		}
	}

	public void onTerminate() {
		Log.d(TAG, "PalmSuda App is Stoped.....");
		super.onTerminate();
	}

	/**
	 * 获取渠道信息
	 */
	public String getMetaData() {
		try {
			ApplicationInfo appInfo = this.getPackageManager()
					.getApplicationInfo(getPackageName(),
							PackageManager.GET_META_DATA);
			return appInfo.metaData.getString("UMENG_CHANNEL");
		} catch (Exception e) {
		}
		return "";
	}

	public String getDeviceID() {
		TelephonyManager teleMgr = (TelephonyManager) getBaseContext()
				.getSystemService(Context.TELEPHONY_SERVICE);
		String deviceId = teleMgr.getDeviceId();
		return deviceId;
	}

	public String getMacAddress() {
		WifiManager wManager = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wManager.getConnectionInfo();
		return "MAC:" + wInfo.getMacAddress();
	}

	public String getSubscriberID() {
		String imsi = "";
		try {
			TelephonyManager teleMgr = (TelephonyManager) getBaseContext()
					.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = teleMgr.getSubscriberId();
		} catch (Exception e) {
			imsi = "";
		}
		return imsi;
	}

	public String getCurrentVersion() {
		try {
			if (TextUtils.isEmpty(this.versionName)) {
				PackageInfo info = this.getPackageManager().getPackageInfo(
						this.getPackageName(), 0);
				// this.versionCode = info.versionCode;
				this.versionName = info.versionName;
			}
		} catch (NameNotFoundException e) {
		}
		return this.versionName;
	}

	public String getMobileNumber() {
		String mobileNum = settings.getString(Constants.SAVE_MOBILE_NUM, "");
		if (TextUtils.isEmpty(mobileNum)) {
			PalmAccount cAccount = AuthenticationUtil.getAccountData(this);
			if (cAccount != null && cAccount.type == 0) {
				settings.edit()
						.putString(Constants.SAVE_MOBILE_NUM,
								cAccount.getAccountName()).commit();
				mobileNum = cAccount.getAccountName();
			}
		}
		return mobileNum;
	}

	public void saveMobileNumber(String mobileNumber) {
		if (TextUtils.isEmpty(mobileNumber)) {
			return;
		}
		Log.d("hand", "----------para mobileNumber is: " + mobileNumber
				+ "--------------");

		PalmAccount cAccount = AuthenticationUtil.getAccountData(this);

		Log.d("hand", "-----getAccountData(this) is:" + cAccount + "---------");

		if (cAccount == null) {
			cAccount = new PalmAccount();
			cAccount.setAccountName(mobileNumber);
			cAccount.setImsi(getSubscriberID());
			AuthenticationUtil.setAccountData(cAccount);

			Log.d("hand", "-----para mobileNumber is:" + mobileNumber
					+ "---------");
			Log.d("hand", "-----getSubscriberID() is:" + getSubscriberID()
					+ "---------");
			Log.d("hand", "-----saveNewAccount:" + cAccount + "---------");

			AuthenticationUtil.saveNewAccount(app);

		} else {
			AuthenticationUtil.updateAccountParameter(app,
					Params.PARAMS_LOGIN_ID, mobileNumber);
			AuthenticationUtil.updateAccountParameter(app,
					Params.PARAMS_LOGIN_IMSI, getSubscriberID());
		}
		if (settings != null) {
			Editor editor = settings.edit();
			editor.putString(Constants.SAVE_MOBILE_NUM, mobileNumber)
					.putString("CURRENTTAOCAN", "");
			editor.commit();
		}
	}

	public void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}
}
