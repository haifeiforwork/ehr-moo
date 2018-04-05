package com.lgcns.ikep4.collpack.collaboration.board.board.restful.resources;


import java.io.InputStream;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.collpack.collaboration.board.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.collaboration.board.announce.service.AnnounceItemService;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.Board;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardItem;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardPermission;
import com.lgcns.ikep4.collpack.collaboration.board.board.model.BoardReference;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemListHead;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemListBody;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemListReturnData0;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemListReturnData1;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemViewBody;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemViewHead;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemViewReturnData0;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardItemViewReturnData1;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardLineReplyListHead;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardLineReplyListBody;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardLineReplyListReturnData0;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardLineReplyListReturnData1;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardLineReplyListReturnData2;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardListBody;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardListHead;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.BoardListReturnData;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.CollListHead;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.CollListBody;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.CollListReturnData;
import com.lgcns.ikep4.collpack.collaboration.board.board.restful.model.CollParam;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.collaboration.board.board.service.BoardLinereplyService;
import com.lgcns.ikep4.collpack.collaboration.member.model.Member;
import com.lgcns.ikep4.collpack.collaboration.member.service.MemberService;
import com.lgcns.ikep4.collpack.collaboration.workspace.model.Workspace;
import com.lgcns.ikep4.collpack.collaboration.workspace.service.WorkspaceService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.restful.model.Root;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.MultiPart;

@Path("/collaboration")
@Component
public class CollRestful extends BaseController{
	
	@Autowired
	private BoardLinereplyService boardLinereplyService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private ACLService aclService;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardAdminService boardAdminService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private WorkspaceService workspaceService;

	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private AnnounceItemService announceItemService;

