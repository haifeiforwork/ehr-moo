<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preManageMessage" value="message.socialpack.socialblog.management" />
<%-- 메시지 관련 Prefix 선언 End --%>

<body <c:if test="${socialBlog.isPrivateImage == 1}"> style="background-image:Url('<c:url value="/support/fileupload/downloadFile.do?fileId=${socialBlog.imageFileId}" />');" </c:if> <c:if test="${socialBlog.isPrivateImage == 0}"> class="${socialBlog.imageUrl}" </c:if>>									


<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">

(function($){
	
	$jq(document).ready(function() {
		
		// 화면 로딩시 각각 페이지 호출 시작
		getlistSocialBoardItemView();

		$(".divPortletLoad").trigger("click");
	});
	
	// 블로그 로딩시 해당 Portlet 메뉴를 로딩 하기 위한 메서드
	getSocialBlogPortLayout = function(el,portletUrl) {
		$jq.ajax({
		    url : iKEP.getContextRoot() + portletUrl,
		    data : {'blogOwnerId':'${socialBlog.ownerId}'},
		    type : "get",
		    success : function(result) {
		    	$(el).html(result);
		    	$(el).removeAttr( "onclick" );
		    }
		});	
	};
	
	// 처음 로딩시  고정 메뉴 포스팅 글 조회 
	getlistSocialBoardItemView = function() {
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do?blogOwnerId=${socialBlog.ownerId}"/>',
		    success : function(result) {

		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
	};
	
	
	// 검색 버튼을 통한 고정 메뉴 포스팅 글 조회 
	getSearchlistSocialBoardItemView = function() {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : $("#searchBoardItemForm").serialize(),
		    type : "post",
		    success : function(result) {

		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
	};	
	
	// 내가 팔로워 하고 있는 사용자들이 작성한 Microblog List
	getMicroBlogFollowingTimeLine = function() {
		$jq.ajax({
		    url : iKEP.getContextRoot() + "/socialpack/microblogging/followingTimelineHome.do",
		    data : {'userId':'${socialBlog.ownerId}'},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").addClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});	
	};
	
	//날자별 검색을 통해 
	getSearchDateBlogPosting = function(searchDate) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','searchDate':searchDate},
		    type : "get",
		    success : function(result) {

		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
	};
	
	
	// 검색 키워드  통해 
	getSearchBlogPosting = function(searchType,searchKeyword,pagePerRecord) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','searchColumn':searchType,'searchWord':searchKeyword,'pagePerRecord':pagePerRecord},
		    type : "get",
		    success : function(result) {

		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
		
	};
	
	
	// 카테고리 별 검색 
	getCategoryBlogPosting = function(categoryId) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','categoryId':categoryId},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
		
	};
	
	// 최근 코멘트가 달린 포스팅을 검색 
	getRecentCmtBlogPosting = function(itemId) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','itemId':itemId},
		    type : "get",
		    success : function(result) {
		    	
		    	$(document).scrollTop(0);
		    	$jq("#blockContentsArea").removeClass("corner_RoundBox07");
		    	$jq("#blockContentsArea").html(result);
		    	
		    }
		});
		
	};
	
	// 포스팅 선택 삭제 
	userDeleteSocialBoardItem = function(itemId) {
		
		if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
			$jq.ajax({	
			    url : '<c:url value="/socialpack/socialblog/userDeleteSocialBoardItem.do"/>',
			    data : {'blogOwnerId':'${socialBlog.ownerId}','itemId':itemId},
			    type : "post",
			    success : function(result) {
			    	
			    	$(document).scrollTop(0);
			    	$jq("#blockContentsArea").html(result);

			    }
			});
		}
		
	};
	
	
	// 포스팅 선택 삭제 by Blog Admin 
	adminDeleteSocialBoardItem = function(itemId) {
		
		if(confirm("<ikep4j:message pre="${preMessage}" key="deleteItem" />")) {
			$jq.ajax({	
			    url : '<c:url value="/socialpack/socialblog/adminDeleteSocialBoardItem.do"/>',
			    data : {'blogOwnerId':'${socialBlog.ownerId}','itemId':itemId},
			    type : "post",
			    success : function(result) {
			    	
			    	$(document).scrollTop(0);
			    	$jq("#blockContentsArea").html(result);
			    	
			    }
			});
		}
		
	};
	
})(jQuery);  
//-->
</script> 

		<!--blockMain Start-->
		<div id="blockMain">
				
			<!--mainContents Start-->
			<div id="mainContents" class="conPadding_2 fixedContents">
				<h1 class="none"><ikep4j:message pre='${preMain}' key='content' /></h1>
				
				
				<c:if test="${mySocialBlogLayout != null}">
					<c:choose>
						<c:when test="${mySocialBlogLayout.colCount == 2}">
							<c:forEach var="layoutListColumn" items="${mySocialBlogLayout.socialBlogLayoutColumnList}" varStatus="colStatus">
								<c:choose>
									<c:when test="${layoutListColumn.isFixed == 0}">
										<c:if test="${colStatus.index == 0}">
											<!--blockLeft_fixed Start 좌 -->
											<div class="blockLeft_fixed">
												<!--corner_RoundBox07 Start-->
												<div class="corner_RoundBox07">
													<c:if test="${mySocialBlogLayout != null}">
													<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
														<c:if test="${ portletLayout.colIndex == 1 }">
															<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
														</c:if>
													</c:forEach>
													</c:if>
													<div class="l_t_corner"></div>
													<div class="r_t_corner"></div>
													<div class="l_b_corner"></div>
													<div class="r_b_corner"></div>
												</div>
												<!--corner_RoundBox07 End-->
											</div>
											<!--//blockLeft_fixed End-->
										</c:if>
										<c:if test="${colStatus.index == 1}">
											<!--blockRight_fixed Start 우 -->	
											<div class="blockRight_fixed">
												<!--corner_RoundBox07 Start-->
												<div class="corner_RoundBox07">
													<c:if test="${mySocialBlogLayout != null}">
													<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
														<c:if test="${ portletLayout.colIndex == 2 }">
															<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
														</c:if>
													</c:forEach>
													</c:if>
													<div class="l_t_corner"></div>
													<div class="r_t_corner"></div>
													<div class="l_b_corner"></div>
													<div class="r_b_corner"></div>
												</div>
												<!--corner_RoundBox07 End-->
											</div>	
											<!--//blockRight_fixed End-->
										</c:if>

									</c:when>
									<c:otherwise>
										<c:if test="${colStatus.index == 0}">
											<!-- 고정영역 항상 보여 지는 내용 영역  -->
											<!--blockCenter Start 우 -->
											<div class="blockCenter">
												<div class="blockCenter_con_2" id="blockContentsArea">
												</div>
											</div>
											<!--//blockCenter End-->
										</c:if>
										<c:if test="${colStatus.index == 1}">
											<!-- 고정영역 항상 보여 지는 내용 영역  -->
											<!--blockCenter Start 좌-->
											<div class="blockCenter">
												<div class="blockCenter_con_3" id="blockContentsArea">
												</div>
											</div>
											<!--//blockCenter End-->
										</c:if>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<!--blockLeft_fixed Start-->
							<div class="blockLeft_fixed">
								<!--corner_RoundBox07 Start-->
								<div class="corner_RoundBox07">
									<c:if test="${mySocialBlogLayout != null}">
										<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
											<c:if test="${ portletLayout.colIndex == 1 }">
												<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
											</c:if>
										</c:forEach>
									</c:if>
									<div class="l_t_corner"></div>
									<div class="r_t_corner"></div>
									<div class="l_b_corner"></div>
									<div class="r_b_corner"></div>
								</div>
								<!--corner_RoundBox07 End-->
							</div>
							<!--//blockLeft_fixed End-->
							
							<!-- 고정영역 항상 보여 지는 내용 영역  -->
							<!--blockCenter Start-->
							<div class="blockCenter">
								<div class="blockCenter_con" id="blockContentsArea">									
								</div>
							</div>
							<!--//blockCenter End-->
							
							<!--blockRight_fixed Start-->	
							<div class="blockRight_fixed">
								<!--corner_RoundBox07 Start-->
								<div class="corner_RoundBox07">
									<c:if test="${mySocialBlogLayout != null }">
										<c:forEach var="portletLayout" items="${socialBlogPortletLayoutList}" varStatus="colStatus">
											<c:if test="${ portletLayout.colIndex == 2 }">
												<div class="divPortletLoad" onclick="getSocialBlogPortLayout(this,'${portletLayout.socialBlogPortlet.viewUrl}')"></div>
											</c:if>
										</c:forEach>
									</c:if>
									<div class="l_t_corner"></div>
									<div class="r_t_corner"></div>
									<div class="l_b_corner"></div>
									<div class="r_b_corner"></div>
								</div>
								
								<!--corner_RoundBox07 End-->						
							</div>	
							<!--//blockRight_fixed End-->
						</c:otherwise>
					</c:choose>
				</c:if>
				
			</div>

			<!--//mainContents_3 End-->

		</div>
		<!--//blockMain End-->

</body>
