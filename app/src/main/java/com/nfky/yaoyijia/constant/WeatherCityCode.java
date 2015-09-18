package com.nfky.yaoyijia.constant;

/**
 *
 * Created by David on 8/28/15.
 *
 * WeatherCityCode包含了城市的天气代码
 *
 **/

public class WeatherCityCode {

    // 城市编码 上海
	public static final String SHANG_HAI = "101020100";

    // 城市编码 常州
	public static final String CHANG_ZHOU = "101191101";

    // 城市编码 常州
	public static final String SU_ZHOU = "101190401";

    // 城市编码 南京
	public static final String NAN_JING = "101190101";

    // 城市编码 无锡
	public static final String WU_XI = "101190201";

    // 城市编码 昆山
	public static final String KUN_SHAN = "101190404";

    // 城市编码数组，所有新添加的城市编码都要放入此数组中
	public static final String[] cityCodes = new String[] { SHANG_HAI, CHANG_ZHOU, SU_ZHOU, NAN_JING, WU_XI, KUN_SHAN };

    // 城市名称数组，所有新添加的城市名称都要放入此数组中
	public static final String[] cityNames = new String[] { "上海", "常州", "苏州", "南京", "无锡", "昆山" };

	/**
	 * 通过城市名称查找对应的城市编码
     *
	 * @param cityName 城市中文名称
	 * @return 城市编码
	 */
	public static String findCityCodeByCityName(String cityName) {
		for (int i = 0; i < cityNames.length; ++i) {
			if (cityNames[i].equals(cityName)) {
				return cityCodes[i];
			}
		}

		return null;
	}

}
