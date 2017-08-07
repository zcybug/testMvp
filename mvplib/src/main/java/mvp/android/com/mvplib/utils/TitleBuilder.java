package mvp.android.com.mvplib.utils;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mvp.android.com.mvplib.R;
import mvp.android.com.mvplib.log.XLog;

/**
 * 标题栏构造器,使用方法 new TitleBuilder().setMethod().setMethod()......
 * <p/>
 * 统一格式为标题文字,左右各自是文字/图片按钮
 * 按钮都默认不显示,只有在你调用setLeftText时才会显示左侧按钮文字,图片同理
 * 图片或文字的点击事件都用Left/RightOnClickListener
 */
public class TitleBuilder {

    private Toolbar rootView;
    private TextView tvTitle;
    private ImageView ivLeft;
    private TextView tvRight;

    public Toolbar getRootView() {
        return rootView;
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public ImageView getIvLeft() {
        return ivLeft;
    }

    public TextView getTvRight() {
        return tvRight;
    }

    /**
     * Activity中使用这个构造方法
     */
    public TitleBuilder(Toolbar toolbar) {
//        rootView = (Toolbar) context.findViewById(R.id.toolbar);
        if (null == toolbar) {
            XLog.e("toolbar is null");
            return;
        }
        rootView = toolbar;
        tvTitle = (TextView) rootView.findViewById(R.id.title_name);
        ivLeft = (ImageView) rootView.findViewById(R.id.title_back);
        tvRight = (TextView) rootView.findViewById(R.id.title_rightTv);
    }

    /**
     * Fragment中使用这个构造方法
     */
    public TitleBuilder(View context) {
        rootView = (Toolbar) context.findViewById(R.id.toolbar);
        if (rootView == null) {
            XLog.e("toolbar is null");
            return;
        }
        tvTitle = (TextView) rootView.findViewById(R.id.title_name);
        ivLeft = (ImageView) rootView.findViewById(R.id.title_back);
        tvRight = (TextView) rootView.findViewById(R.id.title_rightTv);
    }

    // title
    public TitleBuilder setTitleBgRes(int resid) {
        rootView.setBackgroundResource(resid);
        return this;
    }

    public TitleBuilder setTitleText(String text) {
        tvTitle.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
        tvTitle.setText(text);
        return this;
    }

    // left
    public TitleBuilder setLeftImage(int resId) {
        ivLeft.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
        ivLeft.setImageResource(resId);
        return this;
    }

    public TitleBuilder setLeftImage(boolean isShow) {
        ivLeft.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return this;
    }

//    public TitleBuilder setLeftText(String text) {
//        tvLeft.setVisibility(TextUtils.isEmpty(text) ? View.GONE : View.VISIBLE);
//        tvLeft.setText(text);
//        return this;
//    }

//    public TitleBuilder setLeftOnClickListener(OnClickListener listener) {
//        if (ivLeft.getVisibility() == View.VISIBLE) {
//            ivLeft.setOnClickListener(listener);
//        } else if (tvLeft.getVisibility() == View.VISIBLE) {
//            tvLeft.setOnClickListener(listener);
//        }
//        return this;
//    }

    // right
//    public TitleBuilder setRightImage(int resId) {
//        ivRight.setVisibility(resId > 0 ? View.VISIBLE : View.GONE);
//        ivRight.setImageResource(resId);
//        return this;
//    }

    public TitleBuilder setRightText(String text) {
        tvRight.setVisibility(TextUtils.isEmpty(text) ? View.GONE
                : View.VISIBLE);
        tvRight.setText(text);
        return this;
    }

    public TitleBuilder setRightTextColor(Context context, int resId) {
        tvRight.setTextColor(context.getResources().getColor(resId));
        return this;
    }

    public TitleBuilder setTitleTextColor(Context context, int resId) {
        tvTitle.setTextColor(context.getResources().getColor(resId));
        return this;
    }


//    public TitleBuilder setRightOnClickListener(OnClickListener listener) {
//        if (ivRight.getVisibility() == View.VISIBLE) {
//            ivRight.setOnClickListener(listener);
//        } else if (tvRight.getVisibility() == View.VISIBLE) {
//            tvRight.setOnClickListener(listener);
//        }
//        return this;
//    }

    public Toolbar build() {
        return rootView;
    }

    /**
     * title右侧:图标类
     */
    private TitleBuilder setRightRes(Toolbar.OnMenuItemClickListener onMenuItemClickListener) {
        if (null != onMenuItemClickListener) {
            //扩展menu
            rootView.inflateMenu(R.menu.base_toolbar_menu);
            //添加监听
            rootView.setOnMenuItemClickListener(onMenuItemClickListener);
        } else {
            XLog.e("onMenuItemClickListener is null");
        }
        return this;
    }

    /**
     * 显示title图标
     *
     * @param itemId :itemId :图标对应的选项id（1个到3个）,最多显示3两个
     */
    public TitleBuilder showTitleRes(Toolbar.OnMenuItemClickListener onMenuItemClickListener, int... itemId) {
        if (null == onMenuItemClickListener) {
            XLog.e("onMenuItemClickListener is null");
            return this;
        }
        setRightRes(onMenuItemClickListener);
        for (int item : itemId) {
            //显示
            rootView.getMenu().findItem(item).setVisible(true);//通过id查找,也可以用setIcon()设置图标
//            toolBar.getMenu().getItem(0).setVisible(true);//通过位置查找
        }
        return this;
    }

    /**
     * 隐藏title图标
     *
     * @param itemId :图标对应的选项id
     */
    public TitleBuilder goneTitleRes(int... itemId) {
        for (int item : itemId) {
            //隐藏
            rootView.getMenu().findItem(item).setVisible(false);
        }
        return this;
    }

}
