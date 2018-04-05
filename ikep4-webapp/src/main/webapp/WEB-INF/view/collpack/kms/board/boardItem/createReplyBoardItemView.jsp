<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.createReplyBoardView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardItem.createReplyBoardView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script> 
<%@ include file="/base/common/fileUploadControll.jsp"%><%-- 파일업로드용 Import --%>
<script type="text/javascript">
<!--   
//activeX editor 사용여부
var useActXEditor = "${useActiveX}";
(function($){	 
	$(document).ready(function() {   
		if(window.parent.topScroll != null) {
			window.parent.topScroll(); 	
		} 
		
		if(window.parent.menuMark != null) { 
			window.parent.menuMark("${boardItem.boardId}");
		}
 		$jq("input[name=followBoardPermission]").click(function() {
			if($jq("input[name=followBoardPermission]:checked").val()==0)
			{
				$jq('#readPermissionDiv').show();
				iKEP.iFrameContentResize();
			}
			else{
				$('#readPermissionDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("input[name=readPermission]").click(function() {
			if($jq("input[name=readPermission]:checked").val()==4)
			{
				$jq('#readPermissionListDiv').show();
				iKEP.iFrameContentResize();
			}
			else{
				$('#readPermissionListDiv').hide();
				$jq('#readPermissionList').empty();
				$jq('input[name=searchSubFlag]:hidden').val("0") ;
				iKEP.iFrameContentResize();
			}
		});
		
		$jq("#deleteReadPermissionButton").click(function(event) {
			event.preventDefault();
			var $rPermissionList=$jq('#readPermissionList');
			$jq('option:selected',$rPermissionList).remove();
		});	
				
		$jq("#addReadPermissionButton").click(function() {
			// 조직도 팝업 테스트

			var items = [];
			var $sel = $jq("#boardItemForm").find("[name=readPermissionList]");
			
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});

			$controlType = $jq('input[name=controlType]:hidden').val() ;
			$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
			$selectType = $jq('input[name=selectType]:hidden').val() ;
			$selectMaxCnt = $jq('input[name=selectMaxCnt]:hidden').val() ;
			$searchSubFlag = $jq('input[name=searchSubFlag]:hidden').val() ;

			var url = "<c:url value='/support/addressbook/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
			var callback = function(result){
				$sel.empty();
				$jq.each(result, function() {

					var tpl = "";
					
					switch(this.type) {
						case "group" : tpl = "addrBookItemGroup"; break;
						case "user" : tpl = "addrBookItemUser"; break;
					}
					
					if(this.type=="group")
						this.code="G:"+this.code;
					else
						this.id ="U:"+this.id;
					
					var $option = $jq.tmpl(tpl, this).appendTo($sel);

					$jq.data($option[0], "data", this);

					if( this.searchSubFlag == true ){
						$jq('input[name=searchSubFlag]:hidden').val("1") ;
					}
		
				})
			};
			iKEP.showAddressBook(callback, items, {selectType:"all", selectElement:$sel, isAppend:false, tabs:{common:1}});
			

			
		});			
		$("a.saveBoardItemButton").click(function() { 
			$("#boardItemForm").trigger("submit"); 
			return false;  
		});
		
		// editor 초기화
		initEditorSet();
		
		
		new iKEP.Validator("#boardItemForm", {   
			rules  : {
				title     : {required : true, rangelength : [1, 100] },
				tag       : {required : true, tagDuplicate : true}
			},
			messages : {
				title     : {direction : "top",    required : "<ikep4j:message key="NotNull.boardItem.title" />", rangelength : "<ikep4j:message key="message.collpack.collaboration.Size.boardItem.title" />"}
				tag       : {direction : "top", 
					 required : "<ikep4j:message key="message.collpack.collaboration.NotBlank.boardItem.tag" />", 
					tagDuplicate : "<ikep4j:message key="message.collpack.collaboration.TagDuplicate.boardItem.tag" />"}
			},   
			
		    submitHandler : function(form) {  
		    	// ActiveX Editor 사용 여부가 Y인 경우
		    	if(useActXEditor == "Y"){
			    	if (!$.browser.msie) {
			    		//ekeditor 데이타 업데이트
			    		var editor = $("#contents").ckeditorGet(); 
						editor.updateElement();
						//에디터 내 이미지 파일 링크 정보 세팅
						createEditorFileLink("boardItemForm");
			    	}
		    	}else{
		    		//ekeditor 데이타 업데이트
		    		var editor = $("#contents").ckeditorGet(); 
					editor.updateElement();
					//에디터 내 이미지 파일 링크 정보 세팅
					createEditorFileLink("boardItemForm");
		    	}
		    	
			
				if($jq('input[name=followBoardPermission]:checked').val()==1){
					$jq('input[name=readPermission]').val('${board.readPermission}') ;
				}
			
			
				$jq("#readPermissionList>option").attr("selected",true);
					    	
		    	if(confirm("<ikep4j:message pre="${preMessage}" key="createItemReply" />")) {
					fileController.upload(function(isSuccess, files) {
						if(isSuccess === true) { 

							//에디터 감추기
							if(useActXEditor == "Y"){
						    	if ($.browser.msie) {
						    		//태그프리 선택 탭을 디자인으로 변경 후 저장한다.
						    		var tweTab = document.twe.ActiveTab;
						    		if ( tweTab != 1 ) {
						    			document.twe.ActiveTab = 1;
						    		}
						    		//태그프리 Mime 데이타 세팅
						    		var tweBody = document.twe.MimeValue();
						    		$('input[name="contents"]').val(tweBody);
						    		$("#twe").css("visibility","hidden");
						    	}
							}

							
							$("body").ajaxLoadStart("button");
							form.submit();
						}
					});
				}
		    }
		});   
				 
	    var uploaderOptions = {
	 	    <c:if test="${empty fileDataListJson}">files : "",</c:if> 
	 	    <c:if test="${not empty fileDataListJson}">files : ${fileDataListJson},</c:if> 
		    <c:if test="${board.fileAttachOption eq 0 and not empty board.allowType}">allowExt : "${board.allowType}",</c:if>
		    <c:if test="${board.fileAttachOption eq 1 and not empty board.allowType}">allowFileType : "${board.allowType}",</c:if>
	        isUpdate : true,
	    	onLoad : function() {
	    		iKEP.iFrameContentResize();
	    	}
	    };  
	    
	    var fileController = new iKEP.FileController("#boardItemForm", "#fileUploadArea", uploaderOptions);	 
	    
	 	// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
		    if (!$.browser.msie) {
		    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
		    	var editor = $("#contents").ckeditorGet();
				editor.on("instanceReady",function() {
					iKEP.iFrameContentResize();
				});
				$("input[name=title]").focus();
	    	}
	    }else{
	    	//에디터 로딩 후 현재 페이지 높이 세팅 - iframe 높이 조절
	    	var editor = $("#contents").ckeditorGet();
			editor.on("instanceReady",function() {
				iKEP.iFrameContentResize();
			});
			$("input[name=title]").focus();
	    }
	 	
	});  	
	
	/* editor 초기화  */
	initEditorSet = function() {
		// ActiveX Editor 사용 여부가 Y인 경우
	    if(useActXEditor == "Y"){
			// 브라우저가 ie인 경우
			if ($.browser.msie) {
				// div 높이, 넓이 세팅
				var cssObj = {
			      'height' : '450px',
			      'width' : '100%'
			    };
				$('#editorDiv').css(cssObj);

				// hidden 필드 추가(contents)
				iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",0);
				// ie 세팅
				$('input[name="msie"]').val('1');
			}else{
				// ckeditor 초기화.
				$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
				// ie 이외 브라우저 값 세팅
				$('input[name="msie"]').val('0');
			}
	    }else{
	    	// ckeditor 초기화.
			$("#contents").ckeditor($.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
			// ie 이외 브라우저 값 세팅
			$('input[name="msie"]').val('0');
	    }
	};
	
})(jQuery); 
//-->
</script>

