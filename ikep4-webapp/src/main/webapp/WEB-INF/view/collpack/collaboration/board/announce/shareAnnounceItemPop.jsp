<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.announce.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.announce.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.collaboration.announce.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.collaboration.announce.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.collaboration.announce.boardItem.message.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
var dialogWindow = null;
var fnCaller;
(function($) {
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {
		$("input[name=sortColumn]").val(sortColumn);
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		$("#searchShareListButton").click();
	};

	$(document).ready(function() {
		
		iKEP.iFrameContentResize();
		
		fnCaller = function (params, dialog) {
			dialogWindow = dialog;
			$jq("#closePopButton").click(function() {
				dialogWindow.close();
				return false;
			});
		};		
		
		$jq("#shareCollButton").click(function() {
			var countCheckBox	=	$("input[name=workspaceIds]:checkbox:checked").length;
			if(countCheckBox>0) {
				if(confirm("<ikep4j:message pre='${preMessage}' key='shareItem' />")) {
					$jq.ajax({
						url : '<c:url value="/collpack/collaboration/board/announce/shareCollAnnounceItem.do"/>?'+$jq("#popWokrspaceForm").serialize(),
							type : "get",
							success : function() {
								dialogWindow.callback();	//parent.location.href="<c:url value='/collpack/collaboration/board/announce/listAnnounceItemView.do'/>?workspaceId=${workspaceId}";
								dialogWindow.close();
							},
							error : function() {alert("error");}
					});
				}
			}else{
				alert("<ikep4j:message pre='${preMessage}' key='emptySelectedWorkspace' />");
			}
		});
		
		$jq("#searchShareListButton").click(function() {
			$("#popWokrspaceForm").attr({method:'GET'}).submit(); 
			return false; 
		});
		
		$("#checkboxAllWorkspaceId").click(function() { 
			$("#popWokrspaceForm input[name=workspaceIds]").attr("checked", $(this).is(":checked"));  
		});
	});
})(jQuery);
</script>
<form id="popWokrspaceForm" method="POST" action="<c:url value='/collpack/collaboration/board/announce/shareAnnounceItemPop.do'/>">
<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 

	<input name="itemId" type="hidden" value="${itemId}" title="" />

	<input name="workspaceId" type="hidden" value="${workspaceId}" title="" />

<!--blockListTable Start--> 
<div class="blockDetail">
	<table>
		<caption></caption> 
		<col style="width: 15%;"/>
		<col style="width: 30%;"/>
		<col style="width: 55%;"/>
		<thead>
			<tr>
				<th class="textCenter"><input id="checkboxAllWorkspaceId" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
				<th class="textCenter">
					<a onclick="sort('WORKSPACE_NAME', '<c:if test="${searchCondition.sortColumn eq 'WORKSPACE_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='workspaceName' />
					</a>
				</th>
				<th scope="col" class="textCenter">
					<a onclick="sort('FULL_PATH', '<c:if test="${searchCondition.sortColumn eq 'FULL_PATH'}">${searchCondition.sortType}</c:if>');"  href="#a">
						<ikep4j:message pre='${preList}' key='fullPath' />
					</a>
				</th>
			</tr>
		</thead>
		<tbody>
			<c:choose>
			    <c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="3" class="emptyRecord textCenter"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
					</tr>				        
			    </c:when>
			    <c:otherwise>
					<c:forEach var="workspaceInfo" items="${searchResult.entity}">
					<tr>
						<td class="textCenter">
							<c:if test="${workspaceInfo.hasWorkspace>0}">
								<input name="workspaceIds" class="checkbox" title="checkbox" type="checkbox"  value="${workspaceInfo.workspaceId}" <c:if test="${workspaceInfo.shareFlagCount !=0}">checked</c:if>/>
							</c:if> 
						</td>		
						<td>
							<c:forEach begin="1" end="${workspaceInfo.groupLevel}">
								&nbsp;&nbsp;
							</c:forEach>
							${workspaceInfo.workspaceName}
						</td>
						<td>
							${workspaceInfo.fullPath}
						</td>
					</tr>
					</c:forEach>				      
			    </c:otherwise> 
			</c:choose>  
		</tbody>
	</table>
	<input type="hidden" id="searchShareListButton"/>
	<div id="splitterBox">
		<div>
		<!--Page Number Start
		<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchShareListButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
		</spring:bind>
		--> 
		</div>
		<!--//Page Number End-->
		<br/>
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a id="shareCollButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='shareColl'/></span></a></li>
				<li><a id="closePopButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
			</ul>
			
		</div> 
		<!--//blockButton End-->
	</div>
	
</div>
</form>