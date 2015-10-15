package com.nfky.yaoyijia.generic;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.nfky.yaoyijia.constant.Constant;

/**
 * Created by David on 8/24/15.
 * <p/>
 * Utils是静态工具类，包括Debug调试函数，标准Toast调用以及诸如防止多次点击的工具
 */
public class Utils {

	/**
	 * Debug function, used to print something
	 * debug专用函数
	 *
	 * @param message the message
	 */
	public static void debug(String message) {
		debug(Constant.TAG, message);
	}

	/**
	 * Debug function, used to print something
	 * debug专用函数
	 *
	 * @param tag     the tag
	 * @param message the message
	 */
	public static void debug(String tag, String message) {
		Log.d(tag, message);
	}

    private static Toast mToast = null;

	/**
	 * Toast a message in debug level
	 * toast专用函数
	 *
	 * @param activity the activity
	 * @param message  the message
	 */
	public static void toastMessage(final Activity activity, final String message) {
		toastMessage(activity, message, Toast.LENGTH_SHORT);
	}

	/**
	 * Toast a message in debug level with a length option
	 * toast专用函数
	 *
	 * @param activity the activity
	 * @param message  the message
	 * @param length   the length
	 */
	public static void toastMessage(final Activity activity, final String message, final int length) {
		toastMessage(activity, message, null, length);
	}

	/**
	 * Toast a message
	 * toast专用函数
	 *
	 * @param activity the activity
	 * @param message  the message
	 * @param logLevel the log level
	 * @param length   the length
	 */
	public static void toastMessage(final Activity activity, final String message, String logLevel, final int length) {
		if ("w".equals(logLevel)) {
			Log.w(Constant.TAG, message);
		} else if ("e".equals(logLevel)) {
			Log.e(Constant.TAG, message);
		} else {
			Log.d(Constant.TAG, message);
		}
		activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mToast != null) {
                    mToast.cancel();
                    mToast = null;
                }
                mToast = Toast.makeText(activity, message, length);
                mToast.show();
            }
        });
	}

	/**
	 * Check the Object is NULL
	 * 查看对象是否为null
	 *
	 * @param object the object
	 * @return is or not
	 */
	public static boolean isNull(Object object) {
		boolean result;
		if (!(object instanceof String)) {
			return (null == object);
		}

		if (TextUtils.isEmpty((CharSequence) (object))) {
			result = true;
		} else {
			String str = String.valueOf(object);
			str = str.toLowerCase(Locale.ENGLISH);
			result = ("null").equals(str);
		}

		return result;
	}

    private static long lastClickTime;

	/**
	 * Prevent Double Click
	 * 防止快速的两次点击
	 *
	 * @return is double click or not
	 */
	public static boolean isFastClick() {
		long time = System.currentTimeMillis();
		long timeD = time - lastClickTime;
		if (0 < timeD && timeD < 500) {
			return true;
		}

		lastClickTime = time;
		return false;
	}

	/**
	 * Get Status Bar Height
	 * 获取状态栏高度
	 *
	 * @param ctx an available activity
	 * @return height in integer
	 */
	public static int getStatusBarHeight(Activity ctx) {
		Rect frame = new Rect();
		ctx.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);

		return frame.top;
	}

	/**
	 * Check the email format
	 * 检查Email格式
	 *
	 * @param email the email
	 * @return right or wrong
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Patterns.EMAIL_ADDRESS;
		Matcher mc = pattern.matcher(email);
		return mc.matches();
	}

	/**
	 * Check the URL format
	 * 检查URL格式
	 *
	 * @param url the url
	 * @return right or wrong
	 */
	public static boolean isValidURL(String url) {
		Pattern patterna = Patterns.WEB_URL;
		Matcher mca = patterna.matcher(url);
		return mca.matches();
	}

	/**
	 * If the Keyboard doesn't hide, hide it
	 * 如果键盘没有隐藏，则隐藏键盘
	 *
	 * @param activity an available activity
	 * @param v        关联控件
	 */
	public static void autoCloseKeyboard(Activity activity, View v) {
		View view = activity.getWindow().peekDecorView();
		if (view != null && view.getWindowToken() != null) {
			InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
		}
	}

	private final static String[] strDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 将byte转换为String
	 *
	 * @param bByte byte数据
	 * @return String形式的byte数据
	 */
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return strDigits[iD1] + strDigits[iD2];
	}

	/**
	 * 将byte数组转换为String
	 *
	 * @param bByte byte数组
	 * @return String形式的byte数据
	 */
	private static String byteToString(byte[] bByte) {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < bByte.length; i++) {
            stringBuilder.append(byteToArrayString(bByte[i]));
		}
		return stringBuilder.toString();
	}

	/**
	 * Get String's MD5
	 * 获取一个String的md5
	 *
	 * @param str the source string
	 * @return md5的数据 md 5
	 */
	public static String getMD5(String str) {
		String resultString = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteToString(md.digest(str.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			Utils.debug("getMD5 : " + e.toString());
		}
		return resultString;
	}

	/**
	 * Arrays add
	 * int类型数组相加
	 *
	 * @param arrays that need to add
	 * @return 相加后的结果的数组 integer [ ]
	 */
	public static Integer[] intArrayPlus(Integer[]... arrays) {
		int size = 0;
		for (int i = 0; i < arrays.length; i++) {
			Integer[] temp = arrays[i];
			size += temp.length;
		}

		Integer[] all = new Integer[size];
		int dstPos = 0;
		for (int i = 0; i < arrays.length; i++) {
			System.arraycopy(arrays[i], 0, all, dstPos, arrays[i].length);
			dstPos += arrays[i].length;
		}

		return all;
	}

	/**
	 * Get device's DeviceId
	 * 获取一台Android机器的IMEI码
	 *
	 * @param context 上下文
	 * @return IMEI码 device id
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager telephonyManager;

		telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);

		return telephonyManager.getDeviceId();
	}

	/**
	 * Reserves two decimal fractions
	 * 保留double小数点后两位
	 *
	 * @param number need reserve
	 * @return reserved number
	 */
	public static double convert2dot(double number) {
		BigDecimal bg = new BigDecimal(number);
		return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * Reserves two decimal fractions
	 * 保留double小数点后两位
	 *
	 * @param number need reserve
	 * @return reserved number in string format
	 */
	public static String convert2Double(double number) {
		return String.format(Locale.CHINA, "%.2f", number);
	}

	/**
	 * Get device info
	 * 获取设备参数信息
	 *
	 * @param context 上下文
	 * @return the device info
	 */
	public static Map<String, String> getDeviceInfo(Context context) {
		//用来存储设备信息
		Map<String, String> infoes = new HashMap<String, String>();

		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				String versionName = pi.versionName == null ? "null" : pi.versionName;
				String versionCode = pi.versionCode + "";
				infoes.put("versionName", versionName);
				infoes.put("versionCode", versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
            debug("getDeviceInfo PackageManager.NameNotFoundException ");
		}

		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				infoes.put(field.getName(), field.get(null).toString());
			} catch (Exception e) {
                debug("getDeviceInfo Exception");
			}
		}

        return infoes;
	}

	/**
	 * Review the permission is granted or not
	 * 查看当前权限是否已经获得（部分用户可能会禁用相关权限）
	 *
	 * @param context    the context
	 * @param permission 使用Manifest.permission下的permission
	 * @return the boolean
	 */
	public boolean doesUserHavePermission(Context context, String permission) {
		int result = context.checkCallingOrSelfPermission(permission);

		return result == PackageManager.PERMISSION_GRANTED;
	}

}
