/*
 * 
 */
package com.lgcns.ikep4.support.fileupload.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.model.User;


/**
 * 파일업로드 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileService.java 16258 2011-08-18 05:37:22Z giljae $
 */
@Transactional
public interface FileService extends GenericService<FileData, String> {

	/**
	 * 일반 파일 업로드 처리
	 * 
	 * @param fileList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 * @return @
	 */
	public List<FileData> uploadFile(List<MultipartFile> fileList, String itemId, String itemTypeCode, User user);

	public List<FileData> uploadFile(List<MultipartFile> fileList, String itemId, String itemTypeCode,
			String editorAttach, User user);

	public FileData uploadFile(String strFileName, InputStream in, String editorAttach);

	public FileData uploadFile(String strFileName, InputStream in);
	
	public FileData uploadFile(String strFileName, InputStream in, User user);

	/**
	 * 프로파일 이미지 파일 업로드 처리
	 * 
	 * @param fileList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 * @return @
	 */
	public List<FileData> uploadFileForProfile(List<MultipartFile> fileList, String userId, String targetId);

	/**
	 * 플레시 컨드롤일 경우, 업로드 처리
	 * 
	 * @param fileList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 * @return @
	 */
	public List<FileData> uploadFileForEditor(List<MultipartFile> fileList, Map<String, Object> parameterMap, User user);

	/**
	 * 파일 다운로드 처리
	 * 
	 * @param fileId
	 * @param response @
	 */
	public String downloadFile(String gubun, String fileId, String thumbnailYn, String profileYn,
			HttpServletRequest request, HttpServletResponse response);

	/**
	 * 플레시 컨트롤일 경우, 파일 연결 정보 저장 및 삭제
	 * 
	 * @param user
	 * @param fileList
	 */
	public void saveFileLink(List<FileLink> fileLinkList, String itemId, String itemTypeCode, User user);

	/**
	 * 위지윅 에디터일 경우, 파일 연결 정보 저장 및 삭제
	 * 
	 * @param user
	 * @param 전체 파일 링크 주소
	 * @param 아이템 아이디
	 * @param 아이템 타입코드
	 */
	public void saveFileLinkForEditor(List<FileLink> fileLinkList, String itemId, String itemTypeCode, User user);

	/**
	 * 일반 파일 업로드일 경우, 파일 연결 정보 저장 처리
	 * 
	 * @param fileIdList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 */
	public void createFileLink(List<String> fileIdList, String itemId, String itemTypeCode, User user);

	public void createFileLink(String fileId, String itemId, String itemTypeCode, User user);

	/**
	 * 파일 연결 정보 삭제 처리
	 * 
	 * @param fileIdList
	 * @param itemId
	 * @param itemTypeCode
	 */
	public void removeFileLink(List<String> fileIdList, String itemId, String itemTypeCode);

	public void removeFileLink(String fileId, String itemId, String itemTypeCode);

	/**
	 * ItemId에 연결된 파일 리스트 조회 (FileData의 fileId, fileRealName, fileSize,
	 * fileExtension 값만 리턴)
	 * 
	 * @param itemId
	 * @return @
	 */
	public List<FileData> getItemFile(String itemId, String editorYn);
	
	public List<FileData> getItemFileEcm(String itemId, String editorYn);
	
	public List<FileData> getItemFileList(String itemId, String itemType, String editorYn);

	/**
	 * ItemId에 연결된 파일 리스트 조회(FileData의 모든 값을 리턴)
	 * 
	 * @param itemId
	 * @return @
	 */
	public List<FileData> getItemFileAll(String itemId, String editorYn);

	/**
	 * 썸네일 변환 배치 처리를 위한 리스트 조회
	 * 
	 * @return @
	 */
	public List<FileData> getAllForThumbnailBatch();

	/**
	 * 파일 삭제 배치 처리를 위한 리스트 조회
	 * 
	 * @return @
	 */
	public List<FileData> getAllForFileDeleteBatch();

	/**
	 * 파일 삭제
	 * 
	 * @param fileId @
	 */
	public void removeFile(List<String> fileIdList);

	public void removeFile(String fileId);

	/**
	 * ItemId에 연결된 파일 삭제
	 * 
	 * @param itemId @
	 */
	public void removeItemFile(String itemId);

	/**
	 * 이메일 전송일 경우 첨부파일 업로드
	 * 
	 * @param fileList
	 * @return @
	 */
	public List<FileData> uploadFileForMail(List<MultipartFile> fileList);

	/**
	 * 파일 전달 일경우, 파일 추가/삭제/복사
	 * 
	 * @param fileLinkList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 * @return
	 */
	public List<FileData> copyByFileLinkList(List<FileLink> fileLinkList, String itemId, String itemTypeCode, User user);

	public List<FileData> copyForTransfer(List<String> fileIdList, String itemId, String itemTypeCode, User user);

	/**
	 * 파일 추가/삭제/복사 - 원본 파일 복사는 없이 DB 첨부 부분만 복사함
	 * 
	 * @param fileLinkList
	 * @param itemId
	 * @param itemTypeCode
	 * @param user
	 * @return
	 */
	public List<FileData> copyByFileLinkListVersion(List<FileLink> fileLinkList, String itemId, String itemTypeCode,
			User user);

	public List<FileData> copyForTransferVersion(List<String> fileIdList, String itemId, String itemTypeCode, User user);
	
	/**
	 * 아이템에 연결된 파일 복사-에디터 내 파일 복사 및 에디터 내용의 파일 정보 변경 처리
	 */
	public String copyByFileLinkForEditor(String orgFileId, String itemId, String itemTypeCode, User user);
	
	public FileData getFileInfo(String fileId);

}
