//--	iKEP namespace setting	--------------------------------------------------------------
var iKEP = {};
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

//	initiallize processing...	--------------------------------------------------------------
jQuery(document).ready(function() {
	// dynamic combo setting
	if(iKEP && iKEP.initDynamicCombo) iKEP.initDynamicCombo();

/*
	// ajax configuration
	$.ajaxSetup({
//		beforeSend : function(xhr, options) {
//			if(options.loading) {
//				var div = options.loading;
//				div.css({position:"relative"});
//				$('<div/>').css({position:"absolute", left:0, top:0, width:"100%", height:div.height(), backgroundColor:"white"})
//					.html("loading...")
//					.appendTo(div);
//			}
//		},
		success : function() {
			
		},
		complete : function(xhr, status) {
			switch(status) {
				case "error":
					break;
				case "success":
					break;
			}
		},
		error : function(xhr) {
			var divAjaxMsg = $("div#ajaxErrMsg");
			if(divAjaxMsg.length == 0) {
				divAjaxMsg = $("<div id='ajaxErrMsg'/>").appendTo(document.body);
			}
			var title = xhr.status + " " + xhr.statusText;
			divAjaxMsg.dialog({title:title, modal:true, width:500, height:300})
				.html(xhr.responseText);
		}
	});
	
	$(document).ajaxStart(function() {

	});
	$(document).ajaxStop(function() {

	});
	$(document).ajaxSend(function() {

	});
*/
	// form element masking
	if(jQuery.mask) {
		$.mask.definitions["?"] = "[9 ]";
		$("input.dateISO").mask("9999-99-99");
		$("input.zipcode").mask("999-999?", {placeholder:"*"});
		$("input.phone").mask("(999)?999-9999");
	}
	
	// tooltip setting
	if(iKEP && iKEP.initTooltip) iKEP.initTooltip();
	
	// jstree theme path setting
	if(jQuery.jstree) {
		var href = location.href;
		$.jstree._themes = href.substring(0, href.indexOf("/html/")) + "/css/jstree/themes/";	//"/portal/base/css/jstree/themes/";
	}
});



try {	//	jQuery UI datepicker regional setting : korean	--------------------------------------
	if($.datepicker) {
		$.datepicker.regional['ko'] = {
			closeText: '닫기',
			prevText: '이전달',
			nextText: '다음달',
			currentText: '오늘',
			monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			dayNames: ['일','월','화','수','목','금','토'],
			dayNamesShort: ['일','월','화','수','목','금','토'],
			dayNamesMin: ['일','월','화','수','목','금','토'],
			weekHeader: 'Wk',
			dateFormat: 'yy-mm-dd',
			firstDay: 0,
			isRTL: false,
			showMonthAfterYear: true,
			yearSuffix: '년'
		};
		
		$.datepicker.setDefaults($.datepicker.regional['ko']);
		//$.datepicker.setDefaults($.datepicker.regional['']);
	}
} catch(e) {};



// main menu create ---------------------------------------------------------------------------
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



// tree list source to tree item convert method -------------------------------------------------------------
iKEP.arrayToTreeData = function(list, topItem) {
	var treeItems = [];
	this.createTreeItem = function (item, options) {
		var data = {type:item.type};
		switch(item.type) {
			case "group" : $.extend(data, {code:item.code, parent:item.parent}); break;
			case "user" : $.extend(data, {id:item.id, group:item.parent, empNo:item.empNo, email:item.email}); break;
		}
		
		var attr = {
			data:JSON.stringify(data),
			code:item.code
		};
		
		var result = {data:item.name, attr:attr};
		if(item.type != "user" && item.hasChild > 0) {
			result.state = "closed";
		}
		return $.extend(result, options);	// state:open/closed, , icon:css class name
	};
	this.setData = function(item, list) {
		for(var i=0;i<list.length;i++) {
			if(list[i] != null && item.attr.code == list[i].parent) {
				var childItem = this.createTreeItem(list[i]);
				
				for(var j=i;j<list.length-1;j++) { list[j] = list[j+1]; }
				list.length--;
				i--;
				
				this.setData(childItem, list);
				if(item.children == undefined) item["children"] = [];
				item.children.push(childItem);
			}
		}
	};
	
	if(topItem) {
		var treeItem = this.createTreeItem(topItem, {state:"open"});
		this.setData(treeItem, list);
		treeItems.push(treeItem);
	} else {
		while(list.length > 0) {
			var item = list.shift();
			var treeItem = this.createTreeItem(item, {state:"open"});
			this.setData(treeItem, list);
			treeItems.push(treeItem);
		}
	}
	
	return treeItems;
};



//dialog wrapper
iKEP.Dialog = function(options) {
	var dialog = this;
	this.container = $("<div/>").appendTo(document.body);
	this.destroy = false;
	this.fnCallback = null;
	var _options = $.extend({
		title : "Dialog Window",
		maxHeight : 600,
		maxWidth : 980,
		width : 300,
		modal : false,
		isDestroy : true,
		params : null,
		callback : null
	}, options);
	
	if(_options.url) {
		_options.create = function() {
			var ifrm = $('<iframe style="width:100%; height:0; border-width:0;" frameborder="0" scrolling="no" src="' + _options.url + '"/>').appendTo(dialog.container);
			ifrm.bind("load", function() {
				ifrm.height(ifrm.contents().height());
				
				var frmBody = ifrm[0].contentWindow || ifrm[0].contentDocument;
				if(frmBody.fnCaller) frmBody.fnCaller(_options.params, dialog);
				//frmBody.fnResult = _options.callback;
			});
			//$(this).load(_options.url);
		};
	}
	
	if(_options.isDestroy == true) {
		_options.close = function() {
			iKEP.debug("close");
			if(dialog.destroy == false) {
				dialog.close();
			}
		}
	}
	
	this.fnCallback = _options.callback;

	this.container.dialog(_options);
	
	iKEP.Dialog.prototype.callback = function(res) {
		if(this.fnCallback)
			this.fnCallback(res);
	};
	
	iKEP.Dialog.prototype.open = function() {
		this.container.dialog("open");
	};

	iKEP.Dialog.prototype.close = function() {
		if(_options.isDestroy == true) {
			this.destroy = true;
			this.container.children().remove();
			this.container.parent().remove();
		}
		
		if(this.container.dialog("isOpen")) {
			this.container.dialog("close");
		}
	};
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