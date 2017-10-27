package mvp.android.com.mvplib.version;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/10/27 0027  下午 3:45
 * 修改历史：
 * ================================================
 */

public class KitCheck {

  private static String[] id_card_province = new String[]{"11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91"};
  private static int[] id_card_power = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

  public KitCheck() {
  }

  public static boolean isNull(Object o) {
    return o == null;
  }

  public static boolean isEmpty(CharSequence str) {
    return isNull(str) || str.length() == 0;
  }

  public static boolean isEmpty(Object[] os) {
    return isNull(os) || os.length == 0;
  }

  public static boolean isEmpty(Collection<?> l) {
    return isNull(l) || l.isEmpty();
  }

  public static boolean isEmpty(Map<?, ?> m) {
    return isNull(m) || m.isEmpty();
  }

  public static boolean isEmpty(String str) {
    return isNull(str) || str.length() == 0 || str.equals("null");
  }

  public static boolean isPhone(String text) {
    return isMatch(text, "1[0-9]{10}");
  }

  public static boolean isEmail(String text) {
    return isMatch(text, "[\\w!#$%&\'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&\'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
  }

  public static boolean isDate(String text) {
    return isMatch(text, "([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8])))");
  }

  public static boolean isDigital(String str) {
    return isMatch(str, "^[0-9]*$");
  }

  public static boolean isMatch(String text, String regular) {
    return !isEmpty(text) && text.matches(regular);
  }

  public static boolean isCorrectFile(File file, String md5) {
    if(file != null && !isEmpty(md5)) {
      FileInputStream in = null;

      try {
        in = new FileInputStream(file);
        MappedByteBuffer e = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(e);
        BigInteger bi = new BigInteger(1, md.digest());
        boolean var6 = bi.toString(16).equals(md5.toLowerCase());
        return var6;
      } catch (Exception var16) {
        var16.printStackTrace();
      } finally {
        if(null != in) {
          try {
            in.close();
          } catch (Exception var15) {
            var15.printStackTrace();
          }
        }

      }

      return false;
    } else {
      return false;
    }
  }

  public static boolean isIdCard(String idCard) {
    return isEmpty(idCard)?false:(idCard.length() == 15?isIdCard15(idCard):isIdCard18(idCard));
  }

  public static boolean isIdCard18(String idCard) {
    if(isEmpty(idCard)) {
      return false;
    } else if(idCard.length() != 18) {
      return false;
    } else {
      String idCard17 = idCard.substring(0, 17);
      if(!isDigital(idCard17)) {
        return false;
      } else {
        idCard = idCard.toUpperCase();
        String provinceId = idCard.substring(0, 2);
        if(!checkProvinceId(provinceId)) {
          return false;
        } else {
          String birthday = idCard.substring(6, 14);
          SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

          try {
            Date idCard18Code = sdf.parse(birthday);
            String c = sdf.format(idCard18Code);
            if(!c.equals(birthday)) {
              return false;
            }
          } catch (ParseException var10) {
            return false;
          }

          String idCard18Code1 = idCard.substring(17, 18);
          char[] c1 = idCard17.toCharArray();
          int[] bit = converCharToInt(c1);
          boolean sum17 = false;
          int sum171 = getPowerSum(bit);
          String checkCode = getCheckCodeBySum(sum171);
          return null == checkCode?false:idCard18Code1.equalsIgnoreCase(checkCode);
        }
      }
    }
  }

  public static boolean isIdCard15(String idCard) {
    if(isEmpty(idCard)) {
      return false;
    } else if(idCard.length() != 15) {
      return false;
    } else if(!isDigital(idCard)) {
      return false;
    } else {
      String provinceId = idCard.substring(0, 2);
      if(!checkProvinceId(provinceId)) {
        return false;
      } else {
        String birthday = idCard.substring(6, 12);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");

        try {
          Date e1 = sdf.parse(birthday);
          String tmpDate = sdf.format(e1);
          return tmpDate.equals(birthday);
        } catch (ParseException var6) {
          return false;
        }
      }
    }
  }

  public static String idCardTo18(String idCard) {
    if(isEmpty(idCard)) {
      return null;
    } else if(idCard.length() != 15) {
      return null;
    } else if(!isDigital(idCard)) {
      return null;
    } else {
      String provinceId = idCard.substring(0, 2);
      if(!checkProvinceId(provinceId)) {
        return null;
      } else {
        String birthday = idCard.substring(6, 12);
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        Date birthDate = null;

        try {
          birthDate = sdf.parse(birthday);
          String calendar = sdf.format(birthDate);
          if(!calendar.equals(birthday)) {
            return null;
          }
        } catch (ParseException var12) {
          return null;
        }

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(birthDate);
        String year = String.valueOf(calendar1.get(1));
        String idcard17 = idCard.substring(0, 6) + year + idCard.substring(8);
        char[] c = idcard17.toCharArray();
        String checkCode = "";
        int[] bit = converCharToInt(c);
        boolean sum17 = false;
        int sum171 = getPowerSum(bit);
        checkCode = getCheckCodeBySum(sum171);
        if(null == checkCode) {
          return null;
        } else {
          idcard17 = idcard17 + checkCode;
          return idcard17;
        }
      }
    }
  }

  private static boolean checkProvinceId(String provinceId) {
    String[] var1 = id_card_province;
    int var2 = var1.length;

    for(int var3 = 0; var3 < var2; ++var3) {
      String id = var1[var3];
      if(id.equals(provinceId)) {
        return true;
      }
    }

    return false;
  }

  private static int getPowerSum(int[] bit) {
    int sum = 0;
    if(id_card_power.length != bit.length) {
      return sum;
    } else {
      for(int i = 0; i < bit.length; ++i) {
        for(int j = 0; j < id_card_power.length; ++j) {
          if(i == j) {
            sum += bit[i] * id_card_power[j];
          }
        }
      }

      return sum;
    }
  }

  private static String getCheckCodeBySum(int sum17) {
    String checkCode = null;
    switch(sum17 % 11) {
      case 0:
        checkCode = "1";
        break;
      case 1:
        checkCode = "0";
        break;
      case 2:
        checkCode = "X";
        break;
      case 3:
        checkCode = "9";
        break;
      case 4:
        checkCode = "8";
        break;
      case 5:
        checkCode = "7";
        break;
      case 6:
        checkCode = "6";
        break;
      case 7:
        checkCode = "5";
        break;
      case 8:
        checkCode = "4";
        break;
      case 9:
        checkCode = "3";
        break;
      case 10:
        checkCode = "2";
    }

    return checkCode;
  }

  private static int[] converCharToInt(char[] c) throws NumberFormatException {
    int[] a = new int[c.length];
    int k = 0;
    char[] var3 = c;
    int var4 = c.length;

    for(int var5 = 0; var5 < var4; ++var5) {
      char temp = var3[var5];
      a[k++] = Integer.parseInt(String.valueOf(temp));
    }

    return a;
  }
}
