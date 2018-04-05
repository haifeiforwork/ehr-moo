package com.lgcns.ikep4.support.customer.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.customer.model.BasicHistory;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.BasicInfoReader;
import com.lgcns.ikep4.support.customer.model.BuyingInfo;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.BasicInfoReaderSearchCondition;
import com.lgcns.ikep4.support.customer.search.BasicInfoSearchCondition;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;

public interface CustomerBasicInfoDao extends GenericDao<Object,String> {

    /**
     * 고객기본정보 글갯수 조회
     * @param searchCondition
     * @return
     */
    Integer countBySearchCondition(BasicInfoSearchCondition searchCondition );
    
    /**
     * 고객 기본정보 리스트 조회
     */ 
    List<BasicInfo> listBasicInfoBySearchCondition(BasicInfoSearchCondition searchCondition);
    
    public Integer countReaderBySearchCondition(BasicInfoReaderSearchCondition searchCondition);
    
    public List<BasicInfoReader> listReaderBySearchCondition(BasicInfoReaderSearchCondition searchCondition);

    /**
     * 고객사의 상세정보(기본정보)를 가져온다.
     * @param id
     * @return
     */
    BasicInfo readBasicInfo( String id );

    /**
     * 고객사의 상세정보(연혁정보)를 가져온다.
     * @param id
     * @return
     */
    List<BasicHistory> readHistory( String id );
    
    /**
     * 고객사의 상세정보(주요멤버)를 가져온다.
     */
    
    List<MainPerson> readMainPerson(String id);
    
    /**
     * 고객사의 상세정보(고객의 직급별 인원현황)을 가져온다.
     * @param id
     * @return
     */
    PersonStatic readPersonStatic(String id);
    
    /**
     * 고객사의 상세정보 (고객이 거래(구매/판매)하는 업체 내역)를 가져온다.
     * @param id
     * @return
     */
    List<MainBusiness> readMainBusiness(String id);
    
    /**
     * 고객사의 상세정보 (관계사정보)를 가져온다.
     * @param id
     * @return
     */
    List<RelationCompany> readRelationCompany(String id);

    /**
     * 고객사의 기본정보를 저장한다. (수정)
     * @param basicInfo
     * @return
     */
    void saveBasicInfo( BasicInfo basicInfo );

    /**
     * 고객사의 연혁정보를 저장한다. (수정)
     * @param basicHistory
     */
    void saveBasicHistory( BasicHistory basicHistory );

    /**
     * 고객사의 기존 연혁정보를 모두 삭제 한다.
     * @param id
     */
    void deleteBasicHistory( String id );

    /**
     * 고객사의 설비현황정보를 저장한다.
     * @param basicInfo
     */
    void saveBasicEquipment( BasicInfo basicInfo );

    /**
     * 고객사의 기존 임원정보를 모두 삭제한다.
     * @param id
     */
    void deleteMainPerson1( String id );

    /**
     * 고객사의 임원정보를 저장한다. 
     * @param mainPerson
     */
    void saveMainPerson1( MainPerson mainPerson );

    
    /**
     * 고객사의 keyMan정보를 저장한다.
     * @param mainPerson
     */
    void saveMainPerson2( MainPerson mainPerson );

    /**
     * 고객사의 기존 keyman정보를 모두 삭제한다.
     * @param id
     */
    void deleteMainPerson2( String id );

    /**
     * 기존에 데이터가 있는지 확인한다. (인원현황)
     * @param id
     * @return 
     */
    int selectPersonStaticCnt (String id);
    
    /**
     * 고객사의 인원현황을 저장한다.
     * @param personStatic
     */
    void savePersonStatic( PersonStatic personStatic );

    /**
     * 고객사의 관계사현황을 모두 삭제한다.
     * @param id
     */
    void deleteRelationCompany( String id );

    /**
     * 고객사의 관계사현황을 저장한다.
     * @param relationCompany
     */
    void saveRelationCompany( RelationCompany relationCompany );

    /**
     * 고객사의 관계사 현황을 생성저장한다.
     * @param personStatic
     */
    void createPersonStatic( PersonStatic personStatic );

    /**
     * 고객사의 주거래선 정보를 모두 삭제한다.
     * @param id
     */
    void deleteMainBusiness( String id );

    /**
     * 고객사 주거래선 (구매)를 저장한다.
     * @param mainBusiness
     */
    void saveMainBusinessBuying( MainBusiness mainBusiness );

    /**
     * 고객사 주거래선 (판매)를 저장한다.
     * @param mainBusiness
     */
    void saveMainBusinessSelling( MainBusiness mainBusiness );
    
    /**
     * 고객사의 기본정보를 생성한다.(새로운 고객정보 생성)
     * @param basicInfo
     */
    void createBasicInfo (BasicInfo basicInfo);

    /**
     * 고객사의 기본정보를 삭제한다. (flag = Y)
     * @param basicInfo
     */
    void deleteBasicInfo( BasicInfo basicInfo );
    
    boolean existsReference(BasicInfo basicInfo);
    
    void updateHitCount( BasicInfo basicInfo );
    
    void updateReference( BasicInfo basicInfo );
    
    void createReference( BasicInfo basicInfo );

    /**
     * 고객사의 구매패턴정보를 가져온다.
     * @param sapId
     * @return
     */
    List<BuyingInfo> getBuyingInfo( String sapId );

    /**
     * sap에서 수신한 고객정보를 저장한다.
     * @param customerBasicInfo
     */
    void updateSapCustomer( CustomerBasicInfo customerBasicInfo );

    /**
     * sap에서 수신한 고객정보(구매정보)를 저장한다.
     * @param customerBuyingInfo
     */
    void updateSapCustomerBuyingInfo( CustomerBuyingInfo customerBuyingInfo );
    
    /**
     * SAP 고객정보 SYNC(고객정보관리)
     * @param customerBasicInfo
     */
    void updateBasicInfoSAPSync( String sapId );
    /**
     * SAP 고객정보 SYNC(인물정보)
     * @param customerBasicInfo
     */
    void updateManInfoSAPSync( String sapId);
    /**
     * SAP 고객정보 SYNC(상담내역)
     * @param customerBasicInfo
     */
    void updateCounselHistorySAPSync(String sapId);
    /**
     * SAP 고객정보 SYNC(주요판매처)
     * @param customerBasicInfo
     */
    void updateMainSellingSAPSync(String sapId);
    
    /**
     * 배치돌때 insert전에 모든 값 delete
     *
     */
    void deleteSapCustomer();
    
    /**
     * 배치돌때 insert전에 모든 값 delete
     *
     */
    void deleteSapCustomerBuyingInfo();

    /**
     * SAP 고객정보 리스팅 
     * @param searchCondition
     * @return 
     */
    List<BasicInfo> listSAPBasicInfobySearchCondition( BasicInfoSearchCondition searchCondition );

    /**
     * SAP 고객정보 갯수 select 
     * @param searchCondition
     * @return
     */
    Integer countBySearchConditionForSAP( BasicInfoSearchCondition searchCondition );

    /**
     * sap 고객정보 상세조회
     * @param sapId
     * @return
     */
    BasicInfo readBasicInfoForSap( String sapId );

   
    
    
    
    
}
