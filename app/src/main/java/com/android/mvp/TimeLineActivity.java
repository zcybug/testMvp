package com.android.mvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.mvp.timeline.TimeAdapter;
import com.android.mvp.timeline.TimeComparator;
import com.android.mvp.timeline.TimeData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/7/25 0025  上午 9:52
 * 修改历史：
 * ================================================
 */

public class TimeLineActivity  extends AppCompatActivity {

    //存储列表数据
    List<TimeData> list = new ArrayList<>();
    TimeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        RecyclerView rlView = (RecyclerView) findViewById(R.id.activity_rlview);

        //初始化数据
        initData();
        // 将数据按照时间排序
        TimeComparator comparator = new TimeComparator();
        Collections.sort(list, comparator);
        // recyclerview绑定适配器
        rlView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TimeAdapter(this, list);
        rlView.setAdapter(adapter);


    }

    private void initData() {
        list.add(new TimeData("20170710", "我是第一个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第一个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第二个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第三个数据"));
        list.add(new TimeData("20140709", "我是多数据模块第一个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第二个数据"));
        list.add(new TimeData("20140708", "我是多数据模块第三个数据"));
        list.add(new TimeData("20140706", "我是最后一个数据"));
    }
}
