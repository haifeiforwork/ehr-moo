/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.model;

import java.util.Date;

/**
 * 
 * 사용량통계 전체  분석
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtStatistics.java 16244 2011-08-18 04:11:42Z giljae $
 */
public class UtStatistics extends UtBaseObject {
    /**
	 *
	 */
	private static final long serialVersionUID = 305919508679826158L;

	/**
    ID[사용현황 ID]
     */
    private String id;

    /**
    MODULE_TYPE[모듈 타입( 0 : 로그인, 1 : 메뉴, 2 : 브라우저, 3 : 포틀릿)]
     */
    private String moduleType;

    /**
    MODULE_ID[모듈 ID(로그인 모듈의 경우 null, 메뉴인 경우 메뉴ID, 브라우저인 경우 null, 포틀릿인 경우 포틀릿 ID)]
     */
    private String moduleId;

    /**
    YEAR[년 (예 : 2011)]
     */
    private Date moduleDate;


    /**
    USAGE_COUNT[각 모듈별 사용량  건수]
     */
    private Integer usageCount;
    
    private double usageAvg;

    private String dataYn;

    public String getDataYn() {
		return dataYn;
	}

	public void setDataYn(String dataYn) {
		this.dataYn = dataYn;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType == null ? null : moduleType.trim();
    }

    public String getModuleId() {
        return moduleId;
    }

    public void setModuleId(String moduleId) {
        this.moduleId = moduleId == null ? null : moduleId.trim();
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

	public Date getModuleDate() {
		return moduleDate;
	}

	public void setModuleDate(Date moduleDate) {
		this.moduleDate = moduleDate;
	}

	public double getUsageAvg() {
		return usageAvg;
	}

	public void setUsageAvg(double usageAvg) {
		this.usageAvg = usageAvg;
	}
}