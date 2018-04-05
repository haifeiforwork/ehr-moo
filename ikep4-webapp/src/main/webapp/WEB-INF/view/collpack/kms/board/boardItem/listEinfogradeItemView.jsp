<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
  
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.kms.board.boardItem.listBoardView.list" />
<c:set var="preButton"  value="message.collpack.kms.board.boardItem.listBoardView.button" /> 
<c:set var="preSearch"  value="message.collpack.kms.board.boardItem.listBoardView.searchCondition" />
<c:set var="preMessage" value="message.collpack.kms.board.boardItem.message.script" />

<c:if test="${!empty searchResult.entity}">
<c:forEach var="info" items="${searchResult.entity}">

</c:forEach>
</c:if>
 
<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value='/base/js/jquery/splitter.js'/>"></script>
<script type="text/javascript">

<!--   
(function($){	
	$(document).ready(function() {    
		iKEP.iFrameContentResize();
		sort = function(sortColumn, sortType) {
			$("#searchEinfogradeItemForm input[name=sortColumn]").val(sortColumn); 
			$("#searchEinfogradeItemForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchEinfogradeItemButton").click();
		}; 
		
		$("input.datepicker").datepicker({
			onSelect: function(date, event) {
				var arrDate = date.split(".");
			}
		});
		
		$("#searchEinfogradeItemForm select[name=workPlaceName]").change(function() { 
			var workPlaceName = $("#searchEinfogradeItemForm select[name=workPlaceName]").val();
			if(workPlaceName == ""){
				$("#teamCode").empty();
				var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
				$("#teamCode").append(teamCode);
			}else{
				$("#searchEinfogradeItemForm").submit(); 
				return false;
			}
		}); 
		
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) { 
			$("#searchEinfogradeItemForm input[name=viewMode]").val(changeViewMode);
			$("#searchEinfogradeItemButton").click(); 
			return false; 
		}; 
		
		$("#searchEinfogradeItemButton").click(function() {   
			$("#searchEinfogradeItemForm").attr({method:'POST'}).submit(); 
			return false; 
		});
		
		
		$("#checkboxAllEinfogradeItem").click(function() { 
			$("#searchEinfogradeItemForm input[name=boardItemIds]").attr("checked", $(this).is(":checked"));  
		});  

		
		$("#searchEinfogradeItemForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchEinfogradeItemButton").click(); 
			$("#searchEinfogradeItemButton").click(); 
		}); 
		
		var objSplit = null;
		
		$("a[name=boardItem]").click(function() {
			$("*[name=boardItemLine]").removeClass("bgSelected");
			
			$(this).parents("*[name=boardItemLine]").addClass("bgSelected");
			
			 if(objSplit == null  ) {
				return true;
			 } else if(objSplit.getType() == "v") { 
				 
				$("#BottomPane").remove();
				$("#RightPane").load($(this).attr("href") + "&layoutType=layoutVertical");
				return false;
				
			} else if(objSplit.getType() == "h") { 
				$("#RightPane").remove();
				$("#BottomPane").load($(this).attr("href") + "&layoutType=layoutHorizental");
				return false;
			} else {  
				return true;
			}
			 
			 return false;
			
		}); 
		
		$("#btnLayoutNormal").click(function() {  
			if(objSplit != null) {
				objSplit.destroy();
				objSplit = null;
				$("input[name=layoutType]").val("layoutNormal");
			}
		});
		
		$("#btnLayoutVertical").click(function(event) { 
			if(objSplit == null) {
				var options = {type : "v", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutVertical");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		
		$("#btnLayoutHorizental").click(function(event) {   
			if(objSplit == null) {
				var options = {type : "h", callback:function($divContent) {}}; 
				objSplit = new iKEP.Splitter("#splitterBox", options);
				$("input[name=layoutType]").val("layoutHorizental");
			} else {
				objSplit.destroy();
				objSplit = null;
				$(this).trigger("click"); 
			} 
		}); 
		if($("input[name=layoutType]").val() ==  "layoutVertical") {
			$("#btnLayoutVertical").click();
		} else if($("input[name=layoutType]").val() ==  "layoutHorizental") {
			$("#btnLayoutHorizental").click();
		} else {
			
		} 
	}); 
})(jQuery);
//-->
</script>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchEinfogradeItemForm" method="post" action="<c:url value='/collpack/kms/board/boardItem/listEinfogradeItemView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
	  
	<spring:bind path="searchCondition.layoutType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	
	<input name="gubun" type="hidden" value="${gubun}" title="" />
<!--mainContents Start-->

	<h1 class="none">미공유지식</h1> 
<!--pageTitle Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>
			<c:if test="${gubun eq '0'}">미공유지식</c:if>
			<c:if test="${gubun eq '1'}">미공유지식</c:if>
			<c:if test="${gubun eq '2'}">보안지식</c:if>
		</h2>  
		<div class="listInfo">  
			<spring:bind path="searchCondition.pagePerRecord">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<c:forEach var="code" items="${boardCode.pageNumList}">
					<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
				</c:forEach> 
				</select> 
			</spring:bind>
			<div class="totalNum"><ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span></div>
		</div>	  
						
		
		<div class="tableSearch"> 
			<spring:bind path="searchCondition.searchColumn">  
				<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='title'/></option>
				</select>		
			</spring:bind>		
			<spring:bind path="searchCondition.searchWord">  					
				<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='' size="20"/>
			</spring:bind>
			<a id="searchEinfogradeItemButton" name="searchEinfogradeItemButton" href="#a"class="ic_search"><span><ikep4j:message pre='${preButton}' key='search'/></span></a>
		</div>			
		<div class="clear"></div>
	</div>
	<div class="blockSearch">
                	<div class="corner_RoundBox03">
						<table summary="tableSearch">
						<tbody>
							<tr>
                               <th scope="row" width="5%">소속</th>
                               <td width="*">
                               		<spring:bind path="searchCondition.workPlaceName">                             			
	                                   	<select title="${status.expression}" name="${status.expression}">
	                                        <option value=""><ikep4j:message key='message.collpack.kms.admin.permission.team.leader.info.add.company' /></option>	                                        
	                                        <c:forEach var="workPlace" items="${workPlaceList}" begin="1">
	                                        	<option value="${workPlace}" <c:if test="${searchCondition.workPlaceName eq workPlace}">selected="selected"</c:if>>${workPlace}</option>
	                                        </c:forEach>
	                                        <option value="ectGrp" <c:if test="${searchCondition.workPlaceName eq 'ectGrp'}">selected="selected"</c:if>>관계사</option>	
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
                               </td>                               
                               <th scope="row" width="7%">등록자</th>
                               <td width="10%">
                          			<spring:bind path="searchCondition.registerName"> 
                          				<input type="text" class="inputbox" title="" name="${status.expression}" value="${status.value}" size="10" onkeypress="javascript:if(event.keyCode == 13){return submit();}" />
                          			</spring:bind>
                               </td>
                               <th scope="row" width="7%">기간</th>
                               <td width="23%">
                               		<input class="datepicker" type="text" name="startDate" id="startDate" size="10" value="<c:out value='${fn:substring(searchCondition.startDate, 0, 10)}'/>" readonly/> ~
									<input class="datepicker" type="text" name="endDate" id="endDate" size="10" value="<c:out value='${fn:substring(searchCondition.endDate, 0, 10)}'/>" readonly/>
                               </td>		
                               <td class="textRight">
                               </td>				
                           </tr>														
		                 </tbody>
		             	</table>
		             	 
		             </div>
				</div>    
	<!--//tableTop End--> 
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div class="blockListTable" id="listDiv"> 
			<%@ include file="/WEB-INF/view/collpack/kms/board/boardItem/listTypeEinfogradeItemView.jsp"%>
			
			<!--Page Number Start-->
			<div class="pageNum">	
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchEinfogradeItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
			</spring:bind>
			<!--//Page Number End-->
			</div>
		</div>
	</div>
</form>		

</div>
