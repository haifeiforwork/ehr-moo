<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  	value="ui.approval.apprCode.header" /> 
<c:set var="preList"    	value="ui.approval.apprCode.list" />
<c:set var="preView"    	value="ui.approval.apprCode.view" />
<c:set var="preCodeMessage" value="ui.approval.apprCode.validation" />
<c:set var="preButton"  	value="ui.approval.common.button" />
<c:set var="preMessage" 	value="ui.approval.common.message" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script  type="text/javascript">
		
	(function($) {
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			
			/**
			 * apprCode Validate
			 */
			var apprCodeValidateOptions = {
				
				debug			: false,
				focusInvalid	: true,
				submitHandler	: function(form){
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
					
					$.post('<c:url value="/approval/admin/apprCode/ajaxApplyApprCode.do"/>', $("#apprCodeDetailForm").serialize()).success(function(data){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveSuccess'/>");
						
						var selectedNode = $.jstree._focused().get_selected();
						
						//코드 하위목록화면
						$("#codeChildList").load('<c:url value="/approval/admin/apprCode/ajaxListCodeChild.do?parentCodeId="/>'+data.codeId)
			       		.error(function(event, request, settings) { alert("error"); });
						
						//코드 상세화면 코드명 설정.
						$("#selectApCodeName").text("("+data.codeKrName+")");
						
						//선택코드 목록 새로고침
						if($("input[name=codeId]").val() == "") {
							$('#codeTree').jstree("refresh", selectedNode);
						}
						else {
							$('#codeTree').jstree("refresh", selectedNode.parent());
						}
						
	                }).error(function(event, request, settings){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveError'/>");
	                });
					
					return true;
	            },
			  	rules			: {
					codeValue		: {maxlength:30},
				    codeKrName			: {required:true	, maxlength:30},
					codeEnName			: {maxlength:30},
				    codeOrder		: {required:true	, digits:true}
			  	},
			  	messages		: {
					codeValue: {
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.codeValue' arguments='30'/>"
				    },
				    codeKrName: {
				      	required 	: "<br><ikep4j:message pre='${preCodeMessage}' key='mustinput.codeKrName'/>",
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.codeKrName' arguments='30'/>"
				    },
					codeEnName: {
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.codeEnName' arguments='30'/>"
				    },
					codeOrder: {
				      	required 	: "<br><ikep4j:message pre='${preCodeMessage}' key='mustinput.codeOrder'/>",
						digits 		: "<br><ikep4j:message pre='${preCodeMessage}' key='checkinput.codeOrder'/>"
				    }
			  	}
			};
			
			$("#apprCodeDetailForm").validate(apprCodeValidateOptions);
			
			$("#detailButton a").click(function(){
                switch (this.id) {
                    case "saveApCode":
						$("#apprCodeDetailForm").submit();
						break;
                    case "newApCode":
                        f_ApCodeDetail('');
                        break;
                    default:
                        break;
                }
            });
		});
		
	})(jQuery); 

</script>

<!--blockDetail Start-->
<div class="blockDetail">

<form id="apprCodeDetailForm" name="apprCodeDetailForm" modelAttribute="apprCode" method="post">
	
	<table summary="<ikep4j:message pre="${preList}" key="summary2" />">
		<caption></caption>
		<tbody>
			<tr>
				<spring:bind path="apprCode.codeId">
				<th scope="row" width="18%">Code ID</th>
				<td width="32%">
					${apprCode.codeId}
					<input 	type="hidden" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}"  readonly/>
				</td>
				</spring:bind>
				<spring:bind path="apprCode.parentCodeId">
				<th scope="row" width="18%">Parent Code ID</th>
				<td width="32%">
					${apprCode.parentCodeId}
					<input 	type="hidden" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" readonly/>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apprCode.codeValue">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="20" <c:if test="${apprCode.codeType eq '1'}">readonly</c:if>/>
				</td>
				</spring:bind>
				<spring:bind path="apprCode.codeType">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<label>
						<input type="radio" class="radio" title="일반" name="${status.expression}" value="0" <c:if test="${status.value eq '0' or status.value eq null}">checked</c:if>/>일반
					</label>&nbsp;&nbsp;
					<label>
						<input type="radio" class="radio" title="시스템" name="${status.expression}" value="1" <c:if test="${status.value eq '1'}">checked</c:if>/>시스템
					</label>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apprCode.codeKrName">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text"  class="inputbox" maxlength="30" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="20"/>
				</td>
				</spring:bind>
				<spring:bind path="apprCode.codeOrder">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="num" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="<c:out value="${status.value}" default="0" />" size="5"/>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apprCode.codeEnName">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="inputbox alpha" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="20"/>
				</td>
				</spring:bind>
				<spring:bind path="apprCode.usage">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<label>
						<input type="radio" class="radio" title="사용" name="${status.expression}" value="1" <c:if test="${status.value eq '1' or status.value eq null}">checked</c:if>/>사용
					</label>&nbsp;&nbsp;
					<label>
						<input type="radio" class="radio" title="미사용" name="${status.expression}" value="0" <c:if test="${status.value eq '0'}">checked</c:if>/>미사용
					</label>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apprCode.codeDesc">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td colspan="3">
					<textarea 	name="${status.expression}" cols="60" rows="3"
								title="<ikep4j:message pre='${preView}' key='${status.expression}' />">${status.value}</textarea>
				</td>
				</spring:bind>
			</tr>
		</tbody>
	</table>
</form>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div id="detailButton" class="blockButton"> 
	<ul>
		<li><a id="saveApCode"  class="button" href="#"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		<li style="display:none;"><a id="newApCode"	class="button" href="#"><span><ikep4j:message pre='${preButton}' key='new'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->
