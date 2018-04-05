package com.lgcns.ikep4.portal.moorimess.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.moorimess.dao.OldRecordDao;
import com.lgcns.ikep4.portal.moorimess.model.OldRecordHistory;
import com.lgcns.ikep4.portal.moorimess.search.OldRecordSearchCondition;



@Repository
public class OldRecordDaoImpl extends GenericDaoSqlmap<OldRecordHistory, String> implements OldRecordDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "portal.moorimess.dao.oldRecord.";
    
    public Integer countBySearchCondition(OldRecordSearchCondition searchCondition){
        return (Integer)this.sqlSelectForObject(NAMESPACE + "countBySearchCondition", searchCondition);
    }
    
    public List<OldRecordHistory> listOldRecordBySearchCondition(OldRecordSearchCondition oldRecordSearchCondition) {
        return this.sqlSelectForList(NAMESPACE + "oldRecordListBySearchCondition",oldRecordSearchCondition);
    }
    
    public OldRecordHistory getOldRecord (int itemId){
        return (OldRecordHistory)this.sqlSelectForObject( NAMESPACE + "getOldRecord" ,itemId);
    }
    
    public void updateLinereplyCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateLinereplyCount" , itemId);
    }
    public void updateOldRecordSubCount( String itemId ){
        this.sqlUpdate( NAMESPACE +"updateOldRecordSubCount" , itemId);
    }
    
    public int createOldRecord( OldRecordHistory oldRecordItem ) {
   
       Integer a = (Integer)this.sqlInsert( NAMESPACE + "createOldRecord", oldRecordItem );
       return a;
    }
    
    

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public OldRecordHistory get( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public void update( OldRecordHistory oldRecord ) {
        this.sqlUpdate( NAMESPACE + "updateOldRecord", oldRecord );
        
    }
    
    public void updateMaster( OldRecordHistory oldRecord ) {
        this.sqlUpdate( NAMESPACE + "updateOldRecordMaster", oldRecord );
        
    }

    public String create( OldRecordHistory arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void deleteOldRecord( OldRecordHistory oldRecord ) {
        this.sqlUpdate( NAMESPACE + "deleteOldRecord" , oldRecord );
        
    }

    public void updateOldRecordSubInfo(OldRecordHistory oldRecord){
    	this.sqlUpdate( NAMESPACE + "updateOldRecordSubInfo" , oldRecord );
    }
  
    
    
    
    
}
