package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.search.MainSellingSearchCondition;


/**
 * 
 * 주요판매처 DAO
 *
 * @author SongHeeJung
 * @version $Id$
 */
public interface CustomerMainSellingDao extends GenericDao<MainSelling, String> {

    /**
     * 리스트 카운팅 
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition( MainSellingSearchCondition searchCondition );

  
    /**
     * 고객별 주요판매처 등록 현황 리스트 
     */
    List<MainSelling> listMainSellingBySearchCondition( MainSellingSearchCondition searchCondition );

    /**
     * 고객별 주요판매처 상세정보 조회
     * @param customerId
     * @return
     */
    List<MainSelling> listMainSellingDetail (String customerId);
    
    /**
     * 마지막으로 주어진 sellingId를 가져온다.
     * @param customerId
     * @return
     */
    int getSellingId(String customerId);
    
    /**
     * 주요판매처를 등록한다.
     * @param mainSelling
     */
    void createMainSelling(MainSelling mainSelling);
    
    
    /**
     * 판매처 등록시 업데이트 내용을 고객사에 속한 모든 판매처 정보에 등록한다.
     */
    void updateRegistInfo (MainSelling mainSelling);
    
    /**
     * 주요판매처를 수정한다.
     * @param mainSelling
     */
    void modifyMainSelling(MainSelling mainSelling);


    /**
     * 주요판매처 정보 (1Item)을 조회한다.
     * @param seqNum
     * @return
     */
    MainSelling getMainSellingItem( int seqNum );


    /**
     * 주요판매처 삭제 
     * @param mainSelling
     */
    void deleteMainSelling( MainSelling mainSelling );

    /**
     * 판매처 갯수 셀렉트 (고객사 기준)
     * @param customerId
     * @return
     */

    int checkMainSelling( String customerId );


    /**
     * sap의 고객명으로 동기화 시킨다.
     * @param customerId
     */
    void syncCustomerName( MainSelling mainSelling);
    
}
