package com.mialab.palmsuda.base;

import android.content.Context;

public class MyProgressDialog {
	private static CustomProgressDialog customProgressDialog;

	public static void show(Context context, String str) {
		if (customProgressDialog == null||customProgressDialog.getOwnerActivity()==null
				||customProgressDialog.getOwnerActivity().isFinishing()) {
			customProgressDialog = new CustomProgressDialog(context);
		}
		customProgressDialog.setTitle(str);
		if (!customProgressDialog.isShowing())
			customProgressDialog.show();
		
	}

	public static void hide() {
		if (customProgressDialog != null && customProgressDialog.isShowing()) {
			customProgressDialog.dismiss();
			customProgressDialog.cancel();
		}
		customProgressDialog=null;
	}

}
