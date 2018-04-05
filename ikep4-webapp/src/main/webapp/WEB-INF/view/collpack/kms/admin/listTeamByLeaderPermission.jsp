<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.permission.team.leader.header" /> 
<c:set var="preList"    		value="message.collpack.kms.admin.permission.list" />
<c:set var="preButton"  		value="message.collpack.kms.admin.permission.button" />
<c:set var="preMessage"  		value="message.collpack.kms.admin.permission.message" />
<c:set var="preInfo"  			value="message.collpack.kms.admin.permission.team.leader.info" />
<c:set var="preAdminSearch"  	value="message.collpack.kms.admin.permission.user.searchCondition" />
<c:set var="preSearch"  		value="ui.ikep4.common.searchCondition" />



 
<%-- 메시지 관련 Prefix 선언 End --%>  

<c:set var="user" value="${sessionScope['ikep.user']}" /> 
 
<script type="text/javascript" src="<c:url value='/base/js/zeroclipboard/ZeroClipboard.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery.layout-latest.js'/>"></script>
<script type="text/javascript">
<!--   
var bbsIframe;  	 // 부모 iframe
var bbsIsIframe = 0; // iframe 존재 여부
var isLayout; // 레이아웃 보기 여부
var bbsLayout = null;
var layoutType = "n"; // n:없음, v:가로보기, h:세로보기

