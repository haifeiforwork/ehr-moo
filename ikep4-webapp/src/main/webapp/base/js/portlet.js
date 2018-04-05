var PortletManager,
	PortletBox,
	PortletSimple,
	PortletIFrame,
	PortletJSON;
(function($){
	// Portlet Manager
	PortletManager = function (options) {
		var portletManagerOptions = $.extend({
			commonSortAreas : [],
			commonPortlets : [],
			sortAreas : [],
			portlets : [],
			portletGroups : [],
			activePortlets : [],
			initialPortlets : [],
			isSetting : false,
			systemCode: ''}, options
		);
		
		var contextPath = iKEP.getContextRoot();
		var baseImgPath = contextPath + "/base/images/common";
		var control = null;

		var createdPortlets = [];	//{id:portletId, portletConfigId:portletConfigId, portlet:portlet}
		
		var tplPortletIcon = $.template(null, '<li><a class="${imagePath}" href="#a" title="${desc}"><span>${title}</span></a></li>');
		
		this.setControl = setControl;
		
		function initial() {
			$.each(portletManagerOptions.activePortlets, function() {
				var activePortlet = this;
				var activePortletInfo = null;
				$.each(portletManagerOptions.portlets, function() { 
					if(this.id == activePortlet.id) { activePortletInfo = this; return false; } 
				});
				
				var options = $.extend({
					publicOption : activePortletInfo.publicOption,
					moveable : activePortletInfo.moveable,
					isSetting : portletManagerOptions.isSetting,
					viewMode : activePortlet.viewMode,
					headerMode : activePortlet.headerMode,
					portletConfigId : activePortlet.portletConfigId,
					portletId : activePortlet.id,
					createIndex : (activePortlet.seq === 0) ? 0 : activePortlet.seq || -1
					}, activePortletInfo.options, activePortlet.options||{});
				
				var portlet = new activePortletInfo.constructor(activePortlet.layout, options);
				
				createdPortlets.push({id:options.portletId, portletConfigId:options.portletConfigId, portlet:portlet});
				portlet.box.container.bind("destroy", destroyPortlet);	// 포틀릿 삭제 이벤트 설정
			});
			
			$.each(portletManagerOptions.commonPortlets, function() {
				var commonPortlet = this;
				var commonPortletInfo = null;
				$.each(portletManagerOptions.portlets, function() { 
					if(this.id == commonPortlet.id) { commonPortletInfo = this; return false; } 
				});
				
				var options = $.extend({
					publicOption : commonPortletInfo.publicOption,
					moveable : commonPortletInfo.moveable,
					isSetting : portletManagerOptions.isSetting,
					viewMode : commonPortlet.viewMode,
					headerMode : commonPortlet.headerMode,
					portletConfigId : commonPortlet.portletConfigId,
					portletId : commonPortlet.id,
					createIndex : (commonPortlet.seq === 0) ? 0 : commonPortlet.seq || -1
					}, commonPortletInfo.options, commonPortlet.options||{});

				var portlet = new commonPortletInfo.constructor(commonPortlet.layout, options);
				createdPortlets.push({id:options.portletId, portletConfigId:options.portletConfigId, portlet:portlet});
				portlet.box.container.bind("destroy", destroyPortlet);	// 포틀릿 삭제 이벤트 설정
			});
			
			if(portletManagerOptions.isSetting) {
				setLayoutRemoveEvent(portletManagerOptions.commonSortAreas);
				setLayoutRemoveEvent(portletManagerOptions.sortAreas);
			}
			
			setPortletSortable();

			//setControl();
		}
		
		function setLayoutRemoveEvent(layout) {
			(layout.join ? $(layout.join(",")) : $(layout)).each(function() {
				$(this).bind("layout:remove", function() {
					$(this).find("div.portletUnit").each(function() {
						for(var i=0, length=createdPortlets.length; i < length; i++) {
							if(this == createdPortlets[i].portlet.box.container[0]) {
								var removedPortlet = createdPortlets[i].portlet;
								removedPortlet.box.container.trigger("destroy", removedPortlet);
								break;
							}
						}
					});
				});
			});
		}
		
		function setPortletSortable() {
			$(portletManagerOptions.sortAreas.join(",")).sortable({
				revert: true,
				connectWith: portletManagerOptions.sortAreas.join(","),
				handle : "div.po_title",
				placeholder: "ui-state-highlight",
				forcePlaceholderSize: true,
				cancel : "div#noMove",
				start : function() {
					$(portletManagerOptions.sortAreas.join(",")).each(function() {
						//$(this).height($(this).height() + 100).css("background-color", "orange");
					});
				},
				update : function(event, ui) {
					if(!portletManagerOptions.isSetting) {
						if(ui.sender == null) {	// 다른 레이아웃에서 왔을때는 update가 2번 발생하므로 sender가 없을때만 수행 하도록
							var $div = ui.item.parent();
							
							var portletConfigId = $.data(ui.item[0], "portletConfigId");
							var pageLayoutId = $.data($div[0], "pageLayoutId");
							var colIndex = $.data($div[0], "colIndex");
							var rowIndex = $div.children("div.portletUnit").index(ui.item) + 1;
							
							//var oldPageLayoutId = $.data($(event.target)[0], "pageLayoutId");
							
							movePortlet(portletConfigId, pageLayoutId, colIndex, rowIndex);
						}
					}
				},
				stop : function(event, ui) {
					$(portletManagerOptions.sortAreas.join(",")).each(function() {
						//$(this).height($(this).height() - 100).css("background-color", "");
						//$(this).css("padding-bottom", 0);
					});
				}
			}).css({minHeight:100});	// 해당 영역에 내용이 없으면 영역 사이즈가 0(없어짐)가 되므로
		}
		
		function setCommonPortletSortable() {
			$(portletManagerOptions.commonSortAreas.join(",")).sortable({
				revert: true,
				connectWith: portletManagerOptions.commonSortAreas.join(","),
				handle : "div.po_title",
				placeholder: "ui-state-highlight",
				forcePlaceholderSize: true,
				cancel : "div#noMove",
				start : function() {
					$(portletManagerOptions.commonSortAreas.join(",")).each(function() {
						//$(this).height($(this).height() + 100).css("background-color", "orange");
					});
				},
				update : function(event, ui) {
					
				},
				stop : function(event, ui) {
					$(portletManagerOptions.commonSortAreas.join(",")).each(function() {
						//$(this).height($(this).height() - 100).css("background-color", "");
						//$(this).css("padding-bottom", 0);
					});
				}
			}).css("min-height", "100px");
		}
		
		function setPortletGroupSortable() {
			$("ul", "#divPortletControl").sortable({
				revert: true,
				connectWith: $.merge($.merge([], portletManagerOptions.commonSortAreas), portletManagerOptions.sortAreas).join(","),
				//handle : "li",
				placeholder: "ui-state-highlight",
				forcePlaceholderSize: true,
				cancel : "li.selected",
				start : function(event, ui) {
					control.css("overflow-y", "visible");
				},
				update : function(event, ui) {
					var $li = ui.item;
					
					if($li.parent()[0] != this) {
						if($.data($li[0], "publicOption") != 1 && $.inArray("#"+$li.parent().attr("id"), portletManagerOptions.commonSortAreas) >= 0) {
							// 공용 포틀릿이 아닌데 공용 영역으로 올라가면...
							alert(iKEPLang.mainPortlet.personalPortletMessage);
						}else if($.data($li[0], "publicOption") == 1 && $.inArray("#"+$li.parent().attr("id"), portletManagerOptions.sortAreas) >= 0) {
							// 공용 포틀릿이 아닌데 공용 영역으로 올라가면...
							alert(iKEPLang.mainPortlet.publicPortletMessage);
						} else {
							var portletId = $.data($li[0], "portletId");
							var multipleMode = $.data($li[0], "multipleMode");
							
							if(multipleMode == '0') {
								$li.addClass("selected");
								$li.children("a").addClass("selected");
							}
							for(var i=0;i<portletManagerOptions.portlets.length;i++) {
								if(portletManagerOptions.portlets[i].id == portletId) {
									var createIndex = $li.prevAll().length;
									createPortlet(portletManagerOptions.portlets[i], $li.parent(), createIndex);
									break;
								}
							}
						}
					} else {
						// alert(포틀릿 생성 영역으로 이동해 주세요.);
					}

					$(this).sortable("cancel");
				},
				stop : function(event, ui) {
					control.css("overflow-y", "auto");
				}
			});
		}
		
		// 포틀릿 설정 영역 관련 UI생성 및 이벤트 설정
		function setControl(portlet) {
			// 포틀릿 영역의 포틀릿 아이콘 클릭 이벤트
			control = $("#divPortletControl").bind("click", function(event) {
				if(!portletManagerOptions.isSetting) {
					var $li = event.target.tagName.toLowerCase() == "li" ? $(event.target) : $(event.target).parents("li:first");
					if($li.length > 0) {
						var $anchor = $li.children("a");
						
						if(!$anchor.hasClass("selected")) {
							var portletId = $.data($li[0], "portletId");
							var multipleMode = $.data($li[0], "multipleMode");
							
							if(multipleMode == '0') {
								$li.addClass("selected");
								$anchor.addClass("selected");
							}
							for(var i=0;i<portletManagerOptions.portlets.length;i++) {
								if(portletManagerOptions.portlets[i].id == portletId) {
									createPortlet(portletManagerOptions.portlets[i], portletManagerOptions.portlets[i].area);
									break;
								}
							}
						}
					}
				}
			});
			
			control.append('<ul/>');
			//	.parent().hide();
			
			if(portletManagerOptions.isSetting === true) {
				setCommonPortletSortable();
				setPortletGroupSortable();
			}
			
			// 포틀릿 그룹 생성
			var group = $("#divPortletGroup").append('<ul/>').children();
			$.each(portletManagerOptions.portletGroups, function(idx) {
				var $li = $('<li/>').appendTo(group)
					.append('<a href="#a"/>').children().text(this.name).attr("title", this.desc)
					.end();
				$.data($li[0], "code", this.code);
				
				if(idx == 0) {
					setPortletIcons($li);
				}
			});
			
			// 포틀릿 그룹 클릭 이벤트
			group.click(function(event) {
				var $el = $(event.target.tagName.toLowerCase() == "a" ? event.target.parentNode : event.target);
				if(!$el.hasClass("licurrent"))
					setPortletIcons($el);
			});
		}
		
		// 포틀릿 아이콘 생성 : 포틀릿 그룹별 생성
		function setPortletIcons($li) {
			var portletGroupCode = $.data($li[0], "code");
			var $ul = control.children().empty();
			
			$li.siblings(".licurrent").removeClass("licurrent")
				.end().addClass("licurrent");
			
			var createPortletIds = [];
			$.each(createdPortlets, function() { createPortletIds.push(this.id); });
			
			$.each(portletManagerOptions.portlets, function(index) {
				if(portletManagerOptions.isSetting || this.publicOption == 0) {
					if((this.groupId == portletGroupCode || portletGroupCode == 'ALL')) {
						var $li = $.tmpl(tplPortletIcon, this).appendTo($ul);
						$.data($li[0], "portletId", this.id);
						$.data($li[0], "multipleMode", this.multipleMode);
						$.data($li[0], "publicOption", this.publicOption);
						
						// 이미 생성된 포틀릿이면 포틀릿 아이콘 disable
						if(this.multipleMode == '0') {
							if($.inArray(this.id, createPortletIds) > -1) {
								$li.addClass("selected");
								$li.children("a").addClass("selected"); 
							}
						}
						
						if(this.publicOption == '1') {	// 공용 포틀릿 표시
							$li.find("span").prepend('<strong class="public_portlet">*</strong>');
						}
					}
				}
			});
		}
		
		// 포틀릿 생성
		function createPortlet(info, area, createIndex) {
			var pageLayoutDiv = $(area);
			var params = {portletId:info.id, pageLayoutId:$.data(pageLayoutDiv[0], "pageLayoutId"), colIndex:$.data(pageLayoutDiv[0], "colIndex"), rowIndex:1, systemCode:portletManagerOptions.systemCode};
			var successEvent = function(portletConfigId) {
				var options = $.extend({viewMode:"NORMAL", portletConfigId:portletConfigId, portletId:info.id, headerMode:info.headerMode, publicOption:info.publicOption, moveable:info.moveable, isSetting:portletManagerOptions.isSetting, createIndex:createIndex||-1}, info.options);
				
				var portlet = new info.constructor(area, options);
				
				createdPortlets.push({id:info.id, portletConfigId:portletConfigId, portlet:portlet});
				
				portlet.box.container.bind("destroy", destroyPortlet);	// 포틀릿 삭제 이벤트 설정
			};
			
			if(portletManagerOptions.isSetting) {
				successEvent(null);
			} else {
				$.ajax({
					url : contextPath + "/portal/admin/screen/portletConfig/createPortletConfig.do",
					data : params,
					type : "post",
					dataType : "html",
					success : successEvent,
					error : function() {alert('portlet create error!');}
				});
			}
		}
		
		// 포틀릿 위치 이동
		function movePortlet(portletConfigId, pageLayoutId, colIndex, rowIndex) {
			if(portletManagerOptions.isSetting) {
				
			} else {
				$.ajax({
					url : contextPath + "/portal/admin/screen/portletConfig/updatePortletConfig.do",
					data : {portletConfigId:portletConfigId, pageLayoutId:pageLayoutId, colIndex:colIndex, rowIndex: rowIndex, systemCode:portletManagerOptions.systemCode},
					type : "post",
					dataType : "html",
					success : function(result) {
						
					},
					error : function() {alert('error');}
				});
			}
		};
		
		// 포틀릿 삭제
		function destroyPortlet(event, currPortlet) {
			
			var containerState = currPortlet.box.getContainerState();
			
			createdPortlets = $.grep(createdPortlets, function(P) {
				var fnDestroyPortlet = function(result) {
					P.portlet = null;
					
					if (control) {
						var $portletIcons = control.children().children();
						$portletIcons.each(function() {
							if($.data(this, "multipleMode") == '0' && $.data(this, "portletId") == P.id) {
								$(this).removeClass("selected");
								$(this).children("a").removeClass("selected");
								return false;
							}
						});
					}					
					if(containerState == "MAX") {
						location.reload();
					}

				};
				
				if(P.portlet == currPortlet) {
					if(portletManagerOptions.isSetting) {
						fnDestroyPortlet();
					} else {
						$.ajax({
							//async : false,
							url : contextPath + "/portal/admin/screen/portletConfig/removePortletConfig.do",
							data : { portletConfigId : $.data(P.portlet.box.container[0], "portletConfigId"), portletId : $.data(P.portlet.box.container[0], "portletId"), systemCode:portletManagerOptions.systemCode},
							type : "post",
							dataType : "html",
							success : fnDestroyPortlet,
							error : function() {alert('error');}
						});
					}
					
					return false;	// 삭제된 포틀릿 걸러내기
				}
				return true;
			});
		};
		
		this.getOptions = function(optionName) {
			if(optionName) {
				return portletManagerOptions[optionName] || null;
			} else {
				return portletManagerOptions;
			}
		};
		
		this.getCreatedPortlets = function() {
			return createdPortlets;
		};
		
		this.getPortlet = function(portletId) {
			var portlet;
			$.each(createdPortlets, function(idx, portletInfo) {
				if(portletId == portletInfo.id) {
					portlet = portletInfo.portlet;
					return;
				}
			});
			
			return portlet;
		};
		
		this.getPortletByConfigId = function(configId) {
			var portlet;
			$.each(createdPortlets, function(idx, portletInfo) {
				if(configId == portletInfo.portletConfigId) {
					portlet = portletInfo.portlet;
					return;
				}
			});
			
			return portlet;
		};
		
		this.getPortletByContainer = function(container) {
			var portlet;
			$.each(createdPortlets, function(idx, portletInfo) {
				if(container == portletInfo.portlet.box.container[0]) {
					portlet = portletInfo.portlet;
					return;
				}
			});
			
			return portlet;
		};
		
		this.setLayoutRemoveEvent = function(layout) {
			setLayoutRemoveEvent(layout);
		};
		
		this.resetSortable = function(isCommon) {
			if(isCommon) setCommonPortletSortable();
			else setPortletSortable();
			setPortletGroupSortable();
		};
		
		initial();
		
		PortletManager.instance = this;	// instance setting
	};
	
	/*
	 * Portlet manager instance detecting.
	 */
	PortletManager.instance = null;
	PortletManager.getInstance = function() { return PortletManager.instance; };
	
	// portlet box class
	PortletBox = function(div, options) {
		options = $.extend({createIndex:-1, viewMode:"NORMAL"}, options);

		var baseImgPath = iKEP.getContextRoot() + "/base/images/theme/theme01";
		var $div = $(div);
		var $divParent = null;
		var caption = null;
		
		var divConfig = null;
		var divContent = null;
		
		var bodyHeight = 0;
		var containerState = options.viewMode;	//MIN:minimize, NORMAL:restore, MAX:maximize
		var duration = 500;
		var viewModeUdateFlag = false;
		
		var classBaseName = "po_icon_";
		
		var icons = null;
		
		this.container = null;
		this.body = null;
		
		this.initial = function() {
			options.isSetting = options.isSetting === true ? true : false;
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
				help : {name:"help", icon:"help", show:false, desc:iKEPLang.mainPortlet.help},
				more : {name:"more", icon:"more", show:false, desc:iKEPLang.mainPortlet.more},
				config : {name:"config", icon:"config", show:false, desc:iKEPLang.mainPortlet.config},
				reload : {name:"reload", icon:"refresh", show:false, desc:iKEPLang.mainPortlet.reload},
				maximize : {name:"maximize", icon:"max", show:false, desc:iKEPLang.mainPortlet.maximize},
				minimize : {name:"minimize", icon:"fold", show:false, desc:iKEPLang.mainPortlet.minimize},
				restore : {name:"restore", icon:"restore", show:false, desc:iKEPLang.mainPortlet.restore},
				close : {name:"close", icon:"close", show:false, desc:iKEPLang.mainPortlet.close}
			}, optionalIcons);
			
			this.container = $('<div/>').addClass("portletUnit");	//portlet
			
			if(options.createIndex < 1 || $div.is(":empty")) this.container.prependTo($div);	// 해당 포틀릿 영역에서 최 상단에 생성되도록
			else $div.children("div.portletUnit").eq(options.createIndex-1).after(this.container);
			
			$.data(this.container[0], "portletConfigId", options.portletConfigId || 0);
			$.data(this.container[0], "portletId", options.portletId || 0);
			
			if(containerState == "MIN") {
				this.body = $('<div/>').addClass("portletUnit_c")	//body
				.append($('<div/>').css({display:"none"}))
				.append($('<div/>'))
				.append($('<div class="l_b_corner"></div><div class="r_b_corner"></div>'))
				.appendTo(this.container);
			} else {
				this.body = $('<div/>').addClass("portletUnit_c")	//body
				.append($('<div/>'))
				.append($('<div/>'))
				.append($('<div class="l_b_corner"></div><div class="r_b_corner"></div>'))
				.appendTo(this.container);
			}
			
			/*
			this.body.bind("reset:containerHeight", function(event) {
				$("div.content", this).css("height", "");
				var container = this;
				//setTimeout(function() {
					//$("div.content", container).css({height:$(container).height()});
				//}, 10);
			});
			*/
			
			divContent = this.body.children().eq(0).addClass("content");
			
			//아이콘 div체크 부분
			if(options.headerMode == '1') {
				var divId = "";
				
				if((!options.isSetting && options.publicOption == 1) || options.moveable == 0) {
					divId = "noMove";  
				} else {
					divId = "move";
				}
				
				caption = $('<div/>').addClass("portletUnit_t").attr("id", divId)	//caption clear
				.append('<div/>').children().addClass("po_title").append('<h2/>').children().append(options.title).end().end()	
				.append('<div/>').children().eq(1).addClass("po_icon").end().end()
				.prependTo(this.container);
				divConfig = this.body.children().eq(1).addClass("config").hide();
				
				this.setIcon();
			}
		};
		
		this.setContent = function(html) {
			try {
				divConfig.html("");
				divConfig.hide();
			} catch(e) {}
			
			if(containerState != "MIN") {
				divContent.css({display:""});
			}
			
			divContent.html(html);
			
			/*
			var container = this.body;
			
			$("img:last", this.body).bind("load", function() {// 컨텐츠 사이즈 디텍트 문제로 인해...(height)
				//divContent.css({height:container.height()});
			});
			container.trigger("reset:containerHeight");
			*/
		};
		
		this.getContainerState = function() {
			return containerState;
		};
		
		this.getDivParent = function() {
			return $divParent;
		};
		
		this.showConfig = function(html) {
			try {
				divContent.html("");
				divContent.hide();
			} catch(e) {}
			
			divConfig.html(html).css({display:""});
		};
		
		this.setIcon = function() {
			var $iconBar = $("div.po_icon", caption);
			var $ul = $('<ul/>').appendTo($iconBar);
			
			$.each(icons, function(index, icon) {
				if(icon && icon.show == true) {
					var $icon = $('<li class="po_icon_' + icon.icon + '"><a href="#a" title="'+icon.desc+'"><span>' + icon.name + '</span></a></li>').appendTo($ul);
					$.data($icon[0], "method", icon.name);
				}
			});
			
			var self = this;
			$ul.click(function(event) {
				var $el = event.target.tagName.toLowerCase() == "li" ? $(event.target) : $(event.target).parents("li:first");
				switch($.data($el[0], "method")) {
					case icons.more.name :	// 더보기
						if(icons.more.callback) icons.more.callback();
						if(icons.more.url) {
							location.href = icons.more.url;
						}
						
						break;
					case icons.config.name :	// 설정
						if(containerState != 'MIN') {
							if(!options.isSetting || options.publicOption == 1) {
								if(divConfig.is(":hidden")) {
									divContent.html("");
									divContent.hide();
									self.container.trigger("click:config");
								} else {
									divConfig.html("");
									divConfig.hide();
									self.container.trigger("click:reload");
								}
							}
						}
						break;
					case icons.reload.name :	// 갱신
						if(containerState != 'MIN') {
							divConfig.html("");
							divConfig.hide();
							self.container.trigger("click:reload");
						}
						break;
					case icons.maximize.name :	//최대화
						containerState = 'MAX';
						$divParent = self.container.wrap('<div/>').parent().addClass("portletUnit");
						
						var divMax = $("#portlet div.maximize").append(self.container).css({display:""});
						$("li."+classBaseName+icons.maximize.icon, $ul).hide();
						$("li."+classBaseName+icons.minimize.icon, $ul).hide();
						$("li."+classBaseName+icons.restore.icon, $ul).css({display:"inline-block"});
						divMax.siblings("div").hide();

						self.container.trigger("click:maximize");
						
						if(icons.maximize.callback) icons.maximize.callback();
						
						if(!options.isSetting && viewModeUdateFlag) {
							portletViewModeUpdate($.data(self.container[0], "portletConfigId"), "MAX");
						}						
						break;
					case icons.minimize.name :	// 최소화
						containerState = 'MIN';
						bodyHeight = self.body.height();

						$("li."+classBaseName+icons.maximize.icon, $ul).hide();
						$("li."+classBaseName+icons.minimize.icon, $ul).hide();
						$("li."+classBaseName+icons.restore.icon, $ul).css({display:"inline-block"});
						
						self.body.animate({height:"0px"}, duration, function() {
							$(this).children().eq(0).css({display:"none"});
							$(this).children().eq(1).css({display:"none"});
						});
						
						if(icons.minimize.callback) icons.minimize.callback();
						
						if(!options.isSetting && viewModeUdateFlag) {
							portletViewModeUpdate($.data(self.container[0], "portletConfigId"), "MIN");
						}
						break;
					case icons.restore.name :	// 이전크기
						switch(containerState) {
							case "MIN" :
								containerState = "NORMAL";
								self.body.css({height:"1px"}).animate({height:bodyHeight+"px"}, duration, function() {
									if(self.body.children().eq(0).html() == "") {
										self.body.children().eq(1).css("display", "");
									} else {
										self.body.children().eq(0).css("display", "");
									}
									
									self.body.css("height","");
								});
								break;
							case "MAX" :
								containerState = "NORMAL";
								var divMax = $("#portlet div.maximize").hide();
								divMax.siblings("div").css({display:""});
								$divParent.replaceWith(self.container);
								$divParent = null;
								
								self.container.trigger("click:reload");
								break;
						}
						
						$("li."+classBaseName+icons.maximize.icon, $ul).css({display:"inline-block"});
						$("li."+classBaseName+icons.minimize.icon, $ul).css({display:"inline-block"});
						$("li."+classBaseName+icons.restore.icon, $ul).hide();
						if(icons.restore.callback) icons.restore.callback();
						
						if(!options.isSetting && viewModeUdateFlag) {
							portletViewModeUpdate($.data(self.container[0], "portletConfigId"), 'NORMAL');
						}
						
						break;
					case icons.help.name : // 도움말
						self.container.trigger("click:help");
						break;
					case icons.close.name :	// 닫기
						if(!confirm(iKEPLang.mainPortlet.confirmRemoveMessage)) {
							break;
						}
						
						self.container.trigger("click:close");
						if(icons.close.callback) icons.close.callback();
						if($divParent) $divParent.remove();
						else self.container.remove();
						break;
				}
			});
			
			//포틀릿 처음 로딩시 디비 업데이트 안되게 처리
			if(containerState == "NORMAL") {
				$("li."+classBaseName+icons.restore.icon, $iconBar).trigger("click");
			} else if (containerState == "MIN"){
				$("li."+classBaseName+icons.minimize.icon, $iconBar).trigger("click");
			} else if (containerState == "MAX"){
				$("li."+classBaseName+icons.maximize.icon, $iconBar).trigger("click");
			}
			
			viewModeUdateFlag = true;
		};
		
		function portletViewModeUpdate(portletConfigId, updateViewMode) {
			$.ajax({
				url : iKEP.getContextRoot() + "/portal/admin/screen/portletConfig/updatePortletConfigViewMode.do",
				data : {portletConfigId:portletConfigId, viewMode:updateViewMode},
				type : "post",
				dataType : "html",
				success : function(result) {
				},
				error : function() {alert('error');}
			});
		};
		
		this.initial();
	};
	
	
	
	PortletSimple = function(div, options) {
		var basePath = iKEP.getContextRoot() + "/base/test/ajax_result";
		this.box = null;
		this.options = {
			title:"Simple Portlet",
			url:basePath + "/content_default.html",
			config:null,
			reload:null,
			maximize:null,
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
					self.options.maximize = icons.maximize || null;
					self.options.more = icons.more || null;
					self.options.help = icons.help || null;
				} else {
					self.options[key] = value;
				}
			});
			
			icons = $.extend(true, {
				more:false,
				config:false,
				reload:false,
				maximize:false,
				minimize:true,
				restore:true,
				close:false}, icons);
			
			this.box = new PortletBox(div, {
				title:this.options.title,
				publicOption : this.options.publicOption,
				moveable : this.options.moveable,
				isSetting : this.options.isSetting,
				icons : icons,
				viewMode : options.viewMode,
				headerMode : options.headerMode,
				portletConfigId : options.portletConfigId,
				portletId : options.portletId,
				createIndex : this.options.createIndex
			});
			
			//this.loadContent();
			setTimeout(function() { self.loadContent(); }, 0);
			this.setIconEvent();
		};
		
		this.loadContent = function() {
			var self = this;
			var url;
			
			if(self.box.getContainerState() == "MAX") {
				url = self.options.maximize.url;
			} else {
				url = self.options.url;
			}
			
			if(url) {
				$.ajax({
					url : url,
					data : {portletConfigId:$.data(self.box.container[0], "portletConfigId"), portletId: self.options.portletId},
					type : "get",
					loadingElement : {container : self.box.container},
					success : function(result) {
						self.box.setContent(result);
					},
					error : function() {
						self.box.container.children().eq(1).children().eq(0).html("");
					}
				});
			}
			
		};
		
		this.setIconEvent = function() {
			var self = this;
			this.box.container.bind("click:reload", function(event) {	// reload
				if(self.options.reload && self.options.reload.callback) {
					self.options.reload.callback();
				} else {
					self.loadContent();
				}
			});
			
			this.box.container.bind("click:config", function(event) {	//config
				if(self.options.config.callback) self.options.config.callback();
				if(self.options.config.url) {
					$.ajax({
						url : self.options.config.url,
						data : {portletConfigId:$.data(self.box.container[0], "portletConfigId"), portletId: self.options.portletId},
						type : "get",
						loadingElement : {container : self.box.container},
						success : function(result) {
							self.box.showConfig(result);
						},
						error : function() {
							alert("error");
						}
					});
				}
			});
			
			this.box.container.bind("click:help", function(event){
				if(self.options.help && self.options.help.url) {
					var title = self.options.title + iKEPLang.mainPortlet.titHelpPopup;
					var options = {windowTitle:title, documentTitle:title, width:600, height:500};
					iKEP.portletPopupOpen(self.options.help.url, options);
				}
			});
			
			this.box.container.bind("click:close", function(event) {
				self.box.container.trigger("destroy", self);
				self.box = null;
			});
			
			this.box.container.bind("click:maximize", function(event) {
				if(self.options.maximize.callback) self.options.maximize.callback();
				if(self.options.maximize.url) {
					self.loadContent();
				}
			});
		};
		
		this.initial(div, options);
	};
	
	
	PortletSimple.setListCount = function(portletConfigId,listSize,refreshUrl,portletId)
	{
		$jq.ajax({
			url : iKEP.getContextRoot()+"/portal/admin/screen/portlet/setPortletListSize.do",
			data : {portletConfigId:portletConfigId, listSize:listSize, portletId: portletId},
			type : "post",
			dataType : "html",
			success : function(result) {
				alert(iKEPLang.mainPortlet.configSuccessMessage);
				PortletSimple.load(portletConfigId,refreshUrl);
			},
			error : function() {alert(iKEPLang.mainPortlet.configFailMessage);}
		});
	};
	
	PortletSimple.setUserConfig = function(portletConfigId,propertyName,propertyValue,refreshUrl)
	{
		$jq.ajax({
			url : iKEP.getContextRoot()+"/portal/admin/screen/portlet/setPortletUserConfig.do",
			data : {portletConfigId:portletConfigId, propertyName:propertyName, propertyValue:propertyValue},
			type : "post",
			dataType : "html",
			success : function(result) {
				alert(iKEPLang.mainPortlet.configSuccessMessage);
				PortletSimple.load(portletConfigId,refreshUrl);
			},
			error : function() {alert(iKEPLang.mainPortlet.configFailMessage);}
		});
	};
	
	PortletSimple.load = function(portletConfigId, loadUrl, errorMessage)
	{
		if(loadUrl.indexOf("?" )> -1)		{
			loadUrl=loadUrl+"&portletConfigId="+portletConfigId;
		}else{
			loadUrl=loadUrl+"?portletConfigId="+portletConfigId;
		}
		var container = $jq("#"+portletConfigId).parents("div.portletUnit_c");

		container.children("div.config").empty().hide();
		container.children("div.content").load(loadUrl,function(){
			//container.trigger("reset:containerHeight");
		}).error(function(event, request, settings) { 
			if(errorMessage){
				alert(errorMessage); 
			}
			else{
				alert("error"); 
			}
		}).show(); 
	};
	
	PortletSimple.loadPost = function(portletConfigId, loadUrl, formName, errorMessage)
	{
		if(loadUrl.indexOf("?" )> -1)		{
			loadUrl=loadUrl+"&portletConfigId="+portletConfigId;
		}else{
			loadUrl=loadUrl+"?portletConfigId="+portletConfigId; 
		}
		
		var container = $jq("#"+portletConfigId).parents("div.portletUnit_c");
		
		$jq.post(loadUrl, $("#"+formName).serialize())
		.success(function(data) {
			container.children("div.config").empty().hide();
	    	container.children("div.content").html(data,function(){
	    		//container.trigger("reset:containerHeight");
	    	}).show(); 
		 })
        .error(function(event, request, settings) { 
        	if(errorMessage){
				alert(errorMessage); 
			}
			else{
				alert("error"); 
			}
        });
	};
	
	PortletIFrame = function(div, options) {
		var basePath = iKEP.getContextRoot() + "/base/test/ajax_result";
		this.box = null;
		this.options = {
			title:"IFrame Portlet",
			url:basePath + "/content_default.html",
			config:null,
			reload:null,
			maximize:null,
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
					self.options.maximize = icons.maximize || null;
					self.options.more = icons.more || null;
					self.options.help = icons.help || null;
				} else {
					self.options[key] = value;
				}
			});
			var icons = $.extend(true, {
				more:false,
				config:false,
				reload:true,
				maximize:false,
				minimize:true,
				restore:true,
				close:false}, icons);
			this.box = new PortletBox(div, {
				title:this.options.title,
				publicOption : this.options.publicOption,
				moveable : this.options.moveable,
				isSetting : this.options.isSetting,
				icons:icons,
				viewMode : options.viewMode,
				headerMode : options.headerMode,
				portletConfigId : options.portletConfigId,
				portletId : options.portletId,
				createIndex : this.options.createIndex
			});
			
			this.loadContent();
			this.setIconEvent();
		};
		
		this.loadContent = function() {
			var self = this;
			var url;
			
			if(self.box.getContainerState() == "MAX") {
				url = self.options.maximize.url;
			} else {
				url = self.options.url;
			}
			
			if(url) {
				$jq.ajax({
					url : iKEP.getContextRoot()+"/portal/main/portletCheckUrl.do",
					data : {checkUrl:url},
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == '200') {
							self.box.setContent('<iframe src="' + url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
							
							var divContent = self.box.body.children().eq(0);
							var iFrame = $("iframe", divContent);
							
							var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
							body.onload = function() {
								var contentHeight = ($(body).height()+body.scrollMaxY) + "px";
								iFrame.css({height:contentHeight});
								$(iFrame).parent().css({height:contentHeight});
							};
						} else {
							self.box.setContent(iKEPLang.mainPortlet.noPage);
						}
					},
					error : function() {alert("error");}
				});
				
				
				self.box.setContent('<iframe src="' + url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
				
				var divContent = self.box.body.children().eq(0);
				var iFrame = $("iframe", divContent);
				
				var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
				body.onload = function() {
					var contentHeight = ($(body).height()+body.scrollMaxY) + "px";
					iFrame.css({height:contentHeight});
					$(iFrame).parent().css({height:contentHeight});
				};
			}
		};
		
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
									
					self.box.showConfig('<iframe src="' + self.options.config.url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
					
					var divContent = self.box.body.children().eq(1);
					var iFrame = $("iframe", divContent);
					
					var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
					body.onload = function() {
						var contentHeight = ($(body).height()+body.scrollMaxY) + "px";
						iFrame.css({height:contentHeight});
						$(iFrame).parent().css({height:contentHeight});
					};
					/*
					$.get(self.options.config.url, {portletConfigId:$.data(self.box.container[0], "portletConfigId")}, function(result) {
						self.box.showConfig(result);
					});
					*/
				}
			});
			
			this.box.container.bind("click:help", function(event){
				if(self.options.help && self.options.help.url) {
					var title = self.options.title + iKEPLang.mainPortlet.titHelpPopup;
					var options = {windowTitle:title, documentTitle:title, width:600, height:500};
					iKEP.portletPopupOpen(self.options.help.url, options);
				}
			});
			
			this.box.container.bind("click:close", function(event) {
				self.box.container.trigger("destroy", self);
				self.box = null;
			});
			
			this.box.container.bind("click:maximize", function(event) {
				if(self.options.maximize.callback) self.options.maximize.callback();
				if(self.options.maximize.url) {
					self.loadContent();
				}
			});
		};
		
		this.initial(div, options);
	};
	
	PortletIFrameExt = function(div, options) {
		var basePath = iKEP.getContextRoot() + "/base/test/ajax_result";
		this.box = null;
		this.options = {
			title:"IFrame Portlet",
			url:basePath + "/content_default.html",
			config:null,
			reload:null,
			maximize:null,
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
					self.options.maximize = icons.maximize || null;
					self.options.more = icons.more || null;
					self.options.help = icons.help || null;
				} else {
					self.options[key] = value;
				}
			});
			var icons = $.extend(true, {
				more:false,
				config:false,
				reload:true,
				maximize:false,
				minimize:true,
				restore:true,
				close:false}, icons);
			this.box = new PortletBox(div, {
				title:this.options.title,
				publicOption : this.options.publicOption,
				moveable : this.options.moveable,
				isSetting : this.options.isSetting,
				icons:icons,
				viewMode : options.viewMode,
				headerMode : options.headerMode,
				portletConfigId : options.portletConfigId,
				portletId : options.portletId,
				createIndex : this.options.createIndex
			});
			
			this.loadContent();
			this.setIconEvent();
		};
		
		this.loadContent = function() {
			var self = this;
			var url;
			
			if(self.box.getContainerState() == "MAX") {
				url = self.options.maximize.url;
			} else {
				url = self.options.url;
			}
			
			if(url) {
				$jq.ajax({
					url : iKEP.getContextRoot()+"/portal/main/portletCheckUrl.do",
					data : {checkUrl:url},
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == '200') {
							self.box.setContent('<iframe src="' + url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
							
							var divContent = self.box.body.children().eq(0);
							var iFrame = $("iframe", divContent);
							var contentHeight = self.options.iframeHeight + "px";
							iFrame.css({height:contentHeight});
							$(iFrame).parent().css({height:contentHeight});
						} else {
							self.box.setContent(iKEPLang.mainPortlet.noPage);
						}
					},
					error : function() {alert("error");}
				});
			}
		};
		
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
									
					self.box.showConfig('<iframe src="' + self.options.config.url + '" frameborder="0" style="width:100%; height:100%;"></iframe>');
					
					var divContent = self.box.body.children().eq(1);
					var iFrame = $("iframe", divContent);
					
					var body = iFrame[0].contentWindow || iFrame[0].contentDocument;
					body.onload = function() {
						var contentHeight = ($(body).height()+body.scrollMaxY) + "px";
						iFrame.css({height:contentHeight});
						$(iFrame).parent().css({height:contentHeight});
					};
					/*
					$.get(self.options.config.url, {portletConfigId:$.data(self.box.container[0], "portletConfigId")}, function(result) {
						self.box.showConfig(result);
					});
					*/
				}
			});
			
			this.box.container.bind("click:help", function(event){
				if(self.options.help && self.options.help.url) {
					var title = self.options.title + iKEPLang.mainPortlet.titHelpPopup;
					var options = {windowTitle:title, documentTitle:title, width:600, height:500};
					iKEP.portletPopupOpen(self.options.help.url, options);
				}
			});
			
			this.box.container.bind("click:close", function(event) {
				self.box.container.trigger("destroy", self);
				self.box = null;
			});
			
			this.box.container.bind("click:maximize", function(event) {
				if(self.options.maximize.callback) self.options.maximize.callback();
				if(self.options.maximize.url) {
					self.loadContent();
				}
			});
		};
		
		this.initial(div, options);
	};
	
	
	
	PortletJSON = function(div, options) {
		var basePath = iKEP.getContextRoot() + "/base/test/ajax_result";
		this.box = null;
		this.options = {
			title:"PortletJSON Portlet",
			url:basePath + "/content_json.html",
			config:null,
			reload:null,
			maximize:null,
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
					self.options.maximize = icons.maximize || null;
					self.options.more = icons.more || null;
				} else {
					self.options[key] = value;
				}
			});
			
			var icons = $.extend(true, {
				more:false,
				config:false,
				reload:false,
				maximize:false,
				minimize:true,
				restore:true,
				close:false}, icons);
			
			this.box = new PortletBox(div, {
				title:this.options.title,
				publicOption : this.options.publicOption,
				moveable : this.options.moveable,
				isSetting : this.options.isSetting,
				icons:icons,
				viewMode : options.viewMode,
				headerMode : options.headerMode,
				portletConfigId : options.portletConfigId,
				portletId : options.portletId,
				createIndex : this.options.createIndex
			});
			
			this.loadContent();
			this.setIconEvent();
		};
		
		this.loadContent = function() {
			var self = this;
			var url;
			
			if(self.box.getContainerState() == "MAX") {
				url = self.options.maximize.url;
			} else {
				url = self.options.url;
			}
			
			if(url) {
				$.getJSON(this.options.url, function(result) {
					var html = '<ul>';
					$.each(result, function(index, item) {
						html += '<li>' + (index+1) + '.' + item.title + '</li>';
					});
					html += '</ul>';
					
					self.box.setContent(html);
				});
			}
		};
		
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
					$.get(self.options.config.url, {portletConfigId:$.data(self.box.container[0], "portletConfigId")} ,function(result) {
						self.box.showConfig(result);
					});
				}
			});
			
			this.box.container.bind("click:close", function(event) {
				self.box.container.trigger("destroy", self);
				self.box = null;
			});
			
			this.box.container.bind("click:maximize", function(event) {
				if(self.options.maximize.callback) self.options.maximize.callback();
				if(self.options.maximize.url) {
					self.loadContent();
				}
			});
		};
		
		this.initial(div, options);
	};
})(jQuery);

