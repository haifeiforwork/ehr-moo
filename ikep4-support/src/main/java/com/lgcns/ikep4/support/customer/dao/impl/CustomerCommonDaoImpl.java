package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerCommonDao;
import com.lgcns.ikep4.support.customer.model.Customer;

@Repository
public class CustomerCommonDaoImpl extends GenericDaoSqlmap<Customer, String> implements CustomerCommonDao {

    public String create( Customer arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public Customer get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( Customer arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public List<Customer> getCustomerId( String customerName ) {
        return this.sqlSelectForList( "support.customer.dao.customer.getCustomerId", customerName );
    }

    
    
}
