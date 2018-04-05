<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.collpack.kms.main.menu" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="message.collpack.kms.main.header" />
<c:set var="preSearch"  value="message.collpack.kms.main.search" />
<c:set var="preCommon"  value="message.collpack.common" />
<c:set var="preList"    value="message.collpack.kms.main.list" />
<c:set var="preButton"  value="message.collpack.kms.main.button" />
<c:set var="preScript"  value="message.collpack.kms.main.script" />


<c:set var="preTree"    value="message.collpack.kms.board.boardItem.leftBoardView.tree" /> 
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>

<script type="text/javascript" src="<c:url value="/base/js/portlet.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jclock_kms.js"/>"></script>
<script type="text/javascript">
iKEP.setLoadTime(${todayM});
var compulsionTimeFlg = false;
var compulsionStartTimeFlg = false;
var compulsionEndTimeFlg = false;
<!--
(function($) {

	topScroll = function(){ 
		$jq(window).scrollTop(0);
	};
	
	resizeIframe = function() { 
		iKEP.iFrameContentResize();  
	};
	
	defaultSetting = function(workspaceId) {		
		$jq.get('<c:url value="/collpack/kms/member/updateWorkspaceDefaultSet.do"/>',
			{workspaceId:workspaceId},
			function(data){
				location.href="<c:url value='/collpack/kms/main/knowledgeShare.do' />?workspaceId="+workspaceId;
			}
		)
		return false;
	};
	menu = function(id){
	
		if($jq("#"+id).is(':hidden'))
		{
			$jq("#"+id).show();
		}
		else
			$jq("#"+id).hide();
	};
	
	
	boardReload = function(){
		//alert("boardReload!");
		$jq("#boardTree").jstree("refresh", -1);
		$jq("#boardTree1").jstree("refresh", -1);
		$jq("#boardTree2").jstree("refresh", -1);
		$jq("#boardTree3").jstree("refresh", -1);
		$jq("#boardTree4").jstree("refresh", -1);
	};
	
	openListPrivateMonth = function(){
		iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do"/>?gubun=2&popupYn=false&pagePerRecord=20&startDate='+$("#startDate1").val()+'&endDate=');
	};
	
	openListPrivateYear = function(){
		iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do"/>?gubun=2&popupYn=false&pagePerRecord=20&startDate='+$("#startDate2").val()+'&endDate=');
	};
	
	openListPublicMonth = function(){
		iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do"/>?gubun=4&popupYn=false&pagePerRecord=20&startDate='+$("#startDate1").val()+'&endDate=');
	};
	
	openListPublicYear = function(){
		iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do"/>?gubun=4&popupYn=false&pagePerRecord=20&startDate='+$("#startDate2").val()+'&endDate=');
	};
	
	$jq(document).ready(function() {
		
		iKEP.setLeftMenu();
		
		$("#divTab1").tabs();
		$("#divTab2").tabs();
		$("#divTab3").tabs();
		$("#divTab4").tabs();
	   	$("#divTab_box1").tabs();
		$("#divTab_s").tabs();
		$("#divTab5").tabs();
		$("#divTab6").tabs();
		$("#divTab7").tabs();


///////////////////////////////////////////////////////////////////////////////////////////////		
		if('${boardId}'!=null && '${boardId}'!="")
			iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do'/>?boardId=${boardId}');
		else
			iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/main/knowledgeShareBody.do"/>');
		
////////////////////////////////////////////////////////////////////////////////////////////////		
		$("#boardTree").bind("loaded.jstree", function (event, datet) {
			
			$(this).jstree("hide_icons");
			$("#boardTree").jstree("open_node","#root");
			iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenBoardMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", isKnowhow:"1"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) { 
		}).bind("close_node.jstree", function (event, data) { //alert("close")
		}); 
		
		//$.jstree._reference("#phtml_1").open_node("#phtml_1");
			
		/* 노드 클릭시 이벤트*/
	    $("#boardTree").delegate("a", "click", function () { 
	    	//alert($(this).parent().attr("boardId"));

	    	if($(this).parent().attr("boardType") == "0") {
	    		iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId='/>' + $(this).parent().attr("boardId") + '&isKnowhow=' + $(this).parent().attr("isKnowhow") + '&popupYn=false&pageGubun=boardItem&pageFirst=true'  ); 
	    	}else if($(this).parent().attr("boardType") == "2") {
	    		if($(this).parent().attr("urlPopup")=="0")
	    			$("#contentIframe").attr("src", "<c:url value='/collpack/kms/board/boardCommon/readLinkBoardView.do'/>?boardId=" +$(this).parent().attr("boardId") + "&popupYn=${popupYn}");
	    		else
	    			iKEP.popupOpen($(this).parent().attr("url"), {width:900, height:500,location:true, menubar:true, scrollbars:true, status:true, titlebar:true, toolbar:true}, "Popup");
	    	}
	    });
////////////////////////////////////////////////////////////////////////////////////////////////
		$("#boardTree1").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#boardTree1").jstree("open_node","#root");
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenBoardMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", isKnowhow:"0"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) {
		}).bind("close_node.jstree", function (event, data) {
		}); 
		
		$("#boardTree3").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#boardTree3").jstree("open_node","#root");
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenBoardMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", isKnowhow:"3"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) {
		}).bind("close_node.jstree", function (event, data) {
		}); 

			
		/* 노드 클릭시 이벤트*/
	    $("#boardTree1").delegate("a", "click", function () { 

	    	if($(this).parent().attr("boardType") == "0") {
	    		iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId='/>' + $(this).parent().attr("boardId") + '&isKnowhow=' + $(this).parent().attr("isKnowhow") + '&popupYn=false&pageGubun=boardItem&pageFirst=true' ); 
	    	}else if($(this).parent().attr("boardType") == "2") {
	    		if($(this).parent().attr("urlPopup")=="0")
	    			$("#contentIframe").attr("src", "<c:url value='/collpack/kms/board/boardCommon/readLinkBoardView.do'/>?boardId=" +$(this).parent().attr("boardId") + "&popupYn=${popupYn}");
	    		else
	    			iKEP.popupOpen($(this).parent().attr("url"), {width:900, height:500,location:true, menubar:true, scrollbars:true, status:true, titlebar:true, toolbar:true}, "Popup");
	    	}
	    });
		
	    $("#boardTree3").delegate("a", "click", function () { 

	    	if($(this).parent().attr("boardType") == "0") {
	    		iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId='/>' + $(this).parent().attr("boardId") + '&isKnowhow=' + $(this).parent().attr("isKnowhow") + '&popupYn=false&pageGubun=boardItem&pageFirst=true' ); 
	    	}else if($(this).parent().attr("boardType") == "2") {
	    		if($(this).parent().attr("urlPopup")=="0")
	    			$("#contentIframe").attr("src", "<c:url value='/collpack/kms/board/boardCommon/readLinkBoardView.do'/>?boardId=" +$(this).parent().attr("boardId") + "&popupYn=${popupYn}");
	    		else
	    			iKEP.popupOpen($(this).parent().attr("url"), {width:900, height:500,location:true, menubar:true, scrollbars:true, status:true, titlebar:true, toolbar:true}, "Popup");
	    	}
	    });
