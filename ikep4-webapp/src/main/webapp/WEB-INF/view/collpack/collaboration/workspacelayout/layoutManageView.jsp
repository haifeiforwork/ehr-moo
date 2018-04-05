<%--
=====================================================
	* 기능설명	:	workspace 내용 수정화면
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"	value="message.collpack.collaboration.layout" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript"> 
<!--
var $portlets;
var activePortlets = [
	<c:if test="${workspacePortletLayoutList != null}">
	<c:forEach var="layoutList" items="${workspacePortletLayoutList}" varStatus="status">
		<c:choose>
			<c:when test="${status.last}">
				<c:if test="${layoutList.colIndex == 1 }">
					{id:"${layoutList.portletId}@${layoutList.boardId}", layout:"l", seq:"${layoutList.rowIndex}"}
				</c:if>
				<c:if test="${layoutList.colIndex == 2 }">
					{id:"${layoutList.portletId}@${layoutList.boardId}", layout:"r", seq:"${layoutList.rowIndex}"}
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${layoutList.colIndex == 1 }">
					{id:"${layoutList.portletId}@${layoutList.boardId}", layout:"l", seq:"${layoutList.rowIndex}"},
				</c:if>
				<c:if test="${layoutList.colIndex == 2 }">
					{id:"${layoutList.portletId}@${layoutList.boardId}", layout:"r", seq:"${layoutList.rowIndex}"},
				</c:if>
			</c:otherwise>
		</c:choose>  
	</c:forEach>
	</c:if>
];
 
(function($) {
	var $portletLayout;
	var tplPortlet = $.template(null, '<li><div class="moveButton_2"><a class="move_btn02" href="#a" title="\${id}"><span>\${desc}</span></a></div></li>');
	
	function getPortletItem(portletId) {	// portlet item result: checkbox element
		var result = null;
		$portlets.each(function() {
			if(portletId == $(this).val()) {
				result = this;
				return false;
			}
		})
		return result;
	}
	
	
	$(document).ready(function() {
		$portletLayout = $("#portletLayout");
		$portlets = $("input", "#ulPortletItems");
		
		var $ulLayout = $portletLayout.find("ul");
		
		$.each(activePortlets, function(){	// 활성화 되어 있는 포틀릿 생성
			var $portletItem = $(getPortletItem(this.id)).attr("checked", true);
			
			var portletInfo = $.extend(this, {desc:$portletItem.attr("title")});
			var $ul = null;
			switch(portletInfo.layout) {
				case "l" : $ul = $ulLayout.eq(0); break;
				case "r" : $ul = $ulLayout.eq(1); break;
			}
			
			var $displayPortlets = $ul.children();
			var $appendPortlet = null;
			
			if($displayPortlets.length > 0) {	// 해당 영역에 포틀릿이 있으면 순번에 맞춰 추가
				$displayPortlets.each(function() {
					
					if(portletInfo.seq < $.data(this, "seq")) {
						$appendPortlet = $.tmpl(tplPortlet, portletInfo);
						$.data($appendPortlet[0], {portletId:portletInfo.id, seq:portletInfo.seq, layout:portletInfo.layout });
						$(this).before($appendPortlet);
						return false;
					}
				});
			}
			
			if($appendPortlet == null) {	// 포틀릿이 추가 되지 않았으면 해당 영역의 마지막에 추가
				$appendPortlet = $.tmpl(tplPortlet, portletInfo);
				$.data($appendPortlet[0], {portletId:portletInfo.id, seq:portletInfo.seq, layout:portletInfo.layout });
				$ul.append($appendPortlet);
			}
		});
		
		var $divSortable = $portletLayout.find("ul");
		$divSortable.sortable({
			revert: true,
			connectWith: $divSortable,
			placeholder: "ui-state-highlight",
			forcePlaceholderSize: true
		});
		
		$("#ulPortletItems").click(function(event) {
			if(event.target.tagName.toLowerCase() == "input") {
				$portletItem = $(event.target);
				if($portletItem.is(":checked")) {	// 생성
					$appendPortlet = $.tmpl(tplPortlet, {id:$portletItem.val(), desc:$portletItem.attr("title"),layout:"l"});
					$.data($appendPortlet[0], "portletId", $portletItem.val());
					$ulLayout.eq(0).prepend($appendPortlet);
				} else {
					$ulLayout.find("li").each(function() {
						if($.data(this, "portletId") == $portletItem.val()) {
							$(this).remove();
							return false;
						}
					});
				}
			}
		});
		
		$("#btnSave").click(function() {
			var portletSet = "";// [[], []];
			
			if(confirm("<ikep4j:message pre='${prefix}' key='script.save' />")) {
				$ulLayout.each(function(idx1) {
					$(this).find("li").each(function(idx2) {
						//portletSet[idx1].push({portletId:$.data(this, "portletId"),layout:$.data(this, "layout"), seq:idx2});
						portletSet = portletSet + "&portletId=" + $.data(this, "portletId") + "&layout=" + $.data(this, "layout") + "&rowIndex=" + idx2;
					});
				});

				$jq.ajax({
					url : '<c:url value="/collpack/collaboration/workspacelayout/saveLayoutManage.do"/>?workspaceId=${workspaceId}'+portletSet,
					type : "post",
					loadingElement : {container:"#pageLodingDiv"},
					success : function(result) {

						//alert('<ikep4j:message pre='${prefix}' key='script.saveSuccess' />')
					},
					error: function(event, request) { 
						alert("error");

					}
				});	
		
			}
		});
		
			
	});
})(jQuery);
-->
</script>

