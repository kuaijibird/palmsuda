package com.mialab.palmsuda.syncauth;

import com.mialab.palmsuda.common.Params;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * This class is an implementation of AbstractAccountAuthenticator for
 * authenticating accounts in the com.openpeak.cius.sync domain.
 */
public class Authenticator extends AbstractAccountAuthenticator {

	private static final String TAG = "Authenticator";

	private final Context mContext;

	public Authenticator(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType,
			String[] requiredFeatures, Bundle options) {
		final Intent intent = new Intent(mContext, NPersoncenterAvtivity.class);
		intent.putExtra(NPersoncenterAvtivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) {
		final Intent intent = new Intent(mContext, NPersoncenterAvtivity.class);
		final AccountManager am = AccountManager.get(mContext);
		intent.putExtra(Params.PARAMS_LOGIN_ID, am.getUserData(account, Params.PARAMS_LOGIN_ID));
		intent.putExtra(NPersoncenterAvtivity.PARAM_CONFIRMCREDENTIALS, true);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType,
			Bundle loginOptions) {
		Log.i(TAG, "getAuthToken:");

		final Bundle result = new Bundle();
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getAuthTokenLabel(String authTokenType) {
		Log.i(TAG, "getAuthTokenLabel:");

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) {
		final Bundle result = new Bundle();
		result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType,
			Bundle loginOptions) {
		Log.i(TAG, "updateCredentials:");
		final Intent intent = new Intent(mContext, NPersoncenterAvtivity.class);
		AccountManager am = AccountManager.get(mContext);
		intent.putExtra(Params.PARAMS_LOGIN_ID, am.getUserData(account, Params.PARAMS_LOGIN_ID));
		intent.setAction("android.intent.action.VIEW");
		intent.putExtra("account", account);
		intent.putExtra(NPersoncenterAvtivity.PARAM_AUTHTOKEN_TYPE, authTokenType);
		intent.putExtra(NPersoncenterAvtivity.PARAM_CONFIRMCREDENTIALS, false);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

}
