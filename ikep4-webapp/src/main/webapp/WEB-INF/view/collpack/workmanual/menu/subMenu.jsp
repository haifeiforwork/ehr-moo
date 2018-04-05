<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 

<c:set var="menuPrefix">ui.collpack.workmanual.subMenu</c:set>

<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.jstree.pack.js"/>"></script>
<script type="text/javascript">
//<![CDATA[
(function($) {
	$jq(document).ready(function(){
		// left menu setting
		iKEP.setLeftMenu();
		iKEP.iFrameMenuOnclick("<c:url value='/collpack/workmanual/listWorkManualView.do'/>");
	
		//트리구성
		$("#categoryTreeMenu").jstree({
			core : {animation : 200},
		    plugins : ["themes", "ui", "json_data"],  
		    ui : {select_limit:1},	
			json_data : {
				data : ${categoryTreeJson},
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
			}
		})
		;
		 
		//트리 노드 클릭
	    $("#categoryTreeMenu").delegate("a", "click", function () {
	    	iKEP.iFrameMenuOnclick("<c:url value='/collpack/workmanual/listCategoryManualView.do?categoryId='/>" + $(this).parent().attr("id"));
	    }); 

		// 개인 업무함 클릭
	    $jq("#privateMenu").click(function() {
	    	iKEP.iFrameMenuOnclick("<c:url value='/collpack/workmanual/listMyManualView.do'/>");
	    }); 
		// 결재함 클릭
	    $jq("#approvalMenu").click(function() {
	    	iKEP.iFrameMenuOnclick("<c:url value='/collpack/workmanual/listApprovalView.do'/>");
	    }); 
		// 카테고리 관리 클릭
	    $jq("#categoryMenu").click(function() {
	    	iKEP.iFrameMenuOnclick("<c:url value='/collpack/workmanual/listCategoryView.do'/>");
	    });
	});

	menuTreeCreateNode = function(pid, data) {
		var obj = $jq("#categoryTreeMenu li[id=" + pid + "]");
		$jq("#categoryTreeMenu").jstree("create_node", obj, "last", data, function() {});
	};

	menuTreeRenameNode = function(id, name) {
		var obj = $jq("#categoryTreeMenu li[id=" + id + "]");
		$jq("#categoryTreeMenu").jstree("rename_node", obj, name);
	};

	menuTreeDeleteNode = function(id) {
		var obj = $jq("#categoryTreeMenu li[id=" + id + "]");
		
		$jq("#categoryTreeMenu").jstree("delete_node", obj);
	};
})(jQuery);
//]]>
</script>

			<!--leftMenu Start-->
				<h1 class="none"></h1>	
				<h2><a href="<c:url value='/collpack/workmanual/listWorkManualMainView.do'/>"><ikep4j:message pre="${menuPrefix}" key="subMenuTitle"/></a></h2>			
				<div class="left_fixed">
					<ul>
						<li class="liFirst opened"><a href="#a">Category</a>
							<div class="boxList_child">
								<div class="leftTree">
									<div><img src="<c:url value="/base/images/common/img_title_cate.gif"/>" alt="category" /></div>
									<div id="categoryTreeMenu"></div>
								</div>
							</div>
						</li>	
						<c:if test="${writeUserYn == 'Y'}">
							<li class="no_child"><a href="#a" id="privateMenu"><ikep4j:message pre="${menuPrefix}" key="subMenu2"/></a></li>
						</c:if>
						<li class="no_child"><a href="#a" id="approvalMenu"><ikep4j:message pre="${menuPrefix}" key="subMenu3"/></a></li>
						<c:if test="${adminYn == 'Y'}">
							<li class="opened"><a href="#a"><ikep4j:message pre="${menuPrefix}" key="subMenu4"/></a>
								<ul>
									<li class="no_child liLast"><a href="#a" id="categoryMenu"><ikep4j:message pre="${menuPrefix}" key="subMenu5"/></a></li>
								</ul>
							</li>
						</c:if>
					</ul>
				</div>
			<!--//leftMenu End-->