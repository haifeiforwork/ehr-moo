package com.lgcns.ikep4.servicepack.usagetracker.model;


public class UtResTimeConfig extends UtBaseObject {

	private static final long serialVersionUID = 6795866846862552007L;

	/**
	 * 설정시간(초)
	 */
	private double resTimeConfigSecond;

	public double getResTimeConfigSecond() {
		return resTimeConfigSecond;
	}

	public void setResTimeConfigSecond(double resTimeConfigSecond) {
		this.resTimeConfigSecond = resTimeConfigSecond;
	}
}