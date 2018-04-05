<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preList"    value="ui.lightpack.board.workspaceItem.listBoardView.list" />
<c:set var="prefix"    value="message.collpack.collaboration.workspace.createWorkspace.search" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />

<script type="text/javascript">
//<![CDATA[

function mouseOver(obj){obj.className = "bgSelected";}
function mouseOut(obj){obj.className = "";}

(function($) {
	$jq(document).ready(function() { 
		
		$jq("select[name=pagePerRecord]").change(function() {
			getList();
        });  
		
		$jq('#searchWord').keypress(function(event) { 
			if(event.which == '13') {
				getList();
			}
		});
		
		$("#checkboxAllBoardItem").click(function() { 
			$("#searchForm input[name=checkboxBoardItem]").attr("checked", $(this).is(":checked"));  
		});  
		$("#adminMultiDeleteBoardItemButton, #adminMultiDeleteBoardItemButton1").click(function() {  
			var itemIds = new Array();
			
			$("#searchForm input[name=checkboxBoardItem]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});			 
			
			if(confirm("변경하시겠습니까?")) {
				$("#searchForm").ajaxLoadStart(); 
				
				$.post("<c:url value='/lightpack/planner/team/updatePlannerMenuList.do'/>", {"plannerId" : "", "itemId" : itemIds.toString(), "popupYn" : ""}) 
				.success(function(data) {  
					getList();
				})
			}  
		});  
	});
	
	getList = function() {
		$("#searchForm")[0].submit();
	};
    
    sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
})(jQuery);
//]]>
</script>

<form id="searchForm" method="post" action="<c:url value='/lightpack/planner/team/getPlannerTeamMenuList.do'/>" onsubmit="return false;">

<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>모바일 메뉴관리</h2>
	<div class="listInfo">  
	</div>	  
	<div class="tableSearch"> 
	</div>		
	<div class="clear"></div>
</div>
<!--//tableTop End-->
<!--splitterBox Start-->
<div id="splitterBox">
	<div>
		<!--blockListTable Start-->
		<div class="blockListTable">
			<table id="workspaceItemTable">   
				<caption></caption> 
				<col style="width: 10%;"/>
				<col style="width: 10%;"/>
				<col style="width: 80%;"/>
				<%-- col style="width: 7%;"/>
				<col style="width: 7%;"/--%>
				<thead>
					<tr>
						<th scope="col"><input id="checkboxAllBoardItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th> 
						<th scope="col">
							번호
						</th>
						<th scope="col">
							메뉴명
						</th>
					</tr>
				</thead> 
				<tbody>
					<c:choose>
					    <c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="7" class="emptyRecord"><ikep4j:message pre='${prefix}' key='emptyRecord' /></td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="workspaceItem" items="${searchResult.entity}" varStatus="status"> 
							<tr class="<c:if test="${itemDisplay eq 1}"></c:if> workspaceItemLine"  onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
								<td>
									<input name="checkboxBoardItem" class="checkbox" title="checkbox" type="checkbox" value="${workspaceItem.workspaceId}" <c:if test="${workspaceItem.presentWorkspaceId=='1' }">checked=checked</c:if> /> 
								</td>
								<td>${status.count }</td>
								<td align=center>${workspaceItem.workspaceName}</td>
							</tr>
							</c:forEach>				      
					    </c:otherwise> 
					</c:choose>  
				</tbody>
			</table>
		</div>
		<!--//blockListTable End-->
	</div>
</div>
			<!--blockButton Start-->
			<div class="blockButton"> 
				
				<li><a id="adminMultiDeleteBoardItemButton" class="button" href="#a"><span>변경</span></a></li>

			</div> 
			<!--//blockButton End-->  
<!--//splitterBox End-->
</form>