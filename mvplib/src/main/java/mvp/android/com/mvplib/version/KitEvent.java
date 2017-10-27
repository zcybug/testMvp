package mvp.android.com.mvplib.version;

import org.greenrobot.eventbus.EventBus;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:45
 * 修改历史：
 * ================================================
 */

public class KitEvent {

  public KitEvent() {
  }

  public static void register(Object subscriber) {
    EventBus.getDefault().register(subscriber);
  }

  public static void unregister(Object subscriber) {
    if(isRegistered(subscriber)) {
      EventBus.getDefault().unregister(subscriber);
    }

  }

  public static boolean isRegistered(Object subscriber) {
    return EventBus.getDefault().isRegistered(subscriber);
  }

  public static boolean hasSubscriberForEvent(Class<?> eventClass) {
    return EventBus.getDefault().hasSubscriberForEvent(eventClass);
  }

  public static void normal(Object event) {
    EventBus.getDefault().post(event);
  }

  public static void sticky(Object event) {
    EventBus.getDefault().postSticky(event);
  }

  public static void stickyRemove(Object event) {
    EventBus.getDefault().removeStickyEvent(event);
  }

  public static void stickyRemove(Class<?> eventClass) {
    EventBus.getDefault().removeStickyEvent(eventClass);
  }

  public static void stickyClear() {
    EventBus.getDefault().removeAllStickyEvents();
  }

  public static void cancel(Object event) {
    EventBus.getDefault().cancelEventDelivery(event);
  }
}
