package com.lgcns.ikep4.util.idgen.service;

import com.lgcns.ikep4.framework.core.service.GenericService;


/**
 * ID 생성 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: IdgenService.java 16247 2011-08-18 04:54:29Z giljae $
 */
public interface IdgenService extends GenericService<String, String> {

	/**
	 * ID값 얻어 오기
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getNextId() ;

}
