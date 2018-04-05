<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage" value="message.workflow.admin.processAdministrationDetail" /> 
<c:set var="preHeader"  value="ui.workflow.admin.processAdministrationDetail" /> 
<c:set var="preSearch"  value="ui.workflow.admin.processAdministrationDetail.search" />
<c:set var="preButton"  value="ui.workflow.admin.processAdministrationDetail.button" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/workflow/style.css'/>" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
<script type="text/javascript" src='<c:url value="/base/js/jquery/jquery.validate-1.8.min.js"/>'></script>
<script type="text/javascript" src="<c:url value='/base/js/common.pack.js'/>"></script>

<script type="text/javascript">
	(function($){
		$(document).ready(function(){
			//프로세스
			var url = '<c:url value="/workflow/modeler/prism.do"/>?mode=model&processId=${listProcessDetail.processId}&version=${listProcessDetail.processVer}&scale=1&minimapView=false';
			$("#statisticsProcess").html('<iframe id="prism" src="' + url + '" frameborder="0" height="100%" width="100%;" scrolling="no"></iframe>');
			
			$("#aProcessDetailUpdate").click(function() {   
				$("#processAdministrationDetail").submit(); 
			});
			
			$("#processAdministrationDetail").validate({ 
				submitHandler: function(form) {
					  if(dateCheck() == "y"){
						  	$.post('<c:url value="/workflow/admin/updateProcessApplyDate.do"/>',{"processId":"${listProcessDetail.processId}","processVer":"${listProcessDetail.processVer}","applyStartDate":$("#start_date").val(),"applyEndDate":$("#end_date").val()})
							.success(function(data){
								if(data > 0){
									show_layer("<ikep4j:message pre='${preMessage}' key='alert.applyDateSuccess' />","return");
								}else{
									show_layer("<ikep4j:message pre='${preMessage}' key='applyDateFail' />","return");
								}
							})
						    .error(function() { alert("error"); })
						    .complete(function(){
						    });
					  }
	            },
	            rules : {
					start_date   : { required:true, date :true, minlength : 10  },
					end_date 	 : { required:true, date :true, minlength : 10 }
				},
				messages : {
					start_date   : {
			            required  : "<ikep4j:message pre='${preMessage}' key='alert.applyStartDateRequired' />",
			            date      : "<ikep4j:message pre='${preMessage}' key='alert.applyDateRight' />",
			            minlength : "<ikep4j:message pre='${preMessage}' key='alert.applyDateRight' />"
			        },
			        end_date : {
			            required  : "<ikep4j:message pre='${preMessage}' key='alert.applyEndDateRequired' />",
			            date      : "<ikep4j:message pre='${preMessage}' key='alert.applyDateRight' />",
			            minlength : "<ikep4j:message pre='${preMessage}' key='alert.applyDateRight' />"
			        }
				}
			});
			
			$("#start_date,#end_date").datepicker({
				showOn: "button",     
				buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",     
				buttonImageOnly: true,
				buttonText : "<ikep4j:message pre='${preHeader}' key='calendar' />"
			});
		});
		
		function dateCheck(){
			var start_date = $("#start_date").val().replace(/\D/g,"/");
			var end_date = $("#end_date").val().replace(/\D/g,"/");;
			
			var dStart_date = new Date(start_date);
			var dEnd_date = new Date(end_date);

			if (dStart_date > dEnd_date){
				alert("<ikep4j:message pre='${preMessage}' key='alert.applyEndDateMiss' />");
				$("#end_date").focus();
				return "n";
			}
			return dateValidation(start_date,end_date);
		}
		
		function dateValidation(start_date,end_date){
			var arrDayStartDate = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
			var arrDayEndDate = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
			var arrStartValue = start_date.split('/');
			var arrEndValue = end_date.split('/');
			if (parseInt(arrStartValue[0]) % 4 == 0 || parseInt(arrStartValue[0]) % 100 == 0 || parseInt(arrStartValue[0]) % 400 == 0) arrDayStartDate[1] = 29;
			if (parseInt(arrEndValue[0]) % 4 == 0 || parseInt(arrEndValue[0]) % 100 == 0 || parseInt(arrEndValue[0]) % 400 == 0) arrDayEndDate[1] = 29;
			
			if (parseInt(arrStartValue[1]) < 1 || parseInt(arrStartValue[1]) > 12) {
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.applyStartDateMiss' />","return");
				$("#start_date").focus();
				return "n";
			}else if (parseInt(arrStartValue[2]) < 1 || parseInt(arrStartValue[2]) > arrDayStartDate[parseInt(arrStartValue[1])-1]){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.applyStartDateMiss' />","return");
				$("#start").focus();
				return "n";
			}
			
			if (parseInt(arrEndValue[1]) < 1 || parseInt(arrEndValue[1]) > 12) { 
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.applyEndDateMiss' />","return");
				$("#end").focus();
				return "n";
			}else if (parseInt(arrDayEndDate[2]) < 1 || parseInt(arrDayEndDate[2]) > arrDayEndDate[parseInt(arrEndValue[1])-1]){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.applyEndDateMiss' />","return");
				$("#end_date").focus();
				return "n";
			}
			return "y";
		}
		
		//=========================================================================
		//* Alert 및 Confirm 팝업 
		//=========================================================================
		show_layer = function(alertName,returnValue){
			$("#tdAlertName").html(alertName);
			if(returnValue == "return"){
				$("#divAlertReturn").css("display","inline");
				$("#divAlertInfo").css("display","none");
				$("#divConfirm").css("display","none");
			}else if(returnValue == "info"){
				$("#divAlertReturn").css("display","none");
				$("#divAlertInfo").css("display","inline");
				$("#divConfirm").css("display","none");
			}else{
				$("#divAlertReturn").css("display","none");
				$("#divAlertInfo").css("display","none");
				$("#divConfirm").css("display","inline");
			}
			$("#layer_p").dialog({width: 300, height:150, modal:true, resizable: false, draggable : false, open:function(event,ui){ $('body').css('overflow','hidden');},close:function(event,ui){$('body').css('overflow','');}});
			$("#layer_p").dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
			$("#layer_p").dialog("widget").children(":first").addClass("ui-widget-workflow-header");
		}
		
		//=========================================================================
		//* 팝업창에서 확인 및 취소 버튼 클릭시 이벤트 
		//=========================================================================
		fnDialogType = function(type,flag){
			if(type == "close"){
				$("#layer_p").dialog("close");
				if(flag == "info"){
					$("#processSearchBoardItemButton").click();
				}
			}else{
				$("#layer_p").dialog("close");
				stateChange();
			}
		}
	})(jQuery);
