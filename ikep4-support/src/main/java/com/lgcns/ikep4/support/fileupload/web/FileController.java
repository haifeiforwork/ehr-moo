package com.lgcns.ikep4.support.fileupload.web;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.framework.common.exception.IKEP4AjaxException;
import com.lgcns.ikep4.framework.common.exception.IKEP4ApplicationException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor;
import com.lgcns.ikep4.support.fileupload.interceptor.FileUploadInterceptor.UPLOAD_ERROR_MESSAGE;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.Thumbnail;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.fileupload.service.ThumbnailService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.excel.ExcelUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.pdf.MhtUtil;
import com.lgcns.ikep4.util.pdf.PdfUtil;


/**
 * 파일업로드 다운로드 처리 Controller
 * 
 * @author 유승목(handul32@hanmail.net)
 * @version $Id: FileController.java 18531 2012-05-04 04:06:25Z yruyo $
 */
@Controller
@RequestMapping(value = "/support/fileupload")
public class FileController extends BaseController {

	/**
	 * 파일 서비스
	 */
	@Autowired
	private FileService fileService;

	/**
	 * 사용자 서비스
	 */
	@Autowired
	private UserService userService;

	/**
	 * 섬네일 서비스
	 */
	@Autowired
	private ThumbnailService thumbnailService;

	/**
	 * 파일 업로드 인테셉터
	 */
	@Autowired
	private List<FileUploadInterceptor> fileUploadInterceptorList;
	
	
	@Autowired
	private IdgenService idgenService;

	/**
	 * 단일 파일 업로드 화면
	 * 
	 * @param targetId
	 * @param editorAttachYn
	 * @param imageYn
	 * @return
	 */
	@RequestMapping("/uploadForm")
	public ModelAndView uploadForm(String targetId, String editorAttachYn, String imageYn) {

		ModelAndView mav = new ModelAndView("support/fileupload/uploadForm");

		mav.addObject("targetId", targetId);
		mav.addObject("editorAttachYn", editorAttachYn);
		mav.addObject("imageYn", imageYn);

		return mav;
	}

	/**
	 * 프로파일 이미지 업로드 화면
	 * 
	 * @param targetId
	 * @param editorAttachYn
	 * @param imageYn
	 * @return
	 */
	@RequestMapping("/uploadFormForProfile")
	public ModelAndView uploadFormForProfile(String userId, String targetId, String editorAttachYn, String imageYn) {

		ModelAndView mav = new ModelAndView("support/fileupload/uploadFormForProfile");

		mav.addObject("userId", userId);
		mav.addObject("targetId", targetId);
		mav.addObject("editorAttachYn", editorAttachYn);
		mav.addObject("imageYn", imageYn);

		return mav;
	}
	
	/**
	 * 사인 이미지 업로드 화면
	 * 
	 * @param targetId
	 * @param editorAttachYn
	 * @param imageYn
	 * @return
	 */
	@RequestMapping("/uploadFormForSign")
	public ModelAndView uploadFormForSign(String userId, String targetId, String editorAttachYn, String imageYn) {

		ModelAndView mav = new ModelAndView("support/fileupload/uploadFormForSign");

		mav.addObject("userId", userId);
		mav.addObject("targetId", targetId);
		mav.addObject("editorAttachYn", editorAttachYn);
		mav.addObject("imageYn", imageYn);

		return mav;
	}

	@RequestMapping("/uploadFormForMeetingRoom")
	public ModelAndView uploadFormForMeetingRoom(String userId, String targetId, String editorAttachYn, String imageYn) {

		ModelAndView mav = new ModelAndView("support/fileupload/uploadFormForMeetingRoom");

		mav.addObject("userId", userId);
		mav.addObject("targetId", targetId);
		mav.addObject("editorAttachYn", editorAttachYn);
		mav.addObject("imageYn", imageYn);

		return mav;
	}

	/**
	 * 원본이미지 보기 화면
	 * 
	 * @param fileId
	 * @return
	 */
	@RequestMapping("/viewImageFile")
	public ModelAndView viewImageFile(String fileId) {

		ModelAndView mav = new ModelAndView("support/fileupload/viewImageFile");

		mav.addObject("fileId", fileId);

		return mav;
	}

