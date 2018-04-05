package com.lgcns.ikep4.support.partner.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.partner.dao.PartnerBasicInfoDao;
import com.lgcns.ikep4.support.partner.model.Partner;


@Repository
public class PartnerBasicInfoDaoImpl extends GenericDaoSqlmap<Object, String> implements PartnerBasicInfoDao{

    private static final String NAMESPACE = "support.partner.dao.basicInfo.";
    
    public boolean existsReference(Partner basicInfo) {
		Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "existsReference", basicInfo);
		return count > 0;
	}
    
    public void updateHitCount( Partner basicInfo ) {
        this.sqlUpdate( NAMESPACE + "updateHitCount", basicInfo );
    }
    
    public void updateReference( Partner basicInfo ) {
        this.sqlUpdate( NAMESPACE + "updateReference", basicInfo );
    }
    
    public void createReference( Partner basicInfo ) {
        this.sqlInsert( NAMESPACE + "createReference", basicInfo );
    }

	public String create(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Object get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Object arg0) {
		// TODO Auto-generated method stub
		
	}


}
