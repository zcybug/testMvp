package mvp.android.com.mvplib.widget;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

import mvp.android.com.mvplib.log.XLog;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/21 0021  下午 2:26
 * 修改历史：
 * ================================================
 */

@SuppressLint("AppCompatCustomView")
public class MEditText extends EditText implements TextWatcher {
    private Context context;
    private int maxLen = 8;//输入的最大位数 默认8
    private int pointLen = 2;//小数点后可输入多少位 默认2

    public MEditText(Context context) {
        super(context);
        this.context = context;
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public MEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        XLog.e("CharSequence=" + s.toString() + "start=" + start + "=count=" + count + "=after=" + after);
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        XLog.e("CharSequence=" + s.toString() + "start=" + start + "=count=" + count);
        setPriceLimit();
//        if (text.contains(".")) {
//            XLog.e("CharSequence=" + s.toString() + "start=" + start + "=count=" + count);
//            editable.delete(start, start);
//        }
//        if ((!text.contains(".") || text.length() - 1 - text.indexOf(".") <= 1)
//                && text.length() < (text.contains(".") ? 10 : 7)) {
//            XLog.e("CharSequence=" + s.toString() + "start=" + start + "=count=" + count);
//        }
//        if (editable.length() > 2 && !editable.toString().contains(".")) {//第一位不能为 .
//            XLog.e("CharSequence=" + s.toString() + "start=" + start + "=count=" + count);
////            editable.insert(s1, "825");
//        } else {
//
//        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        XLog.e("====" + s.toString());
    }

    public void setPriceLimit() {
        if (getInputType() == InputType.TYPE_CLASS_NUMBER) {
            Editable editable = this.getText();
            int start = this.getSelectionStart();
            String text = this.getText().toString();
            if (text.length() == 1) {
                if (text.substring(0, 1).contains(".")) {//取字符首位为 .
                    XLog.e("=========1=====");
                    editable.delete(start - 1, start);
                    Toast.makeText(context, "首位不能输入小数点", Toast.LENGTH_SHORT).show();
                }
            } else if (text.length() == 2) {//
                if (text.substring(0, 1).contains("0") && !text.substring(1, 2).contains(".")) {
                    XLog.e("=========2=====");
                    Toast.makeText(context, "首位0,第二位只能输入小数点", Toast.LENGTH_SHORT).show();
                    editable.delete(start - 1, start);
                }
            } else if (text.length() > 2 && text.length() < (text.contains(".") ? maxLen + pointLen + 1 : maxLen)) {
                if (text.substring(start - 2, start - 1).contains(".") && text.substring(start - 1, start).contains(".")) {
                    editable.delete(start - 1, start);
                    Toast.makeText(context, "小数点后面不能输入小数点", Toast.LENGTH_SHORT).show();
                } else {
                    if ((!text.contains(".") || text.length() - 1 - text.indexOf(".") <= pointLen)) {
                        XLog.e("==============");
                    } else {
                        editable.delete(start - 1, start);
                        Toast.makeText(context, "小数点后只能输入" + pointLen + "位小数", Toast.LENGTH_SHORT).show();
                    }
                }
            } else if (text.length() > (text.contains(".") ? maxLen + 3 : maxLen)) {
                editable.delete(start - 1, start);
                Toast.makeText(context, "最多只能输入" + maxLen, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
