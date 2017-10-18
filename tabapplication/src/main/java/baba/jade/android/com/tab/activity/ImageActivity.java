package baba.jade.android.com.tab.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import baba.jade.android.com.tab.R;
import baba.jade.android.com.tab.pager.HackyViewPager;
import baba.jade.android.com.tab.pager.ImageViewPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/18 0018  下午 3:26
 * 修改历史：
 * ================================================
 * @author Administrator
 */

public class ImageActivity extends AppCompatActivity {

  public static String url1 =
      "http://img.shu163.com/uploadfiles/wallpaper/2010/6/2010063006111517.jpg";
  public static String url2 = "http://pic.pp3.cn/uploads//allimg/111116/11021321R-4.jpg";
  public static String url3 = "http://static.xuehu365.com/admin/image/2Q1EelZG.jpg";

  ImageViewPagerAdapter adapter;

  HackyViewPager pager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    this.getWindow()
        .setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_pic_image);

    pager = (HackyViewPager) findViewById(R.id.pager);
    List<String> list = new ArrayList<>();
    list.add(url1);
    list.add(url2);
    list.add(url3);
    adapter = new ImageViewPagerAdapter(getSupportFragmentManager(), list);
    pager.setAdapter(adapter);
  }
}
