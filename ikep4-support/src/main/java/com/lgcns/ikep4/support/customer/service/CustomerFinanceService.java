package com.lgcns.ikep4.support.customer.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.customer.model.Finance;
import com.lgcns.ikep4.support.customer.model.FinanceCondition;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.FinanceSearchCondition;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;



/**
 * 
 * 고객 기본정보 서비스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface CustomerFinanceService extends GenericService<Object, String> {
    
    /**
     * 고객 기본정보 리스팅 
     * @param searchCondition
     * @return
     */
    SearchResult<Finance> listFinanceBySearchCondition(FinanceSearchCondition searchCondition);

    /**
     * 고객사의 기본 정보를 가져온다.
     * @param id
     * @return
     */
    Finance readFinance( String id );
    
    /**
     * 고객기본정보 ( 연혁정보)를 가져온다.
     * @param id
     * @return
     */
    List readFinanceCondition( String id);
    
    List profitLossList( String id);
    
    List conditionList( String id);
    
    List rateList( String id);
    
    /**
     * 고객기본정보 (주요멤버)를 가져온다.
     * @param id
     * @return
     */
    List readMainPerson( String id);
    
    /**
     * 고객기본정보 (인원현황)를 가져온다.
     * @param id
     * @return
     */
    PersonStatic readPersonStatic(String id);

    /**
     * 고객사의 상세정보 (고객이 거래(구매/판매)하는 업체 내역)를 가져온다.
     * @param id
     * @return
     */
    List readMainBusiness(String id);
    
    /**
     * 고객사의 상세정보 (관계사 정보)를 가져온다.
     * @param id
     * @return
     */
    List readRelationCompany(String id);

    /**
     * 고객사 기본정보를 저장한다. 
     * @param finance
     * @return
     */
    String saveFinance( Finance finance );

    /**
     * 고객사의 연혁정보를 저장한다.
     * @param financeCondition
     * @param historyCnt TODO
     */
    void saveFinanceCondition( FinanceCondition financeCondition, String historyCnt );

    
    /**
     * 고객사의 설비현황을 저장한다. 
     * @param finance
     */
    void saveFinanceRate( Finance finance );

    /**
     * 고객의 주요임원을 저장한다.
     * @param mainPerson
     * @param personCnt
     */
    void saveMainPerson1( MainPerson mainPerson, String personCnt );

    /**
     * 고객의 keyMan을 저장한다.
     * @param mainPerson
     * @param personCnt
     */
    void saveMainPerson2( MainPerson mainPerson, String personCnt );

    /**
     * 고객의 인원현황을 저장한다.
     * @param personStatic
     */
    void savePersonStatic( PersonStatic personStatic ,String id);

    /**
     * 고객의 관계사현황을 저장한다.
     * @param relationCompany
     */
    void saveRelationCompany( RelationCompany relationCompany , String companyCnt );

    /**
     * 고객의 주거래선을 저장한다. 
     * @param mainBusiness
     * @param buyingCnt
     * @param sellingCnt
     */
    void saveMainBusiness( MainBusiness mainBusiness, String buyingCnt, String sellingCnt );

    /**
     * 고객정보를 삭제한다. (flag = Y)
     * @param finance
     */
    void deleteFinance(String id );
    
    void deleteFinanceCondition(String id );
    
    void deleteFinanceRate(String id );

    
    /**
     * 고객의 구매패턴정보를 가져온다.
     * @param sapId
     * @return
     */
    List getBuyingInfo( String sapId );
    
    /**
     * sap에서 수신된 고객정보를 DB의 temp 테이블에 저장 
     * @param customerFinanceList
     */
    public void updateSapCustomer(List<CustomerBasicInfo> customerFinanceList);


    /**
     * sap에서 수신된 고객정보(구매정보)를 DB의 temp 테이블에 저장 
     * @param customerBuyingInfoList
     */
    public void updateSapCustomerBuyingInfo(List<CustomerBuyingInfo> customerBuyingInfoList);
    
    /**
     * SAP 정보 SYNC
     * @param customerBuyingInfoList
     */
     void financeSAPSync(List<CustomerBasicInfo> customerFinanceList);
    
    /**
     * sap에서 수신 전에 모든 데이터 삭제 (기본정보)
     *
     */
    void deleteSapCustomer();
    
    /**
     * sap에서 수신 전에 모든 데이터 삭제 (구매정보)
     *
     */
    void deleteSapCustomerBuyingInfo();

    /**
     * SAP 고객정보 리스팅 
     * @param searchCondition
     * @return
     */
    SearchResult<Finance> listSAPFinancebySearchCondition( FinanceSearchCondition searchCondition );

    
    /**
     * SAP 고객정보 상세조회
     * @param sapId
     * @return
     */
    Finance readFinanceForSap( String sapId );
    
    String yearInfo(String code);
    
    void saveFinanceYearInfo( Finance finance );
 
    
}
