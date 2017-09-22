package baba.jade.android.com.tablib.jp.animate;

import android.view.View;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by jpeng on 16-11-15.
 * 实现旋转的动画类
 */
public class RotateAnimater extends BouncingAnimater{


    @Override
    public void onPressDown(View v, boolean selected) {
        super.onPressDown(v,selected);
        getSpring().setEndValue(180f);
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
        super.onTouchOut(v,selected);
        getSpring().setEndValue(selected?360f:0f);
    }

    @Override
    public void onSelectChanged(View v,boolean selected) {
        super.onSelectChanged(v,selected);
        getSpring().setEndValue(selected?360f:0f);

    }

    @Override
    public void onPageAnimate(View v,float offset) {
        setPlaying(false);
        ViewHelper.setRotation(mTarget, offset*360);

    }

    @Override
    public boolean isNeedPageAnimate() {
        return true;
    }


    @Override
    public void onSpringUpdate(View target, float currentValue) {
        if(isPlaying())
            ViewHelper.setRotation(target, currentValue);
    }
}
