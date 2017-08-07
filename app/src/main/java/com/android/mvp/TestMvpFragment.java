package com.android.mvp;

import android.os.Bundle;
import android.view.View;

import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.log.XLog;
import mvp.android.com.mvplib.model.ServerModel;
import mvp.android.com.mvplib.mvp.MvpRxFragment;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class TestMvpFragment extends MvpRxFragment<TestPresenter, TestView> implements TestView {

    public static TestMvpFragment newInstance() {
        return new TestMvpFragment();
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected TestView createView() {
        return this;
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_test;
    }

    @Override
    protected void onFragmentCreate(Bundle savedInstanceState) {
        super.onFragmentCreate(savedInstanceState);
    }

    @Override
    protected void initHttp() {
        if (null != presenter)
            addSubscribe(presenter.getServerModel());
        else
            XLog.e("presenter is null");
    }

    @Override
    public <T> void success(HttpCodeStatus requestCode, int loadId, T data) {
        super.success(requestCode, loadId, data);
        ServerModel serverModel = (ServerModel) data;
        XLog.e("=requestCode=" + requestCode + "serverModel==" + serverModel.toString());
    }

    //    @OnClick(R.id.save)
    public void requestImage(View view) {
        if (null != presenter)
            addSubscribe(presenter.getServerModel());
        else
            XLog.e("presenter is null");

//        OkGo.get(Urls.URL_JSONOBJECT)//
//                .tag(this)//
//                .headers("header1", "headerValue1")//
//                .params("param1", "paramValue1")//
//                .execute(new DialogCallback<BaseResponse<ServerModel>>(this) {
//                    @Override
//                    public void onSuccess(BaseResponse<ServerModel> responseData, Call call, Response response) {
////                        XLog.e("code=" + responseData.code + ";msg=" + responseData.msg + ";data=" + responseData.data.toString());
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                    }
//                });

//        if (null != presenter)
//            addSubscribe(presenter.requestImage(imageView));
//        else
//            XLog.e("presenter is null");

//        Subscription subscription = ServerApi.getBitmap("aaa", "bbb")//
//                .doOnSubscribe(new Action0() {
//                    @Override
//                    public void call() {
//                        showLoading();
//                    }
//                })//
//                .observeOn(AndroidSchedulers.mainThread())//
//                .subscribe(new Action1<Bitmap>() {
//                    @Override
//                    public void call(Bitmap bitmap) {
//                        hideLoading();                   //请求成功
//                        imageView.setImageBitmap(bitmap);
//                        System.out.println("---------");
//                    }
//                }, new Action1<Throwable>() {
//                    @Override
//                    public void call(Throwable throwable) {
//                        throwable.printStackTrace();            //请求失败
//                        showToast("请求失败");
//                        hideLoading();
//                    }
//                });
//        addSubscribe(subscription);
    }
}
