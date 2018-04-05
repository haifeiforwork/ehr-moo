<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.readManualView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<c:set var="preSearch">ui.collpack.workmanual.common.searchCondition</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){  
		$("div.blockComment_rewrite").hide();  

	});   

	
	//답변 댓글  폼 보이게
	showReplyForm = function(linereplyId) {  
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide();
		$("#" + linereplyId).find("input[name=linereplyContents]").val("");  
		$("#" + linereplyId).show();
	};
	//댓글의 답글 숨기기
	closeReplyLinereplyForm = function(linereplyId) { 
		$("#" + linereplyId).hide();
	};

	//답변 댓글 수정 폼 보이게
	showUpdateForm =function(linereplyId) { 
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide(); 
		
		var contextObjet = $("#" + linereplyId).siblings("p[name=linereplyContents]");  
		var contextText = $(contextObjet).text();   
		$("#" + linereplyId).find("input[name=linereplyContents]").val(contextText); 
		$("#" + linereplyId).show();
	};
	//댓글 수정 폼을 화면에서 안보이게 한다.
	closeUpdateLinereplyForm = function(linereplyId) {  
		$("#" + linereplyId ).hide();
		return false;  
	};
	

	//댓글 저장	
	createLinereply = function() {   
		$.post('<c:url value="/collpack/workmanual/createLinereply.do"/>', $("#linereplyForm").serialize())
		.success(function(data) { 
			loadLinereplyList(); 
		})
		.error(function(event, request, settings) { alert("error"); });
	};	
	
	//댓글의 답글 
	createReplyLinereply = function(linereplyId) {   
		var formObject = $("#" + linereplyId).children("form");
		$.post('<c:url value="/collpack/workmanual/createReplyLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadLinereplyList();
		})
		.error(function(event, request, settings) { alert("error"); });
	}; 
	
	//댓글 및 답글 수정을 한다. 
	updateLinereply = function(linereplyId) { 
		var formObject = $("#" + linereplyId).children("form"); 
		
		$.post('<c:url value="/collpack/workmanual/updateLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadLinereplyList();
		})
		.error(function(event, request, settings) { alert("error"); });
	};

	//작성자 모드로 글 삭제
	deleteLinereplyByUser = function(manualId, linereplyId) { 
		if(confirm("<ikep4j:message pre='${messagePrefix}' key='comfirm.delete' />")) {
		    $.post("<c:url value='/collpack/workmanual/deleteLinereplyByUser.do'/>", {"manualId" : manualId , "linereplyId" : linereplyId}) 
		    .success(function(data) { 
		    	loadLinereplyList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); });
		}
	};

	//관리자 모드로 글 삭제
	deleteLinereplyByAdmin = function(manualId, linereplyId) { 
		if(confirm("<ikep4j:message pre='${messagePrefix}' key='comfirm.delete' />")) {
		    $.post("<c:url value='/collpack/workmanual/deleteLinereplyByAdmin.do'/>", {"manualId" : manualId , "linereplyId" : linereplyId}) 
		    .success(function(data) { 
		    	loadLinereplyList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); });
		}
	}; 
	
})(jQuery);
//]]>
</script>


