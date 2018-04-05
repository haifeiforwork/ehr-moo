package com.lgcns.ikep4.support.user.member.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.restful.model.VcardFolder;

/**
 * 
 * 명함 폴더 Dao
 *
 * @version $Id$
 */
public interface VcardFolderDao extends GenericDao<VcardFolder, String> {
	/**
	 * 사용자 트리 목록
	 */
	public List<VcardFolder> listUserFolderTree(String userId, String portalId);
}