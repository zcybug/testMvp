package com.android.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.android.mvp.util.KeyboardUtil;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/14 0014  下午 2:29
 * 修改历史：
 * ================================================
 */

public class Key1Activity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_key);
        editText = (EditText) findViewById(R.id.editText1);
        editText.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int numberType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                new KeyboardUtil(Key1Activity.this, Key1Activity.this, editText)
                        .showKeyboard();
                editText.setInputType(numberType);

                return true;
            }
        });
    }
}
