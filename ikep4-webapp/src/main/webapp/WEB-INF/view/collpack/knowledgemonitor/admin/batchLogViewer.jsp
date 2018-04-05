<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<c:set var="messagePrefix">ui.collpack.knowledgemonitor.common.message</c:set>
<c:set var="confirmPrefix">ui.collpack.knowledgemonitor.common.confirm</c:set>
<c:set var="buttonPrefix">ui.collpack.knowledgemonitor.common.button</c:set>
<c:set var="menuPrefix">ui.collpack.knowledgemonitor.menu</c:set>
<c:set var="batchLogViewerPrefix">ui.collpack.knowledgemonitor.administrator.batchLogViewer</c:set>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre="${menuPrefix}" key="administrator.batchLogViewer"/></h2>
</div>
<!--//pageTitle End-->

<!--blockSearch Start-->
<div class="blockSearch">
	<div class="corner_RoundBox03">
		<form action="">
		<table summary="">
			<caption></caption>
			<tbody>
				<tr>
					<th scope="row" width="5%"><ikep4j:message pre="${batchLogViewerPrefix}" key="searchLog"/></th>
					<td width="95%">
						<select title="<ikep4j:message pre="${messagePrefix}" key="year"/>" name="year">
							<option value="">2011</option>
							<option value="">2012</option>
						</select>								
						<select title="<ikep4j:message pre="${messagePrefix}" key="month"/>" name="month">
							<option value="">01</option>
							<option value="">02</option>
							<option value="">03</option>
							<option value="" selected="selected">04</option>
							<option value="">05</option>
							<option value="">06</option>
							<option value="">07</option>
							<option value="">08</option>
							<option value="">09</option>
							<option value="">10</option>
							<option value="">11</option>
							<option value="">12</option>
						</select>
					</td>
				</tr>
			</tbody>
		</table>
		<div class="searchBtn"><a href="#a"><span>Search</span></a></div>
		</form>
		<div class="l_t_corner"></div>
		<div class="r_t_corner"></div>
		<div class="l_b_corner"></div>
		<div class="r_b_corner"></div>				
	</div>
</div>	
<!--//blockSearch End-->

<!--blockListTable Start-->
<div class="MyContentsTable">
	<table summary="My Document">
		<caption></caption>
		<tbody>
			<tr class="msg_unread">
				<td scope="col" width="10%" class="tdFirst textRight_p20">성공</td>
				<td scope="col" width="70%" class="textLeft_p20">22011/01/31 12:22 pm ~ 2011/01/31 12:22 pm </td>
				<td scope="col" width="20%" class="textLeft_p20 tdLast">모듈명 23892 items</td>
			</tr>
			<tr class="msg_unread">
				<td scope="col" width="10%" class="tdFirst textRight_p20">성공</td>
				<td scope="col" width="70%" class="textLeft_p20">22011/01/31 12:22 pm ~ 2011/01/31 12:22 pm </td>
				<td scope="col" width="20%" class="textLeft_p20 tdLast">모듈명 23892 items</td>
			</tr>
			<tr class="msg_unread">
				<td scope="col" width="10%" class="tdFirst textRight_p20">실패</td>
				<td scope="col" width="70%" class="textLeft_p20">22011/01/31 12:22 pm ~ 2011/01/31 12:22 pm </td>
				<td scope="col" width="20%" class="textLeft_p20 tdLast">모듈명 23892 items</td>
			</tr>
			<tr class="msg_unread">
				<td scope="col" width="10%" class="tdFirst textRight_p20">실패</td>
				<td scope="col" width="70%" class="textLeft_p20">22011/01/31 12:22 pm ~ 2011/01/31 12:22 pm </td>
				<td scope="col" width="20%" class="textLeft_p20 tdLast">모듈명 23892 items</td>
			</tr>
			<tr class="msg_unread">
				<td scope="col" colspan="3" class="tdFirst tdLast">none</td>
			</tr>																																																																																														
		</tbody>
	</table>
</div>
<!--//blockListTable End-->



<script type="text/javascript">
$jq(document).ready(function() {
});
</script>

<script type="text/javascript">
</script>
