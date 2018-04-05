// jQuery alias setting
var $jq = jQuery.noConflict();

var orgCallback = "";
 
//--	iKEP namespace setting	--------------------------------------------------------------
var iKEP = new (function() {
	var _contextRoot = window["contextRoot"] || "";
	var _webAppPath = null;
	var _loadTime = null;
	var __setLoadTime = null;
	
	this.getContextRoot = function () { return _contextRoot; };
	this.getWebAppPath = function() {
		if(_webAppPath == null) {
			_webAppPath = location.protocol + "//" + location.hostname +
				((!location.port || location.port == "80") ? "" : ":" + location.port) +
				this.getContextRoot();
		}
		return _webAppPath;
	};
	
	this.setLoadTime = function(millisecond) {
		if(top == window) {
			_loadTime = new Date(millisecond);
			__setLoadTime = new Date();
		} else { top.iKEP.setLoadTime(millisecond); }
	};
	
	this.getCurTime = function() {	// server side sync time
		if(top == window) {
			if(_loadTime != null) {
				var now = new Date();
				var passing = now - __setLoadTime;
				
				return new Date(_loadTime.getTime() + passing);
			} else { return new Date(); }
		} else { return top.iKEP.getCurTime(); }
	};
	
	this.urlAddressBookPopup = _contextRoot + "/support/popup/addresbookPopup.do";
	this.urlAddressBookSurveyPopup = _contextRoot + "/support/popup/addresbookSurveyPopup.do";
	this.urlAddressBookMailPopup = _contextRoot + "/support/popup/addresbookMailPopup.do";
	this.urlAddressBookReaderPopup = _contextRoot + "/support/popup/addresbookReaderPopup.do";
	this.popupInterfacePool = [];
})();

// solution configration
jQuery.extend(iKEP, {
	config : {
		uploader: {
			maxFileSize : 50*1024*1024,
			maxTotalFileSize: 50*1024*1024,
			uploadUrl : "/support/fileupload/uploadFileForFlash.do",
			downloadUrl : "/support/fileupload/downloadFile.do"
		},
		jstree : {isUserPriority : true}
	},
	template : {
		userOption : jQuery.template ? jQuery.template(null, '<option value="${id}">${userName} ${jobTitleName} ${teamName}</option>') : null,
		userQnaOption : jQuery.template ? jQuery.template(null, '<option value="${id}/${userName}/${mobile}/${email}">${userName} ${jobTitleName} ${teamName}</option>') : null,
		userEmailOption : jQuery.template ? jQuery.template(null, '<option value="${addrType}:${userName}:${email}:${id}">${addrType} : ${userName} ${jobTitleName} ${teamName} (${email})</option>') : null,
		userBoardOption : jQuery.template ? jQuery.template(null, '<option value="${id}" id="${id}">${userName} ${jobTitleName} ${teamName}</option>') : null,
		userWorkspaceBoardOption : jQuery.template ? jQuery.template(null, '<option value="${id}" id="${type}^${id}">${userName} ${jobTitleName} ${teamName}</option>') : null,
		userMessageOption : jQuery.template ? jQuery.template(null, '<option id="${id}" value="${id}/${userName}/user" >${userName} ${jobTitleName} ${teamName}</option>') : null,
		groupOption : jQuery.template ? jQuery.template(null, '<option value="${code}">${name}</option>') : null,
		groupNameOption : jQuery.template ? jQuery.template(null, '<option value="${groupId}" id="${groupId}">${groupName}</option>') : null,
		groupBoardOption : jQuery.template ? jQuery.template(null, '<option value="${code}" id="${code}^${name}">${name}</option>') : null,
		higroupBoardOption : jQuery.template ? jQuery.template(null, '<option value="${code}" id="${code}^${name}" class="hierarchied">[H]${name}</option>') : null,				
		groupWorkspaceBoardOption : jQuery.template ? jQuery.template(null, '<option value="${code}" id="${type}^${code}">${name}</option>') : null,
		groupMessageOption : jQuery.template ? jQuery.template(null, '<option id="${code}" value="${code}/${name}/group" >${name}</option>') : null
	}
});

iKEP.debug = function(obj) {
	try {
		console.log(obj);
	} catch(e) {
		if(typeof obj == "object") {
			var msg = "";
			for(key in obj) {
				msg += key + " = " + obj[key] + "\n";
			}
			alert(msg);
		} else {
			alert(obj);
		}
	}
};

iKEP.zerofill = function(val, len, direction, fillStr) {
	var result = "";
	switch(val) {
		case null :
		case undefined : val = ""; break;
		default :
			switch(typeof(val)) {
				case "object" : val = val.toString();
				case "number" : val = val + "";
			}
	}

	if(val.length > len) result = val.substring(0, len);
	else if(val.length == len) result = val;
	else {
		result = val;
		var ch = fillStr || "0",
			loop = len - val.length;
		for(var i=0; i<loop; i++) result = direction == "right" ? result + ch : ch + result;
	}
	
	return result;
};

iKEP.dialogOpen = function(url, options, argument) {
	options = jQuery.extend({width:200, height:200, top:undefined, left:undefined, resizable:true, scrollbar:true, modal:true, argument:undefined, callback:null}, options);
	options.resizable = options.resizable === true ? "yes" : "no";
	options.scrollbar = options.scrollbar === true ? "yes" : "no";
	
	var dialogUrl = this.getContextRoot() + "/support/dialog/dialog.do";
	var isModal = options["modal"] && true;

	var features = "dialogWidth:" + options.width + "px; dialogHeight:" + options.height + "px;";
	if(options.left == undefined && options.top == undefined) {
		if(jQuery.browser["msie"]) features += " center:yes;";
		else {
			features += " dialogLeft:" + (Math.floor((screen.availWidth - options.width) / 2) + screen.availLeft) + "px;";
			features += " dialogTop:" + (Math.floor((screen.availHeight - options.height) / 2) + screen.availTop) + "px;";
		}
	} else {
		if(!isNaN(options.left)) features += " dialogLeft:" + options.left + "px;";
		else features += " dialogLeft:" + (Math.floor((screen.availWidth - options.width) / 2) + screen.availLeft) + "px;";	// IE의 경우 사이즈 계산을 못하나 NaN으로 입력시 무시하고 센터로 보내짐.
		
		if(!isNaN(options.top)) features += " dialogTop:" + options.top + "px;";
		else features += " dialogTop:" + (Math.floor((screen.availHeight - options.height) / 2) + screen.availTop) + "px;";
	}
	features += " resizable:" + options.resizable + ";";
	features += " scroll:" + options.scrollbar + ";";
	
	var arguments = {url:url, parent:window, argument:argument||options.argument};
	if(options.callback) {
		var funcName = "fn_" + (new Date()).getTime();
		arguments["callback"] = funcName;
		window[funcName] = options.callback;
	}

	var result = null;
	if(options.modal == true) result = window.showModalDialog(dialogUrl, arguments, features);
	else {
		if(window.showModelessDialog) result = window.showModelessDialog(dialogUrl, arguments, features);	// IE only
		else {
			options.argument = argument;
			iKEP.popupOpen(arguments.url, options);
		}
	}

	return result;
};

iKEP.portletPopupOpen = function(url, options, windowName) {
	options = jQuery.extend({width:200, height:200, top:undefined, left:undefined, resizable:true, windowTitle:undefined, documentTitle:undefined, argument:null, callback:null}, options||{});
	
	var features = "location=no,menubar=no,scrollbars=no,status=no,titlebar=no,toolbar=no";
	features += ",width=" + (options.width||200) + ", height=" + (options.height||200);
	features += ",left=" + (!isNaN(options.left) ? options.left : ((Math.floor((screen.availWidth - options.width) / 2) + (jQuery.browser["msie"] ? 0 : screen.availLeft))));// IE의 경우 사이즈 계산을 못하나 NaN으로 입력시 무시하고 센터로 보내짐.
	features += ",top=" + (!isNaN(options.top) ? options.top : ((Math.floor((screen.availHeight - options.height) / 2) + (jQuery.browser["msie"] ? 0 : screen.availTop))));
	features += ",resizable=" + (options.resizable === true ? "yes" : "no");
	
	windowName = windowName || "_blank";
	
	var popupUrl = this.getContextRoot() + "/support/popup/popupPortlet.do?poolIdx=" + this.popupInterfacePool.length;
	var interface = {
		url:url,
		windowTitle : options.windowTitle,
		documentTitle : options.documentTitle,
		argument:(jQuery.browser["msie"] ? JSON.stringify(options.argument) : options.argument) || null,
		callback : null
	};
	if(options.callback) {
		interface.callback = function(result) {
			if(jQuery.browser["msie"]) result = jQuery.parseJSON(result);
			options.callback(result);
		};
	}
	this.popupInterfacePool.push(interface);
	
	var popup = window.open(popupUrl, windowName, features);
	return popup;
};

iKEP.popupOpen = function(url, options, windowName) {
	options = jQuery.extend({width:200, height:200, top:undefined, left:undefined, resizable:true, scrollbar:true, argument:undefined, callback:null}, options);
	options.resizable = options.resizable === true ? "yes" : "no";
	options.scrollbar = options.scrollbar === true ? "yes" : "no";
	options.modal = options.modal === true ? "yes" : "no";
	
	var features = "location=no,menubar=no,status=no,titlebar=no,toolbar=no";
	features += ",width=" + (options.width||200) + ", height=" + (options.height||200);
	features += ",left=" + (!isNaN(options.left) ? options.left : ((Math.floor((screen.availWidth - options.width) / 2) + (jQuery.browser["msie"] ? 0 : screen.availLeft))));// IE의 경우 사이즈 계산을 못하나 NaN으로 입력시 무시하고 센터로 보내짐.
	features += ",top=" + (!isNaN(options.top) ? options.top : ((Math.floor((screen.availHeight - options.height) / 2) + (jQuery.browser["msie"] ? 0 : screen.availTop))));

	features += ",resizable=" + options.resizable;
	features += ",scrollbars=" + options.scrollbar;
	
	windowName = windowName || "_blank";
	
	if(options.argument || options.callback) {
		url += (url.indexOf("?") == -1 ? "?" : "&") + "poolIdx=" + this.popupInterfacePool.length;
		var interface = { argument:(jQuery.browser["msie"] ? JSON.stringify(options.argument) : options.argument) || null, callback : null };
		if(options.callback) {
			interface.callback = function(result) {
				if(jQuery.browser["msie"]) result = jQuery.parseJSON(result);
				options.callback(result);
			};
		}
		this.popupInterfacePool.push(interface);
	}
	//alert(features);
	var popup = window.open(url, windowName, features);
	popup.focus();
//	위도우 사이즈 조정
//	jQuery(popup).bind("load", function() {
//		iKEP.debug(options.height);
//		iKEP.debug(jQuery(popup.document).height());
//	});
//	iKEP.debug(options.height);
//	iKEP.debug(jQuery(popup.document).height());
	
	return popup;
};

iKEP.getPopupArgument = function() {
	var result = null;
	if(parent && parent.dialogArguments) {	// dialog window
		if(parent && parent["argument"]) {
			result = parent["argument"];
		}
	} else {	// popup window
		var openerWindow = opener || top.opener; 
		
		var poolIdx = this.getUrlArguments("poolIdx");
		if(poolIdx != undefined && poolIdx > -1 && openerWindow) {
			var interface = openerWindow.iKEP.popupInterfacePool[poolIdx];
			result = jQuery.browser["msie"] ? jQuery.parseJSON(interface.argument) : interface.argument;
		}
	}
	
	return result;
};

iKEP.getPopupMailArgument = function() {
	var result = null;
	//if(parent && parent.dialogArguments) {	// dialog window
	//	if(parent && parent["argument"]) {
	//		result = parent["argument"];
	//	}
	//} else {	// popup window
		var openerWindow = opener || top.opener; 
		
		var poolIdx = this.getUrlArguments("poolIdx");
		if(poolIdx != undefined && poolIdx > -1 && openerWindow) {
			//iKEP.debug(poolIdx);
			//iKEP.debug(openerWindow.iKEP.popupInterfacePool);
			var interface = openerWindow.iKEP.popupInterfacePool[poolIdx];
			result = interface.argument;
		}
	//}
	
	return result;
};

iKEP.returnPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			var interface = openerWindow.iKEP.popupInterfacePool[poolIndex];
			interface.callback(result);
		}
		window.close() || top.window.close();
	}
};


iKEP.returnSchedulePopup = function (result, poolIdx) {
	var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/init.do?action=onDirectUpdate";
	url += "&scheduleId=" + JSON.queryString(result.scheduleId);

	parent.opener.location.href = url;
	top.window.close();
};

