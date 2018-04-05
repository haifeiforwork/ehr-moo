package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerQualityClaimSellDao;
import com.lgcns.ikep4.support.customer.model.QualityClaimSellHistory;
import com.lgcns.ikep4.support.customer.search.CustomerQualityClaimSellSearchCondition;



@Repository
public class CustomerQualityClaimSellDaoImpl extends GenericDaoSqlmap<QualityClaimSellHistory, String> implements CustomerQualityClaimSellDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.qualityClaimSell.";
    
    public Integer countBySearchCondition(CustomerQualityClaimSellSearchCondition searchCondition){
        return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
    }
    
    public List<QualityClaimSellHistory> listQualityClaimSellBySearchCondition(CustomerQualityClaimSellSearchCondition customerQualityCliamSearchCondition) {
        return this.sqlSelectForList(NAMESPACE + "qualityClaimSellListBySearchCondition",customerQualityCliamSearchCondition);
    }
    
    public QualityClaimSellHistory getQualityClaimSell (int itemId){
        return (QualityClaimSellHistory)this.sqlSelectForObject( NAMESPACE + "getQualityClaimSell" ,itemId);
    }
    
    public void updateLinereplyCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
    }
    
    public void updateQualityClaimSellMoreCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateQualityClaimSellMoreCount" , itemId);
    }
    
    public void updateQualityClaimSellMoreInfo( QualityClaimSellHistory qualityClaimSellItem ){
        this.sqlUpdate( NAMESPACE +"updateQualityClaimSellMoreInfo" , qualityClaimSellItem);
    }
    
    public int createQualityClaimSell( QualityClaimSellHistory qualityClaimSellItem ) {
   
       Integer a = (Integer)this.sqlInsert( NAMESPACE + "createQualityClaimSell", qualityClaimSellItem );
       return a;
    }
    
    

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public QualityClaimSellHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( QualityClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "updateQualityClaimSell", qualityClaimSell );
        
    }
    
    public void updateMaster( QualityClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "updateQualityClaimSellMaster", qualityClaimSell );
        
    }

    public String create( QualityClaimSellHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteQualityClaimSell( QualityClaimSellHistory qualityClaimSell ) {
        this.sqlUpdate( NAMESPACE + "deleteQualityClaimSell" , qualityClaimSell );
        
    }

  
    
    
    
    
}
