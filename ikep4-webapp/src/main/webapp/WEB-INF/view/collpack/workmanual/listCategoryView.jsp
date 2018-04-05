<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix">ui.collpack.workmanual.listCategoryView</c:set>
<c:set var="messagePrefix">ui.collpack.workmanual.message</c:set>
<c:set var="buttonPrefix">ui.collpack.workmanual.button</c:set>
<%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		//트리구성
		$("#categoryTree").jstree({
			core : {animation : 200},
		    plugins : ["themes", "ui", "crrm", "dnd", "json_data", "contextmenu"],
		    ui : {select_limit:1},		
			json_data : {
				data : {data:{title:"<ikep4j:message pre='${prefix}' key='tree.title'/>", icon:"dept"},
					    attr:{id:"ROOT_CATEGORY"}, 
					    state:"open",
					    children:${categoryTreeJson}
				    },
				ajax : {
					url : "<c:url value='/collpack/workmanual/listChildCategory.do'/>",
					data : function($el) {
						return {
							"categoryParentId" : $el.attr("id")
						};
					},
					success : function(data) {
						return data.items;
					}
				}
			},	
			contextmenu : {  
				items : function($item){
					var menuItem = {
						"createMenu" : {label : "<ikep4j:message pre='${prefix}' key='rightMenu.create'/>",  action : function(item) { 
							selectedItem = item;
							$("#categoryViewBlock").load("<c:url value='/collpack/workmanual/categoryPage.do?mode=C&upYn=N&categoryId='/>" + $(item).attr("id"), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
						}},
						"updateMenu" : {label : "<ikep4j:message pre='${prefix}' key='rightMenu.update'/>",  action : function(item) {
							selectedItem = item;
							$("#categoryViewBlock").load("<c:url value='/collpack/workmanual/categoryPage.do?mode=U&upYn=N&categoryId='/>" + $(item).attr("id"), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
						}},
						"deleteMenu" : {label : "<ikep4j:message pre='${prefix}' key='rightMenu.delete'/>",  action : function(item) {
							var tree = this;
							if(confirm("<ikep4j:message pre='${messagePrefix}' key='comfirm.delete'/>")) {
								$.get("<c:url value='/collpack/workmanual/deleteCategory.do?categoryId='/>" + $(item).attr("id"))
								.success(function(data) {
									tree.remove(item);
									resetDatail();
								})
								.error(function(event, request, settings) { alert("error"); });
							} 
						}}, 
						ccp : false/* 에디터 바꾸기 Context Menu 사용 안함 */, create : false/* 생성 Context Menu 사용 안함 *//* 이름 바꾸기 Context Menu 사용 안함 */
					};
					var categoryId = $item.attr("id");
					if($item.attr("id") == "ROOT_CATEGORY") { 
						$.extend(menuItem, { 
							"updateMenu" : false,
							"deleteMenu" : false
						});
					}
					 
					return menuItem;
				}
			}
		})
		.bind("move_node.jstree", function(event, data) {
	
			var o = data.rslt.o;   // 이동한 카테고리
			var np = data.rslt.np; // 이동후 부모 카테고리
			var op = data.rslt.op; // 이동전 부모 카테고리
			var cp = data.rslt.cp; // 이동후 순번 (0부터 시작)
	
			// Root Category 로 이동할수 없다.
			if ("ROOT_CATEGORY" == $jq(np).attr("id")) {
				$jq.jstree.rollback(data.rlbk);
				alert("<ikep4j:message pre='${prefix}' key='tree.doNotMoveToRoot'/>");
				return;
			}
	
			var sourceText = $jq("#categoryTree").jstree("get_text", o);
			if (!confirm(sourceText + "<ikep4j:message pre='${prefix}' key='tree.confirm.move'/>")) {
				$jq.jstree.rollback(data.rlbk);
				return;
			}
	
			var sourceId = o.attr("id");
			var targetParentId = np.attr("id");
			var targetSortOrder = cp + 1;
	
			var params = "";
			params += "sourceId=" + sourceId;
			params += "&targetParentId=" + targetParentId;
			params += "&targetSortOrder=" + targetSortOrder;
			
			$jq.ajax({
				url : iKEP.getContextRoot() + "/collpack/workmanual/moveCategory.do",
				type : "post",
				data : params,
				success : function(data, textStatus, jqXHR) {
					o.attr("pid", targetParentId);
					resetDatail();
				},
				error : function(jqXHR, textStatus, errorThrown) {
			    	$jq.jstree.rollback(data.rlbk);
			    	alert("category move error");
				}
			});
		}); 

		//노드 클릭
	    $("#categoryTree").delegate("a", "click", function () {
	    	if($(this).parent().attr('id') != "ROOT_CATEGORY") {
	    		$("#categoryViewBlock").load("<c:url value='/collpack/workmanual/categoryPage.do?mode=R&upYn=N&categoryId='/>" + $(this).parent().attr('id'), $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
	    	}
	    }); 
		
		
	});

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
	};
	
	//상위 권한 체크박스 클릭시
	reloading = function(upYn, mode, categoryId) {
		$("#categoryViewBlock").load("<c:url value='/collpack/workmanual/categoryPage.do?upYn='/>" + upYn + "&mode=" + mode + "&categoryId=" + categoryId, $("#Form").serialize()).error(function(event, request, settings) { alert("error"); });
	};
	
	var selectedItem = null;
	getSelectedItem = function() {
		return selectedItem;
	};
	
	resetDatail = function() {
		$("#categoryViewBlock").attr("innerHTML", "");
	};
	
})(jQuery);
//]]>
</script>



				<h1 class="none"></h1>
				
				<!--pageTitle Start-->
				<div id="pageTitle">
					<h2><ikep4j:message pre="${prefix}" key="main.title"/></h2>
				</div>
				<!--//pageTitle End-->

				<!--blockLeft Start-->
				<div class="blockLeft" style="width:30%;">	
					<div class="leftTree treeBox">
						<div id="categoryTree"></div>
					</div>
				</div>
				<!--//blockLeft End-->
				
				<!--blockRight Start-->
				<div class="blockRight" style="width:68%;">
					<div id="categoryViewBlock">
					</div>
				</div>
				<!--//blockRight End-->
				