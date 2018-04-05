<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.workspace.main.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.main.header" />
<c:set var="preSearch"  value="message.collpack.collaboration.main.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.collaboration.main.list" />
<c:set var="preButton"  value="message.collpack.collaboration.main.button" />
<c:set var="preScript"  value="message.collpack.collaboration.main.script" />
<c:set var="preScript1"  value="message.collpack.collaboration.alliance.listAlliance.script" />

<c:set var="preTree"    value="message.collpack.collaboration.board.boardItem.leftBoardView.tree" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!--
(function($) {
	topScroll = function(){ 
		$jq(window).scrollTop(0);
	};
	
	resizeIframe = function() { 
		iKEP.iFrameContentResize();  
	};

	memberOut = function(workspaceId,memberId){
		
		if(!confirm('${workspace.workspaceName} - <ikep4j:message pre='${prefix}' key='script.memberOut' />'))
		{
			return false; 		
		}
		$.ajax({    
			url : "<c:url value="/collpack/collaboration/member/deleteMemberAjax.do"/>",     
			data : {workspaceId:workspaceId,memberId:memberId},     
			type : "post",     
			success : function(result) {    
				alert("<ikep4j:message pre='${prefix}' key='script.memberOutSuccess' />");
				document.location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspace.workspaceId}";
			},
			error : function(event, request, settings){
			 	alert("<ikep4j:message pre='${prefix}' key='script.memberOutError' />");
			}
		}); 
	};
	
	closeWorkspace = function(workspaceId){
		
		if(!confirm('${workspace.workspaceName} - 폐쇄신청하시겠습니까?'))
		{
			return false; 		
		}
		$.ajax({    
			url : "<c:url value="/collpack/collaboration/workspace/updateWorkspaceCloseStatusAjax.do"/>",     
			data : {workspaceId:workspaceId},     
			type : "post",     
			success : function(result) {    
				alert("폐쇄신청 완료되었습니다.");
				document.location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspace.workspaceId}";
			},
			error : function(event, request, settings){
			 	alert("폐쇄신청 실패 - 관리자에게 문의하세요.");
			}
		}); 
	};
	defaultSetting = function(workspaceId) {		
		$jq.get('<c:url value="/collpack/collaboration/member/updateWorkspaceDefaultSet.do"/>',
			{workspaceId:workspaceId},
			function(data){
				location.href="<c:url value='/collpack/collaboration/main/Workspace.do' />?workspaceId="+workspaceId;
			}
		)
		return false;
	};
	menu = function(id){
	
		if($jq("#"+id).is(':hidden'))
		{
			$jq("#"+id).show();
		}
		else
			$jq("#"+id).hide();
	};
	
	boardReload = function(){
		$jq("#boardTree").jstree("refresh", -1);
	};
	
	$jq(document).ready(function() {

		iKEP.setLeftCollaborationMenu();	

		if('${boardId}'!=null && '${boardId}'!="")
			iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do'/>?workspaceId=${workspace.workspaceId}&boardId=${boardId}');
		else
			iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/main/WorkspaceBody.do"/>?workspaceId=${workspace.workspaceId}');
		
		// alliance on/off
		if($jq("#alliance").is(':hidden'))
		{
			$jq("#alliance").show();
		}
		else
			$jq("#alliance").hide();
		
		// admin on/off
		if($jq("#Administrator").is(':hidden'))
		{
			$jq("#Administrator").show();
		}
		else
			$jq("#Administrator").hide();
		
		// admin on/off
		if($jq("#CollMenu").is(':hidden'))
		{
			$jq("#CollMenu").show();
		}
		else
			$jq("#CollMenu").hide();


		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#boardTree").jstree("open_node","#root");
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/collaboration/board/boardAdmin/listChildrenBoardMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", workspaceId:"${workspace.workspaceId}"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) {
		}).bind("close_node.jstree", function (event, data) {
		}); 

			
		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () { 

	    	if($(this).parent().attr("boardType") == "0") {
	    		iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${workspace.workspaceId}&boardId='/>' + $(this).parent().attr("boardId")); 
	    	}else if($(this).parent().attr("boardType") == "2") {
	    		if($(this).parent().attr("urlPopup")=="0")
	    			$("#contentIframe").attr("src", "<c:url value='/collpack/collaboration/board/boardCommon/readLinkBoardView.do'/>?boardId=" +$(this).parent().attr("boardId") + "&popupYn=${popupYn}");
	    		else
	    			iKEP.popupOpen($(this).parent().attr("url"), {width:900, height:500,location:true, menubar:true, scrollbars:true, status:true, titlebar:true, toolbar:true}, "Popup");
	    	}
	    });
		
		
	    $("#appointmentPopup").click( function() {  
			var url = "<c:url value='/collpack/collaboration/board/boardCommon/appointmentPopupView.do'/>";
			
			iKEP.popupOpen(url , {width:500, height:400});
		});
				
	});

	mainReload	= function(){
		location.reload();
	}
	goWorkspace = function(workspaceId) {
		location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId="+workspaceId
		return false;

	}; 		
	goBoard = function(workspaceId,boardId) {
		location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId="+workspaceId+"&boardId="+boardId;
		return false;

	}; 

})(jQuery);
//-->
</script>



