package com.nfky.yaoyijia.handler;

/**
 * Created by David on 9/11/15.
 *
 * ILogHandler 是用来操作日志的接口，包括Tag类型日志和应用类型日志
 *
 */

public interface ILogHandler {

    // TAG类型的日志文件名头（退出调用）
    String LOG_TAG_FILE_NAME = "log_tag";
    // 本应用类型的日志文件名头（崩溃调用）
    String LOG_APP_FILE_NAME = "log_app";
    // 日志文件扩展名
    String LOG_SUFFIX = ".log";
    // 当写入日志时，日志文件的总大小的最大值不能超过LOG_MAX_VOLUME
    long LOG_MAX_VOLUME = 15 * 1024 * 1024;
    // 当写入日志时，日志文件总大小太大时需要删除，仅保留LOG_DAY_LENGTH天（毫秒）内的日志。
    long LOG_DAY_LENGTH = 7 * 24 * 3600000;
    // 时间戳格式
    String FORMAT_TIMESTAMP = "_yyyy-MM-dd-HH-mm-ss";


    String writeLogTag();

    String writeLogApp(String extraInfo);

}
