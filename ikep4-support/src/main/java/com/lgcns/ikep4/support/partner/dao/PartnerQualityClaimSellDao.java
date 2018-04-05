package com.lgcns.ikep4.support.partner.dao;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellSearchCondition;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface PartnerQualityClaimSellDao extends GenericDao<PartnerClaimSellHistory, String>{

    
    /**
     * 품질/클레임 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(PartnerQualityClaimSellSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 리스트 출력
     * @param partnerQualityCliamSearchCondition
     * @return
     */
    List<PartnerClaimSellHistory> listQualityClaimSellBySearchCondition(PartnerQualityClaimSellSearchCondition partnerQualityCliamSearchCondition);
    
    List<PartnerClaimSellHistory> crStatisticsList(PartnerQualityClaimSellSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 상세조회 
     * @param itemId
     * @return
     */
    PartnerClaimSellHistory getQualityClaimSell (int itemId);
    
    PartnerClaimSellHistory getQualityClaimSellOther (int itemId);
    
    /**
     * 품질/클레임 상담이력 생성
     * @param qualityClaimSellItem
     * @return
     */
    int createQualityClaimSell( PartnerClaimSellHistory qualityClaimSellItem ) ;

    /**
     * 품질/클레임 상담이력 삭제
     * @param seqNum
     */
    void deleteQualityClaimSell( PartnerClaimSellHistory qualityClaimSell );

    
    /**
     * 품질/클레임 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );
    
    void updateQualityClaimSellMoreCount( String itemId );
    
    void updateQualityClaimSellMoreInfo( PartnerClaimSellHistory qualityClaimSell );

    void updateMaster( PartnerClaimSellHistory qualityClaimSell );
 
}
