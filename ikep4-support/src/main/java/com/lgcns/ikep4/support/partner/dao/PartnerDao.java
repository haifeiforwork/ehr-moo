package com.lgcns.ikep4.support.partner.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerManCareer;
import com.lgcns.ikep4.support.partner.model.PartnerManFamily;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;


/**
 * 
 * 인물정보 DAO
 *
 * 인물정보 PERSON_INFO,PERSON_FAMILY,PERSON_CAREER 테이블 CRUD와 목록조회 등 DB와의 연동을 담당하는 클래스이다. 
 * @author SongHeeJung
 * @version $Id$
 */
public interface PartnerDao extends GenericDao<PartnerManInfoItem, String>{
	
	/**
	 * 인물정보 목록을 가져온다.
	 * @return 인물목록의 모든 정보들 
	 */

	


	/**
	 * 사용자 이름을 가져온다.
	 * @param userId
	 * @return
	 */
	String getUserName(String userId);




    /**
     * 고객정보 조회 권한이 있는지 체크 
     * @param userId
     * @return
     */

    Integer checkAccess( String userId );
    
    Integer checkRegInfo( PartnerClaimSellHistory qualityClaimSell );
    
    Partner getPortalDefault();
    
    List<PartnerManInfoItem> getPartnerId( String partnerName);
	
    /**
	 * 인물정보 목록을 가져온다.
	 * @return 인물목록의 모든 정보들 
	 */
	
	List<PartnerManInfoItem> listManInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition);
	
	List<PartnerManInfoItem> listRegInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition);
	
	List<PartnerManInfoItem> listItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition);

	
	
	/**
	 * 검색조건을 충족하는 인물정보의 총 갯수를 구한다.
	 * @param searchCondition 인물정보 검색조건
	 * @return 인물정보 갯수
	 */
	Integer countBySearchCondition(PartnerManInfoItemSearchCondition searchCondition);
	
	Integer countRegBySearchCondition(PartnerManInfoItemSearchCondition searchCondition);
	

	/**
	 * 인물 상세정보를 조회한다.
	 * @param itemId 인물id
	 * @return 인물 상세정보
	 */	
	PartnerManInfoItem readManInfoItem(int itemId);
	
	/**
	 * 인물 상세정보(가족정보)를 조회한다.
	 * @param itemId
	 * @return 인물의 가족정보
	 */
	List<PartnerManInfoItem> listManFamily(String itemId);
	
	/**
	 * 인물 상세정보(경력정보)를 조회한다.
	 * @param itemId
	 * @return 인물의 경력정보
	 */
	List<PartnerManInfoItem> listManCareer(String itemId);
	
	/**
	 * 인물 상세정보를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfo(PartnerManInfoItem manInfoItem);
	
	void updateManInfoMaster(PartnerManInfoItem manInfoItem);
	
	/**
	 * 인물 상세정보(가족정보)를 수정한다.
	 * @param manInfoItem
	 */
	void updateManInfoFamily(PartnerManFamily manFamily);


	/**
	 * 인물 상세정보(가족정보)를 삭제한다.
	 * @param manFamily
	 */
    void deleteManInfoFamily( String itemId );
    
    /**
     * 인물 상세정보(경력정보)를 수정한다.
     * @param manCareer
     */
    void updateManInfoCareer(PartnerManCareer manCareer);
    /**
     * 인물 상세정보(경력정보)를 삭제한다.
     * @param manCareer
     */
    void deleteManInfoCareer( String itemId);
    
    /**
     * 인물 상세정보를 등록한다.
     * @param manInfoItem
     */
    void createManInfoItem( PartnerManInfoItem manInfoItem);
    
    int createManInfoMaster( PartnerManInfoItem manInfoItem);
	
	
	/**
	 * 인물 상세정보(가족정보)를 저장한다.
	 * @param manFamily
	 */
	void createManInfoFamily(PartnerManFamily manFamily);
	
	/**
     * 인물 상세정보(경력정보)를 저장한다.
     * @param manCareer
     */
    void createManInfoCareer( PartnerManCareer manCareer );
	
	/**
	 * 인물정보 가져온다.(인물ID)
	 * @param manInfoItem
	 * @return
	 */
	int getSeqNum(PartnerManInfoItem manInfoItem);

	/**
	 * 인물정보를 삭제한다.
	 * @param seqNum
	 */
	void deleteManInfo(PartnerManInfoItem manInfoItem);

	void deleteManInfoMaster(PartnerManInfoItem manInfoItem);
    
}
