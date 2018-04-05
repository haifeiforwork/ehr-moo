<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/jquery/treeview/jquery.treeview.js"/>"></script>

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/ess/treeview/jquery.treeview.css"/>" />

<script type="text/javascript">

(function($){
	
	$(document).ready(function(){
		
		window.print();
		
	});
	
	
	
})(jQuery);;
</script>
<style>

.rigthFrame{
	display:inline-block;
	position:absolute;
	top:45px;
	overflow:auto;
}

.table_box{
	width:880px;
}

textarea{
	padding:5px;
}
</style>

<form id="capaCatalogForm" name="capaCatalogForm" method="post" action="<c:url value='/portal/moorimess/manPowerMng/capaCatalogView.do'/>" >
<div id="wrap">
	
	<div class="rigthFrame">
		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">역량정보</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list02">
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<caption></caption>
					<colgroup>
						<col width="20%"/>
						<col width="30%"/>
						<col width="20%"/>
						<col width="30%"/>
					</colgroup>
					<tbody>
						<tr>
							<th class="list02_td_bg">역량그룹</th>
							<td class="list02_td f_left"><span class="EX_QGROUPTXT">${result.EX_QGROUPTXT}</span></td>
							<th class="list02_td_bg">역량명</th>
							<td class="list02_td f_left"><span class="EX_STEXT">${result.EX_STEXT}</span></td>
						</tr>
						<tr>
							<th class="list02_td_bg">역량내용</th>
							<td class="list02_td f_left" colspan="3" id="EX_DESCR">
								${EX_DESCR}
							</td>
						</tr>
				</table>
			</span>
		</div>

		<div class="table_box">
			<p class="f_title" style="padding-bottom:10px;">역량사전(Category)</p>

			<span style="display:inline-block; .display:inline; *zoom:1"  class="list01">
				<table width="100%" border="0" cellpadding="0" cellspacing="0" id="categoryTab">
					<caption></caption>
					<colgroup>
						<col width="20%"/>
						<col width="35%"/>
						<col width="*"/>
					</colgroup>
					<thead>
						<tr>
							<th>LEVEL</th>
							<th>Key Word</th>
							<th>행동지표</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="list" items="${result.ET_LIST}">
						<tr>
							<td>${list.RATING_TEXT}</td>
							<td style="text-align: left;">${list.KEY_WORD}</td>
							<td style="text-align: left;">${list.TLINE}</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</span>
		</div>
	</div>
</div>
</form>
<form id="ajaxForm" name="ajaxForm" method="post">
</form>
<form id="printPopForm" name="printPopForm" method="post">
</form>
<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>