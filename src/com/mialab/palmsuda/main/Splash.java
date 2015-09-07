package com.mialab.palmsuda.main;

import com.mialab.palmsuda.main.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Splash extends Activity {
	private static final String TAG = "Splash";
	private RelativeLayout cover_page;
	public String versionName = "";
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置为无标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置为全屏模式
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);

		// 获取当前版本,并设置在启动界面的TextView
		this.getCurrentVersion();

		cover_page = (RelativeLayout) findViewById(R.id.cover_page);
		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(5000);
		cover_page.startAnimation(aa);

		Animation anim = android.view.animation.AnimationUtils.loadAnimation(
				this, R.anim.rotate);
		android.view.animation.LinearInterpolator lir = new android.view.animation.LinearInterpolator();
		anim.setInterpolator(lir);
		findViewById(R.id.progressBar1).startAnimation(anim);

		handler.postDelayed(new SplashHandler(), 8000);
		// 延迟8秒，再运行SplashHandler的run()方法
	}

	class SplashHandler implements Runnable {
		@Override
		public void run() {
			gotoPalmHome();
		}
	}

	/**
	 * 加载主界面
	 */
	private void gotoPalmHome() {
		Intent intent = new Intent(this, PalmSudaHome.class);
		startActivity(intent);
		finish();// 把当前的Activity从任务栈里面移除
	}	

	public void getCurrentVersion() {
		try {
			PackageInfo info = this.getPackageManager().getPackageInfo(
					this.getPackageName(), 0);
			this.versionName = info.versionName;
			PalmSudaApp.versionName = this.versionName;

			Log.d(TAG, "Current version_name is " + versionName);

			TextView textVersion = (TextView) findViewById(R.id.textVersion);
			textVersion.setText(versionName);

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
	}	
}
