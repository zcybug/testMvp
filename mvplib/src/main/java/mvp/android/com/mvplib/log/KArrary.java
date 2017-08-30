package mvp.android.com.mvplib.log;

import android.util.Pair;
import java.util.Arrays;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/8/30 0030  上午 9:15
 * 修改历史：
 * ================================================
 */

public class KArrary {

  public KArrary() {
  }

  public static int getArrayDimension(Object objects) {
    int dim = 0;

    for (int i = 0; i < objects.toString().length() && objects.toString().charAt(i) == 91; ++i) {
      ++dim;
    }

    return dim;
  }

  public static Pair<Pair<Integer, Integer>, String> arrayToObject(Object object) {
    StringBuilder builder = new StringBuilder();
    boolean cross = false;
    boolean vertical = false;
    int var6;
    int var7;
    int var9;
    int var10;
    if (object instanceof int[][]) {
      int[][] objects = (int[][]) ((int[][]) object);
      var9 = objects.length;
      var10 = var9 == 0 ? 0 : objects[0].length;
      int[][] var5 = objects;
      var6 = objects.length;

      for (var7 = 0; var7 < var6; ++var7) {
        int[] objects1 = var5[var7];
        builder.append(arrayToString(objects1).second + "\n");
      }
    } else if (object instanceof byte[][]) {
      byte[][] var11 = (byte[][]) ((byte[][]) object);
      var9 = var11.length;
      var10 = var9 == 0 ? 0 : var11[0].length;
      byte[][] var14 = var11;
      var6 = var11.length;

      for (var7 = 0; var7 < var6; ++var7) {
        byte[] var27 = var14[var7];
        builder.append(arrayToString(var27).second + "\n");
      }
    } else if (object instanceof short[][]) {
      short[][] var12 = (short[][]) ((short[][]) object);
      var9 = var12.length;
      var10 = var9 == 0 ? 0 : var12[0].length;
      short[][] var16 = var12;
      var6 = var12.length;

      for (var7 = 0; var7 < var6; ++var7) {
        short[] var28 = var16[var7];
        builder.append(arrayToString(var28).second + "\n");
      }
    } else if (object instanceof long[][]) {
      long[][] var13 = (long[][]) ((long[][]) object);
      var9 = var13.length;
      var10 = var9 == 0 ? 0 : var13[0].length;
      long[][] var18 = var13;
      var6 = var13.length;

      for (var7 = 0; var7 < var6; ++var7) {
        long[] var29 = var18[var7];
        builder.append(arrayToString(var29).second + "\n");
      }
    } else if (object instanceof float[][]) {
      float[][] var15 = (float[][]) ((float[][]) object);
      var9 = var15.length;
      var10 = var9 == 0 ? 0 : var15[0].length;
      float[][] var20 = var15;
      var6 = var15.length;

      for (var7 = 0; var7 < var6; ++var7) {
        float[] var30 = var20[var7];
        builder.append(arrayToString(var30).second + "\n");
      }
    } else if (object instanceof double[][]) {
      double[][] var17 = (double[][]) ((double[][]) object);
      var9 = var17.length;
      var10 = var9 == 0 ? 0 : var17[0].length;
      double[][] var22 = var17;
      var6 = var17.length;

      for (var7 = 0; var7 < var6; ++var7) {
        double[] var31 = var22[var7];
        builder.append(arrayToString(var31).second + "\n");
      }
    } else if (object instanceof boolean[][]) {
      boolean[][] var19 = (boolean[][]) ((boolean[][]) object);
      var9 = var19.length;
      var10 = var9 == 0 ? 0 : var19[0].length;
      boolean[][] var24 = var19;
      var6 = var19.length;

      for (var7 = 0; var7 < var6; ++var7) {
        boolean[] var32 = var24[var7];
        builder.append(arrayToString(var32).second + "\n");
      }
    } else if (object instanceof char[][]) {
      char[][] var21 = (char[][]) ((char[][]) object);
      var9 = var21.length;
      var10 = var9 == 0 ? 0 : var21[0].length;
      char[][] var25 = var21;
      var6 = var21.length;

      for (var7 = 0; var7 < var6; ++var7) {
        char[] var33 = var25[var7];
        builder.append(arrayToString(var33).second + "\n");
      }
    } else {
      Object[][] var23 = (Object[][]) ((Object[][]) object);
      var9 = var23.length;
      var10 = var9 == 0 ? 0 : var23[0].length;
      Object[][] var26 = var23;
      var6 = var23.length;

      for (var7 = 0; var7 < var6; ++var7) {
        Object[] var34 = var26[var7];
        builder.append(arrayToString(var34).second + "\n");
      }
    }

    return Pair.create(Pair.create(Integer.valueOf(var9), Integer.valueOf(var10)),
        builder.toString());
  }

