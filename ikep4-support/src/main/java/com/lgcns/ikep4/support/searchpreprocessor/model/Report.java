/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.support.searchpreprocessor.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;


public class Report extends SpBaseObject {
	
	/**
	 *
	 */
	private static final long serialVersionUID = -8920207102785210419L;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	Date resultDt;
	
	@DateTimeFormat(pattern="yyyy.MM.dd")
	Date startDt;
	
	@DateTimeFormat(pattern="yyyy.MM.dd")
	Date endDt;
	
	Integer totalCount;
	public Date getResultDt() {
		return resultDt;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setResultDt(Date resultDt) {
		this.resultDt = resultDt;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Date getStartDt() {
		return startDt;
	}
	public Date getEndDt() {
		return endDt;
	}
	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}
	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}
	
	
}