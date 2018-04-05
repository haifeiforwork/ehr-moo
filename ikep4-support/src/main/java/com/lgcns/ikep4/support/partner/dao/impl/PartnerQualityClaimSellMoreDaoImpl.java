
package com.lgcns.ikep4.support.partner.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.partner.dao.PartnerQualityClaimSellMoreDao;
import com.lgcns.ikep4.support.partner.model.PartnerQualitySub;
import com.lgcns.ikep4.support.partner.search.PartnerQualityClaimSellMoreSearchCondition;
import com.lgcns.ikep4.support.user.member.model.User;

@Repository
public class PartnerQualityClaimSellMoreDaoImpl extends GenericDaoSqlmap<PartnerQualitySub, String> implements
        PartnerQualityClaimSellMoreDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "support.partner.dao.partnerQualityClaimSellMore.";

    public Integer countBySearchCondition( PartnerQualityClaimSellMoreSearchCondition searchCondition ) {
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countBySearchCondition", searchCondition );
    }

    public List<PartnerQualitySub> listBySearchCondition( PartnerQualityClaimSellMoreSearchCondition searchCondition ) {
        return this.sqlSelectForList( NAMESPACE + "listBySearchCondition", searchCondition );
    }

    public void updateStep( PartnerQualitySub partnerQualitySub ){
        this.sqlInsert( NAMESPACE + "updateStep", partnerQualitySub );
        
    }
    
    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public void remove( String arg0 ) {
        // TODO Auto-generated method stub

    }

    public void update( PartnerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "update", object );

    }
    
    public void updateTlComment( PartnerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "updateTlComment", object );
    }
    
    public void updateOfComment( PartnerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "updateOfComment", object );
    }
    
    public void updateSubMaster( PartnerQualitySub object ) {
        this.sqlInsert( NAMESPACE + "updateSubMaster", object );

    }

    public String create( PartnerQualitySub partnerQualitySub ) {
        this.sqlInsert( NAMESPACE + "create", partnerQualitySub );
        return partnerQualitySub.getQualityClaimSellMoreId();
    }

    public PartnerQualitySub get( String id ) {
        
        return (PartnerQualitySub)this.sqlSelectForObject( NAMESPACE +"get" , id);
    }

    public Integer countChildren( String linereplyId ) {
        
        return (Integer)this.sqlSelectForObject( NAMESPACE + "countChildren",linereplyId);
    }

    public void logicalDelete( PartnerQualitySub partnerQualitySub ) {
       this.sqlUpdate( NAMESPACE + "logicalDelete" ,partnerQualitySub );
        
    }

    public void physicalDelete( String linereplyId ) {
      this.sqlUpdate( NAMESPACE + "physicalDelete" , linereplyId );
        
    }

    public void physicalDeleteThreadByQualityClaimSellMoreId( String linereplyId ) {
        this.sqlDelete( NAMESPACE + "physicalDeleteThreadByQualityClaimSellMoreId", linereplyId );
        
    }

    public List listHistorysMemo() {
        return this.sqlSelectForListOfObject( NAMESPACE  + "listHistorysMemo"); 
    }
    
    @SuppressWarnings("unchecked")
	public List<User> getUserInfoList(String userId) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "getUserInfoList", userId);
	}
    
    @SuppressWarnings("unchecked")
	public List<User> getUserInfoList2(String id) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "getUserInfoList2", id);
	}
    
    @SuppressWarnings("unchecked")
	public List<User> getUserInfoList3(String id) {
		return getSqlMapClientTemplate().queryForList(NAMESPACE + "getUserInfoList3", id);
	}

}
