package com.android.mvp;

import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.android.com.keyboardlib.CustomViewKeyboardUtil;
import mvp.android.com.mvplib.base.BaseDetailActivity;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/18 0018  上午 11:08
 * 修改历史：
 * ================================================
 */

public class CustomKeyboard1Activity extends BaseDetailActivity {
    @Bind(R.id.input)
    EditText editText;

    @OnClick({R.id.show, R.id.hide, R.id.number, R.id.abc, R.id.price, R.id.idcard, R.id.random, R.id.symbol})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.show:
                showCustomKeyboard(editText);
                break;
            case R.id.hide:
                hideCustomKeyboard();
                break;
            case R.id.number:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_NUMBER;
                showCustomKeyboard(editText);
                break;
            case R.id.abc:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_ABC;
                showCustomKeyboard(editText);
                break;
            case R.id.price:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_PRICE;
                showCustomKeyboard(editText);
                break;
            case R.id.idcard:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_ID;
                showCustomKeyboard(editText);
                break;
            case R.id.random:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_RANDOM_NUMBER;
                showCustomKeyboard(editText);
                break;
            case R.id.symbol:
                custom_keyboard_change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_SYMBOL;
                showCustomKeyboard(editText);
                break;
        }
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        ButterKnife.bind(mActivity);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int numberType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                showCustomKeyboard(editText);
                editText.setInputType(numberType);
                return false;
            }
        });
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_keyboard1;
    }

    @Override
    protected String getTopTitle() {
        return "自定义键盘";
    }
}