////////////////////////////////////////////////////////////////////////////////////////////////
		$("#boardTree2").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#boardTree2").jstree("open_node","#root");
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenBoardMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId :  typeof node == "object" ?  $(node).attr("boardId") : "", isKnowhow:"2"};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) {
		}).bind("close_node.jstree", function (event, data) {
		}); 

			
		/* 노드 클릭시 이벤트*/
	    $("#boardTree2").delegate("a", "click", function () { 

	    	if($(this).parent().attr("boardType") == "0") {
	    		iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listBoardItemView.do?boardId='/>' + $(this).parent().attr("boardId") + '&isKnowhow=' + $(this).parent().attr("isKnowhow") + '&popupYn=false&pageGubun=boardItem&pageFirst=true' ); 
	    	}else if($(this).parent().attr("boardType") == "2") {
	    		if($(this).parent().attr("urlPopup")=="0")
	    			$("#contentIframe").attr("src", "<c:url value='/collpack/kms/board/boardCommon/readLinkBoardView.do'/>?boardId=" +$(this).parent().attr("boardId") + "&popupYn=${popupYn}");
	    		else
	    			iKEP.popupOpen($(this).parent().attr("url"), {width:900, height:500,location:true, menubar:true, scrollbars:true, status:true, titlebar:true, toolbar:true}, "Popup");
	    	}
	    });
		
	    $("#boardTree4").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
			
			 $("#boardTree4").jstree("open_node","#root");
			 iKEP.iFrameContentResize();
		}).jstree({
		    plugins:["themes", "ui", "json_data"],   
		    "json_data" : {
		    	"ajax" : {
		    		"url" : "<c:url value='/collpack/kms/board/boardAdmin/listChildrenNoticeMenu.do'/>",
		    		"data" : function (node) {
		    			//iKEP.debug(node)
		    			return { boardId : "", isKnowhow:""};
		    			iKEP.iFrameContentResize();
		    		}
		    	}
		    }	        
		}).bind("open_node.jstree", function (event, data) {
		}).bind("close_node.jstree", function (event, data) {
		}); 

			
		/* 노드 클릭시 이벤트*/
	    $("#boardTree4").delegate("a", "click", function () { 
    		iKEP.iFrameMenuOnclick("<c:url value='/collpack/kms/notice/listNoticeItemView.do'/>"); 
	    });
	    var clockOption = {
				//seedTime에 서버 GMT시간을 넣으면 됨. 시간 기준은 millisecond. 스크립트로는 new Date()).getTime() 
		      	seedTime: iKEP.getCurTime().getTime(),	//${todayM}
					format : "%H:%M:%S"
		    };
		$(".info_dt").jclock(clockOption);
		if("${compulsionDay}" == "1"){
			if("${compulsionTime.mon}" == "1"){
				compulsionTimeFlg = true;
			}
		}else if("${compulsionDay}" == "2"){
			if("${compulsionTime.tue}" == "1"){
				compulsionTimeFlg = true;
			}
		}else if("${compulsionDay}" == "3"){
			if("${compulsionTime.wed}" == "1"){
				compulsionTimeFlg = true;
			}
		}else if("${compulsionDay}" == "4"){
			if("${compulsionTime.thu}" == "1"){
				compulsionTimeFlg = true;
			}
		}else if("${compulsionDay}" == "5"){
			if("${compulsionTime.fri}" == "1"){
				compulsionTimeFlg = true;
			}
		}		
		if(compulsionTimeFlg){
			setInterval(compulsionTimeCheck, 5000);
			//compulsionTimeCheck();
		}
		
		$("#startTimeSaveBtn").click(function() { 
			$("#compulsionTimeClickFlg").val("1");
			$.post('<c:url value="/collpack/kms/main/compulsionTimeClickSave.do"/>', $("#compulsionTimeForm").serialize()) 
		    .success(function(data) {
		    	alert('저장되었습니다.');
		    	compulsionStartTimeFlg = true;
		    	$("#endTimeSaveBtn").show();
				$("#startTimeSaveBtn").hide();
		    })
		    .error(function(event, request, settings) { 
		    	alert('error');
		    });  
		});
		
		$("#endTimeSaveBtn").click(function() { 
			$("#compulsionTimeClickFlg").val("2");
			$.post('<c:url value="/collpack/kms/main/compulsionTimeClickSave.do"/>', $("#compulsionTimeForm").serialize()) 
		    .success(function(data) {
		    	alert('저장되었습니다.');
		    	compulsionEndTimeFlg = true;
		    	$("#compulsionTimeDiv").hide();
		    })
		    .error(function(event, request, settings) { 
		    	alert('error');
		    });  
		});
		
