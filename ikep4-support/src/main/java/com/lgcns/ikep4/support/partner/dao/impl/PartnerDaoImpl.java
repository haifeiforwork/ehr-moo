package com.lgcns.ikep4.support.partner.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.partner.dao.PartnerDao;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.model.PartnerManCareer;
import com.lgcns.ikep4.support.partner.model.PartnerManFamily;
import com.lgcns.ikep4.support.partner.model.PartnerManInfoItem;
import com.lgcns.ikep4.support.partner.search.PartnerManInfoItemSearchCondition;


/**
 * 
 * PartnerDao 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Repository
public class PartnerDaoImpl extends GenericDaoSqlmap<PartnerManInfoItem, String> implements PartnerDao{

	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.partner.dao.partner.";
	

    
    public String getUserName(String id){
        return (String)this.sqlSelectForObject( NAMESPACE + "getUserName", id );
    }


    public Integer checkAccess( String userId ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE + "checkAccess", userId );
    }
    
    
    public Integer checkRegInfo( PartnerClaimSellHistory qualityClaimSell ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "checkRegInfo", qualityClaimSell );
    }
    
    public Partner getPortalDefault() {
		return (Partner) sqlSelectForObject(NAMESPACE + "getPortalDefault");
	}

    public List<PartnerManInfoItem> getPartnerId( String partnerName ) {
        return this.sqlSelectForList( NAMESPACE + "getPartnerId", partnerName );
    }
	
	
	public List<PartnerManInfoItem> listManInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "manInfoItemBySearchCondition",manInfoItemSearchCondition);
	}
	
	public List<PartnerManInfoItem> listRegInfoItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "regInfoItemBySearchCondition",manInfoItemSearchCondition);
	}
	
	public List<PartnerManInfoItem> listItemBySearchCondition(PartnerManInfoItemSearchCondition manInfoItemSearchCondition) {
		return this.sqlSelectForList(NAMESPACE + "itemBySearchCondition",manInfoItemSearchCondition);
	}
	
	public Integer countBySearchCondition(PartnerManInfoItemSearchCondition searchCondition){
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
	}
	
	public Integer countRegBySearchCondition(PartnerManInfoItemSearchCondition searchCondition){
		return (Integer)this.sqlSelectForObject(NAMESPACE + "countRegBySearchCondition", searchCondition);
	}
	
	public PartnerManInfoItem readManInfoItem(int itemId){
		return (PartnerManInfoItem)this.sqlSelectForObject(NAMESPACE+"getManInfo", itemId);
	}
	
    public List<PartnerManInfoItem> listManFamily(String itemId) {
        return this.sqlSelectForList(NAMESPACE + "getManFamily",itemId);
    }
    
    public List<PartnerManInfoItem> listManCareer(String itemId){
        return this.sqlSelectForList(NAMESPACE + "getManCareer",itemId);
    }
    
    public void updateManInfo(PartnerManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "updateManInfo", manInfoItem );
    }
    
    public void updateManInfoMaster(PartnerManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "updateManInfoMaster", manInfoItem );
    }
    
    public void updateManInfoFamily(PartnerManFamily manFamily){
        this.sqlUpdate( NAMESPACE + "updateManInfoFamily", manFamily );
    }
	

    public void deleteManInfoFamily( String itemId ){
        this.sqlDelete( NAMESPACE + "deleteManInfoFamily", itemId );
    }
    
    public void updateManInfoCareer(PartnerManCareer manCareer){
        this.sqlUpdate( NAMESPACE + "updateManInfoCareer", manCareer );
    }
    
    public void deleteManInfoCareer( String itemId ){
        this.sqlDelete( NAMESPACE + "deleteManInfoCareer", itemId );
    }
    
    public void createManInfoItem( PartnerManInfoItem manInfoItem){
        this.sqlInsert( NAMESPACE + "createManInfo", manInfoItem );
    }
    
    public int createManInfoMaster( PartnerManInfoItem manInfoItem){
        Integer a = (Integer)this.sqlInsert( NAMESPACE + "createManInfoMaster", manInfoItem );
        return a;
    }
    
    public void createManInfoFamily(PartnerManFamily manFamily){
        this.sqlInsert( NAMESPACE + "createManFamily", manFamily );
    }
    
    public void createManInfoCareer( PartnerManCareer manCareer ){
        this.sqlInsert( NAMESPACE + "createManCareer", manCareer );
    }
    
    public int getSeqNum(PartnerManInfoItem manInfoItem){
        return (Integer)this.sqlSelectForObject( NAMESPACE + "getSeqNum", manInfoItem );
    }
    
    public void deleteManInfo(PartnerManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "deleteManInfo", manInfoItem  );
        
    }
    
    public void deleteManInfoMaster(PartnerManInfoItem manInfoItem){
        this.sqlUpdate( NAMESPACE + "deleteManInfoMaster", manInfoItem  );
        
    }


    public String create( PartnerManInfoItem arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public PartnerManInfoItem get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( PartnerManInfoItem arg0 ) {
        // TODO Auto-generated method stub
        
    }
    
	
}
