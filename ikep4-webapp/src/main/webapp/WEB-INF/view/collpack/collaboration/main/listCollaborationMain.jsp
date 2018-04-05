<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.collaboration.subMain" />
<%-- 메시지 관련 Prefix 선언 End --%>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!--

(function($) {	
	goWorkspace = function(workspaceId) {
		if(workspaceId == 0) {return;}
		location.href="<c:url value="/collpack/collaboration/main/Workspace.do"/>?workspaceId="+workspaceId
		return false;
	};

	contentIframeShow = function() {
		if($jq("#contentIframe").index()==-1)
		{
			$jq("#tagResult").empty();
			$jq("<iframe id='contentIframe' name='contentIframe' title='contentIframe' width='100%' onload=\"iKEP.iFrameResize('#contentIframe');\" scrolling=\"no\" frameborder=\"0\"></iframe>").appendTo("#tagResult");
		}
	};
	
	showIframe = function(url){
	/**	$jq("#tagResult").hide();
		setTimeout(function() {
			$jq("#divIframe").show();
		}, 500);		**/
	}
	tagIframe = function(){
	
		$jq("#divIframe").hide();		
		$jq("#tagResult").show();		
		location.href="#TOP";
		
		iKEP.iFrameContentResize();
	}
	//showIframe = function()
	//{
	//	$jq("#divIframe").show();
	//}
	/*resizeIFreme = function () {
			
		setTimeout(function() {
			var iframe = $("#rightFrame");
			var newHeight = iframe.contents().height();// + 30;

		  	iframe.height(newHeight);
		}, 500);
	}*/
		
    $jq(document).ready(function() {
    	
    	iKEP.setLeftMenu();
    	var url="";

 		<c:if test="${searchCondition.listType=='listAppMember'}">
 			url="<c:url value="/collpack/collaboration/workspace/listMyApplicationView.do"/>?listType=listAppMember";
 		</c:if>
 		<c:if test="${searchCondition.listType=='listMyColl'}">
 			url="<c:url value="/collpack/collaboration/workspace/listMyCollaborationView.do"/>";
 		</c:if> 		
 		<c:if test="${searchCondition.listType=='WorkspaceType'}">
 			url="<c:url value="/collpack/collaboration/workspace/listWorkspaceTypeView.do"/>?typeId=${searchCondition.typeId}&listType=WorkspaceType";
 		</c:if>
 		<c:if test="${searchCondition.listType=='create'}">
 			url="<c:url value="/collpack/collaboration/workspace/createWorkspaceView.do"/>";
 		</c:if>
 		<c:if test="${searchCondition.listType=='WaitingOpen'}">
 			url="<c:url value="/collpack/collaboration/workspace/listWorkspaceWaitingView.do"/>?listType=WaitingOpen";
 		</c:if>
 		
   		iKEP.iFrameMenuOnclick(url);
    	//autoIframe(url);

    });
    
})(jQuery);


