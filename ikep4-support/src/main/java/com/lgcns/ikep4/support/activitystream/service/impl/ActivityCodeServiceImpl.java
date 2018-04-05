package com.lgcns.ikep4.support.activitystream.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.activitystream.dao.ActivityCodeDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.activitystream.service.ActivityCodeService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * ActivityCode 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeServiceImpl.java 3386 2011-03-18 07:16:40Z handul32
 *          $
 */
@Service
public class ActivityCodeServiceImpl extends GenericServiceImpl<ActivityCode, String> implements ActivityCodeService {

	/**
	 * activity stream Dao
	 */
	@Autowired
	private ActivityCodeDao activityStreamDao;

	/**
	 * 아이디생성 Service
	 */
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * Activity Code 리스트 조회
	 */
	public List<ActivityCode> select(User user) {
		return activityStreamDao.select(user.getUserId());
	}
	
	/**
	 * Activity Code 등록
	 */
	public String create(ActivityCode activityCode) {

		activityStreamDao.remove(activityCode.getRegisterId());

		if (activityCode.getActivityCodeList() != null) {
			for (String code : activityCode.getActivityCodeList()) {

				activityCode.setSubscriptionActivityCode(idgenService.getNextId());
				activityCode.setActivityCode(code);
				activityStreamDao.create(activityCode);
			}
		}
		return "ok";
	}
	
	/**
	 * Activity Code 삭제
	 * @param userId
	 */
	public void remove(String userId) {
		activityStreamDao.remove(userId);
	}

	/**
	 * Activity Config 리스트 조회
	 */
	public ActivityCode selectConfig(ActivityCode activityCode) {
		return activityStreamDao.selectConfig(activityCode);
	}
	
	/**
	 * Activity Config 등록
	 */
	public String createConfig(ActivityCode activityCode) {

		activityStreamDao.removeConfig(activityCode);
		activityStreamDao.createConfig(activityCode);
		return "ok";
	}
	
	/**
	 * Activity Config 삭제
	 */
	public void removeConfig(ActivityCode activityCode) {
		activityStreamDao.removeConfig(activityCode);
	}
	
	/**
	 * Activity Config 리스트 조회
	 */
	public List<ActivityCode> selectConfigList(ActivityCode activityCode) {
		return activityStreamDao.selectConfigList(activityCode);
	}

}
