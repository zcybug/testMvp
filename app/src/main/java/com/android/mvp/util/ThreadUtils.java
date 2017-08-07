package com.android.mvp.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/1 0001  上午 8:53
 * 修改历史：http://mp.weixin.qq.com/s?__biz=MzU0NTA2OTA2MA==&mid=2247484183&idx=1&sn=cda760b731eae6a1f4be0175c94923b5&chksm=fb73c27ccc044b6a0b57dfd560976dd67e0972fcd1a10c9f2d0179b3cf54efeefdc74b1a5f6d&mpshare=1&scene=23&srcid=0801JKQAG2bTcgxybW59VHaX#rd
 * ================================================
 */

public class ThreadUtils {
    /**
     * 1、newFixedThreadPool() ：
     * 作用：该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，
     * 也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。
     * 栗子：假如有一个新任务提交时，线程池中如果有空闲的线程则立即使用空闲线程来处理任务，如果没有，
     * 则会把这个新任务存在一个任务队列中，一旦有线程空闲了，则按FIFO方式处理任务队列中的任务。
     * 2、newCachedThreadPool() ：
     * 作用：该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。
     * 即该线程池中的线程数量不确定，是根据实际情况动态调整的。
     * 栗子：假如该线程池中的所有线程都正在工作，而此时有新任务提交，那么将会创建新的线程去处理该任务，
     * 而此时假如之前有一些线程完成了任务，现在又有新任务提交，那么将不会创建新线程去处理，
     * 而是复用空闲的线程去处理新任务。那么此时有人有疑问了，那这样来说该线程池的线程岂不是会越集越多？
     * 其实并不会，因为线程池中的线程都有一个“保持活动时间”的参数，通过配置它，
     * 如果线程池中的空闲线程的空闲时间超过该“保存活动时间”则立刻停止该线程，
     * 而该线程池默认的“保持活动时间”为60s。
     * 3、newSingleThreadExecutor() ：
     * 作用：该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，
     * 多余的任务会保存到一个任务队列中，等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。
     * 4、newScheduledThreadPool() ：
     * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
     * 5、newSingleThreadScheduledExecutor() ：
     * 作用：该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。
     * 只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小。
     */

    public void testFixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 20; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void testSingleThreadPool() {
        ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void testCachedThreadPool() {
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void testScheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);        //延迟2秒后执行该任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 2, TimeUnit.SECONDS);        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 2, TimeUnit.SECONDS);
    }


    public void testSingleThreadScheduledPool() {
        ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                System.out.println("线程：" + threadName + ",正在执行");
            }
        }, 1, 2, TimeUnit.SECONDS);
    }

    public void testPriorityThreadPool() {
        ExecutorService priorityThreadPool = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 1; i <= 10; i++) {
            final int priority = i;
            priorityThreadPool.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    String threadName = Thread.currentThread().getName();
                    System.out.println("线程：" + threadName + ",正在执行优先级为：" + priority + "的任务");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public static void main(String[] args) {
        ThreadUtils utils = new ThreadUtils();
//        utils.testFixedThreadPool();
//        utils.testSingleThreadPool();
//        utils.testCachedThreadPool();
//        utils.testScheduledThreadPool();
//        utils.testSingleThreadScheduledPool();
        utils.testPriorityThreadPool();
    }
}
