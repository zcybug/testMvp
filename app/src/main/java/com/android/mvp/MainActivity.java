package com.android.mvp;

import android.content.Intent;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvp.android.com.mvplib.HttpCodeStatus;
import mvp.android.com.mvplib.log.XLog;
import mvp.android.com.mvplib.model.BaseEntity;
import mvp.android.com.mvplib.model.ServerModel;
import mvp.android.com.mvplib.mvp.MvpOkgoActivity;
import mvp.android.com.mvplib.utils.DialogUtils;
import mvp.android.com.mvplib.utils.Urls;

public class MainActivity extends MvpOkgoActivity<TestPresenter, TestView> implements TestView {

    @Bind(R.id.show)
    TextView showTv;
    LinearLayout layout;
    Toolbar toolbar;

    private ShareActionProvider mShareActionProvider;

    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        ButterKnife.bind(mActivity);
//        TextView tv = new TextView(this);
//        tv.setText("3D Rotate");
//        Rotate3d rotate = new Rotate3d();
//        rotate.setDuration(1000);
//        tv.measure(0, 0);
//        rotate.setCenter(tv.getMeasuredWidth() / 2, tv.getMeasuredHeight() / 2);
//        rotate.setFillAfter(true); // 使动画结束后定在最终画面，如果不设置为true，则将会回到初始画面
//        tv.startAnimation(rotate);
//        setContentView(tv);

//        setTitleName(getResources().getString(R.string.add));//title
////                  setTitleBack(false,R.mipmap.ic_search);//有图标，但不是返回
//        setTitleBack(true, 0);//返回
//        setTitleRightText("保存");//右侧文字
        baseToolBar.setLeftImage(true).setRightText("我我的").showTitleRes(this, R.id.menu_item_share);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected String getTopTitle() {
        return "Main";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected TestPresenter createPresenter() {
        return new TestPresenter();
    }

    @Override
    protected TestView createView() {
        return this;
    }

    @OnClick(R.id.pos)
    public void post() {
        presenter.setRequestId(HttpCodeStatus.HTTP_REQUEST_CODE_102, 1);
        presenter.postServerModel(Urls.URL_JSONOBJECT, this);
//        presenter.postData(Urls.URL_JSONOBJECT, this);
    }

    @Override
    public <T> void success(HttpCodeStatus requestCode, int loadId, T data) {
        super.success(requestCode, loadId, data);
        ServerModel serverModel = (ServerModel) data;
        XLog.e(serverModel.ip + "ip==" + serverModel.toString());
        showTv.setText(serverModel.toString());
    }

    @Override
    public void success(HttpCodeStatus requestCode, int loadId, BaseEntity data) {
        super.success(requestCode, loadId, data);
        ServerModel serverModel = JSON.parseObject(data.data, ServerModel.class);
        XLog.e(serverModel.ip + "ip==" + serverModel.toString());
        showTv.setText(serverModel.toString());
    }

    class Rotate3d extends Animation {
//    private float mCenterX = 0;
//    private float mCenterY = 0;
//    public void setCenter(float centerX, float centerY) {
//        mCenterX = centerX;
//        CenterY = centerY;
//    }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            Matrix matrix = t.getMatrix();
            Camera camera = new Camera();
            camera.save();
            camera.rotateY(180 * interpolatedTime);
            camera.getMatrix(matrix);
            camera.restore();
//        matrix.preTranslate(-mCenterX, -mCenterY);
//        matrix.postTranslate(mCenterX, mCenterY);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.title_add) {
            DialogUtils.getInstance().showToast(this, "添加");
            return true;
        }
        if (item.getItemId() == R.id.menu_item_share) {
            DialogUtils.getInstance().showToast(this, "分享");
            Intent myShareIntent = new Intent(Intent.ACTION_SEND);
            myShareIntent.setType("image/*");
//            myShareIntent.putExtra(Intent.EXTRA_STREAM, );
            setShareIntent(myShareIntent);
            return true;
        }
        return super.onMenuItemClick(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return super.onCreateOptionsMenu(menu);
    }

    private void setShareIntent(Intent shareIntent) {
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }
}
