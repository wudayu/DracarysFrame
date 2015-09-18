package com.nfky.yaoyijia.generic;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by David on 8/25/15.
 *
 * 分辨率工具类
 */
public class DensityUtils {

    /**
     * 获取屏幕宽度（像素）
     *
     * @param context 上下文
     * @return 宽度 in pixel
     */
    public static int getScreenWidth(Context context) {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    /**
     * 获取屏幕高度（像素）
     *
     * @param context 上下文
     * @return 高度 in pixel
     */
    public static int getScreenHeight(Context context) {
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    /**
     * 将sp转换为px
     *
     * @param context 上下文
     * @param spValue sp值
     * @return px值
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 获取ActionBar的高度
     *
     * @param context 上下文
     * @return ActionBar的高度
     */
    public static int getActionBarHeight(Context context) {
        TypedArray actionbarSizeTypeArray = context.obtainStyledAttributes(new int[]{android.R.attr.actionBarSize});
        return (int) actionbarSizeTypeArray.getDimension(0, 0);
    }

}
