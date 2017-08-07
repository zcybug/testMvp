package mvp.android.com.mvplib.callback;

import com.lzy.okgo.model.HttpParams;

import java.util.Map;


/**
 * Created by 王松 on 2016/10/8.
 */

public interface ILoadFoodData<T> extends IHttpCallback<T> {

    /**
     * 无参数请求
     *
     * @param httpUrl
     * @param onDataLoadListener
     */
    void getData(String httpUrl, OnDataLoadListener onDataLoadListener);


    void getData(String httpUrl, HttpParams params, OnDataLoadListener onDataLoadListener);

    /**
     * 一个参数请求
     *
     * @param httpUrl
     * @param name
     * @param content
     * @param onDataLoadListener
     */
    void getData(String httpUrl, String name, String content, OnDataLoadListener onDataLoadListener);

    /**
     * 键值对请求
     *
     * @param httpUrl
     * @param params
     * @param onDataLoadListener
     */
    void getData(String httpUrl, Map params, OnDataLoadListener onDataLoadListener);


    /**
     * 无参数请求
     *
     * @param httpUrl
     * @param onDataLoadListener
     */
    void postData(String httpUrl, OnDataLoadListener onDataLoadListener);

    void postData(String httpUrl, HttpParams params, OnDataLoadListener onDataLoadListener);

    /**
     * 一个参数请求
     *
     * @param httpUrl
     * @param name
     * @param content
     * @param onDataLoadListener
     */
    void postData(String httpUrl, String name, String content, OnDataLoadListener onDataLoadListener);

    /**
     * 键值对请求
     *
     * @param httpUrl
     * @param params
     * @param onDataLoadListener
     */
    void postData(String httpUrl, Map params, OnDataLoadListener onDataLoadListener);

}
