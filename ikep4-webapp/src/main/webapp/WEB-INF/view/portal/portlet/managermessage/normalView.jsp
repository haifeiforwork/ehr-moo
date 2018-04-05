<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<c:set var="prefix" value="message.portal.portlet.managerMessage.normalView"/>
<%-- Tab Lib End --%> 
<script type="text/javascript">
//<![CDATA[
(function($){	 
	$(document).ready(function() {   
		$("#${portletConfigId} a.item").click(function() { 
			var url = $(this).attr("href")+ "&popupYn=true&portletYn=true";
			
			var options = {
				windowTitle : $(this).html(),
				documentTitle : "게시판 내용보기",
				width:720, height:500, modal:true, 
				callback : function(result) {
					 
				}
			};
			
			iKEP.portletPopupOpen(url, options);
	 
			return false;
		});
	}); 

})(jQuery);
//]]>
</script>
<div class="mtitle">
	<c:if test="${managermessageTitleYn == 'Y'}">
		<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletManagerMessage.titleFileId}"/>
	</c:if>
	<c:if test="${managermessageTitleYn != 'Y'}">
		<img src="<c:url value='/base/images/common/mtitle_management.gif'/>" />
	</c:if>
	<div class="more" style="float:right; padding-top: 7px;"><a href='<c:url value='/lightpack/board/boardCommon/boardView.do?boardId=${portletManagerMessage.boardId}&portletYn=true' />'><img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div>
</div>
<div id="${portletConfigId}" style="padding-top:10px;">
	<h2 class="none"><ikep4j:message pre="${prefix}" key="title" /></h2>
	<c:set var="img_td">
		<td valign="top" style="padding-right:5px;width:90px;">
		<c:if test="${managermessageImageYn == 'Y'}">
			<img src="<c:url value='/support/fileupload/downloadFile.do'/>?fileId=${portletManagerMessage.imageFileId}" width="100" height="107"/>
		</c:if>
		<c:if test="${managermessageImageYn != 'Y'}">
			<img src="<c:url value='/base/images/common/img_ceo.gif'/>"  width="100" height="107"/>
		</c:if>
		</td>
	</c:set>
	<c:set var="text_td">
		<td valign="top">
			<div class="ellipsis">
				<b>${portletManagerMessage.subject}</b>
			</div>
			<div class="ellipsis" style="margin-top: 2px; max-height:52px; white-space:normal; font: 100%/1.5em '맑은 고딕', Tahoma, 'Dotum'; BACKGROUND: #fff;">
				<a class="item" href="<c:url value='/lightpack/board/boardItem/readBoardItemLinkView.do?popupYn=true&amp;itemId=${portletManagerMessage.itemId}'/>">
					${portletManagerMessage.summary}
				</a>
			</div>
		</td>
	</c:set>
	<table width="100%" class="ex_me" style="table-layout: fixed;">	
		<tbody>
			<tr>
				<c:if test="${portletManagerMessage.location eq 'right'}">
					${text_td}
					${img_td}
				</c:if>
				<c:if test="${portletManagerMessage.location eq 'left'}">
					${img_td}
					${text_td}
				</c:if>
			</tr>
		</tbody>
	</table>
</div>

