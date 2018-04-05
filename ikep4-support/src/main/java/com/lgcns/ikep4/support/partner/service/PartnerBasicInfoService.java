package com.lgcns.ikep4.support.partner.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.partner.model.Partner;



/**
 * 
 * 고객 기본정보 서비스 
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface PartnerBasicInfoService extends GenericService<Object, String> {
    
    
    void updateHitCount( Partner basicInfo );

    
    
 
    
}
