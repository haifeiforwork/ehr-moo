<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" /> 
<c:set var="preSearch"  	value="ui.approval.common.searchCondition" />
<c:set var="preMessage"  	value="ui.approval.collaboration.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preTitle"    	value="ui.approval.proposal.list.title" /> 
<c:set var="preSearch2"  	value="ui.approval.proposal.searchCondition" />

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
	
	var isAdmin = "${searchCondition.isAdmin}";
	var cachedTdObj;
	
	goView = function( proposalNo) {
		var moveFrm = $("#moveForm");
		moveFrm.find("input[name='viewMode']").val("modify");
		moveFrm.find("input[name='proposalNo']").val( proposalNo);
		moveFrm.submit();
	},
	
	setUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.empNo);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			cachedTdObj.find('.searchUserName').val(managerName);
			cachedTdObj.find('.searchUserId').val(userId);
		});
	};
	
	$(document).ready(function() {    
		
		iKEP.iFrameContentResize();
		sort = function(sortColumn, sortType) {
			$("#searchForm input[name=sortColumn]").val(sortColumn); 
			$("#searchForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchButton").click();
		}; 
		
		$("#searchButton").click(function() {
			
			$("#searchForm").attr({method:'POST'}).submit(); 
			return false; 
		});
		
		
		$("#searchForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchTemporaryItemButton").click(); 
			$("#searchButton").click(); 
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
		
		// [검색-직원선택] ================== Start
		$jq('.addrSearchBtn').click( function() {
			cachedTdObj = $(this).parent("td");
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			cachedTdObj.find('.searchUserName').trigger("keypress");
		});
		
		$('.addrBtn').click( function() {
			
			cachedTdObj = $(this).parent("td");
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('.searchUserName').keypress( function(event) {
			cachedTdObj = $(this).parent("td");
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('.btnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
		    $(this).parent("td").find("input").val("");
		});
		// [검색-직원선택]  ================== End
		
		//[검색 -달력] 
		$("input[name=searchStartReqDate]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=searchEndReqDate]").datepicker({dateFormat: 'yy.mm.dd',yearRange:'c-20:c+10'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		// [등록]
		$(".createBtn").click( function(){
			var moveFrm = $("#moveForm");
			moveFrm.find("input[name='viewMode']").val("create");
			moveFrm.submit();
			return false;
		});
		
	});
})(jQuery);
	
//-->
</script>
<!-- moverForm -->
<form id="moveForm" action="<c:url value='/approval/collaboration/proposal/editProposalView.do' />"  method="post">
	<input type="hidden" name="viewMode" value="" />
	<input type="hidden" name="proposalNo" value="" />
	<input type="hidden" name="searchConditionString" value="${searchConditionString}" />
</form>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchForm" method="post" action="<c:url value='/approval/collaboration/testreq/listTestRequestView.do' />">  
	<spring:bind path="searchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 		
	<spring:bind path="searchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind> 
<!--mainContents Start-->

	<h1 class="none"><ikep4j:message pre='${preTitle}' key='summary' /></h1> 
	
	<!--pageTitle Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2><ikep4j:message pre='${preTitle}' key='summary' /></h2>  
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
			
		</div>
		<div class="clear"></div>
	</div>
	<!--//tableTop End--> 
	
	<!-- //blockSearch Start -->
	<div class="blockSearch">
		<div class="corner_RoundBox03">
			<table summary="tableSearch">
				<tbody>
					<tr>
						<!-- 작성기간 -->
						<th scope="row" width="5%"><ikep4j:message pre='${preSearch2}' key='reqDate' /></th>
                        <td width="40%">
                        <spring:bind path="searchCondition.searchStartReqDate">
				           	<input id="${status.expression}" name="${status.expression}" class="inputbox w20 datepicker" type="text" value="${status.value}" maxlength="10"/>
				           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
				        </spring:bind>
				           	 - 
				        <spring:bind path="searchCondition.searchEndReqDate">
				           	<input id="${status.expression}" name="${status.expression}" class="inputbox w20 datepicker" type="text" value="${status.value}"  maxlength="10"/>
				           	<img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar"/>
				        </spring:bind>
                        </td>
                        
                        <!-- 작성자 -->
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='reqEmpNo' /></th>
	                    <td width="*" style="padding-top: 5px;">
	                    	<spring:bind path="searchCondition.searchReqEmpNo">
		                    	<input name="${status.expression}" class="searchUserId" type="hidden" value="${status.value}"  />
	                    	</spring:bind>
	                    	<spring:bind path="searchCondition.searchReqEmpNm">
		                    	<input name="${status.expression}" class="searchUserName inputbox" type="text" value="${status.value}" size="20" />
	                    	</spring:bind>
	                    	
							<a href="#a" class="button_ic addrSearchBtn" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a href="#a" class="button_ic addrBtn">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a href="#a" class="button_ic valign_bottom btnDeleteControl" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
	                    </td>
	                    <td class="textRight" rowspan="2">
	                    	<a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
	                    </td>		
					</tr>
					<tr>
						<!-- 제품명 -->
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='searchProductName' /></th>
	                    <td width="40%">
	                    	<spring:bind path="searchCondition.searchProductName">
		                    	<input name="${status.expression}" class="inputbox" type="text" size="47" maxlength="100" value="${status.value}"  />
	                    	</spring:bind>
	                    </td>
						<!-- 수신자 -->
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='tcsRcvEmpNo' /></th>
	                    <td width="*" style="padding-top: 5px;">
	                    	<spring:bind path="searchCondition.searchTcsRcvEmpNo">
		                    	<input name="${status.expression}" class="searchUserId" type="hidden" value="${status.value}"  />
	                    	</spring:bind>
	                    	<spring:bind path="searchCondition.searchTcsRcvEmpNo">
		                    	<input name="${status.expression}" class="searchUserName inputbox" type="text" value="${status.value}" size="20" />
	                    	</spring:bind>
	                    	
							<a href="#a" class="button_ic addrSearchBtn" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a href="#a" class="button_ic addrBtn">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a href="#a" class="button_ic valign_bottom btnDeleteControl" >
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
	                    </td>
					</tr>
				</tbody>
            </table>
		</div>
	</div>
	<!-- //blockSearch End -->
	
	<!-- blockButton Start-->
	<div class="blockButton">
		<ul>
			<li>
				<a class="button createBtn" href="#a">
					<span><ikep4j:message pre='${preButton}' key='new'/></span>
				</a>
			</li>
		</ul>
	</div>
	<!--//blockButton End-->
	
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div class="blockListTable" id="listDiv"> 
			<%@ include file="/WEB-INF/view/approval/collaboration/proposal/listProposalIncView.jsp"%>
			
			<!--Page Number Start-->
			<div class="pageNum">	
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" /> 
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" /> 
			</spring:bind>
			<!--//Page Number End-->
			</div>
		</div>
		
		<!-- blockButton Start-->
		<div class="blockButton">
			<ul>
				<li>
					<a class="button createBtn" href="#a">
						<span><ikep4j:message pre='${preButton}' key='new'/></span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>		

</div>