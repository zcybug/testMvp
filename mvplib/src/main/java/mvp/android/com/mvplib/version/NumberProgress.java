package mvp.android.com.mvplib.version;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import mvp.android.com.mvplib.R;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:53
 * 修改历史：
 * ================================================
 */

public class NumberProgress extends View {
  private static final String INSTANCE_STATE = "saved_instance";
  private static final String INSTANCE_TEXT_COLOR = "text_color";
  private static final String INSTANCE_TEXT_SIZE = "text_size";
  private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
  private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
  private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
  private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
  private static final String INSTANCE_MAX = "max";
  private static final String INSTANCE_PROGRESS = "progress";
  private static final String INSTANCE_SUFFIX = "suffix";
  private static final String INSTANCE_PREFIX = "prefix";
  private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";
  private static final int PROGRESS_TEXT_VISIBLE = 0;
  private final int default_text_color;
  private final int default_reached_color;
  private final int default_unreached_color;
  private final float default_progress_text_offset;
  private final float default_text_size;
  private final float default_reached_bar_height;
  private final float default_unreached_bar_height;
  private int mMaxProgress;
  private int mCurrentProgress;
  private int mReachedBarColor;
  private int mUnreachedBarColor;
  private int mTextColor;
  private float mTextSize;
  private float mReachedBarHeight;
  private float mUnreachedBarHeight;
  private String mSuffix;
  private String mPrefix;
  private float mDrawTextWidth;
  private float mDrawTextStart;
  private float mDrawTextEnd;
  private String mCurrentDrawText;
  private Paint mReachedBarPaint;
  private Paint mUnreachedBarPaint;
  private Paint mTextPaint;
  private RectF mUnreachedRectF;
  private RectF mReachedRectF;
  private float mOffset;
  private boolean mDrawUnreachedBar;
  private boolean mDrawReachedBar;
  private boolean mIfDrawText;
  private NumberProgress.OnProgressBarListener mListener;
  private Paint mCicrlePaint;

  public NumberProgress(Context context) {
    this(context, (AttributeSet) null);
  }

