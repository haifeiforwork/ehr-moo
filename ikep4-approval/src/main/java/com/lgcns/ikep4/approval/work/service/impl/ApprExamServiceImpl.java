/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.approval.work.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.approval.work.dao.ApprExamDao;
import com.lgcns.ikep4.approval.work.model.ApprExam;
import com.lgcns.ikep4.approval.work.service.ApprExamService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

/**
 * 검토요청 Service 구현
 *
 * @author jeehye
 * @version $Id: ApprExamServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apprExamService")
public class ApprExamServiceImpl extends GenericServiceImpl<ApprExam, String> implements ApprExamService {

	@Autowired
	private ApprExamDao apprExamDao;

	/**
	 *  검토요청 저장
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void apprExamCreate(ApprExam apprExam) {  
		
		String [] examUserIds = StringUtils.split(apprExam.getExamUserId(), ";");
		
		for(int i=0; i<examUserIds.length; i++){
			apprExam.setExamUserId(examUserIds[i]);
			apprExam.setExamOrder(Integer.toString(i+1));
			apprExam.setExamStatus("0");
			
			this.apprExamDao.apprExamCreate(apprExam);
	    }
	
	}
	
	/**
	 * 검토요청 목록
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public List<ApprExam> listApprExamInfo(ApprExam apprExam) {  
		
		List<ApprExam> list = (List<ApprExam>)this.apprExamDao.listApprExamInfo(apprExam);  
		return list;
	}
	
	/**
	 * 최초 검토 요청자 ID
	 * @param apprId
	 * @return ID
	 */
	public String examFirstReqId(String apprId) {  
		
		String firstReqId = "";
		try {
			firstReqId = (String)this.apprExamDao.examFirstReqId(apprId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return firstReqId;
	}
	
	
	/**
	 *  검토요청 저장
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public void updateApprExamInfoSave(ApprExam apprExam) {  
		
		this.apprExamDao.updateApprExamInfoSave(apprExam);
	
	}

	/**
	 * 관리자가 결재복원(결재회수,결재복원)시  해당 결재자의 검토요청 내용 삭제처리
	 */
	public void remove(Map<String, String> map) {
		this.apprExamDao.remove(map);
		
	}

	/**
	 *  검토자 존재 여부
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public boolean existSetExamUser(ApprExam apprExam) {  
		
		Map<String, String> map = new HashMap<String, String>();
		String [] examUserIds = StringUtils.split(apprExam.getExamUserId(), ";");
		int existCnt = 0;
		for(int i=0; i<examUserIds.length; i++){

			map.put("userId", 	examUserIds[i]);
			map.put("apprId", 		apprExam.getApprId());
			
			if(apprExamDao.existExamUser(map)) {
				existCnt++;
			}
	    }
		
		return existCnt > 0 ;
	
	}
		
	/**
	 * 검토자여부
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public boolean existExamUser(Map map) {  
		
		return this.apprExamDao.existExamUser(map);
	
	}

	/**
	 * 검토요청 권한 부여 여부 
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public boolean existExamIsRequest(Map map) {  
		
		return this.apprExamDao.existExamIsRequest(map);
	
	}
	
	/**
	 * 검토한 문서여부
	 * @param apprEntrust 모델
	 * @return ID
	 */
	public boolean existExamStatus(Map map) {  
		
		return this.apprExamDao.existExamStatus(map);
	
	}
	
}
