package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellSearchCondition;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface CustomerQualityClaimSellDao extends GenericDao<QualityClaimSellHistory, String>{

    
    /**
     * 품질/클레임 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(CustomerQualityClaimSellSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 리스트 출력
     * @param customerQualityCliamSearchCondition
     * @return
     */
    List<QualityClaimSellHistory> listQualityClaimSellBySearchCondition(CustomerQualityClaimSellSearchCondition customerQualityCliamSearchCondition);
    
    /**
     * 품질/클레임 상담이력 상세조회 
     * @param itemId
     * @return
     */
    QualityClaimSellHistory getQualityClaimSell (int itemId);
    
    /**
     * 품질/클레임 상담이력 생성
     * @param qualityClaimSellItem
     * @return
     */
    int createQualityClaimSell( QualityClaimSellHistory qualityClaimSellItem ) ;

    /**
     * 품질/클레임 상담이력 삭제
     * @param seqNum
     */
    void deleteQualityClaimSell( QualityClaimSellHistory qualityClaimSell );

    
    /**
     * 품질/클레임 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );
    
    void updateQualityClaimSellMoreCount( String itemId );
    
    void updateQualityClaimSellMoreInfo( QualityClaimSellHistory qualityClaimSell );

    void updateMaster( QualityClaimSellHistory qualityClaimSell );
 
}
