package com.lgcns.ikep4.portal.moorimess.dao.impl;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.portal.moorimess.dao.StatementDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.model.StatementDetail;



@Repository
public class StatementDaoImpl extends GenericDaoSqlmap<StatementDetail, String> implements StatementDao {

    /** The Constant NAMESPACE. */
    private static final String NAMESPACE = "portal.moorimess.dao.statement.";
    
    /** 위임자 등록 */
    public void createMandator(StatementDetail mandator) {
		this.sqlInsert(NAMESPACE + "insertMandator", mandator);
	}
    
    /** 위임자 정보 조회 */
    public User selectMandatorInfo(StatementDetail mandator){
    	return (User)this.sqlSelectForObject(NAMESPACE + "selectMandatorInfo", mandator);
    }
    
    /** 위임자 권한 존재 여부 */
    public boolean selectMandatorCount(StatementDetail mandator){
    	
    	Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "selectMandatorCount", mandator);
    	
    	return count>0;
    }
    
    /** 위임자 삭제*/
    public void deleteMandator(StatementDetail mandator){
    	this.sqlDelete(NAMESPACE + "deleteMandator", mandator);
    }
    
    /** 위임자가 위임받았는지 여부 조회 */
    public boolean selectMandatorRoleCount(StatementDetail mandator){
    	
    	Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "selectMandatorRoleCount", mandator);
    	
    	return count>0;
    }
    
    /** 위임자가 권한테이블에 권한이 있는지 여부 조회 */
    public boolean selectExistRoleCount(StatementDetail mandator){
    	
    	Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "selectExistRoleCount", mandator);
    	
    	return count>0;
    };
    
    /** 위임자 권한 등록 */
    public void createMandatorRole(StatementDetail mandator) {
		this.sqlInsert(NAMESPACE + "insertMandatorRole", mandator);
	}
    
    /** 위임자 권한 삭제 */
    public void deleteMandatorRole(StatementDetail mandator){
    	this.sqlDelete(NAMESPACE + "deleteMandatorRole", mandator);
    }
    
    /** 사용자가 임원/팀장 인지 여부 조회 */
    public boolean isMaster(User user){
    	
	Integer count = (Integer)this.sqlSelectForObject(NAMESPACE + "isMaster", user);
    	
    	return count>0;
    }
    
    /** 기존에 있던 위임자 아이디 조회 */
    public StatementDetail selectMandatorId(StatementDetail mandator){
    	return (StatementDetail)this.sqlSelectForObject(NAMESPACE + "selectMandatorId", mandator);
    }
    
	public String create(StatementDetail arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}


	public StatementDetail get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}


	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}


	public void update(StatementDetail arg0) {
		// TODO Auto-generated method stub
		
	}
    
    
    
}
