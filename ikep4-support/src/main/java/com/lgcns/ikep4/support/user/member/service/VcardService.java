package com.lgcns.ikep4.support.user.member.service;

import java.util.List;


import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.user.restful.model.Vcard;
import com.lgcns.ikep4.support.user.restful.model.VcardSearchCondition;

/**
 * 
 * 명함관리 Service
 *
 * @version $Id$
 */
@Transactional
public interface VcardService extends GenericService<Vcard, String>{
	
	/**
	 * 사용자가 폴더에 등록한 명함 목록(전체)+폴더에 등록되지 않은 명함 목록
	 * @return
	 */
	public List<Vcard> listUserVcardCheckFolder(VcardSearchCondition vcardSearchCondition);
}