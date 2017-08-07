package mvp.android.com.mvplib.base;

import mvp.android.com.mvplib.log.XLog;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public abstract class BaseDetailFragment extends BaseFragment {


    @Override
    protected void lazyLoad() {
        XLog.e("isVisible=" + isVisible + ";isInitView=" + isInitView + ";isFristAddData=" + isFristAddData);
        if (!isVisible || !isInitView || !isFristAddData) {
            return;
        }
        isFristAddData = false;
        initHttp();
    }

}
