package com.lgcns.ikep4.support.partner.dao;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;





public interface PartnerQualityClaimSellMoreDao extends GenericDao<PartnerQualitySub, String>{



     /**
      * 댓글 검색조건을 충족하는 댓글의 총 갯수를 구한다.
      * @param searchCondition
      * @return
      */
    Integer countBySearchCondition( PartnerQualityClaimSellMoreSearchCondition searchCondition );

    /**
     * 댓글 검색조건을 이용해서 게시글 목록을 조회한다.
     * @param searchCondition
     * @return
     */
    List<PartnerQualitySub> listBySearchCondition( PartnerQualityClaimSellMoreSearchCondition searchCondition );

    /**
     * 스텝수를 업데이트 한다.
     * @param partnerQualityClaimSellMore
     */
    void updateStep( PartnerQualitySub partnerQualitySub );
    
    void updateTlComment( PartnerQualitySub object );
    
    void updateOfComment( PartnerQualitySub object );

    /**
     * 댓글의 하위 답변 댓글의 갯수를 조회한다.
     * @param linereplyId id조회 대상 댓글 ID
     * @return 자식 댓글 갯수
     */
    Integer countChildren( String linereplyId );

    /**
     * 삭제 플러그를 "1"로 업데이트 한다.
     * @param partnerQualityClaimSellMore
     */
    void logicalDelete( PartnerQualitySub partnerQualitySub );

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
     
    void updateSubMaster( PartnerQualitySub object );
    
    List<User> getUserInfoList(String userId);
    
    List<User> getUserInfoList2(String id);
    
    List<User> getUserInfoList3(String id);
     
}
