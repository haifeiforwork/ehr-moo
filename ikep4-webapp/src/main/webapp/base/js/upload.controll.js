(function($) {
	var langUpload = iKEPLang.uploadControll;
	var langPLUpload = langUpload.plupload;
	
	plupload.addI18n({
		'Select files' : langPLUpload.caption,
		'Add files to the upload queue and click the start button.' : langPLUpload.description,
		'Filename' : langPLUpload.lblFileName,
		'Status' : langPLUpload.lblStatus,
		'Size' : langPLUpload.lblSize,
		'Add files' : langPLUpload.btnAddFiles,
		'Start upload': langPLUpload.btnStartUpload,
		'Uploaded %d/%d files' : langPLUpload.noticeUploading,
		'Drag files here.' : langPLUpload.dragComment,
		'Error: File to large: ' : langPLUpload.errSize,
		'Error: Invalid file extension: ': langPLUpload.errExtension/*,
		'N/A' : "",
		'File extension error.' :  "",
		'File size error.' : "",
		'Init error.' : langPLUpload.errInit,
		'HTTP Error.' : langPLUpload.errHttp,
		'Security error.' : langPLUpload.errSecurity,
		'Generic error.' : "",
		'IO error.' : "",
		'Stop Upload' : ""*/
		//'Stop current upload' : langPLUpload.btnStoUploadp,
		//'Start uploading queue' : langPLUpload.btnStartUpload
		, 'Max file size : %w' : langUpload.errMaxFileSize
	});
	
	
	iKEP.FileController = function(form, container, options) {
		var config = iKEP.config.uploader;
		
		var uploader = null;
		var $form = $(form);
		var $container = $(container);
		var $divUploader = null;
		var $divUploadContainer = null;
		var $divUploadedFiles = null;
		
		var activeDuration = 600;
		var jsessionid = jQuery.cookie("JSESSIONID");
		var contextRoot = iKEP.getContextRoot();
		
		var uploadResult = [];	//itemFormat : {fileId:fileId, name:fileRealName, size:0, type:fileExtension, flag:"add/del/use"}
		var callbackUploadComplete = null;
		
		var isActiveControll = false;
		
		var defaultAllowFileType = ["all"];//["txt", "img", "comp", "doc"];
		//var defaultAllowExt = ["jpg,gif,png,txt,zip"];
		var filterings = [
		    {type:"all", title:langUpload.filter.all, extensions:"asc,txt,text,diff,log,htm,html,xhtml,rtf,xml,vcf,csv,bmp,gif,jpeg,jpg,png,tiff,tif,zip,alz,rar,jar,tar,bat,doc,dot,pdf,pgp,ps,ai,eps,rtf,xls,xlb,ppt,pps,pot,docx,pptx,xlsx,hwp,wav,wma,mpga,mpega,mp2,mp3,m4a,avi,mkv,mpeg,mpg,mpe,qt,mov,mp4,m4v,flv,rv,swf,swfl,com,exe"},
			{type:"txt", title:langUpload.filter.txt, extensions:"asc,txt,text,diff,log,htm,html,xhtml,rtf,xml,vcf,csv"},	// text
			{type:"img", title:langUpload.filter.img, extensions:"bmp,gif,jpeg,jpg,png,tiff,tif"},	//images
			{type:"comp", title:langUpload.filter.comp, extensions:"zip,alz,rar,jar,tar,bat"},	//compress
			{type:"doc", title:langUpload.filter.doc, extensions:"doc,dot,pdf,pgp,ps,ai,eps,rtf,xls,xlb,ppt,pps,pot,docx,pptx,xlsx,hwp"},	// document
			{type:"ado", title:langUpload.filter.mp, extensions:"wav,wma,mpga,mpega,mp2,mp3,m4a"},		//audio
			{type:"vod", title:langUpload.filter.vod, extensions:"avi,mkv,mpeg,mpg,mpe,qt,mov,mp4,m4v,flv,rv,swf,swfl"},	//video
			{type:"app", title:langUpload.filter.app, extensions:"com,exe"}	// application
			//{type:"all", title:langUpload.filter.all, extensions:"*"}	// all
		];

		options.initActive = options["initActive"] === false ? false : true;
		options.isUpdate = options["isUpdate"] === false ? false : true;
		options.uploadUrl = options["uploadUrl"] || contextRoot + this.uploadUrl + ";jsessionid=" + (jsessionid != null ? ""+jsessionid : "");
		options.downloadUrl = options["downloadUrl"] || contextRoot + this.downloadUrl;
		options.maxTotalSize = options["maxTotalSize"] || config.maxTotalFileSize;
		options.maxFileSize = config.maxFileSize;//options["maxFileSize"] || options.maxTotalSize; 정책상 화면에서 설정을 허용하지 않음.
		
		
		if(!options["allowFileType"] && !options["allowExt"]) options["allowFileType"] = defaultAllowFileType;
		
		function getExtFilter() {
			var filter = [];
			var loop = filterings.length;
			
			if(options["allowExt"]) {
				filter.push({title:langUpload.selectFiles + " ( " + options["allowExt"].replaceAll(",", ", ") + " )", extensions:options.allowExt});
			}
			
			if(options["allowFileType"]) {
				if(typeof(options.allowFileType) == "string") options.allowFileType = options.allowFileType.split(",");
				$.each(options.allowFileType, function() {
					var type = this.toString();
					if(type == "allowAll") {
						if(options["allowExt"]) filter.length = 1;
						else filter = [];
						
						$.each(filterings, function() {
							if(this.type != "all") this.title += " ( " + this.extensions.replaceAll(",", ", ") + " )";
							filter.push(this);
						});
						return false;
					}

					for(var i=0; i<loop; i++) {
						if(type == filterings[i].type) {
							var filterItem = filterings[i];
							if(filterItem.type != "all") filterItem.title += " (" + filterItem.extensions.replaceAll(",", ", ") + ")";
							filter.push(filterItem);
							break;
						}
					}
				});
			}
			
			return filter;
		}
		
		function resizeFileList($listContainer) {
			var fileListWidth = $listContainer.innerWidth(),
				otherWidth = 0;
		
			// CSS control
			$.each(document.styleSheets, function(idx, css) {
				if(css.href.indexOf("common.css") >= 0) {
					if(css["insertRule"]) {
						$.each(css.cssRules, function(idx, rule) {
							switch(rule.selectorText) {
								case ".plupload_file_action" : otherWidth += parseInt(rule.style.width, 10) + parseInt(rule.style.marginLeft, 10); break;
								case ".plupload_file_size, .plupload_file_status, .plupload_progress" : otherWidth += parseInt(rule.style.width, 10)*2; break;
							}
						});
						otherWidth += 33;
						css.insertRule(".plupload_filelist .plupload_file_name {width:" + (fileListWidth-otherWidth) + "px}", css.cssRules.length);
					} else {
						$.each(css.rules, function(idx, rule) {
							switch(rule.selectorText) {
								case ".plupload_file_size" : if(rule.style.width) otherWidth += parseInt(rule.style.width, 10); break;
								case ".plupload_file_status" : if(rule.style.width) otherWidth += parseInt(rule.style.width, 10); break;
								case ".plupload_file_action" : 
									if(rule.style.width) otherWidth += parseInt(rule.style.width, 10);
									if(rule.style.marginLeft) otherWidth += parseInt(rule.style.marginLeft, 10);
									break;
							}
						});
						otherWidth += 33;
						css.addRule(".plupload_filelist .plupload_file_name", "width:" + (fileListWidth-otherWidth) + "px");
					}
					return false;
				}
			});
			
			$("ul>li", "div.fileAttach").each(function(idx, li) {
				var $li = $(li);
				var $left = $li.children().eq(0), $right = $li.children().eq(1);
				var width = $li.innerWidth() - $right.outerWidth() - 30;
				$left.css({overflow:"hidden", textOverflow:"ellipsis", whiteSpace:"nowrap", width:width});
			});
		}
		
		function initial() {
			if(options.isUpdate == true) {
				/* drag & drop support comment ----------------
				* browserplus : IE9, chrome10, safari5 not support.
				* greas : IE, chrome supported - frame layout not support.
				* html5 : all browser not support.
				* ---------------------------------------------*/
				var activeImages = {
					open : {img:contextRoot+"/base/images/icon/ic_fileup_closed.gif", alt:langUpload.altRollup},
					close : {img:contextRoot+"/base/images/icon/ic_fileup_opened.gif", alt:langUpload.altRolldown}
				};
				$container.html('<div class="fileup">' +
					'<div class="fileup_t">' +
						'<h3>' + langUpload.lblUploadFile + '</h3>' +
						'<div class="btn"><a href="#a"><img src="' + (options.initActive ? activeImages.close.img : activeImages.open.img) + '" ' +
							'alt="' + (options.initActive ? activeImages.close.alt : activeImages.open.alt) + '" ' +
							'title="' + (options.initActive ? activeImages.close.alt : activeImages.open.alt) + '"/></a></div>' +
					'</div>' +
					'<div class="fileup_c">' +
						'<div class="fileAttach"></div>' +	// uploaded files setting area
						'<div><p>You browser doesn\'t have BrowserPlus, Flash support.</p></div>' +	// upload controll ui area
					'</div>' +
				'</div>');
				
				$divUploader = $("div.fileup_c", $container);
				$divUploadContainer = $divUploader.children("div").eq(1);
				setUploadedFiles();	// uploaded file setting
				
				$("div.btn > a", $container).click(function(event) {
					var $img = $(this).children("img");
					switch($img.attr("alt")) {
						case activeImages.open.alt :
							$divUploader.show();
							$img.attr("src", activeImages.close.img);
							$img.attr("alt", activeImages.close.alt);
							$img.attr("title", activeImages.close.alt);
							break;
						case activeImages.close.alt :
							$divUploader.hide();
							$img.attr("src", activeImages.open.img);
							$img.attr("alt", activeImages.open.alt);
							$img.attr("title", activeImages.open.alt);
							break;
					}
					
					iKEP.iFrameContentResize();
				});
				
				options.initActive ? $divUploader.show() : $divUploader.hide();

				$divUploadContainer.pluploadQueue({
					runtimes : ($.browser["safari"] === true || $.browser["mozilla"] === true) ? "gears,flash" : "browserplus,flash",
			        url : options.uploadUrl,
			        max_file_size : options.maxFileSize < options.maxTotalSize ? options.maxFileSize : options.maxTotalSize,
			        //chunk_size : '1mb',
			        unique_names : false,
			        //resize : {width : 320, height : 240, quality : 90},
			        filters : options["filter"] || getExtFilter(),
			        flash_swf_url : contextRoot+"/base/js/plupload/plupload.flash.swf",
			        preinit : {
			        	Init : function() {
			        		isActiveControll = true;
			        		uploader = $divUploadContainer.pluploadQueue();
			        		
			        		resizeFileList($("#"+uploader.settings.container+"_filelist"));
			        		$(window).resize(function() { resizeFileList($("#"+uploader.settings.container+"_filelist")); });
			        		
			        		$("div.plupload_header", $divUploadContainer).remove();
			        		$("a.plupload_start", $divUploadContainer).remove();
			        		
			        		$("div.plupload_filelist_footer", $divUploadContainer).children("div.plupload_file_size")
			        			.append(' / <span><b>' + plupload.formatSize(options.maxTotalSize) + '</b></span>')
			        			.width(120);
			        		
			        		updateTotalUploadSize();
			        		
			        		if(options.initActive) initialComplete();
			        	}
			        },
			        init : {
			        	FilesAdded : function(up, files) {
			        		checkZeroFile(files);
			        		
			        		setTimeout(updateTotalUploadSize, 100);
			        	},
			        	FilesRemoved : function(up, files) {
			        		setTimeout(updateTotalUploadSize, 100);
			        	},
			        	FileUploaded : function(up, file, xhr) {
			        		var $fileItem = $("#" + file.id);
			        		var itemHeight = $fileItem.height();
			        		var posTop = $fileItem.position().top + itemHeight;
			        		if(up.files[up.files.length-1].id != file.id) $fileItem.parent().scrollTop(posTop);

			        		
			        		var json = $.parseJSON(xhr.response);	// format : {fileId, fileRealName, filesize, fileExtension}
			        		if(json["fileId"]) {
				        		uploadResult.push({
				        			fileId:json.fileId,
				        			name:json.fileRealName,
				        			size:Number(json.fileSize),
				        			type:json.fileExtension,
				        			flag:"add",
				        			path : json.filePath	// with profile image upload
				        		});
			        		} else {
			        			throw new Exception("upload error");
			        		}
			        	},
			        	UploadComplete : function() {
							var result = false;
			        		if (uploader.total.uploaded == uploader.files.length) result = true;
							
			        		uploadResult = uploadResult.concat(getBeforeUploadedFiles());

			        		setUpdateFiles(uploadResult);
							callbackUploadComplete(result, uploadResult);
						},
			        	Error : function(up, error) {
			        		switch(error.code) {
			        			case plupload.GENERIC_ERROR : break; 
			        			case plupload.HTTP_ERROR : break;
			        			case plupload.INIT_ERROR : /*support comment randering*/ break;
			        			case plupload.IO_ERROR : break;
			        			case plupload.SECURITY_ERROR : break;
			        		}
			        	}
			        }
			    });
			} else {
				$divUploader = $container;
				setUploadedFilesView();
			}
		}
		
		function getBeforeUploadedFiles() {
			var files = [];
			if($divUploadedFiles) {
        		var $uploadedFiles = $divUploadedFiles.find("li");
        		if($uploadedFiles.length > 0) {
        			$uploadedFiles.each(function() {
        				var fileInfo = $.data(this, "fileInfo");
        				var flag = ($("input[type=checkbox]:checked", this).length > 0) ? "del" : "use";
        				
        				files.push({fileId:fileInfo.fileId, name:fileInfo.fileRealName, size:fileInfo.fileSize, type:fileInfo.fileExtension, flag:flag});
        			});
        		}
    		}
			return files;
		}
		
		function initialComplete() {
			options["onLoad"] && options["onLoad"]();
			
			try {	// iframe content area resize
        		var docIframe = jQuery(parent.document).find("iframe[name=contentIframe]");
        		if(docIframe.length > 0) iKEP.iFrameResize(docIframe);
    		} catch(e) {}
		}
		
		function setUploadedFiles() {	// 수정 화면에서의 업로드된 파일 목록 생성
			if(options && options["files"]) {
				if(options.files.length > 0) {
					var tpl = $.template(null, '<li>' +
							'<div class="floatLeft">' + 
								'<img alt="${fileExtension}" src="' + contextRoot + '/base/images/icon/ic_file_${fileExtension}.gif"  onerror="iKEP.FileController.setEtcFileIcon(this)" />&nbsp;' + 
								'<a href="${downloadUrl}">${fileRealName} </a>' +
							'</div>' +
							(options.isUpdate ? '<div class="floatRight"><label><input type="checkbox" title="' + langUpload.titDelete + '" class="checkbox"> ' + langUpload.btnDelete + '</label></div>' : '') +
						'</li>');
					
					$divUploadedFiles = $('<ul>').prependTo($divUploader.children("div").eq(0));
					$.each(options.files, function() {
						this.fileExtension = this.fileExtension.toLowerCase();
						this["downloadUrl"] = options.downloadUrl + (options.downloadUrl.indexOf("?") > -1 ? "&" : "?") + "fileId=" + this.fileId;
						this.lblSize = plupload.formatSize(this.fileSize);
						var $li = $.tmpl(tpl, this).appendTo($divUploadedFiles);
						$li.data("fileInfo", this);
					});
					
					// IE6, 7에서 controller가 안보이는 현상때문에 추가
					$('<div class="clear"/>').appendTo($divUploader.children("div").eq(0));
					
					$($divUploadedFiles).click(function(event) {
						var el = event.target;
						if(el.tagName.toLowerCase() == "input" && el.type.toLowerCase() == "checkbox") {
							updateTotalUploadSize();
							
							if(el.checked) $(el).parents("li:first").addClass("through");
							else $(el).parents("li:first").removeClass("through");
						}
					});
				}
			}
			
			if(!options.initActive) initialComplete();
		}
		
		function setUploadedFilesView() {	// 조회 화면에서 업로드된 파일 목록 생성
			if(options && options["files"]) {
				if(options.files.length > 0) {
					//var initActive = false;
					var initActive = true;
					var activeImages = {
						open : {img:contextRoot+"/base/images/icon/ic_ar_down.gif", alt:langUpload.altRollup},
						close : {img:contextRoot+"/base/images/icon/ic_ar_up.gif", alt:langUpload.altRolldown}
					};
					
					$divUploadedFiles = $('<ul>').prependTo($divUploader).wrap('<div class="filedown"/>');
					$('<div class="filedown_ic"><a href="#a">' + langUpload.lblAttachFile + ' <span class="colorPoint">(' + options.files.length + ')</span> ' +
						'<img src="' + (initActive ? activeImages.close.img : activeImages.open.img) + '" ' +
							'alt="' + (initActive ? activeImages.close.alt : activeImages.open.alt) + '" ' +
							'title="' + (initActive ? activeImages.close.alt : activeImages.open.alt) + '" /></a>' +
					'</div>')
						.prependTo($divUploader)
						.find("a").click(function() {
							var $img = $(this).children("img");
							switch($img.attr("alt")) {
								case activeImages.open.alt :
									$divUploadedFiles.parent().show();
									$img.attr("src", activeImages.close.img);
									$img.attr("alt", activeImages.close.alt);
									$img.attr("title", activeImages.close.alt);
									break;
								case activeImages.close.alt :
									$divUploadedFiles.parent().hide();
									$img.attr("src", activeImages.open.img);
									$img.attr("alt", activeImages.open.alt);
									$img.attr("title", activeImages.open.alt);
									break;
							}
							
							iKEP.iFrameContentResize();
						});
					initActive ? $divUploadedFiles.parent().show() : $divUploadedFiles.parent().hide();
					
					var tpl = $.template(null, '<li><img alt="${fileExtension}" src="' + contextRoot + '/base/images/icon/ic_file_${fileExtension}.gif" onerror="iKEP.FileController.setEtcFileIcon(this)" />&nbsp;<a href="${downloadUrl}" title="${fileRealName}">${fileRealName}</a></li>');
					$.each(options.files, function() {
						this.fileExtension = this.fileExtension.toLowerCase();
						this["downloadUrl"] = options.downloadUrl + (options.downloadUrl.indexOf("?") > -1 ? "&" : "?") + "fileId=" + this.fileId;
						this.lblSize = plupload.formatSize(this.fileSize);
						var $li = $.tmpl(tpl, this).appendTo($divUploadedFiles);
					});
				}
			}
			
			initialComplete();
		}
		
		function setUpdateFiles(files) {	// 폼에 파일 관련 정보 셋팅
			$form.find("input[name^=fileLinkList]").remove();
			
			if(files.length > 0 && $form) {
				var tplFileId = $.template(null, '<input type="hidden" name="fileLinkList[${index}].fileId" value="${fileId}"/>');
				var tplFileFlag = $.template(null, '<input type="hidden" name="fileLinkList[${index}].flag" value="${flag}"/>');
				var tplFileSize = $.template(null, '<input type="hidden" name="fileLinkList[${index}].fileSize" value="${size}"/>');
				
				$.each(files, function(index) {
					var fileInfo = $.extend({index:index}, this);
					$form.append($.tmpl(tplFileId, fileInfo));
					$form.append($.tmpl(tplFileFlag, fileInfo));
					$form.append($.tmpl(tplFileSize, fileInfo));
				});
			}
			
			return files;
		}
		
		function checkZeroFile(files) {
			var result = false;
			$.each(files, function() {
    			if(this.size <= 0) {
    				result = true;
    				alert(langUpload.errZeroSize);

    				return false;
    			}
    		});
			
			return result;
		}
		
		function getTotalUploadSize() {
			var totSize = 0;
			if($divUploadedFiles) {
				var deleteFiles = [];	// delete file detect
				$("input[type=checkbox]:checked", $divUploadedFiles).each(function() {
					deleteFiles.push($(this).parents("li:first").data("fileInfo").fileId);
				});
	
				if(options && options["files"]) {
					$.each(options.files, function() {
						if(deleteFiles.indexOf(this.fileId) == -1) totSize += this.fileSize;	// uploaded files format
					});
				}
			}
			
			$.each(uploader.files, function() { totSize += this.size; });	// plupload controll format

			return totSize;
		}
		
		function updateTotalUploadSize() {
			if(!$divUploadedFiles) return false;
			
			var size = getTotalUploadSize();
			$("span.plupload_total_file_size", $divUploadContainer).html(plupload.formatSize(size));
		}
		
		this.getPLUploader = function() {
			return uploader;
		};
		
		this.getFiles = function() {
			//return to array<File>
			// File : loaded, name, percent, size, status(Queued, Uploading, Failded, Done)
			return uploader.files;
		};
		
		this.getUploadFileCount = function() {
			/*
			 * uploader.total
			 * - queued
			 * - size
			 * - uploaded
			 */
			return uploader.total.queued;
		};
		
		this.getUploadState = function() {
			var state = -1;
			switch(true) {
				case uploader.files.length == 0: state = 0; break;	// initial
				case uploader.total.uploaded == 0 : state = 1; break; // ready
			}
			return state;
		};
		
		this.upload = function(callback) {
			callbackUploadComplete = callback;
			
			if(!isActiveControll) {	// 폼 저장시 파일 파일 컨트롤러가 활성화 된적이 없으면 변경된 내역이 없는 것으로 간주 
				var uploadedFiles = getBeforeUploadedFiles();
				setUpdateFiles(uploadedFiles);
				
				callbackUploadComplete(true, uploadedFiles);
				return;
			}
			
			if(uploader.state == plupload.STARTED) return false;
			
			if(checkZeroFile(uploader.files)) {
				callbackUploadComplete(false, []);
				return false;
			}
			
			if(options.maxTotalSize < getTotalUploadSize()) {
				alert(langUpload.errTotalSizeOver.replace(/%w/, plupload.formatSize(options.maxTotalSize)));
				callbackUploadComplete(false, []);
				return false;
			}
			
			var readyCnt = 0, failCnt = 0;
			$.each(uploader.files, function() {
				switch(this.status) {
					case plupload.QUEUED : readyCnt++; break;
					case plupload.FAILED : failCnt++; break;
				}
			});
			
			if (failCnt == 0 && readyCnt > 0) {	// upload progress percent
				//scroll top
				$("#" + uploader.settings.container + "_filelist").scrollTop(0);
				
				uploader.start();
			} else {
				if(failCnt > 0) {
					callbackUploadComplete(false, []);
				} else {
					uploader.trigger("UploadComplete");
				}
			}
		};
		
		this.cancel = function() {
			uploader.stop();
			uploader.refresh();
		};
		
		this.destroy = function() {
			uploader.trigger("Destroy");
			$container.empty();
		};
		
		initial();
	};
	
	iKEP.FileController.prototype.uploadUrl = iKEP.config.uploader.uploadUrl;
	iKEP.FileController.prototype.downloadUrl = iKEP.config.uploader.downloadUrl;
	
	iKEP.FileController.setEtcFileIcon = function(img) {
		jQuery(img).attr("src", iKEP.getContextRoot() + "/base/images/icon/ic_file_etc.gif");
	};
	
	iKEP.FileController.setFileList = function(container, files, downloadUrl) {
		if(files && files.length > 0) {
			var contextRoot = iKEP.getContextRoot();
			downloadUrl = downloadUrl || (contextRoot+iKEP.FileController.prototype.downloadUrl);
			
			$ulContainer = $('<ul class="fileAttachmentList">').appendTo(container);
			var tpl = $.template(null, '<li><img class="icon" alt="${fileExtension}" src="' + contextRoot + '/base/images/icon/ic_file_${fileExtension}.gif" onerror="iKEP.FileController.setEtcFileIcon(this)" />&nbsp;<a href="${downloadUrl}">${fileRealName} </a></li>');
			$.each(files, function() {
				this.fileExtension = this.fileExtension.toLowerCase();
				this["downloadUrl"] = downloadUrl + (downloadUrl.indexOf("?") > -1 ? "&" : "?") + "fileId=" + this.fileId;
				this.lblSize = plupload.formatSize(this.fileSize);
				$.tmpl(tpl, this).appendTo($ulContainer);
			});
			
			iKEP.iFrameContentResize();
		} else {
			$('<span class="file_noAttachment">' + langUpload.lblNoAttachment + '</span>').appendTo(container);
		}
			
	};
})(jQuery);