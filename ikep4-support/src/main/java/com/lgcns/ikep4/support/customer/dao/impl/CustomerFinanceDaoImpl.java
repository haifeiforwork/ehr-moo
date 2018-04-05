package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerFinanceDao;
import com.lgcns.ikep4.support.customer.model.FinanceCondition;
import com.lgcns.ikep4.support.customer.model.Finance;
import com.lgcns.ikep4.support.customer.model.BuyingInfo;
import com.lgcns.ikep4.support.customer.model.MainBusiness;
import com.lgcns.ikep4.support.customer.model.MainPerson;
import com.lgcns.ikep4.support.customer.model.PersonStatic;
import com.lgcns.ikep4.support.customer.model.RelationCompany;
import com.lgcns.ikep4.support.customer.search.FinanceSearchCondition;
import com.lgcns.ikep4.util.model.CustomerBasicInfo;
import com.lgcns.ikep4.util.model.CustomerBuyingInfo;


@Repository
public class CustomerFinanceDaoImpl extends GenericDaoSqlmap<Object, String> implements CustomerFinanceDao{

    private static final String NAMESPACE = "support.customer.dao.finance.";
    
    public String create( Finance arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public Finance get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( Finance arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public Integer countBySearchCondition( FinanceSearchCondition searchCondition ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List listFinanceBySearchCondition( FinanceSearchCondition searchCondition ) {
        
        return this.sqlSelectForList( NAMESPACE + "financeListBySearchCondition", searchCondition );
    }

    public Finance readFinance( String id ) {
        
        return (Finance)this.sqlSelectForObject( NAMESPACE + "getFinance", id );
    }


    public List readHistory( String id ) {
       
        return this.sqlSelectForList( NAMESPACE + "getHistory", id );
    }

    public List profitLossList( String id ) {
        
        return this.sqlSelectForList( NAMESPACE + "profitLossList", id );
    }
    
    public List conditionList( String id ) {
        
        return this.sqlSelectForList( NAMESPACE + "conditionList", id );
    }

	public List rateList( String id ) {
	    
	    return this.sqlSelectForList( NAMESPACE + "rateList", id );
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

    public void saveFinance( Finance finance ) {
        
        this.sqlUpdate( NAMESPACE + "saveFinance", finance );
    }
    
    public void updateFinanceCondition( FinanceCondition financeCondition ) {
        
        this.sqlUpdate( NAMESPACE + "updateFinanceCondition", financeCondition );
    }

	public void updateFinanceRate( Finance finance ) {
	    
	    this.sqlUpdate( NAMESPACE + "updateFinanceRate", finance );
	}
    

    public void saveFinanceCondition( FinanceCondition financeCondition ) {
       this.sqlInsert( NAMESPACE + "saveFinanceCondition", financeCondition );
        
    }

    public void deleteFinanceCondition( String id ) {
       this.sqlDelete( NAMESPACE + "deleteFinanceCondition", id );
        
    }
    
    public void deleteFinanceRate( String id ) {
        this.sqlDelete( NAMESPACE + "deleteFinanceRate", id );
         
     }

    public void saveFinanceRate( Finance finance ) {
        this.sqlInsert( NAMESPACE + "saveFinanceRate", finance );
        
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

    public void createFinance( Finance finance ) {
        this.sqlInsert( NAMESPACE + "createFinance", finance );
        
    }

    public void deleteFinance( String id ) {
        this.sqlUpdate( NAMESPACE + "deleteFinance", id );
        
    }

    public List getBuyingInfo( String sapId ) {
       return this.sqlSelectForList( NAMESPACE + "getBuyingInfo", sapId );
    }
    
    

    public void updateSapCustomer( CustomerBasicInfo customerFinance ) {
        this.sqlInsert( NAMESPACE + "updateSapCustomer", customerFinance );
        
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

    public List listSAPFinancebySearchCondition( FinanceSearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listSAPFinancebySearchCondition", searchCondition );
    }

    public Integer countBySearchConditionForSAP( FinanceSearchCondition searchCondition ) {
      
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchConditionForSAP", searchCondition );
    }
    
    public Integer countFinance( Finance finance ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countFinance", finance );
    }
    
    public Integer countFinanceCondition( FinanceCondition financeCondition ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countFinanceCondition", financeCondition );
    }

	public Integer countFinanceRate( Finance finance ) {
	    
	    return (Integer)this.sqlSelectForObject( NAMESPACE + "countFinanceRate", finance );
	}

    public Finance readFinanceForSap( String sapId ) {
         
        return (Finance)this.sqlSelectForObject( NAMESPACE + "readFinanceForSap", sapId );
    }

    public void updateFinanceSAPSync( String sapId ) {
        this.sqlUpdate( NAMESPACE + "financeSAPSync", sapId );
        
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
    
    public String yearInfo(String code){
    	return (String)this.sqlSelectForObject( NAMESPACE + "selectYearInfo", code );
    }
    
    public void saveFinanceYearInfo(Finance finance){
    	this.sqlUpdate( NAMESPACE + "updateYearInfo1", finance );
    	this.sqlUpdate( NAMESPACE + "updateYearInfo2", finance );
    }
    

}
