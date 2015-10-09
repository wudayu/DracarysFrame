package com.nfky.yaoyijia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.nfky.yaoyijia.MainApp;
import com.nfky.yaoyijia.generic.Utils;
import com.nfky.yaoyijia.model.VcControlBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 10/8/15.
 *
 * ViewControlActivity是用来对界面元素进行权限控制的一个抽象的Activity
 * 所有需要进行界面控制的Activity必须继承自ViewControlActivity
 */
public abstract class ViewControlActivity extends BaseActivity {

    // MainApp
    MainApp mApp = (MainApp) this.getApplication();
    // 需要此界面需要权限管理的View集合
    private List<View> controlViews = new ArrayList<>();

    // remove this 测试用禁用数组
    private List<VcControlBlock> blocks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle arg0) {
        initTest();
        if (isNotAllowedVisible(this.getClass().getSimpleName())) {
            this.finish();
            startActivity(new Intent(this, TestActivity.class));
        }

        super.onCreate(arg0);
    }

    // remove this
    private void initTest() {
        VcControlBlock tmp = new VcControlBlock();
        tmp.setComponentName("edt_test01");
        tmp.setFailedMessage("");
        blocks.add(tmp);
        tmp = new VcControlBlock();
        tmp.setComponentName("UserInterfaceActivity");
        tmp.setFailedMessage("isFailed");
        blocks.add(tmp);
    }

    /**
     * 将需要权限管理的View加入集合中
     *
     * @param controlViews 需要权限管理的views
     */
    protected void addControlViews(View... controlViews) {
        this.controlViews.clear();

        for (int i = 0; controlViews != null && i < controlViews.length; ++i) {
            View controlView = controlViews[i];
            if (isNotAllowedVisible(((String) controlView.getTag()))) {
                // maybe invisible, maybe gone, need fix it when use it.
                controlView.setVisibility(View.INVISIBLE);

            }

            this.controlViews.add(controlView);
        }
    }

    /**
     * 判断当前控件是否不允许显示 或 当前组件是否不允许开启
     *
     * @param controlViewTag 当前加入权限控制的控件的Tag
     * @return true->不允许显示或开启
     */
    private boolean isNotAllowedVisible(String controlViewTag) {
        for (VcControlBlock block : blocks) {
            Utils.debug("block.getComponentName() = " + block.getComponentName() + ", controlViewTag = " + controlViewTag);
            if (block.getComponentName().equalsIgnoreCase(controlViewTag))
                return true;
        }

        return false;
    }

}