////////////////////////////////////////////////////////////////////////////////////////////////
	});
	
	compulsionTimeCheck = function(){
		var nowDate = new Date();
		var hour = leadingZeros(nowDate.getHours(),2);
		var min = leadingZeros(nowDate.getMinutes(),2);
		var shour = "${compulsionTime.startHour}";
		var smin = "${compulsionTime.startMin}";
		var ehour = "${compulsionTime.endHour}";
		var emin = "${compulsionTime.endMin}";
		
		var tmpTime = hour+min;
		var tmpsTime = shour+smin;
		var tmpeTime = ehour+emin;
		
		if((tmpTime > tmpsTime || tmpTime == tmpsTime) && (tmpTime == tmpeTime || tmpTime < tmpeTime)){
			if(compulsionStartTimeFlg){
				if(compulsionEndTimeFlg){
					$("#compulsionTimeDiv").hide();
				}else{
					$("#compulsionTimeDiv").show();
					$("#endTimeSaveBtn").show();
					$("#startTimeSaveBtn").hide();
				}
			}else{
				if(compulsionEndTimeFlg){
					$("#compulsionTimeDiv").hide();
				}else{
					if("${compulsionStartTimeClick}" == "0"){
						$("#compulsionTimeDiv").show();
						$("#startTimeSaveBtn").show();
						$("#endTimeSaveBtn").hide();
					}else if("${compulsionStartTimeClick}" == "1" && "${compulsionEndTimeClick}" == "0"){
						$("#compulsionTimeDiv").show();
						$("#endTimeSaveBtn").show();
						$("#startTimeSaveBtn").hide();
					}else{
						$("#compulsionTimeDiv").hide();
					}
				}
			}
		}else{
			$("#compulsionTimeDiv").hide();
		}
	};
	
	leadingZeros = function(n, digits) {
	// 1 -> 01 과 같이 변경하기
	var zero = '';
	n = n.toString();

	if (n.length < digits) {
	for (i = 0; i < digits - n.length; i++)
	zero += '0';
	}
	return zero + n;
	} 
})(jQuery);
//-->
</script>
<form id="compulsionTimeForm" method="post" onsubmit="return false" action="">
	<input type="hidden" id="compulsionTimeClickFlg" name="compulsionTimeClickFlg" />
