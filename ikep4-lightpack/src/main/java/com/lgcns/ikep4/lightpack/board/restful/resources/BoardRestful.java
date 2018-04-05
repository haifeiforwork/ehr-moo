package com.lgcns.ikep4.lightpack.board.restful.resources;

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

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.lightpack.board.model.Board;
import com.lgcns.ikep4.lightpack.board.model.BoardItem;
import com.lgcns.ikep4.lightpack.board.model.BoardItemCategory;
import com.lgcns.ikep4.lightpack.board.model.BoardItemReader;
import com.lgcns.ikep4.lightpack.board.model.BoardLinereply;
import com.lgcns.ikep4.lightpack.board.model.BoardReference;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemListBody;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemListHead;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemListReturnData0;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemListReturnData1;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemListReturnData2;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemViewBody;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemViewHead;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemViewReturnData0;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardItemViewReturnData1;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardLineReplyListBody;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardLineReplyListHead;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardLineReplyListReturnData0;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardLineReplyListReturnData1;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardLineReplyListReturnData2;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardListBody;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardListHead;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardListReturnData;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardParam;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardPermissionBody;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardPermissionHead;
import com.lgcns.ikep4.lightpack.board.restful.model.BoardPermissionReturnData;
import com.lgcns.ikep4.lightpack.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.lightpack.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.lightpack.board.service.BoardAdminService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemCategoryService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemReaderService;
import com.lgcns.ikep4.lightpack.board.service.BoardItemService;
import com.lgcns.ikep4.lightpack.board.service.BoardLinereplyService;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.portal.main.model.Portal;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.restful.model.Root;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.BodyPart;
import com.sun.jersey.multipart.BodyPartEntity;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.MultiPart;

@Path("/board")
@Component
public class BoardRestful extends BaseController {
	
	@Autowired
	private ACLService aclService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BoardAdminService boardAdminService;
	
	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private BoardLinereplyService boardLinereplyService;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private BoardItemReaderService boardItemReaderService;
	
	@Autowired
	private BoardItemCategoryService boardItemCategoryService;
	
	
	public boolean checkSystemPermission(String userId) {
		Boolean hasPermission = this.aclService.hasSystemPermission(Board.BOARD_PERMISSION_CLASS_NAME, 
				new String[] {"READ", "WRITE", "MANAGE" }, Board.BOARD_RESOURCE_NAME, userId);

		return hasPermission;
	}
	
	public boolean checkBoardPermission(String userId, String boardId) {
		Boolean hasPermission = this.aclService.hasSystemPermission(Board.BOARD_ACL_CLASS_NAME, "WRITE", boardId, userId);
		
		return hasPermission;
	}
	
	public boolean isSystemAdmin(String userId) {
		return this.aclService.isSystemAdmin("BBS", userId);
	}
	
	/**
	 * 게시글쓰기권한 체크
	 */
	@POST 
	@Path("/retrieveBoardPermit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardPermissionHead retrieveBoardPermit( BoardParam boardParam ) {
		
		BoardPermissionHead result = new BoardPermissionHead();
		
		try {
			
			BoardPermissionReturnData returnData = new BoardPermissionReturnData();
			
			boolean isBBSAdmin = aclService.isSystemAdmin("BBS", boardParam.getUserId());
			
			// 1. 접근권한 체크
			if (isBBSAdmin || checkBoardPermission(boardParam.getUserId(), boardParam.getBoardId()) ) {
				returnData.setIsWritePermit("1");
			}else{
				returnData.setIsWritePermit("0");
			}
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardPermissionBody body = new BoardPermissionBody(returnData);
			result = new BoardPermissionHead(head, body);
	
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new BoardPermissionHead(head, null);
		}
		
		return result;
	}
	
