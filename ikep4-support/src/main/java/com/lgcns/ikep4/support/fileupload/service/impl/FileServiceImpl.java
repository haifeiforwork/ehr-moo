/*
 * 
 */
package com.lgcns.ikep4.support.fileupload.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.dao.ThumbnailDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;
import com.lgcns.ikep4.support.fileupload.service.FileCheckService;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.file.FileDeletor;
import com.lgcns.ikep4.util.http.HttpUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;

/**
 * 파일업로드 처리 서비스
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileServiceImpl.java 17315 2012-02-08 04:56:13Z yruyo $
 */
@Service
public class FileServiceImpl extends GenericServiceImpl<FileData, String>
		implements FileService {

	public final static int BUFFER_SIZE = 8192;

	/**
	 * 파일 Dao
	 */
	@Autowired
	private FileDao fileDao;

	/**
	 * 파일 링크 Dao
	 */
	@Autowired
	private FileLinkDao fileLinkDao;

	/**
	 * 섬네일 Dao
	 */
	@Autowired
	private ThumbnailDao thumbnailDao;

	/**
	 * 아이디생성 서비스
	 */
	@Autowired
	private IdgenService idgenService;

	/**
	 * 파일권한체크 서비스
	 */
	@Autowired
	private FileCheckService fileCheckService;

	/**
	 * 사용자 정보 서비스
	 */
	@Autowired
	protected UserService userService;

	@Autowired
	protected MessageSource messageSource;

	/**
	 * 파일 저장
	 */
	@Override
	public String create(FileData fileData) {
		return this.fileDao.create(fileData);
	}

	/**
	 * 파일 삭제
	 */
	@Override
	public void delete(String fileId) {
		this.fileDao.remove(fileId);
	}

	/**
	 * 파일 조회
	 */
	@Override
	public FileData read(String fileId) {

		FileData fileData = null;
		try {
			fileData = this.fileDao.get(fileId);

			if (fileData != null) {

				Properties prop = PropertyLoader
						.loadProperties("/configuration/fileupload-conf.properties");

				String uploadRoot = "";
				String uploadRootForFile = prop
						.getProperty("ikep4.support.fileupload.upload_root");
				String uploadRootForImage = prop
						.getProperty("ikep4.support.fileupload.upload_root_image");

				if (checkImageFile(fileData.getFileRealName())) {
					uploadRoot = uploadRootForImage;
				} else {
					uploadRoot = uploadRootForFile;
				}

				fileData.setFilePhysicalPath(uploadRoot
						+ fileData.getFilePath() + File.separatorChar
						+ fileData.getFileName());
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return fileData;
	}

	/**
	 * 파일 업로드
	 */
	public List<FileData> uploadFile(List<MultipartFile> fileList,
			String itemId, String itemTypeCode, String editorAttachYn, User user) {

		List<FileData> uploadList = new ArrayList<FileData>();

		try {

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));

			String editorAttach = editorAttachYn;
			if (StringUtil.isEmpty(editorAttach)) {
				editorAttach = "0";
			}

			if (fileList != null) {
				for (MultipartFile file : fileList) {

					if (!file.isEmpty()) {

						if (checkImageFile(file.getOriginalFilename())) {
							uploadRoot = uploadRootForImage;
							uploadFolder = uploadFolderForImage;
						} else {
							uploadRoot = uploadRootForFile;
							uploadFolder = uploadFolderForFile;
						}

						File folder = new File(uploadRoot + uploadFolder);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						String fileExtension = StringUtil.getFileExtension(file
								.getOriginalFilename());
						File saveFile = new File(folder, UUID.randomUUID()
								.toString().replace("-", "")
								+ "." + fileExtension);

						file.transferTo(saveFile);

						String fileId = StringUtil.replaceQuot(EncryptUtil
								.encryptText(idgenService.getNextId()));

						FileData fileData = new FileData();
						fileData.setFileId(fileId);
						fileData.setFilePath(uploadFolder);
						fileData.setFileRealName(file.getOriginalFilename());
						fileData.setFileSize((int) file.getSize());
						fileData.setFileName(saveFile.getName());
						fileData.setFileContentsType(file.getContentType());
						fileData.setFileExtension(fileExtension);
						fileData.setEditorAttach(Integer.parseInt(editorAttach));
						fileData.setRegisterId(user.getUserId());
						fileData.setRegisterName(user.getUserName());
						fileData.setUpdaterId(user.getUserId());
						fileData.setUpdaterName(user.getUserName());

						this.fileDao.create(fileData);

						uploadList.add(fileData);
					}

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return uploadList;
	}

	/**
	 * 파일 업로드
	 */
	public List<FileData> uploadFile(List<MultipartFile> fileList,
			String itemId, String itemTypeCode, User user) {

		return this.uploadFile(fileList, itemId, itemTypeCode, "0", user);

	}

	public FileData uploadFile(String strFileName, InputStream in) {

		return uploadFile(strFileName, in, "0");
	}

	@SuppressWarnings("unused")
	public FileData uploadFile(String strFileName, InputStream in,
			String editorAttach) {

		List<FileData> uploadList = new ArrayList<FileData>();
		FileData fileData = new FileData();

		try {
			User user = (User) this.getRequestAttribute("ikep.user");

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));
			String downloadUrl = prop
					.getProperty("ikep4.support.fileupload.downloadurl");

			if (checkImageFile(strFileName)) {
				uploadRoot = uploadRootForImage;
				uploadFolder = uploadFolderForImage;
			} else {
				uploadRoot = uploadRootForFile;
				uploadFolder = uploadFolderForFile;
			}

			File folder = new File(uploadRoot + uploadFolder);
			if (!folder.exists()) {
				folder.mkdirs();
			}

			String fileExtension = StringUtil.getFileExtension(strFileName);
			File saveFile = new File(folder, UUID.randomUUID().toString()
					.replace("-", "")
					+ "." + fileExtension);

			int iReadByte;
			FileOutputStream fos = new FileOutputStream(saveFile);
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((iReadByte = in.read(buffer)) != -1) {
				fos.write(buffer, 0, iReadByte);
			}
			fos.flush();
			fos.close();
			fos = null;

			String fileId = StringUtil.replaceQuot(EncryptUtil
					.encryptText(idgenService.getNextId()));

			fileData.setFileId(fileId);
			fileData.setFilePath(uploadFolder);
			fileData.setFileRealName(strFileName);
			fileData.setFileSize((int) saveFile.length());
			fileData.setFileName(saveFile.getName());
			fileData.setFileContentsType(fileExtension);
			fileData.setFileExtension(fileExtension);
			fileData.setEditorAttach(Integer.parseInt(editorAttach));
			fileData.setRegisterId(user.getUserId());
			fileData.setRegisterName(user.getUserName());
			fileData.setUpdaterId(user.getUserId());
			fileData.setUpdaterName(user.getUserName());
			fileData.setDownloadLinkUrl(getRequest().getContextPath()
					+ downloadUrl + fileId);
			this.fileDao.create(fileData);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return fileData;
	}
	
	public FileData uploadFile(String strFileName, InputStream in, User user) {

		List<FileData> uploadList = new ArrayList<FileData>();
		FileData fileData = new FileData();

		try {
			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));
			String downloadUrl = prop
					.getProperty("ikep4.support.fileupload.downloadurl");

			if (checkImageFile(strFileName)) {
				uploadRoot = uploadRootForImage;
				uploadFolder = uploadFolderForImage;
			} else {
				uploadRoot = uploadRootForFile;
				uploadFolder = uploadFolderForFile;
			}

			File folder = new File(StringUtil.replaceBlackChar(uploadRoot + uploadFolder));
			if (!folder.exists()) {
				folder.mkdirs();
			}

			String fileExtension = StringUtil.getFileExtension(strFileName);
			File saveFile = new File(folder, UUID.randomUUID().toString()
					.replace("-", "")
					+ "." + fileExtension);

			int iReadByte;
			FileOutputStream fos = new FileOutputStream(saveFile);
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((iReadByte = in.read(buffer)) != -1) {
				fos.write(buffer, 0, iReadByte);
			}
			fos.flush();
			fos.close();
			fos = null;

			String fileId = StringUtil.replaceQuot(EncryptUtil
					.encryptText(idgenService.getNextId()));

			fileData.setFileId(fileId);
			fileData.setFilePath(uploadFolder);
			fileData.setFileRealName(strFileName);
			fileData.setFileSize((int) saveFile.length());
			fileData.setFileName(saveFile.getName());
			fileData.setFileContentsType(fileExtension);
			fileData.setFileExtension(fileExtension);
			fileData.setEditorAttach(Integer.parseInt("0"));
			fileData.setRegisterId(user.getUserId());
			fileData.setRegisterName(user.getUserName());
			fileData.setUpdaterId(user.getUserId());
			fileData.setUpdaterName(user.getUserName());
			fileData.setDownloadLinkUrl(getRequest().getContextPath()
					+ downloadUrl + fileId);

			this.fileDao.create(fileData);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return fileData;
	}

	public List<FileData> uploadFileForProfile(List<MultipartFile> fileList,
			String userId, String targetId) {

		List<FileData> uploadList = new ArrayList<FileData>();

		try {

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRootForProfileImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_profile_image");
			String uploadFolderForProfileImage = prop
					.getProperty("ikep4.support.fileupload.upload_folder_profile_image");

			if (fileList != null) {
				for (MultipartFile file : fileList) {

					if (!file.isEmpty()) {

						File oldfile = null;
						User user = userService.read(userId);
						if (targetId.equals("picture")) {
							oldfile = new File(uploadRootForProfileImage
									+ user.getPicturePath());
						} else {
							oldfile = new File(uploadRootForProfileImage
									+ user.getProfilePicturePath());
						}

						String userFolder = "";
						for (int i = 0; i < userId.length(); i++) {
							userFolder = userFolder + userId.charAt(i) + "/";
						}

						uploadFolderForProfileImage = uploadFolderForProfileImage
								+ userFolder;

						File folder = new File(uploadRootForProfileImage
								+ uploadFolderForProfileImage);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						String imageSize = "";
						if (targetId.equals("picture")) {
							imageSize = "170";
						} else {
							imageSize = "75";
						}

						String fileExtension = StringUtil.getFileExtension(file
								.getOriginalFilename());
						File saveFile = new File(folder, userId + "_"
								+ imageSize + "_"
								+ UUID.randomUUID().toString().replace("-", "")
								+ "." + fileExtension);

						file.transferTo(saveFile);

						FileData fileData = new FileData();
						fileData.setFilePath(uploadFolderForProfileImage
								+ saveFile.getName());
						fileData.setFileRealName(file.getOriginalFilename());
						fileData.setFileSize((int) file.getSize());
						fileData.setFileName(saveFile.getName());
						fileData.setFileContentsType(file.getContentType());
						fileData.setFileExtension(fileExtension);
						fileData.setRegisterId(userId);
						fileData.setTargetId(targetId);

						uploadList.add(fileData);

						fileDao.updateProfileImage(fileData);

						// 이전 파일 삭제
						if (oldfile.exists()) {
							FileDeletor fileDeletor = new FileDeletor(oldfile);
							fileDeletor.start();
						}
					}

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return uploadList;
	}

	/**
	 * 파일 업로드 (에디터용)
	 */
	public List<FileData> uploadFileForEditor(List<MultipartFile> fileList,
			Map<String, Object> parameterMap, User user) {

		List<FileData> fileDataList = this.uploadFile(fileList, "", "", "1",
				user);

		return fileDataList;
	}

	/**
	 * 파일 다운로드
	 */
	public String downloadFile(String gubun, String fileIdStr,
			String thumbnailYn, String profileYn, HttpServletRequest request,
			HttpServletResponse response) {

		String fileUrl = "";
		String filePath = "";
		String fileName = "";
		String realFileName = "";
		String itemTypeCode = "";
		int editorAttach = 0;

		// 프로파일 이미지 다운로드 여부
		boolean isProfile = false;
		// 섬네일 이미지 다운로드 여부
		boolean isThumbnail = false;
		// 이미지 파일인지 여부
		boolean isImageFile = false;
		// DB에 파일이 있는지 여부
		boolean isDbFile = false;
		// 실제경로에 파일이 있는지 여부
		Boolean isRealFile = false;
		// 파일다운로드 권한이 있는지 여부
		boolean isCheckFile = true;
		// 파일다운로드가 정상적으로 준비 되었는지 여부
		boolean isDownloadFile = false;

		Thumbnail thumbnail = null;
		FileData fileData = null;

		try {

			User user = (User) this.getRequestAttribute("ikep.user");

			String userId = "";
			if (user == null) {
				userId = "";
			} else {
				userId = user.getUserId();
			}

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");
			String uploadRoot = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String downloadHistory = prop
					.getProperty("ikep4.support.fileupload.download.history");
			String imageForwardingYn = prop
					.getProperty("ikep4.support.fileupload.image_forwarding_yn");
			String imageForwardingPath = prop
					.getProperty("ikep4.support.fileupload.image_forwarding_path");

			String fileId = fileIdStr;
			if (StringUtil.isEmpty(fileId)) {
				fileId = "";
			}
			if (!StringUtil.isEmpty(thumbnailYn)
					&& thumbnailYn.equalsIgnoreCase("Y")) {
				isThumbnail = true;
			}
			if (!StringUtil.isEmpty(profileYn)
					&& profileYn.equalsIgnoreCase("Y")) {
				isProfile = true;
			}

			try {
				if (!fileId.equals("")) {
					if (isThumbnail) {

						if (gubun.equals("profile")) {
							thumbnail = this.thumbnailDao
									.selectProfileImage(fileId);
						} else {
							thumbnail = this.thumbnailDao.get(fileId);
						}

						if (thumbnail != null) {
							isDbFile = true;
							filePath = thumbnail.getThumbnailPath();
							fileName = thumbnail.getThumbnailName();
							realFileName = thumbnail.getThumbnailRealName();
							itemTypeCode = thumbnail.getItemTypeCode();
							editorAttach = thumbnail.getEditorAttach();
						}

					} else {

						if (gubun.equals("profile")) {
							fileData = this.fileDao.selectProfileImage(fileId);
						} else {
							fileData = this.fileDao.get(fileId);
						}

						if (fileData != null) {
							isDbFile = true;
							filePath = fileData.getFilePath();
							fileName = fileData.getFileName();
							realFileName = fileData.getFileRealName();
							itemTypeCode = fileData.getItemTypeCode();
							editorAttach = fileData.getEditorAttach();
						}
					}

					// 이미지 파일인지 여부
					isImageFile = checkImageFile(realFileName);
					if (isImageFile) {
						uploadRoot = uploadRootForImage;
					}

					// 다운로드 권한 체크
					try {
						if (!isImageFile) {
							if (fileData.getItemId() != null
									&& fileData.getItemTypeCode() != null) {
								isCheckFile = fileCheckService.checkFile(
										fileId, fileData.getItemId(),
										fileData.getItemTypeCode(), userId);
							}
						}
					} catch (Exception e) {
					}

					// 실제 파일이 있는지 여부
					isRealFile = checkRealFile(uploadRoot, filePath, fileName);

				}
			} catch (Exception e) {
			}

			String convName = "";
			// BufferedInputStream fin = null;
			FileInputStream fin = null;

			// 파일다운로드 권한이 있는 경우
			if (isCheckFile) {

				// 실제 파일이 있을 경우
				if (isRealFile) {

					// imageForwardingYn=Y && 이미지 파일인 경우
					// 다운로드를 웹서버로 포워팅 처리
					if (imageForwardingYn.equals("Y") && isImageFile) {

						fileUrl = HttpUtil.getWebAppUrl(request)
								+ imageForwardingPath + filePath + "/"
								+ fileName;

						// 일반 파일인 경우
						// 다운로드를 서블릿에서 처리
					} else {

						File file = new File(uploadRoot + filePath, fileName);
						String header = request.getHeader("User-Agent");
						if (header.contains("Trident") && !header.contains("MSIE")) {
							convName = HttpUtil.getDisposition(URLEncoder.encode(realFileName,"UTF-8").replaceAll("\\+", "%20"),HttpUtil.getBrowser(request));
						}else{
							convName = HttpUtil.getDisposition(realFileName,HttpUtil.getBrowser(request));
						}
							
						
						// fin = new BufferedInputStream(new
						// FileInputStream(file));
						fin = new FileInputStream(file);

						// 파일 다운로드 로그 처리 이미지는 저장하지 않음
						if (!isImageFile) {
							saveDownLoadHistroy(request, user, editorAttach,
									itemTypeCode, downloadHistory, fileId);
						}

						isDownloadFile = true;
					}
				}
				// 실제 파일이 없을 경우
				else {

					if (isProfile) {

						fileUrl = getTempImage(request, isThumbnail);

					} else {

						// Db에 파일이 있는경우
						if (isDbFile) {
							isDownloadFile = true;
						}
						// DB에 파일이 없는 겨우
						else {
							// DB에 파일이 없음 메시지
							outputMessage(response, messageSource.getMessage(
									"support.fileupload.message.download.nodb",
									null, new Locale(user.getLocaleCode())));
						}
					}

				}

				// 정상 다운로드 인 경우에 다운로드 처리
				if (isDownloadFile) {

					downloadFile(response, convName, fin);

				}

				// 파일 다운로드 권한이 없을 경우
			} else {

				// 다운로드 권한 없음 메시지
				outputMessage(response, messageSource.getMessage(
						"support.fileupload.message.download.noauth", null,
						new Locale(user.getLocaleCode())));

			}

		} catch (Exception e) {
		}

		return fileUrl;
	}

	private boolean checkImageFile(String fileName) {

		Properties prop = PropertyLoader
				.loadProperties("/configuration/fileupload-conf.properties");
		String keywordList = prop
				.getProperty("ikep4.support.fileupload.image_file");

		Pattern p = Pattern.compile(keywordList, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(fileName);

		return m.find();
	}

	/**
	 * 다운로드 히스토리 저장
	 * 
	 * @param request
	 * @param user
	 * @param editorAttach
	 * @param itemTypeCode
	 * @param downloadHistory
	 * @param fileId
	 */
	private void saveDownLoadHistroy(HttpServletRequest request, User user,
			int editorAttach, String itemTypeCode, String downloadHistory,
			String fileId) {

		// 다운로드 히스토리 저장
		if (user != null && editorAttach != 1 && itemTypeCode != null
				&& downloadHistory.indexOf(itemTypeCode) > -1) {

			String portalId = (String) getRequestAttribute("ikep.portalId");

			FileData history = new FileData();
			history.setDownloadHistoryId(idgenService.getNextId());
			history.setFileId(fileId);
			history.setDownloadIp(request.getRemoteAddr());
			history.setRegisterId(user.getUserId());
			history.setPortalId(portalId);
			this.fileDao.createDownloadHistroy(history);
		}

	}

	/**
	 * 실제 파일이 있는지 여부 체크
	 * 
	 * @param uploadRoot
	 * @param filePath
	 * @param fileName
	 * @return
	 */
	private boolean checkRealFile(String uploadRoot, String filePath,
			String fileName) {

		if (!filePath.equals("") && !fileName.equals("")) {
			File file = new File(uploadRoot + filePath, fileName);

			// 실제 파일이 있는지 여부 체크
			if (file.exists()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 임시 이미지 셋팅
	 * 
	 * @param request
	 * @param isThumbnail
	 * @return
	 */
	private String getTempImage(HttpServletRequest request, boolean isThumbnail) {

		String imgUrl = "";
		if (isThumbnail) {
			imgUrl = HttpUtil.getWebAppUrl(request)
					+ "/base/images/common/photo_50x50.gif";
		} else {
			imgUrl = HttpUtil.getWebAppUrl(request)
					+ "/base/images/common/photo_170x170.gif";
		}

		return imgUrl;
	}

	/**
	 * OutputStream 파일 다운로드 쓰기
	 * 
	 * @param response
	 * @param convName
	 * @param fin
	 * @param outs
	 */
	@SuppressWarnings("unused")
	private void downloadFile(HttpServletResponse response, String convName, BufferedInputStream fin) {

		BufferedOutputStream outs = null;

		try {

			outs = new BufferedOutputStream(response.getOutputStream());

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ convName + ";");

			byte b[] = new byte[BUFFER_SIZE];
			int read = 0;

			while ((read = fin.read(b)) != -1) {
				outs.write(b, 0, read);
			}
			outs.close();
			fin.close();

		} catch (IOException ioe) {
			throw new IKEP4ApplicationException("", ioe);
		} finally {
			try {
				if (outs != null) {
					outs.close();
				}
				if (fin != null) {
					fin.close();
				}
			} catch (IOException ioe) {
				throw new IKEP4ApplicationException("", ioe);
			}
		}

	}

	/**
	 * NIO을 이용한 파일 다운로드
	 * 
	 * @param response
	 * @param convName
	 * @param fin
	 * @param outs
	 */
	private void downloadFile(HttpServletResponse response, String convName,
			FileInputStream fin) {
		FileChannel source = null;
		WritableByteChannel destination = null;

		try {

			source = fin.getChannel();
			destination = Channels.newChannel(response.getOutputStream());

			response.setContentType("application/x-msdownload");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ convName + ";");

			source.transferTo(0, fin.available(), destination);

		} catch (IOException ioe) {
			throw new IKEP4ApplicationException("", ioe);
		} finally {
			try {
				if (fin != null) {
					fin.close();
				}
				if (source != null) {
					source.close();
				}
				if (destination != null) {
					destination.close();
				}
			} catch (IOException ioe) {
				throw new IKEP4ApplicationException("", ioe);
			}
		}
	}

	/**
	 * OutputStream 메시지 쓰기
	 * 
	 * @param response
	 * @param message
	 */
	private void outputMessage(HttpServletResponse response, String message) {

		try {

			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();

			out.println("<HTML>");
			out.println("<HEAD><TITLE></TITLE></HEAD>");
			out.println("<BODY>");

			out.println("<script type=\"text/javascript\" language=\"javascript\">");
			out.println("	history.back();");
			out.println("	alert('" + message + "');");
			out.println("</script>");

			out.println("</BODY>");
			out.println("</HTML>");

			out.close();

		} catch (IOException ioe) {
			throw new IKEP4ApplicationException("", ioe);
		}
	}

	/**
	 * 아이템에 연결된 모든 파일 얻기 (FileData의 fileId, fileRealName, fileSize,fileExtension
	 * 값만 리턴
	 */
	public List<FileData> getItemFile(String itemId, String editorYn) {

		List<FileData> returnList = new ArrayList<FileData>();

		try {
			List<FileData> fileList = this.fileDao
					.getItemFile(itemId, editorYn);

			if (fileList != null) {
				for (FileData fileData : fileList) {

					fileData.setFilePath("");
					fileData.setFilePhysicalPath("");
					fileData.setFileContentsType("");
					fileData.setFileName("");
					returnList.add(fileData);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return returnList;

	}
	
	public List<FileData> getItemFileEcm(String itemId, String editorYn) {

		List<FileData> returnList = new ArrayList<FileData>();

		try {
			List<FileData> fileList = this.fileDao.getItemFileEcm(itemId, editorYn);

			if (fileList != null) {
				for (FileData fileData : fileList) {
					returnList.add(fileData);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return returnList;

	}
	
	public List<FileData> getItemFileList(String itemId, String itemType, String editorYn) {

		List<FileData> returnList = new ArrayList<FileData>();

		try {
			//List<FileData> fileList = this.fileDao.getItemFile(itemId, editorYn);
			List<FileData> fileList = this.fileDao.getItemFileList(itemId, itemType, editorYn);
			if (fileList != null) {
				for (FileData fileData : fileList) {

					fileData.setFilePath("");
					fileData.setFilePhysicalPath("");
					fileData.setFileContentsType("");
					fileData.setFileName("");
					returnList.add(fileData);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return returnList;

	}

	/**
	 * 아이템에 연결된 모든 파일 얻기 (FileData의 모든 값을 리턴)
	 */
	public List<FileData> getItemFileAll(String itemId, String editorYn) {

		List<FileData> returnList = new ArrayList<FileData>();

		try {
			List<FileData> fileList = this.fileDao
					.getItemFile(itemId, editorYn);

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");

			if (fileList != null) {
				for (FileData fileData : fileList) {

					if (checkImageFile(fileData.getFileRealName())) {
						uploadRoot = uploadRootForImage;
					} else {
						uploadRoot = uploadRootForFile;
					}

					fileData.setFilePhysicalPath(uploadRoot
							+ fileData.getFilePath() + File.separatorChar
							+ fileData.getFileName());
					returnList.add(fileData);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return returnList;

	}

	/**
	 * 섬네일 변화 배치처리를 위한 목록 조회
	 */
	public List<FileData> getAllForThumbnailBatch() {
		return this.fileDao.getAllForThumbnailBatch();
	}

	/**
	 * 파일 삭제 배치처를 위한 목록 조회
	 */
	public List<FileData> getAllForFileDeleteBatch() {
		return this.fileDao.getAllForFileDeleteBatch();
	}

	/**
	 * 파일 삭제
	 */
	public void removeFile(List<String> fileIdList) {

		try {

			if (fileIdList != null) {
				for (String fileId : fileIdList) {

					this.removeFile(fileId);

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 파일 삭제
	 */
	public void removeFile(String fileId) {

		try {
			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");

			FileData fileData = this.fileDao.get(fileId);

			if (fileData != null) {

				if (checkImageFile(fileData.getFileRealName())) {
					uploadRoot = uploadRootForImage;
				} else {
					uploadRoot = uploadRootForFile;
				}

				File file = new File(uploadRoot + fileData.getFilePath(),
						fileData.getFileName());

				if (file.exists()) {
					FileDeletor fileDeletor = new FileDeletor(file);
					fileDeletor.start();
				}

				this.fileLinkDao.remove(fileId);
				this.fileDao.remove(fileId);

				Thumbnail thumbnail = this.thumbnailDao.get(fileId);

				if (thumbnail != null) {

					File thumnailfile = new File(uploadRoot
							+ thumbnail.getThumbnailPath(),
							thumbnail.getThumbnailName());

					if (thumnailfile.exists()) {
						FileDeletor fileDeletor = new FileDeletor(file);
						fileDeletor.start();
					}

					this.thumbnailDao.remove(fileId);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 아이템에 연결된 파일 삭제
	 */
	public void removeItemFile(String itemId) {

		try {
			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");

			List<FileData> fileList = this.fileDao.getItemFile(itemId, "");

			if (fileList != null) {
				for (FileData fileData : fileList) {

					if (checkImageFile(fileData.getFileRealName())) {
						uploadRoot = uploadRootForImage;
					} else {
						uploadRoot = uploadRootForFile;
					}

					File file = new File(uploadRoot + fileData.getFilePath(),
							fileData.getFileName());

					if (file.exists()) {
						FileDeletor fileDeletor = new FileDeletor(file);
						fileDeletor.start();
					}

					this.fileLinkDao.remove(fileData.getFileId());
					this.fileDao.remove(fileData.getFileId());
				}
			}

			List<Thumbnail> thumnailList = this.thumbnailDao
					.getItemThumbnail(itemId);

			if (thumnailList != null) {
				for (Thumbnail thumbnail : thumnailList) {

					File thumnailfile = new File(uploadRoot
							+ thumbnail.getThumbnailPath(),
							thumbnail.getThumbnailName());

					if (thumnailfile.exists()) {
						FileDeletor fileDeletor = new FileDeletor(thumnailfile);
						fileDeletor.start();
					}

					this.thumbnailDao.remove(thumbnail.getParentFileId());
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 파일업로드 (메일첨부용)
	 */
	public List<FileData> uploadFileForMail(List<MultipartFile> fileList) {

		List<FileData> uploadList = new ArrayList<FileData>();

		try {

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));

			if (fileList != null) {
				for (MultipartFile file : fileList) {

					if (!file.isEmpty()) {

						if (checkImageFile(file.getOriginalFilename())) {
							uploadRoot = uploadRootForImage;
							uploadFolder = uploadFolderForImage;
						} else {
							uploadRoot = uploadRootForFile;
							uploadFolder = uploadFolderForFile;
						}

						File folder = new File(uploadRoot + uploadFolder);
						if (!folder.exists()) {
							folder.mkdirs();
						}

						File saveFile = new File(folder, UUID.randomUUID()
								.toString());

						file.transferTo(saveFile);

						FileData fileData = new FileData();
						fileData.setFilePath(uploadFolder);
						fileData.setFileRealName(file.getOriginalFilename());
						fileData.setFileSize((int) file.getSize());
						fileData.setFileName(saveFile.getName());
						fileData.setFileContentsType(file.getContentType());
						fileData.setFileExtension(StringUtil
								.getFileExtension(fileData.getFileRealName()));

						uploadList.add(fileData);
					}

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return uploadList;
	}

	/**
	 * 파일 링크 저장 (단일파일 업로드용)
	 */
	public void createFileLink(List<String> fileIdList, String itemId,
			String itemTypeCode, User user) {

		try {
			if (fileIdList != null) {
				for (String fileId : fileIdList) {

					String fileLinkId = idgenService.getNextId();

					FileLink fileLink = new FileLink();
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setItemId(itemId);
					fileLink.setFileId(fileId);
					fileLink.setItemTypeCode(itemTypeCode);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());

					this.fileLinkDao.create(fileLink);

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 파일 링크 저장 (단일파일 업로드용)
	 */
	public void createFileLink(String fileId, String itemId,
			String itemTypeCode, User user) {

		try {
			List<String> fileIdList = new ArrayList<String>();
			fileIdList.add(fileId);

			this.createFileLink(fileIdList, itemId, itemTypeCode, user);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 파일 링크 삭제
	 */
	public void removeFileLink(List<String> fileIdList, String itemId,
			String itemTypeCode) {

		try {
			if (fileIdList != null) {
				for (String fileId : fileIdList) {

					this.fileLinkDao.remove(fileId);

				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 파일 링크 삭제
	 */
	public void removeFileLink(String fileId, String itemId, String itemTypeCode) {

		try {
			List<String> fileIdList = new ArrayList<String>();
			fileIdList.add(fileId);

			this.removeFileLink(fileIdList, itemId, itemTypeCode);
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
	}

	/**
	 * 파일 링크 저장 (에디터용)
	 */
	public void saveFileLinkForEditor(List<FileLink> fileLinkList,
			String itemId, String itemTypeCode, User user) {
		List<FileData> savedEditorFileList = this.getItemFile(itemId, "Y");

		List<FileLink> modifiedFileLinkList = new ArrayList<FileLink>();

		FileLink temp = null;

		Boolean added = true;

		try {
			if (fileLinkList == null) {
				return;
				// 저장되어 있는 FileData가 없는 경우
			} else if (savedEditorFileList == null
					|| savedEditorFileList.size() == 0) {
				// 추가된 FileLink에 add 플레그를 넣어준다.
				for (FileLink modified : fileLinkList) {
					modified.setFlag("add");
					modifiedFileLinkList.add(modified);
				}

				// 저장되어 있는 FileData가 있는 경우
			} else {
				// 수정화면에서 온 이미지 파일 VS 저장되어 있는 이미지 파일 비교하여 수정화면에서만 존재하는 이미지 파일은
				// add됬다고 Flag를 넣어 준다.
				for (FileLink modified : fileLinkList) {
					added = true;

					for (FileData saved : savedEditorFileList) {
						if (modified.getFileId().equals(saved.getFileId())) {
							added = false;
							break;
						}
					}
					if (added) {
						modified.setFlag("add");
						modifiedFileLinkList.add(modified);
					}
				}

				Boolean deleted = true;

				// 저장되어 있는 이미지 파일 VS 수정화면에서 온 이미지 파일 비교하여 저장되어 있는 이미지만 존재하는 이미지
				// 파일은
				// add됬다고 Flag를 넣어 준다.
				for (FileData saved : savedEditorFileList) {
					deleted = true;

					for (FileLink modified : fileLinkList) {
						if (modified.getFileId().equals(saved.getFileId())) {
							deleted = false;
							break;
						}
					}
					if (deleted) {
						temp = new FileLink();

						temp.setFileId(saved.getFileId());
						temp.setFlag("del");
						modifiedFileLinkList.add(temp);
					}
				}
			}

			this.saveFileLink(modifiedFileLinkList, itemId, itemTypeCode, user);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 파일 링크 저장 (플레시용)
	 */
	public void saveFileLink(List<FileLink> fileLinkList, String itemId,
			String itemTypeCode, User user) {

		List<String> deleteIdList = new ArrayList<String>();

		try {
			if (fileLinkList != null) {
				for (FileLink fileLink : fileLinkList) {

					// 추가
					if ("add".equals(fileLink.getFlag())) {

						String fileLinkId = idgenService.getNextId();
						fileLink.setFileId(fileLink.getFileId());
						fileLink.setItemId(itemId);
						fileLink.setItemTypeCode(itemTypeCode);
						fileLink.setFileLinkId(fileLinkId);
						fileLink.setRegisterId(user.getUserId());
						fileLink.setRegisterName(user.getUserName());
						fileLink.setUpdaterId(user.getUserId());
						fileLink.setUpdaterName(user.getUserName());

						this.fileLinkDao.create(fileLink);

					}
					// 삭제
					else if ("del".equals(fileLink.getFlag())) {

						deleteIdList.add(fileLink.getFileId());
					}

				}
			}

			if (deleteIdList.size() > 0) {
				this.removeFile(deleteIdList);
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 아이템에 연결된 파일 복사
	 */
	public List<FileData> copyByFileLinkList(List<FileLink> fileLinkList,
			String itemId, String itemTypeCode, User user) {

		List<FileData> uploadList = new ArrayList<FileData>();

		try {

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));

			if (fileLinkList != null) {
				for (FileLink link : fileLinkList) {

					if (StringUtil.isEmpty(link.getFlag())) {
						link.setFlag("");
					}

					// 신규추가
					if (link.getFlag().equals("add")) {

						String fileLinkId = idgenService.getNextId();

						FileLink fileLink = new FileLink();
						fileLink.setFileId(link.getFileId());
						fileLink.setItemId(itemId);
						fileLink.setItemTypeCode(itemTypeCode);
						fileLink.setFileLinkId(fileLinkId);
						fileLink.setRegisterId(user.getUserId());
						fileLink.setRegisterName(user.getUserName());
						fileLink.setUpdaterId(user.getUserId());
						fileLink.setUpdaterName(user.getUserName());

						this.fileLinkDao.create(fileLink);

					}
					// 삭제
					else if (link.getFlag().equals("del")) {
						log.debug("no delete");
						// 아무것도 안함
					}
					// 기존 파일 복사
					else {

						FileData srcData = this.fileDao.get(link.getFileId());

						if (srcData != null) {

							if (checkImageFile(srcData.getFileRealName())) {
								uploadRoot = uploadRootForImage;
								uploadFolder = uploadFolderForImage;
							} else {
								uploadRoot = uploadRootForFile;
								uploadFolder = uploadFolderForFile;
							}

							File folder = new File(uploadRoot + uploadFolder);
							if (!folder.exists()) {
								folder.mkdirs();
							}

							srcData.setFilePhysicalPath(uploadRoot
									+ srcData.getFilePath()
									+ File.separatorChar
									+ srcData.getFileName());

							File srcFile = new File(
									srcData.getFilePhysicalPath());
							File saveFile = new File(folder, UUID.randomUUID()
									.toString());

							try {
								FileUtils.copyFile(srcFile, saveFile);
							} catch (Exception e) {
							}

							String fileId = StringUtil.replaceQuot(EncryptUtil
									.encryptText(idgenService.getNextId()));

							FileData fileData = new FileData();
							fileData.setFileId(fileId);
							fileData.setFilePath(uploadFolder);
							fileData.setFileRealName(srcData.getFileRealName());
							fileData.setFileSize(srcData.getFileSize());
							fileData.setFileName(saveFile.getName());
							fileData.setFileContentsType(srcData
									.getFileContentsType());
							fileData.setFileExtension(srcData
									.getFileExtension());
							fileData.setEditorAttach(0);
							fileData.setRegisterId(user.getUserId());
							fileData.setRegisterName(user.getUserName());
							fileData.setUpdaterId(user.getUserId());
							fileData.setUpdaterName(user.getUserName());

							this.fileDao.create(fileData);

							String fileLinkId = idgenService.getNextId();

							FileLink fileLink = new FileLink();
							fileLink.setFileLinkId(fileLinkId);
							fileLink.setItemId(itemId);
							fileLink.setFileId(fileId);
							fileLink.setItemTypeCode(itemTypeCode);
							fileLink.setRegisterId(user.getUserId());
							fileLink.setRegisterName(user.getUserName());
							fileLink.setUpdaterId(user.getUserId());
							fileLink.setUpdaterName(user.getUserName());

							this.fileLinkDao.create(fileLink);

							uploadList.add(fileData);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return uploadList;
	}

	/**
	 * 아이템에 연결된 파일 복사
	 */
	public List<FileData> copyForTransfer(List<String> fileIdList,
			String itemId, String itemTypeCode, User user) {

		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		List<FileData> fileDataList = null;

		try {
			if (fileIdList != null) {
				for (String fileId : fileIdList) {
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileId);
					fileLinkList.add(fileLink);
				}

				fileDataList = copyByFileLinkList(fileLinkList, itemId,
						itemTypeCode, user);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return fileDataList;

	}

	/**
	 * 아이템에 연결된 파일 복사-첨부파일복사는 없음
	 */
	public List<FileData> copyByFileLinkListVersion(
			List<FileLink> fileLinkList, String itemId, String itemTypeCode,
			User user) {

		List<FileData> uploadList = new ArrayList<FileData>();

		try {
			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));

			if (fileLinkList != null) {
				for (FileLink link : fileLinkList) {

					if (StringUtil.isEmpty(link.getFlag())) {
						link.setFlag("");
					}

					// 신규추가
					if (link.getFlag().equals("add")) {

						String fileLinkId = idgenService.getNextId();

						FileLink fileLink = new FileLink();
						fileLink.setFileId(link.getFileId());
						fileLink.setItemId(itemId);
						fileLink.setItemTypeCode(itemTypeCode);
						fileLink.setFileLinkId(fileLinkId);
						fileLink.setRegisterId(user.getUserId());
						fileLink.setRegisterName(user.getUserName());
						fileLink.setUpdaterId(user.getUserId());
						fileLink.setUpdaterName(user.getUserName());

						this.fileLinkDao.create(fileLink);

					}
					// 삭제
					else if (link.getFlag().equals("del")) {
						// 삭제된 파일을 기본 파일 복사 부분에서는 제외하기
						// delFileList.add(link.getFileId());
						log.debug("no delete");
						// 아무것도 안함
					}
					// 기존 파일 복사
					else {

						FileData srcData = this.fileDao.get(link.getFileId());

						if (srcData != null) {
							// srcData.setFilePhysicalPath(uploadRoot +
							// srcData.getFilePath() + File.separatorChar +
							// srcData.getFileName());

							/**
							 * File srcFile = new
							 * File(srcData.getFilePhysicalPath()); File
							 * saveFile = new File(folder,
							 * UUID.randomUUID().toString());
							 * FileUtils.copyFile(srcFile, saveFile);
							 **/

							if (checkImageFile(srcData.getFileRealName())) {
								uploadRoot = uploadRootForImage;
								uploadFolder = uploadFolderForImage;
							} else {
								uploadRoot = uploadRootForFile;
								uploadFolder = uploadFolderForFile;
							}

							File folder = new File(uploadRoot + uploadFolder);
							if (!folder.exists()) {
								folder.mkdirs();
							}

							String fileId = StringUtil.replaceQuot(EncryptUtil
									.encryptText(idgenService.getNextId()));

							FileData fileData = new FileData();
							fileData.setFileId(fileId);
							fileData.setFilePath(srcData.getFilePath());
							fileData.setFileRealName(srcData.getFileRealName());
							fileData.setFileSize(srcData.getFileSize());
							fileData.setFileName(srcData.getFileName());
							fileData.setFileContentsType(srcData
									.getFileContentsType());
							fileData.setFileExtension(srcData
									.getFileExtension());
							fileData.setEditorAttach(0);
							fileData.setRegisterId(user.getUserId());
							fileData.setRegisterName(user.getUserName());
							fileData.setUpdaterId(user.getUserId());
							fileData.setUpdaterName(user.getUserName());

							this.fileDao.create(fileData);

							String fileLinkId = idgenService.getNextId();

							FileLink fileLink = new FileLink();
							fileLink.setFileLinkId(fileLinkId);
							fileLink.setItemId(itemId);
							fileLink.setFileId(fileId);
							fileLink.setItemTypeCode(itemTypeCode);
							fileLink.setRegisterId(user.getUserId());
							fileLink.setRegisterName(user.getUserName());
							fileLink.setUpdaterId(user.getUserId());
							fileLink.setUpdaterName(user.getUserName());

							this.fileLinkDao.create(fileLink);

							uploadList.add(fileData);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return uploadList;
	}

	/**
	 * 첨부 버전관리용 - 신규 등록,이전 첨부는 복사(파일은 제외)
	 */
	public List<FileData> copyForTransferVersion(List<String> fileIdList,
			String itemId, String itemTypeCode, User user) {

		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		List<FileData> fileDataList = null;

		try {
			if (fileIdList != null) {
				for (String fileId : fileIdList) {
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileId);
					fileLinkList.add(fileLink);
				}

				fileDataList = copyByFileLinkListVersion(fileLinkList, itemId,
						itemTypeCode, user);
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return fileDataList;

	}

	/**
	 * 세션 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private Object getRequestAttribute(String name) {
		return RequestContextHolder.currentRequestAttributes().getAttribute(
				name, RequestAttributes.SCOPE_SESSION);
	}

	/**
	 * Request 정보 얻기
	 * 
	 * @param name
	 * @return
	 */
	private HttpServletRequest getRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return sra.getRequest();
	}

	/**
	 * 파일 저장 경로 얻기
	 * 
	 * @return
	 */
	private String getFilePath(String path) {

		String date = DateUtil.getToday("yyyy-MM-dd");
		String yyyy = date.substring(0, 4);
		String mm = date.substring(5, 7);

		return path + yyyy + "/" + mm + "/" + date;
	}
	
	/**
	 * 아이템에 연결된 파일 복사-에디터 내 파일 복사 및 에디터 내용의 파일 정보 변경 처리
	 */
	public String copyByFileLinkForEditor(String orgFileId, String itemId, String itemTypeCode, User user) {
		String fileId = "";
		String fileLinkId = "";
		try {

			Properties prop = PropertyLoader
					.loadProperties("/configuration/fileupload-conf.properties");

			String uploadRoot = "";
			String uploadFolder = "";
			String uploadRootForFile = prop
					.getProperty("ikep4.support.fileupload.upload_root");
			String uploadRootForImage = prop
					.getProperty("ikep4.support.fileupload.upload_root_image");
			String uploadFolderForFile = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder"));
			String uploadFolderForImage = getFilePath(prop
					.getProperty("ikep4.support.fileupload.upload_folder_image"));

			if (orgFileId != null) {

				FileData srcData = this.fileDao.get(orgFileId);

				if (srcData != null) {

					if (checkImageFile(srcData.getFileRealName())) {
						uploadRoot = uploadRootForImage;
						uploadFolder = uploadFolderForImage;
					} else {
						uploadRoot = uploadRootForFile;
						uploadFolder = uploadFolderForFile;
					}

					File folder = new File(StringUtil.replaceBlackChar(uploadRoot + uploadFolder));
					if (!folder.exists()) {
						folder.mkdirs();
					}

					srcData.setFilePhysicalPath(uploadRoot
							+ srcData.getFilePath()
							+ File.separatorChar
							+ srcData.getFileName());

					File srcFile = new File(
							srcData.getFilePhysicalPath());
					File saveFile = new File(folder, UUID.randomUUID()
							.toString());

					try {
						FileUtils.copyFile(srcFile, saveFile);
					} catch (Exception e) {
					}

					fileId = StringUtil.replaceQuot(EncryptUtil
							.encryptText(idgenService.getNextId()));

					FileData fileData = new FileData();
					fileData.setFileId(fileId);
					fileData.setFilePath(uploadFolder);
					fileData.setFileRealName(srcData.getFileRealName());
					fileData.setFileSize(srcData.getFileSize());
					fileData.setFileName(saveFile.getName());
					fileData.setFileContentsType(srcData
							.getFileContentsType());
					fileData.setFileExtension(srcData
							.getFileExtension());
					fileData.setEditorAttach(1);
					fileData.setRegisterId(user.getUserId());
					fileData.setRegisterName(user.getUserName());
					fileData.setUpdaterId(user.getUserId());
					fileData.setUpdaterName(user.getUserName());

					this.fileDao.create(fileData);

					fileLinkId = idgenService.getNextId();

					FileLink fileLink = new FileLink();
					fileLink.setFileLinkId(fileLinkId);
					fileLink.setItemId(itemId);
					fileLink.setFileId(fileId);
					fileLink.setItemTypeCode(itemTypeCode);
					fileLink.setRegisterId(user.getUserId());
					fileLink.setRegisterName(user.getUserName());
					fileLink.setUpdaterId(user.getUserId());
					fileLink.setUpdaterName(user.getUserName());

					this.fileLinkDao.create(fileLink);
				}
			}
		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}
		return fileId;
	}

	public FileData getFileInfo(String fileId) {
		return fileDao.getFileInfo(fileId);
	}

}
