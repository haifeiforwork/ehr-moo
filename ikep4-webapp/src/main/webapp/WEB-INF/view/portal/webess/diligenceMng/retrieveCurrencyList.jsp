<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<script type="text/javascript">
(function($) {
	$(document).ready(function(){
		$("#searchButton").click(function(){
			$("#currencyForm").attr("action", "<c:url value='/portal/moorimess/diligenceMng/retrieveCurrencyList.do'/>");
			$("#currencyForm").attr("target", "_self");
			$("#currencyForm").submit();
		});
		
		$("a[name=currencyKey]").click(function(){
			$(opener.document).find("input[name=currency]").val($.trim($(this).html()));
			opener.$jq.getExchangeRate();
			self.close();
		});
		
		$("input[name=searchText]").val("<c:out value="${params.searchText}"/>");
	});
})(jQuery);
//-->
</script>

<form name="currencyForm" id="currencyForm" method="post">
<div id="wrap" style="padding-top:10px; padding-left:7px;">
	<div id="contents">
		<h1><img src="<c:url value="/base/images/ess/bullet_01.png"/>" alt=""/>통화키 검색</h1>
		
		<div class="search_box">
			<table border="0">
				<tbody>
					<tr>
						<td><span class="f_333">통화키명</span></td>
						<td>
							<input type="text" name="searchText" id="searchText" value="" class="input"/>
						</td>
						<td>
							<a href="#" class="button_img06" id="searchButton"><span>조회</span></a>
						</td>
					</tr>
				</tbody>
			</table>
		</div>
		
		<div class="list01">
			<table width="100%" border="0" cellpadding="0" cellspacing="0">
				<caption></caption>
				<colgroup>
					<col width="25%"/>
					<col width="*"/>
				</colgroup>
				<thead>
					<tr>
						<th>통화코드</th>
						<th>통화명</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${resultList }" var="result">
						<tr>
							<td>
								<a href="#" name="currencyKey" class="board_2"><c:out value="${result.WAERS }"/></a>
							</td>
							<td><c:out value="${result.KTEXT }"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>