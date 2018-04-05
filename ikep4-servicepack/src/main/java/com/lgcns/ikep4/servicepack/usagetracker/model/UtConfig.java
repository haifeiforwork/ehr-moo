/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;


/**
 * 
 * 사용량 통계 configure
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfig.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtConfig extends UtConfigKey {
    /**
	 *
	 */
	private static final long serialVersionUID = 4914784810589684625L;
	/**
	 * USAGE[로그 사용 여부 ( 0 : 사용, 1 : 미사용)]
	 */
	
	
	private Integer usage;
	
	public Integer getUsage() {
		return usage;
	}
	public void setUsage(Integer usage) {
		this.usage = usage;
	}
}