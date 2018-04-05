<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- 메시지 관련 Prefix 선언 Start --%>  
<c:set var="preTree"    value="ui.lightpack.award.common.leftAwardView" /> 
<c:set var="prefix"    value="ui.lightpack.award.awardCommon.awardView" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
 <%-- 메시지 관련 Prefix 선언 End --%> 
<script type="text/javascript">
<!--   
function resizeIframe() { 
	iKEP.iFrameContentResize();  
}
function topScroll() { 
	$jq(window).scrollTop(0);
} 

function iframeReflash(url) {
	("#contentIframe").attr("src", url); 
}

// iframe의 게시판 목록화면에서 호출됨 : 불필요해 보임(?)
function menuMark(awardId) {
	return false;
	var $li = null;
	$jq("#awardList").find("a.award").each(function() {
		var $anchor = $jq(this);
		if($anchor.data("awardId") == awardId) {
			$li = $anchor.parent();
			return false;
		}
	});
	
	var $currLi = $jq("#awardList").find("li.licurrent").has(".no_child");
	if($li != null && $li[0] != $currLi[0]) {
		$jq("#awardList").find("li.award").has(".licurrent").removeClass("licurrent");
		
		$li.addClass("licurrent");
		var $parent = $li.parents("li.award", "#awardList");
		if($parent.size() > 0) {
			$parent.addClass("licurrent");
			$parent.children("ul").show();
		}
	}
}

function menuSetCss(awardId) {
	$jq("#awardList").find("a.award").each(function() {
		var $anchor = $jq(this);
		if($anchor.data("awardId") == awardId) {
			$anchor.trigger("click");
			return false;
		}
	});
}

function wResize(){
	var iframe = $jq("#contentIframe");
	var windowHeight = $jq(window).height();
	var conHeight = iframe.contents().height();
	
	if(windowHeight > conHeight){		
		iframe.height(windowHeight-19);
	}else{
		iframe.height(conHeight+3);
	}
}

function changeMenuType(isTree) {
	var $container = $jq("#awardList").empty();
	
	if(isTree) {
		setBbsTree(true);
	} else {
		$container.jstree("destroy");
		setBbsMenu(true);
	}
}

//게시판 아이템 구분 아이콘
function getAwardItemTypeHtml(awardType) {
	var itemClass = "";
	switch(awardType) {
		case 0 : itemClass = "award"; break;
		case 1 : itemClass = "link"; break;
		case 2 : itemClass = "category"; break;
	}
	return '<ins class="' + itemClass + '"/>';
}

