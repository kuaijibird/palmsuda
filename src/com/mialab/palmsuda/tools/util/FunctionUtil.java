package com.mialab.palmsuda.tools.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.main.PalmSudaApp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

/** 
 * @author  mialab 
 * @date 创建时间：2015-8-4 下午11:35:59 
 *  
 */
public class FunctionUtil {
	public static String EncoderByMd5(String str) {
		if (TextUtils.isEmpty(str)) {
			return "";
		}
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md5.update(str.getBytes());
		String strDes = bytes2Hex(md5.digest()); // to HexString
		return strDes;
	}
	
	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;

		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	public static boolean isNetworkConnected() {
		ConnectivityManager cManager = (ConnectivityManager) PalmSudaApp.getInstance().getSystemService(
				Context.CONNECTIVITY_SERVICE);

		if (cManager != null) {
			NetworkInfo localNetworkInfo = cManager.getActiveNetworkInfo();
			if (localNetworkInfo != null)
				return localNetworkInfo.isConnectedOrConnecting();
		}
		return false;
	}
	
	public static String[] getUrlSchemeHostPath(String moduleUrl) {
		String[] retu = new String[3];
		if (TextUtils.isEmpty(moduleUrl)) {
			retu[0] = Constants.HTTP_SCHEME;
			retu[1] = Constants.HTTP_HOST;
			retu[2] = Constants.HTTP_PATH;
			return retu;
		}

		String[] splits = moduleUrl.split("/");

		retu[0] = splits[0].replace(":", "");
		retu[1] = splits[2];
		retu[2] = "";
		for (int i = 3; i < splits.length - 1; i++) {
			retu[2] += splits[i] + "/";
		}
		retu[2] += splits[splits.length - 1];
		return retu;
	}
	
	/**
	 * 
	 * @param localVersion
	 *            String
	 * @param serverVersion
	 *            String
	 * @return 0 = same, -1 = local version newer than server, 1 = local version
	 *         old than server, -2 unknown
	 */
	public static int checkVersion(String localVersion, String serverVersion) {
		try {
			if (TextUtils.isEmpty(localVersion) || TextUtils.isEmpty(serverVersion)) {
				return -2;
			}
			String[] localVs = localVersion.split("\\.");
			String[] serverVs = serverVersion.split("\\.");

			int index = Math.min(localVs.length, serverVs.length);
			for (int i = 0; i < index; i++) {
				if (getInt(localVs[i]) == getInt(serverVs[i])) {
					continue;
				} else if (getInt(localVs[i]) < getInt(serverVs[i])) {
					return 1;
				} else {
					return -1;
				}
			}
		} catch (Exception e) {
			return 1;
		}
		return 0;
	}
	
	public static int getInt(String str) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return 0;
		}
	}

}
