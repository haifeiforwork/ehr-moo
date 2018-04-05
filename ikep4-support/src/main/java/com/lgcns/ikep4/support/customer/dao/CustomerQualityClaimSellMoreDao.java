package com.lgcns.ikep4.support.customer.dao;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellMoreSearchCondition;





public interface CustomerQualityClaimSellMoreDao extends GenericDao<CustomerQualitySub, String>{



     /**
      * 댓글 검색조건을 충족하는 댓글의 총 갯수를 구한다.
      * @param searchCondition
      * @return
      */
    Integer countBySearchCondition( CustomerQualityClaimSellMoreSearchCondition searchCondition );

    /**
     * 댓글 검색조건을 이용해서 게시글 목록을 조회한다.
     * @param searchCondition
     * @return
     */
    List<CustomerQualitySub> listBySearchCondition( CustomerQualityClaimSellMoreSearchCondition searchCondition );

    /**
     * 스텝수를 업데이트 한다.
     * @param customerQualityClaimSellMore
     */
    void updateStep( CustomerQualitySub customerQualitySub );

    /**
     * 댓글의 하위 답변 댓글의 갯수를 조회한다.
     * @param linereplyId id조회 대상 댓글 ID
     * @return 자식 댓글 갯수
     */
    Integer countChildren( String linereplyId );

    /**
     * 삭제 플러그를 "1"로 업데이트 한다.
     * @param customerQualityClaimSellMore
     */
    void logicalDelete( CustomerQualitySub customerQualitySub );

    /**
     * 댓글을 삭제한다.
     * @param linereplyId 삭제 대상 댓글 ID
     */
    void physicalDelete( String linereplyId );

    /**
     * 댓글 쓰레드를 댓글 ID를 기준으로 삭제한다.
     * @param linereplyId
     */
    void physicalDeleteThreadByQualityClaimSellMoreId( String linereplyId );
    
    List<HashMap<String, String>> listHistorysMemo();
     
    void updateSubMaster( CustomerQualitySub object );
     
}
