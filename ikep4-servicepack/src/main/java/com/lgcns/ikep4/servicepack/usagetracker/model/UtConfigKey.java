/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

/**
 * 
 * 사용량 통계 configure key
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigKey.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtConfigKey extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 4125847161173612199L;

	/**
	 * LOG_TARGET[로그  적용 대상 ( 0 : 사용자로그인, 1 : 메뉴, 2 : 포틀릿)]
	 */
	private String logTarget;

	/**
	 * PORTAL_ID[포탈 ID]
	 */
	private String portalId;

	public String getLogTarget() {
		return logTarget;
	}

	public UtConfigKey() {
		super();
	}
	
	public UtConfigKey(String logTarget, String portalId) {
		super();
		this.logTarget = logTarget;
		this.portalId = portalId;
	}



	public void setLogTarget(String logTarget) {
		this.logTarget = logTarget;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
}