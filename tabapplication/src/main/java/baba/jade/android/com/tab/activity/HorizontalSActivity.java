package baba.jade.android.com.tab.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import baba.jade.android.com.tab.R;
import baba.jade.android.com.tablib.MyHorizontalScrollView;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/9/28 0028  上午 8:58
 * 修改历史：
 * ================================================
 */

public class HorizontalSActivity extends AppCompatActivity {
  MyHorizontalScrollView mHorizontalScrollView;
  LinearLayout visibleWindow;
  TextView rec;
  ViewPager mViewPager;
  View tabline;
  int leftInvisibleNum;
  int visibleNum;
  int rightInvisibleNum;
  int pos;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hs_tab);
    mHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.id_list);
    visibleWindow = (LinearLayout) findViewById(R.id.visible_window);
    rec = (TextView) findViewById(R.id.id_rec);
    mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
    tabline = findViewById(R.id.id_tabline);

    /**
     * 设置list的回调方法，主要是计算左边未可见TextView，右边未可见TextView和可见TextView的数量
     */
    mHorizontalScrollView.setOnOverScrolledListener(
        new MyHorizontalScrollView.OnOverScrolledListener() {
          @Override
          public void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
            Toast.makeText(HorizontalSActivity.this, "Scrolling completed!", Toast.LENGTH_LONG)
                .show();
            int singleTextVieweWidth = rec.getWidth();
            leftInvisibleNum = 0;
            if (scrollX != 0) {
              //如果左边未见的TextView没有整数数量的话，就取整加1
              leftInvisibleNum =
                  scrollX / singleTextVieweWidth + (scrollX % singleTextVieweWidth == 0 ? 0 : 1);
            }
            visibleNum =
                (visibleWindow.getWidth() - scrollX % singleTextVieweWidth) / singleTextVieweWidth;
            rightInvisibleNum = 8 - leftInvisibleNum - visibleNum;
          }
        });

    //ViewPager进行页面切换的时候的监听器
    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //postion是目前所在位置，为整数：0,1,2,3,5...，positionOffset是位置偏移量，
        // positionOffsetPixels是偏移的像素。

        //设置红色底线的位置，setX是相对于父控件的X位置。（position+positionOffset）*底线宽度就
        // 是底线应该在具体位置（相对于父控件的具体位置）
        tabline.setX((float) (position * tabline.getWidth() + positionOffset * tabline.getWidth()));
        //pos是静止时的页号，或者是要去往的页号
        if (positionOffset == 0.0f) {
          pos = position;
        }
        //当要翻页时，进行判断和滑动
        if (positionOffset != 0.0f) {
          if (position < pos)//如果要去往的页面是在当前页面的左边
          {
            pos = position;
            if (pos < leftInvisibleNum)//如果去往的页面是未可见
            {
              //mHorizontalScrollView向右滑动
              mHorizontalScrollView.scrollBy(rec.getWidth() * -1, 0);
              computeScrollParm();//计算TextView的数量
            }
          } else  //如果要去往的页面是在当前页面的右边
          {
            pos++;
            if (7 - pos < rightInvisibleNum)//如果去往的页面是未可见，7为最后一个页面的页号
            {
              //mHorizontalScrollView向左滑动
              mHorizontalScrollView.scrollBy(rec.getWidth(), 0);
              computeScrollParm();
            }
          }
        }
      }

      @Override public void onPageSelected(int position) {

      }

      @Override public void onPageScrollStateChanged(int state) {

      }
    });
  }

  /**
   * visibleNum 是可见的范围内TextView的数量，rightInvisibleNum 是可见范围右边未可见的TextView的数量
   * leftInvisibleNum 是可见范围左边未可见的TextView的数量，此三变量为MainActivity的私有变量。
   * 这段代码不能放在onCreate方法里面，因为rec还没有图形界面，不能获取宽度。
   */
  @Override public void onWindowFocusChanged(boolean hasFocus) {
    super.onWindowFocusChanged(hasFocus);
    int singleTextVieweWidth = rec.getWidth();
    leftInvisibleNum = 0;
    visibleNum = visibleWindow.getWidth() / singleTextVieweWidth;
    rightInvisibleNum = 8 - visibleNum;
  }

  private void computeScrollParm() {
    int scrollX = mHorizontalScrollView.getScrollX();
    int singleTextVieweWidth = rec.getWidth();
    leftInvisibleNum = 0;
    if (scrollX != 0) {
      leftInvisibleNum =
          scrollX / singleTextVieweWidth + (scrollX % singleTextVieweWidth == 0 ? 0 : 1);
    }
    visibleNum = (visibleWindow.getWidth() - scrollX % singleTextVieweWidth) / singleTextVieweWidth;
    rightInvisibleNum = 8 - leftInvisibleNum - visibleNum;
  }
}
