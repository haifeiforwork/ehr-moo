package com.lgcns.ikep4.portal.usagetracker.model;

import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * 
 * 로그 설정 관리 model 객체
 *
 * @author 임종상
 * @version $Id: PortalLogConfig.java 16243 2011-08-18 04:10:43Z giljae $
 */
public class PortalLogConfig extends BaseObject{

	private static final long serialVersionUID = -959278345573204243L;
	
	/**
	 * 포탈 ID
	 */
	private String portalId;
	
	/**
	 * 로그  적용 대상( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿)
	 */
	private String logTarget;
	
	/**
	 * 로그 사용 여부( 0 : 사용, 1 : 미사용)
	 */
	private int usage;
	
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getLogTarget() {
		return logTarget;
	}
	public void setLogTarget(String logTarget) {
		this.logTarget = logTarget;
	}
	public int getUsage() {
		return usage;
	}
	public void setUsage(int usage) {
		this.usage = usage;
	}
}
