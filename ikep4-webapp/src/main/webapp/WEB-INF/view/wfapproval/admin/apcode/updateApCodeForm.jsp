<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  	value="ui.wfapproval.admin.apcode.header" /> 
<c:set var="preList"    	value="ui.wfapproval.admin.apcode.list" />
<c:set var="preView"    	value="ui.wfapproval.admin.apcode.view" />
<c:set var="preButton"  	value="ui.wfapproval.common.button" />
<c:set var="preMessage" 	value="ui.wfapproval.common.message" />
<c:set var="preCodeMessage" value="ui.wfapproval.admin.apcode.validation" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>"></script>

<script  type="text/javascript">
		
	(function($) {
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
			
			/**
			 * apCode Validate
			 */
			var apCodeValidateOptions = {
				
				debug			: false,
				focusInvalid	: true,
				submitHandler	: function(form){
					if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
					
					$.post('<c:url value="/wfapproval/admin/apcode/ajaxApplyApCode.do"/>', $("#apCodeDetailForm").serialize()).success(function(data){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveSuccess'/>");
						
						var selectedNode = $.jstree._focused().get_selected();
						
						//코드 하위목록화면
						$("#codeChildList").load('<c:url value="/wfapproval/admin/apcode/ajaxListCodeChild.do?parentCodeId="/>'+data.codeId)
			       		.error(function(event, request, settings) { alert("error"); });
						
						//코드 상세화면 코드명 설정.
						$("#selectApCodeName").text("("+data.krName+")");
		
						//선택코드 목록 새로고침
						$('#codeTree').jstree("refresh", selectedNode);
						
	                }).error(function(event, request, settings){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveError'/>");
	                });
					
					return true;
	            },
			  	rules			: {
					codeValue		: {maxlength:30},
				    krName			: {required:true	, maxlength:30},
					enName			: {maxlength:30},
				    codeOrder		: {required:true	, digits:true}
			  	},
			  	messages		: {
					codeValue: {
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.codeValue' arguments='30'/>"
				    },
				    krName: {
				      	required 	: "<br><ikep4j:message pre='${preCodeMessage}' key='mustinput.krName'/>",
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.krName' arguments='30'/>"
				    },
					enName: {
						maxlength 	: "<br><ikep4j:message pre='${preCodeMessage}' key='maxinput.enName' arguments='30'/>"
				    },
					codeOrder: {
				      	required 	: "<br><ikep4j:message pre='${preCodeMessage}' key='mustinput.codeOrder'/>",
						digits 		: "<br><ikep4j:message pre='${preCodeMessage}' key='checkinput.codeOrder'/>"
				    }
			  	}
			};
			
			$("#apCodeDetailForm").validate(apCodeValidateOptions);
			
			$("#detailButton a").click(function(){
                switch (this.id) {
                    case "saveApCode":
						$("#apCodeDetailForm").submit();
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

<form id="apCodeDetailForm" name="apCodeDetailForm" modelAttribute="apCode" method="post">
	
	<table summary="<ikep4j:message pre="${preList}" key="summary2" />">
		<caption></caption>
		<tbody>
			<tr>
				<spring:bind path="apCode.codeId">
				<th scope="row" width="18%">Code ID</th>
				<td width="32%">
					<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="30" readonly/>
				</td>
				</spring:bind>
				<spring:bind path="apCode.parentCodeId">
				<th scope="row" width="18%">Parent Code ID</th>
				<td width="32%">
					<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="30" readonly/>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apCode.codeValue">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="inputbox" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="30" <c:if test="${apCode.codeType eq 'S'}">readonly</c:if>/>
				</td>
				</spring:bind>
				<spring:bind path="apCode.codeType">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<label>
						<input type="radio" class="radio" title="일반" name="${status.expression}" value="G" <c:if test="${status.value eq 'G' or status.value eq null}">checked</c:if>/>일반
					</label>&nbsp;&nbsp;
					<label>
						<input type="radio" class="radio" title="시스템" name="${status.expression}" value="S" <c:if test="${status.value eq 'S'}">checked</c:if>/>시스템
					</label>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apCode.krName">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text"  class="inputbox" maxlength="30" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="30"/>
				</td>
				</spring:bind>
				<spring:bind path="apCode.codeOrder">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="num" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="<c:out value="${status.value}" default="0" />" size="5"/>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apCode.enName">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<input 	type="text" class="inputbox alpha" title="<ikep4j:message pre='${preView}' key='${status.expression}' />" 
							name="${status.expression}" value="${status.value}" size="30"/>
				</td>
				</spring:bind>
				<spring:bind path="apCode.codeUse">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td>
					<label>
						<input type="radio" class="radio" title="사용" name="${status.expression}" value="Y" <c:if test="${status.value eq 'Y' or status.value eq null}">checked</c:if>/>사용
					</label>&nbsp;&nbsp;
					<label>
						<input type="radio" class="radio" title="미사용" name="${status.expression}" value="N" <c:if test="${status.value eq 'N'}">checked</c:if>/>미사용
					</label>
				</td>
				</spring:bind>
			</tr>
			<tr>
				<spring:bind path="apCode.codeDesc">
				<th scope="row"><ikep4j:message pre='${preView}' key='${status.expression}' /></th>
				<td colspan="3">
					<textarea 	name="${status.expression}" cols="80" rows="3"
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
