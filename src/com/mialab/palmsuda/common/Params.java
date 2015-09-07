package com.mialab.palmsuda.common;

public class Params {
	public static final String PARAMS_ACCOUNT_ID = "accountid";
	public static final String PARAMS_ACCOUNT_TYPE = "accounttype";
	public static final String PARAMS_LOGIN_ID = "loginid";
	public static final String PARAMS_LOGIN_PW = "loginpw";
	public static final String PARAMS_LOGIN_IMSI = "loginimsi";
	public static final String PARAMS_USER_NAME = "username";
	public static final String PARAMS_USER_PWD = "password";
	//public static final String PARAMS_USER_PWD_NEW = "newpassword";
	//public static final String PARAMS_USER_REG_CODE = "regcode";
	public static final String PARAMS_USER_NICK_NAME = "nickname";
	//public static final String PARAMS_USER_ADDRESS = "address";
	public static final String PARAMS_USER_BIRTHDAY = "birthday";
	public static final String PARAMS_USER_EMAIL = "email";
	public static final String PARAMS_USER_CITY = "city";
	public static final String PARAMS_USER_DESC = "desc";
	public static final String PARAMS_USER_SEX = "sex";
	public static final String PARAMS_NEW_MOBILE_NUMBER = "newmobilenum";
	public static final String PARAMS_MOBILE_TOKEN = "token";
	
