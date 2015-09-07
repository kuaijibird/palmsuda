package com.mialab.palmsuda.main;

//import com.app.palmsuda.main.WapPageActivity;

import java.util.Collections;

import com.mialab.palmsuda.base.BaseActivity;
import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.modules.ContentItem;
import com.mialab.palmsuda.modules.ModuleItem;
import com.mialab.palmsuda.service.BackService;
import com.mialab.palmsuda.tools.util.FunctionUtil;
import com.mialab.palmsuda.views.VScrollTextView;
import com.mialab.palmsuda.main.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * @author mialab
 * @date 创建时间：2015-8-22 上午9:42:14
 * 
 */
public class PalmSudaHome extends BaseActivity {
	private static final String TAG = Constants.APP_TAG;
	private static final int DIALOG_CONFIRM_QUIT = 1;
	private static final int DIALOG_CONFIRM_UPDATE = 2;
	private static final int DIALOG_ABOUT = 3;
	private static final int DIALOG_SD_ERROR = 4;
	private static final int DIALOG_NETWORK_ERROR = 5;
	private static final int DIALOG_FUNCTION_ERROR = 6;
	private static final int DIALOG_NOMORE_SPACE = 7;

	private MainViewHelper mViewHelper;
	private SharedPreferences settings;
	private VScrollTextView tvName;
	private ContentItem aItem;
	private String contentName = "";
	public String versionName = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.palm_main);
		
		getImageWorker().setOnScreen(Constants.APP_TAG, true);
		// 获取本地设置文件
		settings = PalmSudaApp.getInstance().getSettings();

		mViewHelper = new MainViewHelper(this);
		tvName = (VScrollTextView) findViewById(R.id.grid_title_name);

		tvName.setTextSize(this, 20);
		tvName.setTextColor(Color.WHITE);
		tvName.setTxtInfo(getString(R.string.app_name));

		loadData();

	}

	/**
	 * 加载本地程序
	 */
	private void loadData() {
		PalmSudaApp.contentId = "";

		if (TextUtils.isEmpty(PalmSudaApp.contentId)) {
			Log.d("hand", "-------------------walk this way------------------");
			handleMainView("10285", "苏州大学", true, false);
		}
	}

	private void handleMainView(final String contentid, final String addr,
			final boolean isfirst, final boolean isSwitch) {
		if (!isfirst) {
			showWaitDialog(getString(R.string.weather_flush_content));
		}
		MaintainMgr.getInstance().getModuleList(contentid, addr, versionName,
				isfirst, new MaintainMgr.ContentReadyCallback() {
					@Override
					public void dataReady(Object data) {
						if (data != null) {
							aItem = (ContentItem) data;
							setMainView(aItem, isSwitch);
						} else {
							dismissDlg();
							showToast("没有获得数据,请查看网络是否通畅！");
						}
					}
				});
	}

	private void setMainView(ContentItem aItem, boolean isSwitch) {
		mViewHelper.setContentItem(aItem);
		contentName = aItem.getContentName();
		// dismissDlg();

		Log.d("hand",
				"----------aItem.getContentName(): " + aItem.getContentName()
						+ "-----------");
		Log.d("hand",
				"----------aItem.getMobileNum() is: " + aItem.getMobileNum()
						+ "-----------");
		Log.d("hand",
				"----------aItem.getBackgroundUrl() is: "
						+ aItem.getBackgroundUrl() + "-----------");
		Log.d("hand", "----------aItem.getmItems() is: " + aItem.getmItems()
				+ "-----------");

		PalmSudaApp.contentId = aItem.getContentId();
		settings.edit().putString(Constants.SAVE_SELECT_AREA, contentName)
				.putString(Constants.SAVE_AREA_ID, PalmSudaApp.contentId)
				.commit();

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			Log.i(TAG, "handleMessage:msg.what=" + msg.what);
			switch (msg.what) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				showDialog(DIALOG_SD_ERROR);
				break;
			case 3:
				break;
			case 4:
				break;
			case 5: // Menu updated
				showDialog(DIALOG_CONFIRM_UPDATE);
				break;
			case 6:
				showToast((String) msg.obj);
				break;
			}
		}
	};

	/**
	 * GridView各item的点击跳转事件-TTT
	 * 
	 * @param area
	 * @param item
	 */
	public void gridViewClick(ContentItem area, final ModuleItem item) {
		String mdKey = item.getModuleKey();
		int type = item.getType();

		Log.d("hand",
				"-----------item.getModuleKey() is: " + item.getModuleKey()
						+ "---------");
		Log.d("hand", "-----------item.getType() is: " + item.getType()
				+ "---------");

		if (TextUtils.isEmpty(mdKey)) {
			this.showToast(R.string.dialog_net_error_msg);
			return;
		}

		if (item.getOptions().isNeedNetwork()
				&& !FunctionUtil.isNetworkConnected()) {
			this.showDialog(DIALOG_NETWORK_ERROR);
			return;
		}

		Log.d("hand", "-----------versionName is: " + versionName + "---------");
		Log.d("hand",
				"-----------item.getNeedClientVersion() is: "
						+ item.getNeedClientVersion() + "---------");

		if (FunctionUtil.checkVersion(versionName, item.getNeedClientVersion()) > 0) {
			Log.d("hand",
					"-----------被拦截了：  "
							+ FunctionUtil.checkVersion(versionName,
									item.getNeedClientVersion()) + "---------");
			showDialog(DIALOG_FUNCTION_ERROR);
			return;
		}

		if (type == 1) {
			// String wapUrl = MaintainMgr.getInstance().formatWapUrl(item);
			// String proxy = item.getParameter_b();
			// Intent intent1 = new Intent();
			// intent1.putExtra(Constants.PARAM_WAP_URL, wapUrl);
			// intent1.putExtra(Constants.PARAM_WAP_TITLE,
			// item.getModuleName());
			// if (!TextUtils.isEmpty(proxy)) {
			// intent1.putExtra(Constants.PARAM_WAP_PROXY, proxy);
			// }
			// intent1.setClass(PalmSudaHome.this, WapPageActivity.class);
			// startActivity(intent1);
		} else if (type == 0) {
			Intent it = new Intent();
			it.putExtra(Constants.MODULE_URL, item.getModuleUrl());
			// it.putExtra(Constants.CITYID, area.getCityId());
			// it.putExtra(Constants.CITYNAME, area.getCityName());
			// it.putExtra(Constants.WORK_CITYID, item.getWorkCityId());
			// it.putExtra(Constants.MODULE_ID, item.getModuleId());
			// it.putExtra(Constants.MODULE_NAME, item.getModuleName());
			// it.putExtra(Constants.PARAM_A, item.getParameter_a());
			// it.putExtra(Constants.PARAM_B, item.getParameter_b());
			// it.putExtra(Constants.PARAM_C, item.getParameter_c());
			int hasad = item.getHasAd();
			if (1 == hasad) {
				it.putExtra("HASAD", hasad);
			}

			@SuppressWarnings("rawtypes")
			Class startClass = MaintainMgr.getInstance().getStartClass(mdKey,
					it);
			if (startClass != null && isClassAvailable(startClass.getName())) {
				it.setClass(PalmSudaHome.this, startClass);
				startActivity(it);
			} else {
				showDialog(DIALOG_FUNCTION_ERROR);
			}
		} else if (type == 2) {
			// String pkgname = item.getParameter_a();
		} else if (type == 9) {
			Toast.makeText(this, "对不起，该功能模块暂时尚未上线!", Toast.LENGTH_SHORT).show();
		}
	}

	void showToast(CharSequence msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

	public void showToast(int id) {
		Toast.makeText(this, id, Toast.LENGTH_LONG).show();
	}

	public void onBackPressed() {
		this.showDialog(DIALOG_CONFIRM_QUIT);
	}

	private boolean isClassAvailable(String classname) {
		boolean isava = true;
		try {
			if (Class.forName(classname) != null) {
				isava = true;
			}
		} catch (Exception e) {
			Log.d(Constants.APP_TAG, "isClassAvailable", e);
			isava = false;
		}
		return isava;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mImageWorker != null)
			mImageWorker.setOnScreen(Constants.APP_TAG, false);

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (mImageWorker != null)
			mImageWorker.setOnScreen(Constants.APP_TAG, true);
		if (mViewHelper != null) {
			mViewHelper.notifyDataChange();
		}
	}

	public void onDestroy() {
		Log.d(TAG, "============PalmSuda  onDestroy=============");
		super.onDestroy();
		try {
			if (mViewHelper != null) {
				mViewHelper.onDestroy();
			}

			MaintainMgr.getInstance().runBackground(new Runnable() {
				@Override
				public void run() {
					MaintainMgr.getInstance().cleardata();
					// NetworkUtilities.shutDown();
				}
			});
			System.exit(0);
		} catch (Exception e) {
		}
	}

	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DIALOG_CONFIRM_QUIT:
			return new AlertDialog.Builder(PalmSudaHome.this)
					.setIcon(R.drawable.d_alert_dialog_icon)
					.setTitle(R.string.alert_dialog_quit_title)
					.setMessage(R.string.alert_dialog_quit_msg)
					.setPositiveButton(R.string.alert_dialog_yes,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
									finish();
								}
							})
					.setNegativeButton(R.string.alert_dialog_no,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									/* User clicked Cancel so do some stuff */
									dialog.cancel();
								}
							}).create();
		case DIALOG_ABOUT:
			int iconid = android.R.drawable.ic_dialog_alert;
			String msg = this.getResources().getString(
					R.string.dialog_about_msg);
			return new AlertDialog.Builder(this)
					.setIcon(iconid)
					.setTitle(R.string.menu_about)
					.setMessage(msg)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							}).create();
		case DIALOG_SD_ERROR:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.dialog_error_title)
					.setMessage(R.string.dialog_sd_error_msg)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							}).create();
		case DIALOG_NOMORE_SPACE:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.kind_tips)
					.setMessage(R.string.dialog_sd_nospace_msg)
					.setPositiveButton(android.R.string.ok,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.cancel();
								}
							}).create();
		case DIALOG_NETWORK_ERROR:
			return new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle(R.string.dialog_error_title)
					.setMessage(R.string.dialog_net_error_msg)
					.setPositiveButton(R.string.common_setting,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									startActivity(new Intent(
											Settings.ACTION_WIRELESS_SETTINGS));
									dialog.cancel();
									finish();
								}
							})
					.setNegativeButton(R.string.common_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									dialog.dismiss();
									finish();
								}
							}).create();
		}
		return null;
	}
}
