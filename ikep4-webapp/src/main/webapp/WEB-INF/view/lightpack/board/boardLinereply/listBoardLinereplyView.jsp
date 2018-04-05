<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preList"  value="ui.lightpack.board.boardLinereply.listBoardLinereplyView" />
<c:set var="preButton"  value="ui.lightpack.common.button" /> 
<c:set var="preMessage" value="message.lightpack.common.boardLinereply" />
<c:set var="preSearch"  value="ui.ikep4.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--  


(function($){	 
	$(document).ready(function() {   
		$("div.blockComment_rewrite").hide();   
	});  
	
	/*
	*  답변 댓글  폼을 화면에서 보이게 한다.
	*/	
	showReplyForm = function(linereplyId) {  
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide();
		$("#" + linereplyId).find("textarea[name=contents]").val("");  
		$("#" + linereplyId).show();
		$("#" + linereplyId).find("textarea[name=contents]").focus();
		return false;  
	};

	/*
	*  댓글 수정 폼을 화면에서 보이게 한다.
	*/	
	showUpdateForm =function(linereplyId) { 
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide(); 
		
		var contextObjet = $("#" + linereplyId).siblings("p[name=contents]");  
		var contextText = $(contextObjet).text();   
		$("#" + linereplyId).find("textarea[name=contents]").val(contextText); 
		$("#" + linereplyId).show(); 
		$("#" + linereplyId).find("textarea[name=contents]").focus();
		return false;  
	};

	/*
	*  신규 답변을 저장한다.
	*/	
	createLinereply = function() {    
		var contents = $("textarea[name=contents]","#boardLinereplyForm").val();  
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("<ikep4j:message key="NotNull.boardLinereplyItem.content"/>");
			return false;
		} 
		
		if(contents.length > 1000) {
			alert("댓글은 최소 1자 ~ 최대 1000자 내에 입력하셔야 합니다.");
			return false;
		}  
		$("#blockComment").ajaxLoadStart(); 
		
		$.post('<c:url value="/lightpack/board/boardLinereply/createBoardLinereply.do"/>', $("#boardLinereplyForm").serialize())
		.success(function(data) { 
			loadBoardLinereplyList(); 
		})
		.error(function(event, request, settings) { alert("error"); $("#blockComment").ajaxLoadComplete(); }); 
		
		return false;  
	};

	/*
	*  댓글의 답글을 저장한다. 
	*/
	createReplyLinereply = function(linereplyId) { 
		debugger;
		var formObject = $("#" + linereplyId).children("form");  
		
		var contents = $("textarea[name=contents]",formObject).val();  
		
		if(contents == null || contents.replace(/\s/g, "").length == 0 ) {
			alert("<ikep4j:message key="NotNull.boardLinereplyItem.content"/>");
			return false;
		} 
		
		if(contents.length > 1000) {
			alert("댓글은 최소 1자 ~ 최대 1000자 내에 입력하셔야 합니다.");
			return false;
		} 
		$("#blockComment").ajaxLoadStart();
		
		$.post('<c:url value="/lightpack/board/boardLinereply/createReplyBoardLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardLinereplyList();
		})
		.error(function(event, request, settings) { alert("error"); $("#blockComment").ajaxLoadComplete();});
	 
		return false;  
	}; 

	/*
	*  댓글 수정을 한다. 
	*/
	updateLinereply = function(linereplyId) { 
		var formObject = $("#" + linereplyId).children("form"); 
		
		var contents = $("textarea[name=contents]",formObject).val(); 
		
		if(contents == null || contents.replace(/\s/g, "").length == 0) {
			alert("<ikep4j:message key="NotNull.boardLinereplyItem.content"/>");
			return false;
		}
		
		if(contents.length > 1000) {
			alert("댓글은 최소 1자 ~ 최대 1000자 내에 입력하셔야 합니다.");
			return false;
		}
		$("#blockComment").ajaxLoadStart();
		
		$.post('<c:url value="/lightpack/board/boardLinereply/updateBoardLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadBoardLinereplyList();
		})
		.error(function(event, request, settings) { alert("error"); }); 
	    
		return false;  
	};
			
	/*
	*  댓글 수정 폼을 화면에서 안보이게 한다.
	*/
	closeUpdateLinereplyForm = function(linereplyId) {  
		$("#" + linereplyId ).hide();
		return false;  
	};

	/*
	*  답글 댓글 폼을 화면에서 안보이게 한다.
	*/
	closeReplyLinereplyForm = function(linereplyId) { 
		$("#" + linereplyId ).hide();
		return false;  
	};			
			
	/*
	*  작성자 모드로 댓글을 삭제한다.
	*/		
	userDeleteBoardLinereply = function(itemId, linereplyId) { 
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) { 
			$("#blockComment").ajaxLoadStart();
		    $.post("<c:url value='/lightpack/board/boardLinereply/userDeleteBoardLinereply.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId}) 
		    .success(function(data) { 
		    	loadBoardLinereplyList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); $("#blockComment").ajaxLoadComplete();});
		}  
		else
		{
			$("#blockComment").ajaxLoadComplete();
		}
	    
		return false;  
	};

	/*
	*  관리자 모드로 댓글을 삭제한다.
	*/	
	adminDeleteBoardLinereply = function(itemId, linereplyId) { 
		$("#blockComment").ajaxLoadStart();
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
		    $.post("<c:url value='/lightpack/board/boardLinereply/adminDeleteBoardLinereply.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId})  
		    .success(function(data) { 
		    	loadBoardLinereplyList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); $("#blockComment").ajaxLoadComplete(); });
		}  
		else
		{
			$("#blockComment").ajaxLoadComplete();
		}
	    
		return false;  
	};	
	
	$("input[name=contents]").keypress(function(event) {
		if(event.which == '13') {
			$(this).parents("form:eq(0)").find("a.sumbit").click();
		}
	});
	
})(jQuery); 
//-->
</script>		 
	<div class="blockComment_t"> 
		<ikep4j:message pre='${preList}' key='linereply' /> <span class="comment_num"><c:if test="${not searchResult.emptyRecord}">(${boardItem.linereplyCount})</c:if></span>  
	</div>  
	<c:if test="${board.linereply eq 1 or isSystemAdmin}"> <%-- 답글쓰기 가능 여부 --%>	
		<div class="guestbook_write"> 
			<form id="boardLinereplyForm" onsubmit="return false;">
				<spring:bind path="searchCondition.itemId">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>  
				<table summary="<ikep4j:message pre='${preList}' key='createTable' />"> 
					<caption></caption>
					<tr>
						<td>
							<textarea name="contents" title="<ikep4j:message pre='${preList}' key='lineReplyInput' />" cols="" rows="3" ></textarea>
						</td>
						<td width="74" class="textRight"> 
							<a href="#a" onclick="createLinereply();" class="button_re" ><span><ikep4j:message pre='${preButton}' key='create'/></span></a> 
						</td> 
					</tr>
				</table> 
			</form>
		</div>    
	</c:if>
	<c:forEach var="boardLinereply" varStatus="varStatus" items="${searchResult.entity}"> 
		<c:choose>
			<c:when test="${not varStatus.first}"> 
				<c:if test="${preIndentation-boardLinereply.indentation > 1}">
					<c:forEach begin="${boardLinereply.indentation}" end="${preIndentation-1}"></div></c:forEach> 
				</c:if>
				<c:if test="${preIndentation-boardLinereply.indentation < 2}">
					<c:forEach begin="${boardLinereply.indentation}" end="${preIndentation}"></div></c:forEach> 
				</c:if>
			</c:when> 
		</c:choose>	 
		<c:choose>
	 		<c:when test="${boardLinereply.indentation == 0 }">
				<div name="boardLinereplyItem" class="blockComment_c"> 
			</c:when>
			<c:otherwise>
				<div name="boardLinereplyItem"  class="blockComment_re ">
				<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar.gif"/>" alt="" /></div>
			</c:otherwise>
		</c:choose>	  
			<!-- 무림제지 기능 막음 2012.11.7
		    <div class="blockCommentPhoto">  
				<c:choose>
					<c:when test="${board.anonymous eq 1}">
						<img src="<c:url value='/base/images/common/photo_130x95.gif'/>" alt="Image"/>
					</c:when>  
					<c:otherwise>  
						 <a href="#a" class="${varStatus.first ? 'firstLineItem' : ''}"><img src="<c:url value='${boardLinereply.user.profilePicturePath}'/>" alt="Image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a>					 
					</c:otherwise> 	 
				</c:choose> 
			</div>
			  -->		    	
		    <div class="blockCommentInfo">
		    	<span class="blockCommentInfo_name">
					<c:choose>
						<c:when test="${board.anonymous eq 1}">
							<span><ikep4j:message pre='${preList}' key='anonymous'/></span>
						</c:when>  
						<c:otherwise>  
							<c:set var="user"   value="${sessionScope['ikep.user']}" />
							<c:set var="portal" value="${sessionScope['ikep.portal']}" />
							<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
									<a href="#a" onclick="javascript:viewPopUpProfile('${boardLinereply.registerId}')">${boardLinereply.user.userName} ${boardLinereply.user.jobTitleName}</a>
								</c:when>
								<c:otherwise> 
									<a href="#a" onclick="javascript:viewPopUpProfile('${boardLinereply.registerId}')">${boardLinereply.user.userEnglishName} ${boardLinereply.user.jobTitleEnglishName}</a>
								</c:otherwise>
							</c:choose>							 
						</c:otherwise> 	 
					</c:choose> 
		    	</span>
		    	<span>
		    		<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
							${boardLinereply.user.teamName}
						</c:when>
						<c:otherwise> 
							${boardLinereply.user.teamEnglishName}
						</c:otherwise>
					</c:choose>
		    	</span>
		    	<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
		    	<span><ikep4j:timezone date="${boardLinereply.registDate}" pattern="yyyy.MM.dd HH:mm:ss"/></span> &nbsp; 
		    	<span class="rebtn">
					<c:choose>
						<c:when test="${isSystemAdmin}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
		 					<a class="ic_delete" href="#a" onclick="adminDeleteBoardLinereply('${boardLinereply.itemId}', '${boardLinereply.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
							<a class="ic_modify"  href="#a" onclick="showUpdateForm('${boardLinereply.linereplyId}UpdateForm');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
						</c:when>   
						<c:when test="${boardLinereply.registerId eq user.userId and board.linereply eq 1}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
		 					<a class="ic_delete" href="#a" onclick="userDeleteBoardLinereply('${boardLinereply.itemId}', '${boardLinereply.linereplyId}');" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
							<a class="ic_modify" href="#a" onclick="showUpdateForm('${boardLinereply.linereplyId}UpdateForm');" title="<ikep4j:message pre='${preButton}' key='update'/>"><span><ikep4j:message pre='${preButton}' key='update'/></span></a>
						</c:when>    
					</c:choose> 
					<c:if test="${boardLinereply.indentation lt 2 and (board.linereply eq 1 or isSystemAdmin)}">
		    			<a class="ic_reply" href="#a" onclick="showReplyForm('${boardLinereply.linereplyId}ReplyForm');" title="<ikep4j:message pre='${preButton}' key='createLinereply'/>"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>	 
					</c:if>
		    	</span>
		    </div> 
			<c:choose>
		 		<c:when test="${boardLinereply.linereplyDelete eq 1}">
		 			<p><span class="deletedItem"><ikep4j:message pre='${preList}' key='contents' post="deleteContents"/></span></p> 
				</c:when>
				<c:otherwise>
					<% pageContext.setAttribute("newLineChar", "\n"); %> 
					<p name="contents">${fn:replace(boardLinereply.contents, newLineChar, '<br/>')}</p>  
					<!--blockComment_rewrite Start--> 
					<div id="${boardLinereply.linereplyId}UpdateForm"  name="updateForm" class="blockComment_rewrite modify"> 
						<form name="updateForm" onsubmit="return false;"> 
							<input name="linereplyId" type="hidden" value="${boardLinereply.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
							
						    <table summary="<ikep4j:message pre='${preList}' key='updateTable' />"> 
								<caption></caption> 
								<tr> 
									<td> 
										<textarea name="contents" title="<ikep4j:message pre='${preList}' key='lineReplyInput' />" cols="" rows="3" ></textarea> 
									</td> 
									<td width="95" class="textRight"> 
										<ul> 
											<li><a class="button_s sumbit" href="#a" onclick="updateLinereply('${boardLinereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
											<li><a class="button_s" href="#a" onclick="closeUpdateLinereplyForm('${boardLinereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
										</ul> 
									</td>  
								</tr>  
							</table> 
						</form>
					</div> 
					<!--//blockComment_rewrite End--> 
				</c:otherwise>
			</c:choose>	  
			<!--blockComment_rewrite Start-->
			<div id="${boardLinereply.linereplyId}ReplyForm" name="replyForm" class="blockComment_rewrite">
				<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif'/>" alt="" /></div>  
				<form name="replyForm" onsubmit="return false;">  
					<input name="linereplyParentId" type="hidden" value="${boardLinereply.linereplyId}" title="<ikep4j:message pre='${preList}'  key='linereplyParentId'/>" />			
					<table summary="<ikep4j:message pre='${preList}' key='replyTable' />">
						<caption></caption>
						<tr>
							<td>  
								<textarea name="contents" title="<ikep4j:message pre='${preList}' key='lineReplyInput' />" cols="" rows="3" ></textarea> 
							</td>
							<td width="95" class="textRight">
								<ul>
									<li><a class="button_s sumbit" href="#a" onclick="createReplyLinereply('${boardLinereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
									<li><a class="button_s" href="#a" onclick="closeReplyLinereplyForm('${boardLinereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> 
								</ul>
							</td>
						</tr> 
					</table> 	 
				</form>
			</div>  
		</form>	 
		<c:choose>
	 		<c:when test="${varStatus.last}">
				</div>
			</c:when>  
			<c:when test="${not(varStatus.first or varStatus.last)}"> 
				<c:set var="preIndentation" value="${boardLinereply.indentation}"/>
			</c:when> 
		</c:choose>	  		
	</c:forEach> 
	<!--Page Numbur Start--> 
	<form id="boardLinereplySearchForm" method="post" onsubmit="return false;"> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="boardLinereplySearchForm" ajaxEventFunctionName="loadBoardLinereplyList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" displayYn="true"/>
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->  
	</form> 
<!--//blockComment End--> 