package com.lgcns.ikep4.support.fileupload.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.ThumbnailDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;
import com.lgcns.ikep4.support.fileupload.service.ThumbnailService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.idgen.dao.IdgenDao;


/**
 * 섬네일 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: ThumbnailServiceImpl.java 16273 2011-08-18 07:07:47Z giljae $
 */
@Service
public class ThumbnailServiceImpl extends GenericServiceImpl<Thumbnail, String> implements ThumbnailService {
	
	/**
	 * 파일 Dao
	 */
	@Autowired
	private FileDao fileDao;
	
	/**
	 * 섬네일 Dao
	 */
	@Autowired
	private ThumbnailDao thumbnailDao;
	
	/**
	 * 아이디 생성 Dao
	 */
	@Autowired
	private IdgenDao idgenDao;
	
	/**
	 * 섬네일 등록
	 */
	public String create(Thumbnail thumbnail) {
		return thumbnailDao.create(thumbnail);
	}
	
	/**
	 * 섬네일 삭제
	 */
	public void delete(String fileId) {
		thumbnailDao.remove(fileId);
	}
	
	/**
	 * 섬네일 조회
	 */
	public Thumbnail read(String fileId) {
		return (Thumbnail) thumbnailDao.get(fileId);
	}
	
	/**
	 * 섬네일  조회
	 */
	public Thumbnail getThumbnail(String fileId) {

		Thumbnail thumbnail = thumbnailDao.get(fileId);
		try {
			if (thumbnail == null) {
				Thumbnail thumbnailTmp = new Thumbnail();

				FileData fileData = fileDao.get(fileId);
				thumbnailTmp.setThumbnailId(fileData.getFileId());
				thumbnailTmp.setThumbnailContentsType(fileData.getFileContentsType());
				thumbnailTmp.setThumbnailExtension(fileData.getFileExtension());
				thumbnailTmp.setThumbnailFileSize(fileData.getFileSize());
				thumbnailTmp.setThumbnailName(fileData.getFileName());
				thumbnailTmp.setThumbnailPath(fileData.getFilePath());
				thumbnailTmp.setThumbnailRealName(fileData.getFileRealName());

				return thumbnailTmp;
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return thumbnail;
	}
	
	/**
	 * 아이템에 연결된 섬네일 조회
	 */
	public List<Thumbnail> getItemThumbnail(String itemId, String editorYn) {

		List<Thumbnail> thumbnailList = thumbnailDao.getItemThumbnail(itemId);
		try {
			if (thumbnailList == null) {
				List<Thumbnail> thumbnailListTmp = new ArrayList<Thumbnail>();
				List<FileData> fileList = fileDao.getItemFile(itemId, editorYn);

				for (FileData fileData : fileList) {
					Thumbnail thumbnailTmp = new Thumbnail();
					thumbnailTmp.setThumbnailId(fileData.getFileId());
					thumbnailTmp.setThumbnailContentsType(fileData.getFileContentsType());
					thumbnailTmp.setThumbnailExtension(fileData.getFileExtension());
					thumbnailTmp.setThumbnailFileSize(fileData.getFileSize());
					thumbnailTmp.setThumbnailName(fileData.getFileName());
					thumbnailTmp.setThumbnailPath(fileData.getFilePath());
					thumbnailTmp.setThumbnailRealName(fileData.getFileRealName());

					thumbnailListTmp.add(thumbnailTmp);
				}
				return thumbnailListTmp;
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return thumbnailList;
	}
	
	/**
	 * 섬네일 등록
	 */
	public String createThumbnail(FileData fileData, File file, User user) {

		String thumbnailId = idgenDao.getNextId();

		Thumbnail thumbnail = new Thumbnail();
		thumbnail.setThumbnailId(thumbnailId);
		thumbnail.setParentFileId(fileData.getFileId());
		thumbnail.setThumbnailPath(fileData.getFilePath());
		thumbnail.setThumbnailRealName(fileData.getFileRealName());
		thumbnail.setThumbnailFileSize((int) file.length());
		thumbnail.setThumbnailName(file.getName());
		thumbnail.setThumbnailContentsType(fileData.getFileContentsType());
		thumbnail.setThumbnailExtension(fileData.getFileExtension());
		thumbnail.setRegisterId(user.getUserId());
		thumbnail.setRegisterName(user.getUserName());
		thumbnail.setUpdaterId(user.getUserId());
		thumbnail.setUpdaterName(user.getUserName());

		return thumbnailDao.create(thumbnail);

	}
	
	/**
	 * 섬네일 등록(배치작업용)
	 */
	public String createThumbnailForBatch(FileData fileData, File file) {

		String thumbnailId = idgenDao.getNextId();

		Thumbnail thumbnail = new Thumbnail();
		thumbnail.setThumbnailId(thumbnailId);
		thumbnail.setParentFileId(fileData.getFileId());
		thumbnail.setThumbnailPath(fileData.getFilePath());
		thumbnail.setThumbnailRealName(fileData.getFileRealName());
		thumbnail.setThumbnailFileSize((int) file.length());
		thumbnail.setThumbnailName(file.getName());
		thumbnail.setThumbnailContentsType(fileData.getFileContentsType());
		thumbnail.setThumbnailExtension(fileData.getFileExtension());
		thumbnail.setRegisterId("system");
		thumbnail.setRegisterName("system");
		thumbnail.setUpdaterId("system");
		thumbnail.setUpdaterName("system");

		return thumbnailDao.create(thumbnail);

	}

}
