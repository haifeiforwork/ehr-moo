<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.create" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<ikep4j:message  pre='${preMessage}' key='approverId' var="approverId"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

 <%-- 메시지 관련 Prefix 선언 End --%>
 <script type="text/javascript">
<!--   

$jq(document).ready(function() { 
	$jq("#approveButton").click(function() {
		
		if($jq("input[name=approverId]").val() == "") {
			alert("<ikep4j:message  pre='${preMessage}' key='required' arguments='${approverId}'/>");
			$jq("input[name=approverName]").focus();
			return;
		}
		
		if( confirm("<ikep4j:message pre='${preMessage}' key='approve.confirm'/>") )
			$jq("#surveyForm").submit();
		else
			
		return false; 
	});
});  

(function($) {
    $jq(document).ready(function() {
    	// 조직도 팝업 테스트
		$jq("#btnApproveAddrControl").click(function() {
			var items = [];
			var approverId = $jq("#approverId").val( );
			
			if(approverId != '' )
				items.push(approverId);

			var options={
					selectType:'user', 
					isAppend:true, 
					tabs:{common : 0,
	            		  personal : 0,
	            		  collaboration : 0,
	            		  sns : 0
					},
					selectMaxCnt:1 
				}

				iKEP.showAddressBook(
						function(result) {
							$jq.each(result, function() {
								$jq("#approverId").val( this.id );
							})
						}, 
					items, 
					options);
			
		
		});
     });
})(jQuery); 

(function($) {
	$jq(document).ready(function() {
		setUser = function(data) {
			$jq.each(data, function() {
				$jq("#approverId").val( this.userId );
			})	
		};

		
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#approverName').keypress( function(event) {
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "N", setUser);
		});
		
		        //검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#approveSearchBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#approverName').trigger("keypress");
		});
		        
		
	});
})(jQuery);

//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!-- pageTitle Start -->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!-- pageTitle End --> 
<!-- blockDetail Start -->
<div class="blockDetail">
	<form id="surveyForm" method="post" action="<c:url value='/servicepack/survey/approve/approveSubmit.do'/>">
	<input name="surveyId" title="" type="hidden" value="${survey.surveyId}" />
	<input name="controlTabType" title="" type="hidden" value="1:0:1:0" />
	<input name="controlType" title="" type="hidden" value="ORG" />
	<input name="selectType" id="selectType" title="" type="hidden" value="USER" />
	<input name="selectMaxCnt" title="" type="hidden" value="1" />
	<input name="searchSubFlag" title="" type="hidden" value="" />
	<input type="hidden" name="surveyStatus" value="1" title=""/>
	<table  summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<col style="width:20%"/>
		<col style="width:80%"/>
		<tbody> 
			<tr> 
				<th scope="row"><ikep4j:message pre='${preMessage}' key='approver' /></th>
				<td>
					<div>
					<spring:bind path="survey.approverName">
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox datepicker" 
					value="${status.value}" 
					size="30" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
					/>
					</spring:bind>
					
					<a name="approveSearchBtn" id="approveSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="<ikep4j:message pre='${preButton}' key='search' />" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
					<a id="btnApproveAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
					</div>
					<div style="margin-top:10px;">
					<spring:bind path="survey.approverId">	
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox datepicker" 
					value="${status.value}" 
					size="50" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
					readonly/>
					</spring:bind>
					</div>
				</td>
			</tr>
		</tbody> 
	</table>
	</form>
</div>
<!-- blockDetail End --> 
<!-- tableBottom Start -->
<div class="tableBottom">										
	<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<li><a id="approveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='requestApprove'/></span></a></li>
			<li><a id="cancelButton" class="button" href="<c:url value='/servicepack/survey/readSurvey.do'/>?surveyId=${survey.surveyId}"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a></li>
			<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!-- blockButton End --> 
</div>
<%@ include file="/WEB-INF/view/servicepack/survey/includeSurvey.jsp"%>
<!-- tableBottom End --> 
