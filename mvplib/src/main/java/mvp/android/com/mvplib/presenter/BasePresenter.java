package mvp.android.com.mvplib.presenter;

import android.support.annotation.Nullable;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.BaseRequest;

import java.lang.ref.SoftReference;

import mvp.android.com.mvplib.BaseConfig;
import mvp.android.com.mvplib.log.XLog;
import mvp.android.com.mvplib.view.IBaseView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class BasePresenter<V extends IBaseView> extends ServerApi {
    //即对象的软引用。如果一个对象具有软引用，内存空间足够，垃圾回收器就不会回收它；
    // 如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。
    // 软引用可用来实现内存敏感的高 速缓存。使用软引用能防止内存泄露，增强程序的健壮性。
    // SoftReference的特点是它的一个实例保存对一个Java对象的软引用
    // 该软引用的存在不妨碍垃圾收集线程对该Java对象的回收。
    // 也就是说，一旦SoftReference保存了对一个Java对象的软引用后，在垃圾线程对 这个Java对象回收前，
    // SoftReference类所提供的get()方法返回Java对象的强引用。另外，一旦垃圾线程回收该Java对象之后，
    // get()方法将返回null
    private SoftReference<V> mView;

    /**
     * 返回继承V的对象
     *
     * @return
     */
    public V getRealView() {
        return mView != null ? mView.get() : null;
    }

    /**
     * 将presenter与view绑定
     **/
    public void attachView(Object object, V view) {
        this.object = object;
        mView = new SoftReference<V>(view);
    }

    /**
     * 将presenter与view解绑
     **/
    public void detachView() {
        if (mView != null) {
            mView.clear();
        }
        onCancelTag();
    }

    /**
     * 取消请求
     */
    public void onCancelTag() {
        if (null != object)
            OkGo.getInstance().cancelTag(object);
    }

    @Override
    public void onBefore(BaseRequest request) {
        getRealView().getLoadId(loadId);
        getRealView().showLoading();
    }

    @Override
    public void onCacheSuccess(Object o, Call call) {

    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        XLog.e("e.getMessage()=" + e.getMessage() + "=response.code()=" + response.code());
        getRealView().getLoadId(loadId);
        getRealView().hintMsg(response.code(), e.getMessage());
    }

    @Override
    public void onCacheError(Call call, Exception e) {

    }

    /**
     * 网络失败结束之前的回调
     */
    @Override
    public void parseError(Call call, Exception e) {

    }

    @Override
    public void onAfter(@Nullable Object o, @Nullable Exception e) {
        if (null != onDataLoadListener)
            onDataLoadListener.hintMsg(BaseConfig.FINISH_CODE_10001);
    }

    @Override
    public void upProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {

    }
}
