package com.android.mvp;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import mvp.android.com.mvplib.base.BaseDetailActivity;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/24 0024  下午 3:25
 * 修改历史：
 * ================================================
 */

public class RealmDBActivity extends BaseDetailActivity {

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_realm;
    }

    @Override
    protected String getTopTitle() {
        return "realmDB";
    }

    @OnClick({R.id.input, R.id.out})
    public void in(View view) {
    }
}
