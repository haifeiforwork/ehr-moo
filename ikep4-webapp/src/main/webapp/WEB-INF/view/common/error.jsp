<%@ page isErrorPage="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>iKEP 4.00</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" />
<script type="text/javascript">
<!--    
if(window.parent.topScroll != null) {
	window.parent.topScroll(); 	
} 
//-->
</script>	
</head>

<body>

	<div class="nopagewrap">
		<div class="nopage">
			<img src="<c:url value="/base/images/icon/ic_nopage2.gif"/>" alt="" /><span>시스템에 오류가 발생하였습니다.</span>
			<div class="clear"></div>	
			<div class="nopage_list">
				<ul>
					<li>서비스 이용에 불편을 드려 죄송합니다.</li>
				</ul>
			</div>
		</div>
	</div>

</body>
</html>
