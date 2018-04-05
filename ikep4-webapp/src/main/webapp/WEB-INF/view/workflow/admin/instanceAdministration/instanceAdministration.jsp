<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preMessage" value="message.workflow.admin.instanceAdministration" />
<c:set var="preCommonMessage" value="message.workflow.admin.common" /> 
<c:set var="preHeader"  value="ui.workflow.admin.instanceAdministration" /> 
<c:set var="preSearch"  value="ui.workflow.admin.instanceAdministration.search" />
<c:set var="preButton"  value="ui.workflow.admin.instanceAdministration.button" />

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
		$("#pagePerRecord > option[value=${instanceSearchCondition.pagePerRecord}]").attr("selected",true);
		//=========================================================================
		//* 인스턴스 상태(검색조건) 
		//=========================================================================
		$("#instanceAdministrationSearchState > option[value=${instanceSearchCondition.instanceAdministrationSearchState}]").attr("selected",true);
		//=========================================================================
		//* 콤보박스 값 설정
		//=========================================================================
		$("#instanceAdministrationSearchPartition > option[value=${instanceSearchCondition.instanceAdministrationSearchPartition}]").attr("selected",true);
		searchOnChange("${instanceSearchCondition.instanceAdministrationSearchPartition}");
		//=========================================================================
		//* 검색 및 페이지 클릭이벤트
		//=========================================================================
		$("#instanceSearchBoardItemButton").click(function() {
			$("#pageIndex").val("1");
			goSubmit();
		}); 
		
		//=========================================================================
		//* 페이지 건수 변경시 이벤트
		//=========================================================================
		$("#pagePerRecord").change(function() {
			$("#instanceSearchBoardItemButton").click();
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
		$("#tbody_instanceList > tr").hover(
			function(){$(this).addClass("bgSelected").css("cursor","pointer");},
			function(){$(this).removeClass("bgSelected").css("cursor","pointer");}
		);
		
		//=========================================================================
		// * 프로세스중에 한건을 클릭시
		//=========================================================================
		$("#tbody_instanceList > tr > td").click(function(){
			$("input[name=instanceId]").val($(this).parent().find("#td_instanceId").html());
			var parameters = $("#instanceAdministrationActivityForm").serialize();
			$("#instanceAdministrationActivityForm").submit(); 
		});
		
		//=========================================================================
		// * 프로세스 상태값 변경
		//=========================================================================
		$("#ainstanceAdministrationStateChange").click(function(){
			
			var instanceId = "";
			var instanceState = $("#instanceAdministrationStateChange option:selected").val();
			$("input[name=checkboxProcess]:checked").each(function(i){
				if(i==0){
					instanceId += $(this).attr("value");	
				}else{
					instanceId += ":"+$(this).attr("value");
				}
			});
			
			if(instanceId.length <= 0){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.instanceChecked'/>","return");
				return;
			}else if(instanceState == "state"){
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.instanceStateChecked'/>","return");
				return;
			}else{
				show_layer("<ikep4j:message pre='${preMessage}' key='confirm.instanceStateChange'/>","confirm");
			}	
		});
		
		//=========================================================================
		// 검색조건(달력)
		//=========================================================================
		$("#instanceAdministrationSearchStartDate,#instanceAdministrationSearchEndDate").datepicker({
			showOn: "button",     
			buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",     
			buttonImageOnly: true,
			buttonText : "<ikep4j:message pre='${preHeader}' key='calendar' />",
		 	onSelect: function(date, event) {
		 		var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        
    	        if($(this).attr("id") == "instanceAdministrationSearchStartDate" && $("#instanceAdministrationSearchEndDate").attr("value").length > 0){
    	        	if((Number($("#instanceAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#instanceAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	        		$("#instanceAdministrationSearchStartDate").val("");
    	        		$("#instanceAdministrationSearchDateFlag").val("end");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	        	}
    	        }
    	        
    	        if($(this).attr("id") == "instanceAdministrationSearchEndDate"){
    	    		if((Number($("#instanceAdministrationSearchEndDate").attr("value").replace(/\./g,""))) - (Number($("#instanceAdministrationSearchStartDate").attr("value").replace(/\./g,""))) < 0 ){
    	    			$("#instanceAdministrationSearchEndDate").val("");
    	    			$("#instanceAdministrationSearchDateFlag").val("start");
    	    			show_layer("<ikep4j:message pre='${preCommonMessage}' key='alert.searchDayCondition' />","return");
    	    			return false;
    	    		}
    	        }
    	        
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	        
    	        
    	        if($("#instanceAdministrationSearchStartDate").attr("value").length > 0 && $("#instanceAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#instanceAdministrationSearchDateFlag").val("start");
    	        }else if($("#instanceAdministrationSearchStartDate").attr("value").length > 0){
    	        	$("#instanceAdministrationSearchDateFlag").val("start");
    	        }else if($("#instanceAdministrationSearchEndDate").attr("value").length > 0){
    	        	$("#instanceAdministrationSearchDateFlag").val("end");
    	        }
		 	}
		}); 
	});
	
	//=========================================================================
	// * 프로세스 모니터링
	//=========================================================================
	fnModeler = function(partitionId,processId,processVer,instanceId,processName,instanceName){
		var dialog = new iKEP.Dialog({
			title: processName+"("+instanceName+")",
			url: "<c:url value='/workflow/modeler/prism.do?mode=runtime&instanceId="+instanceId+"&processType=workflow&partitionId="+partitionId+"&processId="+processId+"&version="+processVer+"&minimapView=false&saveView=false&refreshView=false'/>",
			modal: true,
			width: 700,
			height:500,
			resizable: false,
			open : function(event,ui){
						$(this).dialog("widget").addClass("ui-dialog1").addClass("ui-widget-content1");
						$(this).dialog("widget").children(":first").addClass("ui-widget-workflow-header");
		    },
		    scroll : "no"	   
		});
	}
	
	//=========================================================================
	// * 
	//=========================================================================
	goSubmit  = function(){
		var instanceAdministrationForm = document.getElementById("instanceAdministrationForm");
		instanceAdministrationForm.action = "<c:url value='/workflow/admin/instanceAdministration.do'/>";
		instanceAdministrationForm.submit();
	}
	
	stateChange = function(){
		var instanceId = "";
		var instanceState = $("#instanceAdministrationStateChange option:selected").val();
		$("input[name=checkboxProcess]:checked").each(function(i){
			if(i==0){
				instanceId += $(this).attr("value");	
			}else{
				instanceId += ":"+$(this).attr("value");
			}
		});
		$.post('<c:url value="updateInstanceState.do"/>',{"instanceId":instanceId,"instanceState":instanceState})
		.success(function(data){
			$("input[name=dataStateCount]").val(data);
		})
	    .error(function() { alert("error"); })
	    .complete(function(){
	    	if($("input[name=dataStateCount]").attr("value").length > 0){
				show_layer($("input[name=dataStateCount]").attr("value")+"<ikep4j:message pre='${preCommonMessage}' key='alert.processUpdateCount'/>","info");
			}else{
				show_layer("<ikep4j:message pre='${preMessage}' key='alert.instanceUpdateFail'/>","return");
			}
	    });
	}
	//=========================================================================
	//* 콤보박스 변경 이벤트 발생시...
	//=========================================================================
	searchOnChange = function(thisValues){
		if(thisValues != "all"){
			$("#instanceAdministrationSearchProcess").css("display","inline");	
		}else{
			$("#instanceAdministrationSearchProcess").css("display","none");
		}
		//기존값 제거
		$("#instanceAdministrationSearchProcess option").remove(); 
		//AJAX 호출
		$.post('<c:url value="listComboProcess.do"/>',{partitionId : thisValues}) 
			.success(function(data){ 
				for(var i=0; i<data.length; i++){
					$("#instanceAdministrationSearchProcess").get(0).options[i] = new Option(data[i].PROCESS_NAME == "ALL" ? "<ikep4j:message pre='${preHeader}' key='processName' />" : data[i].PROCESS_NAME,data[i].PROCESS_ID);
				}
				$("#instanceAdministrationSearchProcess > option[value=${instanceSearchCondition.instanceAdministrationSearchProcess}]").attr("selected",true);
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
		
		$("#instanceSearchBoardItemButton").click();
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
				var instanceState = $("#instanceAdministrationStateChange option:selected").val();
				$("#instanceAdministrationSearchState").val(instanceState).attr("selected", true);
				$("#instanceSearchBoardItemButton").click();
			}
		}else{
			$("#layer_p").dialog("close");
			stateChange();
		}
	}
})(jQuery);
</script>
<!--blockSearch Start-->
<form id="instanceAdministrationForm" method="post" onsubmit="return false;">
<input name="dataStateCount" type="hidden" value=""/>
<spring:bind path="instanceSearchCondition.sortColumn">
	<input name="${status.expression}" type="hidden" value="${status.value}" />
</spring:bind> 		
<spring:bind path="instanceSearchCondition.sortType">
	<input name="${status.expression}" type="hidden" value="${status.value}" />
</spring:bind>
<spring:bind path="instanceSearchCondition.instanceAdministrationSearchDateFlag">
<input type="hidden" name="${status.expression}" id="${status.expression}"  value="${status.value}">
</spring:bind>
<h1 class="none"><ikep4j:message pre='${preHeader}' key='instanceManagement'/></h1>
				
<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preHeader}' key='instanceManagement'/></h2>
	<div id="pageLocation">
		<ul>
			<li class="liFirst"><ikep4j:message pre='${preHeader}' key='workflow'/></li>
			<li><ikep4j:message pre='${preHeader}' key='current'/></li>
			<li class="liLast"><ikep4j:message pre='${preHeader}' key='instanceManagement'/></li>
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
						<spring:bind path="instanceSearchCondition.instanceAdministrationSearchPartition">
						<select name="${status.expression}" id="${status.expression}" onchange="searchOnChange(this.value)" style="width:100px" title="<ikep4j:message pre='${preHeader}' key='partition'/>">
							<option value="all"><ikep4j:message pre='${preHeader}' key='partition' /></option>
							<c:forEach var="listComboPartition" items="${listComboPartition}">
							<option value="${listComboPartition.partitionId}">${listComboPartition.partitionName}</option>
							</c:forEach>
						</select>
						</spring:bind>
						<spring:bind path="instanceSearchCondition.instanceAdministrationSearchProcess">
						<select name="${status.expression}" id="${status.expression}" style="width:130px" title="<ikep4j:message pre='${preHeader}' key='processName'/>">
						</select>
						</spring:bind>
					</td>
					<th scope="row" width="5%"><ikep4j:message pre='${preHeader}' key='instanceStartDate'/></th>
					<td width="45%">								
						<div class="subInfo">
							<spring:bind path="instanceSearchCondition.instanceAdministrationSearchStartDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='startDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
							~
							<spring:bind path="instanceSearchCondition.instanceAdministrationSearchEndDate">
							<input type="text" id="${status.expression}" name="${status.expression}" class="inputbox datepicker" title="<ikep4j:message pre='${preSearch}' key='endDate'/>" value="${status.value}" size="10" readonly>
							</spring:bind>
						</div>
					</td>									
				</tr>
				<tr>
					<th scope="row"><ikep4j:message pre='${preHeader}' key='instanceState'/></th>
					<td>
						<spring:bind path="instanceSearchCondition.instanceAdministrationSearchState">
						<select title="<ikep4j:message pre='${preHeader}' key='instanceState'/>" name="${status.expression}" id="${status.expression}">
							<option value="RUNNING"><ikep4j:message pre='${preHeader}' key='progress'/></option>
							<option value="COMPLETE"><ikep4j:message pre='${preHeader}' key='complete'/></option>
							<option value="SUSPEND"><ikep4j:message pre='${preHeader}' key='stop'/></option>
							<option value="FAULT"><ikep4j:message pre='${preHeader}' key='error'/></option>
						</select>
						</spring:bind>
					</td>
					<th><ikep4j:message pre='${preHeader}' key='title'/></th>
					<td>
						<spring:bind path="instanceSearchCondition.instanceAdministrationSearchTitle">
						<input type="text" class="inputbox" title="<ikep4j:message pre='${preSearch}' key='instanceName'/>" name="${status.expression}" id="${status.expression}" value="${status.value}" size="40" />
						</spring:bind>
					</td>								
				</tr>
				</tbody>
		</table>
		<div class="searchBtn">
			<a id="instanceSearchBoardItemButton" name="instanceSearchBoardItemButton" href="#a" title="<ikep4j:message pre='${preHeader}' key='button.search'/>"><span><ikep4j:message pre='${preHeader}' key='button.search'/></span></a>
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
		<c:if test="${!instanceSearchResult.emptyRecord}">
		<div class="listInfo">  
			<spring:bind path="instanceSearchCondition.pagePerRecord">  
				<select name="${status.expression}" id="${status.expression}" title='<ikep4j:message pre='${preHeader}' key='pageCount'/>' />
					<option value="10">10</option>
					<option value="15">15</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
					<option value="50">50</option>
				</select> 
			</spring:bind>
			<div class="totalNum">${instanceSearchResult.pageIndex}/ ${instanceSearchResult.pageCount} <ikep4j:message pre='${preHeader}' key='page'/> ( <ikep4j:message pre='${preHeader}' key='totalCount'/><span> ${instanceSearchResult.recordCount}</span>)</div>
			<div align="right">
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${preHeader}' key='progress'/>" /><ikep4j:message pre='${preHeader}' key='progress'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_02.png'/>" alt="<ikep4j:message pre='${preHeader}' key='complete'/>" /><ikep4j:message pre='${preHeader}' key='complete'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${preHeader}' key='stop'/>" /><ikep4j:message pre='${preHeader}' key='stop'/>
				<img style="vertical-align:text-top;" src="<c:url value='/base/workflow/prism/assets/images/signal_03.png'/>" alt="<ikep4j:message pre='${preHeader}' key='error'/>" /><ikep4j:message pre='${preHeader}' key='error'/>
			</div>
		</div>
		<div class="clear"></div>
		</c:if>
		<div class="blockListTable">
			<table summary="<ikep4j:message pre='${preHeader}' key='instanceManagement'/>" style="font-size:13;">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" width="5%"><input id="checkboxAllProcess" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('INSTANCE_ID', '<c:if test="${instanceSearchCondition.sortColumn eq 'INSTANCE_ID'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='instanceId'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('PARTITION_NAME', '<c:if test="${instanceSearchCondition.sortColumn eq 'PARTITION_NAME'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='partitionName'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('PROCESS_NAME', '<c:if test="${instanceSearchCondition.sortColumn eq 'PROCESS_NAME'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='processName'/>
							</a>
						</th>
						<th scope="col" width="35%">
							<a onclick="sortProcess('TITLE', '<c:if test="${instanceSearchCondition.sortColumn eq 'TITLE'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='title'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('STATE', '<c:if test="${instanceSearchCondition.sortColumn eq 'STATE'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='instanceState'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('CREATE_DATE', '<c:if test="${instanceSearchCondition.sortColumn eq 'CREATE_DATE'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='startDate'/>
							</a>
						</th>
						<th scope="col" width="10%">
							<a onclick="sortProcess('FINISHED_DATE', '<c:if test="${instanceSearchCondition.sortColumn eq 'FINISHED_DATE'}">${instanceSearchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preHeader}' key='endDate'/>
							</a>
						</th>
					</tr>
				</thead>
				<c:choose>
				<c:when test="${instanceSearchResult.emptyRecord}">
				<tbody>
					<tr>
						<td colspan="8" height="10px"><ikep4j:message pre='${preCommonMessage}' key='dataNotFound'/></td>
					</tr>
					<c:forEach begin="${fn:length(instanceSearchResult.entity)}" end="${instanceSearchCondition.pagePerRecord-2}" step="1" >
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>
					</c:forEach>	
				</tbody>
				</c:when>
				<c:otherwise>
				<tbody id="tbody_instanceList">
					<c:forEach var="instanceList" items="${instanceSearchResult.entity}" varStatus="status">
						<tr>
							<td width="5%"><input name="checkboxProcess" class="checkbox" title="checkbox" type="checkbox" value="${instanceList.instanceId}" /></td>
							<td width="10%"><div class="ellipsis">${instanceList.instanceId}</div></td>
							<td width="10%"><div class="ellipsis">${instanceList.partitionName}</div></td>
							<td width="10%"><div class="ellipsis">${instanceList.processName}</div></td>					
							<td width="35%" id="td_instanceId" class="textLeft"><div class="ellipsis">${instanceList.title}</div></td>
							<td width="10%">
								<c:choose>
									<c:when test="${instanceList.state == 'RUNNING'}">
										<a onclick="fnModeler('${instanceList.partitionId}','${instanceList.processId}','${instanceList.processVer}','${instanceList.instanceId}','${instanceList.processName}','${instanceList.title}')" title="<ikep4j:message pre='${preHeader}' key='modeler' />"><img src="<c:url value='/base/workflow/prism/assets/images/signal_01.png'/>" alt="<ikep4j:message pre='${preHeader}' key='progress'/>" /></a>
									</c:when>
									<c:when test="${instanceList.state == 'COMPLETE'}">
										<a onclick="fnModeler('${instanceList.partitionId}','${instanceList.processId}','${instanceList.processVer}','${instanceList.instanceId}','${instanceList.processName}','${instanceList.title}')" title="<ikep4j:message pre='${preHeader}' key='modeler' />"><img src="<c:url value='/base/workflow/prism/assets/images/signal_02.png'/>" alt="<ikep4j:message pre='${preHeader}' key='complete'/>" /></a>
									</c:when>
									<c:when test="${instanceList.state == 'SUSPEND'}">
										<a onclick="fnModeler('${instanceList.partitionId}','${instanceList.processId}','${instanceList.processVer}','${instanceList.instanceId}','${instanceList.processName}','${instanceList.title}')" title="<ikep4j:message pre='${preHeader}' key='modeler' />"><img src="<c:url value='/base/workflow/prism/assets/images/signal_05.png'/>" alt="<ikep4j:message pre='${preHeader}' key='stop'/>" /></a>
									</c:when>
									<c:otherwise>
										<a onclick="fnModeler('${instanceList.partitionId}','${instanceList.processId}','${instanceList.processVer}','${instanceList.instanceId}','${instanceList.processName}','${instanceList.title}')" title="<ikep4j:message pre='${preHeader}' key='modeler' />"><img src="<c:url value='/base/workflow/prism/assets/images/signal_03.png'/>" alt="<ikep4j:message pre='${preHeader}' key='error'/>" /></a>
									</c:otherwise>
								</c:choose>
							</td>
							<td width="10%"><div class="ellipsis"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${instanceList.createDate}"/></div></td>
							<td width="10%"><div class="ellipsis"><ikep4j:timezone pattern="yyyy.MM.dd hh:mm" date="${instanceList.finishedDate}"/></div></td>
						</tr>
					</c:forEach>
				</tbody>
				<tbody>
					<c:forEach begin="${fn:length(instanceSearchResult.entity)}" end="${instanceSearchCondition.pagePerRecord-1}" step="1" >
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>
					</c:forEach>
				</tbody>
				</c:otherwise>
				</c:choose>
			</table>
		</div>
		<!--Page Numbur Start--> 
		<spring:bind path="instanceSearchCondition.pageIndex">
		<ikep4j:pagination searchFormId="instanceAdministrationForm" ajaxEventFunctionName="goSubmit" pageIndexInput="${status.expression}" searchCondition="${instanceSearchCondition}" />
		<input name="${status.expression}" id="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preHeader}' key='currentPageIndex'/>" />
		</spring:bind> 
		<!--//Page Numbur End-->
		<div class="tableTop" align="right">
			<select title="<ikep4j:message pre='${preHeader}' key='instanceState'/>" name="instanceAdministrationStateChange" id="instanceAdministrationStateChange">
				<option value="state"><ikep4j:message pre='${preHeader}' key='instanceState'/></option>
				<option value="SUSPEND"><ikep4j:message pre='${preHeader}' key='stop'/></option>
				<option value="FAULT"><ikep4j:message pre='${preHeader}' key='resume'/></option>
			</select>
			<a class="button_s" href="#" id="ainstanceAdministrationStateChange" title="<ikep4j:message pre='${preButton}' key='stateChange'/>"><span><ikep4j:message pre='${preButton}' key='stateChange'/></span></a>
		</div>
	</div>
</div>
</form>
<div class="none ui-dialog1" id="layer_p" title="<ikep4j:message pre='${preHeader}' key='alert'/>" style="overflow:hidden;"> 
	<!--blockDetail Start-->
	<div class="blockDetail_2" style="width:255px">
		<table summary="<ikep4j:message pre='${preHeader}' key='alert'/>" >
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