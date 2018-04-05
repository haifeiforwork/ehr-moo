<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preButton"  	value="ui.approval.common.button" /> 
<c:set var="preSearch"  	value="ui.approval.common.searchCondition" />
<c:set var="preTitle"    	value="ui.approval.userauthmgnt.list.title" /> 
<c:set var="preMessage"  	value="ui.approval.collaboration.message" />
<c:set var="preDefault"     value="ui.approval.collaboration.default" />
<c:set var="preSearch2"  	value="ui.approval.userauthmgnt.searchCondition" />
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
			
			$jq("#searchUserName").val(managerName);
			$jq("#searchUserId").val(userId);
		});
	};
	setGroup = function(data) {
		var groupName = "";
		var groupId = "";
		
		$jq.each(data, function() {
			
			groupName = $jq.trim(this.name);
			groupId = $jq.trim(this.code);	
			
			$jq("#searchGroupName").val(groupName);
			$jq("#searchGroupId").val(groupId);
		});
	};
	
	// 수정
	openModify = function ( workGbnCd, empNo, deptId) {
		
		iKEP.showDialog({
			title: "<ikep4j:message pre="${preTitle}" key="summary" />",
			url: "<c:url value='/approval/collaboration/userauthmgnt/editUserAuthMgntView.do?viewMode=modify' />" + "&searchWorkGbnCd="+workGbnCd+"&searchEmpNo="+empNo+"&searchDeptId="+deptId,
			modal: true,
			width: 700,
			height: 240,
			callback : saveCallbackFnc
		});
	};
	
	saveCallbackFnc = function () {
		
		$("#searchButton").trigger("click");
	};
	$(document).ready(function() {    
		
		iKEP.iFrameContentResize();
		sort = function(sortColumn, sortType) {
			$("#searchForm input[name=sortColumn]").val(sortColumn); 
			$("#searchForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchButton").click();
		}; 
		//뷰모드 체인지 함수
		changeViewMode = function(changeViewMode) { 
			$("#searchForm input[name=viewMode]").val(changeViewMode);
			$("#searchButton").click(); 
			return false; 
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
		$jq('#addrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchUserName').trigger("keypress");
		});
		
		$('#addrBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#searchUserName').keypress( function(event) {
			//alert(1);
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('#btnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchUserName').val('');
			$jq('#searchUserId').val('');
		});
		// [검색-직원선택]  ================== End
		
		// [검색-부서선택]  ================== Start
		$jq('#addrGroupSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchGroupName').trigger("keypress");
		});
		
		$('#addrGroupBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setGroup, [], {selectType:"group", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#searchGroupName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchGroup(event, "N", setGroup);
		});
		
		$jq('#btnGroupDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#searchGroupName').val('');
			$jq('#searchGroupId').val('');
		});
		// [검색-부서선택]  ================== End
		
		// checkbox All
		$("#checkboxAll").click(function() { 
			$("#searchForm input[name=boardItemIds]").attr("checked", $(this).is(":checked"));  
		});
		
		// 삭제 
		$(".deleteUserAuthMgntButton").click(function() {  
			var delCheckedItemObj = $("input[name=boardItemIds]:checked");
			var delCheckedLen = delCheckedItemObj.length;
			
			if( delCheckedLen == 0) {
				alert("<ikep4j:message pre="${preMessage}" key="emptySelected" />");
				return false;
			}
			
			if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
				
				var deleteItemList = [];
				for( var i = 0; i < delCheckedLen; i++ ) {
					
					var targetObj = $(delCheckedItemObj[i]).parent("td");
					var workGbnCd = targetObj.find("input[name='workGbnCd']").val();
					var deptId = targetObj.find("input[name='deptId']").val();
					var empNo = targetObj.find("input[name='empNo']").val();
					deleteItemList.push({"workGbnCd" : workGbnCd, "deptId" : deptId, "empNo" : empNo});
				}
				var params =  JSON.stringify( deleteItemList);

				$jq.ajax({
					url : iKEP.getContextRoot() + "/approval/collaboration/userauthmgnt/ajaxDelUserAuthMgnt.do",
					type : "post",
					data : {"deleteList": params},
					success : function(data, textStatus, jqXHR) {
						
						alert( "<ikep4j:message pre='${preMessage}' key='deleteSuccess'/>");
						saveCallbackFnc();
					},
					error : function(jqXHR, textStatus, errorThrown) {
						alert( "<ikep4j:message pre='${preMessage}' key='deleteFail'/>");
					}
				});
			}
			
		});
		
		// 등록
		$(".createUserAuthMgntButton").click(function() {
			
			iKEP.showDialog({
				title: "<ikep4j:message pre="${preTitle}" key="summary" />",
				url: "<c:url value='/approval/collaboration/userauthmgnt/editUserAuthMgntView.do?viewMode=create' />",
				modal: true,
				width: 700,
				height: 240,
				callback : saveCallbackFnc
			});
		});
		
		if( isAdmin =='true') {
			$(".blockButton").show();
		}
	});
})(jQuery);
	
