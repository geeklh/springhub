package com.geekli.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class CommonUtil {


    /**
     * 乘法
     */
    public static BigDecimal mul(String arg1, String arg2) {
        if (StringUtils.isEmpty(arg1)) {
            arg1 = "0.0";
        }
        if (StringUtils.isEmpty(arg2)) {
            arg2 = "0.0";
        }
        BigDecimal big1 = new BigDecimal(arg1);
        BigDecimal big2 = new BigDecimal(arg2);
        BigDecimal big3 = big1.multiply(big2);
        return big3;
    }

    /**
     * 减法
     */
    public static BigDecimal sub(String arg1, String arg2) {
        if (StringUtils.isEmpty(arg1)) {
            arg1 = "0.0";
        }
        if (StringUtils.isEmpty(arg2)) {
            arg2 = "0.0";
        }
        BigDecimal big1 = new BigDecimal(arg1);
        BigDecimal big2 = new BigDecimal(arg2);
        BigDecimal big3 = big1.subtract(big2);
        return big3;
    }

    /**
     * 加法
     */
    public static BigDecimal add(String arg1, String arg2) {
        if (StringUtils.isEmpty(arg1)) {
            arg1 = "0.0";
        }
        if (StringUtils.isEmpty(arg2)) {
            arg2 = "0.0";
        }
        BigDecimal big1 = new BigDecimal(arg1);
        BigDecimal big2 = new BigDecimal(arg2);
        BigDecimal big3 = big1.add(big2);
        return big3;
    }

    /**
     * 加法
     */
    public static String add2(String arg1, String arg2) {
        if (StringUtils.isEmpty(arg1)) {
            arg1 = "0.0";
        }
        if (StringUtils.isEmpty(arg2)) {
            arg2 = "0.0";
        }
        BigDecimal big1 = new BigDecimal(arg1);
        BigDecimal big2 = new BigDecimal(arg2);
        BigDecimal big3 = big1.add(big2);
        return big3.toString();
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");// 去掉多余的0
            s = s.replaceAll("[.]$", "");// 如最后一位是.则去掉
        }
        return s;
    }
}
