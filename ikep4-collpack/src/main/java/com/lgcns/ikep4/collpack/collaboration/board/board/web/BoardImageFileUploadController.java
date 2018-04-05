package com.lgcns.ikep4.collpack.collaboration.board.board.web;

//import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.ModelAndView;

//import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
//import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.framework.web.BaseController;

@Controller("wsBoardImageFileUploadController")
@RequestMapping(value = "/collpack/collaboration/board/boardImageUpload")
public class BoardImageFileUploadController extends BaseController {	
	private static final String DEFAULT_BOARD_ROOT_ID = "0";
	//@Autowired
	//private BoardAdminService boardAdminService;
	
	@Autowired
	private FileService fileService; 

	/**
	 * 이미지 업로드 조회
	 * @param boardId
	 * @param targetId
	 * @return
	 */
	@RequestMapping("imageUploadView")
	public ModelAndView uploadForm(String boardId, String targetId) {

		ModelAndView mav = new ModelAndView();

		mav.addObject("targetId", targetId);

		return mav;
	}
 
	/**
	 * 파일 업로드 처리
	 * 
	 * @param model
	 * @param request
	 * @return @
	 */
	@RequestMapping("imageUpload")
	public ModelAndView uploadFile(String workspaceId,String boardId, String targetId, String editorAttach, HttpServletRequest request) { 
		
		User user = (User) getRequestAttribute("ikep.user");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("workspaceId", workspaceId);
		map.put("boardRootId", DEFAULT_BOARD_ROOT_ID);
		map.put("boardId", boardId);
		
		//Board board = this.boardAdminService.readBoard(map); 
		
		//Long imageFileSize = board.getImageFileSize(); 
		
		ModelAndView mav = new ModelAndView();

		try {
			MultipartRequest multipartRequest = (MultipartRequest) request;
			
			
			List<MultipartFile> fileList = multipartRequest.getFiles("file");

	 
			List<FileData> uploadList = fileService.uploadFile(fileList, "", "", editorAttach,user);

			
			mav.addObject("targetId", targetId);
			mav.addObject("fileId", ((FileData) uploadList.get(0)).getFileId());
			mav.addObject("fileName", ((FileData) uploadList.get(0)).getFileRealName());
			mav.addObject("status", "success");
			mav.addObject("message", messageSource.getMessage("support.fileupload.message.upload.success", null, Locale.getDefault()));

		} catch (Exception exception) {
			StringBuffer buffer = new StringBuffer();
			buffer.append("\r\nWorkspace 게시판 파일 업로드 처리 ...... ");

			this.log.debug(buffer.toString());
			buffer.delete(0, buffer.length());

			this.log.error(exception.getStackTrace());
			
			mav.addObject("status", "error");
			mav.addObject("message", messageSource.getMessage("support.fileupload.message.upload.fail", null, Locale.getDefault()));

			return mav;
		}

		return mav;
	} 
	

}
