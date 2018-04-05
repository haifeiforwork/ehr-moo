/**
 * LG CNS IKEP4 
*/
package com.lgcns.ikep4.servicepack.usagetracker.util;

import java.util.ArrayList;
import java.util.List;

import com.lgcns.ikep4.servicepack.usagetracker.model.UtConfig;

/**
 * 
 * 사용량 통계 configure
 *
 * @author 고인호(ihko11@daum.net)
 * @version $Id: UtConfigUtil.java 16244 2011-08-18 04:11:42Z giljae $
 */
public final class UtConfigUtil{
	
	/**
	 * utconfiglist에 값이 없을경우 기본적으로 셋팅
	 * @param portalId
	 * @return
	 */
	private UtConfigUtil(){}
	
	public static List<UtConfig> notExistUtConfigMake(String portalId){
		
		List<UtConfig> result = new ArrayList<UtConfig>();
		
		for (int i = 0; i < UsageTrackerConstance.LOG_TARGET_COUNT; i++) {
			UtConfig utConfig = new UtConfig();
			utConfig.setPortalId(portalId);
			utConfig.setLogTarget(String.valueOf(i));
			utConfig.setUsage( UsageTrackerConstance.UT_CONFIG_LOGIN_USAGE_USE );
			
			result.add(utConfig);
		}
		return result;
	}
}