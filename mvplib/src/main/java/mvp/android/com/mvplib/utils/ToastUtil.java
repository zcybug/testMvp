package mvp.android.com.mvplib.utils;

import android.content.Context;

import mvp.android.com.mvplib.widget.ToastView;


/**
 * 创建：   dema
 * 时间：   2017/5/12 16:37
 */
public class ToastUtil {

    private static final ToastUtil ourInstance = new ToastUtil();

    public static ToastUtil getInstance() {
        return ourInstance;
    }

    private ToastUtil() {
    }


    public void showErrMsg(Context context, String msg) {
        ToastView.makeText(context.getApplicationContext(), msg).show();
    }

}
