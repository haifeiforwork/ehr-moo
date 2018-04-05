<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="sessionPortal" value="${sessionScope['ikep.portal']}" />
<c:set var="sessionUser" value="${sessionScope['ikep.user']}" />

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preTap"    	value="ui.socialpack.microblogging.tap" />
<c:set var="preButton"  value="ui.socialpack.microblogging.button" />
<c:set var="preLink"  	value="ui.socialpack.microblogging.link" /> 
<c:set var="preLabel" 	value="ui.socialpack.microblogging.label" />
<c:set var="preMessage"	value="ui.socialpack.microblogging.message" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- Chart 관련 css, js Start --%> 
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/plot_chart/jquery.jqplot.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jquery.jqplot.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.dateAxisRenderer.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/jqplot.highlighter.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/plot_chart/excanvas.min.js"/>"></script>
<%-- Chart 관련 css, js End --%>
<script type="text/javascript">

(function($) {
	var dropdownTabs;
	$(document).ready(function() {
		//iKEP.debug(${retweetStatistics});
		<c:if test="${not empty retweetStatistics }">
			s1 = ${retweetStatistics};
			
			$.jqplot.config.enablePlugins = true;
			
			plot1 = $.jqplot('chart1',[s1],{
		       axes: {
		           xaxis: {
		               min:0,
		               max:10,
		               tickOptions: {
		                   formatString: '%.0f'  // x축 하단 표시 설정
		               },
		               numberTicks: 11  // x축 하단 표시 갯수
		           },
		           yaxis: {
		               min:0,
		               tickOptions: {
		                   formatString: '%.0f'  // y 하단 표시 설정
		               }
		           }
		       },
		       highlighter: {
		           sizeAdjust: 10,
		           tooltipLocation: 'e',
		           tooltipAxes: 'y',    
		           useAxesFormatters: false,
		           tooltipFormatString: '<b><i><span style="color: red;">count</span></i></b> %.0f'     // 표시 박스 설정
		       }
		   });
		</c:if>
	});
	// $(document).ready END
})(jQuery);
</script>
	<!-- tweet한 글의 원글과 답글 리스트 -->
	<c:forEach var="mblog" items="${mblogList}" varStatus="loopStatus">
		<c:if test="${mblog.mblogId == selectedMblogId}">
		<div class="microblog_layer_top">
			<div class="microblog_img">
				<a href="#a" name="userInfo" id="${mblog.registerId}">
					<!-- //아이디에 해당하는 사진정보. -->
					<img src="<c:url value='${mblog.profilePicturePath}' />" width="50" height="50" alt="" title="${mblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
				</a>
			</div>
			<div class="microblog_con">
				<span class="microblog_id_2"><a href="#a">@${mblog.registerId}</a></span>
				<div class="microblog_name">
					<c:choose>
						<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
							<c:out value="${mblog.registerName}"/>
						</c:when>
						<c:otherwise>
							<c:out value="${mblog.registerEnglishName}"/>
						</c:otherwise>
					</c:choose>
				</div>
			</div>
			<div class="microblog_ph"><c:out value="${mblog.contentsDisplay}" escapeXml="false"/></div>
			<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)}</span>
			<span class="microblog_icon">
				<span class="microblog_ic_favorite"><a href="#a" onclick="regFavorite('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='favorite' /></a></span>
				<c:if test="${sessionUser.userId != mblog.registerId && 1 == mblog.isRetweetAllowed}">
					<span class="microblog_ic_retweet"><a href="#a" onclick="retwitPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='retweet' /></a></span>
				</c:if>
					<span class="microblog_ic_reply"><a href="#a" onclick="replyPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='reply' /></a></span>
				<c:if test="${sessionUser.userId == mblog.registerId}">
					<span class="microblog_ic_delete"><a href="#a" onclick="removeMblog('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='delete' /></a></span>
				</c:if>
			</span>
		</div>
		</c:if>
		<c:if test="${mblog.mblogId != selectedMblogId}">
			<ul class="microblog_li">
				<li id="timelineMblogId_${mblog.mblogId}" name="timelineThreadId_${mblog.threadId}">
					<div class="microblog_img_2">
						<a href="#a" name="userInfo" id="${mblog.registerId}">
							<!-- //아이디에 해당하는 사진정보. -->
							<img src="<c:url value='${mblog.profilePicturePath}' />" width="50" height="50" alt="" title="${mblog.registerId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
						</a>
					</div>
					<div class="microblog_con_2">
						<span class="microblog_id"><a href="#a" name="userInfo" id="${mblog.registerId}"><c:out value="${mblog.registerId}"/></a></span>
						<span class="microblog_name">
							<c:choose>
								<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
									<c:out value="${mblog.registerName}"/>
								</c:when>
								<c:otherwise>
									<c:out value="${mblog.registerEnglishName}"/>
								</c:otherwise>
							</c:choose>
						</span>
						<p><c:out value="${mblog.contentsDisplay}" escapeXml="false"/></p>
						<span class="microblog_time">${ikep4j:getTimeInterval(mblog.registDate, sessionUser.localeCode)} </span>
						<span class="microblog_icon none">
								<span class="microblog_ic_favorite"><a href="#a" onclick="regFavorite('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='favorite' /></a></span>
							<c:if test="${sessionUser.userId != mblog.registerId && 1 == mblog.isRetweetAllowed}">
								<span class="microblog_ic_retweet"><a href="#a" onclick="retwitPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='retweet' /></a></span>
							</c:if>
								<span class="microblog_ic_reply"><a href="#a" onclick="replyPop('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='reply' /></a></span>
							<c:if test="${sessionUser.userId == mblog.registerId}">
								<span class="microblog_ic_delete"><a href="#a" onclick="removeMblog('${mblog.mblogId}');"><ikep4j:message pre='${preButton}' key='delete' /></a></span>
							</c:if>
						</span>
					</div>
					<div class="selected_ar"></div>
				</li>
			</ul>			
		</c:if>		
	</c:forEach>
	
	<!-- retweet한 사용자 이미지 리스트 -->
	<div class="retweetlist">
		<h3><ikep4j:message pre='${preLabel}' key='retweetedby'  /> ${retweetUserListSize} <ikep4j:message pre='${preLabel}' key='others'/></h3>
		<ul>
			<c:forEach var="retweetUser" items="${retweetUserList}" varStatus="loopStatus">
				<a href="#a">
				<!-- //아이디에 해당하는 사진정보. -->
				<img src="<c:url value='${retweetUser.profilePicturePath}' />" width="50" height="50" alt="" title="${retweetUser.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
				</a>
			</c:forEach>																																										
		</ul>
	</div>	
	
	<!-- reteew 통계 -->
	<div class="retweetlist">
		<h3><ikep4j:message pre='${preLabel}' key='retweets'  /></h3>
		<c:if test="${not empty retweetStatistics }">
			<div id="chart1" style="margin-top:20px; margin-left:20px; margin-bottom:20px; width:90%; height:120px;"></div>
		</c:if>
	</div>
	
	<!-- tweet 글에 맨션된 사용자 정보 리스트 -->
	<div class="retweetlist">
		<h3><ikep4j:message pre='${preLabel}' key='mentionedinthisTweet'  /></h3>
		<ul>
			<c:forEach var="mentionedUser" items="${mentionedUserList}" varStatus="loopStatus">
				<div>
					<div class="microblog_img">
						<a href="<c:url value='/socialpack/microblogging/privateHome.do?ownerId=${mentionedUser.userId}'/>">
							<!-- //아이디에 해당하는 사진정보. -->
							<img src="<c:url value='${mentionedUser.profilePicturePath}' />" width="50" height="50" alt="" title="${mentionedUser.userId}" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
						</a>
					</div>
					<div class="microblog_con">
						<div class="microblog_me">
							<c:choose>
								<c:when test="${sessionUser.localeCode == sessionPortal.defaultLocaleCode}">
									${mentionedUser.teamName}  
									&nbsp;<strong>${mentionedUser.userName}</strong> 
								</c:when>
								<c:otherwise>
									${mentionedUser.teamEnglishName}  
									&nbsp;<strong>${mentionedUser.userEnglishName}</strong> 
								</c:otherwise>
							</c:choose>
							&nbsp; ${mentionedUser.userId} 
						</div>
						<div class="more">
							<div id="followBtnDetail_${mentionedUser.userId}" >
								<c:if test="${sessionUser.userId == mentionedUser.userId}">
									&nbsp;
								</c:if>
								<c:if test="${sessionUser.userId != mentionedUser.userId}">
									<c:if test="${not empty mentionedUser.isFollowing}">
										<a class="button_follow" href="#a" onclick="unfollow('${mentionedUser.userId}','followBtnDetail_${mentionedUser.userId}');"><span><ikep4j:message pre='${preButton}' key='unfollow' /></span></a>
									</c:if>
									<c:if test="${empty mentionedUser.isFollowing}">
										<a class="button_pr" href="#a" onclick="follow('${mentionedUser.userId}','followBtnDetail_${mentionedUser.userId}');"><span><img src="<c:url value='/base/images/icon/ic_plus.gif'/>" title="" /><ikep4j:message pre='${preButton}' key='follow' /></span></a>
									</c:if>
								</c:if>
							</div> 
						</div>
					</div>
					<div class="clear"><c:out value="${mentionedUser.expertField}" escapeXml="false"/></div>
				</div>
			</c:forEach>																																										
		</ul>
	</div>	