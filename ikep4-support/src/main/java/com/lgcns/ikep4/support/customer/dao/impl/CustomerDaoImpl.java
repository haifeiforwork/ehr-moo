package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerDao;
import com.lgcns.ikep4.support.customer.model.Customer;
import com.lgcns.ikep4.support.customer.model.ManCareer;
import com.lgcns.ikep4.support.customer.model.ManFamily;
import com.lgcns.ikep4.support.customer.model.ManInfoItem;
import com.lgcns.ikep4.support.customer.search.ManInfoItemSearchCondition;

/**
 * 
 * CustomerDao 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Repository
public class CustomerDaoImpl extends GenericDaoSqlmap<ManInfoItem, String> implements CustomerDao{

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.customer.dao.customer.";
	


	public List<ManInfoItem> listManInfoItemBySearchCondition(ManInfoItemSearchCondition manInfoItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "manInfoItemBySearchCondition",manInfoItemSearchCondition);
	}
	
	public Integer countBySearchCondition(ManInfoItemSearchCondition searchCondition){
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	public ManInfoItem readManInfoItem(int itemId){
		return (ManInfoItem)this.sqlSelectForObject(NAMESPACE+"getManInfo", itemId);
	}
	
    public List<ManInfoItem> listManFamily(String itemId) {
        return this.sqlSelectForList(NAMESPACE + "getManFamily",itemId);
    }
    
    public List<ManInfoItem> listManCareer(String itemId){
        return this.sqlSelectForList(NAMESPACE + "getManCareer",itemId);
    }
    
    public void updateManInfo(ManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "updateManInfo", manInfoItem );
    }
    
    public void updateManInfoFamily(ManFamily manFamily){
        this.sqlUpdate( NAMESPACE + "updateManInfoFamily", manFamily );
    }
	

    public void deleteManInfoFamily( String itemId ){
        this.sqlDelete( NAMESPACE + "deleteManInfoFamily", itemId );
    }
    
    public void updateManInfoCareer(ManCareer manCareer){
        this.sqlUpdate( NAMESPACE + "updateManInfoCareer", manCareer );
    }
    
    public void deleteManInfoCareer( String itemId ){
        this.sqlDelete( NAMESPACE + "deleteManInfoCareer", itemId );
    }
    
    public void createManInfoItem( ManInfoItem manInfoItem){
        this.sqlInsert( NAMESPACE + "createManInfo", manInfoItem );
    }
    
    public void createManInfoFamily(ManFamily manFamily){
        this.sqlInsert( NAMESPACE + "createManFamily", manFamily );
    }
    
    public void createManInfoCareer( ManCareer manCareer ){
        this.sqlInsert( NAMESPACE + "createManCareer", manCareer );
    }
    
    public int getSeqNum(ManInfoItem manInfoItem){
        return (Integer)this.sqlSelectForObject( NAMESPACE + "getSeqNum", manInfoItem );
    }
    
    public void deleteManInfo(ManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "deleteManInfo", manInfoItem  );
        
    }
    
    public String getUserName(String id){
        return (String)this.sqlSelectForObject( NAMESPACE + "getUserName", id );
    }

    public String create( ManInfoItem arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public ManInfoItem get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( ManInfoItem arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void syncCustomerName( ManInfoItem manInfoItem ) {
        this.sqlUpdate( NAMESPACE + "syncCustomerName", manInfoItem );
        
    }

    public Integer checkAccess( String userId ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE + "checkAccess", userId );
    }
    
    
	
}
