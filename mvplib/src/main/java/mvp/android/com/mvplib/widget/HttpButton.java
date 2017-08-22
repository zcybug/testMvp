package mvp.android.com.mvplib.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：有点击效果请求中背景变灰，不可点击
 * 创建时间：2017/8/21 0021  上午 11:04
 * 修改历史：
 * ================================================
 */

@SuppressLint("AppCompatCustomView")
public class HttpButton extends Button {

    public HttpButton(Context context) {
        super(context);
    }

    public HttpButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HttpButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }
}
