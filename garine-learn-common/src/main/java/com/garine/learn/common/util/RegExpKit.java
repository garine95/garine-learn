package com.garine.learn.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用正则工具类
 *
 * @author zhangh
 * @date 2017年4月28日
 * @since
 */
public class RegExpKit {

    /**
     * 匹配大于0的正整数
     */
    public final static String REGEX_QUANTITY = "^\\+?[1-9][0-9]*$";

    /**
     * 匹配身份证, 中国的身份证为15位或18位
     */
    public final static String REGEX_IDCARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)";

    /**
     * 匹配email地址
     */
    public final static String REGEX_EMAIL = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";

    /**
     * 匹配长度
     */
    public static final String REGEX_LENGTH = "^([1-9][0-9]{0,3}|0)(\\.\\d{1,2})?$";

    /**
     * 匹配宽度
     */
    public static final String REGEX_WIDTH = "^([1-9][0-9]{0,3}|0)(\\.\\d{1,2})?$";

    /**
     * 匹配高度
     */
    public static final String REGEX_HEIGHT = "^([1-9][0-9]{0,3}|0)(\\.\\d{1,2})?$";

    /**
     * 匹配重量
     */
    public static final String REGEX_WEIGHT = "^([1-9][0-9]{0,3}|0)(\\.\\d{1,3})?$";

    /**
     * 匹配URL
     */
    public static final String REGEX_URL = "^(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]$";

    /**
     * 验证字符串是否匹配指定正则表达式
     */
    public static boolean validate(String content, String regExp) {
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        return matcher.matches();
    }
}
