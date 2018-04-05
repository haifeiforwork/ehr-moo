<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<c:set var="preHeader"  value="ui.servicepack.intelligentsearch.window" /> 
<c:set var="preButton"  value="ui.support.searchpreprocessor.common.button" /> 
<c:set var="preLeft"  value="ui.support.searchpreprocessor.common.left" /> 
<c:set var="preSearch"  value="ui.servicepack.intelligentsearch.search" /> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		$("#searchBtn").click(function(){
			$('#intelligentSearchForm').submit();
		});	
	});
})(jQuery);

var searchCall = function(searchKeyword){
	$jq('#searchKeyword').val(searchKeyword);
	$jq('#intelligentSearchForm').submit();
}
//-->
 </script> 
<script type="text/javascript">
<!--
(function($) {
	$(document).ready(function() {
		
		$("#popularSearchButton1").click(function(){
			var setting ={'searchOption':'today'};
			ajaxCall(setting);
		});
		$("#popularSearchButton2").click(function(){
			var setting ={'searchOption':'week'};
			ajaxCall(setting);
		});
		$("#popularSearchButton3").click(function(){
			var setting ={'searchOption':'month'};
			ajaxCall(setting);
		});
		
		var ajaxCall= function(settings) {
				var config ={'startIndex':'0','next':'10','searchOption':'today'};
	     		if (settings) $.extend(config, settings); 
	     		//alert( JSON.stringify(config));
	     		$("#popularList").empty();
				 $.post('<c:url value="/servicepack/intelligentsearch/ajax/popular.do"/>',
						 config
				 )
				 .success(function(data) {
					 $(data).each(function(index){
						 index = index+1;
						 if( index <10 ) index = "0"+index;
						 this.index =index;
						 $.tmpl("rowtemplet1",this).appendTo( $("#popularList") );
					 });
				 })
				 .error(function(event, request, settings) { alert("error"); }); 
	 
	   };
		
	   $.template("rowtemplet1", '<li class="tag_No\${index}"><strong><span class="none">No.\${index}</span><a href="#" onclick="searchCall(\'\${searchKeyword}\');">\${searchKeyword}</a></strong></li>');

		
		deleteHistory = function(searchHistoryId, obj) {
			
			var index = $jq(obj).parent().index();
			
			$jq.ajax({     
				url : '<c:url value="/servicepack/intelligentsearch/deleteHistory.do" />',     
				data :  {'searchHistoryId':searchHistoryId},     
				type : "post",     
				success : function(result) {  
					//alert($jq("#historyUlList").children().get(index).html());
					$jq(obj).parent().remove();
				}, 
				error : function(event, request, settings) { alert("error"); }
			});  
			
		};
		
		deleteHistoryAll = function() {
			
			$jq.ajax({     
				url : '<c:url value="/servicepack/intelligentsearch/deleteHistoryAll.do" />',     
				data :  {},     
				type : "post",     
				success : function(result) {  
					$jq("#historyUlList").empty();
				}, 
				error : function(event, request, settings) { alert("error"); }
			});  
			
		};
	
	});
})(jQuery);
//-->
 </script> 

<script type="text/javascript">
<!--

var viewPorfile = function(userId){
	iKEP.goProfilePopupMain(userId);
};

(function($) {
	$(document).ready(function() {
		
		$("#popularTagSearchButton1").click(function(){
			var setting ={'searchOption':'today'};
			ajaxCall(setting);
		});
		$("#popularTagSearchButton2").click(function(){
			var setting ={'searchOption':'week'};
			ajaxCall(setting);
		});
		$("#popularTagSearchButton3").click(function(){
			var setting ={'searchOption':'month'};
			ajaxCall(setting);
		});
		
		var ajaxCall= function(settings) {
				var config ={'startIndex':'0','next':'10','searchOption':'today'};
	     		if (settings) $.extend(config, settings); 
	     		//alert( JSON.stringify(config));
	     		$("#popularTagList").empty();
				 $.post('<c:url value="/servicepack/intelligentsearch/ajax/populartag.do"/>',
						 config
				 )
				 .success(function(data) {
					 $(data).each(function(index){
						 index = index+1;
						 if( index <10 ) index = "0"+index;
						 this.index =index;
						 $.tmpl("rowtemplet",this).appendTo( $("#popularTagList") );
					 });
				 })
				 .error(function(event, request, settings) { alert("error"); }); 
	 
	   };
		
		$.template("rowtemplet", '<li class="tag_No\${index}"><strong><span class="none">No.\${index}</span><a href="#">\${tagName}</a></strong></li>');
	});
})(jQuery);
//-->
 </script> 
