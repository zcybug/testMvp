package mvp.android.com.mvplib.base;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import mvp.android.com.mvplib.R;

/**
 * Created by Administrator on 2017/6/29 0029.
 */

public class ToorBaseActivity extends AppCompatActivity implements View.OnClickListener, Toolbar.OnMenuItemClickListener {

    /*Toolbar*/
    private Toolbar toolBar;
    /*是否第一次加载图标(主要针对首页一对多fragment)*/
    private boolean title_menu_first = true;
    /*是否第一次加载返回*/
    private boolean title_back_first = true;
    /*是否是返回(有可能是代表别的功能)*/
    private boolean is_title_back = true;
    /*返回*/
    private ImageView titleBack;
    /*标题名称*/
    private TextView titleName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setRootView();
//        coordinatorLayout = getView(R.id.coordinatorLayout);
        initToolbar();
        initWidght();
    }

    protected void setRootView() {
    }

    protected void initWidght() {
    }


    //
    protected <T extends View> T getView(int resourcesId) {
        return (T) findViewById(resourcesId);
    }/*初始化toolbar*/

    private void initToolbar() {
        toolBar = getView(R.id.toolbar);
        toolBar.setTitle("");
        toolBar.setTitleTextColor(Color.WHITE);
        titleName = getView(R.id.title_name);
//        setSupportActionBar(toolBar);
    }

    /**
     * 设置返回
     *
     * @param back        :是否返回：是-->返回，不是则设置其他图标
     * @param resourcesId :图标id,返回时随意设置，不使用
     */
    protected void setTitleBack(final boolean back, int resourcesId) {
        is_title_back = back;
        if (title_back_first || titleBack == null) {
            titleBack = getView(R.id.title_back);
            titleBack.setOnClickListener(this);
            title_back_first = false;
        }
        titleBack.setVisibility(View.VISIBLE);
        if (!back) {
            titleBack.setImageResource(resourcesId);
        }
    }

    /**
     * 设置title
     *
     * @param title ：title
     */
    protected void setTitleName(String title) {
        titleName.setText(title);
    }

    /**
     * title右侧:图标类
     */
    protected void setRightRes() {
        //扩展menu
        toolBar.inflateMenu(R.menu.base_toolbar_menu);
        //添加监听
        toolBar.setOnMenuItemClickListener(this);
    }

    /**
     * 显示title图标
     *
     * @param itemId :itemId :图标对应的选项id（1个到3个）,最多显示3两个
     */
    protected void showTitleRes(int... itemId) {
        if (title_menu_first) {
            setRightRes();
            title_menu_first = false;
        }
        for (int item : itemId) {
            //显示
            toolBar.getMenu().findItem(item).setVisible(true);//通过id查找,也可以用setIcon()设置图标
//            toolBar.getMenu().getItem(0).setVisible(true);//通过位置查找
        }
    }

    /**
     * 隐藏title图标
     *
     * @param itemId :图标对应的选项id
     */
    protected void goneTitleRes(int... itemId) {
        if (titleBack != null)
            titleBack.setVisibility(View.GONE);
        for (int item : itemId) {
            //隐藏
            toolBar.getMenu().findItem(item).setVisible(false);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back && is_title_back) {
            onBackPressed();
        }
    }

    //toolbar菜单监听
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return false;
    }

}
