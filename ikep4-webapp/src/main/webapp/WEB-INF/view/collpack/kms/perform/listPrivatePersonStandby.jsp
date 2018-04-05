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

function mouseOver(obj){
	
	var tds = $jq(obj).find("td");
	$jq.each(tds, function(){
		$jq(this).attr("style", "background-color:#edf2f5;");
	})
}

function mouseOut(obj){
	
	var tds = $jq(obj).find("td");
	$jq.each(tds, function(){
		
		$jq(this).attr("style", "background-color:none;");
	})
	
}


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
		
		$("#registerName").click(function() {
			
			var alreadySelectedPerson = "${searchCondition.isPerson}";
			if(alreadySelectedPerson == "Y"){
				
				return false;
			}
			
		});
		
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
		
		$("#searchForm select[name=isknowhowbox]").change(function() { 
			var isknowhow = $("#searchForm select[name=isknowhowbox]").val();
			$jq("#searchIsknowhow").val(isknowhow);
		}); 
		
		$("#searchForm select[name=teamCode]").change(function() { 
			$("#teamName").val($("#teamCode option:selected").text());
			$("#searchForm").submit(); 
			return false;
		}); 
		
		
		$("#searchForm select[name=searchYear]").change(function() { 
			
			var selectedIsPerson = $("input:radio[name=isPerson]:checked").val();
			var alreadySelectedPerson = "${searchCondition.isPerson}";
			
			
			if(alreadySelectedPerson == "Y" && selectedIsPerson == "N"){
				
				$("#registerName").val("");
				$("#teamCode").val("");
			}
			
			
			if(selectedIsPerson == 'Y'){
				//alert("second:" + selectedIsPerson);
				$("#teamCode").val("");
				$("#registerName").val("");
			}
			
			$("#searchForm").submit(); 
			return false;
		}); 
		
		$("#searchForm select[name=searchMonth]").change(function() { 
			
			var selectedIsPerson = $("input:radio[name=isPerson]:checked").val();
			var alreadySelectedPerson = "${searchCondition.isPerson}";
			
			
			if(alreadySelectedPerson == "Y" && selectedIsPerson == "N"){
				
				$("#registerName").val("");
				$("#teamCode").val("");
			}
			
			
			if(selectedIsPerson == 'Y'){
				//alert("second:" + selectedIsPerson);
				$("#teamCode").val("");
				$("#registerName").val("");
			}
			
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
			
			$("#searchForm input[name=actionType]").val("search");
			$("#teamName").val($("#teamCode option:selected").text());
			
			var selectedIsPerson = $("input:radio[name=isPerson]:checked").val();
			var alreadySelectedPerson = "${searchCondition.isPerson}";
			if(selectedIsPerson == "N"){
				alreadySelectedPerson = "N";
			}
			
			if(alreadySelectedPerson == "Y" && selectedIsPerson == "N"){
				
				$("#registerName").val("");
				$("#teamCode").val("");
			}
			
			
			if(selectedIsPerson == 'Y'){
				//alert("second:" + selectedIsPerson);
				$("#teamCode").val("");
				$("#registerName").val("");
			}
			
			$jq("#searchIsknowhow").val($("#searchForm select[name=isknowhowBox]").val());
			
			$("#searchForm").submit();
			
			return false; 
		});
		
		isPersonClk = function() {
			
			$("#searchForm input[name=actionType]").val("search");
			$("#teamName").val($("#teamCode option:selected").text());
			
			var selectedIsPerson = $("input:radio[name=isPerson]:checked").val();
			var alreadySelectedPerson = "${searchCondition.isPerson}";
			if(selectedIsPerson == "N"){
				alreadySelectedPerson = "N";
			}
			
			if(alreadySelectedPerson == "Y" && selectedIsPerson == "N"){
				
				$("#registerName").val("");
				$("#teamCode").val("");
			}
			
			
			if(selectedIsPerson == 'Y'){
				//alert("second:" + selectedIsPerson);
				$("#teamCode").val("");
				$("#registerName").val("");
			}
			
			$jq("#searchIsknowhow").val($("#searchForm select[name=isknowhowBox]").val());
			
			$("#searchForm").submit();
			
			return false; 
		}
		
		$("input[name=isPerson]").change(function() {
			var radioValue = $(this).val();

			if(radioValue == "N"){
				$("#registerName").val("");
				$("#registerName").attr("disabled",false);
			}else{
				$("#registerName").val("${user.userName}");
				$("#registerName").attr("disabled",true);
			}
		});
		
		
		
		$("#excelButton").click(function() { 
			
			if(${searchResult.emptyRecord}){
				alert("<ikep4j:message key='message.collpack.kms.perform.excel.nodata'/>");
				return false;
			}		
			
			if(excelBtnCnt == 0){
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/downloadExcelPrivate.do'/>");
				$jq("#searchForm").submit();
				$jq("#searchForm").attr("action","<c:url value='/collpack/kms/perform/listPrivateStandby.do'/>");
				excelBtnCnt++;
			}else{
				alert("조회 후 엑셀다운 받으세요");
			}				
			
			return false; 
		});
				
		
			
		$("#searchForm select[name=pagePerRecord]").change(function() {
			$("#searchForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchForm input[name=searchWord]").val("");
			$jq("#searchIsknowhow").val($("#searchForm select[name=isknowhowBox]").val());
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
<form id="searchForm" method="post" action="<c:url value='/collpack/kms/perform/listPrivateStandby.do' />">
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
<spring:bind path="searchCondition.workPlaceName">
<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<input type="hidden" id="searchIsknowhow" name="searchIsknowhow"/> 	

<!--mainContents Start-->	 
<!--pageTitle Start-->
                <!--tableTop Start-->
				<div class="tableTop">
					<h2>부서별 오픈 전 지식실적</h2>
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
							   <th scope="row" width="5%"><ikep4j:message pre="${prePrivate}" key='condition.div' /></th>
							   <td colspan="8">
							   		<select name="isknowHowBox" id="isknowHowBox">		
                                    	<option value=""><ikep4j:message key='message.collpack.kms.perform.private.list.all' /></option>
                                    	<option value="1" <c:if test="${'1' eq searchCondition.searchIsknowhow}">selected="selected"</c:if>><ikep4j:message key='message.collpack.kms.perform.private.list.normal' /></option>
                                    	<option value="0" <c:if test="${'0' eq searchCondition.searchIsknowhow}">selected="selected"</c:if>><ikep4j:message key='message.collpack.kms.perform.private.list.knowHow' /></option>                                	
                                    </select>
							   </td>
							</tr>
							<tr>
                               <th scope="row" width="5%"><ikep4j:message pre="${prePrivate}" key='condition.workPlaceName' /></th>
                               <td width="13%">
                               		<c:choose> 
									<c:when test="${searchCondition.isPerson eq 'Y' or empty teamList }">										
										<input type="text" class="inputbox" title="" name="" value="${user.workPlaceName} ${user.teamName}" size="20" disabled="disabled"/>
										<spring:bind path="searchCondition.teamCode">
											<input type="hidden" name="${status.expression}" value="${user.groupId}"/>
										</spring:bind>
									</c:when>
									<c:otherwise>
										<spring:bind path="searchCondition.teamCode">
		                                    <select title="teamCode" name="${status.expression}" id="${status.expression}">		
		                                    	<c:set var="teamValue" value="${searchCondition.teamCode}" />
		                                    	    
		                                    	<option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.teamCode' /></option>                                	
		                                        <c:forEach var="teamInfo" items="${teamList}">				                                        
		                                        	<option value="${teamInfo.teamCode}" <c:if test="${teamValue eq teamInfo.teamCode}">selected="selected"</c:if>>${teamInfo.teamName}</option>
		                                        </c:forEach>	                                        
		                                    </select>
										</spring:bind>	
									</c:otherwise>
									</c:choose>                            
                               </td>                               
                               <th scope="row" width="7%"><ikep4j:message pre="${prePrivate}" key='list.registerName' /></th>
                               <td width="10%">
                               	<c:set var="registerName" value="${user.userName}"/>
                      			<c:if test="${searchCondition.isPerson eq 'N'}">
                      				<c:set var="registerName" value="${searchCondition.registerName}"/>
                      			</c:if>
                      			<input type="text" class="inputbox" title="" name="registerName" id="registerName" value="${registerName}" size="10" <c:if test="${searchCondition.isPerson eq 'Y'}">disabled="disabled"</c:if> <c:if test="${searchCondition.isPerson eq 'N'}">onkeypress="javascript:if(event.keyCode == 13){return submit();}"</c:if>/>
                               </td>        
                               <th scope="row" width="5%"><ikep4j:message key='message.collpack.kms.perform.title' /></th>                       
                               <td width="12%">     
                                    <spring:bind path="searchCondition.isPerson">                    
                               		<input type="radio" name="isPerson" id="isPerson" value="Y" <c:if test="${searchCondition.isPerson eq 'Y'}">checked="checked"</c:if> onclick="isPersonClk();" />
									<label for="radio1"><ikep4j:message key='message.collpack.kms.perform.title.private' /></label>
									<input type="radio" name="isPerson" id="isPerson" value="N" <c:if test="${searchCondition.isPerson eq 'N'}">checked="checked"</c:if> onclick="isPersonClk();" />
									<label for="radio2"><ikep4j:message key='message.collpack.kms.perform.title.team' /></label>
									</spring:bind>
                               </td>
                               <th scope="row" width="5%"><ikep4j:message pre="${prePrivate}" key='list.searchMonth' /></th>
                               <td width="23%">
                               		<input class="datepicker" type="text" name="startDate" id="startDate" size="10" value="<c:out value='${fn:substring(searchCondition.startDate, 0, 10)}'/>" readonly/> ~
									<input class="datepicker" type="text" name="endDate" id="endDate" size="10" value="<c:out value='${fn:substring(searchCondition.endDate, 0, 10)}'/>" readonly/>
                               </td>		
                               <td class="textRight">
                               <a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
<%--                                <a href="#a" id="excelButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_excel.gif'/>" alt="" /></a> --%>
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
							<th scope="col" width="5%">
								<ikep4j:message pre='${prePrivate}' key='list.itemId' />
							</th>					
							<th scope="col" width="10%">
								<a onclick="sort('IS_KNOWHOW', '<c:if test="${searchCondition.sortColumn eq 'IS_KNOWHOW'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.gubun' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'IS_KNOWHOW' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>
							<th scope="col" width="10%">
								<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.registDate' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>		
							</th>
							<th scope="col" width="*">
								<ikep4j:message pre='${prePrivate}' key='list.title' />
							</th>
							<th scope="col" width="8%">
								<a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${prePrivate}' key='list.registerName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>							
							<th scope="col" width="14%">
								<ikep4j:message pre='${prePrivate}' key='list.teamName' />
							</th>
							<th scope="col" width="5%">
								<ikep4j:message pre='${prePrivate}' key='list.file' />
							</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="7" class="emptyRecord"><ikep4j:message key='ui.ikep4.common.searchCondition.emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="performance" items="${searchResult.entity}">
									<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine" onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
										<td>
											${performance.rowNum}
										</td>
										<td>
											<c:if test="${performance.isKnowHow eq 0}">
												<ikep4j:message pre="${prePrivate}" key='list.knowHow' />
											</c:if>
											<c:if test="${performance.isKnowHow eq 1}">
												<ikep4j:message pre="${prePrivate}" key='list.normal' />
											</c:if>				
											<c:if test="${performance.isKnowHow eq 3}">
												원문 게시판
											</c:if>					
										</td>
										<td>
											${performance.registDate}
										</td>
										<td>
										<c:choose>
											<c:when test="${performance.status eq 5}">
												<span class="deletedItem">
													<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${performance.itemId}&searchConditionString=${searchCondition.searchConditionString}&popupYn=false&pageGubun=listPrivate'/>">											
														<b>${performance.title}</b>
													</a>
												</span>
											</c:when>
											<c:otherwise>
												<a name="announceItem" href="<c:url value='/collpack/kms/board/boardItem/readBoardItemView.do?itemId=${performance.itemId}&searchConditionString=${searchCondition.searchConditionString}&popupYn=false&pageGubun=listPrivate'/>">											
													${performance.title}
												</a>
											</c:otherwise>
										</c:choose>	
										</td>
										<td>
											${performance.registerName}
										</td>										
										<td>
											${performance.teamName}
										</td>
										<td>
											<c:if test="${performance.attachFileCount != '0'}">
												<img src="<c:url value='/base/images/icon/ic_attach.gif'/>" />				
											</c:if>					
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

	