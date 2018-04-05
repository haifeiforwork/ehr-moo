package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.Realty;
import com.lgcns.ikep4.support.customer.search.RealtySearchCondition;


/**
 * 
 * 고객별 상담내역 DAO
 *
 * @author SongHeeJung
 * @version $Id$
 */
public interface CustomerRealtyDao extends GenericDao<Realty, String>{

    /**
     * 상담이력 글 갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(RealtySearchCondition searchCondition);
    
    /**
     * 상담이력 리스트 출력
     * @param searchCondition
     * @return
     */
    List<Realty> listRealtyBySearchCondition(RealtySearchCondition searchCondition);
    
    List<Realty> selectRealty();

    /**
     * 상담이력 상세조회 
     * @param itemId
     * @return
     */
    Realty getRealty( int itemId );

    /**
     * 상담이력 등록
     * @param realty
     * @return
     */
    int createRealty( Realty realty );

    /**
     * 상담이력 삭제
     * @param realty
     */
    void deleteRealty( Realty realty );

    /**
     * 상담이력 아이템 댓글 갯수 업데이트
     * @param itemId
     */
    void updateLinereplyCount( String itemId );

    /**
     * sap고객명 동기화
     * @param realty
     */
    void syncCustomerName( Realty realty );
}
