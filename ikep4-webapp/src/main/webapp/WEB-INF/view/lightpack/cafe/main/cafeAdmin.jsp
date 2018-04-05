<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.subMain" />
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
<!--

(function($) {
	topScroll = function(){ 
		$jq(window).scrollTop(0);
	}
	resizeIframe = function() { 
		iKEP.iFrameContentResize();  
	}
	mainReload = function(){
		loaction.reload();
	}
	goCafe = function(cafeId) {
		location.href="<c:url value='/lightpack/cafe/main/Cafe.do'/>?cafeId="+cafeId
		return false;
	}; 	
	
	showIframe = function(url){
		$jq("#tagResult").hide();

		setTimeout(function() {
			$jq("#divIframe").show();
		}, 500);		
	}
	nullIframe = function(){
	
		$jq("#divIframe").hide();
		
		$jq("#tagResult").show();
		
		location.href="#TOP";
		
		iKEP.iFrameContentResize();
	}
		
    $jq(document).ready(function() {
    	
    	iKEP.setLeftMenu();
    	var url="";

 		<c:if test="${searchCondition.listType=='updateCafeInfoView'}">
 			url="<c:url value='/lightpack/cafe/cafe/updateCafeInfoView.do'/>?cafeId=${cafe.cafeId}";
 		</c:if>
   		iKEP.iFrameMenuOnclick(url);
    	//autoIframe(url);

    });
	
    closeCafe = function(cafeId){
		
		if(!confirm('${cafe.cafeName} - <ikep4j:message pre='${prefix}' key='script.closeCafe' />'))
		{
			return false; 		
		}
		$.ajax({    
			url : "<c:url value='/lightpack/cafe/cafe/updateCafeCloseStatusAjax.do'/>",     
			data : {cafeId:cafeId},     
			type : "post",     
			success : function(result) {    
				alert("<ikep4j:message pre='${prefix}' key='script.closeCafeSuccess' />");
				document.location.href="<c:url value='/lightpack/cafe/main/main.do'/>?cafeId=${cafe.cafeId}";
			},
			error : function(event, request, settings){
			 	alert("<ikep4j:message pre='${prefix}' key='script.closeCafeError' />");
			}
		}); 
	};
})(jQuery);


//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu" class="leftMenu_QnA">
			

				<form id="subForm">
				<spring:bind path="cafe.cafeId">
					<input type="hidden" name="${status.expression}" id="${status.expression}" value="${status.value}" title=""/>
				</spring:bind>
			
				<br>
				<h1 class="none">${cafe.cafeName}</h1>	
				<h3 style="text-overflow:ellipsis; overflow:hidden;"><a href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}" title="${cafe.cafeName}">${cafe.cafeName}</a></h3>			
				<br>
				<div class="left_fixed">
					<ul>		
						<li class="opened liFirst licurrent">
							<a href="#a"><ikep4j:message pre="${prefix}" key="menu.basicInfo"/></a>
							<ul>
								<li class="no_child selected licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/cafe/updateCafeInfoView.do'/>?cafeId=${cafe.cafeId}')"><ikep4j:message pre="${prefix}" key="menu.basicInfoSub"/></a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/cafe/updateCafeIntroView.do"/>?cafeId=${cafe.cafeId}')"><ikep4j:message pre="${prefix}" key="menu.cafeIntroManage"/></a></li>
								<li class="no_child liLast"><a href="#a" onClick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/cafelayout/layoutManageView.do"/>?cafeId=${cafe.cafeId}')"><ikep4j:message pre="${prefix}" key="menu.cafeLayoutManage"/></a></li>
							</ul>		
						</li>
						<li class="opened"><a href="#a"><ikep4j:message pre="${prefix}" key="menu.cafeMemberManage"/></a>
							<ul>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/member/listCafeMemberView.do"/>?cafeId=${cafe.cafeId}&listType=Member')"><ikep4j:message pre="${prefix}" key="menu.cafeMemberGradeManage"/></a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/member/listCafeMemberView.do"/>?cafeId=${cafe.cafeId}&listType=Join')"><ikep4j:message pre="${prefix}" key="menu.joinMemberManage"/></a></li>
							</ul>		
						</li>						
						<li class="no_child">
							<a href="#a" onClick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/board/boardAdmin/listBoardView.do"/>?cafeId=${cafe.cafeId}&amp;boardRootId=0')"><ikep4j:message pre="${prefix}" key="menu.cafeMenuManage"/></a>
						</li>		
						<li class="no_child liLast">
						<c:choose>
							<c:when test="${cafe.cafeStatus=='WC'}"> 
								<a href="#a" onclick="javascript:alert('<ikep4j:message pre="${prefix}" key="script.waitingClose" />')"><ikep4j:message pre="${prefix}" key="menu.closeCafe" /></a>
							</c:when>
							<c:when test="${cafe.cafeStatus=='O'}"> 
								<a href="#a" onclick="closeCafe('${cafe.cafeId}');"><ikep4j:message pre="${prefix}" key="menu.closeCafe" /></a>
							</c:when>		
							<c:otherwise>
							</c:otherwise>
						</c:choose>
						
						</li>								
					</ul>
				</div>
				</form>
				
			</div>	
			<!--//leftMenu End-->
			
			<!--mainContents Start-->
			<div id="mainContents">

			
				
				<iframe id="contentIframe" name="contentIframe" width="100%"  onload="iKEP.iFrameResize('#contentIframe');" scrolling="no" frameborder="0"></iframe>
								

				
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->	
				

