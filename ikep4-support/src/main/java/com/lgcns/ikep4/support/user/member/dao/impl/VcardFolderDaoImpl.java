package com.lgcns.ikep4.support.user.member.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.dao.VcardFolderDao;
import com.lgcns.ikep4.support.user.restful.model.VcardFolder;

/**
 * 
 * 명함 폴더 Dao 구현 객체
 *
 * @version $Id$
 */
@Repository
public class VcardFolderDaoImpl  extends GenericDaoSqlmap<VcardFolder, String> implements VcardFolderDao {
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.user.member.dao.VcardFolder.";

	/**
	 * 사용자 트리 목록
	 */
	public List<VcardFolder> listUserFolderTree(String userId, String portalId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("portalId", portalId);
		
		List<VcardFolder> list = (List<VcardFolder>) this.sqlSelectForList(NAMESPACE + "listUserFolderTree", map);
		
		return list;
	}

	public String create(VcardFolder arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public VcardFolder get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(VcardFolder arg0) {
		// TODO Auto-generated method stub
		
	}
}