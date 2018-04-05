<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<script type="text/javascript" src="<c:url value="/base/js/dextUpload.controll.js"/>"></script>
<SCRIPT FOR="FileUploadManager" Event="OnError(nCode, sMsg, sDetailMsg)" LANGUAGE="javascript">
		OnFileManagerError(nCode, sMsg, sDetailMsg);
</SCRIPT>
<script for="FileUploadManager" event="OnChangedStatus" language="javascript"> 
 		dextFileUploadRefresh();
        document.getElementById("imgBtn_fileAdd").focus();
</script>
