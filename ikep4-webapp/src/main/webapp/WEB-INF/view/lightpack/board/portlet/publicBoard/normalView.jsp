<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preList"    value="ui.lightpack.board.portlet.publicBoard" />  
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">
//<![CDATA[
(function($){	  
	tabResize_${portletConfigId} = function() {
		if($(".boardTab").length > 0)  {
			var itemSize = ($("#portletlightpackPublicBoard").width() - 30) / ($(".boardTab").length + 1); 
			$(".boardTab").css("width", itemSize); 	
		} 
	};
	
	eventFunction = function(boardId) {
		
		if($jq("input[id="+boardId+"_hidden]").val() != "open") {
			$jq("#portletlightpackPublicBoard").ajaxLoadStart();
		}
		
	};
	
	$(document).ready(function() {
		/*
		$.ajaxSetup({
			beforeSend : function(jqXHR, settings) {
				$("#portletlightpackPublicBoard").ajaxLoadStart(); 
			},
			complete : function(jqXHR, textStatus) {
				$("#portletlightpackPublicBoard").ajaxLoadComplete(); 
			}
		});
		*/
		
		var boardId = $("a.board:eq(0)").attr("id");
		
		var tab = $("#portletlightpackPublicBoard").tabs({ 
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
				documentTitle : "게시판 내용보기",
				width:600, 
				height:500, 
				modal:true 
			};
			
			iKEP.portletPopupOpen(url, options);  
			
			return false;
		});
		 
		$("div.more").click(function() {  
			location.href = "<c:url value='/lightpack/board/boardCommon/boardView.do?boardId=' />" + boardId + "&portletYn=true";
		});
		
		$(window).resize(function() {
			tabResize_${portletConfigId}();
		});
		
		tabResize_${portletConfigId}();
	});  

})(jQuery);	
//]]>
</script>
<div id="${portletConfigId}">
<div id="portletlightpackPublicBoard" class="iKEP_tab_s"> 
	<ul>
		<c:forEach var="board" items="${boardList}">
		<li>
			<a id="${board.boardId}" href="<c:url value='/lightpack/board/portlet/publicBoard/listBoardItem.do?boardId=${board.boardId}'/>" onclick="eventFunction(this.id);" class="board tabItem">
				<div class="ellipsis boardTab">
					<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">${board.boardName}</c:when>
						<c:otherwise>${board.boardEnglishName}</c:otherwise>
					</c:choose>
				</div>
				<input type="hidden" id="${board.boardId}_hidden" value=""/> 
			</a>
		</li>
		</c:forEach> 
	</ul>
	<c:if test="${not empty boardList}"><div class="more"><a href='#a'><img src='<c:url value='/base/images/common/btn_more.gif'/>' alt='more' /></a></div></c:if>
	<div class="newslist">
		<div id="ui-tabs-1" style="padding-bottom:7px;"></div> 
	</div>				
</div>	 
</div> 