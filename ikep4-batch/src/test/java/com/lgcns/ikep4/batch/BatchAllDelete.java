package com.lgcns.ikep4.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.service.QuartzJobService;

@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class BatchAllDelete extends AbstractTransactionalJUnit4SpringContextTests{
	@Autowired
	private QuartzJobService quartzJobService;
	
	@Test
	@Rollback(false)
	public void allDelete() {
		quartzJobService.deleteJob("BASIC_RemoveWorkspaceBoard");
		quartzJobService.deleteJob("BASIC_RemoveWorkspace");
		quartzJobService.deleteJob("BASIC_SyncMoveWorkspaceBoard");
		quartzJobService.deleteJob("BASIC_SyncTeamWorkspace");
		quartzJobService.deleteJob("BASIC_SyncUserWorkspace");
		quartzJobService.deleteJob("BASIC_SyncWorkspaceStatistics");
		quartzJobService.deleteJob("BASIC_expertNetworkBatch");
		quartzJobService.deleteJob("BASIC_forumActivityBatch");
		quartzJobService.deleteJob("BASIC_forumDiscussionScoreBatch");
		quartzJobService.deleteJob("BASIC_forumItemScoreBatch");
		quartzJobService.deleteJob("BASIC_forumLinereplyBestBatch");
		quartzJobService.deleteJob("BASIC_ideaContributionBatch");
		quartzJobService.deleteJob("BASIC_ideaBestBatch");
		quartzJobService.deleteJob("BASIC_ideaSuggesstionBatch");
		quartzJobService.deleteJob("BASIC_knowledgeMapBatch");
		quartzJobService.deleteJob("BASIC_KnowledgeStreamingQuartz");
		quartzJobService.deleteJob("BASIC_qnaScoreBatch");
		quartzJobService.deleteJob("BASIC_socialAnalyzerBatch");
		quartzJobService.deleteJob("BASIC_qnaStatisticsBatch");
		quartzJobService.deleteJob("BASIC_boardItemDeleteBatch");
		quartzJobService.deleteJob("BASIC_alarmScheduleJob");
		quartzJobService.deleteJob("BASIC_boardItemNotiScheduleJob");//게시판 독서자 메일 배치
		quartzJobService.deleteJob("BASIC_portletWeatherBatch");
		quartzJobService.deleteJob("BASIC_assessmentManagementBatch");
		quartzJobService.deleteJob("BASIC_knowledgeMonitorBatch");
		quartzJobService.deleteJob("BASIC_seamlessMessageBatch");
		quartzJobService.deleteJob("BASIC_surveyEndingScheduled");
		quartzJobService.deleteJob("BASIC_utStatisticsScheduled");
		quartzJobService.deleteJob("BASIC_socialanAnalyzerBatch");
		quartzJobService.deleteJob("BASIC_activityDelLogBatch");
		quartzJobService.deleteJob("BASIC_fileDeleteBatch");
		quartzJobService.deleteJob("BASIC_thumbnailBatch");
		quartzJobService.deleteJob("BASIC_messageScheduled");
		quartzJobService.deleteJob("BASIC_rankHistoryScheduled");
		quartzJobService.deleteJob("BASIC_rankSchedule");
		quartzJobService.deleteJob("BASIC_smsRecentDeleteBatch");
		quartzJobService.deleteJob("BASIC_cafeBoardItemDeleteBatch");
		//무림페이퍼 추가 배치 
		quartzJobService.deleteJob( "BASIC_customerInfoSyncBatch" );
		quartzJobService.deleteJob( "BASIC_kmsDivisionPermissionBatch" );
		quartzJobService.deleteJob( "BASIC_UserInfoSyncTrigger" );
		
	}
}
