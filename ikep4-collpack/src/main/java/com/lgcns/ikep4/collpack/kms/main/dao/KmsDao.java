package com.lgcns.ikep4.collpack.kms.main.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface KmsDao extends GenericDao<Object, String> {

	List<HashMap<String, String>> listKeyword(String userId);
	
	List<HashMap<String, String>> listKeywordAll();

	void insertKeyword(Map<String, String> paramMap);

	void deleteKeyword(Map<String, String> paramMap);

}
