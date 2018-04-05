package com.lgcns.ikep4.support.activitystream.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.activitystream.model.ActivityDelLog;


/**
 * ActivityDelLog 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityDelLogService.java 10972 2011-05-13 08:19:46Z handul32
 *          $
 */
@Transactional
public interface ActivityDelLogService extends GenericService<ActivityDelLog, String> {

	/**
	 * Activity Delete log 조회
	 * 
	 * @param activityDelLog
	 * @return
	 */
	public List<ActivityDelLog> select(ActivityDelLog activityDelLog);

	/**
	 * Activity Delete log 배치 처리
	 * 
	 * @param activityDelLog
	 * @return
	 */
	public int deleteBatch(ActivityDelLog activityDelLog);

}
