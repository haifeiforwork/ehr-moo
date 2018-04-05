package com.lgcns.ikep4.collpack.collaboration.workspace.dao;

import java.util.List;
import java.util.Map;

import com.lgcns.ikep4.collpack.collaboration.workspace.model.Congratulations;
import com.lgcns.ikep4.framework.core.dao.GenericDao;

public interface CongratulationsDao extends GenericDao<Congratulations, String> {

	/**
	 * 기념일 목록 조회
	 * @param map
	 * @return
	 */
	public List<Congratulations> listCongratulations(Map<String, String> map);

}