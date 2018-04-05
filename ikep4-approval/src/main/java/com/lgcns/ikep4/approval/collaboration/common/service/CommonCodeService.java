package com.lgcns.ikep4.approval.collaboration.common.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.collaboration.common.model.CommonCode;


/**
 * 공통코드 SERVICE
 * 
 * @author pjh
 * @version $Id$
 */
@Transactional
public interface CommonCodeService {
	
	/**
	 * 공통코드 목록 조회
	 * @param grpCd
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList( String grpCd) throws Exception;
	
	/**
	 * 공통코드 목록 조회
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList( Map<String, String> paramMap) throws Exception;
}
