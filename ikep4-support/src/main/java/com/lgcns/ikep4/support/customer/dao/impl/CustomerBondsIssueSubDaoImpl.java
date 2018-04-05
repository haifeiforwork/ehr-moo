
package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueSubDao;
import com.lgcns.ikep4.support.customer.model.CustomerBondsIssueSub;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSubSearchCondition;

@Repository
public class CustomerBondsIssueSubDaoImpl extends GenericDaoSqlmap<CustomerBondsIssueSub, String> implements
        CustomerBondsIssueSubDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.customerBondsIssueSub.";

    public Integer countBySearchCondition( CustomerBondsIssueSubSearchCondition searchCondition ) {
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List<CustomerBondsIssueSub> listBySearchCondition( CustomerBondsIssueSubSearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listBySearchCondition", searchCondition );
    }

    public void updateStep( CustomerBondsIssueSub customerBondsIssueSub ){
        this.sqlInsert( NAMESPACE + "updateStep", customerBondsIssueSub );
        
    }
    
    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub

    }

    public void update( CustomerBondsIssueSub object ) {
        this.sqlInsert( NAMESPACE + "update", object );
    }
    
    public void updateSubMaster( CustomerBondsIssueSub object ) {
        this.sqlInsert( NAMESPACE + "updateSubMaster", object );
    }

    public String create( CustomerBondsIssueSub customerBondsIssueSub ) {
        this.sqlInsert( NAMESPACE + "create", customerBondsIssueSub );
        return customerBondsIssueSub.getBondsIssueSubId();
    }

    public CustomerBondsIssueSub get( String id ) {
        
        return (CustomerBondsIssueSub)this.sqlSelectForObject( NAMESPACE +"get" , id);
    }

    public Integer countChildren( String linereplyId ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countChildren",linereplyId);
    }

    public void logicalDelete( CustomerBondsIssueSub customerBondsIssueSub ) {
       this.sqlUpdate( NAMESPACE + "logicalDelete" ,customerBondsIssueSub );
        
    }

    public void physicalDelete( String linereplyId ) {
      this.sqlUpdate( NAMESPACE + "physicalDelete" , linereplyId );
        
    }

    public void physicalDeleteThreadByBondsIssueSubId( String linereplyId ) {
        this.sqlDelete( NAMESPACE + "physicalDeleteThreadByBondsIssueSubId", linereplyId );
        
    }

    public List listHistorysMemo() {
        return this.sqlSelectForListOfObject( NAMESPACE  + "listHistorysMemo"); 
    }

}
