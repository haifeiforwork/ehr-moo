<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMessage" value="message.workflow.admin.processAdministration" />
<c:set var="preCommonMessage" value="message.workflow.admin.common" />
<c:set var="preHeader"  value="ui.workflow.admin.processAdministration" /> 
<c:set var="preSearch"  value="ui.workflow.admin.processAdministration.search" />
<c:set var="preButton"  value="ui.workflow.admin.processAdministration.button" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/workflow/style.css'/>" />

<script type="text/javascript">
(function($){
	
	//=========================================================================
	//* OnLoad 함수
	//=========================================================================
	$(document).ready(function() {
		
		//=========================================================================
		//* 한페이지에 보여줄 건수 설정 
		//=========================================================================
		$("#pagePerRecord > option[value=${processSearchCondition.pagePerRecord}]").attr("selected",true);
		//=========================================================================
		//* 프로세스 상태(검색조건) 
		//=========================================================================
		$("#processAdministrationSearchState > option[value=${processSearchCondition.processAdministrationSearchState}]").attr("selected",true);
		//=========================================================================
		//* 콤보박스 값 설정
		//=========================================================================
		$("#processAdministrationSearchPartition > option[value=${processSearchCondition.processAdministrationSearchPartition}]").attr("selected",true);
		searchOnChange("${processSearchCondition.processAdministrationSearchPartition}");
		//=========================================================================
		//* 검색 및 페이지 클릭이벤트
		//=========================================================================
		$("#processSearchBoardItemButton").click(function() {
			goSubmit();
		}); 
		
		//=========================================================================
		//* 페이지 건수 변경시 이벤트
		//=========================================================================
		$("#pagePerRecord").change(function() {
			$("#pageIndex").val("1");
			$("#processSearchBoardItemButton").click();
		});
		
		//=========================================================================
		//* 체크박스 전체 선택시 이벤트
		//=========================================================================
		$("#checkboxAllProcess").click(function() { 
			$("input[name=checkboxProcess]").attr("checked", $(this).is(":checked"));  
		}); 
		
		//=========================================================================
		// * 마우스 오버/아웃시 CSS적용하기 위한 함수
		//=========================================================================
		$("#tbody_processList > tr").hover(
			function(){
				if($(this).find("input[name=checkboxProcess]").val().length > 0){
					$(this).addClass("bgSelected").css("cursor","pointer");
				}
			},
			function(){
				if($(this).find("input[name=checkboxProcess]").val().length > 0){
					$(this).removeClass("bgSelected").css("cursor","pointer");
				}
			}
		);
		
		//=========================================================================
		// * 프로세스 상태값 변경
		//=========================================================================
		$("#aProcessAdministrationStateChange").click(function(){
			var processId = "";
			var processState = $("#processAdministrationStateChange option:selected").val();
			$("input[name=checkboxProcess]:checked").each(function(i){
				if(i==0){
					processId += $(this).attr("value");	
				}else{
					processId += ":"+$(this).attr("value");
				}
			});
			
			if(processId.length <= 0){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.processChecked' />","return");
				return;
			}else if(processState == "state"){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.processStateChecked' />","return");
				return;
			}else{
				show_layer("<ikep4j:message pre='${preMessage}' key='confirm.processStateChange' />","confirm");
			}
		});
		
		
		goSubmit  = function(){
			var processAdministrationForm = document.getElementById("processAdministrationForm");
			processAdministrationForm.action = "<c:url value='/workflow/admin/processAdministration.do'/>";
			processAdministrationForm.submit();	
		}
		
		stateChange = function(){
			var processId = "";
			var processState = $("#processAdministrationStateChange option:selected").val();
			$("input[name=checkboxProcess]:checked").each(function(i){
				if(i==0){
					processId += $(this).attr("value");	
				}else{
					processId += ":"+$(this).attr("value");
				}
				
			});
			$.post('<c:url value="updateProcessState.do"/>',{"processId":processId,"processState":processState})
			.success(function(data){
				$("input[name=dataStateCount]").val(data);
			})
		    .error(function() { alert("error"); })
		    .complete(function(){
		    	if($("input[name=dataStateCount]").attr("value").length > 0){
					show_layer($("input[name=dataStateCount]").attr("value")+"<ikep4j:message pre='${preCommonMessage}' key='alert.processUpdateCount' />","info");
				}else{
					show_layer("<ikep4j:message pre='${preMessage}' key='alert.processUpdateFail' />","return");
				}
		    });
		}
		
		$("#processAdministrationSearchStartDate,#processAdministrationSearchEndDate").datepicker({
			showOn: "button",     
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",     
			buttonImageOnly: true,
			buttonText : "<ikep4j:message pre='${preHeader}' key='calendar' />",
		 	onSelect: function(date, event) {
		 		var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        
    	        if($(this).attr("id") == "processAdministrationSearchStartDate" && $("#processAdministrationSearchEndDate").attr("value").length > 0){
    	        	if((Number($("#processAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#processAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	        		$("#processAdministrationSearchStartDate").val("");
    	        		$("#processAdministrationSearchDateFlag").val("end");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	        	}
    	        }
    	        
    	        if($(this).attr("id") == "processAdministrationSearchEndDate"){
    	    		if((Number($("#processAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#processAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	    			$("#processAdministrationSearchEndDate").val("");
    	    			$("#processAdministrationSearchDateFlag").val("start");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	    		}
    	        }
    	        
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	        
    	        
    	        if($("#processAdministrationSearchStartDate").attr("value").length > 0 && $("#processAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#processAdministrationSearchDateFlag").val("start");
    	        }else if($("#processAdministrationSearchStartDate").attr("value").length > 0){
    	        	$("#processAdministrationSearchDateFlag").val("start");
    	        }else if($("#processAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#processAdministrationSearchDateFlag").val("end");
    	        }
		 	}
		}); 
	});
	
	//=========================================================================
	//* 프로세스 모델러 호출
	//=========================================================================
	fnModeler = function(partitionId,processId,processVer,processName){
		var dialog = new iKEP.Dialog({
			title: processName+"("+processVer+")",
			url: "<c:url value='/workflow/admin/processAdministrationDetail.do?partitionId="+partitionId+"&processId="+processId+"&processVer="+processVer+"'/>",
			modal: true,
			width: 700,
			height:500,
			resizable: false,
			open : function(event,ui){
						$(this).dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
						$(this).dialog("widget").children(":first").addClass("ui-widget-workflow-header");
		    },
		    scroll : "no",
		    isOpen : function(event,ui){alert(1)}
		});
	}
	
	//=========================================================================
	//* 콤보박스 변경 이벤트 발생시...
	//=========================================================================
	searchOnChange = function(thisValues){
		if(thisValues != "all"){
			$("#processAdministrationSearchProcess").css("display","inline");	
		}else{
			$("#processAdministrationSearchProcess").css("display","none");
		}
		//기존값 제거
		$("#processAdministrationSearchProcess option").remove(); 
		//AJAX 호출
		$.post('<c:url value="listComboProcess.do"/>',{partitionId : thisValues}) 
			.success(function(data){ 
				for(var i=0; i<data.length; i++){
					$("#processAdministrationSearchProcess").get(0).options[i] = new Option(data[i].PROCESS_NAME == "ALL" ? "<ikep4j:message pre='${preHeader}' key='processName' />" : data[i].PROCESS_NAME ,data[i].PROCESS_ID);
				}
				$("#processAdministrationSearchProcess > option[value=${processSearchCondition.processAdministrationSearchProcess}]").attr("selected",true);
			})
		    .error(function() { alert("error"); }
		);
	}
	
	//=========================================================================
	//* ProcessList 정렬 이벤트
	//=========================================================================
	sortProcess = function(sortColumn, sortType){
		$("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$("input[name=sortType]").val('DESC');	
		} else {
			$("input[name=sortType]").val('ASC');
		}
		
		$("#processSearchBoardItemButton").click();
	};
	
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
<!--blockSearch Start-->
<form id="processAdministrationForm" method="post" onsubmit="return false;">
<input name="dataStateCount" type="hidden" value=""/>
<spring:bind path="processSearchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}"  />
</spring:bind> 		
<spring:bind path="processSearchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}"  />
</spring:bind>
<spring:bind path="processSearchCondition.processAdministrationSearchDateFlag">
<input type="hidden" name="${status.expression}" id="${status.expression}"  value="${status.value}">
</spring:bind>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='processManagement'/></h1>
				
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='processManagement'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${preHeader}' key='workflow'/></li>
			<li><ikep4j:message pre='${preHeader}' key='current'/></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='processManagement'/></li>
		</ul>
	</div>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<table summary="<ikep4j:message pre='${preHeader}' key='searchCondition'/>">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='partition'/>/<ikep4j:message pre='${preHeader}' key='processName'/></th>
					<td width="45%">		
						<spring:bind path="processSearchCondition.processAdministrationSearchPartition">
						<select title="<ikep4j:message pre='${preHeader}' key='partitionName'/>" name="${status.expression}" id="${status.expression}" onchange="searchOnChange(this.value)" style="width:100px">
							<option value="all"><ikep4j:message pre='${preHeader}' key='partitionName' /></option>
							<c:forEach var="listComboPartition" items="${listComboPartition}">
							<option value="${listComboPartition.partitionId}">${listComboPartition.partitionName}</option>
							</c:forEach>
						</select>
						</spring:bind>
						<spring:bind path="processSearchCondition.processAdministrationSearchProcess">
						<select title="<ikep4j:message pre='${preHeader}' key='processName'/>" name="${status.expression}" id="${status.expression}" style="width:130px">
						</select>
						</spring:bind>	
					</td>
					<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='processStartDate'/></th>
					<td width="45%">								
						<div class="subInfo">
							<spring:bind path="processSearchCondition.processAdministrationSearchStartDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='startDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
							~
							<spring:bind path="processSearchCondition.processAdministrationSearchEndDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='endDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
						</div>
					</td>									
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='processState'/></th>
					<td colspan="3">
						<spring:bind path="processSearchCondition.processAdministrationSearchState">
						<select title="<ikep4j:message pre='${preHeader}' key='processState'/>" name="${status.expression}" id="${status.expression}">
								<option value="all"><ikep4j:message pre='${preHeader}' key='processState' /></option>
								<option value="ACTIVE"><ikep4j:message pre='${preHeader}' key='processActive'/></option>
								<option value="INACTIVE"><ikep4j:message pre='${preHeader}' key='processInactive'/></option>
						</select>
						</spring:bind>
					</td>
				</tr>
				</tbody>
		</table>
		<div class="searchBtn">
			<a id="processSearchBoardItemButton" name="processSearchBoardItemButton" href="#a" title="<ikep4j:message pre='${preHeader}' key='button.search'/>"><span><ikep4j:message pre='${preHeader}' key='button.search'/></span></a>
		</div>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

<div class="blockShuttle" >
	<div class="sbox">
		<c:if test="${!processSearchResult.emptyRecord}">
		<div class="listInfo">  
			<spring:bind path="processSearchCondition.pagePerRecord">  
				<select name="${status.expression}" id="${status.expression}" title='<ikep4j:message pre='${preHeader}' key='pageCount'/>'>
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
					<option value="50">50</option>
				</select> 
			</spring:bind>
			<div class="totalNum">${processSearchResult.pageIndex}/ ${processSearchResult.pageCount} <ikep4j:message pre='${preHeader}' key='page'/> ( <ikep4j:message pre='${preHeader}' key='totalCount'/><span> ${processSearchResult.recordCount}</span>)</div>
			<div align="right">
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${preHeader}' key='processActive'/>" /><ikep4j:message pre='${preHeader}' key='processActive'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${preHeader}' key='processInactive'/>" /><ikep4j:message pre='${preHeader}' key='processInactive'/>
			</div>
		</div>
		<div class="clear"></div>
		</c:if>
		<div class="blockListTable" height="50%">
			<table summary="<ikep4j:message pre='${preHeader}' key='processManagement' />" style="font-size:13; table-layout:fixed;">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%"><input id="checkboxAllProcess" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
						<th scope="col" width="20%">
							<a onclick="sortProcess('PARTITION_NAME', '<c:if test="${processSearchCondition.sortColumn eq 'PARTITION_NAME'}">${processSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='partitionName' />
							</a>
						</th>
						<th scope="col" width="20%">
							<a onclick="sortProcess('PROCESS_NAME', '<c:if test="${processSearchCondition.sortColumn eq 'PROCESS_NAME'}">${processSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='processName' />
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('PROCESS_VER', '<c:if test="${processSearchCondition.sortColumn eq 'PROCESS_VER'}">${processSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='processVersion' />
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('PROCESS_STATE', '<c:if test="${processSearchCondition.sortColumn eq 'PROCESS_STATE'}">${processSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='processState' />
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('DEPLOY_DATE', '<c:if test="${processSearchCondition.sortColumn eq 'DEPLOY_DATE'}">${processSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='startDate' />
							</a>
						</th>
						<th scope="col" width="5%">
						</th>
					</tr>
				</thead>
				<c:choose>
				<c:when test="${processSearchResult.emptyRecord}">
				<tbody>
					<tr>
						<td colspan="7" height="10px"><ikep4j:message pre='${preCommonMessage}' key='dataNotFound' /></td>
					</tr>
					<c:forEach begin="${fn:length(processSearchResult.entity)}" end="${processSearchCondition.pagePerRecord-2}" step="1" >
					<tr>
						<td colspan="7">&nbsp;</td>
					</tr>
					</c:forEach>	
				</tbody>
				</c:when>
				<c:otherwise>
				<tbody id="tbody_processList">
					<c:forEach var="processList" items="${processSearchResult.entity}" varStatus="status">
					<tr>
						<td width="5%"><input name="checkboxProcess" class="checkbox" title="checkbox" type="checkbox" value="${processList.processId}" /></td>
						<td width="20%"><div class="ellipsis">${processList.partitionName}</div></td>					
						<td width="20%" id="td_processId" class="textLeft"><div class="ellipsis">${processList.processName}</div></td>
						<td width="10%">${processList.processVer}</td>
						<td width="10%">
							<c:choose>
									<c:when test="${processList.processState == 'ACTIVE'}">
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${preHeader}' key='processActive' />" />
									</c:when>
									<c:otherwise>
										<img src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${preHeader}' key='processInactive' />" />
									</c:otherwise>
							</c:choose>
						</td>
						<td width="10%"><div class="ellipsis"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${processList.deployDate}"/></div></td>
						<td width="5%"><a class="button_s" onclick="fnModeler('${processList.partitionId}','${processList.processId}','${processList.processVer}','${processList.processName}')" title="<ikep4j:message pre='${preHeader}' key='modeler' />"><span><ikep4j:message pre='${preHeader}' key='modeler' /></span></a></td>
					</tr>
					</c:forEach>
				</tbody>
				<tbody>
					<c:forEach begin="${fn:length(processSearchResult.entity)}" end="${processSearchCondition.pagePerRecord-1}" step="1" >
					<tr>
						<td colspan="7">&nbsp;</td>
					</tr>
					</c:forEach>
				</tbody>
				</c:otherwise>
				</c:choose>
			</table>
		</div>
		<!--Page Numbur Start--> 
		<spring:bind path="processSearchCondition.pageIndex">
		<ikep4j:pagination searchFormId="processAdministrationForm" ajaxEventFunctionName="goSubmit" pageIndexInput="${status.expression}" searchCondition="${processSearchCondition}" />
		<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preHeader}' key='currentPageIndex'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->
		<div class="tableTop" align="right">
			<select title="<ikep4j:message pre='${preHeader}' key='processState'/>" name="processAdministrationStateChange" id="processAdministrationStateChange">
				<option value="state"><ikep4j:message pre='${preHeader}' key='processState' /></option>
				<option value="ACTIVE"><ikep4j:message pre='${preHeader}' key='processActive'/></option>
				<option value="INACTIVE"><ikep4j:message pre='${preHeader}' key='processInactive'/></option>
			</select>
			<a class="button_s" href="#" id="aProcessAdministrationStateChange" title="<ikep4j:message pre='${preButton}' key='stateChange'/>"><span><ikep4j:message pre='${preButton}' key='stateChange'/></span></a>
		</div>
	</div>
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