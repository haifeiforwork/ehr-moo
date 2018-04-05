package com.lgcns.ikep4.collpack.kms.board.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.web.TreeMaker;




@Controller("kmsBoardMigrationController")
@RequestMapping(value = "/collpack/kms/board/boardMigration")
@SessionAttributes("collaboration")
public class BoardMigrationController extends BaseController {
	
	private static final String DEFAULT_BOARD_ROOT_ID = "0";
	
	@Autowired
	private BoardAdminService boardAdminService;
	
	@Autowired
	private WorkspaceService workspaceService;
	
	
	private String boardTreeJson(String workspaceId) {
		
		Map<String, String> boardMap = new HashMap<String, String>();
		boardMap.put("workspaceId", workspaceId);
		boardMap.put("boardRootId", DEFAULT_BOARD_ROOT_ID);
		
	    List<Board> boardList =  boardAdminService.listByBoardRootId(boardMap);
	    
	    return TreeMaker.init(boardList, "boardId", "boardParentId", "boardName").create().toJsonString();
	}
	  
	/**
	 * 해당 워크스페이스의 게시판 목록 Tree
	 * @param workspaceId
	 * @param boardRootId
	 * @return
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@RequestMapping(value = "/listBoardTreeView")
	public ModelAndView listBoardTreeView(@RequestParam(value="workspaceId") String workspaceId,
			@RequestParam(value="boardRootId") String boardRootId) 
		throws JsonGenerationException, JsonMappingException, IOException {
		
		//Map<String, String> map = new HashMap<String, String>();
		//map.put("workspaceId", workspaceId);
		//map.put("boardRootId", boardRootId);
		
		//List<Board> boardList = this.boardAdminService.listByBoardRootId(map);
		  
		//BoardCode boardCode = new BoardCode();
		
		Board dummyBoard = new Board();
		
		dummyBoard.setBoardId(null); 
 
		return new ModelAndView()
		.addObject("boardTreeJson", boardTreeJson(workspaceId))
		.addObject("workspaceId", workspaceId)
		.addObject("ajaxBoard", new Board());
		//.addObject("boardCode", boardCode);
	}
	/**
	 * 시샵,운영자로 등록된 Workspace 목록 조회
	 * @param workspaceId
	 * @param boardId
	 * @return
	 */
	@RequestMapping(value = "/listWorkspaceManagerView")
	public ModelAndView listWorkspaceManagerView(@RequestParam(value="workspaceId") String workspaceId,@RequestParam(value="boardId") String boardId){
		
		Portal portal = (Portal) getRequestAttribute("ikep.portal");
		User user = (User)this.getRequestAttribute("ikep.user");		
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("portalId", portal.getPortalId());
		map.put("memberId", user.getUserId());
		
		List<Workspace> workspaceList = null;
		workspaceList = workspaceService.listWorkspaceManager(map);
		
 
		return new ModelAndView()
		.addObject("workspaceId", workspaceId)
		.addObject("boardId", boardId)
		.addObject("workspaceList", workspaceList);
	}	
	
	/**
	 * 시샵,운영자로 등록된 Workspace의 선택한 게시판 Tree
	 * @param workspaceId
	 * @param boardId
	 * @param targetWorkspaceId
	 * @return
	 */
	@RequestMapping(value = "/listTargetBoardTreeView")
	public ModelAndView listTargetBoardTreeView(@RequestParam(value="workspaceId") String workspaceId,
			@RequestParam(value="boardId") String boardId,
			@RequestParam(value="targetWorkspaceId") String targetWorkspaceId) {

		
		return new ModelAndView()
		.addObject("boardTreeJson", boardTreeJson(targetWorkspaceId))		
		.addObject("targetWorkspaceId", targetWorkspaceId)		
		.addObject("workspaceId", workspaceId)
		.addObject("boardId", boardId);
	}



	/**
	 * 게시판 이관
	 * @param orgWorkspaceId
	 * @param orgBoardId
	 * @param targetWorkspaceId
	 * @param targetBoardId
	 */
	@RequestMapping(value = "/moveBoard")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody void updateBoardView(
			@RequestParam(value="orgWorkspaceId") String orgWorkspaceId, 
			@RequestParam(value="orgBoardId") String orgBoardId, 
			@RequestParam(value="targetWorkspaceId") String targetWorkspaceId, 
			@RequestParam(value="targetBoardId") String targetBoardId) { 
		

		
		User user = (User)this.getRequestAttribute("ikep.user");

		
		this.boardAdminService.moveBoardByWorkspace(orgWorkspaceId,orgBoardId,targetWorkspaceId,targetBoardId,user);

	}
	

}