iKEP.returnKmsRefPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.setRef(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnCallback = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.setCallback(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnRolePopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.orgCallback(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnClassPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.setJobClass(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnDutyPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.setJobDuty(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnUserPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.orgCallback(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.returnGroupPopup = function (result, poolIdx) {
	if(parent && parent.dialogArguments) {	// dialog window
		var callback = parent.dialogArguments["callback"];
		if(parent.opener[callback]) {
			parent.opener[callback](result);
			parent.opener[callback] = undefined;
		} else {
			parent.window.returnValue = result;
		}
		parent.close();
	} else {	// popup window
		var openerWindow = opener || top.opener;
		var poolIndex = (poolIdx === '0' || poolIdx === 0) ? 0 : (poolIdx || this.getUrlArguments("poolIdx"));	// poolIdx가 있으면 url에서 조회하지 않음
		 
		if(poolIndex != undefined && poolIndex > -1 && openerWindow) {
			if(jQuery.browser["msie"]) {
				result = JSON.stringify(result);
				}	// 팝업에서 데이타 전달시 Object타입의 데이타를 전달하면 데이타 변형이 일어남. IE only
			openerWindow.orgCallback(result);
		}
		window.close() || top.window.close();
	}
};

iKEP.getUrlArguments = function(name) {
	var result = undefined, paramSplit, url = location.href;

	if((paramSplit = url.indexOf("?")) > -1) {
		var bookmarkPosition = url.indexOf("#");
		var paramString = url.substring(paramSplit+1, bookmarkPosition > -1 ? bookmarkPosition : url.length);
		var params = paramString.split("&");

		result = name ? undefined : {};
		for(var i=0;i<params.length;i++) {
			var param = params[i].split("=");
			if(name) {
				if(name == param[0]) {
					result = isNaN(param[1]) ? param[1] : parseInt(param[1], 10);
					break;
				}
			} else {
				result[param[0]] = isNaN(param[1]) ? param[1] : parseInt(param[1], 10);
			}
		}
	}
	
	return result;
};

// 스케줄 수정화면으로 보내기
iKEP.goScheduleUpdate = function(scheduleInfo, target) {
	var url = iKEP.getContextRoot() + "/lightpack/planner/calendar/init.do?action=onDirectUpdate";
	url += "&" + JSON.queryString(scheduleInfo);

	if(target) target.location.href = url;
	else location.href = url;
};

iKEP.showAddressBook = function(callback, items, options) {
	orgCallback = callback;
	var _options = jQuery.extend(true, {searchWord:"", selectElement:null, controlType:"ORG", selectType:"ALL", selectMaxCnt:500, searchSubFlag:"false", isAppend:false, modal:false, tabs:{common:0, personal:0, collaboration:0, sns:0, org:1, search:1}}, options);
	
	var url = this.urlAddressBookPopup + "?controlType=" + _options.controlType.toUpperCase() +
		"&controlTabType=" + _options.tabs.common + ":" + _options.tabs.personal + ":" + _options.tabs.collaboration + ":" + _options.tabs.sns +":" + _options.tabs.org +":" + _options.tabs.search +
		"&selectType=" + _options.selectType.toUpperCase() +
		"&selectMaxCnt=" + _options.selectMaxCnt +
		"&searchSubFlag=";
	
	var searchSubFlag = _options.searchSubFlag;
	if(_options.searchSubFlag != "false") {
		var $el = $(_options.searchSubFlag);
		if($el.length > 0) {
			switch($el.val()) {
				case "1" :
				case "true" :
					searchSubFlag = "true";
					break;
				default : searchSubFlag = "false";
			}
		}
	}
	url += searchSubFlag;
	
	var windowName = "winAddressBook";
	
	if(_options.isAppend == true) items = undefined;
	if(_options.selectElement == null && callback && typeof(callback) != "function") {
		_options.selectElement = callback;
		callback = null;
	}
	var popupOptions = {
		width : 700,
		height : 550,
		argument : { search:_options.searchWord, items:items||undefined },
		callback : callback
	};

	if(_options.selectElement) {
		popupOptions.callback = function(result) {
//			if(result && result.length > 0) {	// 아이템을 모두 삭제한 경우 적용을 위해....
				var orgItems = [];
				var $select = jQuery(_options.selectElement);
				if(_options.isAppend == true) {
					$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				} else {
					$select.empty();
				}
				
				jQuery.each(result, function() {
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "common" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "user" : identity = this.id; tpl = iKEP.template.userOption; break;
						case "addruser" : identity = this.id; tpl = iKEP.template.userOption; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						var $option = jQuery.tmpl(tpl, this).appendTo($select);
						jQuery.data($option[0], "data", this);
					}
				});
//			}
			callback && callback(result);
		};
	}

	if(_options.modal == true) this.dialogOpen(url, popupOptions);
	else this.popupOpen(url, popupOptions, windowName);
};

iKEP.showAddressReaderBook = function(callback, items, options) {
	var _options = jQuery.extend(true, {searchWord:"", selectElement:null, controlType:"ORG", selectType:"ALL", selectMaxCnt:500, searchSubFlag:"false", isAppend:false, modal:false, tabs:{common:0, personal:0, collaboration:0, sns:0, org:1, search:1}}, options);
	
	var url = this.urlAddressBookReaderPopup + "?controlType=" + _options.controlType.toUpperCase() +
		"&controlTabType=" + _options.tabs.common + ":" + _options.tabs.personal + ":" + _options.tabs.collaboration + ":" + _options.tabs.sns +":" + _options.tabs.org +":" + _options.tabs.search +
		"&selectType=" + _options.selectType.toUpperCase() +
		"&selectMaxCnt=" + _options.selectMaxCnt +
		"&searchSubFlag=";
	
	var searchSubFlag = _options.searchSubFlag;
	if(_options.searchSubFlag != "false") {
		var $el = $(_options.searchSubFlag);
		if($el.length > 0) {
			switch($el.val()) {
				case "1" :
				case "true" :
					searchSubFlag = "true";
					break;
				default : searchSubFlag = "false";
			}
		}
	}
	url += searchSubFlag;
	
	var windowName = "winAddressBook";
	
	if(_options.isAppend == true) items = undefined;
	if(_options.selectElement == null && callback && typeof(callback) != "function") {
		_options.selectElement = callback;
		callback = null;
	}
	var popupOptions = {
		width : 700,
		height : 550,
		argument : { search:_options.searchWord, items:items||undefined },
		callback : callback
	};

	if(_options.selectElement) {
		popupOptions.callback = function(result) {
//			if(result && result.length > 0) {	// 아이템을 모두 삭제한 경우 적용을 위해....
				var orgItems = [];
				var $select = jQuery(_options.selectElement);
				if(_options.isAppend == true) {
					$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				} else {
					$select.empty();
				}
				
				jQuery.each(result, function() {
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "common" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "user" : identity = this.id; tpl = iKEP.template.userOption; break;
						case "addruser" : identity = this.id; tpl = iKEP.template.userOption; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						var $option = jQuery.tmpl(tpl, this).appendTo($select);
						jQuery.data($option[0], "data", this);
					}
				});
//			}
			callback && callback(result);
		};
	}

	if(_options.modal == true) this.dialogOpen(url, popupOptions);
	else this.popupOpen(url, popupOptions, windowName);
};

iKEP.showAddressSurveyBook = function(callback, items, options) {
	var _options = jQuery.extend(true, {searchWord:"", selectElement:null, controlType:"ORG", selectType:"ALL", selectMaxCnt:500, searchSubFlag:"false", isAppend:false, modal:false, tabs:{common:0, personal:0, collaboration:0, sns:0, org:1, search:1}}, options);
	
	var url = this.urlAddressBookSurveyPopup + "?controlType=" + _options.controlType.toUpperCase() +
		"&controlTabType=" + _options.tabs.common + ":" + _options.tabs.personal + ":" + _options.tabs.collaboration + ":" + _options.tabs.sns +":" + _options.tabs.org +":" + _options.tabs.search +
		"&selectType=" + _options.selectType.toUpperCase() +
		"&selectMaxCnt=" + _options.selectMaxCnt +
		"&searchSubFlag=";
	
	var searchSubFlag = _options.searchSubFlag;
	if(_options.searchSubFlag != "false") {
		var $el = $(_options.searchSubFlag);
		if($el.length > 0) {
			switch($el.val()) {
				case "1" :
				case "true" :
					searchSubFlag = "true";
					break;
				default : searchSubFlag = "false";
			}
		}
	}
	url += searchSubFlag;
	
	var windowName = "winAddressBook";
	
	if(_options.isAppend == true) items = undefined;
	if(_options.selectElement == null && callback && typeof(callback) != "function") {
		_options.selectElement = callback;
		callback = null;
	}
	var popupOptions = {
		width : 700,
		height : 550,
		argument : { search:_options.searchWord, items:items||undefined },
		callback : callback
	};

	if(_options.selectElement) {
		popupOptions.callback = function(result) {
//			if(result && result.length > 0) {	// 아이템을 모두 삭제한 경우 적용을 위해....
				var orgItems = [];
				var $select = jQuery(_options.selectElement);
				if(_options.isAppend == true) {
					$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				} else {
					$select.empty();
				}
				
				jQuery.each(result, function() {
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "common" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "user" : identity = this.id; tpl = iKEP.template.userOption; break;
						case "addruser" : identity = this.id; tpl = iKEP.template.userOption; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						var $option = jQuery.tmpl(tpl, this).appendTo($select);
						jQuery.data($option[0], "data", this);
					}
				});
//			}
			callback && callback(result);
		};
	}

	if(_options.modal == true) this.dialogOpen(url, popupOptions);
	else this.popupOpen(url, popupOptions, windowName);
};

iKEP.showAddressMailBook = function(callback, items, options) {
	var _options = jQuery.extend(true, {searchWord:"", selectElement:null, controlType:"ORG", selectType:"ALL", selectMaxCnt:2000, searchSubFlag:"false", isAppend:false, modal:false, tabs:{common:1, personal:1, collaboration:0, sns:0}}, options);
		
	var url = this.urlAddressBookMailPopup + "?controlType=" + _options.controlType.toUpperCase() +
		"&controlTabType=" + _options.tabs.common + ":" + _options.tabs.personal + ":" + _options.tabs.collaboration + ":" + _options.tabs.sns +
		"&selectType=" + _options.selectType.toUpperCase() +
		"&selectMaxCnt=" + _options.selectMaxCnt +
		"&searchSubFlag=";
	
	var searchSubFlag = _options.searchSubFlag;
	if(_options.searchSubFlag != "false") {
		var $el = $(_options.searchSubFlag);
		if($el.length > 0) {
			switch($el.val()) {
				case "1" :
				case "true" :
					searchSubFlag = "true";
					break;
				default : searchSubFlag = "false";
			}
		}
	}
	url += searchSubFlag;
	
	var windowName = "winAddressBookMail";
	
	if(_options.isAppend == true) items = undefined;
	if(_options.selectElement == null && callback && typeof(callback) != "function") {
		_options.selectElement = callback;
		callback = null;
	}
	var popupOptions = {
		width : 700,
		height : 550,
		argument : { search:_options.searchWord, items:items||undefined },
		callback : callback
	};

	if(_options.selectElement) {
		popupOptions.callback = function(result) {
			if(result && result.length > 0) {
				var templateName = ["addrBookItemGroup", "addrBookItemUser", "addrBookItemAddrUser"];
				jQuery.template(templateName[0], '<option value="\${code}">\${name}</option>');
				jQuery.template(templateName[1], '<option value="\${id}">\${userName} \${jobTitleName} (\${teamName})</option>');
				jQuery.template(templateName[2], '<option value="\${id}">\${userName} \${jobTitleName} (\${teamName})</option>');

				var orgItems = [];
				var $select = jQuery(_options.selectElement);
				if(_options.isAppend == true) {
					$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				} else {
					$select.empty();
				}
				
				jQuery.each(result, function() {
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = templateName[0]; break;
						case "user" : identity = this.id; tpl = templateName[1]; break;
						case "addruser" : identity = this.id; tpl = templateName[2]; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						var $option = jQuery.tmpl(tpl, this).appendTo($select);
						jQuery.data($option[0], "data", this);
					}
				});
			}
			callback && callback(result);
		};
	}

	if(_options.modal == true) this.dialogOpen(url, popupOptions);
	else this.popupOpen(url, popupOptions, windowName);
};



iKEP.showApprovalLine = function(callback, items, options) {
	var _options = jQuery.extend(true, {searchWord:"", selectElement:null, controlType:"ORG", selectType:"ALL", selectMaxCnt:500, searchSubFlag:"false", isAppend:false, modal:false, tabs:{common:0, personal:0, collaboration:0, sns:0}}, options);
	var _contextRoot = window["contextRoot"] || "";;
	var apprLineUrl=_contextRoot + "/approval/work/apprLine/createApprLineView.do";
	
	var url = apprLineUrl + "?controlType=" + _options.controlType.toUpperCase() +
		"&controlTabType=" + _options.tabs.common + ":" + _options.tabs.personal + ":" + _options.tabs.collaboration + ":" + _options.tabs.sns +
		"&selectType=" + _options.selectType.toUpperCase() +
		"&selectMaxCnt=" + _options.selectMaxCnt +
		"&apprId="+_options.apprId	+
		"&apprType="+_options.apprType	+	
		"&entrustUserId="+_options.entrustUserId	+	
		"&apprLineType="+_options.apprLineType	+	
		"&searchSubFlag=";
	
	var searchSubFlag = _options.searchSubFlag;
	if(_options.searchSubFlag != "false") {
		var $el = $(_options.searchSubFlag);
		if($el.length > 0) {
			switch($el.val()) {
				case "1" :
				case "true" :
					searchSubFlag = "true";
					break;
				default : searchSubFlag = "false";
			}
		}
	}
	url += searchSubFlag;
	
	var windowName = "winApprovalLine";
	
	if(_options.isAppend == true) items = undefined;
	if(_options.selectElement == null && callback && typeof(callback) != "function") {
		_options.selectElement = callback;
		callback = null;
	}
	var popupOptions = {
		width : 1000,
		height : 650,
		argument : { search:_options.searchWord, items:items||undefined ,apprLineType:_options.apprLineType},
		callback : callback
	};

	if(_options.selectElement) {
		popupOptions.callback = function(result) {
			if(result && result.length > 0) {
				var orgItems = [];
				var $select = jQuery(_options.selectElement);
				if(_options.isAppend == true) {
					$select.children().each(function() { orgItems.push(jQuery(this).attr("value")); });
				} else {
					$select.empty();
				}
				
				jQuery.each(result, function() {
					var identity = "";
					var tpl = "";
					switch(this.type) {
						case "group" : identity = this.code; tpl = iKEP.template.groupOption; break;
						case "user" : identity = this.id; tpl = iKEP.template.userOption; break;
						case "addruser" : identity = this.id; tpl = iKEP.template.userOption; break;
					}

					if(jQuery.inArray(identity, orgItems) == -1) {
						var $option = jQuery.tmpl(tpl, this).appendTo($select);
						jQuery.data($option[0], "data", this);
					}
				});
			}
			callback && callback(result);
		};
	}

	if(_options.modal == true) this.dialogOpen(url, popupOptions);
	else this.popupOpen(url, popupOptions, windowName);
};

iKEP.showApprMyRequestComplete = function(callback) {
	var url = iKEP.getContextRoot() + '/approval/work/apprlist/popListApprMyRequestComplete.do';
	
	iKEP.popupOpen(url, {width:900, height:600, callback:callback}, "MyRequestComplete");
};

iKEP.showTwittingPage = function(text, stringHttp) {
	var url = iKEP.getContextRoot() + "/socialpack/microblogging/getTweettingPage.do";
	
	var windowName = "winTwittingPop";
	
	var popupOptions = {
		width : 800,		
		height : 142, //changed from 205 to 142(2011.06.21)
		argument:{arguText:text, arguUrl:stringHttp}
	};

	this.popupOpen(url, popupOptions, windowName);
};

iKEP.checkProhibitWord = function(moduleCode, text) {
	var result = '';
	
	$jq.ajax({
		url : iKEP.getContextRoot() + "/support/abusereporting/checkProhibitWord.do",
		// ArConstant에 정의되어 있는 모듈별 상수, 체크할 contents
		data : {'moduleCode':moduleCode,'contents':text},
		type : "post",
		async : false,
		success : function(data) {
				result = data;
		}
	});
	
	return result;
};

// 마이크로블로그 내용만 간단 보기 팝업
iKEP.viewMicroblogContents = function(mblogId) {
	var url = iKEP.getContextRoot() + "/socialpack/microblogging/getMblog.do?mblogId="+mblogId;
	iKEP.popupOpen(url, {width:420, height:200, scrollbar:'no'}, "microblogContents");
};

// DOM element의 입력 문자 길이 체크 : keypress 이벤트 대용 (firefox, opera 브라우저의 2바이트 문자 입력시 키 이벤트 발생하지 않는 문제 있음)
iKEP.checkLength = function(el, callback, isByte) {
	isByte = isByte || false;
	var $el = jQuery(el);
	var fncCallback = callback;

	$el.bind("focusin", function () {
    	var interval = setInterval(function(){
    		var val = $el.val();
    		fncCallback(isByte ? val.byteLength() : val.length, el);
    	}, 500);
    	
    	var fncFocusOut = function() {
    		clearInterval(interval);
    		$el.unbind("focusout", fncFocusOut);
    		
    		var val = $el.val();
    		fncCallback(isByte ? val.byteLength() : val.length, el);
    	};
    	$el.bind("focusout", fncFocusOut);
    });
};

//주소록 사용자 검색 팝업
iKEP.popupAddrPerson = function(isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupAddrPerson.do?isMulti='+isMulti;
	
	iKEP.popupOpen(url, {width:800, height:510, callback:callback}, "PersonSearch");
};

// 사용자 검색 팝업
iKEP.popupUser = function(name, column, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupUser.do?isMulti='+isMulti+'&name='+name+'&column='+column;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,column:column,isMulti:isMulti}}, "UserSearch");
};


iKEP.subPopupUser = function(name, column, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/subPopupUser.do?isMulti='+isMulti+'&name='+name+'&column='+column;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,column:column,isMulti:isMulti}}, "UserSearch");
};


// 사용자 검색 : AJAX
iKEP.searchUser = function(event, isMulti, callback, column) {
	orgCallback = callback;
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		if(column == undefined) {
			column= "title";
		}
		
		if(!name) {
			iKEP.popupUser(name, column, isMulti, callback);
		} else {
			jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
			.success(function(data) {
				
				switch(data.userName) {
					case "isMany" : iKEP.popupUser(name, column, isMulti, callback); break;
					case "isEmpty" : callback([]); break;
					default :
						callback([{
							type:"user",
							id:data.userId,
							empNo:data.empNo,
							userName:data.userName,
							jobTitleName:data.jobTitleName,
							jobDutyName:data.jobDutyName,
							group:data.groupId,
							teamName:data.teamName,
							email:data.mail,
							mobile:data.mobile,
							sapId:data.sapId,
							profilePicturePath:data.profilePicturePath,
							registDate:data.registDate
						}]);
				}
			})
			.error(function(event, request, settings) { alert("error"); });
		}
	}
};

//사용자 검색2(사용자가 없을시 메세지와 사용자 이름 초기화) : AJAX
iKEP.searchUser2 = function(event, isMulti, callback, column) {
	orgCallback = callback;
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		if(column == undefined) {
			column= "title";
		}
		
		if(!name) {
			iKEP.popupUser(name, column, isMulti, callback);
		} else {
			jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
			.success(function(data) {
				switch(data.userName) {
					case "isMany" : iKEP.popupUser(name, column, isMulti, callback); break;
					case "isEmpty" : alert("사용자가 정보가 없습니다."); callback([{empNo:'',
					                        							userName:''}]); break;
					default :
						alert("선택한 임직원( "+ data.userName +" )으로 선택이 완료되었습니다.");
						callback([{
							type:"user",
							id:data.userId,
							empNo:data.empNo,
							userName:data.userName,
							jobTitleName:data.jobTitleName,
							jobDutyName:data.jobDutyName,
							group:data.groupId,
							teamName:data.teamName,
							email:data.mail,
							mobile:data.mobile,
							sapId:data.sapId,
							profilePicturePath:data.profilePicturePath,
							registDate:data.registDate
						}]);
				}
			})
			.error(function(event, request, settings) { alert("error"); });
		}
	}
};

iKEP.searchPlannerUser = function(event, isMulti, callback, column) {
	orgCallback = callback;
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		if(column == undefined) {
			column= "title";
		}
		
		if(!name) {
			iKEP.subPopupUser(name, column, isMulti, callback);
		} else {
			jQuery.post(iKEP.getContextRoot()+"/support/popup/getUser.do", {name:name, column:column})
			.success(function(data) {
				
				switch(data.userName) {
					case "isMany" : iKEP.subPopupUser(name, column, isMulti, callback); break;
					case "isEmpty" : callback([]); break;
					default :
						callback([{
							type:"user",
							id:data.userId,
							empNo:data.empNo,
							userName:data.userName,
							jobTitleName:data.jobTitleName,
							jobDutyName:data.jobDutyName,
							group:data.groupId,
							teamName:data.teamName,
							email:data.mail,
							mobile:data.mobile,
							sapId:data.sapId,
							profilePicturePath:data.profilePicturePath,
							registDate:data.registDate
						}]);
				}
			})
			.error(function(event, request, settings) { alert("error"); });
		}
	}
};


//테라스 메일 사용자 등록 팝업
iKEP.popupAddPerson = function(userAddr, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/addPersonPopup.do?userAddr='+userAddr;
	
	iKEP.popupOpen(url, {width:650, height:287, callback:callback}, "AddPerson");
};

//부서 검색 팝업
iKEP.popupGroup = function(name, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupGroup.do?isMulti='+isMulti+'&name='+name;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,isMulti:isMulti}}, "GroupSearch");
};

// 부서 검색 : AJAX
iKEP.searchGroup = function(event, isMulti, callback) {
	orgCallback = callback;
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		if(!name) {
			iKEP.popupGroup(name, isMulti, callback);
		}else {
			jQuery.post(iKEP.getContextRoot()+"/support/popup/getGroup.do", {name:name})
			.success(function(data) {
				switch(data.groupName) {
					case "isMany" : iKEP.popupGroup(name, isMulti, callback); break;
					case "isEmpty" : callback([]); break;
					default : callback([{type:"group", code:data.groupId, name:data.groupName, parent:data.parentGroupId}]);
				}
			})
			.error(function(event, request, settings) { alert("error"); });
		}
	}
};


//그룹 검색 팝업
iKEP.popupGroupType = function(name, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupGroupType.do?isMulti='+isMulti;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,isMulti:isMulti}}, "GroupTypeSearch");
};

//그룹 검색 : AJAX
iKEP.searchGroupType = function(event, isMulti, callback) {
	orgCallback = callback;
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		jQuery.post(iKEP.getContextRoot()+"/support/popup/getGroupType.do", {name:name})
		.success(function(data) {
			switch(data.groupName) {
				case "isMany" : iKEP.popupGroupType(name, isMulti, callback); break;
				case "isEmpty" : callback([]); break;
				default : callback([data]);
			}
		})
		.error(function(event, request, settings) { alert("error"); });
	}
};


//역할 검색 팝업
iKEP.popupRole = function(name, isMulti, callback) {
	orgCallback = callback;
	var url = iKEP.getContextRoot() + '/support/popup/popupRole.do?isMulti='+isMulti;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,isMulti:isMulti}}, "RoleSearch");
};

//역할 검색 : AJAX
iKEP.searchRole = function(event, isMulti, callback) {
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		jQuery.post(iKEP.getContextRoot()+"/support/popup/getRole.do", {name:name})
		.success(function(data) {
			switch(data.roleName) {
				case "isMany" : iKEP.popupRole(name, isMulti, callback); break;
				case "isEmpty" : callback([]); break;
				default : callback([data]);
			}
		})
		.error(function(event, request, settings) { alert("error"); });
	}
};



//고용형태 검색 팝업
iKEP.popupJobClass = function(name, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupJobClass.do?isMulti='+isMulti;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,isMulti:isMulti}}, "JobClassSearch");
};

//고용형태 검색 : AJAX
iKEP.searchJobClass = function(event, isMulti, callback) {
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		jQuery.post(iKEP.getContextRoot()+"/support/popup/getJobClass.do", {name:name})
		.success(function(data) {
			switch(data.jobClassName) {
				case "isMany" : iKEP.popupJobClass(name, isMulti, callback); break;
				case "isEmpty" : callback([]); break;
				default : callback([data]);
			}
		})
		.error(function(event, request, settings) { alert("error"); });
	}
};



//직책 검색 팝업
iKEP.popupJobDuty = function(name, isMulti, callback) {
	var url = iKEP.getContextRoot() + '/support/popup/popupJobDuty.do?isMulti='+isMulti;
	
	iKEP.popupOpen(url, {width:800, height:490, callback:callback, argument:{name:name,isMulti:isMulti}}, "JobDutySearch");
};

//직책 검색 : AJAX
iKEP.searchJobDuty = function(event, isMulti, callback) {
	if (event.which == '13' || event.which === undefined) {
		var $el = jQuery(event.target);
		var name = $el.val();
		
		jQuery.post(iKEP.getContextRoot()+"/support/popup/getJobDuty.do", {name:name})
		.success(function(data) {
			switch(data.jobDutyName) {
				case "isMany" : iKEP.popupJobDuty(name, isMulti, callback); break;
				case "isEmpty" : callback([]); break;
				default : callback([data]);
			}
		})
		.error(function(event, request, settings) { alert("error"); });
	}
};


//메일 보내기 팝업
iKEP.sendMailPop = function(nameList, emailList, title, content, fileIdList, fileNameList) {
	
	//테라스 메일 전송으로 수정
	iKEP.sendTerraceMailPop(emailList, title, content);
	//이전 발신 로직 
	//var url = iKEP.getContextRoot() + '/support/mail/sendMailForm.do';
	//iKEP.popupOpen(url, {width:800, height:760, argument:{nameList:nameList, emailList:emailList, title:title, content:content, fileIdList:fileIdList, fileNameList:fileNameList}}, "MailSend");
};

//메일 보내기 팝업
iKEP.sendTerraceMailPop = function(emailList, title, content) {

	// 개발, 운영 메일서버 URL 구분 
	var mailHost ="http://webmail.moorim.co.kr";
	var regurl=/eptest.moorim.co.kr/g;
	
	if (regurl.test(location.href)) { 
		mailHost="http://mailtest.moorim.co.kr";
	}

	// Object 타입의 이름,메일주소 목록을 다음형태로 변경	
	/*
	var emailList = '&to=' + emailList.toString(); //JSON.stringify(emailList);
	var enTitle = encodeURIComponent(title);
	var enContent = encodeURIComponent(content);
	var url = mailHost+'/dynamic/mail/writeMessage.do?wmode=popup' + emailList + "&subj=" +enTitle + "&content=" + enContent;
	alert(url);
	iKEP.popupOpen( url, {width:800, height:760}, "MailSendTerrace");
	*/
	
	//위의 get방식을 아래와 같이 동적form생성후 post방식으로 변경합니다.
	var mailContentsForm = document.createElement("form");
	mailContentsForm.name ="mailContentsForm";
	mailContentsForm.action = mailHost+'/dynamic/mail/writeMessage.do';
	mailContentsForm.method ="post";
	
	var inputWmode = document.createElement("input");
	inputWmode.type="hidden";
	inputWmode.name="wmode";
	inputWmode.value="popup";
	mailContentsForm.insertBefore(inputWmode, null);
	
	var inputTo = document.createElement("input");
	inputTo.type="hidden";
	inputTo.name="to";
	inputTo.value=emailList.toString();
	mailContentsForm.insertBefore(inputTo, null);
	
	var inputTitle = document.createElement("input");
	inputTitle.type="hidden";
	inputTitle.name="subj";
	inputTitle.value=title;
	mailContentsForm.insertBefore(inputTitle, null);
	
	var inputContents= document.createElement("input");
	inputContents.type="hidden";
	inputContents.name="content";
	inputContents.value=content;
	mailContentsForm.insertBefore(inputContents, null);
	
		
	var mailContentPop = iKEP.popupOpen('', {width:800, height:760}, "MailSendTerrace");
	
	mailContentsForm.target="MailSendTerrace";//폼의 타겟은 메일창.
	document.body.insertBefore(mailContentsForm, null);//생성된 폼을 document에 집어넣은후
	mailContentsForm.submit();//전송
	
	
};



