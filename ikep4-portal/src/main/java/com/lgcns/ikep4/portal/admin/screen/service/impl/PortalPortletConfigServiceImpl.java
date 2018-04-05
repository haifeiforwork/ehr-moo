package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortlet;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPortletConfigService;
import com.lgcns.ikep4.portal.usagetracker.model.PortalPortletUsageRank;
import com.lgcns.ikep4.portal.usagetracker.service.PortalPortletUsageRankService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 포틀릿 셋팅 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalPortletConfigServiceImpl.java 16243 2011-08-18 04:10:43Z giljae $
 */
@Service("portalPortletConfigService")
public class PortalPortletConfigServiceImpl extends GenericServiceImpl<PortalPortletConfig, String> implements
		PortalPortletConfigService {

	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalPortletDao portalPortletDao;

	@Autowired
	private IdgenService idgenService;
	
	@Autowired
	private PortalPortletUsageRankService portalPortletUsageRankService;

	/**
	 * 포틀릿 config 등록
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param systemCode 시스템 코드
	 * @param portalId 포탈 ID
	 */
	public void createPortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String portalId, String localeCode) {
		
		List<PortalPortlet> pageLayoutPortletList = portalPortletDao.listPageLayoutPortlet(portalPortletConfig.getPageLayoutId(), systemCode, localeCode, "portletName");
		
		portalPortletConfig.setPortletConfigId(idgenService.getNextId());
		
		//포틀릿 config 인서트
		portalPortletConfigDao.createPortletConfig(portalPortletConfig);
		
		//행 번호 업데이트
		for(int i = 0; i < pageLayoutPortletList.size(); i++) {
			PortalPortlet portalPortlet = (PortalPortlet) pageLayoutPortletList.get(i);
			
			portalPortletConfigDao.updatePortletConfigRowIndex(portalPortlet.getPortletConfigId(), (i+2));
		}
		
		//포틀릿 통계 등록 로그 등록
		PortalPortletUsageRank portalPortletUsageRank = new PortalPortletUsageRank();
		
		portalPortletUsageRank.setPortletId(portalPortletConfig.getPortletId());
		portalPortletUsageRank.setOwnerId(portalPortletConfig.getRegisterId());
		portalPortletUsageRank.setAction("0");
		portalPortletUsageRank.setPortalId(portalId);
		portalPortletUsageRank.setRegisterId(portalPortletConfig.getRegisterId());
		portalPortletUsageRank.setRegisterName(portalPortletConfig.getRegisterName());
		
		portalPortletUsageRankService.createPortletLog(portalPortletUsageRank);
	}

	/**
	 * 포틀릿 config 수정
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param systemCode 시스템 코드
	 */
	public void updatePortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String localeCode) {
		
		PortalPortletConfig portalPortletConfigTemp = portalPortletConfigDao.getPortletConfig(portalPortletConfig.getPortletConfigId());
		String oldPageLayoutId = "";
		
		if(portalPortletConfigTemp != null) {
			oldPageLayoutId = portalPortletConfigTemp.getPageLayoutId();
		}
		
		List<PortalPortlet> list = portalPortletDao.listPageLayoutMovePortlet(portalPortletConfig.getPageLayoutId(), systemCode, portalPortletConfig.getPortletConfigId());
		
		int rowIndex = 1;
		
		for(int i = 0; i < list.size(); i++) {
			PortalPortlet portalPortlet = (PortalPortlet) list.get(i);
			
			if(portalPortletConfig.getRowIndex() == rowIndex) {
				//이동되는 포틀릿 레이아웃 rowIndex 업데이트
				portalPortletConfigDao.updatePortletConfig(portalPortletConfig);
				rowIndex++;
			}

			//페이지레이아웃에 포틀릿 rowIndex 업데이트
			portalPortletConfigDao.updatePortletConfigRowIndex(portalPortlet.getPortletConfigId(), rowIndex);
			rowIndex++;
		}
		
		// 이동시 포틀릿이 페이지 레이아웃에 마지막에 위치한 경우
		if(portalPortletConfig.getRowIndex() == rowIndex) {
			//이동되는 포틀릿 레이아웃 rowIndex 업데이트
			portalPortletConfigDao.updatePortletConfig(portalPortletConfig);
			rowIndex++;
		}
		
		//이동전 페이지레이아웃 rowIndex 업데이트
		if(!oldPageLayoutId.equals(portalPortletConfig.getPageLayoutId()) && !"".equals(oldPageLayoutId)) {
			List<PortalPortlet> oldList = portalPortletDao.listPageLayoutPortlet(oldPageLayoutId, systemCode, localeCode, "portletName");
			
			for(int j = 0; j < oldList.size(); j++) {
				PortalPortlet oldPortalPortlet = (PortalPortlet) oldList.get(j);
				
				//페이지레이아웃에 포틀릿 rowIndex 업데이트
				portalPortletConfigDao.updatePortletConfigRowIndex(oldPortalPortlet.getPortletConfigId(), j+1);
			}
		}
	}

	/**
	 * 포틀릿 config 삭제
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 * @param systemCode 시스템 코드
	 * @param portalId 포탈 ID
	 * @param user 유저 객체
	 */
	public void removePortletConfig(PortalPortletConfig portalPortletConfig, String systemCode, String portalId, User user) {
		String pageLayoutId = portalPortletConfigDao.getPortletConfig(portalPortletConfig.getPortletConfigId()).getPageLayoutId();
		
		portalPortletConfigDao.removePortletConfig(portalPortletConfig);
		
		List<PortalPortlet> list = portalPortletDao.listPageLayoutPortlet(pageLayoutId, systemCode, user.getLocaleCode(), "portletName");
		
		//삭제 후 rowIndex 변경
		for(int i = 0; i < list.size(); i++) {
			PortalPortlet portalPortlet = (PortalPortlet) list.get(i);
			
			//페이지레이아웃에 포틀릿 rowIndex 업데이트
			portalPortletConfigDao.updatePortletConfigRowIndex(portalPortlet.getPortletConfigId(), i+1);
		}
		
		//포틀릿 통계 삭제 로그 등록
		PortalPortletUsageRank portalPortletUsageRank = new PortalPortletUsageRank();
		
		portalPortletUsageRank.setPortletId(portalPortletConfig.getPortletId());
		portalPortletUsageRank.setOwnerId(user.getUserId());
		portalPortletUsageRank.setAction("1");
		portalPortletUsageRank.setPortalId(portalId);
		portalPortletUsageRank.setRegisterId(user.getUserId());
		portalPortletUsageRank.setRegisterName(user.getUserName());
		
		portalPortletUsageRankService.createPortletLog(portalPortletUsageRank);
	}
	
	/**
	 * 포틀릿 config viewMode 수정
	 * @param portalPortletConfig 포틀릿 셋팅 모델
	 */
	public void updatePortletConfigViewMode(PortalPortletConfig portalPortletConfig) {
		portalPortletConfigDao.updatePortletConfigViewMode(portalPortletConfig);
	}
}
