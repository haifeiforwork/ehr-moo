/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.dao.ApprCodeDao;
import com.lgcns.ikep4.approval.admin.model.ApprCode;
import com.lgcns.ikep4.approval.admin.service.ApprCodeService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 결재 코드관리 Service 구현
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApprCodeServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprCodeServiceImpl")
public class ApprCodeServiceImpl extends GenericServiceImpl<ApprCode, String> implements ApprCodeService {

	@Autowired
	private ApprCodeDao apprCodeDao;

	@Autowired
	private IdgenService idgenService;

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#listApprCode()
	 */
	@Transactional(readOnly = true)
	public List<ApprCode> listRootApprCode(Map<String, String> map) {
		return this.apprCodeDao.listRootApprCode(map);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#listApprCode()
	 */
	@Transactional(readOnly = true)
	public List<ApprCode> listGroupApprCode(String codeId) {
		return this.apprCodeDao.listGroupApprCode(codeId);
	}
	
	@Transactional(readOnly = true)
	public List<ApprCode> listGroupApprCodeByValue(String codeValue, String localeCode) {
		
		List<ApprCode> listCode = null;
		
		StringUtil.nvl(localeCode, "ko");
		
		if(localeCode.equals("ko")){
			listCode = this.apprCodeDao.listGroupApprCodeByValueKr(codeValue);
		}else{
			listCode = this.apprCodeDao.listGroupApprCodeByValueEn(codeValue);
		}
		
		return listCode;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#readApprCode(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public ApprCode readApprCode(String codeId) {
		return this.apprCodeDao.get(codeId);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#createApprCode(com.lgcns.ikep4.approval.admin.model.ApprCode)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String createApprCode(ApprCode apprCode) {
		
		String sCodeId = this.idgenService.getNextId();
		
		apprCode.setCodeId(sCodeId);

		this.apprCodeDao.create(apprCode);

		return sCodeId;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#updateApprCode(com.lgcns.ikep4.approval.admin.model.ApprCode)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateApprCode(ApprCode apprCode) {
		this.apprCodeDao.update(apprCode);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#deleteApprCode(java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteApprCode(String codeId) {
		if(this.apprCodeDao.exists(codeId)){
			this.apprCodeDao.remove(codeId);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#deleteChildApprCode(java.lang.String)
	 */
	public boolean existsChildApprCode(String parentCodeId) {
		return this.apprCodeDao.existsChild(parentCodeId);
	}

	
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#readApprCode(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public String getCodeIdByCodeValue(String codeValue, String portalId) {
		Map map = new HashMap<String, Object>();
		map.put("codeValue", 		codeValue);
		map.put("portalId", 		portalId);
		return this.apprCodeDao.getCodeIdByCodeValue(map);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.approval.admin.service.ApprCodeService#listApprCode()
	 */
	@Transactional(readOnly = true)
	public List<ApprCode> getApprCodeList(String codeId, String localeCode, String usage) {
		Map map = new HashMap<String, Object>();
		map.put("codeId", 		codeId);
		map.put("localeCode", 	localeCode);
		map.put("usage", 		usage);
		return this.apprCodeDao.getApprCodeList(map);
	}
}