//파일업로드 팝업
iKEP.fileUpload = function(targetId, editorAttachYn, imageYn, callback) {
	var title = "File Upload";
	if(imageYn == '1') title ='Image Upload';
	
	if(editorAttachYn == null) editorAttachYn ='0';
	if(imageYn == null) imageYn ='0';
	
	var url = iKEP.getContextRoot() + '/support/fileupload/uploadForm.do?targetId='+targetId+"&editorAttachYn="+editorAttachYn+"&imageYn="+imageYn;
	var width = 590;
	var height = 170;
	
	iKEP.showDialog({
		title: title,
		url: url,
		width:width,
		height:height,
		modal: true,
		scroll: "no",
		callback: callback || afterFileUpload
	});
	
};


//원본이미지보기 팝업
iKEP.viewImageFile = function(fileId, fileName) {
	
	//var imageDialog = null;
	var url = iKEP.getContextRoot() + '/support/fileupload/viewImageFile.do?fileId='+fileId;
	var width = $jq(window).width()*0.8;
	var height = $jq(window).height()*0.8;
	
	if(fileName==undefined) fileName = "";
	
	parent.parent.showMainFrameDialog(url, fileName, width, height);
	
};


//원도우 닫기
iKEP.closePop = function() {
	window.close();
};


//RSS,ATOM 링크 추가
iKEP.addChannel = function(feedType, metaType, metaId) { 
	//LG 하우시스 납품용
	if(feedType == "rss") {
		alert('RSS2.0 피드 URL을 생성합니다.');
	} else {
		alert('ATOM1.0 피드 URL을 생성합니다.');
	}
	
	return false;
	
	var url = iKEP.getContextRoot() + '/support/rss/channel/checkDupChannel.do';
	
	$jq.post(url, {feedType:feedType, metaType:metaType, metaId:metaId})
	.success(function(data) {  
		
		var json = $jq.parseJSON(data);
		if(json.status=="success") { 
			
			url = iKEP.getContextRoot() + '/support/rss/channel/createChannelForm.do?feedType='+feedType+'&metaType='+metaType+'&metaId='+metaId;
			
			channelDialog = new iKEP.Dialog({     
				title : json.title,     
				url : url,     
				modal : true,
				width : 700,
				height : 200,
				params : "",
				autoOpen: true,
				scroll: "no"
			}); 
		
		} else {
			alert(json.message);
		}
	})
	.error(function(event, request, settings) { alert("error"); });  
};


//즐겨찾기 추가
iKEP.addFavorite = function(type, itemTypeCode, targetId, targetTitle, callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/createFavorite.do';

	$jq.post(url, {type:type, itemTypeCode:itemTypeCode, targetId:targetId, targetTitle:targetTitle})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json);
	})
	.error(function(event, request, settings) { /*alert("error");*/ });  
	
};

//즐겨찾기 삭제
iKEP.delFavorite = function(favoriteId, targetId, callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/deleteFavorite.do';

	$jq.post(url, {favoriteId:favoriteId, targetId:targetId})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json);
	})
	.error(function(event, request, settings) { /*alert("error");*/ });  
	
};

//즐겨찾기 체크
iKEP.chkFavorite = function(targetId, callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/checkFavorite.do';

	$jq.post(url, {targetId:targetId})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json);
	})
	.error(function(event, request, settings) { alert("error"); });  
	
};

//즐겨찾기 확인 - 화면에서 복수개의 즐겨찾기 체크시 사용 키값 그대로 반환 용도
iKEP.chkMultiFavorite = function(targetId, targetTitle, callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/checkFavorite.do';

	$jq.post(url, {targetId:targetId})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json,targetId,targetTitle);
	})
	.error(function(event, request, settings) { alert("error"); });  
	
};

//즐겨찾기 추가 - 화면에서 복수개의 즐겨찾기 체크시 사용 키값 그대로 반환 용도
iKEP.addMultiFavorite = function(type, itemTypeCode, targetId, targetTitle, callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/createFavorite.do';

	$jq.post(url, {type:type, itemTypeCode:itemTypeCode, targetId:targetId, targetTitle:targetTitle})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json,targetId,targetTitle);
	})
	.error(function(event, request, settings) { alert("error"); });  
	
};

//즐겨찾기 삭제 - 화면에서 복수개의 즐겨찾기 체크시 사용 키값 그대로 반환 용도
iKEP.delMultiFavorite = function(favoriteId,targetId,targetTitle,callback) { 
	
	var url = iKEP.getContextRoot() + '/support/favorite/deleteFavorite.do';

	$jq.post(url, {favoriteId:favoriteId, targetId:targetId})
	.success(function(data) {
		var json = $jq.parseJSON(data);
		if(callback!=undefined) callback(json,targetId,targetTitle);
	})
	.error(function(event, request, settings) { alert("error"); });  
	
};


//즐겨찾기 ShortCut
iKEP.shortcutForFavorite = function() {
	var url = iKEP.getContextRoot() + '/support/favorite/getShortcut.do';
	iKEP.popupOpen(url, {width:400, height:500}, "FavoriteShortCut");
	
};

//최근본글 ShortCut
iKEP.shortcutForRecent = function() {
	var url = iKEP.getContextRoot() + '/support/activitystream/getShortcut.do';
	iKEP.popupOpen(url, {width:400, height:500}, "RecentShortCut");
	
};

//SMS보내기(gubun(내부사용자:1,외부사용자:2) receverId(내부사용자일경우 userId, 외부사용자일 경우 phoneNum)
iKEP.sendSms = function(gubun, receiverId) {
	
	var url = iKEP.getContextRoot() + "/support/sms/sms.do?gubun="+gubun+"&receiverId="+receiverId;
	iKEP.popupOpen(url, {width:580, height:448, resizable:false}, "SendSms");
	//width:564, height:428
	
};

iKEP.listAgent = function() {
	
	var url = iKEP.getContextRoot() + "/portal/main/listUserAgent.do";
	iKEP.popupOpen(url, {width:580, height:448, resizable:false}, "ListAgent");
	//width:564, height:428
	
};

//쪽지 보내기 (senderUserId : 수신인 ID)
iKEP.sendMessage = function(senderUserId) {
	
	var url = iKEP.getContextRoot() + "/support/message/messageNew.do?senderUserId=" + senderUserId;
	iKEP.popupOpen(url, {width:800, height:600}, "messagePopup");
	
};


// 해당 사람의 프로파일로 이동하기 
iKEP.goProfileMain = function(userId) {
	document.location.href = iKEP.getContextRoot() + '/portal/main/listUserMain.do?rightFrameUrl=/support/profile/getProfile.do?targetUserId='+userId;
};


//해당 사람의 프로파일로 이동하기 
iKEP.goProfilePopupMain = function(targetUserId) {
	
	var url = iKEP.getContextRoot() + "/support/profile/getProfilePopup.do?targetUserId="+ targetUserId;
	iKEP.popupOpen(url, {width:800, height:370}, "userProfile");
	
};

//RSS 아이템 상세 보기
var channelItemDialog = null;
iKEP.channelItemView = function(url, title) {
	
	//var width = $jq(window).width()*0.8;
	//var height = $jq(window).height()*0.8;

	//parent.showMainFrameDialog(url, title, width, height);
	
	window.open(url, "", "width=800,height=500,status=no,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes");
};


//메일 상세 보기
var mailDetailDialog = null;
iKEP.mailDetailView = function(url, title) {
	
	var width = $jq(window).width()*0.8;
	var height = $jq(window).height()*0.8;
	
	parent.showMainFrameDialog(url, title, width, height);
	
};


iKEP.checkApprPassword = function(pwd){
	
	//중간 공백 체크
	if (pwd.indexOf(' ') > -1) {
        return false;
    }
	
	 //한글체크
    if((/[가-힣]/).test(pwd)) {
    	return false;
    }
    
    return true;
};

iKEP.sizeFixWithWindow = function(target) {
	function setSize(target, isReady) { 
        var $window = jQuery(window);
        var $el = jQuery(target);
        var $parent = $el.parent();
        
        var height = $window.height(); 
        var width = $window.width();
        
        var position = $el.offset();
        //var rightOffset = isReady === true && !jQuery.browser["msie"] ? 0 : $parent.outerWidth() - $parent.width() - position.left;
        var rightOffset = $parent.outerWidth() - $parent.width() - position.left;       
       // $el.css( {width: Math.ceil(width - position.left - rightOffset), height: Math.ceil(height - position.top)} );
        $el.css( {width: Math.ceil(width - position.left-20), height: Math.ceil(height - position.top)} );
    }
	
	jQuery(window).wresize( function() { setSize(target); } ); 
	setSize(target, true);
};

iKEP.createTagEmbedObject = function(target, param1, param2, width, height){
	
	if(width == null){
		width="202";
	}
	if(height == null){
		height="140";
	}
	
	var idx = (new Date()).getTime();
	var swf = iKEP.getContextRoot()+"/base/swf/tag/lgcns_tags.swf";
	var param = "tagUrl="+param1+"&amp;divID="+param2;
	var embedTag = (jQuery.browser["msie"] === true) ? '<object id="embedObj_' + idx + '" align="middle" width="'+width+'" height="'+height+'" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9.0.0.0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">' +
			'<param name="allowScriptAccess" value="always"/>' +
			'<param name="movie" value="' + swf + '"/>' +
			'<param name="flashvars" value="' + param + '"/>' +
			'<param name="wmode" value="transparent"/>' +	//window
			'<param name="menu" value="false"/>' +
			'<param name="scaleMode" value="noScale"/>' +
			'<param name="showMenu" value="false"/>' +
			'<param name="align" value="CT"/>' +
			'<param name="quality" value="high"/>' +
			'<param name="bgcolor" value="#ffffff"/>' +
		'</object>' : '<embed align="middle" width="'+width+'" height="'+height+'"' +
		' pluginspage="http://www.macromedia.com/go/getflashplayer"' +
		' type="application/x-shockwave-flash"' +
		' allowscriptaccess="always"' +
		' bgcolor="#ffffff"' +
		' quality="high"' +
		' menu="false"' +
		' wmode="transparent"' +	//window
		' flashvars="' + param + '"' +
		' src="' + swf + '"/>';
	
	jQuery(target).html(embedTag);

};

iKEP.createIndexEmbedObject = function(target, movie, width, height){
	
	var idx = (new Date()).getTime();
	var swf = iKEP.getContextRoot()+"/base/swf/index/profile/"+movie;
	var embedTag = (jQuery.browser["msie"] === true) ? '<object id="embedObj_' + idx + '" align="middle" width="'+width+'" height="'+height+'" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9.0.0.0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">' +
			'<param name="allowScriptAccess" value="always"/>' +
			'<param name="movie" value="' + swf + '"/>' +
			'<param name="wmode" value="transparent"/>' +	//window
			'<param name="menu" value="false"/>' +
			'<param name="scaleMode" value="noScale"/>' +
			'<param name="showMenu" value="false"/>' +
			'<param name="align" value="CT"/>' +
			'<param name="quality" value="high"/>' +
			'<param name="bgcolor" value="#ffffff"/>' +
		'</object>' : '<embed align="middle" width="'+width+'" height="'+height+'"' +
		' pluginspage="http://www.macromedia.com/go/getflashplayer"' +
		' type="application/x-shockwave-flash"' +
		' allowscriptaccess="always"' +
		' bgcolor="#ffffff"' +
		' quality="high"' +
		' menu="false"' +
		' wmode="transparent"' +	//window
		' src="' + swf + '"/>';
	
	jQuery(target).html(embedTag);

};

iKEP.createSWFEmbedObject = function(target, movie, width, height){
	
	var idx = (new Date()).getTime();	
	var embedTag = (jQuery.browser["msie"] === true) ? '<object id="embedObj_' + idx + '" align="middle" width="'+width+'" height="'+height+'" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=9.0.0.0" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000">' +
			'<param name="allowScriptAccess" value="always"/>' +
			'<param name="movie" value="' + movie + '"/>' +
			'<param name="wmode" value="transparent"/>' +	//window
			'<param name="menu" value="false"/>' +
			'<param name="scaleMode" value="noScale"/>' +
			'<param name="showMenu" value="false"/>' +
			'<param name="align" value="CT"/>' +
			'<param name="quality" value="high"/>' +
			'<param name="bgcolor" value="#ffffff"/>' +
		'</object>' : '<embed align="middle" width="'+width+'" height="'+height+'"' +
		' pluginspage="http://www.macromedia.com/go/getflashplayer"' +
		' type="application/x-shockwave-flash"' +
		' allowscriptaccess="always"' +
		' bgcolor="#ffffff"' +
		' quality="high"' +
		' menu="false"' +
		' wmode="transparent"' +	//window
		' src="' + movie + '"/>';
	
	jQuery(target).html(embedTag);

};

iKEP.iFrameResize = function(target){
	var iframe = jQuery(target); 
	
	try{
		iframe.contents().height();
	}catch(error)
	{
		iframe.height(jQuery(window).height()-19);
		return;
	}
	
	iframe.height(0);	
	var windowHeight = jQuery(window).height();

	var conHeight = iframe.contents().height();
	
	if(windowHeight > conHeight){		
		iframe.height(windowHeight-19);
		jQuery(document).scrollTop(0);
	}else{
		iframe.height(conHeight+20);
		jQuery(document).scrollTop(0);
	}

};

iKEP.iFrameResize2 = function(target){
	var iframe = jQuery(target);

	try{
		iframe.contents().height();
	}catch(error)
	{
		iframe.height(jQuery(window).height()-19);
		return;
	}
	//iframe.height(0);	
	var windowHeight = jQuery(window).height();
	var conHeight = iframe.contents().height();
	
	if(windowHeight > conHeight){		
		iframe.height(windowHeight-19);
	}else{
		iframe.height(conHeight+3);
	}

};

iKEP.iFrameMenuOnclick = function(url){
	var iframe = jQuery('#contentIframe');
	iframe.attr("src", url);
	jQuery(document).scrollTop(0);
};

iKEP.iFrameContentResize = function(){
	try {	// iframe content area resize
		var docIframe = jQuery(parent.document).find("iframe[name=contentIframe]");
		if(docIframe.length > 0) iKEP.iFrameResize2(docIframe);
	} catch(e) {}
};

// user context menu
iKEP.showUserContextMenu = function(target, userId, direction) {
	iKEP.showUserContextMenu.prototype.currUserId = userId;
	var $container = null;
	
	if(!iKEP.showUserContextMenu.prototype.container) {
		jQuery.get(iKEP.getContextRoot() + "/portal/main/getPackageVersion.do")
			.success(function(res) {	// FULL, LIGHT, BASIC
				var lang = iKEPLang.userContextMenu;
				$container = jQuery('<div class="layer_02 none" style="top:0; left:0;"/>')
					.append('<a class="layer_close" href="#a"><span>' + lang.close + '</span></a>' +
						'<ul class="list_1">' +
							'<li><a class="anchorUserProfile" href="#a">' + lang.profile + '</a></li>' +
							(res == "FULL" ? ('<li><a class="anchorUserSMS" href="#a">' + lang.sms + '</a></li>') : '') +
							'<li><a class="anchorUserMessage" href="#a">' + lang.message + '</a></li>'+
						'</ul>')
					.appendTo(document.body);
				
				$container.click(function(event) {
					event.preventDefault();
					
					var target = event.target;
					var $target = jQuery(target);
					if(target.tagName.toLowerCase() == "span") {
						target = $target.parent();
						$taget = jQuery(target);
					}
					
					if(target.tagName.toLowerCase() == "a") {
						switch(true) {
							case $target.hasClass("anchorUserProfile") :
								iKEP.goProfilePopupMain(iKEP.showUserContextMenu.prototype.currUserId);
								break;
							case $target.hasClass("anchorUserSMS") :
								iKEP.sendSms(1, iKEP.showUserContextMenu.prototype.currUserId);
								break;
							case $target.hasClass("anchorUserMessage") :
								iKEP.sendMessage(iKEP.showUserContextMenu.prototype.currUserId);
								break;
							case $target.hasClass("layer_close") :
								break;
						}
						$container.hide();
					}
				});
				
				iKEP.showUserContextMenu.prototype.container = $container;
				
				iKEP.showUserContextMenu(target, userId, direction);
			});
		return false;
	}
	
	$container = iKEP.showUserContextMenu.prototype.container;
	
	var clickItem = jQuery(target);
	var position = clickItem.offset();	//position
	var left = position.left, top = position.top;
	switch(direction) {
		case "left" : left -= $container.outerWidth() + 2; break;
		case "right" : left += clickItem.outerWidth() + 2; break;
		case "top" : top -= $container.outerHeight(); break;
		default : top += clickItem.outerHeight();
	}
	
	$container.show().css({ left:left, top:top });
};
iKEP.showUserContextMenu.prototype.container;
iKEP.showUserContextMenu.prototype.currUserId;

iKEP.showUserSimpleInfo = function(isShow, target, info, direction) {	//info : name, rank, team
	//event.stopPropagation();
	var $container = null;
	
	if(!iKEP.showUserSimpleInfo.prototype.container) {
		$container = jQuery('<div style="top:0; left:0;" class="infoLayer none"/>')
			.append('<div class="corner_RoundBox10">' +
					'<div><span class="name"></span> &nbsp;<span class="team"></span></div>' +
					'<div class="ar"></div>' +
					'<div class="l_t_corner"></div>' +
					'<div class="r_t_corner"></div>' +
					'<div class="l_b_corner"></div>' +
					'<div class="r_b_corner"></div>' +		
				'</div>')
			.appendTo(document.body);

//		jQuery(document.body).click(function(event) {
//			event.stopPropagation();
//			$container.hide();
//		});
		iKEP.showUserSimpleInfo.prototype.container = $container;
	}
	
	$container = iKEP.showUserSimpleInfo.prototype.container;
	if(isShow) {
		$container.find("span.name").html(info.name + " " + info.rank).end()
			.find("span.team").html(info.team);
		
		var clickItem = jQuery(target);
		var position = clickItem.offset();	// offset()
		
		var offset = direction == "bottom" ?  clickItem.outerHeight() : $container.outerHeight()*-1 - 8;
		$container.show().css({
			left:position.left+"px",
			top:(position.top+offset)+"px"
		});
	} else {
		$container.hide();
	}
};
iKEP.showUserSimpleInfo.prototype.container;


iKEP.showTooltipInfo = function(isShow, target, info, direction) {	//info : name, rank, team
	
	var $container = null;
	
	if(!iKEP.showTooltipInfo.prototype.container) {
		$container = jQuery('<div style="top:0; left:0;" class="infoLayer none"/>')
			.append('<div class="corner_RoundBox11">' +
					'<div class="tipContent"></div>' +
					'<div class="ar"></div>' +
					'<div class="l_t_corner"></div>' +
					'<div class="r_t_corner"></div>' +
					'<div class="l_b_corner"></div>' +
					'<div class="r_b_corner"></div>' +		
				'</div>')
			.appendTo(document.body);

		iKEP.showTooltipInfo.prototype.container = $container;
	}
	
	$container = iKEP.showTooltipInfo.prototype.container;
	if(isShow) {
		$container.find("div.tipContent").html(info);
		
		var clickItem = jQuery(target);
		var position = clickItem.offset();	// offset()
		
		var offset = direction == "bottom" ?  clickItem.outerHeight() : $container.outerHeight()*-1 - 8;
		$container.show().css({
			left:position.left+"px",
			top:(position.top+offset)+"px"
		});
	} else {
		$container.hide();
	}
};
iKEP.showTooltipInfo.prototype.container;


