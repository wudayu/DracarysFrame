package com.nfky.yaoyijia.activity;

import android.widget.EditText;

import com.nfky.yaoyijia.R;

/**
 * Created by David on 10/8/15.
 */
public class UserInterfaceActivity extends ViewControlActivity {

    EditText edtTest01;
    EditText edtTest02;

    @Override
    protected void initContainer() {
        setContentView(R.layout.activity_user_interface);
    }

    @Override
    protected void initComponents() {
        edtTest01 = (EditText) findViewById(R.id.edt_test01);
        edtTest02 = (EditText) findViewById(R.id.edt_test02);

        addControlViews(edtTest01, edtTest02);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void afterAllSet() {

    }

}
