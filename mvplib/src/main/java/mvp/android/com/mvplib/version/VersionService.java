package mvp.android.com.mvplib.version;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import java.io.File;
import mvp.android.com.mvplib.R;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:42
 * 修改历史：
 * ================================================
 */

public class VersionService extends Service {

  private static final int NOTIFY_ID = 0;
  public static boolean isRunning = false;
  private NotificationManager notificationManager;
  private NotificationCompat.Builder notificationBuilder;
  private VersionService.DownloadBinder binder = new VersionService.DownloadBinder();

  public VersionService() {
  }

  public static void bindService(Context context, ServiceConnection connection) {
    Intent intent = new Intent(context, VersionService.class);
    context.startService(intent);
    context.bindService(intent, connection, BIND_DEBUG_UNBIND);
    isRunning = true;
  }

  @Override public void onCreate() {
    super.onCreate();
    this.notificationManager = (NotificationManager) this.getSystemService("notification");
  }

  @Override public IBinder onBind(Intent intent) {
    return this.binder;
  }

  @Override public void onDestroy() {
    this.notificationManager = null;
    super.onDestroy();
  }

  private void showNotification() {
    this.notificationBuilder = new NotificationCompat.Builder(this);
    this.notificationBuilder.setContentTitle("开始下载")
        .setContentText("正在连接服务器")
        .setSmallIcon(R.mipmap.abs_version_notificaiton_icon)
        .setLargeIcon(KitBitmap.convert4Drawable(KitSystem.appIcon()))
        .setOngoing(true)
        .setWhen(System.currentTimeMillis());
    this.notificationManager.notify(0, this.notificationBuilder.build());
  }

  private void refreshNotification(int rate) {
    this.notificationBuilder.setContentTitle("正在下载：" + KitSystem.appName())
        .setContentText(rate + "%")
        .setProgress(100, rate, false)
        .setWhen(System.currentTimeMillis());
    this.notificationManager.notify(0, this.notificationBuilder.build());
  }

  private void stopNotification(String msg) {
    this.notificationBuilder.setContentTitle(KitSystem.appName()).setContentText(msg);
    Notification notification = this.notificationBuilder.build();
    notification.flags = 16;
    this.notificationManager.notify(0, notification);
    this.close();
  }

  public void goInstall(File file) {
    Uri fileUri = FileProvider.getUriForFile(this,
        this.getApplicationContext().getPackageName() + ".fileProvider", file);
    Intent intent;
    if (KitSystem.appIsForeground()) {
      this.notificationManager.cancel(0);
      intent = new Intent("android.intent.action.VIEW");
      intent.setFlags(268435456);
      if (Build.VERSION.SDK_INT >= 24) {
        intent.setFlags(1);
        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
      } else {
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
      }

      if (this.getPackageManager().queryIntentActivities(intent, 0).size() > 0) {
        this.startActivity(intent);
      }
    } else {
      intent = new Intent("android.intent.action.VIEW");
      if (Build.VERSION.SDK_INT >= 24) {
        intent.setFlags(1);
        intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
      } else {
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
      }

      PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 134217728);
      this.notificationBuilder.setContentIntent(contentIntent)
          .setContentTitle(KitSystem.appName())
          .setContentText("下载完成，请点击安装")
          .setProgress(0, 0, false)
          .setAutoCancel(true)
          .setDefaults(-1);
      this.notificationManager.notify(0, this.notificationBuilder.build());
    }

    this.close();
  }

  private void close() {
    this.stopSelf();
    isRunning = false;
  }

  public class DownloadBinder extends Binder {
    public DownloadBinder() {
    }

    public void start(final Version version, Version.VersionCallback versionCallback,
        final Callback<Integer> downloadCallback) {
      String apkName = version.downloadUrl.substring(version.downloadUrl.lastIndexOf("/") + 1,
          version.downloadUrl.length());
      File appCache = VersionService.this.getExternalCacheDir();
      if (!appCache.exists()) {
        appCache.mkdirs();
      }

      File apkDir = new File(appCache + File.separator + version.upgradeVersion);
      if (!apkDir.exists()) {
        apkDir.mkdirs();
      }

      final File apkFile = new File(apkDir, apkName);
      if (apkFile.exists() && KitCheck.isCorrectFile(apkFile, version.fileMd5)) {
        downloadCallback.success(Integer.valueOf(101));
        VersionService.this.goInstall(apkFile);
      } else {
        VersionService.this.showNotification();
        versionCallback.download(version.downloadUrl, apkFile.getAbsolutePath(),
            new Callback<Integer>() {
              @Override public void success(Integer integer) {
                downloadCallback.success(integer);
                if (integer.intValue() == 101) {
                  if (!KitCheck.isEmpty(version.fileMd5) && !KitCheck.isCorrectFile(apkFile,
                      version.fileMd5)) {
                    VersionService.this.stopNotification("md5不匹配");
                    downloadCallback.failure(3, "md5不匹配");
                  } else {
                    VersionService.this.goInstall(apkFile);
                  }
                } else {
                  VersionService.this.refreshNotification(integer.intValue());
                }
              }

              @Override public void failure(int code, String failure) {
                downloadCallback.failure(code, failure);

                try {
                  VersionService.this.notificationManager.cancel(0);
                  VersionService.this.close();
                } catch (Exception var4) {
                  var4.printStackTrace();
                }
              }
            });
      }
    }
  }
}
