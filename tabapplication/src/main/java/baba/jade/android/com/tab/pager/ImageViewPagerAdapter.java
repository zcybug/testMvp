package baba.jade.android.com.tab.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import baba.jade.android.com.tab.fragment.ImageFragment;
import java.util.List;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/18 0018  下午 3:23
 * 修改历史：
 * ================================================
 */

public class ImageViewPagerAdapter extends FragmentStatePagerAdapter {

  private static final String IMAGE_URL = "image";

  private List<String> mDatas;
  private int defPosition = -1;

  public ImageViewPagerAdapter(FragmentManager fm, List data) {
    super(fm);
    mDatas = data;
  }

  public void setPosition(int position) {
    this.defPosition = position;
  }

  @Override public Fragment getItem(int position) {
    String url = mDatas.get(position);
    if (-1 != defPosition && defPosition < mDatas.size()) {
      url = mDatas.get(defPosition);
    }
    Fragment fragment = ImageFragment.newInstance(url);
    return fragment;
  }

  @Override public int getCount() {
    return mDatas.size();
  }
}


