/*
* wonchu Editor v1.0
* 공식배포처 : http://www.wonchu.net
* 제작자 이메일 : wonchu.net@gmail.com
*
* 이프로그램은 개인/기업/영리/비영리 에 관계없이 무료로 자유롭게 수정 배포 할수 있으나 판매는 할수 없습니다.
* 이프로그램의 사용으로 인해 발생한 어떠한 문제도 제작자는 책임지지 않습니다.
* 사용상의 문제나 건의사항은 공식 배포사이트의 게시판을 이용해주시거나 메일로 보내 주시기 바랍니다.
*/

(function($){
    $.fn.wceditor = function(config) {
        var $target = this;
		if($target[0].tagName.toLowerCase() == "textarea") {
		    if(typeof(config)=="undefined") config = wceditorBaseToolbar;
		    WCEditor.initialize($target, config);
		}else{
		    alert("Textarea does not exist."); return;
		}
    };
})(jQuery);


var wceditorBaseConfig = {
        root                : "/ikep4-webapp/base/js/wceditor/",
        width               : "100%",
    	height              : "350px",
    	minResizHeight      : "100px",
    	fontFamily          : "돋움, dotum",
    	fontSize            : "9pt",
    	miniMode            : false,
    	conversionMode      : true,
    	resize              : true
};

var wceditorMiniToolbar = {
	    toolbar : [
	        ["newpage"],
	        ["forecolor","backcolor"],
	        ["bold","italic","strikethrough","underline"],
	        ["character", "link"]
	   	]
};

var wceditorMiniNoteToolbar = {
	    toolbar : [
	        ["forecolor","backcolor"],
	        ["bold","italic","underline"],
	        ["insertorderedlist","insertunorderedlist"],
	        ["character", "link","image"]
	   	]
};

var wceditorSimpleToolbar = {
	    toolbar : [
	        ["newpage"],
	        ["fontsize"],
	        ["forecolor","backcolor"],
	        ["bold","italic","strikethrough","underline"],
	        ["justifyleft","justifycenter","justifyright","justifyfull"],
	        ["insertorderedlist","insertunorderedlist"],
	        ["character","link","urlimage"]
	    ]
};

var wceditorBaseToolbar = {
	    toolbar : [
	        ["fontname","fontsize"],
	        ["newpage","previewer", "print"],
	        ["forecolor","backcolor"],
	        ["bold","italic","strikethrough","underline"],
	        ["table","fieldset","hr"],
	        ["justifyleft","justifycenter","justifyright","justifyfull"],
	        ["insertorderedlist","insertunorderedlist"],
	        ["character","link","urlimage"]
	    ]
};
 
var wceditorMaxToolbar = {
    toolbar : [
        ["fontname","fontsize"],
        ["copy","cut","paste"],
        ["newpage","previewer", "print"],
        ["forecolor","backcolor"],
        ["bold","italic","strikethrough","underline"],
        ["table","fieldset","hr"],
        ["justifyleft","justifycenter","justifyright","justifyfull"],
        ["insertorderedlist","insertunorderedlist"],
        ["character","link","urlimage"]
    ]
};


