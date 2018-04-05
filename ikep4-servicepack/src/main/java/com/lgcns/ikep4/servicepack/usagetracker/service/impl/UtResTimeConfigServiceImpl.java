package com.lgcns.ikep4.servicepack.usagetracker.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.servicepack.usagetracker.dao.UtResTimeConfigDao;
import com.lgcns.ikep4.servicepack.usagetracker.model.UtResTimeConfig;
import com.lgcns.ikep4.servicepack.usagetracker.service.UtResTimeConfigService;

@Service
@Transactional
public class UtResTimeConfigServiceImpl extends GenericServiceImpl<UtResTimeConfig, String> implements UtResTimeConfigService {

	@Autowired
	private UtResTimeConfigDao utResTimeConfigDao;

	/**
	 * 시간 설정 수정
	 * @param utResTimeConfig
	 */
	public void updateResTimeConfig(UtResTimeConfig utResTimeConfig) {
		
		// 설정 정보 있는지 체크
		if(utResTimeConfigDao.existsConfig(utResTimeConfig.getPortalId())) {
			//있으면 업데이트
			utResTimeConfigDao.update(utResTimeConfig);
		} else {
			//없으면 등록
			utResTimeConfigDao.create(utResTimeConfig);
		}
	}
	
	/**
	 * 상세
	 */
	@Override
	public UtResTimeConfig read(String id) {
		return utResTimeConfigDao.get(id);
	}
}