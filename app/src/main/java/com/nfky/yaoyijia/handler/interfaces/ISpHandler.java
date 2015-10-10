package com.nfky.yaoyijia.handler.interfaces;

/**
 * Created by David on 9/18/15.
 *
 * ISpHandler 是系统中所有SP的键定义的地方，同时使用SpUtils来操作SharedPreference
 *
 */
public interface ISpHandler {

    /** Keys 的注释格式：[描述用途] [数据格式] */
    // 是否记录密码 boolean
    String REMEMBER_PASSWORD = "sp_remember_password";
    // 用户名 String
    String USER_NAME = "sp_user_name";
    // 密码 String
    String PASSWORD = "sp_password";



    /** Methods **/
    boolean hasKey(final String key);

    String getString(String key, final String defaultValue);

    void setString(final String key, final String value);

    boolean getBoolean(final String key, final boolean defaultValue);

    void setBoolean(final String key, final boolean value);

    void setInt(final String key, final int value);

    int getInt(final String key, final int defaultValue);

    void setFloat(final String key, final float value);

    float getFloat(final String key, final float defaultValue);

    void setLong(final String key, final long value);

    long getLong(final String key, final long defaultValue);

}
