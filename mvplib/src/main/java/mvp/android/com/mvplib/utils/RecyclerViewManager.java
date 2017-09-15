package mvp.android.com.mvplib.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

/**
 * ================================================
 * 项目名称：BabApp
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/9/15 0015  上午 10:49
 * 修改历史：
 * ================================================
 */

public class RecyclerViewManager {

  /**
   * 单例对象实例
   */
  private static RecyclerViewManager instance = null;

  public static RecyclerViewManager getInstance() {
    if (instance == null) {
      instance = new RecyclerViewManager();
    }
    return instance;
  }

  public RecyclerViewManager initVerticalRv(Context context) {
    LinearLayoutManager layoutManager = new LinearLayoutManager(context);
    return this;
  }
}
