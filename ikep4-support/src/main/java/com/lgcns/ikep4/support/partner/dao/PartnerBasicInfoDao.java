package com.lgcns.ikep4.support.partner.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.partner.model.Partner;

public interface PartnerBasicInfoDao extends GenericDao<Object,String> {

    
    boolean existsReference(Partner basicInfo);
    
    void updateHitCount( Partner basicInfo );
    
    void updateReference( Partner basicInfo );
    
    void createReference( Partner basicInfo );

    
    
    
}
