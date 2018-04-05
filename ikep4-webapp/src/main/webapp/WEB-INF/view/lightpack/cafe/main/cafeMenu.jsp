<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.cafe.main.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.lightpack.cafe.main.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.main.search" />
<c:set var="preDetail"  value="message.lightpack.cafe.cafe.createCafe.detail" />
<c:set var="preList"    value="message.lightpack.cafe.main.list" />
<c:set var="preButton"  value="message.lightpack.cafe.main.button" />
<c:set var="preScript"  value="message.lightpack.cafe.main.script" />
<c:set var="preScript1"  value="message.lightpack.cafe.alliance.listAlliance.script" />

<c:set var="preTree"    value="message.lightpack.cafe.board.boardItem.leftBoardView.tree" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript">
<!--
(function($) {
	



})(jQuery);


//-->
</script>

			<h2 style="text-overflow:ellipsis; overflow:hidden;">
			<a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}" title="${cafe.cafeName}">${cafe.cafeName}</a>
			</h2>
			<!--corner_RoundBox07 Start-->
			<div class="corner_RoundBox07">
			
				<!--socialblog_pr Start-->
				<div class="socialblog_pr">
					<div style="width:150px;height:150px">
					<h2 class="none"><ikep4j:message pre="${preDetail}" key="introImage" /></h2>
						<a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}">
							<img src="<c:url value='/support/fileupload/downloadFile.do?fileId=${cafe.imageId}'/>" alt="image" width="150px" height="150px" onerror="this.src='<c:url value='/base/images/common/photo_130x95.gif'/>'" />
							</a>
					</div>
					<div class="cafe_list">
						<ul>
							<c:if test="${member == null}">
								<li><img src="<c:url value='/base/images/icon/ic_cafe_01.gif'/>" alt="joinCafe" /> <a id="memberJoinBtn" href="#a"><ikep4j:message pre="${preDetail}" key="joinCafe" /></a></li>
							</c:if>
							<c:if test="${member != null}">
								<c:if test="${member.memberLevel != 5}">
									<li><img src="<c:url value='/base/images/icon/ic_cafe_02.gif'/>" alt="leaveCafe" /> <a id="memberLeaveBtn" href="#a"><ikep4j:message pre="${preDetail}" key="leaveCafe" /></a></li>
								</c:if>
								<c:if test="${member.memberLevel == 5}">
									<li><img src="<c:url value='/base/images/icon/ic_cafe_02.gif'/>" alt="leaveCafe" /> <a id="memberLeaveBtn2" href="#a"><ikep4j:message pre="${preDetail}" key="leaveCafe2" /></a></li>
								</c:if>
							</c:if>
							<c:if test="${isCafeManager || isCafeAdmin}">
								<li><img src="<c:url value='/base/images/icon/ic_cafe_01.gif'/>" alt="inviteCafe" /> <a id="memberInviteBtn" href="#a"><ikep4j:message pre="${preDetail}" key="inviteCafe" /></a></li>
							</c:if>
							<li><img src="<c:url value='/base/images/icon/ic_registration.gif'/>" alt="recentItem" /> <a id="recentItemBtn" href="#a"><ikep4j:message pre="${preDetail}" key="recentItem" /></a></li>
							<li><img src="<c:url value='/base/images/icon/ic_image_2.png'/>" alt="recentImage" /> <a id="recentImageBtn" href="#a"><ikep4j:message pre="${preDetail}" key="recentImage" /></a></li>
							<li><img src="<c:url value='/base/images/icon/ic_graph.gif'/>" alt="activity" /> <a id="activityBtn" href="#a"><ikep4j:message pre="${preDetail}" key="activity" /></a></li>
						</ul>
					</div>
				</div>
				<!--//socialblog_pr End-->
				
				<!--blockButton_2 Start-->
				<div class="blockButton_2 mb0"> 
					<a id="writeItemBtn" class="button_2" href="#a"><span><img src="<c:url value='/base/images/icon/ic_cafe_04.gif'/>" alt="" /><ikep4j:message pre="${preDetail}" key="writeItem" /></span></a>				
				</div>
				<!--//blockButton_2 End-->	
				
				<div class="dotline_2"></div>
				
				<div class="socialblog_search">
					<h2 class="none"><ikep4j:message pre="${preDetail}" key="searchItem" /></h2>
					<input type="text" class="inputbox" title="inputbox" id="searchTxt" name="" value="" style="width:116px" />
					<a id="searchBtn" href="#a" class="ic_search"><span><ikep4j:message pre="${preDetail}" key="searchItem" /></span></a>												
				</div>
	
				<div id="boardList" class="sMenu">
					<h3>Cafe Menu</h3>
	
				    <ul>
				    
					<c:set var="preIndentation" value="1"/>
					<c:set var="close" value="false"/>
					
					<c:forEach var="board" varStatus="varStatus" items="${boardList}">  
					
						<c:choose>
							<c:when test="${board.boardType == 1}"> 
								 <li class="viewAll"><a href="#a" id="AB_${board.boardId}_${board.boardType}_${board.hasChildren}" class="board">${board.boardName}</a></li>
							</c:when> 
							<c:otherwise>
								<li><a href="#a" id="AB_${board.boardId}_${board.boardType}_${board.hasChildren}" class="board">${board.boardName}</a></li>
							</c:otherwise>
						</c:choose>	  
							
					</c:forEach> 
					
					</ul>
				</div> 	
				
				<c:if test="${isSystemAdmin || isCafeAdmin}">
					<!--blockButton_2 Start-->
					<div class="blockButton_2 mb0"> 
						<a class="button_2 normal" href="<c:url value='/lightpack/cafe/main/cafeAdmin.do'/>?cafeId=${cafe.cafeId}&amp;listType=updateCafeInfoView"><span><ikep4j:message pre="${preDetail}" key="cafeAdmin" /></span></a>				
					</div>
					<!--//blockButton_2 End-->	
				</c:if>
				
			</div>
			<!--//sMenu End-->
	
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>				
		
					

	
	
