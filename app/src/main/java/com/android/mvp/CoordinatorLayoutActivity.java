package com.android.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
import mvp.android.com.mvplib.utils.MViewHolder;

/**
 * Created by Administrator on 2017/7/6 0006.
 */

public class CoordinatorLayoutActivity extends AppCompatActivity {

    @Bind(R.id.coor_listview)
    ListView listView;
    private List<ListViewModel> listViewModels = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinator_layout);
        ButterKnife.bind(this);
        initList();
        ListAdapter adapter = new ListAdapter(listViewModels, this);
        listView.setAdapter(adapter);
    }

    private void initList() {
        listViewModels.add(new ListViewModel("CoordinatorLayoutActivity"));
        listViewModels.add(new ListViewModel("MainActivity"));
        listViewModels.add(new ListViewModel("MainAc"));
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
            return convertView;
        }
    }
}
