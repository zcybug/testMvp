package zcy.android.com.realmapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 2017/9/4.
 */

public class BlankFragment1 extends BaseFragment {

  private View view;
  public static final String INTERFACE = BlankFragment1.class.getName() + "NPNR";
  private static final String tagStr = "fragment1_str";

  public static BlankFragment1 getInstance(int tag) {
    Bundle bundle = new Bundle();
    bundle.putInt(tagStr, tag);
    BlankFragment1 contentFragment = new BlankFragment1();
    contentFragment.setArguments(bundle);
    return contentFragment;
  }

  public void onButtonPressed(String uri) {
    mFunctionManager.invokeFunction(INTERFACE);
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_layout, container, false);
    view.findViewById(R.id.fragment_btn_1).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        onButtonPressed("uri");
      }
    });
    return view;
  }

  public void setButtonListener(View view) {
    onButtonPressed("uri");
  }
}
