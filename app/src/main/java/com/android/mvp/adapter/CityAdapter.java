/*
 * Copyright 2014 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.mvp.R;
import com.android.mvp.RealmDBActivity;
import com.android.mvp.db.User;
import java.util.List;
import mvp.android.com.mvplib.utils.MViewHolder;

// This adapter is strictly to interface with the GridView and doesn't
// particular show much interesting Realm functionality.

// Alternatively from this example,
// a developer could update the getView() to pull items from the Realm.

public class CityAdapter extends MBaseAdapter<User> {

  public static final String TAG = RealmDBActivity.class.getName();

  private LayoutInflater inflater;

  private List<User> cities = null;

  public CityAdapter(List<User> list, Context context) {
    super(list, context);
    cities = list;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    if (null == convertView) {
      convertView = LayoutInflater.from(context).inflate(R.layout.item_lsitview, null);
    }
    TextView showTv = MViewHolder.get(convertView, R.id.item_tv);
    showTv.setText(
        "sessionId：" + cities.get(position).getSessionId() + "；name：" + cities.get(position)
            .getName() + "；age：" + cities.get(position).getAge());
    return convertView;
  }
}
