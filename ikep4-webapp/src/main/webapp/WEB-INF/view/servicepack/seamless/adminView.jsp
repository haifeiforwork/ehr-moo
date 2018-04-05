<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.seamless.adminView.header" /> 
<c:set var="preDetail"  value="ui.servicepack.seamless.adminView.detail" />
<c:set var="preButton"  value="ui.servicepack.seamless.button" /> 
<c:set var="preMsg"  value="ui.servicepack.seamless.MSG" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!-- 
	//편집모드
	function changeMode(pos, show) {
		if (show == 1) {
			$jq("#setDiv"+pos+">label>input").removeAttr("disabled");
			$jq("#hidden"+pos+"_Button").hide();
			$jq("#visible"+pos+"_Button").show();
			$jq("#set"+pos+"_text").removeAttr("disabled");
		} else {
			$jq("#setDiv"+pos+">label>input").attr("disabled",true);
			$jq("#hidden"+pos+"_Button").show();
			$jq("#visible"+pos+"_Button").hide();
			$jq("#set"+pos+"_text").attr("disabled",true);
		}
	};
	function getPosName(pos) {
		var inputVal;
		if (pos == 1){
			inputVal = "maxAttachFilesize";
		} else if (pos == 2) {
			inputVal = "maxReceiverCount";
		} else if (pos == 3) {
			inputVal = "keepDays";
		} else if (pos == 4) {
			inputVal = "maxImapCount";
		};
		return inputVal;
	}
	
	function checkDirect(pos) {
		inputVal = getPosName(pos);
		if ($jq("input[name="+inputVal+"]:checked").val() != '' && $jq("input[name="+inputVal+"]:checked").val() == 0) {
			$jq("#set"+pos+"_text").show();
		} else {
			$jq("#set"+pos+"_text").hide();
			$jq("#set"+pos+"_text").val(null);
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
	
	cancleClick = function (pos) {
		$jq("#set"+pos+"_text").val($jq("#org"+pos+"_text").val());
		changeMode(pos,0);
		orgValue(pos);
		checkDirect(pos);
	};
	
	//ajax 메세지 설정
	set_commit = function(pos) {
		inputVal = getPosName(pos);
		var columnValue = "";
		if ($jq("input[name="+inputVal+"]:checked").val() != '' && $jq("input[name="+inputVal+"]:checked").val() == 0) {
			columnValue = $jq("#set"+pos+"_text").val();
		} else {
			columnValue = $jq("input[name="+inputVal+"]:checked").val();
		}
		$jq.get('<c:url value="/servicepack/seamless/adminSettingUpdate.do?columnId='+inputVal+'&columnValue='+columnValue+'" />')
	    .success(function(updateValue) { 
	    	$jq("#org"+pos+"_text").val(updateValue);
	    	changeMode(pos,0);
	    	alert("<ikep4j:message pre='${preMsg}' key='saveOk'/>");
	    })    
		.error(function(event, request, settings) { alert("error"); });  
	};
	
	$jq(document).ready(function() { 
		for (var pos=1;pos<=7;pos++) {
			$jq("#set"+pos+"_text").val($jq("#org"+pos+"_text").val());
			changeMode(pos,0);
			checkDirect(pos);
		}
			
		$jq("input[name=maxAttachFilesize]").click(function() {
			checkDirect(1);
		});
		$jq("input[name=maxReceiverCount]").click(function() {
			checkDirect(2);
		});
		$jq("input[name=keepDays]").click(function() {
			checkDirect(3);
		});
		$jq("input[name=maxImapCount]").click(function() {
			checkDirect(4);
		});
		
		$jq("#set1_text,#set2_text,#set3_text,#set4_text").keyup(function(){
        	$jq(this).val( $jq(this).val().replace(/[^0-9]/g, '') );
		});
	});
	
//-->
</script>
		<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
		<!--pageTitle Start-->
		<div id="pageTitle"> 
			<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
		</div> 
		<!--//pageTitle End--> 
		
		<div style="height:15px;"></div>
		<!--blockDetail Start-->
		<div class="blockDetail"> 
			<table summary="">
				<caption></caption>
				<thead>
					<tr>
						<th scope="col" class="textLeft"><ikep4j:message pre="${preDetail}" key="individualMaxFilesize" /> (MB)</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>
							<div id="setDiv1" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxAttachFilesize" > 
									<c:forEach var="code" items="${messageCode.maxAttachFilesize}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq null}"><ikep4j:message pre="${preDetail}" key="unlimited" />(MB)</c:when>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" />(MB)</c:when>
												<c:otherwise>${code.value}</c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
							<input id="org1_text" type="hidden" value="${messageAdmin.maxAttachFilesize}" />
								&nbsp;&nbsp;
								<input id="set1_text" name="set1_text" type="text" size="10" />
							</div>
							<div id="hidden1_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set1_edit" onclick="changeMode(1,1);" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
							</div>
							<div id="visible1_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set1_commit" class="button_s" onclick="set_commit(1);" href="#a"><span><ikep4j:message pre='${preButton}' key='commit'/></span></a>
								<a id="set1_cancle" class="button_s" onclick="cancleClick(1);" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" class="textLeft"><ikep4j:message pre="${preDetail}" key="individualMaxRecipient" /> (<ikep4j:message pre="${preDetail}" key="person" />)</th>
					</tr>
					<tr>
						<td>
							<div id="setDiv2" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxReceiverCount" > 
									<c:forEach var="code" items="${messageCode.maxReceiverCount}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq null}"><ikep4j:message pre="${preDetail}" key="unlimited" /></c:when>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" /></c:when>
												<c:otherwise>${code.value}&nbsp;<ikep4j:message pre="${preDetail}" key="person" /></c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org2_text" type="hidden" value="${messageAdmin.maxReceiverCount}" />
								&nbsp;&nbsp;
								<input id="set2_text" name="set2_text" type="text" size="10" />
							</div>
							<div id="hidden2_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set2_edit" onclick="changeMode(2,1);" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
							</div>
							<div id="visible2_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set2_commit" class="button_s" onclick="set_commit(2);" href="#a"><span><ikep4j:message pre='${preButton}' key='commit'/></span></a>
								<a id="set2_cancle" class="button_s" onclick="cancleClick(2);" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" class="textLeft"><ikep4j:message pre="${preDetail}" key="messageExpiration" /> <ikep4j:message pre="${preDetail}" key="messageExpirationSub" /></th>
					</tr>
					<tr>
						<td>
							<div id="setDiv3" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.keepDays" > 
									<c:forEach var="code" items="${messageCode.keepDays}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq null}"><ikep4j:message pre="${preDetail}" key="unlimited" /></c:when>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" /></c:when>
												<c:otherwise>${code.value}&nbsp;<ikep4j:message pre="${preDetail}" key="day" /></c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org3_text" type="hidden" value="${messageAdmin.keepDays}" />
								&nbsp;&nbsp;
								<input id="set3_text" name="set3_text" type="text" size="10" />
							</div>
							<div id="hidden3_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set3_edit" onclick="changeMode(3,1);" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
							</div>
							<div id="visible3_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set3_commit" class="button_s" onclick="set_commit(3);" href="#a"><span><ikep4j:message pre='${preButton}' key='commit'/></span></a>
								<a id="set3_cancle" class="button_s" onclick="cancleClick(3);" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
							</div>
						</td>
					</tr>
					<tr>
						<th scope="col" class="textLeft"><ikep4j:message pre="${preDetail}" key="imapMailMaxCount" /></th>
					</tr>
					<tr>
						<td>
							<div id="setDiv4" style="margin-top:5px;">
								<spring:bind path="setmessageAdmin.maxImapCount" > 
									<c:forEach var="code" items="${messageCode.maxImapCount}">
										<label><input type="radio" class="radio" name="${status.expression}" value="${code.key}" <c:if test="${code.key eq status.value}">checked="checked"</c:if> />
											<c:choose>
												<c:when test="${code.key eq null}"><ikep4j:message pre="${preDetail}" key="unlimited" /></c:when>
												<c:when test="${code.key eq 0}"><ikep4j:message pre="${preDetail}" key="etc" /></c:when>
												<c:otherwise>${code.value}</c:otherwise>
											</c:choose>
										</label>&nbsp;
									</c:forEach>
								</spring:bind>
								<input id="org4_text" type="hidden" value="${messageAdmin.maxImapCount}" />
								&nbsp;&nbsp;
								<input id="set4_text" name="set4_text" type="text" size="10" />
							</div>
							<div id="hidden4_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set4_edit" onclick="changeMode(4,1);" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='edit'/></span></a>
							</div>
							<div id="visible4_Button" class="blockMsgbtn" style="margin-top:-15px;">
								<a id="set4_commit" class="button_s" onclick="set_commit(4);" href="#a"><span><ikep4j:message pre='${preButton}' key='commit'/></span></a>
								<a id="set4_cancle" class="button_s" onclick="cancleClick(4);" href="#a"><span><ikep4j:message pre='${preButton}' key='cancle'/></span></a>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		<!--//blockDetail End-->
