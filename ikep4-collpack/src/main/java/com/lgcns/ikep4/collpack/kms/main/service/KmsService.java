package com.lgcns.ikep4.collpack.kms.main.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.service.GenericService;

public interface KmsService extends GenericService<Object, String> {

	List<HashMap<String, String>> listKeyword(String userId);
	
	List<HashMap<String, String>> listKeywordAll();

	void insertKeyword(Map<String, String> paramMap);

	void deleteKeyword(Map<String, String> paramMap);

}
