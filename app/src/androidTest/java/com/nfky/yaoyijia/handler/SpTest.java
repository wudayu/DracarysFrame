package com.nfky.yaoyijia.handler;

import android.test.AndroidTestCase;

import com.nfky.yaoyijia.handler.interfaces.ISpHandler;

/**
 * Created by David on 10/13/15.
 */
public class SpTest extends AndroidTestCase {

    /**
     * 测试: getString() setString()
     */
    public void testGetSetString() {
        ISpHandler spHandler = SpHandler.getInstance(getContext());
        String testStr = "testing";

        spHandler.setString(ISpHandler.PASSWORD, testStr);
        String newStr = spHandler.getString(ISpHandler.PASSWORD, "");

        assertEquals(testStr, newStr);
    }

    /**
     * 测试: getBoolean() setBoolean()
     */
    public void testGetSetBoolean() {
        ISpHandler spHandler = SpHandler.getInstance(getContext());
        boolean testBool = true;

        spHandler.setBoolean(ISpHandler.REMEMBER_PASSWORD, testBool);
        boolean newBool = spHandler.getBoolean(ISpHandler.REMEMBER_PASSWORD, false);

        assertEquals(testBool, newBool);
    }

    /**
     * 测试: getInt() setInt()
     */
    public void testGetSetInt() {
        ISpHandler spHandler = SpHandler.getInstance(getContext());
        int testInt = 1023;

        spHandler.setInt(ISpHandler.PASSWORD, testInt);
        int newInt = spHandler.getInt(ISpHandler.PASSWORD, -1);

        assertEquals(testInt, newInt);
    }

    /**
     * 测试: getFloat() setFloat()
     */
    public void testGetSetFloat() {
        ISpHandler spHandler = SpHandler.getInstance(getContext());
        float testFloat = 1023.32101f;

        spHandler.setFloat(ISpHandler.PASSWORD, testFloat);
        float newFloat = spHandler.getFloat(ISpHandler.PASSWORD, -1.111f);

        assertEquals(testFloat, newFloat);
    }

    /**
     * 测试: getLong() setLong()
     */
    public void testGetSetLong() {
        ISpHandler spHandler = SpHandler.getInstance(getContext());
        long testLong = 192292929232322l;

        spHandler.setLong(ISpHandler.PASSWORD, testLong);
        long newLong = spHandler.getLong(ISpHandler.PASSWORD, -1232323323123l);

        assertEquals(testLong, newLong);
    }

}
