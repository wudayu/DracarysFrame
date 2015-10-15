package com.nfky.yaoyijia.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.nfky.yaoyijia.handler.interfaces.ISpHandler;

/**
 * Created by David on 9/18/15.
 * <p/>
 * SpHandler 是ISP的实现类，是用于简化SharedPreference操作的类，相关会使用到的键需要写在ISP中
 * 由于SpHandler有两种状态，一种是获取默认Sp文件，一种是获取自定义文件，所以不能用纯粹的单例模式。
 */
public class SpHandler implements ISpHandler {

    private SharedPreferences sp;
    private String defaultFileName;

    /** Generate the Singleton */
    private static volatile SpHandler instance;

    private SpHandler() {}

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static ISpHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (SpHandler.class) {
                if (instance == null) {
                    instance = new SpHandler();
                }
            }
        }

        if (instance.sp == null) {
            instance.sp = PreferenceManager.getDefaultSharedPreferences(context);
        }

        return instance;
    }

    /**
     * 若使用特定文件的Sp，为了防止与其他界面的Sp冲突，这里new一个新的SpHandler来记录新值
     *
     * @param context  the context
     * @param fileName the file name
     * @return the instance
     */
    public static ISpHandler getInstance(Context context, String fileName) {
        SpHandler spSpecificHandler = new SpHandler();

        if (spSpecificHandler.defaultFileName == null || !spSpecificHandler.defaultFileName.trim().equals(fileName)) {
            spSpecificHandler.defaultFileName = fileName;
            spSpecificHandler.sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }

        return spSpecificHandler;
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
