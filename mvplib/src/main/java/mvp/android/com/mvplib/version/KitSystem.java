package mvp.android.com.mvplib.version;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

public class KitSystem {

  public static final String KIT_SYSTEM = "kit:system:";
  public static final String KIT_PROFILE = "kit:profile:";
  private static volatile KitKeep sKitKeep;
  private static Context sContext;
  private static List<Activity> uis = new LinkedList();

  public KitSystem() {
  }

  public static synchronized KitKeep init(Context context) {
    if(sKitKeep == null) {
      Class var1 = KitSystem.class;
      synchronized(KitSystem.class) {
        if(sKitKeep == null) {
          sKitKeep = new KitKeep(context, context.getPackageName() + "_system");
          sContext = context;
          if(sKitKeep.getInt("kit:system:width") == 0) {
            WindowManager wm = (WindowManager)context.getSystemService("window");
            DisplayMetrics outMetrics = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(outMetrics);
            sKitKeep.putInt("kit:system:width", outMetrics.widthPixels);
            sKitKeep.putInt("kit:system:height", outMetrics.heightPixels);
            sKitKeep.putFloat("kit:system:density", outMetrics.density);
            imei();
          }
        }
      }
    }

    return sKitKeep;
  }

  public static void keepChangedNotify(boolean on) {
    sKitKeep.changedNotify(on);
  }

  public static boolean contains(String key) {
    return sKitKeep.contains(key);
  }

  public static KitKeep remove(String key) {
    return sKitKeep.remove(key);
  }

  public static KitKeep clear() {
    return sKitKeep.clear();
  }

  public static KitKeep putString(String key, String value) {
    return sKitKeep.putString(key, value);
  }

  public static String getString(String key) {
    return getString(key, "");
  }

  public static String getString(String key, String defaultValue) {
    return sKitKeep.getString(key, defaultValue);
  }

  public static KitKeep putInt(String key, int value) {
    return sKitKeep.putInt(key, value);
  }

  public static int getInt(String key) {
    return getInt(key, 0);
  }

  public static int getInt(String key, int defaultValue) {
    return sKitKeep.getInt(key, defaultValue);
  }

  public static KitKeep putLong(String key, long value) {
    return sKitKeep.putLong(key, value);
  }

  public static long getLong(String key) {
    return getLong(key, 0L);
  }

  public static long getLong(String key, long defaultValue) {
    return sKitKeep.getLong(key, defaultValue);
  }

  public static KitKeep putFloat(String key, float value) {
    return sKitKeep.putFloat(key, value);
  }

  public static float getFloat(String key) {
    return getFloat(key, 0.0F);
  }

  public static float getFloat(String key, float defaultValue) {
    return sKitKeep.getFloat(key, defaultValue);
  }

  public static KitKeep putBoolean(String key, boolean value) {
    return sKitKeep.putBoolean(key, value);
  }

  public static boolean getBoolean(String key) {
    return getBoolean(key, false);
  }

  public static boolean getBoolean(String key, boolean defaultValue) {
    return sKitKeep.getBoolean(key, defaultValue);
  }

  public static <T> KitKeep putObj(String key, T value) {
    return sKitKeep.putObj(key, value);
  }

  public static <T> T getObj(String key, Class<T> clazz) {
    return sKitKeep.getObj(key, clazz);
  }

  public static boolean isSign() {
    return !KitCheck.isEmpty(uid());
  }

  public static KitKeep uid(String uid) {
    return sKitKeep.putString("kit:profile:uid", uid);
  }

  public static String uid() {
    return sKitKeep.getString("kit:profile:uid");
  }

  public static KitKeep token(String token) {
    return sKitKeep.putString("kit:profile:token", token);
  }

  public static String token() {
    return sKitKeep.getString("kit:profile:token");
  }

  public static void signOut() {
    sKitKeep.remove("kit:profile:uid").remove("kit:profile:token");
    Map kvs = sKitKeep.getAll();
    if(!KitCheck.isEmpty(kvs)) {
      ArrayList ks = new ArrayList();
      Iterator var2 = kvs.entrySet().iterator();

      while(var2.hasNext()) {
        Map.Entry e = (Map.Entry)var2.next();
        if(((String)e.getKey()).startsWith("kit:profile:")) {
          ks.add(e.getKey());
        }
      }

      sKitKeep.remove(ks);
    }

  }

  public static String imei() {
    String imei = sKitKeep.getString("kit:system:imei");
    if(KitCheck.isEmpty(imei)) {
      try {
        imei = Settings.Secure.getString(sContext.getContentResolver(), "android_id");
        if(KitCheck.isEmpty(imei) && "9774d56d682e549c".equals(imei)) {
          File e1 = new File(sContext.getFilesDir(), "INSTALLATION");
          if(!e1.exists()) {
            writeInstallationFile(e1);
          }

          imei = readInstallationFile(e1);
        }
      } catch (Exception var4) {
        try {
          imei = KitKey.aes2Hex(((TelephonyManager)sContext.getSystemService("phone")).getDeviceId());
        } catch (Exception var3) {
          imei = UUID.randomUUID().toString();
        }
      }

      sKitKeep.putString("kit:system:imei", imei);
    }

    return imei;
  }

