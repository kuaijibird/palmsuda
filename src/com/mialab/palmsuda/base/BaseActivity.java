package com.mialab.palmsuda.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.main.PalmSudaApp;
import com.mialab.palmsuda.main.R;
import com.mialab.palmsuda.tools.ivache.ImageCache;
import com.mialab.palmsuda.tools.ivache.ImageWorker;

/** 
 * @author  mialab 
 * @date 创建时间：2015-8-22 上午9:46:32 
 *  
 */
public class BaseActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		PalmSudaApp.SCREEN_WEIDTH = getResources().getDisplayMetrics().widthPixels;
		PalmSudaApp.SCREEN_HEIGTH = getResources().getDisplayMetrics().heightPixels;
		super.onCreate(savedInstanceState);
	}
	@Override
	protected void onResume() {
		super.onResume();
		//MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		//MobclickAgent.onPause(this);
	}

	public ImageWorker mImageWorker = null;
	public ImageWorker getImageWorker() {
		if (mImageWorker == null) {
			ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams();
			ImageWorker.ScreenWeith = getResources().getDisplayMetrics().widthPixels;
			cacheParams.memCacheSize = (1024 * 1024 * getMemoryClass(getApplicationContext())) / 4;
			// cacheParams.clearDiskCacheOnStart = true;
			mImageWorker = ImageWorker.newInstance();
			mImageWorker.setCommonResID(R.drawable.photobg, R.drawable.photobg);
			mImageWorker.addParams(Constants.APP_TAG, cacheParams);
		}
		return mImageWorker;
	}
	public int getMemoryClass(Context context) {
		return ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
	}
	
	public void showWaitDialog(String msg) {	
		MyProgressDialog.show(this, msg);
	}

	public void dismissDlg() {
		MyProgressDialog.hide();
	}
	
	
	public void showInfoDlg(String msg) {
		if (!this.isFinishing()) {
			new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_info).setTitle("友情提示")
					.setMessage("" + msg).setPositiveButton(android.R.string.ok, null).create().show();
		}
	}
}
