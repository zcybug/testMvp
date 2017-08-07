package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.android.mvp.widge.DrawView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/7/31 0031  下午 1:42
 * 修改历史：
 * ================================================
 */

public class DrawActivity extends AppCompatActivity {
    @Bind(R.id.draw_layout)
    LinearLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        ButterKnife.bind(this);
        final DrawView view=new DrawView(this);
        //通知view组件重绘
        view.invalidate();
        layout.addView(view);
    }
}