//-->
</script>

<div id="tagResult">
<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="searchForm" method="get" action="<c:url value='/approval/collaboration/userauthmgnt/listUserAuthMgntView.do' />">  
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
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='workGbnCd' /></th>
	                    <td width="40%;">
							<spring:bind path="searchCondition.searchWorkGbnCd">
								<select class="selectbox" name="${status.expression}" >
									<option value=""><ikep4j:message pre='${preDefault}' key='option.all'/></option>
									<c:forEach var="code" items="${ workGnbCdList}">
										<c:choose>
											<c:when test="${searchCondition.viewMode eq 'modify'}">
												<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${isEnableSystemAdm eq true}">
														<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
													</c:when>
													<c:otherwise>
														<c:if test="${code.charCol1 eq user.groupId || code.charCol2 eq user.groupId}">
															<option value="${code.comCd}" <c:if test="${code.comCd eq status.value}">selected="selected"</c:if>>${code.comNm}</option>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select> 
							</spring:bind>
	                    </td>		
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='userName' /></th>
	                    <td width="*">
	                    	<spring:bind path="searchCondition.searchUserId">
		                    	<input id="${status.expression}" name="${status.expression}" class="inputbox" type="hidden" value="${status.value}"  />
	                    	</spring:bind>
	                    	<spring:bind path="searchCondition.searchUserName">
		                    	<input id="${status.expression}" name="${status.expression}" class="inputbox" type="text" value="${status.value}" size="20" />
	                    	</spring:bind>
	                    	
							<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
							</a>
							<a id="addrBtn" href="#a" class="button_ic">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
							</a>
							<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" /><ikep4j:message pre='${preButton}' key='delete' /></span>
							</a>
	                    </td>
	                    <td class="textRight" rowspan="2">
	                    	<a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>" alt="" /></a>
	                    </td>				
					</tr>
					<tr>
	                    <th scope="row" width="5%"><ikep4j:message pre="${preSearch2}" key='deptName' /></th>
						<td width="*;" colspan="3" style="padding-top: 5px;">
							<spring:bind path="searchCondition.searchGroupId">
		                    	<input id="${status.expression}" class="inputbox" name="${status.expression}" type="hidden" value="${status.value}"  />
							</spring:bind>
							<spring:bind path="searchCondition.searchGroupName">
		                    	<input id="${status.expression}" class="inputbox" name="${status.expression}" type="text" value="${status.value}" size="20" />
							</spring:bind>
								<a name="addrGroupSearchBtn" id="addrGroupSearchBtn" class="button_ic" href="#a">
									<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search' /></span>
								</a>
								<a id="addrGroupBtn" href="#a" class="button_ic">
									<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preButton}' key='address' /></span>
								</a>
								<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl">
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
	<div class="blockButton none">
		<ul>
			<li>
				<a class="button createUserAuthMgntButton" href="#a">
					<span><ikep4j:message pre='${preButton}' key='new'/></span>
				</a>
			</li>
			<li style="display:none;">
				<a class="button deleteUserAuthMgntButton" href="#a">
					<span><ikep4j:message pre='${preButton}' key='delete'/></span>
				</a>
			</li>
		</ul>
	</div>
	<!--//blockButton End-->
	<!--Layout Start-->
	<div id="container">
		<!--List Layout Start-->
		<div class="blockListTable" id="listDiv"> 
			<%@ include file="/WEB-INF/view/approval/collaboration/userauthmgnt/listUserAuthMgntIncView.jsp"%>
			
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
		<div class="blockButton none">
			<ul>
				<li>
					<a class="button createUserAuthMgntButton" href="#a">
						<span><ikep4j:message pre='${preButton}' key='new'/></span>
					</a>
				</li>
				<li style="display:none;">
					<a class="button deleteUserAuthMgntButton" href="#a">
						<span><ikep4j:message pre='${preButton}' key='delete'/></span>
					</a>
				</li>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>		

</div>
