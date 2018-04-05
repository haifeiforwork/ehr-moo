/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.support.searchpreprocessor.web;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
//import com.lgcns.ikep4.support.message.dao.MessageAdminDao;
import com.lgcns.ikep4.support.searchpreprocessor.model.Dictionary;
import com.lgcns.ikep4.support.searchpreprocessor.model.Rank;
import com.lgcns.ikep4.support.searchpreprocessor.service.DictionaryService;
import com.lgcns.ikep4.support.searchpreprocessor.service.RankService;


/**
 * 검색어 rank 배치
 * 
 * @author 고인호(ihko11@daum.net)
 * @version $Id: RankScheduled.java 17315 2012-02-08 04:56:13Z yruyo $
 */
public class RankScheduled extends QuartzJobBean {

	private DictionaryService dictionaryService;

	private RankService rankService;

	/**
	 * 실행 메소드
	 */
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");

			this.dictionaryService = (DictionaryService) appContext.getBean("dictionaryServiceImpl");
			this.rankService = (RankService) appContext.getBean("rankServiceImpl");

			// 검색어 사전에서 현재까지의 카운트 가져오기

			List<String> portalList = dictionaryService.getPortalIdList();

			for (String portalId : portalList) {

				Dictionary dictionary = new Dictionary();
				dictionary.setPortalId(portalId);

				List<Dictionary> dictionaryList = dictionaryService.getRankList(dictionary);
				int rankingNum = 1;

				// 랭킹 테이블에서 기존값 가져오기
				List<Rank> rankList = rankService.getRankList();
				// 기존데이터 모두삭제
				rankService.removeAll();

				for (Dictionary dbInfo : dictionaryList) {

					Rank rank = null;

					for (Rank rankdb : rankList) {

						if (dbInfo.getSearchKeywordId().equals(rankdb.getSearchKeywordId())) {
							rank = rankdb;
							break;
						}
					}

					if (rank != null) {
						rank.setPreSearchRank(rank.getSearchRank());
						rank.setSearchRank(rankingNum++);
					} else {
						rank = new Rank();
						rank.setSearchKeywordId(dbInfo.getSearchKeywordId());
						rank.setPreSearchRank(0);
						rank.setSearchRank(rankingNum++);
					}

					rankService.create(rank);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

}
