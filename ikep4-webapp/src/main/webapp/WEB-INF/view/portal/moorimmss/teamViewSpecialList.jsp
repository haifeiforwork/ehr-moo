<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


 
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" /> 
<script type="text/javascript" language="javascript">
<!-- 
(function($) {
	


	setUser = function(data) {
		
		var managerName = "";
		var jobTitleName = "";
		var teamName = "";
		var userId = "";
		
		$jq.each(data, function() {
			
			managerName = $jq.trim(this.userName);
			jobTitleName = $jq.trim(this.jobTitleName);
			teamName = $jq.trim(this.teamName);
			userId = $jq.trim(this.id);	
			
			if(jobTitleName.length > 0) {
				
				managerName += " " + jobTitleName;
			}
			
			if(teamName.length > 0) {
				
				managerName += " " + teamName;
			}
			
			$jq("#specialUserName").val(managerName);
			$jq("#userId").val(userId);
		});
	};
	
	setGroup = function(data) {
		var groupName = "";
		var groupId = "";
		
		$jq.each(data, function() {
			
			groupName = $jq.trim(this.name);
			groupId = $jq.trim(this.code);	
			
			$jq("#specialGroupName").val(groupName);
			$jq("#groupId").val(groupId);
		});
	};	

	goList  = function(data) {
		document.location.href = '<c:url value="/portal/moorimmss/teamViewSpecialList.do" />';
	};

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
 
		///////////////////////////////////////////////////////////////유저
		$jq('#addrSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#specialUserName').trigger("keypress");
		});
		
		$('#addrBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#specialUserName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('#btnDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#specialUserName').val('');
			$jq('#userId').val('');
		});
		
		
		
		
		///////////////////////////////////////////////////////////////그룹
		$jq('#addrGroupSearchBtn').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#specialGroupName').trigger("keypress");
		});
		
		$('#addrGroupBtn').click( function() {
			
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setGroup, [], {selectType:"group", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#specialGroupName').keypress( function(event) {
			
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchGroup(event, "N", setGroup);			
		});
		
		$jq('#btnGroupDeleteControl').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#specialGroupName').val('');
			$jq('#groupId').val('');
		});

		$jq("#btn_edit").click(function() {  
			
			if(confirm("작성한 팀뷰어 예외 사항을 추가 저장하시겠습니까?")) {
				$("#mgmtForm").ajaxLoadStart();
				$jq.ajax({     
					url : '<c:url value="/portal/moorimmss/teamViewSpecialAdd.do" />',     
					data :  {
						userId : $jq('#userId').val(),
						groupId : $jq('#groupId').val()
					},     
					type : "post",     
					success : function(result) {      
						alert("저장 성공했습니다.");
						goList();
					},
					error : function(event, request, settings){
						
						alert("저장 실패했습니다. 관리자에게 문의하세요.");
					}
				});
			}  
		}); 
		$jq("#btn_refresh").click(function() {  
			goList();
		});
		
		$("#btn_delete").click(function() {
			var deleteItemIds = new Array();
			$("#mgmtForm input[name=chkItemId]:checked").each(function(index) { 
				deleteItemIds.push($(this).val()); 
			});	
			
			if(deleteItemIds.length > 0) {
				if(confirm("선택한 팀뷰어 예외를 삭제하시겠습니까?")) {
					$("#mgmtForm").ajaxLoadStart();
					$.ajax({
						url : '<c:url value="/portal/moorimmss/teamViewSpecialDelete.do" />',     
						data : {
							itemIds : deleteItemIds.toString()
						},     
						type : "post",     
						success : function(result) { 
							alert("삭제 성공했습니다.");
							goList();
						},
						error : function(event, request, settings){
							 alert("삭제 실패했습니다. 관리자에게 문의하세요.");
						}
					});
				}  
			} else {
				alert("삭제할 건을 체크하십시요.");
			}
		});  
	});
	
})(jQuery);  


//-->	
</script>
				<h1 class="none">MSS 팀뷰어 예외자 관리</h1>
				<!--subTitle_2 Start-->
				<div class="subTitle_2 noline">
					<h3>MSS 팀뷰어 예외자 관리</h3>
				</div>
				<!--//subTitle_2 End-->
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				<form id="mgmtForm" >
					<table summary="<ikep4j:message pre="${preMandator}" key="trusteeMgmt" />">
						<caption></caption>
						<thead>
							<tr>
								<th scope="col" width="10%"></th>
								<th scope="col" width="50%">사용자 아이디</th>
								<th scope="col" width="*">팀뷰어에서 봐야 할 부서</th>
							</tr>
						</thead>
						<tbody>
							<c:if test="${!empty teamViewSpecialList}" >
							<c:forEach var="teamViewSpecial" items="${teamViewSpecialList}" varStatus="status">
							<tr>
								<td><input type="checkbox" id="chkItemId" name="chkItemId" value="${teamViewSpecial.itemId}"/></td>
								<td>${teamViewSpecial.userId}</td>
								<td>${teamViewSpecial.groupId}</td>
							</tr>
							</c:forEach>
							</c:if>
							<c:if test="${empty teamViewSpecialList}" >
							<tr>
								<td colspan="3">예외 사용자가 없습니다.</td>
							</tr>
							</c:if>
							<tr>
							<td></td>
							<td>
							<input id="userId" name="userId" type="hidden" value=""  />
							<input type="text" class="inputbox" id="specialUserName" name="specialUserName" value="" size="30" />
							<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />검색</span>
							</a>
							<a id="addrBtn" href="#a" class="button_ic">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle">주소록</span>
							</a>
							<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />삭제</span>
							</a>
							</td>
							<td>
							<input id="groupId" name="groupId" type="hidden" value=""  />
							<input type="text" class="inputbox" id="specialGroupName" name="specialGroupName" value="" size="30" />
							<a name="addrGroupSearchBtn" id="addrGroupSearchBtn" class="button_ic" href="#a">
								<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />검색</span>
							</a>
							<a id="addrGroupBtn" href="#a" class="button_ic">
								<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle">주소록</span>
							</a>
							<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl">
								<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />삭제</span>
							</a>
							</td>
							</tr>																																	
						</tbody>
					</table>	
				</form>				
				</div>
				<!--//blockListTable End-->	
							
				<!--blockButton Start-->
				<div class="blockButton"> 
					<ul>

							<li id="btn_edit"><a class="button" href="#a"><span>추가</span></a></li>
							
							<li id="btn_delete"><a class="button" href="#a"><span>삭제</span></a></li>
							
							<li id="btn_refresh"><a class="button" href="#a"><span>새로고침</span></a></li>		
							
					</ul>
				</div>
				<!--//blockButton End-->	
													
			<!--//mainContents End-->

			