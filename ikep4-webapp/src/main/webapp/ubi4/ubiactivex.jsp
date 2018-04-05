<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UbiReport4 AXViewer</title>
<script language='javascript'>
<!--
	/* URL 정보 */
	var app = 'myapp';
	var appUrl = self.location.protocol + '//' + self.location.host + (app==''?'':('/' + app));

	/* Viewer Param */
	var pRootUrl = appUrl;
	var pFileUrl = appUrl + '/ubi4/';
	var pServerUrl = appUrl + '/UbiServer';
	var pScale = '-9999';
	var pToolbar = 'true';
	var pProgress = 'true';
	var pExecType = 'TYPE4';

	/* Modify for your environment */
	var pDataSource = 'jdbc/tutorial';
	var pJrfDir = '/webapp/myapp/ubi4/work/';
	var pJrfName = 'ubi_sample.jrf';
	var pArg = 'user#홍길동#';

	var pCodebase = appUrl + '/ubi4/ubiviewer/UbiViewerX4.cab#version=4,0,1711,2903';

	/* Viewer Size Adjustment */
	var wGap = 8;
	var hGap = 8;

	function getArg() {
	
		return pArg;
	}

	function Ubi_Resize() {

		var w = ((self.innerWidth || (document.documentElement && document.documentElement.clientWidth) || document.body.clientWidth)) - wGap;
		var h = ((self.innerHeight || (document.documentElement && document.documentElement.clientHeight) || document.body.clientHeight)) - hGap;
		document.getElementById("UbiViewer").width = w + 'px';
		document.getElementById("UbiViewer").height = h + 'px';
	}

	function UbiViewer_Init() {

		var w = ((self.innerWidth || (document.documentElement && document.documentElement.clientWidth) || document.body.clientWidth)) - wGap;
		var h = ((self.innerHeight || (document.documentElement && document.documentElement.clientHeight) || document.body.clientHeight)) - hGap;

		document.write("<object id='UbiViewer' classid='CLSID:9BE79626-84B2-489D-BBFC-8492339AF9C2' codebase='" + pCodebase + "' width='" + w + "px' height='" + h + "px'>");
		document.write("	<param name='servletRootURL'	value='" + pRootUrl + "'>");
		document.write("	<param name='fileURL'		value='" + pFileUrl + "'>");
		document.write("	<param name='UbiServerURL'	value='" + pServerUrl + "'>");
		document.write("	<param name='dataSource'	value='" + pDataSource + "'>");
		document.write("	<param name='jrfFileDir'	value='" + pJrfDir + "'>");
		document.write("	<param name='jrfFileName'	value='" + pJrfName + "'>");
		document.write("	<param name='scale'		value='" + pScale + "'>");
		document.write("	<param name='toolbar'		value='" + pToolbar + "'>");
		document.write("	<param name='progress'		value='" + pProgress + "'>");
		document.write("	<param name='execType'		value='" + pExecType + "'>");
		document.write("</object>");
	}

	function finishLoad() {

		console.log('finishLoad');
	}
//-->
</script>
<script language="javascript" for="UbiViewer" event="PrintEnd()">
<!--
	console.log('PrintEnd Status : ' + UbiViewer.getPrintStatus());
//-->
</script>
<script language="javascript" for="UbiViewer" event="ExportEnd()">
<!--
	console.log('ExportEnd filePath : ' + UbiViewer.getVariable("exportFilePath"));
//-->
</script>
</head>
<body style='margin:3px' onresize="Ubi_Resize()">
<script type="text/javascript">
<!--
	UbiViewer_Init();
//-->
</script>
</body>
</html>
