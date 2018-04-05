<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>UbiReport4 WSViewer</title>
<script src='./js/ubinonax.js'></script>
<script src='./js/msg.js'></script>
<script language='javascript'>
<!--
/*-----------------------------------------------------------------------------------
wsViewer.invisibletoolbar = 'EXPORT_PDF,EXPORT_EXCEL';
wsViewer.printset();
wsViewer.directPrint();
wsViewer.exportset();
wsViewer.ExportFile('PDF');
-----------------------------------------------------------------------------------*/
	/* URL 정보 */
	var app = 'myapp';
	var appUrl = self.location.protocol + '//' + self.location.host + (app==''?'':('/' + app));

	/* Viewer Object */
	var wsViewer = null;
	var wsViewerWidth = 900;
	var wsViewerHeight = screen.height - 200;
	var wsViewerLeft = (screen.width - wsViewerWidth)/2;
	var wsViewerTop = (screen.height - wsViewerHeight)/2;

	/* Viewer Param */
	var pServerUrl = appUrl + '/UbiServer';
	var pRootUrl = appUrl;
	var pFileUrl = appUrl + '/ubi4';
	var pScale = '-9999';
	var pToolbar = 'true';
	var pProgress = 'true';

	/* Modify for your environment */
	var pDatasource = 'Tutorial';
	var pJrfDir = '/webapp/myapp/ubi4/work/';
	var pJrf = 'ubi_sample.jrf';
	var pArg = 'user#홍길동';

	function Ubi_WSViewerInit() {

		InitWebSocket(ShowReport);
	}

	function ShowReport(ws) {
		
		wsViewer = new UbiWSViewer(ws);

		wsViewer.ubiserverurl = pServerUrl;
		wsViewer.servletrooturl = pRootUrl;
		wsViewer.fileurl = pFileUrl;
		wsViewer.scale = pScale;
		wsViewer.toolbar = pToolbar;
		wsViewer.progress = pProgress;
		wsViewer.datasource = pDatasource;
		wsViewer.jrffiledir = pJrfDir;

		wsViewer.jrffilename = pJrf;
		wsViewer.arg = pArg;

		wsViewer.setResize(wsViewerLeft, wsViewerTop, wsViewerWidth, wsViewerHeight);	// setResize('max');, setResize('hide');
		wsViewer.retrieve();
	}

	function RetrieveEnd() {

	}

	function PrintEnd(status) {

	}
	
	function ExportEnd(filePath) {

	}
	
	function Ubi_Version() {

		wsViewer.aboutBox();
	}

//-->
</script>
</head>
<body style="margin:2px">
<h1>WebSocket<h1>
<script type="text/javascript">
<!--
	Ubi_WSViewerInit();
//-->
</script>
</body>
</html>
