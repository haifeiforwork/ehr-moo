<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.subMain" />
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
<!--

(function($) {
	
	goCafe = function(cafeId) {
		location.href="<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId="+cafeId
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
	
	clickListCafeCategoryBtn = function(){
		
		$jq('#listCategoryBtn').trigger("click");
	}
		
    $jq(document).ready(function() {
    	
    	iKEP.setLeftMenu();
    	var url="";

 		<c:if test="${searchCondition.listType=='categoryList'}">
 			url="<c:url value='/lightpack/cafe/category/listCategoryView.do'/>";
 		</c:if>
   		iKEP.iFrameMenuOnclick(url);
    	//autoIframe(url);

    });
    
})(jQuery);


//-->
</script>

		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu" class="leftMenu_QnA">
			

				<h1 class="none"><ikep4j:message pre="${prefix}" key="administrator" /></h1>	
				<h2><a href="<c:url value='/lightpack/cafe/main/menu.do'/>?listType=categoryList"><ikep4j:message pre="${prefix}" key="administrator" /></a></h2>	
				<div class="left_fixed">	
					<ul>
						<li class="liFirst no_child licurrent"><a href="#a" id="listCategoryBtn"  onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/category/listCategoryView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.category" /></a></li>
						<li class="opened"><a href="#a"><ikep4j:message pre="${prefix}" key="administrator.list" /></a>
							<ul class="qnalist_sub">
								<li class="qnalist"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/cafe/listCafeCategoryView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.cafe" /></a></li>
								<li class="qnalist"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/cafe/listCloseCafeView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.closeCafe" /></a></li>
								<li class="qnalist"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/cafe/listCafeLogView.do"/>')"><ikep4j:message pre="${prefix}" key="administrator.logCafe" /></a></li>
							</ul>	
						</li>
					</ul>
				</div>

			</div>	
			<!--//leftMenu End-->
			
			<!--mainContents Start-->
			<div id="mainContents">

			
				
				<iframe id="contentIframe" name="contentIframe" width="100%"  onoad="iKEP.iFrameResize('#contentIframe');" scrolling="no" frameborder="0"></iframe>
								

				
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->		
				
				

