<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript" language="javascript">
//<!--
	$jq(document).ready(function() {
		$jq("#equipment :input:visible:enabled:first").focus().select();
		
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
					equipmentName : {
						required : true,
						rangelength : [0, 20]
					},
					equipmentEnglishName : {
						required : true,
						terminology : true,
						rangelength : [0, 100]								
					}
			    },
			    messages : {
			    	equipmentName : {
						required : '<ikep4j:message key="NotNull.Equipment.equipmentName" />',
						rangelength : '<ikep4j:message key="Size.Equipment.equipmentName" />'
					},
					equipmentEnglishName : {
						required : '<ikep4j:message key="NotNull.Equipment.equipmentEnglishName" />',
						terminology : '<ikep4j:message key="Terminology.Equipment.equipmentEnglishName" />',
						rangelength : '<ikep4j:message key="Size.Equipment.equipmentEnglishName" />'
					}
			    },
			    submitHandler : function(jobTitle) {
			    	
			    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
					if (($jq("#status").val() == 'success') || ($jq("#status").val() == 'modify')) {
						$jq.ajax({
							url : '<c:url value="createEquipment.do" />',
							data : $jq("#equipment").serialize(),
							type : "post",
							success : function(result) {
								alert('<ikep4j:message pre="${preMessage}" key="saveComplete"/>');
								$jq("#tempEquipmentId").val(result);
								getList();
							},
								error : function(xhr, exMessage) {
							var errorItems = $.parseJSON(xhr.responseText).exception;
							validator.showErrors(errorItems);
							}
						});
					} else {
						alert('<ikep4j:message pre="${preMessage}" key="saveFail"/>');
						return;
					}
				}

			 };

		var validator = new iKEP.Validator("#equipment", validOptions);
	});
//-->
</script>

<form id="equipment" name="equipment" method="post" onsubmit="return false;" action="">

	<input type="hidden" id="equipmentId" name="equipmentId" value="${equipment.equipmentId}"/>
	<input type="hidden" id="status" name="status" value="${equipment.status}"/>

	<table summary="<ikep4j:message pre='${preHeader}' key='equipment'/>">
	<caption></caption>
		<colgroup>
			<col width="15%"/>
			<col width="10%"/>
			<col width="75%"/>
		</colgroup>
	<tbody>
		<tr>
			<th scope="row" rowspan="2">
				<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='equipmentName'/>
			</th>
			<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name'/></th>
			<td>
				<div>
					<input type="text" name="equipmentName" id="equipmentName" value="${equipment.equipmentName}" size="50" class="inputbox"/>
				</div>
			</td>
		</tr>
		<tr>
			<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName'/></th>
			<td>
				<div>
					<input type="text" name="equipmentEnglishName" id="equipmentEnglishName" value="${equipment.equipmentEnglishName}" size="50" class="inputbox" />
				</div>
			</td>
		</tr>
		<tr>
			<th scope="row" colspan="2">
				<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='useFlag'/>
			</th>
			<td>
				<div>
					<input type="radio" id="useRadio" name="useFlag" class="radio" value="1" <c:if test="${empty equipment.useFlag || equipment.useFlag == '1'}">checked="checked"</c:if> />
					<label for="useRadio"> <ikep4j:message pre='${preDetail}' key='useFlagY'/></label>&nbsp;
					<input type="radio" id="unUseRadio" name="useFlag" class="radio" value="0" <c:if test="${!empty equipment.useFlag && equipment.useFlag != '1'}">checked="checked"</c:if> />
					<label for="unUseRadio"> <ikep4j:message pre='${preDetail}' key='useFlagN'/></label>
				</div>
			</td>
		</tr>
	</tbody>
	
	</table>
	
	<input type="hidden" id="sortOrder" name="sortOrder" value="${equipment.sortOrder}"/> 
	<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
	
</form>