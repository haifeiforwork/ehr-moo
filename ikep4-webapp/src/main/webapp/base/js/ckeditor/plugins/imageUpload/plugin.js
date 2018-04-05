var fnCKEditor_imageUploadComplete;
(function($) {
    var imageUploadCmd = {
        modes: { wysiwyg: 1, source: 1 },
        exec: function(editor) {   
        	var $button = jQuery("a.cke_button_imageUpload");
        	var buttonTop = $button.offsetParent().offset().top + $button.position().top;
        	
        	iKEP.showDialog({
        	    title : editor.config.popupTitle,
        	    url : iKEP.getContextRoot() + "/support/fileupload/uploadFormForEditor.do?targetId=" + editor.element.getId() + "&" + $("#editorFileUploadParameter").serialize(),
        	    modal : true,
        	    resizable: false, 
        	    width : 600,
        	    height : 165,
        	    //position : ["center", buttonTop + $button.outerHeight()],	// 버튼 위치의 바로 아래로 보여지도록 위치 보정
        	    callback : function(result) {	//fileId, fileName, targetId
        	    	var fileId = result.fileId, targetId = result.targetId;
        	    	var editor = $("#" + targetId).ckeditorGet();  
        	    	editor.insertHtml("<img name='editorImage' id='"+ fileId +"' src='" + iKEP.getWebAppPath() + "/support/fileupload/downloadFile.do?fileId=" + fileId + "'/>");
        	    },
        	    scroll : "no" 
        	}); 
        }
    }; 
   
    var pluginName = 'imageUpload';  
    CKEDITOR.plugins.add(pluginName, {
      init: function(editor) { 
         var command = editor.addCommand(pluginName, imageUploadCmd);
         editor.ui.addButton('imageUpload', {
        	label : editor.lang.common.image,
            command: pluginName,
            icon: iKEP.getContextRoot() + "/base/images/icon/ic_img_add.png"
         }); 
  
      }  
  });
  
    /*
     * 이미지 업로드시 이미지 사이즈를 지정하고자 할때 해당 이미지 태그 생성시 onload에 이 함수를 할당한다.
     * ex) <img onload="fnCKEditor_imageUploadComplete(this)" ....
     * 
     * 사이즈가 지정되면 해당 이미지 resize시 가로 사이즈만 조정하게 되면 세로 사이즈가 비율에 따라 조정 되지 않아 우선 적용되지 않도록 함.
     * */
    fnCKEditor_imageUploadComplete = function(img) {
    	var $img = $("#" + img.getAttribute("id"), $("iframe", "#cke_contents_contents").contents());
    	
    	setTimeout(function() {
    		var width = $img.width(),
    			height = $img.height();
    		if(width > 0 && height > 0) {	// 사이즈 디텍트가 안되는 경우가 있음 : download 속도가 느려질때...
    			$img.css({width:width+"px", height:height+"px"});
    		}
    	}, 100);
    	
    }
})(jQuery); 
