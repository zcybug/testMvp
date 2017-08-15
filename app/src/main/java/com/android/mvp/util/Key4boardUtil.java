package com.android.mvp.util;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.android.mvp.R;

import java.util.List;

import mvp.android.com.mvplib.log.XLog;

public class Key4boardUtil {

    public enum KeyboardStyle {
        KEYBOARD_STYLE_K1,
        KEYBOARD_STYLE_K2,
        KEYBOARD_STYLE_K3,
        KEYBOARD_STYLE_K4
    }

    private Context ctx;
    private Activity act;
    private KeyboardView keyboardView;
    private Keyboard k1;// 字母键盘
    private Keyboard k2;// 数字键盘
    private Keyboard k3;//身份证大写X
    private Keyboard k4;//密码
    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写

    private EditText ed;

    public Key4boardUtil(Activity act, Context ctx, EditText edit, KeyboardStyle keyboardStyle) {
        this.act = act;
        this.ctx = ctx;
        this.ed = edit;
        k1 = new Keyboard(ctx, R.xml.qwerty);
        k2 = new Keyboard(ctx, R.xml.symbols);
        k3 = new Keyboard(ctx, R.xml.keyboard_idcard);
        k4 = new Keyboard(ctx, R.xml.keyboard_pass);
        keyboardView = (KeyboardView) act.findViewById(R.id.keyboard_view);
        if (keyboardStyle == KeyboardStyle.KEYBOARD_STYLE_K1) {
            keyboardView.setKeyboard(k1);
        } else if (keyboardStyle == KeyboardStyle.KEYBOARD_STYLE_K2) {
            keyboardView.setKeyboard(k2);
        } else if (keyboardStyle == KeyboardStyle.KEYBOARD_STYLE_K3) {
            keyboardView.setKeyboard(k3);
        } else if (keyboardStyle == KeyboardStyle.KEYBOARD_STYLE_K4) {
            keyboardView.setKeyboard(k4);
        }
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(listener);
    }

    private OnKeyboardActionListener listener = new OnKeyboardActionListener() {
        int primaryCode;
        StringBuilder builder;

        //上滑动
        @Override
        public void swipeUp() {
            XLog.e("swipeUp()====");
        }

        //右滑动
        @Override
        public void swipeRight() {
            XLog.e("swipeRight()====");
        }

        //左滑动
        @Override
        public void swipeLeft() {
            XLog.e("swipeLeft()====");
        }

        ////下滑动
        @Override
        public void swipeDown() {
            XLog.e("swipeDown()====");
        }

        // 需要在 键盘xml，也就是我此时的number.xml里面中key标签对里添加一个keyOutputText的属性，打印出来的就是它的值
        @Override
        public void onText(CharSequence text) {
            if (null == builder) {
                builder = new StringBuilder();
            }
            builder.append(text);
            XLog.e("onText()====" + text + "==ed==" + builder.toString());
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(k1);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
                if (isnun) {
                    isnun = false;
                    keyboardView.setKeyboard(k1);
                } else {
                    isnun = true;
                    keyboardView.setKeyboard(k2);
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        //松开触发
        @Override
        public void onRelease(int primaryCode) {
            this.primaryCode = primaryCode;
            XLog.e("onRelease()====");
        }

        //按下触发
        @Override
        public void onPress(int primaryCode) {
            this.primaryCode = primaryCode;
            XLog.e("onPress()====");
        }

        //松开触发，在OnRelease之前触发
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            XLog.e("onKey()====");
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                hideKeyboard();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(k1);

            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 数字键盘切换
                if (isnun) {
                    isnun = false;
                    keyboardView.setKeyboard(k1);
                } else {
                    isnun = true;
                    keyboardView.setKeyboard(k2);
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else {
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Key> keylist = k1.getKeys();
        if (isupper) {//大写切换小写
            isupper = false;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {//小写切换大写
            isupper = true;
            for (Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toUpperCase();
                    key.codes[0] = key.codes[0] - 32;
                }
            }
        }
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE) {
            keyboardView.setVisibility(View.INVISIBLE);
        }
    }

    private boolean isword(String str) {
        String wordstr = "abcdefghijklmnopqrstuvwxyz";
        if (wordstr.indexOf(str.toLowerCase()) > -1) {
            return true;
        }
        return false;
    }
}
