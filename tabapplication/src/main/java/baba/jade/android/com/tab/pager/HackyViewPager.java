package baba.jade.android.com.tab.pager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/18 0018  下午 3:21
 * 修改历史：
 * ================================================
 */

public class HackyViewPager extends ViewPager {

  private boolean isLocked;

  public HackyViewPager(Context context) {
    super(context);
    isLocked = false;
  }

  public HackyViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    isLocked = false;
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    if (!isLocked) {
      try {
        return super.onInterceptTouchEvent(ev);
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
        return false;
      }
    }
    return false;
  }

  @Override public boolean onTouchEvent(MotionEvent event) {
    return !isLocked && super.onTouchEvent(event);
  }

  public void toggleLock() {
    isLocked = !isLocked;
  }

  public void setLocked(boolean isLocked) {
    this.isLocked = isLocked;
  }

  public boolean isLocked() {
    return isLocked;
  }
}
