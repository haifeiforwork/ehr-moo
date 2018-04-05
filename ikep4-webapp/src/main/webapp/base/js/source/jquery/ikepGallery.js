/*
 * iKEP4 image gallery
 * lightBox jquery plugin customize
 */
// ikepGallery ----------------------------------------------------------------------------
(function($) {
	$.fn.ikepGallery = function(settings) {
		settings = jQuery.extend({
			coverBgColor:		"#000",
			coverOpacity:		0.8,
			fixedNavigation:	false,
			minWidth :			200,
			borderSize:			0,
			resizeDuration:		400,
			keyToClose:	"x",	// close
			keyToPrev:	"p",	// previous image
			keyToNext:	"n",	// next image

			images:				[],
			currImg:			0,
			
			imgLoading:	iKEP.getContextRoot() + "/base/images/common/loading_b.gif",
			imgBtnPrev:	iKEP.getContextRoot() + "/base/images/common/btn_gallery_prev.png",
			imgBtnNext:	iKEP.getContextRoot() + "/base/images/common/btn_gallery_next.png",
			imgBtnClose:iKEP.getContextRoot() + "/base/images/common/btn_gallery_close.png",
			imgBlank:	iKEP.getContextRoot() + "/base/images/common/blank.gif"
		}, settings);

		var $targets = this;

		function initialize() {
			init($(this), $targets);
			
			return false;
		}
		function init($target, $targets) {
			$("embed, object, select").css("visibility", "hidden");
			
			setInterface();

			settings.images.length = 0;
			settings.currImg = 0;

			if($targets.length == 1) {
				settings.images.push(new Array($target.attr("href"), $target.attr("title")));
			} else {
				for(var i=0; i < $targets.length; i++) {
					var $anchor = $($targets[i]);
					settings.images.push(new Array($anchor.attr("href"),$anchor.attr("title")));
					
					if($targets[i] == $target[0]) settings.currImg = i;
				}
			}
			
			setGallery();
		}
		function setInterface() {
			var html = '<div id="ikepGalleryCover"></div>' +
				'<div id="ikepGallery">' +
					'<div id="galleryImageBox">' +
						'<div>' +
							'<img id="galleryImage">' +
							'<div id="galleryNavigation"><a href="#a" id="galleryBtnPrev"></a><a href="#a" id="galleryBtnNext"></a></div>' +
							'<div id="gallery-loading" style="background-image:url(' + settings.imgLoading + ')"></div>' +
						'</div>' +
					'</div>' +
					'<div id="galleryDataBox">' +
						'<div>' +
							'<div id="galleryDataDetail"></div>' +
							'<div><a href="#a" id="galleryBtnClose"><img src="' + settings.imgBtnClose + '"/></a></div>' +
						'</div>' +
					'</div>' +
				'</div>';
			$('body').append(html);	

			var arrPageSizes = getPageSize();

			$('#ikepGalleryCover').css({
				backgroundColor:	settings.coverBgColor,
				opacity:			settings.coverOpacity,
				width:				arrPageSizes[0],
				height:				arrPageSizes[1]
			}).fadeIn();

			var arrPageScroll = getPageScroll();

			$('#ikepGallery').css({
				top:	arrPageScroll[1] + (arrPageSizes[3] / 10),
				left:	arrPageScroll[0]
			}).show();

			$('#ikepGalleryCover, #ikepGallery, #galleryBtnClose').click(function() {
				close();									
			});

			$(window).resize(function() {
				var $cover = $('#ikepGalleryCover');
				if($cover.length > 0) {
					$cover.css({width:0, height:0});

					var arrPageSizes = getPageSize();
					var $box = $("#ikepGallery");
					var $img = $("#galleryImageBox");
					var height = $box.position().top + $box.height();
					var width = $img.position().left + $img.width();

					$cover.css({
						width:		arrPageSizes[0] > width ? arrPageSizes[0] : (width + 2),
						height:		arrPageSizes[1] > height ? arrPageSizes[1] : (height + 2)
					});
					
					var arrPageScroll = getPageScroll();
					$('#ikepGallery').css({
						top:	arrPageScroll[1] + (arrPageSizes[3] / 10),
						left:	arrPageScroll[0]
					});
				}
			});
		}
		function setGallery() {
			$('#gallery-loading').show();
			if ( settings.fixedNavigation ) {
				$('#galleryImage,#galleryDataBox').hide();
			} else {
				$('#galleryImage,#galleryNavigation,#galleryBtnPrev,#galleryBtnNext,#galleryDataBox').hide();
			}

			var objImagePreloader = new Image();
			objImagePreloader.onload = function() {
				$('#galleryImage').attr('src',settings.images[settings.currImg][0]);
				
				// 화면내에서 잘려 보이지 않도록 하기 위해 사이즈 조절
				var offset = {width:30, height:130};
				var viewSize = {width:$(window.document).width()-offset.width, height:$(window.document).height()-offset.height};
				var imageSize = {width:objImagePreloader.width, height:objImagePreloader.height};
				
				if(imageSize.width > viewSize.width || imageSize.height > viewSize.height) {
					if(imageSize.width / viewSize.width > imageSize.height / viewSize.height) {	//가로 사이즈 비율 적용
						imageSize.height = imageSize.height * (viewSize.width/imageSize.width);
						imageSize.width = viewSize.width;
					} else {	//세로사이즈 비율 적용
						imageSize.width = imageSize.width * (viewSize.height/imageSize.height);
						imageSize.height = viewSize.height;
					}
					var containerSize = {width:imageSize.width, height:imageSize.height};
				} else {
					var containerSize = {width:imageSize.width < settings.minWidth ? settings.minWidth : imageSize.width, height:imageSize.height};
				}

				resizeContainer(containerSize.width, containerSize.height);
				
				$('#galleryImage').attr({width:imageSize.width, height:imageSize.height});
				objImagePreloader.onload=function(){};
			};
			objImagePreloader.src = settings.images[settings.currImg][0];
		};
		function resizeContainer(intImageWidth,intImageHeight) {
			var intCurrentWidth = $('#galleryImageBox').width();
			var intCurrentHeight = $('#galleryImageBox').height();

			var intWidth = (intImageWidth + (settings.borderSize * 2));
			var intHeight = (intImageHeight + (settings.borderSize * 2));

			var intDiffW = intCurrentWidth - intWidth;
			var intDiffH = intCurrentHeight - intHeight;

			$('#galleryImageBox').animate({ width: intWidth+"px", height: intHeight+"px" },settings.resizeDuration,function() { showImage(); });
			if ( ( intDiffW == 0 ) && ( intDiffH == 0 ) ) {
				if ( $.browser.msie ) {
					pause(250);
				} else {
					pause(100);	
				}
			} 
			$('#galleryDataBox').css({ width: intImageWidth });
			$('#galleryBtnPrev,#galleryBtnNext').css({ height: intImageHeight + (settings.borderSize * 2) });
		};
		function showImage() {
			$('#gallery-loading').hide();
			$('#galleryImage').fadeIn(function() {
				showControl();
				setAavigator();
			});
			preloadNeighborImages();
		};
		function showControl() {
			$('#galleryDataBox').slideDown('fast', function() {
				/* customize code : UI 구성 완료시 cover 사이즈 재조정
				 * IE에서는 컨튼츠 영역을 넘어서는 이미지 랜더링 후 작은 이미지로 넘어 올때 영역을 정확히 디텍트 하지 못하는 문제 있음.(커버가 전체를 덤지 못함)
				 */
				$('#ikepGalleryCover').css({ width:0, height:0 });	// FF에서는 컨트츠 영역을 넘어서는 이미지 랜더링 후 작은 이미지로 넘어 올때 이미 커저있는 커버 사이즈 때문에 이를 다시 디텍트하도록 하기 위해서 사용
				// 이 경우 위와 같이 IE와 같은 문제가 발생하기도 함.(작은 이미지로 돌아 올때 커버가 전체를 덤지 못함)
					
				var arrPageSizes = getPageSize();
				var $box = $("#ikepGallery");
				var $img = $("#galleryImageBox");
				var height = $box.position().top + $box.height();
				var width = $img.position().left + $img.width();
				$('#ikepGalleryCover').css({
					width:		arrPageSizes[0] > width ? arrPageSizes[0] : (width + 2),
					height:		arrPageSizes[1] > height ? arrPageSizes[1] : (height + 2)
				});
			});

			if ( settings.images.length > 1 ) {
				var str = iKEPLang.gallery.imgCount.replace(/\#\{cur\}/, settings.currImg + 1).replace(/\#\{tot\}/, settings.images.length);
				$('#galleryDataDetail').html(str);
			}		
		}
		function setAavigator() {
			$('#galleryNavigation').show();

			$('#galleryBtnPrev,#galleryBtnNext').css({ 'background' : 'transparent url(' + settings.imgBlank + ') no-repeat' });
			
			if ( settings.currImg != 0 ) {
				if ( settings.fixedNavigation ) {
					$('#galleryBtnPrev').css({ 'background' : 'url(' + settings.imgBtnPrev + ') left center no-repeat' })
						.unbind()
						.bind('click',function() {
							settings.currImg = settings.currImg - 1;
							setGallery();
							return false;
						});
				} else {
					$('#galleryBtnPrev').unbind().hover(function() {
						$(this).css({ 'background' : 'url(' + settings.imgBtnPrev + ') left center no-repeat' });
					},function() {
						$(this).css({ 'background' : 'transparent url(' + settings.imgBlank + ') no-repeat' });
					}).show().bind('click',function() {
						settings.currImg = settings.currImg - 1;
						setGallery();
						return false;
					});
				}
			}
			
			if ( settings.currImg != ( settings.images.length -1 ) ) {
				if ( settings.fixedNavigation ) {
					$('#galleryBtnNext').css({ 'background' : 'url(' + settings.imgBtnNext + ') right center no-repeat' })
						.unbind()
						.bind('click',function() {
							settings.currImg = settings.currImg + 1;
							setGallery();
							return false;
						});
				} else {
					$('#galleryBtnNext').unbind().hover(function() {
						$(this).css({ 'background' : 'url(' + settings.imgBtnNext + ') right center no-repeat' });
					},function() {
						$(this).css({ 'background' : 'transparent url(' + settings.imgBlank + ') no-repeat' });
					}).show().bind('click',function() {
						settings.currImg = settings.currImg + 1;
						setGallery();
						return false;
					});
				}
			}
			enableKeyboardNavigator();
		}
		function enableKeyboardNavigator() {
			$(document).keydown(function(objEvent) {
				keyboardAction(objEvent);
			});
		}
		function disableKeyboardNavigation() {
			$(document).unbind();
		}
		function keyboardAction(objEvent) {
			if ( objEvent == null ) {	// IE
				keycode = event.keyCode;
				escapeKey = 27;
			} else {	// other browser
				keycode = objEvent.keyCode;
				escapeKey = objEvent.DOM_VK_ESCAPE;
			}
			
			key = String.fromCharCode(keycode).toLowerCase();
			if ( ( key == settings.keyToClose ) || ( key == 'x' ) || ( keycode == escapeKey ) ) {
				close();
			}

			if ( ( key == settings.keyToPrev ) || ( keycode == 37 ) ) {
				if ( settings.currImg != 0 ) {
					settings.currImg = settings.currImg - 1;
					setGallery();
					disableKeyboardNavigation();
				}
			}

			if ( ( key == settings.keyToNext ) || ( keycode == 39 ) ) {
				if ( settings.currImg != ( settings.images.length - 1 ) ) {
					settings.currImg = settings.currImg + 1;
					setGallery();
					disableKeyboardNavigation();
				}
			}
		}
		function preloadNeighborImages() {
			if ( (settings.images.length -1) > settings.currImg ) {
				objNext = new Image();
				objNext.src = settings.images[settings.currImg + 1][0];
			}
			if ( settings.currImg > 0 ) {
				objPrev = new Image();
				objPrev.src = settings.images[settings.currImg -1][0];
			}
		}
		function close() {	// remove
			$('#ikepGallery').remove();
			$('#ikepGalleryCover').fadeOut(function() { $(this).remove(); });

			$('embed, object, select').css({ 'visibility' : 'visible' });
		}
		function getPageSize() {
			var xScroll, yScroll;
			if (window.innerHeight && window.scrollMaxY) {	
				xScroll = window.innerWidth + window.scrollMaxX;
				yScroll = window.innerHeight + window.scrollMaxY;
			} else if (document.body.scrollHeight > document.body.offsetHeight){ // all but Explorer Mac
				xScroll = document.body.scrollWidth;
				yScroll = document.body.scrollHeight;
			} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla and Safari
				xScroll = document.body.offsetWidth;
				yScroll = document.body.offsetHeight;
			}
			var windowWidth, windowHeight;
			if (self.innerHeight) {	// all except Explorer
				if(document.documentElement.clientWidth){
					windowWidth = document.documentElement.clientWidth; 
				} else {
					windowWidth = self.innerWidth;
				}
				windowHeight = self.innerHeight;
			} else if (document.documentElement && document.documentElement.clientHeight) { // Explorer 6 Strict Mode
				windowWidth = document.documentElement.clientWidth;
				windowHeight = document.documentElement.clientHeight;
			} else if (document.body) { // other Explorers
				windowWidth = document.body.clientWidth;
				windowHeight = document.body.clientHeight;
			}	

			if(yScroll < windowHeight){
				pageHeight = windowHeight;
			} else { 
				pageHeight = yScroll;
			}

			if(xScroll < windowWidth){	
				pageWidth = xScroll;		
			} else {
				pageWidth = windowWidth;
			}
			arrayPageSize = new Array(pageWidth,pageHeight,windowWidth,windowHeight);
			return arrayPageSize;
		};
		function getPageScroll() {
			var xScroll, yScroll;
			if (self.pageYOffset) {
				yScroll = self.pageYOffset;
				xScroll = self.pageXOffset;
			} else if (document.documentElement && document.documentElement.scrollTop) { // IE6
				yScroll = document.documentElement.scrollTop;
				xScroll = document.documentElement.scrollLeft;
			} else if (document.body) {// other IE
				yScroll = document.body.scrollTop;
				xScroll = document.body.scrollLeft;	
			}
			arrayPageScroll = new Array(xScroll,yScroll);
			return arrayPageScroll;
		};
		 function pause(ms) {
			var date = new Date(); 
			curDate = null;
			do { var curDate = new Date(); }
			while ( curDate - date < ms);
		 };
		
		 return this.unbind('click').click(initialize);
	};
})(jQuery);