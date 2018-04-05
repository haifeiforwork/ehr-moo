/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.dao;

import java.util.List;

import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.wfapproval.work.model.ApDoc;
import com.lgcns.ikep4.framework.core.dao.GenericDao;



/**
 * 품위서 작성 DAO 정의
 * 
 * @author 이동겸(volcote@naver.com)
 * @version $Id: ApDocDao.java 16234 2011-08-18 02:44:36Z giljae $
 */
public interface ApDocDao extends GenericDao<ApDoc, Integer> {
	/**
	 * 목록 가져오기
	 * 
	 * @return
	 */
	public List<ApDoc> selectAll(ApDoc apdoc);
	
	/**
	 * 게시물 개수
	 * 
	 * @return
	 */
	public int selectCount(ApDoc apdoc);
	
	
	/**
	 * 첨부파일 정보 결재첨부파일 테이블 등록
	 * 
	 * @return
	 */
	public String create(FileLink fileLink);
	
	/**
	 * 사용자정의결재선 테이블 등록
	 * 
	 * @return
	 */
	public Integer createApUser(ApDoc apdoc);
	
	/**
	 * 사용자정의결재선목록 테이블 등록
	 * 
	 * @return
	 */
	public Integer createApUserActivity(ApDoc apdoc);
	
	/**
	 * 결재선목록  테이블 등록
	 * 
	 * @return
	 */
	public Integer createApProcess(ApDoc apdoc);
	
	/**
	 * 사용자정의결재선목록 리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectAllApUser(ApDoc apdoc);
	
	/**
	 * 사용자정의결재선목록 결재선 상세 리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectAllApUserList(ApDoc apdoc);
	
	/**
	 * 결재선목록 리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectAllApProcess(ApDoc apdoc);
	
	/**
	 * USER_ID,APPR_ID로  결재선 조회(결재,합의 판단)
	 * 
	 * @return
	 */
	public List<ApDoc> selectApProcessUserId(ApDoc apdoc);
	
	/**
	 * APPR_ID로  수신자리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectApReceive(ApDoc apdoc);

	/**
	 * APPR_ID로  열람권한리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectApAuthUser(ApDoc apdoc);
	
	/**
	 * APPR_ID로  기결재참조리스트 조회
	 * 
	 * @return
	 */
	public List<ApDoc> selectApRelations(ApDoc apdoc);
	
	/**
	 * APPR_ID로  열람권한 리스트 삭제
	 * 
	 * @return
	 */
	public void removeApAuthUser(String apprId);

	/**
	 * APPR_ID로  수신자리스트 삭제
	 * 
	 * @return
	 */
	public void removeApReceive(String apprId);

	/**
	 * APPR_ID로  결재선리스트 삭제
	 * 
	 * @return
	 */
	public void removeApProcess(String apprId);

	
	public ApDoc getApDoc(ApDoc apdoc) ;
	
	public void updateApProcess(ApDoc apdoc);
	
}
