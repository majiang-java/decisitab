package com.ifre.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;



/**
 * 签名算法工具类
 * @author 王传圣
 */
public class SignUtil {

	/**
     * MD5加密签名
     * @author 王传圣
     * @param  params 参数信息
     * @param  key 秘钥
     * @return String 签名结果
     * @throws UnsupportedEncodingException 
     */
    public static String md5Sign(Map<String, Object> params, String key) throws UnsupportedEncodingException {
        String string1 = createSign(params, false);
        String stringSignTemp = string1 + "&key=" + key;
        System.out.println(stringSignTemp);
        String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        return signValue;
    }
    /**
     * 构造签名
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String createSign(Map<String, Object> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }
   
}