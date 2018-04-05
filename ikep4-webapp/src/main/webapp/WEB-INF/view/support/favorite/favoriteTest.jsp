<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>




<script type="text/javascript" language="javascript">

(function($) {
	
	
	/* Personal Service Layer Box 보이기 */
	showPersonalBox = function(action){
		
		var $personalBox = $("#personalBox");
		var $personalBoxTitle = $("#personalBoxTitle");	
				
		if(action == "personal"){
			$personalBox.removeClass("quicktxt_recentBox quicktxt_favoriteBox").addClass("quicktxt_personalBox");
			$personalBoxTitle.removeClass("quicktxt_tit_recent quicktxt_tit_favorite").addClass("quicktxt_tit_personal");
			$personalBoxTitle.children("span").html("Personal");
			getPersonalInfo("personal");
		}else if(action == "recent"){
			$personalBox.removeClass("quicktxt_personalBox quicktxt_favoriteBox").addClass("quicktxt_recentBox");
			$personalBoxTitle.removeClass("quicktxt_tit_personal quicktxt_tit_favorite").addClass("quicktxt_tit_recent");
			$personalBoxTitle.children("span").html("Recent");			
			getPersonalInfo("recent");
		}else{
			$personalBox.removeClass("quicktxt_personalBox quicktxt_recentBox").addClass("quicktxt_favoriteBox");
			$personalBoxTitle.removeClass("quicktxt_tit_personal quicktxt_tit_recent").addClass("quicktxt_tit_favorite");
			$personalBoxTitle.children("span").html("Favorite");
			getPersonalInfo("favorite");
		}		
		
		$personalBox.show();
		
	}
	
	/* Personal Service Layer Box 닫기 */
	hidePersonalBox = function(){
		$("#personalBoxContent").empty();
		$("#personalBox").hide();
	}
	
	/* Personal Service 정보 ajax로 가져오기 */
	getPersonalInfo = function(action){					
		var $personalBoxContent = $("#personalBoxContent");
		$personalBoxContent.empty();
		var url = "";
		var parms = "";
		if(action == "personal"){
			url = iKEP.getContextRoot() + '/support/personal/getShortcut.do';
		}else if(action == "recent"){
			url = iKEP.getContextRoot() + '/support/activitystream/getShortcut.do';
		}else{
			url = iKEP.getContextRoot() + '/support/favorite/getShortcut.do';
		}				
		
		$.ajax({				
			url : url,
			data : "",
			type : "post",
			success : function(result) {
				$personalBoxContent.html(result);
			},
			error : function(event, request, settings) { 
				alert("error");	
			}
		});			
		
	}	
	
	
	

	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		
	});
	
})(jQuery); 
 
 </script>





    
<a class="button" href="#a" onclick="javascript:iKEP.addFavorite('CONTENTS','BBS','100000164397','test')">Favorite Add</a>


<br/><br/><br/>

<a href="javascript:iKEP.addChannel('rss','BD','20000000')">RSS 2.0</a>
<a href="javascript:iKEP.addChannel('atom','BD','20000000')">ATOM 1.0</a>
<a href="javascript:iKEP.addChannel('rss','QA100','')">QA100</a>
<a href="javascript:iKEP.addChannel('rss','QA200','')">QA200</a>
<a href="javascript:iKEP.addChannel('rss','QA300','')">QA300</a>
<a href="javascript:iKEP.addChannel('rss','QA400','')">QA400</a>
<a href="javascript:iKEP.addChannel('rss','QA500','100000114706')">QA500-100000114706</a>


<br/><br/><br/>

<div class="quicktxt">
	<ul>
		<li class="quicktxt_personal"><a href="#a" onclick="showPersonalBox('personal');"><span>personal</span></a></li>
		<li class="quicktxt_recent"><a href="#a" onclick="showPersonalBox('recent');"><span>recent</span></a></li>
		<li class="quicktxt_favorite"><a href="#a" onclick="showPersonalBox('favorite');"><span>favorite</span></a></li>
	</ul>
	<div class="quicktxt_personalBox none" id="personalBox">
		<div class="quicktxt_menuBox">
			<div class="quicktxt_tit_personal" id="personalBoxTitle"><span>Personal</span><a href="#a" id="personalBoxClose"><img src="<c:url value="/base/images/icon/ic_close_layer.gif" />" alt="" /></a></div>
			<div id="personalBoxContent"></div>
		</div>
	</div>			
</div> 
 


	