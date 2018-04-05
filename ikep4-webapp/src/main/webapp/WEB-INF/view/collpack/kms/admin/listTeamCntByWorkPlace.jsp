<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.permission.teamcnt.header" /> 
<c:set var="preList"    		value="message.collpack.kms.admin.permission.list" />
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
		
		viewPopUpProfile = function(targetUserId) { 
			var pageURL = "<c:url value='/support/profile/getProfilePopup.do' />?targetUserId=" + targetUserId;
			iKEP.popupOpen( pageURL , {width:820, height:370, callback:function(result) {} });
		};
		
		sort = function(sortColumn, sortType) {
			
			$("#searchUserPermForm input[name=actionType]").val("sort");
			$("#searchUserPermForm input[name=sortColumn]").val(sortColumn); 
			$("#searchUserPermForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');
						
			$("#searchUserPermButton").click();
		}; 
				
		
		$("#searchUserPermForm select[name=workPlaceName]").change(function() {
			
			$("#searchUserPermForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchUserPermForm").submit(); 
			return false;
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
<form id="searchUserPermForm" method="post" action="<c:url value='/collpack/kms/admin/listTeamCntByWorkPlace.do' />">
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
				<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
		                     <tr>
		                         <th scope="row" width="10%"><ikep4j:message pre="${preList}" key="workPlace" /></th>
		                         <td width="20%">
		                         	<spring:bind path="searchCondition.workPlaceName">
			                            <select title="${status.expression}" name="${status.expression}">
			                                <c:forEach var="code" items="${workPlaceList}">		                                 	
												<option value="${code}" <c:if test="${code eq status.value}">selected="selected"</c:if>>${code}</option>
											</c:forEach> 
			                             </select>
		                        	 </spring:bind>
		                         </td>	
		                         <th scope="row" width="10%"><ikep4j:message pre="${preList}" key="teamName" /></th>				
		                         <td scope="row" width="70%">
		                         	<spring:bind path="searchCondition.teamName">
		                         		<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='' size="27"/>
		                         	</spring:bind>
		                         </td>
		                     </tr>														
		                 </tbody>
		             	</table>
		             	<div class="searchBtn"><a id="searchUserPermButton" name="searchUserPermButton" href="#a"><span>Search</span></a></div> 
		             </div>
				</div>             
                            
				<!--//blockListTable Start-->	
				<div class="blockListTable">
				<!--Layout Start-->
				<div id="container">
					<!--List Layout Start-->
					<div id="listDiv"> 
					<table summary="<ikep4j:message pre='${preList}' key='summary' />">
						<caption></caption>
						<thead>
						<tr>							
							<th scope="col" width="10%">
								<ikep4j:message pre='${preList}' key='itemSeqId' />
							</th>							
							<th scope="col" width="30%">
								<a onclick="sort('TEAM_CODE', '<c:if test="${searchCondition.sortColumn eq 'TEAM_CODE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamCode' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CODE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CODE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
							</th>
							<th scope="col" width="30%">
								<a onclick="sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
							</th>
							<th scope="col" width="30%">
								<a onclick="sort('TEAM_CNT', '<c:if test="${searchCondition.sortColumn eq 'TEAM_CNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamObliCnt' />	
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_CNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
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
											${adminTeamLeader.rowNum}
										</td>
										<td>
											${adminTeamLeader.teamCode}
										</td>
										<td>
											${adminTeamLeader.teamName}										
										</td>
										<td>
											${adminTeamLeader.teamCnt}
										</td>									
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
						</tbody>				
					</div>
					<!--//blockListTable End-->
					</table>
					
				
					
					<!--Layout Start-->
					<div id="container">
						<!--List Layout Start-->
						<div id="listDiv"> 						
							
							<!--Page Number Start-->
							<spring:bind path="searchCondition.pageIndex">
								<ikep4j:pagination searchButtonId="searchUserPermButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
								<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
							</spring:bind>
							<!--//Page Number End-->
							</div>
						</div>
					</div>
					
				</div>
			</div>						
		  </div>			
				<!--//splitterBox End-->
</form>
</div>

	