	/**
	 * 나의 Collaboration리스트 (내가 가입한 Collaboration리스트를 조회한다.)
	 */
	@POST 
	@Path("/retrieveCollList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public CollListHead retrieveCollList( CollParam collParam ) {
		
		CollListHead result = new CollListHead();
		
		try {
		
			Map<String, String> myCollMap = new HashMap<String, String>();
			myCollMap.put("portalId", collParam.getPortalId());
			myCollMap.put("memberId", collParam.getUserId());

			List<Workspace> myWorkspaceList = null;
			myWorkspaceList = workspaceService.listMyCollaborationForMobile(myCollMap);
			
			List<CollListReturnData> returnDataList = new ArrayList<CollListReturnData>();
			
			if(myWorkspaceList != null){
				
				User user = userDao.get(collParam.getUserId());
				Portal portal = portalService.readPortalDefault();
				for ( Workspace workspace : myWorkspaceList ) {
					
					CollListReturnData returnData = new CollListReturnData();
					
					returnData.setTypeId(StringUtil.nvl(workspace.getTypeId(), ""));
					
					
					//문제발생.
					returnData.setTypeName(StringUtil.nvl(workspace.getWorkspaceName(), ""));
					
					returnData.setCollaborationId(StringUtil.nvl(workspace.getWorkspaceId(), ""));
					returnData.setCollaborationName(StringUtil.nvl(workspace.getWorkspaceName(), ""));
		
					returnDataList.add(returnData);
				}
			}
			Head head = new Head(0, Response.Status.OK.toString());
			CollListBody body = new CollListBody(returnDataList);
			result = new CollListHead(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, e.toString());
			result = new CollListHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * Collaboration 게시글 리스트 조회
	 */
	@POST 
	@Path("/retrieveCollItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardItemListHead retrieveCollItemList( CollParam collParam ) {
		
		BoardItemListHead result = new BoardItemListHead();
		
		try {

			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();

			searchCondition.setWorkspaceId(collParam.getCollaborationId());
			searchCondition.setIsAll("yes");
			searchCondition.setPagePerRecord(collParam.getListSize());
			searchCondition.setPageIndex(collParam.getPage());
			searchCondition.setStartRowIndex(collParam.getPage()-1);
			
			if ( !StringUtil.isEmpty(collParam.getSearchWord()) ) {
				searchCondition.setSearchColumn("all"); // 확인필요 : title/contents/registerName/all
				searchCondition.setSearchWord(collParam.getSearchWord());
			}

			List<BoardItem> boardItemList = boardItemService.listBoardItemBySearchConditionMain(searchCondition);

			// 3. 데이터 가공 및 JAXB 처리
			List<BoardItemListReturnData0> returnData0 = new ArrayList<BoardItemListReturnData0>();
			
			if(boardItemList != null){
				for ( BoardItem item : boardItemList ) {
					
					User user = userDao.get(collParam.getUserId());
					Board board = boardAdminService.readBoard(item.getBoardId());
					
					BoardPermission boardPermission = this.initPermission(user, board);
					BoardPermission permission = new BoardPermission(false,false,false);
					
					// 게시물 권한 체크(게시판과 동일한 권한인 경우)
					if (item.getFollowBoardPermission() == 1) {
						// 게시판 권한 체크
						permission = initPermission(user, board);
					}
					else {
						// 게시물 권한 체크
						permission = initContentsPermission(user, board.getWorkspaceId(), item);
					}
					
					BoardItemListReturnData0 returnItem = new BoardItemListReturnData0();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setItemGroupId(StringUtil.nvl(item.getItemGroupId(), ""));
					returnItem.setItemStep(StringUtil.nvl(item.getStep().toString(), ""));
					returnItem.setItemLevel(StringUtil.nvl(item.getIndentation().toString(), ""));
					returnItem.setItemTitle(StringUtil.nvl(item.getTitle(), ""));
					returnItem.setItemHitCount(StringUtil.nvl(item.getHitCount().toString(), ""));
					returnItem.setItemCommentCount(StringUtil.nvl(item.getLinereplyCount().toString(), ""));
					returnItem.setItemAttachCnt(StringUtil.nvl(item.getAttachFileCount().toString(), ""));
					returnItem.setRegUserName(StringUtil.nvl(item.getRegisterName(), ""));
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
					
					if(permission.getIsReadPermission()==true) {
						returnItem.setReadPermit("1");
					} else {
						returnItem.setReadPermit("0");
					}
		
					if ( null == item.getItemParentId() || StringUtil.isEmpty(item.getItemParentId()) ) {
						returnItem.setItemParentId("0");	
					} else {
						returnItem.setItemParentId(item.getItemParentId());
					}
	
					if ( 0 == item.getItemDisplay() ) {
						returnItem.setIsImportant("0");
					} else {
						returnItem.setIsImportant("1");
					}
		
					returnData0.add(returnItem);
				}
			}
			
			BoardItemListReturnData1 returnData1 = new BoardItemListReturnData1(searchCondition.getPageCount(), boardItemList.size());
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardItemListBody body = new BoardItemListBody(returnData0, returnData1);
			result = new BoardItemListHead(head, body);
		
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(1, e.toString());
			result = new BoardItemListHead(head, null);
		}
		
		return result;
		
	}
	
	/**
	 * 게시글 상세 조회
	 */
	@POST 
	@Path("/retrieveCollItemDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardItemViewHead retrieveCollItemDetail( CollParam collParam ) {
		
		BoardItemViewHead result = new BoardItemViewHead();
		
		try {
			//User user = userDao.get(collParam.getUserId());
			//Portal portal = portalService.readPortalDefault();
			
			BoardReference boardReference = new BoardReference();
			boardReference.setRegisterId(collParam.getUserId());
			boardReference.setItemId(collParam.getItemId());
			
			this.boardItemService.updateHitCount(boardReference);
			
			BoardItem boardItem = this.boardItemService.readBoardItem(collParam.getItemId());

			if (boardItem == null) {
				throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
			}

			//Board board = this.boardAdminService.readBoard(boardItem.getBoardId());

			//게시판 글 상세정보
			BoardItemViewReturnData0 returnData0 = new BoardItemViewReturnData0();
			returnData0.setItemId(StringUtil.nvl(boardItem.getItemId(), ""));
			returnData0.setItemGroupId(boardItem.getItemGroupId());
			returnData0.setItemStep(boardItem.getStep());
			returnData0.setItemLevel(boardItem.getIndentation());
			returnData0.setItemTitle(StringUtil.nvl(boardItem.getTitle(), ""));
			
			returnData0.setRegUserId(StringUtil.nvl(boardItem.getRegisterId(), ""));
			returnData0.setRegUserName(StringUtil.nvl(boardItem.getRegisterName(), ""));
			User regUser = userDao.get(boardItem.getRegisterId());
			returnData0.setRegUserDept(StringUtil.nvl(regUser.getTeamName(), ""));
			returnData0.setRegUserTitle(StringUtil.nvl(regUser.getJobTitleName(), ""));
			
			returnData0.setRegDate(DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy.MM.dd HH:mm"));
			returnData0.setItemContent(StringUtil.nvl(boardItem.getContents(), ""));
			returnData0.setItemHitCount(boardItem.getHitCount());
			returnData0.setItemReplyCount(boardItem.getReplyCount());
			returnData0.setItemCommentCount(boardItem.getLinereplyCount());
			
			List<BoardItemViewReturnData1> returnData1 = new ArrayList<BoardItemViewReturnData1>();
			if (boardItem.getFileDataList() != null) {
				
				//properties파일에 설정되어 있는 정보로 첨부파일 다운로드 URL 생성
				String propUrl = "";
				Properties commonProp = PropertyLoader.loadProperties("/configuration/common-conf.properties");
				propUrl = commonProp.getProperty("ikep4.baseUrl");
				
				Properties fileprop = PropertyLoader.loadProperties("/configuration/fileupload-conf.properties");
				propUrl += fileprop.getProperty("ikep4.support.fileupload.downloadurl");
			
				List<FileData> FileList = boardItem.getFileDataList();
				
				if(FileList != null){
					for(FileData file : FileList){
						FileData fileData = fileService.read(file.getFileid());
						String fileUrl = propUrl + fileData.getFileId();
						
						BoardItemViewReturnData1 returnAttachData = new BoardItemViewReturnData1();
						returnAttachData.setAttachFileId(fileData.getFileId());
						returnAttachData.setAttachFileName(fileData.getFileRealName());
						returnAttachData.setAttachFileSize(fileData.getFileSize()+"");
						returnAttachData.setAttachFileUrl(fileData.getFilePath());
						returnAttachData.setAttachFileStoredName(fileData.getFileName());
						returnAttachData.setAttachTargetUrl(fileUrl);
						
						returnData1.add(returnAttachData);
					}
				}
			}
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardItemViewBody body = new BoardItemViewBody(returnData0, returnData1);
			result = new BoardItemViewHead(head, body);
		
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(1, e.toString());
			result = new BoardItemViewHead(head, null);
		}
		
		return result;
		
	}
	
	
	//LOCATION : 협업 댓글 목록 조회 (REST)
	/**
	 * 댓글 목록 조회
	 */
	@POST 
	@Path("/retrieveCollLineReplyList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardLineReplyListHead retrieveCollLineReplyList( CollParam collParam ) {
		
		BoardLineReplyListHead result = new BoardLineReplyListHead();

		try {
			
			BoardLinereplySearchCondition searchCondition = new BoardLinereplySearchCondition();
			
			searchCondition.setItemId(collParam.getItemId());
			searchCondition.setPagePerRecord(collParam.getListSize());
			searchCondition.setPageIndex(collParam.getPage());
			searchCondition.setStartRowIndex(collParam.getPage()-1);
			
			SearchResult<BoardLinereply> searchResult = this.boardLinereplyService.listBoardLinereplyBySearchCondition(searchCondition);

			BoardLineReplyListReturnData0 returnData0 = new BoardLineReplyListReturnData0(searchResult.getRecordCount());
			
			List<BoardLineReplyListReturnData1> returnData1 = new ArrayList<BoardLineReplyListReturnData1>();
			
			if(searchResult.getEntity() != null){
				for ( BoardLinereply item : searchResult.getEntity() ) {
					
					BoardLineReplyListReturnData1 returnItem = new BoardLineReplyListReturnData1();
	
					returnItem.setItemId(collParam.getItemId());
					returnItem.setCommentId(StringUtil.nvl(item.getLinereplyId(),""));
					returnItem.setCommentContent(StringUtil.nvl(item.getContents(),""));
					returnItem.setRegUserId(StringUtil.nvl(item.getRegisterId(),""));
					returnItem.setRegUserName(StringUtil.nvl(item.getRegisterName(),""));
					
					User regUser = userDao.get(item.getRegisterId());
					returnItem.setRegUserDept(StringUtil.nvl(regUser.getTeamName(),""));
					returnItem.setRegUserTitle(StringUtil.nvl(regUser.getJobTitleName(),""));
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
	
					returnData1.add(returnItem);
				}
			}
			BoardLineReplyListReturnData2 returnData2 = new BoardLineReplyListReturnData2(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardLineReplyListBody body = new BoardLineReplyListBody(returnData0, returnData1, returnData2);
			result = new BoardLineReplyListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, e.toString());
			result = new BoardLineReplyListHead(head, null);
		}
		
		return result;
		
	}
	
	
	/**
	 * 댓글 등록
	 * (return 값이 Head 만 있는 경우의 예)
	 */
	@POST 
	@Path("/insertCollLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root insertCollLineReply( CollParam collParam ) {

		Root result = new Root();

		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(collParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(collParam.getItemId());
			boardLinereply.setContents(collParam.getCommentContent());
	
			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			boardLinereplyService.createBoardLinereply(boardLinereply);
			
			if ( null != boardLinereply.getLinereplyId() && !StringUtil.isEmpty(boardLinereply.getLinereplyId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : BoardLineReply create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(2, e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	/**
	 * 댓글 수정
	 */
	@POST 
	@Path("/updateCollLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateCollLineReply( CollParam collParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(collParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
			
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(collParam.getItemId());
			boardLinereply.setLinereplyId(collParam.getCommentId());
			boardLinereply.setContents(collParam.getCommentContent());
			
			BoardLinereply readBoardLinereply = this.boardLinereplyService.readBoardLinereply(boardLinereply.getLinereplyId());

			readBoardLinereply.setContents(collParam.getCommentContent());

			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			this.boardLinereplyService.updateBoardLinereply(readBoardLinereply);
			
			if ( null != boardLinereply.getLinereplyId() && !StringUtil.isEmpty(boardLinereply.getLinereplyId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : BoardLineReply update failure.(BoardLineReplyId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	/**
	 * 댓글 삭제
	 */
	@POST 
	@Path("/deleteCollLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteCollLineReply( CollParam collParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(collParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(collParam.getItemId());
			boardLinereply.setLinereplyId(collParam.getCommentId());
			//boardLinereply.setContents(commentContent);
	
			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			boardLinereplyService.userDeleteBoardLinereply(boardLinereply);
			
			if ( null != boardLinereply.getLinereplyId() && !StringUtil.isEmpty(boardLinereply.getLinereplyId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : BoardLineReply delete failure.(BoardLineReplyId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(2, e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	/**
	 * 게시판 목록 가져오기
	 */
	@POST 
	@Path("/retrieveBoardList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardListHead retrieveBoardList( CollParam boardParam ) {
		
		BoardListHead result = new BoardListHead();
		List<Board> sourceList = null;
		
		try {
			
			Map<String, String> param = new HashMap<String, String>();
			param.put("portalId", boardParam.getPortalId());
			param.put("userId", boardParam.getUserId());
			param.put("workspaceId", boardParam.getCollaborationId());
			param.put("boardRootId", "0");
			
			sourceList = this.boardAdminService.listByBoardRootIdForMobile(param);

			List<BoardListReturnData> returnDataList = new ArrayList<BoardListReturnData>();
			
			if( null != sourceList ){
				
				User user = userDao.get(boardParam.getUserId());
				Portal portal = portalService.readPortal(boardParam.getPortalId());
				
				BoardPermission permission = new BoardPermission(false,false,false);
				
				for ( Board board : sourceList ) {
				
					board.setWorkspaceId(boardParam.getCollaborationId());
					// 권한 체크
					permission = initPermission(user, board);
					
					BoardListReturnData returnData = new BoardListReturnData();
					
					returnData.setBoardId(StringUtil.nvl(board.getBoardId(), ""));
					returnData.setBoardParentId(StringUtil.nvl(board.getBoardParentId(), ""));
					returnData.setHasChildren(StringUtil.nvl(board.getHasChildren().toString(), ""));
					returnData.setIsReply(StringUtil.nvl(board.getReply().toString(), ""));
					returnData.setIsComment(StringUtil.nvl(board.getLinereply().toString(), ""));
					returnData.setSiblingOrder(StringUtil.nvl(board.getSortOrder().toString(), ""));
					
					if(permission.getIsReadPermission()==true) {
						returnData.setReadPermit("1");
					} else {
						returnData.setReadPermit("0");
					}
					if(permission.getIsWritePermission()==true) {
						returnData.setWritePermit("1");
					} else {
						returnData.setWritePermit("0");
					}
					
					if( user.getLocaleCode().equals(portal.getDefaultLocaleCode()) ){
						returnData.setBoardName(StringUtil.nvl(board.getBoardName(), ""));
					} else {
						returnData.setBoardName(StringUtil.nvl(board.getBoardName(),""));
					}
					
					returnDataList.add(returnData);
				}
			}
			Head head = new Head(0, Response.Status.OK.toString());
			BoardListBody body = new BoardListBody(returnDataList);
			result = new BoardListHead(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error : " + e.toString());
			result = new BoardListHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * Collaboration 게시글 리스트 조회 (board)
	 */
	@POST 
	@Path("/retrieveBoardItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardItemListHead retrieveBoardItemList( CollParam collParam ) {
		User user = userDao.get(collParam.getUserId());
		Portal portal = portalService.readPortal(collParam.getPortalId());
		boolean isDefaultLocale = portal.getDefaultLocaleCode().equals(user.getLocaleCode());
		
		BoardItemListHead result = new BoardItemListHead();
		List<BoardItemListReturnData0> returnData0 = new ArrayList<BoardItemListReturnData0>();
		
		try {
			
				Board board = this.boardAdminService.readBoard(collParam.getBoardId());
				BoardPermission permission = checkPermission(user, board);
				
				BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();

				searchCondition.setWorkspaceId(collParam.getCollaborationId());
				searchCondition.setBoardId(collParam.getBoardId());
				searchCondition.setPagePerRecord(collParam.getListSize());
				searchCondition.setPageIndex(collParam.getPage());
				searchCondition.setStartRowIndex(collParam.getPage()-1);
				
				if ( !StringUtil.isEmpty(collParam.getSearchWord()) ) {
					searchCondition.setSearchColumn("mobile"); 
					searchCondition.setSearchWord(collParam.getSearchWord());
				}
				
				List<BoardItem>	boardItemList = boardItemService.listBoardItemBySearchConditionMain(searchCondition);	
				
			if(boardItemList != null){
				for ( BoardItem item : boardItemList ) {
					
					BoardPermission boardPermission = this.initPermission(user, board);
					
					// 게시물 권한 체크(게시판과 동일한 권한인 경우)
					if (item.getFollowBoardPermission() == 1) {
						// 게시판 권한 체크
						permission = initPermission(user, board);
					}
					else {
						// 게시물 권한 체크
						permission = initContentsPermission(user, board.getWorkspaceId(), item);
					}
					
					BoardItemListReturnData0 returnItem = new BoardItemListReturnData0();
					
					User itemUser = item.getUser();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setItemGroupId(StringUtil.nvl(item.getItemGroupId(), ""));
					returnItem.setItemStep(StringUtil.nvl(item.getStep().toString(), ""));
					returnItem.setItemLevel(StringUtil.nvl(item.getIndentation().toString(), ""));
					returnItem.setItemTitle(StringUtil.nvl(item.getTitle(), ""));
					returnItem.setItemHitCount(StringUtil.nvl(item.getHitCount().toString(), ""));
					returnItem.setItemCommentCount(StringUtil.nvl(item.getLinereplyCount().toString(), ""));
					returnItem.setItemAttachCnt(StringUtil.nvl(item.getAttachFileCount().toString(), ""));
					returnItem.setRegUserName(isDefaultLocale ? StringUtil.nvl(itemUser.getUserName(), "") : StringUtil.nvl(itemUser.getUserEnglishName(), ""));
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
					
					if(permission.getIsReadPermission()==true) {
						returnItem.setReadPermit("1");
					} else {
						returnItem.setReadPermit("0");
					}
		
					if ( null == item.getItemParentId() || StringUtil.isEmpty(item.getItemParentId()) ) {
						returnItem.setItemParentId("0");	
					} else {
						returnItem.setItemParentId(item.getItemParentId());
					}
	
					if ( 0 == item.getItemDisplay() ) {
						returnItem.setIsImportant("0");
					} else {
						returnItem.setIsImportant("1");
					}
		
					returnData0.add(returnItem);
				}
				
				BoardItemListReturnData1 returnData1 = new BoardItemListReturnData1(searchCondition.getPageCount(), boardItemList.size());
				Head head = new Head(0, Response.Status.OK.toString());
				BoardItemListBody body = new BoardItemListBody(returnData0, returnData1);
				result = new BoardItemListHead(head, body);
				
			} else {
				BoardItemListReturnData1 returnData1 = new BoardItemListReturnData1(searchCondition.getPageCount(), 0);
				Head head = new Head(0, Response.Status.OK.toString());
				BoardItemListBody body = new BoardItemListBody(returnData0, returnData1);
				result = new BoardItemListHead(head, body);
			}					
	
		} catch(Exception e) {
			Head head = new Head(1, "Error : " + e.toString());
			result = new BoardItemListHead(head, null);
		}
		
		return result;
	}
	
	/**
	 * 게시글 등록 (공지사항 게시판, 첨부파일 포함)
	 */
	@POST 
	@Path("/insertNoticeItem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root insertNoticeItem(MultiPart multipart) throws Exception {
		Root result = new Root();
		AnnounceItem announceItem = new AnnounceItem();
		List<FileData> uploadList = new ArrayList<FileData>();
		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		
		try {
			
			List<BodyPart> bodypart = multipart.getBodyParts();

			//User 정보를 우선으로 설정해준다.
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				if("userId".equals(fdbodypart.getName())){
					user = userDao.get(fdbodypart.getValue());
			    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			    	break;
				}
			}
			
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
				
				//파일명이 없는것은 Content 로 간주 한다.
				if(disposition.getFileName() == null){
					String name = fdbodypart.getName();
				    String value = fdbodypart.getValue();
				    
				    if("portalId".equals(name)) announceItem.setPortalId(value);
				    if("collaborationId".equals(name)) announceItem.setWorkspaceId(value); //add
				    if("boardId".equals(name)){
						Board board = this.boardAdminService.readBoard(value);
						BoardPermission permission = checkPermission(user, board);
						if ( !permission.getIsWritePermission() ) { //등록권한체크
							Head head = new Head(1, "Error : No permission to write board.");
							result = new Root(head, null);
							//사용자 세션 삭제
							RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
							return result;
						}
				    }
				    if("itemTitle".equals(name)) announceItem.setTitle(value);
				    if("itemContent".equals(name)) announceItem.setContents(value);
				    if("itemLevel".equals(name)) announceItem.setItemSeqId(value);
				    
				//첨부파일 업로드
				}else{
					String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(fileService.uploadFile(fileName, is, user));
				    }
				}
			}
			
			announceItem.setAttachFileCount(0);
			announceItem.setItemDisplay(0);
			announceItem.setItemDelete(0);
			
			
			if (uploadList != null && uploadList.size() > 0) {
				announceItem.setFileDataList(uploadList);
				
				//FileLinkList 생성
				for(FileData fileData : uploadList){
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileData.getFileId());
					fileLink.setFileSize(fileData.getFileSize()+"");
					fileLink.setRegisterId(fileData.getRegisterId());
					fileLink.setRegisterName(fileData.getRegisterName());
					fileLink.setUpdaterId(fileData.getUpdaterId());
					fileLink.setUpdaterName(fileData.getUpdaterName());
					fileLink.setFlag("add");
					fileLinkList.add(fileLink);
				}
				announceItem.setFileLinkList(fileLinkList);
			}
			

			ModelBeanUtil.bindRegisterInfo(announceItem, user.getUserId(), user.getUserName());
			
			String boardItemId = null; 
			announceItem.setMsie(0);
			boardItemId = announceItemService.createAnnounceItem(announceItem, user);
			
			if ( null != boardItemId && !StringUtil.isEmpty(boardItemId) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(2, "Error : boardItem create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(3, "Error : " + e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;
	}
	
	
	/**
	 * 게시글 등록 (첨부파일 포함)
	 */
	@POST 
	@Path("/insertCollItem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root insertCollItem(MultiPart multipart) throws Exception {
		
		Root result = new Root();
		BoardItem boardItem = new BoardItem();
		List<FileData> uploadList = new ArrayList<FileData>();
		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		
		try {
			
			List<BodyPart> bodypart = multipart.getBodyParts();

			//User 정보를 우선으로 설정해준다.
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				if("userId".equals(fdbodypart.getName())){
					user = userDao.get(fdbodypart.getValue());
			    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			    	break;
				}
			}
			
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
				
				//파일명이 없는것은 Content 로 간주 한다.
				if(disposition.getFileName() == null){
					String name = fdbodypart.getName();
				    String value = fdbodypart.getValue();
				    
				    if("portalId".equals(name)) boardItem.setPortalId(value);
				    if("collaborationId".equals(name)) boardItem.setWorkspaceId(value); //add
				    if("boardId".equals(name)){
				    	boardItem.setBoardId(value);
						Board board = this.boardAdminService.readBoard(value);
						BoardPermission permission = checkPermission(user, board);
						if ( !permission.getIsWritePermission() ) { //등록권한체크
							Head head = new Head(1, "Error : No permission to write board.");
							result = new Root(head, null);
							//사용자 세션 삭제
							RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
							return result;
						}
				    }
				    if("itemTitle".equals(name)) boardItem.setTitle(value);
				    if("itemContent".equals(name)) boardItem.setContents(value);
				    if("itemParentId".equals(name)){
				    	if("".equals(value) || value == null) boardItem.setItemParentId("0");
				    	else boardItem.setItemParentId(value);
				    }
				    if("itemGroupId".equals(name)) boardItem.setItemGroupId(value);
				    if("itemStep".equals(name)) boardItem.setStep(Integer.parseInt(value));
				    if("itemLevel".equals(name)) boardItem.setItemSeqId(value);
				    
				//첨부파일 업로드
				}else{
					String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(fileService.uploadFile(fileName, is, user));
				    }
				}
			}
			
			boardItem.setUser(user);
			boardItem.setAttachFileCount(0);
			boardItem.setItemDisplay(0);
			boardItem.setIndentation(0);
			boardItem.setItemDelete(0);
			
			
			if (uploadList != null && uploadList.size() > 0) {
				boardItem.setFileDataList(uploadList);
				
				//FileLinkList 생성
				for(FileData fileData : uploadList){
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileData.getFileId());
					fileLink.setFileSize(fileData.getFileSize()+"");
					fileLink.setRegisterId(fileData.getRegisterId());
					fileLink.setRegisterName(fileData.getRegisterName());
					fileLink.setUpdaterId(fileData.getUpdaterId());
					fileLink.setUpdaterName(fileData.getUpdaterName());
					fileLink.setFlag("add");
					fileLinkList.add(fileLink);
				}
				boardItem.setFileLinkList(fileLinkList);
			}
			
			Date date = Calendar.getInstance().getTime();
			boardItem.setStartDate(date);
			boardItem.setEndDate(date);
			boardItem.setMsie(0);

			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
			
			String boardItemId = null; 
			//버젼 관리 등록을 위한 변수
			boardItem.setItemType("0");
			boardItemId = boardItemService.createBoardItem(boardItem,user);
						
			if ( null != boardItemId && !StringUtil.isEmpty(boardItemId) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(2, "Error : boardItem create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(3, "Error : " + e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;

    }

	/**
	 * 게시글 수정
	 */
	@POST 
	@Path("/updateCollItem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root updateCollItem(MultiPart multipart) throws Exception {
		
		Root result = new Root();
		BoardItem boardItem = new BoardItem();
		//List<FileData> uploadList = new ArrayList<FileData>();
		//List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		String boardItemId = null;
		try{
			List<BodyPart> bodypart = multipart.getBodyParts();

			//User 정보와 BoardItemId를 우선으로 설정해준다.
			for(BodyPart bp : bodypart) {
				
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				if("userId".equals(fdbodypart.getName())){
					user = userDao.get(fdbodypart.getValue());
			    	RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
				}
				
				if("itemId".equals(fdbodypart.getName())){
					boardItem.setItemId(fdbodypart.getValue()); 
					boardItemId = fdbodypart.getValue();
					
					if(!"".equals(boardItemId) && boardItemId != null){
						//이전의 첨부파일 삭제
						//this.fileService.removeItemFile(boardItemId);//Skip 처리
					}
				}
			}
			
			for(BodyPart bp : bodypart) {
				FormDataBodyPart fdbodypart = (FormDataBodyPart) bp;
				FormDataContentDisposition disposition = fdbodypart.getFormDataContentDisposition();
				
				//파일명이 없는것은 Content 로 간주 한다.
				if(disposition.getFileName() == null){
					String name = fdbodypart.getName();
				    String value = fdbodypart.getValue();
				    
				    if("portalId".equals(name)) boardItem.setPortalId(value);
				    if("boardId".equals(name)){
				    	boardItem.setBoardId(value);
						Board board = this.boardAdminService.readBoard(value);
						BoardPermission permission = checkPermission(user, board);
						if ( !permission.getIsWritePermission() ) { //등록권한체크
							Head head = new Head(1, "Error : No permission to write board.");
							result = new Root(head, null);
							//사용자 세션 삭제
							RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
							return result;
						}
				    }
				    
				    if("itemTitle".equals(name)) boardItem.setTitle(value);
				    if("itemContent".equals(name)) boardItem.setContents(value);
				    if("itemParentId".equals(name)){
				    	if("".equals(value) || value == null) boardItem.setItemParentId("0");
				    	else boardItem.setItemParentId(value);
				    }
				    if("itemGroupId".equals(name)) boardItem.setItemGroupId(value);
				    if("itemStep".equals(name)) boardItem.setStep(Integer.parseInt(value));
				    if("itemLevel".equals(name)) boardItem.setItemSeqId(value);
				    
				//첨부파일 업로드
				}else{
					//모바일용 파일처리 SKIP
					/*String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(this.fileService.uploadFile(fileName, is, user));
				    }*/
				}
			}
			
			boardItem.setUser(user);
			boardItem.setAttachFileCount(999);	//모바일용 첨부파일처리 SKIP을 위해서 999 로 셋팅
			boardItem.setItemDisplay(0);
			boardItem.setIndentation(0);
			boardItem.setItemDelete(0);
			
			//모바일용 파일처리 SKIP
			/*if (uploadList != null && uploadList.size() > 0) {
				boardItem.setFileDataList(uploadList);
				
				//FileLinkList 생성
				for(FileData fileData : uploadList){
					FileLink fileLink = new FileLink();
					fileLink.setFileId(fileData.getFileId());
					fileLink.setFileSize(fileData.getFileSize()+"");
					fileLink.setRegisterId(fileData.getRegisterId());
					fileLink.setRegisterName(fileData.getRegisterName());
					fileLink.setUpdaterId(fileData.getUpdaterId());
					fileLink.setUpdaterName(fileData.getUpdaterName());
					fileLink.setFlag("add");
					fileLinkList.add(fileLink);
				}
				boardItem.setFileLinkList(fileLinkList);
			}*/

			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
			
			Date date = Calendar.getInstance().getTime();
			boardItem.setStartDate(date);
			boardItem.setEndDate(date);

			// 포틀릿 아이디 넣기 TagService에서 필요로 함
			boardItem.setPortalId(user.getPortalId());
			
			this.boardItemService.updateBoardItem(boardItem, user);
						
			if ( null != boardItemId && !StringUtil.isEmpty(boardItemId) ) {
				Head head = new Head(0, "success");
				result = new Root(head, null);
			} else {
				Head head = new Head(2, "Error : boardItem update failure.(boardItemId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(3, "Error : " + e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;

    }
	
	/**
	 * 게시글 삭제
	 */
	@POST 
	@Path("/deleteCollItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteCollItem( CollParam boardParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
			
			BoardItem boardItem = this.boardItemService.readBoardItemMasterInfo(boardParam.getItemId());
			
			if ( null == boardItem ) {
				Head head = new Head(1, "Error : boardItem delete failure.(boardItem is not exist)");
				result = new Root(head, null);
			} else if ( !boardItem.getRegisterId().equals(boardParam.getUserId()) && !isSystemAdmin(user) ) {
				Head head = new Head(2, "Error : boardItem delete failure.(no permission)");
				result = new Root(head, null);
			} else {
				
				ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
	
				this.boardItemService.userDeleteBoardItem(boardItem);
				
				if ( null != boardParam.getItemId() && !StringUtil.isEmpty(boardParam.getItemId()) ) {
					Head head = new Head(0, "Success");
					result = new Root(head, null);
				} else {
					Head head = new Head(3, "Error : boardItem delete failure.(boardItemId is null)");
					result = new Root(head, null);
				}
			}
			
		} catch(Exception e) {
			Head head = new Head(4, "Error : " + e.toString());
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
	}
	
	/**
	 * 로그인 사용자가 게시판의 시스템 관리자인지 체크한다.
	 * 
	 * @param user 로그인 사용자 모델 객체
	 * @return 시스템 관리자 여부
	 */
	public boolean isSystemAdmin(User user) {
		return this.aclService.isSystemAdmin("Collaboration", user.getUserId());
	}

	/**
	 * 게시판 쓰기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isWritePermission(User user, String boardId) {
		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "WRITE", boardId, user.getUserId());
	}

	/**
	 * 게시판 읽기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isReadPermission(User user, String boardId) {
		return this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "READ", boardId, user.getUserId());
	}

	/**
	 * 게시물 읽기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isContentsReadPermission(User user, String itemId) {
		return this.aclService.hasContentPermission(Board.BOARD_ITEM_ACL_CLASS_NAME, "READ", itemId, user.getUserId());
	}
	
	/**
	 * 게시물 쓰기 권한 체크
	 * 
	 * @param user
	 * @param boardId
	 * @return
	 */
	public boolean isContentsWritePermission(User user, String itemId) {
		return this.aclService.hasContentPermission(Board.BOARD_ITEM_ACL_CLASS_NAME, "WRITE", itemId, user.getUserId());
	}
	
	
	/**
	 * 게시판 권한 체크
	 * 
	 * @param user
	 * @param board
	 * @return
	 */
	private BoardPermission checkPermission(User user, Board board) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Boolean isWritePermission = this.isWritePermission(user, board.getBoardId());
		Boolean isReadPermission = this.isReadPermission(user, board.getBoardId());

		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
		} else if (isWritePermission) {
			isReadPermission = Boolean.TRUE;
		}

		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}
	
	/**
	 * 게시판 권한 체크
	 * 
	 * @param user
	 * @param board
	 * @return
	 */
	@SuppressWarnings("unused")
	private BoardPermission initPermission(User user, Board board) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);
		Boolean isWritePermission = false;
		Boolean isReadPermission = false;

		/** 시스템 Admin **/
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;
			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}
		
		// 시샵, 운영진 체크
		Member member = new Member();
		member.setWorkspaceId(board.getWorkspaceId());
		member.setMemberId(user.getUserId());

		member = memberService.read(member);
		if(member == null) {
			member = new Member();
			member.setMemberId(user.getUserId());
			member.setBoardId(board.getBoardId());
			member = memberService.readForAlliance(member);
		}

		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
			isSystemAdmin = Boolean.TRUE;
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		/** Write Check **/

		if (board.getWritePermission().equals("1")) {
			isWritePermission = Boolean.TRUE;
		} else {

			if (member != null && !member.getMemberLevel().equals("")) {
				if (board.getWritePermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
																												// 이상
					isWritePermission = true;
				} else if (board.getWritePermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
																														// 이상
					isWritePermission = true;
				} else if (board.getWritePermission().equals("4")) { // 개별 설정
					isWritePermission = this.isWritePermission(user, board.getBoardId());
				}

			}
		}
		// 쓰기권한자에게 쓰기 권한과 읽기 권한을 True 한다.
		if (isWritePermission) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		/** Read Check **/

		if (board.getReadPermission().equals("1")) {
			isReadPermission = Boolean.TRUE;
		} else {
			if (member != null && !member.getMemberLevel().equals("")) {
				if (board.getReadPermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
																												// 이상
					isReadPermission = true;
				} else if (board.getReadPermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
																														// 이상
					isReadPermission = true;
				} else if (board.getReadPermission().equals("4")) { // 개별 설정
					isReadPermission = this.isReadPermission(user, board.getBoardId());
				}

			}
		}
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}
	
	/**
	 * 게시물 권한 체크
	 * 
	 * @param user
	 * @param workspaceId
	 * @param boardItem
	 * @return
	 */
	private BoardPermission initContentsPermission(User user, String workspaceId, BoardItem boardItem) {

		Boolean isSystemAdmin = this.isSystemAdmin(user);

		Boolean isWritePermission = false;
		Boolean isReadPermission = false;

		/** 시스템 Admin **/
		if (isSystemAdmin) {
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}
		// 시샵, 운영진 체크
		Member member = new Member();
		member.setWorkspaceId(workspaceId);
		member.setMemberId(user.getUserId());

		member = memberService.read(member);
		if(member == null) {
			member = new Member();
			member.setMemberId(user.getUserId());
			member.setBoardId(boardItem.getBoardId());
			member = memberService.readForAlliance(member);
		}

		if (member != null && (member.getMemberLevel().equals("1") || member.getMemberLevel().equals("2"))) {
			isSystemAdmin = Boolean.TRUE;
			isWritePermission = Boolean.TRUE;
			isReadPermission = Boolean.TRUE;

			return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
		}

		if (boardItem.getReadPermission().equals("1")) {
			isReadPermission = true;
		} else {
			if (member != null && !member.getMemberLevel().equals("")) {
				if (boardItem.getReadPermission().equals("2") && Integer.parseInt(member.getMemberLevel()) <= 4) { // 정/준회원
																													// 이상
					isReadPermission = true;
				} else if (boardItem.getReadPermission().equals("3") && Integer.parseInt(member.getMemberLevel()) <= 3) { // 정회원
																															// 이상
					isReadPermission = true;
				} else if (boardItem.getReadPermission().equals("4")) { // 개별 설정
					isReadPermission = this.isContentsReadPermission(user, boardItem.getItemId());
				}
				// else
				// isReadPermission = this.isContentsReadPermission(user,
				// boardItem.getItemId());
			}
		}
		return new BoardPermission(isSystemAdmin, isWritePermission, isReadPermission);
	}
	

}
