package com.mialab.palmsuda.syncauth;

import android.accounts.Account;
import android.accounts.AccountManager;

/** 
 * @author  mialab 
 * @date 创建时间：2015-8-23 上午6:29:44 
 *  
 */
public class PalmAccount {
	private String accountName = "";
	private String password = "";
	private String nickname = "";
	private String authcode = "";
	private String birthDate = "";
	private String cityName = "";
	private int sex = 0;//0 男，1 女
	private String email = "";
	private String mkeywords = "";
	public int type;
	private String imsi = "";
	private String newNum = "";

	public PalmAccount() {

	}

	public PalmAccount(Account account, AccountManager mAccountManager) {
		accountName = account.name;
		password = mAccountManager.getPassword(account);
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAuthcode() {
		return authcode;
	}

	public void setAuthcode(String authcode) {
		this.authcode = authcode;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMkeywords() {
		return mkeywords;
	}

	public void setMkeywords(String mkeywords) {
		this.mkeywords = mkeywords;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("\taccountName=" + accountName);
		return sb.toString();
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getNewNum() {
		return newNum;
	}

	public void setNewNum(String newNum) {
		this.newNum = newNum;
	}


}
