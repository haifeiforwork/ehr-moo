package com.lgcns.ikep4.portal.moorimess.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.StatementDetail;

/**
 * 
 * 고객정보 채권 관련 이슈 상담이력 서비스 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Transactional
public interface StatementService {

    StatementDetail createMandator(User user, String portalid, String trusteeId, String gubun);
    
    /** SAPID로 사용자정보 조회*/
    public User selectMandatorInfo(StatementDetail mandator);
    
    /** 사용자가 임원/팀장 인지 여부 조회 */
    public boolean isMaster(User user);
    
}