<!--leftMenu Start-->
			<div id="leftMenu">
				<h1 class="none"><ikep4j:message pre="${preHeader}" key="title" /></h1>	
				<h2><a href="<c:url value="/servicepack/intelligentsearch/index.do"/>"><ikep4j:message pre="${preHeader}" key="title" /></a></h2>	
				
				<div class="boxList">
					<p><ikep4j:message pre="${preSearch}" key="recommend" /></p>
				</div>
				<div class="boxList_sub blt">
					
						<c:choose>
							<c:when test="${!empty recommendList}">
							<ul>
							<c:forEach var="info" items="${recommendList}"  varStatus="loopStatus">
								<li><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a></li>
							</c:forEach>
							</ul>	
							</c:when>
						</c:choose>
					
				</div>
				
				<div class="boxList">
					<p><ikep4j:message pre="${preSearch}" key="history" /> <a class="button_s" href="#a"  onclick="deleteHistoryAll();"><span><ikep4j:message pre="${preSearch}" key="deleteAll"/></span></a></p>
				</div>
				<div class="boxList_sub blt">
						<c:choose>
							<c:when test="${!empty historyList}">
							<ul id="historyUlList">
							<c:forEach var="info" items="${historyList}"  varStatus="loopStatus">
								<li>
									<a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a>
									<img src="<c:url value='/base/images/icon/ic_btn_delete2.gif'/>" alt="" onclick="deleteHistory('${info.searchHistoryId}',this);" style="cursor:pointer"/>
								</li>
							</c:forEach>
							</ul>	
							</c:when>
						</c:choose>
				</div>	
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents" >
				<h1 class="none"><ikep4j:message pre="${preSearch}" key="contents" /></h1>
				
				<!--Intelligent_l Start-->
				<div class="Intelligent_l">
					
					<!--blockSearch Start-->
					<div class="blockSearch">
						<div class="corner_RoundBox03">
							<form id="intelligentSearchForm" name="intelligentSearchForm" method="post" action="<c:url value="/servicepack/intelligentsearch/search.do"/>">
							<table summary="<ikep4j:message pre="${preSearch}" key="summery" />">
								<caption></caption>
								<tbody>
									<tr>
										<td>
											<div class="Intel_search_con">
												<input name="searchKeyword" id="searchKeyword" class="inputbox" title="search" type="text"/>
												<div class="Intel_search_con_btn" id="searchBtn"><a href="#a"><img src="<c:url value='/base/images/icon/ic_search_con.gif'/>" alt="" /></a></div>
											</div>
										</td>
									</tr>
									<tr>
										<td>
											<div class="Intel_search_info">
												<span class="search_infoTxt"><ikep4j:message pre="${preSearch}" key="popular" /></span>
												<c:choose>
													<c:when test="${!empty popularList}">
													<c:forEach var="info" items="${popularList}"  varStatus="loopStatus">
														<c:if test="${loopStatus.count < 8}">
															<span><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a></span>
														</c:if>
													</c:forEach>	
													</c:when>
													<c:otherwise>
													</c:otherwise>
												</c:choose>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
							</form>
							<div class="l_t_corner"></div>
							<div class="r_t_corner"></div>
							<div class="l_b_corner"></div>
							<div class="r_b_corner"></div>				
						</div>
					</div>	
					<!--//blockSearch End-->
					
					
					<!--Intel_content Start-->
					<div class="Intel_content">
						<h2>Content</h2>
						<ul>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>
							<li>
								<div class="Intel_content_photo">
									<span><a href="#a"><img src="<c:url value='/base/images/common/temp_img_35x35_1.gif'/>" alt="image" /></a></span>
								</div>
								<div class="Intel_content_con">
									<p class="lntel_contentTitle">
										<span class="cate_block_3"><span class="cate_tit_3">Blog</span></span>
										<span><a href="#a">Should I dig in my back yard?</a></span>
									</p>
									<div class="clear"></div>
									<p class="Intel_content_Info">
										<span class="Intel_content_Info_name"><a href="#a">홍길동 과장</a></span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>UC컨설팅1팀</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>2011/03/21 22:28pm</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>조회 201</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>추천 34</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>댓글 107</span>
										<img src="<c:url value='/base/images/common/bar_info.gif'/>" alt="" />
										<span>공유 2</span>
									</p>	
								</div>
								<div class="clear"></div>
							</li>																				
						</ul>	
					</div>
					<!--//Intel_content End-->
					
					<!--blockButton_3 Start-->
					<div class="blockButton_3"> 
						<a class="button_3" href="#a"><span><ikep4j:message pre="${preButton}" key="addview" arguments="20"/> <img src="<c:url value='/base/images/icon/ic_more_ar.gif'/>" alt="" /></span></a>				
					</div>
					<!--//blockButton_3 End-->
					
				</div>
				<!--//Intelligent_l End-->
				
				<!--Intelligent_r Start-->
				<div class="Intelligent_r">
					<div class="Intelligent_rBox">
						<div class="company_Title">
							<h3>Company PVI : <span class="colorPoint">${compayPvi}</span></h3>
						</div>
						<div class="company_Info">
							<p>- My PVI : <span>${assessment.pvi}</span></p>
							<p>- Top : <span>${assessment.rate}%</span>&nbsp;&nbsp; Rank : <span>${assessment.rank}</span></p>
						</div>
					</div>
					<div>
						<div class="PowerUser_Title">
							<h3>Power User</h3>
						</div>
						
							<c:choose>
								<c:when test="${!empty topFiveRankList}">
								<ul class="user_List">
								<c:forEach var="info" items="${topFiveRankList}"  varStatus="loopStatus">
									<li><span>${loopStatus.index+1}</span><a href="#a" onclick="iKEP.showUserContextMenu(this, '${info.userId}', 'bottom');">
									<c:choose>
								      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
								       ${info.userName} 
								      </c:when>
								      <c:otherwise>
								       ${info.userEnglishName}  
								      </c:otherwise>
								     </c:choose>
									</a></li>
								</c:forEach>	
								</ul>
								</c:when>
							</c:choose>
						
					</div>
					<div class="popularTag colorTag">
						<div class="subTitle_2 noline mb10">
							<h3><ikep4j:message pre="${preSearch}" key="popular"/> &nbsp;&nbsp;<a class="button_s" href="#a"  id="popularSearchButton1"><span><ikep4j:message pre="${preSearch}" key="today"/></span></a> <a class="button_s" href="#a"  id="popularSearchButton2"><span><ikep4j:message pre="${preSearch}" key="week"/></span></a></h3>
						</div>
						
							<c:choose>
								<c:when test="${!empty popularList}">
								<ul id="popularList">
								<c:forEach var="info" items="${popularList}"  varStatus="loopStatus">
									<fmt:formatNumber var="rowNum" pattern="00" type="number" value="${loopStatus.index+1}"/>
									<li class="tag_No${rowNum}"><strong><span class="none">No.${rowNum}</span><a href="#a" onclick="searchCall('${info.searchKeyword}');">${info.searchKeyword}</a></strong></li>
								</c:forEach>
								</ul>	
								</c:when>
							</c:choose>
						
					</div>
					
				</div>
				<!--//Intelligent_r End-->

		  </div>
