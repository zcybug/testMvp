package mvp.android.com.mvplib.log;

import android.util.Log;
import android.util.Pair;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/30 0030  上午 9:14
 * 修改历史：
 * ================================================
 */

public final class KLog {

  private static boolean canLog = true;
  private static final String[] types = new String[] {
      "int", "java.lang.String", "boolean", "char", "float", "double", "long", "short", "byte"
  };

  private KLog() {
  }

  public static void init(boolean can) {
    canLog = can;
  }

  public static boolean canLog() {
    return canLog;
  }

  public static void v(Object msg) {
    if (canLog) {
      Log.v(tag(), msg(msg));
    }
  }

  public static void d(Object msg) {
    if (canLog) {
      Log.d(tag(), msg(msg));
    }
  }

  public static void i(Object msg) {
    if (canLog) {
      Log.i(tag(), msg(msg));
    }
  }

  public static void w(Object msg) {
    if (canLog) {
      Log.w(tag(), msg(msg));
    }
  }

  public static void e(Object msg) {
    if (canLog) {
      Log.e(tag(), msg(msg));
    }
  }

  private static String tag() {
    StackTraceElement[] stackTraceElements = (new Throwable()).getStackTrace();
    StackTraceElement stackTraceElement = stackTraceElements[2];
    String stackTrace = stackTraceElement.toString();
    stackTrace = stackTrace.substring(stackTrace.lastIndexOf(40), stackTrace.length());
    String tag2 = "kit-log-%s.%s";
    return String.format(tag2, new Object[] { stackTrace, stackTraceElement.getMethodName() });
  }

  private static String msg(Object object) {
    if (object == null) {
      return obj2Str(object);
    } else {
      String simpleName = object.getClass().getSimpleName();
      if (object instanceof Throwable) {
        return object.toString();
      } else if (object instanceof String) {
        return object.toString();
      } else {
        String msg;
        if (object.getClass().isArray()) {
          msg = "Temporarily not support more than two dimensional Array!";
          int var11 = KArrary.getArrayDimension(object);
          switch (var11) {
            case 1:
              Pair var13 = KArrary.arrayToString(object);
              msg = simpleName.replace("[]", "[" + var13.first + "] {\n");
              msg = msg + var13.second + "\n";
              break;
            case 2:
              Pair var15 = KArrary.arrayToObject(object);
              Pair var17 = (Pair) var15.first;
              msg = simpleName.replace("[][]", "[" + var17.first + "][" + var17.second + "] {\n");
              msg = msg + var15.second + "\n";
          }

          return msg + "}";
        } else if (object instanceof Collection) {
          Collection var9 = (Collection) object;
          String var10 = "%s size = %d [\n";
          var10 = String.format(var10, new Object[] { simpleName, Integer.valueOf(var9.size()) });
          if (!var9.isEmpty()) {
            Iterator var12 = var9.iterator();

            String var16;
            Object var18;
            for (int var14 = 0; var12.hasNext(); var10 = var10 + String.format(var16, new Object[] {
                Integer.valueOf(var14), obj2Str(var18), var14++ < var9.size() - 1 ? ",\n" : "\n"
            })) {
              var16 = "[%d]:%s%s";
              var18 = var12.next();
            }
          }

          return var10 + "\n]";
        } else if (!(object instanceof Map)) {
          return obj2Str(object);
        } else {
          msg = simpleName + " {\n";
          Map map = (Map) object;
          Set keys = map.keySet();

          Object key;
          String itemString;
          Object value;
          for (Iterator flag = keys.iterator(); flag.hasNext(); msg =
              msg + String.format(itemString, new Object[] { obj2Str(key), obj2Str(value) })) {
            key = flag.next();
            itemString = "[%s -> %s]\n";
            value = map.get(key);
          }

          return msg + "}";
        }
      }
    }
  }

  public static <T> String obj2Str(T object) {
    if (object == null) {
      return "{object is null}";
    } else if (!object.toString().startsWith(object.getClass().getName() + "@")) {
      return object.toString();
    } else {
      StringBuilder builder = new StringBuilder(object.getClass().getSimpleName() + "{");
      Field[] fields = object.getClass().getDeclaredFields();
      Field[] var3 = fields;
      int var4 = fields.length;

      for (int var5 = 0; var5 < var4; ++var5) {
        Field field = var3[var5];
        field.setAccessible(true);
        boolean flag = false;
        String[] var8 = types;
        int var9 = var8.length;

        for (int var10 = 0; var10 < var9; ++var10) {
          String type = var8[var10];
          if (field.getType().getName().equalsIgnoreCase(type)) {
            flag = true;
            Object value = null;

            try {
              try {
                value = field.get(object);
              } catch (IllegalAccessException var17) {
                value = var17;
              }
              break;
            } finally {
              builder.append(String.format("%s=%s, ",
                  new Object[] { field.getName(), value == null ? "null" : value.toString() }));
              break;
            }
          }
        }

        if (!flag) {
          builder.append(String.format("%s=%s, ", new Object[] { field.getName(), "Object" }));
        }
      }

      return builder.replace(builder.length() - 2, builder.length() - 1, "}").toString();
    }
  }
}