  public NumberProgress(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public NumberProgress(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    this.default_text_color = Color.rgb(66, 145, 241);
    this.default_reached_color = Color.rgb(66, 145, 241);
    this.default_unreached_color = Color.rgb(204, 204, 204);
    this.mMaxProgress = 100;
    this.mCurrentProgress = 0;
    this.mSuffix = "%";
    this.mPrefix = "";
    this.mUnreachedRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
    this.mReachedRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
    this.mDrawUnreachedBar = true;
    this.mDrawReachedBar = true;
    this.mIfDrawText = true;
    this.default_reached_bar_height = this.dp2px(1.5F);
    this.default_unreached_bar_height = this.dp2px(1.0F);
    this.default_text_size = this.sp2px(10.0F);
    this.default_progress_text_offset = this.dp2px(3.0F);
    TypedArray attributes = context.getTheme()
        .obtainStyledAttributes(attrs, R.styleable.NumberProgress, defStyleAttr, 0);
    this.mReachedBarColor = attributes.getColor(R.styleable.NumberProgress_progress_reached_color,
        this.default_reached_color);
    this.mUnreachedBarColor =
        attributes.getColor(R.styleable.NumberProgress_progress_unreached_color,
            this.default_unreached_color);
    this.mTextColor = attributes.getColor(R.styleable.NumberProgress_progress_text_color,
        this.default_text_color);
    this.mTextSize = attributes.getDimension(R.styleable.NumberProgress_progress_text_size,
        this.default_text_size);
    this.mReachedBarHeight =
        attributes.getDimension(R.styleable.NumberProgress_progress_reached_bar_height,
            this.default_reached_bar_height);
    this.mUnreachedBarHeight =
        attributes.getDimension(R.styleable.NumberProgress_progress_unreached_bar_height,
            this.default_unreached_bar_height);
    this.mOffset = attributes.getDimension(R.styleable.NumberProgress_progress_text_offset,
        this.default_progress_text_offset);
    int textVisible = attributes.getInt(R.styleable.NumberProgress_progress_text_visibility, 0);
    if (textVisible != 0) {
      this.mIfDrawText = false;
    }

    this.setProgress(attributes.getInt(R.styleable.NumberProgress_progress_current, 0));
    this.setMax(attributes.getInt(R.styleable.NumberProgress_progress_max, 100));
    attributes.recycle();
    this.initializePainters();
  }

  @Override protected int getSuggestedMinimumWidth() {
    return (int) this.mTextSize;
  }

  @Override protected int getSuggestedMinimumHeight() {
    return Math.max((int) this.mTextSize,
        Math.max((int) this.mReachedBarHeight, (int) this.mUnreachedBarHeight));
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    this.setMeasuredDimension(this.measure(widthMeasureSpec, true),
        this.measure(heightMeasureSpec, false));
  }

  private int measure(int measureSpec, boolean isWidth) {
    int mode = MeasureSpec.getMode(measureSpec);
    int size = MeasureSpec.getSize(measureSpec);
    int padding = isWidth ? this.getPaddingLeft() + this.getPaddingRight()
        : this.getPaddingTop() + this.getPaddingBottom();
    int result;
    if (mode == 1073741824) {
      result = size;
    } else {
      result = isWidth ? this.getSuggestedMinimumWidth() : this.getSuggestedMinimumHeight();
      result += padding;
      if (mode == -2147483648) {
        if (isWidth) {
          result = Math.max(result, size);
        } else {
          result = Math.min(result, size);
        }
      }
    }

    return result;
  }

  @Override protected void onDraw(Canvas canvas) {
    if (this.mIfDrawText) {
      this.calculateDrawRectF();
    } else {
      this.calculateDrawRectFWithoutProgressText();
    }

    if (this.mDrawReachedBar) {
      canvas.drawRect(this.mReachedRectF, this.mReachedBarPaint);
    }

    if (this.mDrawUnreachedBar) {
      canvas.drawRect(this.mUnreachedRectF, this.mUnreachedBarPaint);
    }

    if (this.mIfDrawText) {
      canvas.drawText(this.mCurrentDrawText, this.mDrawTextStart, this.mDrawTextEnd,
          this.mTextPaint);
    }
  }

  private void initializePainters() {
    this.mReachedBarPaint = new Paint(1);
    this.mReachedBarPaint.setColor(this.mReachedBarColor);
    this.mUnreachedBarPaint = new Paint(1);
    this.mUnreachedBarPaint.setColor(this.mUnreachedBarColor);
    this.mTextPaint = new Paint(1);
    this.mTextPaint.setColor(this.mTextColor);
    this.mTextPaint.setTextSize(this.mTextSize);
  }

  private void calculateDrawRectFWithoutProgressText() {
    this.mReachedRectF.left = (float) this.getPaddingLeft();
    this.mReachedRectF.top = (float) this.getHeight() / 2.0F - this.mReachedBarHeight / 2.0F;
    this.mReachedRectF.right =
        (float) (this.getWidth() - this.getPaddingLeft() - this.getPaddingRight())
            / ((float) this.getMax() * 1.0F) * (float) this.getProgress()
            + (float) this.getPaddingLeft();
    this.mReachedRectF.bottom = (float) this.getHeight() / 2.0F + this.mReachedBarHeight / 2.0F;
    this.mUnreachedRectF.left = this.mReachedRectF.right;
    this.mUnreachedRectF.right = (float) (this.getWidth() - this.getPaddingRight());
    this.mUnreachedRectF.top = (float) this.getHeight() / 2.0F + -this.mUnreachedBarHeight / 2.0F;
    this.mUnreachedRectF.bottom = (float) this.getHeight() / 2.0F + this.mUnreachedBarHeight / 2.0F;
  }

  private void calculateDrawRectF() {
    this.mCurrentDrawText = String.format("%d",
        new Object[] { Integer.valueOf(this.getProgress() * 100 / this.getMax()) });
    this.mCurrentDrawText = this.mPrefix + this.mCurrentDrawText + this.mSuffix;
    this.mDrawTextWidth = this.mTextPaint.measureText(this.mCurrentDrawText);
    if (this.getProgress() == 0) {
      this.mDrawReachedBar = false;
      this.mDrawTextStart = (float) this.getPaddingLeft();
    } else {
      this.mDrawReachedBar = true;
      this.mReachedRectF.left = (float) this.getPaddingLeft();
      this.mReachedRectF.top = (float) this.getHeight() / 2.0F - this.mReachedBarHeight / 2.0F;
      this.mReachedRectF.right =
          (float) (this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()) / ((float) this
              .getMax() * 1.0F) * (float) this.getProgress() - this.mOffset
              + (float) this.getPaddingLeft();
      this.mReachedRectF.bottom = (float) this.getHeight() / 2.0F + this.mReachedBarHeight / 2.0F;
      this.mDrawTextStart = this.mReachedRectF.right + this.mOffset;
    }

    this.mDrawTextEnd = (float) ((int) ((float) this.getHeight() / 2.0F
        - (this.mTextPaint.descent() + this.mTextPaint.ascent()) / 2.0F));
    if (this.mDrawTextStart + this.mDrawTextWidth >= (float) (this.getWidth()
        - this.getPaddingRight())) {
      this.mDrawTextStart =
          (float) (this.getWidth() - this.getPaddingRight()) - this.mDrawTextWidth;
      this.mReachedRectF.right = this.mDrawTextStart - this.mOffset;
    }

    float unreachedBarStart = this.mDrawTextStart + this.mDrawTextWidth + this.mOffset;
    if (unreachedBarStart >= (float) (this.getWidth() - this.getPaddingRight())) {
      this.mDrawUnreachedBar = false;
    } else {
      this.mDrawUnreachedBar = true;
      this.mUnreachedRectF.left = unreachedBarStart;
      this.mUnreachedRectF.right = (float) (this.getWidth() - this.getPaddingRight());
      this.mUnreachedRectF.top = (float) this.getHeight() / 2.0F + -this.mUnreachedBarHeight / 2.0F;
      this.mUnreachedRectF.bottom =
          (float) this.getHeight() / 2.0F + this.mUnreachedBarHeight / 2.0F;
    }
  }

  public int getTextColor() {
    return this.mTextColor;
  }

  public float getProgressTextSize() {
    return this.mTextSize;
  }

  public void setProgressTextSize(float textSize) {
    this.mTextSize = textSize;
    this.mTextPaint.setTextSize(this.mTextSize);
    this.invalidate();
  }

  public int getUnreachedBarColor() {
    return this.mUnreachedBarColor;
  }

  public void setUnreachedBarColor(int barColor) {
    this.mUnreachedBarColor = barColor;
    this.mUnreachedBarPaint.setColor(this.mUnreachedBarColor);
    this.invalidate();
  }

  public int getReachedBarColor() {
    return this.mReachedBarColor;
  }

  public void setReachedBarColor(int progressColor) {
    this.mReachedBarColor = progressColor;
    this.mReachedBarPaint.setColor(this.mReachedBarColor);
    this.invalidate();
  }

  public int getProgress() {
    return this.mCurrentProgress;
  }

  public void setProgress(int progress) {
    if (progress <= this.getMax() && progress >= 0) {
      this.mCurrentProgress = progress;
      this.invalidate();
    }
  }

  public int getMax() {
    return this.mMaxProgress;
  }

  public void setMax(int maxProgress) {
    if (maxProgress > 0) {
      this.mMaxProgress = maxProgress;
      this.invalidate();
    }
  }

  public float getReachedBarHeight() {
    return this.mReachedBarHeight;
  }

  public void setReachedBarHeight(float height) {
    this.mReachedBarHeight = height;
  }

  public float getUnreachedBarHeight() {
    return this.mUnreachedBarHeight;
  }

  public void setUnreachedBarHeight(float height) {
    this.mUnreachedBarHeight = height;
  }

  public void setProgressTextColor(int textColor) {
    this.mTextColor = textColor;
    this.mTextPaint.setColor(this.mTextColor);
    this.invalidate();
  }

  public String getSuffix() {
    return this.mSuffix;
  }

  public void setSuffix(String suffix) {
    if (suffix == null) {
      this.mSuffix = "";
    } else {
      this.mSuffix = suffix;
    }
  }

  public String getPrefix() {
    return this.mPrefix;
  }

  public void setPrefix(String prefix) {
    if (prefix == null) {
      this.mPrefix = "";
    } else {
      this.mPrefix = prefix;
    }
  }

  public void incrementProgressBy(int by) {
    if (by > 0) {
      this.setProgress(this.getProgress() + by);
    }

    if (this.mListener != null) {
      this.mListener.onProgressChange(this.getProgress(), this.getMax());
    }
  }

  @Override protected Parcelable onSaveInstanceState() {
    Bundle bundle = new Bundle();
    bundle.putParcelable("saved_instance", super.onSaveInstanceState());
    bundle.putInt("text_color", this.getTextColor());
    bundle.putFloat("text_size", this.getProgressTextSize());
    bundle.putFloat("reached_bar_height", this.getReachedBarHeight());
    bundle.putFloat("unreached_bar_height", this.getUnreachedBarHeight());
    bundle.putInt("reached_bar_color", this.getReachedBarColor());
    bundle.putInt("unreached_bar_color", this.getUnreachedBarColor());
    bundle.putInt("max", this.getMax());
    bundle.putInt("progress", this.getProgress());
    bundle.putString("suffix", this.getSuffix());
    bundle.putString("prefix", this.getPrefix());
    bundle.putBoolean("text_visibility", this.getProgressTextVisibility());
    return bundle;
  }

  @Override protected void onRestoreInstanceState(Parcelable state) {
    if (state instanceof Bundle) {
      Bundle bundle = (Bundle) state;
      this.mTextColor = bundle.getInt("text_color");
      this.mTextSize = bundle.getFloat("text_size");
      this.mReachedBarHeight = bundle.getFloat("reached_bar_height");
      this.mUnreachedBarHeight = bundle.getFloat("unreached_bar_height");
      this.mReachedBarColor = bundle.getInt("reached_bar_color");
      this.mUnreachedBarColor = bundle.getInt("unreached_bar_color");
      this.initializePainters();
      this.setMax(bundle.getInt("max"));
      this.setProgress(bundle.getInt("progress"));
      this.setPrefix(bundle.getString("prefix"));
      this.setSuffix(bundle.getString("suffix"));
      this.setProgressTextVisibility(
          bundle.getBoolean("text_visibility") ? NumberProgress.ProgressTextVisibility.VISIBLE
              : NumberProgress.ProgressTextVisibility.INVISIBLE);
      super.onRestoreInstanceState(bundle.getParcelable("saved_instance"));
    } else {
      super.onRestoreInstanceState(state);
    }
  }

  public float dp2px(float dp) {
    float scale = this.getResources().getDisplayMetrics().density;
    return dp * scale + 0.5F;
  }

  public float sp2px(float sp) {
    float scale = this.getResources().getDisplayMetrics().scaledDensity;
    return sp * scale;
  }

  public boolean getProgressTextVisibility() {
    return this.mIfDrawText;
  }

  public void setProgressTextVisibility(NumberProgress.ProgressTextVisibility visibility) {
    this.mIfDrawText = visibility == NumberProgress.ProgressTextVisibility.VISIBLE;
    this.invalidate();
  }

  public void setOnProgressBarListener(NumberProgress.OnProgressBarListener listener) {
    this.mListener = listener;
  }

  public interface OnProgressBarListener {
    void onProgressChange(int var1, int var2);
  }

  public static enum ProgressTextVisibility {
    VISIBLE, INVISIBLE;

    private ProgressTextVisibility() {
    }
  }
}
