package zcy.android.com.realmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mvp.android.com.mvplib.utils.MViewHolder;
import zcy.android.com.realmapp.struct.FunctionNoParamNoResult;
import zcy.android.com.realmapp.struct.FunctionsManager;

public class MainActivity extends AppCompatActivity {
  List<ListViewModel> listViewModels = new ArrayList<>();

  @Bind(R.id.list) ListView listView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list);
    ButterKnife.bind(this);
    initList();
    ((ListView) findViewById(R.id.list)).setAdapter(new MListAdapter());
  }

  private void initList() {
    listViewModels.add(new ListViewModel("测试"));
    listViewModels.add(new ListViewModel("测试"));
    listViewModels.add(new ListViewModel("测试"));
  }

  private class MListAdapter extends BaseAdapter {

    @Override public int getCount() {
      return null == listViewModels ? 0 : listViewModels.size();
    }

    @Override public Object getItem(int position) {
      return listViewModels.get(position);
    }

    @Override public long getItemId(int position) {
      return position;
    }

    @Override public View getView(final int position, View convertView, ViewGroup parent) {
      if (null == convertView) {
        convertView =
            LayoutInflater.from(MainActivity.this).inflate(R.layout.item_lsitview, parent, false);
      }
      TextView showTv = MViewHolder.get(convertView, R.id.item_tv);
      showTv.setText(listViewModels.get(position).getName());
      showTv.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          try {
            Intent intent = new Intent();
            intent.setClassName(MainActivity.this,
                MainActivity.this.getPackageName() + "." + listViewModels.get(position).getName());
            startActivity(intent);
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      });
      return convertView;
    }
  }

  public void setFunctionForFragment(String tag){
    FragmentManager fm=getSupportFragmentManager();
    BaseFragment fragment= (BaseFragment) fm.findFragmentByTag(tag);
    FunctionsManager fmanager=FunctionsManager.getInstance();
    fragment.setmFunctionManager(fmanager.addFunction(new FunctionNoParamNoResult(BlankFragment1.INTERFACE) {
      @Override
      public void funtion() {
        Toast.makeText(MainActivity.this,"成功调用无参无返回值的接口",Toast.LENGTH_LONG).show();
      }
    }));
  }
}
