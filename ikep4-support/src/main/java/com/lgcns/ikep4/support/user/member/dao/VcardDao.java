package com.lgcns.ikep4.support.user.member.dao;

import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.user.restful.model.Vcard;
import com.lgcns.ikep4.support.user.restful.model.VcardSearchCondition;

/**
 * 
 * 명함 관리 Dao
 *
 * @version $Id$
 */
public interface VcardDao extends GenericDao<Vcard, String> {
	
	/**
	 * 사용자가 폴더에 등록한 명함 목록(전체)+폴더에 등록되지 않은 명함 목록
	 * @return
	 */
	public List<Vcard> listUserVcardCheckFolder(VcardSearchCondition vcardSearchCondition);
	
}