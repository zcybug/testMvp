package mvp.android.com.mvplib.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;

import mvp.android.com.mvplib.BaseConfig;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.base.BaseDetailActivity;
import mvp.android.com.mvplib.log.XLog;
import mvp.android.com.mvplib.model.BaseEntity;
import mvp.android.com.mvplib.presenter.BasePresenter;
import mvp.android.com.mvplib.utils.DialogUtils;
import mvp.android.com.mvplib.view.IBaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public abstract class MvpRxActivity<P extends BasePresenter<V>, V extends IBaseView> extends BaseDetailActivity implements IBaseView {

    protected P presenter;
    protected V IView;
    private int loadId = 0;//0 无loading  1

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IView = createView();
        presenter = createPresenter();
        if (null != IView && null != presenter) {
            presenter.attachView(mContext, IView);
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
        if (null != presenter)
            presenter.detachView();
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
