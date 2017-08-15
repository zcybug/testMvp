package com.android.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.mvp.widge.key3.DefineKeyboardUtil;
import com.android.mvp.widge.key3.StockKeyboardView;

/**
 * @author tr
 * @time 2014-3-6
 * @description 自定义对话框(全键盘、随机数字键盘)
 */
public class DefineKeyboardActivity extends Activity implements OnTouchListener {

    private EditText edit_all_keyboard;
    private EditText edit_number_keyboard;

    private int inputType;

    public static DefineKeyboardUtil mDefineKeyboardUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_define_keyboard);

        mDefineKeyboardUtil = null;

        edit_all_keyboard = (EditText) findViewById(R.id.edit_all_keyboard);
        // 对输入框增加触摸事件，让其显示自定义输入框
        edit_all_keyboard.setOnTouchListener(this);
        edit_number_keyboard = (EditText) findViewById(R.id.edit_number_keyboard);
        // 对输入框增加触摸事件，让其显示自定义输入框
        edit_number_keyboard.setOnTouchListener(this);

        hideInputType();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // TODO Auto-generated method stub
        if (v.getId() == R.id.edit_all_keyboard) {
            mDefineKeyboardUtil = new DefineKeyboardUtil(
                    getApplicationContext(), edit_all_keyboard,
                    (StockKeyboardView) findViewById(R.id.keyboard_view));
        } else if (v.getId() == R.id.edit_number_keyboard) {
            mDefineKeyboardUtil = new DefineKeyboardUtil(
                    getApplicationContext(), edit_number_keyboard,
                    (StockKeyboardView) findViewById(R.id.keyboard_view));
        }
        mDefineKeyboardUtil.showKeyboard();
        return false;
    }

    // 退出之前先判断键盘是否可见，键盘可见，先隐藏键盘，再退出程序
    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (mDefineKeyboardUtil.isShowKeyboard()) {
            mDefineKeyboardUtil.hideKeyboard();
        } else {
            finish();
        }

    }

    /**
     * 判断当前系统版本，选择使用何种方式隐藏默认键盘
     */
    private void hideInputType() {
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT <= 10) {
            // 屏蔽默认输入法
            edit_all_keyboard.setInputType(InputType.TYPE_NULL);
            // 屏蔽默认输入法
            edit_number_keyboard.setInputType(InputType.TYPE_NULL);

        } else {
            getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            /**方法一*/
//			try {
//				Class<EditText> cls = EditText.class;
//				Method setShowSoftInputOnFocus;
//				setShowSoftInputOnFocus = cls.getMethod(
//						"setShowSoftInputOnFocus", boolean.class);
//				setShowSoftInputOnFocus.setAccessible(true);
//				setShowSoftInputOnFocus.invoke(edit_all_keyboard, false);
//				setShowSoftInputOnFocus.invoke(edit_number_keyboard, false);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}

            /**方法二*/
//			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(edit_all_keyboard.getWindowToken(), 0);
//			imm.hideSoftInputFromWindow(edit_number_keyboard.getWindowToken(), 0);

        }
    }

}