(function($){	 
	/* window risize 시 Contaner 높이 조절 */
	resizeContanerHeight = function(){
		var docHeight = 0;
		var adjustHeight = 20;		
		var $lefMenu;
		var $Container	= $('#container');
		
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top);
				}
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-17);				
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}	
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		} 
	}
	
	/* Contaner & iframe 높이 조절 */
	setContanerHeight = function() {
		var docHeight = 0;
		var adjustHeight = 20;
		var $lefMenu;
		var $Container	= $('#container');
		
		// layout 존재하므로 layout destroy 함수 호출시
		if(isLayout){
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				docHeight = $(parent).height();
				$lefMenu = $("#leftMenu", parent.document);
				var leftMenuPostion = $lefMenu.offset();
				if($lefMenu.length > 0) {
					$lefMenu.height(docHeight - leftMenuPostion.top)
					.css({overflowY:"auto",overflowX:"hidden"});
				}
				
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( docHeight - $Container.offset().top - adjustHeight );
				// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
				bbsIframe.height(docHeight-19);
			}else{
				// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
				$Container.height( $(window).height() - $Container.offset().top - adjustHeight );
			}			
			
		}else{ // layout 없으므로 layout 함수 호출시
			// iframe 안에서 호출된 경우
			if(bbsIsIframe>0){
				$lefMenu = $("#leftMenu", parent.document);
				$lefMenu.css({overflowY:"",overflowX:""});
				bbsIframe.height($(document).height());
			}
		
		}
		
		if(bbsLayout != null){
			bbsLayout.resizeAll();	
		}
		
	}
	
		
	
	$(document).ready(function() {   		
		
		sort = function(sortColumn, sortType) {
			
			$("#searchUserPermForm input[name=actionType]").val("sort");
			$("#searchUserPermForm input[name=sortColumn]").val(sortColumn); 
			$("#searchUserPermForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');						
			$("#searchUserPermButton").click();
		}; 
				
		
		$("#checkboxAllTeam").click(function() { 
			$("#searchUserPermForm input[name=checkTeam]").attr("checked", $(this).is(":checked"));  
		}); 
		
		$("#deleteTeamButton").click(function() {  
			var itemIds = new Array();
			
			$("#searchUserPermForm input[name=checkTeam]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});	
			
			if (itemIds.length > 0 ){	
				if(confirm("<ikep4j:message pre="${preMessage}" key="team.delete" />")) {
					$("#searchUserPermForm").ajaxLoadStart(); 
					
					$.post("<c:url value='/collpack/kms/admin/deleteTeamByLeader.do'/>", {"userId" : "${adminPermission.userId}", "teamCodes" : itemIds.toString()}) 
					.success(function(data) {  
						
						$("#searchUserPermButton").click();
					})				
				}
			}
		}); 
		
		
		$("#addTeamButton").click(function() {  
			
			var teamCode = $("#searchUserPermForm select[name=teamCode] > option:selected").val();
			
			if(teamCode != ""){
				
				if(confirm("<ikep4j:message pre="${preMessage}" key="team.add" />")) {
			
					$("#searchUserPermForm").ajaxLoadStart(); 
					
					$.post("<c:url value='/collpack/kms/admin/addTeamByLeader.do'/>", {"userId" : "${adminPermission.userId}", "teamCode" : teamCode}) 
					.success(function(data) {
						$("#searchUserPermButton").click();
					})				
				}  
			}
			
			
		}); 
		
		
		$("#searchUserPermButton").click(function() { 
			
			$("#searchUserPermForm input[name=actionType]").val("search");			
			$("#searchUserPermForm").submit();
			
			return false; 
		});
		
		
		
		$("#searchUserPermForm select[name=pagePerRecord]").change(function() { 
			$("#searchUserPermForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchUserPermForm").submit(); 
			return false;
		});  
		
		
		$("#searchUserPermForm select[name=workPlaceName]").change(function() { 
			var workPlaceName = $("#searchUserPermForm select[name=workPlaceName]").val();
			
			if(workPlaceName == ""){
				$("#teamCode").empty();
				var teamCode = "<option value=''>" + "<ikep4j:message pre="${preInfo}" key="add.teamCode" />" +"</option>";
				$("#teamCode").append(teamCode);
			}else{			
				$.post("<c:url value='/collpack/kms/admin/listTeamCodes.do'/>", {"workPlaceName" : workPlaceName}) 
				.success(function(data) {
					$("#teamCode").empty();
					$("#teamCode").append(data);
				})
			}
		}); 
		
		
		/* iframe 구성여부 확인 */
		bbsIframe = $(parent.document).find("iframe[name=contentIframe]");
		bbsIsIframe = bbsIframe.length;
		
		/* 기본 layout 설정 여부 */ 
		isLayout = false;	
		layoutType = "n";		
		
		/* 윈도우 resize 이벤트 */
		$(window).bind("resize", resizeContanerHeight);		
				
		
		$("a.boardItem").click(function() {
			$("*.boardItemLine").removeClass("bgSelected");			
			$(this).parents("*.boardItemLine").addClass("bgSelected");
			 if(!isLayout) {  
				<c:choose>
					<c:when test="${popupYn}"> 
						iKEP.popupOpen($(this).attr("href")+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}", {width:1000, height:600, resizable:false, callback:function(result) {
							location.href = "<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do'/>?boardId=" + result[0] + "&searchConditionString=" +  result[1] + "&popupYn=${popupYn}";  
						}}, "param");   
						
						return false;	
					</c:when> 
					<c:otherwise>
						return true;
					</c:otherwise>
				</c:choose>
				
			 } else {  
				return true;
			}
			 
			return false;			
		});		
	});
		
})(jQuery);
//-->
</script>
<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchUserPermForm" method="post" action="<c:url value='/collpack/kms/admin/listTeamByLeaderPermission.do' />">
<input type="hidden" id="searchUserPermButton" name="searchUserPermButton" />
<input type="hidden" id="userId" name="userId" value="${adminPermission.userId}" />
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.actionType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--mainContents Start-->	 
<!--pageTitle Start-->
                <!--tableTop Start-->
				<div class="tableTop">
					<h2><ikep4j:message pre="${preHeader}" key="title" /></h2>
					<div class="tableTop_bgR"></div>					 
					<div class="listInfo">  
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${announceCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
					</div>	
				</div>
				<!--//tableTop End-->
				
				
				<!--blockDetail Start-->
                <div class="blockDetail">
                    <table summary="new group">
                        <colgroup>
                            <col width="20%"/>
                            <col width="80%"/>
                        </colgroup>
                        <tbody>                        	
                            <tr>
                                <th scope="row"><ikep4j:message pre='${preInfo}' key='user' /></th>
                                <td>
                                	<input name="" title="" class="inputbox" type="text" size="27" value="${adminPermission.userName}" readonly />
                                    <input name="" title="" class="inputbox" type="text" size="27" value="${adminPermission.empNo}" readonly />
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><ikep4j:message pre='${preInfo}' key='teamName' /></th>
                                <td>
                                	<input name=""  title="" class="inputbox" type="text" size="27" value="${adminPermission.teamName}" readonly/>
                                    <input name=""  title="" class="inputbox" type="text" size="27" value="${adminPermission.workPlaceName}" readonly/>
                                </td>
                            </tr>
                            <tr>
                                <th scope="row"><ikep4j:message pre='${preInfo}' key='add' /></th>
                                <td>
                                    <select title="workPlaceName" name="workPlaceName">
                                        <option value=""><ikep4j:message pre='${preInfo}' key='add.company' /></option>
                                        <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
                                        	<option value="${workPlace}">${workPlace}</option>
                                        </c:forEach>
                                    </select>
                                    
                                    <select title="teamCode" name="teamCode" id="teamCode">
                                        <option value=""><ikep4j:message pre='${preInfo}' key='add.teamCode' /></option>
                                    </select>
                                    
                                    <a class="button_s" id="addTeamButton" href="#a"><span><ikep4j:message pre='${preInfo}' key='add.title' /></span></a>
                                </td>
                            </tr>
                            
                        </tbody>
                    </table>
                </div>
                <!--//blockDetail End-->                            
				<!--//blockListTable Start-->	
				<div class="blockListTable">
				
					<table summary="<ikep4j:message pre='${preList}' key='summary' />">
						<caption></caption>
						<thead>
						<tr>
							<th scope="col" width="3%">
								<!-- <input name="" class="checkbox" title="checkbox" type="checkbox" value="" /> -->
								<input id="checkboxAllTeam" class="checkbox" title="checkbox" type="checkbox" value="" />
							</th>							
							<th scope="col" width="7%">
								<ikep4j:message pre='${preList}' key='itemSeqId' />
							</th>							
							<th scope="col" width="45%">
								<a onclick="sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
							</th>
							<th scope="col" width="45%">
								<a onclick="sort('TEAM_CODE', '<c:if test="${searchCondition.sortColumn eq 'TEAM_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamCode' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CODE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CODE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
							</th>											
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="8" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="adminTeamLeader" items="${searchResult.entity}">
									<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine">													
										<td>
											<input name="checkTeam" class="checkbox" title="checkbox" type="checkbox" value="${adminTeamLeader.teamCode}" />
										</td>
										<td>
											${adminTeamLeader.rowNum}											
										</td>
										<td>
											${adminTeamLeader.teamName}										
										</td>
										<td>
											${adminTeamLeader.teamCode}
										</td>																				
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
						</tbody>				
					
					<!--//blockListTable End-->
					</table>
					<div class="pageNum"> 						
						
						<!--Page Number Start-->
						<spring:bind path="searchCondition.pageIndex">
							<ikep4j:pagination searchButtonId="searchUserPermButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
							<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
						</spring:bind>
						<!--//Page Number End-->
					</div>
				</div>
				 <!--blockButton Start-->
                <div class="blockButton"> 
                    <ul>
                        <li><a id="deleteTeamButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
                        <li><a id="listTeamButton" class="button" href="<c:url value='/collpack/kms/admin/listLeaderPermission.do'/>"><span><ikep4j:message pre='${preButton}' key='list' /></span></a></li>
                    </ul>
                </div>
                <!--//blockButton End-->
					
				</div>
			</div>						
		  </div>			
				<!--//splitterBox End-->
				</div>
</form>
</div>

	