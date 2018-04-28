package com.eva.common.cn;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import org.apache.commons.lang3.StringUtils;

public class PinYinTool {
    private PinYinTool() {
    }

    public static String getPinYin(String cnStr) {
        if (StringUtils.isEmpty(cnStr)) {
            return "";
        } else {
            HanyuPinyinOutputFormat fmt = new HanyuPinyinOutputFormat();
            fmt.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            fmt.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            fmt.setVCharType(HanyuPinyinVCharType.WITH_V);
            char[] charArray = cnStr.toCharArray();
            StringBuilder result = new StringBuilder();

            try {
                char[] var4 = charArray;
                int var5 = charArray.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    char ch = var4[var6];
                    if (Character.toString(ch).matches("[\\u4E00-\\u9FA5]+")) {
                        result.append(PinyinHelper.toHanyuPinyinStringArray(ch, fmt)[0]);
                    } else {
                        result.append(Character.toString(ch));
                    }
                }
            } catch (Exception var8) {
//                throw new SystemExceptionion(var8, "汉字转换为全拼出错！", new Object[0]);
            }

            return result.toString();
        }
    }

    public static String getPinYinHeadChars(String cnStr) {
        if (StringUtils.isEmpty(cnStr)) {
            return "";
        } else {
            HanyuPinyinOutputFormat fmt = new HanyuPinyinOutputFormat();
            fmt.setCaseType(HanyuPinyinCaseType.LOWERCASE);
            fmt.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
            fmt.setVCharType(HanyuPinyinVCharType.WITH_V);
            char[] charArray = cnStr.toCharArray();
            StringBuilder result = new StringBuilder();

            try {
                char[] var4 = charArray;
                int var5 = charArray.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    char ch = var4[var6];
                    String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(ch, fmt);
                    if (pinyinArray != null) {
                        result.append(pinyinArray[0].charAt(0));
                    } else {
                        result.append(ch);
                    }
                }
            } catch (Exception var9) {
//                throw new SystemException(var9, "汉字的首字母提取出错！", new Object[0]);
            }

            return result.toString();
        }
    }

}
