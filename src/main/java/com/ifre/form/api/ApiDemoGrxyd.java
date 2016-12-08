package com.ifre.form.api;

/**
 * 个人信用贷
 * 
 * @author CaiPeng
 *
 */
public class ApiDemoGrxyd {

	private String gender;
	private String age;
	private String isOpen;
	private String liveCase;
	private String car;
	private String compJoinYear;
	private String isNative;
	private String creditCardAccount;
	private String queryTimesOne;
	private String rate;
	private String monthlyPay;
	private String monthlyPayment;

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getLiveCase() {
		return liveCase;
	}

	public void setLiveCase(String liveCase) {
		this.liveCase = liveCase;
	}

	public String getCar() {
		return car;
	}

	public void setCar(String car) {
		this.car = car;
	}

	public String getCompJoinYear() {
		return compJoinYear;
	}

	public void setCompJoinYear(String compJoinYear) {
		this.compJoinYear = compJoinYear;
	}

	public String getIsNative() {
		return isNative;
	}

	public void setIsNative(String isNative) {
		this.isNative = isNative;
	}

	public String getCreditCardAccount() {
		return creditCardAccount;
	}

	public void setCreditCardAccount(String creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	public String getQueryTimesOne() {
		return queryTimesOne;
	}

	public void setQueryTimesOne(String queryTimesOne) {
		this.queryTimesOne = queryTimesOne;
	}

	public String getRate() {
		//<option value="-99">保密</option>
		if("0".equals(monthlyPay) || "0".equals(monthlyPayment)){
			rate = "-99";
		}else{
			double temprate = Double.parseDouble(monthlyPay)/Double.parseDouble(monthlyPayment);
			if(temprate > 0 && temprate <= 1){
				//<option value="1">大于等于0小于等于1</option>
				rate = "1";
			}else if(temprate > 1 && temprate <= 2){
				//<option value="2">大于1小于等于2</option>
				rate = "2";
			}else if(temprate > 2 && temprate <= 3){
				//<option value="3">大于2小于等于3</option>
				rate = "3";
			}else if(temprate > 3 && temprate <= 7){
				//<option value="4">大于3小于等于7</option>
				rate = "4";
			}else if(temprate > 7){
				//<option value="5">大于7</option>
				rate = "5";
			}else{
				rate = "-99";
			}
		}
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getMonthlyPay() {
		return monthlyPay;
	}

	public void setMonthlyPay(String monthlyPay) {
		this.monthlyPay = monthlyPay;
	}

	public String getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(String monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

}