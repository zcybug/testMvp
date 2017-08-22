package com.android.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.android.mvp.adapter.MBaseAdapter;
import com.android.mvp.model.ListViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import lib.android.com.kotlinlib.kotlinforandroid.LoginKotlinActivity;
import mvp.android.com.mvplib.mvp.MvpOkgoActivity;
import mvp.android.com.mvplib.utils.MViewHolder;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class ListViewActivity extends MvpOkgoActivity<TestPresenter, TestView> implements TestView {

    @Bind(R.id.listView)
    ListView listView;

    @OnClick(R.id.kotlin)
    public void skipKotlin() {
        startActivity(new Intent(mContext, LoginKotlinActivity.class));
    }

    private List<ListViewModel> listViewModels = new ArrayList<>();

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected TestView createView() {
        return this;
    }

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        baseToolBar.setTitleText("MVP").setLeftImage(false).showTitleRes(this, R.id.title_add);
        initList();
        ListAdapter adapter = new ListAdapter(listViewModels, this);
        listView.setAdapter(adapter);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_listview;
    }

    @Override
    protected String getTopTitle() {
        return null;
    }

    private void initList() {
        //CustomKeyboard1Activity
        listViewModels.add(new ListViewModel("CustomKeyboard1Activity"));
        //CustomKeyboardActivity
        listViewModels.add(new ListViewModel("CustomKeyboardActivity"));
        //DefineKeyboardActivity
        listViewModels.add(new ListViewModel("DefineKeyboardActivity"));
        //Key1Activity
        listViewModels.add(new ListViewModel("Key1Activity"));
        //Key2Activity
        listViewModels.add(new ListViewModel("Key2Activity"));
        //Key3Activity
        listViewModels.add(new ListViewModel("Key3Activity"));
        //Key5Activity
        listViewModels.add(new ListViewModel("Key5Activity"));
        //WebtbsActivity
        listViewModels.add(new ListViewModel("WebtbsActivity"));
        //WebActivity
        listViewModels.add(new ListViewModel("WebActivity"));
        //SpannableActivity
        listViewModels.add(new ListViewModel("SpannableActivity"));
        //MTextViewActivity
        listViewModels.add(new ListViewModel("MTextViewActivity"));
        //TextActivity
        listViewModels.add(new ListViewModel("TextActivity"));
        //TextViewActivity
        listViewModels.add(new ListViewModel("TextViewActivity"));
        //RxActivity
        listViewModels.add(new ListViewModel("RxActivity"));
        //ThreadActivity
        listViewModels.add(new ListViewModel("ThreadActivity"));
        //DrawActivity
        listViewModels.add(new ListViewModel("DrawActivity"));
        listViewModels.add(new ListViewModel("CoordinatorLayoutActivity"));
        listViewModels.add(new ListViewModel("MainActivity"));
        listViewModels.add(new ListViewModel("MainAc"));
        //TestOkMvpActivity
        listViewModels.add(new ListViewModel("TestOkMvpActivity"));
        listViewModels.add(new ListViewModel("TestMvpActivity"));
        listViewModels.add(new ListViewModel("TestToorActivity"));
        listViewModels.add(new ListViewModel("WebviewActivity"));
        listViewModels.add(new ListViewModel("AnimationActivity"));
        listViewModels.add(new ListViewModel("LayerListActivity"));
        listViewModels.add(new ListViewModel("LineActivity"));
        listViewModels.add(new ListViewModel("OvalActivity"));
        listViewModels.add(new ListViewModel("RectangleActivity"));
        listViewModels.add(new ListViewModel("RingActivity"));
        listViewModels.add(new ListViewModel("ShapeActivity"));
        listViewModels.add(new ListViewModel("UiActivity"));
        listViewModels.add(new ListViewModel("VRActivity"));
        listViewModels.add(new ListViewModel("VRVideoActivity"));
        listViewModels.add(new ListViewModel("ZXingActivity"));
        listViewModels.add(new ListViewModel("TimeLineActivity"));
        listViewModels.add(new ListViewModel("GetIP"));
        //SearchActivity
        listViewModels.add(new ListViewModel("SearchActivity"));
        //PinyinActivity
        listViewModels.add(new ListViewModel("PinyinActivity"));
        //CalendarActivity
        listViewModels.add(new ListViewModel("CalendarActivity"));
    }


    class ListAdapter extends MBaseAdapter<ListViewModel> {

        public ListAdapter(List<ListViewModel> list, Context context) {
            super(list, context);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_lsitview, null);
            }
            TextView showTv = MViewHolder.get(convertView, R.id.item_tv);
            showTv.setText(list.get(position).getName());
            showTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent intent = new Intent();
                        intent.setClassName(ListViewActivity.this, ListViewActivity.this.getPackageName() + "." + listViewModels.get(position).getName());
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return convertView;
        }
    }
}
