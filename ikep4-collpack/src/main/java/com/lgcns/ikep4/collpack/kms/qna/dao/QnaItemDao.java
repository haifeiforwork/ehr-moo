package com.lgcns.ikep4.collpack.kms.qna.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.qna.model.QnaConfig;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaItem;
import com.lgcns.ikep4.collpack.kms.qna.model.QnaRecommendPK;
import com.lgcns.ikep4.collpack.kms.qna.search.QnaItemSearchCondition;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface QnaItemDao extends GenericDao<QnaItem, String> {
	/**
	 * 공지사항 목록
	 * @param qnaItemSearchCondition
	 * @return
	 */
	List<QnaItem> listBySearchCondition(QnaItemSearchCondition qnaItemSearchCondition);
	
	List<QnaItem> listQnaSubItem(QnaItemSearchCondition qnaItemSearchCondition);
	/**
	 * 공지사항 목록 갯수
	 * @param boardItemSearchCondition
	 * @return
	 */
	Integer countBySearchCondition(QnaItemSearchCondition boardItemSearchCondition);
	
	Integer countQnaSubItem(QnaItemSearchCondition boardItemSearchCondition);
	
	void updateRecommendCount(String id);
	
	boolean existsRecommend(QnaRecommendPK boardRecommend);
	
	QnaRecommendPK createRecommend(QnaRecommendPK boardRecommend);
	
	void updateStep(QnaItem boardItem);
	
	void updateReplyCount(String id);
	
	void insertTargetGroup(QnaItem boardItem);
	
	List<QnaItem> selectTargetUserList(String id);

	/**
	 * 공지사항 내용 조회 카운트 변경
	 * @param id
	 */
	void updateQnaHitCount(String id);

//	/**
//	 * 공지사항 등록
//	 * @param qnaItem
//	 */
//	void createLinkQna(QnaItem qnaItem);

	/**
	 * 공지사항 정보 조회
	 * @param itemId
	 * @param workspaceId
	 * @return
	 */
//	QnaItem getQna(String itemId, String workspaceId);
	QnaItem getQna(String itemId);
	
	public QnaConfig readQnaconfig();
	
	public void createQnaConfig(QnaConfig qnaConfig);
	
	public void updateQnaConfig(QnaConfig qnaConfig);

	/**
	 * 공지사항 링크 정보 삭제
	 * @param itemId
	 * @param workspaceId
	 */
//	void removeQnaLink(String itemId,String workspaceId);
	void removeQnaLink(String itemId);

	/**
	 * 공지사항 정보 삭제
	 * @param itemId
	 */
	void removeQnaItem(String itemId);

	/**
	 * 하위 팀 Workspace 정보 Count
	 * @param searchCondition
	 * @return
	 */
	Integer countChildWorkspaceBySearchCondition(QnaItemSearchCondition searchCondition);
	/**
	 *  하위 팀 Workspace 정보  목록
	 * @param searchCondition
	 * @return
	 */
	List<QnaItem> listChildWorkspaceBySearchCondition(QnaItemSearchCondition searchCondition);

	/**
	 * 공지사항 공유 링크 정보 삭제
	 * @param qnaItemId
	 */
	void removeQnaShareLink(String qnaItemId);

	/**
	 * 공지사항 조회 Reference 등록
	 * @param userId
	 * @param itemId
	 */
	void createQnaItemReference(String userId,String itemId);

	/**
	 * 조회 Reference 카운트
	 * @param userId
	 * @param itemId
	 * @return
	 */
	int countQnaReference(String userId, String itemId);

	/**
	 * 조회 Reference 삭제
	 * @param itemId
	 */
	void removeQnaReference(String itemId); 
	
	/**
	 * Workspace 메인 포틀릿 
	 * @param map
	 * @return
	 */
	public List<QnaItem> listQnaItemByPortlet(Map<String,String> map);
	/**
	 * Workspace 삭제 배치시 공지사항 첨부 파일 삭제
	 * @param workspaceId
	 * @return
	 */
//	public List<QnaItem> listDeleteQnaByWorkspace(String workspaceId);
	public List<QnaItem> listDeleteQnaByWorkspace();
	
	public QnaItem getTopQnaItem();
}
