<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.support.tagging.constants.TagConstants" %>

<c:set var="preUi" 	value="ui.support.tagging" />

<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="profilePro"><%=TagConstants.ITEM_TYPE_PROFILE_PRO %></c:set>
<c:set var="profileAttention"><%=TagConstants.ITEM_TYPE_PROFILE_ATTENTION %></c:set>

<script type="text/javascript">
//<![CDATA[	
	$jq(function(){
		
		//전문가, team에서만 tab있음.
		<c:if test="${param.tagItemType == profilePro || param.tagItemType == profileAttention || fn:contains(teamList.teamIdes, param.tagItemType)}">

		var teamIdes = "${teamList.teamIdes}";
		var tagItemType = "${param.tagItemType}";
		
		var profilePro = "${profilePro}";
		
		var selectedVal = 0;
		<c:if test="${param.tagItemType == profilePro || param.tagItemType == profileAttention }">
			if(tagItemType == profilePro){
				selectedVal = 0;
			} else {
				selectedVal = 1;
			}
		</c:if>
		
		<c:if test="${fn:contains(teamList.teamIdes, param.tagItemType)}">
			var teamIdList = teamIdes.split(",");
			
			for(var i=0; i < teamIdList.length; i++){
				if(teamIdList[i] == tagItemType){
					selectedVal = i;
				}
			}
		
		</c:if>
		
		
		$jq("#divTab1").tabs({     
			selected : selectedVal,    
			cache : true,     
			select : function(event, ui) {   
					var tagItemType = "";
				<c:if test="${param.tagItemType == profilePro || param.tagItemType == profileAttention}">
					if(ui.index == 0){
						tagItemType = "${profilePro}";
					} else{
						tagItemType = "${profileAttention}";
					} 
				</c:if>
				
				<c:if test="${fn:contains(teamList.teamIdes, param.tagItemType)}">	
					for(var i=0; i < teamIdList.length; i++){
						if(ui.index == i){
							tagItemType = teamIdList[i];
						}
					}
				</c:if>
				
					location.href = 'list${listType}Tag.do?tagItemType='+tagItemType;
					
			},     
			load : function(event, ui) {        
				//iKEP.debug(ui);     
				} 
		});  
		
		</c:if>
		
		
		var tagItemType = "${param.tagItemType}";
		
		$jq(".Box_type_01 a").click(function (e){
			
			var itemType = e.target.getAttribute('name');
			
			location.href = 'list${listType}Tag.do?tagItemType='+itemType.replaceAll("_",",");
			
			return false;
			
		}).each(function(){
			
			var name = this.getAttribute('name');
			
			if(tagItemType == name.replaceAll("_",",")){
				this.setAttribute('class', 'selected');
			}
			
		});
		
		<%-- team coll이면 목록 가져오기  --%>
		<c:if test="${listType == 'Coll'}">

		var collSubType = "${param.tagItemSubType}";
			$jq.ajax({    
				url : "<c:url value='/collpack/collaboration/workspace/myWorkspace.do'/>",     
				data : {userId:'${user.userId}'},     
				type : "post",  
				dataType : "json",
				success : function(result) {   
					
					var html = '<select title="teamCollSelect" id="teamCollSelect" onchange="goListBySelect();">';
						html += '<option value="All" >-<ikep4j:message pre='${preUi}' key='listWhole'/>-</option>';
					$jq.each(result, function(){
						var sel = "";
						if(collSubType == this.workspaceId){
							sel = "selected";
						}
						html += '<option value="'+this.workspaceId+'" '+sel+' >['+this.typeName+'] '+this.workspaceName+'</option>';
					});
						html += '</select>';
					
					$jq(".tableSearch").html(html);  
				},
				error : function(event, request, settings){
				 alert("error");
				}
			}); 
		</c:if>
		
		//page per 미리선택
		$jq('#pagePerSelect option').each(function(){
			if(this.value == '${param.pagePer}'){
				this.selected = true;
			}
		});
		
		//tagItemType 미리선택
		$jq('#tagSearch option').each(function(){
			if(this.value == '${param.tagItemType}'){
				this.selected = true;
			}
		});

		var paramOrder = "${param.tagOrder}";

		var orderSel = 0;
		if(paramOrder == "frequency"){
			orderSel = 1;
		} else if(paramOrder == "tagName"){
			orderSel = 2;
		}

		$jq(".soting a").eq(orderSel).wrapInner("<b>");
		
	
	});
	
	function goListBySelect(){
		
		var tagItem = $jq('#teamCollSelect').val();
		
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='tagItemType' && ram.key !='tagItemSubType'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		if(tagItem == "All"){
			location.href = 'list${listType}Tag.do?tagItemType=<%=TagConstants.ITEM_TYPE_WORKSPACE %>' + param;
		} else {
			location.href = 'list${listType}Tag.do?tagItemSubType='+ tagItem + param;
		}
	}
	
	function tagSearch(){

		$jq("#mainContents").ajaxLoadStart(); 
		
		$jq('#tagSearch').submit();
	}
	
	function goPage(){
		
		var pageIndex = $jq('#pageIndex').val();
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='pageIndex'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		location.href = 'list${listType}Tag.do?pageIndex='+pageIndex +'&'+ param;
		
	}
	
	function goOrder(val){
		
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='tagOrder'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		location.href = 'list${listType}Tag.do?tagOrder='+val +'&'+ param;
		
	}
	
	function goPagePer(val){
		
		var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != '' && ram.key !='pagePer'}">${ram.key}=${ram.value}&</c:if></c:forEach>';
		
		var pagePer = $jq('#pagePerSelect').val();
		
		location.href = 'list${listType}Tag.do?pagePer='+ pagePer +'&'+ param;
		
	}


	String.prototype.replaceAll = function( searchStr, replaceStr ){
		return this.split( searchStr ).join( replaceStr );
	}
	
	
	//]]>	
