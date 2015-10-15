package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nfky.yaoyijia.constant.Constant;

/**
 * Created by David on 8/28/15.
 * <p/>
 * DafWeather是WeatherModel的Object Json对象
 */
@JsonIgnoreProperties(ignoreUnknown = Constant.jsonIgnoreTooMuch)
public class VcWeather {

	private String cityId;
	private String city;
	private String temp1;	// Temperature
	private String temp2;
	private String weather;	// Weather
	private String ptime;

	/**
	 * Gets city id.
	 *
	 * @return the city id
	 */
	public String getCityId() {
		return cityId;
	}

	/**
	 * Sets city id.
	 *
	 * @param cityId the city id
	 */
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	/**
	 * Gets city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets city.
	 *
	 * @param city the city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets temp 1.
	 *
	 * @return the temp 1
	 */
	public String getTemp1() {
		return temp1;
	}

	/**
	 * Sets temp 1.
	 *
	 * @param temp1 the temp 1
	 */
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}

	/**
	 * Gets temp 2.
	 *
	 * @return the temp 2
	 */
	public String getTemp2() {
		return temp2;
	}

	/**
	 * Sets temp 2.
	 *
	 * @param temp2 the temp 2
	 */
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}

	/**
	 * Gets weather.
	 *
	 * @return the weather
	 */
	public String getWeather() {
		return weather;
	}

	/**
	 * Sets weather.
	 *
	 * @param weather the weather
	 */
	public void setWeather(String weather) {
		this.weather = weather;
	}

	/**
	 * Gets ptime.
	 *
	 * @return the ptime
	 */
	public String getPtime() {
		return ptime;
	}

	/**
	 * Sets ptime.
	 *
	 * @param ptime the ptime
	 */
	public void setPtime(String ptime) {
		this.ptime = ptime;
	}

	@Override
	public String toString() {
		return "CsWeather [cityId=" + cityId + ", city=" + city + ", temp1="
				+ temp1 + ", temp2=" + temp2 + ", weather=" + weather
				+ ", ptime=" + ptime + "]";
	}

}
