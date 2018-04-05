<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
	<c:set var="info" value='${requestScope.info}'/>
	<c:set var="sectionFlag" value="${requestScope.sectionFlag}"/>

<html>
	<head>
		<%@ include file="/base/common/meta.jsp"%>
	</head>
		<script type="text/javascript">
		function twitter_up(){
		    var objForm = document.twitterForm;
		    var url = "<c:url value='/support/externalSNS/TwitterUp.do'/>";
		    objForm.target = "_self";
		    objForm.method = "POST";
		    objForm.action = url;
		    objForm.submit();
		}
		
		function twitter_home(){
			 var objForm = document.twitterForm;
			 var url = "<c:url value='/support/externalSNS/TwitterList.do'/>";
		     objForm.target = "_self";
		     objForm.method = "GET";
		     objForm.action = url;
		     objForm.submit();
		}
		
		</script>
  <body>
	<form name="twitterForm">
		<input type = 'text' name="content" size='140' maxlength='140'></input>
		<input type = 'button' value = '등록' onclick = 'javascript:twitter_up();'></input>
		<div style="height:2px"></div>
		카테고리 : 
		<select name="sectionFlag" style="width:140px" onChange="javascript:twitter_home();">
			<option value="homeTimeLine" <c:if test="${sectionFlag=='homeTimeLine'}">selected</c:if>>TIMELINE</option>
			<option value="mentions" <c:if test="${sectionFlag=='mentions'}">selected</c:if>>MENTINONS</option>
			<option value="retweet" <c:if test="${sectionFlag=='retweet'}">selected</c:if>>RETWEET</option>
		</select>
	</form>
	<c:if test="${!empty info}">
		<table border="1" width="50%">
		<c:forEach var = "content" items="${info}" varStatus="status">
			<tr>
				<td rowspan="3">
					<img src="<c:out value='${content.profileImage}'/>"/>
				</td>
				<td width="30%">
					<strong>
						<c:out value="${content.screenName}" />
					</strong>
				</td>
				<td width="*">
					<c:out value="${content.name}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:out value="${content.text}" escapeXml="true"/>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<c:out value="${content.createAt}" />
				</td>
			</tr>
		</c:forEach>
		</table>
	</c:if>
	<c:if test="${empty info}">
		<table border="1" width="100%">
			<tr>
				<td>
					등록된 트윗이 없습니다.
				</td>
			</tr>
		</table>
	</c:if>
</body>

</html>