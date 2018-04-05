<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listCategoryView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		var isReadPermission = "${manualCategory.readPermission}";
		if(isReadPermission == 1) {
			$jq('#readTh').attr("rowspan", 1); 
			$jq('#readTr').hide(); 
		}
	});
	
})(jQuery);
//]]>
</script>

	
						<!--blockDetail Start-->					
						<div class="blockDetail clear">
							<table summary="<ikep4j:message pre='${prefix}' key='table.summary'/>">
								<caption></caption>
								<thead>
									<tr>
										<th scope="col" width="25%"><ikep4j:message pre="${prefix}" key="categoryName"/></th>
										<td class="textLeft">${manualCategory.categoryName}</td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<th id="readTh" width="25%" rowspan="2" scope="col"><ikep4j:message pre="${prefix}" key="readUser"/></th>
										<td>
											<label><input name="readPermission" value="1" type="radio" class="radio" title="<ikep4j:message pre="${prefix}" key="public.open"/>"  <c:if test="${manualCategory.readPermission == 1 || mode == 'C'}">checked="checked"</c:if> disabled="disabled"/><ikep4j:message pre="${prefix}" key="public.open"/></label>&nbsp;&nbsp;
											<label><input name="readPermission" value="0" type="radio" class="radio" title="<ikep4j:message pre="${prefix}" key="public.close"/>" <c:if test="${manualCategory.readPermission == 0 && mode != 'C'}">checked="checked"</c:if> disabled="disabled"/><ikep4j:message pre="${prefix}" key="public.close"/></label>
										</td>
									</tr>
									<tr id="readTr">
										<td class="textLeft">
											<c:forEach var="item" items="${readUserList}">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.readUserName} ${item.readUserJobTitleName} ${item.readUserTeamName}</c:when>
													<c:otherwise>${item.readUserEnglishName} ${item.readUserJobTitleEnglishName} ${item.readUserTeamEnglishName}</c:otherwise>
												</c:choose><br/>
											</c:forEach>
												
											<div class="blockBlank_10px"></div>
											
											<c:forEach var="item" items="${readGroupList}">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.readGroupName}</c:when>
													<c:otherwise>${item.readGroupEnglishName}</c:otherwise>
												</c:choose><br/>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre="${prefix}" key="wirteUser"/></th>
										<td>
											<c:forEach var="item" items="${writeUserList}">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.writeUserName} ${item.writeUserJobTitleName} ${item.writeUserTeamName}</c:when>
													<c:otherwise>${item.writeUserEnglishName} ${item.writeUserJobTitleEnglishName} ${item.writeUserTeamEnglishName}</c:otherwise>
												</c:choose><br/>
											</c:forEach>
										</td>
									</tr>
									<tr>
										<th width="25%" scope="col"><ikep4j:message pre="${prefix}" key="approvalUser"/></th>
										<td>
											<c:forEach var="item" items="${approvalUserList}">
												<c:choose>
													<c:when test="${user.localeCode == portal.defaultLocaleCode}">${item.approvalUserName} ${item.approvalUserJobTitleName} ${item.approvalUserTeamName}</c:when>
													<c:otherwise>${item.approvalUserEnglishName} ${item.approvalUserJobTitleEnglishName} ${item.approvalUserTeamEnglishName}</c:otherwise>
												</c:choose><br/>
											</c:forEach>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!--//blockDetail End-->