<!--blockComment Start-->  

	<div class="blockComment_t"> 
		<ikep4j:message pre="${prefix}" key="body.reply"/><span class="comment_num">(${searchCondition.recordCount})</span>  
	</div> 
	
	<%-- 답글쓰기  --%> 
	<div class="guestbook_write"> 
		<form id="linereplyForm" action="">
			<spring:bind path="searchCondition.manualId">
			<input type="hidden" name="${status.expression}" value="${status.value}"/>
			</spring:bind>		
			<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>"> 
				<caption></caption>
				<tr>
					<td>
						<textarea name="linereplyContents" title="<ikep4j:message pre="${prefix}" key="body.input"/>" cols="" rows="3" ></textarea>
					</td>
					<td width="74" class="textRight"> 
						<a href="#a" onclick="createLinereply();" class="button_re" ><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a> 
					</td> 
				</tr>
			</table>
			<div class="guestbook_write_num"></div>
		</form>
	</div>
	
	<c:forEach var="linereply" items="${searchResult.entity}" varStatus="varStatus"> 
		<c:choose>
			<c:when test="${not varStatus.first}"> 
				<c:forEach begin="${linereply.indentation}" end="${preIndentation}"></div></c:forEach>  
			</c:when> 
		</c:choose>	 
		<c:choose>
	 		<c:when test="${linereply.indentation == 0}">
				<div class="blockComment_c"> 
			</c:when>
			<c:otherwise>
				<div class="blockComment_re ">
				<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar.gif"/>" alt=""/></div>
			</c:otherwise>
		</c:choose>	  
		    <div class="blockCommentPhoto"><a href="#a"><img src="<c:url value='${linereply.profilePicturePath}'/>" alt="image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div> 
		    <div class="blockCommentInfo">
		    	<span class="blockCommentInfo_name">
		    		<c:choose>
					    <c:when test="${user.localeCode == portal.defaultLocaleCode}">${linereply.registerName}&nbsp;${linereply.jobTitleName}</c:when>
					    <c:otherwise>${linereply.registerEnglishName}&nbsp;${linereply.jobTitleEnglishName}</c:otherwise>
				    </c:choose>
		    	</span>
		    	<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />						
		    	<span>${linereply.teamName}</span>
		    	<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
		    	<span><ikep4j:timezone date="${linereply.registDate}" pattern="yyyy.MM.dd HH:mm"/></span> 
		    	<span class="rebtn">  
 		    		<c:if test="${adminYn == 'Y'}"><%-- 관리자  삭제, 수정--%>
		    			<a class="ic_delete" href="#a" onclick="deleteLinereplyByAdmin('${linereply.manualId}', '${linereply.linereplyId}');"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a>
						<a class="ic_modify" href="#a" onclick="showUpdateForm('${linereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a>
		    		</c:if>  
		    		<c:if test="${linereply.registerId == user.userId && linereply.isDelete == 0}"><%-- 작성자  삭제, 수정 --%>
		    			<a class="ic_delete" href="#a" onclick="deleteLinereplyByAdmin('${linereply.manualId}', '${linereply.linereplyId}');"><span><ikep4j:message pre='${buttonPrefix}' key='delete'/></span></a>
						<a class="ic_modify" href="#a" onclick="showUpdateForm('${linereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${buttonPrefix}' key='modify'/></span></a>  		
		    		</c:if>
		    		<c:if test="${linereply.indentation < 2 && linereply.isDelete == 0}"><%-- depth가 3이면 답글 작성 불가 --%>
		    			<a class="ic_reply" href="#a" onclick="showReplyForm('${linereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${buttonPrefix}' key='lineRreply.save'/></span></a>
					</c:if>		 	 
		    	</span>
		    </div> 
		    
			<c:choose>
		 		<c:when test="${linereply.isDelete == 1}">
		 			<p><span class="deletedItem"><ikep4j:message pre='${prefix}' key='body.deleteContents'/></span></p> 
				</c:when>
				<c:otherwise> 
					<p name="linereplyContents">${linereply.linereplyContents}</p> 
					<!--blockComment_rewrite Start--> 
					<div id="${linereply.linereplyId}UpdateForm" name="updateForm" class="blockComment_rewrite modify"> 
						<form name="updateForm" action=""> 
							<input type="hidden" name="linereplyId" value="${linereply.linereplyId}"/> 
						    <table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>"> 
								<caption></caption> 
								<tr> 
									<td> 
										<input name="linereplyContents" title="<ikep4j:message pre="${prefix}" key="body.input"/>" class="inputbox" type="text" value=""/> 
									</td> 
									<td width="95" class="textRight"> 
										<ul> 
											<li><a class="button_ic" href="#a" onclick="updateLinereply('${linereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a></li>
											<li><a class="button_ic" href="#a" onclick="closeUpdateLinereplyForm('${linereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${buttonPrefix}' key='cancel'/></span></a></li> 
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
			<div id="${linereply.linereplyId}ReplyForm" name="replyForm" class="blockComment_rewrite">
				<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif'/>" alt="" /></div>  
				<form name="replyForm" action=""> 
					<input type="hidden" name="manualId"          value="${linereply.manualId}"         />
					<input type="hidden" name="linereplyId"       value="${linereply.linereplyId}"      />
					<input type="hidden" name="linereplyParentId" value="${linereply.linereplyId}"      />
					<input type="hidden" name="linereplyGroupId"  value="${linereply.linereplyGroupId}" />
					<input type="hidden" name="step"              value="${linereply.step + 1}"         />
					<input type="hidden" name="indentation"       value="${linereply.indentation + 1}"  />		
					<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
						<caption></caption>
						<tr>
							<td>  
								<input name="linereplyContents" title="<ikep4j:message pre="${prefix}" key="body.input"/>" class="inputbox" type="text" value=""/> 
							</td>
							<td width="95" class="textRight">
								<ul>
									<li><a class="button_ic" href="#a" onclick="createReplyLinereply('${linereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a></li>
									<li><a class="button_ic" href="#a" onclick="closeReplyLinereplyForm('${linereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${buttonPrefix}' key='cancel'/></span></a></li> 
								</ul>
							</td>
						</tr> 
					</table> 	 
				</form>
			</div>  
		</form>	 
		<c:choose>
	 		<c:when test="${varStatus.last}">
				<c:forEach begin="0" end="${linereply.indentation}">
					</div>	
				</c:forEach>
			</c:when>  
			<c:when test="${not(varStatus.first or varStatus.last)}"> 
				<c:set var="preIndentation" value="${linereply.indentation}"/>
			</c:when> 
		</c:choose>	  		
	</c:forEach> 
	
	<!--Page Numbur Start--> 
	<form id="linereplySearchForm" action="" method="post"> 
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchFormId="linereplySearchForm" ajaxEventFunctionName="loadLinereplyList" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->  
	</form>
