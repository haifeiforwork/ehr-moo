/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.portal.admin.screen.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.code.model.AdminSearchCondition;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.dao.PortalThemeDao;
import com.lgcns.ikep4.portal.admin.screen.model.PortalTheme;
import com.lgcns.ikep4.portal.admin.screen.service.PortalThemeService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 포탈 테마 Service 구현 클래스
 *
 * @author 임종상(malboru80@naver.com)
 * @version $Id: PortalThemeServiceImpl.java 18250 2012-04-24 08:36:40Z yu_hs $
 */
@Service("portalThemeService")
public class PortalThemeServiceImpl extends GenericServiceImpl<PortalTheme, String> implements PortalThemeService {
	
	@Autowired
	private PortalThemeDao portalThemeDao;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 첨부파일 관리 service
	 */
	@Autowired
	private FileService fileService;
	
	/**
	 * 테마 생성
	 * @param portalTheme 테마 모델
	 * @return 테마 ID
	 */
	public String createTheme(PortalTheme portalTheme) {
		portalTheme.setThemeId(idgenService.getNextId());
		
		if(portalTheme.getDefaultOption() == 1) {
			portalThemeDao.clearDefaultTheme(portalTheme.getPortalId());
		}
		
		return portalThemeDao.createTheme(portalTheme);
	}
	
	/**
	 * 테마 수정
	 * @param portalTheme 테마 모델
	 */
	public void updateTheme(PortalTheme portalTheme) {
		if(portalTheme.getDefaultOption() == 1) {
			portalThemeDao.clearDefaultTheme(portalTheme.getPortalId());
		}
		portalThemeDao.updateTheme(portalTheme);
	}
	
	/**
	 * 테마 조회
	 * @param themeId 테마 ID
	 * @return PortalTheme 테마 정보
	 */
	public PortalTheme readTheme(String themeId) {
		PortalTheme portalTheme = portalThemeDao.getTheme(themeId);
		
		if(portalTheme != null && !StringUtil.isEmpty(portalTheme.getPreviewImageId())) {
			FileData fileData = new FileData();
			fileData = fileService.read(portalTheme.getPreviewImageId());
			
			portalTheme.setFileData(fileData);
		}
		
		return portalTheme;
	}

	/**
	 * 테마 리스트
	 * @param portalTheme 테마 모델
	 * @return List<PortalTheme> 테마 목록
	 */
	public List<PortalTheme> listTheme(PortalTheme portalTheme) {
		return portalThemeDao.listTheme(portalTheme);
	}
	
	/**
	 * 테마 리스트
	 * @param searchCondition 검색조건 모델 클래스
	 * @return SearchResult<PortalTheme> 테마 목록
	 */
	public SearchResult<PortalTheme> listBySearchCondition(AdminSearchCondition searchCondition) {
		
		Integer count = portalThemeDao.countBySearchCondition(searchCondition);
		searchCondition.terminateSearchCondition(count);
		
		SearchResult<PortalTheme> searchResult = null;
		
		if (searchCondition.isEmptyRecord()) {
			
			searchResult = new SearchResult<PortalTheme>(searchCondition);
		} else {

			List<PortalTheme> portalThemeList = portalThemeDao.listBySearchCondition(searchCondition);

			searchResult = new SearchResult<PortalTheme>(portalThemeList, searchCondition);
		}
		
		return searchResult;
		
	}
	
	/**
	 * 테마 삭제
	 * @param themeId 테마 ID
	 */
	public void removeTheme(String themeId) {
		PortalTheme portalTheme = portalThemeDao.getTheme(themeId);
		if(portalTheme.getDefaultOption() == 1) {
			portalThemeDao.resetDefaultTheme(portalTheme);
		}
		
		portalThemeDao.removeTheme(themeId);
	}
}
