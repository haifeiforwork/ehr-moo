package com.lgcns.ikep4.support.note.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.note.dao.NoteSharedUserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.note.model.NoteFolder;
import com.lgcns.ikep4.support.note.search.NoteSearchCondition;

@Repository
public class NoteSharedUserDaoImpl extends GenericDaoSqlmap<User,String> implements NoteSharedUserDao {

	private String nameSpace = "support.note.noteSharedUser.";
	/*
	 * (non-Javadoc)
	 * @see com.lgcns.ikep4.framework.core.dao.GenericDao#selectAll(com.lgcns.ikep4.support.addressbook.model.Addrgroup)
	 */
	public List<User> listBySharedUser(NoteSearchCondition noteSearchCondition) {
		return this.sqlSelectForList(nameSpace + "listBySharedUser", noteSearchCondition);
	}
	
	/**
	 * 폴더 공유 정보 생성
	 * @param NoteFolder
	 */
	public void createSharedUser(NoteFolder noteFolder){
		try {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			
			parameterMap.put("folderId", noteFolder.getFolderId());
			parameterMap.put("portalId", noteFolder.getPortalId());
			parameterMap.put("registerId", noteFolder.getRegisterId());
			parameterMap.put("registerName", noteFolder.getRegisterName());
			
			// Batch 처리
			getSqlMapClientTemplate().getSqlMapClient().startBatch();
			for (User user : noteFolder.getSharedUserList()) {
				parameterMap.put("userId", user.getUserId());
				sqlInsert(nameSpace + "createSharedUser", parameterMap);
			}
			getSqlMapClientTemplate().getSqlMapClient().executeBatch();
		} catch (SQLException sqlException) {
			// TODO : Exception 처리 필요
		}
	}

	/**
	 * 폴더 공유 정보 삭제
	 * @param NoteFolder
	 */
	public void physicalDeleteSharedUser(String folderId){
		sqlDelete(nameSpace + "physicalDeleteSharedUser", folderId);
	}
	
	@Deprecated
	public String create(User arg0) {
		return null;
	}

	@Deprecated
	public void remove(String arg0) {
		
	}

	@Deprecated
	public User get(String arg0) {
		return null;
	}

	@Deprecated
	public boolean exists(String arg0) {
		return false;
	}

	@Deprecated
	public void update(User arg0) {
		
	}
}