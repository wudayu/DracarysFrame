package com.nfky.yaoyijia.net.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nfky.yaoyijia.model.VcWeather;

/**
 * Created by David on 8/28/15.
 * <p/>
 * WeatherResult是根据气象台接口所单独设定的数据对象，接收天气Object Json
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResult {

	@JsonProperty(value = "weatherinfo")
	private VcWeather weather;

	/**
	 * Gets weather.
	 *
	 * @return the weather
	 */
	public VcWeather getWeather() {
		return weather;
	}

	/**
	 * Sets weather.
	 *
	 * @param weather the weather
	 */
	public void setWeather(VcWeather weather) {
		this.weather = weather;
	}

	@Override
	public String toString() {
		return "WeatherResult [weather=" + weather + "]";
	}

}
