package com.lgcns.ikep4.support.note.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.note.dao.NoteSharedUserDao;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.service.NoteFolderService;
import com.lgcns.ikep4.support.note.service.NoteSharedUserService;
import com.lgcns.ikep4.support.security.acl.model.ACLGroupPermission;
import com.lgcns.ikep4.support.security.acl.model.ACLResourcePermission;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.group.model.Group;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;

@Service
public class NoteSharedUserServiceImpl extends GenericServiceImpl<User, String> implements NoteSharedUserService {

	@Autowired
	private NoteSharedUserDao noteSharedUserDao;

	public List<User> listBySharedUser(NoteSearchCondition noteSearchCondition) {

		List<User> sharedUserList = this.noteSharedUserDao.listBySharedUser(noteSearchCondition);

		return sharedUserList;
	}

	/**
	 * 폴더 공유 정보 수정
	 * @param NoteFolder 모델
	 */
	public void createSharedUser(NoteFolder noteFolder) { 
		
		// 공유 정보 생성
		noteSharedUserDao.createSharedUser(noteFolder);
	}
	
	/**
	 * 폴더 공유 정보 삭제
	 * @param NoteFolder 모델
	 */
	public void physicalDeleteSharedUser(String folderId) { 

		// 공유 정보 삭제
		noteSharedUserDao.physicalDeleteSharedUser(folderId);
	}
	
}