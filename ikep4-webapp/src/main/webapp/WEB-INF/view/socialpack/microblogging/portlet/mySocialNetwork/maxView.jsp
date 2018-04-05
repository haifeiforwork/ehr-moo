<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%>
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%>
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%>
<c:set var="preTap" value="ui.socialpack.microblogging.tap" />
<c:set var="preMessage" value="ui.socialpack.microblogging.message" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--
	(function($) {

		// onload시 수행할 코드
		$jq(document).ready(function() {

			$("#divTab_s4").tabs();

		});

		// 현재 탭과 그 탭안에서의 항목
		var nowDiv = "mb_tabs-1";

		mbTabsClickEvent = function(div) {
			nowDiv = div;

			//alert(div);
			//alert($jq("li", "#"+nowDiv).length);
			// Following tab 클릭시 
			if ("mb_tabs-1" == nowDiv) {
				// 기존에 조회된 게 없으면 조회.
				if (0 == $jq("li", "#" + nowDiv).length) {
					getFollowUser('pre');
				}
			}// Follower tab 클릭시 
			else if ("mb_tabs-2" == nowDiv) {
				// 기존에 조회된 게 없으면 조회.
				if (0 == $jq("li", "#" + nowDiv).length) {
					getFollowUser('pre');
				}
			}
		};

		// follow관련 user list 볼 때.
		getFollowUser = function(standardType) {
			//alert(searchString);
			//alert(standardType);
			//alert(nowDiv);
			var standardUserId = '';
			try {
				if ("pre" == standardType) {
					standardUserId = $jq("li", "#" + nowDiv).last().attr("id")
							.replace('userUserId_', '');
				} else {
					standardUserId = $jq("li", "#" + nowDiv).first().attr("id")
							.replace('userUserId_', '');
				}
			} catch (e) {	}
			//alert(standardUserId);
			//alert('${ownerId}');

			var url = "";

			// Following 조회
			if ("mb_tabs-1" == nowDiv) {
				url = "<c:url value='/socialpack/microblogging/portlet/mySocialNetwork/getFollowingList.do'/>";
			} // Follower 조회
			else if ("mb_tabs-2" == nowDiv) {
				url = "<c:url value='/socialpack/microblogging/portlet/mySocialNetwork/getFollowerList.do'/>";
			}

			//alert(url);
			//alert(nowDiv);
			//alert(bothFollow);
			$jq.get(
						url,
						// 조회해온 데이터를 앞/뒤에 붙일건지, 	조회기준 UserId
						{
							'standardType' : standardType,
							'standardUserId' : standardUserId,
							'nowSize' : $jq("li", "#" + nowDiv).length
						},
						function(data) {
							if ("" != data) {
								data = $jq.trim(data);
							}
							if ("" == data
									&& "" == $jq.trim($jq("ul",
											"#" + nowDiv).html())) {
								data = "<li><ikep4j:message pre='${preMessage}' key='list.empty' /></li>";
							}

							//iKEP.debug(data);
							if ("pre" == standardType) {
								$jq("ul", "#" + nowDiv).append(data);
							} else {
								$jq("ul", "#" + nowDiv).prepend(data);
							}
						});
		};

		setMoreDiv = function(type) {

			var followingCount = eval($jq("#followingCount").val());
			var followerCount = eval($jq("#followerCount").val());

			var nowLength = $jq("li", "#" + nowDiv).length;

			//alert(followingCount);
			//alert(followerCount);
			//alert(nowLength);

			// Following tab
			if ("mb_tabs-1" == nowDiv) {
				if (nowLength < followingCount) {
					$jq("#moreDiv1").show();
				} else {
					$jq("#moreDiv1").hide();
				}
			}// Follower tab  
			else if ("mb_tabs-2" == nowDiv) {
				if (nowLength < followerCount) {
					$jq("#moreDiv2").show();
				} else {
					$jq("#moreDiv2").hide();
				}
			}

		};

	})(jQuery);
//-->
</script>
<input name="followingCount" id="followingCount" type="hidden" value="" />
<input name="followerCount" id="followerCount" type="hidden" value="" />

<div id="${portletConfigId}" class="imgList_2">

	<div id="divTab_s4" class="iKEP_tab_s">
		<ul>
			<li><a href="#mb_tabs-1" onclick="javascript:mbTabsClickEvent('mb_tabs-1');"><ikep4j:message pre='${preTap}' key='following' /></a></li>
			<li><a href="#mb_tabs-2" onclick="javascript:mbTabsClickEvent('mb_tabs-2');"><ikep4j:message pre='${preTap}' key='followers' /></a></li>
		</ul>
		<div id="mb_tabs-1">
			<ul class="imgList_1_max" ></ul>
			<div class="clear"></div>				
			<!--blockButton_3 Start-->
			<div class="blockButton_3 imgList_no mt10" id="moreDiv1" onclick="javascript:getFollowUser('pre');"> 
				<a class="button_3" href="#a"><span><ikep4j:message	pre='${preMessage}' key='list.more' /> <img	src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt=""/></span></a>				
			</div>
			<!--//blockButton_3 End-->			
		</div>
		<div id="mb_tabs-2">
			<ul class="imgList_1_max" ></ul>
			<div class="clear"></div>				
			<!--blockButton_3 Start-->
			<div class="blockButton_3 imgList_no mt10" id="moreDiv2" onclick="javascript:getFollowUser('pre');"> 
				<a class="button_3" href="#a"><span><ikep4j:message	pre='${preMessage}' key='list.more' /> <img	src="<c:url value="/base/images/icon/ic_more_ar.gif" />" alt=""/></span></a>				
			</div>
			<!--//blockButton_3 End-->			
		</div>
	</div>

</div>
<script type="text/javascript">
<!--
	mbTabsClickEvent('mb_tabs-1');
//-->
</script>