	/**
	 * 파일 업로드 처리
	 * 
	 * @param targetId
	 * @param editorAttach
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadFile")
	public @ResponseBody
	String uploadFile(String targetId, String editorAttach, HttpServletRequest request) {

		String reStr = "";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) getRequestAttribute("ikep.user");

		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");

			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach, user);

			map.put("targetId", targetId);
			map.put("fileId", ((FileData) uploadList.get(0)).getFileId());
			map.put("fileName", ((FileData) uploadList.get(0)).getFileRealName());
			map.put("status", "success");
			map.put("message",
					messageSource.getMessage("support.fileupload.message.upload.success", null,
							new Locale(user.getLocaleCode())));

		} catch (Exception e) {
			map.put("status", "error");
			map.put("message",
					messageSource.getMessage("support.fileupload.message.upload.fail", null,
							new Locale(user.getLocaleCode())));
		}

		try {
			reStr = objectMapper.writeValueAsString(map);
		} catch (Exception e) {
		}

		return reStr;

	}
	

	@SuppressWarnings("unchecked")
	@RequestMapping("/dextUploadFileList")
	public @ResponseBody
	List<FileData>  dextUploadFileList(@RequestBody Map<String, Object> params ) {
		
		List<Map<String, Object>> fileList = ( List<Map<String, Object>>) params.get("fileList");
		
		List<FileData> uploadedFileList= new  ArrayList<FileData>();
		
	
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			for (Map<String, Object> upfile : fileList) {
				
				FileData fileData = new FileData();
				String fileId = StringUtil.replaceQuot(EncryptUtil
						.encryptText(idgenService.getNextId()));
		
	
				fileData.setFileId(fileId);
				fileData.setFilePath(((String)upfile.get("uploadPath")).trim());
				String orginFileName2= URLDecoder.decode((String)upfile.get("orginFileName"),"UTF-8");

				fileData.setFileRealName((String)upfile.get("orginFileName"));
				fileData.setFileSize(Integer.parseInt((String)upfile.get("fileSize")));
				fileData.setFileName((String)upfile.get("uploadedFileName"));
				fileData.setFileContentsType((String)upfile.get("fileExtension"));
				fileData.setFileExtension((String)upfile.get("fileExtension"));
				fileData.setEditorAttach(0);
				fileData.setRegisterId(user.getUserId());
				fileData.setRegisterName(user.getUserName());
				fileData.setUpdaterId(user.getUserId());
				fileData.setUpdaterName(user.getUserName());
				
		
				fileService.create(fileData);
				
				uploadedFileList.add(fileData);
			}
		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}
		return uploadedFileList;
	}
	
	@RequestMapping("/dextUploadFile")
	public @ResponseBody
	FileData  dextUploadFile(@RequestParam("uploadPath") String uploadPath, 
			@RequestParam("orginFileName") String orginFileName, 
			@RequestParam("uploadedFileName") String uploadedFileName, 
			@RequestParam("fileExtension") String fileExtension, 
			@RequestParam("fileSize") String fileSize ) {

		FileData fileData = new FileData();
		try {
			User user = (User) getRequestAttribute("ikep.user");
			
			String fileId = StringUtil.replaceQuot(EncryptUtil
					.encryptText(idgenService.getNextId()));
	

			fileData.setFileId(fileId);
			fileData.setFilePath(uploadPath);
			String orginFileName2= URLDecoder.decode(orginFileName,"UTF-8");
			//System.out.println("#####################################orginFileName:"+orginFileName);
			//System.out.println("#####################################orginFileName2:"+orginFileName2);
			fileData.setFileRealName(orginFileName2);
			fileData.setFileSize(Integer.parseInt(fileSize));
			fileData.setFileName(uploadedFileName);
			fileData.setFileContentsType(fileExtension);
			fileData.setFileExtension(fileExtension);
			fileData.setEditorAttach(0);
			fileData.setRegisterId(user.getUserId());
			fileData.setRegisterName(user.getUserName());
			fileData.setUpdaterId(user.getUserId());
			fileData.setUpdaterName(user.getUserName());
			
	
			fileService.create(fileData);
		} catch (Exception ex) {

			throw new IKEP4AjaxException("", ex);
		}

		return fileData;
	}
	/**
	 * 파일 업로드 처리 (프로파일 이미지 처리용)
	 * 
	 * @param targetId
	 * @param editorAttach
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadFileForProfile")
	public @ResponseBody
	String uploadFileForProfile(String userId, String targetId, String editorAttach, HttpServletRequest request) {

		String reStr = "";
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, String> map = new HashMap<String, String>();
		User user = (User) getRequestAttribute("ikep.user");

		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			List<MultipartFile> fileList = multipartRequest.getFiles("file");

			List<FileData> uploadList = fileService.uploadFileForProfile(fileList, userId, targetId);

			map.put("targetId", targetId);
			map.put("fileId", ((FileData) uploadList.get(0)).getFileId());
			map.put("fileName", ((FileData) uploadList.get(0)).getFilePath());
			map.put("status", "success");
			map.put("message",
					messageSource.getMessage("support.fileupload.message.upload.success", null,
							new Locale(user.getLocaleCode())));

		} catch (Exception e) {
			map.put("status", "error");
			map.put("message",
					messageSource.getMessage("support.fileupload.message.upload.fail", null,
							new Locale(user.getLocaleCode())));
		}

		try {
			reStr = objectMapper.writeValueAsString(map);
		} catch (Exception e) {
		}

		return reStr;

	}

	/**
	 * 파일업로드 화면(에디터용)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/uploadFormForEditor")
	public ModelAndView uploadFormForEditor(HttpServletRequest request) {
		Map<String, String> parameterMap = new HashMap<String, String>();

		for (Object key : request.getParameterMap().keySet()) {
			parameterMap.put(String.valueOf(key), request.getParameter(String.valueOf(key)));
		}

		ModelAndView mav = new ModelAndView("support/fileupload/uploadFormForEditor");

		mav.addObject("parameterMap", parameterMap);

		return mav;
	}

	/**
	 * 파일 업로드 처리(에디터용)
	 * 
	 * @param model
	 * @param request
	 * @return @
	 */
	@RequestMapping("/uploadFileForEditor")
	public ModelAndView uploadFileForEditor(String targetId, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("support/fileupload/uploadEndForEditor");
		User user = (User) getRequestAttribute("ikep.user");
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			MultipartFile file = multipartRequest.getFile("file");

			Map<String, Object> parameterMap = new HashMap<String, Object>();

			for (Object key : request.getParameterMap().keySet()) {
				parameterMap.put(String.valueOf(key), request.getParameter(String.valueOf(key)));
			}

			if (file != null) {
				parameterMap.put("fileName", file.getName());
				parameterMap.put("fileContentType", file.getContentType());
				parameterMap.put("fileOriginalFilename", file.getOriginalFilename());
				parameterMap.put("fileSize", file.getSize());

				final String interceptorKey = String.valueOf(parameterMap.get("interceptorKey"));

				FileUploadInterceptor fileUploadInterceptor = null;
				for (FileUploadInterceptor loopingItem : this.fileUploadInterceptorList) {
					if (loopingItem.getKey().equals(interceptorKey)) {
						fileUploadInterceptor = loopingItem;
					}
				}

				UPLOAD_ERROR_MESSAGE interceptorResultCode = fileUploadInterceptor.beforeProcess(parameterMap);
				
				if (interceptorResultCode == FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.OK) {
					List<FileData> uploadList = fileService.uploadFileForEditor(Arrays.asList(file), parameterMap, user);
					/*add createFileLink*/
					List<String> fileIdList = getFileIdList(uploadList);
					fileService.createFileLink(fileIdList, "", "PM", user);

					mav.addObject("targetId", request.getParameter("targetId"));
					mav.addObject("fileId", ((FileData) uploadList.get(0)).getFileId());
					mav.addObject("fileName", ((FileData) uploadList.get(0)).getFileRealName());
					mav.addObject("status", "success");
					mav.addObject(
							"message",
							messageSource.getMessage("support.fileupload.message.upload.success", null,	new Locale(user.getLocaleCode())));
				} else {
					mav.addObject("status", "error");
					if(interceptorResultCode == FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.MAX_SIZE)
						mav.addObject(
								"message",
								messageSource.getMessage("support.fileupload.message.uploadfail.maxSize", null, new Locale(user.getLocaleCode())));
					if(interceptorResultCode == FileUploadInterceptor.UPLOAD_ERROR_MESSAGE.NOT_SUPPORT_EXT)
						mav.addObject(
								"message",
								messageSource.getMessage("support.fileupload.message.uploadfail.notSupportExt", null, new Locale(user.getLocaleCode())));
				}
			}
		} catch (Exception e) {
			// ex.printStackTrace();
			mav.addObject("status", "error");
			mav.addObject(
					"message",
					messageSource.getMessage("support.fileupload.message.upload.fail", null, new Locale(user.getLocaleCode())));

			return mav;
		}

