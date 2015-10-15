package com.nfky.yaoyijia.handler.interfaces;

/**
 * Created by David on 9/11/15.
 * <p/>
 * ILogHandler 是用来操作日志的接口，包括Tag类型日志和应用类型日志
 */
public interface ILogHandler {

    /**
     * The constant LOG_TAG_FILE_NAME. TAG类型的日志文件名头（退出调用）
     */
    String LOG_TAG_FILE_NAME = "log_tag";
    /**
     * The constant LOG_APP_FILE_NAME. 本应用类型的日志文件名头（崩溃调用）
     */
    String LOG_APP_FILE_NAME = "log_app";
    /**
     * The constant LOG_SUFFIX. 日志文件扩展名
     */
    String LOG_SUFFIX = ".log";
    /**
     * The constant LOG_MAX_VOLUME. 当写入日志时，日志文件的总大小的最大值不能超过LOG_MAX_VOLUME
     */
    long LOG_MAX_VOLUME = 15 * 1024 * 1024;
    /**
     * The constant LOG_DAY_LENGTH. 当写入日志时，日志文件总大小太大时需要删除，仅保留LOG_DAY_LENGTH天（毫秒）内的日志。
     */
    long LOG_DAY_LENGTH = 7 * 24 * 3600000;
    /**
     * The constant FORMAT_TIMESTAMP. 时间戳格式
     */
    String FORMAT_TIMESTAMP = "_yyyy-MM-dd-HH-mm-ss";


    /**
     * Write log tag string.
     *
     * @return the string
     */
    String writeLogTag();

    /**
     * Write log app string.
     *
     * @param extraInfo the extra info
     * @return the string
     */
    String writeLogApp(String extraInfo);

}
