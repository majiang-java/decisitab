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
public class StringUtils
{
  

  /**
   * Checks if is numbers or letters.
   *
   * @param charSequence the char sequence
   * @return true, if is numbers or letters
   */
  public static boolean isNumbersOrLetters(String charSequence)
  {
    if (charSequence == null) {
      return false;
    }
    Matcher matcher = Pattern.compile("^[0-9a-zA-Z]+$").matcher(charSequence);
    return matcher.find();
  }

  /**
   * Checks if is numbers.
   *
   * @param charSequence the char sequence
   * @return true, if is numbers
   */
  public static boolean isNumbers(String charSequence)
  {
    if (charSequence == null) {
      return false;
    }
    Matcher matcher = Pattern.compile("^[0-9]+$").matcher(charSequence);
    return matcher.find();
  }

  /**
   * The main method.
   *
   * @param args the arguments
   */
  public static final void main(String[] args)
  {
    System.out.println(isNumbersOrLetters("223DD!"));
    System.out.println(isNumbers("223"));
  }
}