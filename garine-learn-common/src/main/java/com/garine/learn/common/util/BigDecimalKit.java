package com.garine.learn.common.util;

import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * 数值计算
 *
 * @author zhangh
 * @date 2017年12月9日
 * @since
 */
public class BigDecimalKit {

    private BigDecimalKit() {
    }

    /**
     * 加法计算
     */
    public static double add(Double v1, Double... v2) {
        return add(toStr(v1), toStr(v2));
    }

    /**
     * 加法计算
     */
    public static double add(BigDecimal v1, BigDecimal... v2) {
        return add(toStr(v1), toStr(v2));
    }

    /**
     * 加法计算
     */
    public static double add(String v1, String... v2) {
        BigDecimal b1 = new BigDecimal(v1);
        for (String str : v2) {
            BigDecimal b2 = new BigDecimal(str);
            b1 = b1.add(b2);
        }
        return b1.doubleValue();
    }

    /**
     * 减法运算
     */
    public static double sub(String value1, String value2) {
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 减法运算
     */
    public static double sub(Double value1, Double value2) {
        return sub(toStr(value1), toStr(value2));
    }

    /**
     * 减法运算
     */
    public static double sub(BigDecimal value1, BigDecimal value2) {
        return sub(toStr(value1), toStr(value2));
    }
    
    /**
     * 乘法运算
     *
     * @param value1 被乘数
     * @param value2 乘数
     * @return 两个参数的积
     */
    public static double mul(Double value1, Double... value2) {
        return mul(toStr(value1), toStr(value2));
    }
    
    public static double mul(double d1, double d2, int scale) {
        return round(mul(d1, d2), scale);
    }
    
    /**
     * 乘法运算
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(String v1, String... v2) {
        BigDecimal total = new BigDecimal(v1);
        for (String str : v2) {
            BigDecimal b2 = new BigDecimal(str);
            total = total.multiply(b2);
        }
        return total.doubleValue();
    }
    
    /**
     * 乘法运算
     *
     */
    public static double mul(BigDecimal v1, BigDecimal... v2) {
        return mul(toStr(v1), toStr(v2));
    }
    
    
    /**
     * 除法运算
     */
    public static double div(Double d1, Double d2, int scale) {
        return div(toStr(d1), toStr(d2), scale);
    }
    
    /**
     * 除法运算
     */
    public static double div(BigDecimal b1, BigDecimal b2, int scale) {
        return div(toStr(b1), toStr(b2), scale);
    }
    
    /**
     * 除法运算
     *
     */
    public static double div(String value1, String value2, int scale) {
        if (scale < 0) {
            // 如果精确范围小于0，抛出异常信息。
            throw new IllegalArgumentException("精确度不能小于0");
        } else if (Double.parseDouble(value2) == 0) {
            // 如果除数为0，抛出异常信息。
            throw new IllegalArgumentException("除数不能为0");
        }
        BigDecimal b1 = new BigDecimal(value1);
        BigDecimal b2 = new BigDecimal(value2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("精确度不能小于0");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 判断是否大于0
     *
     * @param obj
     * @return
     */
    public static boolean greaterThanZero(BigDecimal obj) {
        return Objects.nonNull(obj) && BigDecimal.ZERO.compareTo(obj) < 0;
    }
    
    /**
     * null转为0
     * @param t
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Number> T nullToZero(T t) {
        return (T) MoreObjects.firstNonNull(t, 0);
    }
    
    private static String[] toStr(Object[] array) {
        return Arrays.asList(array).stream().map(t -> toStr(t)).toArray(String[]::new);
    }
    
    /**
     * 将数值转为字符转，null转0
     * @param obj
     * @return
     */
    private static String toStr(Object obj) {
        obj = MoreObjects.firstNonNull(obj, 0d);
        if (obj instanceof Double) {
            Double d = (Double) obj;
            return d.toString();
        }
        if (obj instanceof String) {
            String str = (String) obj;
            return NumberUtils.isNumber(str) ? str : "0";
        }
        if (obj instanceof BigDecimal) {
            BigDecimal b = (BigDecimal) obj;
            return b.toPlainString();
        }
        throw new IllegalArgumentException("参数格式错误");
    }
}
