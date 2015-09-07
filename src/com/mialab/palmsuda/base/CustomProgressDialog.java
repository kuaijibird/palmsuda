package com.mialab.palmsuda.base;

import com.mialab.palmsuda.main.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomProgressDialog extends Dialog {
	private TextView tv_tips;
	private ImageView img;
	private String title;
	private AnimationDrawable animationDrawable;

	public CustomProgressDialog(Context context) {
		super(context, R.style.successDialog);
		setOwnerActivity((Activity) context);
		setCanceledOnTouchOutside(true);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void show() {
		super.show();
		animationDrawable = (AnimationDrawable) img.getDrawable();
		animationDrawable.start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.base_progress_dialog);
		tv_tips = (TextView) findViewById(R.id.tv_dialog_text);
		img = (ImageView) findViewById(R.id.img_loading);
		if (!TextUtils.isEmpty(title)) {
			tv_tips.setText(title);
		}
	}
}
