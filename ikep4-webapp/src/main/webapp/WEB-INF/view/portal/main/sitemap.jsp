<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi"  value="ui.portal.main.sitemap" />

<script type="text/javascript">
<!--

function openBWsiteMap(){
	window.open('<c:url value='/portal/moorimess/bwMain.do'/>' , 'bw','left=0,top=0,width='+(window.screen.availWidth-16)+',height='+(window.screen.availHeight-32)+',toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1');
	//return false;
}

(function($) {
	
	$(document).ready(function() {		
	});
})(jQuery);

-->
</script>

<!--wrapper Start-->
<div id="wrapper">

	<!--blockMain Start-->
	<div id="blockMain">
		<!--mainContents Start-->
		<div id="mainContents_3">
			<h2><span><ikep4j:message pre='${preUi}' key='sitemap' /></span></h2>
			<c:if test="${a1}">
			<ul class="group">
				<li class="title"><ikep4j:message pre='${preUi}' key='portalPersonal' /></li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/portal/main/listUserMain.do?rightFrameUrl=/support/profile/getProfile.do'/>" >My Blog</a></li>
						<li><a href="<c:url value='/collpack/collaboration/main/myWorkspace.do'/>">My Team</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z1}">
			<ul class="group">
				<li class="title">메인메뉴</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/portal/main/portalMain.do'/>" target="_parent" >메인메뉴</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a1}">
			<ul class="group">
				<li class="title"><ikep4j:message pre='${preUi}' key='workAlignment' /></li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/lightpack/board/boardCommon/boardView.do?boardRootId=0'/>"><ikep4j:message pre='${preUi}' key='bbs' /></a></li>
						<li><a href="<c:url value='/collpack/collaboration/main/listCollaborationMain.do?typeId=00002&listType=WorkspaceType'/>">자료실</a></li>
						<li><a href="<c:url value='/collpack/collaboration/main/listCollaborationMain.do?typeId=00003&listType=WorkspaceType#a'/>">동호회</a></li>
						<li><a href="<c:url value='/collpack/collaboration/main/Workspace.do?workspaceId=100000813708'/>">MR.Guardianship</a></li>
						<li><a href="<c:url value='/servicepack/survey/index.do'/>">설문조사</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z1}">
			<ul class="group">
				<li class="title">게시판</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/lightpack/board/boardCommon/boardView.do?boardRootId=0'/>"><ikep4j:message pre='${preUi}' key='bbs' /></a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a1}">
			<ul class="group">
				<li class="title">Web Diary</li>
				<li>
					<ul class="link">
						<li><a href="" onclick="javascript:window.open(iKEP.getContextRoot()+'/lightpack/planner/webDiary/init.do' , 'wd','left=0,top=0,width=1024,height=685,toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1');return false;">다이어리</a></li>
						<li><a href="<c:url value='/lightpack/planner/calendar/init.do'/>">일정관리</a></li>
						<li><a href="<c:url value='/lightpack/meetingroom/main.do'/>">자원관리</a></li>
						<li><a href="<c:url value='/lightpack/todo/todoView.do'/>">업무관리</a></li>
						<li><a href="<c:url value='/support/addressbook/addressbookHome.do'/>">주소록</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z1}">
			<ul class="group">
				<li class="title">일정</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/lightpack/planner/calendar/init.do'/>">일정</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a4}">
			<ul class="group">
				<li class="title"><ikep4j:message pre='${preUi}' key='knowledgeStreaming' /></li>
				<li>
					<ul class="link">
						<li>지식광장</li>
						<%-- <c:if test="${acl0}">
						<li><a href="<c:url value='/collpack/kms/main/Kms.do'/>">지식광장</a></li>
						</c:if>
						<c:if test="${acl4||acl5||acl8}">
						<li><a href="<c:url value='/support/customer/customerCommon/menuView.do'/>">고객정보</a></li>
						</c:if> --%>
						<%-- <li><a href="<c:url value='/support/publication/publication/menuView.do'/>">간행물주소록</a></li> --%>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z4}">
			<ul class="group">
				<li class="title">지식광장</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/collpack/kms/main/Kms.do'/>">지식광장</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<div class="clear"></div>
			<h3><span><ikep4j:message pre='${preUi}' key='sitemap' /></span></h3>
			<c:if test="${a2}">
			<ul class="group secondRow">
				<li class="title"><ikep4j:message pre='${preUi}' key='ess' /></li>
				<li>
					<ul class="link">
						<li>인사관리</li>
						<li>평가</li>
						<li>인재개발</li>
						<li>근태/출장</li>
						<li>급여</li>
						<li>개인별 업무분장</li>
						<li>조직도</li>
						<li>급여/근태</li>
						<%-- <li><a href="<c:url value='/portal/moorimess/personalMng.do'/>">인사관리</a></li>
						<li><a href="<c:url value='/portal/moorimess/evaluationMng.do'/>">평가</a></li>
						<li><a href="<c:url value='/portal/moorimess/manPowerMng.do'/>">인재개발</a></li>
						<li><a href="<c:url value='/portal/moorimess/diligenceMng.do'/>">근태/출장</a></li>
						<li><a href="<c:url value='/portal/moorimess/payMng.do'/>">급여</a></li>
						<li><a href="<c:url value='/portal/moorimess/personalDivsionMng.do'/>">개인별 업무분장</a></li>
						<li><a href="<c:url value='/portal/moorimess/organogramMng.do'/>">조직도</a></li>
						<li><a href="<c:url value='/portal/moorimess/payDiligenceMng.do'/>">급여/근태</a></li> --%>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z3}">
			<ul class="group">
				<li class="title">MSS</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/portal/moorimmss/mssMain.do'/>">MSS</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a3}">
			<ul class="group secondRow">
				<li class="title"><ikep4j:message pre='${preUi}' key='mss' /></li>
				<li>
					<ul class="link">
						<li>인사관리</li>
						<li>조직관리</li>
						<li>인재개발</li>
						<li>평가관리</li>
						<li>노무관리</li>
						<li>급여/근태</li>
						<li>팀뷰어 예외관리</li>
						<%-- <li><a href="<c:url value='/portal/moorimmss/personalMng.do'/>">인사관리</a></li>
						<li><a href="<c:url value='/portal/moorimmss/organMng.do'/>">조직관리</a></li>
						<li><a href="<c:url value='/portal/moorimmss/manPowerMng.do'/>">인재개발</a></li>
						<li><a href="<c:url value='/portal/moorimmss/evaluationMng.do'/>">평가관리</a></li>
						<li><a href="<c:url value='/portal/moorimmss/laborMng.do'/>">노무관리</a></li>
						<li><a href="<c:url value='/portal/moorimmss/diligencePayMng.do'/>">급여/근태</a></li>
						<li><a href="<c:url value='/portal/moorimmss/teamViewMng.do'/>">팀뷰어 예외관리</a></li> --%>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z4}">
			<ul class="group">
				<li class="title">BW</li>
				<li>
					<ul class="link">
						<li><a href="" onclick="javascript:window.open(iKEP.getContextRoot()+'/portal/moorimess/bwMain.do' , 'bw','left=0,top=0,width='+(window.screen.availWidth-16)+',height='+(window.screen.availHeight-32)+',toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1');return false;">BW</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a4}">
			<ul class="group secondRow">
				<li class="title"><ikep4j:message pre='${preUi}' key='system' /></li>
				<li>
					<ul class="link">
						<li>BW</li>
						<li>MES</li>
						<li>전표결재</li>
						<%-- <li><a href="" onclick="javascript:window.open(iKEP.getContextRoot()+'/portal/moorimess/bwMain.do' , 'bw','left=0,top=0,width='+(window.screen.availWidth-16)+',height='+(window.screen.availHeight-32)+',toolbar=0,menubar=0,location=0,status=0,scrollbars=0,resizable=1');return false;">BW</a></li>
						<li><a href="" onclick="javascript:fnRun();return false;">MES</a></li>
						<li><a href="<c:url value='/portal/moorimess/statementCommon/statementMenuView.do?sflag=1'/>">전표결재</a></li> --%>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z5}">
			<ul class="group">
				<li class="title">HR결재</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/portal/moorimess/initEssMain.do?whereLink=zhr_es_003'/>">HR결재</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${a1}">
			<ul class="group secondRow">
				<li class="title"><ikep4j:message pre='${preUi}' key='search' /></li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/search/search.jsp?collection=ALL'/>"><ikep4j:message pre='${preUi}' key='search' /></a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<c:if test="${z1}">
			<ul class="group">
				<li class="title">전표결재</li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='/portal/moorimess/statementCommon/statementMenuView.do?sflag=1'/>">전표결재</a></li>
					</ul>
				</li>
			</ul>
			</c:if>
			<div class="clear"></div>
			<h3><span><ikep4j:message pre='${preUi}' key='sitemap' /></span></h3>
			<ul class="group secondRow">
				<li class="title1"><ikep4j:message pre='${preUi}' key='sitemap' /></li>
				<li>
					<ul class="link">
						<li><a href="<c:url value='http://www.moorim.co.kr'/>" target="_blank"><ikep4j:message pre='${preUi}' key='paper' /></a></li>
						<li><a href="<c:url value='http://www.moorimpowertech.co.kr'/>" target="_blank"><ikep4j:message pre='${preUi}' key='powertech' /></a></li>
						<li><a href="<c:url value='http://www.officeway.co.kr'/>" target="_blank"><ikep4j:message pre='${preUi}' key='officeway' /></a></li>
						<li><a href="<c:url value='http://www.moorimchem.co.kr'/>" target="_blank"><ikep4j:message pre='${preUi}' key='comtech' /></a></li>
						<li><a href="<c:url value='http://moorimcapital.co.kr/main.asp'/>" target="_blank">무림캐피탈</a></li>
						<li><a href="<c:url value='http://moorim.studymart.co.kr'/>" target="_blank">무림연수원</a></li>
						<li><a href="<c:url value='http://moorim.papermarketplace.co.kr/index.aspx'/>" target="_blank">무림B2B</a></li>
					</ul>
				</li>
			</ul>
		</div>
		<!--//mainContents End-->	
	</div>
	<!--//blockMain Start End-->
	
</div>
<!--//wrapper End-->