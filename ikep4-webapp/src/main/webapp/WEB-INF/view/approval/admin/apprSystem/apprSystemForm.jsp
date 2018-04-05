<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 


<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage"	value="message.approval.admin.apprSystem.apprSystemList"/>

<c:set var="preHeader"	value="ui.approval.admin.apprSystem.apprSystemList.header"/>
<c:set var="preList"	value="ui.approval.admin.apprSystem.apprSystemList.list"/>
<c:set var="preView"	value="ui.approval.admin.apprSystem.apprSystemList.view"/>
<c:set var="preSearch"	value="ui.approval.admin.apprSystem.apprSystemList.search"/>
<c:set var="preButton"	value="ui.approval.admin.apprSystem.apprSystemList.button"/>	
				
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal"	value="${sessionScope['ikep.portal']}" />
<c:set var="user"	value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript" language="javascript">
<!--
	$jq(document).ready(function() {
		$jq("#mainForm :input:visible:enabled:first").focus().select();
		
		// ID 중복을 체크함
		$jq("#checkSystemIdButton").click(function() {
			checkSystemId();
		});
		
		$jq("input[name=systemId]").change(function() {
			$jq("#systemIdCheck").val('changed');
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
		
		var validOptions = {
				rules : {
					systemId : {
						required : true,
						rangelength : [0, 20]
					},
					systemName : {
						required : true,
						rangelength : [0, 20]
					},
					systemEnName : {
						required : true,
						englishName : true,
						rangelength : [0, 100]										
					}
				},
				messages : {
					systemId : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemId" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemId" />"
					},
					systemName : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemName" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemName" />"
					},
					systemEnName : {
						required : "<ikep4j:message key="NotNull.apprSystem.systemEnName" />",
						englishName : "<ikep4j:message key="EnglishName.apprSystem.systemEnName" />",
						rangelength : "<ikep4j:message key="Size.apprSystem.systemEnName" />"
					}
				},
				submitHandler : function() {
					
					// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
					if (($jq("#systemIdCheck").val() == 'success') || 
							($jq("#systemIdCheck").val() == 'modify')) {
						$jq.ajax({
							url : '<c:url value="createApprSystem.do" />',
							data : $jq("#mainForm").serialize(),
							type : "post",
							success : function(result) {
								alert("<ikep4j:message pre="${preMessage}" key="saveCompleted" />");
								$jq("#tempCode").val(result);
								getList();
							},
							error : function(xhr, exMessage) {
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
							}
						});
					} else {
						alert("<ikep4j:message pre="${preMessage}" key="checkDuplicated" />");
						return;
					}
				}

			 };

		var validator = new iKEP.Validator("#mainForm", validOptions);
	});
//-->
</script>

<form id="mainForm" name="mainForm" method="post" onsubmit="return false;" action="">

	<input type="hidden" id="systemIdCheck" name="systemIdCheck" value="${apprSystem.systemIdCheck}"/>

	<table summary="<ikep4j:message pre="${preList}" key="tableSummary" />">
	<caption></caption>
		<colgroup>
			<col width="15%"/>
			<col width="10%"/>
			<col width="75%"/>
		</colgroup>								
	<tbody>
		<tr>
			<th scope="row" colspan="2">
				<span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemId" />
			</th>
			<td>
				<div>
					<input type="text" name="systemId" id="systemId" value="${apprSystem.systemId}" size="50" class="inputbox" />
					&nbsp;<a class="button_ic" id="checkSystemIdButton" href="#a"><span><ikep4j:message pre="${preList}" key="checkDuplicated" /></span></a>
				</div>
			</td>
		</tr>
				<tr>
					<th scope="row" colspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemType" />
					</th>
					<td>
						<div>
							<select title="<ikep4j:message pre='${preList}' key='systemType' />" name="systemType" id="systemType">
							<option value="0" <c:if test="${apprSystem.systemType==0}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='systemType.systemType0' /></option>
							<option value="1" <c:if test="${apprSystem.systemType==1}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='systemType.systemType1' /></option>
							<option value="2" <c:if test="${apprSystem.systemType==2}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='systemType.systemType2' /></option>
							<option value="3" <c:if test="${apprSystem.systemType==3}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='systemType.systemType3' /></option>
							<option value="4" <c:if test="${apprSystem.systemType==4}">selected="selected"</c:if>><ikep4j:message pre='${preList}' key='systemType.systemType4' /></option>
							</select>	
						</div>
					</td>
				</tr>		
		<tr>
			<th scope="row" rowspan="2"><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemName" /></th>
			<th><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemName" /></th>
			<td>
				<div>
					<input type="text" name="systemName" id="systemName" value="${apprSystem.systemName}" size="50" class="inputbox" />
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row"><span class="colorPoint">*</span> <ikep4j:message pre="${preList}" key="systemEnName" /></th>
			<td>
				<div>
					<input type="text" name="systemEnName" id="systemEnName" value="${apprSystem.systemEnName}" size="50" class="inputbox" />
				</div>
			</td>
		</tr>
				<tr>
					<th scope="row" colspan="2"><ikep4j:message pre="${preList}" key="description" /></th>
					<td>
						<div>
							<textarea name="description" id="description"  class=""  style="width:80%;" title="<ikep4j:message pre='${preList}' key='description'/>">${apprSystem.description}</textarea>
						</div>
					</td>
				</tr>		
	</tbody>
	
	</table>
	
	<input type="hidden" id="systemOrder" name="systemOrder" value="${apprSystem.systemOrder}"/>
	<input type="hidden" id="oldSystemOrder" name="oldSystemOrder" value="${oldSystemOrder}"/>
		
	</form>