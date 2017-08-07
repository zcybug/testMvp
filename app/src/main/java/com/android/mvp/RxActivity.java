package com.android.mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import mvp.android.com.mvplib.log.XLog;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/1 0001  下午 2:24
 * 修改历史：
 * ================================================
 */

public class RxActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
//        testFrom();
//        test();
//        testJust();
    }


    private void test() {
        Observable<Integer> observableString = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                for (int i = 0; i < 5; i++) {
                    observer.onNext(i);
                }
                observer.onCompleted();
            }
        });

        Subscription subscriptionPrint = observableString.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                XLog.e("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                XLog.e("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Integer item) {
                XLog.e("Item is " + item);
            }
        });
    }

    private void testFrom() {
        List<Integer> items = new ArrayList<Integer>();
        items.add(1);
        items.add(10);
        items.add(100);
        items.add(200);
        Observable<Integer> observableString = Observable.from(items);
        Subscription subscriptionPrint = observableString.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                XLog.e("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                XLog.e("Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Integer item) {
                XLog.e("Item is " + item);
            }
        });
    }

    private void testJust() {
        Observable<String> observableString = Observable.just(helloWorld());
        Subscription subscriptionPrint = observableString.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                XLog.e("Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                XLog.e("Oh,no! Something wrong happened!");
            }

            @Override
            public void onNext(String message) {
                XLog.e(message);
            }
        });
    }

    private String helloWorld() {
        return "Hello World";
    }

}
