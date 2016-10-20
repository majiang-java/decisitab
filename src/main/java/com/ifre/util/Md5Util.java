package com.ifre.util;

import org.apache.commons.codec.digest.DigestUtils;

public class Md5Util {

	public static boolean md5(String mdStr,String md5) {
		boolean b = true;
		String md5Digest = DigestUtils.md5Hex(mdStr);
		
		if (!md5Digest.equals(md5)) {
			b = false;
		}
		return b;
	}
}
