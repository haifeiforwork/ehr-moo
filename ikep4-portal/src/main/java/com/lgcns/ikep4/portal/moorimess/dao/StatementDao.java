package com.lgcns.ikep4.portal.moorimess.dao;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.StatementDetail;


/**
 * 
 * 고객정보 품질/클레임 상담이력 DAO
 * 
 * @author SongHeeJung
 * @version $Id$
 */

public interface StatementDao extends GenericDao<StatementDetail, String>{

    /** 위임자 등록 */
    void createMandator(StatementDetail mandator);
    
    /** 위임자 정보 조회 */
    public User selectMandatorInfo(StatementDetail mandator);
    
    /** 권한자가 위임했는지  여부 조회 */
    public boolean selectMandatorCount(StatementDetail mandator);
    
    /** 위임자 삭제 */
    public void deleteMandator(StatementDetail mandator);
    
    /** 위임자가 위임받았는지 여부 조회 */
    public boolean selectMandatorRoleCount(StatementDetail mandator);
    
    /** 위임자가 권한테이블에 권한이 있는지 여부 조회 */
    public boolean selectExistRoleCount(StatementDetail mandator);
 
    /** 위임자 권한 등록 */
    public void createMandatorRole(StatementDetail mandator);
    
    /** 위임자 권한 삭제 */
    public void deleteMandatorRole(StatementDetail mandator);
    
    /** 사용자가 임원/팀장 인지 여부 조회 */
    public boolean isMaster(User user);
    
    /** 기존에 있던 위임자 아이디 조회 */
    public StatementDetail selectMandatorId(StatementDetail mandator);
}
