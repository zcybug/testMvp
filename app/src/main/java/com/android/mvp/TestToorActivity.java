package com.android.mvp;

import android.view.MenuItem;
import android.view.View;

import mvp.android.com.mvplib.base.ToorBaseActivity;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class TestToorActivity extends ToorBaseActivity {
    @Override
    protected void setRootView() {
        super.setRootView();
        setContentView(R.layout.activity_toor);
    }

    @Override
    protected void initWidght() {
        super.initWidght();
        setTitleName(getResources().getString(R.string.add));//title
//                  setTitleBack(false,R.mipmap.ic_search);//有图标，但不是返回
        setTitleBack(true, 0);//返回
//        setTitleRightText("保存");//右侧文字
        showTitleRes(R.id.title_add, R.id.title_setting);//扩展menu（图标）
        //goneTitleRes(R.id.title_add);隐藏图标，一般用不到
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
//            workFragment.titleSearch();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.title_add://添加
                break;
            case R.id.title_setting://设置
                break;
        }
        return super.onMenuItemClick(item);
    }
}
