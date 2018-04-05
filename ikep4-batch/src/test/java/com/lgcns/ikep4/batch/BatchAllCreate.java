/**
 * 
 */
package com.lgcns.ikep4.batch;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import com.lgcns.ikep4.support.quartz.model.BatchCronJobDetail;
import com.lgcns.ikep4.support.quartz.model.BatchSimpleJobDetail;
import com.lgcns.ikep4.support.quartz.service.QuartzJobService;

/**
 * 배치 등록 테스트 유닛
 * @author 주길재
 *
 */
@ContextConfiguration({ "classpath:/context-batch-dao.xml",
	"classpath:/context-batch-service.xml", "classpath:/context-batch-test.xml" })
public class BatchAllCreate extends AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	private QuartzJobService quartzJobService;
	
	private BatchCronJobDetail cronJobDetail = null;
	
	private BatchSimpleJobDetail simpleJobDetail = null;
	
	@Test
	@Rollback(false)
	public void testAllCreate() {
		
		cronJobDetail = new BatchCronJobDetail();

		cronJobDetail.setTriggerName("BASIC_RemoveWorkspaceBoardTrigger");
		cronJobDetail.setJobName("BASIC_RemoveWorkspaceBoard");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.RemoveWorkspaceBoard");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Workspace 삭제 (삭제된 Workspace 정보 최종 삭제)");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_RemoveWorkspaceTrigger");
		cronJobDetail.setJobName("BASIC_RemoveWorkspace");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.RemoveWorkspace");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Workspace 게시판 삭제 (게시물,권한,첨부 삭제)");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_SyncMoveWorkspaceBoardTrigger");
		cronJobDetail.setJobName("BASIC_SyncMoveWorkspaceBoard");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.SyncMoveWorkspaceBoard");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("게시판 이관시 해당 WS 게시판 권한 변경");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_SyncTeamWorkspaceTrigger");
		cronJobDetail.setJobName("BASIC_SyncTeamWorkspace");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.SyncTeamWorkspace");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("부서별 Workspace 생성");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_SyncUserWorkspaceTrigger");
		cronJobDetail.setJobName("BASIC_SyncUserWorkspace");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.SyncUserWorkspace");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("신규 사용자의 부서 Workspace 멤버 가입");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_SyncWorkspaceStatisticsTrigger");
		cronJobDetail.setJobName("BASIC_SyncWorkspaceStatistics");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.collaboration.quartzScheduler.SyncWorkspaceStatistics");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Workspace 통계 생성");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_expertNetworkCronTrigger");
		//cronJobDetail.setJobName("BASIC_expertNetworkBatch");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.expertnetwork.batch.ExpertNetworkQuartz");
		//cronJobDetail.setCronExpression("0 0 1 * * ?");
		//cronJobDetail.setDescription("인기전문가 및 분야별 전문가 식별");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_forumActivityCronTrigger");
		cronJobDetail.setJobName("BASIC_forumActivityBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.forum.batch.ActivityQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("사용자 활동 점수 저장, 랭킹 수정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_forumDiscussionScoreCronTrigger");
		cronJobDetail.setJobName("BASIC_forumDiscussionScoreBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.forum.batch.DiscussionScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("토론 주제 점수 저장");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_forumItemScoreCronTrigger");
		cronJobDetail.setJobName("BASIC_forumItemScoreBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.forum.batch.ItemScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("토론글 점수 저장, 우수 토론글 선정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_forumLinereplyBestCronTrigger");
		cronJobDetail.setJobName("BASIC_forumLinereplyBestBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.forum.batch.LinereplyBestQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("우수 토론 의견 선정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_ideaContributionCronTrigger");
		cronJobDetail.setJobName("BASIC_ideaContributionBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.ideation.batch.ContributionScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("기여 점수 저장, 랭킹 수정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_ideaBestCronTrigger");
		cronJobDetail.setJobName("BASIC_ideaBestBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.ideation.batch.IdeaBestQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Idea 베스트 선정, 사용자 베스트 idea개수 수정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_ideaSuggesstionCronTrigger");
		cronJobDetail.setJobName("BASIC_ideaSuggesstionBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.ideation.batch.SuggestionScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("제안 점수 저장, 랭킹 수정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_knowledgeMapCronTrigger");
		cronJobDetail.setJobName("BASIC_knowledgeMapBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.knowledgehub.batch.KnowledgeMapQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("인기지식 집계");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_KnowledgeStreamingTrigger");
		//cronJobDetail.setJobName("BASIC_KnowledgeStreamingQuartz");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.knowledgestreaming.batch.KnowledgeStreamingQuartz");
		//cronJobDetail.setCronExpression("0 43 14 * * ?");
		//cronJobDetail.setDescription("지식 활용 수준 집계 데이터");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_qnaScoreCronTrigger");
		cronJobDetail.setJobName("BASIC_qnaScoreBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.qna.batch.ScoreQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Q&A score저장, best 질문, 답변 베스트 선정");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_qnaStatisticsCronTrigger");
		cronJobDetail.setJobName("BASIC_qnaStatisticsBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.qna.batch.StatisticsQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("Q&A 평균시간, 평균 답변수  처리");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_boardItemDeleteBatchTrigger");
		cronJobDetail.setJobName("BASIC_boardItemDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.lightpack.board.batch.BoardItemDeleteBatch");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("삭제 대기 상태인 게시판과 게시판에 속해 있는 게시물, 게시기간이 지난 게시물, 댓글 삭제");
		
		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		
		simpleJobDetail = new BatchSimpleJobDetail();
		simpleJobDetail.setTriggerName("BASIC_alarmScheduleTrigger");
		simpleJobDetail.setJobName("BASIC_alarmScheduleJob");
		simpleJobDetail.setJobClass("com.lgcns.ikep4.lightpack.planner.batch.NotiSchedule");
		simpleJobDetail.setRepeatInterval(300000);
		simpleJobDetail.setRepeatCount(-1);
		simpleJobDetail.setDescription("일정 알람 서비스(메일,쪽지,SMS)");
		
		quartzJobService.createJobSimpleTrigger(simpleJobDetail);
		
		simpleJobDetail = new BatchSimpleJobDetail();
		simpleJobDetail.setTriggerName("BASIC_boardItemNotiScheduleTrigger");
		simpleJobDetail.setJobName("BASIC_boardItemNotiScheduleJob");
		simpleJobDetail.setJobClass("com.lgcns.ikep4.lightpack.board.batch.BoardItemNotiSchedule");
		simpleJobDetail.setRepeatInterval(900000);
		simpleJobDetail.setRepeatCount(-1);
		simpleJobDetail.setDescription("게시판 독서자 메일 서비스");
		
		quartzJobService.createJobSimpleTrigger(simpleJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_portletWeatherBatchTrigger");
		//cronJobDetail.setJobName("BASIC_portletWeatherBatch");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.portal.portlet.batch.PortletWeatherBatch");
		//cronJobDetail.setCronExpression("0 0 7 * * ?");
		//cronJobDetail.setDescription("날씨 포틀릿 배치 프로그램");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_assessmentManagementCronTrigger");
		cronJobDetail.setJobName("BASIC_assessmentManagementBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.assess.batch.AssessmentManagementQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("개인별/조직별 평가 순위 집계");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_knowledgeMonitorCronTrigger");
		//cronJobDetail.setJobName("BASIC_knowledgeMonitorBatch");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.collpack.knowledgemonitor.batch.KnowledgeMonitorQuartz");
		//cronJobDetail.setCronExpression("0 0 1 * * ?");
		//cronJobDetail.setDescription("지식등록 건수 집계");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_seamlessMessageCronTrigger");
		cronJobDetail.setJobName("BASIC_seamlessMessageBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.servicepack.seamless.batch.SeamlessMessageBatch");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("만료기한 경과한 메일 보관 정보 삭제");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_surveyEndingCronTrigger");
		cronJobDetail.setJobName("BASIC_surveyEndingScheduled");
		cronJobDetail.setJobClass("com.lgcns.ikep4.servicepack.survey.web.SurveyEndingScheduled");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("설문 일일 마감");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_utStatisticsCronTrigger");
		cronJobDetail.setJobName("BASIC_utStatisticsScheduled");
		cronJobDetail.setJobClass("com.lgcns.ikep4.servicepack.usagetracker.batch.UtStatisticsScheduled");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("사용자랭킹/ 사용량 통계/히스토리 이동");
		cronJobDetail.setJobListener("com.lgcns.ikep4.support.searchpreprocessor.web.RankExecutionExceptionListener");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_socialAnalyzerCronTrigger");
		//cronJobDetail.setJobName("BASIC_socialAnalyzerBatch");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.socialpack.socialanalyzer.batch.SocialAnalyzerBatch");
		//cronJobDetail.setCronExpression("0 0 1 * * ?");
		//cronJobDetail.setDescription("소셜 랭킹 작업 및 소셜 네트웍 점수 구하기 작업");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		//무림제외
		//cronJobDetail = new BatchCronJobDetail();
		//cronJobDetail.setTriggerName("BASIC_activityDelLogTrigger");
		//cronJobDetail.setJobName("BASIC_activityDelLogBatch");
		//cronJobDetail.setJobClass("com.lgcns.ikep4.support.activitystream.batch.ActivityDelLogBatch");
		//cronJobDetail.setCronExpression("0 32 1 * * ?");
		//cronJobDetail.setDescription("Activity Stream Log을 삭제하는 배치 프로그램");

		//quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_fileDeleteTrigger");
		cronJobDetail.setJobName("BASIC_fileDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.fileupload.batch.FileDeleteBatch");
		cronJobDetail.setCronExpression("0 11 18 * * ?");
		cronJobDetail.setDescription("파일삭제 배치 프로그램");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_thumbnailTrigger");
		cronJobDetail.setJobName("BASIC_thumbnailBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.fileupload.batch.ThumbnailBatch");
		cronJobDetail.setCronExpression("0 11 18 * * ?");
		cronJobDetail.setDescription("섬네일 변환 배치 프로그램");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_messageCronTrigger");
		cronJobDetail.setJobName("BASIC_messageScheduled");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.message.batch.MessageBatch");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("만료기한 경과 쪽지 삭제");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_rankHistoryCronTrigger");
		cronJobDetail.setJobName("BASIC_rankHistoryScheduled");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.searchpreprocessor.web.RankHistoryScheduled");
		cronJobDetail.setCronExpression("1 1 1 * * ?");
		cronJobDetail.setDescription("3개월 이하 히스토리 삭제");
		cronJobDetail.setJobListener("com.lgcns.ikep4.support.searchpreprocessor.web.RankExecutionExceptionListener");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_rankCronTrigger");
		cronJobDetail.setJobName("BASIC_rankSchedule");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.searchpreprocessor.web.RankScheduled");
		cronJobDetail.setCronExpression("0 1 * * * ?");
		cronJobDetail.setDescription("랭킹 100위 생성");
		cronJobDetail.setJobListener("com.lgcns.ikep4.support.searchpreprocessor.web.RankExecutionExceptionListener");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_smsRecentDeleteCronTrigger");
		cronJobDetail.setJobName("BASIC_smsRecentDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.support.sms.batch.SmsQuartz");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("최근 3개월 이전 데이터 삭제");

		quartzJobService.createJobCronTrigger(cronJobDetail);
		
		cronJobDetail = new BatchCronJobDetail();
		cronJobDetail.setTriggerName("BASIC_cafeBoardItemDeleteBatch");
		cronJobDetail.setJobName("BASIC_cafeBoardItemDeleteBatch");
		cronJobDetail.setJobClass("com.lgcns.ikep4.lightpack.cafe.board.batch.CafeBoardItemDeleteBatch");
		cronJobDetail.setCronExpression("0 0 1 * * ?");
		cronJobDetail.setDescription("영구 삭제된 카페 게시판, 삭제된 게시판 및 게시글, 댓글 삭제");
		
		
        quartzJobService.createJobCronTrigger(cronJobDetail); 
        
        
      //무림페이퍼 배치 추가 
        cronJobDetail = new BatchCronJobDetail();
        
        cronJobDetail.setTriggerName( "BASIC_UserInfoSyncBatch" );
        cronJobDetail.setJobName( "BASIC_UserInfoSyncTrigger" );
        cronJobDetail.setJobClass( "com.lgcns.ikep4.support.user.batch.UserInfoSyncBatch" );
        cronJobDetail.setCronExpression( "0 20 5 * * ?" );
        cronJobDetail.setDescription( "SAP의 사용자 정보 동기화" );
                
        quartzJobService.createJobCronTrigger(cronJobDetail);
       
       
        cronJobDetail = new BatchCronJobDetail();        
        cronJobDetail.setTriggerName( "BASIC_kmsDivisionPermissionBatch" );
        cronJobDetail.setJobName( "BASIC_kmsDivisionPermissionBatch" );
        cronJobDetail.setJobClass( "com.lgcns.ikep4.collpack.kms.batch.KmsDivisionPermissionBatch" );
        cronJobDetail.setCronExpression( "0 0 1 * * ?" );
        cronJobDetail.setDescription( "신규리더가 추가될때 IKEP4_KMS_DIVISION_PERMISSION 테이블에 관리자를 추가한다" );
        
        quartzJobService.createJobCronTrigger(cronJobDetail);
        
        
        cronJobDetail = new BatchCronJobDetail();        
        cronJobDetail.setTriggerName( "BASIC_customerInfoSyncTrigger" );
        cronJobDetail.setJobName( "BASIC_customerInfoSyncBatch" );
        cronJobDetail.setJobClass( "com.lgcns.ikep4.support.customer.batch.CustomerInfoSyncBatch" );
        cronJobDetail.setCronExpression( "0 35 4 * * ?" );
        cronJobDetail.setDescription( "SAP의 고객정보 데이터를 가져온다." );
        
        quartzJobService.createJobCronTrigger(cronJobDetail);
        
        
        cronJobDetail = new BatchCronJobDetail();        
        cronJobDetail.setTriggerName( "BASIC_UserProfileImageSyncBatch" );
        cronJobDetail.setJobName( "BASIC_UserProfileImageSyncSyncTrigger" );
        cronJobDetail.setJobClass( "com.lgcns.ikep4.support.user.batch.UserProfileImageSyncBatch" );
        cronJobDetail.setCronExpression( "0 0 3 * * ?" );
        cronJobDetail.setDescription( "사용자의 profile 이미지를 서버와 동기화" );
        
        quartzJobService.createJobCronTrigger(cronJobDetail);
        

        
        
        
	}
}
