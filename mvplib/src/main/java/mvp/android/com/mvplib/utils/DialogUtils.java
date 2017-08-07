package mvp.android.com.mvplib.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.Window;
import android.widget.Toast;

import mvp.android.com.mvplib.BaseConfig;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class DialogUtils {

    private ProgressDialog progressDialog;
    private static DialogUtils ourInstance = new DialogUtils();

    public static DialogUtils getInstance() {
        if (null == ourInstance)
            ourInstance = new DialogUtils();
        return ourInstance;
    }

    private DialogUtils() {
    }

    /**
     * @param context
     */
    public void showProgressDialog(Context context) {
        if (progressDialog != null && progressDialog.isShowing()) return;
        progressDialog = new ProgressDialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("请求网络中...");
        progressDialog.show();
    }


    public void hideProgressDialog() {
        if (null != progressDialog)
            progressDialog.dismiss();
    }

    /**
     * 提示接口返回错误
     *
     * @param code
     * @param errMsg
     */
    public void showErrMsg(Context context, int code, String errMsg) {
        if (code == BaseConfig.SYSTEM_404) {
            showToast(context, errMsg);
        } else if (code == BaseConfig.SYSTEM_500) {
            showToast(context, errMsg);
        } else if (code == BaseConfig.FINISH_CODE_10001) {//请求完成 okgo onAfter

        } else {
            showToast(context, errMsg);
        }
    }

    public void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