</script>

<h1 class="none">contents Area</h1>

	
	<!--tableTop Start-->
	<div class="tableTop">
	
		<div class="tableTop_bgR"></div>
		<h2><c:choose>
				<c:when test="${listType == 'Coll' }">
					<ikep4j:message pre='${preUi}' key='listCollaborationTag'/>
				</c:when>
				<c:when test="${param.isMy == '1' }">
					<ikep4j:message pre='${preUi}' key='menuMyTag'/> - 
					<c:choose>
						<c:when test="${param.tagItemType == 'PE' }">
							<ikep4j:message pre='${preUi}' key='listProfileProTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'PI' }">
							<ikep4j:message pre='${preUi}' key='listProfileConTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'WW' }">
							<ikep4j:message pre='${preUi}' key='listWhoWhoTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'WS' }">
							<ikep4j:message pre='${preUi}' key='listSocialBlogTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'QA' }">
							<ikep4j:message pre='${preUi}' key='listQnaTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'CV' }">
							<ikep4j:message pre='${preUi}' key='listCoporateVocaTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'CF' }">
							<ikep4j:message pre='${preUi}' key='listCafeTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'FR' }">
							<ikep4j:message pre='${preUi}' key='listForumtag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'ID' }">
							<ikep4j:message pre='${preUi}' key='listIdationTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'BD' }">
							<ikep4j:message pre='${preUi}' key='listBbsTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'MB' }">
							<ikep4j:message pre='${preUi}' key='listMicrobloggingTag'/>
						</c:when>
						<c:when test="${param.tagItemType == 'OP' }">
							<ikep4j:message pre='${preUi}' key='listOnlinePollTag'/>
						</c:when>
					</c:choose>
				</c:when>
				<c:otherwise>
					<ikep4j:message pre='${preUi}' key='listWholeTag'/> 
				</c:otherwise>
			</c:choose>
		</h2>
	
	
		<div class="listInfo">
			<select title="pagePerSelect" id="pagePerSelect" onchange="goPagePer();">
				<option selected="selected">10</option>
				<option>15</option>
				<option>20</option>
				<option>30</option>
				<option>40</option>
				<option>50</option>
			</select>
			<div class="totalNum"><ikep4j:message pre='${preUi}' key='listWhole'/><span> ${count}</span></div>
		</div>																			
		<div class="tableSearch">
		
			<form id="tagSearch" action="tagSearch.do" method="get" >
				<input name="pageIndex" type="hidden" id="pageIndex" value="1" title="pageIndex" />
				<input type="hidden" name="listType" value="${listType }"/>
				<c:if test="${!(listType == 'Coll' || param.isMy == '1') }">
				<select title="tagItemType" name="tagItemType">
					<option value="<%=TagConstants.ITEM_TYPE_PROFILE_PRO %>"><ikep4j:message pre='${preUi}' key='listProfileProTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_PROFILE_ATTENTION %>"><ikep4j:message pre='${preUi}' key='listProfileConTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_WHO %>"><ikep4j:message pre='${preUi}' key='listWhoWhoTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_SOCIAL_BLOG %>"><ikep4j:message pre='${preUi}' key='listSocialBlogTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_WORKSPACE %>"><ikep4j:message pre='${preUi}' key='listTeamCollTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_QNA %>"><ikep4j:message pre='${preUi}' key='listQnaTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"><ikep4j:message pre='${preUi}' key='listCoporateVocaTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_WORK_MANUAL %>"><ikep4j:message pre='${preUi}' key='listWorkManualTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_CAFE %>"><ikep4j:message pre='${preUi}' key='listCafeTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_FORUM %>"><ikep4j:message pre='${preUi}' key='listForumtag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_IDEATION %>"><ikep4j:message pre='${preUi}' key='listIdationTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_BBS %>"><ikep4j:message pre='${preUi}' key='listBbsTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_MICROBLOGGING %>"><ikep4j:message pre='${preUi}' key='listMicrobloggingTag'/></option>
					<option value="<%=TagConstants.ITEM_TYPE_ONLINE_POLL %>"><ikep4j:message pre='${preUi}' key='listOnlinePollTag'/></option>
				</select>													
				<input type="text" class="inputbox" title="inputbox" name="tagName" value="${param.tagName }" size="20" />
				<a href="#a" onclick="tagSearch();return false;" title="<ikep4j:message pre='${preUi}' key='listSearch'/>"><img src="<c:url value="/base/images/theme/theme01/basic/ic_search.gif"/>" alt="<ikep4j:message pre='${preUi}' key='listSearch'/>" /></a>
				</c:if>	
			</form>
		
		</div>	
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	
	<!--blockbox Start-->
	<c:if test="${listType == 'All' && param.goMore != 1}">
		<div class="Box_type_01">
			<table summary="list">
				<caption></caption>
				<tbody>
					<tr>
						<td width="25%" class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_PROFILE_PRO %>"><ikep4j:message pre='${preUi}' key='listProfileTag'/></a></td>
						<td width="25%" class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_CONPORATE_VOCA %>"><ikep4j:message pre='${preUi}' key='listCoporateVocaTag'/></a></td>
						<td width="25%" class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_BBS %>"><ikep4j:message pre='${preUi}' key='listBbsTag'/></a></td>
						<td width="25%" class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_WHO %>"><ikep4j:message pre='${preUi}' key='listWhoWhoTag'/></a></td>								
					</tr>
					<tr>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_WORK_MANUAL %>"><ikep4j:message pre='${preUi}' key='listWorkManualTag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_MICROBLOGGING %>"><ikep4j:message pre='${preUi}' key='listMicrobloggingTag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_SOCIAL_BLOG %>"><ikep4j:message pre='${preUi}' key='listSocialBlogTag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_CAFE %>"><ikep4j:message pre='${preUi}' key='listCafeTag'/></a></td>
					</tr>
					<tr>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_ONLINE_POLL %>"><ikep4j:message pre='${preUi}' key='listOnlinePollTag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_WORKSPACE %>"><ikep4j:message pre='${preUi}' key='listTeamCollTag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_FORUM %>"><ikep4j:message pre='${preUi}' key='listForumtag'/></a></td>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_QNA %>"><ikep4j:message pre='${preUi}' key='listQnaTag'/></a></td>
					</tr>
					<tr>
						<td class="textLeft"><a href="#a" name="<%=TagConstants.ITEM_TYPE_IDEATION %>"><ikep4j:message pre='${preUi}' key='listIdationTag'/></a></td>
					</tr>
				</tbody>
			</table>
		</div>	
		
		<!--//blockbox End-->
	
		<!--tab Start-->	
		<c:if test="${param.tagItemType == profilePro || param.tagItemType == profileAttention}">	
			<div id="divTab1" class="iKEP_tab">
				<ul>
					<li><a href="#tabs-1" title="<ikep4j:message pre='${preUi}' key='listProTag'/>"><ikep4j:message pre='${preUi}' key='listProTag'/></a></li>
					<li><a href="#tabs-2" title="<ikep4j:message pre='${preUi}' key='listConTag'/>"><ikep4j:message pre='${preUi}' key='listConTag'/></a></li>
				</ul>		
				<div style="display:none;">
					<div id="tabs-1"></div>
					<div id="tabs-2"></div>
				</div>			
			</div>	
		</c:if>	
		<%--
		<c:if test="${fn:contains(teamList.teamIdes, param.tagItemType)}">	
			<div id="divTab1" class="iKEP_tab">
				<ul>
					<c:forEach var="tag" items="${teamList.list}" >
						<li><a href="#tabs-1" title="team">${tag.teamTypeName }</a></li>
					</c:forEach>
				</ul>		
				<div style="display:none;">
					<c:forEach var="tag" items="${teamList.list}" varStatus="loop">
						<div id="tabs-${loop.count }"></div>
					</c:forEach>
				</div>			
			</div>	
		</c:if>	
		 --%>	
		<!--//tab End-->
	</c:if>
	<div class="soting">
		<p>
		<a href="#a" class="current" onclick="goOrder('date');return false;" title="<ikep4j:message pre='${preUi}' key='listOrdertime'/>"><ikep4j:message pre='${preUi}' key='listOrdertime'/></a> 
		| <a href="#a" onclick="goOrder('frequency');return false;" title="<ikep4j:message pre='${preUi}' key='listOrderFre'/>"><ikep4j:message pre='${preUi}' key='listOrderFre'/></a> 
		| <a href="#a" onclick="goOrder('tagName');return false;" title="<ikep4j:message pre='${preUi}' key='listOrderName'/>"><ikep4j:message pre='${preUi}' key='listOrderName'/></a>
		</p>
	</div>

	<div class="tag_cloud">
	
		<c:forEach var="tag" items="${tagList}" >
			<span><a href="tagSearch.do?tagId=${tag.tagId}&amp;tagItemType=${param.tagItemType}" class="tag${tag.displayStep}" title="tag">${tag.tagName}</a></span>
		</c:forEach>
		
		<c:if test="${fn:length(tagList) == 0}">
			<ikep4j:message key='message.support.tagging.noDate.tag'/>
		</c:if>
	</div>
									
<!--Page Numbur Start--> 
<ikep4j:pagination searchFormId="tagSearch" pageIndexInput="pageIndex" searchCondition="${searchCondition}" ajaxEventFunctionName="goPage"/>

<!--//Page Numbur End-->