<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.admin.apprPwdInit.form.header" />
<c:set var="preForm"  	value="ui.approval.admin.apprPwdInit.form" />
<c:set var="preValidation" value="ui.approval.admin.apprPwdInit.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	
	
	(function($){
		
		//검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
		//result: 검색되거나 선택된 값을 배열형태로 리턴함
     	setAddress = function(data) {
						var name="";
						var userId="";
						$.each(data, function() {
							name = $.trim(this.name);
							userId = $.trim(this.id);	
						})
						
						$("#signUserName").val(name);
						$("#signUserId").val(userId);
					};
		
		setUser = function(data) {
			var name="";
			var userId="";
			
			$jq.each(data, function() {
				name = $jq.trim(this.userName);
				userId = $jq.trim(this.id);	
			})
			$jq("#signUserName").val(name);
			$jq("#signUserId").val(userId);
		};
		
		saveForm = function() {
			
			if($jq("#signUserId").val()=="") {
				alert("<ikep4j:message pre="${preForm}" key="alert.selectUser" />")
				return;	
			}
			
			if(confirm("<ikep4j:message pre="${preForm}" key="confirm.initApprPwd" />")) {
				
				$jq.ajax({     
					url : '<c:url value="/approval/admin/apprPwdInit/initApprPassword.do" />',     
					data :  $jq("#searchForm").serialize(),     
					type : "post",     
					success : function(result) {         
						alert("<ikep4j:message pre="${preMessage}" key="saveSuccess" />");
						$jq('#signUserName').val('');
						$jq('#signUserId').val('');
					},
					error : function(event, request, settings){
						 alert("error");
					}
				});
			}  
			
		};
		
		$(document).ready(function(){
			
			$jq("#apprPwdInitFormLinkOfLeft").click();
			
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
		
			//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
	        $jq('#signUserName').keypress( function(event) {
	            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
				iKEP.searchUser(event, "N", setUser);			
			});
			
		    //검색버튼을 클릭했을때 이벤트 바인딩
			$jq('#addrSearchBtn').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#signUserName').trigger("keypress");
			});
		    
			$jq('#btnCancel').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#signUserName').val('');
				$jq('#signUserId').val('');
			});
			
			$jq("#btnInit").click(function() {  
				
				saveForm();
				
			});  
		    
		});
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<%-- <div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div> --%>
</div>
<!--//pageTitle End-->

<form id="searchForm" name="searchForm" method="post" action="" onsubmit="return false;">

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='signUserName'/></th>
					<td>
						<input type="text" class="inputbox" id="signUserName" name="signUserName" title="<ikep4j:message pre='${preForm}' key='signUserName'/>" value="" size="12" />
						<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
							<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" /><ikep4j:message pre='${preButton}' key='search'/></span>
						</a>
						<a id="addrBtn" href="#a" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="address"><ikep4j:message pre='${preButton}' key='address'/></span>
						</a>
					</td>
					
					<input type="hidden" id="signUserId" name="signUserId" value=""/>
				</tr>
				
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<!--directive Start-->
	<div class="directive"> 
		<ul>
			<li><span><ikep4j:message pre="${preForm}" key="msg1" /></span></li>	
		</ul>
	</div>
	<div class="blockBlank_10px"></div>
	<!--//directive End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="btnInit"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='init'/></span></a></li>
			<li><a id="btnCancel"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
	
	
</form>			

<!--//mainContents End-->