package com.android.mvp;

import android.os.Bundle;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.model.BaseEntity;
import mvp.android.com.mvplib.mvp.MvpOkgoActivity;
import mvp.android.com.mvplib.utils.Urls;
import mvp.android.com.mvplib.widget.HttpButton;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/21 0021  上午 11:01
 * 修改历史：
 * ================================================
 */

public class TestOkMvpActivity extends MvpOkgoActivity<TestPresenter, TestView> implements TestView {
    @Bind(R.id.http_btn)
    HttpButton button;

    @OnClick(R.id.http_btn)
    public void http(View view) {
        button.setEnabled(false);
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(2000);
                    presenter.setLoadId(0);
                    presenter.postServerModel(Urls.URL_JSONOBJECT, TestOkMvpActivity.this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected TestView createView() {
        return this;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        ButterKnife.bind(mActivity);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_test;
    }

    @Override
    protected String getTopTitle() {
        return "测试";
    }

    @Override
    public void success(HttpCodeStatus requestCode, int loadId, BaseEntity data) {
        super.success(requestCode, loadId, data);
        button.setEnabled(true);
    }

    @Override
    public <T> void success(HttpCodeStatus requestCode, int loadId, T data) {
        super.success(requestCode, loadId, data);
        button.setEnabled(true);
    }
}
