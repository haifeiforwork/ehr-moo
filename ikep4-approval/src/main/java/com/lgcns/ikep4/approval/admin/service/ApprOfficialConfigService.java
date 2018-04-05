package com.lgcns.ikep4.approval.admin.service;

import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.approval.admin.model.ApprOfficialConfig;
import com.lgcns.ikep4.framework.core.service.GenericService;


/**
 * 공문 설정 서비스
 * 
 * @author 유승목
 * @version $Id$
 */
@Transactional
public interface ApprOfficialConfigService extends GenericService<ApprOfficialConfig, String> {

	/**
	 * 이미지 삭제
	 * 
	 * @param map
	 */
	void deleteImgFile(Map map);

}
