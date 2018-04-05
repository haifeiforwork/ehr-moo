<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.normalView"/>

<script type="text/javascript">
<!--   
(function($){	  
	var tabResize_public_rss = function() {
		if($(".boardTab").length > 0)  {
			var itemSize = ($("#portletPublicRSS").width() -30) / ($(".boardTab").length+1); 
			$(".boardTab").css("width", itemSize); 	
		}
	};
	
	eventFunction = function(channelId) {
		//리스트에서 ajaxLoadComplete()를 정상 호출하지 못해 주석처리함
		/*
		if($jq("input[id="+channelId+"_hidden]").val() != "open") {
			$jq("#portletPublicRSS").ajaxLoadStart();
		}
		*/
	};
	
	$(document).ready(function() {
		tabResize_public_rss();		
		var boardId = $("a.board:eq(0)").attr("id");
		
		var tab = $("#portletPublicRSS").tabs({ 
			selected : 0, 
			cache : true,  
			select : function(event, ui) {
				boardId = ui.tab.id;
			}
		}); 
				
		$("a.item").live("click", function() {  
			var url = $(this).attr("href")+ "&popupYn=true";
				
			var options = {
				windowTitle : $(this).html(),
				documentTitle : "RSS 내용보기",
				width:600, 
				height:500, 
				modal:true 
			};
			
			iKEP.portletPopupOpen(url, options);  
			
			return false;
		});
				
		$(window).resize(function() {
			tabResize_public_rss();
		}); 
	});  

})(jQuery);	
//-->
</script>
<div id="${portletConfigId}">
	<div id="portletPublicRSS" class="iKEP_tab_s">
		<c:if test="${!empty publicRssList}">
			<ul>
				<c:forEach var="channel" items="${channelList}" begin="0" end="${publicRss.tabCount -1}">
					<li>
						<a id="${channel.channelId}" href="<c:url value='/portal/portlet/publicRss/rssItemList.do?portletConfigId=${channel.channelId}&amp;popupYn=true'/>" onclick="eventFunction('${channel.channelId}');" class="board tabItem">
							<div class="ellipsis boardTab">${channel.channelTitle}</div>
							<input type="hidden" id="${channel.channelId}_hidden" value=""/>
						</a>
					</li>
				</c:forEach> 
			</ul>
		</c:if>
		<c:if test="${empty publicRssList}">
			<div><br><ikep4j:message pre="${prefix}" key="rssNoUrlMessage" /></div>
		</c:if>
		<div class="newslist">
			<div id="ui-tabs-1"></div> 
		</div>				
	</div>	 
</div>