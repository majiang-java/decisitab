package com.ifre.form.api;

/**
 * 模型结果单例
 * 
 * @author CaiPeng
 *
 */
public class ModelResultWyEnumerationItem {

	//可以优化，待添加边界值比较校验
	private double minScore;
	private double maxScore;
	private String ranking;
	private Double defaultRatio;

	public double getMinScore() {
		return minScore;
	}

	public void setMinScore(double minScore) {
		this.minScore = minScore;
	}

	public double getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(double maxScore) {
		this.maxScore = maxScore;
	}

	public String getRanking() {
		return ranking;
	}

	public void setRanking(String ranking) {
		this.ranking = ranking;
	}

	public Double getDefaultRatio() {
		return defaultRatio;
	}

	public void setDefaultRatio(Double defaultRatio) {
		this.defaultRatio = defaultRatio;
	}

	@Override
	public String toString() {
		return "ModelResultWyEnumerationItem [minScore=" + minScore + ", maxScore=" + maxScore + ", ranking=" + ranking
				+ ", defaultRatio=" + defaultRatio + "]";
	}

}