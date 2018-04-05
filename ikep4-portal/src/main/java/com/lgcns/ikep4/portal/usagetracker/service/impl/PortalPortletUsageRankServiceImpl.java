package com.lgcns.ikep4.portal.usagetracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLogConfigDao;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalPortletUsageRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLogConfig;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalPortletUsageRankService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 포틀릿 통계 로그 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalPortletUsageRankServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalPortletUsageRankService")
public class PortalPortletUsageRankServiceImpl extends GenericServiceImpl<PortalPortletUsageRank, String> implements PortalPortletUsageRankService {
	
	@Autowired
	private PortalPortletUsageRankDao portalPortletUsageRankDao;
	
	@Autowired
	private PortalLogConfigDao portalLogConfigDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 포틀릿 통계 로그 저장 
	 * @param portalPortletUsageRank 포틀릿 통계 로그 모델 객체
	 * @return usage value
	 */
	public int createPortletLog(PortalPortletUsageRank portalPortletUsageRank) {
		List<PortalLogConfig> logConfigList = portalLogConfigDao.getLogConfig();
		
		int usage=0;
		for(int i=0 ; i <= logConfigList.size()-1 ; i++){
			if(logConfigList.get(i).getLogTarget().equalsIgnoreCase("portlet")){
				usage = logConfigList.get(i).getUsage();
			}
		}
		
		if(usage == 0){
			portalPortletUsageRank.setPortletHistoryId(idgenService.getNextId());
			portalPortletUsageRankDao.createPortletLog(portalPortletUsageRank);
		}
		
		return usage;
	}

}