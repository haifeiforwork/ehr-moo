<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.message.messageAdminSeting.header" /> 
<c:set var="preSearch"  value="ui.support.message.searchCondition" /> 
<c:set var="preDetail"  value="ui.support.message.messageAdminSeting.detail" />
<c:set var="preButton"  value="ui.support.message.button" /> 
<c:set var="preMsg"  value="ui.support.message.MSG" /> 
<c:set var="preScriptMsg"  value="ui.support.message.messageAdminSeting.scriptMsg" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/plot_chart/jquery.jqplot.css'/>" />

<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jquery.jqplot.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/excanvas.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.cursor.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.pieRenderer.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/base/js/plot_chart/jqplot.dateAxisRenderer.min.js'/>"></script>

 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!-- 
	//편집모드
	function changeMode(show) {
		if (show == 1) {
			for(i=1; i<8; i++) {
				$jq("#setDiv"+i+"").show();
				$jq("#setDiv"+i+"1").hide();
			}
			$jq("#hidden_Button").hide();
			$jq("#visible_Button").show();
		} else {
			for(i=1; i<8; i++) {
				$jq("#setDiv"+i+"").hide();
				$jq("#setDiv"+i+"1").show();
			}
			$jq("#hidden_Button").show();
			$jq("#visible_Button").hide();
		}
	};
	
	function getPosName(pos) {
		var inputVal;
		if (pos == 1){
			inputVal = "maxMonthFilesize";
		} else if (pos == 2) {
			inputVal = "maxStoredFilesize";
		} else if (pos == 3) {
			inputVal = "maxAttachFilesize";
		} else if (pos == 4) {
			inputVal = "maxReceiverCount";
		} else if (pos == 5) {
			inputVal = "keepDays";
		} else if (pos == 6) {
			inputVal = "diskSize";
		} else if (pos == 7) {
			inputVal = "managerAlarmRatio";
		};
		return inputVal;
	}
	
	function checkDirect(pos) {
		if(pos > 5) return;
		inputVal = getPosName(pos);
		
		if ($jq("input[name="+inputVal+"]:checked").val() == 0) {
			$jq("#set"+pos+"_text").show();
		} else {
			$jq("#set"+pos+"_text").hide();
			$jq("#set"+pos+"_text").val(0);
		}
		
	};
	
	function orgValue(pos) {
		inputVal = getPosName(pos);
		var checkYn = "N";
		$jq("input[name="+inputVal+"]").each(function(){
			if($jq(this).val() == $jq("#org"+pos+"_text").val()) {
				$jq(this).attr("checked", true);
				checkYn = "Y";
			}
		});
		if (checkYn == "N") $jq("input[name="+inputVal+"]").each(function(){ if($jq(this).val() == 0) $jq(this).attr("checked", true);});

	};
	
	cancleClick = function () {
		
		for(i=1; i<8; i++) {
			$jq("#set"+i+"_text").val($jq("#org"+i+"_text").val());
			orgValue(i);
			checkDirect(i);
		}
		
		changeMode(0);
	};
	
	//ajax 메세지 설정
	setCommit = function() {
		
		for(i=1 ; i<8; i++) {
			inputVal = getPosName(i);
			var columnValue = "";
			if (i <= 5 ) {
				if ($jq("input[name="+inputVal+"]:checked").val() == 0) {
					columnValue = $jq("#set"+i+"_text").val();
				} else {
					columnValue = $jq("input[name="+inputVal+"]:checked").val();
				}
			} else {
				columnValue = $jq("input[name="+inputVal+"]").val();
			}
			if (columnValue == "") return false;
			
			$jq.get('<c:url value="/support/message/messageSettingUpdate.do?columnId='+inputVal+'&columnValue='+columnValue+'" />')
		    .success(function(updateValue) { 
		    	$jq("#org"+i+"_text").val(updateValue);
		    })    
			.error(function(event, request, settings) { alert("error"); });  
		}
		
		alert("<ikep4j:message pre='${preMsg}' key='saveOk'/>");
		
		document.location.href = "<c:url value='/support/message/messageAdminSeting.do'/>" ;
	};
	
	var chartTicker = 7;
	$jq(document).ready(function() { 
		
		$jq("#chart2").width($jq(window).width()-600);
		chartTicker = ($jq(window).width()-600)/100;
		
		for (var pos=1;pos<=7;pos++) {
			$jq("#set"+pos+"_text").val($jq("#org"+pos+"_text").val());
			changeMode(0);
			checkDirect(pos);
		}
			
		$jq("input[name=maxMonthFilesize]").click(function() {
			checkDirect(1);
		});
		$jq("input[name=maxStoredFilesize]").click(function() {
			checkDirect(2);
		});
		$jq("input[name=maxAttachFilesize]").click(function() {
			checkDirect(3);
		});
		$jq("input[name=maxReceiverCount]").click(function() {
			checkDirect(4);
		});
		$jq("input[name=keepDays]").click(function() {
			checkDirect(5);
		});
		
		$jq("select[name=searchChart]").change(function() {
			$jq("#searchForm").submit(); 
			return false; 
        }); 
		
		$jq("#set1_text,#set2_text,#set3_text,#set4_text,#set5_text,#set6_text,#set7_text").keyup(function(){
        	$jq(this).val( $jq(this).val().replace(/[^0-9]/g, '') );
		});
		$jq.jqplot.config.enablePlugins = true;
		
		//현재 사용율 
		line1 = [['<ikep4j:message pre="${preScriptMsg}" key="emptyDisk"/>',${messageAdmin.diskSize - messageAdmin.useStoredFilesize}], ['<ikep4j:message pre="${preScriptMsg}" key="useDisk"/>',${messageAdmin.useStoredFilesize}]];
		plot1 = $jq.jqplot('chart1', [line1], {
			title: '<ikep4j:message pre="${preScriptMsg}" key="nowDiskInfo"/>',
			grid: {
                drawBorder: false, 
                drawGridlines: false,
                background: '#ffffff',
                shadow:false
            },
            cursor: {
				showVerticalLine:false,
				showHorizontalLine:false,
				showCursorLegend:false,
				showTooltip: false,
				zoom:false
				} ,
			seriesDefaults:{
				renderer:$jq.jqplot.PieRenderer,
				 rendererOptions: {                              
					 showDataLabels: true   
					} 
				},
			legend:{
				show:true, 
				location: 'w'
			}
		});
		var now = new Date();
		var year= now.getFullYear();
		var mon = (now.getMonth()+1)>9 ? ''+(now.getMonth()+1) : '0'+(now.getMonth()+1);
		var day = now.getDate()>9 ? ''+now.getDate() : '0'+now.getDate();

		//월별디스크 정보 jqplot 사용 messageChart
		line2=[
		<c:forEach var="chart" items="${messageChart}">
			['${chart.registDate}', ${chart.filesize}],
		</c:forEach>
		<c:if test="${messageChart eq '[]'}">[year+'-'+mon+'-'+day, 0]</c:if>
			];
		//alert(line2);
		plot2 = $jq.jqplot('chart2', [line2], {
			title:'<ikep4j:message pre="${preScriptMsg}" key="chartTitle"/>',
			gridPadding:{right:35},
			axes:{
				xaxis:{
					renderer:$jq.jqplot.DateAxisRenderer					
					//tickInterval:'1 month'
					//numberTicks:chartTicker
					},
				yaxis:{
					label:'',
					min:0,
					autoscale: true
					}
			},
			cursor: {
				showVerticalLine:true,
				showHorizontalLine:false,
				showCursorLegend:true,
				showTooltip: false,
				zoom:true
				} ,
			series:[{lineWidth:2,color:'blue'}]
			});

	});
	
