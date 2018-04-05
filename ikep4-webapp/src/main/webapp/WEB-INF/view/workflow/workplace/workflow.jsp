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
<%
Calendar c = Calendar.getInstance();
long ret = c.getTimeInMillis();

  int d = c.get(Calendar.DAY_OF_MONTH);
  int m = c.get(Calendar.MONTH);
  int y = c.get(Calendar.YEAR);
  int h = c.get(Calendar.HOUR);
  int mm = c.get(Calendar.MINUTE);
  int ss = c.get(Calendar.SECOND);
  

  String date = String.format("%04d%02d%02d", c.get(Calendar.YEAR), c.get(Calendar.MONTH)+1, c.get(Calendar.DAY_OF_MONTH));
//  System.out.println(" s : " +  s + " to ret : " + ret);
//  System.out.println(" to ret1 : " + y + "-" + m + "-" + d + " " + h + ":" + mm + ":" + ss);
//  System.out.println(" date : " +  date );
%>
<html>
	<%@ include file="/base/common/taglibs.jsp"%>
	<%@ include file="/base/common/meta.jsp" %>
<script type="text/javascript">

//<!--
	
	function deployProcess() {
		var xpdlStream		= document.getElementById("textXpdl").value;
		document.getElementById("frm_Command").command.value	= "deploy";
		document.getElementById("frm_Command").xpdlStream.value	= xpdlStream;
		document.getElementById("frm_Command").submit();
	}	
	
	function deleteProcess() {
		var processId		= document.getElementById("textProcessId").value;
		document.getElementById("frm_Command").command.value	= "deleteProcess";
		document.getElementById("frm_Command").processId.value	= processId;
		document.getElementById("frm_Command").submit();
	}
	
	function changeProcess(procId) {
		document.getElementById("textProcessId").value	= procId;
	}
//-->

</script>

<body>
<table border="1" width="100%" height="500">
  
	<tr height="25" align="left">
    
		<th>&nbsp;&nbsp;XPDL 디플로이</th>
  
	</tr>
  
	<tr>
    
		<td>
			<textarea name="textXpdl" width="100%" style="width:100%;height:100%"></textarea>
		</td>
  
	</tr>
	<tr>
		<td align="right" height="20">
			<input type="button" value="Deploy" onclick="deployProcess()">
		</td>
	</tr>
  <tr>
  	<td height="20">
  		<table border="1" width="100%">
  			<tr>
	  			<td align="left">
					<input type="button" value="DeleteProcess" onclick="deleteProcess()">
			  		<input type="text" name="textProcessId" value="iKEP_SAMPLE_V01"/>
			  		<input type="button" name="btnChangeProc" value="전자결재" onclick="changeProcess('APPROVAL_PROCESS')"/>
	  			</td>
  			</tr>
  		</table>
  	</td>
  </tr>  

</table>

<form id="frm_Command" method="post" action="<c:url value='/workflow/workflow.do'/>" target="ifrm_Deploy">
	<input type="hidden" name="command" value=""/>
	<input type="hidden" name="xpdlStream" value=""/>
	<input type="hidden" name="processId" value=""/>
</form>
<iframe name="ifrm_Deploy" style="display:none"></iframe>

</body>
</html>
