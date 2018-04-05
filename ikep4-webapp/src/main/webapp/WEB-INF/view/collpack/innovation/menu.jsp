<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.innovation.constants.InnovationConstants" %>

<c:set var="preUi" 			value="ui.collpack.innovation" />

<c:set var="user" value="${sessionScope['ikep.user']}" />

<div id="indexLeftRocket">
	<script type="text/javascript">iKEP.createSWFEmbedObject('#indexLeftRocket','<c:url value="/base/swf/index/innovation/rocket_left_7.swf"/>',266,278);</script>
</div>
<div class="rocketName_2 mb10"></div>
         <div class="myMenu">
         	<div class="profile_2" style="padding:5px;">
             	<img id="profilePictureImage" src="<c:url value='${user.profilePicturePath}'/>" alt="profileImage" width="50" height="50" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'" />
             	<div class="name">
			<span>${user.userName } ${user.jobTitleName }</span><br />
			<div>${user.teamName}</div>
		</div>
             </div>
             <div class="record_2">
                 <div class="title"><ikep4j:message pre='${preUi}' key='main.myRecored'/></div>
                 <div class="myStar">
                     <div class="subj"><ikep4j:message pre='${preUi}' key='main.myStar'/></div>
                     <div class="gaugeGL"><img src="<c:url value="/base/images/futureplanet/gaugeGL.gif"/>" alt="" /></div>
                     <div class="gaugeBg">
                         <!-- 하단 DIV의 style="width:00px;" 값에 따라 게이지가 변동함 (최대:130px / 최소:1px) -->
                       <div class="gaugeG" style="width:80px;"></div>
                   </div>
                   <div class="gaugeR"><img src="<c:url value="/base/images/futureplanet/gaugeR.gif"/>" alt="" /></div>
                   <div class="num">(41<ikep4j:message pre='${preUi}' key='main.count'/>)</div>
               </div><br />
               <div class="myRanking">
                   <div class="subj"><ikep4j:message pre='${preUi}' key='main.myRank'/></div>
                   <div class="gaugeOL"><img src="<c:url value="/base/images/futureplanet/gaugeOL.gif"/>" alt="" /></div>
                   <div class="gaugeBg">
                       <!-- 하단 DIV의 style="width:00px;" 값에 따라 게이지가 변동함 (최대:130px / 최소:1px) -->
                       <div class="gaugeO" style="width:120px;"></div>
                   </div>
                   <div class="gaugeR"><img src="<c:url value="/base/images/futureplanet/gaugeR.gif"/>" alt="" /></div>
                   <div class="num">(12<ikep4j:message pre='${preUi}' key='main.rank'/>)</div>
               </div>
           </div>
           <div class="teamRecord_2">
               <div class="title"><ikep4j:message pre='${preUi}' key='main.teamRecord'/></div>
               <div class="myStar">
                   <div class="subj"><ikep4j:message pre='${preUi}' key='main.teamStar'/></div>
                   <div class="gaugeBL"><img src="<c:url value="/base/images/futureplanet/gaugeBL.gif"/>" alt="" /></div>
                   <div class="gaugeBg">
                       <!-- 하단 DIV의 style="width:00px;" 값에 따라 게이지가 변동함 (최대:130px / 최소:1px) -->
                       <div class="gaugeB" style="width:100px;"></div>
                   </div>
                   <div class="gaugeR"><img src="<c:url value="/base/images/futureplanet/gaugeR.gif"/>" alt="" /></div>
                   <div class="num">(121<ikep4j:message pre='${preUi}' key='main.count'/>)</div>
               </div><br />
               <div class="myRanking">
                   <div class="subj"><ikep4j:message pre='${preUi}' key='main.teamRank'/></div>
                   <div class="gaugePL"><img src="<c:url value="/base/images/futureplanet/gaugePL.gif"/>" alt="" /></div>
                   <div class="gaugeBg">
                       <!-- 하단 DIV의 style="width:00px;" 값에 따라 게이지가 변동함 (최대:130px / 최소:1px) -->
                       <div class="gaugeP" style="width:131px;"></div>
                   </div>
                   <div class="gaugeR"><img src="<c:url value="/base/images/futureplanet/gaugeR.gif"/>" alt="" /></div>
                   <div class="num">(1<ikep4j:message pre='${preUi}' key='main.rank'/>)</div>
               </div><br />
               <div class="topRanker">
               	<div class="title"><ikep4j:message pre='${preUi}' key='main.teamTopRanker'/></div>
                   <ul>
                   	<li><img src="<c:url value="/base/images/futureplanet/samplePhoto01.gif"/>" alt="1<ikep4j:message pre='${preUi}' key='main.rank'/>" /><div><div class="first">1<ikep4j:message pre='${preUi}' key='main.rank'/></div>김태극</div></li>
                       <li><img src="<c:url value="/base/images/futureplanet/samplePhoto02.gif"/>" alt="2<ikep4j:message pre='${preUi}' key='main.rank'/>" /><div><div>2<ikep4j:message pre='${preUi}' key='main.rank'/></div>이항영</div></li>
                       <li><img src="<c:url value="/base/images/futureplanet/samplePhoto03.gif"/>" alt="3<ikep4j:message pre='${preUi}' key='main.rank'/>" /><div><div>3<ikep4j:message pre='${preUi}' key='main.rank'/></div>김희경</div></li>
                       <li><img src="<c:url value="/base/images/futureplanet/samplePhoto04.gif"/>" alt="4<ikep4j:message pre='${preUi}' key='main.rank'/>" /><div><div>4<ikep4j:message pre='${preUi}' key='main.rank'/></div>이승찬</div></li>
                   </ul>
               </div>
           </div>
<!--
            <div class="myMenuBtnArea">
            	<a href="#a"><img src="../../images/futureplanet/lgcnsTop50.gif" alt="LG CNS Top50" /></a>
            </div>
-->
       </div>
       <div class="myMenuBtnAreaBottom">
       	<a href="subMain.do?boardId=<%=InnovationConstants.BOARD_NOTICE%>"><img src="<c:url value="/base/images/futureplanet/noticeBtn.gif"/>" alt="notice" /></a><a href="#a" onclick="openInfo();return false;"><img src="<c:url value="/base/images/futureplanet/introductionSub.gif"/>" alt="<ikep4j:message pre='${preUi}' key='main.infoFuturePlanet'/>" /></a>
       </div>