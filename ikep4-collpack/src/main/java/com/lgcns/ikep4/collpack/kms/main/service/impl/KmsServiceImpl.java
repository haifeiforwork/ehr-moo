package com.lgcns.ikep4.collpack.kms.main.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.collpack.kms.main.dao.KmsDao;
import com.lgcns.ikep4.collpack.kms.main.service.KmsService;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;

@Service("kmsService")
public class KmsServiceImpl extends GenericServiceImpl<Object, String> implements KmsService {

	@Autowired
	public KmsDao kmsDao;
	
	public List<HashMap<String, String>> listKeyword(String userId) {
		return kmsDao.listKeyword(userId);
	}
	
	public List<HashMap<String, String>> listKeywordAll() {
		return kmsDao.listKeywordAll();
	}

	public void insertKeyword(Map<String, String> paramMap) {
		kmsDao.insertKeyword(paramMap);
	}

	public void deleteKeyword(Map<String, String> paramMap) {
		kmsDao.deleteKeyword(paramMap);		
	}

}
