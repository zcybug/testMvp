package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.mvp.util.PausableThreadPoolExecutor;
import com.android.mvp.util.PriorityRunnable;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/1 0001  上午 10:51
 * 修改历史：
 * ================================================
 */

public class ThreadActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.show)
    TextView show;
    @Bind(R.id.stop)
    Button stop;

    private boolean isPause = false;
    private PausableThreadPoolExecutor pausableThreadPoolExecutor = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        ButterKnife.bind(this);
        stop.setOnClickListener(this);
        show.setText(Runtime.getRuntime().availableProcessors() + "");//获取cpu数量 通常核心线程数可以设为CPU数量+1，而最大线程数可以设为CPU的数量*2+1。
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.stop) {//开始或暂停
            if (null == pausableThreadPoolExecutor) {
                isPause = true;
                startThread(v);
            } else {
                if (isPause) {
                    pausableThreadPoolExecutor.resume();
                    isPause = false;
                } else {
                    pausableThreadPoolExecutor.pause();
                    isPause = true;
                }
            }
        }
    }

    public void startThread(View view) {
        isPause = true;
        pausableThreadPoolExecutor = new PausableThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS, new PriorityBlockingQueue<Runnable>());
        for (int i = 1; i <= 100; i++) {
            final int priority = i;
            pausableThreadPoolExecutor.execute(new PriorityRunnable(priority) {
                @Override
                public void doSth() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show.setText(priority + "");
                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