</form>
		<!--leftMenu Start-->

				<h1 class="none">레프트메뉴</h1>
                <h2 style="margin-bottom:10px;">
                	<a href="<c:url value="/collpack/kms/main/knowledgeShare.do" />">
                	<span><img style="padding-top:6px" src="<c:url value='/base/images/title/lmt_know_know1.gif'/>"/></span>
                	</a>
                </h2>
                <div class="blockButton_2 mb10">
                	<a class="button_2" href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/createBoardItemView.do?boardId=0&isKnowhow=0&searchConditionString='/>')"><span><img src="<c:url value="/base/images/icon/ic_idea.png"/>" width="14" height="17" alt="지식등록"/>지식등록</span></a> 
				</div>
				
				<div id="compulsionTimeDiv" class="boxList_2" style=" text-align: center; height: 80px; vertical-align: middle; background:#f3f3f3;display: none;">
                     <span class="info_dt" style="font-size: 35px;"></span>
                     <span id="startTimeSaveBtn" style="cursor: pointer;"><img style="padding-top: 20px;" src="<c:url value='/base/images/common/btn_kmtime_1.gif'/>"/></span>
                     <span id="endTimeSaveBtn" style="cursor: pointer;display: none;"><img style="padding-top: 20px;" src="<c:url value='/base/images/common/btn_kmtime_2.gif'/>"/></span>
                </div>
                <!--tab Start-->		
                <div id="divTab1" class="iKEP_tab" style="margin-bottom:0px !important;">
                    <ul>
                        <li><a href="#tabs-1">월간활동</a></li>
                        <li><a href="#tabs-2">연간활동</a></li>
                    </ul>
                    <div>
                        <div id="tabs-1" style="padding-top:0px !important; padding-bottom:0px !important;" >
                            <div class="boxList_2" style="padding-top:5px !important; padding-bottom:3px !important;">
                                <div class="subTitle_2" style="line-height:16px !important; margin-bottom:5px !important; ">
                                    <img src="<c:url value='/base/images/icon/ic_cal2.gif'/>"/> 나의 월간지식활동
                                    <div class="date">(${yyyy}.${mm}.01 ~ ${yyyy}.${mm}.${dd})</div>
                                    <input type="hidden" name="startDate1" id="startDate1" value="${yyyy}-${mm}-01" /> 
									<input type="hidden" name="endDate1" id="endDate1" value="${yyyy}-${mm}-${dd}" />
                                </div>
                                <ul>
                                	<li>등록건수: <a href="#a" onclick="openListPublicMonth();"><strong>${performance2.regCnt}</strong></a> 건</li>
                                    <li>채택건수: <a href="#a" onclick="openListPrivateMonth();"><strong>${performance2.regSum}</strong></a> 건</li>
                                    <li>평가점수: <span class="colorPointS">${performance2.markSum}</span> 점</li>
                                    <li>추천/댓글점수: <span class="colorPointS">${performance2.scoreSum}</span> 점</li>
                                </ul>
                            </div>
                        </div>
                        <div id="tabs-2" style="padding-top:0px !important; padding-bottom:0px !important;">
                            <div class="boxList_2" style="padding-top:5px !important; padding-bottom:3px !important;">
                                <div class="subTitle_2" style="line-height:16px !important; margin-bottom:5px !important; ">
                                    <img src="<c:url value='/base/images/icon/ic_cal3.gif'/>"/> 나의 연간지식활동
                                    <div class="date">
                                    	<c:if test="${mm == '12'}">
                                    		(${yyyy}.12.01 ~ ${yyyy+1}.11.30)
										</c:if>
										<c:if test="${mm != '12'}">
                                    		(${yyyy1}.12.01 ~ ${yyyy}.11.30)
										</c:if>
                                    </div>
                                    <c:if test="${mm == '12'}">
	                                    <input type="hidden" name="startDate2" id="startDate2" value="${yyyy}.12.01" /> 
										<input type="hidden" name="endDate2" id="endDate2" value="${yyyy+1}.11.30" />
									</c:if>
									<c:if test="${mm != '12'}">
										<input type="hidden" name="startDate2" id="startDate2" value="${yyyy1}.12.01" /> 
										<input type="hidden" name="endDate2" id="endDate2" value="${yyyy}.11.30" />
									</c:if>
                                </div>
                                <ul>
                                	<li>등록건수: <a href="#a" onclick="openListPublicYear();"><strong>${performance1.regCnt}</strong></a> 건</li>
                                    <li>채택건수: <a href="#a" onclick="openListPrivateYear();"><strong>${performance1.regSum}</strong></a> 건</li>
                                    <li>평가점수: <span class="colorPointS">${performance1.markSum}</span> 점</li>
                                    <li>추천/댓글점수: <span class="colorPointS">${performance1.scoreSum}</span> 점</li>
                                </ul>
                            </div>
                        </div>
                    </div>				
                </div>		
                <!--//tab End-->
				<div class="left_fixed">
					<ul>
						<li class="opened"><a href="#a">지식맵</a>
							<!--tab Start-->
                            <ul style="padding-top:2px !important; padding-bottom:0px !important;">    	
                                <div id="divTab_box1" class="iKEP_tab_s" style="margin-bottom:5px;">
                                    <ul>
                                        <li><a href="#tabs-1">일반</a></li>
                                        <li><a href="#tabs-2">업무</a></li>
                                        <li><a href="#tabs-3">원문</a></li>
                                        <li><a href="#tabs-4">이슈</a></li>
                                    </ul>
                                    <div class="tab_conbox">
                                        <div id="tabs-1"><div id="boardTree"></div></div>
                                        <div id="tabs-2"><div id="boardTree1"></div></div>
                                        <div id="tabs-3"><div id="boardTree3"></div></div>
                                        <div id="tabs-4"><div id="boardTree4"></div></div>
                                    </div>				
                                </div>		
							</ul>	
                            <!--//tab End-->				
						</li>
						<%-- <li class="closed"><a href="#a">최신지식</a>
							<ul style="display:none">
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listLatestItemView.do?isKnowhow=1&popupYn=false&pageGubun=latestItem&pagePerRecord=20" />')">일반정보</a></li>				
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listLatestItemView.do?isKnowhow=0&popupYn=false&pageGubun=latestItem&pagePerRecord=20" />')">업무노하우</a></li>
							</ul>						
						</li> --%>
						<li class="opened"><a href="#a">지식조회</a>
							<ul>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=1&popupYn=false&pageGubun=searchItem&pagePerRecord=20" />')">일반정보</a></li>				
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=0&popupYn=false&pageGubun=searchItem&pagePerRecord=20" />')">업무노하우</a></li>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listSearchItemView.do?isKnowhow=3&popupYn=false&pageGubun=searchItem&pagePerRecord=20" />')">원문 게시판</a></li>
								<c:if test="${isKeyInfoAssessor}">
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listKeyInfoItemView.do?gubun=2&popupYn=false&pagePerRecord=20"/>')">경영진 보고정보</a></li>
								</c:if>
								<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/qna/listQnaItemView.do" />')">무림지식인(Q&A)</a></li>
							</ul>						
						</li>
						
						<!-- <li class="closed"><a href="#a">지식공유사이트</a>
							<ul style="display:none">
								<li class="licurrent">
									<div id="boardTree2"></div>
								</li>
							</ul>						
						</li>	 -->
						
						<li class="closed"><a href="#a">지식실적</a>
							<ul style="display:none">
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/perform/listPrivate.do"/>')">실적</a></li>				
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/perform/listPrivateStat.do"/>')">개인별 연간 지식실적통계</a></li>
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/perform/listTeamStat.do"/>')">부서별 연간 지식실적통계</a></li>
								<c:if test="${isSystemAdmin || user.leader == '1'}">
									<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/perform/listPrivateStandby.do"/>')">부서별 오픈 전 지식실적</a></li>
								</c:if>
							</ul>						
						</li>
						<li class="closed"><a href="#a">나의지식</a>
							<ul style="display:none">
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do?gubun=0&popupYn=false&pagePerRecord=20"/>')">임시저장</a></li>				
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do?gubun=4&popupYn=false&pagePerRecord=20"/>')">등록완료</a></li>
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do?gubun=2&popupYn=false&pagePerRecord=20"/>')">채택완료</a></li>
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTargetItemView.do?gubun=2&popupYn=false&pagePerRecord=20"/>')">배포지식</a></li>
								<%-- <li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listTemporaryItemView.do?gubun=3&popupYn=false&pagePerRecord=20"/>')">미공유지식</a></li> --%>
								<%-- <li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/board/boardItem/listEinfogradeItemView.do?gubun=0&popupYn=false&pagePerRecord=20"/>')">미공유지식</a></li> --%>
							</ul>						
						</li>												
                        <!-- li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listTemporaryItemView.do'/>')"><ikep4j:message pre='${prefix}' key='detail.menu.temporary' /></a></li-->
						<c:if test="${isAssessor}">
						<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listAssessItemView.do?popupYn=false&pagePerRecord=20'/>')"><ikep4j:message pre='${prefix}' key='detail.menu.assess' /></a></li>
						</c:if>
						<c:if test="${isSystemAdmin}">
                        <li class="closed"><a href="#a">Administrator</a>
							<ul style="display:none">
                                <li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listAssessItemView.do?popupYn=false&pagePerRecord=20'/>')">관리자평가</a></li>
                                <li class="opened"><a href="#a">권한관리</a>
									<ul>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/listUserPermission.do"/>')">사용자관리</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/listLeaderPermission.do"/>')">부서실적조회권한관리</a></li>
										<!-- li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/listTeamCntByWorkPlace.do"/>')">부서지식관리</a></li-->
									</ul>
								</li>
                                <li class="opened"><a href="#a">지식맵관리</a>
									<ul>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/board/listBoardView.do?isKnowHow=1"/>')">일반정보</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/board/listBoardView.do?isKnowHow=0"/>')">업무노하우</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/board/listBoardView.do?isKnowHow=3"/>')">원문 게시판</a></li>
										<%-- <li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/board/listBoardView.do?isKnowHow=2"/>')">지식공유사이트</a></li> --%>
									</ul>
								</li>
								<li class="opened"><a href="#a">Handsome한 무림가족</a>
									<ul>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/listHandsomePeople.do"/>')">Handsome한 무림인</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/listHandsomeTeam.do"/>')">Handsome한 부서</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/listAward.do"/>')">두근두근 포상품</a></li>
										<li><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/periodManage.do"/>')">게시기간 관리</a></li>
									</ul>
								</li>
								<%-- <li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/listWinners.do"/>')">우수지식인관리</a></li> --%>
								<li class="licurrent"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value="/collpack/kms/admin/winner/getAssessStandard.do"/>')">지식활동 평가기준관리</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/perform/listExpert.do'/>')">전문가평가이력</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/listRecommendReply.do'/>')">추천/댓글 이력</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/announce/listAnnounceItemView.do'/>')">지식공지사항</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/site/listSiteItemView.do'/>')">지식공유사이트</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listEinfogradeItemView.do?gubun=1&popupYn=false&pagePerRecord=20'/>')">미공유지식</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/board/boardItem/listEinfogradeItemView.do?gubun=2&popupYn=false&pagePerRecord=20'/>')">보안지식</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/expertFieldManagement.do'/>')">전문분야 관리</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/expertManagement.do'/>')">전문가 설정</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/listSpecialUser.do'/>')">특정조회자관리</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/createFormMessage.do'/>')">등록화면주의사항 설정</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/listKeyInfoPermission.do'/>')">경영진보고정보 조회자 설정</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/compulsionTimeSettingForm.do'/>')">의무사용시간 설정</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/admin/compulsionTimeLogView.do'/>')">의무사용시간 이력</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/notice/listNoticeItemView.do'/>')">이달의 정보 ISSUE</a></li>
								<li class="no_child"><a href="#a" onclick="iKEP.iFrameMenuOnclick('<c:url value='/collpack/kms/notice/noticeConfigView.do'/>')">이달의 정보 ISSUE 설정</a></li>
							</ul>						
						</li>
						</c:if>
					</ul>
				</div>