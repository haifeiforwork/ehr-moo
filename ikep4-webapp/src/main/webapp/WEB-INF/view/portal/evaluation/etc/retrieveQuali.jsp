<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/treeview/jquery.treeview.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/ess/treeview/jquery.treeview.css" />

<script type="text/javascript">

(function($){
	$(document).ready(function() {

		$.initView = function() {

			$.fetchTreeData();

			$.DisabledBackgroundStyleChange();

		}

		$.getDetail = function(linkCode) {
			$.callProgress();

			$.ajax({
				url : "<c:url value='/portal/evaluation/etc/detail.do'/>"
				, type : "post"
				, data : {
					'action' : 'QUALI',
					'object_id' : linkCode
					}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);
					} else {
						if(result.ET_DFTXT != null && result.ET_DFTXT.length > 0) {
							$('#defiContent').html(result.ET_DFTXT[0].DESCR.replace(/(?:\r\n|\r|\n)/g, '<br/>'));
						}
						if(result.ET_BHIDX != null && result.ET_BHIDX.length > 0) {
							$('#bhidContent').html(result.ET_BHIDX[0].DESCR.replace(/(?:\r\n|\r|\n)/g, '<br/>'));
						}
					}

					$.stopProgress();
				}
				, error : function(xhr, exMessage) {
					$.stopProgress();
					AjaxError.showAlert(xhr, exMessage);
				}
			});
		}

		$.fetchTreeData = function() {
			$.callProgress();
			$.ajax({
				url : "<c:url value='/portal/evaluation/etc/getTree.do'/>"
				, type : "post"
				, data : {'gubun' : 'QULITY'}
				, success : function(result) {
					if(result == null || result == "") {
						alert("트리 조회에 실패하였습니다.");
					} else if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);
					} else {
						_jsonQuliData = result.ET_TREE;
						$.buildTree({DATA : result.ET_TREE, TREE : '#treeSpace', CONTROL : '#sideTreeControl'});
					}

					$.stopProgress();
				}
				, error : function(xhr, exMessage) {
					$.stopProgress();
					AjaxError.showAlert(xhr, exMessage);
				}
			});
		}

		$.buildTree = function(panel) {
			var $nodeLvl0 = $(panel.TREE),
				expandClass = 'expandable',
				$expandDiv = $('<div class="hitarea expandable-hitarea"></div>'),
				$expandUl = $('<ul style="display:none;"></ul>'),
				$li = $(document.createElement('li')),
				$a = $(document.createElement('a')).attr('name', 'linked'),
				treeData = panel.DATA;

			$.each(treeData, function(index, tree) {
				var nodeName = tree.TEXT,
					nodeLevel = tree.TLEVEL,
					nodeLink = tree.LINK,
					nodeId = tree.CHILDID.replace(/\s/gi, ""),
					nodeParentId = tree.PARENTID.replace(/\s/gi, ""),
					nodeParentUlId = "ul_" + tree.PARENTID.replace(/\s/gi, ""),
					$cloneLi = $li.clone(),
					$cloneA = $a.clone();

				$cloneLi.attr('id', nodeId);
				$cloneA.attr('href', '#').attr('data-param', nodeLink).text(nodeName);
				$cloneLi.append($cloneA);

				if(nodeLevel == 1) {
					$nodeLvl0.append($cloneLi);
				} else {
					var $parentNode = $("#" + nodeParentId);
					var $parentUlNode = $('#' + nodeParentUlId);

					if($parentUlNode.length == 0) {
						var $cloneDiv = $expandDiv.clone();
						var $cloneUl = $expandUl.clone();
						$cloneUl.attr('id', nodeParentUlId);

						$parentNode.addClass(expandClass)
								   .prepend($cloneDiv)
								   .append($cloneUl.append($cloneLi));
					} else {
						$parentUlNode.append($cloneLi);
					}
				}
			});

			$nodeLvl0.treeview({
				collapsed: true,
				animated: "medium",
				control: panel.CONTROL,
				persist: "location"
			});

			$nodeLvl0.bindTreeLink();

			$nodeLvl0.find('.hitarea').eq(0).trigger('click')
						.next()
							.next()
								.find('a[name=linked]').eq(0).trigger('click');

		}

		$.fn.bindTreeLink = function() {
			$(this).find('a[name=linked]').click(function(e) {
				e.preventDefault();

				var $this = $(this);
				var linkCode, linkText, linkType;
				var params = $this.data("param").split("?");

				if(params.length == 2) {
					var arr = params[1].split("&");
					for(var i = 0 ; i < arr.length ; i++) {
						if(arr[i].split("=")[0] == "pa_otype") {
							linkType = arr[i].split("=")[1];
						} else if(arr[i].split("=")[0] == "pa_objid") {
							linkCode = arr[i].split("=")[1];
							linkText = $this.text();
						}
					}

					$('#selected_item').text(linkText);
					$.getDetail(linkCode);
				} else {
					return false;
				}
			});
		}

		$.initView();

	});
})(jQuery);

</script>

<div id="wrap">

	<h1 class="title">역량 카탈로그</h1>

	<!-- 리스트 -->
	<div class="float_wrap">
		<div class="float_left tviewer fixed" style="width: 15%;">
			<div id="sideTreeControl"></div>
			<ul class="treeview" id="treeSpace"></ul>
		</div>

		<div class="float_right ttable fixed" style="width: 85%;">
			<h2 class="title" id="selected_item">-</h2>
			<table class="contents_table">
				<colgroup>
					<col style="" />
				</colgroup>
				<thead>
					<tr>
						<th>정의</th>
					</tr>
				</thead>
				<tbody>
					<tr style="height:150px;">
						<td id="defiContent"></td>
					</tr>
				</tbody>
			</table>

			<table class="contents_table margintop_10">
				<colgroup>
					<col style="" />
				</colgroup>
				<thead>
					<tr>
						<th>행동지표</th>
					</tr>
				</thead>
				<tbody>
					<tr style="height:150px;">
						<td id="bhidContent"></td>
					</tr>
				</tbody>
			</table>

		</div>
	</div>

</div>

<form id="ajaxForm" name="ajaxForm" method="post"></form>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>