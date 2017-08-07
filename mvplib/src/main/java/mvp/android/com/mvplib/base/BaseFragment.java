package mvp.android.com.mvplib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    protected Context mContext;

    /**
     * 是否初始化完成
     */
    protected boolean isInitView = false;
    /**
     * 是否视第一次加载数据
     */
    protected boolean isFristAddData = false;
    /**
     * Fragment当前状态是否可见
     */
    protected boolean isVisible;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        isFristAddData = true;
        onFragmentCreate(savedInstanceState);
        if (getLayoutView() != 0) {
            View view = inflater.inflate(getLayoutView(), container, false);
            isInitView = true;
            return view;
        } else
            return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected abstract void onFragmentCreate(Bundle savedInstanceState);

    protected abstract int getLayoutView();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    /**
     * 延迟加载 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    protected abstract void initHttp();

}
