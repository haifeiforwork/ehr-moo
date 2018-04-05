<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript"> 

var idx=0;
var intervalID;

(function($) {
	$(document).ready(function() {
	
		/**var mainMenu = $("#topMenu");
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
		});**/
		
		//iKEP.setMainMenu();
		
		$("#searchWord").keypress(function(event){
            if(event.which == 13) {
            	$("#searchButton").click();
            }
        });
		
		$("#searchButton").click(function() {
			findCollaboration();
		});
		
		//loadBoardItemList();
		sort = function(sortColumn, sortType) { 
			$("#mainForm input[name=actionType]").val("sort");
			$("#mainForm input[name=sortColumn]").val(sortColumn); 
			$("#mainForm input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
			$("#searchButton").click();
		}; 
	
		selectChange = function() { 
			$("#mainForm input[name=actionType]").val("pagePerRecord"); 
			//$("#mainForm").submit(); 
			$("#searchButton").click();
			return false;
		};
						
		$jq("#wsid_0").show();
		clearInterval(intervalID);
		intervalID=setInterval(function(){TeamColl_Info();},5000); // 5 sec
		
		$("div.TeamColl_summaryView").delegate("a.anchorBoardItem", "click", function(event){
			event.preventDefault();
			
			$("*[class=boardItemLine]").removeClass("bgSelected");
			$(this).parents("*[class=boardItemLine]").addClass("bgSelected");
				
			iKEP.showDialog({
				title : $(this).attr("title") || $(this).text(),
				width:800,
				height:500,
				url: $(this).attr("href") + "&amp;docPopup=true",
				modal:true
			});  
		});

	});
	var pageIndex=2;

	loadBoardItemList = function() {
		
		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/main/mainBoardItemList.do"/>',     
			data : {pageIndex:pageIndex}, 
			type : "post",     
			loadingElement : {button:"#moreDiv"},
			success : function(data) {  
				$jq("#blockBoardItem").append(data);
				pageIndex=pageIndex+1;
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	findCollaboration = function(data) {
		if(data == null)
			data=$jq("#mainForm").serialize()
		$jq.ajax({     
			url : '<c:url value='/collpack/collaboration/main/findCollaboration.do' />',     
			data : data, 
			type : "post",     
			loadingElement : {container:"#tagResult"},
			success : function(result) {  
				$jq("#tagResult").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
	};
	
	findCollaborationBySortColumn = function(sortColumn,sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		$("input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
		findCollaboration();
	}

	defaultSetting = function(workspaceId) {
		
		$jq.get('<c:url value="/collpack/collaboration/member/updateWorkspaceDefaultSet.do"/>',
			{workspaceId:workspaceId},
			function(data){
				$jq('form[name=mainForm]').attr({
					action:"<c:url value='/collpack/collaboration/main/Collaboration.do' />"
				});	
			
				$("#mainForm").attr({method:'POST'}).submit(); 
				return false; 
			}
		)
		
		$jq.ajax({     
			url : '<c:url value="/collpack/collaboration/main/getSubListForMyWorkspace.do" />',     
			data : "", 
			type : "post",     
			loadingElement : {container:"#myWorkspaceDiv"},
			success : function(result) {  
				$jq("#myWorkspaceDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
		return false;
	};
	
	TeamColl_Info = function(pidx) {
		if(pidx != null)
			idx=pidx;
			
		if(idx>=${workspaceListCnt}) {idx=0;}
		
		if($jq("#wsid_"+idx).is(':hidden'))
		{
			$jq("#wsid_"+idx).show();
		}
		
		for(var i=0;i <${workspaceListCnt}; i++){
			if(idx!=i) {
				$jq("#wsid_"+i).hide();
			}
		}
		idx++;
		
		clearInterval(intervalID);
		intervalID=setInterval(function(){TeamColl_Info();},5000); // 5 sec
	};
	
	initSearch	= function(){
		location.href="#TOP";
	};
	
})(jQuery);
</script>

<!--blockMain Start-->
<div id="blockMain">

 
<!--mainContents Start-->
<div id="TeamColl_main" >
	<h1 class="none"><ikep4j:message pre="${prefix}" key="collaboration" /></h1>

	<!--TeamColl_BoxL Start-->
	<div class="TeamColl_lBox">
	
		<form id="mainForm" name="mainForm" method="post" action="<c:url value='/collpack/collaboration/main/findCollaboration.do' />" onsubmit="return false;">
		
		<h2><ikep4j:message pre="${prefix}" key="findCollaboration" /></h2>
		<div class="conSearch">		
		
			<div class="conSearch_sel">
				<h2 class="none"><ikep4j:message pre="${prefix}" key="search.searchCondition" /></h2>
			</div>
			
			<input id="searchWord" name="name" style="width:222px" type="text" title="<ikep4j:message pre="${prefix}" key="search.searchWord" />" />
			<a  class="sel_btn" id="searchButton" href="#a"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>
			
			<input name="sortColumn" type="hidden" value="" title="sortColumn" />	
			<input name="sortType" type="hidden" value="" title="sortType" />
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		
		<div id="tagResult">
		
			<c:if test="${empty workspaceList}">
			<div style="background:#f2f2f2; padding:4px;">
			
			<div class="TeamColl_Info">
				<div class="btn_roll">
					<a href="#a" ><img src="<c:url value='/base/images/common/btn_collMainRoll_on.png'/>" alt="1" /></a>
					<a href="#a" ><img src="<c:url value='/base/images/common/btn_collMainRoll_off.png'/>" alt="2" /></a>
					<a href="#a" ><img src="<c:url value='/base/images/common/btn_collMainRoll_off.png'/>" alt="3" /></a>
					<a href="#a" ><img src="<c:url value='/base/images/common/btn_collMainRoll_off.png'/>" alt="4" /></a>	
				</div>
				<div class="TeamColl_InfoBox">				
				<div class="TeamColl_InfoImg"><img src="<c:url value='/base/images/common/imgTeamColl.png'/>" alt="" /></div>
					<dl>
						<dt></dt>
						<dd></dd>
						<dd>
						<span class="persons"><ikep4j:message pre="${prefix}" key="detail.memberTotal" /> : </span>
						<span><strong><ikep4j:message pre="${prefix}" key="detail.memberCount" /></strong></span> 	
						<span class="sysop"><ikep4j:message pre="${prefix}" key="detail.sysopName" /> : </span>
						</dd>
					</dl>
				</div>	
			</div>
			</div>
			</c:if>
					
			<c:if test="${!empty workspaceList}">
			<div style="background:#f2f2f2; padding:4px;">
			<c:forEach var="workspace" items="${workspaceList}"  varStatus="status">
			
			<div class="TeamColl_Info none" id="wsid_${status.index}" >
				
				<div class="btn_roll">
					<c:forEach var="i" begin="1" end="${workspaceListCnt}" step="1"> 
					<a href="#a" onclick="javascirpt:TeamColl_Info(${i-1});"><img src="<c:url value='/base/images/common/btn_collMainRoll_'/><c:if test="${status.index==(i-1)}">on</c:if><c:if test="${status.index!=(i-1)}">off</c:if>.png" alt="" /></a>
					</c:forEach>
				</div>
				<div class="TeamColl_InfoBox">
				<div class="TeamColl_InfoImg">
				<span id="viewDiv${status.index}">
				
					<c:if test="${not empty workspace.fileDataList}">
					<c:forEach var="fileDataList" items="${workspace.fileDataList}">
					<img width="150" id='viewImageDiv' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' alt="" />
					</c:forEach>
					</c:if>
					<c:if test="${empty workspace.fileDataList}">
					<img src="<c:url value='/base/images/common/temp_img2.gif'/>" alt="" />
					</c:if>
					
				</span>				
				
				</div>
					<dl>
						<dt><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspace.workspaceId}">${workspace.workspaceName}</a></dt>
						<dd>${workspace.description}</dd>
						<dd>
						<span class="persons"><ikep4j:message pre="${prefix}" key="detail.memberTotal" /> : </span>
						<span><strong>${workspace.memberCount}<ikep4j:message pre="${prefix}" key="detail.memberCount" /></strong></span> 
					
						<span class="sysop"><ikep4j:message pre="${prefix}" key="detail.sysopName" /> : 
						<c:choose>
						<c:when test="${user.localeCode == portal.defaultLocaleCode}">
							<a title="" href="#a" onclick="iKEP.showUserContextMenu(this, '${workspace.sysopId}', 'bottom')">${workspace.sysopName}</a>
						</c:when>
						<c:otherwise>
							<a title="" href="#a" onclick="iKEP.showUserContextMenu(this, '${workspace.sysopId}', 'bottom')">${workspace.sysopEnglishName}</a>
						</c:otherwise>
						</c:choose>					
						</span></dd>
					</dl>
				</div>				
			</div>			
			</c:forEach>
			</div>
			</c:if>
								
			<div class="TeamColl_cate">
				<table summary="<ikep4j:message pre="${prefix}" key="detail.type" />">
					<caption>
					</caption>
					<tbody>
					<tr>
						<c:forEach var="workspaceTypeList" items="${workspaceTypeList}"  varStatus="status" end="3">
						<td class="cate0<c:out value="${status.count }"/>">
							<div><a href="<c:url value="/collpack/collaboration/main/listCollaborationMain.do"/>?typeId=${workspaceTypeList.typeId}&amp;listType=WorkspaceType"><span class="title">${workspaceTypeList.typeName}</span>&nbsp;<span class="number">(${workspaceTypeList.countWorkspaceByType})</span><br />
							<span class="discr">${workspaceTypeList.typeDescription}</span></a></div>
						</td>
						</c:forEach>				
	
					</tr>
					</tbody>
				</table>
			</div>
	
			<!--subTitle_2 Start-->
			<div class="subTitle_2">
				<h3><ikep4j:message pre="${prefix}" key="detail.myCollUpdate" /></h3>
				<!-- div class="TeamColl_list">
					<ul>
						<li><a href="#a"><img src="<c:url value='/base/images/icon/ic_view_list.gif'/>" alt="" title="목록보기[on]" /></a></li>
						<li><a href="#a"><img src="<c:url value='/base/images/icon/ic_view_summary_on.gif'/>" alt="" title="요약보기" /></a></li>
					</ul>
				</div> -->
			</div>
			<!--//subTitle_2 End-->
			
			<!--blockListTable Start-->			
			<div class="blockListTable TeamColl_summaryView">
				<table summary="<ikep4j:message pre="${prefix}" key="detail.myCollUpdate.boardItem" />">
					<caption></caption>						
					<tbody>					
						<c:forEach var="boardItem" items="${boardItem}"  varStatus="status">
						<c:choose>
						<c:when test="${boardItem.typeName=='Team'  || boardItem.typeName=='Organization'}">
						<c:set var="class" value="cate_tit_1"/>				
						</c:when>
						<c:when test="${boardItem.typeName=='TFT'}">
						<c:set var="class" value="cate_tit_2"/>				
						</c:when>
						<c:when test="${boardItem.typeName=='Cop'}">
						<c:set var="class" value="cate_tit_3"/>				
						</c:when>
						<c:otherwise>
						<c:set var="class" value="cate_tit_4"/>				
						</c:otherwise>
						</c:choose>					
						<tr class="boardItemLine">
							<td>
								<div class="TeamColl_summaryViewTitle">
									<div class="cate_block_1"><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${boardItem.workspaceId}" title="${boardItem.workspaceName}"><span class="${class}">${boardItem.workspaceName}</span></a></div>
									<a class="anchorBoardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?workspaceId='/>${boardItem.workspaceId}&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}">${boardItem.title}</a>
								</div>
								<div class="summaryViewInfo">
								
									<c:choose>
									<c:when test="${user.localeCode == portal.defaultLocaleCode}">
										<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerName} ${boardItem.jobTitleName}</a></span><span class="timeline">${ikep4j:getTimeInterval(boardItem.updateDate, user.localeCode)}</span>
									</c:when>
									<c:otherwise>
										<span class="summaryViewInfo_name"><a href="#a" onclick="iKEP.showUserContextMenu(this, '${boardItem.registerId}', 'bottom');iKEP.iFrameContentResize(); return false;">${boardItem.registerEnglishName} ${boardItem.jobTitleEnglishName}</a></span><span class="timeline">${ikep4j:getTimeInterval(boardItem.updateDate, user.localeCode)}</span>
									</c:otherwise>
									</c:choose>								
								</div>
								
								<spring:htmlEscape defaultHtmlEscape="false"></spring:htmlEscape>
								<div class="summaryViewCon"><a class="anchorBoardItem" href="<c:url value='/collpack/collaboration/board/boardItem/readBoardItemLinkView.do?workspaceId='/>${boardItem.workspaceId}&amp;boardId=${boardItem.boardId}&amp;itemId=${boardItem.itemId}" title="${boardItem.title}"><ikep4j:extractTextBody text="${boardItem.contents}"/></a></div>
	        					
								  
								<c:if test="${not empty boardItem.tagList}">
								<div class="summaryViewTag">
									<span class="ic_tag"><span><ikep4j:message pre='${prefix}' key='boardItem.tag' /></span></span>
									<!--tagList--> 
									<c:forEach var="tag" items="${boardItem.tagList}" varStatus="tagLoop">
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
			</div>
			<!--//blockListTable End-->
					
			<!--blockBoardItemList Start-->
			<div id="blockBoardItem" class="blockBoardItem"></div>
			<!--//blockBoardItemList End-->		
			
			<!--blockButton Start-->
			<div class="blockButton_3" id="interestMoreDataBtn">
			<a class="button_3" href="#a" id="moreDiv" onclick="javascript:loadBoardItemList(); return false;"><span><ikep4j:message pre="${prefix}" key="detail.more" /><img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>
			</div>
			<!--//blockButton End-->	
		</div>
		
		</form>
	</div>
	<!--//TeamColl_BoxL End-->


	<!--TeamColl_BoxR Start-->
	<div class="TeamColl_rBox">
		<!--//My Coll_BoxL Start-->
		<div class="myColl_box">
			<div class="subTitle_2">
				<h3><ikep4j:message pre="${prefix}" key="detail.myCollaboration" /></h3>
				<!-- 
				<div class="btn_mymenu"><a href="<c:url value="/collpack/collaboration/main/listCollaborationMain.do?listType=listMyColl"/>"><img src="<c:url value='/base/images/common/btn_mymenu.gif'/>" alt="more" /></a></div>
				 -->
			</div>
			<ul class="coll_list" id="myWorkspaceDiv">
				<c:forEach var="myWorkspaceList" items="${myWorkspaceList}"  varStatus="status">
					<c:choose>
					<c:when test="${myWorkspaceList.typeName=='Team' || myWorkspaceList.typeName=='Organization'}">
					<c:set var="class" value="cate_tit_fix_1"/>				
					</c:when>
					<c:when test="${myWorkspaceList.typeName=='TFT'}">
					<c:set var="class" value="cate_tit_fix_2"/>				
					</c:when>
					<c:when test="${myWorkspaceList.typeName=='Cop'}">
					<c:set var="class" value="cate_tit_fix_3"/>				
					</c:when>
					<c:otherwise>
					<c:set var="class" value="cate_tit_fix_4"/>				
					</c:otherwise>
					</c:choose>				
					
					<c:if test="${myWorkspaceList.isDefault=='1' }">
						<li class="selected">
					</c:if>
					<c:if test="${myWorkspaceList.isDefault!='1' }">
						<li>
					</c:if>	
								
					<span class="${class} ellipsis" title="${myWorkspaceList.typeName}">${myWorkspaceList.typeName}</span><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${myWorkspaceList.workspaceId}" title="${myWorkspaceList.workspaceName}"><span class="collName_my">${myWorkspaceList.workspaceName}</span></a>
					<!--<a onclick="defaultSetting('${myWorkspaceList.workspaceId}')" href="#a" title="<ikep4j:message pre="${prefix}" key="detail.myCollaboration.defaultSetting" />"><span class="arrow"><ikep4j:message pre="${prefix}" key="detail.myCollaboration.default" /></span></a>  -->
					</li>
				</c:forEach>
			</ul>
			<div class="clear"></div>
		</div>
		<!--//My Coll_BoxL End-->
		
		<!--//New Coll_BoxL Start-->
		<div class="mt10">
			<div class="subTitle_2 mb10">
				<h3><ikep4j:message pre="${prefix}" key="detail.newCollaboration" /></h3>
				<div class="btn_more"><a href="<c:url value="/collpack/collaboration/main/listCollaborationMain.do"/>?typeId=0&amp;listType=WorkspaceType"><img src="<c:url value='/base/images/common/btn_more.gif'/>" alt="more" /></a></div>
			</div>
			
			<c:forEach var="workspaceNewList" items="${workspaceNewList}"  varStatus="status">
			<c:if test="${status.index==0}">
			<div class="floatLeft mr5 photo" style="width:50px;height:50px;">
				
				<c:if test="${not empty workspaceNewList.fileDataList}">
				<c:forEach var="fileDataList" items="${workspaceNewList.fileDataList}">
				<div class="img_tit ellipsis">${workspaceNewList.typeName}</div>			
				<img width="50" height="50" id='viewImage' src='<c:url value="/support/fileupload/downloadFile.do"/>?fileId=${fileDataList.fileId}' alt="image" />							
				</c:forEach>
				</c:if>
				<c:if test="${empty workspaceNewList.fileDataList}">
				<div class="img_tit ellipsis">${workspaceNewList.typeName}</div>
				<img src="<c:url value='/base/images/common/temp_teamColl.gif'/>" alt="" />
				</c:if>
			</div>
			<span class="newColl_headline"><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspaceNewList.workspaceId}" title="${workspaceNewList.workspaceName}"><div class="ellipsis"><strong>${workspaceNewList.workspaceName}</strong></div></a>
			<a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspaceNewList.workspaceId}" title="${workspaceNewList.description}"><div>${fn:substring(workspaceNewList.description, 0, 40)}...</div></a></span>
			</c:if>
			</c:forEach>
			<div class="clear"></div>
			
			<ul class="coll_list mt5">
				<c:forEach var="workspaceNewList" items="${workspaceNewList}"  varStatus="status">
				<c:if test="${status.index!=0}">
				<c:choose>
				<c:when test="${workspaceNewList.typeName=='Team' || workspaceNewList.typeName=='Organization'}">
				<c:set var="class" value="cate_tit_fix_1"/>				
				</c:when>
				<c:when test="${workspaceNewList.typeName=='TFT'}">
				<c:set var="class" value="cate_tit_fix_2"/>				
				</c:when>
				<c:when test="${workspaceNewList.typeName=='Cop'}">
				<c:set var="class" value="cate_tit_fix_3"/>				
				</c:when>
				<c:otherwise>
				<c:set var="class" value="cate_tit_fix_4"/>				
				</c:otherwise>
				</c:choose>	
				<li class="ellipsis"><span class="${class} ellipsis" title="${workspaceNewList.typeName}">${workspaceNewList.typeName}</span><a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspaceNewList.workspaceId}" title="${workspaceNewList.workspaceName}">${workspaceNewList.workspaceName}</a></li>
				</c:if>
				</c:forEach>
			</ul>
			<div class="clear"></div>
		</div>
		<!--//New Coll_BoxL End-->
		<div class="popularTag colorTag">
			<div class="subTitle_2 noline mb10">
				<h3><ikep4j:message pre="${prefix}" key="detail.collaborationTag" /></h3>
			</div>
			<div class="tag_cloud"  id="tag01">
			<script type="text/javascript">
			iKEP.createTagEmbedObject("#tag01", "<c:url value="/collpack/collaboration/main/tag.do"/>", "tagResult");
			</script>
	
			<%--<c:forEach var="tag" items="${tagList}">
			<span><a href="#a" onclick="initSearch();iKEP.tagging.tagSearchByIsNotSubType('${tag.tagId}', '${tagItemType}', '${tag.tagItemSubType}');return false;" class="tag${tag.displayStep}">${tag.tagName}</a></span>
			</c:forEach>--%>
			</div>
			
		</div>


		<!--blockButton_2 Start-->
		<!-- 
		<div class="blockButton_2 mt10"> 
			<a class="button_2 normal" href="<c:url value="/collpack/collaboration/main/listCollaborationMain.do?listType=create"/>"><span><ikep4j:message pre="${prefix}" key="detail.collaborationCreate" /></span></a>				
		</div>
		 -->
		
		<!-- 
		<c:if test="${isSystemAdmin}">
		<div class="btn_administration mt10"> 
			<a href="<c:url value="/collpack/collaboration/main/listCollaborationMain.do?listType=WaitingOpen"/>"><span><ikep4j:message pre="${prefix}" key="detail.administration" /></span></a>				
		</div>
		</c:if>
		-->
		<!--//blockButton_2 End-->				

	</div>
	<!--//TeamColl_BoxR End-->
										
</div>
<!--//mainContents End-->

<div class="clear"></div>

 
</div>
<!--//blockMain End-->
		