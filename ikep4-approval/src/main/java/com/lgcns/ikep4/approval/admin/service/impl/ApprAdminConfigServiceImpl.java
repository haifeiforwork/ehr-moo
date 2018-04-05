/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.admin.dao.ApprAdminConfigDao;
import com.lgcns.ikep4.approval.admin.dao.ApprReadAllDao;
import com.lgcns.ikep4.approval.admin.model.ApprAdminConfig;
import com.lgcns.ikep4.approval.admin.service.ApprAdminConfigService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * 위임 관리 Service 구현
 * 
 * @author jeehye
 * @version $Id: ApprNoticeServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprAdminConfigService")
public class ApprAdminConfigServiceImpl extends GenericServiceImpl<ApprAdminConfig, String> implements
		ApprAdminConfigService {

	@Autowired
	private ApprAdminConfigDao apprAdminConfigDao;

	@Autowired
	private ApprReadAllDao apprReadAllDao;

	@Autowired
	private IdgenService idgenService;

	/**
	 * 결재 기능 사용 생성
	 * 
	 * @param apprAdminConfig 모델
	 */
	public void adminConfigCreate(ApprAdminConfig apprAdminConfig) {

		apprAdminConfig.setConfigId(idgenService.getNextId());
		this.apprAdminConfigDao.adminConfigCreate(apprAdminConfig);

		if (apprAdminConfig.getIsReadAll().equals("1")) {
			apprReadAllDao.readAllDelete(apprAdminConfig.getPortalId());
			if (apprAdminConfig.getUserIdList() != null) {
				for (String userId : apprAdminConfig.getUserIdList()) {
					try {
						apprAdminConfig.setUserId(userId);
						apprReadAllDao.readAllCreate(apprAdminConfig);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * 결재 기능 사용 수정
	 * 
	 * @param apprAdminConfig 모델
	 */
	public void adminConfigUpdate(ApprAdminConfig apprAdminConfig) {
		this.apprAdminConfigDao.adminConfigUpdate(apprAdminConfig);

		if (apprAdminConfig.getIsReadAll().equals("1")) {
			apprReadAllDao.readAllDelete(apprAdminConfig.getPortalId());
			if (apprAdminConfig.getUserIdList() != null) {
				for (String userId : apprAdminConfig.getUserIdList()) {
					try {
						apprAdminConfig.setUserId(userId);
						apprReadAllDao.readAllCreate(apprAdminConfig);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	/**
	 * 결재 기능 사용 조회
	 * 
	 * @param portalId
	 * @return ApprAdminConfig
	 */
	public ApprAdminConfig adminConfigDetail(String portalId) {
		ApprAdminConfig apprAdminConfig = (ApprAdminConfig) this.apprAdminConfigDao.adminConfigDetail(portalId);
		apprAdminConfig.setUserList(apprReadAllDao.readAllList(portalId));
		return apprAdminConfig;
	}

	public boolean existReadAllAuth(String userId) {
		return apprReadAllDao.existReadAllAuth(userId);
	}

}
