<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preManageMenu" value="ui.socialpack.socialblog.management.menu" />
<c:set var="preManageDesign" value="ui.socialpack.socialblog.management.designsetting" />
<c:set var="preManageStat" value="ui.socialpack.socialblog.management.statistics" />

<c:set var="preCode"    value="ui.socialpack.socialblog.common.code" />
<c:set var="preButton"  value="ui.socialpack.socialblog.common.button" /> 
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preSearch"  value="ui.socialpack.socialblog.common.searchCondition" />
<c:set var="preMessageSet"  value="message.socialpack.socialblog.management" />

<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />

<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<script type="text/javascript">
<!--

var $portlets;
var currLayout = "${socialBlog.layoutId}";	// 3 / 2l / 2r
var activePortlets = [
                  	<c:if test="${socialBlogPortletLayoutList != null}">
                	<c:forEach var="portletLayoutList" items="${socialBlogPortletLayoutList}" varStatus="ptStatus">
                	<c:choose>
                		<c:when test="${ptStatus.last}">
                			<c:if test="${portletLayoutList.colIndex == 1 }">
                				{id:"${portletLayoutList.portletId}", layout:"left", seq:"${portletLayoutList.rowIndex}"}
                			</c:if>
                			<c:if test="${portletLayoutList.colIndex != 1 }">
            					{id:"${portletLayoutList.portletId}", layout:"right", seq:"${portletLayoutList.rowIndex}"}
            				</c:if>
                		</c:when>
                		<c:otherwise>
	                		<c:if test="${portletLayoutList.colIndex == 1 }">
		        				{id:"${portletLayoutList.portletId}", layout:"left", seq:"${portletLayoutList.rowIndex}"},
		        			</c:if>
		        			<c:if test="${portletLayoutList.colIndex != 1 }">
		    					{id:"${portletLayoutList.portletId}", layout:"right", seq:"${portletLayoutList.rowIndex}"},
		    				</c:if>
                		</c:otherwise>
                	</c:choose>  
                	</c:forEach>
                	</c:if>
];

