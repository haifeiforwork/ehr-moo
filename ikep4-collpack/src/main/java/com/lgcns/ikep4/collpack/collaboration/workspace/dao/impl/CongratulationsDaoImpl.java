package com.lgcns.ikep4.collpack.collaboration.workspace.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.collaboration.workspace.dao.CongratulationsDao;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Congratulations;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

@Repository("wsCongratulationsDao")
public class CongratulationsDaoImpl extends GenericDaoSqlmap<Congratulations, String> implements CongratulationsDao {

	private static final String NAMESPACE = "collpack.collaboration.workspace.dao.congratulations.";

	/**
	 * 기념일 목록
	 */
	public List<Congratulations> listCongratulations(Map<String, String> param) {
		
		List<Congratulations> list = (List<Congratulations>) this.sqlSelectForList(NAMESPACE + "listCongratulations", param);

		return list;
		
	}
	

	@Deprecated
	public Congratulations get(String id) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public boolean exists(String id) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public String create(Congratulations object) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public void update(Congratulations object) {
		
		throw new UnsupportedOperationException();
		
	}

	@Deprecated
	public void remove(String id) {
		
		throw new UnsupportedOperationException();
		
	}
	
}