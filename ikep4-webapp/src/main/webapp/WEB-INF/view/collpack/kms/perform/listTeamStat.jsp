<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="prePrivate"    		value="message.collpack.kms.perform.private" />
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
var excelBtnCnt = 0;

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
		
		$("input.datepicker").datepicker({
			onSelect: function(date, event) {
				var arrDate = date.split(".");
				//var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
				//event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "." + (objDate.getMonth()+1) + "." + objDate.getDate());
			}
		});
		
		$("#searchForm select[name=workPlaceName]").change(function() { 
			var workPlaceName = $("#searchForm select[name=workPlaceName]").val();
			
			if(workPlaceName == ""){
				$("#teamCode").empty();
				var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
				$("#teamCode").append(teamCode);
			}else{
				$("#searchForm").submit(); 
				return false;
			}
		}); 
		
		$("#searchForm select[name=teamCode]").change(function() { 
			$("#teamName").val($("#teamCode option:selected").text());
			$("#searchForm").submit(); 
			return false;
		}); 
		
		
		$("#searchForm select[name=searchYear]").change(function() { 
			$("#teamName").val($("#teamCode option:selected").text());
			$("#searchForm").submit(); 
			return false;
		}); 
		
		$("#searchForm select[name=searchMonth]").change(function() { 
			$("#teamName").val($("#teamCode option:selected").text());
			$("#searchForm").submit(); 
			return false;
		}); 
				
		
		sort = function(sortColumn, sortType) { 
			$("#searchForm input[name=actionType]").val("sort");
			$("#searchForm input[name=sortColumn]").val(sortColumn); 
			$("#searchForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchButton").click();
		}; 
			
		
		
		$("#searchButton").click(function() { 
			$("#teamName").val($("#teamCode option:selected").text());
			$("#searchForm input[name=actionType]").val("search");			
			$("#searchForm").submit();
			
			return false; 
		});
		
		
		
		$("#excelButton").click(function() { 
			
			if(${searchResult.emptyRecord}){
				alert("<ikep4j:message key='message.collpack.kms.perform.excel.nodata'/>");
				return false;
			}		
			if(excelBtnCnt == 0){
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/downloadExcelTeamStat.do'/>");
				$jq("#searchForm").submit();
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/listTeamStat.do'/>");
				excelBtnCnt++;
			}else{
				alert("조회 후 엑셀다운 받으세요");
			}
			return false; 
		});
				
		
			
		$("#searchForm select[name=pagePerRecord]").change(function() {
			$("#searchForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchForm input[name=searchWord]").val("");
			$("#searchForm").submit(); 
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
		
				
	});
	
	
})(jQuery);
//-->
</script>
<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchForm" method="post" action="<c:url value='/collpack/kms/perform/listTeamStat.do' />">
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.actionType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.teamName">
<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<!--mainContents Start-->	 
<!--pageTitle Start-->
                <!--tableTop Start-->
				<div class="tableTop">
					<h2><ikep4j:message  key="message.collpack.kms.perform.team.stat.header.title" /></h2>
					<div class="tableTop_bgR"></div>					 
					<div class="listInfo">  
						<spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<c:forEach var="code" items="${announceCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
							</select> 
						</spring:bind>
						<div class="totalNum"><ikep4j:message  key='ui.ikep4.common.searchCondition.pageCount.info' /><span> ${searchResult.recordCount}</span></div>
					</div>					
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->					
				<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
							<tr>
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='condition.workPlaceName' /></th>
                               <td width="28%">
                               <c:choose>
	                               	<c:when test="${isSystemAdmin == true}">  
	                               		<spring:bind path="searchCondition.workPlaceName">                             			
		                                   	<select title="${status.expression}" name="${status.expression}">
		                                        <option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.company' /></option>	                                        
		                                        <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
		                                        	<option value="${workPlace}" <c:if test="${searchCondition.workPlaceName eq workPlace}">selected="selected"</c:if>>${workPlace}</option>
		                                        </c:forEach>
		                                    </select>	                                    
		                                </spring:bind>
		                                <spring:bind path="searchCondition.teamCode">
		                                    <select title="teamCode" name="${status.expression}" id="${status.expression}">
		                                    	<option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.teamCode' /></option>
		                                        <c:forEach var="teamInfo" items="${teamList}">
		                                        	<option value="${teamInfo.teamCode}" <c:if test="${searchCondition.teamCode eq teamInfo.teamCode}">selected="selected"</c:if>>${teamInfo.teamName}</option>
		                                        </c:forEach>	                                        
		                                    </select>
										</spring:bind>		                                    
									</c:when>
									<c:otherwise>
										<input type="text" class="inputbox" title="" name="" value="${user.workPlaceName} ${user.teamName}" size="20" readonly/> 
									</c:otherwise>   
								</c:choose>                                 
                               </td> 
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='list.searchMonth' /></th>
                               <td width="25%">
                               		<input class="datepicker" type="text" name="startDate" id="startDate" size="10" value="<c:out value='${fn:substring(searchCondition.startDate, 0, 10)}'/>" readonly/> ~
									<input class="datepicker" type="text" name="endDate" id="endDate" size="10" value="<c:out value='${fn:substring(searchCondition.endDate, 0, 10)}'/>" readonly/>
                               </td>		
                               <td class="textRight">
                               <a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
                               <a href="#a" id="excelButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_excel.gif'/>" alt="" /></a>
                               </td>				
                           </tr>														
		                 </tbody>
		             	</table>
		             	 
		             </div>
				</div>             
                            
				<!--//blockListTable Start-->	
				<div class="blockListTable">
				<!--Layout Start-->
				<div id="container">
					<!--List Layout Start-->
					<div id="listDiv"> 
					<table summary="">
						<caption></caption>
						<thead>
						<tr>
							<th scope="col" width="6%">
								<ikep4j:message pre='${prePrivate}' key='list.itemId' />
							</th>							
							<th scope="col" width="*">								
								<a onclick="sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.teamName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>
							<th scope="col" width="7%">								
								<ikep4j:message pre='${prePrivate}' key='list.memberCnt' />
							</th>	
							<th scope="col" width="7%">
								<ikep4j:message pre='${prePrivate}' key='list.obligationCnt' />	
							</th>
							<th scope="col" width="7%">
								<a onclick="sort('REG_SUM', '<c:if test="${searchCondition.sortColumn eq 'REG_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.regCnt' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'REG_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'REG_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>											
							</th>
							<th scope="col" width="7%">								
								<ikep4j:message pre='${prePrivate}' key='list.regRate' />			
							</th>	
							<th scope="col" width="7%">								
								<a onclick="sort('MARK_SUM', '<c:if test="${searchCondition.sortColumn eq 'MARK_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.markSum' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'MARK_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'MARK_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>	
							<th scope="col" width="7%">								
								
								<a onclick="sort('HIT_SUM', '<c:if test="${searchCondition.sortColumn eq 'HIT_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.hitSum' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'HIT_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'HIT_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
							<th scope="col" width="8%">
								
								<a onclick="sort('USAGE_SUM', '<c:if test="${searchCondition.sortColumn eq 'USAGE_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.usageSum' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'USAGE_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'USAGE_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>				
							</th>
							<th scope="col" width="7%">								
								<a onclick="sort('RECOMMEND_SUM', '<c:if test="${searchCondition.sortColumn eq 'RECOMMEND_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.recommendSum' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'RECOMMEND_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>
							<th scope="col" width="8%">								
								<a onclick="sort('LINEREPLY_SUM', '<c:if test="${searchCondition.sortColumn eq 'LINEREPLY_SUM'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.lineReplySum' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_SUM' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'LINEREPLY_SUM' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>		
							<th scope="col" width="8%">
								<ikep4j:message pre='${prePrivate}' key='list.conversionMark' />
							</th>							
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="12" class="emptyRecord"><ikep4j:message key='ui.ikep4.common.searchCondition.emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="performance" items="${searchResult.entity}">
									<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine">
										<td>
											${performance.rowNum}
										</td>
										<td>
											${performance.teamName}
										</td>
										<td>
											${performance.memberCnt}
										</td>		
										<td>
											${performance.obligationSum}
										</td>	
										<td>
											${performance.regSum}
										</td>	
										<td>
											${performance.obligationRegRate}
										</td>	
										<td>
											${performance.markSum}
										</td>	
										<td>
											${performance.hitSum}
										</td>	
										<td>
											${performance.usageSum}
										</td>	
										<td>
											${performance.recommendSum}
										</td>	
										<td>
											${performance.lineReplySum}
										</td>
										<td>
											${performance.conversionMark}
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
								<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
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

	