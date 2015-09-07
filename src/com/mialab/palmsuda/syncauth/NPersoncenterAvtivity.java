package com.mialab.palmsuda.syncauth;

import com.mialab.palmsuda.main.R;

import android.accounts.AccountAuthenticatorActivity;
import android.os.Bundle;

public class NPersoncenterAvtivity extends AccountAuthenticatorActivity {
	public static final String PARAM_CONFIRMCREDENTIALS = "confirmCredentials";
	public static final String PARAM_AUTHTOKEN_TYPE = "authtokenType";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
	}

}
