<%--
=====================================================
	* 기능설명	:	동맹 요청
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.alliance.helpAlliance" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

	
	<!--popup_contents Start-->
	<div id="popup_contents">
	
		<!--top_text Start-->
		<div class="corner_RoundBox01" style="margin-bottom:20px;">
			<ul>
				<li><ikep4j:message pre="${prefix}" key="pageSubTitle1" /></li>
				<li><ikep4j:message pre="${prefix}" key="pageSubTitle2" /></li>
			</ul>
			<div class="l_t_corner"></div>
			<div class="r_t_corner"></div>
			<div class="l_b_corner"></div>
			<div class="r_b_corner"></div>
		</div>
		<!--//top_text End-->
		
		<!--alliance_contents Start-->
		<div class="alliance">
			<div class="blockLeft">
				<h2 class="none"><ikep4j:message pre="${prefix}" key="collaborationA" /></h2>
				<div class="alliance_left01">
					<div class="alliance_title01">
						<span class="alliance_text01">01.</span> <span><ikep4j:message pre="${prefix}" key="alliance" /></span>
					</div>
					<div class="alliance_contents">
						<span class="blockLeft" style="width:70%"><ikep4j:message pre="${prefix}" key="alliance.message1" /></span>
						<span><img src="<c:url value="/base/images/common/alliance_img01.gif"/>" alt="" /></span>
					</div>
					<div class="alliance_contents02">
						<ul>
							<li><span><ikep4j:message pre="${prefix}" key="auth" /> : </span><ikep4j:message pre="${prefix}" key="alliance.message2" /></li>
						</ul>
					</div>
				</div>
				<div class="alliance_left02">
					<div class="alliance_title01">
						<span class="alliance_text01">04.</span> <span><ikep4j:message pre="${prefix}" key="board" /></span>
					</div>
					<div class="alliance_contents">
						<span class="blockLeft" style="width:70%"><ikep4j:message pre="${prefix}" key="board.message1" /></span>
						<span><img src="<c:url value="/base/images/common/alliance_img04.gif"/>" alt="" /></span>
					</div>
					<div class="alliance_contents02">
						<ul>
							<li><span><ikep4j:message pre="${prefix}" key="auth" /> : </span><ikep4j:message pre="${prefix}" key="board.message2" /></li>
						</ul>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="blockRight" style="width:50%">
				<h2 class="none"><ikep4j:message pre="${prefix}" key="collaborationB" /></h2>			
				<div class="alliance_Right01">
					<div class="alliance_title01">
						<span class="alliance_text02">02.</span> <span><ikep4j:message pre="${prefix}" key="approval" /></span>
					</div>
					<div class="alliance_contents">
						<span class="blockLeft" style="width:70%"><ikep4j:message pre="${prefix}" key="approval.message1" /></span>
						<span><img src="<c:url value="/base/images/common/alliance_img02.gif"/>" alt="" /></span>
					</div>
					<div class="alliance_contents02">
						<ul>
							<li><span><ikep4j:message pre="${prefix}" key="auth" /> : </span><ikep4j:message pre="${prefix}" key="approval.message2" /></li>
						</ul>
					</div>
				</div>
				<div class="alliance_Right02">
					<div class="alliance_title01">
						<span class="alliance_text02">03.</span> <span><ikep4j:message pre="${prefix}" key="shareBoard" /></span>
					</div>
					<div class="alliance_contents">
						<span class="blockLeft" style="width:70%"><ikep4j:message pre="${prefix}" key="shareBoard.message1" /></span>
						<span><img src="<c:url value="/base/images/common/alliance_img03.gif"/>" alt="" /></span>
					</div>
					<div class="alliance_contents02">
						<ul>
							<li><span><ikep4j:message pre="${prefix}" key="auth" /> : </span><ikep4j:message pre="${prefix}" key="shareBoard.message2" /></li>
						</ul>
					</div>
				</div>
			</div>
			<div class="alliance_arrow01">
				<img src="<c:url value="/base/images/icon/ic_big_arrow01.png"/>" alt=""  />
			</div>
			<div class="alliance_arrow02">
				<img src="<c:url value="/base/images/icon/ic_big_arrow02.png"/>" alt=""  />
			</div>
			<div class="alliance_arrow03">
				<img src="<c:url value="/base/images/icon/ic_big_arrow03.png"/>" alt=""  />
			</div>
		</div>
		<!--//alliance_contents End-->
		
	<div class="clear"></div>
		
	</div>
	<!--//popup_contents End-->
	