<div id="pageLodingDiv">

<h1 class="none"><ikep4j:message pre='${prefix}' key='header.pageTitle' /></h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${prefix}' key='header.pageTitle' /></h2>
</div>
<!--//pageTitle End-->


<div class="layout_group">
	<!--layout Start-->
	<div class="blockLeft" style="width:70%">
		<div class="fixed_layout">
			<div class="Leftmenu_fixed">
				<div class="corner_RoundBox08 fixed01">
					<div class="fixed01_txt" style="margin-left:-46px; margin-top:-6px;"><ikep4j:message pre='${prefix}' key='detail.leftMenuFix' /></div>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
			</div>
		
			<div class="Collaboration_fixed">
				<div class="corner_RoundBox08 fixed02">
					<div class="fixed02_txt"><ikep4j:message pre='${prefix}' key='detail.workspaceIntroFix' /></div>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
				
				<div id="portletLayout" class="moveButton_table">
					<table summary="">
						<caption></caption>
						<tbody>
							<tr>
								<td width="50%" class="paddingR"><ul class="moveButton"></ul></td>
								<td width="50%"><ul class="moveButton"></ul></td>
							</tr>
						</tbody>
					</table>
				</div>
								
			</div>
		</div>	
	</div>
	<!--//layout End-->
	
	<!--layout_portlet Start-->
	<div class="blockRight" style="width:28%">
		<div class="layout_portletList02">
			<div class="portletList_title02"><strong><ikep4j:message pre='${prefix}' key='detail.portletList' /></strong></div>
			<div class="portletList_s02">
				<ul id="ulPortletItems">
					<c:if test="${workspacePortletList != null}">
					<c:forEach var="portletList" items="${workspacePortletList}" varStatus="status">
					<li><label><input type="checkbox" class="checkbox" title="${portletList.portletName}" value="${portletList.portletId}@${portletList.boardId}" /> ${portletList.portletName}</label></li>
					</c:forEach>
					</c:if>				
				</ul>
			</div>
		</div>
	</div>
	<!--//layout_portlet End-->
	<div class="clear"></div>
</div>

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a id="btnSave" class="button" href="#a"><span><ikep4j:message pre='${prefix}' key='button.save' /></span></a></li>
	</ul>
</div>
<!--//blockButton End-->

</div>
