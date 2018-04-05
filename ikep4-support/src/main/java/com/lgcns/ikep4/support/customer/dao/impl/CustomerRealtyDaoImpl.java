package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerRealtyDao;
import com.lgcns.ikep4.support.customer.model.Realty;
import com.lgcns.ikep4.support.customer.search.RealtySearchCondition;


@Repository
public class CustomerRealtyDaoImpl extends GenericDaoSqlmap<Realty, String> implements CustomerRealtyDao{

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.realty.";
    
    
    
    
    public String create( Realty arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public Realty get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( Realty realty ) {
        
        this.sqlUpdate( NAMESPACE + "updateRealty", realty );
        
    }

    public Integer countBySearchCondition( RealtySearchCondition searchCondition ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE  + "countBySearchCondition", searchCondition);
    }

    public List<Realty> listRealtyBySearchCondition( RealtySearchCondition searchCondition ) {
        
        return this.sqlSelectForList( NAMESPACE + "realtyListBySearchCondition", searchCondition );
    }

    public Realty getRealty( int itemId ) {
        
        return (Realty)this.sqlSelectForObject( NAMESPACE +"getRealty" , itemId);
    }

    public int createRealty( Realty realty ) {
        Integer a = (Integer)this.sqlInsert( NAMESPACE +"createRealty" , realty);
        return a;
    }

    public void deleteRealty( Realty realty ) {
       this.sqlDelete( NAMESPACE + "deleteRealty", realty );
        
    }

    public void updateLinereplyCount( String itemId ) {
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
        
    }

    public void syncCustomerName( Realty realty ) {
        this.sqlUpdate( NAMESPACE + "syncCustomerName", realty );
        
    }
    
    public List<Realty> selectRealty(){
    	String tmp = "";
    	return (List<Realty>) sqlSelectForList(NAMESPACE + "selectRealty", tmp);
    }

  
}
