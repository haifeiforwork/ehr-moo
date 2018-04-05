
package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellMoreDao;
import com.lgcns.ikep4.support.customer.model.CustomerQualitySub;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellMoreSearchCondition;

@Repository
public class CustomerQualityClaimSellMoreDaoImpl extends GenericDaoSqlmap<CustomerQualitySub, String> implements
        CustomerQualityClaimSellMoreDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.customerQualityClaimSellMore.";

    public Integer countBySearchCondition( CustomerQualityClaimSellMoreSearchCondition searchCondition ) {
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List<CustomerQualitySub> listBySearchCondition( CustomerQualityClaimSellMoreSearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listBySearchCondition", searchCondition );
    }

    public void updateStep( CustomerQualitySub customerQualitySub ){
        this.sqlInsert( NAMESPACE + "updateStep", customerQualitySub );
        
    }
    
    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub

    }

    public void update( CustomerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "update", object );

    }
    
    public void updateSubMaster( CustomerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "updateSubMaster", object );

    }

    public String create( CustomerQualitySub customerQualitySub ) {
        this.sqlInsert( NAMESPACE + "create", customerQualitySub );
        return customerQualitySub.getQualityClaimSellMoreId();
    }

    public CustomerQualitySub get( String id ) {
        
        return (CustomerQualitySub)this.sqlSelectForObject( NAMESPACE +"get" , id);
    }

    public Integer countChildren( String linereplyId ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countChildren",linereplyId);
    }

    public void logicalDelete( CustomerQualitySub customerQualitySub ) {
       this.sqlUpdate( NAMESPACE + "logicalDelete" ,customerQualitySub );
        
    }

    public void physicalDelete( String linereplyId ) {
      this.sqlUpdate( NAMESPACE + "physicalDelete" , linereplyId );
        
    }

    public void physicalDeleteThreadByQualityClaimSellMoreId( String linereplyId ) {
        this.sqlDelete( NAMESPACE + "physicalDeleteThreadByQualityClaimSellMoreId", linereplyId );
        
    }

    public List listHistorysMemo() {
        return this.sqlSelectForListOfObject( NAMESPACE  + "listHistorysMemo"); 
    }

}
