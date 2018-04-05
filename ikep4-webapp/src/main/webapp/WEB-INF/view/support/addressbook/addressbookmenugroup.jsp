<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.addressbook.header" /> 
<c:set var="prePrivate"    value="ui.support.addressbook.addrgroup.private" />
<c:set var="prePublic"  value="ui.support.addressbook.addrgroup.public" />

<script type="text/javascript">
<!-- 
var tmpWords = [];
var cnt = 0;
var viewCnt = 0;
(function($) {
	
	/**
	 * onload시 수행할 코드
	 */
	$(document).ready(function() {
		
		$(".openclose").hide();
		groupView = function(category) {
			for(i=0;i<cnt;i++){
				if("grp_"+category == tmpWords[i]){
					viewCnt++;
				}
			}
			if(viewCnt%2 == 0){
				$("ul[name=grp_"+category+"]").show();
			}else{
				$("ul[name=grp_"+category+"]").hide();
			}
			viewCnt = 0;
			
			if(cnt < 1){
				tmpWords[0] = "grp_"+category;
			}else{
				tmpWords[cnt] = "grp_"+category;
			}
			cnt++;
		};
	});
})(jQuery);  
//-->
</script>
<%-- 메시지 관련 Prefix 선언 End --%>

				<c:if test="${groupType != null && groupType == 'P'}">
					<li class="no_child liLast <c:if test="${addrgroupId == null }">licurrent</c:if>"><a class="blt_01" href="#a" onclick="Addressbook.getAddrHome('','P','<ikep4j:message pre='${prePrivate}' key='totalList.title'/>');"><ikep4j:message pre='${prePrivate}' key='totalList.title'/></a></li>
				</c:if>
				<c:if test="${addrgroupList != null}">
				<c:if test="${groupType == 'P'}">
					<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
						<c:if test="${not(status.last && groupType != 'P') }">
						<li class="no_child <c:if test="${addrgroup.addrgroupId == addrgroupId }">licurrent</c:if>">
						</c:if>
						<c:if test="${status.last && groupType != 'P' }">
						<li class="no_child liLast <c:if test="${addrgroup.addrgroupId == addrgroupId }">licurrent</c:if>">
						</c:if>
							<c:if test="${groupType != null && groupType == 'P'}">
							<a href="#a" onclick="Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');"><c:out value="${addrgroup.addrgroupName}"/></a>
							</c:if>
							<c:if test="${groupType != null && groupType != 'P'}">
							<a href="#a" onclick="Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');"><c:out value="${addrgroup.addrgroupName}"/></a>
							</c:if>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${groupType == 'G'}">
					<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
						<c:if test="${not(status.last && groupType != 'G') }">
						<li class="no_child <c:if test="${addrgroup.addrgroupId == addrgroupId }">licurrent</c:if>">
						</c:if>
						<c:if test="${status.last && groupType != 'G' }">
						<li class="no_child liLast <c:if test="${addrgroup.addrgroupId == addrgroupId }">licurrent</c:if>">
						</c:if>
							<c:if test="${groupType != null && groupType == 'G'}">
							<a href="#a" onclick="Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');"><c:out value="${addrgroup.addrgroupName}"/></a>
							</c:if>
							<c:if test="${groupType != null && groupType != 'G'}">
							<a href="#a" onclick="Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');"><c:out value="${addrgroup.addrgroupName}"/></a>
							</c:if>
						</li>
					</c:forEach>
				</c:if>
				<c:if test="${groupType != 'P' && groupType != 'G'}">
				<c:set var="category" value="tmp"></c:set>
				<div class="left_fixed">
					<ul>
					<li class="board opened">
					<ul>
					<c:forEach var="addrgroup" items="${addrgroupList.entity}" varStatus="status">
						<li class="board opened">
							<c:if test="${addrgroup.categoryId != category}">
								<a class="board" style="cursor:pointer;" onclick="groupView(${addrgroup.categoryId});">${addrgroup.categoryName}</a>
							</c:if>
							<ul class="openclose" id="grp_${addrgroup.categoryId}" name="grp_${addrgroup.categoryId}">
								<li class="board no_child">
									<a class="board" href="#a" onclick="Addressbook.getAddrHome('${addrgroup.addrgroupId}','${addrgroup.groupType}','${addrgroup.addrgroupName}');"><c:out value="${addrgroup.addrgroupName}"/></a>
								</li>
							</ul>
						</li>
						<c:set var="category" value="${addrgroup.categoryId}"></c:set>
					</c:forEach>
					</ul>
					</li>
					</ul>
				</div>
				</c:if>
				</c:if>
				<c:if test="${groupType != null && groupType == 'P'}">
					<li class="no_child liLast <c:if test="${addrgroupId == 'NOGROUP' }">licurrent</c:if>"><a href="#a" onclick="Addressbook.getAddrHome('NOGROUP','${groupType}','<ikep4j:message pre='${prePrivate}' key='undiffer.title'/>');"><ikep4j:message pre='${prePrivate}' key='undiffer.title'/></a></li>
				</c:if>
				

				
								