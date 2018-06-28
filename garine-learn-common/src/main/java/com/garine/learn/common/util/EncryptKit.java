/*
 * Copyright (c) 2016 4PX Information Technology Co.,Ltd. All rights reserved.
 */
package com.garine.learn.common.util;

import java.security.MessageDigest;

/**
 * 加密方法工具类
 *
 * @author zhoujy
 * @date 2017年7月24日
 */
public class EncryptKit {

    /**
     * md5的32位小写算法
     *
     * @param express 需要加密字符串
     */
    public static String md5Encrypt(String express) {
        byte[] expressBytes = express.getBytes();
        String des = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] result = md.digest(expressBytes);
            StringBuilder buf = new StringBuilder();
            for (int i = 0; i < result.length; i++) {
                byte b = result[i];
                buf.append(String.format("%02X", b));
            }
            des = buf.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("md5 failure");
        }
        return des;
    }

    public static void main(String[] args) {
        String str ="123456789000001Print";

        System.out.println(EncryptKit.md5Encrypt(str));
    }

}
