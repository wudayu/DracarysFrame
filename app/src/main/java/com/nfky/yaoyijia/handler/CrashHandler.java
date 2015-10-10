package com.nfky.yaoyijia.handler;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.nfky.yaoyijia.R;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.handler.interfaces.ILogHandler;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

/**
 * Created by David on 9/14/15.
 *
 * 意外异常的捕捉类
 *
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private Context context = null;
    private ILogHandler logHandler = null;
    //系统默认的UncaughtExceptionHandler
    private Thread.UncaughtExceptionHandler defaultHandler = null;


    /** Generate Singleton */
    private static volatile CrashHandler instance;

    private CrashHandler() {}

    public static Thread.UncaughtExceptionHandler getInstance(Context context) {
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                    // 设置CrashHandler
                    Thread.setDefaultUncaughtExceptionHandler(instance);
                }
            }
        }

        instance.context = context;
        instance.logHandler = LogHandler.getInstance(instance.context);
        instance.defaultHandler = Thread.getDefaultUncaughtExceptionHandler();

        return instance;
    }


    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && defaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            defaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Utils.debug("InterruptedException : Thread.sleep(1000);");
            }
            //退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex 需要处理的异常
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(context, context.getText(R.string.error_got_uncaught_exception), Toast.LENGTH_LONG).show();
                Looper.loop();
            }
        }.start();

        //写入日志文件
        logHandler.writeLogApp(getDeviceInfo() + getExceptionInfo(ex));

        return true;
    }

    /**
     * 获取设备参数文本信息
     *
     * @return 设备参数信息的字符串
     */
    public String getDeviceInfo() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : Utils.getDeviceInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        return sb.toString();
    }

    /**
     * 获取异常中的文本信息
     *
     * @param ex 需要处理的异常
     * @return String形式的异常信息
     */
    public String getExceptionInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();

        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();

        return result + "\n\n";
    }

}
