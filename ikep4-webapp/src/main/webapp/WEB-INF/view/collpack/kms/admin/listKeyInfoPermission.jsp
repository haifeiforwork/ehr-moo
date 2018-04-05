<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  		value="message.collpack.kms.admin.winner.header" /> 
<c:set var="preList"    		value="message.collpack.kms.admin.winner.list" />
<c:set var="preCondition"    		value="message.collpack.kms.admin.winner.condition" />
<c:set var="preAdminSearch"  	value="message.collpack.kms.admin.permission.user.searchCondition" />
<c:set var="preSearch"  		value="ui.ikep4.common.searchCondition" />

 <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/kms/kms.css"/>" />
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
		
		viewPopUp = function(userId) { 
			var pageURL = "<c:url value='collpack/kms/admin/modifySpecialUserForm.do' />?userId=" + userId;
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
			var categoryIds = new Array();
			var str = new Array();
			
			
			$("#searchUserPermForm input[name=checkTarget]:checked").each(function(index) { 
				str = $(this).val();
				itemIds.push(str); 
			});	
			
			if (itemIds.length > 0 ){	
				if(confirm("<ikep4j:message pre="message.collpack.kms.admin.winner.message" key="delete" />")) {
					$("#searchUserPermForm").ajaxLoadStart(); 
					
					$.post("<c:url value='/collpack/kms/admin/deleteKeyInfoPermission.do'/>", {"userIds" : itemIds.toString()}) 
					.success(function(data) {  
						$("#searchUserPermForm").submit(); 
					})				
				}
			}
		}); 
		
		$("#searchUserPermButton").click(function() { 
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/listHandsomePeople.do'/>");
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
		
		
		
		
		$("#searchUserPermForm select[name=pagePerRecord]").change(function() {
			$jq("#searchUserPermForm").attr("action","<c:url value='/collpack/kms/admin/winner/listHandsomePeople.do'/>");
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
<form id="searchUserPermForm" method="post" action="<c:url value='/collpack/kms/admin/listSpecialUser.do' />">
<!--mainContents Start-->	 
<!--pageTitle Start-->
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
							<th scope="col" width="7%">
								<ikep4j:message pre='${preList}' key='name' />			
							</th>	
							<th scope="col" width="8%">
								<ikep4j:message pre='${preList}' key='jobTitle' />			
							</th>	
							<th scope="col" width="12%">
								<ikep4j:message pre='${preList}' key='teamName' />
							</th>
							<th scope="col" width="12%">
								조회여부				
							</th>
							<th scope="col" width="12%">
								알림여부				
							</th>
						</tr>
						</thead>
						<tbody>
							<c:choose>
							    <c:when test="${empty keyInfoPermissionUser}">
									<tr>
										<td colspan="6" class="emptyRecord"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td> 
									</tr>				        
							    </c:when>
							    <c:otherwise>
									<c:forEach var="keyInfo" items="${keyInfoPermissionUser}">
									<tr  name="boardItemLine" >
										<td>
											<input name="checkTarget" class="checkbox" title="checkbox" type="checkbox" value="${keyInfo.userId}" />
										</td>
										<td>
											<a href="<c:url value='/collpack/kms/admin/modifyKeyInfoPermissionForm.do'/>?userId=${keyInfo.userId}" >${keyInfo.userName}</a>
										</td>
										<td>
											<a href="<c:url value='/collpack/kms/admin/modifyKeyInfoPermissionForm.do'/>?userId=${keyInfo.userId}" >${keyInfo.jobDutyName}</a>
										</td>
										<td>
											<a href="<c:url value='/collpack/kms/admin/modifyKeyInfoPermissionForm.do'/>?userId=${keyInfo.userId}" >${keyInfo.teamName}</a>
										</td>
										<td>
											${keyInfo.viewYn}
										</td>
										<td>
											${keyInfo.alarmYn}
										</td>
									</tr>
									</c:forEach>				      
							    </c:otherwise> 
							</c:choose>  
						</tbody>				
					</div>
					<!--//blockListTable End-->
					</table>
				</div>
					
			</div>
		</div>	
		
		<!--blockButton Start-->
         <div class="blockButton"> 
             <ul>
                 <li><a class="button" id="applyWinnerButton" href="<c:url value='/collpack/kms/admin/registKeyInfoPermissionForm.do'/>"><span><ikep4j:message pre="message.collpack.kms.admin.winner.button" key="apply"/></span></a></li>
                 <li><a class="button" id="deleteWinnerButton" href="#a"><span><ikep4j:message pre="message.collpack.kms.admin.permission.button" key="delete"/></span></a></li>
             </ul>
         </div>
         <!--//blockButton End-->					
	</div>			
				<!--//splitterBox End-->
</form>
</div>

	