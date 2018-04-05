<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  	value="ui.support.note.noteUserFolderMain" />
<c:set var="preHeader"  value="ui.support.note.updateFolderView" /> 
<c:set var="preDetail"  value="ui.support.note.updateFolderView.detail" />
<c:set var="preMessage" value="message.support.common.note" />
<c:set var="preButton"  value="ui.support.common.button" />   
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 메시지 관련 Prefix 선언 End --%>   
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/note.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/units/support/note/event_class.js"/>"></script>
<script type="text/javascript"> 
<!--  
(function($) {    

	getSelectedColor = function() {
		var result = "";

		var el = $("#divEventColorList").children(".event_color_button_active");
		if(el.size() > 0) {
			var pattern = /note_class_type\d/;
			$.each(el.children().attr("class").split(" "), function(index, className) {
				if(pattern.test(className)) result = className;
			});
		}
		
		return result;
	}
	
	setCategory = function() {		
		// color list
		var defaultColor = "${noteFolder.color}";
		var template = $.template(null, '<a class="event_color_button \${color}"></a>');
		var $divEventColorList = $("#divEventColorList").click(function(event) {
			var el = event.target;
			if(el.tagName.toLowerCase() == "a" && !$(el).hasClass("event_color_button_active")) {
				$(this).children("span.event_color_button_active").replaceWith(function() {
					return $(this).children();
				});
				$(el).wrap('<span class="event_color_button_active"/>');
			}
		});
		var $selectedColor;
		for(var key in eventStyle) {
			var $anchor = $.tmpl(template, {color:key}).appendTo($divEventColorList);
			if(defaultColor == key) $selectedColor = $anchor;
		}
		if(!$selectedColor) $selectedColor = $divEventColorList.children(":first");
		$selectedColor.wrap('<span class="event_color_button_active"/>');		
	}

	setSharedUser = function(data) {   
		
		var $sel = $jq("select[name=selectSharedUserList]");
		
		$.each(data, function(index, value) {  
			if($("#selectSharedUserList option[value=" + value.id + "]").length == 0) {
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);   
			} 
		})   
		
		$("#selectSharedUserListCount").text(displayUserCountText($("#selectSharedUserList option").length));
	};
	
	setSharedUserAddress = function(data) {   
		
		var $sel = $jq("select[name=selectSharedUserList]").children().remove().end();
		
		$.each(data, function(index, value) {  
			
				$.tmpl(iKEP.template.userBoardOption, this)
				.appendTo($sel)
				.data("data", this);
		});
		$("#selectSharedUserListCount").text(displayUserCountText($("#selectSharedUserList option").length));
	};
		
	setAddressInit = function() {
		
		var addrObj;
		var $sel;
		
		$sel = $jq("select[name=selectSharedUserList]").children().remove().end();
		
		<c:forEach var="user" items="${noteFolder.sharedUserList}"> 
			addrObj = $.parseJSON('{"type":"user","id":"${user.userId}","userName":"${user.userName}","jobTitleName":"${user.jobTitleName}","teamName":"${user.teamName}"}');
			$.tmpl(iKEP.template.userBoardOption, addrObj)
				.appendTo($sel)
				.data("data", addrObj);
		</c:forEach>			
	};
	
	displayUserCountText = function(count) {
		if(count == null) {
			count = 0;
		}
		return "<ikep4j:message pre='${preDetail}' key='all'/> " + count + "<ikep4j:message pre='${preDetail}' key='myung'/>";
	};  
	
	displaySharedPermissionTableRow = function() {
		if($("input[name=shared]:checked").attr("value") == "1") {
			$(".sharedPermissionTableRow").show();
			$(".sharedPermissionTableRow").children().show();
		} else {
			$(".sharedPermissionTableRow").children().hide();
			$(".sharedPermissionTableRow").hide();
			$("#selectSharedUserList option").remove();
		} 
		
		iKEP.iFrameContentResize();
	};
	
	$(document).ready(function() {  
		$("#folderDetail").ajaxLoadComplete();	
				
		window.parent.topScroll(); 		
		setCategory();
		displaySharedPermissionTableRow();
		
		$("option.dummy").remove();
		
		new iKEP.Validator("#folderForm", {
			rules  : {
				folderName        : { required : true, rangelength : [1, 100] }
			},
			messages : { 
				folderName        : {direction : "right",  required : "<ikep4j:message key='NotEmpty.note.folderName' />",        
									rangelength : "<ikep4j:message key='Size.note.folderName' />"}
			},
		    submitHandler : function(form) {   
				$("#folderDetail").ajaxLoadStart();	
				$jq.ajax({
					url : "<c:url value='/support/note/createFolderAjax.do'/>",
					data : $jq("#folderForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						parent.location.href = "<c:url value='/support/note/noteView.do'/>?folderId=F"; 
					}
				});
		    }
		});     
		//커서 포커스를 첫번째 Input에 넣는다.
		$("input[name=folderName]").focus();
		
		$("a[name=updateFolderButton]").click(function() {  
			if($("input[name=shared]:checked").val() == "1" && $("#selectSharedUserList option").length == 0 ) {
				alert('<ikep4j:message pre='${preMessage}' key='notInputedSharedPermission'/>');
				return false;
			}			
			
			$("input[name^=sharedUserList]").remove();
			
			$("#selectSharedUserList option").each(function(index) {  
				var userInfo = $(this).attr("id").split("^");
				    
				$("<input name='sharedUserList[" + index + "].userId'       type='hidden' value='"+ userInfo[0] +"' />").appendTo( "#folderForm" );   
				$("<input name='sharedUserList[" + index + "].userName'     type='hidden' value='"+ userInfo[1] +"' />").appendTo( "#folderForm" );   
				$("<input name='sharedUserList[" + index + "].jobTitleName' type='hidden' value='"+ userInfo[2] +"' />").appendTo( "#folderForm" );   
				$("<input name='sharedUserList[" + index + "].teamName'     type='hidden' value='"+ userInfo[3] +"' />").appendTo( "#folderForm" );   
				$("<input name='sharedUserList[" + index + "].mail'         type='hidden' value='"+ userInfo[4] +"' />").appendTo( "#folderForm" );   
			});

			var color = getSelectedColor();
			$("input[name=color]").val(color);
			
			$jq.ajax({     
				url : '<c:url value="/support/note/existFolderName.do" />',
				data :  $jq("#folderForm").serialize(),     
				async : false,
				success : function(result) {      
					if(!result) {
						$("#folderForm").attr("action","<c:url value='/support/note/createFolder.do'/>");
						$("#folderForm").trigger("submit"); 
					}else {
						alert("<ikep4j:message pre='${prefix}' key='alert.message2' />");
						return;
					}
				}
		    });	
		}); 		

		$("input[name=shared]").click(function() { 
			displaySharedPermissionTableRow();
		}); 
		
		//그룹목록 추가 버튼에 이벤트 바인딩
		$("#addSharedUserButton").click( function() {
			
			var items = [];
			$jq("select[name=selectSharedUserList]").children().each(function() {
				items.push($(this).data("data"));
			});
			
			iKEP.showAddressBook(setSharedUserAddress, items, {selectType:"user", tabs:{common:1, personal:1}});
		});
		
		$("#sharedUserName").keypress( function(event) {  
			iKEP.searchUser(event, "Y", setSharedUser); 
		}); 
        
		$("#searchSharedUserButton").click( function() {  
			$("#sharedUserName").trigger("keypress");
		});
		
		$("#deleteSharedUserButton").click( function() {  
			$("#selectSharedUserList option:selected").remove();
			$("#selectSharedUserListCount").text(displayUserCountText($("#selectSharedUserList option").length));
		});
	
		new iKEP.QuickPeopleSelector("#btnQuickAddrParticipant", function(item){
			setSharedUser([item]);
			//selectUserCallback([item], "selectSharedUserList");
		});
	
		$("a[name=readFolderViewButton]").click(function() {
		  	location.href = "<c:url value='/support/note/readFolderView.do?folderId=${noteFolder.folderId}'/>";
		});
	 
		setAddressInit();
		
		return false;  
		
	}); 
})(jQuery);  
//-->
</script> 
	<!--pageTitle Start-->
	<div id="pageTitle" class="none"> 
		<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2>
	</div> 
	<!--//pageTitle End--> 
	
	<!--blockDetail Start-->
	<form id="folderForm" method="post" action="null">  
	<div class="blockDetail"> 
		<spring:bind path="noteFolder.portalId">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
		</spring:bind> 
			<spring:bind path="noteFolder.folderId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
			</spring:bind>	 
		<c:if test="${not empty noteFolder.portalId}">
			<spring:bind path="noteFolder.folderParentId">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
			</spring:bind> 		
		</c:if>	
		<spring:bind path="noteFolder.folderType">
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" /> 
		</spring:bind>	
		<spring:bind path="noteFolder.shared">
			<input type="hidden" id="shared" name="shared" value="0" />
			</spring:bind>			
		<table> 
			<caption></caption>
			<colgroup>
				<col style="width: 18%;"/>
				<col style="width: 82%;"/> 			
			</colgroup>  
			<spring:bind path="noteFolder.folderName">
			<tr> 
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>	
					<div>
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					/>
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			        </c:forEach>  
				</td> 
			</tr>				
			</spring:bind> 
			<c:if test="${noteFolder.folderType eq 0}">
			<spring:bind path="noteFolder.color">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preDetail}' key='${status.expression}'/>" />
					<div id="divEventColorList" class="mt5"></div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			<spring:bind path="noteFolder.defaultFlag">
			<tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>		
						<label>
						<input name="${status.expression}" 
						type="checkbox" 
						class="checkbox" 
						value="1" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
						<c:if test="${1 eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message pre='${preDetail}' key='defaultMessage' />
						</label>	
					</div>
					<c:forEach items="${status.errorMessages}" var="error">
			       		<label for="${status.expression}" class="serverError"> ${error}</label>
			  	   </c:forEach>  
				</td>  
			</tr>				
			</spring:bind>  
			
			<%-- <tr> 
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>					
						<label>
						<input name="shared" 
						type="radio" 
						class="radio" 
						value="0" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='sharedN' />"
						<c:if test="${'0' eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message pre='${preDetail}' key='sharedN' />
						</label>			
						<label>
						<input name="shared" 
						type="radio" 
						class="radio" 
						value="1" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='sharedY' />"
						<c:if test="${'1' eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message pre='${preDetail}' key='sharedY' />
						</label>
					</div>
				</td>  
			</tr>	 	 --%>		
			 
			<%-- <tr class="sharedPermissionTableRow"> 
				<th scope="row" rowspan="2"><ikep4j:message pre='${preDetail}' key='sharedPermissionInput' /></th>
				<td> 
					<input id="sharedUserName" name="sharedUserName" title="<ikep4j:message pre='${preDetail}' key='user' />" class="inputbox valign_middle" type="text" size="12" /> 
					<a id="searchSharedUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a> 
					<a id="addSharedUserButton" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_address.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='addressBook' /></span></a> 
					<a id="btnQuickAddrParticipant" class="btn sBtn sBtn_icon_quick"><ikep4j:message pre="${preButton}" key="quick" /></a>
					<div class="mt5"> 	
						<select title="select" id="selectSharedUserList" name="selectSharedUserList" size="15" multiple="multiple" class="multi w60" title="<ikep4j:message pre='${preDetail}' key='user' />" >
							<option class="dummy"></option>							
							<c:forEach var="user" items="${noteFolder.sharedUserList}"> 
								<option value="${userId}" id="${user.userId}^${user.userName}^${user.jobTitleName}^${user.teamName}^${user.mail}">
								${user.userName} ${user.jobTitleName} (${user.teamName}, ${user.mail})
								</option>
							</c:forEach>  
						</select>	 	
						<a id="deleteSharedUserButton" class="button_ic valign_bottom" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif'/>" alt="" /><ikep4j:message pre='${preButton}' key='permissionDelete' /></span></a> 
						<span id="selectSharedUserListCount" class="totalNum_s"><ikep4j:message pre='${preDetail}' key='all' />  ${fn:length(noteFolder.sharedUserList)}<ikep4j:message pre='${preDetail}' key='myung' /></span> 
					</div>	 
				</td>   
			</tr> --%>
			</c:if>			
		</table>
	</div>  
	</form>
	<!--//blockDetail End-->
	<!--blockButton Start-->
	<div class="blockButton" id="divButton"> 
		<ul> 
			<li><a name="updateFolderButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<!-- <li><a name="readFolderViewButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li> -->
			<li><a name="listBoardViewButton" class="button" href="<c:url value='/support/note/listFolderView.do'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
		</ul>
	</div>
	<!--//blockButton End--> 