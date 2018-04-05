package com.lgcns.ikep4.support.note.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;

public interface NoteSharedUserDao extends GenericDao<User, String> {

	public List<User> listBySharedUser(NoteSearchCondition noteSearchCondition);

	/**
	 * 폴더 공유 생성
	 * @param NoteFolder
	 */
	public void createSharedUser(NoteFolder noteFolder);

	/**
	 * 폴더 공유 삭제
	 * @param NoteFolder
	 */
	public void physicalDeleteSharedUser(String folderId);
}