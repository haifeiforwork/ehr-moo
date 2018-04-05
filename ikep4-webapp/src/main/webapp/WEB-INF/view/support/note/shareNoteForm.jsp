<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  value="ui.support.note.readNoteView.detail" />
<c:set var="preButton"  value="ui.support.common.button" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="preMessage" value="message.support.common.note" /> 
<%-- 메시지 관련 Prefix 선언 End --%> 

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.tagsinput.min.js"/>"></script>
<script type="text/javascript" language="javascript">  
var dialogWindow = null;
var fnCaller;
var shareType = "${shareType}";
var itemId = "";

(function($) { 
	
	fnCaller = function (params, dialog) {
		if(params) {
		}

		dialogWindow = dialog;
		$("#popup_contents").delegate("#cancelButton", "click", function() {	
			dialogWindow.close();
		});
	};
	
	$(document).ready(function() {		
		
		$("#submitButton").click(function() {       
			var workspace = $jq("#shareForm").find("select[name=worksapce]");
			if (workspace) {
				if(workspace.val() == "") {
					alert('<ikep4j:message pre="${preMessage}" key="workspaceSelect" />');
					return;
				}				
			}
			
			if($("select[name=boardId]").val() == "") {
				alert('<ikep4j:message pre="${preMessage}" key="boardSelect" />');
				return;
			}

			$jq.ajax({
				url : "<c:url value='/support/note/shareNoteAjax.do'/>",
				data : $jq("#shareForm").serialize(),
				type : "post",
				loadingElement : {container : $jq("#popup_contents")},
				dataType : "html",
				success : function(result) {
					if (result == "") {
						
					} else {
						itemId = result;					
						$("#createDiv").hide();	
						$("#createButtonDiv").hide();
						$("#viewDiv").show();
						$("#viewButtonDiv").show();
						
						<%-- Dialog 창 사이즈 조정 --%>
						var dialogHeight = 190;
						if (shareType == "W") dialogHeight = 220;
						dialogWindow.container.dialog("option", "height", dialogHeight);
						var showTitle = "<ikep4j:message pre="${preDetail}" key="shareTitle1" />";
						dialogWindow.container.dialog("option", "title", showTitle);

						<%-- 워크스페이스 정보 및 배포대상 정보를 화면에 표기 --%>
						$jq("#viewDiv").find("td").each(function() {
							var type = $jq(this).attr("type");
							if (type == "W") {
								$jq(this).text($jq("input[name=workspaceName]").val());
							} else if (type == "B") {
								$jq(this).text($jq("select[name=boardId] option:selected").text());
							}
						});
					}
				}
			});
			
		});  

		$("#goDetailButton").click(function() { 			
			var referenceId = $jq("select[name=boardId]").val();
			var workspaceId = $jq("input[name=workspaceId]").val();
			if (shareType == 'B') {				
				<%-- MainFrame 으로 보여주기 --%>
				reloadFullScreen(encodeURIComponent("/lightpack/board/boardCommon/boardView.do?boardId=" + referenceId + "&itemId=" + itemId));
			} else if (shareType == 'W') {
				<%-- MainFrame 으로 보여주기 --%>
				reloadFullScreen(encodeURIComponent("/collpack/collaboration/main/Workspace.do?workspaceId=" + workspaceId + "&boardId=" + referenceId + "&itemId=" + itemId));
			} else if (shareType == 'K') {
				<%-- MainFrame 으로 보여주기 --%>
				reloadFullScreen(encodeURIComponent("/collpack/knowledge/board/boardCommon/knowledgeView.do?categoryId=" + referenceId + "&itemId=" + itemId + "&popupYn=false"));
			} else {
				
			}
			$("#cancelButton").click();
		});  
		
		$jq("select[name=worksapce]").change(function() {
			$jq("#boardId").ajaxLoadStart("button");
			var wId = $jq("select[name=worksapce] option:selected").val();
			var wNm = $jq("select[name=worksapce] option:selected").text();
			$jq("input[name=workspaceId]").val(wId);
			$jq("input[name=workspaceName]").val(wNm);
			$jq.get("<c:url value='/support/note/shareNoteBoardList.do'/>",{workspaceId:wId},
				function(data) {
					$jq("#boardId option:eq(0)").nextAll().remove();	
					$jq.each(data, function() {	
						$jq("<option/>").attr("value",this.boardId).text(this.boardName).appendTo("#boardId");
					});		
					$jq("#boardId").ajaxLoadComplete();
				}
			)
        });  
		
		$jq("input[name=tag]", "#shareForm").tagsInput();
		
	});  
})(jQuery);  
</script>  