  public static Pair arrayToString(Object object) {
    StringBuilder builder = new StringBuilder("[");
    boolean length = false;
    int var5;
    int var6;
    int var9;
    if (object instanceof int[]) {
      int[] objects = (int[]) ((int[]) object);
      var9 = objects.length;
      int[] var4 = objects;
      var5 = objects.length;

      for (var6 = 0; var6 < var5; ++var6) {
        int item = var4[var6];
        builder.append(item + ",\t");
      }
    } else if (object instanceof byte[]) {
      byte[] var10 = (byte[]) ((byte[]) object);
      var9 = var10.length;
      byte[] var13 = var10;
      var5 = var10.length;

      for (var6 = 0; var6 < var5; ++var6) {
        byte var26 = var13[var6];
        builder.append(var26 + ",\t");
      }
    } else if (object instanceof short[]) {
      short[] var11 = (short[]) ((short[]) object);
      var9 = var11.length;
      short[] var15 = var11;
      var5 = var11.length;

      for (var6 = 0; var6 < var5; ++var6) {
        short var27 = var15[var6];
        builder.append(var27 + ",\t");
      }
    } else if (object instanceof long[]) {
      long[] var12 = (long[]) ((long[]) object);
      var9 = var12.length;
      long[] var17 = var12;
      var5 = var12.length;

      for (var6 = 0; var6 < var5; ++var6) {
        long var28 = var17[var6];
        builder.append(var28 + ",\t");
      }
    } else if (object instanceof float[]) {
      float[] var14 = (float[]) ((float[]) object);
      var9 = var14.length;
      float[] var19 = var14;
      var5 = var14.length;

      for (var6 = 0; var6 < var5; ++var6) {
        float var29 = var19[var6];
        builder.append(var29 + ",\t");
      }
    } else if (object instanceof double[]) {
      double[] var16 = (double[]) ((double[]) object);
      var9 = var16.length;
      double[] var21 = var16;
      var5 = var16.length;

      for (var6 = 0; var6 < var5; ++var6) {
        double var30 = var21[var6];
        builder.append(var30 + ",\t");
      }
    } else if (object instanceof boolean[]) {
      boolean[] var18 = (boolean[]) ((boolean[]) object);
      var9 = var18.length;
      boolean[] var23 = var18;
      var5 = var18.length;

      for (var6 = 0; var6 < var5; ++var6) {
        boolean var31 = var23[var6];
        builder.append(var31 + ",\t");
      }
    } else if (object instanceof char[]) {
      char[] var20 = (char[]) ((char[]) object);
      var9 = var20.length;
      char[] var24 = var20;
      var5 = var20.length;

      for (var6 = 0; var6 < var5; ++var6) {
        char var32 = var24[var6];
        builder.append(var32 + ",\t");
      }
    } else {
      Object[] var22 = (Object[]) ((Object[]) object);
      var9 = var22.length;
      Object[] var25 = var22;
      var5 = var22.length;

      for (var6 = 0; var6 < var5; ++var6) {
        Object var33 = var25[var6];
        builder.append(KLog.obj2Str(var33) + ",\t");
      }
    }

    return Pair.create(Integer.valueOf(var9),
        builder.replace(builder.length() - 2, builder.length(), "]").toString());
  }

  public static boolean isArray(Object object) {
    return object.getClass().isArray();
  }

  public static char getType(Object object) {
    if (isArray(object)) {
      String str = object.toString();
      return str.substring(str.lastIndexOf("[") + 1, str.lastIndexOf("[") + 2).charAt(0);
    } else {
      return '\u0000';
    }
  }

  private static void traverseArray(StringBuilder result, Object object) {
    if (!isArray(object)) {
      result.append(object.toString());
    } else if (getArrayDimension(object) == 1) {
      switch (getType(object)) {
        case 'B':
          result.append(Arrays.toString((byte[]) ((byte[]) object))).append("\n");
          return;
        case 'D':
          result.append(Arrays.toString((double[]) ((double[]) object))).append("\n");
          return;
        case 'F':
          result.append(Arrays.toString((float[]) ((float[]) object))).append("\n");
          return;
        case 'I':
          result.append(Arrays.toString((int[]) ((int[]) object))).append("\n");
          return;
        case 'J':
          result.append(Arrays.toString((long[]) ((long[]) object))).append("\n");
          return;
        case 'L':
          result.append(Arrays.toString((Object[]) ((Object[]) object))).append("\n");
        case 'C':
        case 'E':
        case 'G':
        case 'H':
        case 'K':
        case 'M':
        case 'N':
        case 'O':
        case 'P':
        case 'Q':
        case 'R':
        case 'T':
        case 'U':
        case 'V':
        case 'W':
        case 'X':
        case 'Y':
        default:
          return;
        case 'S':
          result.append(Arrays.toString((short[]) ((short[]) object))).append("\n");
          return;
        case 'Z':
          result.append(Arrays.toString((boolean[]) ((boolean[]) object))).append("\n");
      }
    } else {
      for (int i = 0; i < ((Object[]) ((Object[]) object)).length; ++i) {
        traverseArray(result, ((Object[]) ((Object[]) object))[i]);
      }
    }
  }

  public static String traverseArray(Object object) {
    StringBuilder result = new StringBuilder();
    traverseArray(result, object);
    return result.toString();
  }
}
