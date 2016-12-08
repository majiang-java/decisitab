package com.ifre.form.api;

/**
 * 评分卡模型
 * 
 * @author CaiPeng
 *
 */
public class ApiDemoPfk {

	private String monthlyIncome;

	public String getMonthlyIncome() {
		return monthlyIncome;
	}

	public void setMonthlyIncome(String monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	@Override
	public String toString() {
		return "ApiDemoPfk [monthlyIncome=" + monthlyIncome + "]";
	}

}