package com.android.mvp;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by Administrator on 2017/6/27 0027.
 */
public class TestPresenterTest {
    private TestPresenter testPresenter;

    @Before
    public void setUp() throws Exception {
        testPresenter = new TestPresenter();
    }

    @Test
    public void verify() throws Exception {
        testPresenter.verify("aaaa");
    }

}