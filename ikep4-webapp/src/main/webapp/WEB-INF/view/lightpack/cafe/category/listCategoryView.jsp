<%--
=====================================================
	* 기능설명	:	카테고리 관리 목록
	* 작성자		:	김종철
=====================================================
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preDetail"  value="message.lightpack.cafe.category.detail" />
<c:set var="preButton"  value="message.lightpack.cafe.category.button" />
<c:set var="preTree"  value="message.lightpack.cafe.category.tree" />
<%-- 메시지 관련 Prefix 선언 End --%>

<%-- 세션정보 선언 Start --%> 
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />
<%-- 세션정보 선언 End --%>


<script type="text/javascript">
<!-- 
(function($){
	
	$jq(document).ready(function(){
		
		createTree();

		//노드 클릭
	    $("#adminCategoryTree").delegate("a", "click", function () {
	    	if($(this).parent().attr('categoryId') != null && $(this).parent().attr('categoryId') != "") {
	    		$("#categoryViewBlock").load("<c:url value='/lightpack/cafe/category/categoryPage.do?mode=R&upYn=N&categoryId='/>" + $(this).parent().attr('categoryId'), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
	    	}
	    }); 
		
	    iKEP.iFrameContentResize();
	    
	});
	
	createTree = function () {
		//트리구성
		$("#adminCategoryTree").bind("loaded.jstree", function (event, datet) {
			$(this).jstree("hide_icons");
		}).jstree({
		    plugins:["themes", "ui", "crrm", "json_data", "contextmenu"],  
		  
		    "json_data" : {
		    	"data" : {"data" : "<ikep4j:message pre='${preDetail}' key='category'/>", "attr" : {"dummy" : "true", "categoryId" : ""}, "state" : "open",  children : ${categoryTreeJson}}
		    }, 	
			"contextmenu" : {  
				items : function($item){
					var menuItem = {
						"createMenu" : {label : "<ikep4j:message pre='${preTree}' key='create'/>",  action : function(item) { 
							if($(item).attr("parentId") == 'null' || $(item).attr("parentId") == undefined){
								selectedItem = item;
								$("#categoryViewBlock").load("<c:url value='/lightpack/cafe/category/categoryPage.do?mode=C&upYn=N&categoryId='/>" + $(item).attr("categoryId"), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
							}else{
								alert("<ikep4j:message pre='${preTree}' key='message.noCreate'/>");
							}
						}},
						"updateMenu" : {label : "<ikep4j:message pre='${preTree}' key='update'/>",  action : function(item) {
							selectedItem = item;
							$("#categoryViewBlock").load("<c:url value='/lightpack/cafe/category/categoryPage.do?mode=U&upYn=N&categoryId='/>" + $(item).attr("categoryId"), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
						}},
						"deleteMenu" : {label : "<ikep4j:message pre='${preTree}' key='delete'/>",  action : function(item) {
							var tree = this;
							if(confirm("<ikep4j:message pre='${preTree}' key='comfirm.delete'/>")) {
								
								$.get("<c:url value='/lightpack/cafe/category/checkCategory.do?categoryId='/>" + $(item).attr("categoryId"))
								.success(function(data) {

									if(data == "ok") {
										$.get("<c:url value='/lightpack/cafe/category/deleteCategory.do?categoryId='/>" + $(item).attr("categoryId"))
										.success(function(data) {
											tree.remove(item); 
											$("#categoryViewBlock").html("");
										})
										.error(function(event, request, settings) { alert("error"); });
									}
									else {
										alert("<ikep4j:message pre='${preTree}' key='notEmpty'/>");
									}
								})
								.error(function(event, request, settings) { alert("error"); });
								
							} 
						}}, ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					var categoryId = $item.attr("categoryId");
					var dummy   = $item.attr("dummy");
					
					if(dummy) { 
						$.extend(menuItem, { 
							"updateMenu" : false,
							"deleteMenu" : false
						});
					}
					 
					return menuItem;
				}
			}
		});
	} 
	

	//공개 비공개 제어
	settingRead = function() {
		var readPermission = $jq("[name=readPermission]:checked").val();
		if(readPermission == 0) {
			$jq('#readTh').attr("rowspan", 2); 
			$jq('#readTr').show(); 
		} else {
			$jq('#readTh').attr("rowspan", 1); 
			$jq('#readTr').hide(); 
		}
	}
	
	//상위 권한 체크박스 클릭시
	reloading = function(upYn, mode, categoryId) {
		//$("#categoryViewBlock").load("<c:url value='/collpack/workmanual/categoryPage.do?upYn='/>" + upYn + "&mode=" + mode + "&categoryId=" + categoryId, $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
	}
	
	var selectedItem = null;	
	//저장 버튼 클릭시
	save = function() {
		
		$.post('<c:url value="/lightpack/cafe/category/saveCategory.do"/>', $("#categoryForm").serialize())
		.success(function(data) {
			$jq("#categoryViewBlock").load("<c:url value='/lightpack/cafe/category/categoryPage.do?mode=R&upYn=N&categoryId='/>" + data, $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
			parent.clickListCafeCategoryBtn();
		})
		.error(function(e) { alert(e.responseText); });
	}
	
	update = function(categoryId) {
		$jq("#categoryViewBlock").load("<c:url value='/lightpack/cafe/category/categoryPage.do?mode=U&upYn=N&categoryId='/>" + categoryId, $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });		
	}

	
	
	
})(jQuery);

//-->
</script>
<h1 class="none">Contents</h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre='${preDetail}' key='category' /></h2>
					<span><ikep4j:message pre='${preDetail}' key='help' /><span>
				</div>
				<!--//pageTitle End-->

				<!--blockLeft Start-->
				<div class="blockLeft" style="width:30%;height:500px;">	
					<div class="leftTree">
						<div id="adminCategoryTree"></div>
					</div>
				</div>
				<!--//blockLeft End-->
				
				<!--blockRight Start-->
				<div class="blockRight" style="width:68%;">
					<div id="categoryViewBlock">
					</div>
				</div>
				<!--//blockLight End-->	