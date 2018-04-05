package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerMainSellingDao;
import com.lgcns.ikep4.support.customer.model.MainSelling;
import com.lgcns.ikep4.support.customer.search.MainSellingSearchCondition;


@Repository
public class CustomerMainSellingDaoImpl extends GenericDaoSqlmap<MainSelling, String> implements CustomerMainSellingDao{

    
    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.mainSelling.";
    
    
    
    public String create( MainSelling arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public MainSelling get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( MainSelling arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public Integer countBySearchCondition( MainSellingSearchCondition searchCondition ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition" ,searchCondition);
    }

    public List<MainSelling> listMainSellingBySearchCondition( MainSellingSearchCondition searchCondition ) {
        
        return this.sqlSelectForList( NAMESPACE +"mainSellingListBySearchCondition", searchCondition );
    }

    public List<MainSelling> listMainSellingDetail( String customerId ) {
        return this.sqlSelectForList( NAMESPACE + "getMainSellingList", customerId ); 
        
    }

    public int getSellingId( String customerId ) {
       return (Integer)this.sqlSelectForObject( NAMESPACE + "getSellingId", customerId );
    }

    public void createMainSelling( MainSelling mainSelling ) {
       this.sqlInsert( NAMESPACE + "createMainSelling", mainSelling );
    }

    public void updateRegistInfo( MainSelling mainSelling ) {
        this.sqlUpdate( NAMESPACE + "updateRegistInfo", mainSelling );
        
    }

    public void modifyMainSelling( MainSelling mainSelling ) {
        this.sqlUpdate( NAMESPACE + "modifyMainSelling", mainSelling );
        
    }

    public MainSelling getMainSellingItem( int seqNum ) {
        
        return (MainSelling)this.sqlSelectForObject( NAMESPACE + "getMainSellingItem", seqNum );
    }

    public void deleteMainSelling( MainSelling mainSelling ) {
        this.sqlUpdate( NAMESPACE + "deleteMainSelling", mainSelling );
        
    }

    public int checkMainSelling( String customerId ) {
        
        return (Integer)sqlSelectForObject( NAMESPACE + "checkMainSelling", customerId );
    }

    public void syncCustomerName( MainSelling mainSelling ) {
        this.sqlUpdate( NAMESPACE + "syncCustomerName", mainSelling );
        
    }
    
    

}
