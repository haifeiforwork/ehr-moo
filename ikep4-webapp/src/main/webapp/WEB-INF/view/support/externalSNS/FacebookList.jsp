<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
	<c:set var="info" value='${requestScope.info}'/>
	<c:set var="sectionFlag" value="${requestScope.sectionFlag}"/>

<html>
	<head>
		<%@ include file="/base/common/meta.jsp"%>
		<title>FACEBOOK FUNCTION</title>
		<script type="text/javascript" src="<c:url value='/base/js/jquery/jquery-1.6.2.min.js'/>"></script>
		<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-ui-1.8.13.min.without.datepicker.js"/>"></script>
		<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.ui.datepicker.customize.pack.js"/>"></script>
		<script type="text/javascript" src="<c:url value='/base/js/common.pack.js'/>"></script>
		<script type="text/javascript">
		(function($) {
			$(document).ready(function() {
			 	$("input[type=button]").click(function() {
				 	var text = $("#message");
				 	var message = text.val();
				 	if(message!=null&&message !=""){
				 		alert(text.val());
					 	$.ajax({
		         			url : "<c:url value='/support/externalSNS/facebookCreatePost.do'/>",
		         			data :{message:text.val()},
							type : "GET",
							success : function(result) {
							}
						});
				 	}else{
				 		alert("내용을 입력해주세요.");
				 	}
				});
			});
		})(jQuery);
		</script>
	</head>
	<body>
		<input type="text" id="message" size="140"></input><input type="button" value="입력" />
	</body>
</html>