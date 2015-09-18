package com.nfky.yaoyijia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.nfky.yaoyijia.constant.Constant;

/**
 *
 * Created by David on 8/28/15.
 *
 * DafWeather是WeatherModel的Object Json对象
 *
 **/

@JsonIgnoreProperties(ignoreUnknown = Constant.jsonIgnoreTooMuch)
public class VcWeather {

	private String cityId;
	private String city;
	private String temp1;	// Temperature
	private String temp2;
	private String weather;	// Weather
	private String ptime;

	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTemp1() {
		return temp1;
	}
	public void setTemp1(String temp1) {
		this.temp1 = temp1;
	}
	public String getTemp2() {
		return temp2;
	}
	public void setTemp2(String temp2) {
		this.temp2 = temp2;
	}
	public String getWeather() {
		return weather;
	}
	public void setWeather(String weather) {
		this.weather = weather;
	}
	public String getPtime() {
		return ptime;
	}
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
