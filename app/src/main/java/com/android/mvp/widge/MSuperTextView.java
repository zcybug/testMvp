package com.android.mvp.widge;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.mvp.R;

import mvp.android.com.mvplib.utils.DensityUtil;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/2 0002  上午 9:34
 * 修改历史：
 * ================================================
 */

public class MSuperTextView extends RelativeLayout {

    private Context mContext;

    private Drawable liftImgId;
    private ImageView leftIconIV;
    private LayoutParams leftImgParams;
    private Drawable rightImgId;
    private ImageView rightIconIV;
    private LayoutParams rightImgParams;

    private String liftText;
    private int liftTextSize;
    private int liftTextColor;
    private LayoutParams liftTextParams;
    private TextView liftTv;

    private String rightText;
    private int rightTextSize;
    private int rightTextColor;
    private LayoutParams rightTextParams;
    private TextView rightTv;

    private int defaultColor = 0xFF373737;//文字默认颜色
    private int defaultSize = 14;//默认字体大小
    private int defaultMaxEms = 10;

    public MSuperTextView(Context context) {
        this(context, null);
    }

    public MSuperTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSuperTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        getAttrs(attrs);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.SuperTV);
        liftImgId = typedArray.getDrawable(R.styleable.SuperTV_lifeImgId);
        rightImgId = typedArray.getDrawable(R.styleable.SuperTV_rightImgId);
        liftText = typedArray.getString(R.styleable.SuperTV_liftText);
        liftTextColor = typedArray.getColor(R.styleable.SuperTV_liftTextColor, defaultColor);
        liftTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTV_liftTextSize, defaultSize);
        rightText = typedArray.getString(R.styleable.SuperTV_rightText);
        rightTextColor = typedArray.getColor(R.styleable.SuperTV_rightTextColor, defaultColor);
        rightTextSize = typedArray.getDimensionPixelSize(R.styleable.SuperTV_rightTextSize, defaultSize);
        initView();
    }

    private void initView() {
        initLeftIcon();
        initRightIcon();
        initLiftText();
        initRightText();
    }

    private void initLeftIcon() {
        if (null == leftIconIV) {
            leftIconIV = new ImageView(mContext);
        }
        leftImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        leftImgParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        leftIconIV.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        leftIconIV.setId(R.id.sLeftImgId);
        leftIconIV.setLayoutParams(leftImgParams);
        leftIconIV.setImageDrawable(liftImgId);
        leftImgParams.setMargins(DensityUtil.dip2px(mContext, 14f), 0, 0, 0);
        addView(leftIconIV);
    }

    private void initRightIcon() {
        if (null == rightIconIV) {
            rightIconIV = new ImageView(mContext);
        }
        rightImgParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightImgParams.addRule(CENTER_VERTICAL);
        rightImgParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        rightImgParams.setMargins(0, 0, DensityUtil.dip2px(mContext, 14f), 0);
        rightIconIV.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        rightIconIV.setId(R.id.sRightImgId);
        rightIconIV.setLayoutParams(rightImgParams);
        rightIconIV.setImageDrawable(rightImgId);
        addView(rightIconIV);
    }

    private void initLiftText() {
        if (null == liftTv) {
            liftTv = new TextView(mContext);
        }
        liftTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        liftTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        liftTextParams.addRule(RelativeLayout.RIGHT_OF, R.id.sLeftImgId);
        liftTextParams.setMargins(DensityUtil.dip2px(mContext, 8f), 0, 0, 0);
        liftTv.setId(R.id.sLeftViewId);
        liftTv.setText(liftText);
        liftTv.setTextColor(liftTextColor);
        liftTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, liftTextSize);
        liftTv.setLayoutParams(liftTextParams);
        addView(liftTv);
    }

    private void initRightText() {
        if (null == rightTv) {
            rightTv = new TextView(mContext);
        }
        rightTextParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        rightTextParams.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
        rightTextParams.addRule(RelativeLayout.LEFT_OF, R.id.sRightImgId);
        rightTextParams.setMargins(0, 0, DensityUtil.dip2px(mContext, 8f), 0);
        rightTv.setId(R.id.sRightViewId);
        rightTv.setText(rightText);
        rightTv.setTextColor(rightTextColor);
        rightTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, rightTextSize);
        rightTv.setLayoutParams(rightTextParams);
        addView(rightTv);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
    }
}
