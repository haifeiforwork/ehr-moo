<%--
=====================================================
	* 기능설명	:	workspace 멤버 등록(관리자)
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.member.addNewMember.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.member.addNewMember.search" />
<c:set var="preCode"    value="message.collpack.common.code" />
<c:set var="preDetail"  value="message.collpack.collaboration.member.addNewMember.detail" />
<c:set var="preButton"  value="message.collpack.collaboration.member.addNewMember.button" />
<c:set var="preScript"  value="message.collpack.collaboration.member.addNewMember.script" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value='/base/js/jquery/plugins.pack.js'/>" ></script>
<script type="text/javascript">
<!-- 
var memberAdd =0;
var associateAdd=0;
(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() {
		
		<c:forEach var="workspaceTypeList" items="${workspaceTypeList}" varStatus="status">
		<c:if test="${status.index==0}">
		changeType('${workspaceTypeList.typeId}');
		</c:if>
		</c:forEach>
		
		$jq("#saveButton").click(function() {
			
			$jq("#memberIds>option").attr("selected",true);
			$jq("#associateIds>option").attr("selected",true);

			$jq.ajax({     
				url : '<c:url value='/collpack/collaboration/member/addNewMember.do' />',     
				data : $jq("#mainForm").serialize(), 
				type : "post",			
				success : function(result) {  
					location.href="<c:url value='/collpack/collaboration/member/listWorkspaceMemberView.do' />?listType=Member&workspaceId=${searchCondition.workspaceId}";
				},
				error : function(event, request, settings) { alert("error"); }
			});  
		
			return false; 
		});
		
		$("#btnMemberRemove").click(function(event) {
			event.preventDefault();
			var $member=$('#memberIds');
			$('option:selected',$member).remove();
		});
		$("#btnAssociateRemove").click(function(event) {
			event.preventDefault();
			var $associate=$('#associateIds');
			$('option:selected',$associate).remove();
		});
		$jq("#listButton").click(function() {
			document.location.href="<c:url value="/collpack/collaboration/member/listWorkspaceMemberView.do"/>?workspaceId=${searchCondition.workspaceId}&listType=${searchCondition.listType}";
			return false; 
		});
		
		$jq("#addMemberButton").click(function() {
			var items = [];
			$jq("select[name=memberIds]").children().each(function() {
				items.push($(this).data("data"));
			});

			iKEP.showAddressBook(setUserMember, items, {selectType:"all", tabs:{group:0,common:0,personal:0,collaboration:0,sns:0}});
		});	
		$jq("#addAssociateButton").click(function() {
			var items = [];
			$jq("select[name=associateIds]").children().each(function() {
				items.push($(this).data("data"));
			});

			iKEP.showAddressBook(setUserAssociate, items, {selectType:"all", tabs:{group:0,common:0,personal:0,collaboration:0,sns:0}});
		});			
		setUserMember = function(data) {
			// 주소록에서 사용자 중복 확인
			if(memberAdd==0) {
				var $sel = $jq("select[name=memberIds]")
					.children().remove()
					.end();	

				var $select = $jq("#memberIds");
				var $select1 = $jq("#associateIds");
				var duplicationUsers = [];

				$jq.each(data, function() {

					var hasOption = $select1.find('option[value="'+this.id+'"]');

					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}
				
				})
				
				if(duplicationUsers.length > 0) alert('<ikep4j:message pre='${preScript}' key='associateDup'/> : '+"[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
	
			} else { // 개별검색으로 사용자 중복 확인
				var $select = $jq("#memberIds");
				var $select1 = $jq("#associateIds");
				var duplicationUsers = [];
				var duplicationUsers1 = [];
				var dupCheck = 0;
				$jq.each(data, function() {

					var hasOption = $select.find('option[value="'+this.id+'"]');
					var hasOption1 = $select1.find('option[value="'+this.id+'"]');
					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else if(hasOption1.length < 1 )  {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}
					if(hasOption1.length > 0){
						dupCheck=1;
						duplicationUsers1.push(this.userName);
					}					
				})
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				if(dupCheck > 0) alert('<ikep4j:message pre='${preScript}' key='associateDup'/> : '+"[" + duplicationUsers1.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				
				$jq('#userNameMember').val('');
			}
			memberAdd=0;
		};
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#userNameMember').keypress( function(event) {
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
            memberAdd=1;
			iKEP.searchUser(event, "Y", setUserMember);			
		});
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#userMemberSearchBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#userNameMember').trigger("keypress");
 
		});		
		setUserAssociate = function(data) {
			if(memberAdd==0)
			{
				var $sel = $jq("select[name=associateIds]")
					.children().remove()
					.end();	
					
				var $select = $jq("#associateIds");
				var $select1 = $jq("#memberIds");
				var duplicationUsers = [];
			
				$jq.each(data, function() {

					var hasOption = $select1.find('option[value="'+this.id+'"]');	
			
					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}
				
				})
				
				if(duplicationUsers.length > 0) alert('<ikep4j:message pre='${preScript}' key='memberDup'/> : ' +"[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);				
				
			} else {
				var $select = $jq("#associateIds");
				var $select1 = $jq("#memberIds");
				var duplicationUsers = [];
				var duplicationUsers1 = [];
				var dupCheck = 0;				
				$jq.each(data, function() {

					var hasOption = $select.find('option[value="'+this.id+'"]');	
					var hasOption1 = $select1.find('option[value="'+this.id+'"]');					
					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else if(hasOption1.length < 1 ) {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}
					if(hasOption1.length > 0){
						dupCheck=1;
						duplicationUsers1.push(this.userName);
					}					
				})
				
				//$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
				
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				if(dupCheck > 0) alert('<ikep4j:message pre='${preScript}' key='memberDup'/> : ' +"[" + duplicationUsers1.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);				
				$jq('#userNameAssociate').val('');
			}
			
			memberAdd=0;
		};
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#userNameAssociate').keypress( function(event) {
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
            memberAdd=1;
			iKEP.searchUser(event, "Y", setUserAssociate);			
		});	
		//검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#userAssociateSearchBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#userNameAssociate').trigger("keypress");
 
		});						
	});

	addMemberList = function(){

		var items = [];
		var $sel = $jq("#mainForm").find("[name=memberIds]");
		//var $sel = $jq("#mainForm").find("[name="+selectName+"]");
		
		$jq.each($sel.children(), function() {
			items.push($jq.data(this, "data"));
		});

		//alert($jq)
		$controlType	= $jq('input[name=controlType]:hidden').val() ;
		$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
		$selectType		= $jq('input[name=selectType]:hidden').val() ;
		$selectMaxCnt	= $jq('input[name=selectMaxCnt]:hidden').val() ;
		$searchSubFlag	= $jq('input[name=searchSubFlag]:hidden').val() ;

		// 수정 될 일반 팝업 형태의 팝업 오픈 시 사용될 스크립트
		var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
		iKEP.popupOpen(
			url,
			{
				width:700, height:550,
				argument : {search:"keyword", items:items },
				callback:function(result) {
					
					$sel.empty();
					//var duplicationUsers = [];
					
					$jq.each(result, function() {

						var tpl = "";
						var dupCheck = 0;						
						
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}					

						/** 정회원 , 준회원 중복여부 **/
						var userId = this.id;						
						var $sel1 = $jq("#mainForm").find("[name=associateIds]");		
						$jq.each($sel1.children(), function() {
							if(userId == $(this).val()) {
								dupCheck =1;
								//duplicationUsers.push(this.userName);
							}
						});
						
						if(dupCheck==0){
							var $option = $jq.tmpl(tpl, this).appendTo($sel);
							$jq.data($option[0], "data", this);									
							if( this.searchSubFlag == true ){
								$jq('input[name=searchSubFlag]:hidden').val("true") ;
							}
						}						
						
					});
					//if(duplicationUsers.length > 0) alert('<ikep4j:message pre='${preScript}' key='associateDup'/> : ' +"[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
					
		        }
			}
		);
		
	};
	
	addAssociateList = function(){

		var items = [];
		var $sel = $jq("#mainForm").find("[name=associateIds]");
		//var $sel = $jq("#mainForm").find("[name="+selectName+"]");
		
		$jq.each($sel.children(), function() {
			items.push($jq.data(this, "data"));
		});

		//alert($jq)
		$controlType	= $jq('input[name=controlType]:hidden').val() ;
		$controlTabType = $jq('input[name=controlTabType]:hidden').val() ;
		$selectType		= $jq('input[name=selectType]:hidden').val() ;
		$selectMaxCnt	= $jq('input[name=selectMaxCnt]:hidden').val() ;
		$searchSubFlag	= $jq('input[name=searchSubFlag]:hidden').val() ;

		// 수정 될 일반 팝업 형태의 팝업 오픈 시 사용될 스크립트
		var url = "<c:url value='/support/popup/addresbookPopup.do'/>"+"?controlType=" + $controlType + "&controlTabType=" + $controlTabType + "&selectType=" + $selectType + "&selectMaxCnt=" + $selectMaxCnt + "&searchSubFlag=" + $searchSubFlag;
		iKEP.popupOpen(
			url,
			{
				width:700, height:550,
				argument : {search:"keyword", items:items },
				callback:function(result) {
					
					$sel.empty();
					//var duplicationUsers = [];
					
					$jq.each(result, function() {

						var tpl = "";
						var dupCheck = 0;
						
						switch(this.type) {
							case "group" : tpl = "addrBookItemGroup"; break;
							case "user" : tpl = "addrBookItemUser"; break;
						}
						

						/** 정회원 , 준회원 중복여부 **/
						var userId = this.id;						
						var $sel1 = $jq("#mainForm").find("[name=memberIds]");		
						$jq.each($sel1.children(), function() {
							if(userId == $(this).val()){
								dupCheck =1;
								//duplicationUsers.push(this.userName);
							}
						});
						
						if(dupCheck==0){
							var $option = $jq.tmpl(tpl, this).appendTo($sel);
							$jq.data($option[0], "data", this);									
							if( this.searchSubFlag == true ){
								$jq('input[name=searchSubFlag]:hidden').val("true") ;
							}
						}	
							
						
					});
					//if(duplicationUsers.length > 0) alert('<ikep4j:message pre='${preScript}' key='memberDup'/> : ' +"[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
		        }
			}
		)
	};	
	

		
		
		
	// java script 전역변수 항목에 추가
	$jq.template("addrBookItemUser", "<option value='\${id}'>\${name}/\${jobTitle}/\${teamName}</option>");
	$jq.template("addrBookItemGroup", "<option value='\${code}'>\${name}</option>");

	// 주의 위에 code 가 가이드 입력시 오류로 입력되지 않아서 부득이하게 공백으로 입력
	// 추후 사용시 공백 제거후 사용 해주세요

	
})(jQuery);  
//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 

