package baba.jade.android.com.tablib.jp.animate;

import android.view.View;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

/**
 * Created by jpeng on 16-11-20.
 * 实现弹性的动画父类
 */
public abstract class BouncingAnimater implements Animatable{
    //弹性动画控制
    private Spring mSpring;
    //是否正在播放动画
    private boolean mPlaying;
    //动画控件
    View mTarget;

    public BouncingAnimater(){
        buildSpring();
    }

    public Spring getSpring() {
        return mSpring;
    }

    public boolean isPlaying(){
        return mPlaying;
    }

    public void bindTarget(View target){
        mTarget = target;
    }

    public void setPlaying(boolean play){
        this.mPlaying = play;
        if (!play&&mSpring!=null){
            mSpring.setAtRest();
        }
    }

    public Spring buildSpring(){
        SpringSystem mSystem = SpringSystem.create();
        mSpring = mSystem.createSpring();
        mSpring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring) {
                BouncingAnimater.this.onSpringUpdate(mTarget, (float) spring.getCurrentValue());

            }
        });
        return mSpring;
    };



    @Override
    public void onPressDown(View v, boolean selected) {
        setPlaying(true);
        getSpring().setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(100,5f));
    }

    @Override
    public void onTouchOut(View v, boolean selected) {
        setPlaying(true);
        getSpring().setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(50,2f));
    }

    @Override
    public void onSelectChanged(View v, boolean selected) {
        setPlaying(true);
        getSpring().setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(50*3,3*2f));
    }

    public abstract void onSpringUpdate(View target, float currentValue);
}
