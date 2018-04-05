<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.favorite.header" /> 
<c:set var="preList"    value="ui.support.favorite.list" />
<c:set var="preDetail"  value="ui.support.favorite.detail" />
<c:set var="preButton"  value="ui.support.favorite.button" /> 
<c:set var="preMessage" value="ui.support.favorite.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

<c:set var="user" value="${sessionScope['ikep.user']}" />					

<c:forEach var="favorite" items="${searchResult.entity}">
	<div class="blockBlank_10px"></div>
	<div class="comment_con">
		<div class="comment_box_l" >
			<ul>
				<li class="ellipsis">
					<a href="#a" onclick="showDatail('${favorite.targetUrl}','${favorite.targetId}','${favorite.subId}','${ikep4j:replaceQuot(favorite.title)}')">${favorite.title}</a>
				</li>
				<li>									
					<div class="summaryViewInfo">
						<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.goProfilePopupMain('${favorite.registerId}')">${favorite.registerName}</a></span>
						<img src="<c:url value="/base/images/common/bar_info.gif" />" alt="" />
						<span><ikep4j:timezone pattern="yyyy.MM.dd HH:mm:ss" date="${favorite.registDate}"/></span>
					</div>
				</li>							
			</ul>
		</div>
		
		<div class="comment_box_c">						
			<div class="comment_RoundBox1" style="word-break:break-all;">
				<p><img src="<c:url value="/base/images/common/box_pps2_l.png" />" width="19" height="27" alt=""/></p>
				${favorite.contents}
				<div class="l_t_corner"></div>
				<div class="r_t_corner"></div>
				<div class="l_b_corner"></div>
				<div class="r_b_corner"></div>				
			</div>						
		</div>	
		
		<div class="comment_today">
			<ul>
				<li>${ikep4j:getTimeInterval(favorite.linereplyRegistDate, user.localeCode)} by 
					<a href="#a" onclick="iKEP.goProfilePopupMain('${favorite.linereplyRegisterId}')">${favorite.linereplyRegisterName}</a>
				</li>
				<li>${favorite.module}</li>							
			</ul>
		</div>	
	</div>
	<div class="clear"></div>
	<div class="d_line"></div>		
</c:forEach>

<script type="text/javascript" language="javascript">
<!-- 

(function($) {
	
	// onload시 수행할 코드
	$jq(document).ready(function() {
		
		$jq("#recordCount").val("${searchCondition.recordCount}");
		$jq("#currentCount").val("${searchCondition.currentCount}");
		
		setMoreDiv();
		
	});
	
	
})(jQuery);  

//-->
</script>					