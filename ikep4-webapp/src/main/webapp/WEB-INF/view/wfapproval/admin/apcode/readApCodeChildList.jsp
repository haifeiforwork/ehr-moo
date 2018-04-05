<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.wfapproval.admin.apcode.header" /> 
<c:set var="preList"    value="ui.wfapproval.admin.apcode.list" />
<c:set var="preView"    value="ui.wfapproval.admin.apcode.view" />
<c:set var="preButton"  value="ui.wfapproval.common.button" />
<c:set var="preSearch"  value="ui.wfapproval.common.searchCondition" />

<!--
<style type="text/css">
	.ui-selectable .ui-selecting { background: #FECA40; }
    .ui-selectable .ui-selected { background: #F39814; color: white; }
</style>
-->

<script  type="text/javascript">
	
	(function($) {
		
		/**
		 * onload시 수행할 코드
		 */
		$(document).ready(function() {
		   
			$("#dataSortable").sortable({
				cursor	: "crosshair"
				//, placeholder : "ui-state-highlight"
				//, revert : true
			});
			
			/*
			$("#selectable").disableSelection();
			
			$("#selectable").selectable({
				filter		: "tr", //">*>tr>td",
				stop		: function(event, ui){
					//iKEP.debug(ui);
		        },
	            unselected	: function(event, ui){
	                //iKEP.debug(ui);
	            }
	        });
	        */
	        
			
			
            $("#checkboxAllApForm").click(function(){
                $("input[name=apCodeIds]").attr("checked", $(this).is(":checked"));
            });
			
			$("#detailChildButton a").click(function(){
                switch (this.id) {
                    case "deleteApCode":
						
						//선택된 항목이 없다면 리턴.
						if($("#apCodeIds:checked").length == 0){
							return false;
						}
						
						$.post('<c:url value="/wfapproval/admin/apcode/ajaxDeleteApCode.do"/>', $("#apCodeChildForm").serialize()).success(function(data){
		                    alert("삭제 하였습니다.");
							
							var selectedNode = $.jstree._focused().get_selected();
							
							//코드 하위목록화면 새로고침
							$("#codeChildList").load('<c:url value="/wfapproval/admin/apcode/ajaxListCodeChild.do?parentCodeId="/>'+data.parentCodeId)
				       		.error(function(event, request, settings) { alert("error"); });
							
							//선택코드 목록 새로고침
							$('#codeTree').jstree("refresh", selectedNode);
							
		                }).error(function(event, request, settings){
		                    alert("error");
		                });
						break;
					case "orderApCode":
						$.post('<c:url value="/wfapproval/admin/apcode/ajaxSaveOrderApCode.do"/>', $("#apCodeChildForm").serialize()).success(function(data){
		                    alert("저장 하였습니다.");
							
							var selectedNode = $.jstree._focused().get_selected();
							
							//코드 하위목록화면 새로고침
							$("#codeChildList").load('<c:url value="/wfapproval/admin/apcode/ajaxListCodeChild.do?parentCodeId="/>'+data.parentCodeId)
				       		.error(function(event, request, settings) { alert("error"); });
							
							//선택코드 목록 새로고침
							$('#codeTree').jstree("refresh", selectedNode);
							
		                }).error(function(event, request, settings){
		                    alert("error");
		                });
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
<form id="apCodeChildForm" name="apCodeChildForm" modelAttribute="apCodeChild" method="post">
	
	<input name="parentCodeId" type="hidden" value="${parentCodeId}"/>
	
	<table id="selectable" summary="<ikep4j:message pre="${preList}" key="summary2" />">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="8%" class="textCenter"><input id="checkboxAllApForm" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
				<th scope="col" width="10%" class="textCenter">
					<ikep4j:message pre='${preView}' key='codeOrder' />
				</th>
				<th scope="col" width="15%" class="textCenter">
					<ikep4j:message pre='${preView}' key='codeValue' />
				</th>
				<th scope="col" width="15%" class="textCenter">
					<ikep4j:message pre='${preView}' key='krName' />
				</th>
				<th scope="col" width="15%" class="textCenter">
					<ikep4j:message pre='${preView}' key='enName' />
				</th>
				<th scope="col" class="textCenter">
					<ikep4j:message pre='${preView}' key='codeDesc' />
				</th>
			</tr>
		</thead>
		<tbody id="dataSortable" class="ui-sortable">
			<c:choose>
			    <c:when test="${empty apCodeChild}">
					<tr>
						<td colspan="6" class="emptyRecord" class="textCenter"><ikep4j:message pre='${preSearch}' key='emptyRecord' /></td>
					</tr>
			    </c:when>
			    <c:otherwise>
					<c:forEach var="apCode" items="${apCodeChild}" varStatus="apCodeItem">
						<!--
						<c:choose>
				 			<c:when test="${apCodeItem.count % 2 == 0}">
				 				<c:set var="className" value="bgWhite"/>
				 			</c:when>
				 			<c:otherwise>
				 				<c:set var="className" value="bgSelected"/>
				 			</c:otherwise>
				 		</c:choose>
						-->
						<c:set var="className" value="bgWhite"/>
						<!--tr class="${className}"-->
						<tr>
							<input 	id="apCodeOrder" name="apCodeOrder" type="hidden" value="${apCode.codeId}"/>
							<td class="textCenter">
								<c:choose>
						 			<c:when test="${apCode.codeType eq 'G'}">	
										<c:choose>
								 			<c:when test="${apCode.childCount eq 0}">
								 				<input 	id="apCodeIds" name="apCodeIds" class="checkbox" title="checkbox" type="checkbox" value="${apCode.codeId}"/>
								 			</c:when>
								 			<c:otherwise>└[${apCode.childCount}]</c:otherwise>
								 		</c:choose>
									</c:when>
								 	<c:otherwise>시스템</c:otherwise>
								</c:choose>
							</td>
							<td class="textCenter">${apCode.codeOrder}</td>
							<td class="textCenter">${apCode.codeValue}</td>
							<td class="textCenter">${apCode.krName}</td>
							<td class="textCenter">${apCode.enName}</td>
							<td class="textLeft">${apCode.codeDesc}</td>
						</tr>
					</c:forEach>
			    </c:otherwise> 
			</c:choose>
		</tbody>
	</table>
</form>
</div>
<!--//blockDetail End-->	


<!--blockButton Start-->
<div id="detailChildButton" class="blockButton">
	<c:if test="${not empty apCodeChild}">
	<ul>
		<li><a id="orderApCode"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='saveOrder'/></span></a></li>
		<li><a id="deleteApCode"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
	</ul>
	</c:if>
</div>
<!--//blockButton End-->
