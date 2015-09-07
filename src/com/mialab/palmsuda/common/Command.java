package com.mialab.palmsuda.common;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;

import com.mialab.palmsuda.common.Constants;
import com.mialab.palmsuda.main.PalmSudaApp;
import com.mialab.palmsuda.tools.util.FunctionUtil;

import android.text.TextUtils;
import android.util.Log;

public class Command {
	public static final String PARAMS_CMD = "cmd";
	
	
	
	/*
	private static final String SERVER_URL = Constants.SERVER_URL;

	public static final String PARAMS_CMD = "cmd";

	// login
	public static final String CMD_USER_LOGIN = "user.login";
	// logout
	public static final String CMD_USER_LOGOUT = "user.logout";

	public static final String TOKEN = "WANGYAN-FREY-19740714";

	// Register sms
	public static final String CMD_USER_SMS_REG = "user.sms.send";

	public static final String CMD_USER_REGISTER = "user.reg";
	public static final String CMD_USER_CHANGE_PW = "user.password.change";
	public static final String CMD_USER_PW_RESET = "user.password.reset";
	public static final String CMD_USER_STATUS = "user.login.status";

	public static final String CMD_USER_CUMULATION = "user.used.cumulation";
	public static final String CMD_USER_CUMULATION2 = "flowcenter.list.item";

	public static final String CMD_COLLECT_REFERRER = "collect.referrer";

	// Common
	public static final String COMMON = "common.";
	public static final String CMD_COMMON_LIST_HOT_CITY = "common.hot.city";
	public static final String CMD_COMMON_LIST_CITY_BY_NAME = "common.list.city";
	public static final String CMD_COMMON_GET_MY_AWARD = "common.my.award";
	public static final String CMD_COMMON_LIST_SUPPORT_CITY = "common.support.city";
	public static final String CMD_COMMON_LOCATION_INFO = "common.location.info";
	public static final String CMD_COMMON_LIST_MY_MSG = "common.my.msg";
	public static final String CMD_COMMON_GET_MSG_BY_ID = "common.msg.by.id";
	public static final String CMD_COMMON_CRASH_LOG = "common.crash.log";
	public static final String CMD_COMMON_PULL_CLICK_LOG = "common.pull.click.log";

	// Weather
	public static final String WEATHER = "weather.";
	public static final String CMD_WEATHER_CURRENT_INFO = "weather.current.info";
	public static final String CMD_WEATHER_INFO = "weather.info";
	public static final String CMD_WEATHER_CALENDAR_MONTH = "weather.calendar.month";
	public static final String CMD_WEATHER_CALENDAR_DATE = "weather.calendar.date";

	// Collect
	public static final String COLLECT = "collect.";
	public static final String CMD_COLLECT_MOBILE_INFO = "collect.mobile.info";
	public static final String CMD_COLLECT_CUSTOMER_RESPONSE = "collect.customer.response";
	public static final String CMD_COLLECT_WEB_DOWNLOAD = "collect.web.download";
	public static final String CMD_COLLECT_FUNCTION_USED = "collect.function.used";
	public static final String CMD_COLLECT_MARKET_INSTALL = "collect.market.install";

	public static final String CMD_COLLECT_MOBILE_PARAMETER = "collect.mobile.parameter";

	// Control
	public static final String CONTROL = "control.";
	public static final String CMD_CONTROL_GET_CITY_CONFIG = "control.city.config";
	public static final String CMD_CONTROL_GET_MOBILE_NUMBER = "control.mobile.number";
	public static final String CMD_CONTROL_GET_SUPER_CONFIG = "control.super.config";
	public static final String CMD_CONTROL_SAVE_ACCESS_LOG = "control.accesslog.save";

	// Software
	public static final String SOFTWARE = "software.";
	public static final String CMD_SOFTWARE_LIST_TOP = "software.top";
	public static final String CMD_SOFTWARE_LIST_HOT = "software.hot";
	public static final String CMD_SOFTWARE_LIST_TYPE = "software.type";
	public static final String CMD_SOFTWARE_LIST_BY_TYPE = "software.by.type";
	public static final String CMD_SOFTWARE_SEARCH = "software.search";

	// Pull
	public static final String CMD_PULL_DATA_NORMAL = "pull.data.normal";
	public static final String CMD_PULL_DATA_WEATHER = "pull.data.weather";

	// Goods
	public static final String ALIPAY_GOODS_LIST = "alipay.goods.list";
	public static final String ALIPAY_GET_ORDER = "alipay.get.order";
	public static final String ALIPAY_GOODS_LIST_WITH_TYPE = "alipay.goods.with.sub.type.list";
	public static final String ALIPAY_ORDER_LIST = "alipay.order.list";
	public static final String ALIPAY_ORDER_DETAIL = "alipay.order.detail";
	public static final String ALIPAY_ORDER_DELETE = "alipay.order.delete";

	public static final String CMD_BIND_MOBILE = "control.bind.mobile";

	public static final String CMD_USER_TITLE_INTEGRAL = "user.title.integral";
	public static final String CMD_CONTROL_MOBILE_INFO = "control.mobile.info";
	public static final String CMD_CONTROL_MODULES_ADVERT = "control.city.modules.advert";

	// Account
	public static final String ACCOUNT = "account.";
	public static final String CMD_ACCOUNT_LIVING_LIST = "account.living.list";
	public static final String CMD_ACCOUNT_LIVING_DEL = "account.living.del";
	public static final String CMD_ACCOUNT_LIVING_BIND = "account.living.bind";
	public static final String CMD_ACCOUNT_BIND = "account.bind";

	public static final String CMD_USER_EXT_INFO = "user.ext.info";
	public static final String CMD_USER_NUM_UPDATE = "user.mobilenum.update";

	// Travel
	public static final String CMD_TRAVEL_LIST_LINE = "travel.line";
	public static final String CMD_TRAVEL_LINE_DETAIL = "travel.line.detail";
	public static final String CMD_TRAVEL_BOOK_LINE = "travel.book.line";
	public static final String CMD_TRAVEL_RECOMMAND_LINE = "travel.recommand.line";
	public static final String CMD_TRAVEL_RECOMMAND_AGENCY = "travel.recommand.agency";
	public static final String CMD_TRAVEL_LIST_TYPE = "travel.type";
	public static final String CMD_TRAVEL_ADVERT = "travel.advert";
	public static final String CMD_TRAVEL_CALL_LOG = "travel.call.log";

	public static final String CMD_CALL_LOAG_ADD = "activity.call.log.add";

	
	public static String getCommandT1(String para) {
		if (para == null) {
			return null;
		}
		return FunctionUtil.EncoderByMd5(TOKEN + para);
	}
	*/

}
