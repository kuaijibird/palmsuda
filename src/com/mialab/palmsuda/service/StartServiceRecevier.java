package com.mialab.palmsuda.service;

import java.util.Calendar;

import com.mialab.palmsuda.common.Constants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author mialab
 * @date 创建时间：2015-9-3 下午11:26:43
 * 
 */
public class StartServiceRecevier extends BroadcastReceiver {
	public String TAG = Constants.APP_TAG;

	@Override
	public void onReceive(Context context, Intent intent) {
		try {
			Log.d(TAG, "StartServiceRecevier:onReceive:[" + intent.getAction()
					+ "]");

			// 设置Action封装到Intent对象中，启动BackService，
			// 由BackService根据不同的Action，做相应的处理。

			int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
			if (intent.getAction().equals("start.service.receiver.action")
					&& hour > 7 && hour < 22) {
				Intent it = new Intent(context, BackService.class);
				it.setAction("PULLDATA_WEATHER");
				context.startService(it);
			}

			/*
			 * if (intent.getAction().equals("start.service.receiver.action") &&
			 * hour > 7 && hour < 22) { Intent it = new Intent(context,
			 * BackService.class); it.setAction("PULLDATA");
			 * context.startService(it); }
			 */
		} catch (Exception e) {
		}

	}

}