var setBbsTree, setBbsMenu;
(function($){	 
	$(document).ready(function() {
		<c:choose>
			<c:when test="${isMenuTree == true}">setBbsTree();</c:when>
			<c:otherwise>setBbsMenu();</c:otherwise>
		</c:choose>
		
		$(window).bind("resize", wResize); 
		
		$("#awardAdminViewButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardAdmin/listAwardView.do?awardRootId=${awardRootId}&popupYn=${popupYn}'/>");  
		}); 
		$("#deleteAwardItemViewButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardItem/deleteAwardItemListView.do'/>");  
		}); 
		$("#awardMenuAdminForMobileButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardItem/getAwardMenuList.do'/>");  
		}); 
		
		$("#tempSaveButton").click(function() {   
			iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardItem/listTempSaveItemView.do'/>");  
		});
		
	});
	
	var awardList = [];
	<c:forEach var="award" varStatus="varStatus" items="${awardList}">
	awardList.push({awardId:"${award.awardId}", awardType:${award.awardType}, awardLevel:${award.indentation}, cntChild:${award.hasChildren}, awardName:"${user.localeCode == portal.defaultLocaleCode ? award.awardName : award.awardEnglishName}"});
	</c:forEach>
	
	function clickAward(awardId, awardType) {
		switch(parseInt(awardType, 10)) {
			case 0 :	// 게시판
				iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardItem/listAwardItemView.do'/>?awardId=" + awardId + "&popupYn=${popupYn}&isSystemAdmin=${isSystemAdmin}");
				break;
			case 1 :	// 링크
				$("#contentIframe").attr("src", "<c:url value='/lightpack/award/awardCommon/readLinkAwardView.do'/>?awardId=" + awardId + "&popupYn=${popupYn}");
				break;
			case 2 :	// 카테고리
				break;
		}
	}
	
	setBbsMenu = function(isInitial) {	// normal menu
		$("#awardList").addClass("left_fixed");
		if(awardList.length > 0) {
			var $container = $("<ul/>").appendTo("#awardList");
			var $parents = [];	// li
			$.each(awardList, function(idx, item) {
				var $anchor = $("<a href=\"#a\" class=\"award\">" + this.awardName + "</a>").data({awardId:this.awardId, awardType:this.awardType, cntChild:this.cntChild, awardLevel:this.awardLevel});
				var $item = $("<li class=\"award\"/>").append($anchor);
				if(idx == 0) $item.addClass("liFirst");
				$item.addClass(this.cntChild > 0 ? "opened" : "no_child");
				
				if(this.awardLevel == 1) {
					$container.append($item);
					$parents[0] = $item;	//li
				} else {
					switch(this.awardLevel - $parents[this.awardLevel-2].children("a").data("awardLevel")) {
						case 1 :	// parent의 하위 레벨
							$parents[this.awardLevel-1] = $item;	
							if($parents[this.awardLevel-2].children("ul").length > 0) $parents[this.awardLevel-2].children("ul").append($item);
							else $("<ul/>").appendTo($parents[this.awardLevel-2]).append($item);
							break;
						case -1 :	// parent의 상위 레벨
							$parents[this.awardLevel-2].children("ul").append($item);
							break;
						case 0 :	// parent와 동일 레벨
							$parents[this.awardLevel-2].children("ul").append($item);
							break;
					} 
				}
				
				// 아이템 구분
				$anchor.prepend(getAwardItemTypeHtml(this.awardType));
			});
			
			iKEP.setLeftMenu();
			
			$("#awardList").click(function(event) {
				var $anchor = $(event.target);
				if(event.target.tagName.toLowerCase() == "a" && $anchor.hasClass("award")) {
					var awardInfo = $anchor.data();
					switch(awardInfo.awardType) {
						case 0 :	// award
						case 1 :	// link
							clickAward(awardInfo.awardId, awardInfo.awardType);
							break;
						case 2 : //category
					}
				}
			});
			
			if(isInitial != true) {
				<c:choose>
					<c:when test="${not empty itemId}">
						iKEP.iFrameMenuOnclick("<c:url value='/lightpack/award/awardItem/readAwardItemLinkView.do'/>?itemId=${itemId}&popupYn=${popupYn}"); 
					</c:when>
					<c:when test="${not empty awardId}">
						menuSetCss(${awardId});
					</c:when>
					<c:otherwise>
						var firstLoadAward = $("li.no_child::eq(0) a", $container).data();
						if(firstLoadAward) {
							menuSetCss(firstLoadAward.awardId);
						}
					</c:otherwise> 
				</c:choose>
			}
		}
	};
	
	setBbsTree = function(isInitial) {
		var bbsList = [];
		var parent = [];
		var fnToData = function(orgData) {
			var icon = "category";
			switch(orgData.awardType) {
				case 0 : icon = "award"; break;
				case 1 : icon = "link"; break;
			}
			return {
				data:{title:orgData.awardName, icon : icon},
				attr:{id:"BBSItem_"+orgData.awardId, award_type:orgData.awardType},
				awardLevel:orgData.awardLevel
			};
		};
		$.each(awardList, function() {
			var treeData = fnToData(this);
			if(treeData.awardLevel == 1) {
				bbsList.push(treeData);
				parent[0] = treeData;
			} else {
				switch(treeData.awardLevel - parent[treeData.awardLevel-2].awardLevel) {
					case 1 :	// parent의 하위 레벨
						parent[treeData.awardLevel-1] = treeData;	
						if(parent[treeData.awardLevel-2]["children"] == undefined) parent[treeData.awardLevel-2]["children"] = new Array(treeData);
						else parent[treeData.awardLevel-2]["children"].push(treeData);
						break;
					case -1 :	// parent의 상위 레벨
						parent[treeData.awardLevel-2]["children"].push(treeData);
						parent[treeData.awardLevel-1] = treeData;
						break;
					case 0 :	// parent와 동일 레벨
						parent[treeData.awardLevel-2]["children"].push(treeData);
						break;
				} 
			}
		});
		
		$("#awardList").removeClass("left_fixed")
			.bind("loaded.jstree", function(event) {
				
				$(this)//.jstree("hide_icons")
					.jstree("open_all");
				var tree = $.jstree._reference(this);
				
				if(isInitial != true) {
					var $items = tree._get_children(-1),
						$firstChild;
	 				while($items.size() > 0) {
	 					$firstChild = $items.eq(0);
						$items = tree._get_children($firstChild);
	 				}
	 				
/*	 				$(this).find("li").each(function(event) {
// 	 					$(this).children("a").prepend(getAwardItemTypeHtml(parseInt($(this).attr("award_type"), 10)));
	 					$(this).children("a").children("ins").removeClass("jstree-icon").addClass("award");
	 				});*/
	 				
	 				$firstChild.children("a").trigger("click");	// 첫번째 아이템 클릭
				}
			}).jstree({
				plugins:["themes", "ui", "json_data"],
				json_data:{data:bbsList}
			}).bind("select_node.jstree", function(event, data) {
				var tree = jQuery.jstree._reference(this);
				var $li = data.rslt.obj;
				switch($li.attr("award_type")) {
					case "0" :
					case "1" :
						var awardId = $li.attr("id").split("_")[1];
						clickAward(awardId, $li.attr("award_type"));
						break;
				}
			});
	};
})(jQuery);
//-->
</script>
<!--leftMenu Start-->   
	<h1 class="none"><ikep4j:message pre="${preTree}" key="tree" /></h1> 
	<h2 class="han">
	<a href="<c:url value='/lightpack/award/awardCommon/awardView.do?awardRootId=0'/>">
		<span><img style="vertical-align:bottom;" src="<c:url value='/base/images/title/lmt_coll.gif'/>"/></span>
		<font size="4" style="font-weight: bold;color:#4374D9;font-family: 맑은 고딕;vertical-align: bottom;">수상자료관리</font>
	</a>
