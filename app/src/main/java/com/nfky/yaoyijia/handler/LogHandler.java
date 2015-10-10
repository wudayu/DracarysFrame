package com.nfky.yaoyijia.handler;

import android.content.Context;

import com.nfky.yaoyijia.constant.Constant;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.IFileHandler;
import com.nfky.yaoyijia.handler.interfaces.ILogHandler;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by David on 9/11/15.
 *
 * 日志处理类，包括日常日志和异常日志处理，其中，日常日志的具体操作和应用场景需要在具体的情况下来重新编写
 */
public class LogHandler implements ILogHandler {

    private Context context = null;
    private IFileHandler fileHandler = null;
    private String cachePath = null;

    /** Generate the Singleton */
    private static volatile LogHandler instance;

    private LogHandler() {}

    public static ILogHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (LogHandler.class) {
                if (instance == null) {
                    instance = new LogHandler();
                }
            }
        }

        instance.context = context;
        instance.fileHandler = FileHandler.getInstance(context);
        instance.cachePath = instance.fileHandler.getCacheDirByType(IFileHandler.CacheDir.LOG);

        return instance;
    }

    /**
     * 将本次会话所有打印出的Tag写入Tag类型日志文件
     * FIXME 需要根据在何时写入日志文件来进行修改，当前是准备在程序退出时调用，比较不合理。
     *
     * @return 返回建立的日志文件的文件名
     */
    @Override
    public String writeLogTag() {
        // 首先当Log文件夹大小超过15MB时，清理存在超过七天的日志
        fileHandler.cleanCache(cachePath, LOG_MAX_VOLUME, LOG_DAY_LENGTH);
        // 生成log的内容
        StringBuilder log = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d | grep  " + Constant.TAG);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP, Locale.US);
        String timeStamp = sdf.format(new Date());
        String fileName = LOG_TAG_FILE_NAME + timeStamp + LOG_SUFFIX;
        // 写入文件
        output(fileName, log.toString());

        return fileName;
    }

    /**
     * 将所有打印出的Logcat内容写入App类型日志文件
     * 一般用于在程序崩溃的时候保存当前Crash信息
     *
     * @param extraInfo 需要加入参数来加入 When, Where, Who, What 等信息
     * @return 返回建立的日志文件的文件名
     */
    @Override
    public String writeLogApp(String extraInfo) {
        // 首先当Log文件夹大小超过15MB时，清理存在超过七天的日志
        fileHandler.cleanCache(cachePath, LOG_MAX_VOLUME, LOG_DAY_LENGTH);
        // 生成log的内容
        StringBuilder log = new StringBuilder();
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                log.append(line);
            }
        } catch (IOException e) {
            return null;
        }
        // 生成文件名
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_TIMESTAMP, Locale.US);
        String timeStamp = sdf.format(new Date());
        String fileName = LOG_APP_FILE_NAME + timeStamp + LOG_SUFFIX;
        // 在机器的Log信息最后加入extraInfo
        log.append("\n\n\n\n");
        log.append(extraInfo);
        // 写入文件
        output(fileName, log.toString());

        return fileName;
    }

    /**
     * Print the message into file
     * 不适合连续调用
     *
     * @param message 写入日志文件的信息
     */
    public synchronized void output(String fileName, String message) {
        File file = new File(instance.cachePath, fileName);
        FileOutputStream fos = null;
        DataOutputStream dos = null;
        try {
            fos = new FileOutputStream(file, true);
            dos = new DataOutputStream(fos);
            // dos.writeUTF(content);
            dos.writeUTF(message);
        } catch (IOException e) {
            Utils.debug(e.toString());
        } finally {
            try {
                if (dos != null) {
                    dos.flush();
                    dos.close();
                    dos = null;
                }
                if (fos != null) {
                    fos.flush();
                    fos.close();
                    fos = null;
                }
            } catch (IOException e) {
                Utils.debug(e.toString());
            }
        }
    }

}
