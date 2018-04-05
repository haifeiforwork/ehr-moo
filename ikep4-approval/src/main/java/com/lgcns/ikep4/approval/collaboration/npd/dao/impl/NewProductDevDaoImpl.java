package com.lgcns.ikep4.approval.collaboration.npd.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.approval.collaboration.npd.dao.NewProductDevDao;
import com.lgcns.ikep4.approval.collaboration.npd.model.NewProductDev;
import com.lgcns.ikep4.approval.collaboration.npd.search.NewProductDevSearchCondition;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * 신제품 개발 NewProductDevDao 구현체 클래스
 * 
 * @author pjh
 * @version $Id$
 */
@Repository
public class NewProductDevDaoImpl extends GenericDaoSqlmap<NewProductDev, String> implements NewProductDevDao{
	
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "com.lgcns.ikep4.approval.collaboration.npd.dao.NewProductDev.";
	
	public String create(NewProductDev arg0) {
		return null;
	}

	public boolean exists(String arg0) {
		return false;
	}

	public NewProductDev get(String arg0) {
		return null;
	}

	public void remove(String arg0) {
	}

	public void update(NewProductDev arg0) {
	}
	
	/**
	 * 개발검토의뢰서 목록 Count
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public int getProductDevCount( NewProductDevSearchCondition newProductDevSearchCondition) throws Exception {
		
		return (Integer) sqlSelectForObject( NAMESPACE + "getProductDevCount", newProductDevSearchCondition);
	}
	
	/**
	 * 개발검토의뢰서 목록 조회
	 * @param newProductDevSearchCondition
	 * @return
	 * @throws Exception
	 */
	public List<NewProductDev> getProductDevList( NewProductDevSearchCondition newProductDevSearchCondition) {
		
		
		return sqlSelectForList( NAMESPACE + "getProductDevList" , newProductDevSearchCondition);
	}
	
	/**
	 * 개발검토의뢰서 목록 조회
	 */
	public NewProductDev getDevRequest( NewProductDevSearchCondition newProductDevSearchCondition) throws Exception {
		
		return (NewProductDev) sqlSelectForObject( NAMESPACE + "getDevRequest", newProductDevSearchCondition);
	}
	
	/**
	 * 개발 의뢰 검토 완료 기간 초과 알림 대상 조회
	 * @return
	 * @throws Exception
	 */
	public List<NewProductDev> getScheduleLimitTarget() throws Exception{
		
		return sqlSelectForList( NAMESPACE + "getScheduleLimitTarget");
	}
	
	/**
	 * 개발검토의뢰서 등록
	 * @param dev
	 * @return
	 * @throws Exception
	 */
	public void insertTbDevRequest( NewProductDev newProductDev) throws Exception {
		
		sqlInsert( NAMESPACE + "insertTbDevRequest", newProductDev);
	}
	
	/**
	 * 개발검토의뢰서 결재라인 등록
	 * @param dev
	 * @return
	 * @throws Exception
	 */
	public void insertTbDevReqApprLine( NewProductDev newProductDev) throws Exception {
		
		sqlInsert( NAMESPACE + "insertTbDevReqApprLine", newProductDev);
	}
	
	/**
	 * 개발검토의뢰서 수정
	 * @param newProductDev
	 * @throws Exception
	 */
	public void updateTbDevRequest( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateTbDevRequest", newProductDev);
	}
	
	/**
	 * 결과파일항목ID 수정
	 */
	public void updateRsltFileId(NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateRsltFileId", newProductDev);
	}
	
	/**
	 * 승인/반려 - 개발검토의뢰서 수정
	 */
	public void updateApproveTbDevRequest( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateApproveTbDevRequest", newProductDev);
	}
	
	/**
	 * 승인/반려 - 개발검토의뢰서 결재라인 수정
	 */
	public void updateApproveTbDevReqApprLine( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateApproveTbDevReqApprLine", newProductDev);
	}
	
	/**
	 * 결재라인 수정 
	 */
	public void updateTbDevReqApprLine( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateTbDevReqApprLine", newProductDev);
	}
	
	/**
	 * 개발검토의뢰서 삭제
	 * @param newProductDev
	 * @throws Exception
	 */
	public void deleteTbDevRequest( String mgntNo) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteTbDevRequest", mgntNo);
	}
	
	/**
	 * 개발검토의뢰서 결재라인삭제
	 * @param newProductDev
	 * @throws Exception
	 */
	public void deleteTbDevReqApprLine( String mgntNo) throws Exception {
		
		sqlDelete( NAMESPACE + "deleteTbDevReqApprLine", mgntNo);
	}
	
	/**
	 * 초기화 - 개발검토의뢰서
	 */
	public void initTbDevRequest( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "initTbDevRequest", newProductDev);
	}
	
	/**
	 * 초기화 - 개발검토의뢰서  결재라인
	 */
	public void initTbDevReqApprLine( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "initTbDevReqApprLine", newProductDev);
	}
	
	/**
	 * 접수 개발검토의뢰서
	 */
	public void updateReciptTbDevRequest( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateReciptTbDevRequest", newProductDev);
	}
	
	/**
	 * 접수 개발검토의뢰서 결재라인
	 */
	public void updateReciptTbDevReqApprLine( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateReciptTbDevReqApprLine", newProductDev);
	}
	
	/**
	 * 요구일정 수정
	 * @param newProductDev
	 * @throws Exception
	 */
	public void updateReqScheduleCd( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateReqScheduleCd", newProductDev);
	}
	
	/**
	 * 개발제안서 파일 ID 수정
	 * @param testRequest
	 * @throws Exception
	 */
	public void updateFileId( NewProductDev newProductDev) throws Exception {
		
		sqlUpdate( NAMESPACE + "updateFileId" , newProductDev);
	}
}