//-->
</script>

		<!--pageTitle Start-->
		<div id="pageTitle"> 
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
		</div> 
		<!--//pageTitle End--> 
		
		<form id="searchForm" method="post" action="<c:url value='/support/message/messageAdminSeting.do' />">  
		<div class="tableSearch">
		<spring:bind path="searchCondition.searchChart">  
			<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
				<option value="dayChart" <c:if test="${'dayChart' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='dayChart'/></option>
				<option value="weekChart" <c:if test="${'weekChart' eq status.value}">selected="selected"</c:if> ><ikep4j:message pre='${preSearch}' key='${status.expression}' post='weekChart'/></option>
			</select>		
		</spring:bind>
		</div>
		</form>
		
		<div id="chart1" style="margin-top:20px; margin-left:10px; width:300px; height:250px;"></div>
		<div id="chart2" style="margin-top:-250px; margin-left:350px; width:800px; height:250px;"></div>
		<div style="height:15px;"></div>
		<!--blockDetail Start-->
		<div class="blockDetail"> 
			<table summary="Message">
				<caption></caption>
				<col style="width: 30%;"/>
				<col style="width: *;"/>
				<tbody>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="monthlyMaxFilesize" /></th>
						<td>
							<div id="setDiv1" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxMonthFilesize" > 	
									<c:forEach var="code" items="${messageCode.maxMonthFilesize}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" />(MB)</c:when>
												<c:otherwise>${code.value}</c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org1_text" type="hidden" value="${messageAdmin.maxMonthFilesize}" />
								&nbsp;&nbsp;
								<input id="set1_text" name="set1_text" type="text" size="10" />
							</div>
							<div id="setDiv11" style="margin-top:5px;">
								${messageAdmin.maxMonthFilesize} MB
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="storedMaxFilesize" /></th>
						<td>
							<div id="setDiv2" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxStoredFilesize" > 	
									<c:forEach var="code" items="${messageCode.maxStoredFilesize}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" />(MB)</c:when>
												<c:otherwise>${code.value}</c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org2_text" type="hidden" value="${messageAdmin.maxStoredFilesize}" />
								&nbsp;&nbsp;
								<input id="set2_text" name="set2_text" type="text" size="10" />
							</div>
							<div id="setDiv21" style="margin-top:5px;">
								${messageAdmin.maxStoredFilesize} MB
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="individualMaxFilesize" /></th>
						<td>
							<div id="setDiv3" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxAttachFilesize" > 
									<c:forEach var="code" items="${messageCode.maxAttachFilesize}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" />(MB)</c:when>
												<c:otherwise>${code.value}</c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org3_text" type="hidden" value="${messageAdmin.maxAttachFilesize}" />
								&nbsp;&nbsp;
								<input id="set3_text" name="set3_text" type="text" size="10"  />
							</div>
							<div id="setDiv31" style="margin-top:5px;">
								${messageAdmin.maxAttachFilesize} MB
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="individualMaxRecipient" /></th>
						<td>
							<div id="setDiv4" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxReceiverCount" > 
									<c:forEach var="code" items="${messageCode.maxReceiverCount}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" /></c:when>
												<c:otherwise>${code.value}&nbsp;<ikep4j:message pre="${preDetail}" key="person" /></c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org4_text" type="hidden" value="${messageAdmin.maxReceiverCount}" />
								&nbsp;&nbsp;
								<input id="set4_text" name="set4_text" type="text" size="10"  />
							</div>
							<div id="setDiv41" style="margin-top:5px;">
								${messageAdmin.maxReceiverCount} <ikep4j:message pre="${preDetail}" key="person" />
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="messageExpiration" /></th>
						<td>
							<div id="setDiv5" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.keepDays" > 
									<c:forEach var="code" items="${messageCode.keepDays}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" /></c:when>
												<c:otherwise>${code.value}&nbsp;<ikep4j:message pre="${preDetail}" key="day" /></c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org5_text" type="hidden" value="${messageAdmin.keepDays}" />
								&nbsp;&nbsp;
								<input id="set5_text" name="set5_text" type="text" size="10"  />
							</div>
							<div id="setDiv51" style="margin-top:5px;">
								${messageAdmin.keepDays} <ikep4j:message pre="${preDetail}" key="day" />
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="diskMaxSize" /></th>
						<td>
							<div id="setDiv6" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.diskSize" > 
									<label><input type="text" id="set6_text" name="${status.expression}" value="${status.value}" size="15"  /> (GB)</label>
								</spring:bind>
								<input id="org6_text" type="hidden" value="${messageAdmin.diskSize}" />
							</div>
							<div id="setDiv61" style="margin-top:5px;">
								${messageAdmin.diskSize} GB
							</div>
						</td>
					</tr>
					<tr>
						<th scope="row"><ikep4j:message pre="${preDetail}" key="adminAlertSMS" /></th>
						<td>
							<div id="setDiv7" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.managerAlarmRatio" > 
									<label><input type="text" id="set7_text" name="${status.expression}" value="${status.value}" size="3" maxlength="2" />
										% <ikep4j:message pre="${preDetail}" key="adminAlertComent" />
									</label>
								</spring:bind>
								<input id="org7_text" type="hidden" value="${messageAdmin.managerAlarmRatio}" />
							</div>
							<div id="setDiv71" style="margin-top:5px;">
								${messageAdmin.managerAlarmRatio} %
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
		
		<!--blockButton Start-->
		<div class="blockButton" id="hidden_Button" > 
			<ul>
				<li><a class="button" href="#none" onclick="changeMode(1);"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a></li>
			</ul>
		</div>
		<div class="blockButton" id="visible_Button"> 
			<ul>
				<li><a class="button" href="#none" onclick="setCommit();"><span><ikep4j:message pre='${preButton}' key='commit'/></span></a></li>
				<li><a class="button" href="#none" onclick="cancleClick();"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a></li>
			</ul>
		</div>
		<!--blockButton End-->
	
