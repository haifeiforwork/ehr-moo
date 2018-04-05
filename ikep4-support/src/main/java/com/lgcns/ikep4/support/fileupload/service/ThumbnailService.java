package com.lgcns.ikep4.support.fileupload.service;

import java.io.File;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 섬네일 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface ThumbnailService extends GenericService<Thumbnail, String> {

	/**
	 * 썸네일 조회
	 * 
	 * @param fileId
	 * @return @
	 */
	public Thumbnail getThumbnail(String fileId);

	/**
	 * ItemId에 연결된 썸네일 리스트 조회
	 * 
	 * @param itemId
	 * @return @
	 */
	public List<Thumbnail> getItemThumbnail(String itemId, String editorYn);

	/**
	 * 썸네일 파일 추가
	 * 
	 * @param fileData
	 * @param file
	 * @return @
	 */
	public String createThumbnail(FileData fileData, File file, User user);

	/**
	 * 썸네일 파일 추가 - 배치작업용
	 * 
	 * @param fileData
	 * @param file
	 * @return
	 */
	public String createThumbnailForBatch(FileData fileData, File file);

}
