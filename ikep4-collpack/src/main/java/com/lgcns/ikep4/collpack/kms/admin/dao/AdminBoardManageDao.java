package com.lgcns.ikep4.collpack.kms.admin.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface AdminBoardManageDao extends GenericDao<Object, String> {

	public List listByBoardRootId(Map<String, String> paramMap);

	public List listChildrenBoard(Map<String, String> boardMap);

	public Board get(Map<String, String> paramMap);

	public List getParents(Map<String, String> paramMap);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 증가시킨다.
	 *
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderIncrease(Board board);

	/**
	 * 파라미터로 들어온 게시판보다 정렬값이 큰 게시판을 대상으로 현재 정렬값을 1씩 감소시킨다.
	 *
	 * @param board 정렬 대상 게시판 모델 객체
	 */
	public void updateSortOderDecrease(Board board);

	public void updateMove(Board after);

	public void logicalDelete(Map<String, String> paramMap);

}
