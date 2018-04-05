package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPageLayoutDao;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalPortletConfigDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPageLayout;
import com.lgcns.ikep4.portal.admin.screen.model.PortalPortletConfig;
import com.lgcns.ikep4.portal.admin.screen.service.PortalPageLayoutService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;

/**
 * 
 * 포탈 페이지 레이아웃 Service 구현 클래스
 *
 * @author 임종상
 * @version $Id: PortalPageLayoutServiceImpl.java 19098 2012-06-04 08:44:50Z malboru80 $
 */
@Service("portalPageLayoutService")
public class PortalPageLayoutServiceImpl extends GenericServiceImpl<PortalPageLayout, String> implements PortalPageLayoutService {

	@Autowired
	private PortalPageLayoutDao portalPageLayoutDao;
	
	@Autowired
	private PortalPortletConfigDao portalPortletConfigDao;
	
	@Autowired
	private PortalLayoutDao portalLayoutDao;
	
	@Autowired
	private IdgenService idgenService;

	/**
	 * 페이지 리셋
	 * @param pageId 페이지 ID
	 * @param ownerId 소유자 ID
	 */
	public void removePortletReset(String pageId, String ownerId) {
		//owner 페이지 레이아웃 리스트
		List<PortalPageLayout> pageLayoutList = portalPageLayoutDao.listOwnerPageLayout(pageId, ownerId);
		
		for(int i = 0; i < pageLayoutList.size(); i++) {
			PortalPageLayout portalPageLayoutTemp = pageLayoutList.get(i);
			
			//포틀릿 config 삭제
			portalPortletConfigDao.removePageLayoutPortletConfig(portalPageLayoutTemp.getPageLayoutId());
			
			//포틀릿 레이아웃 삭제
			portalPageLayoutDao.removePageLayout(portalPageLayoutTemp.getPageLayoutId());
		}
	}

	/**
	 * 포틀릿 레이아웃 설정
	 * @param pageId
	 * @param layoutId
	 * @param ownerId
	 */
	public void updateUserPageLayout(String pageId, String layoutId, User user) {
		
		//owner 페이지 레이아웃 체크count
		int ownerPageLayoutCount = portalPageLayoutDao.countOwnerPageLayout(pageId, user.getUserId());
		PortalLayout portalLayout = portalLayoutDao.getLayout(layoutId);

		int layoutNum = Integer.parseInt(portalLayout.getLayoutNum());
		double width = 0.0;
		
		if(layoutNum == 3) {
			width = 32.5;
		} else if(layoutNum == 2) {
			width = 49.5;
		} else {
			width = 100.0;
		}
		
		if(layoutNum != ownerPageLayoutCount) {
			
			//owner 페이지 레이아웃 리스트
			List<PortalPageLayout> pageLayoutList = portalPageLayoutDao.listOwnerPageLayout(pageId, user.getUserId());
			
			if(pageLayoutList != null && pageLayoutList.size() > 0) {
				//페이지 레이아웃 layoutId, width수정
				PortalPageLayout updatePortalPageLayout = new PortalPageLayout();
				updatePortalPageLayout.setLayoutId(layoutId);
				updatePortalPageLayout.setWidth(width);
				
				for(int i=0; i < pageLayoutList.size(); i++) {
					updatePortalPageLayout.setPageLayoutId(pageLayoutList.get(i).getPageLayoutId());
					
					portalPageLayoutDao.updatePageLayout(updatePortalPageLayout);
				}
			}
			
			if(ownerPageLayoutCount > layoutNum) {
				
				//삭제되는 레이아웃 포틀릿 이동
				List<PortalPortletConfig> portalPortletConfigList = portalPortletConfigDao.listLayoutMovePortletConfig(user.getUserId(), pageId, layoutNum);
				
				if(portalPortletConfigList != null && portalPortletConfigList.size() > 0) {
					PortalPageLayout portalPageLayout = pageLayoutList.get(layoutNum-1);
					String movePageLayoutId = portalPageLayout.getPageLayoutId();
					
					for(int l=0; l < portalPortletConfigList.size(); l++) {
						PortalPortletConfig portalPortletConfig = portalPortletConfigList.get(l);
						
						portalPortletConfig.setPageLayoutId(movePageLayoutId);
						portalPortletConfig.setColIndex(layoutNum);
						portalPortletConfig.setRowIndex(portalPortletConfigDao.pageLayoutMaxRowIndex(movePageLayoutId) + 1);
						portalPortletConfig.setUpdaterId(user.getUserId());
						portalPortletConfig.setUpdaterName(user.getUserName());
						
						portalPortletConfigDao.updatePortletConfig(portalPortletConfig);
					}
				}
				
				//레이아웃 삭제
				for(int m = 0; m < pageLayoutList.size(); m++) {
					PortalPageLayout portalPageLayoutTemp = pageLayoutList.get(m);
					
					if(portalPageLayoutTemp.getColIndex() > layoutNum) {
						//포틀릿 레이아웃 삭제
						portalPageLayoutDao.removePageLayout(portalPageLayoutTemp.getPageLayoutId());
					}
				}
				
			} else {
				// 등록된 레이아웃 단수가 작으면 레이아웃만 등록
				int addCount = layoutNum - ownerPageLayoutCount;
				
				//페이지 레이아웃 생성
				PortalPageLayout createPortalPageLayout = new PortalPageLayout();
				createPortalPageLayout.setPageId(pageId);
				createPortalPageLayout.setOwnerId(user.getUserId());
				createPortalPageLayout.setLayoutId(layoutId);
				createPortalPageLayout.setWidth(width);
				createPortalPageLayout.setRegisterId(user.getUserId());
				createPortalPageLayout.setRegisterName(user.getUserName());
				createPortalPageLayout.setUpdaterId(user.getUserId());
				createPortalPageLayout.setUpdaterName(user.getUserName());
				
				for(int j=1; j <= addCount; j++) {
					createPortalPageLayout.setPageLayoutId(idgenService.getNextId());
					createPortalPageLayout.setColIndex(ownerPageLayoutCount + j);
					
					//페이지 레이아웃 등록
					portalPageLayoutDao.createPageLayout(createPortalPageLayout);
				}
			}
		}
	}
}