<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.work.delegate.form.header" />
<c:set var="preForm"  	value="ui.wfapproval.work.delegate.form" />
<c:set var="preValidation" value="ui.wfapproval.work.delegate.validation" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preMessage" value="ui.wfapproval.common.message" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />
<c:set var="preIcon"  	value="ui.wfapproval.common.icon" />
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
		
		var viewUnitDelegateDialog;
		
		/**
		 * 위임 설정 화면 오픈.
		 */
		viewUnitDelegateDialog = function () {
			
			$instanceLogId	= $("input[name=seqId]:hidden");
			
			viewApFormProcessDialog = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre='${preForm}' key='subTitle'/>",
				url 		: "updateUnitDelegateForm.do",
				modal 		: true,
				width 		: 650,
				height 		: 250,
				params 		: {id:$instanceLogId.val()}
			});
		};
		
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
			
			$("#viewUnitDelegateButton").click(function(){
				viewUnitDelegateDialog();
			});
			
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
			
			$("#startDateStr").datepicker().next().eq(0).click(function() { $("#startDateStr").datepicker("show"); });
			$("#endDateStr").datepicker().next().eq(0).click(function() { $("#endDateStr").datepicker("show"); });
			
			$("#saveDelegateButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
			
			//저장 후 메시지
			if( "${saveResult}" != "ui.workflow.workplace.common.saveresult.none" ){
				alert("<spring:message code='${saveResult}'/>");
			}
			
			//입력값 확인
			$("#myForm").validate({ 
				submitHandler	: function(form) {
	                  if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
	                  form.submit();
	                  return true;
	            },
	            rules			: {
	            		startDateStr	: { required: true, lesserThan: "#endDateStr" },
	            		endDateStr		: { required: true, greaterThan: "#startDateStr" },
	            		mandatorName 	: { required: true },
	            		reasonComment	: { required: true, minlength: 2, maxlength: 60 }
	            },
	            messages		: {
		            		startDateStr	: { 	
								required		: "<ikep4j:message pre='${preValidation}' key='noselect.startdate'/>", 
	            				lesserThan		: "" 
							},
		            		endDateStr		: { 		
								required		: "<ikep4j:message pre='${preValidation}' key='noselect.enddate'/>",
        						greaterThan		: "<ikep4j:message pre='${preValidation}' key='fault.selectdate'/>"  
							},
    						mandatorName	: { 
								required		: "<ikep4j:message pre='${preValidation}' key='noselect.mandator'/>" 
							},
        					reasonComment	: {
		                        required		: "<ikep4j:message pre='${preValidation}' key='mustinput.reasonComment'/>",
		                        minlength		: "<ikep4j:message pre='${preValidation}' key='mininput.reasonComment' arguments='2'/>",
		                        maxlength		: "<ikep4j:message pre='${preValidation}' key='maxinput.reasonComment' arguments='60'/>"
          					}
	            }
			}); 
		});
	})(jQuery);
	//-->
</script>

<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre="${preHeader}" key="pageLocation.1depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.2depth" /></li>
			<li><ikep4j:message pre="${preHeader}" key="pageLocation.3depth" /></li>
			<li class="liLast"><ikep4j:message pre="${preHeader}" key="pageLocation.4depth" /></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<form id="myForm" name="myForm" method="post" action="<c:url value='/wfapproval/work/delegate/delegateSetSave.do'/>">

<spring:bind path="delegateBean.seqId">
	<input type="hidden" name="${status.expression}" value="${delegateBean.seqId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="delegateBean.isSetup">
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<select title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" 
								name="${status.expression}">
							<c:forEach var="code" items="${workplaceCode.delegateClassList}">
								<option value="${code.key}" <c:if test="${code.key eq delegateBean.isSetup}">selected="selected"</c:if>><spring:message code="${code.value}" /></option>
							</c:forEach> 
						</select>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='delegateperiod'/></th>
					<td>
						<spring:bind path="delegateBean.startDateStr">											
						<input 	type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" 
								title="<ikep4j:message pre='${preForm}' key='startDateStr'/>" 
								value="<ikep4j:timezone date="${delegateBean.startDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> 
								<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preIcon}' key='calendar'/>" /> ~
						</spring:bind>
						<spring:bind path="delegateBean.endDateStr">
						<input 	type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" 
								title="<ikep4j:message pre='${preForm}' key='endDateStr'/>" 
								value="<ikep4j:timezone date="${delegateBean.endDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> 
								<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preIcon}' key='calendar'/>" />
						</spring:bind>
					</td>
				</tr>
				<tr>
					<spring:bind path="delegateBean.mandatorName">
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" value="${delegateBean.mandatorName}" size="12" readonly="readonly"/>
						<a id="addrBtn" href="#" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preForm}' key='organization'/></span>
						</a>
					</td>
					</spring:bind>
					<spring:bind path="delegateBean.mandatorId">
						<input type="hidden" id="${status.expression}" name="${status.expression}" value="${delegateBean.mandatorId}"/>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="delegateBean.reasonComment">
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" class="tabletext" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" cols="300" rows="7">${delegateBean.reasonComment}</textarea>
					</td>
					</spring:bind>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveDelegateButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
			<li><a id="viewUnitDelegateButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->