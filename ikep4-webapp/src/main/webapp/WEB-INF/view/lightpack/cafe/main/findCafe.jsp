<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.findCollaboration" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript"> 
(function($) {
	$(document).ready(function() {
		var mainMenu = $("#topMenu");
		var menuItems = mainMenu.children("ul").children("li");
		var width = (menuItems.length * $(menuItems[0]).outerWidth());
		mainMenu.css({width:width + "px"});
		
		menuItems.bind("mouseenter mouseleave", function(event) {
			var el = event.target;
			switch(el.tagName.toLowerCase()) {
				case "ul" :
				case "a" : el = $(el).parent(); break;
				case "span" : el = $(el).parent().parent(); break;
				default : el = $(el);
			}
			el.children("ul").toggle();
		});
		
		$.each(menuItems.children("ul"), function() {
			var subMenu = $(this);
			subMenu.css({left:((subMenu.parent().outerWidth() - subMenu.outerWidth()) / 2 + 1)+"px"});
		});
		
		$("#searchButton").click(function() { 
			//$("input[name=searchType]").val('collaboration');	
			$("#mainForm").attr({method:'GET'}).submit(); 
			return false; 
		});
		//iKEP.setMainMenu();
		

		$("#mainForm select[name=pagePerRecord]").change(function() {
			//서브밋하는 코드 넣으시면 됩니다.
			//ex : $("#searchBoardItemButton").click(); 
			$("#searchButton").click(); 
		}); 		
	});
	
	defaultSetting = function(cafeId) {
		
		$jq.get('<c:url value="/lightpack/cafe/member/updateCafeDefaultSet.do"/>',
			{cafeId:cafeId},
			function(data){
				$jq('form[name=mainForm]').attr({
					action:"<c:url value='/lightpack/cafe/main/findCollaboration.do' />",
				});	
			
				$("#mainForm").attr({method:'GET'}).submit(); 
				return false; 
			}
		)
		return false;
	};
	initSearch	= function(){
		location.href="#TOP";
		$jq("#name").val("");
	
	}

})(jQuery);
</script>
<a id="TOP"></a>
<!--mainContents Start-->
<div id="TeamColl_main" >
	<h1 class="none"><ikep4j:message pre="${prefix}" key="findCollaboration" /></h1>

	<form id="mainForm" name="mainForm" method="get" action="<c:url value='/lightpack/cafe/main/findCollaboration.do' />">  
	
	<!--TeamColl_BoxL Start-->
	<div class="TeamColl_lBox">
	
		<h2><ikep4j:message pre="${prefix}" key="findCollaboration" /></h2>
		<div class="conSearch">
			<div class="conSearch_sel">
				<h2 class="none"><ikep4j:message pre="${prefix}" key="search.searchCondition" /></h2>
			
				<a class="sel_con" style="width:38px;" href="#a">COLL.<span>select</span></a>
				<div class="conSearch_layer none">
					<ul>
						<li><a href="#a"><ikep4j:message pre="${prefix}" key="search.collaboration" /></a></li>
					</ul>
				</div>
			</div>
			<input id="name" name="name" style="width:174px" type="text" value="${name}" title="<ikep4j:message pre="${prefix}" key="search.searchWord" />" />
			<a class="sel_btn" id="searchButton" href="#a"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		
		<div id="tagResult">
		
		<!--div class="subTitle_4">
			<div class="title_4"><span>'${name}'</span><ikep4j:message pre="${prefix}" key="search.result" /></div>
		</div-->

		<!--tableTop Start-->
		<div class="tableTop">
			<div class="tableTop_bgR"></div>
			<h2><span>'${name}'</span><ikep4j:message pre="${prefix}" key="search.result" /></h2>
			<div class="listInfo">  
				<spring:bind path="searchCondition.pagePerRecord">  
					<select name="${status.expression}" title='<ikep4j:message pre='${prefix}' key='search.pagePerRecord' />'>
					<c:forEach var="code" items="${cafeCode.pageNumList}">
						<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach> 
					</select> 
				</spring:bind>
				<div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${prefix}' key='search.page' /> ( <ikep4j:message pre='${prefix}' key='search.pageCount.info' /><span> ${searchResult.recordCount}</span>)</div>
			</div>

			<div class="clear"></div>
		</div>
		<!--//tableTop End-->	

		<!--blockListTable Start-->
		<div class="corporateView TeamColl_summaryView">
			<table summary="<ikep4j:message pre='${prefix}' key='list'/>">
				<caption></caption>						
				<tbody>
					<c:forEach var="cafeList" items="${cafeList}"  varStatus="status">
					<tr>
						<td>
							<div class="TeamColl_summaryViewTitle">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<span class="cate_block_1"><span class="cate_tit_1">${cafeList.typeName}</span></span>
								</c:when>
								<c:otherwise>
									<span class="cate_block_1"><span class="cate_tit_1">${cafeList.typeEnglishName}</span></span>
								</c:otherwise>
								</c:choose>		
								<span><a href="<c:url value="/lightpack/cafe/main/Cafe.do"/>?cafeId=${cafeList.cafeId}">${cafeList.cafeName}</a></span>
							</div>
							<span class="corporateViewInfo">
								<c:choose>
								<c:when test="${user.localeCode == portal.defaultLocaleCode}">
									<span class="corporateViewInfo_name"><a href="#a"><ikep4j:message pre='${prefix}' key='list.sysopName'/> : ${cafeList.sysopName}</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.registDate'/> :<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.openDate}"/></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.memberCount'/> : ${cafeList.memberCount}</span>
									<c:if test="${!empty cafeList.groupName}">
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.teamName'/> : ${cafeList.groupName}</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<span class="corporateViewInfo_name"><a href="#a"><ikep4j:message pre='${prefix}' key='list.sysopName'/> : ${cafeList.sysopEnglishName}</a></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.registDate'/> :<ikep4j:timezone pattern="yyyy.MM.dd" date="${cafeList.openDate}"/></span>
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.memberCount'/> : ${cafeList.memberCount}</span>
									<c:if test="${!empty cafeList.groupEnglishName}">
									<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
									<span><ikep4j:message pre='${prefix}' key='list.teamName'/> : ${cafeList.groupEnglishName}</span>
									</c:if>
								</c:otherwise>
								</c:choose>

							</span>
							<div class="corporateViewCon"><a href="#a">${cafeList.description}</a></div>
							

							<c:if test="${not empty cafeList.tagList}">
							<div class="corporateViewTag">
								<span class="ic_tag"><span><ikep4j:message pre='${prefix}' key='tag' /></span></span>
								<!--tagList--> 
								<c:forEach var="tag" items="${cafeList.tagList}" varStatus="tagLoop">
								<c:if test="${tagLoop.count != 1}">, </c:if>
								<a href="#a" onclick="iKEP.tagging.tagSearchByName('${tag.tagName}', '${tag.tagItemType}', '${tag.tagItemSubType}');return false;">${tag.tagName}</a>
								</c:forEach>
							</div>
							</c:if>							
							
						</td>
					</tr>
					</c:forEach>
																																														
				</tbody>
			</table>

			<!--Page Numbur Start--> 
			<spring:bind path="searchCondition.pageIndex">
			<ikep4j:pagination searchButtonId="searchButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
			<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${prefix}' key='search.pageIndex'/>" />
			</spring:bind> 
			<!--//Page Numbur End--> 			
		</div>
		<!--//blockListTable End-->
		</div>
	</div>
	<!--//TeamColl_BoxL End-->
				

	<!--TeamColl_BoxR Start-->
	<div class="TeamColl_rBox">
		<div class="clear">&nbsp;</div>
		<div class="myColl_box">
			<div class="subTitle_2">
				<h3><ikep4j:message pre="${prefix}" key="detail.myCollaboration" /></h3>
				<div class="btn_mymenu"><a href="<c:url value="/lightpack/cafe/main/listCollaborationMain.do?listType=listMyColl"/>"><img src="<c:url value='/base/images/common/btn_mymenu.gif'/>" alt="more" /></a></div>
			</div>
			<ul class="coll_list">
			
				<c:forEach var="myCafeList" items="${myCafeList}"  varStatus="status">
				<c:choose>
				<c:when test="${myCafeList.typeName=='Team'}">
				<c:set var="class" value="cate_tit_fix_1"/>				
				</c:when>
				<c:when test="${myCafeList.typeName=='TFT'}">
				<c:set var="class" value="cate_tit_fix_2"/>				
				</c:when>
				<c:when test="${myCafeList.typeName=='Cop'}">
				<c:set var="class" value="cate_tit_fix_3"/>				
				</c:when>
				<c:otherwise>
				<c:set var="class" value="cate_tit_fix_4"/>				
				</c:otherwise>
				</c:choose>				
				<c:if test="${myCafeList.isDefault=='1' }">
				<li class="selected">
				</c:if>
				<c:if test="${myCafeList.isDefault!='1' }">
				<li>
				</c:if>				
				<span class="${class}">${myCafeList.typeName}</span><a href="<c:url value="/lightpack/cafe/main/Cafe.do"/>?cafeId=${myCafeList.cafeId}"><span>${myCafeList.cafeName}</span></a>
				<a onclick="defaultSetting('${myCafeList.cafeId}')" href="#a" title="<ikep4j:message pre="${prefix}" key="detail.myCollaboration.defaultSetting" />"><span class="arrow"><ikep4j:message pre="${prefix}" key="detail.myCollaboration.default" /></span></a></li>
				</c:forEach>
			</ul>
		</div>
		<div class="mt10">
			<div class="subTitle_2 mb10">
				<h3><ikep4j:message pre="${prefix}" key="detail.newCollaboration" /></h3>
				<div class="btn_more"><a href="<c:url value="/lightpack/cafe/main/listCollaborationMain.do"/>?typeId=0&amp;listType=CafeType"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
			</div>
			<c:forEach var="cafeNewList" items="${cafeNewList}"  varStatus="status">
			<c:if test="${status.index==0}">
			<div class="floatLeft mr5 photo">
				
				<c:if test="${not empty cafeNewList.fileDataList}">
				<c:forEach var="fileDataList" items="${cafeNewList.fileDataList}">
				<div class="img_tit">${cafeNewList.typeName}</div>			
				<img width="50" id='viewImage' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' />							
				</c:forEach>
				</c:if>
				<c:if test="${empty cafeNewList.fileDataList}">
				<div class="img_tit">${cafeNewList.typeName}</div>
				<img src="<c:url value='/base/images/common/temp_teamColl.gif'/>" alt="" />
				</c:if>
			</div>
			<span class="newColl_headline"><a href="<c:url value="/lightpack/cafe/main/Cafe.do"/>?cafeId=${cafeNewList.cafeId}" title="${cafeNewList.cafeName}"><strong>${cafeNewList.cafeName}</strong>
			<br />${fn:substring(cafeNewList.description, 0, 40)}...</a></span>
			</c:if>
			</c:forEach>
			<br/>
			<ul class="coll_list mt5">
				<c:forEach var="cafeNewList" items="${cafeNewList}"  varStatus="status">
				<c:if test="${status.index!=0}">
				<c:choose>
				<c:when test="${cafeNewList.typeName=='Team'}">
				<c:set var="class" value="cate_tit_fix_1"/>				
				</c:when>
				<c:when test="${cafeNewList.typeName=='TFT'}">
				<c:set var="class" value="cate_tit_fix_2"/>				
				</c:when>
				<c:when test="${cafeNewList.typeName=='Cop'}">
				<c:set var="class" value="cate_tit_fix_3"/>				
				</c:when>
				<c:otherwise>
				<c:set var="class" value="cate_tit_fix_4"/>				
				</c:otherwise>
				</c:choose>	
				<li><span class="${class}">${cafeNewList.typeName}</span><a href="<c:url value="/lightpack/cafe/main/Cafe.do"/>?cafeId=${cafeNewList.cafeId}">${cafeNewList.cafeName}</a></li>
				</c:if>
				</c:forEach>
			</ul>
		</div>

		<div class="popularTag colorTag">
			<div class="subTitle_2 noline mb10">
				<h3><ikep4j:message pre="${prefix}" key="detail.collaborationTag" /></h3>
			</div>
			<div class="tag_cloud">
			<c:forEach var="tag" items="${tagList}">
			<span><a href="#" onclick="initSearch();iKEP.tagging.tagSearchByIsNotSubType('${tag.tagId}', '${tagItemType}', '${tag.tagItemSubType}');return false;" class="tag${tag.displayStep}">${tag.tagName}</a></span>
			</c:forEach>
			</div>
		</div>

		<!--blockButton_2 Start-->
		<div class="blockButton_2 mt20"> 
			<a class="button_2 normal" href="<c:url value="/lightpack/cafe/main/listCollaborationMain.do?listType=create"/>"><span><ikep4j:message pre="${prefix}" key="detail.collaborationCreate" /></span></a>				
		</div>
		<c:if test="${isSystemAdmin}">
		<div class="btn_administration"> 
			<a href="<c:url value="/lightpack/cafe/main/listCollaborationMain.do?listType=WaitingOpen"/>"><span><ikep4j:message pre="${prefix}" key="detail.administration" /></span></a>				
		</div>
		</c:if>
		<!--//blockButton_2 End-->				

	</div>
	<!--//TeamColl_BoxR End-->
										
</div>
<!--//mainContents End-->


 
		