package com.lgcns.ikep4.support.activitystream.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.activitystream.dao.ActivityDelLogDao;
import com.lgcns.ikep4.support.activitystream.model.ActivityDelLog;
import com.lgcns.ikep4.support.activitystream.service.ActivityDelLogService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * ActivityDelLog 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityDelLogServiceImpl.java 3386 2011-03-18 07:16:40Z
 *          handul32 $
 */
@Service
public class ActivityDelLogServiceImpl extends GenericServiceImpl<ActivityDelLog, String> implements
		ActivityDelLogService {

	/**
	 * Activity Delete log Dao
	 */
	@Autowired
	private ActivityDelLogDao activityDelLogDao;

	/**
	 * 아이디생성 Service
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * Activity Delete log 등록
	 */
	public String create(ActivityDelLog activityDelLog) {

		activityDelLog.setLogId(idgenService.getNextId());
		activityDelLogDao.create(activityDelLog);

		return "ok";
	}

	/**
	 * Activity Delete log 조회
	 */
	public List<ActivityDelLog> select(ActivityDelLog activityDelLog) {
		return activityDelLogDao.select(activityDelLog);
	}

	/**
	 * Activity Delete log 배치 처리
	 */
	public int deleteBatch(ActivityDelLog activityDelLog) {

		int deleteCount = activityDelLogDao.deleteBatch(activityDelLog);

		return deleteCount;
	}

}
