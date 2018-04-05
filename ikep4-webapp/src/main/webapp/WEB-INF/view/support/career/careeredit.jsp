<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.profile.sub.career" /> 
<c:set var="prePrivate" value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<c:set var="preButton"  value="ui.support.profile.sub.career.button" /> 
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

								<c:if test="${career != null}">
								<table>
								<tr class="career_innertable">
									<td>
									<form name="careerForm" id="careerForm" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="return false">
									<input id="registerId" name="registerId" value="<c:out value="${user.userId}"/>" type="hidden" />
									<c:choose>
									<c:when test="${user.localeCode == 'ko'}">
									<input id="registerName" name="registerName" value="<c:out value="${user.userName}"/>" type="hidden" />
									</c:when>
									<c:otherwise>
									<input id="registerName" name="registerName" value="<c:out value="${user.userEnglishName}"/>" type="hidden" />
									</c:otherwise>
									</c:choose>
									<input id="type" name="type" value="<c:out value="${type}"/>" type="hidden" />
									<input id="careerId" name="careerId" value="<c:out value="${career.careerId}"/>" type="hidden" />
									
										<!--blockDetail Start-->
										<div class="innertableWrap pl10 pr5" style="background: none repeat scroll 0% 0% rgb(245, 245, 245);">
											<div class="innertable">
												<table summary="<ikep4j:message pre='${preHeader}' key='histCardInputTable'/>">

													<caption></caption>
													<tbody>
														<tr>
															<th width="18%"><ikep4j:message pre='${preHeader}' key='title.workdate'/></th>
															<td width="32%">
																<div>
																<input type="text" class="inputbox" id="workStartDate" name="workStartDate" title="<ikep4j:message pre='${preHeader}' key='title.startDate'/>" value="<fmt:formatDate value='${career.workStartDate}' type='date' pattern='yyyy.MM.dd'/>" size="9" maxlength="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif' />" alt="<ikep4j:message pre='${preButton}' key='calendar'/>" /> ~
																<input type="text" class="inputbox" id="workEndDate" name="workEndDate" title="<ikep4j:message pre='${preHeader}' key='title.endDate'/>" value="<fmt:formatDate value='${career.workEndDate}' type='date' pattern='yyyy.MM.dd'/>" size="9" maxlength="10" /> <img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif' />" alt="<ikep4j:message pre='${preButton}' key='calendar'/>" />
																</div>
															</td>
															<th width="18%"><ikep4j:message pre='${preHeader}' key='title.comName'/></th>
															<td width="32%">
																<div><input name="companyName" title="<ikep4j:message pre='${preHeader}' key='title.comName'/>" class="inputbox" type="text" maxlength="12" value="<c:out value="${career.companyName}"/>" /></div>
															</td>															
														</tr>
														<tr>
															<th scope="row"><ikep4j:message pre='${preHeader}' key='title.roleName'/></th>
															<td colspan="3" style="padding-right:5px !important;"><div><input name="roleName" title="<ikep4j:message pre='${preHeader}' key='title.roleName'/>" class="inputbox w100 mt5 mb5" type="text" maxlength="16" value="<c:out value="${career.roleName}"/>" /></div></td>
														</tr>
														<tr>
															<th scope="row"><ikep4j:message pre='${preHeader}' key='title.workChange'/></th>
															<td colspan="3" style="padding-right:5px !important;">
																<div><textarea name="workChange" class="w100" title="<ikep4j:message pre='${preHeader}' key='title.workChange'/>" cols="" rows="5"><c:out value="${career.workChange}"/></textarea></div>
															</td>
														</tr>

													</tbody>
												</table>
											</div>
											<div class="innertable_btn mr10 textRight">
												<a href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
												<a onclick="readCareerView('${career.careerId}',this,'${type}')" href="#a" class="button_s"><span><ikep4j:message pre='${preButton}' key='cancel'/></span></a>
											</div>
										</div>									
										<!--//blockDetail End-->
									</form>
									</td>
								</tr>
								</table>
								</c:if>
<script type="text/javascript">
<!--
	$jq("input[name=workStartDate]").each(function() {
		$jq(this).datepicker({onSelect:function(){$jq(this).trigger("focusout");}}).next("img").eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
	});
	$jq("input[name=workEndDate]").each(function() {
		$jq(this).datepicker({onSelect:function(){$jq(this).trigger("focusout");}}).next("img").eq(0).click(function() { $jq(this).prev().eq(0).datepicker("show"); });
	});

//-->
</script>