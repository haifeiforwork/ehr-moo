<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ page import="com.lgcns.ikep4.collpack.innovation.constants.InnovationConstants" %>
<c:set var="user" value="${sessionScope['ikep.user']}" />

<c:set var="preUi" value="ui.collpack.innovation" />

<script type="text/javascript">
//<![CDATA[
$jq(document).ready(function() {
	
	
	//최근 방문 토론 주제 
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/listDiscussionByReference.do"/>",     
		data : {endRowIndex:4},     
		type : "get",     
		success : function(result) {        
			
			var html = "";
			
			$jq.each(result, function(i){
				
				var linkUrl = iKEP.getWebAppPath()+"/collpack/forum/getView.do?discussionId="+this.discussionId ;
				
				html += '<li style="background:url(\'\');line-height:12px;"><div class="ellipsis" style="width:260px;"><a href="'+linkUrl+'">'+this.title+'</a></div></li>';

			});

			$jq("#recentDis").html(html);

			$jq("#recentDis").find("li:last").addClass("fLast");
			
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
	
	//최근 활발하게 진행 중인 토론 주제
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/listDiscussionByItemCount.do"/>",     
		data : {endRowIndex:4, limitDate:"200"},     
		type : "get",     
		success : function(result) {        
			
			var html = "";
			$jq.each(result, function(){
				var linkUrl = iKEP.getWebAppPath()+"/collpack/forum/getView.do?discussionId="+this.discussionId ;
				html += '<li><a href="'+linkUrl+'"><img src="<c:url value="/support/fileupload/downloadFile.do"/>?fileId='+this.imageId+'&amp;thumbnailYn=Y" width="90" height="80"/><span>'+this.title.cut(15)+'</span></a></li>';
			});

			$jq("#creativeThinking").html(html);
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 

	//hot issue 
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/popularListAjax.do"/>",     
		data : {endRowIndex:"3"},     
		type : "get",     
		success : function(result) {        
			
			showList("favoriteForum", result);
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
	
	//best Forum
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/bestListAjax.do"/>",     
		data : {endRowIndex:"3"},     
		type : "get",     
		success : function(result) {        
			showList("bestForum", result);
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 



	//Idea Square
	$jq.ajax({    
		url : "<c:url value="/collpack/ideation/lastListAjax.do"/>",     
		data : {endRowIndex:"3"}, 
		type : "get",     
		success : function(result) {        
			
			showList("recentIdes", result);
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 

	
	//Idea Best
	$jq.ajax({    
		url : "<c:url value="/collpack/ideation/lastListAjax.do"/>",     
		data : {endRowIndex:"3", isBest:"1"},   
		type : "get",     
		success : function(result) {        
			
			showList("bestIdea", result);
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
	
	//나의 forum 순위
	$jq.ajax({    
		url : "<c:url value="/collpack/forum/myRankAjax.do"/>",     
		data : null,   
		type : "get",     
		success : function(result) {        
			
			var disVal = (result.discussionRank > 0) ? result.discussionRank : 0;
			
			$jq("#myForumRank").text(disVal);
			
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 

	
	//나의 Idea Activities 순위
	$jq.ajax({
		
		url : "<c:url value="/collpack/ideation/myRanAjax.do"/>",     
		data : null,   
		type : "get",     
		success : function(result) {     
			
			var sugVal = (result.suggestionRank > 0) ? result.suggestionRank : 0;
			var conVal = (result.contributionRank > 0) ? result.contributionRank : 0;
			
			$jq("#myIdeaSug").text(sugVal);
			$jq("#myIdeaCon").text(conVal);
		},
		error : function(event, request, settings){
		 	alert("error");
		}
	}); 
	
	
	
	 $jq.ajax({    
			url : "<c:url value="/collpack/assess/getUserPivInfo.do"/>",     
			data : {userId:'${profile.userId}', portalId:'${profile.portalId}'},     
			type : "post",    
			//dataType : "json",  
			success : function(result) {    

				var data = $jq.parseJSON(result);
				
				var imgStep = Number(data.pviStep) + 1;
				
				iKEP.createSWFEmbedObject('#indexRocket',iKEP.getWebAppPath()+'/base/swf/index/innovation/rocket_'+imgStep+'.swf',284,309);
				
			},
			error : function(xhr, exMessage, httpStatus) {
				alert('error');
					
	        }

		}); 
});

function showList(id, result){

	var html = "";
	
	$jq.each(result, function(i){
		
		if(i > 2){return;}

		var updateDate = new Date(this.registDate);

		updateDateToString = updateDate.getFullYear()+"."+ (updateDate.getMonth()+1)+"."+updateDate.getDate();
		
		var linkUrl = iKEP.getWebAppPath()+'/collpack/';
		
		linkUrl += (id == 'favoriteForum' || id=='bestForum') ? "forum/getView.do?discussionId="+this.discussionId : "ideation/getView.do?itemId="+this.itemId;
		
		html += '<li><div class="ellipsis" style="width:260px;"><a href="'+linkUrl+'">'+this.title+'</a></div>';

		html += '<span class="name">'+this.registerName+'</span>';

		html += '<span class="date">['+updateDateToString+']</span></li>';
	});

	$jq("#"+id).html(html);

	$jq("#"+id).find("li:last").addClass("fLast");
}



String.prototype.cut = function(len) {
    var str = this;
    var l = 0;
    for (var i=0; i<str.length; i++) {
            l += (str.charCodeAt(i) > 128) ? 2 : 1;
            if (l > len) return str.substring(0,i) + "...";
    }
    return str;
}

function viewRank(){
	iKEP.showDialog({
	    title : "LG CNS TOP 10",
	    url : "<c:url value="/collpack/innovation/activityList.do"/>",
	    modal : true,
	    width:800,
	    height:400,
	});
	
}

//]]>
</script>
  <!-- fMainLeft -->
     <div class="fMainLeft">
     	<div class="subLists">
         	<ul>
                 <li><a href="<c:url value="/collpack/forum/popularList.do"/>"><img src="<c:url value="/base/images/futureplanet/subList01.png"/>" alt="<ikep4j:message pre='${preUi}' key='main.creativeThinking'/>" /></a></li>
                 <li><a href="<c:url value="/collpack/forum/bestList.do"/>"><img src="<c:url value="/base/images/futureplanet/subList02.png"/>" alt="<ikep4j:message pre='${preUi}' key='main.seminarInfo'/>" /></a></li>
             </ul>
         </div>
         
          <div class="hotIssue" style="margin-top:14px;">
         	<div ><img src="<c:url value="/base/images/futureplanet/favorite_title.png"/>" alt="Favorite Forum" /></div>
			<div class="contents" style="border-bottom:0px;height:82px;">
             	<ul id="recentDis" style="list-style:disc;color:#ffffff;">
             		<li></li>
                 </ul>
             </div>
         </div>
         
         
          <div class="creativeThinking">
             <div class="contents">
             	<ul id="creativeThinking">
             		<li></li>
                 </ul>
             </div>
         </div> 
         
         <div class="hotIssue" style="margin-top:148px;">
         	<div class="title"><img src="<c:url value="/base/images/futureplanet/favorite_title.png"/>" alt="Favorite Forum" /><a href="<c:url value="/collpack/forum/popularList.do"/>"><img src="<c:url value="/base/images/futureplanet/moreBtn2.gif"/>" alt="more" /></a></div>
			<div class="contents">
             	<ul id="favoriteForum">
             		<li></li>
                 </ul>
             </div>
         </div>
         <div class="seminarInfo">
            	<div class="title"><img src="<c:url value="/base/images/futureplanet/best_title.png"/>" alt="Best Forum" /><a href="<c:url value="/collpack/forum/bestList.do"/>"><img src="<c:url value="/base/images/futureplanet/moreBtn2.gif"/>" alt="more" /></a></div>
				<div class="contents">
                	<ul id="bestForum">
             		<li></li>
                 </ul>
                </div>
            </div>
     </div>
     <!-- fMainLeft end -->
     
     <!-- fMainCenter -->
     <div class="fMainCenter">
     	 <div class="rocket" id="indexRocket" style="width:284px;height:309px;margin-top:99px;">&nbsp;</div>
         <div class="profile" style="margin-top:14px;">
         	<img id="profilePictureImage" src="<c:url value='${user.profilePicturePath}'/>" alt="profileImage" width="50" height="50" onerror="this.src='<c:url value='/base/images/common/photo_50x50.gif'/>'"/>
             <div class="name"><span>${user.userName } ${user.jobTitleName }</span><br /><div>${user.teamName}</div></div>
         </div>
         <div class="record1">
             <div class="title">
             	<img class="valign_middle" alt="My Forum Activities" src="<c:url value="/base/images/icon/ic_graph.gif"/>"> 
             	<span style="vertical-align:bottom;">My Forum Activities</span>
             </div>
             <div class="myStar">
             	<div class="subj"><ikep4j:message pre='${preUi}' key='main.forumRank'/> : <span id="myForumRank"></span> <ikep4j:message pre='${preUi}' key='main.rank'/></div>
             </div>
         </div>
         
         <div class="record">
             <div class="title">
             	<img class="valign_middle" alt="My Idea Activities" src="<c:url value="/base/images/icon/ic_graph.gif"/>">
             	<span style="vertical-align:bottom;">My Idea Activities</span>
             </div>
             <div class="myStar">
             	<div class="subj"><ikep4j:message pre='${preUi}' key='main.ideaSugRank'/> : <span id="myIdeaSug"></span> <ikep4j:message pre='${preUi}' key='main.rank'/></div>
             </div><br />
             <div class="myRanking">
             	<div class="subj"><ikep4j:message pre='${preUi}' key='main.ideaConRank'/> : <span id="myIdeaCon"></span> <ikep4j:message pre='${preUi}' key='main.rank'/></div>
             </div>
         </div>
         
          <div class="myMenuBtnArea" style="width:280px;">
             	<a href="#a" onclick="viewRank();return false;"><img src="<c:url value="/base/images/futureplanet/lgcnsTop10.gif"/>" alt="LG CNS Top10" /></a>
             </div>
     </div>
     <!-- fMainCenter end -->
     
     <!-- fMainRight -->
     <div class="fMainRight">
     	<div class="subLists" style="height:80px;">
         	<ul >
                 <li><a href="<c:url value="/collpack/ideation/lastList.do"/>"><img src="<c:url value="/base/images/futureplanet/subList03.png"/>" alt="<ikep4j:message pre='${preUi}' key='main.discussionRoom'/>" /></a></li>
                 <li><a href="<c:url value="/collpack/ideation/lastList.do?isBest=1"/>"><img src="<c:url value="/base/images/futureplanet/subList04.png"/>" alt="<ikep4j:message pre='${preUi}' key='main.ideaSquare'/>" /></a></li>
             </ul>
         </div>
         <div class="ideaSquare" style="margin-top:220px;">
	      <div class="title"><img src="<c:url value="/base/images/futureplanet/recent_idea.png"/>" alt="Recnet Idea" /><a href="<c:url value="/collpack/ideation/lastList.do"/>"><img src="<c:url value="/base/images/futureplanet/moreBtn2.gif"/>" alt="more" /></a></div>
			<div class="contents">
             	<ul id="recentIdes">
             		<li></li>
                 </ul>
             </div>
         </div>
         
         <div class="ideaSquare" >
	      <div class="title"><img src="<c:url value="/base/images/futureplanet/best_idea.png"/>" alt="Best Idea" /><a href="<c:url value="/collpack/ideation/lastList.do?isBest=1"/>"><img src="<c:url value="/base/images/futureplanet/moreBtn2.gif"/>" alt="more" /></a></div>
			<div class="contents">
             	<ul id="bestIdea">
             		<li></li>
                 </ul>
             </div>
         </div>
     </div>
     <!-- fMainRight end -->