	/**
	 * 게시판 목록 가져오기
	 */
	@POST 
	@Path("/retrieveBoardList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardListHead retrieveBoardList( BoardParam boardParam ) {
		
		BoardListHead result = new BoardListHead();
		
		try {
			List<Board> sourceList = null;
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("portalId", boardParam.getPortalId());
			param.put("userId", boardParam.getUserId());
			param.put("boardRootId", "0");
			param.put("className", "BBS-Board");
			
			sourceList = boardAdminService.listByBoardRootIdPerMobile(param);
			
			List<BoardListReturnData> returnDataList = new ArrayList<BoardListReturnData>();
			
			if(sourceList != null){
				
				User user = userDao.get(boardParam.getUserId());
				Portal portal = portalService.readPortal(boardParam.getPortalId());
				for ( Board board : sourceList ) {
					
					BoardListReturnData returnData = new BoardListReturnData();
					
					returnData.setBoardId			(StringUtil.nvl(board.getBoardId(), ""));
					if(user.getLocaleCode().equals(portal.getDefaultLocaleCode())){
						returnData.setBoardName			(StringUtil.nvl(board.getBoardName(), ""));
					}else{
						returnData.setBoardName			(StringUtil.nvl(board.getBoardEnglishName(), ""));
					}
					returnData.setBoardParentId		(StringUtil.nvl(board.getBoardParentId(), ""));
					returnData.setBoardLevel		(StringUtil.nvl(board.getIndentation().toString(), ""));
					returnData.setBoardRootId		(StringUtil.nvl(board.getBoardRootId(), ""));
					returnData.setHasChildren		(StringUtil.nvl(board.getHasChildren().toString(), ""));
					returnData.setSiblingOrder		(StringUtil.nvl(board.getSortOrder().toString(), ""));
					returnData.setIsReply			(StringUtil.nvl(board.getReply().toString(), ""));
					returnData.setIsComment			(StringUtil.nvl(board.getLinereply().toString(), ""));
					returnData.setBoardType			(StringUtil.nvl(board.getBoardType().toString(), ""));
					//게시판 여부 (0: 게시판 X  1:게시판 O )
					if(board.getBoardType() != null){
						if("2".equals(board.getBoardType()))
							returnData.setIsBoard			("0");
						else{
							returnData.setIsBoard			("1");
						}
					}else{
						returnData.setIsBoard			("0");
					}
					
					
					returnData.setWritePermit		(StringUtil.nvl(board.getWritePermission().toString(), ""));
					
					//게시판 작성권한 : 1 이면 게시판 권한 따름.
					/*if(board.getMobileWriteUse() == 0){
						returnData.setWritePermit("0");
						returnData.setIsReply("0");
						returnData.setIsComment("0");
					}*/
		
					returnDataList.add(returnData);
				}
			}
			Head head = new Head(0, Response.Status.OK.toString());
			BoardListBody body = new BoardListBody(returnDataList);
			result = new BoardListHead(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new BoardListHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 게시물 목록 가져오기
	 */
	@POST 
	@Path("/retrieveBoardItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardItemListHead retrieveBoardItemList( BoardParam boardParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		BoardItemListHead result = new BoardItemListHead();
		String boardId = "";
		
		try {
			
			Boolean isSystemAdmin = false;
	
			isSystemAdmin = this.isSystemAdmin(boardParam.getUserId());
			
			// 2. 데이터 가져오기
			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
			
			searchCondition.setUserId(boardParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			
			searchCondition.setPagePerRecord(boardParam.getListSize());
			searchCondition.setPageIndex(boardParam.getPage());
			searchCondition.setStartRowIndex(boardParam.getPage()-1);
			
			boardId = boardParam.getBoardId();
	
			if ( StringUtil.isEmpty(boardParam.getBoardId()) ) { // 전체 게시판 리스트 중 첫번째 게시판을 가져온다.
				List<Board> bList = boardAdminService.listByBoardRootId("0", boardParam.getPortalId());
				if( bList != null){
					for ( Board b : bList ) {
						if ( "0".equals(b.getBoardType()) ) {
							boardId = b.getBoardId();
							searchCondition.setBoardId(b.getBoardId());
							break;
						}
					}
				}
			} else {
				searchCondition.setBoardId(boardParam.getBoardId());
			}
			
			if ( !StringUtil.isEmpty(boardParam.getSearchWord()) ) {
				searchCondition.setSearchColumn("all"); // 확인필요 : title/contents/registerName/all
				searchCondition.setSearchWord(boardParam.getSearchWord());
			}
			
			SearchResult<BoardItem> searchResult =null;
			searchCondition.setViewMode("0");
			if("100000793108".equals(searchCondition.getBoardId())||"100000793116".equals(searchCondition.getBoardId())){//공문공지이거나 일반공지 이면
				searchResult = this.boardItemService.listBoardItemBySearchCondition2(searchCondition);
			}else{
				searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);
			}
			
			// 3. 데이터 가공 및 JAXB 처리
			List<BoardItemListReturnData0> returnData0 = new ArrayList<BoardItemListReturnData0>();
			
			if(searchResult.getEntity() != null){
				for ( BoardItem item : searchResult.getEntity() ) {
					
					BoardItemListReturnData0 returnItem = new BoardItemListReturnData0();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setItemGroupId(StringUtil.nvl(item.getItemGroupId(), ""));
					returnItem.setItemStep(StringUtil.nvl(item.getStep().toString(), ""));
					returnItem.setItemLevel(StringUtil.nvl(item.getIndentation().toString(), ""));
					returnItem.setItemTitle(StringUtil.nvl(item.getTitle(), ""));
					returnItem.setItemDisplay(StringUtil.nvl(item.getItemDisplay().toString(), ""));
					returnItem.setItemHitCount(StringUtil.nvl(item.getHitCount().toString(), ""));
					returnItem.setItemCommentCount(StringUtil.nvl(item.getLinereplyCount().toString(), ""));
					returnItem.setItemAttachCnt(StringUtil.nvl(item.getAttachFileCount().toString(), ""));
					returnItem.setRegUserName(StringUtil.nvl(item.getRegisterName(), ""));
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
					returnItem.setWordId(StringUtil.nvl(item.getWordId(), ""));
					returnItem.setWordName(StringUtil.nvl(item.getWordName(), ""));
		
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
			BoardItemCategory categoryBoardId = new BoardItemCategory();
			categoryBoardId.setBoardId(boardId);
			List<BoardItemCategory> categoryList = boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
			List<BoardItemListReturnData2> returnData2 = new ArrayList<BoardItemListReturnData2>();
			
			if(categoryList.size()>0){
				
				for ( BoardItemCategory item : categoryList ) {
					
					BoardItemListReturnData2 returnItem = new BoardItemListReturnData2();
					
					returnItem.setCategoryId(StringUtil.nvl(item.getCategoryId(), ""));
					returnItem.setCategoryName(StringUtil.nvl(item.getCategoryName(), ""));
		
					returnData2.add(returnItem);
				}
			}
			
			
			BoardItemListReturnData1 returnData1 = new BoardItemListReturnData1(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardItemListBody body = new BoardItemListBody(returnData0, returnData1,returnData2);
			result = new BoardItemListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new BoardItemListHead(head, null);
		}
		
		return result;
		
	}
	
	
	/**
	 * 게시글 상세 조회
	 */
	@POST 
	@Path("/retrieveBoardItemDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardItemViewHead retrieveBoardItemDetail( BoardParam boardParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		BoardItemViewHead result = new BoardItemViewHead();
		
		boolean isSystemAdmin = this.isSystemAdmin(boardParam.getUserId());
		
		// 1. 접근권한 체크
		/*if ( !checkBoardPermission(boardParam.getUserId(),boardParam.getBoardId())) {
			Head head = new Head(1, "No permission.");
			result = new BoardItemViewHead(head, null);

			return result;
		}*/
		
		try {
			
			// 2. 데이터 가져오기
			// 조회 카운트를 증가 시킨다.
			BoardReference boardReference = new BoardReference();
			boardReference.setRegisterId(boardParam.getUserId());
			boardReference.setItemId(boardParam.getItemId());
			
			this.boardItemService.updateHitCount(boardReference);
			
			// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
			BoardItem boardItem = this.boardItemService.readBoardItem(boardParam.getItemId());

			if (boardItem == null) {
				throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
			}

			Date toDate = new Date();
			User user = userDao.get(boardParam.getUserId());
			RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			
			
			Board board = this.boardAdminService.readBoard(boardItem.getBoardId());
			
			String readerView ="";
			if(board.getContentsReadPermission()==1){//독서자 설정 게시판이고 
				BoardItemReader boardItemReader =new BoardItemReader();
				boardItemReader.setBoardItemId(boardItem.getItemId());
				boardItemReader.setReaderId(user.getUserId());
			
				if(this.boardItemReaderService.selectReaderCount(boardItemReader)!=0){//독서자 설정이 없으면 독서자 체크하지 않는다.

					if( ( this.boardItemReaderService.readerFlag(boardItemReader)==0 ) &&  ( !isSystemAdmin ) && ( !(boardItem.getRegisterId()).equals(user.getUserId()) ) ){//독서자, 등록자가 아니면
		
						Head head = new Head(1, "No permission");
						result = new BoardItemViewHead(head, null);

						return result;
					}
				}
			}
			
			// 삭제 게시물에 대한 처리
			if (!(boardItem.getItemDelete() == 0 || boardItem.getItemDelete() == 3) ) {
				throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.deletedItem");
			}

			// 게시기간이 지난 아이템의 처리
			if (boardItem.getEndDate().getTime() < toDate.getTime() && !boardItem.getRegisterId().equals(user.getUserId())) {
				throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.expiredItem");
			}

			// 게시기간이 시작되지 않은 아이템의 처리
			if (boardItem.getStartDate().getTime() > toDate.getTime() && !boardItem.getRegisterId().equals(user.getUserId())) {
				throw new IKEP4AuthorizedException(messageSource, "message.lightpack.common.boardItem.preItem");
			}
			
			//이전 게시글의 정보 조회
			BoardItem prevItem = this.boardItemService.readPrevBoardItem(boardParam.getBoardId(), boardParam.getItemId());
			//다음 게시글의 정보 조회
			BoardItem nextItem = this.boardItemService.readNextBoardItem(boardParam.getBoardId(), boardParam.getItemId());
			
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

			returnData0.setWordId(StringUtil.nvl(boardItem.getWordId(), ""));
			returnData0.setWordName(StringUtil.nvl(boardItem.getWordName(), ""));
			
			returnData0.setRegDate(DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy.MM.dd HH:mm"));
			returnData0.setItemContent(StringUtil.nvl(boardItem.getContents(), ""));
			returnData0.setItemHitCount(boardItem.getHitCount());
			returnData0.setItemReplyCount(boardItem.getReplyCount());
			returnData0.setItemCommentCount(boardItem.getLinereplyCount());
			
			if(boardItem.getAnonymous() != null){
				returnData0.setIsAnonymous(boardItem.getAnonymous());
			}else{
				returnData0.setIsAnonymous(1);
			}
			
			//이전 게시글의 정보 설정
			if(prevItem != null){
				returnData0.setPreItemTitle(StringUtil.nvl(prevItem.getTitle(), ""));
				returnData0.setPreItemId(StringUtil.nvl(prevItem.getItemId(), ""));
				returnData0.setPreItemRepCount(prevItem.getLinereplyCount());
			}else{
				returnData0.setPreItemTitle("");
				returnData0.setPreItemId("");
				returnData0.setPreItemRepCount(0);
			}
			
			//다음 게시글의 정보 설정
			if(nextItem != null){
				returnData0.setNexItemTitle(StringUtil.nvl(nextItem.getTitle(), ""));
				returnData0.setNexItemId(StringUtil.nvl(nextItem.getItemId(), ""));
				returnData0.setNexItemReplyCount(nextItem.getLinereplyCount());
			}else{
				returnData0.setNexItemTitle("");
				returnData0.setNexItemId("");
				returnData0.setNexItemReplyCount(0);
			}
			
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
			
			/*List<BoardItemCategory> boardItemCategoryList = null;
			BoardItemCategory categoryBoardId = new BoardItemCategory();
			categoryBoardId.setBoardId(board.getBoardId());
			boardItemCategoryList = boardItemCategoryService.listCategoryBoardItem(categoryBoardId);
			
			List<BoardItemViewReturnData2> returnData2 = new ArrayList<BoardItemViewReturnData2>();
			
			for(BoardItemCategory category : boardItemCategoryList){

				BoardItemViewReturnData2 returnCategoryData = new BoardItemViewReturnData2();
				returnCategoryData.setCategoryId(category.getCategoryId());
				returnCategoryData.setCategoryName(category.getCategoryName());
				returnData2.add(returnCategoryData);
			}*/

			Head head = new Head(0, Response.Status.OK.toString());
			BoardItemViewBody body = new BoardItemViewBody(returnData0, returnData1);
			result = new BoardItemViewHead(head, body);
		
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(1, "Error");
			result = new BoardItemViewHead(head, null);
		}
		
		return result;
		
	}

	
	/**
	 * 게시글 등록 (첨부파일 포함)
	 */
	@POST 
	@Path("/insertBoardItem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root insertBoardItem(MultiPart multipart) throws Exception {
		
		Root result = new Root();
		BoardItem boardItem = new BoardItem();
		List<FileData> uploadList = new ArrayList<FileData>();
		List<FileLink> fileLinkList = new ArrayList<FileLink>();
		User user = null;
		
		try{
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
				    if("boardId".equals(name)) boardItem.setBoardId(value);
				    if("itemTitle".equals(name)) boardItem.setTitle(value);
				    if("itemContent".equals(name)) boardItem.setContents(value);
				    if("itemParentId".equals(name)){
				    	if("".equals(value) || value == null) boardItem.setItemParentId("0");
				    	else boardItem.setItemParentId(value);
				    }
				    if("itemGroupId".equals(name)) boardItem.setItemGroupId(value);
//				    if("itemStep".equals(name)) boardItem.setStep(Integer.parseInt(value));
//				    if("itemLevel".equals(name)) boardItem.setItemSeqId(value);
				    if("categoryId".equals(name)) boardItem.setWordId(value);
				    if("categoryName".equals(name)) boardItem.setWordName(value);
				//첨부파일 업로드
				}else{
					String fileName = URLDecoder.decode(disposition.getFileName(), "utf8");
					BodyPartEntity bpEntity = (BodyPartEntity)fdbodypart.getEntity();
				    InputStream is = bpEntity.getInputStream();
				    
				    if(fileName != null && !"".equals(fileName)){
					    //첨부파일 저장
						uploadList.add(fileService.uploadFile(fileName, is));
				    }
				}
			}
			
			boardItem.setMsie(0);
			boardItem.setUser(user);
			boardItem.setAttachFileCount(0);
			boardItem.setItemDisplay(0);
			boardItem.setIndentation(0);
			boardItem.setItemDelete(0);
			boardItem.setStep(0);
			
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
			boardItem.setToDate(date);
			boardItem.setTempSave("0");
			
			Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

			// #게시기간 단위 설정 (year,month,day)
			String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
			
			// #게시기간 설정
			int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));
			boardItem.setDisStartDate(date);
			boardItem.setDisEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));

			
			if ("year".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));
			} else if ("month".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			} else if ("day".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(date, itemPeriod));
			} else {
				boardItem.setEndDate(DateUtils.addYears(date, 1));
			}
			
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
			
