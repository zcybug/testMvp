package baba.jade.android.com.tab;

import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import baba.jade.android.com.tab.fragment.ButtonFragment;
import baba.jade.android.com.tab.fragment.TestFragment;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  private ViewPager mPager;
  private ArrayList<Fragment> fragmentList;
  private ImageView image;
  private TextView view1, view2, view3, view4;
  private int currIndex;//当前页卡编号
  private int bmpW;//横线图片宽度
  private int offset;//图片移动的偏移量

  /**
   * @param buttonClickedListener 写一个对外公开的方法
   */
  private ActivityToFragment buttonClickedListener;

  public void onClicked(ActivityToFragment buttonClickedListener) {
    this.buttonClickedListener = buttonClickedListener;
  }

  public interface ActivityToFragment {
    void sendMsgFragment(String defSortMode);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_tab);
    InitTextView();
    InitImage();
    InitViewPager();
  }

  /*
   * 初始化标签名
   */
  public void InitTextView() {
    view1 = (TextView) findViewById(R.id.tv_guid1);
    view2 = (TextView) findViewById(R.id.tv_guid2);
    view3 = (TextView) findViewById(R.id.tv_guid3);
    view4 = (TextView) findViewById(R.id.tv_guid4);

    view1.setOnClickListener(new txListener(0));
    view2.setOnClickListener(new txListener(1));
    view3.setOnClickListener(new txListener(2));
    view4.setOnClickListener(new txListener(3));

    TextView classify = (TextView) findViewById(R.id.tv_classify);
    TextView center = (TextView) findViewById(R.id.tv_center);
    TextView search = (TextView) findViewById(R.id.tv_search);
    classify.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(MainActivity.this, "分类", Toast.LENGTH_LONG).show();
        if (null != buttonClickedListener) buttonClickedListener.sendMsgFragment("分类");
      }
    });
    center.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(MainActivity.this, "中间", Toast.LENGTH_LONG).show();
        if (null != buttonClickedListener) buttonClickedListener.sendMsgFragment("中间");
      }
    });
    search.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        Toast.makeText(MainActivity.this, "搜索", Toast.LENGTH_LONG).show();
        if (null != buttonClickedListener) buttonClickedListener.sendMsgFragment("搜索");
      }
    });
  }

  public class txListener implements View.OnClickListener {
    private int index = 0;

    public txListener(int i) {
      index = i;
    }

    @Override public void onClick(View v) {
      // TODO Auto-generated method stub
      mPager.setCurrentItem(index);
    }
  }

  /*
   * 初始化图片的位移像素
   */
  public void InitImage() {
    image = (ImageView) findViewById(R.id.cursor);
    bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.cursor).getWidth();
    DisplayMetrics dm = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(dm);
    int screenW = dm.widthPixels;
    offset = (screenW / 4 - bmpW) / 2;

    //imgageview设置平移，使下划线平移到初始位置（平移一个offset）
    Matrix matrix = new Matrix();
    matrix.postTranslate(offset, 0);
    image.setImageMatrix(matrix);
  }

  /*
   * 初始化ViewPager
   */
  public void InitViewPager() {
    mPager = (ViewPager) findViewById(R.id.viewpager);
    fragmentList = new ArrayList<Fragment>();
    Fragment btFragment = new ButtonFragment();
    Fragment secondFragment = TestFragment.newInstance("this is second fragment");
    Fragment thirdFragment = TestFragment.newInstance("this is third fragment");
    Fragment fourthFragment = TestFragment.newInstance("this is fourth fragment");
    fragmentList.add(btFragment);
    fragmentList.add(secondFragment);
    fragmentList.add(thirdFragment);
    fragmentList.add(fourthFragment);

    //给ViewPager设置适配器
    mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));
    mPager.setCurrentItem(0);//设置当前显示标签页为第一页
    mPager.addOnPageChangeListener(new MyOnPageChangeListener());//页面变化时的监听器
  }

  public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {
    private int one = offset * 2 + bmpW;//两个相邻页面的偏移量

    @Override public void onPageScrolled(int arg0, float arg1, int arg2) {
      // TODO Auto-generated method stub

    }

    @Override public void onPageScrollStateChanged(int arg0) {
      // TODO Auto-generated method stub

    }

    @Override public void onPageSelected(int arg0) {
      // TODO Auto-generated method stub
      Animation animation = new TranslateAnimation(currIndex * one, arg0 * one, 0, 0);//平移动画
      currIndex = arg0;
      animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
      animation.setDuration(200);//动画持续时间0.2秒
      image.startAnimation(animation);//是用ImageView来显示动画的
      int i = currIndex + 1;
      Toast.makeText(MainActivity.this, "您选择了第" + i + "个页卡", Toast.LENGTH_SHORT).show();
    }
  }

  public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
      super(fm);
      this.list = list;
    }

    @Override public int getCount() {
      return list.size();
    }

    @Override public Fragment getItem(int arg0) {
      return list.get(arg0);
    }
  }
}
