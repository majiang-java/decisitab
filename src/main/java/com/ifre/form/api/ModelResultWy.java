package com.ifre.form.api;

/**
 * 模型结果-违约模型
 * 
 * @author CaiPeng
 *
 */
public class ModelResultWy {

	private String totalScore;
	private String defaultRatio;
	private String ranking;
	private String trimScore;

	public String getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(String totalScore) {
		this.totalScore = totalScore;
	}

	public String getDefaultRatio() {
		return defaultRatio;
	}

	public void setDefaultRatio(String defaultRatio) {
		this.defaultRatio = defaultRatio;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public String getTrimScore() {
		return trimScore;
	}

	public void setTrimScore(String trimScore) {
		this.trimScore = trimScore;
	}

	@Override
	public String toString() {
		return "ModelResult [totalScore=" + totalScore + ", defaultRatio=" + defaultRatio + ", ranking=" + ranking
				+ ", trimScore=" + trimScore + "]";
	}

}