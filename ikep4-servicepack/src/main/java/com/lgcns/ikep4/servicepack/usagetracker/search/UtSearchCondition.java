package com.lgcns.ikep4.servicepack.usagetracker.search;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.lgcns.ikep4.framework.web.SearchCondition;
import com.lgcns.ikep4.servicepack.usagetracker.util.Criteria;


/**
 * 설문 리스트 검색
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtSearchCondition.java 19668 2012-07-05 08:08:31Z malboru80 $
 */
public class UtSearchCondition extends SearchCondition {
	private static final long serialVersionUID = 1L;

	private String id;

	private String searchColumn;

	private String searchWord;

	private String viewMode;

	private String layoutType;

	private boolean init = Boolean.TRUE;

	private String redirect;

	private String registerId;

	private String portalId;

	private boolean admin;

	private int searchOption;

	private int viewOption;

	private int process;
	
	private String flg;

	protected List<Criteria> oredCriteria;

	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date startDate;

	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date endDate;
	
	private String sdate;
	
	private String edate;

	private String menuId;
	
	private String subMenuId;
	
	private String resTimeUrlId;
	
	private String workspace;

	public String getResTimeUrlId() {
		return resTimeUrlId;
	}

	public void setResTimeUrlId(String resTimeUrlId) {
		this.resTimeUrlId = resTimeUrlId;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	public boolean isInit() {
		return init;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSearchColumn() {
		return searchColumn;
	}

	public void setSearchColumn(String searchColumn) {
		this.searchColumn = searchColumn;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getViewMode() {
		return viewMode;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public String getLayoutType() {
		return layoutType;
	}

	public void setLayoutType(String layoutType) {
		this.layoutType = layoutType;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public int getSearchOption() {
		return searchOption;
	}

	public int getViewOption() {
		return viewOption;
	}

	public void setSearchOption(int searchOption) {
		this.searchOption = searchOption;
	}

	public void setViewOption(int viewOption) {
		this.viewOption = viewOption;
	}

	public List<Criteria> getOredCriteria() {
		return oredCriteria;
	}

	public void setOredCriteria(List<Criteria> oredCriteria) {
		this.oredCriteria = oredCriteria;
	}

	public int getProcess() {
		return process;
	}

	public void setProcess(int process) {
		this.process = process;
	}

	public String getSubMenuId() {
		return subMenuId;
	}

	public void setSubMenuId(String subMenuId) {
		this.subMenuId = subMenuId;
	}

	public String getWorkspace() {
		return workspace;
	}

	public void setWorkspace(String workspace) {
		this.workspace = workspace;
	}

	public String getSdate() {
		return sdate;
	}

	public void setSdate(String sdate) {
		this.sdate = sdate;
	}

	public String getEdate() {
		return edate;
	}

	public void setEdate(String edate) {
		this.edate = edate;
	}

	public String getFlg() {
		return flg;
	}

	public void setFlg(String flg) {
		this.flg = flg;
	}

}