package com.ifre.util;

import java.security.Key;  

import javax.crypto.Cipher;  
import javax.crypto.KeyGenerator;  
import javax.crypto.SecretKey;  
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;  

import org.apache.commons.codec.binary.Base64;  

/**
 * Aes加密工具类
 */
public class AesUtil {
	
	/** 
     * 密钥算法 
     * java6支持56位密钥，bouncycastle支持64位 
     */  
    public static final String KEY_ALGORITHM="AES";  
      
    /** 
     * 加密&解密算法/工作模式/填充方式 
     */  
    public static final String CIPHER_ALGORITHM="AES/ECB/PKCS5Padding";  
    
    public static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";
    
    public static final String CIPHER_ALGORITHM_CBC_NO = "AES/CBC/NoPadding";
      
    /** 
     *  
     * 生成密钥，java6只支持56位密钥，bouncycastle支持64位密钥 
     * @return byte[] 二进制密钥 
     */  
    public static byte[] initkey() throws Exception{  
          
        //实例化密钥生成器  
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);  
        //初始化密钥生成器，AES要求密钥长度为128位、192位、256位  
        kg.init(128);  
        //生成密钥  
        SecretKey secretKey = kg.generateKey();  
        //获取二进制密钥编码形式  
        return secretKey.getEncoded();  
    }  
    
    /** 
     * 转换密钥 
     * @param key 二进制密钥 
     * @return Key 密钥 
     */  
    public static Key toKey(byte[] key) throws Exception{  
        //生成密钥  
        SecretKey secretKey = new SecretKeySpec(key,KEY_ALGORITHM);  
        return secretKey;  
    }  
      
    /** 
     * 加密数据 
     * @param data 待加密数据 
     * @param key 密钥 
     * @return byte[] 加密后的数据 
     */  
    public static byte[] encrypt(byte[] data,byte[] key) throws Exception{  
    	
        //还原密钥  
        Key k = toKey(key);  
        /** 
         * 实例化 
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现 
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC") 
         */  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //初始化，设置为加密模式  
        cipher.init(Cipher.ENCRYPT_MODE, k);  
        //执行操作  
        return cipher.doFinal(data);  
    }  
   
    /** 
     * 解密数据 
     * @param data 待解密数据 
     * @param key 密钥 
     * @return byte[] 解密后的数据 
     */  
    public static byte[] decrypt(byte[] data,byte[] key) throws Exception{  
    	
        //还原密钥  
        Key k = toKey(key);  
        /** 
         * 实例化 
         * 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现 
         * Cipher.getInstance(CIPHER_ALGORITHM,"BC") 
         */  
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);  
        //初始化，设置为解密模式  
        cipher.init(Cipher.DECRYPT_MODE, k);  
        //执行操作  
        return cipher.doFinal(data);  
    }  
    
    /** 
     * 加密数据 
     * @param sSrc 待加密数据 
     * @param sKey 密钥 
     * @return byte[] 加密后的数据 
     */  
    public static String base64EncryptCBC(String src, String key) throws Exception {
    	return Base64.encodeBase64String(encryptCBC(src,key));
    }
    
    /** 
     * 加密数据 
     * @param sSrc 待加密数据 
     * @param sKey 密钥 
     * @return byte[] 加密后的数据 
     */  
    public static byte[] encryptCBC(String sSrc,String sKey) throws Exception {
    	
        if (sKey == null || sKey.length() != 22) {
        	throw new Exception("加密秘钥长度必须为22位");
        }
       
        byte[] raw = Base64.decodeBase64(sKey+"=");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        SecretKeySpec skeySpec = new SecretKeySpec(raw,KEY_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(raw);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes());

        return encrypted;
    }
    
    /** 
     * 解密数据 
     * @param sSrc 待解密数据 
     * @param sKey 密钥 
     * @return String 解密后的数据 
     */  
    public static String decryptCBC(String src, String key) throws Exception {
    	return decryptCBC(Base64.decodeBase64(src),key);
    }
    
    /** 
     * 解密数据 
     * @param sSrc 待解密数据 
     * @param sKey 密钥 
     * @return String 解密后的数据 
     */ 
    public static String decryptCBC(byte[] sSrc, String sKey) throws Exception {
    	
    	if (sKey == null || sKey.length() != 22) {
    		throw new Exception("加密秘钥长度必须为22位");
        }
       
    	byte[] raw = Base64.decodeBase64(sKey+"=");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, KEY_ALGORITHM);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);
        IvParameterSpec iv = new IvParameterSpec(raw);
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
        byte[] original = cipher.doFinal(sSrc);
        String originalString = new String(original);
        return originalString;
    }

    /** 
     * CBC工作模式PKCS5Padding填充方式样例
     * @return void 
     */   
    public static void example() throws Exception {  
    	
        String data = "TEST|1343432432";  
        System.out.println("待加密数据：" + data);  
        //加密密钥  
        String key = "yuweivskfk312341234567";     
        //加密数据  
        byte[] encrypt_byte_data = AesUtil.encryptCBC(data, key);  
        String encrypt_data = Base64.encodeBase64String(encrypt_byte_data);
        System.out.println("加密后数据：" + encrypt_data);  
        //解密数据  
        String decrypt_data = AesUtil.decryptCBC(encrypt_data, key);  
        System.out.println("解密后数据："+ decrypt_data);  
    }   
    
    /** 
     * @param args 
     * @throws Exception  
     */  
    public static void main(String[] args) throws Exception {  
    	example();
    }
    
}