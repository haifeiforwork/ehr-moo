
package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerLinereplyDao;
import com.lgcns.ikep4.support.customer.model.CustomerLinereply;
import com.lgcns.ikep4.support.customer.search.CustomerLinereplySearchCondition;

@Repository
public class CustomerLinereplyDaoImpl extends GenericDaoSqlmap<CustomerLinereply, String> implements
        CustomerLinereplyDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.customerLinereply.";

    public Integer countBySearchCondition( CustomerLinereplySearchCondition searchCondition ) {
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List<CustomerLinereply> listBySearchCondition( CustomerLinereplySearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listBySearchCondition", searchCondition );
    }

    public void updateStep( CustomerLinereply customerLinereply ){
        this.sqlInsert( NAMESPACE + "updateStep", customerLinereply );
        
    }
    
    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub

    }

    public void update( CustomerLinereply object ) {
        this.sqlInsert( NAMESPACE + "update", object );

    }

    public String create( CustomerLinereply customerLinereply ) {
        this.sqlInsert( NAMESPACE + "create", customerLinereply );
        return customerLinereply.getLinereplyId();
    }

    public CustomerLinereply get( String id ) {
        
        return (CustomerLinereply)this.sqlSelectForObject( NAMESPACE +"get" , id);
    }

    public Integer countChildren( String linereplyId ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countChildren",linereplyId);
    }

    public void logicalDelete( CustomerLinereply customerLinereply ) {
       this.sqlUpdate( NAMESPACE + "logicalDelete" ,customerLinereply );
        
    }

    public void physicalDelete( String linereplyId ) {
      this.sqlUpdate( NAMESPACE + "physicalDelete" , linereplyId );
        
    }

    public void physicalDeleteThreadByLinereplyId( String linereplyId ) {
        this.sqlDelete( NAMESPACE + "physicalDeleteThreadByLinereplyId", linereplyId );
        
    }

    public List listHistorysMemo() {
        return this.sqlSelectForListOfObject( NAMESPACE  + "listHistorysMemo"); 
    }

}
