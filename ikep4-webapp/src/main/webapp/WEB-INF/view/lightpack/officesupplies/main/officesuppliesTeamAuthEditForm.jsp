<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;
	
	var realtyCnt = "${officesuppliesCnt}";
	
	var flg = "1";

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officesupplies/officesuppliesUseRequestMyList.do' />");
		$jq("#searchForm")[0].submit();
	}

	
	// 소팅을 위한 함수
	function sort(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
	$jq(document).ready(function() {
		//left menu setting
		//$jq("#callManageOfLeft").click();
		
		$jq("#newFormButton").click(function() {
			
		});
		
		$jq("#deleteButton").click(function() {
			
			deleteForm();
		});
		
		$jq("#searchBoardItemButton").click(function() {  
			
			getList();
		});
		
		// 백스페이스 방지(input, select 제외)
		$jq(document).keydown(function(e) {
			
			var element = e.target.nodeName.toLowerCase();
			
			if (element != 'input') {
				
			    if (e.keyCode === 8) {
			    	
			        return false;
			    }
			}
		});
		
		
		$jq("a.btn_save").click(function(){
			var tempStatus = $jq("input[name=statusCheck]:checked").attr("value");
			$jq('input[name=status]').val(tempStatus);
			$jq("#requestForm").trigger("submit"); 
			return false;
		});
		
		$jq("a.btn_cancel").click(function() {
			$jq("#listForm").submit();
		});
		
		$jq('#addrSearchBtn1').click( function() {
			flg="1";
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#teamManagerName').trigger("keypress");
		});
		
		$jq('#addrBtn1').click( function() {
			flg="1";
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#teamManagerName').keypress( function(event) {
			flg="1";
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('#btnDeleteControl1').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#teamManagerName').val('');
			$jq('#teamManagerId').val('');
		});
		
		$jq('#addrSearchBtn2').click( function() {
			flg="2";
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#teamLeaderName').trigger("keypress");
		});
		
		$jq('#addrBtn2').click( function() {
			flg="2";
			//파라미터(콜백함수, 전송파라미터, 팝업옵션)
			iKEP.showAddressBook(setUser, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
		});
		
		$jq('#teamLeaderName').keypress( function(event) {
			flg="2";
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);			
		});
		
		$jq('#btnDeleteControl2').click( function() {
			
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#teamLeaderName').val('');
			$jq('#teamLeaderId').val('');
		});
		
	});
	
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
				if(flg == "1"){
					$jq("#teamManagerName").val(managerName);
					$jq("#teamManagerId").val(userId);
				}else{
					$jq("#teamLeaderName").val(managerName);
					$jq("#teamLeaderId").val(userId);
				}
				
			});
		};
		
	})(jQuery);  
//-->
</script>


	<!--blockListTable Start-->
	
			<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/officesupplies/OfficesuppliesTeamAuthList.do'/>" >
				<input type="hidden" name="teamId" value=""/> 
			</form>
			<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/officesupplies/officesuppliesTeamAuthSave.do'/>" >
				<input type="hidden" name="status" value=""/> 
				<input type="hidden" name="teamId" value="${teamAuthInfo.teamId}"/> 
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>부서 권한 관리</h2>
				</div>
				<!--//tableTop End-->	
				<div class=blockDetail>
				<table>
					<caption></caption>
						<colgroup>
							<col width="15%" />
							<col width="*" />
						</colgroup>
					<thead></thead>
					<tbody>
						<tr>
							<th>부서</th>
							<td>${teamAuthInfo.teamName}</td>
						</tr>
						<tr>
							<th>사용여부</th>
							<td>
								<label>
								<input name="statusCheck" type="radio" class="radio" value="Y" <c:if test="${teamAuthInfo.useYn eq 'Y'}">checked="checked"</c:if> /> 
									사용
								</label>
								<label>
									<input name="statusCheck" type="radio" class="radio" value="N" <c:if test="${teamAuthInfo.useYn eq 'N'}">checked="checked"</c:if>/> 
									미사용
								</label>
							</td>
						</tr>
						<tr>
							<th>검토자</th>
							<td>
								<input id="teamManagerId" name="teamManagerId" type="hidden" value="${teamAuthInfo.teamManagerId}"  />
								<input type="text" class="inputbox" id="teamManagerName" name="teamManagerName" value="${teamAuthInfo.teamManagerName}" size="30" />
								<a name="addrSearchBtn1" id="addrSearchBtn1" class="button_ic" href="#a">
									<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />검색</span>
								</a>
								<a id="addrBtn1" href="#a" class="button_ic">
									<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle">주소록</span>
								</a>
								<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl1">
									<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />삭제</span>
								</a>
							</td>
						</tr>
						<tr>
							<th>결재자</th>
							<td>
								<input id="teamLeaderId" name="teamLeaderId" type="hidden" value="${teamAuthInfo.teamLeaderId}"  />
								<input type="text" class="inputbox" id="teamLeaderName" name="teamLeaderName" value="${teamAuthInfo.teamLeaderName}" size="30" />
								<a name="addrSearchBtn2" id="addrSearchBtn2" class="button_ic" href="#a">
									<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />검색</span>
								</a>
								<a id="addrBtn2" href="#a" class="button_ic">
									<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle">주소록</span>
								</a>
								<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl2">
									<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />삭제</span>
								</a>
							</td>
						</tr>
					</tbody>
				</table>
				</div>
					<div class="blockButton"> 
						<ul>
							<li><a class="button btn_save" href="#a"><span>저장</span></a></li>
							<li><a class="button btn_cancel" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a></li>
						</ul>
					</div>	
			</form>
	<!--//blockListTable End-->
