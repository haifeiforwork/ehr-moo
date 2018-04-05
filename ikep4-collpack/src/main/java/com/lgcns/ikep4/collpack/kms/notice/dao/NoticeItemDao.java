package com.lgcns.ikep4.collpack.kms.notice.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.notice.model.NoticeConfig;
import com.lgcns.ikep4.collpack.kms.notice.model.NoticeItem;
import com.lgcns.ikep4.collpack.kms.notice.search.NoticeItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.portal.portlet.model.PortletManagerMessage;

public interface NoticeItemDao extends GenericDao<NoticeItem, String> {
	/**
	 * 공지사항 목록
	 * @param noticeItemSearchCondition
	 * @return
	 */
	List<NoticeItem> listBySearchCondition(NoticeItemSearchCondition noticeItemSearchCondition);
	/**
	 * 공지사항 목록 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(NoticeItemSearchCondition boardItemSearchCondition);

	/**
	 * 공지사항 내용 조회 카운트 변경
	 * @param id
	 */
	void updateNoticeHitCount(String id);

//	/**
//	 * 공지사항 등록
//	 * @param noticeItem
//	 */
//	void createLinkNotice(NoticeItem noticeItem);

	/**
	 * 공지사항 정보 조회
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	NoticeItem getNotice(String itemId, String workspaceId);
	NoticeItem getNotice(String itemId);
	
	public NoticeConfig readNoticeconfig();
	
	public void createNoticeConfig(NoticeConfig noticeConfig);
	
	public void updateNoticeConfig(NoticeConfig noticeConfig);

	/**
	 * 공지사항 링크 정보 삭제
	 * @param itemId
	 * @param workspaceId
	 */
//	void removeNoticeLink(String itemId,String workspaceId);
	void removeNoticeLink(String itemId);

	/**
	 * 공지사항 정보 삭제
	 * @param itemId
	 */
	void removeNoticeItem(String itemId);

	/**
	 * 하위 팀 Workspace 정보 Count
	 * @param searchCondition
	 * @return
	 */
	Integer countChildWorkspaceBySearchCondition(NoticeItemSearchCondition searchCondition);
	/**
	 *  하위 팀 Workspace 정보  목록
	 * @param searchCondition
	 * @return
	 */
	List<NoticeItem> listChildWorkspaceBySearchCondition(NoticeItemSearchCondition searchCondition);

	/**
	 * 공지사항 공유 링크 정보 삭제
	 * @param noticeItemId
	 */
	void removeNoticeShareLink(String noticeItemId);

	/**
	 * 공지사항 조회 Reference 등록
	 * @param userId
	 * @param itemId
	 */
	void createNoticeItemReference(String userId,String itemId);

	/**
	 * 조회 Reference 카운트
	 * @param userId
	 * @param itemId
	 * @return
	 */
	int countNoticeReference(String userId, String itemId);

	/**
	 * 조회 Reference 삭제
	 * @param itemId
	 */
	void removeNoticeReference(String itemId); 
	
	/**
	 * Workspace 메인 포틀릿 
	 * @param map
	 * @return
	 */
	public List<NoticeItem> listNoticeItemByPortlet(Map<String,String> map);
	/**
	 * Workspace 삭제 배치시 공지사항 첨부 파일 삭제
	 * @param workspaceId
	 * @return
	 */
//	public List<NoticeItem> listDeleteNoticeByWorkspace(String workspaceId);
	public List<NoticeItem> listDeleteNoticeByWorkspace();
	
	public NoticeItem getTopNoticeItem();
}
