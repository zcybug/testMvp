package com.android.mvp;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.lzy.okgo.OkGo;
import com.lzy.okrx.RxAdapter;

import mvp.android.com.mvplib.BaseConfig;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.callback.JsonConvert;
import mvp.android.com.mvplib.callback.MvpHttpCallback;
import mvp.android.com.mvplib.callback.OnDataLoadListener;
import mvp.android.com.mvplib.model.BaseResponse;
import mvp.android.com.mvplib.model.ServerModel;
import mvp.android.com.mvplib.presenter.BasePresenter;
import mvp.android.com.mvplib.utils.StrUtils;
import mvp.android.com.mvplib.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class TestPresenter extends BasePresenter<TestView> {

    public void verify(String msg) {
        if (StrUtils.isNull(msg)) {
            System.out.print("msg is null");
            return;
        }
    }

    public Subscription requestImage(final ImageView imageView) {
        return getBitmap("aaa", "bbb")//
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getRealView().showLoading();
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Action1<Bitmap>() {
                    @Override
                    public void call(Bitmap bitmap) {
                        getRealView().hintMsg("请求成功");
                        imageView.setImageBitmap(bitmap);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();            //请求失败
                        getRealView().hintMsg("请求失败");
                    }
                });
    }


    public Subscription getServerModel() {
        return OkGo.post(Urls.URL_JSONOBJECT)//
                .headers("aaa", "111")//
                .params("bbb", "222")//一定要注意这里的写法,JsonConvert最后的大括号千万不能忘记
                .getCall(new JsonConvert<BaseResponse<ServerModel>>() {
                }, RxAdapter.<BaseResponse<ServerModel>>create())//
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        getRealView().showLoading();
                    }
                })//
                .map(new Func1<BaseResponse<ServerModel>, ServerModel>() {
                    @Override
                    public ServerModel call(BaseResponse<ServerModel> response) {
                        return response.data;
                    }
                })//
                .observeOn(AndroidSchedulers.mainThread())//
                .subscribe(new Action1<ServerModel>() {
                    @Override
                    public void call(ServerModel serverModel) {
                        getRealView().hintMsg("请求成功");
                        getRealView().success(HttpCodeStatus.HTTP_REQUEST_CODE_STRING, loadId, serverModel);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();            //请求失败
                        getRealView().hintMsg("请求失败");
                    }
                });
    }


    public void postServerModel(String httpUrl, final OnDataLoadListener onDataLoadListener) {
//        this.onDataLoadListener = onDataLoadListener;
        OkGo.post(httpUrl)
                .tag(object)
                .execute(new MvpHttpCallback<BaseResponse<ServerModel>>(this) {
                    @Override
                    public void onSuccess(BaseResponse<ServerModel> serverModelBaseResponse, Call call, Response response) {
                        if (serverModelBaseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, serverModelBaseResponse.data);
                        } else {
                            onDataLoadListener.hintMsg(serverModelBaseResponse.code, serverModelBaseResponse.msg);
                        }
                    }
                });
    }
}
