<%--
=====================================================
* 기능 설명 : Workflow Workplace 개인설정 부재중 설정
* 작성자 : 이재경
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="ui.workflow.workplace.personal" />
<c:set var="prefixCommon"  value="ui.workflow.workplace.common" />
<c:set var="prefixCommonBtn"  value="ui.workflow.workplace.common.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script type="text/javascript">
	<!--
	//시작일과 종료일 체크하는 메써드 jQuery에 추가	
	jQuery.validator.addMethod("greaterThan", function(value, element, params) {
		var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
		var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
        if (!/Invalid|NaN/.test(val_Date)) {return val_Date >= par_Date;}
        return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) >= parseFloat(jQuery(params).val())); 
    });
	jQuery.validator.addMethod("lesserThan", function(value, element, params) {
		var val_Date = new Date(value.substring(0,4), value.substring(5,7), value.substring(8,10) );
		var par_Date = new Date(jQuery(params).val().substring(0,4), jQuery(params).val().substring(5,7), jQuery(params).val().substring(8,10) );
        if (!/Invalid|NaN/.test(val_Date)) {return val_Date <= par_Date;}
        return isNaN(value) && isNaN(jQuery(params).val()) || (parseFloat(value) <= parseFloat(jQuery(params).val())); 
    });
	
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
						
						$("#mandatorName").val(name);
						$("#mandatorId").val(userId);
					};
		
		$(document).ready(function(){
			
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $jq('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
			
			$("#startDateStr").datepicker().next().eq(0).click(function() { $("#startDateStr").datepicker("show"); });
			$("#endDateStr").datepicker().next().eq(0).click(function() { $("#endDateStr").datepicker("show"); });
			
			$("#delegateSaveBtn").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
			
			//저장 후 메시지
			if( "${saveResult}" != "ui.workflow.workplace.common.saveresult.none" ){
				alert("<spring:message code='${saveResult}'/>");
			}
			
			//입력값 확인
			$("#myForm").validate({ 
				submitHandler: function(form) {
	                  if (!confirm("<ikep4j:message pre='${prefixCommon}' key='confirm.save'/>")) return false;
	                  form.submit();
	                  return true;
	            },
	            rules: {
            		startDateStr: { required: true, lesserThan: "#endDateStr" },
            		endDateStr: { required: true, greaterThan: "#startDateStr" },
            		mandatorName : { required: true },
            		reasonComment: { required: true, minlength: 2, maxlength: 60 }
	            },
	            messages: {
            		startDateStr: {required: "<ikep4j:message pre='${prefix}' key='select.startdate'/>",lesserThan: "" },
            		endDateStr: {required: "<ikep4j:message pre='${prefix}' key='select.enddate'/>",greaterThan: "<ikep4j:message pre='${prefixCommon}' key='fault.selectdate'/>"},
            		mandatorName: { required: "<ikep4j:message pre='${prefix}' key='select.mandator'/>" },
            		reasonComment: {required: "<br><ikep4j:message pre='${prefix}' key='input.reasonComment'/>",minlength: $.format("<br>{0}<ikep4j:message pre='${prefix}' key='input.mintext'/>"),maxlength: $.format("<br>{0}<ikep4j:message pre='${prefix}' key='input.maxtext'/>")}}
			}); 
		});
	})(jQuery);
	//-->
</script>

<!--mainContents Start-->
<h1 class="none">컨텐츠영역</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='delegatesetting'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${prefix}' key='workplace'/></li>
			<li><ikep4j:message pre='${prefix}' key='personalset'/></li>
			<li class="liLast"><ikep4j:message pre='${prefix}' key='delegatesetting'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<form id="myForm" name="myForm" method="post" action="<c:url value='/workflow/workplace/personal/delegateSetSave.do'/>">

<spring:bind path="delegateBean.seqId">
	<input type="hidden" name="${status.expression}" value="${delegateBean.seqId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${prefix}' key='delegatesetting'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="delegateBean.isSetup">
					<th scope="row" width="18%"><ikep4j:message pre='${prefix}' key='${status.expression}'/></th>
					<td width="82%">
						<select title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" 
								name="${status.expression}">
							<c:forEach var="code" items="${workplaceCode.delegateClassList}">
								<option value="${code.key}" <c:if test="${code.key eq delegateBean.isSetup}">selected="selected"</c:if>><spring:message code="${code.value}" /></option>
							</c:forEach> 
						</select>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<th scope="row" width="10%"><ikep4j:message pre='${prefix}' key='delegateperiod'/></th>
					<td>
						<spring:bind path="delegateBean.startDateStr">											
						<input type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" value="<ikep4j:timezone date="${delegateBean.startDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" /> ~
						</spring:bind>
						<spring:bind path="delegateBean.endDateStr">
						<input type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" value="<ikep4j:timezone date="${delegateBean.endDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${prefixCommon}' key='calendar'/>" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<spring:bind path="delegateBean.mandatorName">
					<th scope="row" width="10%"><ikep4j:message pre='${prefix}' key='${status.expression}'/></th>
					<td>
						<input 	type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" 
								value="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}">${delegateBean.mandatorName}</c:when><c:otherwise>${delegateBean.mandatorEnglishName}</c:otherwise></c:choose>" 
			  					size="20" readonly="readonly"/>
						<a class="button_ic" href="#a" id="addrBtn"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt=""/><ikep4j:message pre='${prefix}' key='organization'/></span></a>
						</spring:bind>						
						<spring:bind path="delegateBean.mandatorId">
						<input type="hidden" id="${status.expression}" name="${status.expression}" value="${delegateBean.mandatorId}" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<spring:bind path="delegateBean.reasonComment">
					<th scope="row" width="10%"><ikep4j:message pre='${prefix}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" class="w100" title="<ikep4j:message pre='${prefix}' key='${status.expression}'/>" cols="" rows="5">${delegateBean.reasonComment}</textarea>
					</td>
					</spring:bind>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<a class="button" id="delegateSaveBtn" name="delegateSaveBtn">
			<span><ikep4j:message pre='${prefixCommonBtn}' key='save'/></span>
		</a>
	</div>
	<!--//blockButton End-->
</form>			

<!--//mainContents End-->