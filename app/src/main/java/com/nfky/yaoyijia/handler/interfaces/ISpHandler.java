package com.nfky.yaoyijia.handler.interfaces;

/**
 * Created by David on 9/18/15.
 * <p/>
 * ISpHandler 是系统中所有SP的键定义的地方，同时使用SpUtils来操作SharedPreference
 */
public interface ISpHandler {

    /**
     * Keys 的注释格式：[描述用途] [数据格式]
     */
    /**
     * The constant REMEMBER_PASSWORD
     * 是否记录密码 boolean
     */
    String REMEMBER_PASSWORD = "sp_remember_password";
    /**
     * The constant USER_NAME.
     * 用户名 String
     */
    String USER_NAME = "sp_user_name";
    /**
     * The constant PASSWORD.
     * 密码 String
     */
    String PASSWORD = "sp_password";

    /**
     * Methods  @param key the key
     *
     * @return the boolean
     */
    boolean hasKey(final String key);

    /**
     * Gets string.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the string
     */
    String getString(String key, final String defaultValue);

    /**
     * Sets string.
     *
     * @param key   the key
     * @param value the value
     */
    void setString(final String key, final String value);

    /**
     * Gets boolean.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the boolean
     */
    boolean getBoolean(final String key, final boolean defaultValue);

    /**
     * Sets boolean.
     *
     * @param key   the key
     * @param value the value
     */
    void setBoolean(final String key, final boolean value);

    /**
     * Sets int.
     *
     * @param key   the key
     * @param value the value
     */
    void setInt(final String key, final int value);

    /**
     * Gets int.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the int
     */
    int getInt(final String key, final int defaultValue);

    /**
     * Sets float.
     *
     * @param key   the key
     * @param value the value
     */
    void setFloat(final String key, final float value);

    /**
     * Gets float.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the float
     */
    float getFloat(final String key, final float defaultValue);

    /**
     * Sets long.
     *
     * @param key   the key
     * @param value the value
     */
    void setLong(final String key, final long value);

    /**
     * Gets long.
     *
     * @param key          the key
     * @param defaultValue the default value
     * @return the long
     */
    long getLong(final String key, final long defaultValue);

}
