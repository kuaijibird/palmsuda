package com.mialab.palmsuda.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.modules.ContentItem;
import com.mialab.palmsuda.modules.ModuleConfigOptions;
import com.mialab.palmsuda.modules.ModuleItem;
import com.mialab.palmsuda.modules.ModuleKey;
import com.mialab.palmsuda.tools.util.FunctionUtil;

/**
 * @author mialab
 * @date 创建时间：2015-8-22 下午10:37:14
 * 
 */
public class MaintainMgr {
	private static final String TAG = "MaintainMgr";
	private static MaintainMgr mgr;

	private ContentItem mContentItem;

	private PalmSudaApp app = PalmSudaApp.getInstance();

	private ExecutorService executorService = null;

	private MaintainMgr() {
		executorService = Executors.newFixedThreadPool(6);
	}

	public static MaintainMgr getInstance() {
		if (mgr == null) {
			mgr = new MaintainMgr();
		}
		return mgr;
	}

	private String getAssetModuleList(String contentId) throws IOException {
		Log.d("hand", "-------参数app为：  " + app + "-------");
		return getFromAsset(app, "datas/" + contentId + ".txt");
	}

	public void getModuleList(final String contentId,
			final String contentAddress, final String version,
			final boolean isfirst, final ContentReadyCallback aCallback) {
		final Handler handler = new Handler() {
			public void handleMessage(Message message) {
				aCallback.dataReady(message.obj);
			}
		};
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String src = null;
					ContentItem contentItem = null;

					if (contentItem == null) {
						src = getAssetModuleList(contentId);

						Log.d("hand", "-----------contentId is: " + contentId
								+ "---------");
						Log.d("hand", "-----------src is: " + src + "---------");

						contentItem = getContentItem(src, false, "");

						Log.d("hand", "-----------contentItem is: "
								+ contentItem + "---------");
						Log.d("hand",
								"---------------"
										+ contentItem.getContentName()
										+ "------------");
						Log.d("hand",
								"---------------" + contentItem.getMobileNum()
										+ "-----------");
						Log.d("hand",
								"---------------" + contentItem.getmItems()
										+ "---------");
						Log.d("hand",
								"---------------"
										+ contentItem.getBackgroundUrl()
										+ "---------");
					}

					if (contentItem != null) {
						mContentItem = contentItem;
					}

					handler.sendMessage(Message.obtain(handler, 0, contentItem));
				} catch (Exception e) {
					handler.sendMessage(Message
							.obtain(handler, 0, mContentItem));
					android.util.Log.e("hand", "content item---", e);
				}
			}
		});
	}

	private ContentItem getContentItem(String src, boolean isSave,
			String filename) {
		ContentItem aItem = null;
		Log.d("hand",
				"-------!TextUtils.isEmpty(src) is: " + !TextUtils.isEmpty(src)
						+ "----------------");

		if (!TextUtils.isEmpty(src)) {
			try {
				Log.d("hand", "------This way: " + "----------------");

				JSONObject object = new JSONObject(src.trim())
						.getJSONObject("ContentConfig");
				Log.d("hand", "-------JSONObject object is: " + object
						+ "----------------");

				aItem = new ContentItem();

				if (!object.isNull("id"))
					aItem.setContentId(object.getString("id"));
				if (!object.isNull("parentId"))
					aItem.setParentId(object.getString("parentId"));
				if (!object.isNull("name"))
					aItem.setContentName(object.getString("name"));
				if (!object.isNull("level"))
					aItem.setLevel(object.getInt("level"));
				if (!object.isNull("bgVer")) {
					int bgver = object.getInt("bgVer");
					aItem.setBackgroundVersion(bgver);
				}
				if (!object.isNull("bgUrl"))
					aItem.setBackgroundUrl(object.getString("bgUrl"));
				if (!object.isNull("moduleList")) {
					JSONArray jArray = object.getJSONArray("moduleList");
					int len = jArray.length();
					List<ModuleItem> mList = new ArrayList<ModuleItem>();
					Log.d("hand", "-------ArrayList<ModuleItem> is: " + mList
							+ "----------------");

					for (int i = 0; i < len; i++) {
						JSONObject jbo = jArray.getJSONObject(i);
						Log.d("hand", "-------ModuleInfo is: " + jbo
								+ "----------------");

						if (!jbo.isNull("ModuleInfo")) {
							ModuleItem mItem = new ModuleItem();
							JSONObject jo = jbo.getJSONObject("ModuleInfo");

							Log.d("hand", "-------ModuleItem mItem is: "
									+ mItem + "----------------");

							if (!jo.isNull("id"))
								mItem.setModuleId(jo.getInt("id"));
							if (!jo.isNull("version"))
								mItem.setModuleVersion(jo.getString("version"));
							if (!jo.isNull("name"))
								mItem.setModuleName(jo.getString("name"));
							if (!jo.isNull("key")) {
								mItem.setModuleKey(jo.getString("key"));
							}
							if (!jo.isNull("options"))
								mItem.setOptions(jo.getInt("options"));							
							if (!jo.isNull("iconVer")) {
								int iconVer = jo.getInt("iconVer");
								mItem.setIconVersion(iconVer);
							}
							if (!jo.isNull("iconUrl"))
								mItem.setIconUrl(jo.getString("iconUrl"));
							if (!jo.isNull("type"))
								mItem.setType(jo.getInt("type"));
							if (!jo.isNull("level"))
								mItem.setLevel(jo.getInt("level"));
							if (!jo.isNull("url"))
								mItem.setModuleUrl(jo.getString("url"));
							if (!jo.isNull("needClientVer"))
								mItem.setNeedClientVersion(jo
										.getString("needClientVer"));
							if (!jo.isNull("desc"))
								mItem.setDescription(jo.getString("desc"));

							if (!jo.isNull("dispClientVer")) {
								mItem.setDispClientVersion(jo
										.getString("dispClientVer"));
							}

							if (jo.has("isSupportAdv")) {
								mItem.setHasAd(jo.getInt("isSupportAdv"));
							}

							String dispClientVersion = mItem
									.getDispClientVersion();
							ModuleConfigOptions options = mItem.getmOptions();

							Log.d("hand", "------PalmSudaApp.versionName is:"
									+ PalmSudaApp.versionName + "----------");

							int retu = FunctionUtil.checkVersion(
									PalmSudaApp.versionName, dispClientVersion);

							Log.d("hand", "------retu:" + retu + "----------");
							Log.d("hand", "------mItem.getDispClientVersion():"
									+ mItem.getDispClientVersion()
									+ "----------");
							Log.d("hand", "------options.isMarketVersion():"
									+ options.isMarketVersion() + "----------");
							Log.d("hand", "------retu:" + mItem.getModuleName()
									+ "----------");
							Log.d("hand", "------retu:" + mItem.getIconUrl()
									+ "----------");

							retu = -1; // 仅作调试用

							if (retu < 0) {
								if (Constants.MARKET_VERSION
										&& options.isMarketVersion()) {
									continue;
								} else {
									Log.d("hand",
											"ModuleName---"
													+ mItem.getModuleName());
									mList.add(mItem);
								}
							}
						}
					}
					aItem.setmItems(mList);
				}
			} catch (Exception e) {
				Log.d("hand", "------Exception e:" + e + "----------");
			}
		}
		return aItem;
	}

	public String getFromAsset(Context context, String fileName)
			throws IOException {
		try {
			InputStream in = context.getResources().getAssets().open(fileName);
			Log.d("handong", "------------filename是  :   " + fileName);
			Log.d("handong", "------------打印 InputStream :   " + in);
			if (in != null) {
				int length = in.available();
				byte[] buffer = new byte[length];
				in.read(buffer);
				in.close();
				return new String(buffer, "UTF-8");
			}
		} catch (Exception e) {
			Log.d("handong", "------------发生Exception:   " + e);
			return null;
		}
		return null;
	}

	public String getMobileNumber() {
		String mobileNum = "";
		return mobileNum;
	}

	public Class getStartClass(String mdKey, Intent it) {
		if (mContentItem == null) {
			return null;
		}
		Class startClass = null;
		ModuleItem item = mContentItem.getModuleItemByMdKey(mdKey);
		if (mdKey.contains("weather")) {
			// it.putExtra("cityId", item.getWorkCityId());
			// startClass = WeatherActivity.class;
		} else if (mdKey.equals("suda_news")) {
			// startClass = com.suda.ui.campus_news.NewsActivity.class;
		} else if (mdKey.equals("suda_around")) {
			// startClass = com.suda.ui.around.AroundActivity.class;
		} else if (mdKey.equals("current")) {
			// startClass = com.suda.ui.current.CurrentNewsActivity.class;
		} else if (mdKey.equals("flea")) {
			// startClass = com.suda.ui.flea.FleaActivity.class;
		} else if (mdKey.contains("traffic")) {
			// startClass = com.suda.ui.traffic.TrafficActivity.class;
		} else if (mdKey.contains("pt_job")) {
			// startClass = com.suda.ui.pt_job.Pt_Job_Activity.class;
		} else if (mdKey.equals("qr_code")) {
			// startClass = com.suda.framework.zxing.CaptureActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_PERSONCENTER)) {
			// startClass = PersoncenterActivity2.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_PHONE_GUARD)) {
			// startClass =
			// com.suda.ui.website_development.Website_Develop_Activity.class;

		} else if (mdKey.equals(ModuleKey.MODULE_KEY_OPTIMIZE)) {
			// startClass = com.suda.ui.enterprise.EnterpriseActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_NETFLOW)) {
			// startClass = com.suda.ui.sike.SikeActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_PROCESS)) {
			// startClass = com.suda.ui.cad.CadActivity.class;
		} else if (mdKey.equals(ModuleKey.NOTICEMSG_TYPE_RECHARGE)) {
			// startClass = com.suda.ui.video.VideoActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_MYSETTING)) {
			// startClass = com.suda.ui.child.ChildActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_MESSAGE_CENTER)) {
			// startClass = MsgCenterActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_SOFTWARE_MANAGE)) {
			// startClass = com.suda.ui.training.TrainingActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_COMMUNICATE)) {
			// startClass = com.suda.ui.common.WordActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_CONNECT_US)) {
			// startClass = com.suda.ui.connect.ConnectActivity.class;
		} else if (mdKey.equals(ModuleKey.MODULE_KEY_FLOWPACKET)) {
			// startClass = com.suda.ui.telmgr.FlowMainActivity.class;
		}
		return startClass;
	}

	public boolean isContainsKey(String key) {
		if (mContentItem != null) {
			return mContentItem.getModuleItemByMdKey(key) != null;
		}
		return false;
	}

	public ModuleItem getModuleItemByMdKey(String key) {
		if (mContentItem != null) {
			return mContentItem.getModuleItemByMdKey(key);
		}
		return null;
	}

	public void runBackground(Runnable command) {
		executorService.execute(command);
	}

	public interface ContentReadyCallback {
		public void dataReady(Object data);
	}

	public void cleardata() {
	}
}
