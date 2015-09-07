package com.mialab.palmsuda.syncauth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.common.Params;
import com.mialab.palmsuda.main.R;

public class AuthenticationUtil {

	public static String TAG = "AuthenticationUtil";

	public static PalmAccount mAccount;

	public synchronized static void saveNewAccount(Context context) {

		if (mAccount == null) {
			Log.e(TAG, "saveNewAccount: mAccount is empty!");
			return;
		}
		AccountManager mAccountManager = AccountManager.get(context);
		Account[] mAccounts = mAccountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);

		Log.d("hand", "-----mAccounts.length is: " + mAccounts.length
				+ "------------");

		if (mAccounts.length > 0) {
			Log.d(
					"hand",
					"saveNewAccount: account exist:"
							+ mAccount.getAccountName());
			return;
		}
		final Account account = new Account(
				context.getString(R.string.app_name), Constants.ACCOUNT_TYPE);
		Bundle userData = new Bundle();
		//userData.putInt(Params.PARAMS_ACCOUNT_TYPE, mAccount.type);
		userData.putString(Params.PARAMS_ACCOUNT_TYPE, "" + mAccount.type);

		userData.putString(Params.PARAMS_LOGIN_ID, mAccount.getAccountName());
		userData.putString(Params.PARAMS_LOGIN_PW, mAccount.getPassword());

		userData.putString(Params.PARAMS_USER_NICK_NAME, mAccount.getNickname());
		userData.putString(Params.PARAMS_USER_BIRTHDAY, mAccount.getBirthDate());
		userData.putString(Params.PARAMS_USER_CITY, mAccount.getCityName());
		userData.putString(Params.PARAMS_USER_DESC, mAccount.getMkeywords());
		//userData.putInt(Params.PARAMS_USER_SEX, mAccount.getSex());
		userData.putString(Params.PARAMS_USER_SEX, "" + mAccount.getSex());

		userData.putString(Params.PARAMS_USER_EMAIL, mAccount.getEmail());
		userData.putString(Params.PARAMS_NEW_MOBILE_NUMBER,
				mAccount.getNewNum());
		userData.putString(Params.PARAMS_LOGIN_IMSI, mAccount.getImsi());

		boolean addAccountSuccess = mAccountManager.addAccountExplicitly(
				account, mAccount.getAccountName(), userData);
		
		Log.d("hand", "--------addAccountSuccess is: " + addAccountSuccess + "-----------");
	}

	public static Account getAccount(Context context) {
		AccountManager mAccountManager = AccountManager.get(context);

		Account[] mAccounts = mAccountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);
		if (mAccounts.length > 0) {
			return mAccounts[0];
		}
		return null;
	}

	public static PalmAccount getAccountData(Context context) {
		AccountManager mAccountManager = AccountManager.get(context);

		Account[] mAccounts = mAccountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);
		if (mAccounts.length > 0) {
			PalmAccount cityAccount = new PalmAccount();
			cityAccount.setAccountName(mAccountManager.getUserData(
					mAccounts[0], Params.PARAMS_LOGIN_ID));
			cityAccount.setPassword(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_LOGIN_PW));

			cityAccount.setBirthDate(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_BIRTHDAY));
			cityAccount.setCityName(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_CITY));
			cityAccount.setEmail(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_EMAIL));
			cityAccount.setNickname(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_NICK_NAME));
			cityAccount.setMkeywords(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_DESC));
			cityAccount.setImsi(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_LOGIN_IMSI));
			cityAccount.setNewNum(mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_NEW_MOBILE_NUMBER));

			String sex = mAccountManager.getUserData(mAccounts[0],
					Params.PARAMS_USER_SEX);
			if (sex != null) {
				int i = 0;
				if (sex.equals("0")) {
					i = 0;
				} else {
					i = 1;
				}
				cityAccount.setSex(i);
			}
			return cityAccount;
		}
		return null;
	}

	public static void setAccountData(PalmAccount mAccount) {
		AuthenticationUtil.mAccount = mAccount;
	}

	public synchronized static void saveEditAccount(Context context,
			Account account) {
		if (mAccount == null) {
			Log.e(TAG, "saveEditAccount: mAccount is empty!");
			return;
		}
		AccountManager mAccountManager = AccountManager.get(context);
		mAccountManager.setPassword(account, mAccount.getAccountName());

		mAccountManager.setUserData(account, Params.PARAMS_LOGIN_PW,
				mAccount.getPassword());
		mAccountManager.setUserData(account, Params.PARAMS_LOGIN_ID,
				mAccount.getAccountName());
		mAccountManager.setUserData(account, Params.PARAMS_LOGIN_IMSI,
				mAccount.getImsi());
		mAccountManager.setUserData(account, Params.PARAMS_USER_BIRTHDAY,
				mAccount.getBirthDate());
		mAccountManager.setUserData(account, Params.PARAMS_USER_CITY,
				mAccount.getCityName());
		mAccountManager.setUserData(account, Params.PARAMS_USER_EMAIL,
				mAccount.getEmail());
		mAccountManager.setUserData(account, Params.PARAMS_USER_NICK_NAME,
				mAccount.getNickname());
		mAccountManager.setUserData(account, Params.PARAMS_USER_DESC,
				mAccount.getMkeywords());
		mAccountManager.setUserData(account, Params.PARAMS_USER_SEX,
				String.valueOf(mAccount.getSex()));
		mAccountManager.setUserData(account, Params.PARAMS_NEW_MOBILE_NUMBER,
				mAccount.getNewNum());

	}

	public synchronized static void updateAccountParameter(Context context,
			String key, String value) {
		AccountManager mAccountManager = AccountManager.get(context);
		Account[] mAccounts = mAccountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);

		mAccountManager.setUserData(mAccounts[0], key, value);
	}

	public synchronized static void deleteAccount(Context context) {
		AccountManager mAccountManager = AccountManager.get(context);
		Account[] mAccounts = mAccountManager
				.getAccountsByType(Constants.ACCOUNT_TYPE);
		if (mAccounts != null && mAccounts.length > 0) {
			for (Account account2 : mAccounts) {
				Log.d(TAG, "account2 name=" + account2.name);
				mAccountManager.removeAccount(account2, null, null);
			}
		}
	}

}
