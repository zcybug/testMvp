package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.android.mvp.widge.MSuperTextView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/4 0004  下午 3:21
 * 修改历史：
 * ================================================
 */

public class MTextViewActivity extends AppCompatActivity {
    @Bind(R.id.msuper_text)
    MSuperTextView msupertext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_text);
        ButterKnife.bind(this);
        msupertext.setLiftTextSize(16).setRightTextSize(12)
                .setLiftTextColor(R.color.base_red).setRightTextColor(R.color.dark_darker)
                .setLiftImg(R.drawable.arrow_right_red).setRightImg(R.drawable.balance_icon);
    }
}
