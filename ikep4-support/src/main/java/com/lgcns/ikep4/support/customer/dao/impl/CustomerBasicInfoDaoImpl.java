package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerBasicInfoDao;
import com.lgcns.ikep4.support.customer.model.BasicHistory;
import com.lgcns.ikep4.support.customer.model.BasicInfo;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.BasicInfoReaderSearchCondition;
import com.lgcns.ikep4.support.customer.search.BasicInfoSearchCondition;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;


@Repository
public class CustomerBasicInfoDaoImpl extends GenericDaoSqlmap<Object, String> implements CustomerBasicInfoDao{

    private static final String NAMESPACE = "support.customer.dao.basicInfo.";
    
    public String create( BasicInfo arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public BasicInfo get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( BasicInfo arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public Integer countBySearchCondition( BasicInfoSearchCondition searchCondition ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List listBasicInfoBySearchCondition( BasicInfoSearchCondition searchCondition ) {
        
        return this.sqlSelectForList( NAMESPACE + "basicInfoListBySearchCondition", searchCondition );
    }
    
    public Integer countReaderBySearchCondition(BasicInfoReaderSearchCondition searchCondition) {
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countReaderBySearchCondition", searchCondition);
	}
    
    public List listReaderBySearchCondition(BasicInfoReaderSearchCondition searchCondition) {
		return this.sqlSelectForList(NAMESPACE + "basicInfoListReaderBySearchCondition", searchCondition);
	}

    public BasicInfo readBasicInfo( String id ) {
        
        return (BasicInfo)this.sqlSelectForObject( NAMESPACE + "getBasicInfo", id );
    }


    public List readHistory( String id ) {
       
        return this.sqlSelectForList( NAMESPACE + "getHistory", id );
    }

    public String create( Object arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( Object arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public List readMainPerson( String id ) {
        
        return this.sqlSelectForList( NAMESPACE + "getMainPerson", id );
    }

    public PersonStatic readPersonStatic( String id ) {
        
        return (PersonStatic)this.sqlSelectForObject( NAMESPACE + "getPersonStatic", id );
    }

    public List readMainBusiness( String id ) {
        
        return this.sqlSelectForList( NAMESPACE+"getMainBusiness", id );
    }

    public List readRelationCompany( String id ) {
        
        return this.sqlSelectForList( NAMESPACE + "getRelationCompany", id );
    }

    public void saveBasicInfo( BasicInfo basicInfo ) {
        
        this.sqlUpdate( NAMESPACE + "saveBasicInfo", basicInfo );
    }

    public void saveBasicHistory( BasicHistory basicHistory ) {
       this.sqlInsert( NAMESPACE + "saveBasicHistory", basicHistory );
        
    }

    public void deleteBasicHistory( String id ) {
       this.sqlDelete( NAMESPACE + "deleteBasicHistory", id );
        
    }

    public void saveBasicEquipment( BasicInfo basicInfo ) {
        this.sqlUpdate( NAMESPACE + "saveBasicEquipment", basicInfo );
        
    }

    public void deleteMainPerson1( String id ) {
        this.sqlDelete( NAMESPACE + "deleteMainPerson1", id );
        
    }

    public void saveMainPerson1( MainPerson mainPerson ) {
        this.sqlInsert( NAMESPACE + "saveMainPerson1", mainPerson );
        
    }

    public void saveMainPerson2( MainPerson mainPerson ) {
        this.sqlInsert( NAMESPACE + "saveMainPerson2", mainPerson );
        
    }

    public void deleteMainPerson2( String id ) {
        this.sqlDelete( NAMESPACE + "deleteMainPerson2", id );
        
    }
    
    public int selectPersonStaticCnt (String id){
        return (Integer)this.sqlSelectForObject( NAMESPACE + "psersonStaticCnt", id );
    }

    public void savePersonStatic( PersonStatic personStatic ) {
       this.sqlUpdate( NAMESPACE + "savePersonStatic", personStatic );
        
    }
    
    public void createPersonStatic( PersonStatic personStatic ) {
        this.sqlInsert( NAMESPACE + "createPersonStatic", personStatic );
        
    }

    public void deleteRelationCompany( String id ) {
        this.sqlDelete( NAMESPACE + "deleteRelationCompany", id );
        
    }

    public void saveRelationCompany( RelationCompany relationCompany ) {
        this.sqlInsert( NAMESPACE + "saveRelationCompany", relationCompany );
        
    }

    public void deleteMainBusiness( String id ) {
       this.sqlDelete( NAMESPACE + "deleteMainBusiness", id );
        
    }

    public void saveMainBusinessBuying( MainBusiness mainBusiness ) {
        this.sqlInsert( NAMESPACE + "saveMainBusinessBuying", mainBusiness );
        
    }

    public void saveMainBusinessSelling( MainBusiness mainBusiness ) {
        this.sqlInsert( NAMESPACE + "saveMainBusinessSelling", mainBusiness );
        
    }

    public void createBasicInfo( BasicInfo basicInfo ) {
        this.sqlInsert( NAMESPACE + "createBasicInfo", basicInfo );
        
    }

    public void deleteBasicInfo( BasicInfo basicInfo ) {
        this.sqlUpdate( NAMESPACE + "deleteBasicInfo", basicInfo );
        
    }
    
    public boolean existsReference(BasicInfo basicInfo) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "existsReference", basicInfo);
		return count > 0;
	}
    
    public void updateHitCount( BasicInfo basicInfo ) {
        this.sqlUpdate( NAMESPACE + "updateHitCount", basicInfo );
    }
    
    public void updateReference( BasicInfo basicInfo ) {
        this.sqlUpdate( NAMESPACE + "updateReference", basicInfo );
    }
    
    public void createReference( BasicInfo basicInfo ) {
        this.sqlInsert( NAMESPACE + "createReference", basicInfo );
    }

    public List getBuyingInfo( String sapId ) {
       return this.sqlSelectForList( NAMESPACE + "getBuyingInfo", sapId );
    }
    
    

    public void updateSapCustomer( CustomerBasicInfo customerBasicInfo ) {
        this.sqlInsert( NAMESPACE + "updateSapCustomer", customerBasicInfo );
        
    }

    public void updateSapCustomerBuyingInfo( CustomerBuyingInfo customerBuyingInfo ) {
        
        this.sqlInsert( NAMESPACE + "updateSapCustomerBuyingInfo", customerBuyingInfo );
        
    }

    public void deleteSapCustomer() {
        this.sqlDelete( NAMESPACE + "deleteSapCustomer");
        
    }

    public void deleteSapCustomerBuyingInfo() {
        this.sqlDelete( NAMESPACE + "deleteSapCustomerBuyingInfo" );
        
    }

    public List listSAPBasicInfobySearchCondition( BasicInfoSearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listSAPBasicInfobySearchCondition", searchCondition );
    }

    public Integer countBySearchConditionForSAP( BasicInfoSearchCondition searchCondition ) {
      
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchConditionForSAP", searchCondition );
    }

    public BasicInfo readBasicInfoForSap( String sapId ) {
         
        return (BasicInfo)this.sqlSelectForObject( NAMESPACE + "readBasicInfoForSap", sapId );
    }

    public void updateBasicInfoSAPSync( String sapId ) {
        this.sqlUpdate( NAMESPACE + "basicInfoSAPSync", sapId );
        
    }

    public void updateManInfoSAPSync( String sapId ) {
        this.sqlUpdate( NAMESPACE + "manInfoSAPSync", sapId );
        
    }

    public void updateCounselHistorySAPSync( String sapId ) {
        this.sqlUpdate( NAMESPACE + "mainSellingSAPSync", sapId );
        
    }

    public void updateMainSellingSAPSync( String sapId ) {
        this.sqlUpdate( NAMESPACE + "counselHistorySAPSync", sapId );
        
    }
    
    
    

}
