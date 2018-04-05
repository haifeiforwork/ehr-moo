<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.config.notice.form.header" />
<c:set var="preForm"  	value="ui.approval.work.config.notice.form" />
<c:set var="preFormItem"  	value="ui.approval.work.config.notice.alram" />
<c:set var="preValidation" value="ui.approval.work.entrust.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	
	(function($){
		
		setUsageTbl = function(){
			if($("input[name=usage]:checked").val() == '1') {
				$("#isUsageTbl").show();
				$("#isUsageTbl2").show();
			}
			else {
				$("#isUsageTbl").hide();
				$("#isUsageTbl2").hide();
			}
		};
		
		$(document).ready(function(){
			
			$jq("#setAlramFormOfLeft").click();
			
			$("#saveNoticeButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
		
			//입력값 확인
			var validOptions = { 
				submitHandler	: function(form) {
					
					var result = "";
					var m = "";
					$("input[name=noticeMethodTmp]:checked").each(function(i) {
						if(i > 0) m = m + "," + $(this).val();
						else m = $(this).val();
					});
					if(m == "0") $("input[name=noticeMethod]:hidden").val("0");
					if(m == "1") $("input[name=noticeMethod]:hidden").val("1");
					if(m == "2") $("input[name=noticeMethod]:hidden").val("2");
					if(m == "0,1") $("input[name=noticeMethod]:hidden").val("3");
					if(m == "0,2") $("input[name=noticeMethod]:hidden").val("4");
					if(m == "1,2") $("input[name=noticeMethod]:hidden").val("5");
					if(m == "0,1,2") $("input[name=noticeMethod]:hidden").val("6");
					
	                if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
	                form.submit();
	                return true;
	            },
	            rules			: {
	            },
	            messages		: {
	            }
			}; 
			
			new iKEP.Validator("#myForm", validOptions);
			
			$("input[name=usage]").click( function() {  
				setUsageTbl();
			});
			
			setUsageTbl();
		    
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

<form id="myForm" name="myForm" method="post" action="<c:url value='/approval/work/config/noticeSetSave.do'/>">

<spring:bind path="apprNotice.userId">
	<input type="hidden" name="${status.expression}" value="${apprNotice.userId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="apprNotice.usage">
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${usageList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq apprNotice.usage or apprNotice.usage eq null}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach> 
					</td>
					</spring:bind>
				</tr>
				<tr id="isUsageTbl">
					
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='noticeMethod'/></th>
					<td>
						<c:forEach var="code" items="${methodList}">
							<input type="checkbox" class="checkbox" id="noticeMethodTmp" name="noticeMethodTmp"  value="${code.key}" 
							<c:if test="${apprNotice.noticeMethod eq code.key}">checked="true"</c:if> 
							<c:if test="${apprNotice.noticeMethod eq '3'}">
								<c:if test="${'0' eq code.key or '1' eq code.key}">checked="true"</c:if>
							</c:if> 
							<c:if test="${apprNotice.noticeMethod eq '4'}">
								<c:if test="${'0' eq code.key or '2' eq code.key}">checked="true"</c:if>
							</c:if>
							<c:if test="${apprNotice.noticeMethod eq '5'}">
								<c:if test="${'1' eq code.key or '2' eq code.key}">checked="true"</c:if>
							</c:if>
							<c:if test="${apprNotice.noticeMethod eq '6'}">
								<c:if test="${'0' eq code.key or '1' eq code.key or '2' eq code.key}">checked="true"</c:if>
							</c:if>    
							 title="<ikep4j:message pre='${preForm}' key='noticeMethod'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach> 
					</td>
					<spring:bind path="apprNotice.noticeMethod">
						<input type="hidden" name="${status.expression}" id="${status.expression}" value=""/>
					</spring:bind>
				</tr>
				<tr id="isUsageTbl2">
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='approvalAlramMethod'/></th>
					<td>
						<input type="checkbox" class="checkbox" id="noticeArrival" name="noticeArrival"  value="1"
						<c:if test="${apprNotice.noticeArrival == 1}">checked="true"</c:if>
						title="<ikep4j:message pre='${preFormItem}' key='arrival'/>"
						/>
						<ikep4j:message pre='${preFormItem}' key='arrival'/>
						<br/>
						<input type="checkbox" class="checkbox" id="noticeFinish" name="noticeFinish"  value="1"
						<c:if test="${apprNotice.noticeFinish == 1}">checked="true"</c:if>
						title="<ikep4j:message pre='${preFormItem}' key='finish'/>"
						/>
						<ikep4j:message pre='${preFormItem}' key='finish'/>
						<br/>
						<input type="checkbox" class="checkbox" id="noticeReject" name="noticeReject"  value="1"
						<c:if test="${apprNotice.noticeReject == 1}">checked="true"</c:if>
						title="<ikep4j:message pre='${preFormItem}' key='reject'/>"
						/>
						<ikep4j:message pre='${preFormItem}' key='reject'/>
						<br/>
						<input type="checkbox" class="checkbox" id="noticeReturn" name="noticeReturn"  value="1"
						<c:if test="${apprNotice.noticeReturn == 1}">checked="true"</c:if>
						title="<ikep4j:message pre='${preFormItem}' key='return'/>"
						/>
						<ikep4j:message pre='${preFormItem}' key='return'/>
					</td>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveNoticeButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->