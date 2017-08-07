package mvp.android.com.mvplib.utils;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Chen Haitao on 2015/7/23.
 */
public class StrUtils {
    public static final String REPORT_MEMBER_CONSUME_SEARCH_KEY = "^[0-9a-zA-Z\\u4e00-\\u9fa5\\s]{1,50}$";  // 必须为1-50个字符(中文、数字和英文)
    public static final String PHONE_REGEX = "^(\\+?\\d{2}-?)?(1[0-9])\\d{9}$";   // 手机号;
    public static final String TRUE_NAME = "^[0-9a-zA-Z\\u4e00-\\u9fa5\\s]{2,15}$";  // 必须为2-15个字符(中文、数字和英文)
    public static final String USER_NAME = "^[0-9a-zA-Z\\u4e00-\\u9fa5\\-_\\s]{3,18}$";  // 必须为3-18个字符(支持中文、英文、数字及-和_)
    //    public static final String PASSWD = "^(?![a-z]+$)(?![A-Z]+$)(?![0-9]+$)[0-9a-zA-Z\\W]\\S{6,16}$";  // 6-16位字符，包含字母、数字或符号中两种
    public static final String PASSWD = "^[0-9a-zA-Z\\W]\\S{6,12}$";  // 6-20位字符，支持字母、数字或符号的组合
    public static final String DEVICE_NO = "^[0-9a-zA-Z]{1,8}$";  // 1-6位字符，支持字母、数字
    public static final String DITITAL = "^[0-9.]+$";  //
    public static final String NUMBER = "^\\d{10,30}$";//验证n位的数字
    public static final String YY_MM_DD = "yyyy-MM-dd";
    public static final String YY_MM_DD_HH_MM = "yyyy-MM-dd  hh:mm";
    public static final String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd  hh:mm:ss";
    public static final String NUMBER_LETTER = "[^a-zA-Z0-9]";
    public static final String CHINASES = "^[0-9a-zA-Z]$";

    public static String getText(String text) {
        if (text == null) {
            return "";
        }
        return text.trim();
    }

    public static boolean isNull(String str) {
        return str == null || str.length() == 0 || str.trim().equalsIgnoreCase("null");
    }

    public static boolean isNotNull(String str) {
        return str != null && str.length() > 0 && (!str.trim().equalsIgnoreCase("null"));
    }

    public static String getDecimalFormatStr(double original) {
        DecimalFormat df = new DecimalFormat("######0.00");
        return df.format(original);
    }


