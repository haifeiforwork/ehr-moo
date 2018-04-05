package com.lgcns.ikep4.support.fileupload.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.fileupload.dao.ThumbnailDao;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;


/**
 * 섬네일 저장 처리 DAO
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailDaoImpl.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Repository
public class ThumbnailDaoImpl extends GenericDaoSqlmap<Thumbnail, String> implements ThumbnailDao {

	/**
	 * 섬네일 쿼리 네임스페이스
	 */
	private static final String NAMESPACE = "support.fileupload.thumbnail.";

	/**
	 * 섬네일 생성
	 */
	public String create(Thumbnail thumbnail) {
		return (String) sqlInsert(NAMESPACE + "insert", thumbnail);

	}

	/**
	 * 섬네일 삭제
	 */
	public void remove(String fileId) {
		sqlDelete(NAMESPACE + "delete", fileId);
	}

	/**
	 * 섬네일 얻기
	 */
	public Thumbnail get(String fileId) {
		return (Thumbnail) sqlSelectForObject(NAMESPACE + "select", fileId);
	}

	/**
	 * 섬네일 프로파일 이미지 조회
	 */
	public Thumbnail selectProfileImage(String userId) {
		return (Thumbnail) sqlSelectForObject(NAMESPACE + "selectProfileImage", userId);
	}

	public boolean exists(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void update(Thumbnail object) {
		// TODO Auto-generated method stub

	}

	public List<Thumbnail> getItemThumbnail(String itemId) {
		return (List<Thumbnail>) sqlSelectForList(NAMESPACE + "selectItemThumbnail", itemId);
	}

}
