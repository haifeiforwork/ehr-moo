/**
 * 
 */
package com.lgcns.ikep4.lightpack.cafe.board.batch;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerContext;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.lightpack.cafe.board.model.Board;
import com.lgcns.ikep4.lightpack.cafe.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.cafe.cafe.model.Cafe;
import com.lgcns.ikep4.lightpack.cafe.cafe.service.CafeService;

/**
 * 카페 배치 클래스
 * (
 * 영구 삭제된 카페 게시판
 * 삭제된 게시판 및 게시글
 * )
 * 제거 하는 클래스
 * @author 조경식
 *
 */
public class CafeBoardItemDeleteBatch extends QuartzJobBean {
	/** The log. */
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private CafeService cafeService;
	
	private BoardAdminService cafeBoardAdminService;
	
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		try {
			// Job Data Map에서ref-bean을 등록하지 못하므로 아래처럼 코드를 처리
			SchedulerContext schedulerContext = context.getScheduler().getContext();
			ApplicationContext appContext = (ApplicationContext) schedulerContext.get("applicationContext");
			
			this.cafeService = (CafeService) appContext.getBean("cafeService");
			this.cafeBoardAdminService = (BoardAdminService) appContext.getBean("cfBoardAdminServiceImpl");
			
			
			/*
			 * 영구 삭제된 카페의 게시판 및 게시글 처리 루틴
			 */
			// 영구 삭제된 카페 리스트 가져오기 (Status = ED)
			List<Cafe> deleteCafeList = this.cafeService.listAllCafeDelete();
			List<String> cafeIdList = new ArrayList<String>();
			List<String> boardIdList = null;
			
			for(Cafe cafe : deleteCafeList) {
				cafeIdList.add(cafe.getCafeId());
				
				//보드 아이디 가져오기
				boardIdList = this.cafeBoardAdminService.listBoardIdForCafe(cafe.getCafeId());
				
				if(boardIdList != null) {
					for(String boardId : boardIdList) {
						// 게시글 삭제, 댓글 삭제, 게시판 삭제
						this.cafeBoardAdminService.physicalDeleteBoard(boardId);
					}
				}
			}
			
			// 카페 삭제
			this.cafeService.physicalDelete(cafeIdList);
			
			/*
			 * 삭제된 카페 게시판에 대한 게시판 삭제 및 관련 게시글 삭제 처리 루틴
			 */
			List<Board> deleteBoardIdList = this.cafeBoardAdminService.listDeleteBoardBatch();
			for(Board board : deleteBoardIdList) {
				// 게시글 삭제, 댓글 삭제, 게시판 삭제
				this.cafeBoardAdminService.physicalDeleteBoard(board.getBoardId());
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new IKEP4ApplicationException("", e);
		}
		
	}
}