iKEP.alarm = function(contents, clearTime) {
	var timer, slideTime = 600, viewTime = 10*1000;
	if(!isNaN(clearTime)) viewTime = clearTime * 1000;

	var $divAlarm = jQuery(iKEP.alarm.html)
		.appendTo(document.body)
		.find("div").html(contents)
		.end();
	
	var alarmHideHandler = function() {
		if(timer) {
			clearTimeout(timer);
			timer = null;
		}
		
		$divAlarm.find("a.layer_close").hide();
		$divAlarm.slideUp(slideTime, function(){
			jQuery(this).unbind("click").remove();
		});
		
		jQuery(document.body).unbind("click", alarmHideHandler);
		$divAlarm = null;
	};
	
	$divAlarm.css({right:0, bottom:0})
		.slideDown(slideTime, function(){
			jQuery(this).find("a.layer_close").show();
			
			jQuery(document.body).click(alarmHideHandler);
			if(viewTime > 0) {
				timer = setTimeout(function(){
					alarmHideHandler();
				}, viewTime);
			}
		})
		.click(function(event) {
			event.stopPropagation();
			
			if(timer) {
				clearTimeout(timer);
				timer = null;
			}
			
			var $el = jQuery(event.target);
			if($el.hasClass("layer_close")) {
				alarmHideHandler();
			}
		});
};
iKEP.alarm.html = '<div class="layer_01 none">' +
	'<a class="layer_close" style="display:none;" href="#a"><span>close</span></a>' +
	'<div></div>' +
'</div>';


// layer select box
iKEP.setSelect = function(select, style) {
	var $select = jQuery(select).css("visibility", "hidden");
	var $parent = $select.parent();
	switch($parent.css("position")) {
		case "absolute" :
		case "relative" : break;
		default : $parent.css("position", "relative");
	}
	
	var pos = $select.position();
	var containerStyle = jQuery.extend({left:pos.left, top:pos.top, width:$select.innerWidth(), height:$select.height()}, style||{});

	var $container = jQuery('<div class="select-container"/>').insertAfter($select)
		.css(containerStyle);
	
	var $layer = jQuery('<ul class="select-layer"/>').appendTo($container)
		.css({top:$container.outerHeight()+1, minWidth:$container.width()});

	var selectedItem;
	$select.children().each(function(idx) {
		if(idx == 0) selectedItem = this;
		else if(this.selected) selectedItem = this;
		
		var $item = jQuery('<li><a>' + this.text + '</a></li>').appendTo($layer);
	});

	if($layer[0].scrollWidth > $layer[0].clientWidth) $layer.width($layer[0].scrollWidth+18);	// 세로 스크롤이 생겨서 임의로 지정
	$layer.hide();
	
	var $label = jQuery('<a><span>' + selectedItem.text + '</span></a>').prependTo($container)
		.click(function(event) {
			event.stopPropagation();
			$layer.toggle();
		});
	
	jQuery(document.body).click(function() { $layer.hide(); });
	$layer.click(function(event) {
		event.stopPropagation();
		var li = event.target;
		if(li.tagName.toLowerCase() == "a") li = jQuery(li).parent()[0];
		
		var selectedIndex = $layer.children().index(li);
		$select[0].selectedIndex = selectedIndex;
		
		$label.children().html(jQuery(li).children().html());
		
		$layer.hide();
	});
};

/* 
 * ActiveX Editor 세팅 메소드 
 * target : ActiveX Editor 세팅될 객체(div)
 * locale : 현재 사용자 locale 정보(ko, en, jp ..)
 * textarea : ckeditor가 사용되는 textarea 객체
 * type : 작성 모드 = 0:등록, 1:수정
 */
iKEP.createActiveXEditor = function(target, locale, textarea, type){
	//1. type 확인 => 0:등록, 1:수정
	//2. 수정모드인 경우 기존 textarea 설정된 값 activeX editor에 설정하기 위해 값 저장
	var oldData="";
	if(type==1){
		oldData = jQuery(textarea).val();
	}
	
	var inputVal = jQuery(textarea).attr("id");
	//3.기존 ekeditor 사용 textarea 삭제
	jQuery(textarea).remove();
	
	//4. ActiveX Editor 생성(태그프리)
	var clsid =  "976A7D6C-B14C-4e50-A5C3-B43D8C49D8C8";
	//var oldversion = "3,5,1,2022";
	var version = "3,7,1,1045";
	
	//var oldcab = iKEP.getContextRoot()+"/base/tagfree/tweditor_old.cab";
	var cab = iKEP.getContextRoot()+"/base/tagfree/tweditor.cab";
	var env = iKEP.getContextRoot()+"/base/tagfree/env_"+locale+".xml";
	var idx = "twe";
	var applyinitdata = 1;//apply:1
	var editmode = 0;//edit:0
	//var oldkey = "9Gtwaq2cLE4cuUHH0ULVw2teVQo8jKQIrLJdGb8PX3AINiuEVOeEuKyEAvW24VDxVCOBn9dwumYhcXpaPSb1SL4NOhR/MQYB4HjDCIJY7hn3hiy2GgAQps2cBYtZ3yVLHE+qaPCxxKaTTNpIeKLUdhRL3jGDUdLwIQRNUcFk5/c=";
	var key = "t3DOqQC6ZG47oenuS3wdjShaIsE4bhGVLatSlOpL66IYA7bYKvbjk7JPRAI/huD49v+A/I3RiElCN012hU/oUyW1V9gWmjBigrkBAJXQE+zFnsAnYnMoWDIP+syEzrVtcdZovvdJ4sr7dJiAUbBdD/b/gPyN0YhJQjdNdoVP6FN0JNTL7guLtRfFu3QzEIZ63U8wvYvBr1O3lKiP3SVNWFpVuvoRAXHRa2ErG09E0F9DiP1L5n4sDwmK3jnfm9ICdCfr/oIsBuF73LIKOdMld/aBqhT1x5jabCei0dQGLUtDlfItsnqKd/nwRZDznylO";	
	
	var embedTag = '<input name="'+inputVal+'" type="hidden" />' + 
			'<object id="' + idx + '" width="100%" height="100%" CLASSID="CLSID:' + clsid + '" CODEBASE="'+cab+'#version='+version+'">' +
			'<PARAM name="InitFile" value="'+env+'"/>' +
			'<PARAM name="ApplyInitData" VALUE="1"/>' +
			'<PARAM name="Mode" VALUE="0"/>' +
			'<PARAM name="LicenseKey" value="'+key+'">' +
			'</object>';
	
	jQuery(target).html(embedTag);
	
	//5. 기존 데이타 ActiveX Editor 세팅함.
	if(type==1){
		jQuery('input[name="'+inputVal+'"]').val(oldData);
	}

};


/* 
 * ActiveX Editor - DextWebEditor 세팅 메소드 
 * target : ActiveX Editor 세팅될 객체(div)
 * locale : 현재 사용자 locale 정보(ko, en, jp ..)
 * textarea : ckeditor가 사용되는 textarea 객체
 * type : 작성 모드 = 0:등록, 1:수정
 */
iKEP.createActiveXDextEditor = function(target, locale, textarea, type){
	//1. type 확인 => 0:등록, 1:수정
	//2. 수정모드인 경우 기존 textarea 설정된 값 activeX editor에 설정하기 위해 값 저장
	var oldData="";
	if(type==1){
		oldData = jQuery(textarea).val();
	}
	
	var inputVal = jQuery(textarea).attr("id");
	//3.기존 ekeditor 사용 textarea 삭제
	jQuery(textarea).remove();
		
	
	//4. ActiveX Editor 생성(Dext Editor)
	var clsid =  "53A281F1-37FD-4d81-91C8-DBE12C73E58E";
	var version = "2,7,1107,130";
	var cab = iKEP.getContextRoot()+"/base/dext/DEXTWebEditor.cab";
	var idx = "DextHTMLEditor";
	var lang = -1;//apply:1
	var editmode = 0;//edit:0
	var key = "";
    
	if(locale != 'ko'){
		lang = 0;
	}
	
	var embedTag = '<input name="'+inputVal+'" type="hidden" />' + 
			'<object id="' + idx + '" width="100%" height="100%" CLASSID="CLSID:' + clsid + '" CODEBASE="'+cab+'#version='+version+'">' +
			'<PARAM name="Locale" value="'+lang+'"/>' +
			'<PARAM NAME="ShowMenuBar" VALUE="1">' +
			'<PARAM NAME="ShowToolBar" VALUE="1">' +
			'<PARAM NAME="ShowToolBarType" VALUE="-1">' +
			//'<PARAM NAME="MaxImageWidth" VALUE="400">' +
			'<PARAM NAME="DefaultCharset" VALUE="utf-8">' +
			'<PARAM NAME="DefaultFontName" VALUE="돋움">' +
			'<PARAM NAME="DefaultFontSize" VALUE="9">' +
			'<PARAM NAME="UseMedia" VALUE="1">' +
			'<PARAM name="AuthKey" value="'+key+'">' +
			'</object>';
	
	jQuery(target).html(embedTag);
	
	//5. 기존 데이타 ActiveX Editor 세팅함.
	if(type==1){
		jQuery('input[name="'+inputVal+'"]').val(oldData);
	}

};

iKEP.checkBrowser = function(target) {
	var $ = jQuery;
	if($.browser["msie"] === true) {
    	if(parseInt($.browser.version, 10) < 8) {
    		var $target = $(target);
    		var targetPostion = $target.css("position");
    		var $container = $('<div class="notice_iebrowser"/>').appendTo($target.css("position", "relative"));

    		if(window.navigator.userAgent.toLowerCase().indexOf("trident") >= 0) {	// IE8 이상의 호환성보기
				var compatibleUrl = "#a";
    			$container.html("<p>" + iKEPLang.ieCheck.msgCompatible.replace("#{compatibleUrl}", compatibleUrl) + "</p>");
    		} else {	// IE8 미만
    			var upgradeUrl = "#upgrade";
    			var modeUrl = "#mode";
    			$container.html("<p>" +
    				iKEPLang.ieCheck.msgUpgrade.replace("#{upgradeUrl}", upgradeUrl).replace("#{modeUrl}", modeUrl) +
    				"</p>");
    		}
    		
			$('<a href="#a" class="btnClose"><span class="ui-icon ui-icon-closethick">close</span></a>').click(function(event) {
    			$target.css("position", targetPostion.toLowerCase() == "static" ? "" : targetPostion);
    			$container.remove();
    		}).appendTo($container);
    	}
    }
};

