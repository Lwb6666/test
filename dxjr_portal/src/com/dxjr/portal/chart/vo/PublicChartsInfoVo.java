package com.dxjr.portal.chart.vo;

import java.io.Serializable;

/**
 * <p>
 * Description:公共报表信息<br />
 * </p>
 * 
 * @title PublicChartsInfoVo.java
 * @package com.dxjr.portal.chart.vo
 * @author justin.xu
 * @version 0.1 2014年9月9日
 */
public class PublicChartsInfoVo implements Serializable {

	/**
	 * 报表在页面上显示的宽度
	 */
	private int chartWidth;
	/**
	 * 报表在页面上显示的高度
	 */
	private int chartHight;
	/**
	 * 报表名称
	 */
	private String chartText;
	/**
	 * 横坐标所需列
	 */
	private String[] xCategories;
	/**
	 * 横坐标名称
	 */
	private String xText;
	/**
	 * 纵坐标名称
	 */
	private String yText;
	/**
	 * 系列名称
	 */
	private String seriesName;
	/**
	 * 报表（纵坐标）值
	 */
	private double[] chartData;
	/**
	 * 报表类型（分为八种，在Enumeration公共常量类定义）
	 */
	private String chartType;

	public int getChartWidth() {
		return chartWidth;
	}

	public void setChartWidth(int chartWidth) {
		this.chartWidth = chartWidth;
	}

	public int getChartHight() {
		return chartHight;
	}

	public void setChartHight(int chartHight) {
		this.chartHight = chartHight;
	}

	public String getChartText() {
		return chartText;
	}

	public void setChartText(String chartText) {
		this.chartText = chartText;
	}

	public String[] getxCategories() {
		return xCategories;
	}

	public void setxCategories(String[] xCategories) {
		this.xCategories = xCategories;
	}

	public String getxText() {
		return xText;
	}

	public void setxText(String xText) {
		this.xText = xText;
	}

	public String getyText() {
		return yText;
	}

	public void setyText(String yText) {
		this.yText = yText;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public double[] getChartData() {
		return chartData;
	}

	public void setChartData(double[] chartData) {
		this.chartData = chartData;
	}

	public String getChartType() {
		return chartType;
	}

	public void setChartType(String chartType) {
		this.chartType = chartType;
	}

}