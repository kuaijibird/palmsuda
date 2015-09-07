package com.mialab.palmsuda.service;

import java.util.Properties;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.IBinder;
import android.util.Log;

import com.mialab.push_client.Constants;
import com.mialab.push_client.LogUtil;
import com.mialab.push_client.NotificationService;
import com.mialab.push_client.NotificationSettingsActivity;

/**
 * @author mialab
 * @date 创建时间：2015-9-3 下午9:26:35
 * 
 */
public final class ServiceManager {
	private static final String LOGTAG = LogUtil
			.makeLogTag(ServiceManager.class);

	private Context context;
	private SharedPreferences sharedPrefs;
	private Properties props;
	private String version = "1.0.0";
	private String apiKey;
	private String xmppHost;
	private String xmppPort;
	private String callbackActivityPackageName;
	private String callbackActivityClassName;

	public ServiceManager(Context context) {
		this.context = context;

		if (context instanceof Activity) {
			Log.i(LOGTAG, "Callback Activity...");
			Activity callbackActivity = (Activity) context;
			callbackActivityPackageName = callbackActivity.getPackageName();
			callbackActivityClassName = callbackActivity.getClass().getName();
		}

		props = loadProperties();
		apiKey = props.getProperty("apiKey", "");
		xmppHost = props.getProperty("xmppHost", "127.0.0.1");
		xmppPort = props.getProperty("xmppPort", "5222");
		Log.i(LOGTAG, "apiKey=" + apiKey);
		Log.i(LOGTAG, "xmppHost=" + xmppHost);
		Log.i(LOGTAG, "xmppPort=" + xmppPort);

		sharedPrefs = context.getSharedPreferences(
				Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString(Constants.API_KEY, apiKey);
		editor.putString(Constants.VERSION, version);
		editor.putString(Constants.XMPP_HOST, xmppHost);
		editor.putInt(Constants.XMPP_PORT, Integer.parseInt(xmppPort));
		editor.putString(Constants.CALLBACK_ACTIVITY_PACKAGE_NAME,
				callbackActivityPackageName);
		editor.putString(Constants.CALLBACK_ACTIVITY_CLASS_NAME,
				callbackActivityClassName);
		editor.commit();
		// Log.i(LOGTAG, "sharedPrefs=" + sharedPrefs.toString());
	}

	public void startService() {
		startNotificationService();
		startBackService();
	}
	
	public void startNotificationService(){
		Thread serviceThread = new Thread(new Runnable() {
			@Override
			public void run() {
				Intent intent = NotificationService.getIntent();
				context.startService(intent);
			}
		});
		serviceThread.start();
	}
	
	public void startBackService() {
		Log.d(LOGTAG, "startBackService.....");
		Intent intent = new Intent("com.mialab.palmsuda.service.BackService");
		context.startService(intent);
		context.bindService(intent, connection, Context.BIND_AUTO_CREATE);		
	}
	
	private BackService backService;
	private ServiceConnection connection = new ServiceConnection() {
		@Override
		public void onServiceDisconnected(ComponentName name) {
			backService = null;
			Log.d(LOGTAG, "onServiceDisconnected...");
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			backService = ((BackService.BackServiceBinder) (service)).getService();
			if (backService != null) {
			}
			Log.d(LOGTAG, "onServiceConnected...");
		}
	};

	public void stopService() {
		stopNotificationService();
		stopBackService();
	}
	
	public void stopNotificationService() {
		Intent intent = NotificationService.getIntent();
		context.stopService(intent);
	}
	
	public void stopBackService() {
		Intent intent = new Intent("com.mialab.palmsuda.service.BackService");
		context.stopService(intent);
	}

	// private String getMetaDataValue(String name, String def) {
	// String value = getMetaDataValue(name);
	// return (value == null) ? def : value;
	// }
	//
	// private String getMetaDataValue(String name) {
	// Object value = null;
	// PackageManager packageManager = context.getPackageManager();
	// ApplicationInfo applicationInfo;
	// try {
	// applicationInfo = packageManager.getApplicationInfo(context
	// .getPackageName(), 128);
	// if (applicationInfo != null && applicationInfo.metaData != null) {
	// value = applicationInfo.metaData.get(name);
	// }
	// } catch (NameNotFoundException e) {
	// throw new RuntimeException(
	// "Could not read the name in the manifest file.", e);
	// }
	// if (value == null) {
	// throw new RuntimeException("The name '" + name
	// + "' is not defined in the manifest file's meta data.");
	// }
	// return value.toString();
	// }

	private Properties loadProperties() {
		// InputStream in = null;
		// Properties props = null;
		// try {
		// in = getClass().getResourceAsStream(
		// "/org/androidpn/client/client.properties");
		// if (in != null) {
		// props = new Properties();
		// props.load(in);
		// } else {
		// Log.e(LOGTAG, "Could not find the properties file.");
		// }
		// } catch (IOException e) {
		// Log.e(LOGTAG, "Could not find the properties file.", e);
		// } finally {
		// if (in != null)
		// try {
		// in.close();
		// } catch (Throwable ignore) {
		// }
		// }
		// return props;

		Properties props = new Properties();
		try {
			int id = context.getResources().getIdentifier("pushclient", "raw",
					context.getPackageName());
			props.load(context.getResources().openRawResource(id));
		} catch (Exception e) {
			Log.e(LOGTAG, "Could not find the properties file.", e);
		}
		return props;
	}

	public void setNotificationIcon(int iconId) {
		Editor editor = sharedPrefs.edit();
		editor.putInt(Constants.NOTIFICATION_ICON, iconId);
		editor.commit();
	}

	public static void viewNotificationSettings(Context context) {
		Intent intent = new Intent().setClass(context,
				NotificationSettingsActivity.class);
		context.startActivity(intent);
	}

}
