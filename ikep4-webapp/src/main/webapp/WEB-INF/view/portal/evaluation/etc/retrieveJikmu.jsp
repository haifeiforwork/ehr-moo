<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/treeview/jquery.treeview.js"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/treeview/jquery.treeview.css"/>" />

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
					'action' : 'JIKMU',
					'object_id' : linkCode
					}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);
					} else {
						$.drawContents(result);
					}

					$.stopProgress();
				}
				, error : function(xhr, exMessage) {
					$.stopProgress();
					AjaxError.showAlert(xhr, exMessage);
				}
			});
		}

		$.drawContents = function(data) {
			var aqualContents = '',
				kqualContents = '',
				legcertContents = '',
				reccertContents = '',
				movjobContents = '';

			$('#outlineContent').empty();
			$('#dutyContent').empty();
			$('#aqualContent').empty();
			$('#kqualContent').empty();
			$('#kpiContent').empty();
			$('#legcertContent').empty();
			$('#reccertContent').empty();
			$('#movjobContent').empty();

			if(data.ET_OUTLINE[0] != null) $('#outlineContent').html(data.ET_OUTLINE[0].DESCR.replaceAll('\r\n', '<br/>'));

			if(data.ET_DUTY != null) {
				$.each(data.ET_DUTY, function(index, item) { data.ET_DUTY[index].DESCR = item.DESCR.replaceAll('\r\n', '<br/>'); });
				$('#dutyRowTemplate').tmpl(data.ET_DUTY).appendTo($('#dutyContent'));
				$('#dutyContent > tr').each(function() {
					$(this).find('td').eq(1).html($(this).find('td').eq(1).text());
				});
			}

			if(data.ET_AQUAL != null) {
				$.each(data.ET_AQUAL, function(index, item) { aqualContents += item.STEXT + '<br/>'; });
				$('#aqualContent').html(aqualContents);
			}
			if(data.ET_KQUAL != null) {
				$.each(data.ET_KQUAL, function(index, item) {
					if(index > 0) kqualContents += '<br/>';
					kqualContents += item.DESCR.replaceAll('\r\n', '<br/>');
				});
				$('#kqualContent').html(kqualContents);
			}

			if(data.ET_KPI != null) $('#kpiRowTemplate').tmpl(data.ET_KPI).appendTo($('#kpiContent'));

			if(data.ET_LEGCERT != null) {
				$.each(data.ET_LEGCERT, function(index, item) { legcertContents += item.STEXT + '<br/>'; });
				$('#legcertContent').html(legcertContents);
			}
			if(data.ET_RECCERT != null) {
				$.each(data.ET_RECCERT, function(index, item) { reccertContents += item.STEXT + '<br/>'; });
				$('#reccertContent').html(reccertContents);
			}

			if(data.ET_MOVJOB != null) {
				$.each(data.ET_MOVJOB, function(index, item) { movjobContents += item.STEXT + '<br/>'; });
				$('#movjobContent').html(movjobContents);
			}
		}

		$.fetchTreeData = function() {
			$.callProgress();
			$.ajax({
				url : "<c:url value='/portal/evaluation/etc/getTree.do'/>"
				, type : "post"
				, data : {'gubun' : 'DUTY'}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
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

<script id="dutyRowTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>\${STEXT}</td>
		<td>\${DESCR}</td>
	</tr>
</script>

<script id="kpiRowTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td>\${KPINM}</td>
		<td>\${MNOTE}</td>
	</tr>
</script>

<div id="wrap">

	<h1 class="title">직무 프로파일</h1>

	<!-- 리스트 -->
	<div class="float_wrap">
		<div class="float_left tviewer fixed" style="width: 15%;">
			<div id="sideTreeControl"></div>
			<ul class="treeview" id="treeSpace"></ul>
		</div>

		<div class="float_right ttable fixed" style="width: 85%;">
			<h2 class="title" id="selected_item">-</h2>

			<h3 class="title">1. 개요</h3>
			<table class="contents_table vert_top">
				<colgroup>
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<td id="outlineContent"></td>
					</tr>
				</tbody>
			</table>

			<h3 class="title">2. 책무</h3>
			<table class="contents_table vert_top">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: 70%;" />
				</colgroup>
				<thead>
					<tr>
						<th>책무명</th>
						<th>관련과업</th>
					</tr>
				</thead>
				<tbody id="dutyContent">
				</tbody>
			</table>

			<h3 class="title">3. 역량</h3>
			<table class="contents_table vert_top">
				<colgroup>
					<col style="width: 50%;" />
					<col style="width: 50%;" />
				</colgroup>
				<thead>
					<tr>
						<th>행동역량</th>
						<th>지식역량</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td id="aqualContent"></td>
						<td id="kqualContent"></td>
					</tr>
				</tbody>
			</table>

			<h3 class="title">4. 주요 성과 지표</h3>
			<table class="contents_table vert_top">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: 70%;" />
				</colgroup>
				<thead>
					<tr>
						<th>성과지표</th>
						<th>측정산식</th>
					</tr>
				</thead>
				<tbody id="kpiContent">
				</tbody>
			</table>

			<h3 class="title">5. 자격증</h3>
			<table class="contents_table">
				<colgroup>
					<col style="width: 30%;" />
					<col style="width: 70%;" />
				</colgroup>
				<tbody>
					<tr>
						<th>법적필수</th>
						<td id="legcertContent"></td>
					</tr>
					<tr>
						<th>직무추천</th>
						<td id="reccertContent"></td>
					</tr>
				</tbody>
			</table>

			<h3 class="title">6. 이동가능직무</h3>
			<table class="contents_table vert_top">
				<colgroup>
					<col style="" />
				</colgroup>
				<tbody>
					<tr>
						<td id="movjobContent"></td>
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