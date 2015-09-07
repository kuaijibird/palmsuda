package com.mialab.palmsuda.common;
/** 
 * @author  mialab 
 * @date 创建时间：2015-8-22 下午6:04:22 
 *  
 */
public class Constants {
	public static boolean TEST_MODE = false;
	public static boolean DEBUG_MODE =true;	
	public static final String APP_TAG = "PalmSuda";
	public static final String APP_DIR = "PalmSuda";
	public static final String APP_CACHE = "/cache";
	
	public static final boolean MARKET_VERSION = false;
	
	public static final String ACCOUNT_TYPE = "com.mialab.palmsuda_demo3.sync";
	
	public static final String HTTP_SCHEME = "http";
	//public static final String HTTP_HOST = "res.51wcity.com";
	//public static final String HTTP_HOST = "mialab.suda.edu.cn";
	
	 public static final String HTTP_HOST = "192.168.0.119:8080";
	
	public static final String HTTP_PATH = "/city-service/service";
	//public static final String HTTP_PATH = "/palm-service/service";
	
	public static final String HTTP_PALM_DATA_ROOT = HTTP_SCHEME + "://"
			+ HTTP_HOST;
	public static final String SERVER_URL = HTTP_PALM_DATA_ROOT + HTTP_PATH;

	public static final String APP_UNAME = "palmsuda";
	
	public static final String SAVE_SELECT_AREA = "CityName";
	public static final String SAVE_AREA_ID = "CityID";

	public static final String SAVE_MOBILE_NUM = "mobile_number";

	public static final String MODULE_URL = "module_url";
	
	//public static final String HTTP_MODULE_LIST = "http://res.51wcity.com/WirelessSZ/city/100_%1$s.html";

}
