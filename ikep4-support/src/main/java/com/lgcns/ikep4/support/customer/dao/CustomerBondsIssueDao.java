package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSearchCondition;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface CustomerBondsIssueDao extends GenericDao<BondsIssueHistory, String>{

    
    /**
     * 품질/클레임 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(CustomerBondsIssueSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 리스트 출력
     * @param customerQualityCliamSearchCondition
     * @return
     */
    List<BondsIssueHistory> listBondsIssueBySearchCondition(CustomerBondsIssueSearchCondition customerQualityCliamSearchCondition);
    
    /**
     * 품질/클레임 상담이력 상세조회 
     * @param itemId
     * @return
     */
    BondsIssueHistory getBondsIssue (int itemId);
    
    /**
     * 품질/클레임 상담이력 생성
     * @param bondsIssueItem
     * @return
     */
    int createBondsIssue( BondsIssueHistory bondsIssueItem ) ;

    /**
     * 품질/클레임 상담이력 삭제
     * @param seqNum
     */
    void deleteBondsIssue( BondsIssueHistory bondsIssue );

    
    /**
     * 품질/클레임 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );

    void updateBondsIssueSubCount( String itemId );
    
    void updateBondsIssueSubInfo(BondsIssueHistory bondsIssue);
    
    void updateMaster( BondsIssueHistory bondsIssue );
 
}