<!--leftMenu Start-->
<h1 class="none"><ikep4j:message pre='${prefix}' key='detail.pageTitle' /></h1>	
<a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${workspace.workspaceId}"><h2 class="coll_title">${workspace.workspaceName}</h2></a>
<div class="left_fixed leftMenu_coll">
	
	<div class="coll_menu">
	<c:if test="${workspace.workspaceId != '100000813708' && workspace.workspaceId != '100001735534' && workspace.workspaceId != '100006308699'}">
		<ul>
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/announce/listAnnounceItemView.do'/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.announce' /></a></li>
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/planner/calendar/workspaceInit.do'/>?workspaceId=${workspace.workspaceId}&amp;workspaceName=${workspace.workspaceName}')">
			<c:choose>
				<c:when test="${workspace.typeId == '00002'}">
					자료실일정
				</c:when>
				<c:when test="${workspace.typeId == '00003'}">
					동호회일정
				</c:when>
				<c:otherwise>팀일정</c:otherwise>
			</c:choose>
			</a></li>
			<c:if test="${!empty workspace.teamId}">
			<!-- 무림제지 기능 비활성화 2012.10.29
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/main/listTeamMention.do'/>?workspaceId=${workspace.workspaceId}&amp;groupId=${workspace.teamId}')"><ikep4j:message pre='${prefix}' key='detail.menu.teamMentions' /></a></li>
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/main/listTeamMessage.do'/>?workspaceId=${workspace.workspaceId}&amp;groupId=${workspace.teamId}')"><ikep4j:message pre='${prefix}' key='detail.menu.teamMessage' /></a></li>
			-->
			</c:if>
			<!-- 무림제지 기능 비활성화 2012.10.29 -->
			<!-- 
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/main/listActivityStream.do" />?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.activityStream' /></a></li>
			-->
			<c:if test="${workspace.isOrganization=='1'}">
			<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/weekly/listWeeklyItemView.do'/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.weekly' /></a></li>
				<c:if test="${existLowRankGroup}">
					<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/weekly/listLowRankWeeklyItemView.do'/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.weeklyCollaboration' /></a></li>
				</c:if>
			</c:if>
		</ul>
		</c:if>
	</div>
	<ul>
		<li class="liFirst opened"><a href="#a"><ikep4j:message pre='${prefix}' key='detail.menu.board' /></a>
			<ul id="board" style="overflow:auto;overflow-y:hidden;border-left:#ccc 1px solid;border-right:#ccc 1px solid;width:178px;">
				<li class="opened">
					<div class="leftTree">
					<!--div><img src="<c:url value='/base/images/common/img_title_cate.gif'/>" alt="category" /></div-->
					<div id="boardTree"></div>
					</div>	
				</li>
			</ul>						
		</li>	
		
		<!-- 무림제지 활동통계기능 막기 2012.11.5
		<li class="opened"><a href="#a"><ikep4j:message pre='${prefix}' key='detail.menu.statistics' /></a>
			<ul id="statistics">
				<li class="closed"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/statistics/collStatisticsListView.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.statistics.collaboration' /></a></li>
				<li class="closed"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/statistics/memberStatisticsListView.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.statistics.member' /></a></li>
			</ul>						
		</li>
		 -->
		<c:if test="${workspace.workspaceId != '100001735534' && workspace.workspaceId != '100006308699'}"> 
		<li class="opened licurrent"><a href="#a"><ikep4j:message pre='${prefix}' key='detail.menu.alliance' /></a>
			<ul id="alliance">
			<c:forEach var="allianceList" items="${allianceList}"  varStatus="allianceStatus">
				<c:if test="${allianceStatus.last}">
				<li class="opened"><a href="<c:url value='/collpack/collaboration/main/Workspace.do'/>?workspaceId=${allianceList.workspaceId}" title="${allianceList.workspaceName}"><span>${allianceList.workspaceName}</span></a>
				</c:if>
				<c:if test="${not allianceStatus.last}">
				<li class="opened licurrent"><a href="<c:url value='/collpack/collaboration/main/Workspace.do'/>?workspaceId=${allianceList.workspaceId}" title="${allianceList.workspaceName}"><span>${allianceList.workspaceName}</span></a>
				</c:if>
				<c:set var="cnt" value="0"/>
				<c:forEach var="allianceBoardList" items="${allianceBoardList}"  varStatus="boardStatus">
					<c:if test="${allianceList.workspaceId==allianceBoardList.workspaceId && !empty allianceBoardList.boardId}">
						<c:if test="${cnt==0}">
						<ul>
						<c:set var="cnt" value="${cnt+1}"/>
						</c:if>
							
						<li class="ellipsis"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/collaboration/board/boardItem/listBoardItemView.do?workspaceId=${allianceList.workspaceId}&boardId=${allianceBoardList.boardId}'/>')" title="${allianceBoardList.boardName}"><span>${allianceBoardList.boardName}</span></a></li>
					</c:if>
					<c:if test="${(allianceList.workspaceId!=allianceBoardList.workspaceId && cnt>0) || boardStatus.last && cnt>0}">
					</ul>
					<c:set var="cnt" value="0"/>
					</c:if>
				</c:forEach>
				</li>
			</c:forEach>
			
			</ul>
		</li>
		</c:if>
		<c:if test="${isWorkspaceManager}">
		<li class="closed"><a href="#a"><ikep4j:message pre='${prefix}' key='detail.menu.administration' /></a>
			<ul id="Administrator">
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/updateWorkspaceInfoView.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.administration.info' /></a></li>
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/updateWorkspaceIntroView.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.administration.intro' /></a></li>
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspacelayout/layoutManageView.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre='${prefix}' key='detail.menu.administration.layout' /></a></li>
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/board/boardAdmin/listBoardView.do"/>?workspaceId=${workspace.workspaceId}&amp;boardRootId=0')"><ikep4j:message pre="${prefix}" key="detail.menu.administration.board" /></a></li>
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/alliance/listAllianceView.do"/>?workspaceId=${workspace.workspaceId}&listType=TO')"><ikep4j:message pre="${prefix}" key="detail.menu.administration.alliance" /></a></li>
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/member/listWorkspaceMemberView.do"/>?listType=Member&amp;workspaceId=${workspace.workspaceId}')"><ikep4j:message pre="${prefix}" key="detail.menu.administration.member" /></a></li>
			<%--li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspaceMap/admin/workspaceMapAdminMain.do"/>?workspaceId=${workspace.workspaceId}')"><ikep4j:message pre="${prefix}" key="detail.menu.administration.map" /></a></li--%>
			<c:if test="${isWorkspaceAdmin}">
			<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/board/boardMigration/listBoardTreeView.do"/>?workspaceId=${workspace.workspaceId}&amp;boardRootId=0')"><ikep4j:message pre="${prefix}" key="detail.menu.administration.migration" /></a></li>
			<li class="liLast">
			<c:choose>
			<c:when test="${workspace.workspaceStatus=='WC'}"> 
				<a href="#a" onclick="javascript:alert('<ikep4j:message pre="${prefix}" key="script.waitingClose" />')"><ikep4j:message pre="${prefix}" key="detail.menu.close" /></a>
			</c:when>
			<c:when test="${workspace.workspaceStatus=='O'}"> 
				<a href="#a" onclick="closeWorkspace('${workspace.workspaceId}');"><ikep4j:message pre="${prefix}" key="detail.menu.close" /></a>
			</c:when>		
			<c:otherwise>
			</c:otherwise>
			</c:choose>
			</li>
			<c:if test="${workspace.workspaceId == '100001735534' || workspace.workspaceId == '100006308699'}">
			<%-- <li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/board/boardCommon/listConsultationRequestView.do"/>')">상담신청 확인</a></li> --%>
			</c:if>
			</c:if>		
			
			</ul>
		</li>
		</c:if>	
		<c:if test="${workspace.workspaceId != '100001735534' && workspace.workspaceId != '100006308699'}">
		<li class="closed"><a href="#a">모바일 메뉴관리</a>
			<ul id="CollMenu">
				<li class="opened">
					<li class="liLast"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/getMyCollaborationList.do"/>')">모바일 메뉴</a></li>
				</li>
			</ul>						
		</li>
		</c:if>
	</ul>
	
	<c:if test="${workspace.workspaceId != '100000813708' && workspace.workspaceId != '100001735534' && workspace.workspaceId != '100006308699'}">
	<div class="coll_box">						
		<h3><ikep4j:message pre='${prefix}' key='detail.menu.myCollaboration' /></h3>
		<ul class="coll_list">
			<c:forEach var="myWorkspaceList" items="${myWorkspaceList}"  varStatus="status">
			<c:choose>
			<c:when test="${myWorkspaceList.typeName=='Organization'}">
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
			<a href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId=${myWorkspaceList.workspaceId}" title="${myWorkspaceList.workspaceName}"><span class="collName_my">${myWorkspaceList.workspaceName}</span></a>
			<%--a onclick="defaultSetting('${myWorkspaceList.workspaceId}')" href="#a" title="<ikep4j:message pre='${prefix}' key='detail.menu.defaultSetting' />"><span class="arrow"><ikep4j:message pre='${prefix}' key='detail.menu.default' /></span></a--%>
			</li>
			</c:forEach>
		</ul>
		<div class="clear"></div>
							
	</div>	
	</c:if>
	<!--blockButton_2 Start-->
	<c:if test="${workspace.workspaceId != '100001735534' && workspace.workspaceId != '100006308699'}">
	<div style="margin-bottom:5px;"> 
		<a class="button_2 normal" href="<c:url value="/collpack/collaboration/main/Collaboration.do"/>"><span><img src="<c:url value='/base/images/icon/ic_home_workspace.png'/>" alt="" /> <ikep4j:message pre='${prefix}' key='detail.menu.allWorkspace' /></span></a>	
	</div>
	<!--//blockButton_2 End-->	
	
	
	
	<!--blockButton_2 Start-->
	<div class="blockButton_2"> 
	<c:choose>
		<c:when test="${empty member.memberLevel}"> 
			<a class="button_2 normal" href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/member/createWorkspaceMemberView.do"/>?workspaceId=${workspace.workspaceId}')"><span><ikep4j:message pre='${prefix}' key='detail.menu.memberIn' /></span></a>
		</c:when>
		<c:when test="${member.memberLevel==5}"> 
			<a class="button_2 normal" href="#a" onclick="javascript:alert('<ikep4j:message pre="${prefix}" key="script.waitingMember" />')"><span><ikep4j:message pre='${prefix}' key='detail.menu.memberOut' /></span></a>
		</c:when>		
		<c:when test="${member.memberLevel!=1}"> 
			<a class="button_2 normal" href="#a" onclick="memberOut('${workspace.workspaceId}','${user.userId}');"><span><ikep4j:message pre='${prefix}' key='detail.menu.memberOut' /></span></a>
		</c:when>			
		<c:when test="${member.memberLevel==1}"> 
			<a class="button_2 normal" href="#a" onclick="javascript:alert('<ikep4j:message pre="${prefix}" key="script.noSysop" />')"><span><ikep4j:message pre='${prefix}' key='detail.menu.memberOut' /></span></a>
		</c:when>		
		<c:otherwise>
		</c:otherwise>
	</c:choose>
	</div>
	</c:if>
	<!--//blockButton_2 End-->		
			
