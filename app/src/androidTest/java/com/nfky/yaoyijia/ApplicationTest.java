package com.nfky.yaoyijia;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.nfky.yaoyijia.generic.Utils;

/**
 * ApplicationTest是可以用来测试MainApp的测试类，但是MainApp中并没有需要测试的方法，这里主要列举几个例子。
 *
 * 注：
 * 1.所有测试用例方法名必须以“test”开头。
 * 2.所有测试用例方法控制的变量必须独立，不应存在固定顺序的依赖。
 *
 */
public class ApplicationTest extends ApplicationTestCase<MainApp> {
    public ApplicationTest() {
        super(MainApp.class);
    }

    public void testShowAgain() {
        createApplication();
        Utils.debug("1111111111111111111111");
        assertEquals(1, 1);
    }

    public void testShowAgainAndAgain() {
        createApplication();
        Utils.debug("0000000000000000000000");
        assertEquals(1, 1);
    }

    public void testShow() {
        createApplication();
        Utils.debug("-1-1-1-1-1-1-1-1-1-1-1");
        assertEquals(1, 1);
    }

}