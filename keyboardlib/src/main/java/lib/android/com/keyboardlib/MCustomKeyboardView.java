package lib.android.com.keyboardlib;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/16 0016  下午 2:30
 * 修改历史：
 * ================================================
 */

public class MCustomKeyboardView extends KeyboardView {
    private Context context;

    public MCustomKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public MCustomKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}
