
package com.lgcns.ikep4.portal.moorimess.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.portal.moorimess.dao.StatementDao;
import com.lgcns.ikep4.portal.moorimess.service.StatementService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.model.StatementDetail;

/**
 * 
 * 고객정보 품질/클레임 상담이력 구현체 클래스 
 *
 * @author SongHeeJung
 * @version $Id$
 */

@Service
@Transactional
public class StatementServiceImpl implements StatementService {

    @Autowired
	private StatementDao statementDao;

    
    public StatementDetail createMandator(User user, String portalId, String trusteeId,String gubun) {
		//BaseUrl
		Properties prop = PropertyLoader.loadProperties("/configuration/common-conf.properties");
		
		String baseUrl = prop.getProperty("ikep4.baseUrl");
    	
    	StatementDetail mandator = new StatementDetail();
		Date now = new Date();

		mandator.setMandatorId(user.getUserId());
		mandator.setRegisterId(user.getUserId());
		mandator.setRegisterName(user.getUserName());
		mandator.setPortalId(portalId);
		mandator.setRegistDate(now);
		mandator.setStartDate(now);
		mandator.setTrusteeId(trusteeId);
		mandator.setEndDate(this.toDate("99991231"));
		
		//운영서버인경우 운영디비 시퀀스
		if(baseUrl.toLowerCase().indexOf("ep.moorim.co.kr")>=0){			
			mandator.setRoleId("100001638388");
		}else{
		//개발서버 및 로컬일경우 개발디비 시퀀스	
			mandator.setRoleId("100000789634");
		}
		
		//구분 1:수정,등록 2:해제
		if("1".equals(gubun)){
			//권한자가 위임을 했을  경우 삭제하고 다시 넣는다.
			if(statementDao.selectMandatorCount(mandator)){
				//기존의 위임자를 조회
				StatementDetail oldman = statementDao.selectMandatorId(mandator);
				
				statementDao.deleteMandator(mandator);
				statementDao.createMandator(mandator);
				
				//위임권자를 수정한후 기존의 위임자 권한을 삭제 한다. 
				if(!statementDao.selectMandatorRoleCount(oldman)){
					statementDao.deleteMandatorRole(oldman);
				}
			}else{
				statementDao.createMandator(mandator);
			}
			//위임자가 권한이 있는지 여부를 확인해서 권한테이블에 넣어준다.
			if(!statementDao.selectExistRoleCount(mandator)){
				statementDao.createMandatorRole(mandator);
			}
		}else if("2".equals(gubun)){
			statementDao.deleteMandator(mandator);
			//위임자가 권한이 없으면 권한테이블에서 권한을 삭제한다. 
			if(!statementDao.selectMandatorRoleCount(mandator)){
				statementDao.deleteMandatorRole(mandator);
			}
		}
		
		return mandator;
	}
    
    public static Date toDate(final String str) {
		if(str.length() < 4) {
			throw new IllegalArgumentException();
		}

		String dtStr = StringUtils.rightPad(str, 14, "0");
		
		Calendar c = Calendar.getInstance();
		c.clear();
		
		int year = Integer.valueOf(dtStr.substring(0, 4)); //new Integer(dtStr.substring(0, 4));
		int month = Integer.valueOf(dtStr.substring(4, 6)) - 1; //new Integer(dtStr.substring(4, 6)) - 1;
		int date = Integer.valueOf(dtStr.substring(6, 8)); //new Integer(dtStr.substring(6, 8));
		int hourOfDay = Integer.valueOf(dtStr.substring(8, 10)); //new Integer(dtStr.substring(8, 10));
		int minute = Integer.valueOf(dtStr.substring(10, 12)); //new Integer(dtStr.substring(10, 12));	
		int second = Integer.valueOf(dtStr.substring(12, 14)); //new Integer(dtStr.substring(12, 14));
		
		c.set(year, month, date, hourOfDay, minute, second);
		
		return c.getTime();
	}
    
    public User selectMandatorInfo(StatementDetail mandator){
    	
    	User user = statementDao.selectMandatorInfo(mandator);
    	
    	return user;
    }
    
    /** 사용자가 임원/팀장 인지 여부 조회 */
    public boolean isMaster(User user){
    	return statementDao.isMaster(user);
    }

}
