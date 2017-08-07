package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvp.android.com.mvplib.utils.PinyinUtilsPro;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/7/28 0028  上午 10:56
 * 修改历史：
 * ================================================
 */

public class PinyinActivity extends AppCompatActivity {
    @Bind(R.id.input)
    EditText editText;
    @Bind(R.id.show)
    TextView show;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinyin);
        ButterKnife.bind(this);
    }


    public void submit(View v) {
        PinyinUtilsPro.convertChineseToPinyin(this, editText.getText().toString().trim());
        show.setText("全拼音：" + PinyinUtilsPro.getPinyin() + "\n拼音首字：" + PinyinUtilsPro.getHeadPinyin());
    }
}
