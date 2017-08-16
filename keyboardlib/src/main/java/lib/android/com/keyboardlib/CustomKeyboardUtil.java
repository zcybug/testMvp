package lib.android.com.keyboardlib;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.List;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/16 0016  下午 2:44
 * 修改历史：
 * ================================================
 */

public class CustomKeyboardUtil {

    private Context ctx;
    private MCustomKeyboardView keyboardView;
    private Keyboard number;// 键盘
    private Keyboard price;
    private Keyboard abc;
    private Keyboard idcard;
    //	private Keyboard k2;
//	private Keyboard k3;
    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写
    public final static int KEYBOARD_STYLE_NUMBER = 1; // 数量
    public final static int KEYBOARD_STYLE_PRICE = 2; // 价格
    public final static int KEYBOARD_STYLE_ABC = 3; // ABC
    public final static int KEYBOARD_STYLE_ID = 4; // 身份证
    public final static int KEYBOARD_STYLE_RANDOM_NUMBER = 5; // 随机书记数字键盘
    private int type = -1;
    private KeyboardListener keyboardListener;

    private EditText ed;

    public interface KeyboardListener {
        void onOK();
    }

    /**
     * @param ctx  必须要用Activity实例作为上下文传入
     * @param edit
     */
    public CustomKeyboardUtil(Context ctx, EditText edit, int type) {
        this.ctx = ctx;
        this.ed = edit;
        //此处可替换键盘xml
        number = new Keyboard(ctx, R.xml.custom_number);
        abc = new Keyboard(ctx, R.xml.custom_abc);
        price = new Keyboard(ctx, R.xml.custom_price);
        idcard = new Keyboard(ctx, R.xml.custom_idcard);
        try {
            keyboardView = (MCustomKeyboardView) ((Activity) ctx).findViewById(R.id.m_custom_keyboard);
            keyboardView.setContext(ctx);
            setStyle(type);
            keyboardView.setEnabled(true);
            keyboardView.setPreviewEnabled(true);
            keyboardView.setOnKeyboardActionListener(listener);
        } catch (Exception e) {
            Log.e("keyboardView", "keyboardView init failed!");
        }
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    private void setStyle(int style) {
        if (style == KEYBOARD_STYLE_NUMBER) {
            keyboardView.setKeyboard(number);
            isnun = false;
        } else if (style == KEYBOARD_STYLE_ABC) {
            isnun = true;
            keyboardView.setKeyboard(abc);
        } else if (style == KEYBOARD_STYLE_PRICE) {
            keyboardView.setKeyboard(price);
        } else if (style == KEYBOARD_STYLE_ID) {
            keyboardView.setKeyboard(idcard);
        } else if (style == KEYBOARD_STYLE_RANDOM_NUMBER) {
            randomNumKey();
            isnun = false;
            keyboardView.setKeyboard(number);
        }
    }

    public void setType(int typein) {
        type = typein;
        setStyle(type);
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void swipeUp() {
        }

        @Override
        public void swipeRight() {
        }

        @Override
        public void swipeLeft() {
        }

        @Override
        public void swipeDown() {
        }

        @Override
        public void onText(CharSequence text) {
        }

        @Override
        public void onRelease(int primaryCode) {
        }

        @Override
        public void onPress(int primaryCode) {
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                keyboardListener.onOK();
            } else if (primaryCode == Keyboard.KEYCODE_DELETE) {// 回退
                if (editable != null && editable.length() > 0) {
                    if (start > 0) {
                        editable.delete(start - 1, start);
                    }
                }
            } else if (primaryCode == Keyboard.KEYCODE_SHIFT) {// 大小写切换
                changeKey();
                keyboardView.setKeyboard(abc);
            } else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {// 键盘切换
                if (isnun) {
                    isnun = false;
                    if (type == KEYBOARD_STYLE_RANDOM_NUMBER) {
                        randomNumKey();
                    }
                    keyboardView.setKeyboard(number);
                } else {
                    isnun = true;
                    keyboardView.setKeyboard(abc);
                }
            } else if (primaryCode == 57419) { // go left
                if (start > 0) {
                    ed.setSelection(start - 1);
                }
            } else if (primaryCode == 57421) { // go right
                if (start < ed.length()) {
                    ed.setSelection(start + 1);
                }
            } else if (primaryCode == 46) {       // 小数点
                String text = ed.getText().toString();
                if (type == KEYBOARD_STYLE_PRICE) {
                    if (!ed.getText().toString().contains(".") && text.length() <= 7) {
                        editable.insert(start,
                                Character.toString((char) primaryCode));
                    }
                } else {
                    editable.insert(start,
                            Character.toString((char) primaryCode));
                }
            } else {
                String text = ed.getText().toString();
                switch (type) {
                    case KEYBOARD_STYLE_NUMBER:
                        if (text.length() < 7) {
                            editable.insert(start,
                                    Character.toString((char) primaryCode));
                        }
                        break;

                    case KEYBOARD_STYLE_PRICE:
                        if ((!text.contains(".") || text.length() - 1
                                - text.indexOf(".") <= 1)
                                && text.length() < (text.contains(".") ? 10 : 7)) {
                            //小数点后最长2位，接受7位整数
                            editable.insert(start,
                                    Character.toString((char) primaryCode));
                        }
                        break;
                    default:
                        editable.insert(start,
                                Character.toString((char) primaryCode));
                        break;
                }

            }
        }
    };

    /**
     * 键盘大小写切换
     */
    private void changeKey() {
        List<Keyboard.Key> keylist = abc.getKeys();
        if (isupper) {// 大写切换小写
            isupper = false;
            for (Keyboard.Key key : keylist) {
                if (key.label != null && isword(key.label.toString())) {
                    key.label = key.label.toString().toLowerCase();
                    key.codes[0] = key.codes[0] + 32;
                }
            }
        } else {// 小写切换大写
            isupper = true;
            for (Keyboard.Key key : keylist) {
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


    /**
     * 随机数字键盘,随机键盘LABEL中不能存在图片，否则在随机换位过程中会报错
     */
    private void randomNumKey() {
        List<Keyboard.Key> keyList = number.getKeys();
        int size = keyList.size();
        for (int i = 0; i < size; i++) {
            int random_a = (int) (Math.random() * (size));
            int random_b = (int) (Math.random() * (size));
            if (keyList.get(random_a).codes[0] == -2 || keyList.get(random_a).codes[0] == -5 || keyList.get(random_a).codes[0] == -3
                    || keyList.get(random_b).codes[0] == -2 || keyList.get(random_b).codes[0] == -5 || keyList.get(random_b).codes[0] == -3) {
                break;
            } else {
                int code = keyList.get(random_a).codes[0];
                CharSequence label = keyList.get(random_a).label;

                keyList.get(random_a).codes[0] = keyList.get(random_b).codes[0];
                keyList.get(random_a).label = keyList.get(random_b).label;

                keyList.get(random_b).codes[0] = code;
                keyList.get(random_b).label = label;
            }
        }
    }
}
