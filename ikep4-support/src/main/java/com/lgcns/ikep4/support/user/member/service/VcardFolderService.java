package com.lgcns.ikep4.support.user.member.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.restful.model.VcardFolder;

/**
 * 
 * 명함 폴더 Service
 *
 * @version $Id$
 */
@Transactional
public interface VcardFolderService extends GenericService<VcardFolder, String>{
	
	/**
	 * 사용자 트리 목록
	 */
	public List<VcardFolder> listUserFolderTree(String userId, String portalId);
	
}