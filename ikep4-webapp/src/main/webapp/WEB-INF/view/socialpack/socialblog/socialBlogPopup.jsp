<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preMain" value="ui.socialpack.socialblog.common.webstandard" />
<c:set var="preManage" value="ui.socialpack.socialblog.management" />
<c:set var="preMessage" value="message.socialpack.socialblog.common.socialBoardItem" />
<c:set var="preManageMessage" value="message.socialpack.socialblog.management" />

<%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/units/socialpack/socialblog.js"/>"></script>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript">

(function($){
	
	$jq(document).ready(function() {
		// 화면 로딩시 각각 페이지 호출 시작
		getlistSocialBoardItemView();

	});
	
	
	// 처음 로딩시  고정 메뉴 포스팅 글 조회 
	getlistSocialBoardItemView = function() {
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/readSocialBoardItemView.do?itemId=${itemId}"/>',
		    success : function(result) {
		    	$jq("#socialBlogPopup").html(result);
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
		    	$jq("#socialBlogPopup").html(result);
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
		    	$jq("#socialBlogPopup").html(result);
		    }
		});	
	};
	
	// 검색 키워드 또는 날자별 검색을 통해 
	getSearchBlogPosting = function(searchType,searchKeyword,pagePerRecord) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','searchColumn':searchType,'searchWord':searchKeyword,'pagePerRecord':pagePerRecord},
		    type : "get",
		    success : function(result) {
		    	$jq("#socialBlogPopup").html(result);
		    }
		});
		
	};
	
	
	// 검색 키워드 또는 날자별 검색을 통해 
	getCategoryBlogPosting = function(categoryId) {
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/listSocialBoardItemView.do"/>',
		    data : {'blogOwnerId':'${socialBlog.ownerId}','categoryId':categoryId},
		    type : "get",
		    success : function(result) {
		    	$jq("#socialBlogPopup").html(result);
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
		    	$jq("#socialBlogPopup").html(result);
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
			    	$jq("#socialBlogPopup").html(result);
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
			    	$jq("#socialBlogPopup").html(result);
			    }
			});
		}
		
	};
	
})(jQuery);

</script>


			<!--mainContents Start-->
			<div class="conPadding_4">
				<h1 class="none"><ikep4j:message pre='${preMain}' key='content' /></h1>
				<div id="socialBlogPopup"></div>
			</div>



