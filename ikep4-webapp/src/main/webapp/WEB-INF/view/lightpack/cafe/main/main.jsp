<%@ page language="java" errorPage="/base/common/error.jsp"	pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"  value="message.lightpack.cafe.main" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript"> 
(function($) {
	
	$(document).ready(function() {
		$("#divTab5").tabs(); 
		
		getSubListForCafe();
		getSubListForMyCafe();
		getSubListForNewCafe();
		
		iKEP.setLeftMenu();	
		
		$("#searchword").keypress(function(event){
            if(event.which == 13) {
            	$("#searchbtn").click();
            }
        });
		
		$("#searchbtn").click(function() {
			getSubListForCafeBySearchWord($("#searchword").val());
		});
		
		
		$jq("#createCafeBtn").click(function() { 
			var url = "<c:url value='/lightpack/cafe/cafe/createCafeView.do'/>";
			
				iKEP.showDialog({
					title : "<ikep4j:message pre="${prefix}" key="cafeCreate" />",
					width:800,
					height:450,
					url: url,
					modal:true,
					callback : function(cafeId) {
						//location.href = "<c:url value='/lightpack/cafe/main/cafe.do'/>?cafeId=" + cafeId; //개설된 까페로 포워딩
						location.reload();
					}
				});
			});
	});
	
	getSubListForCafeBySortColumn = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		$jq("input[name=sortType]").val(sortType == 'ASC' ? 'DESC' : 'ASC');	 
		getSubListForCafe();
	}
	
	getSubListForCafeBySearchWord = function(searchWord) {
		$jq("input[name=searchWord]").val(searchWord);
		getSubListForCafe();
	}
	
	getSubListForCafeByCategoryId = function(categoryId) {
		$jq("input[name=categoryId]").val(categoryId);
		getSubListForCafe();
	}
	
	getSubListForCafe = function() {

		$jq.ajax({     
			url : '<c:url value="/lightpack/cafe/main/getSubListForCafe.do" />',     
			data : $jq("#searchForm").serialize(), 
			type : "post",     
			loadingElement : {container:"#findCafeView"},
			success : function(result) {  
				$jq("#findCafeView").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	getSubListForMyCafe = function() {
		
		$jq.ajax({     
			url : '<c:url value="/lightpack/cafe/main/getSubListForMyCafe.do" />',     
			data :  "",     
			type : "post",     
			loadingElement : {container:"#myCafeDiv"},
			success : function(result) {  
				$jq("#myCafeDiv").html(result);
			}
		});  
		
	};
	
	getMoreItem = function(cafeId) {
		
		var itemDiv = "#itemDiv_"+cafeId;
		var moreDiv = "#moreDiv_"+cafeId;
		var searchForm = "#searchForm_"+cafeId;
		var recordCount = $jq("#recordCount_"+cafeId).val();
		var currentCount = $jq("#currentCount_"+cafeId).val();
		
		$jq.ajax({     
			url : '<c:url value="/lightpack/cafe/main/getSubListForMyCafeItem.do" />',     
			data :  {cafeId:cafeId,recordCount:recordCount,currentCount:currentCount},     
			type : "post",     
			loadingElement : {button:moreDiv},
			success : function(result) {  
				$jq(itemDiv).append(result);
				setMoreDiv(cafeId);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	setMoreDiv = function(cafeId) {
		
		var itemDiv = "#itemDiv_"+cafeId;
		var moreDiv = "#moreDiv_"+cafeId;
		var searchForm = "#searchForm_"+cafeId;
		var recordCount = $jq("#recordCount_"+cafeId).val();
		var currentCount = $jq("#currentCount_"+cafeId).val();

		if(recordCount < 3) {
			$jq(moreDiv).hide();
		}
		else {
			$jq(moreDiv).show();
		}
		
	};
	
	getSubListForWaitCafe = function() {
		
		$jq.ajax({     
			url : '<c:url value="/lightpack/cafe/main/getSubListForWaitCafe.do" />',     
			data :  "",     
			type : "post",     
			loadingElement : {container:"#myCafeDiv"},
			success : function(result) {  
				$jq("#myCafeDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
	getSubListForNewCafe = function() {
		
		$jq.ajax({     
			url : '<c:url value="/lightpack/cafe/main/getSubListForNewCafe.do" />',     
			data :  "",     
			type : "post",     
			loadingElement : {container:"#newCafeDiv"},
			success : function(result) {  
				$jq("#newCafeDiv").html(result);
			}
		});  
		
	};
	
	leavelCafe = function(cafeId, memberLevel) {
		
		if(memberLevel == 1) {
			alert('<ikep4j:message pre="${prefix}" key="leaveCafeSysopt" />');
			return;
		}
		
		if(!confirm('<ikep4j:message pre="${prefix}" key="leaveCafe" />')) {
			return;
		}
		
		$jq.ajax({
			url : '<c:url value='/lightpack/cafe/member/deleteMemberAjax.do' />?cafeId='+cafeId,
			type : "post",
			loadingElement : {container:"#myCafeDiv"},
			success : function(result) {
				getSubListForMyCafe();
			}
		});	
		
	};	
	
	
	cancelCafe = function(cafeId) {
		
		if(!confirm('<ikep4j:message pre="${prefix}" key="cancelCafe" />')) {
			return;
		}
		
		$jq.ajax({
			url : '<c:url value='/lightpack/cafe/member/deleteMemberAjax.do' />?cafeId='+cafeId,
			type : "post",
			loadingElement : {container:"#myCafeDiv"},
			success : function(result) {
				getSubListForWaitCafe();
			}
		});	
		
	};	
	
	clickTab = function(opt) {
		
		if(opt == '1') {
			getSubListForMyCafe();
		}
		else {
			getSubListForWaitCafe();
		}
	}
	
	
})(jQuery);
</script>
		<!--blockMain Start-->
		<div id="blockMain">

			<!--leftMenu Start-->
			<div id="leftMenu" class="leftMenu_QnA">
				<h1 class="none"><ikep4j:message pre="${prefix}" key="cafe" /></h1>	
				<h2><a href="<c:url value='/lightpack/cafe/main/main.do'/>"><ikep4j:message pre="${prefix}" key="cafe" /></a></h2>
				
				<div class="left_fixed">
					<ul>		
						<c:forEach var="defaultCategoryList" items="${defaultCategoryList}" varStatus="idx">
							<c:if test="${defaultCategoryList.childCount eq 0}">
								<li class="<c:if test='${idx.count eq 1}'>liFirst</c:if> no_child"><a href="#a" onclick="javascript:getSubListForCafeByCategoryId('${defaultCategoryList.categoryId}')">
								${defaultCategoryList.categoryName} (${defaultCategoryList.cafeCount})</a></li>
							</c:if>
							<c:if test="${defaultCategoryList.childCount > 0}">
								<li class="licurrent opened"><a href="#a" onclick="javascript:getSubListForCafeByCategoryId('${defaultCategoryList.categoryId}')">
								${defaultCategoryList.categoryName} (${defaultCategoryList.cafeCount})</a>
									<ul class="qnalist_sub">
										<c:forEach var="menuCategoryList" items="${menuCategoryList}" varStatus="sub">
											<c:if test="${menuCategoryList.parentId eq defaultCategoryList.categoryId }">
												<li class="qnalist">
												<a href="#a" onclick="javascript:getSubListForCafeByCategoryId('${menuCategoryList.categoryId}')" title="${menuCategoryList.categoryName} (${menuCategoryList.cafeCount})" style="text-overflow:ellipsis; overflow:hidden;">
												<nobr>${menuCategoryList.categoryName} (${menuCategoryList.cafeCount})</nobr></a>
												</li>
											</c:if>
										</c:forEach>
									</ul>
								</li>
							</c:if>
						</c:forEach>
					</ul>
				</div>
				
				<div class="blockButton_2 mt10"> 
					<a id="createCafeBtn" class="button_2 normal mb5" href="#a"><span><ikep4j:message pre="${prefix}" key="cafeCreate" /></span></a>		
					<c:if test="${isSystemAdmin}">
						<a class="button_2 normal" href="<c:url value='/lightpack/cafe/main/menu.do'/>?listType=categoryList"><span><ikep4j:message pre="${prefix}" key="cafeManage" /></span></a>
					</c:if>
				</div>
			</div>	
			<!--//leftMenu End-->
				
			<!--mainContents Start-->
			<div id="mainContents">
				<h1 class="none"></h1>

				<div style="padding-right:245px;">
			
			
					<div class="cafe_main">
						<h2><ikep4j:message pre="${prefix}" key="findCafe" /></h2>
						<!--conSearch Start-->
						<div class="conSearch">
							<input id="searchword" title="<ikep4j:message pre="${prefix}" key="search" />" style="width:239px" type="text" />
							<a id="searchbtn" class="sel_btn" href="#a"><span><ikep4j:message pre="${prefix}" key="search" /></span></a>
							<div class="clear"></div>
						</div>
						<!--//conSearch End-->
					</div>
					
					<div class="clear"></div>
					
					
					<!--Find Cafe Start-->
					<div id="findCafeView" style="min-height:500px">
					
					</div>
					<!--//Find Cafe End-->	
					
					
					<!--cafeRightBlock Start-->
					<div class="cafeRightBlock">
						
						<div id="divTab5" class="iKEP_tab_menu">

							<!--tab Start-->		
							<div class="iKEP_tab_menu">
								<ul>
									<li	style="width:115px;"><a href="#tabs-1" onclick="clickTab('1')"><strong><ikep4j:message pre="${prefix}" key="myCafe" /></strong></a></li>
									<li	style="width:115px;"><a href="#tabs-1" onclick="clickTab('2')"><strong><ikep4j:message pre="${prefix}" key="joinCafe" /></strong></a></li>
								</ul>			
							</div>
							<!--//tab End-->	
							
							
							<!--My Cafe Start-->
							<div class="cafeBox_tab" id="myCafeDiv" style="min-height:100px">
										
							</div>	
							<!--//My Cafe End-->	
									
							<!--New Cafe Start-->
							<div class="cafe_box" id="newCafeDiv" style="min-height:100px">
							
							</div>	
							<!--//New Cafe End-->	
									
							<div class="tab_con">         
								<div id="tabs-1">
								</div>
							</div>  
							
						</div>
						
					</div>
					<!--//cafeRightBlock End-->	
				</div>			
					
			</div>
			<!--//mainContents End-->
			<div class="clear"></div>
		</div>
		<!--//blockMain End-->
		