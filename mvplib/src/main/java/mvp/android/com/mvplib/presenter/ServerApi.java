package mvp.android.com.mvplib.presenter;

import android.graphics.Bitmap;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.convert.BitmapConvert;
import com.lzy.okgo.convert.FileConvert;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okrx.RxAdapter;

import java.io.File;
import java.util.List;
import java.util.Map;

import mvp.android.com.mvplib.BaseConfig;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.callback.ILoadFoodData;
import mvp.android.com.mvplib.callback.JsonConvert;
import mvp.android.com.mvplib.callback.MvpHttpCallback;
import mvp.android.com.mvplib.callback.OnDataLoadListener;
import mvp.android.com.mvplib.model.BaseEntity;
import mvp.android.com.mvplib.model.BaseResponse;
import mvp.android.com.mvplib.model.ServerModel;
import mvp.android.com.mvplib.utils.Urls;
import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;


/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy
 * 版    本：1.0
 * 创建日期：16/9/30
 * 描    述：
 * 修订历史：
 * ================================================
 */
public abstract class ServerApi<T> implements ILoadFoodData<T> {

    protected OnDataLoadListener onDataLoadListener;

    protected Object object;//网络请求Tag

    protected HttpCodeStatus requestId;//网络请求标识

    protected int loadId = 0;//loading标识 0 无loading  1


    public void setRequestId(HttpCodeStatus requestId, int loadId) {
        this.requestId = requestId;
        this.loadId = loadId;
    }

    public void setRequestId(HttpCodeStatus requestId) {
        setRequestId(requestId, 0);
    }

    public void setLoadId(int loadId) {
        setRequestId(HttpCodeStatus.HTTP_REQUEST_CODE_0, loadId);
    }

    public static Observable<String> getString(String header, String param) {
        return OkGo.post(Urls.URL_METHOD)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(StringConvert.create(), RxAdapter.<String>create());
    }

    public static Observable<BaseResponse<ServerModel>> getServerModel(String header, String param) {
        return OkGo.post(Urls.URL_JSONOBJECT)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(new JsonConvert<BaseResponse<ServerModel>>() {
                }, RxAdapter.<BaseResponse<ServerModel>>create());
    }

    public static Observable<BaseResponse<List<ServerModel>>> getServerListModel(String header, String param) {
        return OkGo.post(Urls.URL_JSONARRAY)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(new JsonConvert<BaseResponse<List<ServerModel>>>() {
                }, RxAdapter.<BaseResponse<List<ServerModel>>>create());
    }

    public static <T> Observable<BaseResponse<T>> getModel(String header, String param) {
        return OkGo.post(Urls.URL_JSONOBJECT)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(new JsonConvert<BaseResponse<T>>() {
                }, RxAdapter.<BaseResponse<T>>create());
    }

    public static Observable<Bitmap> getBitmap(String header, String param) {
        return OkGo.post(Urls.URL_IMAGE)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(BitmapConvert.create(), RxAdapter.<Bitmap>create());
    }

    public static Observable<File> getFile(String header, String param) {
        return OkGo.post(Urls.URL_DOWNLOAD)//
                .headers("aaa", header)//
                .params("bbb", param)//
                .getCall(new FileConvert(), RxAdapter.<File>create());
    }

    @Override
    public void getData(String httpUrl, final OnDataLoadListener onDataLoadListener) {
        OkGo.get(httpUrl)
                .tag(object)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void getData(String httpUrl, HttpParams params, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.get(httpUrl)
                .tag(object)
                .params(params)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void getData(String httpUrl, String name, String content, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.get(httpUrl)
                .tag(object)
                .params(name, content)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void getData(String httpUrl, Map params, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.get(httpUrl)
                .tag(object)
                .params(params)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }


    @Override
    public void postData(String httpUrl, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.post(httpUrl)
                .tag(object)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void postData(String httpUrl, HttpParams params, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.post(httpUrl)
                .tag(object)
                .params(params)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void postData(String httpUrl, String name, String content, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.post(httpUrl)
                .tag(object)
                .params(name, content)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }

    @Override
    public void postData(String httpUrl, Map params, final OnDataLoadListener onDataLoadListener) {
        this.onDataLoadListener = onDataLoadListener;
        OkGo.post(httpUrl)
                .tag(object)
                .params(params)
                .execute(new MvpHttpCallback<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity baseResponse, Call call, Response response) {
                        if (baseResponse.code == BaseConfig.SUCCESS_CODE_0) {
                            onDataLoadListener.success(requestId, loadId, baseResponse);
                        } else {
                            onDataLoadListener.hintMsg(baseResponse.code, baseResponse.msg);
                        }
                    }
                });
    }
}
