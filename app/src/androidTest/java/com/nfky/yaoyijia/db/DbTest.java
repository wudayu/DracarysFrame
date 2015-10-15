package com.nfky.yaoyijia.db;

import android.test.AndroidTestCase;

import com.nfky.yaoyijia.handler.interfaces.IDataHandler;
import com.nfky.yaoyijia.model.VcUser;

/**
 * Created by David on 10/12/15.
 * <p/>
 * DbTest是测试数据库的测试用例类
 */
public class DbTest extends AndroidTestCase {

    /**
     * 测试: getForUserInfo() setForUserInfo()
     */
    public void testGetSetForUserInfo() {
        String userId = "b2e98c8d87afa3c3498ad";
        String mphone = "18651817673";
        String screenName = "Dracarys";

        IDbHandler dbHandler = OrmliteDbHandler.getInstance(this.getContext());

        final VcUser user = new VcUser();
        user.setId(userId);
        user.setMphone(mphone);
        user.setScreenName(screenName);

        dbHandler.setForUserInfo(user);
        dbHandler.getForUserInfo(userId, new IDataHandler.DataCallback<VcUser>() {
            @Override
            public void onSuccess(VcUser object) {
                assertTrue(user.getId().equals(object.getId()) &&
                        user.getMphone().equals(object.getMphone()) &&
                        user.getScreenName().equals(object.getScreenName()));
            }

            @Override
            public void onFailure(String errInfo, Exception excep) {
                assertTrue(false);
            }
        });
    }

}
