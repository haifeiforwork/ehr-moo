package com.lgcns.ikep4.support.partner.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.partner.dao.PartnerQualityClaimSellDao;
import com.lgcns.ikep4.support.partner.model.PartnerClaimSellHistory;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellSearchCondition;



@Repository
public class PartnerQualityClaimSellDaoImpl extends GenericDaoSqlmap<PartnerClaimSellHistory, String> implements PartnerQualityClaimSellDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.partner.dao.partnerClaimSell.";
    
    public Integer countBySearchCondition(PartnerQualityClaimSellSearchCondition searchCondition){
        return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
    }
    
    public List<PartnerClaimSellHistory> listQualityClaimSellBySearchCondition(PartnerQualityClaimSellSearchCondition partnerQualityCliamSearchCondition) {
        return this.sqlSelectForList(NAMESPACE + "qualityClaimSellListBySearchCondition",partnerQualityCliamSearchCondition);
    }
    
    public List<PartnerClaimSellHistory> crStatisticsList(PartnerQualityClaimSellSearchCondition searchCondition){
    	return this.sqlSelectForList(NAMESPACE + "crStatisticsList",searchCondition);
    }
    
    public PartnerClaimSellHistory getQualityClaimSell (int itemId){
        return (PartnerClaimSellHistory)this.sqlSelectForObject( NAMESPACE + "getQualityClaimSell" ,itemId);
    }
    
    public PartnerClaimSellHistory getQualityClaimSellOther (int itemId){
        return (PartnerClaimSellHistory)this.sqlSelectForObject( NAMESPACE + "getQualityClaimSellOther" ,itemId);
    }
    
    public void updateLinereplyCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
    }
    
    public void updateQualityClaimSellMoreCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateQualityClaimSellMoreCount" , itemId);
    }
    
    public void updateQualityClaimSellMoreInfo( PartnerClaimSellHistory qualityClaimSellItem ){
        this.sqlUpdate( NAMESPACE +"updateQualityClaimSellMoreInfo" , qualityClaimSellItem);
    }
    
    public int createQualityClaimSell( PartnerClaimSellHistory qualityClaimSellItem ) {
   
       Integer a = (Integer)this.sqlInsert( NAMESPACE + "createQualityClaimSell", qualityClaimSellItem );
       return a;
    }
    
    

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public PartnerClaimSellHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( PartnerClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "updateQualityClaimSell", qualityClaimSell );
        
    }
    
    public void updateMaster( PartnerClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "updateQualityClaimSellMaster", qualityClaimSell );
        
    }

    public String create( PartnerClaimSellHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteQualityClaimSell( PartnerClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "deleteQualityClaimSell" , qualityClaimSell );
        
    }

  
    
    
    
    
}
