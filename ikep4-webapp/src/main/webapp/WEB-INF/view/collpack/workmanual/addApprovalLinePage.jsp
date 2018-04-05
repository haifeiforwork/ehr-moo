<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.readApprovalView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) { 
	$jq(document).ready(function(){    
		$("tr[name=addUserTr]").hide();
		
	});
	
	var linePosition = "";
	//주소록 버튼 클릭
	addressButtonClick = function(lineId) {
		linePosition = lineId;
		 //파라미터(콜백함수, 전송파라미터, 팝업옵션)
		iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
	};
	
    //주소록
    setAddress = function(data) {
		var name="";
		var userId="";
		$.each(data, function() {
			name = $.trim(this.name);
			userId = $.trim(this.id);
		})
		
		$("#" + linePosition).find("input[name=approvalUserName]").val(name); 
		$("#" + linePosition).find("input[name=approvalUserId]").val(userId);
	};

	//결재자 추가
	createApprovalUser = function(approvalId, approvalLine) { 
		var approvalUserId = $("#" + linePosition).find("input[name=approvalUserId]").val(); 
		$.post("<c:url value='/collpack/workmanual/createApprovalUser.do'/>", {"approvalUserId" : approvalUserId , "approvalId" : approvalId , "approvalLine" : approvalLine}) 
	    .success(function(data) { 
	    	loadApprovalLineList(); 
	    })
	    .error(function(event, request, settings) { alert("error"); });
	};
	//결재자 제거
	deleteApprovalUser = function(approvalId, approvalLine) { 
		if(confirm("<ikep4j:message pre='${messagePrefix}' key='comfirm.delete'/>")) {
		    $.post("<c:url value='/collpack/workmanual/deleteApprovalUser.do'/>", {"approvalId" : approvalId , "approvalLine" : approvalLine}) 
		    .success(function(data) { 
		    	loadApprovalLineList(); 
		    })
		    .error(function(event, request, settings) { alert("error"); });
		}
	};
	
	//결재자 추가라인 보이기
	showAddUserLine = function(lineId) {
		$("tr[name=addUserTr]").hide();
		$("#" + lineId).find("input[name=approvalUserName]").val(""); 
		$("#" + lineId).find("input[name=approvalUserId]").val("");
		$("#" + lineId).show();
	};
	//결재자 추가라인 안보이기
	hideAddUserLine = function(lineId) {
		$("#" + lineId).hide();
	};	
	
	
})(jQuery);
//]]>
</script>


								<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
									<caption></caption>
									<thead>
										<tr>
											<th class="textCenter" scope="col" width="5%"><ikep4j:message pre="${prefix}" key="tab1.table2.column1"/></th>
											<th class="textCenter" scope="col" width="17%"><ikep4j:message pre="${prefix}" key="tab1.table2.column2"/></th>
											<th class="textCenter" scope="col" width="10%"><ikep4j:message pre="${prefix}" key="tab1.table2.column3"/></th>
											<th class="textCenter" scope="col" width="16%"><ikep4j:message pre="${prefix}" key="tab1.table2.column4"/></th>
											<th class="textCenter" scope="col" width="*"><ikep4j:message pre="${prefix}" key="tab1.table2.column5"/></th>
										</tr>
									</thead>
										<tbody>
											<c:forEach var="item" items="${approvalLineList}">
												<tr>
													<td class="textCenter">${item.approvalLine}</td>
													<td class="textLeft">
														<c:choose>
															<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.approvalUserName}&nbsp;${item.approvalUserJobTitleName}</c:when>
															<c:otherwise>${item.approvalUserEnglishName}&nbsp;${item.approvalUserJobTitleEnglishName}</c:otherwise>
														</c:choose>
													</td>
													<td class="textCenter"><c:if test="${item.approvalResult == 'A'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.prepare'/></c:if>
																			<c:if test="${item.approvalResult == 'B'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.holding'/></c:if>
																			<c:if test="${item.approvalResult == 'C'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.accept'/></c:if>
																			<c:if test="${item.approvalResult == 'D'}"><ikep4j:message pre='${messagePrefix}' key='approvalResult.reject'/></c:if>
													</td>
													<td class="textCenter"><ikep4j:timezone date="${item.approvalDate}" pattern="yyyy.MM.dd HH:mm"/></td>
													<td class="textLeft">${item.approvalComment}&nbsp;
														<c:if test="${approvalResult == 'B'}">
															<c:if test="${item.approvalResult == 'A'}">
																<a href="#a" onclick="showAddUserLine('${item.approvalLine}addUserTr')" style="vertical-align:middle"><span><img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" alt="" /></span></a>
																<a href="#a" onclick="deleteApprovalUser('${item.approvalId}', ${item.approvalLine})" style="vertical-align:middle"><span><img src="<c:url value="/base/images/icon/ic_btn_minus.gif"/>" alt="" /></span></a>&nbsp;
															</c:if>
															<c:if test="${item.approvalResult == 'B'}">
																<a href="#a" onclick="showAddUserLine('${item.approvalLine}addUserTr')" style="vertical-align:middle"><span><img src="<c:url value="/base/images/icon/ic_btn_plus.gif"/>" alt="" /></span></a>
															</c:if>
														</c:if>
													</td>
												</tr>
												<c:if test="${item.approvalResult == 'A' || item.approvalResult == 'B'}">
													<tr id="${item.approvalLine}addUserTr" name="addUserTr">
														<td colspan="5">
															<input type="text" class="inputbox" name="approvalUserName" value="" size="20" readonly="readonly" title="input box"/>
															<input type="hidden" name="approvalUserId" value=""/>
															<a class="button" href="#a" onclick="addressButtonClick('${item.approvalLine}addUserTr')" ><span><ikep4j:message pre='ui.support.popup.button' key='address' /></span></a>&nbsp;&nbsp;&nbsp;
															<a class="button" href="#a" onclick="createApprovalUser('${item.approvalId}', ${item.approvalLine})"><span><ikep4j:message pre='${buttonPrefix}' key='save'/></span></a>&nbsp;&nbsp;
															<a class="button" href="#a" onclick="hideAddUserLine('${item.approvalLine}addUserTr')"><span><ikep4j:message pre='${buttonPrefix}' key='cancel'/></span></a> 
														</td>
													</tr>
												</c:if>
											</c:forEach>
										</tbody>
								</table>		
									