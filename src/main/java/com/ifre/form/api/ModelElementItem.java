package com.ifre.form.api;

/**
 * 模型变量
 * 
 * @author CaiPeng
 *
 */
public class ModelElementItem {

	/** 变量取值 **/
	private String value;
	// 码值前端使用字符串比较，等于触发
	/** 码值 **/
	private String codeValue;
	/** 分值不传给前端，仅仅后端使用  **/
	private int score;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCodeValue() {
		//计算码值算法可以优化，先使用简单算法
		try {
			String [] stringArr= codeValue.split(","); 
			return stringArr[1];
		} catch (Exception e) {
			return codeValue;
		}
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "ModelElementItem [value=" + value + ", codeValue=" + codeValue + ", score=" + score + "]";
	}

}