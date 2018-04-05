// Portlet Manager
function PortletManager(activePortlet) {
	var button = null;
	var control = null;
	
	var sortArea = ["#portlet_l", "#portlet_r", "#portlet_c"];
	var portlets = [	// 관리자용(공용)과 개인용 포틀릿 분류 설정 - 하위의 카테고리 분류 적용 방안 모색 : 2011.01.07
		{id:"P001", title:"Portlet 1", constructor:PortletIFrame, area:"#portlet_l", options:{title:"Simple Portlet 01", icons:{reload:true, close:true, config:false}}, desc:""},
		{id:"P002", title:"Portlet 2", constructor:PortletSimple, area:"#portlet_l", options:{title:"Portlet 1", url:"/app/test/ajax_result/content.html", icons:{reload:true, close:true, config:{url:"/app/test/ajax_result/config.html"}}}, desc:""},
		{id:"P003", title:"Portlet 3", constructor:PortletSimple, area:"#portlet_c", options:{title:"Simple Portlet 02"}, desc:""},
		{id:"P004", title:"Portlet 4", constructor:PortletSimple, area:"#portlet_r", options:{title:"Portlet 4", icons:{reload:{callback:function(){alert("reload")}}}}, desc:""},
		{id:"P005", title:"Portlet 5", constructor:PortletSimple, area:"#portlet_l", options:{title:"Portlet 5", icons:{maximize:true, config:{url:"/app/test/ajax_result.html"}}}, desc:""},
		{id:"P006", title:"Portlet 6", constructor:PortletSimple, area:"#portlet_r", options:{title:"Simple Portlet 03"}, desc:""},
		{id:"P007", title:"Portlet 7", constructor:PortletSimple, area:"#portlet_c", options:{title:"Simple Portlet 04"}, desc:""},
		{id:"P008", title:"Portlet 8", constructor:PortletSimple, area:"#portlet_r", options:{title:"Portlet 7", url:"/app/test/ajax_result/content.html", icons:{reload:true, close:true, config:{url:"/app/test/ajax_result/config.html"}}}, desc:""},
		{id:"P009", title:"Portlet 9", constructor:PortletSimple, area:"#portlet_l", options:{title:"Simple Portlet 05"}, desc:""},
		{id:"P010", title:"Portlet 10", constructor:PortletJSON, area:"#portlet_c", options:{title:"JSON Portlet 10"}, desc:""}
	];
	
	var createdPortlets = [];
	
	function initial() {
		$(sortArea.join(",")).sortable({
			revert: true,
			connectWith: sortArea.join(","),
			handle : "div.ui-widget-header",
			placeholder: "ui-state-highlight",
			forcePlaceholderSize: true/*,
			stop : function(event) {console.log(event)},
			change : function(event) { console.log($(event.target)); },
			start : function(event) { console.log("start"); }*/
		});
		
		var createPortlets = new Array(new Array(), new Array());
		$.each(sortArea, function(){ createPortlets[1].push(new Array()); });
		$.each(activePortlet, function(index, item) {
			createPortlets[0].push(item.id);
			for(var i=0;i<sortArea.length;i++) {
				if(sortArea[i] == item.layout) {
					createPortlets[1][i][item.seq] = item.id; 
					break;
				}
			}
		})
		
		setControl(createPortlets[0]);
		initialPortlet(createPortlets[1]);
	}
	
	function setControl(portlet) {
		button = $("#btnPortletControl").bind("click", function(event) {
			var container = control.parent();
			if(container.is(":hidden")) {
				container.css({display:""});
			} else {
				container.hide();
			}
		});
		
		control = $("#divPortletControl").live("click", function(event) {
			var el = $(event.target);
			if(el[0].tagName.toLowerCase() == "a") {
				var portletId = el.parent().attr("portletId");
				el.parent().addClass("disabled");
				el.replaceWith(el.html());
				for(var i=0;i<portlets.length;i++) {
					if(portlets[i].id == portletId) {
						createPortlet(portlets[i]);
						break;
					}
				}
			}
		});
		
		control.parent().hide();
		
		var items = $('<ul/>').appendTo(control);
		$.each(portlets, function(index, item) {
			if($.inArray(item.id, portlet) > -1) {
				items.append('<li portletId="' + item.id + '" class="disabled">' + item.title + '</li>');
			} else {
				items.append('<li portletId="' + item.id + '"><a>' + item.title + '</a></li>');
			}
		})
	}
	
	function createPortlet(info) {
		var portlet = new info.constructor(info.area, info.options);
		portlet.box.container.bind("destroy", function(event, currPortlet) {
			createdPortlets = $.grep(createdPortlets, function(P) {
				if(P.portlet == currPortlet) {
					P.portlet = null;
					var li = control.find("li[portletId=" + P.id + "]"); 
					li.removeClass("disabled").html('<a>' + li.html() + '</a>');
					return false;
				}
				return true;
			});
		});
		createdPortlets.push({id:info.id, portlet:portlet});
	}
	
	function initialPortlet(portlet) {
		$.each(sortArea, function(index, area){
			$.each(portlet[index], function(index, portletId) {
				if(portletId) {
					for(var i=0;i<portlets.length;i++) {
						if(portlets[i].id == portletId) {
							portlets[i].area = area;	// 사용자 설정 영역에 생기도록 변경 : UI에서 삭제했다가 재 생성할때도 이 값을 사용
							createPortlet(portlets[i]);
							break;
						}
					}
				}
			});
		});
	}
	
	initial();
};
/*
(function($) {
	iKEP.PortletBox = function() {
		return {
			container : null,
			body : null,
			setContent : function() {},
			showConfig : function() {},
			setIcon : function() {}
		}
	}
})(jQuery);
*/
// portlet box class
var PortletBox = function(div, options) {
	var div = $(div);
	var divParent = null;
	var caption = null;
	
	var divConfig = null;
	var divContent = null;
	
	var bodyHeight = 0;
	var containerState = 0;	//-1:minimize, 0:restore, 1:maximize
	var duration = 500;
	
	var options = options;
	var icons = null;
	
	this.container = null;
	this.body = null;
	
	this.initial = function() {
		options.isCaption = options.isCaption === undefined && true;
		var optionalIcons = {};
		if(options.icons) {
			for(key in options.icons) {
				switch(typeof options.icons[key]) {
					case "boolean" : optionalIcons[key] = { show:options.icons[key] }; break;
					case "object" : optionalIcons[key] = $.extend({show:true}, options.icons[key]);break;
				}
			}
		}
		
		icons = $.extend(true, {
			more : {name:"more", icon:"ui-icon ui-icon-document", show:false},
			config : {name:"config", icon:"ui-icon ui-icon-wrench", show:false},
			reload : {name:"reload", icon:"ui-icon ui-icon-refresh", show:false},
			maximize : {name:"maximize", icon:"ui-icon ui-icon-arrow-4-diag", show:true},
			minimize : {name:"minimize", icon:"ui-icon ui-icon-minusthick", show:true},
			restore : {name:"restore", icon:"ui-icon ui-icon-newwin", show:true},
			close : {name:"close", icon:"ui-icon ui-icon-closethick", show:false}
		}, optionalIcons);
		
		this.container = $('<div/>').addClass("portlet-container ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")	//portlet
			//.css({marginBottom:"10px"})
			.appendTo(div);
		this.body = $('<div/>').addClass("ui-helper-reset ui-widget-content")	//body
			.append($('<div/>'))
			.append($('<div/>'))
			.appendTo(this.container);
		
		divContent = this.body.children().eq(0).addClass("content");
		
		
		if(options.isCaption) {
			caption = $('<div/>').addClass("portlet-header ui-widget-header ui-corner-all")	//caption clear
				.append('<span/>')
				.append(options.title)
				.prependTo(this.container);
			divConfig = this.body.children().eq(1).addClass("config").hide();
			
			this.setIcon();
		}
	}
	
	this.setContent = function(html) {
		try {divConfig.hide();} catch(e) {}
		divContent.html(html).css({display:""}).css({width:"100%", height:this.body.height()});
	}
	
	this.showConfig = function(html) {
		divContent.hide();
		divConfig.html(html).css({display:""}).css({width:"100%", height:this.body.height()});
	}
	
	this.setIcon = function() {
		var iconBar = $("span", caption).css({float:"right"});
		$.each(icons, function(index, icon) {
			if(icon && icon.show == true) {
				var btn = $('<a/>').addClass(icon.icon).css({float:"left"}).html(icon.name).attr("method", icon.name).appendTo(iconBar);
				if(icon.name == icons.restore.name) btn.hide();
			}
		});
		
		var self = this;
		iconBar.click(function(event) {
			var el = $(event.target);
			switch(el.attr("method")) {
				case icons.more.name :	// 더보기
					if(icons.more.callback) icons.more.callback();
					break;
				case icons.config.name :	// 설정
					if(divConfig.is(":hidden")) {
						self.container.trigger("click:config");
					} else {
						divConfig.hide();
						divContent.show();
					}
					break;
				case icons.reload.name :	// 갱신
					divConfig.hide();
					self.container.trigger("click:reload");
					break;
				case icons.maximize.name :	//최대화
					containerState = 1;
					if(divParent == null) divParent = self.container.wrap('<div/>').parent();
					var divMax = $("#portlet div.maximize").append(self.container).css({display:""});
					$("a[method='"+icons.maximize.name+"']", iconBar).hide();
					$("a[method='"+icons.minimize.name+"']", iconBar).hide();
					$("a[method='"+icons.restore.name+"']", iconBar).css({display:"inline-block"});
					divMax.siblings("div").hide();
					if(icons.maximize.callback) icons.maximize.callback();
					break;
				case icons.minimize.name :	// 최소화
					containerState = -1;
					bodyHeight = self.body.height();
					self.body.animate({height:"0px"}, duration, function() {
						$(this).css({display:"none"});
						$("a[method='"+icons.maximize.name+"']", iconBar).hide();
						$("a[method='"+icons.minimize.name+"']", iconBar).hide();
						$("a[method='"+icons.restore.name+"']", iconBar).css({display:"inline-block"});
					});
					if(icons.minimize.callback) icons.minimize.callback();
					break;
				case icons.restore.name :	// 이전크기
					switch(containerState) {
						case -1 :
							self.body.css({height:"1px", display:""}).animate({height:bodyHeight+"px"}, duration);
							break;
						case 1 :
							var divMax = $("#portlet div.maximize").hide();
							divMax.siblings("div").css({display:""});
							divParent.append(self.container);
							break;
					}
					containerState = 0;
					$("a[method='"+icons.maximize.name+"']", iconBar).css({display:"inline-block"});
					$("a[method='"+icons.minimize.name+"']", iconBar).css({display:"inline-block"});
					$("a[method='"+icons.restore.name+"']", iconBar).hide();
					if(icons.restore.callback) icons.restore.callback();
					break;
				case icons.close.name :	// 닫기
					self.container.trigger("click:close");
					if(icons.close.callback) icons.close.callback();
					if(divParent) divParent.remove();
					else self.container.remove();
					break;
			}
		});
	}
	
	this.initial();
}

