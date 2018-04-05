<%--
=====================================================
	* 기능설명	:	cafe 내용 수정화면
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"	value="message.lightpack.cafe.layout" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript"> 
<!--

var currLayout = "${cafe.layoutId}";
var LEFT_LAYOUT = "CF_LAYOUT_01";
var RIGHT_LAYOUT = "CF_LAYOUT_02";

var activePortlets = [
	<c:if test="${cafePortletLayoutList != null}">
	<c:forEach var="layoutList" items="${cafePortletLayoutList}" varStatus="status">
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
	
	var $portlets;
	var $portletLayout;
	var $sortableLeft;
	var $sortableRight;
	var $ulLayout;
	var tplPortlet = $.template(null, '<li><div class="moveButton_2"><a class="move_btn02" href="#a" title="\${id}"><span>\${desc}</span></a></div></li>');
	
	
	function getPortletItem(portletId) {	
		
		var result = null;
		$portlets.each(function() {
			if(portletId == $(this).val()) {
				result = this;
				return false;
			}
		})
		return result;
	}
	
	function setSortable() {	
		
		$sortableLeft = $("#portletLayoutLeft").find("ul");
		$sortableLeft.sortable({
			revert: true,
			connectWith: $sortableLeft,
			placeholder: "ui-state-highlight",
			forcePlaceholderSize: true
		});
		
		$sortableRight = $("#portletLayoutRight").find("ul");
		$sortableRight.sortable({
			revert: true,
			connectWith: $sortableRight,
			placeholder: "ui-state-highlight",
			forcePlaceholderSize: true
		});
	}
	
	function makePortletLayout() {	
		
		$portlets = $("input", "#ulPortletItems");
		
		$ulLayout.children().remove();
		
		$jq('input[type=checkbox]:checked').attr("checked", false);
		
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
						$(this).after($appendPortlet);
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
		
	}
	
	
	$(document).ready(function() {
		
		if(currLayout == LEFT_LAYOUT) {
			$("#leftLayoutDiv").show();
			$("#rightLayoutDiv").hide();
			$portletLayout = $("#portletLayoutLeft");
		}
		else {
			$("#leftLayoutDiv").hide();
			$("#rightLayoutDiv").show();
			$portletLayout = $("#portletLayoutRight");
		}

		$portlets = $("input", "#ulPortletItems");
		
		$ulLayout = $portletLayout.find("ul");
		
		$("input[name=layout]").click(function(event) {	// 레이아웃 설정 radio 버튼 클릭하면...
			
			$newLayout = null;
			currLayout = $(this).val();
		
			if(currLayout == LEFT_LAYOUT) {
				$("#leftLayoutDiv").show();
				$("#rightLayoutDiv").hide();
				$portletLayout = $("#portletLayoutLeft");
				$ulLayout = $portletLayout.find("ul");
			}
			else {
				$("#leftLayoutDiv").hide();
				$("#rightLayoutDiv").show();
				$portletLayout = $("#portletLayoutRight");
				$ulLayout = $portletLayout.find("ul");
			}
			
			makePortletLayout();
			
		});
		
		$("#ulPortletItems").click(function(event) {
			if(event.target.tagName.toLowerCase() == "input") {
				$portletItem = $(event.target);
				if($portletItem.is(":checked")) {	// 생성
					$appendPortlet = $.tmpl(tplPortlet, {id:$portletItem.val(), desc:$portletItem.attr("title"), layout:"l"});
					$.data($appendPortlet[0], "portletId", $portletItem.val());
					$ulLayout.eq(0).append($appendPortlet);
				} else {
					$ulLayout.find("li").each(function() {
						if($.data(this, "portletId") == $portletItem.val()) {
							$(this).remove();
							return false;
						}
					});
				}
			}
			
			setSortable();
			
		});
		
		$("#btnSave").click(function() {
			var portletSet = "";// [[], []];
			
			if(confirm("<ikep4j:message pre='${prefix}' key='script.save' />")) {
				$ulLayout.each(function(idx1) {
					$(this).find("li").each(function(idx2) {
						portletSet = portletSet + "&portletId=" + $.data(this, "portletId") + "&layout=" + $.data(this, "layout") + "&rowIndex=" + idx2;
					});
				});

				$jq.ajax({
					url : '<c:url value="/lightpack/cafe/cafelayout/saveLayoutManage.do"/>?cafeId=${cafeId}&layoutId='+currLayout+portletSet,
					type : "post",
					loadingElement : {container:"#pageLodingDiv"},
					success : function(result) {
						alert('<ikep4j:message pre='${prefix}' key='script.saveSuccess' />')
					},
					error: function(event, request) { 
						alert("error");

					}
				});	
		
			}
		});

		makePortletLayout();
		
		setSortable();
			
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

	<div class="blog_layout_Stitle">
		<ul class="floatLeft">
			<li><span><ikep4j:message pre='${prefix}' key='layout.menu1' /></span></li>
		</ul>
		<div class="clear"></div>
	</div>
	
	<div class="blog_layout_select">
		<table summary="<ikep4j:message pre='${prefix}' key='layout.menu1' />">
			<caption></caption>
			<tbody>
				<tr>
					<c:if test="${cafeLayoutList != null}">
					<c:forEach var="layoutList" items="${cafeLayoutList}" varStatus="status">
					<td width="${100/cafeLayoutListSize}" class="borderR">
						<img src="<c:url value='${layoutList.imageUrl}' />" alt="${layoutList.layoutName}" />
						<div class="select_txt">
							<label>
								<input name="layout" type="radio" class="radio" title="${layoutList.layoutName}" value="${layoutList.layoutId}" <c:if test="${layoutList.layoutId == cafe.layoutId}">checked="checked"</c:if>/><c:out value="${layoutList.layoutName}" />
							</label>
						</div>
					</td>
					</c:forEach>
					</c:if>
				</tr>
			</tbody>
		</table>
	</div>
	<!--//layout_select End-->
	
	<div class="blog_layout_Stitle">
		<ul>
			<li><span><ikep4j:message pre='${prefix}' key='layout.menu2' /></span></li>
		</ul>
		<div class="clear"></div>
	</div>
						
	<!--layout Start-->
	<div class="blockLeft" style="width:70%">
		
		<!--left menu layout Start-->
		<div class="fixed_layout" id="leftLayoutDiv">
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
					<div class="fixed02_txt"><ikep4j:message pre='${prefix}' key='detail.cafeIntroFix' /></div>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
				
				<div id="portletLayoutLeft" class="moveButton_table">
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
		<!--//left menu layout End-->
		
		<!--right menu layout Start-->
		<div class="fixed_layout" id="rightLayoutDiv">
			<div class="Collaboration_fixed_right">
				<div class="corner_RoundBox08 fixed02">
					<div class="fixed02_txt"><ikep4j:message pre='${prefix}' key='detail.cafeIntroFix' /></div>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
				
				<div id="portletLayoutRight" class="moveButton_table">
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
			<div class="Rightmenu_fixed">
				<div class="corner_RoundBox08 fixed03">
					<div class="fixed01_txt" style="margin-left:-46px; margin-top:-6px;"><ikep4j:message pre='${prefix}' key='detail.rightMenuFix' /></div>
					<div class="l_t_corner"></div>
					<div class="r_t_corner"></div>
					<div class="l_b_corner"></div>
					<div class="r_b_corner"></div>
				</div>
			</div>
		
		</div>	
		<!--//right menu layout End-->
		
	</div>
	<!--//layout End-->
	
	<!--layout_portlet Start-->
	<div class="blockRight" style="width:28%">
		<div class="layout_portletList02">
			<div class="portletList_title02"><strong><ikep4j:message pre='${prefix}' key='detail.portletList' /></strong></div>
			<div class="portletList_s02">
				<ul id="ulPortletItems">
					<c:if test="${cafePortletList != null}">
					<c:forEach var="portletList" items="${cafePortletList}" varStatus="status">
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
