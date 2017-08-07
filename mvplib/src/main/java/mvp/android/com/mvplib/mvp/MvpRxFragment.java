package mvp.android.com.mvplib.mvp;

import android.os.Bundle;

import mvp.android.com.mvplib.BaseConfig;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.base.BaseDetailFragment;
import mvp.android.com.mvplib.log.XLog;
import mvp.android.com.mvplib.model.BaseEntity;
import mvp.android.com.mvplib.presenter.BasePresenter;
import mvp.android.com.mvplib.utils.DialogUtils;
import mvp.android.com.mvplib.view.IBaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public abstract class MvpRxFragment<P extends BasePresenter<V>, V extends IBaseView> extends BaseDetailFragment implements IBaseView {

    protected P presenter;
    protected V IView;
    private int loadId = 0;//0 无loading  1

    @Override
    protected void onFragmentCreate(Bundle savedInstanceState) {
        IView = createView();
        presenter = createPresenter();
        if (null != IView && null != presenter) {
            presenter.attachView(mContext, IView);
        }
        lazyLoad();
    }

    /**
     * 创建P
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 创建V
     *
     * @return
     */
    protected abstract V createView();

    @Override
    public void getLoadId(int loadId) {
        this.loadId = loadId;
    }

    @Override
    public void hideLoading() {
        DialogUtils.getInstance().hideProgressDialog();
    }

    @Override
    public void showLoading() {
        DialogUtils.getInstance().showProgressDialog(mContext);
    }


    @Override
    public <T> void success(HttpCodeStatus requestCode, int loadId, T data) {
        hideLoading();
    }

    @Override
    public void success(HttpCodeStatus requestCode, int loadId, BaseEntity data) {
        hideLoading();
        XLog.e("requestCode=" + requestCode + "=loadId=" + loadId + "=code=" + data.code + "=msg=" + data.msg);
    }

    @Override
    public void hintMsg(String msg) {
        hintMsg(BaseConfig.SUCCESS_CODE_10000, msg);
    }

    @Override
    public void hintMsg(int code) {
        hintMsg(code, null);
    }

    @Override
    public void hintMsg(int code, String msg) {
        XLog.e("code==" + code + ";msg==" + msg);
        hideLoading();
        DialogUtils.getInstance().showErrMsg(mContext, code, msg);
    }

    //调用该方法，fragment清除掉资源与该activity的关联；
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unSubscribe();
        if (null != presenter)
            presenter.detachView();
    }

    //    调用该方法，通知fragment，该视图层已保存；
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //未测试
        if (null == IView) {
            IView = createView();
            presenter = createPresenter();
            if (null != IView && null != presenter) {
                presenter.attachView(mContext, IView);
            }
        }
    }

    private CompositeSubscription compositeSubscription;

    public void addSubscribe(Subscription subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = new CompositeSubscription();
        }
        compositeSubscription.add(subscription);
    }

    public void unSubscribe() {
        if (compositeSubscription != null) compositeSubscription.unsubscribe();
    }
}