var PortletSimple = function(div, options) {
	this.box = null;
	this.options = {
		title:"Simple Portlet",
		url:"/app/test/ajax_result/content_default.html",
		config:{url:"/app/test/ajax_result/config_default.html"},
		reload:null,
		more:null
	};
	
	this.initial = function(div, options) {
		var self = this;
		
		var icons = null;
		$.each(options, function(key, value) {
			if(key == "icons") {
				icons = value;
				self.options.config = icons.config || null;
				self.options.reload = icons.reload || null;
				self.options.more = icons.more || null;
			} else {
				self.options[key] = value;
			}
		});
		var icons = $.extend(true, {
			more:true,
			config:false,
			reload:true,
			maximize:false,
			minimize:true,
			restore:true,
			close:false}, icons);
		this.box = new PortletBox(div, {
			title:this.options.title,
			isCaption : this.options.isCaption,
			icons:icons
		});
		
		this.loadContent();
		this.setIconEvent();
	}
	
	this.loadContent = function() {
		var self = this;
		if(this.options.url) {
			$.ajaxSetup({loading:this.box.container});
			$.get(this.options.url, function(result) {
				self.box.setContent(result);
			});
		}
	}
	
	this.setIconEvent = function() {
		var self = this;
		this.box.container.bind("click:reload", function(event) {	// reload
			if(self.options.reload && self.options.reload.callback) self.options.reload.callback();
			else {
				self.loadContent();
			}
		});
		this.box.container.bind("click:config", function(event) {	//config
			if(self.options.config.callback) self.options.config.callback();
			if(self.options.config.url) {
				$.get(self.options.config.url, function(result) {
					self.box.showConfig(result);
				});
			}
		});
		this.box.container.bind("click:close", function(event) {
			self.box.container.trigger("destroy", self);
			self.box = null;
		});
	}
	
	this.initial(div, options);
}

