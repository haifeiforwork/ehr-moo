package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSearchCondition;



@Repository
public class CustomerQualityClaimDaoImpl extends GenericDaoSqlmap<QualityClaimHistory, String> implements CustomerQualityClaimDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.qualityClaim.";
    
    public Integer countBySearchCondition(CustomerQualityClaimSearchCondition searchCondition){
        return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
    }
    
    public List<QualityClaimHistory> listQualityClaimBySearchCondition(CustomerQualityClaimSearchCondition customerQualityCliamSearchCondition) {
        return this.sqlSelectForList(NAMESPACE + "qualityClaimListBySearchCondition",customerQualityCliamSearchCondition);
    }
    
    public QualityClaimHistory getQualityClaim (int itemId){
        return (QualityClaimHistory)this.sqlSelectForObject( NAMESPACE + "getQualityClaim" ,itemId);
    }
    
    public void updateLinereplyCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
    }
    
    
    public int createQualityClaim( QualityClaimHistory qualityClaimItem ) {
   
       Integer a = (Integer)this.sqlInsert( NAMESPACE + "createQualityClaim", qualityClaimItem );
       return a;
    }
    
    

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public QualityClaimHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( QualityClaimHistory qualityClaim ) {
        this.sqlUpdate( NAMESPACE + "updateQualityClaim", qualityClaim );
        
    }

    public String create( QualityClaimHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteQualityClaim( QualityClaimHistory qualityClaim ) {
        this.sqlUpdate( NAMESPACE + "deleteQualityClaim" , qualityClaim );
        
    }

  
    
    
    
    
}
