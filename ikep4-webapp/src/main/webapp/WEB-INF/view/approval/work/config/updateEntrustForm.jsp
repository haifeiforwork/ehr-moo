<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.entrust.form.header" />
<c:set var="preForm"  	value="ui.approval.work.entrust.form" />
<c:set var="preValidation" value="ui.approval.work.entrust.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
<c:set var="preIcon"  	value="ui.approval.common.icon" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
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
			
			$instanceLogId	= $("input[name=userId]:hidden");
			
			viewApFormProcessDialog = new iKEP.Dialog({     
				title 		: "<ikep4j:message pre='${preForm}' key='subTitle'/>",
				url 		: "updateUnitEntrustForm.do?userId="+$instanceLogId.val(),
				modal 		: true,
				width 		: 650,
				height 		: 200,
				params 		: {userId:$instanceLogId.val()}
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
							
						$("#signUserName").val(name);
						$("#signUserId").val(userId);
					};
		
		setUser = function(data) {
			var name="";
			var userId="";
			
			$jq.each(data, function() {
				name = $jq.trim(this.userName);
				userId = $jq.trim(this.id);	
			})
			$jq("#signUserName").val(name);
			$jq("#signUserId").val(userId);
		};
		
		//입력값 확인
		var validOptions = { 
			submitHandler	: function(form) {
                  if (!confirm("<ikep4j:message pre='${preMessage}' key='save'/>")) return false;
                  form.submit();
                  return true;
            },
            rules			: {
            		startDateStr	: { required: true, lesserThan: "#endDateStr" },
            		endDateStr		: { required: true, greaterThan: "#startDateStr" },
            		signUserId 	: { required: true }
            },
            messages		: {
	            		startDateStr	: { 	
	            			direction 		: "bottom",
							required		: "<ikep4j:message pre='${preValidation}' key='noselect.startdate'/>", 
            				lesserThan		: "<ikep4j:message pre='${preValidation}' key='fault.selectdate'/>" 
						},
	            		endDateStr		: { 		
	            			direction 		: "bottom",
							required		: "<ikep4j:message pre='${preValidation}' key='noselect.enddate'/>",
    						greaterThan		: "<ikep4j:message pre='${preValidation}' key='fault.selectdate'/>"  
						},
						signUserId	: { 
							direction 		: "bottom",
							required		: "<ikep4j:message pre='${preValidation}' key='noselect.mandator'/>" 
						}
            }
		}; 
		
		deleteEntrust = function() { 
			
			var $sel = $jq("input[name=entrustIds]:checked");
			if($sel.length <1) {
				alert('<ikep4j:message pre='${preMessage}' key='select' />');
				return;	
			}
			
			if(!confirm('<ikep4j:message pre='${preMessage}' key='release' />')) {
				return;
			}
				  
			$jq.ajax({     
				url : '<c:url value="/approval/work/config/deleteEntrust.do" />',     
				data :  $jq("#historyForm").serialize(),     
				type : "post",     
				success : function(result) {         
					//getList();
					document.location.href = "<c:url value='/approval/work/config/updateEntrustForm.do'/>";
				},
				error : function(event, request, settings){
					 alert("error");
				}
			});
		};
		
		getHistoryList = function() {
			
			$jq.ajax({     
				url : "<c:url value='/approval/work/config/updateEntrustList.do'/>",     
				data :  $jq("#historyForm").serialize(),     
				type : "post",     
				success : function(result) {         
					$jq("#historyListDiv").html(result);
				} 
			});       
			
		};
		
		$(document).ready(function(){
			
			new iKEP.Validator("#myForm", validOptions);
			
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
			
			$("#deleteDelegateButton").click(function() {   
				deleteEntrust();
			});
			
			//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
	        $jq('#signUserName').keypress( function(event) {
	            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
	            if($jq('#signUserName').val() == "") {
	            	alert('<ikep4j:message pre='${preMessage}' key='signUser' />');
					return;	
	            }
				iKEP.searchUser(event, "N", setUser);			
			});
			
		    //검색버튼을 클릭했을때 이벤트 바인딩
			$jq('#addrSearchBtn').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				iKEP.searchUser(event, "N", setUser);
			});
		    
			$jq('#btnDeleteControl').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#signUserName').val('');
				$jq('#signUserId').val('');
			});
			
			getHistoryList();
		    
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

<form id="myForm" name="myForm" method="post" action="<c:url value='/approval/work/config/entrustSetSave.do'/>">

<spring:bind path="apprEntrust.userId">
	<input type="hidden" name="${status.expression}" value="${apprEntrust.userId}"/>
</spring:bind>

	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<%-- <tr>
					<spring:bind path="apprEntrust.usage">
					<th scope="row" width="18%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<select title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" 
								name="${status.expression}">
							<c:forEach var="code" items="${usageList}">
								<option value="${code.key}" <c:if test="${code.key eq apprEntrust.usage}">selected="selected"</c:if>><spring:message code="${code.value}" /></option>
							</c:forEach> 
						</select>
					</td>
					</spring:bind>
				</tr> --%>
				
				<tr>
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='delegateperiod'/></th>
					<td>
						<spring:bind path="apprEntrust.startDateStr">											
						<input 	type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" 
								title="<ikep4j:message pre='${preForm}' key='startDate'/>" 
								value="<ikep4j:timezone date="${apprEntrust.startDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> 
								<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preIcon}' key='calendar'/>" /> ~
						</spring:bind>
						<spring:bind path="apprEntrust.endDateStr">
						<input 	type="text" class="inputbox" style="width:63px;" id="${status.expression}" name="${status.expression}" 
								title="<ikep4j:message pre='${preForm}' key='endDate'/>" 
								value="<ikep4j:timezone date="${apprEntrust.endDate}" pattern="yyyy.MM.dd"/>" size="8" readonly="readonly"/> 
								<img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="<ikep4j:message pre='${preIcon}' key='calendar'/>" />
						</spring:bind>
					</td>
				</tr>
				
				<tr>
					<spring:bind path="apprEntrust.signUserName">
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<input type="hidden" id="signUserId" name="signUserId" value="${apprEntrust.signUserId}"/>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" value="${apprEntrust.signUserName}" size="12" />
						<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
							<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />Search</span>
						</a>&nbsp;
						<a id="addrBtn" href="#a" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preForm}' key='organization'/></span>
						</a>&nbsp;
						<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
							<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />Delete</span>
						</a>
					</td>
					</spring:bind>
				</tr>
				
				<tr>
					<spring:bind path="apprEntrust.reason">
					<th scope="row" width="10%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" value="${apprEntrust.reason}" size="50" />
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
			<li><a id="deleteDelegateButton"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='release'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

	<div class="clear"></div>
	
	
	<div id="historyListDiv"></div>
	
	
	<div class="clear"></div>

<!--//mainContents End-->