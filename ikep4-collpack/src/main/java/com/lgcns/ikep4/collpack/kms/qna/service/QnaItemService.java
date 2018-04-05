package com.lgcns.ikep4.collpack.kms.qna.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.collpack.kms.qna.model.QnaItem;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaRecommendPK;
import com.lgcns.ikep4.collpack.kms.qna.search.QnaItemSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface QnaItemService {
	/**
	 * 공지사항 목록
	 * 
	 * @param qnaItemSearchCondition
	 * @return
	 */
	SearchResult<QnaItem> listQnaItemBySearchCondition(QnaItemSearchCondition qnaItemSearchCondition);
	
	SearchResult<QnaItem> listQnaSubItem(QnaItemSearchCondition qnaItemSearchCondition);
	
	Boolean exsitRecommend(QnaRecommendPK boardRecommend);
	
	void updateRecommendCount(QnaRecommendPK boardRecommend);

	/**
	 * 공지사항 조회
	 * 
	 * @param userId
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	QnaItem readQnaItem(String userId, String itemId, String workspaceId);
	QnaItem readQnaItem(String userId, String itemId);
	
	String createReplyBoardItem(QnaItem boardItem, User user);
	
	//public QnaConfig readQnaconfig();
	
	//public void createQnaConfig(QnaConfig qnaConfig, List<MultipartFile> fileList, String editorAttach, User user);

	/**
	 * 공지사항 등록
	 * 
	 * @param qnaItem
	 * @param user
	 * @return
	 */
	String createQnaItem(QnaItem qnaItem, User user);

	/**
	 * 공지사항 정보 수정
	 * 
	 * @param qnaItem
	 * @param user
	 */
	void updateQnaItem(QnaItem qnaItem, User user);

	/**
	 * 공지 사항 삭제
	 * 
	 * @param qnaItem
	 */
	void adminDeleteQnaItem(QnaItem qnaItem);

	/**
	 * 공지사항 다중 삭제
	 * 
	 * @param itemIds
	 * @param workspaceId
	 * @param portalId
	 */
//	void adminMultiDeleteQnaItem(List<String> itemIds, String workspaceId, String portalId);
	void adminMultiDeleteQnaItem(List<String> itemIds, String portalId);

//	/**
//	 * Workspace 중 개설된 Workspace 의 하위 팀 목록
//	 * 
//	 * @param searchCondition
//	 * @return
//	 */
//	SearchResult<QnaItem> listChildWorkspaceInfoBySearchCondition(QnaItemSearchCondition searchCondition);

	/**
	 * 공지사항 링크 정보 등록
	 * 
	 * @param qnaItemId
	 * @param workspaceIds
	 */
//	void createQnaLinkItem(String qnaItemId, List<String> workspaceIds);
//	void createQnaLinkItem(String qnaItemId);

//	/**
//	 * Workspace 정보 조회
//	 * 
//	 * @param portalId
//	 * @param workspaceId
//	 * @return
//	 */
//	Workspace getWorkspaceInfo(String portalId, String workspaceId);

	/**
	 * Workspace 메인 포틀릿
	 * 
	 * @param map
	 * @return
	 */
	public List<QnaItem> listQnaItemByPortlet(Map<String, String> map);

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 * 
	 * @param workspaceId
	 * @return
	 */
//	public List<QnaItem> listDeleteQnaByWorkspace(String workspaceId);
	public List<QnaItem> listDeleteQnaByWorkspace();
}
