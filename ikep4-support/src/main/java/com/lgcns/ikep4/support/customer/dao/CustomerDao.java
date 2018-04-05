package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.ManCareer;
import com.lgcns.ikep4.support.customer.model.ManFamily;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;

/**
 * 
 * 인물정보 DAO
 *
 * 인물정보 PERSON_INFO,PERSON_FAMILY,PERSON_CAREER 테이블 CRUD와 목록조회 등 DB와의 연동을 담당하는 클래스이다. 
 * @author SongHeeJung
 * @version $Id$
 */
public interface CustomerDao extends GenericDao<ManInfoItem, String>{
	
	/**
	 * 인물정보 목록을 가져온다.
	 * @return 인물목록의 모든 정보들 
	 */
	
	List<ManInfoItem> listManInfoItemBySearchCondition(ManInfoItemSearchCondition manInfoItemSearchCondition);

	
	
	/**
	 * 검색조건을 충족하는 인물정보의 총 갯수를 구한다.
	 * @param searchCondition 인물정보 검색조건
	 * @return 인물정보 갯수
	 */
	Integer countBySearchCondition(ManInfoItemSearchCondition searchCondition);
	

	/**
	 * 인물 상세정보를 조회한다.
	 * @param itemId 인물id
	 * @return 인물 상세정보
	 */	
	ManInfoItem readManInfoItem(int itemId);
	
	/**
	 * 인물 상세정보(가족정보)를 조회한다.
	 * @param itemId
	 * @return 인물의 가족정보
	 */
	List<ManInfoItem> listManFamily(String itemId);
	
	/**
	 * 인물 상세정보(경력정보)를 조회한다.
	 * @param itemId
	 * @return 인물의 경력정보
	 */
	List<ManInfoItem> listManCareer(String itemId);
	
	/**
	 * 인물 상세정보를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfo(ManInfoItem manInfoItem);
	
	/**
	 * 인물 상세정보(가족정보)를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfoFamily(ManFamily manFamily);


	/**
	 * 인물 상세정보(가족정보)를 삭제한다.
	 * @param manFamily
	 */
    void deleteManInfoFamily( String itemId );
    
    /**
     * 인물 상세정보(경력정보)를 수정한다.
     * @param manCareer
     */
    void updateManInfoCareer(ManCareer manCareer);
    /**
     * 인물 상세정보(경력정보)를 삭제한다.
     * @param manCareer
     */
    void deleteManInfoCareer( String itemId);
    
    /**
     * 인물 상세정보를 등록한다.
     * @param manInfoItem
     */
    void createManInfoItem( ManInfoItem manInfoItem);
	
	
	/**
	 * 인물 상세정보(가족정보)를 저장한다.
	 * @param manFamily
	 */
	void createManInfoFamily(ManFamily manFamily);
	
	/**
     * 인물 상세정보(경력정보)를 저장한다.
     * @param manCareer
     */
    void createManInfoCareer( ManCareer manCareer );
	
	/**
	 * 인물정보 가져온다.(인물ID)
	 * @param manInfoItem
	 * @return
	 */
	int getSeqNum(ManInfoItem manInfoItem);

	/**
	 * 인물정보를 삭제한다.
	 * @param seqNum
	 */
	void deleteManInfo(ManInfoItem manInfoItem);

	/**
	 * 사용자 이름을 가져온다.
	 * @param userId
	 * @return
	 */
	String getUserName(String userId);



	/**
	 * sap의 고객명으로 동기화
	 * @param manInfoItem
	 */
    void syncCustomerName( ManInfoItem manInfoItem );


    /**
     * 고객정보 조회 권한이 있는지 체크 
     * @param userId
     * @return
     */

    Integer checkAccess( String userId );
	

	
    
}
