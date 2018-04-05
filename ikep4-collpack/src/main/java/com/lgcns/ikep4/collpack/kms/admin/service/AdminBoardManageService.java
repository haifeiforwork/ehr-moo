package com.lgcns.ikep4.collpack.kms.admin.service;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.kms.admin.model.Board;
import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;

public interface AdminBoardManageService extends GenericService<Object, String> {

	public List listByBoardRootId(Map<String, String> paramMap);

	public List listChildrenBoard(Map<String, String> paramMap);

	public Board readBoard(Map<String, String> paramMap);

	public void updateBoard(Board board, User user);

	public void updateBoardMove(Board board, Map<String, String> paramMap);

	public List<Board> listParentBoard(Map<String, String> paramMap);

	public String createBoard(Board board, User user);

	public void logicalDeleteBoard(Map<String, String> paramMap);


}
