package com.android.mvp.share;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/6/16 0016.
 */

public class ShareActivity extends AppCompatActivity {
    private static final String SAVE_PIC_PATH = "";

    //保存
    private static final int SAVE = 0;
    //分享
    private static final int SHARE = 1;
    //操作失败
    private static final int FAILE = 2;

    class Handler_Capture extends Handler {
        WeakReference<Activity> weakReference;

        public Handler_Capture(Activity activity) {
            weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (weakReference.get() != null) {
                Bundle data = null;
                switch (msg.what) {
                    case SHARE:
                        data = msg.getData();
                        shareApp("分享二维码到", "msgTitle", "二维码", data.getString("path"));
                        break;
                    case FAILE:
                        String strData = (String) msg.obj;
                        Toast.makeText(getApplicationContext(), strData, Toast.LENGTH_SHORT).show();
                        break;
                    case 1001:
                        break;
                }
            }
        }
    }

    /**
     * 分享i
     *
     * @param activityTitle
     * @param msgTitle
     * @param msgText
     * @param imgPath
     */
    private void shareApp(String activityTitle, String msgTitle, String msgText, String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (TextUtils.isEmpty(imgPath)) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/jpg");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, activityTitle));
    }


    /**
     * 保存图片到文件
     *
     * @param fileName 文件名称
     * @return
     * @throws Exception
     */
    private File saveBitmap(String fileName, Bitmap bitmap) throws Exception {
        if (bitmap == null)
            return null;
        File file = new File(SAVE_PIC_PATH);
        if (!file.exists()) {
            file.mkdirs();
        }
        final File realFile = new File(file, fileName);
        if (!realFile.exists()) {
            realFile.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(realFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        if (!bitmap.isRecycled()) {
            bitmap.recycle();
            System.gc(); // 通知系统回收
        }
        return realFile;
    }

}
