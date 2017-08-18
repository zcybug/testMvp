package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.android.com.keyboardlib.CustomViewKeyboardUtil;

import static lib.android.com.keyboardlib.CustomViewKeyboardUtil.KEYBOARD_STYLE_ABC;


/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/16 0016  下午 2:53
 * 修改历史：
 * ================================================
 */

public class CustomKeyboardActivity extends AppCompatActivity {
    @Bind(R.id.input)
    EditText editText;
    private CustomViewKeyboardUtil keyboardUtil;

    private int change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_ABC;


    @OnClick({R.id.show, R.id.hide, R.id.number, R.id.abc, R.id.price, R.id.idcard, R.id.random, R.id.symbol})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.show:
                showKeyboard();
                break;
            case R.id.hide:
                hideKeyboard();
                break;
            case R.id.number:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_NUMBER;
                showKeyboard();
                break;
            case R.id.abc:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_ABC;
                showKeyboard();
                break;
            case R.id.price:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_PRICE;
                showKeyboard();
                break;
            case R.id.idcard:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_ID;
                showKeyboard();
                break;
            case R.id.random:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_RANDOM_NUMBER;
                showKeyboard();
                break;
            case R.id.symbol:
                change_type = CustomViewKeyboardUtil.KEYBOARD_STYLE_SYMBOL;
                showKeyboard();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_keyboard);
        ButterKnife.bind(this);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int numberType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                initKeyboard();
                editText.setInputType(numberType);
                return false;
            }
        });
    }

    private void initKeyboard() {
        keyboardUtil = new CustomViewKeyboardUtil(this, editText, KEYBOARD_STYLE_ABC);
        keyboardUtil.setKeyboardListener(new CustomViewKeyboardUtil.KeyboardListener() {
            @Override
            public void onOK() {
                String result = editText.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case CustomViewKeyboardUtil.KEYBOARD_STYLE_NUMBER:
                            msg += "num:" + result;
                            break;
                        case CustomViewKeyboardUtil.KEYBOARD_STYLE_PRICE:
                            msg += "price:" + result;
                            break;
                        default:
                            msg += "input:" + result;
                            break;
                    }
                    Toast.makeText(CustomKeyboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
            }
        });
        showKeyboard();
    }

    private void showKeyboard() {
        if (keyboardUtil == null) {
            initKeyboard();
        }
        editText.setText("");
        switch (change_type) {
            case CustomViewKeyboardUtil.KEYBOARD_STYLE_NUMBER:
                editText.setHint("请输入数量");
                break;
            case CustomViewKeyboardUtil.KEYBOARD_STYLE_PRICE:
                editText.setHint("请输入价格");
                break;
            default:
                editText.setHint("");
                break;
        }
        keyboardUtil.showKeyboard(change_type);
    }

    private void hideKeyboard() {
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }
}
