package mvp.android.com.mvplib.version;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:51
 * 修改历史：
 * ================================================
 */

public interface Callback<T> {
  void success(T var1);

  void failure(int var1, String var2);
}
