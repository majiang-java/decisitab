/**
*@project name: scoreModel
*@file name: StringUtils.java
*@Date: 2015-9-18
*@Author: caojianmiao
*@Copyright: 2015 www.ifre.com.cn Inc. All rights reserved.
*Attention：本内容仅限于数信互融科技有限公司内部传阅，禁止外泄以及用于其他的商业目的
*/

package com.ifre.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The Class StringUtils.
 */
public class StringUtils {

	/**
	 * Checks if is numbers or letters.
	 *
	 * @param charSequence
	 *            the char sequence
	 * @return true, if is numbers or letters
	 */
	public static boolean isNumbersOrLetters(String charSequence) {
		if (charSequence == null) {
			return false;
		}
		Matcher matcher = Pattern.compile("^[0-9a-zA-Z]+$").matcher(charSequence);
		return matcher.find();
	}

	/**
	 * Checks if is numbers.
	 *
	 * @param charSequence
	 *            the char sequence
	 * @return true, if is numbers
	 */
	public static boolean isNumbers(String charSequence) {
		if (charSequence == null) {
			return false;
		}
		Matcher matcher = Pattern.compile("^[0-9]+$").matcher(charSequence);
		return matcher.find();
	}

	private static boolean isMatch(String regex, String orginal) {
		if (orginal == null || orginal.trim().equals("")) {
			return false;
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher isNum = pattern.matcher(orginal);
		return isNum.matches();
	}

	public static boolean isPositiveInteger(String orginal) {
		return isMatch("^\\+{0,1}[1-9]\\d*", orginal);
	}

	public static boolean isNegativeInteger(String orginal) {
		return isMatch("^-[1-9]\\d*", orginal);
	}

	public static boolean isWholeNumber(String orginal) {
		return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);
	}

	public static boolean isPositiveDecimal(String orginal) {
		return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isNegativeDecimal(String orginal) {
		return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);
	}

	public static boolean isDecimal(String orginal) {
		return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);
	}

	public static boolean isRealNumber(String orginal) {
		return isWholeNumber(orginal) || isDecimal(orginal);
	}

	/**
	 * The main method.
	 *
	 * @param args
	 *            the arguments
	 */
	public static final void main(String[] args) {
		System.out.println(isNumbersOrLetters("223DD!"));
		System.out.println(isNumbers("223"));
		System.out.println(isRealNumber("223.3"));
	}
}