</h2>
	
	<div id="awardList" class="left_fixed"></div> 
	<%-- <div>	
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${prefix}" key="myItem" /></p>				
		</div>
		<div class="boxList_sub">
			<ul>
				<li class="liFirst no_child"><a id="tempSaveButton" href="#a"><ikep4j:message pre="${prefix}" key="tempSave" /></a></li> 
			</ul>
		</div>	 
	</div>   --%>

	<input type="hidden" id="isSystemAdmin" <c:if test="${isSystemAdmin}"> value="1" </c:if><c:if test="${!isSystemAdmin}"> value="0" </c:if> />

	<c:if test="${isSystemAdmin and not popupYn}"> 	    
	<div>	
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${preTree}" key="administrator" /></p>				
		</div>
		<div class="boxList_sub">
			<ul> 
				<li class="liFirst no_child"><a id="awardAdminViewButton" href="#a"><ikep4j:message pre="${preTree}" key="awardManagement" /></a></li> 
				<!-- <li class="liFirst no_child"><a id="deleteAwardItemViewButton" href="#a">삭제 게시물 관리</a></li>  -->
			</ul>
		</div>	 
	</div>
	</c:if>
	 
	<div>
		<div class="boxList mt-1"><!--상위의 라인과 겹치는 경우만 mt-1을 입력함-->
			<p><ikep4j:message pre="${preTree}" key="awardMenuAdmin" /></p>				
		</div>
		<div class="boxList_sub">
			<ul> 
				<li class="liFirst no_child"><a id="awardMenuAdminForMobileButton" href="#a"><ikep4j:message pre="${preTree}" key="awardMenuAdminForMobileButton" /></a></li> 
			</ul>
		</div>
	</div>

		