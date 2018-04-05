package com.lgcns.ikep4.support.user.member.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.user.member.dao.VcardDao;
import com.lgcns.ikep4.support.user.restful.model.Vcard;
import com.lgcns.ikep4.support.user.restful.model.VcardSearchCondition;

/**
 * 
 * 명함관리 Dao 구현객체
 *
 * @version $Id$
 */
@Repository
public class VcardDaoImpl  extends GenericDaoSqlmap<Vcard, String> implements VcardDao {
	/** The Constant NAMESPACE. */
	private static final String NAMESPACE = "support.user.member.dao.Vcard.";

	/**
	 * 사용자가 폴더에 등록한 명함 목록(전체)+폴더에 등록되지 않은 명함 목록
	 * @return
	 */
	public List<Vcard> listUserVcardCheckFolder(VcardSearchCondition vcardSearchCondition) {
		return (List<Vcard>) sqlSelectForList(NAMESPACE+"listUserVcardCheckFolder", vcardSearchCondition);
	}

	public String create(Vcard arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean exists(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	public Vcard get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void update(Vcard arg0) {
		// TODO Auto-generated method stub
		
	}
	
}