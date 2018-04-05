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
	
	(function($){
		
		/**
		 * 부모창에서 넘어온값
		 */
		var selectData;
		/**
		 * 부모객체
		 */
		var dialogWindow;
		
		/**
		 * 검색되거나 선택된 값이 있을경우 실행되는 콜백 메소드
		 * result: 검색되거나 선택된 값을 배열형태로 리턴함
		 * 
		 * @param {Object} data
		 */
     	setAddress = function(data) {
			var name;
			var userId;
			
			$.each(data, function() {
				name 	= $.trim(this.name);
				userId 	= $.trim(this.id);
			})
			
			$("#mandatorName").val(name);
			$("#mandatorId").val(userId);
		};
		
		$(document).ready(function(){
			
			/**
			 * 부모창 전달값 처리.
			 * 
			 * @param {Object} params
			 * @param {Object} dialog
			 */
			fnCaller = function (params, dialog) {
				
				$instanceLogId = $("input[name=instanceLogId]:hidden");
				
				if(params) {
					//alert(params.id);
					
					selectData = {
						id		: params.id,
						target	: params.target
					};
					
					$instanceLogId.val(params.id);
				}
				
				dialogWindow 	= dialog;
			};
			
			/**
			 * 조직도 팝업 / 주소록 버튼에 이벤트 바인딩
			 */
            $('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
			
			$("#saveDelegateButton").click(function() {   
				$("#unitDelegateForm").submit(); 
			});
			
			/**
			 * 입력값 확인
			 * @param {Object} form
			 */
			$("#unitDelegateForm").validate({ 
				submitHandler	: function(form) {
	                if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
	                  
					$.post('<c:url value="/wfapproval/work/delegate/ajaxApplyUnitDelegate.do"/>', $("#unitDelegateForm").serialize()).success(function(data){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveSuccess'/>");
						
						if(selectData.target == "approval"){
							parent.location.href = "<c:url value='/wfapproval/work/aplist/listApTodo.do'/>";
						}else{							
							parent.location.href = "<c:url value='/workflow/workplace/worklist/myWorkList.do'/>?persistence=Y";
						}
						
	                }).error(function(event, request, settings){
	                    alert("<ikep4j:message pre='${preMessage}' key='saveError'/>");
	                });
					
					return true;
	            },
	            rules			: {
	            		mandatorName 	: { required: true },
	            		reasonComment	: { required: true, minlength: 2, maxlength: 60 }
	            },
	            messages		: {
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

<form id="unitDelegateForm" name="unitDelegateForm" modelAttribute="delegateBean" method="post">

	<spring:bind path="delegateBean.instanceLogId">
		<input type="hidden" name="${status.expression}" value="${status.value}"/>
	</spring:bind>
	
	<div class="clear"></div>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="delegateBean.mandatorName">
					<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" value="${status.value}" size="12" readonly="readonly"/>
						<a id="addrBtn" href="#" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preForm}' key='organization'/></span>
						</a>
					</td>
					</spring:bind>
					<spring:bind path="delegateBean.mandatorId">
						<input type="hidden" id="${status.expression}" name="${status.expression}" value="${status.value}"/>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="delegateBean.reasonComment">
					<th scope="row"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" class="tabletext" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" cols="300" rows="7">${status.value}</textarea>
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
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->