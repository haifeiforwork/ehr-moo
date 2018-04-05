package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerCounselHistoryDao;
import com.lgcns.ikep4.support.customer.model.CounselHistory;
import com.lgcns.ikep4.support.customer.model.Customer;
import com.lgcns.ikep4.support.customer.search.CounselHistorySearchCondition;


@Repository
public class CustomerCounselHistoryDaoImpl extends GenericDaoSqlmap<CounselHistory, String> implements CustomerCounselHistoryDao{

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.counselHistory.";
    
    
    
    
    public String create( CounselHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public CounselHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( CounselHistory counselHistory ) {
        
        this.sqlUpdate( NAMESPACE + "updateCounselHistory", counselHistory );
        
    }

    public Integer countBySearchCondition( CounselHistorySearchCondition searchCondition ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE  + "countBySearchCondition", searchCondition);
    }

    public List<CounselHistory> listCounselHistoryBySearchCondition( CounselHistorySearchCondition searchCondition ) {
        
        return this.sqlSelectForList( NAMESPACE + "counselHistoryListBySearchCondition", searchCondition );
    }

    public CounselHistory getCounselHistory( int itemId ) {
        
        return (CounselHistory)this.sqlSelectForObject( NAMESPACE +"getCounselHistory" , itemId);
    }

    public int createCounselHistory( CounselHistory counselHistory ) {
        Integer a = (Integer)this.sqlInsert( NAMESPACE +"createCounselHistory" , counselHistory);
        return a;
    }

    public void deleteCounselHistory( CounselHistory counselHistory ) {
       this.sqlUpdate( NAMESPACE + "deleteCounselHistory", counselHistory );
        
    }

    public void updateLinereplyCount( String itemId ) {
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
        
    }

    public void syncCustomerName( CounselHistory counselHistory ) {
        this.sqlUpdate( NAMESPACE + "syncCustomerName", counselHistory );
        
    }

  
}
