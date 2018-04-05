<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<HTML>
<HEAD>
<TITLE>DEXTUploadX 업로드</TITLE>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery-1.6.2.min.js"/>"></script>  
<script type="text/javascript" src="<c:url value="/base/js/jquery/json.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<SCRIPT FOR="FileUploadMonitor" Event="OnError(nCode, sMsg, sDetailMsg)" LANGUAGE="javascript">
	OnFileMonitorError(nCode, sMsg, sDetailMsg);
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">


	function OnFileMonitorError(nCode, sMsg, sDetailMsg)
	{
		//alert(nCode);
		alert(sMsg);
		//alert(sDetailMsg);
	}
	
	function OnLoading()
	{
	
		var _contextRoot = window["contextRoot"] || "";
		var _webAppPath = location.protocol + "//" + location.hostname +
		((!location.port || location.port == "80") ? "" : ":" + location.port) + _contextRoot;
	

		document.all["FileUploadMonitor"].UploadURL = _webAppPath+"<c:url value="/base/common/DextFileUploadProcess.jsp" />";
	    //alert(document.all["FileUploadMonitor"].UploadURL);

		// 파일 매니저의 몇 가지 속성들(DefaultPath, Filter 등등)의 값을 파일 모니터에 복사합니다.
		document.all["FileUploadMonitor"].Properties = opener.document.all["FileUploadManager"].Properties;
		
		
		// 이 페이지의 부모 페이지에 있는 파일 매니저 컨트롤의 모든 파일 및 폼 아이템을 파일 모니터 컨트롤에 복사합니다.
		document.all["FileUploadMonitor"].Items = opener.document.all["FileUploadManager"].Items;
		
		
		
		document.all["FileUploadMonitor"].CodePage = 65001;
		

		if (0 == document.getElementById("FileUploadMonitor").Count){
    	    //document.getElementById("FileUploadMonitor").CheckAutoCloseWindow = true;
			document.getElementById("FileUploadMonitor").EnableEmptyFileUpload = true;
		}
		document.all["FileUploadMonitor"].Transfer();
	}

	var fileListArray=[];
	var deleteFileListArray=[];
	var oldFileListArray=[];
	
    function ProcessEnd()
    { 
    	
        var ResponseData; 

      	//alert(document.all["FileUploadMonitor"].ResponseData);

        // 전송이 완료된 후 서버에 저장된 실제 파일의 정보를 저장한다. (파일명, 저장된 파일명, 저장 경로)
        ResponseData = (document.all["FileUploadMonitor"].ResponseData).split("|");

        var i =0;
        var fileDBParam;

		//alert(ResponseData.length);
        while(i<=ResponseData.length-1)
        {
          	//alert(i+" : ("+$.trim(ResponseData[i])+")");
            if(i==0 || i%6==0){
            	fileDBParam =new Array();
            	fileDBParam.push($.trim(ResponseData[i]));
            }else{
            	fileDBParam.push($.trim(ResponseData[i]));
            }
            if(i!=0 && (i+1)%6==0){

            	if(fileDBParam[5]=="add"){
	        		var uploadFile = {
	        				uploadPath: fileDBParam[0],
	        				uploadedFileName:  fileDBParam[1],
	        				orginFileName: fileDBParam[2],
	        				fileSize :fileDBParam[3],
	        				fileExtension :fileDBParam[4]
	    			};
	        		fileListArray.push(uploadFile);
	        		
            	}else if(fileDBParam[5]=="del"){

            		deleteFileListArray.push({fileId: fileDBParam[0],size: "0", flag: "del"});
            	}else if(fileDBParam[5]=="use"){

            		oldFileListArray.push({fileId: fileDBParam[0],size: "0", flag: "use"});
            	}
            }
			i=i+1;
        }
        
        var fileListParam={
        		fileList : fileListArray
        };
        
        /*
        alert("fileListArray.length:" +fileListArray.length+"\n"+
        		"deleteFileListArray.length:"+deleteFileListArray.length+"\n"+
        		"oldFileListArray.length:"+oldFileListArray.length+"\n");
        */
        
        if(fileListArray.length>0){
        	fileDBSave(fileListParam);
        }else{
	        if(deleteFileListArray.length>0){
	        	
	        	setUpdateFiles(deleteFileListArray);
	        }
	        if(oldFileListArray.length>0){
	        	
	        	setUpdateFiles(oldFileListArray);
	        }
            updateForm();
        }
        
        
    }
    
    function monitorClose(){
    	window.self.close();
    }

    (function($){

    	var $form = $(opener.document).find("#${param.formId}");
    	
    	updateForm = function(){
        	opener.writeSubmit($form);
        	monitorClose();
    	};
    	
    	fileDBSave = function(fileListParam) {

			$.postJSON("<c:url value='/support/fileupload/dextUploadFileList.do'/>",fileListParam ,function(data) {
				if(data.length>0){
					var files = [];
					$.each(data, function(i, uploadedFile) {
						//alert(uploadedFile.fileId);
						
						files.push({fileId:uploadedFile.fileId,  size:uploadedFile.fileSize,  flag:"add"});
					});
					setUpdateFiles(files);
			        if(deleteFileListArray.length>0){
			        	
			        	setUpdateFiles(deleteFileListArray);
			        }
			        if(oldFileListArray.length>0){
			        	
			        	setUpdateFiles(oldFileListArray);
			        }
					opener.writeSubmit($form);
					monitorClose();
				}else{
				     alert("전송이 취소되었습니다.");
				}
			});
    		
    	}; 
    	var fileAllCount=0;
         setUpdateFiles = function(files) {	// 폼에 파일 관련 정보 셋팅

    		
    		if(files.length > 0 && $form) {

    			$.each(files, function(index) {
    				var fileInfo = $.extend({index:index}, this);
    				$form.append('<input type="hidden" name="fileLinkList['+fileAllCount+'].fileId" value="'+fileInfo.fileId+'"/>');
    				$form.append('<input type="hidden" name="fileLinkList['+fileAllCount+'].flag" value="'+fileInfo.flag+'"/>');
    				$form.append('<input type="hidden" name="fileLinkList['+fileAllCount+'].fileSize" value="'+fileInfo.size+'"/>');
    				fileAllCount++;
    			});
    			
    		}
    		
    		return files;
    	};
    	
    })(jQuery);  
</script>
<script language="javascript" for="FileUploadMonitor" event="OnTransferComplete()">  
  
    ProcessEnd();        
</script>

<script language="javascript" for="FileUploadMonitor" event="OnTransferCancel()"> 
     alert("전송이 취소되었습니다.");
</script>
</HEAD>	
<BODY onload="OnLoading()" bottomMargin=0 leftMargin=0 topMargin=0 rightMargin=0>
		<OBJECT id="FileUploadMonitor" height="335" width="445" classid="CLSID:96A93E40-E5F8-497A-B029-8D8156DE09C5"
			CodeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>" VIEWASTEXT>
			<param name="EnableAddFileButton" value="FALSE" />
			<param name="EnableAddFolderButton" value="FALSE" />
			<param name="EnableDeleteButton" value="FALSE" />
			<param name="EnableTransferButton" value="FALSE" />
			<param name="CheckAutoCloseWindow" value="FALSE" />
			<param name="VisibleAutoCloseWindow" value="FALSE" />
			<param name="EnableAutoCloseWindow" value="FALSE" />
		</OBJECT>
</BODY>
</HTML>