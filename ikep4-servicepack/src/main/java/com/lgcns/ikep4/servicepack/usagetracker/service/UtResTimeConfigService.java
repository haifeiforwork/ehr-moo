package com.lgcns.ikep4.servicepack.usagetracker.service;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeConfig;

@Transactional
public interface UtResTimeConfigService extends GenericService<UtResTimeConfig,String> {
	
	/**
	 * 시간 설정 수정
	 * @param utResTimeConfig
	 */
	public void updateResTimeConfig(UtResTimeConfig utResTimeConfig);
	
}