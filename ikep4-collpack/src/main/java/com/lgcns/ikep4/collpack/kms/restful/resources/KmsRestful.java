package com.lgcns.ikep4.collpack.kms.restful.resources;

import java.util.ArrayList;
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

import com.lgcns.ikep4.collpack.kms.board.model.Board;
import com.lgcns.ikep4.collpack.kms.board.model.BoardItem;
import com.lgcns.ikep4.collpack.kms.board.model.BoardLinereply;
import com.lgcns.ikep4.collpack.kms.board.model.BoardPermission;
import com.lgcns.ikep4.collpack.kms.board.model.BoardRecommend;
import com.lgcns.ikep4.collpack.kms.board.search.BoardItemSearchCondition;
import com.lgcns.ikep4.collpack.kms.board.search.BoardLinereplySearchCondition;
import com.lgcns.ikep4.collpack.kms.board.service.BoardAdminService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardItemService;
import com.lgcns.ikep4.collpack.kms.board.service.BoardLinereplyService;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemListBody;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemListHead;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemListReturnData0;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemListReturnData1;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemListReturnData2;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemViewBody;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemViewHead;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemViewReturnData0;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsItemViewReturnData1;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsLineReplyListBody;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsLineReplyListHead;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsLineReplyListReturnData0;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsLineReplyListReturnData1;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsLineReplyListReturnData2;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsListBody;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsListHead;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsListReturnData;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsParam;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsPermissionBody;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsPermissionHead;
import com.lgcns.ikep4.collpack.kms.restful.model.KmsPermissionReturnData;
import com.lgcns.ikep4.framework.common.exception.IKEP4AuthorizedException;
import com.lgcns.ikep4.framework.web.BaseController;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.portal.admin.screen.service.PortalService;
import com.lgcns.ikep4.support.cache.service.CacheService;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.service.FileService;
import com.lgcns.ikep4.support.restful.model.Head;
import com.lgcns.ikep4.support.restful.model.Root;
import com.lgcns.ikep4.support.security.acl.service.ACLService;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.support.user.member.model.User;
import com.lgcns.ikep4.support.user.member.service.UserService;
import com.lgcns.ikep4.util.PropertyLoader;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.lang.StringUtil;
import com.lgcns.ikep4.util.model.ModelBeanUtil;

@Path("/kms")
@Component
public class KmsRestful extends BaseController {
	
	@Autowired
	private ACLService aclService;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PortalService portalService;
	
	@Autowired
	private FileService fileService;

	@Autowired
	private CacheService cacheService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BoardItemService boardItemService;
	
	@Autowired
	private BoardAdminService boardAdminService;
	
	@Autowired
	private BoardLinereplyService boardLinereplyService;
	
	private String[] kmsLoginIds;
	private String kmsRedirectUrl;
	private Properties kmsProperty;
	
	/**
	 * 카테고리 목록 가져오기
	 */
	@POST 
	@Path("/kmsCategoryList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsListHead kmsCategoryList( KmsParam kmsParam ) {
		
		KmsListHead result = new KmsListHead();
		
		try {
			
			
			
			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("isKnowhow", kmsParam.getIsKnowhow());
			boardMap.put("boardId", kmsParam.getBoardId());
			List<Board> boardList = null;
			boardList = boardAdminService.listChildrenBoard(boardMap);
			
			List<KmsListReturnData> returnDataList = new ArrayList<KmsListReturnData>();
			
			if(boardList != null){
				
				for ( Board board : boardList ) {
					
					KmsListReturnData returnData = new KmsListReturnData();
					returnData.setBoardId			(StringUtil.nvl(board.getBoardId(), ""));
					returnData.setBoardName			(StringUtil.nvl(board.getBoardName(), ""));
					returnData.setBoardParentId		(StringUtil.nvl(board.getBoardParentId(), ""));
					returnData.setBoardRootId		(StringUtil.nvl(board.getBoardRootId(), ""));
					returnData.setHasChildren		(StringUtil.nvl(board.getHasChildren().toString(), ""));
					returnData.setSiblingOrder		(StringUtil.nvl(board.getSortOrder().toString(), ""));
					returnData.setIsReply			(StringUtil.nvl(board.getReply().toString(), ""));
					returnData.setIsComment			(StringUtil.nvl(board.getLinereply().toString(), ""));
					returnData.setBoardType			(StringUtil.nvl(board.getBoardType().toString(), ""));
					returnData.setIsKnowhow			(StringUtil.nvl(board.getIsKnowhow().toString(), ""));
		
					returnDataList.add(returnData);
				}
			}
			Head head = new Head(0, Response.Status.OK.toString());
			KmsListBody body = new KmsListBody(returnDataList);
			result = new KmsListHead(head, body);
		
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsListHead(head, null);
		}
				
		return result;
	}
	
