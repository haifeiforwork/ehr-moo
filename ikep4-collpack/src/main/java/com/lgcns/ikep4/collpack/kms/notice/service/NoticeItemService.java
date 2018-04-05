package com.lgcns.ikep4.collpack.kms.notice.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.collpack.kms.notice.model.NoticeConfig;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeItem;
import com.lgcns.ikep4.collpack.kms.notice.search.NoticeItemSearchCondition;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.user.member.model.User;


@Transactional
public interface NoticeItemService {
	/**
	 * 공지사항 목록
	 * 
	 * @param noticeItemSearchCondition
	 * @return
	 */
	SearchResult<NoticeItem> listNoticeItemBySearchCondition(NoticeItemSearchCondition noticeItemSearchCondition);

	/**
	 * 공지사항 조회
	 * 
	 * @param userId
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	NoticeItem readNoticeItem(String userId, String itemId, String workspaceId);
	NoticeItem readNoticeItem(String userId, String itemId);
	
	public NoticeConfig readNoticeconfig();
	
	public void createNoticeConfig(NoticeConfig noticeConfig, List<MultipartFile> fileList, String editorAttach, User user);

	/**
	 * 공지사항 등록
	 * 
	 * @param noticeItem
	 * @param user
	 * @return
	 */
	String createNoticeItem(NoticeItem noticeItem, User user);

	/**
	 * 공지사항 정보 수정
	 * 
	 * @param noticeItem
	 * @param user
	 */
	void updateNoticeItem(NoticeItem noticeItem, User user);

	/**
	 * 공지 사항 삭제
	 * 
	 * @param noticeItem
	 */
	void adminDeleteNoticeItem(NoticeItem noticeItem);

	/**
	 * 공지사항 다중 삭제
	 * 
	 * @param itemIds
	 * @param workspaceId
	 * @param portalId
	 */
//	void adminMultiDeleteNoticeItem(List<String> itemIds, String workspaceId, String portalId);
	void adminMultiDeleteNoticeItem(List<String> itemIds, String portalId);

//	/**
//	 * Workspace 중 개설된 Workspace 의 하위 팀 목록
//	 * 
//	 * @param searchCondition
//	 * @return
//	 */
//	SearchResult<NoticeItem> listChildWorkspaceInfoBySearchCondition(NoticeItemSearchCondition searchCondition);

	/**
	 * 공지사항 링크 정보 등록
	 * 
	 * @param noticeItemId
	 * @param workspaceIds
	 */
//	void createNoticeLinkItem(String noticeItemId, List<String> workspaceIds);
//	void createNoticeLinkItem(String noticeItemId);

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
	public List<NoticeItem> listNoticeItemByPortlet(Map<String, String> map);

	/**
	 * 공지사항중 게시물에 첨부가 있는 게시물 목록 조회 - Workspace 삭제 배치시 첨부 파일 삭제처리
	 * 
	 * @param workspaceId
	 * @return
	 */
//	public List<NoticeItem> listDeleteNoticeByWorkspace(String workspaceId);
	public List<NoticeItem> listDeleteNoticeByWorkspace();
}
