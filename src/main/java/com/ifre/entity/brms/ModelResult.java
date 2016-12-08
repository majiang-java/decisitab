package com.ifre.entity.brms;

/**
 * 模型执行结果
 * 
 * @author CaiPeng
 *
 */
public class ModelResult implements java.io.Serializable {
	/**
	 * serialVersionUID:TODO
	 */
	private static final long serialVersionUID = 1L;
	private String prodId;
	private String prodName;
	private int num;
	private int score;
	/**评分等级**/
	private String level;
	private int yearNum;
	private int monthNum;
	private int dayNum;
	private int weekNum;

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public String getProdName() {
		return prodName;
	}

	public void setProdName(String prodName) {
		this.prodName = prodName;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getYearNum() {
		return yearNum;
	}

	public void setYearNum(int yearNum) {
		this.yearNum = yearNum;
	}

	public int getMonthNum() {
		return monthNum;
	}

	public void setMonthNum(int monthNum) {
		this.monthNum = monthNum;
	}

	public int getDayNum() {
		return dayNum;
	}

	public void setDayNum(int dayNum) {
		this.dayNum = dayNum;
	}

	public int getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(int weekNum) {
		this.weekNum = weekNum;
	}

	@Override
	public String toString() {
		return "ModelResult [prodId=" + prodId + ", prodName=" + prodName + ", num=" + num + ", score=" + score
				+ ", level=" + level + ", yearNum=" + yearNum + ", monthNum=" + monthNum + ", dayNum=" + dayNum
				+ ", weekNum=" + weekNum + "]";
	}

}