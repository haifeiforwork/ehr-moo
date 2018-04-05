<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="prefix" value="message.portal.portlet.managerMessage.normalView"/>
<%-- Tab Lib End --%> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {   
		
	}); 

})(jQuery);
//]]>
</script>
<div class="kmbox">
<div class="tableList_3 mb15">

<div class="subTitle_1a">
		<h3>
			이달의 정보 ISSUE
		</h3>		
		<div class="btn_more"><a href='<c:url value='/collpack/kms/notice/listNoticeItemView.do' />'><img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div>
	</div>
	
<div>
	<h2 class="none"><ikep4j:message pre="${prefix}" key="title" /></h2>
	<c:set var="img_td">
		<td valign="top" style="padding-right:5px;width:90px;">
		<c:if test="${campaignmessagesImageYn == 'Y'}">
			<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${noticeconfig.imageFileId}" width="100" height="100"/>
		</c:if>
		<c:if test="${campaignmessagesImageYn != 'Y'}">
			<img src="<c:url value='/base/images/common/img_wisesaying5_1.gif'/>"  width="100" height="100"/>
		</c:if>
		</td>
	</c:set>
	<c:set var="text_td">
		<td valign="top">
			<div class="ellipsis">
				<b>${noticeconfig.subject}</b>
			</div>
			<div class="ellipsis" style="margin-top: 2px; max-height:52px; white-space:normal; font: 100%/1.5em '맑은 고딕', Tahoma, 'Dotum'; BACKGROUND: #fff;">
				<a class="item" href="<c:url value='/collpack/kms/notice/readNoticeItemView.do?itemId=${noticeconfig.itemId}'/>">
					${noticeconfig.summary}
				</a>
			</div>
		</td>
	</c:set>
	<table width="100%" class="ex_me" style="table-layout: fixed;">	
		<tbody>
			<tr>
				<%-- <c:if test="${noticeconfig.location eq 'right'}">
					${text_td}
					${img_td}
				</c:if>
				<c:if test="${noticeconfig.location eq 'left'}"> --%>
					${img_td}
					${text_td}
				<%-- </c:if> --%>
			</tr>
		</tbody>
	</table>
</div>

</div>
</div>
<div class="kmboxline"></div>
