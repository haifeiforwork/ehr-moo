<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 

<c:set var="common" value="ui.support.memo.common" />
<c:set var="preSearch"  value="ui.support.memo.searchCondition" />
<c:set var="buttons" value="ui.support.memo.buttons" />
<c:set var="preMsg"  value="ui.support.memo.message" /> 

 <%-- 메시지 관련 Prefix 선언 End --%>
<style>
a.button_s, a.button_s span { position:relative; margin-right:3px; display:inline-block; border:0; white-space:nowrap; background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s.gif'/>") no-repeat; overflow:visible; }
a.button_s { text-decoration:none; padding:0; background-position:left top; }
a.button_s span { height:20px; font-size:0.9em; *font-family:'돋움', 'Dotum', Tahoma; font-weight:normal; left:7px; color:#111; padding:0px 7px 0 0; *padding:0 7px 0 0; line-height:20px; background-position:right top; cursor:pointer; }
a.button_s.selected { background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s_selected.gif'/>") no-repeat left top; }
a.button_s.selected span { color:#c50050; background:url("<c:url value='/base/images/theme/theme02/basic/btn_basic_s_selected.gif'/>") no-repeat right top; }
</style>

<script type="text/javascript" language="javascript">

(function($) {
	

	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
    	$jq("#btnClose").click(function() {
    		window.close();
		});
    	$jq("#btnCloseTop").click(function() {
    		window.close();
		});
    	$jq("#listButton").click(function() {
    		location.href="memoList.do?searchConditionString=${searchConditionString}";
		});
    	$jq("#updateButton").click(function() {
    		location.href="memoUpdateView.do?itemId=${memo.itemId}&searchConditionString=${searchConditionString}";
		});
	});

})(jQuery);  


	
</script>

<body style="overflow: hidden;">

	<div class="memo_header">
		<div><a href="#a" id="btnCloseTop"><img src="<c:url value='/base/images/common/btn_memo_close.png'/>" alt="" /></a></div>
	</div>

	<div  class="ui-layout-center"   style="width:100%;height:495px;overflow-x: hidden;overflow-y:scroll;background:#fff;">
		<div class="memo_view">
				<div class="memo_search">
					<table class="">
						<tbody>
						<tr><td height="20"></td><td>&nbsp;</td></tr>
							<tr>
								<td width="14%"></td>
								<td width="86%" style="padding-right:5px;text-align:right">${fn:substring(memo.registDate,0,16)}</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="memo_table">
					<table class="">
						<tbody>
							<tr>
								<td width="14%"></td>
								<td width="86%" style="padding-left:5px;">${memo.contents}</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			<div class="memo_bottom">
				<div class="memo_btnWrap">
					<a class="button_s"  id="updateButton"  href="#a"><span><ikep4j:message pre='${buttons}' key='update'/></span></a>
					<a class="button_s" id="listButton"  href="#a"><span><ikep4j:message pre='${buttons}' key='list'/></span></a>
					<a class="button_s" id="btnClose" href="#a"><span><ikep4j:message pre='${buttons}' key='close'/></span></a>
				</div>
			</div>	
		</div>	
	</div>

</body>