</script>

<!--mainContents Start-->
<h1 class="none">processAdministrationDetail</h1>


<!--blockSearch Start-->
<form id="processAdministrationDetail" name="processAdministrationDetail" method="post" action="<c:url value='/workflow/workplace/statistics/procStatistics.do' />">
<div style="overflow:scroll;height:465px;">
<div id="statisticsProcess" class="usage_graph" style="width:99%;height:350px;"></div>		
<div class="clear"></div>
<div class="blockBlank_20px"></div>				
<!--blockLeft Start-->
<div class="blockLeft" style="width:99%;height:200px;margin-left:5px;">
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline2">
		<h3><ikep4j:message pre='${preHeader}' key='basicInfo'/></h3>
	</div>
	<!--//subTitle_2 End-->
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="25%"><ikep4j:message pre='${preHeader}' key='processId'/></th>
					<td>${listProcessDetail.processId}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='processName'/></th>
					<td>${listProcessDetail.processName}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='writer'/></th>
					<td>${listProcessDetail.author}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='description'/></th>
					<td>${listProcessDetail.description}</td>
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='processApplyDay'/></th>
					<td>
						<input type="text" id="start_date" name="end_date" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='processApplyStartDate'/>" value="${listProcessDetail.applyStartDate}" size="10">
						~
						<input type="text" id="end_date" name="end_date" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='processApplyEndDate'/>" value="${listProcessDetail.applyEndDate}" size="10">
						<a id="aProcessDetailUpdate" class="button_s" href="#" style="vertical-align:text-top;"><span><ikep4j:message pre='${preButton}' key='processApplyUpdate'/></span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	
</div>
<!--blockLeft End-->
</div>
</form>		
	
<div class="none ui-dialog1" id="layer_p" title="<ikep4j:message pre='${preHeader}' key='alert'/>" style="overflow:hidden;">
	<!--blockDetail Start-->
	<div class="blockDetail_2" style="width:255px">
		<table summary="<ikep4j:message pre='${preHeader}' key='alert'/>">
			<caption></caption>
			<tbody>
				<tr>
					<td class="textCenter">
						<span id="tdAlertName" class="textCenter"></span>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//blockDetail End-->
	<!--blockButton Start-->
	<div id="divAlertReturn" class="blockButton" style="padding-left:120px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('close','return');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
	</div>
	<div id="divAlertInfo" class="blockButton" style="padding-left:120px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('close','info');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
	</div>
	<div id="divConfirm" class="blockButton" style="padding-left:110px;display:none;"> 
		<a class="button_s" href="#" onclick="fnDialogType('open');" title="<ikep4j:message pre='${preButton}' key='confirm'/>">
			<span><ikep4j:message pre='${preButton}' key='confirm'/></span>
		</a>
		<a class="button_s" href="#" onclick="fnDialogType('close','return');" title="<ikep4j:message pre='${preButton}' key='cancel'/>">
			<span><ikep4j:message pre='${preButton}' key='cancel'/></span>
		</a>
	</div>
</div>