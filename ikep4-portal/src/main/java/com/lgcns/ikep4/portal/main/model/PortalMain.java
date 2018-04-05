package com.lgcns.ikep4.portal.main.model;

import java.util.List;

import com.lgcns.ikep4.framework.core.model.BaseObject;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalDefPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPage;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletCategory;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;

/**
 * 
 * 포탈 메인 model
 *
 * @author 임종상
 * @version $Id: PortalMain.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
public class PortalMain extends BaseObject {

	private static final long serialVersionUID = 2066762836335120127L;
	
	/**
	 * 페이지 정보
	 */
	private PortalPage page;
	
	/**
	 * 포틀릿 리스트
	 */
	private List<PortalPortlet> portletList;

	/**
	 * 포틀릿 카테고리 리스트
	 */
	private List<PortalPortletCategory> portletCategoryList;

	/**
	 * 유저 페이지 레이아웃 리스트
	 */
	private List<PortalPageLayout> pageLayoutList;
	
	/**
	 * 공통 페이지 레이아웃 리스트
	 */
	private List<PortalPageLayout> commonPageLayoutList;
	
	/**
	 * 테마 리스트
	 */
	private List<PortalTheme> themeList;
	
	/**
	 * 공통 포틀릿 리스트
	 */
	private List<PortalPortlet> commonActivePortletList;
	
	/**
	 * 사용자 포틀릿 리스트
	 */
	private List<PortalPortlet> activePortletList;
	
	private List<PortalPortlet> activePortletNomoveList;
	
	/**
	 * 디폴트 레이아웃 리스트
	 */
	private List<PortalDefPageLayout> defPageLayoutList;
	
	/**
	 * 디폴트 포틀릿 리스트
	 */
	private List<PortalDefPortletConfig> defPortletList;
	
	/**
	 * common 레이아웃 리스트
	 */
	private List<PortalLayout> commonLayoutList;
	
	/**
	 * 레이아웃 리스트
	 */
	private List<PortalLayout> layoutList;
	
	/**
	 * 공통 레이아웃 마진
	 */
	private double commonMarginLeft;
	
	/**
	 * 개인 레이아웃 마진
	 */
	private double marginLeft;
	
	/**
	 * 디폴트 레이아웃 ID
	 */
	private String defLayoutId;
	
	/**
	 * 디폴트 레이아웃 ID
	 */
	private String commonLayoutId;
	
	/**
	 * 사용자 레이아웃 ID
	 */
	private String userLayoutId;
	
	public String getUserLayoutId() {
		return userLayoutId;
	}

	public void setUserLayoutId(String userLayoutId) {
		this.userLayoutId = userLayoutId;
	}

	public String getCommonLayoutId() {
		return commonLayoutId;
	}

	public void setCommonLayoutId(String commonLayoutId) {
		this.commonLayoutId = commonLayoutId;
	}

	public double getCommonMarginLeft() {
		return commonMarginLeft;
	}

	public void setCommonMarginLeft(double commonMarginLeft) {
		this.commonMarginLeft = commonMarginLeft;
	}

	public double getMarginLeft() {
		return marginLeft;
	}

	public void setMarginLeft(double marginLeft) {
		this.marginLeft = marginLeft;
	}

	public String getDefLayoutId() {
		return defLayoutId;
	}

	public void setDefLayoutId(String defLayoutId) {
		this.defLayoutId = defLayoutId;
	}

	public List<PortalLayout> getCommonLayoutList() {
		return commonLayoutList;
	}

	public void setCommonLayoutList(List<PortalLayout> commonLayoutList) {
		this.commonLayoutList = commonLayoutList;
	}

	public List<PortalLayout> getLayoutList() {
		return layoutList;
	}

	public void setLayoutList(List<PortalLayout> layoutList) {
		this.layoutList = layoutList;
	}

	public List<PortalDefPageLayout> getDefPageLayoutList() {
		return defPageLayoutList;
	}

	public void setDefPageLayoutList(List<PortalDefPageLayout> defPageLayoutList) {
		this.defPageLayoutList = defPageLayoutList;
	}

	public List<PortalDefPortletConfig> getDefPortletList() {
		return defPortletList;
	}

	public void setDefPortletList(List<PortalDefPortletConfig> defPortletList) {
		this.defPortletList = defPortletList;
	}

	public List<PortalTheme> getThemeList() {
		return themeList;
	}

	public List<PortalPortlet> getCommonActivePortletList() {
		return commonActivePortletList;
	}

	public void setCommonActivePortletList(List<PortalPortlet> commonActivePortletList) {
		this.commonActivePortletList = commonActivePortletList;
	}

	public List<PortalPortlet> getActivePortletList() {
		return activePortletList;
	}

	public void setActivePortletList(List<PortalPortlet> activePortletList) {
		this.activePortletList = activePortletList;
	}

	public void setThemeList(List<PortalTheme> themeList) {
		this.themeList = themeList;
	}

	public PortalPage getPage() {
		return page;
	}

	public void setPage(PortalPage page) {
		this.page = page;
	}

	public List<PortalPortlet> getPortletList() {
		return portletList;
	}

	public void setPortletList(List<PortalPortlet> portletList) {
		this.portletList = portletList;
	}

	public List<PortalPortletCategory> getPortletCategoryList() {
		return portletCategoryList;
	}

	public void setPortletCategoryList(List<PortalPortletCategory> portletCategoryList) {
		this.portletCategoryList = portletCategoryList;
	}

	public List<PortalPageLayout> getPageLayoutList() {
		return pageLayoutList;
	}

	public void setPageLayoutList(List<PortalPageLayout> pageLayoutList) {
		this.pageLayoutList = pageLayoutList;
	}

	public List<PortalPageLayout> getCommonPageLayoutList() {
		return commonPageLayoutList;
	}

	public void setCommonPageLayoutList(List<PortalPageLayout> commonPageLayoutList) {
		this.commonPageLayoutList = commonPageLayoutList;
	}

	public List<PortalPortlet> getActivePortletNomoveList() {
		return activePortletNomoveList;
	}

	public void setActivePortletNomoveList(
			List<PortalPortlet> activePortletNomoveList) {
		this.activePortletNomoveList = activePortletNomoveList;
	}

}
