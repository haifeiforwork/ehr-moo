<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preWebCommon"  value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preList"       value="ui.socialpack.socialblog.socialBoardItem.readBoardView.listBoardLinereply" />
<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardLinereply" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" /> 

 <%-- 메시지 관련 Prefix 선언 End --%>
 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 
<script type="text/javascript">
<!--  
(function($){
	
	var validatorPool = [];
	var validOptionsBlogLineReply = { 
		rules : {
			contents : {
				required : true,
				maxlength : 150
			}
		},
		messages : {
			contents : {
				required : "<ikep4j:message key='NotNull.socialBoardLinereply.contents' />",
				maxlength : "<ikep4j:message key='Size.socialBoardLinereply.contents' />"
			}
		},
		notice : {
			contents : {
				message : "<ikep4j:message key='Size.socialBoardLinereply.contents' />"
			}
	    },
		submitHandler : function(form) {

			var $linereplyFlag = $.data(form, "linereplyFlag");
			// 신규 답글 CREATE
			if( $linereplyFlag == 'CREATE' ){
				$jq.ajax({
					url : "<c:url value='/socialpack/socialblog/createSocialBoardLinereply.do'/>" ,
					data : $(form).serialize(),
					type : "post",
					success : function(result) {
						//var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
						var $divBlockComment = $.data(form, "div_blockComment_first");
						var itemId = $(form).find("input[name=itemId]").val();
						loadSocialBoardLinereplyList($divBlockComment,itemId); 
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $.data(form, "validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});	
				
			// 답글에 답글을 저장
			}else if( $linereplyFlag == 'RECREATE' ){
					$jq.ajax({
						url : "<c:url value='/socialpack/socialblog/createReplySocialBoardLinereply.do'/>" ,
						data : $(form).serialize(),
						type : "post",
						success : function(result) {
							//var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
							var $divBlockComment = $.data(form, "div_blockComment_first");
							var itemId = $(form).find("input[name=itemId]").val();
							loadSocialBoardLinereplyList($divBlockComment,itemId); 
						},
						error : function(xhr, exMessage) {
							var errorItems = $.parseJSON(xhr.responseText).exception;
							var validatorId = $.data(form, "validatorId")-1;
							var validator = validatorPool[validatorId];
							validator.showErrors(errorItems);
						}
					});	
				
			// 답글 수정 UPDATE
			}else{
				
				$jq.ajax({
					url : "<c:url value='/socialpack/socialblog/updateSocialBoardLinereply.do'/>" ,
					data : $(form).serialize(),
					type : "post",
					success : function(result) {
						//var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
						var $divBlockComment = $.data(form, "div_blockComment_first");
						var itemId = $(form).find("input[name=itemId]").val();
						loadSocialBoardLinereplyList($divBlockComment,itemId); 
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $.data(form, "validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});	
			}		
		}
	}
	
	$(document).ready(function() {   
		$("div.blockComment_rewrite").hide();  
	});   

	/*
	*  댓글 폼을 화면에서 보이게 한다.
	*
	showReplyForm = function(linereplyId) { 
		
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide();
		$("#" + linereplyId).find("input[name=contents]").val("");  
		$("#" + linereplyId).show();
		
		var $form = $("form", $("#" + linereplyId)); 	    	
    	validatorPool.push(new iKEP.Validator($form, validOptionsBlogLineReply));
    	var $btn = $form.find(".textRight").children("a:eq(0)");
    	$.data($form[0], "div_blockComment_first", $btn.parents("div.blockComment:first") );
    	$btn.click(function(){
			$form.trigger("submit");
		});
    	$.data($form[0], "linereplyFlag", "CREATE");
    	$.data($form[0], "validatorId", validatorPool.length);
    	iKEP.checkLength(".contents", updateCheck);
    	
		return false;  
	};
	*/
	
	/*
	*  답변 댓글  폼을 화면에서 보이게 한다.
	*/	
	showReplyForm = function(linereplyId) { 
		$("div[name=updateForm]").hide(); 
		$("div[name=replyForm]").hide();
		$("#" + linereplyId).find("input[name=contents]").val("");  
		$("#" + linereplyId).show();
		
		var $form = $("form", $("#" + linereplyId)); 	    	
    	validatorPool.push(new iKEP.Validator($form, validOptionsBlogLineReply));
    	var $btn = $form.find(".textRight").children("a:eq(0)");
    	$.data($form[0], "div_blockComment_first", $btn.parents("div.blockComment:first") );
    	$btn.click(function(){
			$form.trigger("submit");
		});
    	$.data($form[0], "linereplyFlag", "RECREATE");
    	$.data($form[0], "validatorId", validatorPool.length);

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
		$("#" + linereplyId).find("input[name=contents]").val(contextText); 
		$("#" + linereplyId).show(); 
		
    	var $form = $("form", $("#" + linereplyId)); 
    	validatorPool.push(new iKEP.Validator($form, validOptionsBlogLineReply));
    	var $btn = $form.find(".textRight").children("a:eq(0)");
    	$.data($form[0], "div_blockComment_first", $btn.parents("div.blockComment:first") );
    	$btn.click(function(){
			$form.trigger("submit");
		});
    	$.data($form[0], "linereplyFlag", "UPDATE");
    	$.data($form[0], "validatorId", validatorPool.length);
		return false;  
	};


	/*
	*  신규 답변을 저장한다.
	*
	createLinereply = function(btn) {   
		
		var $divBlockComment = $jq(btn).parents("div.blockComment:first");
		var itemId = $jq(btn).parents("form").find("input[name=itemId]").val();
		var submitForm = $jq(btn).parents("form");
		
		$.post('<c:url value="/socialpack/socialblog/createSocialBoardLinereply.do"/>', submitForm.serialize())
		.success(function(data) { 
			loadSocialBoardLinereplyList($divBlockComment,itemId); 
		})
		.error(function(event, request, settings) { alert("error"); }); 
		
		return false;  
	};
	*/

	/*
	*  댓글의 답글을 저장한다.  TODO 여기도 수정 필요 ##########################################
	createReplyLinereply = function(linereplyId,btn) {   
		var formObject = $("#" + linereplyId).children("form"); 
		
		var $divBlockComment = $jq(btn).parents("div.blockComment:first");
		var itemId = formObject.find("input[name=itemId]").val();
		
		$.post('<c:url value="/socialpack/socialblog/createReplySocialBoardLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadSocialBoardLinereplyList($divBlockComment,itemId); 
		})
		.error(function(event, request, settings) { alert("error"); });
	 
		return false;  
	};
	*/

	/*
	*  댓글 수정을 한다.  TODO 여기도 수정 필요 ##########################################
	updateLinereply = function(linereplyId,btn) { 
		var formObject = $("#" + linereplyId).children("form"); 
		
		var $divBlockComment = $jq(btn).parents("div.blockComment:first");
		var itemId = formObject.find("input[name=itemId]").val();
		
		$.post('<c:url value="/socialpack/socialblog/updateSocialBoardLinereply.do"/>', formObject.serialize())
		.success(function(data) { 
			loadSocialBoardLinereplyList($divBlockComment,itemId); 
		})
		.error(function(event, request, settings) { alert("error"); }); 
	    
		return false;  
	};
	*/
			
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
	userDeleteBoardLinereply = function(itemId,linereplyId,btn) { 
		
		var $divBlockComment = $jq(btn).parents("div.blockComment:first");
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
		    $.post("<c:url value='/socialpack/socialblog/userDeleteSocialBoardLinereply.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId}) 
		    .success(function(data) { 
		    	loadSocialBoardLinereplyList($divBlockComment,itemId); 
		    })
		    .error(function(event, request, settings) { alert("error"); });
		}  
	    
		return false;  
	};

	/*
	*  관리자 모드로 댓글을 삭제한다.
	*/	
	adminDeleteBoardLinereply = function(itemId,linereplyId,btn) { 
		
		var $divBlockComment = $jq(btn).parents("div.blockComment:first");
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete' />")) {
		    $.post("<c:url value='/socialpack/socialblog/adminDeleteSocialBoardLinereply.do'/>", {"itemId" : itemId , "linereplyId" : linereplyId}) 
		    .success(function(data) { 
		    	loadSocialBoardLinereplyList($divBlockComment,itemId);
		    })
		    .error(function(event, request, settings) { alert("error"); });
		}  
	    
		return false;  
	}; 
	
	inputLinereply = function(itemId,btn) {
		
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_input").hide();
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_cancel").show();
		$jq.ajax({
		    url : "<c:url value='/socialpack/socialblog/inputSocialBoardLinereply.do'/>",
		    data : {'itemId':itemId}, 
		    type : "get",
		    success : function(result) {
		    	$jq(btn).parents("div.blockComment:first").find("#gbLineReplyInputView_"+itemId).html(result);
		    	
		    	
		    	var $form = $("form", $jq(btn).parents("div.blockComment:first").find("#gbLineReplyInputView_"+itemId) ); 
		    	validatorPool.push(new iKEP.Validator($form, validOptionsBlogLineReply));
		    	var $saveBtn = $form.find(".textRight").children("a:eq(0)");
		    	$.data($form[0], "div_blockComment_first", $saveBtn.parents("div.blockComment:first") );
		    	$saveBtn.click(function(){
					$form.trigger("submit");
				});
		    	$.data($form[0], "linereplyFlag", "CREATE");
		    	$.data($form[0], "validatorId", validatorPool.length);
		    	iKEP.checkLength("#contents_"+itemId, updateCheck);
		    	
		    }
		});
	}; 
	
	//입력체크
	updateCheck = function(length,el){
			textLength = length;
			var $form = $("form", $(el).parents(".guestbook_write:first")); 
			//$form.children(".guestbook_write_num").children("#textcount").html(textLength);
			$form.children(".guestbook_write_num").children("#textcount").html(150 - textLength);

			//var contents = $(el).val();		
			//if( textLength > 150){
			//	$(el).val(contents.substring(0,150));
			//}
	};
	
	cancelLinereply = function(btn) {
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_input").show();
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_cancel").hide();
		//$jq(btn).parents("div.blockComment:first").find("#gbLineReplyInputView").empty();
	}; 
	
	cancelLinereply = function(itemId,btn) {
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_input").show();
		$jq(btn).parents("div.blockComment_t:first").children("#linereply_cancel").hide();
		$jq(btn).parents("div.blockComment:first").find("#gbLineReplyInputView_"+itemId).empty();
	}; 
	
})(jQuery); 
//-->
</script>		

							<!--blockComment Start-->
							<div>
								<div class="blockComment_t">
									<ikep4j:message pre='${preList}' key='linereply' /> <span class="comment_num">(${linereplySearchCondition.recordCount})</span>
									<div class="pr_guestbook_btn" id="linereply_cancel"><a href="#a" onclick="javascript:cancelLinereply('${linereplySearchCondition.itemId}',this);"><img alt="<ikep4j:message pre='${preButton}' key='enfoldment'/>" src="<c:url value='/base/images/icon/ic_ar_up.gif' />" style="padding-bottom: 2px;" class="valign_middle"></a></div>
									<div class="pr_guestbook_btn" id="linereply_input"><a href="#a" onclick="javascript:inputLinereply('${linereplySearchCondition.itemId}',this);" class="button_s"><span><img alt="" src="<c:url value='/base/images/icon/ic_btn_write.gif' />"><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a></div>
								</div>
								
								<div id="gbLineReplyInputView_${linereplySearchCondition.itemId}" ></div>
								
								<c:forEach var="socialBoardLinereply" varStatus="varStatus" items="${searchResult.entity}"> 
								<c:choose>
									<c:when test="${not varStatus.first}"> 
										<c:forEach begin="${socialBoardLinereply.indentation}" end="${preIndentation}"></div></c:forEach>  
									</c:when> 
								</c:choose>	 
								<c:choose>
							 		<c:when test="${socialBoardLinereply.indentation == 0 }">
										<div name="scialBoardLinereplyItem" class="blockComment_c"> 
									</c:when>
									<c:otherwise>
										<div name="socialBoardLinereplyItem"  class="blockComment_re ">
										<div class="reply_ar"><img src="<c:url value="/base/images/icon/ic_reply_ar.gif"/>" alt="" /></div>
									</c:otherwise>
								</c:choose>	

									<div class="blockCommentPhoto"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${socialBoardLinereply.registerId}', 'bottom')"><img id="profilePictureImage" src="<c:url value='${socialBoardLinereply.profilePicturePath}' />" width="50" height="50" alt="profile Image" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" /></a></div>
									<div class="blockCommentInfo">
										<span class="blockCommentInfo_name">
											<a href="#a" onclick="iKEP.showUserContextMenu(this, '${socialBoardLinereply.registerId}', 'bottom')">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
								 					${socialBoardLinereply.registerName} 
													</c:when>   
													<c:otherwise>
								 					${socialBoardLinereply.registerEnglishName}
													</c:otherwise>    
												</c:choose>
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}"> 
								 					${socialBoardLinereply.registerJobTitleName} 
													</c:when>   
													<c:otherwise>
								 					${socialBoardLinereply.registerJobTitleEngName}
													</c:otherwise>    
												</c:choose>  
											</a>
										</span>					
										<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${socialBoardLinereply.registerTeamName}"/></c:when><c:otherwise><c:out value="${socialBoardLinereply.registerTeamEngName}"/></c:otherwise></c:choose></span>
										<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${socialBoardLinereply.registDate}"/></span>
										<span class="rebtn">  
											<c:choose>
												<c:when test="${isSystemAdmin}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
								 					<a href="#a" onclick="adminDeleteBoardLinereply('${socialBoardLinereply.itemId}', '${socialBoardLinereply.linereplyId}',this);" class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
													<a href="#a" onclick="showUpdateForm('${socialBoardLinereply.linereplyId}UpdateForm');" class="ic_modify" title="<ikep4j:message pre='${preButton}' key='updateLinereply'/>"><span><ikep4j:message pre='${preButton}' key='updateLinereply'/></span></a>
												</c:when>   
												<c:when test="${socialBoardLinereply.registerId eq user.userId}"> <%-- 관리자, 읽기 게시판이라면 --> 링크허용  --%>
								 					<a href="#a" onclick="userDeleteBoardLinereply('${socialBoardLinereply.itemId}', '${socialBoardLinereply.linereplyId}',this);" class="ic_delete" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a> 
													<a href="#a" onclick="showUpdateForm('${socialBoardLinereply.linereplyId}UpdateForm');" class="ic_modify" title="<ikep4j:message pre='${preButton}' key='updateLinereply'/>"><span><ikep4j:message pre='${preButton}' key='updateLinereply'/></span></a>
												</c:when>    
											</c:choose>
											<c:if test="${socialBoardLinereply.indentation < 3}"> 
								    		<a href="#a" onclick="showReplyForm('${socialBoardLinereply.linereplyId}ReplyForm');" class="ic_reply" title="<ikep4j:message pre='${preButton}' key='createLinereply'/>"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
								    		</c:if> 
								    	</span>
									</div>
									
									
									<c:choose>
								 		<c:when test="${socialBoardLinereply.linereplyDelete eq 1}">
								 			<p><span class="deletedItem"><ikep4j:message pre='${preList}' key='contents' post="deleteContents"/></span></p> 
										</c:when>
										<c:otherwise> 
											<p name="contents">${socialBoardLinereply.contents}</p> 
											<!--blockComment_rewrite Start--> 
											<div id="${socialBoardLinereply.linereplyId}UpdateForm"  name="updateForm" class="blockComment_rewrite">  <!-- modify -->
												<form name="updateForm" action="" onsubmit="return false;"> 
													<input name="itemId" type="hidden" value="${socialBoardLinereply.itemId}" title="<ikep4j:message pre='${preList}' key='itemId'/>" />
													<input name="linereplyId" type="hidden" value="${socialBoardLinereply.linereplyId}" title="<ikep4j:message pre='${preList}' key='linereplyId'/>" /> 
												    <table summary="<ikep4j:message pre='${preWebCommon}' key='createTable'/>"> 
														<caption></caption> 
														<tr> 
															<td> 
																<input name="contents" title="<ikep4j:message pre='${preList}' key='contents'/>" class="inputbox" type="text" value="" maxlength="150" /> 
															</td> 
															<td width="95" class="textRight"> 
																<a class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
																<a class="button_s" href="#a" onclick="closeUpdateLinereplyForm('${socialBoardLinereply.linereplyId}UpdateForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a>
															</td>  
														</tr>  
													</table> 
												</form>
											</div> 
											<!--//blockComment_rewrite End--> 
										</c:otherwise>
									</c:choose>	 
									
									
									<!--blockComment_rewrite Start-->
									<div id="${socialBoardLinereply.linereplyId}ReplyForm" name="replyForm" class="blockComment_rewrite">
										<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif'/>" alt="" /></div>  
										<form name="replyForm" action="" onsubmit="return false;"> 
											<input name="itemId"            type="hidden" value="${socialBoardLinereply.itemId}" title="<ikep4j:message pre='${preList}'  key='linereplyParentId'/>" /> 
											<input name="linereplyId"       type="hidden" value="${socialBoardLinereply.linereplyId}" title="<ikep4j:message pre='${preList}'  key='linereplyId'/>" /> 
											<input name="linereplyParentId" type="hidden" value="${socialBoardLinereply.linereplyId}" title="<ikep4j:message pre='${preList}'  key='linereplyParentId'/>" /> 
											<input name="linereplyGroupId"  type="hidden" value="${socialBoardLinereply.linereplyGroupId}" title="<ikep4j:message pre='${preList}' key='linereplyGroupId'/>" /> 
											<input name="step"              type="hidden" value="${socialBoardLinereply.step + 1}" title="<ikep4j:message pre='${preList}' key='step'/>" /> 
											<input name="indentation"       type="hidden" value="${socialBoardLinereply.indentation + 1}" title="<ikep4j:message pre='${preList}' key='indentation'/>" /> 				
											<table summary="<ikep4j:message pre='${preWebCommon}' key='createTable'/>">
												<caption></caption>
												<tr>
													<td>  
														<input name="contents" title="<ikep4j:message pre='${preList}' key='contents'/>" class="inputbox" type="text" maxlength="150" /> 
													</td>
													<td width="95" class="textRight">
														<a class="button_s" href="#a" ><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
														<a class="button_s" href="#a" onclick="closeReplyLinereplyForm('${socialBoardLinereply.linereplyId}ReplyForm');"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a>
													</td>
												</tr> 
											</table> 	 
										</form>
									</div>  
								<!-- </form> -->
											
								<c:choose>
						 		<c:when test="${varStatus.last}">
									<c:forEach begin="0" end="${socialBoardLinereply.indentation}">
										</div>	
									</c:forEach>
								</c:when>  
								<c:when test="${not(varStatus.first or varStatus.last)}"> 
									<c:set var="preIndentation" value="${socialBoardLinereply.indentation}"/>
								</c:when> 
							</c:choose>	  		
						</c:forEach> 
						
						<!--Page Numbur Start--> 
						<form id="socialBoardLinereplySearchForm${linereplySearchCondition.itemId}" name="socialBoardLinereplySearchForm${linereplySearchCondition.itemId}" action="" method="post"> 
						
							<spring:bind path="linereplySearchCondition.pageIndex">
								<c:if test="${ linereplySearchCondition.pageCount > 1 }"> 
								<ikep4j:pagination searchFormId="socialBoardLinereplySearchForm${linereplySearchCondition.itemId}" ajaxEventFormId="loadSocialBoardLinereplyListPage" pageIndexInput="${status.expression}" searchCondition="${linereplySearchCondition}" />
								</c:if>
								<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
							</spring:bind> 
							<input name="itemId" type="hidden" value="${linereplySearchCondition.itemId}" title="itemId" /> 
							<!--//Page Numbur End-->  
						</form>

			