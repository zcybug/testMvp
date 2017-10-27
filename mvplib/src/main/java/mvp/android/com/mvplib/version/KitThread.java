package mvp.android.com.mvplib.version;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import java.util.LinkedList;
import java.util.Queue;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:52
 * 修改历史：
 * ================================================
 */

public class KitThread {

  private static KitThread.UIHandlerPoster mainPoster = null;

  public KitThread() {
  }

  private static KitThread.UIHandlerPoster getMainPoster() {
    if (mainPoster == null) {
      Class var0 = KitThread.class;
      synchronized (KitThread.class) {
        if (mainPoster == null) {
          mainPoster = new KitThread.UIHandlerPoster(Looper.getMainLooper(), 16);
        }
      }
    }

    return mainPoster;
  }

  public static void runOnMainThreadAsync(Runnable runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run();
    } else {
      getMainPoster().async(runnable);
    }
  }

  public static void runOnMainThreadSync(Runnable runnable) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run();
    } else {
      KitThread.UISyncPost poster = new KitThread.UISyncPost(runnable);
      getMainPoster().sync(poster);
      poster.waitRun();
    }
  }

  public static void runOnMainThreadSync(Runnable runnable, int waitTime, boolean cancel) {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      runnable.run();
    } else {
      KitThread.UISyncPost poster = new KitThread.UISyncPost(runnable);
      getMainPoster().sync(poster);
      poster.waitRun(waitTime, cancel);
    }
  }

  public static void dispose() {
    if (mainPoster != null) {
      mainPoster.dispose();
      mainPoster = null;
    }
  }

  static final class UIHandlerPoster extends Handler {
    private static final int ASYNC = 1;
    private static final int SYNC = 2;
    private final Queue<Runnable> mAsyncPool;
    private final Queue<KitThread.UISyncPost> mSyncPool;
    private final int mMaxMillisInsideHandleMessage;
    private boolean isAsyncActive;
    private boolean isSyncActive;

    UIHandlerPoster(Looper looper, int maxMillisInsideHandleMessage) {
      super(looper);
      this.mMaxMillisInsideHandleMessage = maxMillisInsideHandleMessage;
      this.mAsyncPool = new LinkedList();
      this.mSyncPool = new LinkedList();
    }

    void dispose() {
      this.removeCallbacksAndMessages((Object) null);
      this.mAsyncPool.clear();
      this.mSyncPool.clear();
    }

    void async(Runnable runnable) {
      Queue var2 = this.mAsyncPool;
      synchronized (this.mAsyncPool) {
        this.mAsyncPool.offer(runnable);
        if (!this.isAsyncActive) {
          this.isAsyncActive = true;
          if (!this.sendMessage(this.obtainMessage(1))) {
            throw new RuntimeException("Could not send handler message");
          }
        }
      }
    }

    void sync(KitThread.UISyncPost post) {
      Queue var2 = this.mSyncPool;
      synchronized (this.mSyncPool) {
        this.mSyncPool.offer(post);
        if (!this.isSyncActive) {
          this.isSyncActive = true;
          if (!this.sendMessage(this.obtainMessage(2))) {
            throw new RuntimeException("Could not send handler message");
          }
        }
      }
    }

    @Override public void handleMessage(Message msg) {
      boolean rescheduled;
      long started;
      Queue timeInMethod;
      long timeInMethod1;
      if (msg.what == 1) {
        rescheduled = false;

        try {
          started = SystemClock.uptimeMillis();

          do {
            Runnable post1 = (Runnable) this.mAsyncPool.poll();
            if (post1 == null) {
              timeInMethod = this.mAsyncPool;
              synchronized (this.mAsyncPool) {
                post1 = (Runnable) this.mAsyncPool.poll();
                if (post1 == null) {
                  this.isAsyncActive = false;
                  return;
                }
              }
            }

            post1.run();
            timeInMethod1 = SystemClock.uptimeMillis() - started;
          } while (timeInMethod1 < (long) this.mMaxMillisInsideHandleMessage);

          if (!this.sendMessage(this.obtainMessage(1))) {
            throw new RuntimeException("Could not send handler message");
          }

          rescheduled = true;
        } finally {
          this.isAsyncActive = rescheduled;
        }
      } else if (msg.what == 2) {
        rescheduled = false;

        try {
          started = SystemClock.uptimeMillis();

          do {
            KitThread.UISyncPost post = (KitThread.UISyncPost) this.mSyncPool.poll();
            if (post == null) {
              timeInMethod = this.mSyncPool;
              synchronized (this.mSyncPool) {
                post = (KitThread.UISyncPost) this.mSyncPool.poll();
                if (post == null) {
                  this.isSyncActive = false;
                  return;
                }
              }
            }

            post.run();
            timeInMethod1 = SystemClock.uptimeMillis() - started;
          } while (timeInMethod1 < (long) this.mMaxMillisInsideHandleMessage);

          if (!this.sendMessage(this.obtainMessage(2))) {
            throw new RuntimeException("Could not send handler message");
          }

          rescheduled = true;
        } finally {
          this.isSyncActive = rescheduled;
        }
      } else {
        super.handleMessage(msg);
      }
    }
  }

  static final class UISyncPost {
    private Runnable mRunnable;
    private boolean isEnd = false;

    UISyncPost(Runnable runnable) {
      this.mRunnable = runnable;
    }

    public void run() {
      if (!this.isEnd) {
        synchronized (this) {
          if (!this.isEnd) {
            this.mRunnable.run();
            this.isEnd = true;

            try {
              this.notifyAll();
            } catch (Exception var4) {
              var4.printStackTrace();
            }
          }
        }
      }
    }

    public void waitRun() {
      if (!this.isEnd) {
        synchronized (this) {
          if (!this.isEnd) {
            try {
              this.wait();
            } catch (InterruptedException var4) {
              var4.printStackTrace();
            }
          }
        }
      }
    }

    public void waitRun(int time, boolean cancel) {
      if (!this.isEnd) {
        synchronized (this) {
          if (!this.isEnd) {
            try {
              this.wait((long) time);
            } catch (InterruptedException var10) {
              var10.printStackTrace();
            } finally {
              if (!this.isEnd && cancel) {
                this.isEnd = true;
              }
            }
          }
        }
      }
    }
  }
}