<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
	/* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
	if (!event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<br>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}
	if (event.shiftKey && event.keyCode == 13){
		twe.InsertHtml("<p>");
		event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
	}		
</script>
<script language="JScript" for="twe" event="OnControlInit()">	
	$jq("input[name=title]").focus();
</script>

<c:if test="${popupYn}"><div class="contentIframe"></c:if>
<form id="editorFileUploadParameter" action="null"> 
	<input name="boardId"  value="${board.boardId}" type="hidden"/> 
	<input name="workspaceId"  value="${board.workspaceId}" type="hidden"/>	
	<input name="interceptorKey"  value="collpack.collaboration.board" type="hidden"/> 
</form>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!--pageTitle Start--> 
<div id="pageTitle" class="btnline"> 
	<h2>${board.boardName}</h2> 
	<div class="blockButton"> 
		<ul>
			<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
				<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			</c:if> 
			<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
			<c:if test="${not popupYn}">	
				<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
			</c:if>
		</ul>
	</div>	
</div>  
<!--//pageTitle End--> 
<!--blockDetail Start-->
<div class="blockDetail">
	<form id="boardItemForm" method="post" action="<c:url value='/collpack/collaboration/board/boardItem/createReplyBoardItem.do'/>"> 
		<input name="controlTabType" title="" type="hidden" value="0:1:0:0" />
		<input name="controlType" title="" type="hidden" value="ORG" />
		<input name="selectType" title="" type="hidden" value="ALL" />
		<input name="selectMaxCnt" title="" type="hidden" value="100" />
		<input name="searchSubFlag" title="" type="hidden" value="" />	
			
		<input name="searchConditionString" type="hidden" value="${searchConditionString}" />
		<input name="popupYn"       type="hidden" value="${popupYn}" />
		<input name="workspaceId" 	type="hidden" value="${board.workspaceId}"          title="<ikep4j:message pre='${preDetail}' key='workspaceId' />"/> 		
		<input name="boardId"      	type="hidden" value="${board.boardId}"             title="<ikep4j:message pre='${preDetail}' key='boardId' />"/>
		<input name="itemParentId" 	type="hidden" value="${boardItem.itemId}"          title="<ikep4j:message pre='${preDetail}' key='itemParentId' />"/>  
		<input name="itemGroupId"  	type="hidden" value="${boardItem.itemGroupId}"     title="<ikep4j:message pre='${preDetail}' key='itemGroupId' />"/>  
		<input name="indentation"  	type="hidden" value="${boardItem.indentation + 1}" title="<ikep4j:message pre='${preDetail}' key='indentation' />"/>  
		<input name="step"         	type="hidden" value="${boardItem.step + 1}"        title="<ikep4j:message pre='${preDetail}' key='step'/>" />  
		<input name="itemDisplay"  	type="hidden" value="${boardItem.itemDisplay}"     title="<ikep4j:message pre='${preDetail}' key='itemDisplay'/>" />
		<input name="itemType" 		type="hidden" value="0"/>
		<input name="msie"        	type="hidden" value="0" />  
		<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
			<caption></caption>
			<col style="width: 15%"/>
			<col style="width: 85%"/> 
			<tbody> 
			<tr> 
				<spring:bind path="boardItem.title">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w100"
						value="[RE] ${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />" />
						<c:forEach items="${status.errorMessages}" var="error">
						    <label for="${status.expression}" class="serverError"> ${error}</label>
						</c:forEach> 
					</div>					
				</td>  
				</spring:bind> 
			</tr>				
			<tr>  
			<th scope="row"><ikep4j:message pre='${preDetail}' key='registerName' /></th>
			<td colspan="3">
				<c:choose>
					<c:when test="${board.anonymous eq 1}">
						<span><ikep4j:message pre='${preDetail}' key='anonymous'/></span>
					</c:when>  
					<c:otherwise>  
						<c:set var="user"   value="${sessionScope['ikep.user']}" />
						<c:set var="portal" value="${sessionScope['ikep.portal']}" />
						<c:choose>
							<c:when test="${user.localeCode == portal.defaultLocaleCode}">
								${user.userName} ${user.jobTitleName} ${user.teamName}
							</c:when>
							<c:otherwise>
								${user.userEnglishName} ${user.jobTitleEnglishName} ${user.teamEnglishName}
							</c:otherwise>
						</c:choose>						
					</c:otherwise> 
				</c:choose> 
			</td>  	 
		</tr>			
			<tr>  
				<spring:bind path="boardItem.tag">
				<th scope="row"><ikep4j:message pre='${preDetail}' key='${status.expression}' /></th>
				<td>
					<div>
						<input 
						name="${status.expression}" 
						type="text" 
						class="inputbox w40" 
						value="${status.value}" 
						size="40" 
						title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"/> 
						<span class="tdInstruction"><ikep4j:message pre='${preDetail}' key='${status.expression}' post="help" /></span>
						<c:forEach items="${status.errorMessages}" var="error">
					   		<label for="${status.expression}" class="serverError"> ${error}</label>
						</c:forEach> 
					</div>
				</td>		
				</spring:bind>  
			</tr>	
			<tr>  
			<spring:bind path="boardItem.contents">
			<td colspan="2" class="ckeditor"> 
				<div id="editorDiv"">					
					<textarea id="contents"
					name="${status.expression}" 
					class="inputbox w100 fullEditor"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"></textarea>					
				</div> 				
			</td> 
			</spring:bind> 
			</tr>
			<tr>			
			<th scope="row"><ikep4j:message pre='${preDetail}' key='readPermission' /></th>
			<td colspan="3">
				<spring:bind path="boardItem.followBoardPermission">
				<div style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.followBoardPermission}"> 
					<label>
					<input name="${status.expression}" 
					type="radio" 
					class="radio" 
					value="${code.key}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					<c:if test="${code.key eq status.value}">checked="checked"</c:if>
					/> 
					<ikep4j:message key='${code.value}'/>
					</label>
				</c:forEach>  
				</div>
				</spring:bind>
				
				<spring:bind path="boardItem.readPermission">
				<div  id="readPermissionDiv" style="display:<c:if test="${boardItem.followBoardPermission==1}">none</c:if>" style="padding-bottom:4px;">
				<c:forEach var="code" items="${boardCode.permissionList}" varStatus="varStatus"> 
					<label>
					<input name="${status.expression}" 
					type="radio" 
					class="radio" 
					value="${code.key}" 
					size="40" 
					title="<ikep4j:message pre='${preDetail}' key='${status.expression}' />"
					<c:if test="${code.key eq status.value}">checked="checked"</c:if>
					/> 
					<ikep4j:message key='${code.value}'/>
					</label>
				</c:forEach> 
				</div>
				
				<div id="readPermissionListDiv" style="display:<c:if test="${boardItem.readPermission!='4'}">none</c:if>">
					<select name="readPermissionList" id="readPermissionList" class="input_select w80" size="4"	style="height:100px;" multiple>
					</select>						
					<a id="addReadPermissionButton" class="button_ic valign_bottom" href="#a" title="<ikep4j:message pre='${preButton}' key='addReadPermission'/>"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" /><ikep4j:message pre='${preButton}' key='addReadPermission'/></span></a>
					<a id="deleteReadPermissionButton" class="button_ic valign_bottom" href="#a" title="<ikep4j:message pre='${preButton}' key='deleteReadPermission'/>"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${preButton}' key='deleteReadPermission'/></span></a>
				</div>	
				</spring:bind>  					
			</td> 
			
			</tr>				
			</tbody> 
		</table>
		<div id="fileUploadArea" class="mt10"></div>
	</form>
</div>
<!--//blockDetail End-->  									
<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<c:if test="${permission.isWritePermission}"><%-- 쓰기 가능 권한인 경우 저장 버튼 활성화 --%>
			<li><a class="button saveBoardItemButton" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
		</c:if> 
		<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemView.do?boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>	
		<c:if test="${not popupYn}">	
			<li><a class="button" href="<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?boardId=${boardItem.boardId}&amp;searchConditionString=${searchConditionString}&amp;popupYn=${popupYn}'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</c:if>
	 </ul>
</div>
<!--//blockButton End-->  
<c:if test="${popupYn}"></div></c:if>