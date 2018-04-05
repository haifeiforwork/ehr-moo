<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preProfileMain"  value="ui.support.profile.main" /> 
<c:set var="preGuestbook"  value="ui.support.guestbook.view.main" />

<c:set var="preButton"  value="ui.support.profile.common.button" />
<c:set var="preMsgProfile"  value="message.support.profile.main" />
<c:set var="preMsgGuestbook"  value="message.support.guestbook.view.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<% 
	pageContext.setAttribute("lf", "\n"); 
%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript" src="<c:url value='/base/js/units/support/guestbook/guestbook.js'/>"></script>
<script type="text/javascript">
<!--

(function($) {
	
	var validatorPool = [];
	var validOptionsGuestbook = { 
		rules : {
			contents : {
				required : true,
				maxlength : 300
			}
		},
		messages : {
			contents : {
				required : "<ikep4j:message key='NotNull.guestbook.contents' />",
				maxlength : "<ikep4j:message key='Size.guestbook.contents' />"
			}
		},
		notice : {
			contents : {
				message : "<ikep4j:message key='Size.guestbook.contents' />"
			}
	    },
		submitHandler : function(form) {
			
			var guestbookType = $.data(form, "guestbookType");
			
			if ( guestbookType == 'GUESTBOOK'){				
				$jq.ajax({
				    url : "<c:url value='/support/guestbook/createGuestbook.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
				    	getGuestbookList(pageIndex);
				    	//document.location.href = "<c:url value='/support/profile/getGuestbookList.do?targetUserId=${targetUserId}'/>" ;
				    },
				    error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						var validatorId = $.data(form, "validatorId")-1;
						var validator = validatorPool[validatorId];
						validator.showErrors(errorItems);
					}
				});
			}else if( guestbookType == 'LINEREPLY'){
				$jq.ajax({
				    url : "<c:url value='/support/guestbook/createGuestbookLineReply.do'/>" ,
				    data : $(form).serialize(),
				    type : "post",
				    success : function(result) {
				    	var grougId = $(form).find('input[name=groupId]:hidden').val();	
				    	Guestbook.viewGuestbookByGroupId(grougId);
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

	
	$jq(document).ready(function() {

		//$jq("#guestbook_input").show();
		$jq("#guestbookInputView").show();
		//$jq("#guestbook_cancel").hide();
		
		inputGuestbookValidate('${targetUserId}','PF');
	});
	
	Guestbook.viewGuestbookList = function() {
		$jq('#searchForm').submit();
	}; 
	
	 function inputGuestbookValidate(targetUserId,viewType) {

		//$jq("#guestbook_input").hide();		
		//var result =  "<form name=\"guestbookForm\" method=\"post\" onsubmit=\"return false\">"
		//					+ "<fieldset>"
		//					+ "		<input type='hidden' name='targetUserId' value='${guestbook.targetUserId}'/>"
		//					+ "		<input type='hidden' name='registerId' value='${user.userId}'/>"
		//					+ "		<input type='hidden' name='registerName' value='${user.userName}'/>"
		//					+ "		<div class='guestbook_write'>"
		//					+ "		<table summary=\"<ikep4j:message pre='${preGuestbook}' key='inputTable'/>\">"
		//					+ "			<caption></caption>"
		//					+ "			<tbody><tr>"
		//					+ "			<td>"
		//					+ "				<div><textarea id='contents' name='contents' rows='3' cols='' title=\"<ikep4j:message pre='${preGuestbook}' key='input'/>\" name=''></textarea></div>"
		//					+ "			</td>"
		//					+ "			<td width=\"74\" class=\"textRight\">"
		//					+ "				<a href=\"#a\" class=\"button_re\"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>"
		//					+ "			</td>"
		//					+ "			</tr>"
		//					+ "			</tbody>"
		//					+ "		</table>"
		//					+ "		<div class='guestbook_write_num'><p style='display:inline'><span id='textcount'>0</span>/150</p></div>"
		//					+ "		</div>"
		//					+ "</fieldset>"
		//					+ "</form>";
		
		$jq.ajax({
			url : "<c:url value='/support/guestbook/inputGuestbook.do'/>" ,
		    data : {'targetUserId':targetUserId,'viewType':viewType }, 
		    type : "get",
		    success : function(result) {
		    	$jq("#guestbookInputView").html(result);
		    	
		    	var $container = $jq("#guestbookInputView");
		    	var $form = $("form", $container);		    	
		    	validatorPool.push(new iKEP.Validator($form, validOptionsGuestbook));
		    	$form.find(".textRight").children("a:eq(0)").click(function(){
					$form.trigger("submit");
				});
		    	$.data($form[0], "guestbookType", "GUESTBOOK");
		    	$.data($form[0], "validatorId", validatorPool.length);
		    	iKEP.checkLength("#contents", updateCheck);
		    	iKEP.iFrameContentResize();
		    	//$jq("#guestbook_cancel").show();
			},
			error : function(xhr, exMessage) {
				//$jq("#guestbook_input").show();	
			}
		});
		
	};

	
	//입력체크
	updateCheck = function(length){
			textLength = length;
			$jq("#textcount").html(300 - textLength);

			//var contents = $jq("#contents").val();
			//if( textLength > 300){
			//	$jq("#contents").val(contents.substring(0,300));
			//}
	};
	
	inputGuestbookLineReplyValid = function(guestbookId,btn,indentation) {
		
		var $gbLineReplyInputView = "";
		if( indentation == '1' ){
			$gbLineReplyInputView = $jq(btn).parents("div.guestbook_c:first").find(".gbLineReplyInputView:first");
		}else{
			$gbLineReplyInputView = $jq(btn).parents("div.blockComment_re:first").find(".gbLineReplyInputView:first");
		}

		var $rebtnspan = "";
		if( indentation == '1' ){
			$rebtnspan = $jq(btn).parents("span.rebtn:first").find(".ic_reply:first");
		}else{
			$rebtnspan = $jq(btn).parents("div.blockComment_re:first").find(".ic_reply:first");
		}
		$rebtnspan.hide();
		
		$jq.ajax({
		    url : "<c:url value='/support/guestbook/inputGuestbookLineReply.do'/>" ,
		    data : {'guestbookId':eval("'"+guestbookId+"'"),'indentation':eval("'"+indentation+"'")}, 
		    type : "get",
		    success : function(result) {
		    	$jq($gbLineReplyInputView).html(result);
		    	
		    	var $form = $("form", $gbLineReplyInputView );
		    	validatorPool.push(new iKEP.Validator($form, validOptionsGuestbook));
		    	$form.find(".textRight").children("ul").children("li:eq(0)").children("a:eq(0)").click(function(){
		    		$form.submit();
				});
		    	$.data($form[0], "guestbookType", "LINEREPLY");
		    	$.data($form[0], "validatorId", validatorPool.length);
		    	iKEP.iFrameContentResize();
		    	//iKEP.checkLength("#contents", updateCheck);
		    	
		    }
		});
	};

	
	addMoreGuestbook = function(btn,searchIndex,moreFlag) {

		var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
		
		getGuestbookList(searchIndex);
		/*
		$jq.ajax({
			url : "<c:url value='/support/guestbook/listGuestbookMore.do'/>",
			data : {'guestbookId':eval("'"+guestbookId+"'"),'targetUserId':'${targetUserId}','pageIndex':eval("'"+searchIndex+"'")}, 
			type : "post",
			success : function(result) {
				$jq("#nowGuestbookview").html(result);
				if(moreFlag == 'false'){
					$rebtnspan.remove();
				}
			}
		});
		*/

	};
	
	Guestbook.deleteGuestbook = function(guestbookId,btn) {
		
		var $rebtnspan = $jq(btn).parents("div.guestbook_c:first");
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+guestbookId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	//$jq("#profileView").html(result);
			    	//$rebtnspan.remove();
			    	var pageIndex = $jq('#searchForm').find('input[name=pageIndex]:hidden').val();
			    	getGuestbookList(pageIndex);
			    }
			});
		}
	};
	
	// 개별 건만 지울때 사용
	Guestbook.deleteGuestbookLineReply = function(linereplyId,btn) {
		
		var $blockComment_re = $jq(btn).parents("div.blockComment_re:first");
		
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+linereplyId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	//$jq("#profileView").html(result);
			    	$blockComment_re.remove();
			    }
			});
		}
	};
	
	// 개별건 지우고 방명록 댓글 재 사용 위해 재 호출
	Guestbook.deleteGuestbookLineReplyByGroupId = function(linereplyId,groupId) {
		
		if(confirm("<ikep4j:message pre='${preMsgGuestbook}' key='comment.delete'/>") == true){
			$jq.ajax({
			    url : "<c:url value='/support/guestbook/deleteGuestbook.do'/>",
			    data : {'guestbookId':eval("'"+linereplyId+"'"),'targetUserId':'${targetUserId}'}, 
			    type : "get",
			    success : function(result) {
			    	Guestbook.viewGuestbookByGroupId(groupId);
			    }
			});
		}
	};
	
	
})(jQuery);  
//-->
</script>

