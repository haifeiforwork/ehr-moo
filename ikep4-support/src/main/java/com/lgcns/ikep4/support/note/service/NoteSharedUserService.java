package com.lgcns.ikep4.support.note.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;

@Transactional
public interface NoteSharedUserService extends GenericService<User, String> {

	/**
	 * 폴더 공유 목록
	 * @param NoteSearchCondition 모델
	 */
	public List<User> listBySharedUser(NoteSearchCondition noteSearchCondition);

	/**
	 * 폴더 공유 정보 생성
	 * @param NoteFolder 모델
	 */
	public void createSharedUser(NoteFolder noteFolder);
	
	/**
	 * 폴더 공유 정보 삭제
	 * @param NoteFolder 모델
	 */
	public void physicalDeleteSharedUser(String folderId);
	
}
