<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  		value="ui.socialpack.microblogging.setting.detail" />
<c:set var="preButton"  		value="ui.socialpack.microblogging.button" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--   

(function($) {

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		$jq("#submitBtn").click(function() {

			$jq.post("<c:url value='/socialpack/microblogging/setting/createSetting.do'/>", 
					$jq('form[name=mbsettingForm]').originalSerialize(), 
					function(data) {
						alert(data);
						window.close();
			});
		});
		
	});

})(jQuery);

//-->
</script>

<!--popup Start-->
<div id="popup">

	<!--popup_title Start-->
	<div id="popup_title">
		<h1><ikep4j:message  key='ui.socialpack.microblogging.setting.title' /></h1>
		<a href="#a" onclick="javascrtipt:window.close();"><span><ikep4j:message pre='${preButton}' key='close' /></span></a>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">
		
		<!--blockDetail Start-->
		<div class="blockDetail">
			<form name="mbsettingForm" action="">
				<table >
					<tbody>
						<tr>
							<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='maxFeedCount' /></th>
							<td colspan="3">
								<div style="padding-bottom:4px;">
									<label><input type="radio" class="radio" name="maxFeedCount" value="200" 
										<c:if test="${'200' == setting.maxFeedCount || empty setting}"> checked="checked" </c:if> />200</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFeedCount" value="300" 
										<c:if test="${'300' == setting.maxFeedCount}"> checked="checked" </c:if> />300</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFeedCount" value="400" 
										<c:if test="${'400' == setting.maxFeedCount}"> checked="checked" </c:if> />400</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFeedCount" value="500" 
										<c:if test="${'500' == setting.maxFeedCount}"> checked="checked" </c:if> />500</label>&nbsp;&nbsp;
								</div>									
							</td>
						</tr>
						<tr>
							<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='maxFavoriteCount' /></th>
							<td colspan="3">
								<div style="padding-bottom:4px;">
									<label><input type="radio" class="radio" name="maxFavoriteCount" value="200"  
										<c:if test="${'200' == setting.maxFavoriteCount || empty setting}"> checked="checked" </c:if> />200</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFavoriteCount" value="300" 
										<c:if test="${'300' == setting.maxFavoriteCount}"> checked="checked" </c:if> />300</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFavoriteCount" value="400" 
										<c:if test="${'400' == setting.maxFavoriteCount}"> checked="checked" </c:if> />400</label>&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="maxFavoriteCount" value="500" 
										<c:if test="${'500' == setting.maxFavoriteCount}"> checked="checked" </c:if> />500</label>&nbsp;&nbsp;
								</div>									
							</td>					
						</tr>
						<!-- tr>
							<th colspan="2" scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preDetail}' key='feedsAtATime' /></th>
							<td colspan="3">
								<div style="padding-bottom:4px;">
									<label><input type="radio" class="radio" name="feedsAtATime" value="10" 
										<c:if test="${'10' == setting.feedsAtATime || empty setting}"> checked="checked" </c:if> />10</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="feedsAtATime" value="20" 
										<c:if test="${'20' == setting.feedsAtATime}"> checked="checked" </c:if> />20</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="feedsAtATime" value="30" 
										<c:if test="${'30' == setting.feedsAtATime}"> checked="checked" </c:if> />30</label>&nbsp;&nbsp;&nbsp;&nbsp;
									<label><input type="radio" class="radio" name="feedsAtATime" value="40" 
										<c:if test="${'40' == setting.feedsAtATime}"> checked="checked" </c:if> />40</label>&nbsp;&nbsp;&nbsp;
								</div>									
							</td>						
						</tr-->			
					</tbody>
				</table>
			</form>	
		</div>
		<!--//blockDetail End-->
					
		<!--blockButton Start-->
		<div class="blockButton"> 
			<ul>
				<li><a class="button" id="submitBtn"><span><ikep4j:message pre='${preButton}' key='save' /></span></a></li>
			</ul>
		</div>
		<!--//blockButton End-->	

	</div>
	<!--//popup_contents End-->
	
	<!--popup_footer Start-->
	<div id="popup_footer"></div>
	<!--//popup_footer End-->
			
</div>
<!--//popup End-->
	
