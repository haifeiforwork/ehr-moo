<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %>
<jsp:useBean id="CommonConstant" class="com.lgcns.ikep4.support.base.constant.JSTLCommonConstant"/>

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
									
									<!--Contents Start-->	
									<!--people_box Start-->		
									<div class="people_box">
										<div class="people_box_photo">
											<div class="people_box_resize">
												<div class="ic_rt"><a href="#a" onclick="goDelete('${favorite.favoriteId}', this)"><img src="<c:url value="/base/images/icon/ic_cir_minus.gif" />" width="14px" height="14px" alt="minus" /></a></div>
											</div>							
											<span><a href="#a" onclick="javascript:iKEP.goProfilePopupMain('${favorite.targetId}')"><img src="<c:url value='${favorite.profilePicturePath}' />" alt="image" width="50px" height="50px" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/></a></span>
										</div>					
										<div class="people_box_info">
											<c:if test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
												<!-- div class="people_box_btn" id="${favorite.targetId}_follow">
													<c:choose>
													    <c:when test="${favorite.followId == null}">
													    	<div class="btn_follow">
															<a class="button_pr" href="#a" onclick="javascript:addFollow('${favorite.targetId}')">
																<span><img src="<c:url value="/base/images/icon/ic_plus.gif" />" alt="" />
																Follow</span>
															</a>
															</div>
														 </c:when>
													    <c:otherwise>
													    	<div class="btn_following">
													    	<a class="button_follow" href="#a" onclick="javascript:delFollow('${favorite.targetId}')">
													    		<span>Following</span>
															</a>
															</div>
													    </c:otherwise>
													</c:choose>
												</div-->
											</c:if>	
											
											<div class="ellipsis">
											<span class="people_box_name"><a href="#a" onclick="javascript:iKEP.goProfilePopupMain('${favorite.targetId}')">${favorite.title} ${favorite.jobTitleName}</a></span>
											<span class="people_box_team"><a href="#a">${favorite.teamName}</a></span>
											<c:if test="${CommonConstant.PACKAGE_VERSION != CommonConstant.IKEP_VERSION_BASIC}">
												<!-- a href="#a" class="ic_rt_note valign_middle" onclick="javascript:iKEP.sendMessage('${favorite.targetId}')"><span>Message</span></a-->
											</c:if>
											</div>
											
											<div class="people_box_con">
												<span class="phone"><span>Office Phone :</span>${favorite.officePhoneNo}</span>
												<c:choose>
													<c:when test="${CommonConstant.PACKAGE_VERSION eq CommonConstant.IKEP_VERSION_FULL}">
														<span class="cellular"><span>Cell Phone :</span><a href="#a" onclick="javascript:iKEP.sendSms('1','${favorite.targetId}')">${favorite.mobile}</a></span>
													</c:when>
													<c:otherwise>
														<span class="cellular"><span>Cell Phone :</span>${favorite.mobile}</span>
													</c:otherwise>
												</c:choose>							
											</div>
											<div class="people_box_mail"><span>Email :</span><span class="email"></span><a href="#a" onclick="javascript:sendMail('${favorite.title}','${favorite.mail}')"> ${favorite.mail}</a></div>	
										</div>					
									</div>
									<!--//people_box End-->	
				
									
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