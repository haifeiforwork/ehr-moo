<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="java.net.*"%>
<%@ page import="com.webfac.service.*"%>
<%@ page import="org.apache.xpath.XPathAPI"%>
<%@ page import="org.w3c.dom.Document"%>
<%@ page import="org.w3c.dom.Element"%>
<%@ page import="org.w3c.dom.NamedNodeMap"%>
<%@ page import="org.w3c.dom.Node"%>
<%@ page import="org.w3c.dom.NodeList"%>
<html>
	<%@ include file="/base/common/taglibs.jsp"%>
	<%@ include file="/base/common/meta.jsp" %>
<script type="text/javascript">

//<!--
	function handler() {
		var resultValue		= document.all["result"].value;
		if(resultValue == 'TRUE') {
			parent.completeClose();
		} else {
			parent.showErrorMsg(resultValue);
		}
	}
</script>
<input type="text" name="result" value="${result}"/>
<script>handler();</script>
