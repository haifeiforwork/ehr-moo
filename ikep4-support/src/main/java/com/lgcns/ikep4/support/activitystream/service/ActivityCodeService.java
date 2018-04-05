package com.lgcns.ikep4.support.activitystream.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.activitystream.model.ActivityCode;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * ActivityCode 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ActivityCodeService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface ActivityCodeService extends GenericService<ActivityCode, String> {

	/**
	 * Activity Code 리스트 조회
	 * 
	 * @param user
	 * @return
	 */
	public List<ActivityCode> select(User user);

	/**
	 * Activity Config 조회
	 * 
	 * @param activityCode
	 * @return
	 */
	public ActivityCode selectConfig(ActivityCode activityCode);

	/**
	 * Activity Config 등록
	 * 
	 * @param activityCode
	 * @return
	 */
	public String createConfig(ActivityCode activityCode);

	/**
	 * Activity Config 삭제
	 * 
	 * @param activityCode
	 */
	public void removeConfig(ActivityCode activityCode);

	/**
	 * Activity Config 리스트 조회
	 * 
	 * @param activityCode
	 * @return
	 */
	public List<ActivityCode> selectConfigList(ActivityCode activityCode);

}
