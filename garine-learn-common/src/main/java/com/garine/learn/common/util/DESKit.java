package com.garine.learn.common.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * DES 算法工具类(1972美国IBM研制，对称加密算法)
 * 
 * @author zhangh
 * @date 2018年5月5日
 * @since
 */
public class DESKit {
    /**
     * 加密块链模式CBC
     */
    private static final String CIPHER_ALGORITHM_CBC = "DES/CBC/PKCS5Padding";

    /**
     * 算法名称   
     */
    private static final String KEY_ALGORITHM = "DES";
    
    /**
     * 加密
     * @param bytP 原文
     * @param bytKey 密钥
     * @return
     * @throws Exception
     */
    public static byte[] encrypt(byte[] bytP, byte[] bytKey) throws Exception {
        Key desKey = toKey(bytKey);
        IvParameterSpec zeroIv = new IvParameterSpec(bytKey);
        Cipher cip = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cip.init(Cipher.ENCRYPT_MODE, desKey, zeroIv);
        return cip.doFinal(bytP);
    }
    
    
    /**  
     * 生成key
     * @param key  
     * @return  
     * @throws Exception   
     */    
    private static Key toKey(byte[] key) throws Exception {    
        DESKeySpec des = new DESKeySpec(key);    
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);    
        SecretKey secretKey = keyFactory.generateSecret(des);    
        return secretKey;    
    }    
    
    /**
     * 解密
     * @param data 需要解密的字节数组
     * @param key 密钥
     * @return
     * @throws Exception 
     */
    public static byte[] decrypt(byte[] data, byte[] bytKey) throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(bytKey);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        cipher.init(Cipher.DECRYPT_MODE, toKey(bytKey), zeroIv);
        return cipher.doFinal(data);
    }
}
