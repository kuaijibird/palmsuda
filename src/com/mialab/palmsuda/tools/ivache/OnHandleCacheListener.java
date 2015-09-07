package com.mialab.palmsuda.tools.ivache;

import android.graphics.Bitmap;
import android.widget.ImageView;

public interface OnHandleCacheListener {

	void onSetImage(final ImageView imageView, final Bitmap bitmap);

	void onError(final ImageView imageView);

}

