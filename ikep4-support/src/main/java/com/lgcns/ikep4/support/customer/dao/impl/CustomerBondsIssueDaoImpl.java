package com.lgcns.ikep4.support.customer.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.customer.dao.CustomerBondsIssueDao;
import com.lgcns.ikep4.support.customer.model.BondsIssueHistory;
import com.lgcns.ikep4.support.customer.search.CustomerBondsIssueSearchCondition;



@Repository
public class CustomerBondsIssueDaoImpl extends GenericDaoSqlmap<BondsIssueHistory, String> implements CustomerBondsIssueDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.customer.dao.bondsIssue.";
    
    public Integer countBySearchCondition(CustomerBondsIssueSearchCondition searchCondition){
        return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
    }
    
    public List<BondsIssueHistory> listBondsIssueBySearchCondition(CustomerBondsIssueSearchCondition customerQualityCliamSearchCondition) {
        return this.sqlSelectForList(NAMESPACE + "bondsIssueListBySearchCondition",customerQualityCliamSearchCondition);
    }
    
    public BondsIssueHistory getBondsIssue (int itemId){
        return (BondsIssueHistory)this.sqlSelectForObject( NAMESPACE + "getBondsIssue" ,itemId);
    }
    
    public void updateLinereplyCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
    }
    public void updateBondsIssueSubCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateBondsIssueSubCount" , itemId);
    }
    
    public int createBondsIssue( BondsIssueHistory bondsIssueItem ) {
   
       Integer a = (Integer)this.sqlInsert( NAMESPACE + "createBondsIssue", bondsIssueItem );
       return a;
    }
    
    

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public BondsIssueHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( BondsIssueHistory bondsIssue ) {
        this.sqlUpdate( NAMESPACE + "updateBondsIssue", bondsIssue );
        
    }
    
    public void updateMaster( BondsIssueHistory bondsIssue ) {
        this.sqlUpdate( NAMESPACE + "updateBondsIssueMaster", bondsIssue );
        
    }

    public String create( BondsIssueHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteBondsIssue( BondsIssueHistory bondsIssue ) {
        this.sqlUpdate( NAMESPACE + "deleteBondsIssue" , bondsIssue );
        
    }

    public void updateBondsIssueSubInfo(BondsIssueHistory bondsIssue){
    	this.sqlUpdate( NAMESPACE + "updateBondsIssueSubInfo" , bondsIssue );
    }
  
    
    
    
    
}
