package com.android.mvp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import com.android.mvp.adapter.CityAdapter;
import com.android.mvp.db.User;
import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import java.util.ArrayList;
import java.util.List;
import mvp.android.com.mvplib.base.BaseDetailActivity;
import mvp.android.com.mvplib.log.KLog;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/24 0024  下午 3:25
 * 修改历史：
 * ================================================
 */

public class RealmDBActivity extends BaseDetailActivity {

  private Realm realm;
  private CityAdapter cityAdapter;
  private List<User> list = new ArrayList<>();

  private RealmResults<User> puppies;

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    ButterKnife.bind(this);
    realm = Realm.getDefaultInstance();
    puppies = realm.where(User.class).findAllAsync();
    cityAdapter = new CityAdapter(list, mContext);
    ((ListView) findViewById(R.id.realm_list)).setAdapter(cityAdapter);
    if (puppies.isLoaded()) {
      list.addAll(puppies);
      cityAdapter.notifyDataSetChanged();
    }
    puppies.addChangeListener(new RealmChangeListener<RealmResults<User>>() {
      @Override public void onChange(RealmResults<User> element) {
        list.clear();
        list.addAll(element);
        cityAdapter.notifyDataSetChanged();
      }
    });
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_realm;
  }

  @Override protected String getTopTitle() {
    return "realmDB";
  }

  public void in(View view) {
    if (view.getId() == R.id.input) {
      RealmAsyncTask task = realm.executeTransactionAsync(new Realm.Transaction() {
        @Override public void execute(Realm realm) {
          User u = realm.createObject(User.class);
          u.setName(((EditText) findViewById(R.id.name)).getText().toString());
          u.setAge(Integer.parseInt(((EditText) findViewById(R.id.age)).getText().toString()));
        }
      }, new Realm.Transaction.OnSuccess() {
        @Override public void onSuccess() {
          KLog.e("OnSuccess");
        }
      }, new Realm.Transaction.OnError() {
        @Override public void onError(Throwable error) {
          KLog.e("OnError");
        }
      });
    }
    if (view.getId() == R.id.out) {
      RealmResults<User> uList = realm.where(User.class)
          .equalTo("name", "john")
          .findAll()
          .where()
          .equalTo("age", 20)
          .findAll();
      for (User u : uList) {
        KLog.e(uList.toString());
      }
    }
    if (view.getId() == R.id.amend) {
      RealmResults<User> uList = realm.where(User.class).equalTo("name", "john").findAll();
      for (User user : uList) {
        KLog.e(user.toString());
        realm.beginTransaction();
        user.setAge(85);
        realm.commitTransaction();
      }
    }
    if (view.getId() == R.id.del) {
      final RealmResults<User> uList = realm.where(User.class).equalTo("name", "john").findAll();
      realm.executeTransaction(new Realm.Transaction() {
        @Override public void execute(Realm realm) {
          uList.deleteFirstFromRealm();
        }
      });
    }
  }
}
