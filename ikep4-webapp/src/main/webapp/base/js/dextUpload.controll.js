	// sMsg는 간략하게 요약된 에러 메시지입니다.
	// sDetailMsg는 보다 자세한 정보가 포함 된 에러 메시지입니다.
	// sDetailMsg는 에러 메시지에 따라 공백인 경우도 있습니다.
	// 반드시 위와 같이 선언을 해줘야 합니다.
	// 이 에러 처리 방식은 DEXTUploadX의 모든 컨트롤이 동일합니다.
	function OnFileManagerError(nCode, sMsg, sDetailMsg)
	{
		//alert(nCode);
		alert(sMsg);
		//alert(sDetailMsg);
	}
    // 파일 추가 버튼
    function btnAddFile_Onclick()
    {
        document.getElementById("FileUploadManager").OpenFileDialog(); 
        dextFileUploadRefresh(); 
    }

    // 항목 삭제 버튼
    function btnDeleteItem_Onclick()
    {
        document.getElementById("FileUploadManager").DeleteSelectedFile(); 
        dextFileUploadRefresh(); 
    }
    function dextFileUploadRefresh()
    {
        document.getElementById('_StatusInfo_count').innerHTML = document.all["FileUploadManager"].Count + " ";
        
        //var AddedFileSize = Math.round(원하는수*1000)/1000; 처럼 사용 (1000은 소수점 뒤 3자리수)
        //var AddedFileSize = (document.getElementById("FileUploadManager").Size) / 1024; //KB
        //document.getElementById('_StatusInfo_count').innerHTML = document.all["FileUploadManager"].Count + " ";
        //document.getElementById('_StatusInfo_size').innerHTML ="(" + Math.round(AddedFileSize*100)/100 + " KB)";
        
        var AddedFileSize = document.getElementById("FileUploadManager").Size;
        
        if ( 1 < AddedFileSize/1024/1024)
        {
            AddedFileSize = AddedFileSize/(1024*1024);
            document.getElementById('_StatusInfo_size').innerHTML ="(" + Math.round(AddedFileSize*100)/100 + " MB)";
        }
        else if ( 1 < AddedFileSize/1024)
        {
            AddedFileSize = AddedFileSize/1024;
            document.getElementById('_StatusInfo_size').innerHTML ="(" + Math.round(AddedFileSize*100)/100 + " KB)";
        }
        else
            document.getElementById('_StatusInfo_size').innerHTML ="(" + Math.round(AddedFileSize*100)/100 + " Byte)";
    }
    function btnTransfer_Onclick(formId)
    {	
    	//alert(iKEP.getContextRoot()+"/base/common/DextFileUploadMonitor.jsp?formId="+formId);
    	iKEP.popupOpen(iKEP.getContextRoot()+"/base/common/DextFileUploadMonitor.jsp?formId="+formId, {width:445, height:335 ,scrollbar:false},"DextUploadMonitor");


    }
    function dextFileUploadInit( fileSize, fileAttachOption, allowType){
	    document.getElementById('_StatusInfo_count').innerHTML = "0 ";
	    document.getElementById('_StatusInfo_size').innerHTML = "(0 Byte)"; 
		document.getElementById("FileUploadManager").XOffsetPercentOfImage = 50; 
		document.getElementById("FileUploadManager").YOffsetPercentOfImage = 40; 
		document.getElementById("FileUploadManager").ImageURL = iKEP.getWebAppPath()+"/base/images/dextupload/fileupload.gif";
	
		if(fileSize){
		
			document.getElementById("FileUploadManager").MaxTotalSize = eval(fileSize);
			var MaxTotalSizeNm = document.getElementById("FileUploadManager").MaxTotalSize;
		
		 	if ( 1 < MaxTotalSizeNm/1024/1024){
		 		MaxTotalSizeNm = MaxTotalSizeNm/(1024*1024);
	            document.getElementById('_Total_size').innerHTML ="&nbsp;&nbsp;&nbsp;※ 전체용량 " + Math.round(MaxTotalSizeNm*100)/100 + " MB 까지 가능";
	        }else if ( 1 < MaxTotalSizeNm/1024){
	        	MaxTotalSizeNm = MaxTotalSizeNm/1024;
	            document.getElementById('_Total_size').innerHTML ="&nbsp;&nbsp;&nbsp;※ 전체용량 " + Math.round(MaxTotalSizeNm*100)/100 + " KB 까지 가능";
	        }else{
	            document.getElementById('_Total_size').innerHTML ="&nbsp;&nbsp;&nbsp;※ 전체용량 " + Math.round(MaxTotalSizeNm*100)/100 + " Byte 까지 가능";
	        }
		 
		}
		
		var defaultAllowFileType = ["all"];//["txt", "img", "comp", "doc"];
		//var defaultAllowExt = ["jpg,gif,png,txt,zip"];
		var filterings = [
		    {type:"all",  extensions:"asc,txt,text,diff,log,htm,html,xhtml,rtf,xml,vcf,csv,bmp,gif,jpeg,jpg,png,tiff,tif,zip,alz,rar,jar,tar,bat,doc,dot,pdf,pgp,ps,ai,eps,rtf,xls,xlb,ppt,pps,pot,docx,pptx,xlsx,hwp,wav,wma,mpga,mpega,mp2,mp3,m4a,avi,mkv,mpeg,mpg,mpe,qt,mov,mp4,m4v,flv,rv,swf,swfl,com,exe"},
			{type:"txt",  extensions:"asc,txt,text,diff,log,htm,html,xhtml,rtf,xml,vcf,csv"},	// text
			{type:"img",  extensions:"bmp,gif,jpeg,jpg,png,tiff,tif"},	//images
			{type:"comp", extensions:"zip,alz,rar,jar,tar,bat"},	//compress
			{type:"doc",  extensions:"doc,dot,pdf,pgp,ps,ai,eps,rtf,xls,xlb,ppt,pps,pot,docx,pptx,xlsx,hwp"},	// document
			{type:"ado", extensions:"wav,wma,mpga,mpega,mp2,mp3,m4a"},		//audio
			{type:"vod", extensions:"avi,mkv,mpeg,mpg,mpe,qt,mov,mp4,m4v,flv,rv,swf,swfl"},	//video
			{type:"app", extensions:"com,exe"}	// application
			//{type:"all", title:langUpload.filter.all, extensions:"*"}	// all
		];

		if(fileAttachOption && allowType){
			
			if(fileAttachOption=="0"){//파일확장자 허용방식 //직접입력.
				document.getElementById("FileUploadManager").Filter = "허용확장자("+allowType+")|*."+allowType.replaceAll(",", ";*.");	
			}else{//파일종류 방식.
				var filterItem;
				for(var i=0; i<filterings.length; i++) {
					if(allowType == filterings[i].type) {
						filterItem = filterings[i];
						document.getElementById("FileUploadManager").Filter = (filterItem.type).toUpperCase()+" Files|*."+filterItem.extensions.replaceAll(",", ";*.")+"|";
						break;
					}
				}
				if(filterItem==null){
					filterItem = filterings[0];
					document.getElementById("FileUploadManager").Filter = (filterItem.type).toUpperCase()+" Files|*."+filterItem.extensions.replaceAll(",", ";*.")+"|";
				}
			}
		}
		
		
	    iKEP.iFrameContentResize();
    }
    
    function  oldFileSetting(oldFilelist, form){
    	 $jq.each(oldFilelist,function(index){
  		   var fileInfo = $jq.extend({index:index}, this);
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].fileId" value="'+fileInfo.fileId+'"/>');
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].flag" value="use"/>');
			$jq(form).append('<input type="hidden" name="fileLinkList['+index+'].fileSize" value="'+fileInfo.size+'"/>');
		});
    }