package com.nfky.yaoyijia.generic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nfky.yaoyijia.generic.interfaces.ISP;

/**
 * Created by David on 9/18/15.
 *
 * SpUtils 是ISP的实现类，是用于简化SharedPreference操作的类，相关会使用到的键需要写在ISP中
 */
public class SpUtils implements ISP {

    private static SharedPreferences sp;
    private static String defaultFileName;

    /** Generate the Singleton */
    private SpUtils() {}

    public static SpUtils getInstance(Context context) {
        if (sp == null) {
            sp = PreferenceManager.getDefaultSharedPreferences(context);
        }
        return new SpUtils();
    }

    public static SpUtils getInstance(Context context, String fileName) {
        if (defaultFileName == null || !defaultFileName.trim().equals(fileName)) {
            defaultFileName = fileName;
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        return new SpUtils();
    }

    @Override
    public boolean hasKey(final String key) {
        return sp.contains(key);
    }

    @Override
    public String getString(String key, final String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    @Override
    public void setString(final String key, final String value) {
        sp.edit().putString(key, value).commit();
    }

    @Override
    public boolean getBoolean(final String key, final boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    @Override
    public void setBoolean(final String key, final boolean value) {
        sp.edit().putBoolean(key, value).commit();
    }

    @Override
    public void setInt(final String key, final int value) {
        sp.edit().putInt(key, value).commit();
    }

    @Override
    public int getInt(final String key, final int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    @Override
    public void setFloat(final String key, final float value) {
        sp.edit().putFloat(key, value).commit();
    }

    @Override
    public float getFloat(final String key, final float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    @Override
    public void setLong(final String key, final long value) {
        sp.edit().putLong(key, value).commit();
    }

    @Override
    public long getLong(final String key, final long defaultValue) {
        return sp.getLong(key, defaultValue);
    }
}