(function($){
	$(document).ajaxStart(function(event) {
	}).ajaxSend(function(event, xhr, ajaxOptions) {
		switch(true) {
			case !!ajaxOptions["loadingElement"]:
				switch(typeof(ajaxOptions.loadingElement)) {
					case "string" : $(ajaxOptions.loadingElement).ajaxLoadStart("button");break;
					case "object" :
						for(var type in ajaxOptions.loadingElement) {
							switch(type) {
								case "button":
								case "container":
									$(ajaxOptions.loadingElement[type]).ajaxLoadStart(type);
									break;
							}
						}
						break;
				}
			
				//$(ajaxOptions["loadingElement"]).ajaxLoadStart(ajaxOptions["aniType"]);
				break;
			/*case ajaxOptions.type.toLowerCase() == "post":
				if(jQuery.fn.syncCnt < 0) jQuery.fn.syncCnt = 1;
				else jQuery.fn.syncCnt++;
			
				if(jQuery.fn.syncCnt > 1) return false;
				$(document.body).ajaxLoadStart();
				break;*/
		}
	}).ajaxSuccess(function(event, xhr, ajaxOptions, resultContent) {
	}).ajaxError(function(event, xhr, ajaxOptions, errorType) {
		var SESSION_TIME_OUT_ERROR_CODE = 511;
		if(xhr.status == SESSION_TIME_OUT_ERROR_CODE)
		{
			//document.location.href = iKEP.getContextRoot() + "/loginForm.do?error=2&spring-security-redirect=/portal/main/portalMain.do";
			document.location.href = iKEP.getContextRoot() + "/logout.do";
		}
	}).ajaxComplete(function(event, xhr, ajaxOptions) {
		switch(true) {
			case !!ajaxOptions["loadingElement"]:
				var $targets = [];
				switch(typeof(ajaxOptions.loadingElement)) {
					case "string" : $targets.push($(ajaxOptions.loadingElement)); break;
					case "object" :
						for(var type in ajaxOptions.loadingElement) {
							$targets.push($(ajaxOptions.loadingElement[type]));
						}
						break;
				}
				$.each($targets, function() {
					if(this && this.length > 0) {
						switch(this.css("position")) {
							case "relative" :
							case "absolute" : this.ajaxLoadComplete(); break;
							default : this.parent().ajaxLoadComplete(true);
						}
					}
				});
				break;
			/*case ajaxOptions.type.toLowerCase() == "post":
				jQuery.fn.syncCnt--;
				if(jQuery.fn.syncCnt > 0) return false;
				$(document.body).ajaxLoadComplete();
				break;*/
		}
	}).ajaxStop(function(event) {
	});
	
	$.ajaxSetup({
		cache:false,
		/*beforeSend : function(xhr, options) {
		},
		success : function() {
		},
		complete : function(xhr, status) {
		},*/
		error : function(xhr) {
//			var json = $.parseJSON(xhr.responseText);
//			var message = json["exception"] || xhr.responseText;
//			alert(message);
		}
	});
	
	//jQuery.fn.syncCnt = 0;
	jQuery.fn.ajaxLoadStart = function(type) {
		var $target = this;
		
		if($target[0].tagName.toLowerCase() == "body") {
			var $loading = $('<div/>').appendTo($target)
				.addClass("ajax-body-loading");
		} else {
			var $loading = null;
			switch($target.css("position")) {
				case "relative" :
				case "absolute" :
					$loading = $('<div/>').appendTo($target);
					break;
				default :
					switch($target.parent().css("position")) {
						case "relative" :
						case "absolute" : break;
						default : $target.parent().css("position", "relative");
							
					}
					var position = $target.position();
					$loading = $('<div/>').appendTo($target.parent())
						.css({left:position.left, top:position.top});
			}
			$loading.css({width:$target.innerWidth(), height:$target.innerHeight()})
				.addClass(type == "button" ? "ajax-button-loading" : "ajax-container-loading");
		}
	};
	
	jQuery.fn.ajaxLoadComplete = function(isComplete) {
		$("div.ajax-body-loading, div.ajax-container-loading, div.ajax-button-loading", this).remove();
		if(!isComplete) this.siblings("div.ajax-container-loading, div.ajax-button-loading").remove();
	};
	
//	initiallize processing...	--------------------------------------------------------------
	$(document).ready(function() {
		// dynamic combo setting
		if(iKEP && iKEP.initDynamicCombo) iKEP.initDynamicCombo();

		// form element masking
		if($.mask) {
			$.mask.definitions["?"] = "[9 ]";
			$("input.dateISO").mask("9999-99-99");
			$("input.zipcode").mask("999-999?", {placeholder:"*"});
			$("input.phone").mask("(999)?999-9999");
		}
		
		// tooltip setting
		//if(iKEP && iKEP.initTooltip) iKEP.initTooltip();
		
		// jstree theme path setting
		if($.jstree) {
			$.jstree.defaults.themes.theme = "ikep";	// default theme
			if(location.protocol == "file:") {
				var href = location.href;
				$.jstree._themes = href.substring(0, href.indexOf("/html/")) + "/css/jstree/themes/";	//"/portal/base/css/jstree/themes/";
			} else {
				$.jstree._themes = iKEP.getContextRoot() + "/base/css/jstree/themes/";
			}
		}
	});


/*
	try {	//	jQuery UI datepicker regional setting : korean	--------------------------------------
		if($.datepicker) {
			var langDatepicker = iKEPLang.datepicker;
			$.datepicker.regional[langDatepicker.langCode] = {
				closeText: langDatepicker.btnClose,
				prevText: langDatepicker.btnPrev,
				nextText: langDatepicker.btnNext,
				currentText: langDatepicker.btnCurrentDate,
				monthNames: [langDatepicker.lblMonthNames[0],langDatepicker.lblMonthNames[1],langDatepicker.lblMonthNames[2],langDatepicker.lblMonthNames[3],langDatepicker.lblMonthNames[4],langDatepicker.lblMonthNames[5],langDatepicker.lblMonthNames[6],langDatepicker.lblMonthNames[7],langDatepicker.lblMonthNames[8],langDatepicker.lblMonthNames[9],langDatepicker.lblMonthNames[10],langDatepicker.lblMonthNames[11]],
				monthNamesShort: [langDatepicker.lblMonthNamesShort[0],langDatepicker.lblMonthNamesShort[1],langDatepicker.lblMonthNamesShort[2],langDatepicker.lblMonthNamesShort[3],langDatepicker.lblMonthNamesShort[4],langDatepicker.lblMonthNamesShort[5],langDatepicker.lblMonthNamesShort[6],langDatepicker.lblMonthNamesShort[7],langDatepicker.lblMonthNamesShort[8],langDatepicker.lblMonthNamesShort[9],langDatepicker.lblMonthNamesShort[10],langDatepicker.lblMonthNamesShort[11]],
				dayNames: [langDatepicker.lblDayNames[0],langDatepicker.lblDayNames[1],langDatepicker.lblDayNames[2],langDatepicker.lblDayNames[3],langDatepicker.lblDayNames[4],langDatepicker.lblDayNames[5],langDatepicker.lblDayNames[6]],
				dayNamesShort: [langDatepicker.lblDayNamesShort[0],langDatepicker.lblDayNamesShort[1],langDatepicker.lblDayNamesShort[2],langDatepicker.lblDayNamesShort[3],langDatepicker.lblDayNamesShort[4],langDatepicker.lblDayNamesShort[5],langDatepicker.lblDayNamesShort[6]],
				dayNamesMin: [langDatepicker.lblDayNamesMin[0],langDatepicker.lblDayNamesMin[1],langDatepicker.lblDayNamesMin[2],langDatepicker.lblDayNamesMin[3],langDatepicker.lblDayNamesMin[4],langDatepicker.lblDayNamesMin[5],langDatepicker.lblDayNamesMin[6]],
				weekHeader: 'Wk',
				dateFormat: 'yy.mm.dd',
				firstDay: 0,
				isRTL: false,
				showMonthAfterYear: true,
				yearSuffix: langDatepicker.lblYear
			};
			
			$.datepicker.setDefaults($.datepicker.regional[langDatepicker.langCode]);
			//$.datepicker.setDefaults($.datepicker.regional['']);
		}
	} catch(e) {};
*/	
	
	try {	// validator extend ---------------------------------------------------------------------------
		if($.validator) {
			$.validator.setDefaults({
				errorClass : "valid-error",
				//focusCleanup: true,
				//onkeyup:false,
				//onfocusout: function(element) { $(element).val($.trim($(element).val())).valid(); },
				onfocusout: function(element) {
					var val = $.trim($(element).val());
					if(val == "") $(element).val(val).valid();
					else $(element).valid();
				},
				errorPlacement : function(error, element) {
					var offset = {left:30, top:-4};
					var $container = element.parent();
					if($container[0].tagName.toLowerCase() == "label") $container = $container.parent();
					
					$container.css("position", "relative");
					error.appendTo($container);
					var direction = iKEP.Validator.getArrowClassNameToDirection(error.children("div").attr("class"));
					var position = iKEP.Validator.getDrawPosition(element, error, direction);
					error.css({left:position.left, top:position.top});
					
					$container.children("label.notice").hide();
					//$container.children("label.serverError").remove(arrowClassName);
				}/*,
				invalidHandler : function(form, validator) {
					iKEP.debug("invalidHandler");
					iKEP.debug(arguments);
				},
				showErrors : function(errorMap, errorList) {	// 공통 영역에서 메세징 처리 할때 사용
					iKEP.debug("showErrors");
					iKEP.debug(arguments);
					
					$.each(errorList, function() {	// element, message
					});

					//this.defaultShowErrors();
				}*/,
				submitHandler : function(form) {
					//iKEP.debug("default submit handler");
					//iKEP.Validator.XSS(form);
					form.submit();
				}
			});
			
			$.validator.addMethod("minlength", function(value, element, params){
				return $.trim(value) == "" ? false : (!value || value.length > params);
			}, "invalid message");
			
			$.validator.addMethod("maxlength", function(value, element, params){
				return !value || value.length <= params;
			}, "invalid message");
			
			$.validator.addMethod("rangelength", function(value, element, params){
				return !value || (value.length >= params[0] && value.length <= params[1]);
			}, "invalid message");
			
			$.validator.addMethod("email", function(value, element, params){	// 이메일 주소 체크 : 한글 배제
				return !value || (!(/[가-힣]/).test(value) && (/^[+a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/i).test(value));
			}, "invalid message");
			
			$.validator.addMethod("pattern", function(value, element, params){	// 정규식에 적합하면 허용
				return (params).test(value);	// false - invalid
			}, "invalid message");
			
			$.validator.addMethod("englishName", function(value, element){
				// this : validator, this.optional(element) : false
				// value, element
				return !(/[^A-Za-z\s]+/).test(value);	// false - invalid
			}, "invalid message");
			
			$.validator.addMethod("companyEnglishName", function(value, element){
				// this : validator, this.optional(element) : false
				// value, element
				return !(/[^A-Za-z\)\(\&\s]+/).test(value);	// false - invalid
			}, "invalid message");
			
			$.validator.addMethod("terminology", function(value, element){
				return !(/[^A-Za-z0-9\s]+/).test(value);	// false - invalid
			}, "invalid message");
			
			$.validator.addMethod("phone", function(value, element){
				return !value || (/^\d{2}\d?-\d?\d{3}-\d{4}$/).test(value); 
			}, "invalid message");
			
			$.validator.addMethod("zipcode", function(value, element){
				return !value || (/^\d{3}-\d{3}$/).test(value);
			}, "invalid message");
			
			$.validator.addMethod("date", function(value, element){	// default date type customize
				return !value || (/^\d{4}\.\d{2}\.\d{2}$/).test(value);
			}, "invalid message");
			
			$.validator.addMethod("numberHyphen", function(value, element) {
				return !value || !(/[^\d\-]+/).test(value);
			}, "invalid message");
			$.validator.addMethod("numberComma", function(value, element) {
				return !value || !(/[^\d\,]+/).test(value);
			}, "invalid message");
			$.validator.addMethod("dateGap", function(value, element, params) {	// params[0]의 날짜와 params[1]만큼의 범위 내의 날짜로 지정했는지 체크
				var $ref, ref = params[0] || null;
				if(typeof(ref) == "string") {
					$ref = (ref.indexOf("#") == 0) ? $(ref) : $("[name=" + ref + "]", this.currentForm);
				} else {
					$ref = $(ref);
				}
				
				if($ref.size() > 0) {
					try {
						var date1 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, value);
						var date2 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, $ref.val());
					} catch(e) {}
					
					if(date1 && date2) {
						var gap = (date1 - date2)/(86400*1000);
						return params[1] > 0 ? gap < params[1] : gap > params[1];
					}
				}
				return true;
			}, "invalid message");
			$.validator.addMethod("dateLT", function(value, element, param) {	// param의 날짜 보다 작으면 허용
				var $ref = (typeof(param) == "string") ?
								(param.indexOf("#") == 0) ? $(param) : $("[name=" + param + "]", this.currentForm)
									: $(param);
				try {
					var date1 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, value);
					var date2 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, $ref.val());
				} catch(e) {}
				
				if(date1 && date2)  return date1 < date2;
				return true;
			}, "invalid message");
			$.validator.addMethod("dateLTE", function(value, element, param) {	// param의 날짜 보다 작거나 같으면 허용
				var $ref = (typeof(param) == "string") ?
						(param.indexOf("#") == 0) ? $(param) : $("[name=" + param + "]", this.currentForm)
							: $(param);
				try {
					var date1 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, value);
					var date2 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, $ref.val());
				} catch(e) {}
				
				if(date1 && date2) return date1 <= date2;
				return true;
			}, "invalid message");
			$.validator.addMethod("dateGT", function(value, element, param) {	// param의 날짜 보다 크면 허용
				var $ref = (typeof(param) == "string") ?
						(param.indexOf("#") == 0) ? $(param) : $("[name=" + param + "]", this.currentForm)
							: $(param);
				try {
					var date1 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, value);
					var date2 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, $ref.val());
				} catch(e) {}
				
				if(date1 && date2)  return date1 > date2;
				return true;
			}, "invalid message");
			$.validator.addMethod("dateGTE", function(value, element, param) {	// param의 날짜 보다 크거나 같으면 허용
				var $ref = (typeof(param) == "string") ?
						(param.indexOf("#") == 0) ? $(param) : $("[name=" + param + "]", this.currentForm)
							: $(param);
				try {
					var date1 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, value);
					var date2 = $.datepicker.parseDate($.datepicker.regional[iKEPLang.datepicker.langCode].dateFormat, $ref.val());
				} catch(e) {}
				
				if(date1 && date2) return date1 >= date2;
				return true;
			}, "invalid message");
			$.validator.addMethod("tagWord", function(value, element, params) {
				return true; //!value || !(/[^\w가-힣,\s*]/).test(value);	// 알파벳/숫자, 한글, 콤마, 공백
			}, "invalid message");
			$.validator.addMethod("tagCount", function(value, element, params) {
				var tags = value;
				var tagArray = tags.split(",");
				
				var tmpTagList = new Array();
				var j = 0;
				for(var i=0; i < tagArray.length; i++){
					
					if(tagArray[i].length > 0){
						tmpTagList[j++] = tagArray[i];
					}
				}
				
				return params >= tmpTagList.length && tmpTagList.length > 0;
				
			}, "invalid message");
			$.validator.addMethod("tagDuplicate", function(value, element, params) {
				var tagArray = value.split(",");
				var tlength = tagArray.length;
				var uTagArray = $.unique(tagArray);
				var uTlength = uTagArray.length;
				
				if(tlength == uTlength)
				{
					return true;
				}
				else
				{
					return false;
				}					
				
			}, "invalid message");
			$.validator.addMethod("minlengthByte", function(value, element, params) {
				return value.byteLength() >= params;
			}, "invalid message");
			$.validator.addMethod("maxlengthByte", function(value, element, params) {
				return value.byteLength() <= params;
			}, "invalid message");
			$.validator.addMethod("rangelengthByte", function(value, element, params) {
				var len = value.byteLength();
				return (len >= params[0] && len <= params[1]);
			}, "invalid message");
			$.validator.addMethod("minSize", function(value, element, params) {
				return element.length >= params;
			}, "invalid message");
			
			// duplicate check
			$.validator.addMethod("chkDuplicate", function(value, element){ return false; }, "invalid message");
			$.validator.addMethod("duplicate", function(value, element){ return false; }, "invalid message");
		}
	} catch(e) {}
	
	
	// validator & notice -------------------------------------------------------------------------------------------
	iKEP.Validator = function(form, options) {
		var self = this;
		var validator = null;
		var noticeStyle = "valid-notice";
		var tplMsgContainer = $.template(null, '<label class="${messageClass}">${msg}<div class="${arrowClass}"></div></label>');	// notice message box
		
		var validDelay = 10;
		var offset = {left:30, top:3};
		
		var validOption = {};
		var notice = null;

		function getValidMessage($inputElement) {
			var $container = $inputElement.parent();
			if($container[0].tagName.toLowerCase() == "label") $container = $container.parent();
			
			return $container.children("label.valid-error:visible");
		}
		
		function hideNotice($inputElement) {
			var $container = $inputElement.parent();
			if($container[0].tagName.toLowerCase() == "label") $container = $container.parent();
			$container.children("label."+noticeStyle).hide();
		}
		
		function showNotice($inputElement) {
			var $container = $inputElement.parent();
			if($container[0].tagName.toLowerCase() == "label") $container = $container.parent();
			
			//$container.children("label.serverError").remove();

			var $msgBox =  $container.children("label."+noticeStyle).filter(".elName_" + $inputElement.attr("name"));
			if($msgBox.length > 0) {
				var direction = iKEP.Validator.getArrowClassNameToDirection($msgBox.children("div").attr("class"));
				var position = iKEP.Validator.getDrawPosition($inputElement, $msgBox, direction);
				$msgBox.css({left:position.left, top:position.top})
					.show();
			} else {
				var noticeItem = notice[$inputElement.attr("name")];
				var noticeInfo = {msg:noticeItem, direction:"top"}; // 메세지 정보 셋팅
				if(typeof(noticeItem) == "object") {
					noticeInfo.msg = noticeItem["message"] || "";
					noticeInfo.direction = noticeItem["direction"] || "top";
				 }
				
				$container.css("position", "relative");
				$msgBox = $.tmpl(tplMsgContainer, {
					messageClass:noticeStyle + " elName_" + $inputElement.attr("name"),
					arrowClass:iKEP.Validator.getArrowClassName(noticeInfo.direction),
					msg:noticeInfo.msg
				}).appendTo($container)
					.show();
				var position = iKEP.Validator.getDrawPosition($inputElement, $msgBox, noticeInfo.direction);
				$msgBox.css({left:position.left, top:position.top});
			}
		}
		
		function generateServerValid() {
			var messages = {};
			$(form).find("label.serverError:not(:empty)").each(function() {
				var $label = $(this);
				if($.trim($label.html())) {
					var validItem = $label.attr("for");
					var message = validOption.messages && validOption.messages[validItem];
					var direction = (message && message["direction"]) || "top";
					messages[validItem] = $label.html() + iKEP.Validator.getArrowTag(direction);
				}
				$label.remove();
			});

			if(!$.isEmptyObject(messages)) {
				validator.showErrors(messages);
			}
		}
		
		this.getValidator = function() {};
		this.showErrors = function(errorItems) {
			var messages = {};
			$.each(errorItems, function(index, error) {
				var message = validOption.messages && validOption.messages[error.field];
				var direction = (message && message["direction"]) || "top";
				
				messages[error.field] = error.defaultMessage + iKEP.Validator.getArrowTag(direction);;
			});
			
			if(!$.isEmptyObject(messages)) {
				validator.showErrors(messages);
			}
		};
		
		$.each(options, function(key) {
			switch(key) {
				case "invalidServer" : break;
				case "notice" : notice = this; break;
				default :
					if(key == "messages") {	// 디자인 처리를 위해 문구 내용에 arrow를 추가함.
						var messages = this;
						for(var item in messages) {
							if(typeof(messages[item]) == "object") {
								var arrowTag = iKEP.Validator.getArrowTag(messages[item]["direction"] || "top");
								for(var msg in messages[item]) {
									if(typeof(messages[item][msg]) == "string" && msg != "direction") {
										messages[item][msg] += arrowTag;
									}
								}
							} else {
								messages[item] += iKEP.Validator.getArrowTag("top");
							}
						}
					}
					validOption[key] = this;
			}
		});
		
		if(notice != null) {
			var elements = [];
			$.each(notice, function(elName) {
				elements.push("[name=" + elName + "]");
			});
			
			$(elements.join(","), form).bind("keyup", function(event) {
				var $el = $(this);
				setTimeout(function(){
					var $errorMessage = getValidMessage($el);
					
					if($errorMessage.length == 0 && !$el.val()) showNotice($el);
					else hideNotice($el);
				}, validDelay);
			}).bind("focusin", function(event) {
				var $el = $(this);
				setTimeout(function(){
					var $errorMessage = getValidMessage($el);
					if($errorMessage.length == 0 && !$el.val()) showNotice($el);
				}, validDelay);
			}).bind("focusout", function(event) {
				var $el = $(this);
				setTimeout(function() { hideNotice($el); }, validDelay);
			});
		}
		
		validator = $(form).validate(validOption);
		//if(options["invalidServer"] == true) $(form).valid();
		generateServerValid();
	};
	$.extend(iKEP.Validator, {	// static method
		getArrowClassNameToDirection : function(className) {
			var result = "top";
			switch(className) {
				case "arrow_t" : result = "top"; break;
				case "arrow_b" : result = "bottom"; break;
				case "arrow_l" : result = "left"; break;
				case "arrow_r" : result = "right"; break;
				case "arrow_tr" : result = "top-right"; break;
				case "arrow_br" : result = "bottom-right"; break;
			}
			return result;
		},
		getArrowClassName : function(direction) {
			var result = "arrow_t";
			switch(direction) {
				//case "top" : result = "arrow_t"; break;
				case "bottom" : result = "arrow_b"; break;
				case "left" : result = "arrow_l"; break;
				case "right" : result = "arrow_r"; break;
				case "top-right" : result = "arrow_tr"; break;
				case "bottom-right" : result = "arrow_br"; break;
			}
			return result;
		},
		getDrawPosition : function($input, $msgBox, direction) {
			var offset = {left:6, top:7};
			var inputPosition = $input.position();
			var position = {left:inputPosition.left + 30, top:inputPosition.top};

			switch(direction) {
				case "top" : position.top = inputPosition.top - $msgBox.outerHeight() - offset.top - 2; break;
				case "bottom" : position.top = inputPosition.top + $input.outerHeight() + offset.top; break;
				case "left" : position.left = inputPosition.left - $msgBox.outerWidth() - offset.left;break;
				case "right" : position.left = inputPosition.left + $input.outerWidth() + offset.left; break;	// layout이 유동적일 때 input의 사이즈가 유동적 이면 사용하지 않도록 한다.
				case "top-right" :	// layout이 유동적일 때 input의 사이즈가 유동적 이면 사용하지 않도록 한다.
					position.top = inputPosition.top - $msgBox.outerHeight() - offset.top - 2;
					position.left = inputPosition.left + $input.outerWidth() - $msgBox.outerWidth();
					break;
				case "bottom-right" :	// layout이 유동적일 때 input의 사이즈가 유동적 이면 사용하지 않도록 한다.
					position.top = inputPosition.top + $input.outerHeight() + offset.top;
					position.left = inputPosition.left + $input.outerWidth() - $msgBox.outerWidth();
					break;
			}
			return position;
		},
		getArrowTag : function (direction) {
			var className = iKEP.Validator.getArrowClassName(direction);
			return '<a onclick="iKEP.Validator.hideMessage(this); return false;" class="close"></a><div class="' + className + '"></div>';
		},
		hideMessage : function(el) {
			$(el).parent().hide();
		},
		XSS : function(form) {
			var replaceItems = [
				{source:"<", dest:"&lt;"},
				{source:">", dest:"&gt;"}
			];
			
			$.each($(form).serializeArray(), function() {
				var value = this.value;
				$.each(replaceItems, function() { value = value.replaceAll(this.source, this.dest); });
				$("[name=" + this.name + "]", form).val(value);
			});
		}
	});



	// main menu create --------------------------------------------------------------------------- 2011.04.01 사용되지 않음. alan
	iKEP.setMainMenu = function() {
		function setAnchor(item) {
			var html = ['<a'];
			//if(item.url) html.push(' href="' + item.url + (item.url.indexOf("?") > -1 ? "&" : "?") + 'mn=' + (parseInt((item.code/10))*10) + '"');
			html.push(' href="' + (item.url ? item.url : '#') + '"');
			html.push('>');
			html.push(item.title);
			html.push('</a>');
			return html.join("");
		}
				
		var currMenu = "";//<%=request.getParameter("mn")%>
		var menuItems = [
	 		{code:"00", title:"Home", url:"./main.html"},
	 		{code:"20", title:"Work", url:"./main.html", items:[
	 			{code:"21", title:"work 1", url:"./main.html"},
	 			{code:"22", title:"work 2", url:"./main.html"},
	 			{code:"23", title:"work 3", url:"./main.html"},
	 			{code:"24", title:"work 4", url:"./main.html"}
	 		]},
	 		{code:"30", title:"Messages", url:"./main.html", items:[]},
	 		{code:"40", title:"Collaboration", url:"./main.html", items:[]},
	 		{code:"50", title:"Knowledge", url:"./main.html", items:[
	 			{code:"51", title:"Knowledge 1", url:""},
	 			{code:"52", title:"Knowledge 2", url:""},
	 			{code:"53", title:"Knowledge 3", url:"./main.html"},
	 			{code:"54", title:"Knowledge 4", url:""}
	 		]},
	 		{code:"60", title:"People", url:"./main.html"},
	 		{code:"70", title:"Innovation", url:"./main.html"}
	 	];

		var $divTopMenu = $("#divTopMenu");
		var html = new Array();
		
		html.push('<ul class="topMenu">');
		$.each(menuItems, function(index, item) {
			if(item.code == "00") html.push('<li>');	// class="noChild"
			else html.push(currMenu == item.code ? '<li class="active">' : ('<li' + (item.items && item.items.length > 0 ? ' class="child"' : '') + '>'));	//class="noChild"
			
			html.push(setAnchor(item));
			if(item.items && item.items.length > 0) {
				html.push('<ul>');
				$.each(item.items, function(index, subitem) {
					html.push('<li>' + setAnchor(subitem) + '</li>');
				});
				html.push('</ul>');
			}
			html.push('</li>');
		});
		html.push('</ul>');
		$divTopMenu.html(html.join(""));
		
		$("ul.topMenu>li", $divTopMenu)
			.bind("mouseover", function(event) {
				event.preventDefault();
				//$(this).addClass("hover");//.children().eq(0).addClass("active");
				if($(this).children("ul").length) $(this).addClass("hover-child");
				else $(this).addClass("hover");
				$(this).find("ul").show();
			})
			.bind("mouseout", function(event) {
				event.preventDefault();
				//$(this).removeClass("hover");//.children().eq(0).removeClass("active");
				if($(this).children("ul").length) $(this).removeClass("hover-child");
				else $(this).removeClass("hover");
				$(this).find("ul").hide();
			});
	};
	
	iKEP.setToggleLeftMenu = function($container) {
		var contentMaring = 27;
		var $leftMenu = $container.children().eq(0);
		var $content = $container.children().eq(1);

		$leftMenu.data("width", $leftMenu.width());
		$content.data("marginLeft", parseInt($content.css("margin-left"), 10));
		
		var $toggle = $('<div/>').appendTo('#leftMenu');
		var $toggleButton = $('<a href="#a"></a>').click(function() {
			if($leftMenu.is(":visible")) {
				$leftMenu.hide();
				$content.css("margin-left", contentMaring+"px");
				$(this).addClass("lefemenu_arrowRight")
					.removeClass("lefemenu_arrowLeft")
					.parent().appendTo($content).css({right:"", left:"-"+contentMaring+"px"});
				$("body").removeClass("bg_leftline");
			} else {
				$content.css("margin-left", $content.data("marginLeft")+"px");
				$leftMenu.show();
				$(this).addClass("lefemenu_arrowLeft")
					.removeClass("lefemenu_arrowRight")
					.parent().appendTo($leftMenu).css({right:0, left:""});
				$("body").addClass("bg_leftline");
			}
		}).addClass("lefemenu_arrowLeft");
		$toggle.css({position:"absolute", top:"10px", right:0})
			.append($toggleButton);
	};
	
	// left menu event set ------------------------------------------------------------------------
	iKEP.setLeftMenu = function() {
		$('body').addClass("bg_leftline");
		$('#leftMenu li:not(.leftTree li)').children('a')
		.click(function(event) {
			var $thisParent = $(this).parent();
			var $this = $(this);
			//console.log($thisParent.attr('class'));

			$('#leftMenu li:not(.leftTree li)').removeClass('licurrent');
			$thisParent.parents('#leftMenu li').addClass('licurrent');
			$thisParent.addClass('licurrent');

			if($thisParent.hasClass('no_child') || $thisParent.find('li').size() === 0) {
				return true;
			}
			
			if($thisParent.children('ul:visible, div:visible').size() > 0) {
				$thisParent.children('ul, div').hide();
				$thisParent.removeClass('opened');
			} else {
				$thisParent.children('ul, div').show();
				$thisParent.addClass('opened');	
			}
		});
		
		iKEP.setToggleLeftMenu($("#leftMenu").parent());
	};
	
	// left collaboration menu event set ------------------------------------------------------------------------
	iKEP.setLeftCollaborationMenu = function() {
		$(".leftMenu_coll .coll_menu a").click(function() {
			var $thisParent = $(this).parent();
			$thisParent.siblings('.selected').removeClass('selected');
			$thisParent.addClass('selected');
		});
		
		$(".leftMenu_coll > ul a").click(function() {
			var $thisParent = $(this).parent();
			
			$('.leftMenu_coll > ul .licurrent').removeClass('licurrent');
			$thisParent.parents('.leftMenu_coll li').addClass('licurrent');
			$thisParent.addClass('licurrent');
			
			if($thisParent.children('ul').size() === 0) {
				return true;
			}		
			
			if($thisParent.children('ul:visible').size() > 0) {
				$thisParent.children('ul, div').hide();
				$thisParent.removeClass('opened');
			} else {
				$thisParent.children('ul, div').show();
				$thisParent.addClass('opened');	
			}
		});
		
		$(".leftMenu_coll > .coll_box a").click(function() {
			$(this).parents('tbody').find('span.selected').removeClass('selected');
			$(this).parents('tr').find('span').addClass('selected');
		});
	};



	// tree list source to tree item convert method -------------------------------------------------------------
	iKEP.arrayToTreeData = function(list, topItem, isCreateId, isSimple) {
		var isUserPriority = iKEP.config.jstree.isUserPriority || false;
		
		var treeItems = [];
		
		this.createTreeItem = function (item, options, isSimple) {
			var attrData = {type:item.type};
			var itemID = "treeItem_" + (item.type == "group" ? item.code : item.id);
			switch(item.type) {
				case "group" : 
					$.extend(attrData, {code:item.code, parent:item.parent, groupTypeId:item.groupTypeId, hasChild:item.hasChild, searchSubFlag:item.searchSubFlag}); 
					break;
				case "user" : 
					if(isSimple) {
						$.extend(attrData, {userName:item.userName, id:item.id, group:item.parent, jobTitleName:item.jobTitleName, searchSubFlag:item.searchSubFlag,leader:item.leader}); 
					}
					else {
						$.extend(attrData, {userName:item.userName, id:item.id, group:item.parent, empNo:item.empNo, email:item.email, jobTitleName:item.jobTitleName, teamName:item.teamName, mobile:item.mobile, searchSubFlag:item.searchSubFlag ,leader:item.leader}); 
					}
					break;
				case "addruser" : 
					if(isSimple) {
						$.extend(attrData, {userName:item.userName, id:item.id, group:item.parent, jobTitleName:item.jobTitleName, searchSubFlag:item.searchSubFlag,leader:item.leader});
					}
					else {
						$.extend(attrData, {userName:item.userName, id:item.id, group:item.parent, empNo:item.empNo, email:item.email, jobTitleName:item.jobTitleName, teamName:item.teamName, mobile:item.mobile, searchSubFlag:item.searchSubFlag,leader:item.leader});
					}
					break;
				default : 
					attrData = $.extend({}, item);
					break;
			}
			
			var attr = {
				data:JSON.stringify(attrData),
				code:item.code
			};
			if(isCreateId == true) attr["id"] = itemID;
			
			var data = {title : item.name, icon : item["icon"] || ""};	// 입력 데이타에 icon이 있으면 해당 아이콘으로 설정한다.
			if(!data.icon) {
				
				if(item.type == "group"){
					 data.icon = "dept";
				}else if(item.type == "user"){
					if(item.leader==1){
						data.icon = "personLeader";
					}else{
						data.icon = "person";
					}
					 
				}else if(item.type == "addruser"){
					 data.icon = "person";
				}
				
				/*
				switch(item.type) {
					case "group" : data.icon = "dept"; break;
					case "user" : data.icon = "person"; break;
					case "addruser" : data.icon = "person"; break;
					//case "teamopenoff" : data.icon = "teamopenoff"; break;
					//case "teamopenon" : data.icon = "teamopenon"; break;
				}
				*/
			}
			var result = {data:data, attr:attr};
			if(item.type != "user" && item.hasChild > 0) {
				result.state = "closed";
			}
			return $.extend(result, options);	// state:open/closed, , icon:css class name
		};
		this.setData = function(item, list) {
			var childOffset = 0;	// remove child count
			for(var i=0;i<list.length;i++) {
				if(list[i] != null && item.attr.code === list[i].parent) {
					childOffset++;
					var childItem = this.createTreeItem(list[i], null, isSimple);
					
					for(var j=i;j<list.length-1;j++) { list[j] = list[j+1]; }
					list.length--;
					i--;
					
					i -= this.setData(childItem, list);
					
					if(item.children == undefined) item["children"] = [];
					
					if(isUserPriority) this.push(item.children, childItem);
					else item.children.push(childItem);
				}
			}
			return childOffset;
		};
		this.push = function(children, item) {
			var len = children.length;
			if(len == 0 || (item.data["icon"] != "person" && item.data["icon"] != "personLeader")) {	// 사원이 아니면 최 하위로 추가
				children.push(item);
			} else {	// 사원이면....
				//children.push(null);
				for(var i=len; i>=0; i--) {
					//iKEP.debug("i = " + i);
					if(i == 0) children[i] = item;	// 전체 목록에 사원이 없으면 최상위에 해당 사원을 추가
					else {
						if(children[i-1].data["icon"] == "person"||children[i-1].data["icon"] == "personLeader") {	// 이전 아이템이 사원이면 해당 사원 밑에 추가
							children[i] = item;
							break;
						} else {	// 사원이 위로 올라 가기 위해 해당 조직을 아래로 하나씩 아래로 내림
							children[i] = children[i-1];
						}
					}
				}
			}
		};
		
		if(topItem) {
			var treeItem = this.createTreeItem(topItem, null, isSimple);//, {state:"open"}
			this.setData(treeItem, list);
			treeItems.push(treeItem);
		} else {
			while(list.length > 0) {
				var item = list.shift();
				var treeItem = this.createTreeItem(item, null, isSimple);//, {state:"open"}
				this.setData(treeItem, list);
				
				if(isUserPriority) this.push(treeItems, treeItem);
				else treeItems.push(treeItem);
				
			}
		}
		
		return treeItems;
	};
	
	// ikepGallery view 영역을 top 프레임으로 설정하기 위해 추가
	iKEP.showGallery = function(items) {
		if(top != window) {
			top.iKEP.showGallery(items);
			return false;
		}
		
		$(items).ikepGallery();
	};
	
	// iKEP.Dialog를 최상위 윈도우(프레임)에서 뛰우기 위해 추가
	iKEP.showDialog = function(options) {
		if(window != top) {
			top.iKEP.showDialog(options);
			return false;
		}
		
		var dialog;
		if(options["target"]) {
			var container = options.target;
			options.target = undefined;
			options.close = function() { $(this).dialog("destroy"); };
			
			dialog = $(container).appendTo(document.body)
				.dialog(options);
		} else {
			var offset = {width:-10, height:-10};
			var viewSize = {width:$(document).width()+offset.width, height:$(document).height()+offset.height};
			if(options["width"] && options.width > viewSize.width) options.width = viewSize.width;
			if(options["height"] && options.height > viewSize.height) options.height = viewSize.height;
			
			if(options["isDestroy"] != true) options.isDestroy = true;
			dialog = new iKEP.Dialog(options);
		}
		
		return dialog;
	};

	//dialog wrapper
	iKEP.Dialog = function(options) {
		var dialog = this;
		this.container = $("<div/>").appendTo(document.body);
		this.destroy = false;
		this.fnCallback = null;
		this._options = $.extend({
			title : "Dialog Window",
			maxHeight : 600,
			maxWidth : 980,
			width : 300,
			modal : false,
			isDestroy : true,
			params : null,
			callback : null,
			scroll : "auto"
		}, options);
		
		if(this._options.url) {
			this._options.create = function() {
				setTimeout(function(){dialog.container.ajaxLoadStart();}, 1);	// dialog의 사이즈를 디텍트 하지 못해서...
				var ifrm = $('<iframe style="width:100%; height:98%; border-width:0;" scrolling="' + dialog._options.scroll + '" frameborder="0" src="' + dialog._options.url + '"/>').appendTo(dialog.container);
				ifrm.bind("load", function() {
					setTimeout(function() {dialog.container.ajaxLoadComplete();}, 10);
					//ifrm.height(ifrm.contents().height());
					
					var frmBody = ifrm[0].contentWindow || ifrm[0].contentDocument;
					if(frmBody.fnCaller) frmBody.fnCaller(dialog._options.params, dialog);
					//frmBody.fnResult = _options.callback;
					
//					setTimeout(function() {	// dialog resize
//						var frmHeight = $(frmBody.document).height();
//						if(dialog.container.dialog("option", "height") > frmHeight || dialog.container.dialog("option", "maxHeight") > frmHeight) {
//							dialog.container.dialog("option", "height", frmHeight);
//						}
//					}, 100);
				});
				//$(this).load(_options.url);
			};
		}
		
		if(this._options.isDestroy == true) {
			this._options.close = function() {
				if(dialog.destroy == false) {
					dialog.close();
				}
			};
		}
		
		this.fnCallback = this._options.callback;

		this.container.dialog(this._options);
		
		if(this._options.titleClass) {
			var dialog = this;
			var divWrap = dialog.container.parent();
			divWrap[0].firstChild.className = this._options.titleClass;
		}
	};
	iKEP.Dialog.prototype.callback = function(res) {
		if(this.fnCallback)
			this.fnCallback(res);
	};
	iKEP.Dialog.prototype.open = function() {
		this.container.dialog("open");
	};
	iKEP.Dialog.prototype.close = function() {
		var dialog = this;
		var divWrap = dialog.container.parent();
		
		if(dialog.container.dialog("isOpen")) {
			dialog.container.dialog("close");
		}
		
		setTimeout(function(){
			if(dialog._options.isDestroy == true && dialog.destroy != true) {
				dialog.destroy = true;
				//dialog.container.dialog("destroy");
				
				setTimeout(function() { 
					$.each(dialog.container.find("iframe"), function() {
						$(this).remove();
					});
					
					dialog.container.remove();
				}, 1000);
			}
		}, 0);
	};

	//accordion -----------------------------------------------------------------
	iKEP.Accordion = function(options) {
		this.parent = $(options.parent) || document.body;
		this.container = null;
		
		var _options = $.extend({
			active : 0,
			autoHeight : false,
			collapsible : true,
			animated : "default",
			cache : false
		}, options);
		
		var loadContents = function(itemInfo, divItem, itemIndex) {
			if(itemInfo.url && (_options.cache == false || itemInfo.loaded != true)) {
				$.get(itemInfo.url, function(result) {
					$(divItem).html(result);
					itemInfo.loaded = true;
					
					container.accordion("resize");
					if(_options.load) {
						_options.load({panel:divItem, index:itemIndex, loadContents:result});
					}
				});
			}
		};

		switch(true) {
			case _options.container != undefined && _options.container != "" :
				this.container = $(_options.container);
				this.container.accordion(_options);
				break;
			case _options.items && _options.items.length > 0 :
				this.container = $("<div/>").appendTo(this.parent);
			
				var container = this.container;
				var items = _options.items;
				$.each(items, function(idx, item) {
					$("<h3/>").appendTo(container)
						.append("<a/>").children()
						.append(item.title);
					$("<div/>").appendTo(container)
						.append("content");
				});
				
				var initialItemIndex = _options.active || 0;
				if(items[initialItemIndex].url) {
					this.container.bind("accordioncreate", function() {
						loadContents(items[initialItemIndex], $(this).children("div").eq(initialItemIndex), initialItemIndex);
					});
				}
				
				this.container.bind("accordionchange", function(event, ui) {
					if(ui.options.active !== false) {	// collapsible이 true 이면 active 가 false
						var activeItemIndex = container.children("div").index(ui.newContent);
						loadContents(items[activeItemIndex], ui.newContent, activeItemIndex);
					}
				});
				
				this.container.accordion(_options);
				break;
		}
		
		this.activate = function(idx) {
			this.container.accordion("activate", idx);
		};
		
		this.destroy = function() {
			this.container.accordion("destroy");
			this.container = null;
			this.parent.empty();
		};
	};
	
	
	
	// dropdown tabs ------------------------------------------------------------------------
	iKEP.DropdownTabs = function(tabs, dropdownMenu, offset) {
		var $tabs = jQuery(tabs);
		var $submenu = jQuery(dropdownMenu);
		
		offset = offset || 0;
		
		var isShowTabSubmenu = false;
		
		this.selectTab = function(tabIndex) {
			isShowTabSubmenu = true;
			$tabs.tabs("select", tabIndex);
		};
		
		$tabs.tabs({
			collapsible : true,
			select : function(event, ui) {	// ui : index, panel, tab
				var selectedIndex = jQuery(this).tabs("option", "selected");	// 이전에 선택되어 있던 탭

				if(isShowTabSubmenu == false) {
					var $menuItem = $submenu.children().eq(ui.index);
					$menuItem.siblings().hide();
					if($menuItem.is(":empty") == false && jQuery.trim($menuItem.html()) != "") {	// 서브메뉴가 있으면...
						if($menuItem.is(":hidden")) {
							var pos = jQuery(ui.tab).offset();
							$menuItem.css("left", pos.left + offset).show();
						} else $menuItem.hide();
						return false;
					}
				}

				isShowTabSubmenu = false;
				if(selectedIndex == ui.index) return false;
			}
		});
		
		$submenu.click(function(event) {
			if(event.target.tagName.toLowerCase() == "a") {
				
				event.stopPropagation();
				var $menuItem = $(event.target).parents("div:first");
				var tabIndex =  $menuItem.prevAll().length;
				if(tabIndex > -1) {
					isShowTabSubmenu = true;
					$tabs.tabs("select", tabIndex);
				}
				jQuery(document.body).trigger("click");
			}
		});
		
		jQuery(document.body).click(function(event) { $submenu.children().hide(); });
	};
		
	
	// Slider ---------------------------------------------------------------------------------
	iKEP.Slider = function(itemArea, buttons) {
		var $container = $(itemArea).css({overflow:"hidden", position:"relative"});
		var $itemContainer = null;
		
		var containerWidth = $container.width();
		var totItemWidth = 0;
		
		function init() {
			var $items = $container.children();
			if($items.length == 0) return false;
			
			var containerHeight = 0;
			$items.each(function() {
				totItemWidth += $(this).outerWidth();
				var height = $(this).outerHeight();
				if(containerHeight < height) containerHeight = height;
			});
			
			$container.height(containerHeight).children().wrapAll('<div/>');
			$itemContainer = $container.children().css({
				position : "absolute",
				left : 0,
				top : 0,
				height : containerHeight,
				width : totItemWidth
			});
			
			setButtonEvent();
			$(window).resize(resizeContainer);
		}
		
		function setButtonEvent() {
			buttons && $(buttons.right).click(function() {
				if($itemContainer.filter(":animated").length > 0) return false;
				if(!$(this).hasClass(getHoverClassName($(this))))  return false;
				
				enableButton($(buttons.left)[0], true);
				var position = $itemContainer.position();

				var moveLeft = position.left - containerWidth;	// 보여지는 영역 만큼 이동해야할 거리
				if(((moveLeft - containerWidth)*-1) > $itemContainer.width()) {	// 이동하고 났을때 위치가 보여지는 영역의 right point보다 멀리가면 보정
					moveLeft = (moveLeft*-1) - ($itemContainer.width() - position.left);
				
					enableButton(this, false);
				}

				if(position.left != moveLeft) {
					$itemContainer.animate({left:moveLeft+"px"}, "fast");
				}
			});
			
			buttons && $(buttons.left).click(function() {
				if($itemContainer.filter(":animated").length > 0) return false;
				if(!$(this).hasClass(getHoverClassName($(this))))  return false;
				
				enableButton($(buttons.right)[0], true);
				var position = $itemContainer.position();
				
				var moveLeft = position.left + containerWidth;	// 보여지는 영역 만큼 이동해야할 거리
				if(moveLeft >= 0) {	// 이동하고 났을때 위치가 보여지는 영역의 right point보다 멀리가면 보정
					moveLeft = 0;
				
					enableButton(this, false);
				}
				
				if(position.left != moveLeft) {
					$itemContainer.animate({left:moveLeft+"px"}, "fast");
				}
			});
			
			//if(buttons && buttons.right) enableButton($(buttons.right)[0], true);
			resizeContainer();
		}
		
		function getHoverClassName($btn) {
			var classs = $btn.attr("class").split(" ");
			return classs[0]+"-hover";
		}
		
		function enableButton(btn, flag) {
			var $btn = $(btn);
			
			var hoverClass = getHoverClassName($btn);
			if(flag == false) { $btn.removeClass(hoverClass); }
			else { $btn.addClass(hoverClass); }
		}
		
		function resizeContainer() {
			containerWidth = $container.width();
			var position = $itemContainer.position();

			enableButton($(buttons.left)[0], position.left < 0 ? true : false);
			enableButton($(buttons.right)[0], (totItemWidth + position.left) > containerWidth ? true : false);
			
			if(position.left < 0 && totItemWidth + position.left < containerWidth) {
				$itemContainer.css({left : containerWidth - totItemWidth});
			}
		}
		
		init();
	};
		
	
	// mopslider ---------------------------------------------------------------------
	iKEP.MopSlider = function(el, options) {
		var $el = $(el);
		options = $.extend({width:0, height:0, sliderButtonWidth:50, margin:20, type:"paper", shuffle:0}, options);
		
		if(options.width <= 0) {
			var itemWidth = $el.children().width();
			var itemCount = $el.children().length;
			
			options.width = itemWidth * itemCount + (options.margin * (itemCount+1));
			var wrapWidth = $el.parent().width();
			if(options.width > wrapWidth) options.width = wrapWidth;
		}
		
		if(options.height <= 0) options.height = $el.height() || 0;
		
		$(el).mopSlider({
			w : options.width,
			h : options.height,
			sldW : options.width - 42,//sliderBarWidth
			btnW : options.sliderButtonWidth,
			itemMgn : options.margin,
			type : options.type,
			shuffle : options.shuffle,
			imgBasePath : iKEP.getContextRoot() + "/base/images/"
		});
	};
	
	

	//dynamic combobox event -----------------------------------------------------------------
	iKEP.initDynamicCombo = function() {
		var dynamicCombo = $("select[ref]");
		$.each(dynamicCombo, function(idx, select) {
			var $select = $(select);
			var selector = $select.attr("ref");
			var $parent = (selector.indexOf("#") === 0) ? $(selector) : $("select[name=" + selector + "]", select.form);
			
			if($select.attr("url") && $parent.length == 1) {
				var data = {};
				data[selector.replace("#", "")] = $parent.val();
				
				$parent.change(function() {
					$.getJSON("ajax_result/dynamic_combo_items.json", data, function(json) {
						$select.empty();
						$.each(json, function(i, item) {
							$("<option/>").attr("value", item.value)
								.append(item.label)
								.appendTo(select);
						});
					});
				});
			}
			
			$select.removeAttr("ref");
		});
	};

	//tooltip -----------------------------------------------------------------------------
	iKEP.initTooltip = function() {
		var showTooltip = function(target, tooltipBox) {
			var offset = 3;
			var pos = target.position();
			
			var left = pos.left;
			var top = pos.top;
			
			var doc = $(document);
			var body = $(document.body);
			
			if((body.width() + doc.scrollLeft()) >= (left + target.width() + tooltipBox.outerWidth())) {	// 옆(left)으로 보여줘도 가리지 않는 경우
				left = parseInt(left + target.width() + offset, 10);
				top = parseInt(top, 10);
			} else {	// 아래쪽으로 보여주는 경우
				left = parseInt(left, 10);
				top = parseInt(top + target.height() + offset, 10);
				
				if(body.height() + doc.scrollTop() < top + tooltipBox.outerHeight()) {	// 위쪽에 보여주는 경우
					top = parseInt(pos.top - (tooltipBox.outerHeight() + offset - 2), 10);
				}
			}
			
			tooltipBox.css({left:left+"px", top:top+"px"}).show();
		};
		
		var tooltipBox = $('<div class="tooltip" style="min-width:100px; max-width:400px;"/>')
							.appendTo(document.body);
		$("[tooltip]").live("hover", function(event){
			var el = $(event.target);
			var str = el.attr("tooltip");
			
			switch(event.type) {
				case "mouseenter" :
					switch(true) {
						case str.substring(0, 4) == "id=#" :
							tooltipBox.append($(str.substring(3, str.length)).show());
							el.attr("tooltip", tooltipBox.html());
							break;
						case str.substring(0, 4) == "url=" :
							$.get(str.substring(4, str.length), function(result){
								tooltipBox.html(result);
								el.attr("tooltip", result);
								showTooltip(el, tooltipBox);
							});
							return false;
							break;
						default : tooltipBox.html(str);
					}
					
					showTooltip(el, tooltipBox);
					break;
				case "mouseleave" :
					tooltipBox.html("")
						.hide();
					break;
			}
		});
	};
	
	// tagging ------------------------------------------------------------------------------------------------------
	iKEP.tagging = new (function(){
		var contextRoot = iKEP.getContextRoot();
		var urlCreate = contextRoot + "/support/tagging/createTagAjax.do";
		var urlSearch = contextRoot+"/support/tagging/tagSearchAjax.do";
		var tmpHtml = "";
		var tmpId = "";
		var tmpType = "";
		var callbackId = "tagResult";		//기본 콜백 ID
		
		var tagSelf = this;
		
		var tagValidOptions = {
			rules : {
				tagName : {
					required : true
					,maxlength  : 100
					,tagCount :10
					,tagDuplicate: true
					,tagWord : true
				}
			},
			messages : {
				tagName : {
					required : iKEPLang.tagging.required
					,maxlength : iKEPLang.tagging.maxlength
					,tagCount  : iKEPLang.tagging.tagCount
					,tagDuplicate  : iKEPLang.tagging.tagDuplicate
					,tagWord  : iKEPLang.tagging.tagWord
				}
			},
			notice : {
				tagName : iKEPLang.tagging.tagNotice
			},
			submitHandler : function(form) {
				
				tagSelf.tagAddAction(form.tagItemId.value, form.tmpType.value);
				
			}
		};
		
		this.tagFormView = function(itemId, type){
			var extItemId = itemId.replace("\.", "");
			//다른 입력폼 닫기
			if(!type || type == 'undefined'){
				if(tmpId && tmpId != extItemId){this.tagCancle(tmpId);}
			} else {
				if(tmpType && (tmpType != type )){this.tagCancle(tmpId, tmpType);}
			}
			
			tmpId = extItemId;
			tmpType = type;
			
			var $form = '';
			var formFrameId = '';
			
			if(!type || type == 'undefined'){
				
				$form = $('#tagForm_'+extItemId);
				formFrameId = "tagFormFrame_"+extItemId;
			} else {
				
				$form = $('#tagForm_'+extItemId+'_'+type);
				formFrameId = "tagFormFrame_"+extItemId+"_"+type;
			}
			
			tmpHtml = $form.find('div').html();
			
			var tagText = "";
			
			if(!type || type == 'undefined'){
				$form.find('a').not(":last").each(function(i){ 
					if(i != 0)tagText += ",";
					tagText += $.trim($(this).text());
				});
			} else {
				$form.find('p').each(function(i){ 
					if(i != 0)tagText += ",";
					tagText += $.trim($(this).text());
				});
			}
			
			var html = '';
			
			if(!type || type == 'undefined'){
				
				html = '<table summary="tag form Table">' +
						'<caption></caption>' +
						'<tr>' +
							'<td><div><input name="tagName" class="inputbox" type="text" value="'+tagText+'" title="tag name"/><div></td>' +
							'<td width="100" class="textRight"><div style="padding-top:9px">' +
								'<ul>' +
									'<li><a id="tagLinkBut" class="button_s" href="#a"><span style="margin-right:6px;">'+iKEPLang.tagging.save+'</span></a></li>' +
									'<li><a class="button_s" href="#a"><span style="margin-right:6px;">'+iKEPLang.tagging.cancel+'</span></a></li>' +
								'</ul>' +
							'</div></td>' +
						'</tr>' +
					'</table>';
				 
			} else {
				 html ='<input name="tagName" class="inputbox" style="width:300px; margin-right:5px; float:left;" type="text" value="'+tagText+'"/>' +
						'<ul>' +
							'<li><a class="button_s" href="#a"><span style="margin-right:6px;">'+iKEPLang.tagging.save+'</span></a></li>' +
							'<li><a class="button_s" href="#a"><span style="margin-right:6px;">'+iKEPLang.tagging.cancel+'</span></a></li>' +
						'</ul>'
					;
			}
			
			var $div = $form.attr('class','blockTag_rewrite modify mb5').find('div').html(html);
			
			if($('#'+formFrameId).size() <= 0){
				$form.wrap("<form id='"+formFrameId+"' onsubmit='return false;'/>");
				
				$('#'+formFrameId).append('<input type="hidden" name="tagItemId" value="'+itemId+'"/>');
				$('#'+formFrameId).append('<input type="hidden" name="tmpType" value="'+type+'"/>');
			}
			
			var self = this;
			$div.find("a.button_s").each(function(idx) {
				switch(idx) {
					case 0 : $(this).click(function() {self.tagAdd(extItemId, type); }); break;
					case 1 : $(this).click(function() { self.tagCancle(extItemId, type); }); break;
				}
			});
			
			 new iKEP.Validator('#'+formFrameId, tagValidOptions);
			 
			 setTimeout(function() {  $('#'+formFrameId).find("input[name=tagName]").focus();},100);
			 
		};
		
		
		this.tagFormPorfileView = function(itemId,type){
			
			this.tagFormView(itemId, type);
			
		};
		
		
		this.tagAdd = function(itemId, type){
			
			var formFrameId = "";
			
			if(!type || type == 'undefined'){
				formFrameId = "tagFormFrame_"+itemId;
			} else {
				formFrameId = "tagFormFrame_"+itemId+"_"+type;
			}
			
			$("#"+formFrameId).submit();
		};
		
		this.tagAddAction = function(itemId, type){
			var extItemId = itemId.replace("\.", "");
			var $form = '';
			
			if(!type || type == 'undefined'){
				$form = $("#tagFormFrame_"+extItemId);
			} else {
				$form = $("#tagFormFrame_"+extItemId+"_"+type);
			}
			
			var tagItemId 		= $form.find(':input[name=tagItemId]').val();
			var tagItemType 	= $form.find(':input[name=tagItemType]').val();
			var tagItemSubType 	= $form.find(':input[name=tagItemSubType]').val();
			var tagItemName	 	= $form.find(':input[name=tagItemName]').val();
			var tagItemContents	= $form.find(':input[name=tagItemContents]').val();
			var tagItemUrl 		= $form.find(':input[name=tagItemUrl]').val();
			
			var $tagName = $form.find(':input[name=tagName]');
			
			var tagName 		= $tagName.val();
			var self = this;
			
			$.ajax({    
				url : urlCreate,     
				data : {tagItemId:tagItemId, tagItemType:tagItemType, tagItemSubType:tagItemSubType, tagItemName:tagItemName, tagItemUrl:tagItemUrl, tagName:tagName, tagItemContents:tagItemContents},
				type : "post",  
				dataType : "json",
				success : function(result) { 
					
					var html = '';
					if(!type || type == 'undefined'){
						html = '<img src="'+contextRoot+'/base/images/theme/theme01/basic/ic_tag.gif" alt="tag" /><span>';
						
						$.each(result, function(i){
							if(i != 0){html += ", ";}
							html += '<a href="#a" onclick="iKEP.tagging.tagSearch(\''+this.tagId+'\', \''+this.tagItemType+'\');return false;" title="'+this.tagName+'">'+this.tagName+'</a>';
						});
					}  else {
						$.each(result, function(i){
							if(i != 0){html += ", ";}
							html += '<p style="display:inline">'+this.tagName+'</p>';
						});
					}
					
					
					
					var styleBtn = "";
					if(!type || type == 'undefined'){
						
						html += '</span> <span class="rebtn"> <a href="#a" onclick="iKEP.tagging.tagFormView(\''+itemId+'\');return false;" title="'+iKEPLang.tagging.edit+'" class="ic_modify"><span>'+iKEPLang.tagging.edit+'</span></a></span>';
						
					} else {
						
						html += '</span> <span class="rebtn"> <a href="#a" onclick="iKEP.tagging.tagFormView(\''+itemId+'\', \''+type+'\');return false;" title=""><img src="'+contextRoot+'/base/images/icon/ic_edit.gif" style="margin-bottom:0;" alt="'+iKEPLang.tagging.edit+'"/></a></span>';
						
					}
					
					
					$form.find('div div').attr('class','').html(html);
					
					tmpHtml = "";
					tmpId = "";
					
				
				},
				error : function(event, request, settings){ alert("error"); }
			}); 
		};
		
		
		this.tagCancle = function(itemId, type){
			
			var $form = '';
			
			if(!type || type == 'undefined'){
				$form = $('#tagForm_'+itemId);
			} else {
				$form = $('#tagForm_'+itemId+"_"+type);
			}
			
			$form.find('div').html(tmpHtml);
			
			tmpHtml = "";
			tmpId = "";
		};
		
		
		this.tagSearch = function(tagId, tagItemType, tagItemSubType, divId){
			
			callbackId = (divId)? divId:"tagResult";
			
			this.tagSearchAction(tagId, tagItemType, tagItemSubType);
		};
		
		this.tagSearchByName = function(tagName, tagItemType, tagItemSubType, divId){
			
			callbackId = (divId)? divId:"tagResult";
			
			this.tagSearchAction("", tagItemType, tagItemSubType, "", "", tagName);
		};
		
		
		this.tagSearchByIsSubType = function(tagId, tagItemType, tagItemSubType, divId){
			
			callbackId = (divId)? divId:"tagResult";
			
			this.tagSearchAction(tagId, tagItemType, tagItemSubType, "", "", "", 1);
		};
		
		this.tagSearchByIsNotSubType = function(tagId, tagItemType, tagItemSubType, divId){
			
			callbackId = (divId)? divId:"tagResult";
			
			this.tagSearchAction(tagId, tagItemType, tagItemSubType, "", "", "", "", 1);
		};
		
		this.tagSearchAction = function(tagId, tagItemType, tagItemSubType, pageIndex, pagePer, tagName, isSubType, isNotSubType){
			
			var data = new Object();
			if(tagId){data.tagId = tagId;}
			if(tagItemType){data.tagItemType = tagItemType;}
			if(tagItemSubType){data.tagItemSubType = tagItemSubType;}
			if(pageIndex){data.pageIndex = pageIndex;}
			if(pagePer){data.pagePer = pagePer;}
			if(tagName){data.tagName = tagName;}
			if(isSubType){data.isSubType = isSubType;}
			if(isNotSubType){data.isNotSubType = isNotSubType;}
			
			$.ajax({    
				url : urlSearch,     
				data : data,     
				type : "post",  
				dataType : "html",
				success : function(result) { 
				
						$("#"+callbackId).html(result);  
						$("#"+callbackId).data({tagId:tagId, tagItemType:tagItemType, tagItemSubType:tagItemSubType, pageIndex:pageIndex, pagePer:pagePer, tagName:tagName});
						
						$('#pagePerSelect option').each(function(){
							if(this.value == pagePer){
								this.selected = true;
							}
						});
						
						$(document).scrollTop(0);
						
					},
				error : function(event, request, settings){ alert("error"); }
			}); 
		};
		
		
		this.goPagePer = function(){
			
			var pagePer = $jq('#pagePerSelect').val();
			
			this.tagSearchAction($("#"+callbackId).data('tagId'), $("#"+callbackId).data('tagItemType'), $("#"+callbackId).data('tagItemSubType'), $("#"+callbackId).data('pageIndex'), pagePer, $("#"+callbackId).data('tagName'));
		};
		
		this.goPageIndex = function(){
			
			var pageIndex = $jq('#tag_pageIndex').val();
			
			this.tagSearchAction($("#"+callbackId).data('tagId'), $("#"+callbackId).data('tagItemType'), $("#"+callbackId).data('tagItemSubType'), pageIndex, $("#"+callbackId).data('pagePer'), $("#"+callbackId).data('tagName'));
		};
		
		
		this.goDetail = function(url){
			
			  iKEP.popupOpen(url ,{width:800, height:670} );
		};
		
		this.lengthCheck = function(tagNames){
			
			var isCheck = true;
			  
			var tages = tagNames.split(",");
			if(tages.length > 10){
				isCheck = true;
			} else {
				isCheck = false;
			}
			return isCheck;
		};
		
		
	})();
	
	iKEP.loadSecurity = function(className,resourceName,operationName,optionList,thWidth){
		$('#mainTable').after("<div id='securityArea'></div>");
		
		$jq.ajax({    
			url : iKEP.getContextRoot()+'/portal/admin/screen/security/loadSecurity.do',     
			data : {className:className, resourceName:resourceName, operationName:operationName, optionList:optionList,thWidth:thWidth },     
			type : "post",     
			success : function(result) {    
				$('#securityArea').html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		}); 
		
		$('#mainTable tr:last').after($jq('#securityTable tr').html());
		$('#securityTable').html('');
	};
	
	iKEP.readSecurity = function(className,resourceName,operationName,optionList,thWidth){

		$('#mainTable').after("<div id='securityArea'></div>");
		
		$jq.ajax({    
			url : iKEP.getContextRoot()+'/portal/admin/screen/security/readSecurity.do',     
			data : {className:className, resourceName:resourceName, operationName:operationName, optionList:optionList,thWidth:thWidth },     
			type : "post",     
			success : function(result) {    
				$('#securityArea').html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		}); 
		
		$('#mainTable tr:last').after($jq('#securityTable tr').html());
		$('#securityTable').html('');
	};
	
	// container 내의 이미지에 대해서 해당 container사이즈 보다 크면 container 안으로 들어가도록 사이즈 조절
	// 윈도우 resize시 이미지 원본 사이즈 만큼 커지며, 원본 보다 작으면 클릭해서 새창으로 원본 이미지 볼 수 있도록 적용
	iKEP.setContentImageRendering = function(container) {
		var $container = $(container);
		
		function showContentImage(event) {
			var orgWidth = $(this).data("orgWidth");
			if(orgWidth != $(this).width()) {
				var imgSrc = $(this).attr("src");
				var imgWindow = window.open("about:blank", "_blank", "width=400, height=400, resizable=yes, scrollbars=yes");
				imgWindow.document.write('<img src="' + imgSrc + '" onclick="window.close()" style="cursor:pointer" />');
			}
		};
		
		var contentWidth = $container.innerWidth();
		$container.find("img").each(function(idx, img) {
			var $img = $(img).click(showContentImage);
			var orgWidth = $img.width();
			if(!orgWidth || ($.browser.msie && orgWidth == 28)) {	// document.ready시에 체크되지 않는 경우 있음 : img tag에 해당 사이즈가 명시되어 있지 않은 경우
				$img.load(function() {
					var $img = $(this);
					var orgWidth = $img.width();
					$img.data("orgWidth", orgWidth);
					if(orgWidth > contentWidth) {
						$img.css("width", contentWidth+"px")
							.css("cursor", "pointer");
					}
				});
			} else {
				$img.data("orgWidth", orgWidth);
				if(orgWidth > contentWidth) {
					$img.css("width", contentWidth+"px")
						.css("cursor", "pointer");
				}
			}
		});
		
		$(window).resize(function() {
			var contentWidth = $container.innerWidth();
			$container.find("img").each(function(idx, img) {
				var $img = $(img);
				var orgWidth = $img.data("orgWidth");
				var curWidth = $img.width();
				if(curWidth <= orgWidth && contentWidth < orgWidth) {
					$img.css("width", contentWidth+"px");
					if(curWidth < orgWidth) $img.css("cursor", "pointer");
				}
				
				if(curWidth != orgWidth && contentWidth >= orgWidth) {
					$img.css("width", orgWidth+"px")
						.css("cursor", "auto");
				}
			});
		});
	};
	
	iKEP.setPopupImageRendering = function(container) {
		var $container = $(container);
		
		function showContentImage(event) {
			var orgWidth = $(this).data("orgWidth");
			if(orgWidth != $(this).width()) {
				var imgSrc = $(this).attr("src");
				var imgWindow = window.open("about:blank", "_blank", "width=400, height=400, resizable=yes, scrollbars=yes");
				imgWindow.document.write('<img src="' + imgSrc + '" onclick="window.close()" style="cursor:pointer" />');
			}
		};
		
		var contentWidth = $container.innerWidth();
		$container.find("img").each(function(idx, img) {
			var $img = $(img).click(showContentImage);
			var orgWidth = $img.width();
			if(!orgWidth || ($.browser.msie && orgWidth == 28)) {	// document.ready시에 체크되지 않는 경우 있음 : img tag에 해당 사이즈가 명시되어 있지 않은 경우
				$img.load(function() {
					var $img = $(this);
					var orgWidth = $img.width();
					$img.data("orgWidth", orgWidth);
					if(orgWidth > contentWidth) {
						$img.css("width", contentWidth+"px")
							.css("cursor", "pointer");
					}
				});
			} else {
				$img.data("orgWidth", orgWidth);
				if(orgWidth > contentWidth) {
					$img.css("width", contentWidth+"px")
						.css("cursor", "pointer");
				}
			}
		});
		
		$(window).resize(function() {
			var contentWidth = $container.innerWidth();
			$container.find("img").each(function(idx, img) {
				var $img = $(img);
				var orgWidth = $img.data("orgWidth");
				var curWidth = $img.width();
					$img.css("width", contentWidth+"px");
					$img.css("cursor", "pointer");
				
			});
		});
	};
	
	iKEP.getCssStyle = function(className, cssFileName) {	// 지정된 cssFileName에서 className의 css 속성을 반환한다.
		var style = null;
		
		for(var i=0;i<document.styleSheets.length;i++) {
			var css = document.styleSheets[i];
			if(cssFileName) {
				if(css.href && css.href.toLowerCase().indexOf(cssFileName.toLowerCase()) > -1) {
					var cssRules = css.cssRules || css.rules;
					for(var j=0;j<cssRules.length;j++) {
						var cssRule = cssRules[j];
						if(cssRule.selectorText.toLowerCase() == className.toLowerCase()) {
							style = cssRule.style;
							break;
						}
					}
					break;
				}
			} else {
				var cssRules = css.cssRules || css.rules;
				for(var j=0;j<cssRules.length;j++) {
					var cssRule = cssRules[j];
					if(cssRule.selectorText.toLowerCase() == className.toLowerCase()) {
						style = cssRule.style;
						break;
					}
				}
			}
		}
		
		return style;
	};
})(jQuery);

