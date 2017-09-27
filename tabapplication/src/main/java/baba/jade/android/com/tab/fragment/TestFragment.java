package baba.jade.android.com.tab.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import baba.jade.android.com.tab.MainActivity;
import baba.jade.android.com.tab.R;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/9/27 0027  上午 9:04
 * 修改历史：
 * ================================================
 */

public class TestFragment extends Fragment {
  private static final String TAG = "TestFragment";
  private String hello;// = "hello android";
  private String defaultHello = "default value";
  private View view;
  private TextView viewhello;

  public static TestFragment newInstance(String s) {
    TestFragment newFragment = new TestFragment();
    Bundle bundle = new Bundle();
    bundle.putString("hello", s);
    newFragment.setArguments(bundle);
    //bundle还可以在每个标签里传送数据
    return newFragment;
  }

  MainActivity mainActivity;

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof MainActivity) {
      mainActivity = (MainActivity) context;
      mainActivity.onClicked(new MainActivity.ActivityToFragment() {
        @Override public void sendMsgFragment(String defSortMode) {
          Log.e("defSortMode==", defSortMode);
          defaultHello = defSortMode;
        }
      });
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Log.d(TAG, "TestFragment-----onCreateView");
    Bundle args = getArguments();
    hello = args != null ? args.getString("hello") : defaultHello;
    view = inflater.inflate(R.layout.guide_2, container, false);
    viewhello = (TextView) view.findViewById(R.id.tv);
    viewhello.setText(hello);
    return view;
  }

  @Override public void setUserVisibleHint(boolean isVisibleToUser) {
    super.setUserVisibleHint(isVisibleToUser);
    Log.e("===", "" + getUserVisibleHint());
    if (getUserVisibleHint()) {
      viewhello.setText(defaultHello);
    } else {
    }
  }
}
