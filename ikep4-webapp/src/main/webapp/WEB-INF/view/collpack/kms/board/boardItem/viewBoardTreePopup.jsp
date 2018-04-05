<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.collaboration.board.boardItem.listBoardView.header" /> 
<c:set var="preList"    value="message.collpack.collaboration.board.boardItem.listBoardView.list" />
<c:set var="preCode"    value="message.collpack.collaboration.common.code" />
<c:set var="preButton"  value="message.collpack.collaboration.common.button" /> 
<c:set var="preMessage" value="message.collpack.collaboration.common.boardItem" />
<c:set var="preSearch"  value="message.collpack.collaboration.common.searchCondition" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
<!--

(function($){
	$(document).ready(function() {
		
		$("#divTab1").tabs();
		$("#divTab2").tabs();
		$("#divTab3").tabs();
		$("#divTab4").tabs();
	   	$("#divTab_box1").tabs();
		$("#divTab_s").tabs();
		$("#divTab5").tabs();
		$("#divTab6").tabs();
		$("#divTab7").tabs();
		
/*		
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
		}).jstree({
		    plugins:["themes", "ui", "json_data"],  
		    "json_data" : {"data" :  ${boardTreeJson}} 	    
		}); 
		
		$.jstree._reference("#phtml_1").open_node("#phtml_1");
*/
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			
			$(this).jstree("hide_icons");
			$("#boardTree").jstree("open_node","#root");
			iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenBoardMenu1.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", isKnowhow:${isKnowhow}};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) { 
		}).bind("close_node.jstree", function (event, data) { //alert("close")
		}); 


		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () {
	    	var targetBoardId = $(this).parent().attr("boardId");

	    	if("${orgBoardId}" == targetBoardId) {
	    		alert('<ikep4j:message pre="${preMessage}" key="sameBoardId" />');
	    		return false;
	    	}
	    	if($(this).parent().attr("boardType") == "0") {
		    	if(confirm($(this).parent().attr("boardName") +" 지식맵을 선택하시겠습니까?")){
		    		var data = $(this).parent().attr("boardId");
		    		iKEP.returnPopup(data);
		    	}
	    	}
	     }); 
	});	 
	
	$jq("#cancelBtn").click(function() {
		iKEP.closePop();			
	});
	
})(jQuery); 
//-->
</script> 
<div id="popup">
	<div id="popup_title_2">
		<h1>지식맵</h1>
		<a href="javascript:iKEP.closePop()"><span>닫기</span></a> 
		<div class="popup_bgTitle_l"></div>
	</div>
	<!--//popup_title End-->
	
	<!--popup_contents Start-->
	<div id="popup_contents">

    	<!--tab Start-->		
        <div id="divTab_box1" class="iKEP_tab_s">
            <ul>
                <li>
                	<a href="#tabs-1">
                		<c:if test="${isKnowhow eq '1'}">
                			일반정보
                		</c:if>
                		<c:if test="${isKnowhow eq '0'}">
                			업무노하우
                		</c:if>
                		<c:if test="${isKnowhow eq '3'}">
                		    원문 게시판
                		</c:if>
                	</a>
                </li>
            </ul>
            <div class="tab_conbox">
                <div id="tabs-1">
					<!-- div class="leftTree treeBox"  --> 
						<!-- div><img src="<c:url value='/base/images/common/img_title_cate1.gif'/>" alt="category" /></div--> 
						<div id="boardTree"></div> 
					<!-- /div -->                 	
                </div>
            </div>				
        </div>		
        <!--//tab End-->

	</div>
	<!--//popup_contents End-->

	<!--popup_footer Start-->
	<div id="popup_footer">
		
	</div>
</div>
	<!--//popup_footer End-->