// JSON extend method : queryString
try {
	JSON.queryString = function(arg) {
		switch(typeof(arg)) {
			case "undefined" : return "undefined"; break;
			case "number" : return arg + ""; break;
			case "boolean" : 
			case "string" : 
			case "function" : return arg.toString(); break;
			case "object" :
				if(arg != null && !jQuery.isEmptyObject(arg)) {
					var jsonObject = arg;
					var result = "";
					var split = "&";
					switch(true) {
						case jQuery.isArray(jsonObject) :
						case jQuery.isPlainObject(jsonObject) :
							for(var key in jsonObject) {
								var value = (typeof(jsonObject[key]) == "object") ? JSON.queryString(jsonObject[key]) : jsonObject[key];
								result += key + "=" + value + split;
							}
							result = result.substring(0, result.length - split.length);
							break;
						default : result = "object";
					}
					return result;
				} else {
					return "";
				}
				break;
		}
	};
} catch (e) {}


String.prototype.replaceAll = function(val, replVal) {
	var reg = new RegExp(val, "g");
	return this.replace(reg, replVal);
};

String.prototype.byteLength = function() {
	var length = 0;
	for(var i=0;i<this.length;i++) {
		length++;
		if(this.charCodeAt(i) > 125) length++;
	}
	
	return length;
};

