package mvp.android.com.mvplib.callback;

import com.lzy.okgo.request.BaseRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/23 0023.
 */

public abstract class MvpHttpCallback<T> extends MJsonCallback<T> {


    private IHttpCallback httpCallback;

    public MvpHttpCallback(int type, IHttpCallback httpCallback) {
        super(type);
        this.httpCallback = httpCallback;
    }

    public MvpHttpCallback(IHttpCallback httpCallback) {
        super();
        this.httpCallback = httpCallback;
    }

    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        httpCallback.onBefore(request);
    }

    @Override
    public void onAfter(T t, Exception e) {
        super.onAfter(t, e);
        httpCallback.onAfter(t, e);
    }

    @Override
    public void onCacheError(Call call, Exception e) {
        super.onCacheError(call, e);
        httpCallback.onCacheError(call, e);
    }

    @Override
    public void onCacheSuccess(T t, Call call) {
        super.onCacheSuccess(t, call);
        httpCallback.onCacheSuccess(t, call);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        httpCallback.onError(call, response, e);
    }

}
