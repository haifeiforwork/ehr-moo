package com.lgcns.ikep4.approval.collaboration.npd.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.npd.search.NewProductDevSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 신제품 개발 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface NewProductDevService {
	
	/**
	 * 신제품 개발 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public SearchResult<NewProductDev> getNewProductDevList( NewProductDevSearchCondition newProductDevSearchCondition, User user) throws Exception;
	
	/**
	 * 조회 및 기본셋팅
	 * @param newProductDevSearchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNewProductObject( NewProductDevSearchCondition newProductDevSearchCondition, User user) throws Exception;
	
	/**
	 * 게시물 단일 정보 조회
	 * @param searchCondition
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getNewProductDev( NewProductDevSearchCondition searchCondition, User user) throws Exception;
	
	/**
	 * 개발 의뢰 검토 완료 기간 초과 알림 대상 조회
	 * @return
	 * @throws Exception
	 */
	public List<?> getScheduleLimitTarget() throws Exception;
	
	/**
	 * 신제품 개발  - 개발검토의뢰서 등록
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void createNewProductDev( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 신제품개발 - 개발검토의뢰서 수정
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public NewProductDev updateNewProductDev( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 요구일정 수정
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void updateReqScheduleCd( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 결과파일 저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void updateRsltFile( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 승인
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveNewProductDev( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 상신/저장
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void approveWithSave( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 메일보내기
	 * @param mailType
	 * @param newProductDev
	 * @throws Exception
	 */
	public void sendApproveMail( int mailType, NewProductDev newProductDev) throws Exception;
	
	/**
	 * 메일 배치
	 * @throws Exception
	 */
	public void sendScheduleLimitSend() throws Exception;
	
	/**
	 * 신제품 개발의뢰서 삭제
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void deleteNewProductDev( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 신제품 개발 초기화
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void initRejctNewProductDev(  NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 접수자 변경
	 * @param newProductDev
	 * @param user
	 * @throws Exception
	 */
	public void changeReceiptNewProductDev(  NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 파일정보 조회
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public NewProductDev getFileObject( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 파일 저장후 갱신데이터 리턴
	 * @param testRequest
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public String ajaxUdateFile( NewProductDev newProductDev, User user) throws Exception;
	
	/**
	 * 권한 확인
	 * @param NewProductDev
	 * @param user
	 * @param statCd
	 * @throws Exception
	 */
	public NewProductDev checkPermission( NewProductDev newProductDev, User user, int statCd) throws Exception;
}