String.prototype.matchCount = function(str) {
	var regexp = new RegExp(str, "g");
	var match = this.match(regexp);
	
	return match ? match.length : 0;
};

String.prototype.addComma = function(point) {	// 문자형 숫자에 콤마 붙이기
	var result = "";
	if(isNaN(this)) {
		result = "NaN";
	} else {
		var sign = "";
		point = point || 3;
		var number = jQuery.trim(this).clearComma();
		
		if(isNaN(number.substring(0, 1))) {
			sign = number.substring(0, 1);
			number = number.substring(1);
		}
	
		
		var intergers = number.split(".");
		
		var integer = "";
		for(var i=0; i<intergers[0].length; i++) {
			if(i > 1 && ((i+1) % point) == 1) integer = "," + integer;
			integer = intergers[0].substr(intergers[0].length-i-1, 1) + integer;
		}
			 
		result = sign + integer;
		if(intergers.length > 1) result += "." + intergers[1];
	}
	
	return result;
};
String.prototype.clearComma = function (isNumber) { // 콤마 없애기
	var result = this.replaceAll(",", "");
	
	if(isNumber && !isNaN(result)) result = Number(result);
	
	 return result;
};

Array.prototype.equal = function(array) {
	if(this == array) return true;
	else {
		if(typeof(array) == "object" &&
			this.toString() == array.toString()) return true;
		else return false;
	}
};

