<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.winner.header" /> 
<c:set var="preList"    		value="message.collpack.kms.admin.winner.list" />
<c:set var="preCondition"    		value="message.collpack.kms.admin.winner.condition" />
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
		
		viewPopUp = function(itemSeq) { 
			var pageURL = "<c:url value='/collpack/kms/admin/winner/getWinnerPopup.do' />?itemSeq=" + itemSeq;
			iKEP.popupOpen( pageURL , {width:500, height:400, callback:function(result) {} });
		};
		
		sort = function(sortColumn, sortType) { 
			$("#searchUserPermForm input[name=actionType]").val("sort");
			$("#searchUserPermForm input[name=sortColumn]").val(sortColumn); 
			$("#searchUserPermForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchUserPermButton").click();
		}; 
				
		$("#checkboxAll").click(function() { 
			$("#searchUserPermForm input[name=checkTarget]").attr("checked", $(this).is(":checked"));  
		});
		
		$("#deleteWinnerButton").click(function() {  
			var itemIds = new Array();
			
			$("#searchUserPermForm input[name=checkTarget]:checked").each(function(index) { 
				itemIds.push($(this).val()); 
			});	
			
			if (itemIds.length > 0 ){	
				if(confirm("<ikep4j:message pre="message.collpack.kms.admin.winner.message" key="delete" />")) {
					$("#searchUserPermForm").ajaxLoadStart(); 
					
					$.post("<c:url value='/collpack/kms/admin/winner/deleteWinner.do'/>", {"itemSeqs" : itemIds.toString()}) 
					.success(function(data) {  
						$("#searchUserPermButton").click();
					})				
				}
			}
		}); 
		
		$("#searchUserPermButton").click(function() { 
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/listWinners.do'/>");
			$("#searchUserPermForm input[name=actionType]").val("search");			
			$("#searchUserPermForm").submit();
			
			return false; 
		});
		
		
		$("#displayButton").click(function() { 
			
			$("#searchUserPermForm").ajaxLoadStart();
			
			$.post("<c:url value='/collpack/kms/admin/winner/displayWinner.do'/>", {"winGb" : $("#searchUserPermForm input:radio[name=winGb]:checked").val()}) 
			.success(function(data) {  
				alert("<ikep4j:message pre="message.collpack.kms.admin.permission.user.popup.myCnt" key="modify" />");
				
				$("#searchUserPermButton").click();
			})		
			 
		});
		
		
		$("#excelWinnerButton").click(function() {
			if(${searchResult.emptyRecord}){
				alert("<ikep4j:message key='message.collpack.kms.perform.excel.nodata'/>");
				return false;
			}
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/downloadExcelWinners.do'/>");
			$jq("#searchUserPermForm").submit(); 
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/listWinners.do'/>");
			return false; 
		});
				
		
		
		$("#searchUserPermForm select[name=pagePerRecord]").change(function() {
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/listWinners.do'/>");
			$("#searchUserPermForm input[name=actionType]").val("pagePerRecord"); 
			$("#searchUserPermForm input[name=searchWord]").val("");
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
		
				
	});
	
	
})(jQuery);
//-->
</script>
<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchUserPermForm" method="post" action="<c:url value='/collpack/kms/admin/winner/listWinners.do' />">
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
					<div class="tableSearch">
                    	<spring:bind path="searchCondition.winYear">						
	                       	<select title="${status.expression}" name="${status.expression}">
	                       	<c:forEach var="year" begin="2012" end="${nowYear}" step="1">
	                        	<option value="${year}" <c:if test="${year eq status.value}">selected="selected"</c:if>>${year}</option>
	                           </c:forEach>
	                       	</select>
                     	</spring:bind>
						<spring:bind path="searchCondition.searchColumn">						
	                        <select title="${status.expression}" name="${status.expression}">
	                        	<option value="userName" <c:if test="${'userName' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preAdminSearch}" key="gubun.name" /></option>
	                            <option value="userId" <c:if test="${'userId' eq status.value}">selected="selected"</c:if>><ikep4j:message pre="${preAdminSearch}" key="gubun.id" /></option>
	                        </select>															
	                        <spring:bind path="searchCondition.searchWord">  					
								<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='' size="20"/>
							</spring:bind>
                        </spring:bind>
						<a href="#a"  id="searchUserPermButton" name="searchUserPermButton" class="button_s"><span>Search</span></a>
						<a href="#a" id="excelWinnerButton" name="excelWinnerButton" class="button_s"><span>Excel</span></a>						
					</div>	
					<div class="clear"></div>
				</div>
				<!--//tableTop End-->			
				     
                            
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
							<th scope="col" width="3%">
								<input id="checkboxAll" class="checkbox" title="checkbox" type="checkbox" value="" />
							</th>	
							<th scope="col" width="3%">
								<ikep4j:message pre='${preList}' key='itemSeqId' />
							</th>						
							<th scope="col" width="5%">
								<ikep4j:message pre='${preList}' key='winYear' />	
							</th>
							<th scope="col" width="5%">
								<a onclick="sort('WIN_GB', '<c:if test="${searchCondition.sortColumn eq 'WIN_GB'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='winGb' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'WIN_GB' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'WIN_GB' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>	
							</th>
							<th scope="col" width="7%">
								<a onclick="sort('IS_MONTH', '<c:if test="${searchCondition.sortColumn eq 'IS_MONTH'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='isMonth' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'IS_MONTH' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'IS_MONTH' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>			
							</th>
							<th scope="col" width="4%">
								<a onclick="sort('SORT', '<c:if test="${searchCondition.sortColumn eq 'SORT'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='sort' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'SORT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'SORT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>			
							</th>
							<th scope="col" width="7%">
								<ikep4j:message pre='${preList}' key='name' />			
							</th>	
							<th scope="col" width="8%">
								<ikep4j:message pre='${preList}' key='jobTitle' />			
							</th>	
							<th scope="col" width="9%">
								<ikep4j:message pre='${preList}' key='id' />			
							</th>							
							<th scope="col" width="6%">
								<ikep4j:message pre='${preList}' key='workPlaceName' />				
							</th>
							<th scope="col" width="12%">
								<a onclick="sort('TEAM_NAME', '<c:if test="${searchCondition.sortColumn eq 'TEAM_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='teamName' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'TEAM_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>					
							</th>
							<th scope="col" width="5%">
								<a onclick="sort('REG_CNT', '<c:if test="${searchCondition.sortColumn eq 'REG_CNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='regCnt' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'REG_CNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'REG_CNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>				
							</th>	
							<th scope="col" width="7%">
								<a onclick="sort('MARK', '<c:if test="${searchCondition.sortColumn eq 'MARK'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='mark' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'MARK' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'MARK' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>				
							</th>								
							<th scope="col" width="6%">
								<a onclick="sort('CONVERSION_MARK', '<c:if test="${searchCondition.sortColumn eq 'CONVERSION_MARK'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='conversionMark' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'CONVERSION_MARK' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'CONVERSION_MARK' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>				
							</th>
							<th scope="col" width="11%">
								<a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
									<ikep4j:message pre='${preList}' key='registDate' />
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>				
							</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="15" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="adminWinner" items="${searchResult.entity}">
									<tr class="<c:if test="${itemDisplay eq 1}"></c:if>" name="boardItemLine" onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
										<td>
											<input name="checkTarget" class="checkbox" title="checkbox" type="checkbox" value="${adminWinner.itemSeq}" />
										</td>
										<td>
											${adminWinner.rowNum}											
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.winYear}</a>										
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.winGb}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.isMonthValue}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.sort}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.userName}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.jobTitleName}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.winnerId}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.workPlaceName}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.teamName}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.regCnt}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.mark}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.conversionMark}</a>
										</td>
										<td>
											<a onclick="javascript:viewPopUp('${adminWinner.itemSeq}');" href="#a">${adminWinner.registDate}</a>
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
		
		<!--blockButton Start-->
         <div class="blockButton"> 
             <ul>
                 <li><a class="button" id="applyWinnerButton" href="<c:url value='/collpack/kms/admin/winner/registerWinners.do'/>"><span><ikep4j:message pre="message.collpack.kms.admin.winner.button" key="apply"/></span></a></li>
                 <li><a class="button" id="deleteWinnerButton" href="#a"><span><ikep4j:message pre="message.collpack.kms.admin.permission.button" key="delete"/></span></a></li>
             </ul>
         </div>
         <!--//blockButton End-->					
	</div>			
				<!--//splitterBox End-->
</form>
</div>

	