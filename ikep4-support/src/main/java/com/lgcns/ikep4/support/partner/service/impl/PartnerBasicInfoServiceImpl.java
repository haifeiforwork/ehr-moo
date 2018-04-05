package com.lgcns.ikep4.support.partner.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.partner.dao.PartnerBasicInfoDao;
import com.lgcns.ikep4.support.partner.model.Partner;
import com.lgcns.ikep4.support.partner.service.PartnerBasicInfoService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;



@Service
@Transactional
public class PartnerBasicInfoServiceImpl extends GenericServiceImpl<Object, String> implements PartnerBasicInfoService {

    @Autowired
    private PartnerBasicInfoDao partnerBasicInfoDao;
    
    @Autowired
    private IdgenService idgenService;
    
    
    public void updateHitCount( Partner basicInfo ) {
        if(partnerBasicInfoDao.existsReference(basicInfo)) {
			partnerBasicInfoDao.updateReference(basicInfo);
		} else {
			partnerBasicInfoDao.updateHitCount(basicInfo);
			partnerBasicInfoDao.createReference(basicInfo);
		}
    }





    

    
}
