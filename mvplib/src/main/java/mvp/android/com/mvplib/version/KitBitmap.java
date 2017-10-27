package mvp.android.com.mvplib.version;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 4:07
 * 修改历史：
 * ================================================
 */

public class KitBitmap {
  private KitBitmap() {
  }

  public static byte[] convert2Bytes(Bitmap bmp) {
    return convert2Bytes(bmp, false);
  }

  public static byte[] convert2Bytes(Bitmap bmp, boolean needRecycle) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
    byte[] result = output.toByteArray();

    try {
      output.close();
    } catch (Exception var13) {
      var13.printStackTrace();
    } finally {
      if (needRecycle && !bmp.isRecycled()) {
        try {
          bmp.recycle();
        } catch (Exception var12) {
          ;
        }
      }
    }

    return result;
  }

  public static Bitmap convert4Bytes(byte[] b) {
    return b != null && b.length != 0 ? BitmapFactory.decodeByteArray(b, 0, b.length) : null;
  }

  public static Bitmap convert4Drawable(Drawable drawable) {
    return drawable == null ? null : ((BitmapDrawable) drawable).getBitmap();
  }

  public static Drawable convert2Drawable(Bitmap bitmap) {
    return bitmap == null ? null : new BitmapDrawable(bitmap);
  }

  public static Bitmap scale(Bitmap org, int newWidth, int newHeight) {
    return scale(org, (float) newWidth / (float) org.getWidth(),
        (float) newHeight / (float) org.getHeight());
  }

  public static Bitmap scale(Bitmap org, float scaleWidth, float scaleHeight) {
    if (org == null) {
      return null;
    } else {
      Matrix matrix = new Matrix();
      matrix.postScale(scaleWidth, scaleHeight);
      return Bitmap.createBitmap(org, 0, 0, org.getWidth(), org.getHeight(), matrix, true);
    }
  }

  public static boolean save(Bitmap bitmap, String path) {
    try {
      File file = new File(path);
      if (file.exists()) {
        file.deleteOnExit();
      }

      file.createNewFile();
      return save(bitmap, file);
    } catch (Exception var3) {
      return false;
    }
  }

  public static boolean save(Bitmap bitmap, File file) {
    if (bitmap == null) {
      return false;
    } else {
      FileOutputStream fos = null;

      try {
        fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        boolean e = true;
        return e;
      } catch (Exception var13) {
        var13.printStackTrace();
      } finally {
        if (fos != null) {
          try {
            fos.close();
          } catch (IOException var12) {
            var12.printStackTrace();
          }
        }
      }

      return false;
    }
  }

  public static synchronized Bitmap decodeFile(String path, int reqWidth, int reqHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(path, options);
    options.inSampleSize = inSampleSize(options, reqWidth, reqHeight);
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(path, options);
  }

  public static synchronized Bitmap decodeBytes(byte[] in, int reqWidth, int reqHeight) {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(in, 0, in.length, options);
    options.inSampleSize = inSampleSize(options, reqWidth, reqHeight);
    options.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeByteArray(in, 0, in.length, options);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
    return bitmap;
  }

  public static synchronized void decodeBytes2Save(byte[] in, int reqWidth, int reqHeight,
      String path) throws Exception {
    BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(in, 0, in.length, options);
    options.inSampleSize = inSampleSize(options, reqWidth, reqHeight);
    options.inJustDecodeBounds = false;
    Bitmap bitmap = BitmapFactory.decodeByteArray(in, 0, in.length, options);
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
    if (bitmap != null && !bitmap.isRecycled()) {
      bitmap.recycle();
    }

    File file = new File(path);
    if (!file.exists()) {
      file.createNewFile();
    }

    FileOutputStream os = new FileOutputStream(file);
    BufferedOutputStream bos = new BufferedOutputStream(os);
    bos.write(baos.toByteArray());
    if (bos != null) {
      try {
        bos.close();
      } catch (IOException var12) {
        ;
      }
    }

    if (baos != null) {
      try {
        baos.close();
      } catch (IOException var11) {
        ;
      }
    }
  }

  public static int inSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
    int height = options.outHeight;
    int width = options.outWidth;
    int inSampleSize = 1;
    if (height > reqHeight || width > reqWidth) {
      int heightRatio = Math.round((float) height / (float) reqHeight);
      int widthRatio = Math.round((float) width / (float) reqWidth);
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
      float totalPixels = (float) (width * height);

      for (float totalReqPixelsCap = (float) (reqWidth * reqHeight * 2);
          totalPixels / (float) (inSampleSize * inSampleSize) > totalReqPixelsCap; ++inSampleSize) {
        ;
      }
    }

    return inSampleSize;
  }
}
