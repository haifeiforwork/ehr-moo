/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.wfapproval.admin.dao.ApCodeDao;
import com.lgcns.ikep4.wfapproval.admin.model.ApCode;
import com.lgcns.ikep4.wfapproval.admin.service.ApCodeService;

/**
 * 결재 코드관리 Service 구현
 *
 * @author 박희진(neoheejin@naver.com)
 * @version $Id: ApCodeServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apCodeService")
public class ApCodeServiceImpl extends GenericServiceImpl<ApCode, String> implements ApCodeService {

	@Autowired
	private ApCodeDao apCodeDao;

	@Autowired
	private IdgenService idgenService;
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#listApCode()
	 */
	@Transactional(readOnly = true)
	public List<ApCode> listRootApCode(String sLevel) {
		return this.apCodeDao.listRootApCode(sLevel);
	}
	
	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#listApCode()
	 */
	@Transactional(readOnly = true)
	public List<ApCode> listGroupApCode(String codeId) {
		return this.apCodeDao.listGroupApCode(codeId);
	}
	
	@Transactional(readOnly = true)
	public List<ApCode> listGroupApCodeByValue(String codeValue, String localeCode) {
		
		List<ApCode> listCode = null;
		
		StringUtil.nvl(localeCode, "ko");
		
		if(localeCode.equals("ko")){
			listCode = this.apCodeDao.listGroupApCodeByValueKr(codeValue);
		}else{
			listCode = this.apCodeDao.listGroupApCodeByValueEn(codeValue);
		}
		
		return listCode;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#readApCode(java.lang.String)
	 */
	@Transactional(readOnly = true)
	public ApCode readApCode(String codeId) {
		return this.apCodeDao.get(codeId);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#createApCode(com.lgcns.ikep4.wfapproval.admin.model.ApCode)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public String createApCode(ApCode apCode) {
		
		String sCodeId = this.idgenService.getNextId();
		
		apCode.setCodeId(sCodeId);

		this.apCodeDao.create(apCode);

		return sCodeId;
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#updateApCode(com.lgcns.ikep4.wfapproval.admin.model.ApCode)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void updateApCode(ApCode apCode) {
		this.apCodeDao.update(apCode);
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#deleteApCode(java.lang.String)
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void deleteApCode(String codeId) {
		if(this.apCodeDao.exists(codeId)){
			this.apCodeDao.remove(codeId);
		}
	}

	/* (non-Javadoc)
	 * @see com.lgcns.ikep4.wfapproval.admin.service.ApCodeService#deleteChildApCode(java.lang.String)
	 */
	public boolean existsChildApCode(String parentCodeId) {
		return this.apCodeDao.existsChild(parentCodeId);
	}

}