			String boardItemId = null; 
			boardItemId = boardItemService.createBoardItem(boardItem,user);
			
			// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");
						
			if ( null != boardItemId && !StringUtil.isEmpty(boardItemId) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : boardItem create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
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
	@Path("/updateBoardItem")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces({MediaType.APPLICATION_JSON})
	public Root updateBoardItem(MultiPart multipart) throws Exception {
		
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
				    if("boardId".equals(name)) boardItem.setBoardId(value);
				    if("itemTitle".equals(name)) boardItem.setTitle(value);
				    if("itemId".equals(name)) boardItem.setItemId(value);
				    if("itemContent".equals(name)) boardItem.setContents(value);
				    if("itemParentId".equals(name)){
				    	if("".equals(value) || value == null) boardItem.setItemParentId("0");
				    	else boardItem.setItemParentId(value);
				    }
				    if("itemGroupId".equals(name)) boardItem.setItemGroupId(value);
//				    if("itemStep".equals(name)) boardItem.setStep(Integer.parseInt(value));
//				    if("itemLevel".equals(name)) boardItem.setItemSeqId(value);
				    if("categoryId".equals(name)) boardItem.setWordId(value);
				    if("categoryName".equals(name)) boardItem.setWordName(value);
				}
			}
			
			boardItem.setMsie(0);
			boardItem.setUser(user);
			boardItem.setAttachFileCount(999);	//모바일용 첨부파일처리 SKIP을 위해서 999 로 셋팅
			boardItem.setItemDisplay(0);
			boardItem.setIndentation(0);
			boardItem.setItemDelete(0);
			boardItem.setTempSave("0");
			boardItem.setStep(0);
			
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());
			
			Date date = Calendar.getInstance().getTime();
			boardItem.setStartDate(date);
			boardItem.setToDate(date);

			Properties boardprop = PropertyLoader.loadProperties("/configuration/board-conf.properties");

			// #게시기간 단위 설정 (year,month,day)
			String itemPeriodType = boardprop.getProperty("lightpack.board.boardItem.itemPeriodType");
			// #게시기간 설정
			int itemPeriod = Integer.valueOf(boardprop.getProperty("lightpack.board.boardItem.itemPeriod"));

			if ("year".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addYears(date, itemPeriod), -1));
			} else if ("month".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(DateUtils.addMonths(date, itemPeriod), -1));
			} else if ("day".equals(itemPeriodType)) {
				boardItem.setEndDate(DateUtils.addDays(date, itemPeriod));
			} else {
				boardItem.setEndDate(DateUtils.addYears(date, 1));
			}

			// 포틀릿 아이디 넣기 TagService에서 필요로 함
			boardItem.setPortalId(user.getPortalId());
			
			this.boardItemService.updateBoardItem(boardItem, user);
			
			// 최근 게시물 포틀릿 contents 캐시 Element 전체 삭제
			cacheService.removeCacheElementPortletContentAll("Cachename-recentBoard-portlet");
						
			if ( null != boardItemId && !StringUtil.isEmpty(boardItemId) ) {
				Head head = new Head(0, "success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : boardItem update failure.(BoardItemId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
				
		return result;

    }
	
	/**
	 * 게시글 삭제
	 * (return 값이 Head 만 있는 경우)
	 */
	@POST 
	@Path("/deleteBoardItem")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteBoardItem( BoardParam boardParam ) {

		Root result = new Root();

		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			BoardItem boardItem = new BoardItem();
			
			boardItem.setPortalId(boardParam.getPortalId());
			boardItem.setItemId(boardParam.getItemId());
			boardItem.setUpdaterId(boardParam.getUserId());
				
			ModelBeanUtil.bindRegisterInfo(boardItem, user.getUserId(), user.getUserName());

			//boardItemService.userDeleteBoardItem(boardItem);
			boardItemService.adminDeleteBoardItem(boardItem);
			
			if ( null != boardParam.getItemId() && !StringUtil.isEmpty(boardParam.getItemId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : boardItem delete failure.(BoardItemId is null)");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	
	/**
	 * 댓글 목록 조회
	 * (returnData가 여러개일 경우의 예)
	 */
	@POST 
	@Path("/retrieveBoardLineReplyList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public BoardLineReplyListHead retrieveBoardLineReplyList( BoardParam boardParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		BoardLineReplyListHead result = new BoardLineReplyListHead();
		

		try {
			
			// 2. 데이터 가져오기
			BoardLinereplySearchCondition searchCondition = new BoardLinereplySearchCondition();
			
			if ( StringUtil.isEmpty(boardParam.getBoardId()) ) { // 전체 게시판 리스트 중 첫번째 게시판을 가져온다.
				List<Board> bList = boardAdminService.listByBoardRootId("0", boardParam.getPortalId());
				for ( Board b : bList ) {
					if ( "0".equals(b.getBoardType()) ) {
						searchCondition.setBoardId(b.getBoardId());
						break;
					}
				}
			} else {
				searchCondition.setBoardId(boardParam.getBoardId());
			}
			
			searchCondition.setItemId(boardParam.getItemId());
			searchCondition.setPagePerRecord(boardParam.getListSize());
			searchCondition.setPageIndex(boardParam.getPage());
			searchCondition.setStartRowIndex(boardParam.getPage()-1);
			
			SearchResult<BoardLinereply> searchResult = this.boardLinereplyService.listBoardLinereplyBySearchCondition(searchCondition);
			
			// 3. 데이터 가공 및 JAXB 처리
			BoardLineReplyListReturnData0 returnData0 = new BoardLineReplyListReturnData0(searchResult.getRecordCount());
			
			List<BoardLineReplyListReturnData1> returnData1 = new ArrayList<BoardLineReplyListReturnData1>();
			
			if(searchResult.getEntity() != null){
				for ( BoardLinereply item : searchResult.getEntity() ) {
					
					BoardLineReplyListReturnData1 returnItem = new BoardLineReplyListReturnData1();
	
					returnItem.setItemId(boardParam.getItemId());
					returnItem.setCommentId(StringUtil.nvl(item.getLinereplyId(),""));
					returnItem.setCommentContent(StringUtil.nvl(item.getContents(),""));
					returnItem.setRegUserId(StringUtil.nvl(item.getRegisterId(),""));
					returnItem.setRegUserName(StringUtil.nvl(item.getRegisterName(),""));
					
					User user = userDao.get(item.getRegisterId());
					returnItem.setRegUserDept(StringUtil.nvl(user.getTeamName(),""));
					returnItem.setRegUserTitle(StringUtil.nvl(user.getJobTitleName(),""));
					
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
	
					if ( null == item.getLinereplyParentId() || StringUtil.isEmpty(item.getLinereplyParentId()) ) {
						returnItem.setCommentParentId("0");	
					} else {
						returnItem.setCommentParentId(item.getLinereplyParentId());
					}
					
					if ( null == item.getLinereplyGroupId() || StringUtil.isEmpty(item.getLinereplyGroupId()) ) {
						returnItem.setCommentGroupId("0");	
					} else {
						returnItem.setCommentGroupId(item.getLinereplyGroupId());
					}
		
					returnData1.add(returnItem);
				}
			}
			BoardLineReplyListReturnData2 returnData2 = new BoardLineReplyListReturnData2(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			BoardLineReplyListBody body = new BoardLineReplyListBody(returnData0, returnData1, returnData2);
			result = new BoardLineReplyListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new BoardLineReplyListHead(head, null);
		}
		
		return result;
		
	}
	
	
	/**
	 * 댓글 등록
	 * (return 값이 Head 만 있는 경우의 예)
	 */
	@POST 
	@Path("/insertBoardLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root insertBoardLineReply( BoardParam boardParam ) {

		Root result = new Root();

		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(boardParam.getItemId());
			boardLinereply.setContents(boardParam.getCommentContent());
	
			ModelBeanUtil.bindRegisterInfo(boardLinereply, user.getUserId(), user.getUserName());

			boardLinereplyService.createBoardLinereply(boardLinereply);
			
			if ( null != boardLinereply.getLinereplyId() && !StringUtil.isEmpty(boardLinereply.getLinereplyId()) ) {
				Head head = new Head(0, "Success");
				result = new Root(head, null);
			} else {
				Head head = new Head(1, "Error : BoardLineReply create failure.");
				result = new Root(head, null);
			}
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	/**
	 * 댓글 수정
	 * (return 값이 Head 만 있는 경우의 예)
	 */
	@POST 
	@Path("/updateBoardLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateBoardLineReply( BoardParam boardParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
			
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(boardParam.getItemId());
			boardLinereply.setLinereplyId(boardParam.getCommentId());
			boardLinereply.setContents(boardParam.getCommentContent());
			
			BoardLinereply readBoardLinereply = this.boardLinereplyService.readBoardLinereply(boardLinereply.getLinereplyId());

			readBoardLinereply.setContents(boardParam.getCommentContent());

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
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}
	
	/**
	 * 댓글 삭제
	 * (return 값이 Head 만 있는 경우)
	 */
	@POST 
	@Path("/deleteBoardLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteBoardLineReply( BoardParam boardParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
		
			BoardLinereply boardLinereply = new BoardLinereply();
			
			boardLinereply.setItemId(boardParam.getItemId());
			boardLinereply.setLinereplyId(boardParam.getCommentId());
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
			
		} catch(Exception e) {
			Head head = new Head(2, "Error");
			result = new Root(head, null);
		}
		
		//사용자 세션 삭제
		RequestContextHolder.currentRequestAttributes().removeAttribute("ikep.user", RequestAttributes.SCOPE_SESSION);
		
		return result;
		
	}

}
