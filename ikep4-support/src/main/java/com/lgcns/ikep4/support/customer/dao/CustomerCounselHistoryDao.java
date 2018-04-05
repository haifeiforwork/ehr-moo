package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.search.CounselHistorySearchCondition;


/**
 * 
 * 고객별 상담내역 DAO
 *
 * @author SongHeeJung
 * @version $Id$
 */
public interface CustomerCounselHistoryDao extends GenericDao<CounselHistory, String>{

    /**
     * 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(CounselHistorySearchCondition searchCondition);
    
    /**
     * 상담이력 리스트 출력
     * @param searchCondition
     * @return
     */
    List<CounselHistory> listCounselHistoryBySearchCondition(CounselHistorySearchCondition searchCondition);

    /**
     * 상담이력 상세조회 
     * @param itemId
     * @return
     */
    CounselHistory getCounselHistory( int itemId );

    /**
     * 상담이력 등록
     * @param counselHistory
     * @return
     */
    int createCounselHistory( CounselHistory counselHistory );

    /**
     * 상담이력 삭제
     * @param counselHistory
     */
    void deleteCounselHistory( CounselHistory counselHistory );

    /**
     * 상담이력 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );

    /**
     * sap고객명 동기화
     * @param counselHistory
     */
    void syncCustomerName( CounselHistory counselHistory );
}