(function($){
	var $portletLayout;
	//<![CDATA[
	var htmlPortletLayout = '<div class="floatLeft move_btn"><ul style="min-width:118px; min-height:100px;"></ul></div>';
	var tplPortlet = $.template(null, '<li><a href="#a" title="\${id}">\${desc}</a></li>');
	//]]>
	
	function setPortletSortable() {
		var $divSortable = $portletLayout.children(".move_btn").children("ul");
		$divSortable.sortable({
			revert: true,
			connectWith: $divSortable,
			placeholder: "ui-state-highlight",
			forcePlaceholderSize: true
		});
	}
	
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
	
	$(document).ready(function(event) {
		$portletLayout = $("#portletLayout");
		$portlets = $("input", "#ulPortletItems");
		
		$("input[name=layout]").each(function() {
			if($(this).val() == currLayout) {
				$(this).attr("checked", true);
				switch($(this).val()) {
				
					<c:if test="${socialBlogLayoutList != null}">
						<c:forEach var="layoutList" items="${socialBlogLayoutList}" varStatus="status">
						case "${layoutList.layoutId}" :
							<c:choose>
		        				<c:when test="${layoutList.colCount == 2}">
									<c:forEach var="layoutListColumn" items="${layoutList.socialBlogLayoutColumnList}" varStatus="colStatus">
										<c:if test="${layoutListColumn.isFixed == 0}">
											<c:if test="${colStatus.index == 0}">
												$portletLayout.prepend(htmlPortletLayout);
												$portletLayout.children("div:eq(1)").addClass("move_fixed");
												$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed_L");
												$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c");
												$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed_R");
												
												//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c2").removeClass("move_fixed_c");
												break;
											</c:if>
											<c:if test="${colStatus.index == 1}">
												$portletLayout.append(htmlPortletLayout);
												$portletLayout.children("div:eq(0)").addClass("move_fixed1");
												$portletLayout.children("div:eq(0)").children("div:eq(0)").addClass("move_fixed_L");
												$portletLayout.children("div:eq(0)").children("div:eq(1)").addClass("move_fixed_c");
												$portletLayout.children("div:eq(0)").children("div:eq(2)").addClass("move_fixed_R");
												
												//$portletLayout.children("div:eq(0)").children("div:eq(1)").addClass("move_fixed_c2").removeClass("move_fixed_c");
												break;
											</c:if>
											
										</c:if>
									</c:forEach>
								</c:when>
								<c:otherwise>
									$portletLayout.append(htmlPortletLayout);
									$portletLayout.prepend(htmlPortletLayout);
									
									$portletLayout.children("div:eq(1)").addClass("move_fixed");
									$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed3_L");
									$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed3_c");
									$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed3_R");
									
									//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed_c2");
									break;
								</c:otherwise>
				       		</c:choose>  
						</c:forEach>
					</c:if>
				
				}
				return false;
			}
		})
		
		var $ulLayout = $portletLayout.children(".move_btn").children("ul");
		$.each(activePortlets, function(){	// 활성화 되어 있는 포틀릿 생성
			
			var $portletItem = $(getPortletItem(this.id)).attr("checked", true);
			var portletInfo = $.extend(this, {desc:$portletItem.attr("title")});
			var $ul = null;
			
			var $layoutId = $jq('input[name=layout].radio:checked').val() ;

			switch(portletInfo.layout) {
				case "left" : $ul = $ulLayout.eq(0); break;
				case "right" : if( $layoutId == 'SB_LAYOUT_02' ){ $ul = $ulLayout.eq(0); }else{ $ul = $ulLayout.eq(1); } break;
			}
			
			var $displayPortlets = $ul.children();
			var $appendPortlet = null;
			if($displayPortlets.length > 0) {	// 해당 영역에 포틀릿이 있으면 순번에 맞춰 추가
				$displayPortlets.each(function() {
					if(portletInfo.seq < $.data(this, "seq")) {
						$appendPortlet = $.tmpl(tplPortlet, portletInfo);
						$.data($appendPortlet[0], {portletId:portletInfo.id, seq:portletInfo.seq});
						$(this).before($appendPortlet);
						return false;
					}
				});
			}
			
			if($appendPortlet == null) {	// 포틀릿이 추가 되지 않았으면 해당 영역으 마지막에 추가
				$appendPortlet = $.tmpl(tplPortlet, portletInfo);
				$.data($appendPortlet[0], {portletId:portletInfo.id, seq:portletInfo.seq});
				$ul.append($appendPortlet);
			}
		});
		
		
		setPortletSortable();
		$("input[name=layout]").click(function(event) {	// 레이아웃 설정 radio 버튼 클릭하면...
			var layout = $(this).val();
			if(layout == currLayout) return false;
			
			$portletLayout.children(".move_btn").children("ul").sortable("destroy");	// layout 이 변경 되므로 sortable 삭제
			
			switch(layout) {
			
				<c:if test="${socialBlogLayoutList != null}">
				<c:forEach var="layoutList" items="${socialBlogLayoutList}" varStatus="status">
				case "${layoutList.layoutId}" :
					<c:choose>
					<c:when test="${layoutList.colCount == 2}">
						switch(currLayout) {
							<c:forEach var="layoutListColumn" items="${layoutList.socialBlogLayoutColumnList}" varStatus="colStatus">
								<c:if test="${layoutListColumn.isFixed == 0}">
									<c:if test="${colStatus.index == 0}">
										//2l 경우
										<c:forEach var="layoutList2l" items="${socialBlogLayoutList}" varStatus="status2l">
											<c:if test="${layoutList.layoutId != layoutList2l.layoutId}">
											case "${layoutList2l.layoutId}" :
												<c:choose>
													<c:when test="${layoutList2l.colCount == 2}">
														$portletLayout.children("div:eq(1)").prependTo($portletLayout); 
														//$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed_L").removeClass("move_fixed3_L");
														//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed3_c");
														//$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed_R").removeClass("move_fixed3_R");
														//break;
													</c:when>
													<c:otherwise>
														$portletLayout.children("div:eq(2)").find("li").appendTo($portletLayout.children("div:eq(0)").children()).end().end().remove();
														//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c2").removeClass("move_fixed_c");
														//$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed_L").removeClass("move_fixed3_L");
														//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed3_c");
														//$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed_R").removeClass("move_fixed3_R");
														//break;
													</c:otherwise>
								       			</c:choose>
								       			$portletLayout.children("div:eq(1)").addClass("move_fixed").removeClass("move_fixed1");
								       			$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed_L").removeClass("move_fixed3_L");
												$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed3_c");
												$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed_R").removeClass("move_fixed3_R");
												break;
								       		</c:if>
										</c:forEach>
									</c:if>
									<c:if test="${colStatus.index == 1}">
										// 2R 경우
										<c:forEach var="layoutList2r" items="${socialBlogLayoutList}" varStatus="status2r">
											<c:if test="${layoutList.layoutId != layoutList2r.layoutId}">
											case "${layoutList2r.layoutId}" :
												<c:choose>
													<c:when test="${layoutList2r.colCount == 2}">
														$portletLayout.children("div:eq(0)").appendTo($portletLayout); 
														//$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed_L").removeClass("move_fixed3_L");
														//$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed3_c");
														//$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed_R").removeClass("move_fixed3_R");
														//break;
													</c:when>
													<c:otherwise>
														$portletLayout.children("div:eq(0)").find("li").appendTo($portletLayout.children("div:eq(2)").children()).end().end().remove();
														//$portletLayout.children("div:eq(0)").children("div:eq(1)").addClass("move_fixed_c2").removeClass("move_fixed_c");
														//break;
													</c:otherwise>
									       		</c:choose> 
									       		$portletLayout.children("div:eq(0)").addClass("move_fixed1").removeClass("move_fixed");
									       		$portletLayout.children("div:eq(0)").children("div:eq(0)").addClass("move_fixed_L").removeClass("move_fixed3_L");
												$portletLayout.children("div:eq(0)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed3_c");
												$portletLayout.children("div:eq(0)").children("div:eq(2)").addClass("move_fixed_R").removeClass("move_fixed3_R");
												break;
								       		</c:if>
										</c:forEach>
										
									</c:if>
								</c:if>
							</c:forEach>
						}
						
						break;
					</c:when>
					<c:otherwise>
						switch(currLayout) {
						<c:forEach var="layoutList3" items="${socialBlogLayoutList}" varStatus="status3">
							<c:if test="${layoutList.layoutId != layoutList3.layoutId}">
							case "${layoutList3.layoutId}" :
								<c:forEach var="layoutList3Column" items="${layoutList3.socialBlogLayoutColumnList}" varStatus="colStatus">
								<c:if test="${layoutList3Column.isFixed == 0}">
									<c:if test="${status3.index == 0}">
										$portletLayout.append(htmlPortletLayout); break;
									</c:if>
									<c:if test="${status3.index == 1}">
										$portletLayout.prepend(htmlPortletLayout); break;
									</c:if>
								</c:if>
								</c:forEach>
							</c:if>
						</c:forEach>
						}
						
						$portletLayout.children("div:eq(1)").addClass("move_fixed").removeClass("move_fixed1");
						$portletLayout.children("div:eq(1)").children("div:eq(0)").addClass("move_fixed3_L").removeClass("move_fixed_L");
						$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed3_c").removeClass("move_fixed_c");
						$portletLayout.children("div:eq(1)").children("div:eq(2)").addClass("move_fixed3_R").removeClass("move_fixed_R");
						$portletLayout.children("div:eq(1)").children("div:eq(1)").addClass("move_fixed_c").removeClass("move_fixed_c2");
						break;
					</c:otherwise>
		       		</c:choose> 
				</c:forEach>
				</c:if>

			}
			currLayout = layout;
			setPortletSortable();	// sortable 재설정
		});
		
		$("#ulPortletItems").click(function(event) {
			if(event.target.tagName.toLowerCase() == "input") {
				$portletItem = $(event.target);
				if($portletItem.is(":checked")) {	// 생성
					var $ul = $portletLayout.children(".move_btn").children("ul").eq(0);
					$appendPortlet = $.tmpl(tplPortlet, {id:$portletItem.val(), desc:$portletItem.attr("title")});
					$.data($appendPortlet[0], "portletId", $portletItem.val());
					$ul.append($appendPortlet);
				} else {
					$portletLayout.children(".move_btn").children("ul").find("li").each(function() {
						if($.data(this, "portletId") == $portletItem.val()) {
							$(this).remove();
							return false;
						}
					});
				}
			}
		});
		
		$("#btnSave").click(function() {
			var portletSet = "";
			if(confirm("<ikep4j:message pre='${preMessageSet}' key='afterUpdate.save' />")) {

				var $layouts = $portletLayout.children(".move_btn");
				$layoutId = $jq('input[name=layout].radio:checked').val() ;
				var colIdx = 1;
				if( $layoutId == 'SB_LAYOUT_02' ){
					colIdx++;
				}
				portletSet = portletSet + "blogOwnerId=${socialBlog.ownerId}&layoutId=" +$layoutId;
				$layouts.each(function() {
					var $layout = $(this);
					$layout.find("li").each(function(idx) {
						portletSet = portletSet + "&portletId=" + $.data(this, "portletId") + "&colIndex=" + colIdx + "&rowIndex=" + idx
						//arr.push({portletId:$.data(this, "portletId"), colIdx:colIdx, seq:idx});
					});
					colIdx++;
				});

				$jq.post(
							'<c:url value="/socialpack/socialblog/setting/saveSocialLayoutManage.do"/>', 
							portletSet
							//$jq.parseJSON(portletSet)
							//{'11':'11','portletId[0]':'SB_PORTLET_01','colIndex':'1','seq':'0','portletId[1]':'SB_PORTLET_03','colIndex':'2','seq':'0','portletId':'SB_PORTLET_04','colIndex':'2','seq':'1','portletId':'SB_PORTLET_02','colIndex':'2','seq':'2','portletId':'SB_PORTLET_05','colIndex':'2','seq':'3','portletId':'SB_PORTLET_06','colIndex':'2','seq':'4'}
						)
			        .success(function(data) {
						alert("<ikep4j:message pre='${preMessageSet}' key='save.success' />");
					})
			        .error(function(event, request, settings) { alert("error"); });

			}
		});
	});
	
	// 카테고리 관리 메뉴 조회
	getCategoryManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialblogSettingHome.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getLayoutManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialLayoutManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getBackgroundManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialBackgroundManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
	// 카테고리 관리 메뉴 조회
	getVisitorManagement = function(blogOwnerId) {
		document.location.href = "<c:url value='/socialpack/socialblog/setting/socialVisitorManage.do?blogOwnerId=' />"+blogOwnerId ;
	};
	
})(jQuery);