  private static String readInstallationFile(File installation) throws IOException {
    RandomAccessFile f = new RandomAccessFile(installation, "r");
    byte[] bytes = new byte[(int)f.length()];
    f.readFully(bytes);
    f.close();
    return new String(bytes);
  }

  private static void writeInstallationFile(File installation) throws IOException {
    FileOutputStream out = new FileOutputStream(installation);
    String id = UUID.randomUUID().toString();
    out.write(id.getBytes());
    out.close();
  }

  public static KitKeep version(String version) {
    return sKitKeep.putString("kit:system:version", version);
  }

  public static String version() {
    return sKitKeep.getString("kit:system:version");
  }

  public static boolean showGuide(String version) {
    if(KitCheck.isEmpty(version)) {
      return false;
    } else if(version.equals(version())) {
      return false;
    } else {
      version(version);
      return true;
    }
  }

  public static File cacheDir() {
    return sContext.getCacheDir();
  }

  public static int width() {
    return sKitKeep.getInt("kit:system:width", 0);
  }

  public static int height() {
    return sKitKeep.getInt("kit:system:height", 0);
  }

  public static float density() {
    return sKitKeep.getFloat("kit:system:density", 0.0F);
  }

  public static int dip2px(float dip) {
    return (int)(dip * density() + 0.5F);
  }

  public static int px2dip(float px) {
    return (int)(px / density() + 0.5F);
  }

  public static float dimension(int id) {
    return sContext.getResources().getDimension(id);
  }

  public static int dimensionPixelSize(int id) {
    return sContext.getResources().getDimensionPixelSize(id);
  }

  public static int calculate1080(int px) {
    return px * width() / 1080;
  }

  public static int calculate720(int px) {
    return px * width() / 720;
  }

  public static void uiCreate(Activity ui) {
    uis.add(ui);
  }

  public static void uiDestroy(Activity ui) {
    uis.remove(ui);
  }

  public static Activity uiTop(Activity ui) {
    return (Activity)uis.get(uis.size() - 1);
  }

  public static Activity uiBottom(Activity ui) {
    return (Activity)uis.get(0);
  }

  public static void uiFinish(boolean exit) {
    try {
      LinkedList e = new LinkedList();
      e.addAll(uis);
      Iterator var2 = e.iterator();

      while(var2.hasNext()) {
        Activity tmp = (Activity)var2.next();

        try {
          if(tmp != null) {
            tmp.finish();
          }
        } catch (Exception var9) {
          ;
        }
      }

      uis.clear();
    } catch (Exception var10) {
      var10.printStackTrace();
    } finally {
      if(exit) {
        System.exit(0);
      }

    }

  }

  public static boolean uiBack(Class ui) {
    boolean find = false;
    LinkedList tps = new LinkedList();
    tps.addAll(uis);
    Iterator var3 = tps.iterator();

    while(var3.hasNext()) {
      Activity tmp = (Activity)var3.next();
      if(find) {
        try {
          if(tmp != null) {
            tmp.finish();
          }

          uis.remove(tmp);
        } catch (Exception var6) {
          var6.printStackTrace();
        }
      }

      if(ui.getName().equals(tmp.getClass().getName())) {
        find = true;
      }
    }

    return find;
  }

  public static Drawable appIcon() {
    try {
      return sContext.getPackageManager().getApplicationIcon(sContext.getPackageName());
    } catch (PackageManager.NameNotFoundException var1) {
      var1.printStackTrace();
      return null;
    }
  }

  public static String appName() {
    PackageInfo packageInfo = null;

    try {
      packageInfo = sContext.getPackageManager().getPackageInfo(sContext.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException var2) {
      var2.printStackTrace();
    }

    return packageInfo != null?packageInfo.applicationInfo.loadLabel(sContext.getPackageManager()).toString():"";
  }

  public static boolean appIsForeground() {
    ActivityManager activityManager = (ActivityManager)sContext.getSystemService("activity");
    String packageName = sContext.getPackageName();
    List appProcesses = activityManager.getRunningAppProcesses();
    if(appProcesses == null) {
      return false;
    } else {
      Iterator var3 = appProcesses.iterator();

      ActivityManager.RunningAppProcessInfo appProcess;
      do {
        if(!var3.hasNext()) {
          return false;
        }

        appProcess = (ActivityManager.RunningAppProcessInfo)var3.next();
      } while(!appProcess.processName.equals(packageName) || appProcess.importance != 100);

      return true;
    }
  }
}
