package com.android.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.mvp.util.Key5boardUtil;
import com.android.mvp.widge.key4.XEditText;

public class Key5Activity extends Activity {
    private RelativeLayout rl_keyboard;
    private Key5boardUtil keyboardUtil;
    private int change_type;
    private EditText et_amount;
    private Button btn_showKey, btn_hideKey, btn_price, btn_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key5);
        initTestBtn();
        initKeyboard();
    }

    private void initTestBtn() {
        btn_showKey = (Button) findViewById(R.id.btn_showKey);
        btn_hideKey = (Button) findViewById(R.id.btn_hideKey);
        btn_price = (Button) findViewById(R.id.btn_price);
        btn_number = (Button) findViewById(R.id.btn_number);
        btn_showKey.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showKeyBoard();
            }
        });
        btn_hideKey.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                hideKeyBoard();
            }
        });
        btn_price.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = Key5boardUtil.TYPE_PRICE;
                showKeyBoard();
            }
        });
        btn_number.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                change_type = Key5boardUtil.TYPE_NUMBER;
                showKeyBoard();
            }
        });
    }

    private void initKeyboard() {
        et_amount = (XEditText) findViewById(R.id.et_amount);
        keyboardUtil = new Key5boardUtil(this, et_amount);
        rl_keyboard = (RelativeLayout) findViewById(R.id.rl_keyboard);
        et_amount.setInputType(InputType.TYPE_NULL);
        keyboardUtil.setKeyboardListener(new Key5boardUtil.KeyboardListener() {

            @Override
            public void onOK() {
                String result = et_amount.getText().toString();
                String msg = "";
                if (!TextUtils.isEmpty(result)) {
                    switch (change_type) {
                        case Key5boardUtil.TYPE_NUMBER:
                            msg += "num:" + result;
                            break;
                        case Key5boardUtil.TYPE_PRICE:
                            msg += "price:" + result;
                            break;
                        default:
                            msg += "input:" + result;
                            break;
                    }
                    Toast.makeText(Key5Activity.this, msg, Toast.LENGTH_SHORT).show();
                }
                hideKeyBoard();
                change_type = -1;
            }
        });
    }

    /**
     * 显示键盘
     */
    protected void showKeyBoard() {
        rl_keyboard.setVisibility(View.VISIBLE);
        et_amount.setText("");
        switch (change_type) {
            case Key5boardUtil.TYPE_NUMBER:
                et_amount.setHint("请输入数量");
                break;

            case Key5boardUtil.TYPE_PRICE:
                et_amount.setHint("请输入价格");
                break;

            default:
                break;
        }
        keyboardUtil.setType(change_type);
        keyboardUtil.showKeyboard();
    }

    /**
     * 显示键盘
     */
    protected void hideKeyBoard() {
        rl_keyboard.setVisibility(View.GONE);
        keyboardUtil.hideKeyboard();
        keyboardUtil.setType(-1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (rl_keyboard.getVisibility() != View.GONE) {
                hideKeyBoard();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
