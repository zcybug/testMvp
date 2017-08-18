package mvp.android.com.mvplib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.EditText;

import lib.android.com.keyboardlib.CustomViewKeyboardUtil;
import mvp.android.com.multiple_status_view.MultipleStatusView;
import mvp.android.com.mvplib.R;
import mvp.android.com.mvplib.log.XLog;

import static lib.android.com.keyboardlib.CustomViewKeyboardUtil.KEYBOARD_STYLE_ABC;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class BaseDetailActivity extends BaseActivity {

    protected Context mContext;
    protected Activity mActivity;

    protected MultipleStatusView baseView;

    //自定义键盘工具类
    protected CustomViewKeyboardUtil keyboardUtil = null;
    //自定义键盘标识
    protected int custom_keyboard_change_type = KEYBOARD_STYLE_ABC;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        mActivity = this;
        getDelegate().setContentView(R.layout.base_layout);
        Window window = getWindow();
        baseView = (MultipleStatusView) window.findViewById(R.id.base_multiple_status);
        baseView.showContent();
        baseView.setContentView(getLayoutView());
        initToolbar();
        onActivityCreate(savedInstanceState);
    }


    /**
     * 初始化
     *
     * @param context
     * @param change_type
     * @param keyboardListener
     * @param editText
     * @return
     */
    protected void initCustomKeyboard(Context context, int change_type, CustomViewKeyboardUtil.KeyboardListener keyboardListener, EditText editText) {
        if (null != editText) {
            keyboardUtil = new CustomViewKeyboardUtil(context, editText, change_type);
            if (null != keyboardListener) {
                keyboardUtil.setKeyboardListener(keyboardListener);
            }
            keyboardUtil.showKeyboard(change_type);
        }
    }


    protected void showCustomKeyboard(EditText editText) {
        if (null == keyboardUtil) {
            initCustomKeyboard(mContext, custom_keyboard_change_type, null, editText);
        }
        XLog.e("custom_keyboard_change_type=" + custom_keyboard_change_type);
        keyboardUtil.showKeyboard(custom_keyboard_change_type);
    }

    protected void hideCustomKeyboard() {
        if (null != keyboardUtil)
            keyboardUtil.hideKeyboard();
    }


}
