package com.lgcns.ikep4.util.idgen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.util.idgen.dao.IdgenDao;
import com.lgcns.ikep4.util.idgen.service.IdgenService;


/**
 * ID 생성 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IdgenServiceImpl.java 16247 2011-08-18 04:54:29Z giljae $
 */
@Service("idgenService")
@Transactional(propagation=Propagation.NOT_SUPPORTED)
public class IdgenServiceImpl extends GenericServiceImpl<String, String> implements IdgenService {

	/**
	 * 아이디생성 Dao
	 */
	@Autowired
	private IdgenDao idgenDao;

	public String getNextId() {
		return idgenDao.getNextId();
	}

}
