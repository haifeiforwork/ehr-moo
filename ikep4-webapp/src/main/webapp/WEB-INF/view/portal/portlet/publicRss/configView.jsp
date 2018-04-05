<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- Tab Lib Start --%> 
<%@ include file="/base/common/taglibs.jsp"%>
<%-- Tab Lib End --%> 

<c:set var="prefix" value="message.portal.portlet.rss.configView"/>

<script type="text/javascript">
<!--   
(function($) {
	var $frmPublicRss,
		$tblPublicRss,
		$btnPublicRssApply,
		$channelRow;
	
	function fnChangeTabCount() {
		$.get("<c:url value="/portal/portlet/publicRss/changeTabCount.do"/>")
			.success(function(channel) {
				if(channel.length > 0) {
					$.each(channel, function() {
						var $row = $channelRow.clone().appendTo($("tbody", $tblPublicRss));
						
						$("input[name=portletConfigId]", $row).val(this.portletConfigId);
						$("input[name=rssUrl]", $row).val(this.rssUrl)
							.change(function() {
								$(this).siblings("a").show();
							});
						
						$("a", $row).hide()
							.click(fnCheck);
					});
					
					$btnPublicRssApply.hide();
				} else {
					$("select[name=tabCount]", $frmPublicRss).change();
				}
			});
	}
	
	function fnCheck(event) {
		var $row = $(this).parents("tr:first");
		var $inputRssUrl = $jq("input[name=rssUrl]", $row);

		if(!$inputRssUrl.val()){
			alert("<ikep4j:message pre="${prefix}" key="blankMsg" />");
			$inputRssUrl.focus();
		} else {
			$.ajax({
				url : "<c:url value="/support/rss/channel/checkChannel.do" />",
				data : {channelUrl:$inputRssUrl.val()},
				type : "post",
				loadingElement : {container:$frmPublicRss},
				success : function(result) {
					if(result != 'urlError') {
						$("a", $row).hide();
						$btnPublicRssApply.show();
						alert("<ikep4j:message pre="${prefix}" key="alert.verificationMessage" />");
					} else {
						alert("<ikep4j:message pre="${prefix}" key="alert.failUrl" />");
					}
				}
			});
		}
	}
	
	$(document).ready(function() {
		var publicRssAddFormDiv = $("div[id=${portletConfigId}]");
		
		$frmPublicRss = $("#frmPublicRss");
		$tblPublicRss = $("#tblPublicRss");
		$channelRow = $("tr", $tblPublicRss).remove();
		$btnPublicRssApply = $("#btnPublicRssApply");
		
		fnChangeTabCount();
		
		$("select[name=tabCount]", $frmPublicRss).change(function() {
			var tabCount = $(this).val();
			var $rowItems = $("tr", $tblPublicRss);
			
			if(tabCount > $rowItems.size()) {	// 추가
				for(var i=$rowItems.size();i<tabCount;i++) {
					var $row = $channelRow.clone().appendTo($("tbody", $tblPublicRss));
					
					$("input[name=portletConfigId]", $row).val(i+1);
					$("input[name=rssUrl]", $row).change(function() {
						$(this).siblings("a").show();
					});
					$("a", $row).click(fnCheck);
				}
			} else {	// 삭제
				for(var i=$rowItems.size();i>tabCount;i--) {
					$rowItems.eq(i-1).remove();
				}
			}
			
			$btnPublicRssApply.show();
		});
		
		$("select[name=listCount]", $frmPublicRss).change(function() {
			$btnPublicRssApply.show();
		});
		
		// When registering RSS urls.
		$btnPublicRssApply.click(function() {
			var isSubmit = true;
			$("tr", $tblPublicRss).each(function() {
				if($("a:visible", this).is("*")) {
					alert("<ikep4j:message pre="${prefix}" key="isNotVerified" />");
					isSubmit = false;
					return false;
				}
			});
			
			if(isSubmit == true) {
				$jq.ajax({
					url : "<c:url value="/portal/portlet/publicRss/createPublicRss.do"/>",
					data : $frmPublicRss.serialize(),
					type : "post",
					loadingElement : {container:publicRssAddFormDiv},
					success : function(result) {
						$btnPublicRssApply.hide();
						alert("<ikep4j:message pre="${prefix}" key="alert.saveSuccess" />");
					},
					error : function() {alert('error');}
				});
			}
		});
	});
})(jQuery);
//-->
</script>

<h4 class="guidetitle_han"><ikep4j:message pre="${prefix}" key="title" /></h4>
<!-- tab  -->
<form id="frmPublicRss" action="" method="post" style="border: 1px solid white; padding: 0px;" onsubmit="return false">
<div  id="${portletConfigId}">
<div>
	<table summary="<ikep4j:message pre="${prefix}" key="summary" />">
		<caption></caption>
		<tbody>	
			<tr>
				<th scope="row">Tab</th>
				<td width="50px">
					<select name="tabCount" title="<ikep4j:message pre="${prefix}" key="select.count" />">
						<option value="1" <c:if test="${0 eq publicRss.tabCount || 1 eq publicRss.tabCount}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${2 eq publicRss.tabCount}">selected="selected"</c:if>>2</option>
						<option value="3" <c:if test="${3 eq publicRss.tabCount}">selected="selected"</c:if>>3</option>
						<option value="4" <c:if test="${4 eq publicRss.tabCount}">selected="selected"</c:if>>4</option>
						<option value="5" <c:if test="${5 eq publicRss.tabCount}">selected="selected"</c:if>>5</option>
					</select>
				</td>
				<th scope="row"><ikep4j:message pre="${prefix}" key="count" /></th>
				<td>
					<select name="listCount" title="<ikep4j:message pre="${prefix}" key="select.count" />">
						<option value="1" <c:if test="${1 eq publicRss.listCount || 1 eq publicRss.listCount}">selected="selected"</c:if>>1</option>
						<option value="2" <c:if test="${2 eq publicRss.listCount}">selected="selected"</c:if>>2</option>
						<option value="3" <c:if test="${3 eq publicRss.listCount}">selected="selected"</c:if>>3</option>
						<option value="4" <c:if test="${4 eq publicRss.listCount}">selected="selected"</c:if>>4</option>
						<option value="5" <c:if test="${5 eq publicRss.listCount}">selected="selected"</c:if>>5</option>
					</select>
				</td>
			</tr>
		</tbody>
	</table>
</div>

<div class="blockDetail">
	<div id="rssListView">
		<table id="tblPublicRss" summary="<ikep4j:message pre="${prefix}" key="summary" />">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre="${prefix}" key="url" /></th>
					<td>
						<input name="rssUrl" title="URL" class="inputbox w70" type="text" value=""/>
						<input name="portletConfigId" title="portletConfigId" class="inputbox w70" type="hidden" value=""/>
						<a class="button_s" href="#a" title="<ikep4j:message pre="${prefix}" key="verification" />"><span><ikep4j:message pre="${prefix}" key="verification" /></span></a>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div class="blockButton" style="padding-top: 5px;"> 
		<ul>
			<li><a id="btnPublicRssApply" class="button_s" href="#a"><span><ikep4j:message pre="${prefix}" key="save" /></span></a></li>
		</ul>
	</div>
</div></div>

</form>