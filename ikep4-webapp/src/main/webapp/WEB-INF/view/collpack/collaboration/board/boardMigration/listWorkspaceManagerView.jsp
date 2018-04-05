<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="preHeader"  value="message.collpack.collaboration.board.boardMigration.listBoardTreeView.header" /> 
<c:set var="preDetail"  value="message.collpack.collaboration.board.boardMigration.listBoardTreeView.detail" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.board" />   
<c:set var="preSearch"  value="message.collpack.collaboration.member.listWorkspaceMember.search" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!-- 

var dialogWindow = null;
function fnCaller(paraams, dialog) {
	dialogWindow = dialog;
}

function applyMigration(orgWorkspaceId,orgBoardId,targetWorkspaceId,targetBoardId) {
	dialogWindow.callback({orgWorkspaceId:orgWorkspaceId, orgBoardId:orgBoardId, targetWorkspaceId:targetWorkspaceId, targetBoardId:targetBoardId});
	dialogWindow.close();
}

var tab, $boardTree;
var $treeNode;

(function($){	
	
	viewBoardTree = function(targetWorkspaceId){
		var workspaceId = "${workspaceId}";
		
		if(workspaceId !=targetWorkspaceId )
			boardTree.location.href="<c:url value='/collpack/collaboration/board/boardMigration/listTargetBoardTreeView.do' />?workspaceId="+workspaceId+"&targetWorkspaceId="+targetWorkspaceId+"&boardId=${boardId}";
		else 	{
	    	alert('<ikep4j:message pre="${preMessage}" key="sameWorkspace" />')
	    	return false;
	    }
	};
		
	$(document).ready(function() {  

		$("tbody > tr").click(function(){

			var index = $('tr').index(this);
			$('tbody >tr').each(function(idx){

				if(index==idx+1)
				{
					$(this).addClass('bgSelected')
				}
				else
					$(this).removeClass();
			});

		});

		
	});	 
})(jQuery); 
//-->
</script> 

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div class="" id="layer_p" title="<ikep4j:message pre="${preDetail}" key="summary" />">
 
	<!--blockLeft Start-->
	<div class="blockLeft" style="width:68%;">
		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre="${preDetail}" key="tableMessage" /></h3>
		</div>
		<!--//subTitle_2 End-->
 
		<!--blockListTable Start-->
		<div class="blockListTable">
			<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col"><ikep4j:message pre='${preDetail}' key='typeId' /></th>
						<th scope="col"><ikep4j:message pre='${preDetail}' key='workspaceName' /></th>
						<th scope="col"><ikep4j:message pre='${preDetail}' key='sysopName' /></th>
					</tr>
				</thead>
				<tbody>
				<c:choose>
				    <c:when test="${empty workspaceList}">
						<tr>
							<td colspan="3" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
						</tr>				        
				    </c:when>
				    <c:otherwise>
						<c:forEach var="workspaceList" items="${workspaceList}">
						<tr class="list-selectable">
							<td>${workspaceList.typeName}</td>
							<td class="textLeft">
								<a onclick="viewBoardTree('${workspaceList.workspaceId}');" href="#a" title="${workspaceList.workspaceName}">
								${workspaceList.workspaceName}
								</a>
							</td>
							<td>${workspaceList.sysopName}</td>
				
						</tr>
						</c:forEach>				      
				    </c:otherwise> 
				</c:choose>  				
																																	
				</tbody>
			</table>				
		</div>
		<!--//blockListTable End-->	
	</div>
	<!--//blockLeft End-->
		
	<!--blockRight Start-->
	<div class="blockRight" style="width:30%;">
		
		<!--subTitle_2 Start-->
		<div class="subTitle_2 noline">
			<h3><ikep4j:message pre="${preDetail}" key="selectBoard" /></h3>
		</div>
		<!--//subTitle_2 End-->
		
		<div class="leftTree treeBox">
			<iframe id="boardTree" name="boardTree" width="100%" height="350" frameborder="0" scrolling="auto">

			<!-- div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div>
			<div id="boardTree"></div> -->
		</div>
	</div>
	<!--//blockRight End-->
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="migration" /></span></a></li>
			<li><a id="cancelButton" class="button" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</div>	
<!-- //Modal window End -->


