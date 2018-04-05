/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.collpack.qna.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.collpack.qna.dao.QnaDao;
import com.lgcns.ikep4.collpack.qna.model.Qna;
import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;

/**
 * TODO Javadoc주석작성
 *
 * @author 이동희 (loverfairy@gmail.com)
 * @version $Id: QnaDaoImpl.java 16295 2011-08-19 07:49:49Z giljae $
 */
@Repository("qnaDao")
public class QnaDaoImpl extends GenericDaoSqlmap<Qna, String> implements QnaDao {
	
	
	public List<Qna> listAll(Qna qnaSearch) {
		return sqlSelectForList("collpack.qna.dao.Qna.selectAll", qnaSearch);
	}
	
	
	
	public List<Qna> listRelation(Qna qnaSearch) {
		return sqlSelectForList("collpack.qna.dao.Qna.selectListRelation", qnaSearch);
	}



	public int selectCount(Qna qnaSearch) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.selectCount", qnaSearch);
	}
	
	public int selectCountRelation(Qna qnaSearch) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.selectCountRelation", qnaSearch);
	}
	
	
	
	
	public List<String> listChildId(String qnaId) {
		return (List)sqlSelectForListOfObject("collpack.qna.dao.Qna.listChildId", qnaId);
	}



	public List<Qna> selectByGroup(String qnaGroupId) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("qnaGroupId", qnaGroupId);
		
		return sqlSelectForList("collpack.qna.dao.Qna.selectByGroup", map);
	}

	public List<Qna> selectByGroup(String qnaGroupId, String line) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("qnaGroupId", qnaGroupId);
		map.put("line", line);
		return sqlSelectForList("collpack.qna.dao.Qna.selectByGroup", map);
	}
	
	
	public int selectGroupAdoptStatus(String qnaId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.selectGroupAdoptStatus", qnaId);
	}
	
	
	public String create(Qna qna) {
		return (String) sqlInsert("collpack.qna.dao.Qna.insert", qna);
	}

	public boolean exists(String qnaId) {
		return false;
	}

	public Qna get(String id) {
		return (Qna) sqlSelectForObject("collpack.qna.dao.Qna.select", id);
	}
	

	
	
	public double getSumAnswerNecessaryTime(String portalId) {
		return (Double)sqlSelectForObject("collpack.qna.dao.Qna.getSumAnswerNecessaryTime", portalId);
	}



	public int getCountAnswerNecessaryTime(String portalId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.getCountAnswerNecessaryTime", portalId);
	}



	public int getCountQna(String portalId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.getCountQna", portalId);
	}



	public int getCountQnaHasAnswer(String portalId) {
		return (Integer)sqlSelectForObject("collpack.qna.dao.Qna.getCountQnaHasAnswer", portalId);
	}



	public void update(Qna qna) {
		sqlUpdate("collpack.qna.dao.Qna.update", qna);
	}
	
	public void updateHit(String seqId) {
		sqlUpdate("collpack.qna.dao.Qna.updateHit", seqId);
	}
	

	public void updateRecommendCount(String qnaId) {
		
		sqlUpdate("collpack.qna.dao.Qna.updateRecommendCount", qnaId);
		
	}

	public void updateReplyCount(String qnaId) {
		
		sqlUpdate("collpack.qna.dao.Qna.updateReplyCount", qnaId);
		
	}

	public void updateLinereplyCount(String qnaId) {
		
		sqlUpdate("collpack.qna.dao.Qna.updateLinereplyCount", qnaId);
		
	}

	public void updateAttachFileCount(String qnaId, int attachFileCount) {
		
		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setAttachFileCount(attachFileCount);
		
		sqlUpdate("collpack.qna.dao.Qna.updateAttachFileCount", qna);
		
	}

	public void updateAnswerAdopt(String qnaId, int answerAdopt) {
		
		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setAnswerAdopt(answerAdopt);
		
		sqlUpdate("collpack.qna.dao.Qna.updateAnswerAdopt", qna);
		
	}

	public void updateUrgent(String qnaId, int urgent) {
		
		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setUrgent(urgent);
		
		sqlUpdate("collpack.qna.dao.Qna.updateUrgent", qna);
		
	}

	public void updateItemDelete(String qnaId, int itemDelete) {
		
		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setItemDelete(itemDelete);
		
		sqlUpdate("collpack.qna.dao.Qna.updateItemDelete", qna);
		
	}

	public void updateStatus(String qnaGroupId, String status) {

		Qna qna = new Qna();
		
		qna.setQnaId(qnaGroupId);
		qna.setStatus(status);
		
		sqlUpdate("collpack.qna.dao.Qna.updateStatus", qna);
		
	}

	public void updateFavoriteCount(String qnaId, int favoriteCount) {
		
		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setFavoriteCount(favoriteCount);
		
		sqlUpdate("collpack.qna.dao.Qna.updateFavoriteCount", qna);
		
	}


	public void updateAnswerNecessaryTime(String qnaId, double answerNecessaryTime) {

		Qna qna = new Qna();
		
		qna.setQnaId(qnaId);
		qna.setAnswerNecessaryTime(answerNecessaryTime);
		
		sqlUpdate("collpack.qna.dao.Qna.updateAnswerNecessaryTime", qna);
		
	}
	
	public void updateRecommendCountSum(String qnaId) {

		sqlUpdate("collpack.qna.dao.Qna.updateRecommendCountSum", qnaId);
		
	}
	

	public void updateGroupHitCount(String qnaGroupId) {
		sqlUpdate("collpack.qna.dao.Qna.updateGroupHitCount", qnaGroupId);
	}

	
	public void updateGroupFavoriteCount(String qnaGroupId, int favoriteCount) {
		
		Qna qna = new Qna();
		
		qna.setQnaGroupId(qnaGroupId);
		qna.setFavoriteCount(favoriteCount);
		
		sqlUpdate("collpack.qna.dao.Qna.updateGroupFavoriteCount", qna);
	}
	
	public void updateCategoryId(String beforeCategoryId, String afterCategoryId) {
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("beforeCategoryId", beforeCategoryId);
		map.put("afterCategoryId", afterCategoryId);
		
		sqlUpdate("collpack.qna.dao.Qna.updateCategoryId", map);
	}
	
	
	

	public void updateMailCount(String qnaId) {
		sqlUpdate("collpack.qna.dao.Qna.updateMailCount", qnaId);
	}



	public void updateMblogCount(String qnaId) {
		sqlUpdate("collpack.qna.dao.Qna.updateMblogCount", qnaId);
	}



	public void remove(String qnaId) {
		sqlDelete("collpack.qna.dao.Qna.delete", qnaId);
	}
	


	public void updateScore(String qnaId, int score) {
		
		Qna qna = new Qna();
		qna.setQnaId(qnaId);
		qna.setScore(score);
		
		sqlUpdate("collpack.qna.dao.Qna.updateScore", qna);
	}
	
	public void updateBestFlag(int qnaType, int score, String portalId) {
		
		Qna qna = new Qna();
		qna.setQnaType(qnaType);
		qna.setScore(score);
		qna.setPortalId(portalId);
		
		sqlUpdate("collpack.qna.dao.Qna.updateBestFlag", qna);
	}
	
	public List<String> selectBestQnaId(int qnaType, String portalId, String newDate, int endRowIndex) {
		
		Qna qna = new Qna();
		qna.setQnaType(qnaType);
		qna.setPortalId(portalId);
		qna.setNewDate(newDate);
		qna.setEndRowIndex(endRowIndex);
		
		return (List)sqlSelectForListOfObject("collpack.qna.dao.Qna.selectBestQnaId", qna);
	}
	
	public void updateScoreInit() {
		sqlUpdate("collpack.qna.dao.Qna.updateScoreInit");
	}
	
}