//-->
</script>
		
				<h1 class="none"><ikep4j:message pre="${prefix}" key="collaboration" /></h1>	
				<h2 class="coll_title">
				<c:choose>
	                <c:when test="${searchCondition.listType=='WorkspaceType'}">
	                    <c:if test="${searchCondition.typeId =='0'}">
	                       <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_coll_collaboration.gif'/>"/></span>
	                    </c:if>
	                    <c:if test="${searchCondition.typeId =='00002'}">
	                        <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_coll_archives.gif'/>"/></span>
	                    </c:if>         
	                     <c:if test="${searchCondition.typeId =='00003'}">
	                        <span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_coll_club.gif'/>"/></span>
	                     </c:if>
	                </c:when>
	                <c:otherwise>
	                    WorkSpace
	                </c:otherwise>                          
                </c:choose> 
				</h2>	
				<div class="left_fixed">	
				<ul>
					<c:if test="${searchCondition.listType=='WorkspaceType'}">
					<li class="licurrent opened liFirst">
					</c:if>
					<c:if test="${searchCondition.listType!='WorkspaceType'}">	
					<li class="liFirst opened">
					</c:if>
						<a href="#a" id="type">Communication 유형</a>
						<ul class="type">
						<c:forEach var="workspaceTypeList" items="${workspaceTypeList}"  varStatus="status">
						 <c:if test="${searchCondition.typeId ==workspaceTypeList.typeId}">
							<li class="<c:if test="${searchCondition.typeId==workspaceTypeList.typeId}">licurrent</c:if> no_child <c:if test="${status.last}">liLast</c:if>"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listWorkspaceTypeView.do"/>?typeId=${workspaceTypeList.typeId}&amp;listType=WorkspaceType')">${workspaceTypeList.typeName}</a></li>
						 </c:if>
						</c:forEach>
						</ul>
					</li>

					<c:if test="${searchCondition.listType=='listAppMember' or searchCondition.listType=='listMyColl'}">
					<li class="licurrent opened">
					</c:if>
					<c:if test="${searchCondition.listType!='listAppMember' and searchCondition.listType!='listMyColl'}">	
					<li class="opened">
					</c:if>
						<a href="#a" id="personal"><ikep4j:message pre="${prefix}" key="personal" /></a>
						<ul class="personal">
						<!-- 무림제지 기능 막음 2012.11.7
						<li class="<c:if test="${searchCondition.listType=='listMyColl'}">licurrent</c:if> no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listMyCollaborationView.do"/>')"><ikep4j:message pre="${prefix}" key="personal.myCollaboration" /></a></li>
						-->
						<li class="<c:if test="${searchCondition.listType=='listAppMember'}">licurrent</c:if> no_child liLast"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listMyApplicationView.do"/>?listType=listAppMember')"><ikep4j:message pre="${prefix}" key="personal.myApplication" /></a></li>
						</ul>
					</li>					

					<c:if test="${isSystemAdmin}">
					<c:if test="${searchCondition.listType=='WaitingOpen'}">
					<li class="licurrent opened">
					</c:if>
					<c:if test="${searchCondition.listType!='WaitingOpen'}">	
					<li class="opened">
					</c:if>					
						<a href="#a" id="Administrator"><ikep4j:message pre="${prefix}" key="administrator" /></a>
						<ul class="Administrator">
						<li class="<c:if test="${searchCondition.listType=='WaitingOpen'}">licurrent</c:if> no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listWorkspaceWaitingView.do"/>?listType=WaitingOpen')"><ikep4j:message pre="${prefix}" key="administrator.waitingCollaboration" /></a></li>
						<li class="no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listWaitingOpenOrgView.do"/>?listType=WaitingOpenOrg')"><ikep4j:message pre="${prefix}" key="administrator.teamCollaboration" /></a></li>
						<li class="no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/listCloseWorkspaceView.do"/>?listType=Close')"><ikep4j:message pre="${prefix}" key="administrator.closeCollaboration" /></a></li>		
						<%--무림제지 기능막음 12.11.26 --%>
						<%-- <li class="no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/admin/workspaceType/listWorkspaceTypeView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.type" /></a></li> --%>
						<li class="no_child"><a href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/admin/category/listCategoryView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.category" /></a></li>
						</ul>
					</li>
					</c:if>
								
				</ul>
				</div>
				<div class="leftSelect mb10">
					<select onchange="goWorkspace(this.value)" name="typeId"  title="<ikep4j:message pre="${prefix}" key="myCollaboration.select" />">
					<c:if test="${searchCondition.typeId =='00003'}">
						<option value="" > 나의 동호회 </option>
					</c:if>
					<c:if test="${searchCondition.typeId =='00002'}">
						<option value="" > 나의 자료실 </option>
					</c:if>   
						<c:set var="typeId" value=""/>
						<c:set var="typeCount" value="0"/>
						<c:forEach var="workspaceList" items="${workspaceList}" varStatus="status">
						<c:if test="${workspaceList.isDefault==1}" >
						<!-- 무림제지 팀워크스페이스 바로가기 기능 막음 2012.11.1
						<option value="0">&nbsp;■&nbsp;<ikep4j:message pre="${prefix}" key="myCollaboration.base" /></option>
						<option style="font-weight:bold; color:#4AB34A;" value="${workspaceList.workspaceId}" >&nbsp;&nbsp;-&nbsp;${workspaceList.workspaceName} </option>	
						-->	
						</c:if>						
						
						<c:if test="${(workspaceList.isDefault!=1) && (workspaceList.typeId == searchCondition.typeId)}" >
						<c:if test="${workspaceList.typeId!=typeId}" >
						<!--<option value="0">&nbsp;■&nbsp;${workspaceList.typeName}</option>-->
						<c:set var="typeId" value="${workspaceList.typeId}"/>
						</c:if>
						<c:set var="typeCount" value="${typeCount+1}"/>
						<option value="${workspaceList.workspaceId}" >&nbsp;&nbsp;-&nbsp;${workspaceList.workspaceName} </option>
						</c:if>		
						
						</c:forEach>	
						<c:if test="${typeCount == 0}" >
							<c:if test="${searchCondition.typeId == '00002'}">
								<option value="0" >&nbsp;&nbsp;-&nbsp;소속 자료실이 없습니다.</option>
							</c:if>
							<c:if test="${searchCondition.typeId == '00003'}">
								<option value="0" >&nbsp;&nbsp;-&nbsp;소속 동호회가 없습니다.</option>
							</c:if>
						</c:if>	
					</select>

				</div>
				<!-- 무림제지 태그클라우드 기능 막음 2012.11.1
				<div class="leftBox mb10">
					<h3><ikep4j:message pre="${prefix}" key="tag" /></h3>
					<div class="tag_cloud"  id="tag01">
					<script>
					iKEP.createTagEmbedObject("#tag01", "<c:url value="/collpack/collaboration/main/tag.do"/>", "tagResult");
					</script>
	
					<%--c:forEach var="tag" items="${tagList}">
					<span><a href="#a" onclick="tagIframe();iKEP.tagging.tagSearchByIsNotSubType('${tag.tagId}', '${tagItemType}', '${tag.tagItemSubType}');return false;" class="tag${tag.displayStep}">${tag.tagName}</a></span>
					</c:forEach--%>
					</div>					
				</div>
				-->
				<c:if test="${searchCondition.typeId ne '0'}">	
				<!--blockButton_2 Start-->
				<div class="blockButton_2"> 
					<a class="button_2 normal" href="#a" onclick="contentIframeShow();iKEP.iFrameMenuOnclick('<c:url value="/collpack/collaboration/workspace/createWorkspaceView.do"/>?typeId=${searchCondition.typeId}')">
					<span>
					<c:if test="${searchCondition.typeId =='00003'}">
						동호회 개설신청
					</c:if>
					<c:if test="${searchCondition.typeId =='00002'}">
						자료실 개설신청
					</c:if> 
					</span>
					</a>				
				</div>
				</c:if>
				<!--//blockButton_2 End-->
