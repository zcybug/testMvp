package lib.android.com.keyboardlib;

import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.os.Vibrator;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

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

public class CustomViewKeyboardUtil {

    private Context ctx;
    private Activity act;
    private MCustomKeyboardView keyboardView;
    private Keyboard number;// 键盘
    private Keyboard price;
    private Keyboard abc;
    private Keyboard idcard;
    private Keyboard symbol;
    private Keyboard random;
    public boolean isnun = false;// 是否数据键盘
    public boolean isupper = false;// 是否大写
    public final static int KEYBOARD_STYLE_NUMBER = 1; // 数量
    public final static int KEYBOARD_STYLE_PRICE = 2; // 价格
    public final static int KEYBOARD_STYLE_ABC = 3; // ABC
    public final static int KEYBOARD_STYLE_ID = 4; // 身份证
    public final static int KEYBOARD_STYLE_RANDOM_NUMBER = 5; // 随机书记数字键盘
    public final static int KEYBOARD_STYLE_SYMBOL = 6;//符号键盘
    private int type = -1;
    private KeyboardListener keyboardListener;

    private EditText ed;

    private RelativeLayout rl_layout;
    private TextView ok;

    public interface KeyboardListener {
        void onOK();
    }


    /**
     * @param ctx  必须要用Activity实例作为上下文传入
     * @param edit
     */
    public CustomViewKeyboardUtil(Context ctx, EditText edit, int type) {
        this.ctx = ctx;
        this.ed = edit;
        this.act = (Activity) ctx;
        //此处可替换键盘xml
        number = new Keyboard(ctx, R.xml.custom_number);
        abc = new Keyboard(ctx, R.xml.custom_abc);
        price = new Keyboard(ctx, R.xml.custom_price);
        idcard = new Keyboard(ctx, R.xml.custom_idcard);
        symbol = new Keyboard(ctx, R.xml.custom_symbol);
        random = new Keyboard(ctx, R.xml.custom_random_number);
        initView();
    }

