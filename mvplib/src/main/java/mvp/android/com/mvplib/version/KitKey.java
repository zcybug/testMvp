package mvp.android.com.mvplib.version;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:48
 * 修改历史：
 * ================================================
 */

public class KitKey {
  private static final String AES_KEY_DEFAULT = "abs.aes.default.";

  public KitKey() {
  }

  public static String aes2Hex(String s) {
    return aes2Hex(s, "abs.aes.default.");
  }

  public static String aes2Hex(String s, String k) {
    byte[] bs = aes2(s, k);
    return bs == null ? s : byte2Hex(bs);
  }

  public static byte[] aes2(String s) {
    return aes2(s, "abs.aes.default.");
  }

  public static byte[] aes2(String s, String k) {
    try {
      Cipher e = Cipher.getInstance("AES");
      e.init(1, toKey(k));
      return e.doFinal(s.getBytes("UTF-8"));
    } catch (Exception var3) {
      var3.printStackTrace();
      return null;
    }
  }

  public static String aes4Hex(String s) {
    return aes4Hex(s, "abs.aes.default.");
  }

  public static String aes4Hex(String s, String k) {
    byte[] bs = hex2Byte(s);
    if (bs == null) {
      return s;
    } else {
      byte[] ss = aes4(bs, k);
      return ss == null ? s : new String(ss);
    }
  }

  public static byte[] aes4(byte[] b) {
    return aes4(b, "abs.aes.default.");
  }

  public static byte[] aes4(byte[] b, String k) {
    try {
      Cipher e = Cipher.getInstance("AES");
      e.init(2, toKey(k));
      return e.doFinal(b);
    } catch (Exception var3) {
      var3.printStackTrace();
      return null;
    }
  }

  private static SecretKeySpec toKey(String k) throws Exception {
    int kl = k.length();
    if (kl > 16) {
      k = k.substring(0, 16);
    } else {
      for (int i = kl; i < 16; ++i) {
        k = k + "0";
      }
    }

    return new SecretKeySpec(k.getBytes(), "AES/CBC/PKCS5PADDING");
  }

  public static String byte2Hex(byte[] buf) {
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < buf.length; ++i) {
      String hex = Integer.toHexString(buf[i] & 255);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }

      sb.append(hex.toUpperCase());
    }

    return sb.toString();
  }

  public static byte[] hex2Byte(String hex) {
    if (hex.length() < 1) {
      return null;
    } else {
      try {
        byte[] e = new byte[hex.length() / 2];

        for (int i = 0; i < hex.length() / 2; ++i) {
          int high = Integer.parseInt(hex.substring(i * 2, i * 2 + 1), 16);
          int low = Integer.parseInt(hex.substring(i * 2 + 1, i * 2 + 2), 16);
          e[i] = (byte) (high * 16 + low);
        }

        return e;
      } catch (Exception var5) {
        var5.printStackTrace();
        return null;
      }
    }
  }
}
