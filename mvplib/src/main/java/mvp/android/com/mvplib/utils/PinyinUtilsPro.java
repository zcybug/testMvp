package mvp.android.com.mvplib.utils;

import android.content.Context;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ================================================
 * 项目名称：MVP1
 * 类 名 称：
 * 创 建 人：zhouchunyu
 * 描    述：
 * 创建时间：2017/7/28 0028  上午 10:37
 * 修改历史：
 * ================================================
 */

public class PinyinUtilsPro {

    private static Map<String, List<String>> pinyinMap = new HashMap<>();
    private static List<String> pinyin = new ArrayList<>();
    private static List<String> hpinyin = new ArrayList<>();

    private static String regx = "(,| |\\[|\\])";// 正则表达式，匹配字符串用

    public static String getPinyin() {
        return String.valueOf(pinyin).replaceAll(regx, "");
    }

    public static String getHeadPinyin() {
        return String.valueOf(hpinyin).replaceAll(regx, "");
    }

//    public static void main(String[] args) {
//        String str = "绿色中国银行长沙分行";
//        convertChineseToPinyin(str);
//        String py = getPinyin();
//        System.out.println(str + " = " + py);
//        String headP = getHeadPinyin();
//        System.out.println(headP);
//    }

    /**
     * 汉字转拼音 最大匹配优先
     *
     * @param chinese
     * @return
     */
    public static void convertChineseToPinyin(Context context, String chinese) {
        if (null != pinyinMap) {
            pinyinMap.clear();
        }
        if (null != pinyin) {
            pinyin.clear();
        }
        if (null != hpinyin) {
            hpinyin.clear();
        }
        initPinyin(context);
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] arr = chinese.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if (ch > 128) { // 非ASCII码
                // 取得当前汉字的所有全拼
                try {
                    String[] results = PinyinHelper.toHanyuPinyinStringArray(ch, defaultFormat);
                    if (results != null) { // 非中文
                        int len = results.length;
                        if (len == 1) { // 不是多音字
                            setValue(results[0]);
                        } else { // 多音字
                            // 合并同音不同声调（去重）
                            List<String> duoyinziPinyins = new ArrayList<>();// 定义一个空的数组
                            for (int k = 0; k < len; k++) {
                                if (!duoyinziPinyins.contains(results[k])) {
                                    duoyinziPinyins.add(results[k]);
                                }
                            }
                            if (duoyinziPinyins.size() == 1) {
                                setValue(duoyinziPinyins.get(0));// 如果新的集合长度是1，就取第一个
                            } else {//
                                int length = chinese.length();
                                boolean flag = false;
                                for (int x = 0; x < duoyinziPinyins.size(); x++) {
                                    String py = duoyinziPinyins.get(x);
                                    if (i + 3 <= length) { // 后向匹配2个汉字 大西洋
                                        if (matchPinyins(py, chinese, i, i + 3)) {
                                            flag = setValue(py);
                                            break;
                                        }
                                    }
                                    if (i + 2 <= length) { // 后向匹配 1个汉字 大西
                                        if (matchPinyins(py, chinese, i, i + 2)) {
                                            flag = setValue(py);
                                            break;
                                        }
                                    }
                                    if ((i - 2 >= 0) && (i + 1 <= length)) { // 前向匹配2个汉字
                                        if (matchPinyins(py, chinese, i - 2, i + 1)) {
                                            flag = setValue(py);
                                            break;
                                        }
                                    }
                                    if ((i - 1 >= 0) && (i + 1 <= length)) { // 前向匹配1个汉字
                                        // 固大
                                        if (matchPinyins(py, chinese, i - 1, i + 1)) {
                                            flag = setValue(py);
                                            break;
                                        }
                                    }
                                    if ((i - 1 >= 0) && (i + 2 <= length)) { // 前向1个，后向1个
                                        // 固大西
                                        if (matchPinyins(py, chinese, i - 1, i + 2)) {
                                            flag = setValue(py);
                                            break;
                                        }
                                    }
                                }
                                if (!flag) {
                                    // 如果都没有找到，也就是常用读音
                                    setValue(duoyinziPinyins.get(0));
                                }
                            }
                        }
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                setValue(String.valueOf(ch));
            }
        }
    }

    // 截取词组，并匹配拼音表中的词组
    private static boolean matchPinyins(String py, String chinese, int m, int n) {
        String s = chinese.substring(m, n);
        List<String> cizus = pinyinMap.get(py);
        if (cizus != null && cizus.contains(s)) {
            return true;
        }
        return false;
    }

    private static boolean setValue(String py) {
        pinyin.add(py);
        hpinyin.add(py.substring(0, 1));
        return true;
    }

    /**
     * 初始化 所有的多音字词组
     */
    public static void initPinyin(Context context) {
        // 读取多音字的全部拼音表;
        BufferedReader br = null;
        try {
            InputStream file = context.getResources().getAssets().open("duoyinzi_dic.txt");
            String s = null;
            br = new BufferedReader(new InputStreamReader(file, "UTF-8"));
            while ((s = br.readLine()) != null) {
                if (s != null) {
                    String[] arr = s.split("#");
                    String pinyin = arr[0];
                    String chinese = arr[1];
                    if (chinese != null) {
                        String[] strs = chinese.split(" ");
                        //去空
                        List<String> list = arr2List(strs);
                        pinyinMap.put(pinyin, list);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 数组转换成集合，并且去掉空格
    private static List<String> arr2List(String[] strs) {
        if (strs != null && strs.length > 0) {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < strs.length; i++) {
                if (!"".equals(strs[i].trim())) {
                    list.add(strs[i].trim());
                }
            }
            return list;
        } else {
            return null;
        }
    }
}