<!--pageTitle Start-->

<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 

</div> 

<!--//pageTitle End-->  
<!--blockListTable Start-->
<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/member/addNewMember.do' />" onsubmit="return false;">  
<spring:bind path="searchCondition.sortColumn">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 		
<spring:bind path="searchCondition.sortType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind> 
<spring:bind path="searchCondition.workspaceStatus">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.listType">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.memberLevel">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="searchCondition.portalId">
<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<div class="blockDetail">

	<table summary="<ikep4j:message pre="${preDetail}" key="summary" />">
		<caption></caption>
		<col style="width: 18%;"/>
		<col style=""/>	
		<tbody>
		

		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='workspaceName' /></th>
			<td>${workspace.workspaceName }</td>
		</tr>		
		
		
		<tr>
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member3' /></th>
			<td>
			<input name="userNameMember" id="userNameMember" type="text" class="inputbox" size="20" title="userName" />
			<a name="userMemberSearchBtn" id="userMemberSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
			<a id="addMemberButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='address'/>"><span><ikep4j:message pre='${preButton}' key='address'/></span></a>
			<div style="padding-top:4px;"> 
			<select name="memberIds" id="memberIds" class="input_select w80" size="4"	style="height:100px;" multiple>
			</select>
			<span>
			<a id="btnMemberRemove" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</span>
			</div>
			</td>
		</tr>		
		
		<tr>			
			<th scope="row"><ikep4j:message pre='${preDetail}' key='member4' /></th>
			<td>
			<input name="userNameAssociate" id="userNameAssociate" type="text" class="inputbox" size="20" title="userName" />
			<a name="userAssociateSearchBtn" id="userAssociateSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>			
			<a id="addAssociateButton" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='address'/>"><span><ikep4j:message pre='${preButton}' key='address'/></span></a>
			<div style="padding-top:4px;"> 
			<select name="associateIds" id="associateIds" class="input_select w80" size="4"	style="height:100px;" multiple>
			</select>
			<span>
			<a id="btnAssociateRemove" class="button_s" href="#a" title="<ikep4j:message pre='${preButton}' key='delete'/>"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a>
			</span>
			</div>
			</td>			
		</tr>
		</tbody>
	</table>

	
	
</div>

<input name="controlTabType" title="" type="hidden" value="0:0:0:0" />
<input name="controlType" title="" type="hidden" value="ORG" />
<input name="selectType" title="" type="hidden" value="USER" />
<input name="selectMaxCnt" title="" type="hidden" value="100" />
<input name="searchSubFlag" title="" type="hidden" value="" />
</form>
<!--//blockListTable End--> 

<!--blockButton Start-->
<div class="blockButton">
<ul>
	<li><a id="saveButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='save'/>"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
	<li><a id="listButton" class="button" href="#a" title="<ikep4j:message pre='${preButton}' key='list'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
</ul>
</div>
<!--//blockButton End-->		
