<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preUi" 			value="ui.collpack.innovation.activityList" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">

//<![CDATA[
$jq(document).ready(function() {
	
	//forum 순위 리스트
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/rankListAjax.do"/>",     
		data : {endRowIndex:"10"},   
		type : "get",     
		success : function(result) {        
			
			showList("forumTop", result);
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
	
	//Idea Activities 순위 리스트
	$jq.ajax({    
		url : "<c:url value="/collpack/ideation/rankListAjax.do"/>",     
		data : {endRowIndex:"10"},  
		type : "get",     
		success : function(result) {        
			
			showList("ideaTop", result);
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
});


function showList(id, result){
	
	var html = "";
	
	$jq.each(result, function(i){
		var rankClass = (i > 0) ? 2: 1;
		
		var teamName, userName, jobTitleName;
		
		<c:choose>
		<c:when test="${user.localeCode == portal.defaultLocaleCode}">
			teamName = this.teamName;
			userName = this.userName;
			jobTitleName = this.jobTitleName;
		</c:when>
		<c:otherwise>
			teamName = this.teamEnglishName;
			userName = this.userEnglishName;
			jobTitleName = this.jobTitleEnglishName;
		</c:otherwise></c:choose>
		
		html += '<li><div class="ranking_photo">';
		html += '<div class="ic_ranking_'+rankClass+'">'+(i+1)+'</div>';
		html += '<div class="photoimg"><a href="#a" onclick="viewPopUpProfile(\''+this.userId+'\');return false;" title="profile"><img title="profile" src="'+iKEP.getWebAppPath()+this.profilePicturePath+'" width="35" height="35" alt="profile" onerror="this.src=\''+iKEP.getWebAppPath()+'/base/images/common/photo_50x50.gif\'"/></a></div>';
		html += '</div><div class="ranking_name"><a href="#a" onclick="viewPopUpProfile(\''+this.userId+'\');return false;" title="profile">'+userName +'&nbsp;'+ jobTitleName +'</a>';
		html += '<span class="forum_team">'+teamName+'</span></div>';
		
		var noramlMag,score;
		
		if(id == 'forumTop'){
			noramlMag = "<ikep4j:message pre='${preUi}' key='forumActScore'/>";
			score = this.discussionScore;
		} else {
			noramlMag = "<ikep4j:message pre='${preUi}' key='ideaActScore'/>";
			score = this.suggestionScore;
		}
		
		html += '<div class="ranking_num"><span class="normal">'+noramlMag+' :</span> '+score+'<ikep4j:message pre="${preUi}" key="pt"/></div>';
		html += '<div class="clear"></div></li>';
	});
	
	$jq("#"+id).html(html);
	
}


function viewPopUpProfile(targetUserId){
	iKEP.goProfilePopupMain(targetUserId);
}

//]]>
</script>
<h1 class="none">Contents Area</h1>
 

<!--blockListTable_2 Start-->
<div class="blockListTable_2 summaryView">
	<table summary="table">
		<caption></caption>
		<thead>
			<tr>
				<th scope="col" width="50%">Forum <span class="colorPoint"><ikep4j:message pre='${preUi}' key='top10'/></span></th>
				<th scope="col" width="50%">Idea <span class="colorPoint"><ikep4j:message pre='${preUi}' key='top10'/></span></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>
					<ul class="ranking" id="forumTop"></ul>			
				</td>
				<td>
					<ul class="ranking" id="ideaTop"></ul>
				</td>
			</tr>
		</tbody>
	</table>
</div>	
<!--//blockListTable_2 End-->
