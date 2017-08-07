package mvp.android.com.mvplib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import mvp.android.com.multiple_status_view.MultipleStatusView;
import mvp.android.com.mvplib.R;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class BaseDetailActivity extends BaseActivity {

    protected Context mContext;
    protected Activity mActivity;

    protected MultipleStatusView baseView;

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
}