-->
</script>

	<!--popup_title Start-->
	<div id="popup_title">
		<h1><ikep4j:message pre='${preManage}' key='title' /></h1>
		<a href="javascript:iKEP.returnPopup();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->

	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!-- Modal window Start -->
		<!-- 사용시class="none"으로 설정 -->
		<div class="" id="layer_p" title="<ikep4j:message pre='${preManage}' key='title' />">
		
			<div class="blog_layout">
				
				<!--leftMenu Start-->
				<div class="floatLeft blog_left">
					<h1 class="none"><ikep4j:message pre='${preManageMenu}' key='leftmenu' /></h1>
					<div class="blog_leftmenu">	
						<ul>
							<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='title' /></a>
								<ul>
									<li><a onclick="getCategoryManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageMenu}' key='category.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='title' /></a>
								<ul>
									<li class="licurrent"><a onclick="getLayoutManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='layout.title' /></a></li>
									<li><a onclick="getBackgroundManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageDesign}' key='background.title' /></a></li>
								</ul>
							</li>
							<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='title' /></a>
								<ul>
									<li><a onclick="getVisitorManagement('${socialBlog.ownerId}');" href="#a"><ikep4j:message pre='${preManageStat}' key='visitor.title' /></a></li>
								</ul>
							</li>
						</ul>
					</div>
				</div>
				<!--//leftMenu End-->
				
				<div class="floatRight" style="width:724px;">
					<div class="blog_layout_contents">
						
						<!--layout_select Start-->
						<div class="blog_layout_Stitle">
							<ul class="floatLeft">
								<li><span><ikep4j:message pre='${preManageDesign}' key='layout.menu1' /></span></li>
							</ul>
							<div class="floatRight">
								<a id="btnSave" class="button_s" href="#a"><span><ikep4j:message pre='${preButton}' key='save' /></span></a>
							</div>
							<div class="clear"></div>
						</div>
						
						<div class="blog_layout_select">
							<table summary="<ikep4j:message pre='${preMain}' key='selectLayout' />">
								<caption></caption>
								<tbody>
									<tr>
										<!-- 
										<td width="25%" class="Ytd">
											<img src="<c:url value='/base/images/common/layout_p01.gif' />" alt="<ikep4j:message pre='${preManageDesign}' key='layout.nowUseLayout' />" />
											<div class="select_txt"><ikep4j:message pre='${preManageDesign}' key='layout.nowUseLayout' /></div>	
										</td>
										-->
										<c:if test="${socialBlogLayoutList != null}">
										<c:forEach var="layoutList" items="${socialBlogLayoutList}" varStatus="status">
										<td width="${100/socialBlogLayoutListSize}" class="borderR">
											<img src="<c:url value='${layoutList.imageUrl}' />" alt="${layoutList.layoutName}" />
											<div class="select_txt">
												<label>
													<input name="layout" type="radio" class="radio" title="${layoutList.layoutName}" value="${layoutList.layoutId}" /><c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${layoutList.layoutName}"/></c:when><c:otherwise><c:out value="${layoutList.layoutEnglishName}"/></c:otherwise></c:choose> 
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
						
						<!--layout_portlet Start-->
						<div class="blog_layout_Stitle">
							<ul>
								<li><span><ikep4j:message pre='${preManageDesign}' key='layout.menu2' /></span></li>
							</ul>
							<div class="clear"></div>
						</div>
						
						<div class="layout_portlet">
							<div class="floatLeft layout_portletList">
								<div class="portletList_title"><ikep4j:message pre='${preManageDesign}' key='layout.portletList' /></div>
								<div class="portletList_s">
									<ul id="ulPortletItems">
									    <c:if test="${socialBlogPortletList != null}">
										<c:forEach var="portletList" items="${socialBlogPortletList}" varStatus="status">
										<li><label><input class="checkbox" title="<c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${portletList.portletName}"/></c:when><c:otherwise><c:out value="${portletList.portletEnglishName}"/></c:otherwise></c:choose>" type="checkbox" value="${portletList.portletId}"  <c:if test="${portletList.isDefault == 1}">checked="checked" disabled="disabled"</c:if> /> <c:choose><c:when test="${user.localeCode == portal.defaultLocaleCode}"><c:out value="${portletList.portletName}"/></c:when><c:otherwise><c:out value="${portletList.portletEnglishName}"/></c:otherwise></c:choose></label></li>
										</c:forEach>
										</c:if>
									</ul>
								</div>
							</div>
							<div id="portletLayout" class="floatRight layout_portlet_move">
								<div class="floatLeft">
									<div class="floatLeft"></div>
									<div class="floatLeft"><span><ikep4j:message pre='${preManageDesign}' key='layout.fixedContents' /></span></div>
									<div class="floatLeft"></div>
								</div>
							</div>
						</div>
						<!--//layout_portlet End-->
					</div>
				</div>
				
			</div>
			
		</div>	
		<!-- //Modal window End -->
	</div>