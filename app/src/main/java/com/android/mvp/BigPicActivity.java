package com.android.mvp;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.mvp.widge.pic.CommonUtil;
import com.android.mvp.widge.pic.GalleryView;
import com.android.mvp.widge.pic.PhotoViewPager;
import com.android.mvp.widge.pic.TransBigImageView;
import com.android.mvp.widge.pic.TransSmallImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import java.util.ArrayList;
import java.util.List;
import mvp.android.com.mvplib.base.BaseDetailActivity;
import uk.co.senab.photoview.PhotoViewAttacher;

import static com.android.mvp.widge.pic.CommonUtil.dip2px;

/**
 * ================================================
 * 项目名称：ImageGalleryOptimize-master
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/12 0012  上午 9:51
 * 修改历史：
 * ================================================
 */

public class BigPicActivity extends BaseDetailActivity {

  GalleryView galleryView;
  private PhotoViewPager pager;
  private TransSmallImageView mTransSmallImageView;
  private TransBigImageView transBigImageView;
  private FrameLayout previewParent;
  private int position;
  private int gridPosition; //瀑布流点击的那个位置
  private ViewPagerAdapter mAdapter;

  private List<String> picList = new ArrayList<>();
  private List<String> mUrlList = new ArrayList<>();
  private List<Integer> mImgWidthList = new ArrayList<>();
  private List<Integer> mImgHeightList = new ArrayList<>();
  final int[] img = new int[] {
      R.mipmap.xxx0, R.mipmap.xxx1, R.mipmap.xxx2, R.mipmap.xxx3, R.mipmap.xxx4, R.mipmap.xxx5,
      R.mipmap.xxx6, R.mipmap.xxx7, R.mipmap.xxx8, R.mipmap.xxx9, R.mipmap.xxx10, R.mipmap.xxx11,
      R.mipmap.xxx12, R.mipmap.xxx13, R.mipmap.xxx14, R.mipmap.xxx15, R.mipmap.xxx16,
      R.mipmap.xxx17, R.mipmap.xxx18, R.mipmap.xxx19, R.mipmap.xxx20, R.mipmap.xxx21,
      R.mipmap.xxx22, R.mipmap.xxx23, R.mipmap.xxx24, R.mipmap.xxx25, R.mipmap.ffg, R.mipmap.ffh,
      R.mipmap.ffj
  };

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    galleryView = (GalleryView) findViewById(R.id.gallery);
    pager = (PhotoViewPager) findViewById(R.id.preview_viewPager);
    mTransSmallImageView = (TransSmallImageView) findViewById(R.id.transImage);
    previewParent = (FrameLayout) findViewById(R.id.previewParent);
    transBigImageView = (TransBigImageView) findViewById(R.id.transBigImage);
    loadData();
    init();
  }

  @Override protected int getLayoutView() {
    return R.layout.activity_big;
  }

  @Override protected String getTopTitle() {
    return "查看大图";
  }

  private void loadData() {
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/1.png");
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/2.png");
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/3.png");
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/4.png");
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/5.png");
    picList.add("http://7xk9dj.com1.z0.glb.clouddn.com/banner/imgs/6.png");
  }

  private void init() {
    //BitmapFactory.Options opts = new BitmapFactory.Options();
    //opts.inJustDecodeBounds = true;
    //opts.inSampleSize = 1;
    ////opts.inJustDecodeBounds = false;
    //for (int i = 0; i < picList.size(); i++) {
    //  BitmapFactory.decodeResource(getResources(), img[i], opts);
    //  int width = opts.outWidth;
    //  int height = opts.outHeight;
    //  mImgWidthList.add(width / 3);
    //  mImgHeightList.add(height / 3);
    //  mUrlList.add(picList.get(i));
    //}
    int width = CommonUtil.getScreenWidth(mContext);
    int height = CommonUtil.getScreenHeight(mContext);
    for (int i = 0; i < picList.size(); i++) {
      mImgWidthList.add(width / 3);
      mImgHeightList.add(width / 3);
      mUrlList.add(picList.get(i));
    }
    if (mUrlList.size() == 0) {
      galleryView.setLoadMoreEnable(false);
      return;
    }
    //初始化GalleryView
    for (int i = 0; i < mUrlList.size(); i++) {
      ImageView imageView = new ImageView(mContext);
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      galleryView.addView(imageView);
    }
    galleryView.init(mImgWidthList, mImgHeightList, (int) dip2px(this, 4), (int) dip2px(this, 4), (int) dip2px(this, 60));
    //当最后一个子View绘制完成后，加载子View的图片
    galleryView.getChildAt(mUrlList.size()).post(new Runnable() {
      @Override public void run() {
        for (int i = 0; i < mUrlList.size(); i++) {
          //指定Gallery中的加载的图片大小，防止OOM
          int w = galleryView.getChildAt(i).getWidth();
          int h = galleryView.getChildAt(i).getHeight();
          Glide.with(mContext)
              .load(mUrlList.get(i))
              .override(w, h)
              .into((ImageView) galleryView.getChildAt(i));

          if (mAdapter == null) {
            mAdapter = new ViewPagerAdapter(mContext, mUrlList);
            pager.setAdapter(mAdapter);
          } else {
            mAdapter.notifyDataSetChanged();
          }
        }
      }
    });


    mTransSmallImageView.setExitEnd(new TransSmallImageView.ExitEnd() {
      @Override
      public void end() {
        galleryView.getChildAt(gridPosition).setVisibility(View.VISIBLE);
        previewParent.setVisibility(View.GONE);
      }
    });
    galleryView.setItemClickListener(new GalleryView.ItemClickListener() {
      @Override
      public void click(int x, int y, float width, float height, final int position) {
        gridPosition = position;
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            galleryView.getChildAt(position).setVisibility(View.INVISIBLE);
          }
        }, 100);
        galleryItemClick(x, y, width, height, position);
      }
    });
    pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      }

      @Override
      public void onPageSelected(int position) {
        BigPicActivity.this.position = position;
        int w = galleryView.getChildAt(position).getWidth();
        int h = galleryView.getChildAt(position).getHeight();
        Glide.with(mContext).load(mUrlList.get(position))
            .override(w, h).dontAnimate().into(mTransSmallImageView);
      }

      @Override
      public void onPageScrollStateChanged(int state) {
      }
    });
    //Gallery加载更多的回调事件
  }


  private void galleryItemClick(final int x, final int y, final float width, final float height, final int position) {
    previewParent.setVisibility(View.VISIBLE);
    PointF pointF = CommonUtil.getNeedSize(mContext, width, height);
    float imgHeight = pointF.y;
    float imgWidth = pointF.x;

    Glide.with(mContext).load(mUrlList.get(position)).override((int) imgWidth, (int) imgHeight).dontAnimate().into(transBigImageView);
    transBigImageView.init(x, y, width, height);
    transBigImageView.startTrans();
    transBigImageView.setTransEnd(new TransBigImageView.TransEnd() {
      @Override
      public void end() {
        pager.setCurrentItem(position, false);
        pager.setVisibility(View.VISIBLE);
      }
    });
  }


  class ViewPagerAdapter extends PagerAdapter {
    private Context mContext;
    private List<?> list;

    public ViewPagerAdapter(Context context, List<?> list) {
      this.mContext = context;
      this.list = list;
    }

    @Override
    public int getCount() {
      return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int i) {
      ImageView imageView = new ImageView(mContext);
      container.addView(imageView);
      //指定要加载的图片大小，防止OOM
      List<Float> info = galleryView.getChildInfo(i);

      PointF pointF = CommonUtil.getNeedSize(mContext, info.get(2), info.get(3));
      float imgHeight = pointF.y;
      float imgWidth = pointF.x;

      final PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);

      //使用GalleryView子View上的图片作为占位图，不然会出现加载闪白
      galleryView.getChildAt(i).setDrawingCacheEnabled(true);
      Drawable drawable = new BitmapDrawable(galleryView.getChildAt(i).getDrawingCache());
      Glide.with(mContext).load(list.get(i)).override((int) imgWidth, (int) imgHeight)
          .dontAnimate().placeholder(drawable).into(new GlideDrawableImageViewTarget(imageView) {
        @Override
        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
          super.onResourceReady(resource, animation);
          attacher.update();
        }
      });

      attacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
        @Override
        public void onViewTap(View view, float x, float y) {
          attacher.setScale(1, false);
          TransSmallImageViewToExit(i);
        }
      });
      return imageView;
    }
  }

  public void TransSmallImageViewToExit(int position) {
    if (position != gridPosition) {
      galleryView.getChildAt(gridPosition).setVisibility(View.VISIBLE);
      galleryView.getChildAt(position).setVisibility(View.INVISIBLE);
      gridPosition = position;
    }
    pager.setVisibility(View.GONE);
    List<Float> info = galleryView.getChildInfo(position);
    mTransSmallImageView.exit((int) (info.get(0) / 1),
        (int) (info.get(1) / 1), info.get(2), info.get(3));
  }
}
