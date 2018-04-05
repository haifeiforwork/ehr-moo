package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.search.MainSellingSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

/**
 * 
 * 고객들의 판매처 정보를 관리한다.
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface CustomerMainSellingService {

    /**
     * 고객별 판매처 현황을 리스팅한다.
     * @param searchCondition
     * @return
     */
    SearchResult<MainSelling> listMainSellingBySearchCondition( MainSellingSearchCondition searchCondition );

    /**
     * 고객별 판매처 상세정보를 조회한다.
     * @param customerId
     * @return
     */
    List<MainSelling> listMainSellingDetail(String customerId);

    /**
     * 고객별 판매처 상세정보를 등록한다. (기존에 한건이라도 고객사 판매처 정보가 있었던 경우)
     * @param mainSelling
     */
    void createMainSelling( MainSelling mainSelling);
    
    
    /**
     * 고객별 판매처 정보를 수정한다.
     * @param mainSelling
     */
    void modifyMainSelling( MainSelling mainSelling);
    
    /**
     * seqNum를 이용하여 판매처 정보를 조회한다.
     * @param seqNum
     * @return
     */
    MainSelling getMainSellingItem(int seqNum);

    /**
     * 판매처 정보를 삭제한다.
     * @param mainSelling
     */
    void deleteMainSelling( MainSelling mainSelling );

    /**
     * 기존에 저장된 판매처 갯수 (고객사 기준)
     * @param customerId
     * @return
     */
    int checkMainSelling( String customerId );
    
    /**
     * SAP 의 name을 동기화 시킨다.
     * @param customerId
     */
    void syncCustomerName( MainSelling mainSelling);
    
}
