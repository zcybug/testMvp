package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.android.com.keyboardlib.CustomKeyboardUtil;
import lib.android.com.keyboardlib.MCustomKeyboardView;

import static lib.android.com.keyboardlib.CustomKeyboardUtil.KEYBOARD_STYLE_ABC;

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
    @Bind(R.id.m_custom_keyboard)
    MCustomKeyboardView keyboardView;
    @Bind(R.id.input)
    EditText editText;
    @Bind(R.id.rl_layout)
    RelativeLayout rl_layout;
    private CustomKeyboardUtil keyboardUtil;

    private int change_type;


    @OnClick({R.id.show, R.id.hide, R.id.number, R.id.abc, R.id.price, R.id.idcard, R.id.random})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.show:
                showKeyboard();
                break;
            case R.id.hide:
                hideKeyboard();
                break;
            case R.id.number:
                change_type = CustomKeyboardUtil.KEYBOARD_STYLE_NUMBER;
                showKeyboard();
                break;
            case R.id.abc:
                change_type = CustomKeyboardUtil.KEYBOARD_STYLE_ABC;
                showKeyboard();
                break;
            case R.id.price:
                change_type = CustomKeyboardUtil.KEYBOARD_STYLE_PRICE;
                showKeyboard();
                break;
            case R.id.idcard:
                change_type = CustomKeyboardUtil.KEYBOARD_STYLE_ID;
                showKeyboard();
                break;
            case R.id.random:
                change_type = CustomKeyboardUtil.KEYBOARD_STYLE_RANDOM_NUMBER;
                showKeyboard();
                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_keyboard);
        ButterKnife.bind(this);
        initKeyboard();
    }

    private void initKeyboard() {
        keyboardUtil = new CustomKeyboardUtil(this, editText, KEYBOARD_STYLE_ABC);
        keyboardUtil.setKeyboardListener(new CustomKeyboardUtil.KeyboardListener() {
            @Override
            public void onOK() {
                String result = editText.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case CustomKeyboardUtil.KEYBOARD_STYLE_NUMBER:
                            msg += "num:" + result;
                            break;
                        case CustomKeyboardUtil.KEYBOARD_STYLE_PRICE:
                            msg += "price:" + result;
                            break;
                        default:
                            msg += "input:" + result;
                            break;
                    }
                    Toast.makeText(CustomKeyboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyboard();
                change_type = -1;
            }
        });
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int numberType = editText.getInputType();
                editText.setInputType(InputType.TYPE_NULL);
                if (null != keyboardUtil) {
                    showKeyboard();
                }
                editText.setInputType(numberType);
                return true;
            }
        });
    }

    private void showKeyboard() {
        rl_layout.setVisibility(View.VISIBLE);
        editText.setText("");
        switch (change_type) {
            case CustomKeyboardUtil.KEYBOARD_STYLE_NUMBER:
                editText.setHint("请输入数量");
                break;
            case CustomKeyboardUtil.KEYBOARD_STYLE_PRICE:
                editText.setHint("请输入价格");
                break;
            default:
                editText.setHint("");
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
    }

    private void hideKeyboard() {
        rl_layout.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }
}
