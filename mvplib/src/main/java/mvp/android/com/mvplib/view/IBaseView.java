package mvp.android.com.mvplib.view;

import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.model.BaseEntity;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public interface IBaseView {


    void getLoadId(int loadId);

    /**
     * 隐藏loading
     */
    void hideLoading();

    /**
     * 显示loading
     */
    void showLoading();

    /**
     * 显示成功
     */
    void hintMsg(String msg);

    /**
     * 显示成功
     */
    void hintMsg(int code);

    /**
     * 显示成功
     */
    void hintMsg(int code, String msg);

    /**
     * 请求成功
     *
     * @param data        返回所需要的实体类
     * @param <T>
     * @param requestCode 网络请求标识
     * @param loadId      loading标识
     */
    <T extends Object> void success(HttpCodeStatus requestCode, int loadId, T data);

    /**
     * 请求成功
     *
     * @param data        返回所需要的实体类
     * @param requestCode 网络请求标识
     * @param loadId      loading标识
     */
    void success(HttpCodeStatus requestCode, int loadId, BaseEntity data);
}
