<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.favorite.header" /> 
<c:set var="preList"    value="ui.support.favorite.list" />
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<c:set var="preButton"  value="ui.support.favorite.button" /> 
<c:set var="preMessage" value="ui.support.favorite.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<table summary="<ikep4j:message pre='${preDetail}' key='favoriteVideo' />">
	<caption></caption>
	<tbody>
		<c:forEach var="favorite" items="${searchResult.entity}">
			<tr class="msg_read">
				<td class="tdFirst textRight_p20" width="7%">
					${ikep4j:getTimeInterval(favorite.registDate, user.localeCode)}
				</td>
				<td class="textLeft_p20" width="10%">
					${favorite.module}
				</td>
				<td class="textLeft" width="27%">
					<img src="<c:url value="/base/images/icon/ic_film.png" />" alt="" />
					<a href="<c:url value="/support/fileupload/downloadFile.do" />?fileId=${favorite.fileId}">
					${favorite.fileRealName}</a>
				</td>
				<td class="textLeft tdLast" width="56%" style="word-wrap:break-word;">
					<a href="#a" onclick="showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')">
					${favorite.title}</a>
					<span class="name"><a href="#a" onclick="iKEP.goProfilePopupMain('${favorite.registerId}')">${favorite.registerName}</a></span>
					<span>
						<ikep4j:message pre='${preList}' key='hitCount'/> ${favorite.hitCount}
						&nbsp;<ikep4j:message pre='${preList}' key='recommendCount'/> ${favorite.recommendCount}
						&nbsp;<ikep4j:message pre='${preList}' key='linereplyCount'/> ${favorite.linereplyCount}
						&nbsp;<ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${favorite.registDate}"/>
					</span>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() {
		
		$jq("#recordCount").val("${searchCondition.recordCount}");
		$jq("#currentCount").val("${searchCondition.currentCount}");
		
		setMoreDiv();
		
	});
	
	
})(jQuery);  

//-->
</script>					