<!--pr_guestbook Start-->
<div class="pr_guestbook">

	<h3><ikep4j:message pre='${preProfileMain}' key='guestbook.title'/></h3>
	<div class="more"><a href="#a" onclick="goGuestbook();"><img src="<c:url value='/base/images/common/btn_more.gif' />" alt="more" /></a></div>

	<form id="searchForm" action = "" method="post"> 
	<spring:bind path="guestbookSearch.pageIndex"> 
		<input name="pageIndex" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preGuestbook}' key='${status.expression}'/>" />
	</spring:bind>
	<!--guestbook_t Start-->
	<%-- div class="guestbook_t">	
			<ikep4j:message pre='${preGuestbook}' key='totalCount'/> <strong>${searchResult.recordCount}</strong> <ikep4j:message pre='${preGuestbook}' key='totalCount1'/>
			<div class="pr_guestbook_btn" id="guestbook_input"><a class="button" onclick="inputGuestbookValidate('${targetUserId}','PF');" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></div>
			<div class="pr_guestbook_btn" id="guestbook_cancel"><a class="button" onclick="Guestbook.cancelGuestbook();" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></div>
	</div--%>
	<!--//guestbook_t End-->
	</form>
	
	<!-- Guestbook Input Start -->
	<div class="pr_guestbookWrap">
		<div><img src="<c:url value='/base/images/common/tit_pr_guest.gif' />" alt="행복한 한마디" /></div>
		<div class="pr_guestbook_textarea ">
			<div class="guestbookInputView" id="guestbookInputView"></div>
		</div>	
	</div>
	<!-- Guestbook Input Start -->
	<div id="prependGuestbookview"></div>

	<div class="clear guestbook_t">
		<ikep4j:message pre='${preGuestbook}' key='totalCount'/> <strong>${searchResult.recordCount}</strong> <ikep4j:message pre='${preGuestbook}' key='totalCount1'/>
	</div>	

	<div id="nowGuestbookview">
	<c:choose>
		<c:when test="${searchResult.emptyRecord}">
			<div class="blockTableRead_t_info_2">
				<p align="center"> 
				<ikep4j:message pre='${preMsgGuestbook}' key='${status.expression}emptyRecord'/>
				</p>
			</div>
		</c:when>
		<c:otherwise>
			<c:forEach var="guestbookList" items="${searchResult.entity}" varStatus="status">
				
				<input type="hidden" class="hidden" name="guestbookId" value="<c:out value="${guestbookList.guestbookId}"/>"  />
				<c:if test="${size != status.index}">
				<div class="guestbook_c" id="GUEST_${guestbookList.guestbookId}">
				</c:if>
				<c:if test="${size == status.index}">
				<div class="guestbook_c divLast" id="GUEST_${guestbookList.guestbookId}">
				</c:if>
				<div class="pr_guestbookInfo_wrap">
					<div class="pr_guestbookInfo">
						<span class="pr_guestbookInfo_name"><a href="#a" onclick="goOtherProfile('${guestbookList.registerId}')" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbookList.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbookList.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
						<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbookList.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbookList.registerTeamEngName}"/></c:otherwise></c:choose></span>
						<img src="<c:url value='/base/images/theme/theme01/basic/bar_pageNum.gif'/>" alt="" />
						<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbookList.registDate}"/></span>
						<span class="rebtn">
						<c:if test="${user.userId == guestbookList.registerId or user.userId == targetUserId }">
						<a onclick="Guestbook.deleteGuestbook('${guestbookList.guestbookId}',this)" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
						</c:if>
						<a onclick="inputGuestbookLineReplyValid('${guestbookList.guestbookId}',this,'1')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
						</span>
					</div>
				</div>

				<div class="commentNum1"><ikep4j:message pre='${preGuestbook}' key='linereply'/> (<span class="colorPoint"><c:out value="${guestbookList.guestbookLineReplyCnt}"/></span>)</div>
				
				<div class="summaryViewConTest">
				<p>${fn:replace(guestbookList.contents, lf, "<br/>")}</p>
				<div class="gbLineReplyInputView" ></div>
				<div class="prependGbLineReplyview">
					<c:if test="${!empty guestbookList.guestbookList}">
					<div class="blockComment_box">
					<c:forEach var="guestbooklist1dp" items="${guestbookList.guestbookList}" varStatus="lrStatus">
					
					<!--blockComment_rewrite Start-->
					<div class="blockComment_re">
						<c:if test="${lrStatus.index==0}">
						<div class="blockComment_re_top"></div>
						</c:if>
						<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif' />" alt="" /></div>
						<div class="blockCommentInfo">
							<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist1dp.registerId}')"><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
							<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist1dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist1dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
							<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist1dp.registDate}"/></span>


							<span class="rebtn">
								<c:if test="${user.userId == guestbooklist1dp.registerId or user.userId == targetUserId }">
									<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist1dp.guestbookId}','${guestbooklist1dp.groupId}')" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
								</c:if>
									<a onclick="inputGuestbookLineReplyValid('${guestbooklist1dp.guestbookId}',this,'2')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
							</span>
							
						</div>
						
						<div class="summaryViewConTest">
						<p>${fn:replace(guestbooklist1dp.contents, lf, '<br/>')}</p>
						<div class="gbLineReplyInputView"></div>
						<div class="prependGbLineReplyview"></div>
						</div>
						
						<c:if test="${guestbooklist1dp.guestbookList != null}">
						<c:forEach var="guestbooklist2dp" items="${guestbooklist1dp.guestbookList}" varStatus="lrStatus">
						<div class="blockComment_re">
							<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif' />" alt="" /></div>
							<div class="blockCommentInfo">
								<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist2dp.registerId}')" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>					
								<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist2dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist2dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
								<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist2dp.registDate}"/></span>


								<span class="rebtn">
									<c:if test="${user.userId == guestbooklist2dp.registerId or user.userId == targetUserId }">
										<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist2dp.guestbookId}','${guestbooklist2dp.groupId}')" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
									</c:if>
										<a onclick="inputGuestbookLineReplyValid('${guestbooklist2dp.guestbookId}',this,'3')" href="#a" class="ic_reply"><span><ikep4j:message pre='${preButton}' key='createLinereply'/></span></a>
								</span>
								
							</div>
							<div class="summaryViewConTest">
							<p>${fn:replace(guestbooklist2dp.contents, lf, '<br/>')}</p>
							<div class="gbLineReplyInputView"></div>
							<div class="prependGbLineReplyview">

								<c:if test="${guestbooklist2dp.guestbookList != null}">
								<c:forEach var="guestbooklist3dp" items="${guestbooklist2dp.guestbookList}" varStatus="lrStatus">
								
								<div class="blockComment_re">
									<div class="reply_ar"><img src="<c:url value='/base/images/icon/ic_reply_ar_b.gif' />" alt="" /></div>
									<div class="blockCommentInfo">
										<span class="blockCommentInfo_name"><a href="#a" onclick="goOtherProfile('${guestbooklist3dp.registerId}')" ><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.guestbookUserName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.guestbookUserEngName}"/></c:otherwise></c:choose> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.registerJobTitleName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.registerJobTitleEngName}"/></c:otherwise></c:choose></a></span>						
										<span><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${guestbooklist3dp.registerTeamName}"/></c:when><c:otherwise><c:out value="${guestbooklist3dp.registerTeamEngName}"/></c:otherwise></c:choose></span>
										<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${guestbooklist3dp.registDate}"/></span>
	
	
										<span class="rebtn">
											<c:if test="${user.userId == guestbooklist3dp.registerId or user.userId == targetUserId }">
												<a onclick="Guestbook.deleteGuestbookLineReplyByGroupId('${guestbooklist3dp.guestbookId}','${guestbooklist3dp.groupId}');" href="#a" class="ic_delete"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
											</c:if>
										</span>
										
									</div>
									<p>${fn:replace(guestbooklist3dp.contents, lf, '<br/>')}</p>
								</div>
								
								</c:forEach>
								</c:if>
																			
							</div>
							</div>
						</div>
						</c:forEach>
						</c:if>
					
					</div>
					</c:forEach>
					</div>
					</c:if>
				</div>
				</div>

			</div>
			</c:forEach>
		</c:otherwise>
	</c:choose> 
	</div>
		
	<c:if test="${searchResult.pageCount > 0 and searchResult.pageIndex < searchResult.pageCount }">						
	<!--blockButton_3 Start-->
	<div class="blockButton_3"> 
		<a class="button_3" onclick="addMoreGuestbook(this,'${searchResult.pageIndex+1}','${(searchResult.pageIndex+1)<searchResult.pageCount}');" href="#a"><span><ikep4j:message pre='${preProfileMain}' key='guestbook.more'/><img src="<c:url value='/base/images/icon/ic_more_ar.gif' />" alt="" /></span></a>				
	</div>
	<!--//blockButton_3 End-->
	</c:if>													
																					
</div>
<!--//pr_guestbook End-->