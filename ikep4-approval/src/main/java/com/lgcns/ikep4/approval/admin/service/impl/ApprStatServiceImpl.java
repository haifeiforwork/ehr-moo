package com.lgcns.ikep4.approval.admin.service.impl;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.approval.admin.dao.ApprStatDao;
import com.lgcns.ikep4.approval.admin.model.ApprStat;
import com.lgcns.ikep4.approval.admin.service.ApprStatService;
import com.lgcns.ikep4.approval.work.search.ApprListSearchCondition;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.support.IKEP4ReloadableResourceBundleMessageSource;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 통계 서비스
 * 
 * @author handul
 * @version $Id$
 */
@Service
public class ApprStatServiceImpl extends GenericServiceImpl<ApprStat, String> implements ApprStatService {

	@Autowired
	private ApprStatDao apprStatDao;

	@Autowired
	private IKEP4ReloadableResourceBundleMessageSource messageSource;

	/**
	 * 리드타임 통계 생성
	 */
	public void generateTimeStat(ApprStat apprStat) {
		apprStatDao.generateTimeStat(apprStat);
	}

	/**
	 * 사용자별 통계 생성
	 */
	public void generateUserStat(ApprStat apprStat) {
		apprStatDao.generateUserStat(apprStat);
	}

	/**
	 * 양식별 통계 생성
	 */
	public void generateFormStat(ApprStat apprStat) {
		apprStatDao.generateFormStat(apprStat);
	}

	/**
	 * 리드타임 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getTimeStatList(ApprListSearchCondition searchCondition) {

		SearchResult<ApprStat> searchResult = null;

		List<ApprStat> apprList = (List<ApprStat>) apprStatDao.getTimeStatList(searchCondition);
		searchResult = new SearchResult<ApprStat>(apprList, searchCondition);

		return searchResult;
	}

	/**
	 * 사용자별 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getUserStatList(ApprListSearchCondition searchCondition) {

		Integer count = apprStatDao.getUserStatCount(searchCondition);

		searchCondition.terminateSearchCondition(count);

		SearchResult<ApprStat> searchResult = null;

		if (searchCondition.isEmptyRecord()) {
			searchResult = new SearchResult<ApprStat>(searchCondition);
		} else {
			List<ApprStat> apprList = (List<ApprStat>) apprStatDao.getUserStatList(searchCondition);
			searchResult = new SearchResult<ApprStat>(apprList, searchCondition);
		}

		return searchResult;
	}

	/**
	 * 양식별 통계 리스트
	 * 
	 * @return
	 */
	public SearchResult<ApprStat> getFormStatList(ApprListSearchCondition searchCondition) {

		SearchResult<ApprStat> searchResult = null;
		searchCondition.terminateSearchCondition(100);
		try {
			List<ApprStat> apprList = (List<ApprStat>) apprStatDao.getFormStatList(searchCondition);
			searchResult = new SearchResult<ApprStat>(apprList, searchCondition);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return searchResult;
	}

	/**
	 * ApprLeadTime String 생성
	 * 
	 * @param apprLeadTime
	 * @return
	 */
	private String makeApprLeadTimeStr(int apprLeadTime) {

		String apprLeadTimeStr = "";
		int apprLeadTimeHour = 0;
		int apprLeadTimeDay = 0;

		User user = (User) this.getRequestAttribute("ikep.user");
		String minuteStr = messageSource.getMessage("ui.approval.common.searchCondition.minute", null,
				new Locale(user.getLocaleCode()));
		String hourStr = messageSource.getMessage("ui.approval.common.searchCondition.hour", null,
				new Locale(user.getLocaleCode()));
		String dayStr = messageSource.getMessage("ui.approval.common.searchCondition.day", null,
				new Locale(user.getLocaleCode()));

		if (apprLeadTime > 60 * 24) {
			apprLeadTimeDay = apprLeadTime / (60 * 24);
			apprLeadTime = apprLeadTime % (60 * 24);

			if (apprLeadTime > 60) {
				apprLeadTimeHour = apprLeadTime / 60;
				apprLeadTime = apprLeadTime % 60;
				apprLeadTimeStr = apprLeadTimeDay + dayStr + " " + apprLeadTimeHour + hourStr + " " + apprLeadTime
						+ minuteStr;
			} else {
				apprLeadTimeStr = apprLeadTimeDay + dayStr + " " + apprLeadTime + minuteStr;
			}
		} else if (apprLeadTime > 60) {
			apprLeadTimeHour = apprLeadTime / 60;
			apprLeadTime = apprLeadTime % 60;
			apprLeadTimeStr = apprLeadTimeHour + hourStr + " " + apprLeadTime + minuteStr;
		} else {
			apprLeadTimeStr = apprLeadTime + minuteStr;
		}

		return apprLeadTimeStr;
	}

	/**
	 * 세션 정보 읽기
	 * 
	 * @param name
	 * @return
	 */
	public Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(name, RequestAttributes.SCOPE_SESSION);
	}
}