Date.prototype.toString = function() {
	var result = this.getFullYear();
	
	var month = (this.getMonth() + 1);
	var day = this.getDate();
	var hours = this.getHours();
	var minutes = this.getMinutes();
	var seconds = this.getSeconds();
	
	result += (month < 10 ? "0" : "") + month + (day < 10 ? "0" : "") + day;
	result += " ";
	result += (hours < 10 ? "0" : "") + hours + ":" + (minutes < 10 ? "0" : "") + minutes + ":" + (seconds < 10 ? "0" : "") + seconds;
	
	return result;
};

// ie 9 이상에서는 native method 사용
if(jQuery.browser["msie"] && jQuery.browser["version"] && parseInt(jQuery.browser["version"], 10) < 9) {
	Array.prototype.indexOf = function(item, offset) {
		var result = -1;
		for(var i=offset||0, loop=this.length; i<loop; i++) {
			if(this[i] == item) {
				result = i;
				break;
			}
		}
		return result;
	};
}

//-- CKEDITOR configuration info
var fullCkeditorConfig = {
		enterMode : "2",
		skin : "v2", 
		height: 400,
		toolbar : [
		              ["Source","-",/*"Save" 임시저장기능 제외*/,"NewPage","Preview",/*"-", "Templates" 문서탬플릿기능 제외*/],
		              ["Cut","Copy","Paste","PasteText","PasteFromWord","-","Print", "SpellChecker", "Scayt"],
		              ["Undo","Redo","-","Find","Replace","-","SelectAll","RemoveFormat"],
		              /*["Form", "Checkbox", "Radio", "TextField", "Textarea", "Select", "Button", "ImageButton", "HiddenField"] 폼 & Input기능 제외*/,
		              "/",
		              ["Bold","Italic","Underline","Strike","-","Subscript","Superscript"],
		              ["NumberedList","BulletedList","-","Outdent","Indent",/*"Blockquote" 인용구기능 제거*//*"CreateDiv" Div 기능제거*/],
		              ["JustifyLeft","JustifyCenter","JustifyRight","JustifyBlock"],
		              ["BidiLtr", "BidiRtl"],
		              ["Link","Unlink","Anchor"],
		              ["imageUpload", "Image" /*이미지 링크 기능 제외*/,/*"Flash" 플래쉬 링크 기능 제외*/,"Table","HorizontalRule","Smiley","SpecialChar","PageBreak",/*"Iframe"*/],
		              "/",
		              ["Styles","Format","Font","FontSize"],
		              ["TextColor","BGColor"],
		              [/*"Maximize" 전체보기 기능 제외 [이유] 이 버튼 클릭시 flash file upload controller를 계속해서 호출함*/"ShowBlocks","-","About"]
			      ],  
	    extraPlugins : 'imageUpload',
	    popupTitle : '',
	    resize_enabled : false
}; 
// cafe용 CKEDITOR configuration info
var cafeCkeditorConfig = {
		enterMode : "2",
		skin : "v2", 
		height: 400,
		toolbar : [
		              ["Source","-","NewPage","Preview"],
		              ["Cut","Copy","Paste","PasteText","-","Print", "SpellChecker", "Scayt"],
		              ["Undo","Redo","-","Find","Replace","-","SelectAll","RemoveFormat"],		             
		              "/",
		              ["Bold","Italic","Underline","Strike"],
		              ["NumberedList","BulletedList"],
		              ["JustifyLeft","JustifyCenter","JustifyRight","JustifyBlock"],
		              ["Link","Unlink","Anchor"],
		              ["imageUpload", "Image","Table","HorizontalRule","Smiley","SpecialChar","PageBreak"],
		              "/",
		              ["Styles","Font","FontSize"],
		              ["TextColor","BGColor"],
		              ["About"]
			      ],  
	    extraPlugins : 'imageUpload',
	    popupTitle : '',
	    resize_enabled : false
 }; 
//-- CKEDITOR configuration info
var simpleCkeditorConfig = { 
		skin : "v2", 
		toolbar : "Basic",
		resize_enabled : false
}; 

/* 태그프리 에디터 첨부된 이미지 확인 */
function createActiveXEditorFileLink(bodyObj, formId) { 
	$jq("input[name^=editorFileLinkList]").remove();
	
	$jq(bodyObj).find("img[name=editorImage]").each(function(index) {   
		$jq("#" + formId).append($jq("<input name='editorFileLinkList[" + index + "].fileId' value='" + $jq(this).attr("id") + "' type='hidden'/>")); 
	});  	
}

/* ek에디터 첨부된 이미지 확인 */
function createEditorFileLink(formId) { 
	$jq("input[name^=editorFileLinkList]").remove();
	
	$jq("#cke_contents").find("iframe").contents().find("img[name=editorImage]").each(function(index) {   
		$jq("#" + formId).append($jq("<input name='editorFileLinkList[" + index + "].fileId' value='" + $jq(this).attr("id") + "' type='hidden'/>")); 
	});  	
}

/* 에디터에 이미지를 붙인다.*/
//afterUpload = function(status, fileId, fileName, message, targetId) { 
//	var editor = $jq("#" + targetId).ckeditorGet();  
//	editor.insertHtml("<img name='editorImage' id='"+ fileId +"' src='" + iKEP.getWebAppPath() + "/support/fileupload/downloadFile.do?fileId=" + fileId + "'/>"); 
//	
//}; 		

/* 용어링크를 붙인다.*/
addDictionaryLink = function(wordId, wordName, targetId) {
	var editor = $jq("#" + targetId).ckeditorGet(); 
	editor.insertHtml("<a class='link' href='getDictionary.do?wordId="+wordId+"'>"+wordName+"</a>");
}; 
 
pageMoveAction = function(inputName, formId, movePageIndex) { 
	$jq("input[name=" + inputName + "]", formId).val(movePageIndex); 
	$jq("input[name=action]", formId).val("pagePerRecord");
};

iKEP.smsAdd = function(registerId, receiverIds, receiverPhonenos, contents, mode, smsQuota) {
	for (var i = 0; i < receiverPhonenos.length; i++) {
		var curPhoneno = receiverPhonenos[i];
		receiverPhonenos[i] = curPhoneno.replaceAll("-","");		
	}

	var textLen = contents.length;
	var bytesLen = 0;
	var totSmsCnt = 1;
	/*var bytesPerSms = 80;
	for (var i = 0; i < textLen; i++) {		
		var oneChar = contents.charAt(i);
		if (escape(oneChar).length > 4) {		
			bytesLen += 2;						
		} else {		
			bytesLen++;		
		}			
	}		
	if ( bytesLen % bytesPerSms == 0 ) {
		totSmsCnt = bytesLen / bytesPerSms;
	} else {
		totSmsCnt = Math.floor((bytesLen / 80) + 1);
	}*/
	var receiverId = receiverIds.join("-");
	var receiverPhoneno = receiverPhonenos.join("-");
	var receiverCnt = receiverIds.length;
	var totalSmsCnt = receiverCnt * totSmsCnt;
	var smsAlert = iKEPLang.sms;
	$jq.ajax({    
		url : iKEP.getContextRoot() + "/support/sms/getRemainSmsCount.do",     
		data : {registerId:registerId},     
		type : "post",  
		success : function(result) {
				totalSmsCnt = totalSmsCnt + eval(result);
				if ( totalSmsCnt > smsQuota ) {
					alert(smsAlert.monthSmsCount.replace("{}", smsQuota));
				} else {
					$jq.ajax({    
						url : iKEP.getContextRoot() + "/support/sms/createSms.do",     
						data : {receiverId:receiverId, receiverPhoneno:receiverPhoneno, contents:contents },
						type : "post",     
						success : function(result) {        			
							alert(smsAlert.sendSuccess);
							if ( mode == "1" ) {
								location.reload(); // reload 처리  
								cancel_text();
							}
						},
						error : function(e){
							alert(e);
							//console.log(e);
						}
					}); 					
				}				
			},
		error : function(){ 
				alert("error"); 
			}
	}); 	
};

iKEP.extSmsAdd = function(registerId, receiverIds, receiverPhonenos, contents, mode, smsQuota) {
	for (var i = 0; i < receiverPhonenos.length; i++) {
		var curPhoneno = receiverPhonenos[i];
		receiverPhonenos[i] = curPhoneno.replaceAll("-","");		
	}

	var textLen = contents.length;
	var bytesLen = 0;
	var totSmsCnt = 1;
	/*var bytesPerSms = 80;
	for (var i = 0; i < textLen; i++) {		
		var oneChar = contents.charAt(i);
		if (escape(oneChar).length > 4) {		
			bytesLen += 2;						
		} else {		
			bytesLen++;		
		}			
	}		
	if ( bytesLen % bytesPerSms == 0 ) {
		totSmsCnt = bytesLen / bytesPerSms;
	} else {
		totSmsCnt = Math.floor((bytesLen / 80) + 1);
	}*/
	var receiverId = receiverIds.join("-");
	var receiverPhoneno = receiverPhonenos.join("-");
	var receiverCnt = receiverIds.length;
	var totalSmsCnt = receiverCnt * totSmsCnt;
	var smsAlert = iKEPLang.sms;
	$jq.ajax({    
		url : iKEP.getContextRoot() + "/support/sms/getRemainSmsCount.do",     
		data : {registerId:registerId},     
		type : "post",  
		success : function(result) {
				totalSmsCnt = totalSmsCnt + eval(result);
				if ( totalSmsCnt > smsQuota ) {
					alert(smsAlert.monthSmsCount.replace("{}", smsQuota));
				} else {
					$jq.ajax({    
						url : iKEP.getContextRoot() + "/support/sms/createSms.do",     
						data : {receiverId:receiverId, receiverPhoneno:receiverPhoneno, contents:contents },
						type : "post",     
						success : function(result) {        			
							alert(smsAlert.sendSuccess);
							if ( mode == "1" ) {
								//location.reload(); // reload 처리  
								cancel_text();
							}
						},
						error : function(e){
							alert(e);
							//console.log(e);
						}
					}); 					
				}				
			},
		error : function(){ 
				alert("error"); 
			}
	}); 	
};

function reloadFullScreen(url){
	if(url){
		top.location.href = iKEP.getContextRoot()+"/portal/main/portalMain.do?mainFrameUrl="+url;
	}else{
		top.location.href = iKEP.getContextRoot()+"/portal/main/portalMain.do?mainFrameUrl="+document.location;
	}
}

//메모 작성창
iKEP.inputMemo = function(kind) {
	var url ="";
	if(kind=="input"){
		url = iKEP.getContextRoot() + "/support/memo/inputMemo.do";
	}else{
		url = iKEP.getContextRoot() + "/support/memo/memoList.do";
	}
	iKEP.popupOpen(url, {width:527, height:548, resizable:false, scrollbar:false}, "Memo");
	//window.open(url, "Memo", "width=527px, height=548px, resizable=no, scrollbars=no");
	
};



/**
*
*  Base64 encode / decode
*  http://www.webtoolkit.info/
*
**/

var Base64 = {

	// private property
	_keyStr : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",

	// public method for encoding
	encode : function (input) {
		var output = "";
		var chr1, chr2, chr3, enc1, enc2, enc3, enc4;
		var i = 0;

		input = Base64._utf8_encode(input);

		while (i < input.length) {

			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);

			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;

			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}

			output = output +
			this._keyStr.charAt(enc1) + this._keyStr.charAt(enc2) +
			this._keyStr.charAt(enc3) + this._keyStr.charAt(enc4);

		}

		return output;
	},

	// public method for decoding
	decode : function (input) {
		var output = "";
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0;

		input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");

		while (i < input.length) {

			enc1 = this._keyStr.indexOf(input.charAt(i++));
			enc2 = this._keyStr.indexOf(input.charAt(i++));
			enc3 = this._keyStr.indexOf(input.charAt(i++));
			enc4 = this._keyStr.indexOf(input.charAt(i++));

			chr1 = (enc1 << 2) | (enc2 >> 4);
			chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);
			chr3 = ((enc3 & 3) << 6) | enc4;

			output = output + String.fromCharCode(chr1);

			if (enc3 != 64) {
				output = output + String.fromCharCode(chr2);
			}
			if (enc4 != 64) {
				output = output + String.fromCharCode(chr3);
			}

		}

		output = Base64._utf8_decode(output);

		return output;

	},

	// private method for UTF-8 encoding
	_utf8_encode : function (string) {
		string = string.replace(/\r\n/g,"\n");
		var utftext = "";

		for (var n = 0; n < string.length; n++) {

			var c = string.charCodeAt(n);

			if (c < 128) {
				utftext += String.fromCharCode(c);
			}
			else if((c > 127) && (c < 2048)) {
				utftext += String.fromCharCode((c >> 6) | 192);
				utftext += String.fromCharCode((c & 63) | 128);
			}
			else {
				utftext += String.fromCharCode((c >> 12) | 224);
				utftext += String.fromCharCode(((c >> 6) & 63) | 128);
				utftext += String.fromCharCode((c & 63) | 128);
			}

		}

		return utftext;
	},

	// private method for UTF-8 decoding
	_utf8_decode : function (utftext) {
		var string = "";
		var i = 0;
		var c = c1 = c2 = 0;

		while ( i < utftext.length ) {

			c = utftext.charCodeAt(i);

			if (c < 128) {
				string += String.fromCharCode(c);
				i++;
			}
			else if((c > 191) && (c < 224)) {
				c2 = utftext.charCodeAt(i+1);
				string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
				i += 2;
			}
			else {
				c2 = utftext.charCodeAt(i+1);
				c3 = utftext.charCodeAt(i+2);
				string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
				i += 3;
			}

		}

		return string;
	}

}

function make_basic_auth(user, pass) {
	  var tok = user + ':' + pass;
	  var hash = Base64.encode(tok);
	  return "Basic " + hash;
}


function createHttpRequest()
{


	if(window.XMLHttpRequest){
		 //Win Mac Linux m1,f1,o8 Mac s1 Linux k3용
		//alert("XMLHttpRequest");
		return new XMLHttpRequest() ;
	}else if(window.ActiveXObject){
		try {
			//alert("MSXML2.XMLHTTP.3.0");
			return new ActiveXObject("MSXML2.XMLHTTP.3.0") ;
		} catch (e) {
			try {
				//alert("MSXML2.XMLHTTP");
				return new ActiveXObject("MSXML2.XMLHTTP") ;
			} catch (e2) {
				try {
					//alert("Microsoft.XMLHTTP");
					return new ActiveXObject("Microsoft.XMLHTTP") ;
				} catch (e3) {
					//alert("catch");
					return false;
	 			}
 			}
 		}
	} else if (window.XDomainRequest){	
		//alert("XDomainRequest");
		return new XDomainRequest() ;
		
	} else {
		//alert("else");
		return false ;
	}
	
}


function mooorim_ESS_link(ess_url , sap_id , sap_pwd, non_css_flag){
	document.domain ="moorim.co.kr";
	//var authStr =make_basic_auth('choongwon', '1q2w3e4r');
	
	var xhr = false;

	xhr = createHttpRequest();
	
	if( xhr==false){
		alert("\n도구 > 인터넷 옵션 > 보안 > 사용자 지정 수준 클릭> [다른 도메인사이에서 창과 프레임 탐색]와 [도메인간의 데이터 원본 엑세스]를  [사용]으로 변경하세요!\n");
	}else{
		//alert(xhr);
		xhr.open("GET", ess_url, true, sap_id , sap_pwd);//ok
		
		xhr.onreadystatechange = function() { 
	         if(xhr.readyState == 4) {//COMPLETE 
	             if(xhr.status == 200){ //200 = 정상처리
	            		var regurl=/eptest.moorim.co.kr/g;
		            	var cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
	            		if (regurl.test(location.href)) { 
	            			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Feptest.moorim.co.kr%2Fikep4-webapp%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
	            		}else{
	            			cssStr="sap-client=300&sap-language=ko&style_sheet=http%3A%2F%2Fep.moorim.co.kr%2Fbase%2Fcss%2Fsap%2Fcontrols_ie6.css%3F6.0.23.0.1&sap-cssversion=6.0.23.0.0&sap-accessibility=&sap-ep-version=6.4.201005101207&sap_ep_version=6.4.201005101207&sap_ep_baseurl=http%3A%2F%2Fep.moorim.co.kr%3A50000%2Firj%2Fportal";//ok	
	            		}
	
	            	 if(non_css_flag){
	            		 location.href=ess_url;
	            	 }else{
		            	 if(ess_url.indexOf("?")>-1){
		                	 location.href=ess_url+"&"+cssStr;
		            	 }else{
		            		 location.href=ess_url+"?"+cssStr;
		            	 }
	            	 }
	            	 
	             }else{
	        		 alert("SAP 인증을 실패했습니다.\n 모든 인터넷 익스플로러 창을 종료하고 다시 실행시키기 바랍니다.");
	            	 /*
	        		 var sapPaasswordModfiyConfirm = 
	            		 confirm("SAP 인증을 실패했습니다.\n SAP패스워드를 변경하지 않으신분은 확인을 눌러 주십시요.\n변경후에 자동로그아웃후 재접속시에도 SAP인증이 실패하면 관리자에게 문의하십시요.");
	            	 if(sapPaasswordModfiyConfirm){
	            		 openNewPassword();
	            	 }
	            	 */
	             }
	         } 
		};
		
		xhr.send(null);//ok
	}
}


function fnRun() {
    try
    {
        var objWSH = new ActiveXObject("WScript.Shell");
        if (objWSH == null) return;
        //retval = objWSH.Run("%SystemRoot%\\System32\\notepad.exe");
        //retval = objWSH.Run("%SystemRoot%\\System32\\ping.exe 127.0.0.1 -t ");
        //retval = objWSH.Run("\"C:\\Program Files (x86)/EditPlus 3/editplus.exe\"");
        //var retval = objWSH.Run("\"C:\\Program Files/Moorim MES/PPMES/Login_eCover.exe\"");
        objWSH.CurrentDirectory="C:\\Program Files/Moorim MES/PPMES/";
        //var retval = objWSH.Run("\"C:\\Program Files/Moorim MES/PPMES/Login_eCover.exe\"");
        var retval = objWSH.Run("Login_eCover.exe");
        //var retval = objWSH.Run("\"C:\\Program Files/Moorim MES/PPMES/Login.lnk\"");
    }
    catch (e) {
        try
        {
            var objWSH = new ActiveXObject("WScript.Shell");
            if (objWSH == null) return;
            objWSH.CurrentDirectory="C:\\Program Files (x86)/Moorim MES/PPMES/";
            //var retval = objWSH.Run("\"C:\\Program Files (x86)/Moorim MES/PPMES/Login_eCover.exe\"");
            var retval = objWSH.Run("Login_eCover.exe");
           // var retval = objWSH.Run("\"C:\\Program Files (x86)/Moorim MES/PPMES/Login.lnk\"");
        }
        catch (e) {
        	alert(e.message + "\n도구 > 인터넷 옵션 > 보안 > 사용자 지정 수준 클릭> [스크립팅하기 안전하지 않은 것으로 표시된 ActiveX컨트롤 초기화 및 스크립팅]을 [확인]으로 변경하세요!\n");
        }

    }
}