var PortletIFrame = function(div, options) {
	this.box = null;
	this.options = {
		title:"Simple Portlet",
		url:"/app/test/ajax_result/content_default.html",
		config:{url:"/app/test/ajax_result/config_default.html"},
		reload:null,
		more:null
	};
	
	this.initial = function(div, options) {
		var self = this;
		
		var icons = null;
		$.each(options, function(key, value) {
			if(key == "icons") {
				icons = value;
				self.options.config = icons.config || null;
				self.options.reload = icons.reload || null;
				self.options.more = icons.more || null;
			} else {
				self.options[key] = value;
			}
		});
		var icons = $.extend(true, {
			more:true,
			config:false,
			reload:true,
			maximize:false,
			minimize:true,
			restore:true,
			close:false}, icons);
		this.box = new PortletBox(div, {
			title:this.options.title,
			isCaption : this.options.isCaption,
			icons:icons
		});
		
		this.loadContent();
		this.setIconEvent();
	}
	
	this.loadContent = function() {
		var self = this;
		if(this.options.url) {
			this.box.setContent('<iframe src="' + this.options.url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
			var divContent = this.box.body.children().eq(0);
			var iFrame = $("iframe", divContent);
			
			var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
			body.onload = function() {
				var contentHeight = ($(body).height()+body.scrollMaxY) + "px";
				iFrame.css({height:contentHeight});
				$(iFrame).parent().css({height:contentHeight});
			};
		}
	}
	
	this.setIconEvent = function() {
		var self = this;
		this.box.container.bind("click:reload", function(event) {	// reload
			if(self.options.reload && self.options.reload.callback) self.options.reload.callback();
			else {
				self.loadContent();
			}
		});
		this.box.container.bind("click:config", function(event) {	//config
			if(self.options.config.callback) self.options.config.callback();
			if(self.options.config.url) {
				$.get(self.options.config.url, function(result) {
					self.box.showConfig(result);
				});
			}
		});
		this.box.container.bind("click:close", function(event) {
			self.box.container.trigger("destroy", self);
			self.box = null;
		});
	}
	
	this.initial(div, options);
}

var PortletJSON = function(div, options) {
	this.box = null;
	this.options = {
		title:"Simple Portlet",
		url:"/app/test/ajax_result/content_json.html",
		config:{url:"/app/test/ajax_result/config_default.html"},
		reload:null,
		more:null
	};
	
	this.initial = function(div, options) {
		var self = this;
		
		var icons = null;
		$.each(options, function(key, value) {
			if(key == "icons") {
				icons = value;
				self.options.config = icons.config || null;
				self.options.reload = icons.reload || null;
				self.options.more = icons.more || null;
			} else {
				self.options[key] = value;
			}
		});
		var icons = $.extend(true, {
			more:true,
			config:false,
			reload:true,
			maximize:false,
			minimize:true,
			restore:true,
			close:false}, icons);
		this.box = new PortletBox(div, {
			title:this.options.title,
			isCaption : this.options.isCaption,
			icons:icons
		});
		
		this.loadContent();
		this.setIconEvent();
	}
	
	this.loadContent = function() {
		var self = this;
		if(this.options.url) {
			$.getJSON(this.options.url, function(result) {
				var html = '<ul>';
				$.each(result, function(index, item) {
					html += '<li>' + (index+1) + '.' + item.title + '</li>';
				});
				html += '</ul>';
				self.box.setContent(html);
			});
		}
	}
	
	this.setIconEvent = function() {
		var self = this;
		this.box.container.bind("click:reload", function(event) {	// reload
			if(self.options.reload && self.options.reload.callback) self.options.reload.callback();
			else {
				self.loadContent();
			}
		});
		this.box.container.bind("click:config", function(event) {	//config
			if(self.options.config.callback) self.options.config.callback();
			if(self.options.config.url) {
				$.get(self.options.config.url, function(result) {
					self.box.showConfig(result);
				});
			}
		});
		this.box.container.bind("click:close", function(event) {
			self.box.container.trigger("destroy", self);
			self.box = null;
		});
	}
	
	this.initial(div, options);
}
