<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.cafe.main.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
<c:set var="preHeader"  value="message.lightpack.cafe.main.header" />
<c:set var="preSearch"  value="message.lightpack.cafe.main.search" />
<c:set var="preDetail"  value="message.lightpack.cafe.cafe.createCafe.detail" />
<c:set var="preList"    value="message.lightpack.cafe.main.list" />
<c:set var="preButton"  value="message.lightpack.cafe.main.button" />
<c:set var="preScript"  value="message.lightpack.cafe.main.script" />
<c:set var="preScript1"  value="message.lightpack.cafe.alliance.listAlliance.script" />

<c:set var="preTree"    value="message.lightpack.cafe.board.boardItem.leftBoardView.tree" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>
<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>

<script type="text/javascript">
<!--
(function($) {
	
	$jq(document).ready(function() {
		//iKEP.setLeftMenu();	
		iKEP.iFrameMenuOnclick('<c:url value="/lightpack/cafe/main/cafeBody.do"/>?cafeId=${cafe.cafeId}');
		
		$jq("#memberJoinBtn").click(function(){
			if("${member.memberLevel}" != ""){
				alert("이미 카페에 가입하신 회원입니다.");
			}else{
				var url = "<c:url value='/lightpack/cafe/member/createCafeMemberView.do'/>?cafeId=${cafe.cafeId}";
				iKEP.showDialog({
					title : "카페 가입신청",
					width:500,
					height:200,
					url: url,
					modal:true,
					callback : function() { location.reload(); }
				});
			}
		});
		
		$jq("#memberLeaveBtn").click(function(){
			
			if("${member.memberLevel}" == 1) {
				alert('<ikep4j:message pre="${prefix}" key="leaveCafeSysopt" />');
				return;
			}
			
			if(!confirm('<ikep4j:message pre="${prefix}" key="leaveCafe" />')) {
				return;
			}
			
			$jq.ajax({
				url : '<c:url value='/lightpack/cafe/member/deleteMemberAjax.do' />?cafeId=${cafe.cafeId}',
				type : "post",
				success : function(result) {
					document.location = "<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}"
				}
			});	
			
		});
		
	$jq("#memberLeaveBtn2").click(function(){
			
			if("${member.memberLevel}" == 1) {
				alert('<ikep4j:message pre="${prefix}" key="leaveCafeSysopt" />');
				return;
			}
			
			if(!confirm('<ikep4j:message pre="${prefix}" key="leaveCafe" />')) {
				return;
			}
			
			$jq.ajax({
				url : '<c:url value='/lightpack/cafe/member/deleteMemberAjax.do' />?cafeId=${cafe.cafeId}',
				type : "post",
				success : function(result) {
					document.location = "<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=${cafe.cafeId}"
				}
			});	
			
		});
		
		$jq("#memberInviteBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/member/sendMailInviteView.do'/>?cafeId=${cafe.cafeId}');
		});
		
		$jq("#writeItemBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/board/boardItem/createBoardItemViewForMain.do'/>?searchConditionString=isAll^yes~cafeId^${cafe.cafeId}&cafeId=${cafe.cafeId}');
		});
		
		$jq("#recentItemBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do'/>?searchConditionString=isAll^yes~cafeId^${cafe.cafeId}');
		});
		
		$jq("#recentImageBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/cafe/getCafeImage.do'/>?cafeId=${cafe.cafeId}');
		});
		
		$jq("#activityBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/cafe/getCafeDateCount.do'/>?cafeId=${cafe.cafeId}');
		});
		
		$jq("#searchBtn").click(function(){
			iKEP.iFrameMenuOnclick('<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do'/>?searchConditionString=isAll^yes~cafeId^${cafe.cafeId}~searchColumn^title~searchWord^'+$jq('#searchTxt').val());
		});
		
		$jq('#searchTxt').keypress( function(event) { 
			if (event.which == '13') {
				$jq('#searchBtn').trigger("click");
			}
		});
		
		
		/* 노드 클릭시 이벤트*/ 
	    $("#boardList").delegate("a", "click", function () { 
	    	var boardItem = $(this).attr("id").split("_");  
	    	 
	    	if(boardItem[3] == "0" || boardItem[3] == "1") {  
	    		//게시판 
		    	if(boardItem[2] == "0") {  
		    		iKEP.iFrameMenuOnclick("<c:url value='/lightpack/cafe/board/boardItem/listBoardItemView.do'/>?boardId=" + boardItem[1] + "&popupYn=${popupYn}");
		    	//카테고리
		    	} else if(boardItem[2] == "1") { 
		    		return false; 
		    	//카테고리
		    	} else if(boardItem[2] == "2") { 
		    		$("#contentIframe").attr("src", "<c:url value='/lightpack/cafe/board/boardCommon/readLinkBoardView.do'/>?boardId=" + boardItem[1] + "&popupYn=${popupYn}");
		    	}
	    	}
	    	
	    	return false;
	    });
	});
	

})(jQuery);


//-->
</script>


		<h1 class="none">Contents</h1>					
		
		<c:if test="${cafe.layoutId == 'CF_LAYOUT_01'}">
		
			<!--blockLeft_fixed Start-->
			<div class="blockLeft_fixed cafe_ltp">
				
				<%@include file="cafeMenu.jsp"%>	
							
			</div>
			<!--//corner_RoundBox07 End-->
			
			
			<!--blockCenter Start-->
			<div class="blockCenter">
				<div class="blockCenter_con_3">
	
					<iframe id="contentIframe" name="contentIframe" width="100%"   scrolling="no" frameborder="0"></iframe>
							
				</div>
			</div>
			<!--//blockCenter End-->
		
		</c:if>
		
		
		<c:if test="${cafe.layoutId == 'CF_LAYOUT_02'}">
		
			<!--blockCenter Start-->
			<div class="blockCenter">
				<div class="blockCenter_con_2">
	
					<iframe id="contentIframe" name="contentIframe" width="100%"   scrolling="no" frameborder="0"></iframe>
					
							
				</div>
			</div>
			<!--//blockCenter End-->
			
			<!--blockLeft_fixed Start-->
			<div class="blockRight_fixed">
				
				<%@include file="cafeMenu.jsp"%>	
							
			</div>
			<!--//corner_RoundBox07 End-->
			
		</c:if>
					
		
	
	