    /**
     * 验证输入是否为电话号
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNumber(String phoneNum) {
//     String expression = "((^(13|15|18|14|17)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        String expression = "^(13|14|15|17|18)\\d{9}$|^(0?\\d{2,3}(\\-)?)?\\d{7,8}$|^400\\d{7}$|^800\\d{7}$";
        CharSequence inputStr = phoneNum;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (TextUtils.isEmpty(phoneNum)) {
            return false;
        }
        return matcher.matches();
    }


    /**
     * 验证邮箱
     *
     * @return true 是 false 不是
     */
    public static boolean isEmail(String strEmail) {
        String strPattern = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
        Pattern p = Pattern.compile(strPattern);
        Matcher m = p.matcher(strEmail);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是否为手机号
     *
     * @param phone
     * @return
     */
    public static boolean isMobileNum(String phone) {
        Pattern p = Pattern.compile(PHONE_REGEX);
        Matcher m = p.matcher(phone);
        return m.find();
    }

    /**
     * 根据正则表达式验证
     *
     * @param matcher 正则
     * @param str     字符串
     * @return
     */
    public static boolean match(String matcher, String str) {
        Pattern p = Pattern.compile(matcher);
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isDigital(String d) {
        return match(DITITAL, d);
    }


    public static String getSignStr(Object object) {
        if (object == null) {
            return "";
        }
        return String.format("%.2f", object);
    }

    public static String getSignStr(double d) {
        return String.format("%.2f", d);
    }


    public static String getRandomBarCode() {
        StringBuilder str = new StringBuilder("66");
        int a[] = new int[10];
        for (int i = 0; i < a.length; i++) {
            a[i] = (int) (10 * (Math.random()));
            str.append(a[i]);
        }
        str.append(getCheckCode(str.toString()));
        return str.toString();
    }

    private static String getCheckCode(String string) {
        int evenCount = 0;
        int oddCount = 0;
        for (int i = string.length() - 1; i >= 0; i--) {
            int j = string.length() - i;
            if (j % 2 == 1) {
                evenCount += Integer.parseInt(string.substring(i, i + 1));
            } else {
                oddCount += Integer.parseInt(string.substring(i, i + 1));
            }
        }
        return String.valueOf((evenCount * 3 + oddCount) % 10);
    }

    public static void setTextStyle(Context ctx, TextView tx, int resId, int val, int styleId, int start, int end) {
        SpannableString styledText = new SpannableString(String.format(ctx.getString(resId), String.valueOf(val)));
        styledText.setSpan(new TextAppearanceSpan(ctx, styleId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    public static void setTextStyle(Context ctx, TextView tx, int resId, long val, int styleId, int start, int end) {
        SpannableString styledText = new SpannableString(String.format(ctx.getString(resId), String.valueOf(val)));
        styledText.setSpan(new TextAppearanceSpan(ctx, styleId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    public static void setTextStyle(Context ctx, TextView tx, int resId, double val, int styleId, int start, int end) {
        SpannableString styledText = new SpannableString(String.format(ctx.getString(resId), StrUtils.getDecimalFormatStr(val)));
        styledText.setSpan(new TextAppearanceSpan(ctx, styleId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    public static void setTextStyle(Context ctx, TextView tx, int resId, String val, int styleId, int start, int end) {
        SpannableString styledText = new SpannableString(String.format(ctx.getString(resId), val));
        styledText.setSpan(new TextAppearanceSpan(ctx, styleId), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tx.setText(styledText, TextView.BufferType.SPANNABLE);
    }

    /**
     * input需大于7位
     *
     * @param input
     * @return
     */
    public static String setGetPhoneMask(String input) {
        if (input.length() <= 7) {
            return "";
        }
        if (StrUtils.isNotNull(input)) {
            String s = "";
            for (int i = 0; i < input.length() - 7; i++) {
                s += "*";
            }
            String maskNumber = input.substring(0, 3) + s + input.substring(input.length() - 4, input.length());
            return maskNumber;
        }
        return "";
    }

    /**
     * 时间戳毫秒数转时间字符串
     *
     * @param mill
     * @param timeStye yyyy-MM-dd HH:mm
     * @return
     */
    public static String timestampToDateAndroid(long mill, String timeStye) {
        Date date = new Date(mill);
        String strs = String.valueOf(mill);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timeStye, Locale.getDefault());
            strs = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }


    /**
     * 时间戳毫秒数转时间字符串
     *
     * @param mill
     * @param timeStye yyyy-MM-dd HH:mm
     * @return
     */
    public static String timestampToDatePhp(String mill, String timeStye) {
        String strs = String.valueOf(mill);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(timeStye, Locale.getDefault());
            strs = sdf.format(new Date(Long.parseLong(mill) * 1000));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strs;
    }

    public static Date stringToDate(String str, String pattern) {
        return new SimpleDateFormat(pattern).parse(str, new ParsePosition(0));
    }

    public static String getFormatedDistance(double distance) {
        if (distance < 100.0) {
            return "<100米";
        } else if (distance >= 100.0 && distance <= 1000.0) {
            return String.valueOf((int) distance) + "米";
        } else if (distance > 1000.0 && distance <= 20000.0) {
            return getDecimalFormatStr(distance / 1000.0) + "公里";
        } else {
            return ">20公里";
        }
    }

    public static boolean inSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year1 = calendar.get(Calendar.YEAR);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);

        calendar.setTime(date2);
        int year2 = calendar.get(Calendar.YEAR);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);

        return (year1 == year2) && (day1 == day2);
    }

    /**
     * 获取两个时间的时间差 如1天2小时30分钟
     */
    public static String getDateElapsed(Date endDate, Date startDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - startDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * 保留两位小数
     */
    public static double getRound(double _value) {
        return Math.round(_value * 100) * 0.01;
    }

    /**
     * 获得字符串的长度，中文算2个，英文算1个
     *
     * @param text
     * @return
     */
    public static int getStringLength(String text) {
        int valueLength = 0;
        String chinese = "[\u4e00-\u9fa5]";
        for (int i = 0; i < text.length(); i++) {
            String temp = text.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    public static int toInt(String value) {
        try {
            if (isNotNull(value)) {
                return Integer.parseInt(value);
            }
            return 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static float toFloat(String value) {
        try {
            if (isNotNull(value)) {
                return Float.parseFloat(value);
            }
            return 0.0f;
        } catch (NumberFormatException e) {
            return 0.00f;
        }
    }

    public static double toDouble(String value) {
        try {
            if (isNotNull(value)) {
                return Double.parseDouble(value);
            }
            return 0.0f;
        } catch (NumberFormatException e) {
            return 0.00f;
        }
    }

    /**
     * 小数转化为百分数,保留两位小数
     *
     * @param rate
     * @return
     */
    public static String decimalToPercent(double rate) {
        String str = StrUtils.getSignStr(rate);
        return Double.parseDouble(str) * 100 + "%";
    }

    /**
     * 手机号中间5位用*代替
     * $1，$2分别匹配第一个括号和第二个括号中的内容
     *
     * @param mobile
     * @return
     */
    public static String mobileFormat(String mobile) {
        if (isNull(mobile)) {
            return null;
        }
        return mobile.replaceAll("(\\d{3})\\d{5}(\\d{3})", "$1*****$2");
    }


    public static String percent(double p1, double p2) {
        String str;
        double p3 = p1 / p2;
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        str = nf.format(p3);
        return str;
    }

    public static String setHtmlRed(String startStr, String contentStr, String endStr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtils.isNotNull(startStr)) {//#aaaaaa
            stringBuilder.append("<font color='#AAAAAA'>" + startStr + "</font>");
        }
        if (StrUtils.isNotNull(contentStr)) {
            stringBuilder.append("<font color='#E14D50'>" + contentStr + "</font>");
        }
        if (StrUtils.isNotNull(endStr)) {
            stringBuilder.append("<font color='#AAAAAA'>" + endStr + "</font>");
        }
        return stringBuilder.toString();
    }


    public static String setHtml999(String startStr, String contentStr, String endStr) {
        StringBuilder stringBuilder = new StringBuilder();
        if (StrUtils.isNotNull(startStr)) {
            stringBuilder.append(startStr);
        }
        if (StrUtils.isNotNull(contentStr)) {
            stringBuilder.append("<font color='#999999'>" + contentStr + "</font>");
        }
        if (StrUtils.isNotNull(endStr)) {
            stringBuilder.append(endStr);
        }
        return stringBuilder.toString();
    }

    /**
     * 返回Blue html
     *
     * @param startStr
     * @param content
     * @param endStr
     * @return
     */
    public static String getBlueHtml(String startStr, String content, String endStr) {
        StringBuffer buffer = new StringBuffer();
        if (StrUtils.isNotNull(startStr)) {
            buffer.append(startStr);
        }
        if (StrUtils.isNotNull(content)) {
            buffer.append("<font color='#7392a0'>" + content + "</font>");
        }
        if (StrUtils.isNotNull(endStr)) {
            buffer.append(endStr);
        }
        return buffer.toString();
    }

    /**
     * 验证身份证号
     *
     * @param cardId
     * @return
     */
    public static boolean isCardID(String cardId) {
        String expression = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[a-zA-Z])$|^[a-zA-Z]{1,2}\\d{6}\\([0-9a-zAZ-Z]\\)$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(cardId);
        return matcher.matches();
    }

    // 只允许字母、数字
    public static String stringFilter(String str) throws PatternSyntaxException {
        String regEx = "[^a-zA-Z0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isCN(String str) {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            if (bytes.length == str.length()) {
                return false;
            } else {
                return true;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 国标码和区位码转换常量
    static final int GB_SP_DIFF = 160;
    //存放国标一级汉字不同读音的起始区位码
    static final int[] secPosValueList = {
            1601, 1637, 1833, 2078, 2274, 2302, 2433, 2594, 2787,
            3106, 3212, 3472, 3635, 3722, 3730, 3858, 4027, 4086,
            4390, 4558, 4684, 4925, 5249, 5600};

    //存放国标一级汉字不同读音的起始区位码对应读音
    static final char[] firstLetter = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
            't', 'w', 'x', 'y', 'z'};

    //获取一个字符串的拼音码
    public static String getFirstLetter(String oriStr) {
        String str = oriStr.toLowerCase();
        StringBuffer buffer = new StringBuffer();
        char ch;
        char[] temp;
        for (int i = 0; i < str.length(); i++) { //依次处理str中每个字符
            ch = str.charAt(i);
            temp = new char[]{ch};
            byte[] uniCode = new String(temp).getBytes();
            if (uniCode[0] < 128 && uniCode[0] > 0) { // 非汉字
                buffer.append(temp);
            } else {
                buffer.append(convert(uniCode));
            }
        }
        return buffer.toString();
    }

    /**
     * 获取一个汉字的拼音首字母。
     * GB码两个字节分别减去160，转换成10进制码组合就可以得到区位码
     * 例如汉字“你”的GB码是0xC4/0xE3，分别减去0xA0（160）就是0x24/0x43
     * 0x24转成10进制就是36，0x43是67，那么它的区位码就是3667，在对照表中读音为‘n’
     */

    static char convert(byte[] bytes) {
        char result = '-';
        int secPosValue = 0;
        int i;
        for (i = 0; i < bytes.length; i++) {
            bytes[i] -= GB_SP_DIFF;
        }
        secPosValue = bytes[0] * 100 + bytes[1];
        for (i = 0; i < 23; i++) {
            if (secPosValue >= secPosValueList[i] && secPosValue < secPosValueList[i + 1]) {
                result = firstLetter[i];
                break;
            }
        }
        return result;
    }


    /**
     * 输入汉字字符串，得到其每个字的拼音首字母
     *
     * @param chinese 汉字与其它混杂的源字符串
     * @return 首字母（不是汉字的部分原样返回）
     * @throws BadHanyuPinyinOutputFormatCombination
     */
    public static String getPinyIninitialsLetters(String chinese) throws BadHanyuPinyinOutputFormatCombination {
        String rtnStr = "";
        char singlechar;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); //取得拼音的格式为不带声调号
        for (int i = 0; i < chinese.length(); i++) {
            singlechar = chinese.charAt(i);
            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(singlechar, format);
            if (pinyin == null || pinyin.length == 0) {
                rtnStr += singlechar;
            } else {
                rtnStr += pinyin[0].substring(0, 1);//多音字时只取第一个音，并取得其拼音手字母
            }
        }
        return rtnStr;
    }
}

