package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSearchCondition;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface CustomerQualityClaimDao extends GenericDao<QualityClaimHistory, String>{

    
    /**
     * 품질/클레임 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(CustomerQualityClaimSearchCondition searchCondition);
    
    /**
     * 품질/클레임 상담이력 리스트 출력
     * @param customerQualityCliamSearchCondition
     * @return
     */
    List<QualityClaimHistory> listQualityClaimBySearchCondition(CustomerQualityClaimSearchCondition customerQualityCliamSearchCondition);
    
    /**
     * 품질/클레임 상담이력 상세조회 
     * @param itemId
     * @return
     */
    QualityClaimHistory getQualityClaim (int itemId);
    
    /**
     * 품질/클레임 상담이력 생성
     * @param qualityClaimItem
     * @return
     */
    int createQualityClaim( QualityClaimHistory qualityClaimItem ) ;

    /**
     * 품질/클레임 상담이력 삭제
     * @param seqNum
     */
    void deleteQualityClaim( QualityClaimHistory qualityClaim );

    
    /**
     * 품질/클레임 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );

 
}
