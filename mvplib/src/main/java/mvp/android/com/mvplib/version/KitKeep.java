package mvp.android.com.mvplib.version;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:44
 * 修改历史：
 * ================================================
 */

public class KitKeep implements SharedPreferences.OnSharedPreferenceChangeListener {
  private static final String KIT_KEEP = "kit:keep:";
  private SharedPreferences sharedPreferences;

  public static KitKeep get(Context context) {
    return new KitKeep(context, context.getPackageName() + "_keep");
  }

  KitKeep(Context context, String name) {
    this.sharedPreferences = context.getSharedPreferences(name, 0);
  }

  private KitKeep put(String key, String value) {
    this.sharedPreferences.edit().putString(key, KitKey.aes2Hex(value, key)).commit();
    return this;
  }

  private String get(String key, String defaultValue) {
    String value = this.sharedPreferences.getString(key, (String)null);
    return KitCheck.isNull(value)?defaultValue:KitKey.aes4Hex(value, key);
  }

  public boolean contains(String key) {
    return this.sharedPreferences.contains(key);
  }

  public KitKeep remove(String key) {
    this.sharedPreferences.edit().remove(key).commit();
    return this;
  }

  protected KitKeep remove(List<String> keys) {
    if(!KitCheck.isEmpty(keys)) {
      SharedPreferences.Editor editor = this.sharedPreferences.edit();
      Iterator var3 = keys.iterator();

      while(var3.hasNext()) {
        String key = (String)var3.next();
        editor.remove(key);
      }

      editor.commit();
    }

    return this;
  }

  protected Map<String, ?> getAll() {
    return this.sharedPreferences.getAll();
  }

  public KitKeep clear() {
    this.sharedPreferences.edit().clear().commit();
    return this;
  }

  public KitKeep putString(String key, String value) {
    return this.put(key, value);
  }

  public String getString(String key) {
    return this.getString(key, "");
  }

  public String getString(String key, String defaultValue) {
    return this.get(key, defaultValue);
  }

  public KitKeep putInt(String key, int value) {
    return this.put(key, value + "");
  }

  public int getInt(String key) {
    return this.getInt(key, 0);
  }

  public int getInt(String key, int defaultValue) {
    return Integer.parseInt(this.get(key, defaultValue + ""));
  }

  public KitKeep putLong(String key, long value) {
    return this.put(key, value + "");
  }

  public long getLong(String key) {
    return this.getLong(key, 0L);
  }

  public long getLong(String key, long defaultValue) {
    return Long.parseLong(this.get(key, defaultValue + ""));
  }

  public KitKeep putFloat(String key, float value) {
    return this.put(key, value + "");
  }

  public float getFloat(String key) {
    return this.getFloat(key, 0.0F);
  }

  public float getFloat(String key, float defaultValue) {
    return Float.parseFloat(this.get(key, defaultValue + ""));
  }

  public KitKeep putBoolean(String key, boolean value) {
    return this.put(key, value + "");
  }

  public boolean getBoolean(String key) {
    return this.getBoolean(key, false);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    return Boolean.parseBoolean(this.get(key, defaultValue + ""));
  }

  public <T> KitKeep putObj(String key, T value) {
    this.sharedPreferences.edit().putString(key, (new Gson()).toJson(value)).commit();
    return this;
  }

  public <T> T getObj(String key, Class<T> clazz) {
    try {
      return (new Gson()).fromJson(this.sharedPreferences.getString(key, (String)null), clazz);
    } catch (Exception var4) {
      var4.printStackTrace();
      return null;
    }
  }

  public void changedNotify(boolean on) {
    if(on) {
      this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    } else {
      this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

  }

  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    KitEvent.normal(new KitKeep.Event(key));
  }

  public static class Event {
    private String key;

    public String key() {
      return this.key;
    }

    public Event(String key) {
      this.key = key;
    }

    public boolean isKey(String key) {
      return !KitCheck.isEmpty(this.key()) && this.key().equals(key);
    }
  }
}