    private void initView() {
        rl_layout = (RelativeLayout) ((Activity) ctx).findViewById(R.id.rl_keyboard_layout);
        ok = (TextView) ((Activity) ctx).findViewById(R.id.btn_keyboard_ok);
        try {
            keyboardView = (MCustomKeyboardView) ((Activity) ctx).findViewById(R.id.custom_keyboard_view);
            keyboardView.setContext(ctx);
            setStyle(type);
            keyboardView.setEnabled(true);
            keyboardView.setPreviewEnabled(true);
            keyboardView.setOnKeyboardActionListener(listener);
        } catch (Exception e) {
            Log.e("keyboardView", "keyboardView init failed!");
        }
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                type = -1;
                if (null != keyboardListener)
                    keyboardListener.onOK();
            }
        });
    }

    public void setKeyboardListener(KeyboardListener keyboardListener) {
        this.keyboardListener = keyboardListener;
    }

    private void setStyle(int style) {
        this.type = style;
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
//            randomNumKey();
            randomdigkey();
            isnun = false;
//            keyboardView.setKeyboard(random);
        } else if (style == KEYBOARD_STYLE_SYMBOL) {
            isnun = true;
            keyboardView.setKeyboard(symbol);
        } else {
            isnun = true;
            keyboardView.setKeyboard(abc);
        }
    }

    public void setType(int typein) {
        Log.e("", "typein==" + typein);
        setStyle(typein);
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
            vibrate(20);
//            if (primaryCode == Keyboard.KEYCODE_SHIFT || primaryCode == Keyboard.KEYCODE_DELETE
//                    || primaryCode == 32 || primaryCode == Keyboard.KEYCODE_MODE_CHANGE
//                    || (primaryCode >= 48 && primaryCode <= 57)) {
//                keyboardView.setPreviewEnabled(false);
//            } else {
//                keyboardView.setPreviewEnabled(true);
//            }
        }

        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            Editable editable = ed.getText();
            int start = ed.getSelectionStart();
            if (primaryCode == Keyboard.KEYCODE_CANCEL) {// 完成
                if (null != keyboardListener)
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
                Log.e("", "isnun==" + isnun);
                if (type == KEYBOARD_STYLE_SYMBOL) {
                    type = KEYBOARD_STYLE_NUMBER;
                    keyboardView.setKeyboard(abc);
                } else {
                    if (isnun) {
                        isnun = false;
                        if (type == KEYBOARD_STYLE_RANDOM_NUMBER) {
//                            randomNumKey();
                            randomdigkey();
                        } else {
                            keyboardView.setKeyboard(number);
                        }
                    } else {
                        isnun = true;
                        keyboardView.setKeyboard(abc);
                    }
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
            } else if (primaryCode == 58971) {//标题

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

//    public void showKeyboard() {
//        int visibility = keyboardView.getVisibility();
//        if (visibility == View.GONE || visibility == View.INVISIBLE) {
//            keyboardView.setVisibility(View.VISIBLE);
//        }
//    }
//
//    public void hideKeyboard() {
//        int visibility = keyboardView.getVisibility();
//        if (visibility == View.VISIBLE) {
//            keyboardView.setVisibility(View.INVISIBLE);
//        }
//    }

    public void showKeyboard(int type) {
        this.ed.setFocusable(true);
        setType(type);
        hideKeyBoard();
        //设置系统亮度
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.System.canWrite(ctx)) {
//                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                intent.setData(Uri.parse("package:" + ctx.getPackageName()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
//            } else {
//                //有了权限，具体的动作
//                Settings.System.putInt(this.ctx.getContentResolver(),
//                        Settings.System.SCREEN_BRIGHTNESS, progress);
//            }
//        }
//        Settings.System.putInt(this.ctx.getContentResolver(), Settings.System.TEXT_SHOW_PASSWORD, 1);
//        rbLetter.setChecked(true);
        int visibility = rl_layout.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            rl_layout.setVisibility(View.VISIBLE);
            rl_layout.startAnimation(AnimationUtils.loadAnimation(this.ctx, R.anim.show_keyboard));
        }
    }

    public void hideKeyboard() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (!Settings.System.canWrite(ctx)) {
//                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
//                intent.setData(Uri.parse("package:" + ctx.getPackageName()));
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
//            } else {
//                //有了权限，具体的动作
//                Settings.System.putInt(this.ctx.getContentResolver(),
//                        Settings.System.SCREEN_BRIGHTNESS, progress);
//            }
//        }
//        Settings.System.putInt(this.ctx.getContentResolver(), Settings.System.TEXT_SHOW_PASSWORD, 1);
        int visibility = rl_layout.getVisibility();
        if (visibility == View.VISIBLE) {
            rl_layout.setVisibility(View.INVISIBLE);
            rl_layout.startAnimation(AnimationUtils.loadAnimation(this.ctx, R.anim.hide_keyboard));
        }
    }


    /**
     * 打开系统软键盘
     */
    protected void openKeyBoard() {
        hideKeyboard();
        InputMethodManager imm = (InputMethodManager) this.ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭系统软键盘
     */
    protected void hideKeyBoard() {
        InputMethodManager inputMsg = (InputMethodManager) this.ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMsg.isActive()) { // 隐藏软键盘
            View curView = this.act.getCurrentFocus();
            if (curView != null) {
                inputMsg.hideSoftInputFromWindow(curView.getWindowToken(), 0);
            }
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
        keyboardView.setKeyboard(number);
    }


    private void randomdigkey() {
        List<Keyboard.Key> keyList = random.getKeys();
        // 查找出0-9的数字键
        List<Keyboard.Key> newkeyList = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            if (keyList.get(i).label != null
                    && isNumber(keyList.get(i).label.toString())) {
                newkeyList.add(keyList.get(i));
            }
        }
        // 数组长度
        int count = newkeyList.size();
        // 结果集
        List<KeyModel> resultList = new ArrayList<KeyModel>();
        // 用一个LinkedList作为中介
        LinkedList<KeyModel> temp = new LinkedList<KeyModel>();
        // 初始化temp
        for (int i = 0; i < count; i++) {
            temp.add(new KeyModel(48 + i, i + ""));
        }
        // 取数
        Random rand = new Random();
        for (int i = 0; i < count; i++) {
            int num = rand.nextInt(count - i);
            resultList.add(new KeyModel(temp.get(num).getCode(), temp.get(num)
                    .getLable()));
            temp.remove(num);
        }
        for (int i = 0; i < newkeyList.size(); i++) {
            newkeyList.get(i).label = resultList.get(i).getLable();
            newkeyList.get(i).codes[0] = resultList.get(i).getCode();
        }
        keyboardView.setKeyboard(random);
    }

    private boolean isNumber(String str) {
        String wordstr = "0123456789.,";
        return wordstr.contains(str);
    }


    /**
     * 震动
     *
     * @param duration
     */
    protected void vibrate(long duration) {
        Vibrator vibrator = (Vibrator) this.ctx.getSystemService(Context.VIBRATOR_SERVICE);
        long[] pattern = {
                0, duration
        };
        vibrator.vibrate(pattern, -1);
    }
}
