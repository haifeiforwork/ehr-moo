package com.lgcns.ikep4.support.user.member.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.user.restful.model.VcardFolder;
import com.lgcns.ikep4.support.user.member.dao.VcardFolderDao;
import com.lgcns.ikep4.support.user.member.service.VcardFolderService;

/**
 * 
 * 명함 폴더 Service 구현 객체
 *
 * @version $Id$
 */
@Service
public class VcardFolderServiceImpl extends GenericServiceImpl<VcardFolder, String> implements VcardFolderService {
	
	@Autowired
	private VcardFolderDao vcardFolderDao;
	
	/**
	 * 사용자 트리 목록
	 */
	public List<VcardFolder> listUserFolderTree(String userId, String portalId) {
		return vcardFolderDao.listUserFolderTree(userId, portalId); 
	}
}