package com.nfky.yaoyijia.handler;

import android.test.AndroidTestCase;

import com.nfky.yaoyijia.constant.WeatherCityCode;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.net.INetHandler;
import com.nfky.yaoyijia.net.RetrofitNetHandler;
import com.nfky.yaoyijia.net.protocol.WeatherResult;

import javax.security.auth.callback.Callback;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by David on 10/16/15.
 */
public class NetTest extends AndroidTestCase {

    /**
     * 测试: getForWeather()
     */
    public void testGetForWeather() {
        INetHandler netHandler = RetrofitNetHandler.getInstance();
        netHandler.getForWeather(WeatherCityCode.findCityCodeByCityName("苏州"), new retrofit.Callback<WeatherResult>() {
            @Override
            public void success(WeatherResult weatherResult, Response response) {
                Utils.debug("========== weather = " + weatherResult.getWeather());
                assertTrue(false);
                assertEquals(1,2);
            }

            @Override
            public void failure(RetrofitError error) {
                Utils.debug("========== error");
                assertTrue(false);
                assertEquals(1,2);
            }
        });
    }

}