var WCEditor = {
    
    /*###############################################################################
    ## Define
    ###############################################################################*/
    
    /* data */
    data : { 
             "initTag"      : "<p>&nbsp;</p>",
             "align"        : "justifyleft;justifycenter;justifyright;justifyfull",
             "keycode"      : "8;37;38;39;40"
           },
    
    /* tempate */
    template : {
        "init"          : "<div id=\"editorDiv\"><div id=\"toolBarBgDiv\"></div><div id=\"toolBarDiv\"><ul><li class=\"starter\"></li></ul></div><div id=\"editDiv\"><div id=\"resizeDiv\"></div><iframe id=\"editIframe\" src=\"\" width=\"100%\" height=\"100%\" border=\"0\" frameborder=\"0\" /></div><div id=\"modeDiv\"><div></div><ul><li id=\"textModeLi\" class=\"off\"></li><li id=\"htmlModeLi\" class=\"off\"></li><li id=\"editorModeLi\" class=\"on\"></li></ul></div></div>",
        "separator"     : "<li class=\"separator\"></li>"
    },
    
    /* button */
    button : {
                "fontname"              : {"title" : "글꼴", 				"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "openLayer"},
                "fontsize"              : {"title" : "글자크기",        	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "openLayer"},
                "copy"                  : {"title" : "복사하기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "execute"},
                "cut"                   : {"title" : "잘라내기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "execute"},
                "paste"                 : {"title" : "붙여넣기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "execute"},
                "newpage"               : {"title" : "새로열기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "newpage"},
                "previewer"             : {"title" : "미리보기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "previewer"},
                "print"                 : {"title" : "인쇄하기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "print"},
                "forecolor"             : {"title" : "글자색상",        	"isIfrm" : "Y", "ifrmLeft" : "0",  	"mouseUp" : "O",  "functionNm" : "openLayer"},
                "backcolor"             : {"title" : "배경색상",        	"isIfrm" : "Y", "ifrmLeft" : "0",  	"mouseUp" : "O",  "functionNm" : "openLayer"},
                "bold"                  : {"title" : "굵게(Crtl+B}",    	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "italic"                : {"title" : "기울림꼴(Crtl+I}",	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "underline"             : {"title" : "글자밑줄(Crtl+U}",	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "strikethrough"         : {"title" : "취소선",          	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "table"                 : {"title" : "테이블",          	"isIfrm" : "Y", "ifrmLeft" : "0",  	"mouseUp" : "",   "functionNm" : "openLayer"},
                "fieldset"              : {"title" : "필드셋",          	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"},
                "hr"                    : {"title" : "밑줄",            	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"},
                "justifyleft"       	: {"title" : "왼쪽맞춤",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "justifycenter"         : {"title" : "가운데맞춤",      	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "justifyright"          : {"title" : "오른쪽맞츰",      	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "justifyfull"           : {"title" : "전체맞춤",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "insertorderedlist"     : {"title" : "번호매기기",      	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "insertunorderedlist"   : {"title" : "글머리기호",      	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "O",  "functionNm" : "execute"},
                "indent"                : {"title" : "들여쓰기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "execute"},
                "outdent"               : {"title" : "내어쓰기",        	"isIfrm" : "N", "ifrmLeft" : "0",   "mouseUp" : "Y",  "functionNm" : "execute"},
                "character"             : {"title" : "특수문자",        	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"},
                "link"                  : {"title" : "링크만들기",      	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"},
                "urlimage"              : {"title" : "이미지링크",      	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"},
                "image"                 : {"title" : "이미지",          	"isIfrm" : "Y", "ifrmLeft" : "0",   "mouseUp" : "",   "functionNm" : "openLayer"}
    },
    
    /*###############################################################################
    ## editor initialize
    ###############################################################################*/
    
    /* editor 초기화 */
    initialize : function(target, config){
        WCEditor.config= $jq.extend(wceditorBaseConfig, config);
        target.hide();
        
        // create edit div
        $jq(this.template.init).insertBefore(target);
        
        if(this.config.width!="100%")   $jq("#editorDiv").css("width", this.config.width);
		if(this.config.height!="350px") $jq("#editDiv").css("height", this.config.height);
        
        // object
        this.mode       = "editor";
        this.target     = target;
        this.editor 	= $jq("#editorDiv");
		this.edit 	    = $jq("#editIframe");
		this.editCw	    = this.edit[0].contentWindow;
		this.editDoc	= this.editCw.document;
		
		// attribute
		this.resizeY	        = 0;
		this.imageRoot          = this.config.root + "img/";
		this.htmlRoot           = this.config.root + "html/";
		this.affectedFontStyle  = "";
		this.preview            = null;
		this.savedRange	        = null;
		this.tempRange			= null;
		this.isES               = typeof(this.editDoc.selection)=="object";
        this.isIE 	   	        = (navigator.appName == "Microsoft Internet Explorer");
        
		this.editDoc.open();
		this.editDoc.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\"><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/><style>body{background-color:#ffffff;word-wrap:break-word;ime-mode:active;margin:10px;padding:0;font-family:" + this.config.fontFamily + ";font-size:" + this.config.fontSize + ";} P {margin:0;padding:0;line-height:1.5;}</style></head><body>" + this.data["initTag"] + "</body></html>");
		this.editDoc.close();
		
		// edit mode
		if(this.isIE) {
			this.editDoc.body.contentEditable = "true";
		} else {
			this.editDoc.designMode = "on";
		}
        
		// edit event
		$jq(this.editDoc)
		.bind("keydown", function(event) {
			WCEditor.checkKeyDown(event);
		})
		.bind("keyup", function(event) {
			WCEditor.checkKeyUp(event);
		})
		.bind("mouseup", function(event) {
			WCEditor.checkMouseUp(event);
		});
		
		// make button
		var id="", left="";
		for (var i=0; i<this.config.toolbar.length; i++){
            for(var j=0; j<this.config.toolbar[i].length; j++){
            	id = this.config.toolbar[i][j];
                if(id=="fontname"){
                    $jq("#toolBarDiv > ul").append("<li id=\"" + id + "Li\" title=\"" + this.button[id].title + "\"><a href='#' id='fontnameA'><span id='fontnameSpan'>" + WCEditorLib.getFirsArrayData(this.config.fontFamily) + "</span></a></li>");
                }else if(this.config.toolbar[i][j]=="fontsize"){
                    $jq("#toolBarDiv > ul").append("<li id=\"" + id + "Li\" title=\"" + this.button[id].title + "\"><a href='#' id='fontsizeA' unselectable='on'>" + this.config.fontSize + "</a></li>");
                }else{
                    $jq("#toolBarDiv > ul").append("<li id=\"" + id + "Li\" title=\"" + this.button[id].title + "\"><img src='" + this.imageRoot + id + ".gif' unselectable='on'></li>");
                }
                
                /* 레이어 생성 */
				if(this.button[id].isIfrm=='Y') { 
            		this.editor.append("<iframe id=\"" + id + "Frame\" src=\"" + this.htmlRoot + id  + ".html\" border=\"0\" frameborder=\"0\" scrolling=\"no\" class=\"buttonIframe\" />");
            		this.setIfrmLeft(id);
			    }
            }
            
            if(this.config.toolbar.length!=(i+1)) $jq("#toolBarDiv > ul").append(this.template.separator);
		}
        
        // button event
        $jq("#toolBarDiv > ul > li:not('.starter,.separator')")
		.bind("mouseover", function(event) {
			WCEditor.changeButton($jq(this), "over");
		})
		.bind("mouseout", function(event) {
			WCEditor.changeButton($jq(this), "out");
		})
		.bind("mousedown", function(event) {
			WCEditor.changeButton($jq(this), "down");
		})
		.bind("mouseup", function(event) {
		    if(WCEditor.button[$jq(this).attr("id").replace("Li", "")].mouseUp=="Y"){
			    WCEditor.changeButton($jq(this), "up");
		    }
		})
		.bind("click", function(event) {
		    var s = $jq(this).attr("id").replace("Li", "");
			eval("WCEditor." + WCEditor.button[s].functionNm + "('" + s + "')");
		});

        if($jq(this.target).val()!=""){
        	this.setContent($jq(this.target).val());
        }
        
        // conversion mode
        if(!this.config.conversionMode){
            if(this.config.resize)
                $jq("#modeDiv > ul").hide();
            else
                $jq("#modeDiv").hide();
        }else {
            $jq("#modeDiv > ul li").click(function() {
                switch (this.id) {
                    case "editorModeLi":
                        if($jq("#editorModeLi").hasClass("off")){
                            $jq(WCEditor.editDoc.body).html($jq(WCEditor.editDoc.body).text());
                            $jq("#editorModeLi").removeClass("off");
                            $jq("#editorModeLi").addClass("on");
                            $jq("#htmlModeLi").removeClass("on");
                            $jq("#htmlModeLi").addClass("off")
                            $jq("#toolBarBgDiv").hide();
                            WCEditor.mode = "editor";
                        }
                        break;
                    case "htmlModeLi":
                        if($jq("#htmlModeLi").hasClass("off")){
                            $jq(WCEditor.editDoc.body).text($jq(WCEditor.editDoc.body).html());
                            $jq("#editorModeLi").removeClass("on");
                            $jq("#editorModeLi").addClass("off");
                            $jq("#htmlModeLi").removeClass("off");
                            $jq("#htmlModeLi").addClass("on");
                            $jq("#toolBarBgDiv").show();
                            WCEditor.mode = "html";
                        }
                        break;
                    default:
                        break;
                }
            });
        }
        
        if(this.config.resize){
            $jq("#modeDiv > div").addClass("resize");
            
            $jq("#modeDiv > div").bind("mousedown", function(event) {
    			WCEditor.resizeStart(event)
    		});
        }
        
    },
    
    /*###############################################################################
    ## editor control
    ###############################################################################*/
    
    /* html 추가 */
    setHtml : function (s, o){
        this.restoreRange();
		if (this.isES) {
            this.savedRange.pasteHTML(s);
		} else {
		    this.editDoc.execCommand("inserthtml", false, s);
		}
		
		if(typeof(o)!="undefined"){
		    $jq("#" + o+"Frame").hide();
    	    WCEditor.changeButton(o, "up");
		}
	},
	
    /* 내용 조회 */
	getContent : function(skip) {
	    if(typeof(skip)=="undefined") skip = false;
	    var str = this.mode=="editor"?$jq(WCEditor.editDoc).find("body").html():$jq(WCEditor.editDoc).find("body").text();
	    if(!skip && str==this.data["initTag"]) str="";
		return str;
	},
	
	/* 내용 설정 */
	setContent : function(s) {
		$jq(WCEditor.editDoc.body).html(s);
	},
	
	/* 내용 저장 */
	saveContent : function(skip) {
		$jq(this.target).val(this.getContent(skip));
	},
	
	/* Selection */
	getSel : function() {
	    this.editCw.focus();
	    return this.isES ? this.editDoc.selection : this.editCw.getSelection();
	},
	
	/* Range 생성 */
	getRange : function() {
	    this.editCw.focus();
	    return this.isES ? this.editDoc.selection.createRange() : this.editCw.getSelection().getRangeAt(0);
	},
	
	/* 영역, 선택 저장 */
	saveRange : function() {
		this.savedRange = this.getRange();
	},
	
	/* 선택된 영역 선택 실행 */
	restoreRange : function() {
		if(this.isES)   this.savedRange.select();
		else            this.editCw.focus();
	},

	/* 선택된 영역 텍스트 */
	getSelectedText : function() {
	    this.editCw.focus();
        return this.isES ? this.savedRange.text : this.savedRange.toString();
	},
	
	/* 선택된 영역 HTML */
	getSelectedHtml : function() {
	    this.editCw.focus();
	    if(this.isES){
	    	return this.savedRange.htmlText;
	    }else{
		    if(this.savedRange.rangeCount < 1 )  // 선택된 영역이 없다면...
		        return null;
		    
		    var d = document.createElement("p");
		    d.appendChild(this.savedRange.cloneContents());
		    return d.innerHTML;
	    }
	},
	
	/* 선택된 영역 체크 */
	checkRange : function(){
	    if(this.getSelectedText()==""){
	        var p = this.getRange();
	        if(p.pasteHTML){
    	        p.pasteHTML("<span id='WCEditorRange1'></span>&nbsp;<span id='WCEditorRange2'></span>");
    	       
    	        var tmp1 = this.editDoc.getElementById("WCEditorRange1");
    		    var tmp2 = this.editDoc.getElementById("WCEditorRange2");
    
    		    p.moveToElementText(tmp1);
    	
    		    var tmpRange = this.editDoc.body.createTextRange();
    		    tmpRange.moveToElementText(tmp2);
    
    		    p.setEndPoint("EndToEnd", tmpRange);
    		    p.select();
    		   
    		    tmp1.parentNode.removeChild(tmp1);
    		    tmp2.parentNode.removeChild(tmp2);
    	    }else{
    	        return false;
    	    }
	    }
	    return true;
	},
	
	/* keyup 이벤트 */
	checkKeyUp : function (e) {
        if(WCEditorLib.hasDataInKey("keycode", e.keyCode)) WCEditor.checkNode();
	},
	
	/* keydown 이벤트 */
	checkKeyDown : function (e) {
        // tab
        if(this.isIE && e.keyCode==9) {
			var o = e.srcElement;
			o.focus();
			o.selection = document.selection.createRange();
			o.selection.text = "    ";
			e.preventDefault();
			e.returnValue = false;
		} 
	},
	
	/* mouseUp 이벤트 */
	checkMouseUp : function (){
        WCEditor.checkNode();
	    WCEditor.closeLayers();
	    WCEditor.initLayerButonStyle();
	},
	
	/* Node 체크 */
	checkNode : function(){
	    var o, p = this.getRange();
	    if(p.collapse) p.collapse(false);
	    else return;
	        
	    this.initExecuteButonStyle();
	    
	    o = p.commonAncestorContainer || p.parentElement();
        
		for(var i=0;i<10;i++){
		    if(!(o.nodeName.toUpperCase() == "BODY" || o.nodeName.toUpperCase() == "HTML")){
		        this.changeButtonMode(o);
		        if(o.parentNode) o = o.parentNode;
		        else break;
		    }else{
		       break;
		    }   
		}
		
		if(this.affectedFontStyle != "") this.affectedFontStyle = "";
		
		//- align defalut check
        var a = WCEditorLib.getArrayData("align");
	    var b = true;
	    for(var i=0;i<a.length;i++){
	        if($jq("#" + a[i] + "Li").length>0){
	            if($jq("#" + a[i] + "Li").hasClass("down")){
	                b = false;
	                break;
	            }
	        }      
	    }

	    if(b && $jq("#justifyleftLi").length>0) this.changeButton("justifyleft", "down");
	   
	},
	
	/* button iframe x좌표 고정 */
	fixIfrmLeft : function(s){
	    
	    /*!!! 한두개 수정시는 WCEditor.button.forecolr.ifrmLeft="28px"로 아래처럼 선언하지 말고 직접 변경을 권장함 */
	    var pos = {
	        "approval.form" :    {"forecolor" : "28px", "backcolor" : "56px", "character" : "78px", "link" : "80px"},
	        "approval.textbox" : {"fontsize" : "4px", "forecolor" : "20px", "backcolor" : "44px", "character" : "158px", "link" : "143px", "urlimage" : "178px"}
	    }
	    
	    $jq.each(pos[s], function(key){
            WCEditor.button[key].ifrmLeft = this;
        });
	},
	
    /*###############################################################################
    ## button action
    ###############################################################################*/
    
    /* execCommand 실행 */
	execute : function(cmd, opt) {
        if(!WCEditorLib.hasValue(opt)) opt = null;
        this.editCw.focus();
        
		this.editDoc.execCommand(cmd, false, opt);
		this.closeLayers();
		
        if(WCEditorLib.hasDataInKey("align", cmd)){
	        var a = WCEditorLib.getArrayData("align");
	        for(var i=0;i<a.length;i++){this.changeButton(a[i], "up");}
	        this.changeButton(cmd, "down");
	    }
	},
    
    /* 레이어 열기 */
	openLayer : function (s){
		var $o = $jq("#" + s + "Frame");
		
        if($o.css("display")!="none") {
            $o.hide();
            return;
        }
        
        this.saveRange();
		this.closeLayers();
	    this.initLayerButonStyle(s);

	    $o.show();	
	    
		if(s=="link"){
			$o[0].contentWindow.init();
		}
	},
	
	/* 레이어 닫기 */
	closeLayers : function(){
	    $jq("#editorDiv iframe.buttonIframe").hide();
	},
	
	/* 새글 작성 */
	newpage : function() {
		this.closeLayers();

		if(this.getContent()!=""){
		    if(!confirm("새로 작성 하시겠습니까?")) return;
	    }
		this.setContent(this.data["initTag"]);
		//this.editCw.focus();
	},
	
	/* 미리보기 */
	previewer : function() {
		this.closeLayers();

		if(this.preview != null) this.preview.close();
		this.preview = window.open("about:blank", "preview", "resizable=yes,scrollbars=yes,left=50,top=50,width=900,height=" + this.config.height);
	
		var s="<title>PREVIEW</title>\r\n";
		s=s+"<link rel=\"stylesheet\" type=\"text/css\" media=\"screen\" href=\"/include/css/import.css\" media=\"screen\" />\r\n";
		s=s+"<style>P {white-space:pre;margin-top:3px;margin-bottom:3px;margin-left:3;margin-right:3;word-break:break-all;}</style>\r\n";
		s=s+"<table border=0 cellspacing=0 cellpadding=0 width=100% height=100% style=\"cursor:pointer\" onClick=\"window.close()\"><tr><td align=center valign=top style='border:1px solid #aaa'>\r\n";
		s=s+"<table border=0 cellspacing=0 cellpadding=0 width=95% height=95%><tr><td valign=top><br>\r\n";
		s=s+this.getContent();
		s=s+"</td></tr></table></td></tr></table>\r\n";
	
		this.preview.document.open();
		this.preview.document.write(s);
		this.preview.document.close();
	},
	
	/* 프린트 */
	print : function(){
		this.closeLayers();
		this.edit.focus();
		window.print();
	},	
	
	/*###############################################################################
    ## miscellaneous action
    ###############################################################################*/
    
	/* button iframe left */
	setIfrmLeft : function(s){
	    
	    if(this.config.miniMode){
	        if(s=="fontname"){
	            $jq("#" + s + "Frame").css({"width" : "120px", "height" : "141px"});
	        } else if(s=="fontsize"){
	            $jq("#" + s + "Frame").css({"width" : "240px", "height" : "127px"});
	        } else if(s=="table"){
	            $jq("#" + s + "Frame").css({"width" : "200px", "height" : "133px"});
	        } else if(s=="character"){
	            $jq("#" + s + "Frame").css("width", "260");
	        }else if(s=="forecolor" || s=="backcolor" || s=="hr"){
	            $jq("#" + s + "Frame").css({"width" : "160px", "height" : "148px"});
	        }
	    }

        // left 값지정 - hidden 페이지에 만들어진 에디터는 position().left 값을 구할수 없어 절대 위치값을 주어야함(ex : approval의 폼항목 선택)
        if(parseInt(this.button[s].ifrmLeft)!=0){
            $jq("#" + s + "Frame").css("left", parseInt(this.button[s].ifrmLeft) + "px");
            return;
        }

		var left  = $jq("#" + s + "Li").position().left;
		var floor = Math.floor(parseInt($jq("#" + s + "Frame").css("width"))/2);
		
		if(floor<left) {
			if(s=="fontname") left += 31;
			else if(s=="fontsize") left += 21;
			else left += 13
			left -= floor;
		}
		
		$jq("#" + s + "Frame").css("left", left + "px");
	},
	
    /* 버튼 효과(mouse) */
	changeButton : function(t, m, p){
	    if(typeof(t) =="string") t = $jq("#" + t + "Li");
		var c = t.attr("class");
		if(m=="down" && c=="down" && typeof(p)=="undefined"){
		    if(c="up") t.attr("class", "up");
		} else if((m=="up" && c=="down") || c!="down") {
		    if(c!=m) t.attr("class",  m);
        }
	},
	
	/* 버튼 효과(key) */
	changeButtonMode : function(o){
	    var s = o.nodeName.toUpperCase();

	    if(s=="STRONG" || s=="B"){
	        this.changeButton("bold", "down", "Y");
	    }else if(s=="EM" || s=="I"){
	        this.changeButton("italic", "down", "Y");
	    }else if(s=="STRIKE"){
	        this.changeButton("strikethrough", "down", "Y");
	    }else if(s=="U"){
	        this.changeButton("underline", "down", "Y");
	    }else if((s=="FONT" && o.style) || (s=="SPAN" && o.style)){
	        if(o.style.color!="" && !this.isAffectedFontStyle("forecolor")){
	            $jq("#forecolorLi").css("background-color", o.style.color);
	        }else if(o.style.backgroundColor!="" && !this.isAffectedFontStyle("backcolor")){
	            $jq("#backcolorLi").css("background-color", o.style.backgroundColor);
	        }else if(o.style.fontFamily!="" && !this.isAffectedFontStyle("fontname")){
	            $jq("#backcolorLi").css("background-color", o.style.backgroundColor);
	            $jq("#fontnameSpan").html(WCEditorLib.getFirsArrayData(o.style.fontFamily));
	        }else if(o.style.fontSize!="" && !this.isAffectedFontStyle("fontsize")){
	            $jq("#fontsizeA").html(o.style.fontSize);
	        }
	    }else if(s=="P"){
	    	if(o.align){
	    		this.changeButton("justify" + o.align, "down", "Y");
	    	}else if(o.style.textAlign){
	    		this.changeButton("justify" + o.style.textAlign, "down", "Y");
	    	}
	    }else if(s=="LI"){
	        if(o.parentNode.nodeName.toUpperCase()=="OL") this.changeButton("insertorderedlist", "down", "Y");
	        else this.changeButton("insertunorderedlist", "down", "Y");
	    }

	},
	
	/* 레이어 버튼 모드 초기화 */
	initLayerButonStyle : function(s){
	    if(!WCEditorLib.hasValue(s)) s == "";
	    for (var i=0; i<this.config.toolbar.length; i++){
            for(var j=0; j<this.config.toolbar[i].length; j++){
                id = this.config.toolbar[i][j];
                if(this.button[id].functionNm=="openLayer" && s!=id) this.changeButton(id, "up");
            }
        }
	},
    
    /* 실행 버튼 모드 초기화 */
	initExecuteButonStyle : function(){
	    for (var i=0; i<this.config.toolbar.length; i++){
            for(var j=0; j<this.config.toolbar[i].length; j++){
                id = this.config.toolbar[i][j];
                
                if(WCEditor.button[id].mouseUp=="O") {
        	        if(id == "forecolor"){
        	            if(!($jq("#" + id + "Li").css("background-color")=="" || $jq("#" + id + "Li").css("background-color")=="#000000")) {
        	                $jq("#" + id + "Li").css("background-color", "#000000");
        	            }
        	        }else if(id == "backcolor"){
        	            if(!($jq("#" + id + "Li").css("background-color")=="" || $jq("#" + id + "Li").css("background-color")=="#ffffff")) {
        	               $jq("#" + id + "Li").css("background-color", "#ffffff");
        	            }
        	        }else if(id == "fontname"){
        	            if(!($jq("#fontnameSpan").html()=="" || $jq("#" + id + "Li").html()==WCEditorLib.getFirsArrayData(this.config.fontFamily))) {
        	                $jq("#fontnameSpan").html(WCEditorLib.getFirsArrayData(this.config.fontFamily));
        	            }
        	        }else if(id == "fontsize"){
        	            if(!($jq("#fontsizeA").html()=="" || $jq("#fontsizeA").html()==this.config.fontSize)) {
        	                $jq("#fontsizeA").html(this.config.fontSize);
        	            }
        	        }else {
        	            this.changeButton(id, "up");
        	        }
        	    }
            }
        }
	},
	
	/* 기 적용된 폰트 스타일 여부*/
	isAffectedFontStyle : function(s){
        if(this.affectedFontStyle.indexOf(s)==-1){
            this.affectedFontStyle += s;
            return false;
        }
        return true;            
    },
    
	/* 폰트 */
	setFont : function (m, o, e){
		
	    if(this.checkRange()){
	    	this.restoreRange();
	    	if(m=="fontname") {
                this.execute(m, o);
                $jq("#fontnameSpan").html(WCEditorLib.getFirsArrayData(o));
                this.changeFontAttr("face", "font-family", o);
            }else if(m=="fontsize"){
                this.execute(m, o);
                $jq("#fontsizeA").html(e);
	            this.changeFontAttr("size", "font-size", e);
            }else if(m=="forecolor"){
                this.execute(m, o);
                $jq("#forecolorLi").css("background-color", o);
	            this.changeFontAttr("color", "color", o);
            }else if(m=="backcolor"){
                this.execute(this.isIE?"backcolor":"hilitecolor", o);
                $jq("#backcolorLi").css("background-color", o);
            }

        }else{
            if(m=="fontname") {
                this.execute("inserthtml", "<font style='font-family:" + o + "'>&nbsp;</font>");
                $jq("#fontnameSpan").html(o);
            }else if(m=="fontsize"){
                this.execute("inserthtml", "<font style='font-size:" + e + "'>&nbsp;</font>");
                $jq("#fontsizeA").html(o);
            }else if(m=="forecolor"){
                this.execute("inserthtml", "<font style='color:" + o + "'>&nbsp;</font>");
                $jq("#forecolorLi").css("background-color", o);
            }else if(m=="backcolor"){
                this.execute("inserthtml", "<font style='background-color:" + o + "'>&nbsp;</font>");
                $jq("#backcolorLi").css("background-color", o);
            }
        }
        
        this.changeButton(m, "up");
	},
	
	/* font -> span */
	changeFontAttr : function(t, s, v) {
		$jq(WCEditor.editDoc).find("body font[" + t + "]").removeAttr(t).css(s, v);
		//$jq(WCEditor.editDoc).find("body font[" + t + "]").replaceWith("<span style=\"" + s + ":" + v + "\">" + $jq(this).html() + "</span>");
		WCEditor.restoreRange();
    },
	
    /* 팔레트 */
	printColorPlate : function (m, b){
	    if(typeof(b)=="undefined") b = false;
		var colortone = new Array(10);
			colortone[0] = new Array('#ffffff','#e5e4e4','#d9d8d8','#c0bdbd','#a7a4a4','#8e8a8b','#827e7f','#767173','#5c585a','#000000');
			colortone[1] = new Array('#fefcdf','#fef4c4','#feed9b','#fee573','#ffed43','#f6cc0b','#e0b800','#c9a601','#ad8e00','#8c7301');
			colortone[2] = new Array('#ffded3','#ffc4b0','#ff9d7d','#ff7a4e','#ff6600','#e95d00','#d15502','#ba4b01','#a44201','#8d3901');
			colortone[3] = new Array('#ffd2d0','#ffbab7','#fe9a95','#ff7a73','#ff483f','#fe2419','#f10b00','#d40a00','#940000','#6d201b');
			colortone[4] = new Array('#ffdaed','#ffb7dc','#ffa1d1','#ff84c3','#ff57ac','#fd1289','#ec0078','#d6006d','#bb005f','#9b014f');
			colortone[5] = new Array('#fcd6fe','#fbbcff','#f9a1fe','#f784fe','#f564fe','#f546ff','#f328ff','#d801e5','#c001cb','#8f0197');
			colortone[6] = new Array('#e2f0fe','#c7e2fe','#add5fe','#92c7fe','#6eb5ff','#48a2ff','#2690fe','#0162f4','#013add','#0021b0');
			colortone[7] = new Array('#d3fdff','#acfafd','#7cfaff','#4af7fe','#1de6fe','#01deff','#00cdec','#01b6de','#00a0c2','#0084a0');
			colortone[8] = new Array('#edffcf','#dffeaa','#d1fd88','#befa5a','#a8f32a','#8fd80a','#79c101','#3fa701','#307f00','#156200');
			colortone[9] = new Array('#d4c89f','#daad88','#c49578','#c2877e','#ac8295','#c0a5c4','#969ac2','#92b7d7','#80adaf','#9ca53b');
		var str = "";
		
		var w = b?14:22;
		var h = b?10:12;
               
		str += "<table cellpadding='0' cellspacing='0' border=0 style='border:solid 1px #bbcecc;margin-left:1px;' bgcolor='#ffffff'>";
	    if(m=='table') str += "<tr height='10' bgcolor='#333333' style='color:white;font-size:9px;'><td colspan='2' align=center>plate</td><td colspan='8' align='right'><span style='cursor:pointer;font-weight:bold' onclick='closePlate();'>x</span>&nbsp;</td></tr>";
		for (var i=0; i<10; i++){
			str += "<tr>";
			for(var j=9; j>=0; j--){
				str += "<td>";
				str += "<table cellpadding='0' cellspacing='0' border='0' style='border-spacing:0px;'><tr>";
				str += "<td width='" + w + "' height='" + (h+2) + "' bgcolor='" + colortone[j][i] + "' style='border:1px solid #ffffff' onmouseover=\"this.style.borderColor='#888888'\" onmouseout=\"this.style.borderColor='#ffffff'\"><a href='#' onclick=\"";
				if(m=="hr")      str	+= "setHr('" + colortone[j][i] + "');return false;\">";
			    else if(m=="table") str	+= "setTableColor('" + colortone[j][i] + "');return false;\">";
				else            str	+= "parent.WCEditor.setFont('" + m + "', '" + colortone[j][i] + "');return false;\">";
				str += "<img src='" + this.imageRoot + "blank.gif' border='0' width='" + (w-2) + "' height='" + h + "' align='absmiddle'></a>";
				str += "</td></tr></table></td>";
			}   
			str += "</tr>";
		}       
		
		return (str+"</table>");
	},
	
	/* 리사이즈 */
	resizeStart : function(e){
    	if(WCEditor.resizeY==0) WCEditor.resizeY=e.clientY;
    	$jq(document)
        .bind("mousemove", function(event) {
            $jq("#editIframe").hide();
            $jq("#resizeDiv").show();
            WCEditor.resizeMove(event);
        })
        .bind("mouseup", function(event) {
            WCEditor.resizeStop();
            $jq("#editIframe").show();
            $jq("#resizeDiv").hide();
        });
    	
	},
	
	resizeMove : function(e){
        var _h = e.clientY + $jq(document).scrollTop() - WCEditor.resizeY + parseInt(WCEditor.config.height);
		if(_h<parseInt(parseInt(WCEditor.config.minResizHeight))) return;
		$jq("#editDiv").css("height", _h + "px");
		console.log("resizeMove");
	},
	
	resizeStop : function (){
		$jq(document).unbind("mousemove").unbind("mouseup");
		console.log("resizeStop");
	}
};   


var WCEditorLib = {
    
	/* 매핑 */
	setParamValue : function (m, p) {
        var i = 0, n = 0;
        if(!this.hasValue(p))return m;
        
        while( (i = m.indexOf("@", i)) != -1) {
       		if(p[n] == null) p[n] = "";
            m = m.substr(0, i) + String(p[n]) + m.substring(i + 1);
            i = i + String(p[n++]).length;
      	}
        return m;
    },
    
    /* 문자추출 */
    getLastValue : function(s, p){
        return s.indexOf(p)!=-1?s.substring(s.lastIndexOf(p)+1):"";
    },
    
    /* 배열의 첫번째 데이터 */
    getFirsArrayData : function(s, p){
        if(!this.hasValue(p)) p = ",";
        var a = s.split(p);
        return $jq.trim(a[0]);
    },
    
    /* Data 추출 */
    getArrayData : function(){
        return (WCEditor.data[arguments[0]]).split(";");
    },
    
    /* 키 포함여부 */
    hasDataInKey : function(s, k){
        var a = WCEditorLib.getArrayData(s);
        for (var i = 0; i < a.length; i++) {if (String(k)==a[i]) return true;}
        return false;
    },
    
    /* 파라메터 체크 */
	hasValue : function(v) {
        return (typeof(v) == "undefined")?false:true;
    },
  
    /* up,down */
    updown : function(o, p, v){
        var maxLen = {'col' : 20, 'row' : 20, 'border' : 10, 'hrheight' : 10};
        var m = maxLen[o];
        var r = 0;
        v = parseInt(v);
        if(v==1 && p=='+')
            r = v+1;
        else if(v==m && p=='-')
            r = v-1;
        else if(v>1 && v<m) 
            r = p=='+'?v+1:v-1; 
        else
            r = v;
            
        return r;
    },
    
    resizeIfrm : function(o){
        var obj = document.getElementById(o);
        var objDocument = obj.contentWindow.document; 
        if(objDocument.height){
            obj.style.height = objDocument.height + "px";
        } else {
            obj.style.height = objDocument.body.scrollHeight + "px";
        }
    },
    
    /* 파일확장자 체크 */
    existExt : function(t, p) {
  	    var fileExt = {	"IMAGE"  : "gif,jpg,jpeg", 
  	    				"EXCEPT" : "java,jsp,do,cgi,js" 
  	    			  };
  	    			  
  	    var s   = fileExt[p];
  	    var e	= this.getLastValue(t, ".").toLowerCase();

		var a   = s.split(",");
        for(var i=0; i <= a.length ;i++) if(e==a[i]) return true;        	
  	    return false;
    }
};