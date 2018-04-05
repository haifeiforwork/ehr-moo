package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.framework.web.SearchResult;

/**
 * 
 * 분석 결과 리턴 obj
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtResult.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtResult extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = -8369842602154678661L;
	 
	private int process;
	private String maxUseAge;
	private String minUseAge;
	private int searchOption;
	private int viewOption;
	
	@DateTimeFormat(pattern="yyyy.MM.dd")
	
	private Date startDate;
	@DateTimeFormat(pattern="yyyy.MM.dd")
	private Date endDate;
	private String menuId;
	private int totalSum=0;
	
	private List<UtLoginLog> smsList;
	private SearchResult<UtLoginLog> utLoginLog;
	private SearchResult<UtMenuLog> utMenuLog;
	private SearchResult<UtStatistics> menuOrPortletLog;

	private List<UtStatistics> data;

	public String getMaxUseAge() {
		return maxUseAge;
	}

	public String getMinUseAge() {
		return minUseAge;
	}

	public List<UtStatistics> getData() {
		return data;
	}

	public void setMaxUseAge(String maxUseAge) {
		this.maxUseAge = maxUseAge;
	}

	public void setMinUseAge(String minUseAge) {
		this.minUseAge = minUseAge;
	}

	public void setData(List<UtStatistics> data) {
		this.data = data;
	}

	public int getProcess() {
		return process;
	}

	public int getSearchOption() {
		return searchOption;
	}

	public int getViewOption() {
		return viewOption;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public void setSearchOption(int searchOption) {
		this.searchOption = searchOption;
	}

	public void setViewOption(int viewOption) {
		this.viewOption = viewOption;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public int getTotalSum() {
		return totalSum;
	}

	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}

	public SearchResult<UtLoginLog> getUtLoginLog() {
		return utLoginLog;
	}

	public SearchResult<UtMenuLog> getUtMenuLog() {
		return utMenuLog;
	}

	public void setUtLoginLog(SearchResult<UtLoginLog> utLoginLog) {
		this.utLoginLog = utLoginLog;
	}

	public void setUtMenuLog(SearchResult<UtMenuLog> utMenuLog) {
		this.utMenuLog = utMenuLog;
	}

	public SearchResult<UtStatistics> getMenuOrPortletLog() {
		return menuOrPortletLog;
	}

	public void setMenuOrPortletLog(SearchResult<UtStatistics> menuOrPortletLog) {
		this.menuOrPortletLog = menuOrPortletLog;
	}

	public List<UtLoginLog> getSmsList() {
		return smsList;
	}

	public void setSmsList(List<UtLoginLog> smsList) {
		this.smsList = smsList;
	}
	
}