	/*
	// Login
	public static final String PARAMS_LOGIN_ID = "loginid";
	public static final String PARAMS_LOGIN_PW = "loginpw";
	public static final String PARAMS_LOGIN_IMSI = "loginimsi";
	public static final String PARAMS_DISPLAY_NAME = "displauname";
	public static final String PARAMS_LOGIN_TOKEN = "logintoken";
	public static final String PARAMS_LOGIN_ISSAVE = "loginsavepw";
	public static final String PARAM_SAVE_LOGIN = "saveaccount";
	public static final String PARAMS_SMS_SEND_ID = "sendid";
	public static final String PARAMS_SMS_SEND_MESSAGE = "message";
	public static final String PARAMS_USER_NAME = "username";
	public static final String PARAMS_USER_PWD = "password";
	public static final String PARAMS_USER_PWD_NEW = "newpassword";
	public static final String PARAMS_USER_REG_CODE = "regcode";
	public static final String PARAMS_USER_NICK_NAME = "nickname";
	public static final String PARAMS_USER_ADDRESS = "address";
	public static final String PARAMS_USER_BIRTHDAY = "birthday";
	public static final String PARAMS_USER_EMAIL = "email";
	public static final String PARAMS_USER_CITY = "city";
	public static final String PARAMS_USER_DESC = "desc";
	public static final String PARAMS_USER_SEX = "sex";
	public static final String PARAMS_USER_NEWPWD = "newpassword";

	public static final String PARAMS_REFERRER = "referrer";

	// Account
	public static final String PARAMS_ACCOUNT_ID = "accountid";
	public static final String PARAMS_ACCOUNT_NAME = "username";
	public static final String PARAMS_ACCOUNT_IDCARD = "idcard";
	public static final String PARAMS_ACCOUNT_AREA = "area";
	public static final String PARAMS_ACCOUNT_PASSWD = "passwd";
	public static final String PARAMS_ACCOUNT_TYPE = "accounttype";
	public static final String PARAMS_ACCOUNT_SUB_TYPE = "accountsubtype";
	public static final String PARAM_ACCOUNT_PASSWORD = "password";

	public static final String PARAM_ACCOUNT_OPTIONS = "account_options";
	public static final String PARAM_ACCOUNT_PULL_START = "pullStartHour";
	public static final String PARAM_ACCOUNT_PULL_END = "pullEndHour";

	// Common
	public static final String PARAMS_PAGE_NUM = "page";
	public static final String PARAMS_PAGE_SIZE = "size";
	public static final String PARAMS_SEARCH_TYPE = "type";
	public static final String PARAMS_SEARCH_SRC = "src";
	public static final String PARAMS_SEARCH_KEY = "searchkey";
	public static final String PARAMS_SEARCH_SIFT = "sift";
	public static final String PARAMS_CITY = "city";
	public static final String PARAMS_MOBILE_TOKEN = "token";
	public static final String PARAMS_PRODUCT_ID = "productid";
	public static final String PARAMS_CITY_ID = "cityid";
	public static final String PARAMS_WORK_CITY_ID = "workcityid";
	public static final String PARAMS_CITY_ADDRESS = "cityaddress";
	public static final String PARAMS_TICKET = "ticket";
	public static final String PARAMS_CHECK_UPDATE = "checkupdate";
	public static final String PARAMS_LANGUAGE = "language";
	public static final String PARAMS_3G_TYPE = "3gtype";
	public static final String PARAMS_MODULE_KEY = "modulekey";
	public static final String PARAMS_LOCATION_INFO = "locationinfo";
	public static final String PARAMS_TODAY = "today";
	public static final String PARAMS_SWITCH = "switch";
	public static final String PARAMS_CDMA_LOCATION = "cdmaloc";
	public static final String PARAMS_PULL_CLICK_TYPE = "pullclicktype";// 1=open,
																		// 2=close
	public static final String PARAMS_T1 = "t1";

	// Pull
	public static final String PARAMS_BELONE_CITY = "belonecity";
	public static final String PARAMS_BELONE_GROUPS = "belonegroups";
	public static final String PARAMS_BELONE_LEVEL = "belonelevel";
	public static final String PARAMS_PULL_OPTIONS = "pulloptions";
	public static final String PARAMS_RECEIVED_MSGS = "receivedmsgs";

	// Mobile
	public static final String PARAMS_NEW_MOBILE_NUMBER = "newmobilenum";
	public static final String PARAMS_MOBILE_NUMBER = "mobilenum";
	public static final String PARAMS_MOBILE_DEVICEID = "deviceid";
	public static final String PARAMS_MOBILE_MODEL = "model";
	public static final String PARAMS_MOBILE_SDK = "sdk";
	public static final String PARAMS_VERSION_NAME = "version";
	public static final String PARAMS_IP = "ip";
	public static final String PARAMS_IMSI = "imsi";
	public static final String PARAMS_CUSTOMER_RESPONSE = "customresponse";
	public static final String PARAMS_CUSTOMER_CONTACTS = "customcontacts";
	public static final String PARAMS_USE_FUNCTIONS = "usefunctions";
	public static final String PARAMS_INSTALL_APPS = "installapps";

	public static final String PARAMS_APP_UPDATE = "appupdate";

	// weather
	public static final String PARAMS_CITY_WEATHER_ID = "weatherid";
	
	public static final String PARAMS_MOBILE_PARAMETERS = "parameters";
	
	// Travel
		public static final String PARAMS_PRICE = "price";
		public static final String PARAMS_DAYS = "days";
		public static final String PARAMS_PLACE = "place";
		public static final String PARAMS_LINE_ID = "lineid";
		public static final String PARAMS_LINE_TYPE = "type";
		public static final String PARAMS_USER_ID = "userid";
		public static final String PARAMS_BOOKER_NAME = "bookername";
		public static final String PARAMS_BOOKER_PHONE = "bookerphone";
		public static final String PARAMS_BOOKER_NUMBER = "bookernum";
		public static final String PARAMS_BOOK_DESC = "bookdesc";
	    public static final String PARAMS_LINE_NAME = "linename";
	    public static final String PARAMS_AGENCY_ID = "agencyid";
	    public static final String PARAMS_AGENCY_NAME = "agencyname";
	    public static final String PARAMS_AGENCY_DEPT = "agencydept";
	    public static final String PARAMS_CALL_PHONE = "phone";
	    */

}