</div>
<div class="blockBlank_10px"></div>	
<c:if test="${workspace.workspaceId == '100001735534' || workspace.workspaceId == '100006308699'}">
<%-- <div>
<a id="appointmentPopup" href="#a">
<img src="<c:url value='/base/images/common/img_counsel_logo1.jpg'/>" alt="category" />		
</a>
</div> --%>
</c:if>
<!--//leftMenu End-->


<%--
<script type="text/javascript">

var menuTreeCreateNode = function(pid, data) {

	var obj = $jq("#mapMenuTree li[id=" + pid + "]");
	$jq("#mapMenuTree").jstree("create_node", obj, "last", data, function() {});
};

var menuTreeRenameNode = function(id, name) {
	var obj = $jq("#mapMenuTree li[id=" + id + "]");
	$jq("#mapMenuTree").jstree("rename_node", obj, name);
};

var menuTreeUpdateNode = function(id, name, desc, tag) {
	var obj = $jq("#mapMenuTree li[id=" + id + "]");
	$jq("#mapMenuTree").jstree("rename_node", obj, name);
	obj.attr("mapdescription", desc);
	obj.attr("tags",tag);
};

var menuTreeDeleteNode = function(id) {
	var obj = $jq("#mapMenuTree li[id=" + id + "]");
	
	$jq("#mapMenuTree").jstree("delete_node", obj);
};
var menuMapTreeReload = function() {

	$jq("mapMenuTree").empty();
	
	/*맵 관련*/
	// Tree setting
	$jq("#mapMenuTree").jstree({
		core : {
			animation : 200
		},
		plugins:["themes", "ui", "json_data"],
		ui : {
			select_limit:1
		},
		json_data : {
			data : {data:{title:"${rootMap.mapName}",icon:"dept"},attr:{id:"${rootMap.mapId}",pid:"${rootMap.mapParentId}",workspaceId:"${workspaceId}"},state:"open",children:${mapTreeJSON}},
			ajax : {
				url : "<c:url value='/collpack/collaboration/workspaceMap/admin/listChildMaps.do'/>",
				data : function($el) {
					return {
						"mapParentId" : $el.attr("id"),
						"workspaceId" : $el.attr("workspaceId")
					};
 				},
				success : function(data) {
					return data.items;
				}
			}
		}
	})
	.bind("select_node.jstree", function(data,event){
		var node = event.rslt.obj;
		if(${rootMap.mapId}!=node.attr("id")){
			iKEP.iFrameMenuOnclick("<c:url value='/collpack/collaboration/workspaceMap/admin/workspaceMapListView.do?mapId='/>" + node.attr("id") + "&mapName=" + encodeURI($jq("#mapMenuTree").jstree("get_text", node),"UTF-8")+"&workspaceId=" + node.attr('workspaceId')+"&countOfPage=10&page=1&requestPage=1&reInit=true");
		}else{
			return false;
		}
	});
};

</script>
--%>