		return mav;
	}

	private List<String> getFileIdList(List<FileData> uploadList) {
		List<String> fileIdList = new ArrayList<String>();
		
		for(FileData fileData : uploadList){
			fileIdList.add(fileData.getFileId());
		}
		
		return fileIdList;
	}

	/**
	 * 파일 업로드 처리(플래시용)
	 * 
	 * @param model
	 * @param request
	 * @return @
	 */
	@RequestMapping("/uploadFileForFlash")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView uploadFileForFlash(ModelMap model, HttpServletRequest request) {

		ModelAndView mav = new ModelAndView("/support/fileupload/output");

		Map<String, String> reMap = new HashMap<String, String>();
		ObjectMapper objectMapper = new ObjectMapper();
		User user = (User) getRequestAttribute("ikep.user");
		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;

			List<MultipartFile> fileList = new ArrayList<MultipartFile>();

			Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
			Iterator<String> iterator = multipartRequest.getFileNames();

			while (iterator.hasNext()) {
				String fileName = (String) iterator.next();
				MultipartFile multipartFile = (MultipartFile) fileMap.get(fileName);
				fileList.add(multipartFile);
			}

			List<FileData> uploadList = null;
			String isProfile = request.getParameter("isProfile");
			if (isProfile != null && isProfile.equalsIgnoreCase("Y")) {
				String userId = request.getParameter("userId");
				String targetId = request.getParameter("targetId");
				uploadList = fileService.uploadFileForProfile(fileList, userId, targetId);
				
				User sessionUser = (User) getRequestAttribute("ikep.user");
				if(userId.equals(sessionUser.getUserId())) {
					sessionUser.setProfilePicturePath(((FileData) uploadList.get(0)).getFilePath());
				}
			} else {
				uploadList = fileService.uploadFile(fileList, "", "", user);
			}

			if (uploadList != null && uploadList.size() > 0) {
				reMap.put("fileId", ((FileData) uploadList.get(0)).getFileId());
				reMap.put("fileRealName", ((FileData) uploadList.get(0)).getFileRealName());
				reMap.put("fileSize", String.valueOf(((FileData) uploadList.get(0)).getFileSize()));
				reMap.put("fileExtension", ((FileData) uploadList.get(0)).getFileExtension());
				reMap.put("filePath", ((FileData) uploadList.get(0)).getFilePath());
				reMap.put("status", "success");
				reMap.put(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.success", null,
								new Locale(user.getLocaleCode())));
				mav.addObject("reStr", objectMapper.writeValueAsString(reMap));
			} else {
				reMap.put("status", "fail");
				reMap.put(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.fail", null,
								new Locale(user.getLocaleCode())));
				mav.addObject("reStr", objectMapper.writeValueAsString(reMap));
			}

		} catch (Exception ex) {
			try {
				reMap.put("status", "fail");
				reMap.put(
						"message",
						messageSource.getMessage("support.fileupload.message.upload.fail", null,
								new Locale(user.getLocaleCode())));
				mav.addObject("reStr", objectMapper.writeValueAsString(reMap));
			} catch (Exception e) {
			}
		}

		return mav;
	}

	/**
	 * 파일 다운로드 처리
	 * 
	 * @param fileId
	 * @param thumbnailYn
	 * @param profileYn
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadFile")
	public String downloadFile(String fileId, String thumbnailYn, String profileYn, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String profileYnStr = profileYn;
			if (StringUtil.isEmpty(profileYnStr)) {
				profileYnStr = "N";
			}

			String fileUrl = fileService.downloadFile("file", fileId, thumbnailYn, profileYnStr, request, response);

			if (StringUtil.isEmpty(fileUrl)) {
				return null;
			} else {
				return "redirect:" + fileUrl;
			}

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 파일 다운로드 처리 (프로파일 이미지용)
	 * 
	 * @param userId
	 * @param smallimageYn
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadFileForProfile")
	public String downloadFileForProfile(String userId, String smallimageYn, HttpServletRequest request,
			HttpServletResponse response) {

		try {

			String fileUrl = fileService.downloadFile("profile", userId, smallimageYn, "Y", request, response);

			return "redirect:" + fileUrl;

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

	}

	/**
	 * 파일 다운로드 처리 (에디터용) TODO Javadoc주석작성
	 * 
	 * @param userId
	 * @param smallimageYn
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/downloadFileForHR")
	public String downloadFileForHR(String userId, String smallimageYn, HttpServletRequest request,
			HttpServletResponse response) {

		User profile = userService.read(userId);

		String fileId = profile.getProfilePictureId();

		fileService.downloadFile("file", fileId, smallimageYn, "N", request, response);

		return null;
	}

	/**
	 * 파일 삭제
	 * 
	 * @param fileId
	 * @param model
	 * @return @
	 */
	/*
	 * @RequestMapping("/deleteFile") public String deleteFile(String fileId,
	 * ModelMap model) { fileService.removeFile(fileId);
	 * model.addAttribute("status", "ok"); return null; }
	 */

	/**
	 * ItemId에 연결된 파일 삭제
	 * 
	 * @param itemId
	 * @param model
	 * @return @
	 */
	/*
	 * @RequestMapping("/deleteItemFile") public String deleteItemFile(String
	 * itemId, ModelMap model) { fileService.removeItemFile(itemId);
	 * model.addAttribute("status", "ok"); return null; }
	 */

	/**
	 * 파일 조회
	 * 
	 * @param fileId
	 * @param model
	 * @return @
	 */
	@RequestMapping("/getFile")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	FileData getFile(String fileId, ModelMap model) {

		FileData fileData = fileService.read(fileId);

		return fileData;
	}

	/**
	 * ItemId에 연결된 파일 조회
	 * 
	 * @param itemId
	 * @param model
	 * @return @
	 */
	@RequestMapping("/getItemFile")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<FileData> getItemFile(String itemId, String editorYn, String itemTypeCode, ModelMap model) {

		List<FileData> fileList = fileService.getItemFile(itemId, editorYn);

		return fileList;
	}

	/**
	 * 썸네일 조회
	 * 
	 * @param fileId
	 * @param model
	 * @return @
	 */
	@RequestMapping("/getThumbnail")
	public String getThumbnail(String fileId, ModelMap model) {

		Thumbnail thumbnail = thumbnailService.read(fileId);

		model.addAttribute("thumbnail", thumbnail);

		return null;
	}

	/**
	 * ItemId에 연결된 썸네일 리스트 조회
	 * 
	 * @param itemId
	 * @param model
	 * @return @
	 */
	@RequestMapping("/getItemThumbnail")
	public String getItemThumbnail(String itemId, String itemTypeCode, ModelMap model) {

		List<Thumbnail> thumbnailList = thumbnailService.getItemThumbnail(itemId, itemTypeCode);

		model.addAttribute("thumbnailList", thumbnailList);

		return null;
	}

	public ModelAndView getView(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 업로드 테스트
	 * 
	 * @return
	 */
	@RequestMapping("/uploadTest")
	public String uploadTest() {

		return "support/fileupload/uploadTest";
	}

	/**
	 * 엑셀 다운로드 테스트
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping("/excelDownTest")
	public @ResponseBody
	String excelDownTest(HttpServletResponse response) {

		try {

			List<FileData> fileList = fileService.getAllForFileDeleteBatch();

			List<Object> dataList = new ArrayList<Object>();
			for (FileData fileData : fileList) {
				dataList.add(fileData);
			}

			LinkedHashMap<String, String> titleMap = new LinkedHashMap<String, String>();
			titleMap.put("fileId", "파일ID");
			titleMap.put("fileName", "파일명");
			titleMap.put("fileName", "파일명");
			titleMap.put("fileSize", "파일크기");
			titleMap.put("registDate", "등록일");

			String fileName = "test.xlsx";

			ExcelUtil.saveExcel(titleMap, dataList, fileName, response, 1);

		} catch (Exception e) {
			throw new IKEP4ApplicationException("", e);
		}

		return "ok";
	}
	
	@RequestMapping("/downLoadPdf.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String downLoadPdf(HttpServletResponse response, String itemId) {

		try {

			String downFileName = "output.pdf";

			// String fileUrl =
			// "http://agora.media.daum.net/best/best?groupId=1&bbsId=all";
			String fileUrl = "http://ikep.lgcns.com:8080/ikep4-webapp/support/fileupload/pdftest.do";

			// String fileName =
			// "C:/ikep4j-project/workspace/ikep4/ikep4-webapp/src/main/webapp/base/html/ui_framework/dialog.html";
			String fileName = "d:/test.htm";

			String htmlDocument = "<html><body><img src=\"http://www.chilkatsoft.com/images/dude.gif\"><br><font face=\"Gulim\">한글 Test</font></body></html>";

			PdfUtil.downloadPdfByStr(response, htmlDocument, downFileName);

			// PdfUtil.downloadPdfByUrl(response, fileUrl, downFileName);

			// PdfUtil.downloadPdfByFile(response, fileName, downFileName);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	@RequestMapping("/pdftest.do")
	public ModelAndView downLoadPdf() {

		ModelAndView mav = new ModelAndView("/support/fileupload/pdftest");

		try {

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}
		return mav;
	}

	@RequestMapping("/downLoadMht.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String downLoadMht(HttpServletResponse response, String itemId) {

		try {

			String downFileName = "output.mht";

			String htmlDocument = "<html><body><img src=\"http://www.chilkatsoft.com/images/dude.gif\"><br><font face=\"Gulim\">한글 Test</font></body></html>";

			MhtUtil.downloadMhtByStr(response, htmlDocument, downFileName);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

	@RequestMapping("/printHtml.do")
	@ResponseStatus(HttpStatus.OK)
	public ModelAndView printHtml(HttpServletResponse response, String itemId) {

		ModelAndView mav = new ModelAndView("support/fileupload/printHtml");

		try {

			String htmlDocument = "<html><body><img src=\"http://www.chilkatsoft.com/images/dude.gif\"><br><font face=\"Gulim\">한글 Test</font></body></html>";

			mav.addObject("htmlDocument", htmlDocument);

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return mav;
	}

	@RequestMapping("/apprDownLoadForm")
	public ModelAndView apprDownLoadForm(String fileName, String htmlDocument) {

		ModelAndView mav = new ModelAndView("support/fileupload/apprDownLoadForm");

		mav.addObject("fileName", fileName);
		mav.addObject("htmlDocument", htmlDocument);

		return mav;
	}

	@RequestMapping("/apprDownLoad.do")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	String apprDownLoad(HttpServletResponse response, String fileName, String fileType, String htmlDocument) {

		try {

			htmlDocument = htmlDocument.replaceAll("&lt;", "<");
			htmlDocument = htmlDocument.replaceAll("&gt;", ">");

			if (fileType.equals("1")) {
				PdfUtil.downloadPdfByStr(response, htmlDocument, fileName + ".pdf");
			} else {
				MhtUtil.downloadMhtByStr(response, htmlDocument, fileName + ".mht");
			}

		} catch (Exception ex) {
			throw new IKEP4AjaxException("", ex);
		}

		return "success";
	}

}