<div id="popup_contents" style="padding-bottom:8px;"> 
	<form name="shareForm" id="shareForm" method="post" action="<c:url value="/support/note/shareNote.do"/>">
		<input type="hidden" name="workspaceId" value=""/>  
		<input type="hidden" name="workspaceName" value=""/>  
		<div id="createDiv" class="blockDetail">
			<table summary="Note Share" style="table-layout:fixed;">
			<caption></caption>
			<colgroup>
				<col width="18%" />
				<col width="*" />
			</colgroup>
			<tbody>
				<c:forEach items="${parameterMap}" var="status"> 
					<c:choose>
						<c:when test="${status.key == 'title'}">	    	
							<tr>
								<th scope="row"><ikep4j:message pre='${preDetail}' key='title' /></th>
								<td>${status.value}</td>  
							</tr>
							<input type="hidden" name="${status.key}" value="${status.value}"/>
						</c:when>
						<c:otherwise>
							<input type="hidden" name="${status.key}" value="${status.value}"/>  
						</c:otherwise>			
					</c:choose>				
				</c:forEach>    	
				<c:if test="${shareType eq 'W'}">
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='workspace' /></th>
					<td>
						<select name="worksapce" class="inputbox" style="min-width:150px; width:90%;">
							<option value=""><ikep4j:message pre='${preMessage}' key='select'/></option>
							<c:forEach var="workspace" items="${workspaceList}">
							<option value="${workspace.workspaceId}">${workspace.workspaceName}</option>
							</c:forEach>
						</select>	
					</td>  
				</tr>		
				</c:if>
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='distribute' /></th>
					<td>
						<select name="boardId" id="boardId" class="inputbox" style="min-width:150px; width:90%;">
							<option value=""><ikep4j:message pre='${preMessage}' key='select'/></option>
							<c:forEach var="board" items="${boardList}">
							<option value="${board.boardId}">${board.boardName}</option>
							</c:forEach>
						</select>	
					</td>  
				</tr>		
				<tr>  
					<th scope="row"><ikep4j:message pre='${preDetail}' key='tag' /></th>
					<td>
						<div>
						<input name="tag" id="tag" type="text" class="inputbox w90" size="40" title="<ikep4j:message pre='${preDetail}' key='tag'/>"/>
						<span class="tdInstruction"><ikep4j:message key='message.support.tagging.rangeTag.help' /></span>
						</div> 						
					</td>		
				</tr>
			</tbody>
			</table>
		</div>
		<div id="viewDiv" class="blockDetail" style="display:none;">
			<table summary="Note Share">
			<caption></caption>
			<colgroup>
				<col width="22%" />
				<col width="78%" />
			</colgroup>
			<tbody>
				<c:forEach items="${parameterMap}" var="status"> 
					<c:if test="${status.key == 'title'}">	    	
					<tr>
						<th scope="row"><ikep4j:message pre='${preDetail}' key='title' /></th>
						<td>${status.value}</td>  
					</tr>
					</c:if>		
				</c:forEach>    	
				<c:if test="${shareType eq 'W'}">
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='workspace' /></th>
					<td type="W"></td>
				</tr>		
				</c:if>
				<tr>
					<th scope="row"><ikep4j:message pre='${preDetail}' key='distribute' /></th>
					<td type="B"></td>  
				</tr>		
			</tbody>
			</table>
		</div>
	</form>	
	<div id="createButtonDiv" class="blockButton mt10"> 
		<ul>
			<li><a class="button" id="submitButton" href="#"><span><ikep4j:message pre='${preButton}' key='share' /></span></a></li>
			<li><a class="button" id="cancelButton" href="#"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<div id="viewButtonDiv" class="blockButton mt10" style="display:none;"> 
		<ul>
			<li><a class="button" id="goDetailButton" href="#"><span><ikep4j:message pre='${preButton}' key='goDetail' /></span></a></li>
			<li><a class="button" id="cancelButton" href="#"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>	 
</div> 