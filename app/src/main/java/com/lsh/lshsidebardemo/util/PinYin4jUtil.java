package com.lsh.lshsidebardemo.util;

import com.lsh.lshsidebardemo.util.CommUtil;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by hua on 2016/10/14.
 */

public class PinYin4jUtil {
    private HanyuPinyinOutputFormat format;
    private static final char DEFAULT_CHAR = '#';
//    private static final String DEFAULT_CHAR = "#";

    public PinYin4jUtil() {
        format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//声调转数字
        format.setVCharType(HanyuPinyinVCharType.WITH_V);//ü转v

    }

    public PinYin4jUtil(HanyuPinyinOutputFormat format) {
        this.format = format;
    }

    //张三--》Z
    //非汉字转换->#
    public String convertHeadChar(String str) {
        if (str != null) {
            str = str.trim();
        }
        if (CommUtil.isEmpty(str)) {
            return String.valueOf(DEFAULT_CHAR);
        }
        return String.valueOf(getHeadCharBySingleChar(str.charAt(0)));
    }

    //张三-->ZHANGSAN
    //123->#123
    //sdf->Sdf
    public String getStrAllPinYin(String str) {
        if (str != null) {
            str = str.trim();
        }
        if (CommUtil.isEmpty(str)) {
            return String.valueOf(DEFAULT_CHAR);
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char currentChar = str.charAt(i);
            try {
                String[] array = getPinYinArray(currentChar);
                if (array == null) {
                    if (i == 0) {
                        builder.append(toUpperCase(currentChar));
                    } else {
                        builder.append(currentChar);
                    }
                } else {
                    builder.append(array[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination ex) {
                ex.printStackTrace();
                builder.append(currentChar);
            }
        }

        return builder.toString();
    }

    //非字母添加 #，字母转大写
    private String toUpperCase(char c) {//非汉字
        StringBuilder stringBuilder = new StringBuilder();
        if (!Character.isLetter(c)) {
            stringBuilder.append(DEFAULT_CHAR);
            stringBuilder.append(c);
        } else {
            stringBuilder.append(Character.toUpperCase(c));
        }
        return stringBuilder.toString();
    }

    //张->Z
    //z->Z
    //1->#
    //@->#
    private char getHeadCharBySingleChar(char c) {
        try {
            String[] array = getPinYinArray(c);
            if (array == null) {//非汉字
                if (Character.isLetter(c)) {
                    return Character.toUpperCase(c);
                } else {
                    return DEFAULT_CHAR;
                }

            } else {
                return array[0].charAt(0);
            }

        } catch (BadHanyuPinyinOutputFormatCombination ex) {
            ex.printStackTrace();
            return DEFAULT_CHAR;
        }
    }

    //张
    private String[] getPinYinArray(char c) throws BadHanyuPinyinOutputFormatCombination {
        return PinyinHelper.toHanyuPinyinStringArray(c, format);
    }


}
