package com.lgcns.ikep4.portal.usagetracker.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLogConfigDao;
import com.lgcns.ikep4.portal.usagetracker.dao.PortalLoginRankDao;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLogConfig;
import com.lgcns.ikep4.portal.usagetracker.model.PortalLoginRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalLoginRankService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 로그인 통계 로그 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalLoginRankServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalLoginRankService")
public class PortalLoginRankServiceImpl extends GenericServiceImpl<PortalLoginRank, String> implements PortalLoginRankService {
	
	@Autowired
	private PortalLoginRankDao portalLoginRankDao;
	
	@Autowired
	private PortalLogConfigDao portalLogConfigDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 로그인 통계  로그 저장
	 * @param portalLoginRank 로그인 통계 로그 모델 객체
	 * @return usage value
	 */
	public int createLoginLog(PortalLoginRank portalLoginRank) {
		
		List<PortalLogConfig> logConfigList = portalLogConfigDao.getLogConfig();
		
		int usage=0;
		for(int i=0 ; i <= logConfigList.size()-1 ; i++){
			if(logConfigList.get(i).getLogTarget().equalsIgnoreCase("login")){
				usage = logConfigList.get(i).getUsage();
			}
		}
		
		if(usage == 0){
			portalLoginRank.setLoginHistoryId(idgenService.getNextId());
			portalLoginRankDao.createLoginLog(portalLoginRank);
		}
		
		return usage;
	}

}