	/**
	 * 우수지식 목록 가져오기
	 */
	@POST 
	@Path("/excellentKmsItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsItemListHead excellentKmsItemList( KmsParam kmsParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		KmsItemListHead result = new KmsItemListHead();
		String boardId = "";
		
		try {
			
			boolean isSystemAdmin = this.isSystemAdmin(kmsParam.getUserId());
			
			User tmpUser =   userService.read(kmsParam.getUserId());
	
			
			// 2. 데이터 가져오기
			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
			
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setPagePerRecord(10);	
			searchCondition.setInfoGrage(tmpUser.getInfoGrade());
			searchCondition.setViewMode("0");
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setSearchWord(kmsParam.getSearchWord());
			searchCondition.setSearchColumn("title");
			searchCondition.setPagePerRecord(kmsParam.getListSize());
			searchCondition.setPageIndex(kmsParam.getPage());
			searchCondition.setStartRowIndex(kmsParam.getPage()-1);
			
			SearchResult<BoardItem> searchResult = boardItemService.excellenceListBySearchCondition(searchCondition);
			
			// 3. 데이터 가공 및 JAXB 처리
			List<KmsItemListReturnData0> returnData0 = new ArrayList<KmsItemListReturnData0>();
			
			if(searchResult.getEntity() != null){
				for ( BoardItem item : searchResult.getEntity() ) {
					
					KmsItemListReturnData0 returnItem = new KmsItemListReturnData0();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setBoardId(StringUtil.nvl(item.getBoardId(), ""));
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
					returnItem.setAssessDate(DateUtil.getFmtDateString(item.getAssessDate(), "yyyy.MM.dd HH:mm:ss"));
					returnItem.setColor(StringUtil.nvl(item.getColor(), ""));
					returnItem.setIsKnowhow(StringUtil.nvl(item.getIsKnowhow().toString(), ""));
					returnItem.setStatus(StringUtil.nvl(item.getStatus().toString(), ""));
					if(!isSystemAdmin && item.getIsKnowhow() == 1 ){
						returnItem.setIsAnonymous(1);
					}else{
						returnItem.setIsAnonymous(0);
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
			List<KmsItemListReturnData2> returnData2 = new ArrayList<KmsItemListReturnData2>();
			
			KmsItemListReturnData1 returnData1 = new KmsItemListReturnData1(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			KmsItemListBody body = new KmsItemListBody(returnData0, returnData1,returnData2);
			result = new KmsItemListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsItemListHead(head, null);
		}
		
		return result;
		
	}
	
	/**
	 * 핫이슈 목록 가져오기
	 */
	@POST 
	@Path("/hotissueKmsItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsItemListHead hotissueKmsItemList( KmsParam kmsParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		KmsItemListHead result = new KmsItemListHead();
		String boardId = "";
		
		try {
			
			boolean isSystemAdmin = this.isSystemAdmin(kmsParam.getUserId());
			
			User tmpUser =   userService.read(kmsParam.getUserId());
	
			
			// 2. 데이터 가져오기
			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
			
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setPagePerRecord(10);	
			searchCondition.setInfoGrage(tmpUser.getInfoGrade());
			searchCondition.setViewMode("0");
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setIsKnowhow("1");
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setSearchWord(kmsParam.getSearchWord());
			searchCondition.setSearchColumn("title");
			searchCondition.setPagePerRecord(kmsParam.getListSize());
			searchCondition.setPageIndex(kmsParam.getPage());
			searchCondition.setStartRowIndex(kmsParam.getPage()-1);
			
			SearchResult<BoardItem> searchResult = boardItemService.hotissueListBySearchCondition(searchCondition);
			
			// 3. 데이터 가공 및 JAXB 처리
			List<KmsItemListReturnData0> returnData0 = new ArrayList<KmsItemListReturnData0>();
			
			if(searchResult.getEntity() != null){
				for ( BoardItem item : searchResult.getEntity() ) {
					
					KmsItemListReturnData0 returnItem = new KmsItemListReturnData0();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setBoardId(StringUtil.nvl(item.getBoardId(), ""));
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
					returnItem.setAssessDate(DateUtil.getFmtDateString(item.getAssessDate(), "yyyy.MM.dd HH:mm:ss"));
					returnItem.setColor(StringUtil.nvl(item.getColor(), ""));
					returnItem.setIsKnowhow(StringUtil.nvl(item.getIsKnowhow().toString(), ""));
					returnItem.setStatus(StringUtil.nvl(item.getStatus().toString(), ""));
					if(!isSystemAdmin && item.getIsKnowhow() == 1 ){
						returnItem.setIsAnonymous(1);
					}else{
						returnItem.setIsAnonymous(0);
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
			List<KmsItemListReturnData2> returnData2 = new ArrayList<KmsItemListReturnData2>();
			
			KmsItemListReturnData1 returnData1 = new KmsItemListReturnData1(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			KmsItemListBody body = new KmsItemListBody(returnData0, returnData1,returnData2);
			result = new KmsItemListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsItemListHead(head, null);
		}
		
		return result;
		
	}
	
	/**
	 * 게시글 목록 가져오기
	 */
	@POST 
	@Path("/kmsItemList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsItemListHead kmsItemList( KmsParam kmsParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		KmsItemListHead result = new KmsItemListHead();
		String boardId = "";
		
		try {
			
			boolean isSystemAdmin = this.isSystemAdmin(kmsParam.getUserId());
			
			User tmpUser =   userService.read(kmsParam.getUserId());
	
			
			// 2. 데이터 가져오기
			BoardItemSearchCondition searchCondition = new BoardItemSearchCondition();
			
			searchCondition.setBoardId(kmsParam.getBoardId());
			
			searchCondition.setIsKnowhow(kmsParam.getIsKnowhow());

			//System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> board.getIsKnowhow() " + board.getIsKnowhow());
			

			if (searchCondition.getViewMode() == null) {
				searchCondition.setViewMode("0");
			}
			
			if(searchCondition.getSearchStartRegDate() == null){
				searchCondition.setSearchStartRegDate("");
			}
			if(searchCondition.getSearchEndRegDate() == null){
				searchCondition.setSearchEndRegDate("");
			}
			
			if(searchCondition.getSearchStartRegDate() == "" && searchCondition.getSearchEndRegDate() != "" ){
				searchCondition.setSearchRegType("2");
			}
			if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() == "" ){
				searchCondition.setSearchRegType("1");
			}
			if(searchCondition.getSearchStartRegDate() != "" && searchCondition.getSearchEndRegDate() != "" ){
				searchCondition.setSearchRegType("0");
			}
			
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setInfoGrage(tmpUser.getInfoGrade());
			searchCondition.setViewMode("0");
			searchCondition.setUserId(kmsParam.getUserId());
			searchCondition.setAdmin(isSystemAdmin);
			searchCondition.setSearchWord(kmsParam.getSearchWord());
			searchCondition.setSearchColumn("title");
			searchCondition.setPagePerRecord(kmsParam.getListSize());
			searchCondition.setPageIndex(kmsParam.getPage());
			searchCondition.setStartRowIndex(kmsParam.getPage()-1);
			searchCondition.setSortColumn("ASSESS_DATE");
			searchCondition.setSortType("DESC");
			
			SearchResult<BoardItem> searchResult = this.boardItemService.listBoardItemBySearchCondition(searchCondition);
			
			// 3. 데이터 가공 및 JAXB 처리
			List<KmsItemListReturnData0> returnData0 = new ArrayList<KmsItemListReturnData0>();
			
			if(searchResult.getEntity() != null){
				for ( BoardItem item : searchResult.getEntity() ) {
					
					KmsItemListReturnData0 returnItem = new KmsItemListReturnData0();
					
					returnItem.setItemId(StringUtil.nvl(item.getItemId(), ""));
					returnItem.setBoardId(StringUtil.nvl(item.getBoardId(), ""));
					returnItem.setItemGroupId(StringUtil.nvl(item.getItemGroupId(), ""));
					returnItem.setItemStep(StringUtil.nvl(item.getStep().toString(), ""));
					returnItem.setItemLevel(StringUtil.nvl(item.getIndentation().toString(), ""));
					returnItem.setItemTitle(StringUtil.nvl(item.getTitle(), ""));
					returnItem.setItemDisplay(StringUtil.nvl(item.getItemDisplay().toString(), ""));
					returnItem.setItemHitCount(StringUtil.nvl(item.getHitCount().toString(), ""));
					returnItem.setItemCommentCount(StringUtil.nvl(item.getLinereplyCount().toString(), ""));
					returnItem.setItemAttachCnt(StringUtil.nvl(item.getAttachFileCount().toString(), ""));
					returnItem.setIsKnowhow(StringUtil.nvl(item.getIsKnowhow().toString(), ""));
					returnItem.setRegUserName(StringUtil.nvl(item.getRegisterName(), ""));
					returnItem.setStatus(StringUtil.nvl(item.getStatus().toString(), ""));
					returnItem.setRegDate(DateUtil.getFmtDateString(item.getRegistDate(), "yyyy.MM.dd HH:mm:ss"));
					if(item.getAssessDate() != null){
						returnItem.setAssessDate(DateUtil.getFmtDateString(item.getAssessDate(), "yyyy.MM.dd HH:mm:ss"));
					}else{
						returnItem.setAssessDate("");
					}
					returnItem.setColor(StringUtil.nvl(item.getColor(), ""));
					if(!isSystemAdmin && item.getIsKnowhow() == 1 ){
						returnItem.setIsAnonymous(1);
					}else{
						returnItem.setIsAnonymous(0);
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
			List<KmsItemListReturnData2> returnData2 = new ArrayList<KmsItemListReturnData2>();
			
			KmsItemListReturnData1 returnData1 = new KmsItemListReturnData1(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			KmsItemListBody body = new KmsItemListBody(returnData0, returnData1,returnData2);
			result = new KmsItemListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsItemListHead(head, null);
		}
		
		return result;
		
	}
	
	/**
	 * 게시글 상세 조회
	 */
	@POST 
	@Path("/kmsItemDetail")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsItemViewHead kmsItemDetail( KmsParam kmsParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		KmsItemViewHead result = new KmsItemViewHead();
		
		boolean isSystemAdmin = this.isSystemAdmin(kmsParam.getUserId());
		
		
		try {
			
			
			// 게시글 정보를 가져온다. 파일정보와 관련 글 정보가 포함되어 있다.
			BoardItem boardItem = this.boardItemService.readBoardItem(kmsParam.getItemId());

			if (boardItem == null) {
				throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
			}
			User user = userDao.get(kmsParam.getUserId());
			//게시물 맵정보를 가져온다.
			Board board = new Board();
			
			Map<String, String> boardMap = new HashMap<String, String>();
			boardMap.put("isKnowhow", boardItem.getIsKnowhow().toString());
			boardMap.put("boardId", boardItem.getBoardId());

			board.setParentList(this.boardAdminService.listParentBoard(boardMap));
			String kmsMapName = this.boardAdminService.getKmsMapName(boardItem.getBoardId());
			
			BoardPermission permission = initKmsPermission(user, boardItem);
			//게시글 조회의 권한이 있는지 없는지 확인
			if( !permission.getIsReadPermission()) {
				throw new IKEP4AuthorizedException();
			}
			
			// 조회 카운트를 증가 시킨다.
			this.boardItemService.updateHitCount(user.getUserId(), kmsParam.getItemId());

			// 삭제 게시물에 대한 처리
			if (boardItem.getItemDelete() > 0 && !this.isSystemAdmin(user.getUserId())) {
				throw new IKEP4AuthorizedException(messageSource, "message.collpack.collaboration.common.boardItem.deletedItem");
			}

			Date toDate = new Date();
			
			RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
			
			
			
			//게시판 글 상세정보
			KmsItemViewReturnData0 returnData0 = new KmsItemViewReturnData0();
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
			returnData0.setBoardId(StringUtil.nvl(boardItem.getBoardId(), ""));
			returnData0.setRegDate(DateUtil.getFmtDateString(boardItem.getRegistDate(), "yyyy.MM.dd HH:mm"));
			if(boardItem.getAssessDate() == null){
				returnData0.setAssessDate("");
			}else{
				returnData0.setAssessDate(DateUtil.getFmtDateString(boardItem.getAssessDate(), "yyyy.MM.dd HH:mm"));
			}
			
			returnData0.setItemContent(StringUtil.nvl(boardItem.getContents(), ""));
			returnData0.setItemHitCount(boardItem.getHitCount());
			returnData0.setItemReplyCount(boardItem.getReplyCount());
			returnData0.setItemCommentCount(boardItem.getLinereplyCount());
			returnData0.setRecommendCount(boardItem.getRecommendCount());
			returnData0.setMark(boardItem.getMark());
			if(StringUtil.isEmpty(boardItem.getScore())){
				returnData0.setScore("0");
			}else{
				returnData0.setScore(boardItem.getScore());
			}
			returnData0.setIsKnowhow(StringUtil.nvl(boardItem.getIsKnowhow().toString(), ""));
			if(boardItem.getInfoGrade() == "A"){
				returnData0.setInfoGrade("보안");
			}else if(boardItem.getInfoGrade() == "B"){
				returnData0.setInfoGrade("보안");
			}else{
				returnData0.setInfoGrade("공유");
			}
			if(!isSystemAdmin && boardItem.getIsKnowhow() == 1 ){
				returnData0.setIsAnonymous(1);
			}else{
				returnData0.setIsAnonymous(0);
			}
			
			
			List<KmsItemViewReturnData1> returnData1 = new ArrayList<KmsItemViewReturnData1>();
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
						
						KmsItemViewReturnData1 returnAttachData = new KmsItemViewReturnData1();
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
			KmsItemViewBody body = new KmsItemViewBody(returnData0, returnData1);
			result = new KmsItemViewHead(head, body);
		
		} catch(Exception e) {e.printStackTrace();
			Head head = new Head(1, "Error");
			result = new KmsItemViewHead(head, null);
		}
		
		return result;
		
	}
	
	/**
	 * 댓글 목록 조회
	 * (returnData가 여러개일 경우의 예)
	 */
	@POST 
	@Path("/kmsLineReplyList")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsLineReplyListHead kmsLineReplyList( KmsParam boardParam ) {
		
		// 접속 로그 남기기 : 추후 추가 
		
		KmsLineReplyListHead result = new KmsLineReplyListHead();
		

		try {
			boolean isSystemAdmin = this.isSystemAdmin(boardParam.getUserId());
			// 2. 데이터 가져오기
			BoardLinereplySearchCondition searchCondition = new BoardLinereplySearchCondition();
			
			searchCondition.setBoardId(boardParam.getBoardId());
			searchCondition.setItemId(boardParam.getItemId());
			searchCondition.setPagePerRecord(boardParam.getListSize());
			searchCondition.setPageIndex(boardParam.getPage());
			searchCondition.setStartRowIndex(boardParam.getPage()-1);
			
			
			SearchResult<BoardLinereply> searchResult = this.boardLinereplyService.listBoardLinereplyBySearchCondition(searchCondition);
			
			// 3. 데이터 가공 및 JAXB 처리
			KmsLineReplyListReturnData0 returnData0 = new KmsLineReplyListReturnData0(searchResult.getRecordCount());
			
			List<KmsLineReplyListReturnData1> returnData1 = new ArrayList<KmsLineReplyListReturnData1>();
			
			if(searchResult.getEntity() != null){
				for ( BoardLinereply item : searchResult.getEntity() ) {
					
					KmsLineReplyListReturnData1 returnItem = new KmsLineReplyListReturnData1();
	
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
					if(!isSystemAdmin && boardParam.getIsKnowhow().equals("1") ){
						returnItem.setIsAnonymous(1);
					}else{
						returnItem.setIsAnonymous(0);
					}
		
					returnData1.add(returnItem);
				}
			}
			KmsLineReplyListReturnData2 returnData2 = new KmsLineReplyListReturnData2(searchCondition.getPageCount(), searchResult.getRecordCount());
			
			Head head = new Head(0, Response.Status.OK.toString());
			KmsLineReplyListBody body = new KmsLineReplyListBody(returnData0, returnData1, returnData2);
			result = new KmsLineReplyListHead(head, body);
		
		} catch(Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsLineReplyListHead(head, null);
		}
		
		return result;
		
	}
	
	
	/**
	 * 댓글 등록
	 * (return 값이 Head 만 있는 경우의 예)
	 */
	@POST 
	@Path("/insertKmsLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root insertKmsLineReply( KmsParam boardParam ) {

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
	@Path("/updateKmsLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateKmsLineReply( KmsParam boardParam ) {

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
	@Path("/deleteKmsLineReply")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root deleteKmsLineReply( KmsParam boardParam ) {

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
	
	@POST 
	@Path("/updateRecommend")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Root updateRecommend( KmsParam boardParam ) {

		Root result = new Root();
		
		// Activity Stream 데이터 등록시 user 정보가 필요하기 때문에 userId로 사용자 정보를 가져와 세션에 추가한다.
		User user = userDao.get(boardParam.getUserId());
		RequestContextHolder.currentRequestAttributes().setAttribute("ikep.user", user, RequestAttributes.SCOPE_SESSION);
		
		try {
			// 게시물 추천을 한다.
			BoardRecommend boardRecommend = new BoardRecommend();

			boardRecommend.setItemId(boardParam.getItemId());
			boardRecommend.setRegisterId(boardParam.getUserId());
			if(user.getJobClassCode().equals("11")){
				boardRecommend.setScore("3");
			}else if(user.getJobClassCode().equals("21")){
				boardRecommend.setScore("2");
			}else{
				boardRecommend.setScore("1");
			}
			boardRecommend.setFlag("2");

			// 이미 존재하면 -1를 리턴한다.
			if (boardItemService.exsitRecommend(boardRecommend)) {
				Head head = new Head(1, "이미 추천한 게시물입니다.");
				result = new Root(head, null);
			} else {
				
				boardItemService.updateRecommendCount(boardRecommend);
				boardItemService.updateScore(boardRecommend);

				Head head = new Head(0, "Success");
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
	 * 권한 체크
	 */
	@POST 
	@Path("/kmsPermit")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public KmsPermissionHead kmsPermit( KmsParam boardParam ) {
		
		KmsPermissionHead result = new KmsPermissionHead();
		
		try {
			
			KmsPermissionReturnData returnData = new KmsPermissionReturnData();
			
			
			// 1. 접근권한 체크
			if (isSystemAdmin(boardParam.getUserId())) {
				returnData.setIsAdminPermit("1");
				returnData.setIsReadPermit("1");
			}else{
				returnData.setIsAdminPermit("0");
				returnData.setIsReadPermit("0");
			}
			
			Map<String, String> map = new HashMap<String, String>();
			map.put("userId", boardParam.getUserId());
			map.put("roleName", "ACL0");
			int kmsRole = userDao.getUserRoleCheck(map);
			if(kmsRole > 0){
				returnData.setIsReadPermit("1");
			}
			
			Head head = new Head(0, Response.Status.OK.toString());
			KmsPermissionBody body = new KmsPermissionBody(returnData);
			result = new KmsPermissionHead(head, body);
	
		} catch (Exception e) {
			Head head = new Head(1, "Error");
			result = new KmsPermissionHead(head, null);
		}
		
		return result;
	}
	
	public boolean isSystemAdmin(String userId) {

		boolean isSystemAdmin = aclService.isSystemAdmin(Board.KMS_MANAGER, userId);

		return isSystemAdmin;
	}
	
	private BoardPermission initKmsPermission(User user, BoardItem boardItem) {

		BoardPermission permission = new BoardPermission(this.isSystemAdmin(user.getUserId()), false, false);
		
		/** 등록자 **/
		if(user.getUserId().equals(boardItem.getRegisterId())){
			permission.setIsWritePermission(true);
			permission.setIsReadPermission(true);
		}else{
		
			/** 사무직이 아닐때 **/
			String jobClassCode = user.getJobClassCode();
			if("11".equals(jobClassCode) || "12".equals(jobClassCode) || "21".equals(jobClassCode) || "22".equals(jobClassCode)|| "24".equals(jobClassCode)|| "31".equals(jobClassCode) || checkKmsExceptionId(user.getUserId())){
				permission.setIsReadPermission(true);
				
				/** 등급별 게시물 **/
				String userGrade = user.getInfoGrade();
				String infoGrade = boardItem.getInfoGrade();
				
				if("C".equals(userGrade) && ("A".equals(infoGrade) || "B".equals(infoGrade)) ){
					permission.setIsReadPermission(false);
				}
				if("B".equals(userGrade) && "A".equals(infoGrade) ){
					permission.setIsReadPermission(false);
				}
			
			}
		}
		
		
		/** 시스템 Admin **/
		if (isSystemAdmin(user.getUserId())) {
			permission.setIsWritePermission(true);
			permission.setIsReadPermission(true);
		}

		return permission;
	}
	
	private boolean checkKmsExceptionId(String userId) {
		
		if(kmsProperty == null){
			kmsProperty = PropertyLoader.loadProperties("/configuration/kms-except-login-id.properties");			
			//kmsLoginIds = kmsProperty.getProperty("kms.login.id").split(","); 
			kmsRedirectUrl = kmsProperty.getProperty("kms.redirect.url");
		}
		/*
		for(String exceptIds : kmsLoginIds){
			
			if(exceptIds.equals(userId)){
				return true;
			}			
		}*/
		
		Map<String, String> emap = new HashMap<String, String>();
		emap.put("userId", userId);
		emap.put("roleName", "COKM");
		int cokmRole = userDao.getUserRoleCheck(emap);
		if(cokmRole > 0){
			return true;
		}
		return false;
	}
}
