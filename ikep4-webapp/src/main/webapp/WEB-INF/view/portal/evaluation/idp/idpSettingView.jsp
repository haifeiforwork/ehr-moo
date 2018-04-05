<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/progress/progress.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/base/js/jquery/treeview/jquery.treeview.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/base/css/ess/treeview/jquery.treeview.css" />

<style>

.mask {
	position : absolute;
	left : 0;
	top : 0;
	z-index : 998;
	background-color : #000;
	display : none;
}

.search_popup {
	width : 600px;
	height : 500px;
	overflow-y : auto;
	background-color : #ffffff;
	z-index : 999;
	display : none;
}

textarea {
	overflow-y : hidden;
}

</style>

<script type="text/javascript">
var monthArray = [
      {code : "", name : "선택"},
      {code : "01", name : "1월"},
      {code : "02", name : "2월"},
      {code : "03", name : "3월"},
      {code : "04", name : "4월"},
      {code : "05", name : "5월"},
      {code : "06", name : "6월"},
      {code : "07", name : "7월"},
      {code : "08", name : "8월"},
      {code : "09", name : "9월"},
      {code : "10", name : "10월"},
      {code : "11", name : "11월"},
      {code : "12", name : "12월"}
];

var _currentButtonClass;
var _currentButtonIndex;

var _stellTreeLoaded = false;
var _orghTreeLoaded = false;
var _quliTreeLoaded = false;
var _eduTreeLoaded = false;

var _jsonStellData;
var _jsonOrghData;
var _jsonQuliData;
var _jsonEduData;

var _loadingImg = '<img src="/base/images/evaluation/ajax-loader.gif" />';

(function($){
	$(document).ready(function() {

		$.fetchTreeData = function(gubun) {
			$.ajax({
				url : "<c:url value='/portal/evaluation/idp/idpTree.do'/>"
				, type : "post"
				, data : {'gubun' : gubun}
				, success : function(result) {
					if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);
					} else {
						if(gubun == 'DUTY') {
							_jsonStellData = result.ET_TREE;

							$.buildTree({GUBUN : gubun, TREE : '#stellTree', CONTROL : '#stellSidetreecontrol'});
							$('.btn_tree_stell').each(function() { $(this).show('slow').prev().remove().end().bindButtonTreeOpen($('#treeStellDiv')); });
						} else if(gubun == 'ORGANIZATION') {
							_jsonOrghData = result.ET_TREE;

							$.buildTree({GUBUN : gubun, TREE : '#orghTree', CONTROL : '#orghSidetreecontrol'});
							$('.btn_tree_orgh').each(function() { $(this).show('slow').prev().remove().end().bindButtonTreeOpen($('#treeOrghDiv')); });
						} else if(gubun == 'QULITY') {
							_jsonQuliData = result.ET_TREE;

							$.buildTree({GUBUN : gubun, TREE : '#quliTree', CONTROL : '#quliSidetreecontrol'});
							$('.btn_tree_quli').each(function() { $(this).show('slow').prev().remove().end().bindButtonTreeOpen($('#treeQuliDiv')); });
						} else if(gubun == 'EDUCATION') {
							_jsonEduData = result.ET_TREE;

							$.buildTree({GUBUN : gubun, TREE : '#eduTree', CONTROL : '#eduSidetreecontrol'});
							$('.btn_tree_edu').each(function() { $(this).show('slow').prev().remove().end().bindButtonTreeOpen($('#treeEduDiv')); });
						}
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);
				}
			});
		}

		$.buildTree = function(panel) {
			var $nodeLvl0 = $(panel.TREE);

			var expandClass = 'expandable';
			var $expandDiv = $('<div class="hitarea expandable-hitarea"></div>');
			var $expandUl = $('<ul style="display:none;"></ul>');
			var $li = $(document.createElement('li'));
			var $a = $(document.createElement('a')).attr('name', 'linked');

			var treeData;
			if(panel.GUBUN == 'DUTY') {
				treeData = _jsonStellData;
			} else if(panel.GUBUN == 'ORGANIZATION') {
				treeData = _jsonOrghData;
			} else if(panel.GUBUN == 'QULITY') {
				treeData = _jsonQuliData;
			} else if(panel.GUBUN == 'EDUCATION') {
				treeData = _jsonEduData;
			}

			$.each(treeData, function(index, tree) {
				var nodeName = tree.TEXT;
				var nodeLevel = tree.TLEVEL;
				var nodeLink = tree.LINK;
				var nodeId = tree.CHILDID.replace(/\s/gi, "");
				var nodeParentId = tree.PARENTID.replace(/\s/gi, "");
				var nodeParentUlId = "ul_" + tree.PARENTID.replace(/\s/gi, "");

				var $cloneLi = $li.clone();
				var $cloneA = $a.clone();

				$cloneLi.attr('id', nodeId);
				$cloneA.attr('href', '#').attr('data-param', nodeLink).text(nodeName);
				$cloneLi.append($cloneA);

				if(nodeLevel == 1) {
					$nodeLvl0.append($cloneLi);
				} else {
					var $parentNode = $("#" + nodeParentId);
					var $parentUlNode = $('#' + nodeParentUlId);

					if($parentUlNode.length == 0) {
						var $cloneDiv = $expandDiv.clone();
						var $cloneUl = $expandUl.clone();
						$cloneUl.attr('id', nodeParentUlId);

						$parentNode.addClass(expandClass)
								   .prepend($cloneDiv)
								   .append($cloneUl.append($cloneLi));
					} else {
						$parentUlNode.append($cloneLi);
					}
				}
			});

			$nodeLvl0.treeview({
				collapsed: true,
				animated: "medium",
				control: panel.CONTROL,
				persist: "location"
			});

			$nodeLvl0.closest('.search_popup').bindLayerEvent();
		}

		$.fn.showLoadingImage = function() {
			$(_loadingImg).insertBefore($(this));
		}

		$.hideTreeButton = function() {
			$('.btn_tree_stell').each(function() { $(this).hide().showLoadingImage(); });
			$('.btn_tree_orgh').each(function() { $(this).hide().showLoadingImage(); });
			$('.btn_tree_quli').each(function() { $(this).hide().showLoadingImage(); });
			$('.btn_tree_edu').each(function() { $(this).hide().showLoadingImage(); });
		}

		$.fn.bindChangeStellCombo = function() {
			$(this).change(function() {
				if($(this).val() != '') {
					$(this).prev().val($(this).find('option:selected').text());
				} else {
					$(this).prev().val('');
				}
			});
		}

		$.fn.makeComboJob = function() {
			var $this = $(this);
			var _selectedValue = $this.val();

			$this.find('option').each(function() { $(this).remove(); });

			$this.append("<option value=''>선택</option>");
			<c:if test="${idplst.STELL ne '00000000'}">
				$this.append("<option value='${idplst.STELL}'>${idplst.STLTX}</option>");
			</c:if>

			var options = [];
			if($('[name=hpjob1]').val() != '00000000') options.push({'code' : $('[name=hpjob1]').val(), 'name' : $('[name=hpjob1t]').val()});
			if($('[name=hpjob2]').val() != '00000000') options.push({'code' : $('[name=hpjob2]').val(), 'name' : $('[name=hpjob2t]').val()});
			if($('[name=hpjob3]').val() != '00000000') options.push({'code' : $('[name=hpjob3]').val(), 'name' : $('[name=hpjob3t]').val()});

			$.each(options, function(index) {
				$this.append("<option value='" + options[index].code + "'>" + options[index].name + "</option>");
			});

			if(_selectedValue != null && _selectedValue != '') $this.val(_selectedValue).prop('selected', true);
		}

		$.fn.closeLayer = function() {
			$('.mask').hide();
			$(this).hide();
		}

		$.fn.bindTreeLink = function() {
			$(this).find('a[name=linked]').click(function(e) {
				e.preventDefault();

				var $this = $(this);
				var linkCode, linkText, linkType;
				var params = $this.data("param").split("?");

				if(params.length == 2) {
					var arr = params[1].split("&");
					for(var i = 0 ; i < arr.length ; i++) {
						if(arr[i].split("=")[0] == "pa_otype") {
							linkType = arr[i].split("=")[1];
						} else if(arr[i].split("=")[0] == "pa_objid") {
							linkCode = arr[i].split("=")[1];
							linkText = $this.text();
						}
					}

					$('.' + _currentButtonClass).eq(_currentButtonIndex).prev().val(linkText)
																  .end().prev().prev().val(linkCode);

					if(linkType == 'C') {
						$('select[name=idobj]').each(function() { $(this).makeComboJob(); });
					}

					$this.closest('.search_popup').closeLayer();
				} else {
					return false;
				}
			});
		}

		$.fn.bindLayerEvent = function() {
			var $this = $(this);

			$this.find('.btn_close').click(function(e) {
				e.preventDefault();
				$this.closeLayer();
			});

			$this.bindTreeLink();
		}

		$.fn.showLayer = function() {
			var maskHeight = $(document).height();
	        var maskWidth = $(window).width();

	        $('.mask').css({'width':maskWidth,'height':maskHeight});
	        $('.mask').fadeIn(1000);
	        $('.mask').fadeTo("slow",0.8);

	        var left = 250;
	        var top = 150;
	        $(this).css({'left':left,'top':top, 'position':'absolute'});

	        $(this).draggable();
	        $(this).show();
		}

		$.fn.bindButtonTreeOpen = function(layerObj) {
			$(this).click(function(e) {
				e.preventDefault();

				_currentButtonClass = $(this).attr('class').split(' ')[1];
				_currentButtonIndex = $('.' + _currentButtonClass).index(this);

				layerObj.find('.collapsable').each(function() {
					$(this).find('div.hitarea:first').click();
					$(this).find('ul').hide();
				});

				layerObj.showLayer();
			});
		}

		$.fn.bindButtonTreeDataDelete = function() {
			$(this).click(function(e) {
				e.preventDefault();

				$(this).prev().prev().val('');
				$(this).prev().prev().prev().val('');
			});
		}

		$.fn.toggleLastColumn = function(isDisplay) {
			var $this = $(this);

			if(isDisplay) {
				$this.find('thead > tr > th').eq(4).show()
					   				   .end().eq(3).removeAttr('colspan');
				$this.find('tbody > tr').each(function() {
					$(this).find('td').eq(5).show()
								.end().eq(4).removeAttr('colspan');
				});
				$this.find('.col_last').css('width', '18%');
				$this.find('.col_change').css('width', '18%');
			} else {
				$this.find('thead > tr > th').eq(4).hide()
		  		   					   .end().eq(3).prop('colspan', '2');
				$this.find('tbody > tr').each(function() {
					$(this).find('td').eq(5).hide()
								.end().eq(4).prop('colspan', '2');
				});
				$this.find('.col_last').css('width', '0%');
				$this.find('.col_change').css('width', '35%');
			}
		}

		$.removeButtonSearch = function() {
			$('.btn_search').each(function() {
				$(this).unbind().hide();
				$(this).next().unbind().hide();
				$(this).prev().removeAttr('class').addClass('width_100');
			});
		}

		$.toggleDisabledAllComponent = function(isDisabled) {
			$('#wrap').find('input, textarea, select').each(function() { $(this).prop('disabled', isDisabled); });
		}

		$.fn.makeMonthCombo = function() {
			var $this = $(this);
			$.each(monthArray, function() {
				$this.append('<option value="' + this.code + '">' + this.name + '</option>');
			});
		}

		$.requalAddLine = function(isBindTreeEvent) {
			$('#requalRowTemplate').tmpl().appendTo($('#requalTable'));

			$('[name=idedpd]', '#requalTable').last().makeMonthCombo();

			$('[name=iddes]', '#requalTable').last().AutoHeightResizeTextarea();

			$('.btn_tree_del', '#requalTable').last().bindButtonTreeDataDelete();
			$('.btn_tree_del', '#requalTable').eq(-2).bindButtonTreeDataDelete();

			if(isBindTreeEvent) {
				$('.btn_tree_quli', '#requalTable').last().bindButtonTreeOpen($('#treeQuliDiv'));
				$('.btn_tree_edu', '#requalTable').last().bindButtonTreeOpen($('#treeEduDiv'));
			}
		}

		$.hpqualAddLine = function(isBindTreeEvent) {
			$('#hpqualRowTemplate').tmpl().appendTo($('#hpqualTable'));

			$('[name=idedpd]', '#hpqualTable').last().makeMonthCombo();

			$('[name=idobj]', '#hpqualTable').last().makeComboJob();
			$('[name=idobj]', '#hpqualTable').last().bindChangeStellCombo();

			$('[name=iddes]', '#hpqualTable').last().AutoHeightResizeTextarea();

			$('.btn_tree_del', '#hpqualTable').last().bindButtonTreeDataDelete();

			if(isBindTreeEvent) {
				$('.btn_tree_edu', '#hpqualTable').last().bindButtonTreeOpen($('#treeEduDiv'));
			}
		}

		$.bindButtonRequalAddRow = function() {
			$('#btnAddRequal').click(function(e) {
				e.preventDefault();

				$.requalAddLine(true);
			});
		}

		$.bindButtonHpqualAddRow = function() {
			$('#btnAddHpqual').click(function(e) {
				e.preventDefault();

				$.hpqualAddLine(true);
			});
		}

		$.bindButtonRequalDelRow = function() {
			$('#btnDelRequal').click(function(e) {
				e.preventDefault();

				$('#requalTable > tbody > tr').each(function(index) {
					if($(this).find('[name=delCheck]').is(':checked')) $(this).remove();
				});
			});
		}

		$.bindButtonHpqualDelRow = function() {
			$('#btnDelHpqual').click(function(e) {
				e.preventDefault();

				$('#hpqualTable > tbody > tr').each(function(index) {
					if($(this).find('[name=delCheck]').is(':checked')) $(this).remove();
				});
			});
		}

		$.bindButtonBackHandler = function() {
			$('#btnBack').click(function() {
				$.callProgress();
				$.CallListPage({
					SUBMIT_ACTION : "<c:url value='/portal/evaluation/idp/idpList.do'/>",
					PARAM_ACTION : "IDP_SETTING",
					PARAM_PAGE_NUM : "${params.currentPage}"
				});
			});
		}

		$.bindButtonSaveHandler = function() {
			$('#btnSave').click(function(e) {
				e.preventDefault();

				$.beforSubmitRenameForModelAttribute();

				if(confirm("저장하시겠습니까?")) {
					$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false, afterSubmitFunc : function() { $.afterSubmitRenameForView(); }});
				} else {
					$.afterSubmitRenameForView();
				}
			});
		}

		$.bindButtonSaveForPerformanceResultHandler = function() {
			$('#btnSave').click(function(e) {
				e.preventDefault();

				$.beforSubmitRenameForModelAttribute();
				$.toggleDisabledAllComponent(false);

				if(confirm("저장하시겠습니까?")) {
					$.businessProcess({button : 'SAVE', sucessMsg : '저장하였습니다.', isCallList : false, afterSubmitFunc : function() { $.afterSubmitPerformanceResultRenameForView(); }});
				} else {
					$.afterSubmitPerformanceResultRenameForView();
				}
			});
		}

		$.bindButtonCompleteHandler = function() {
			$('#btnComplete').click(function(e) {
				e.preventDefault();

				if($('[name=idcgl]').val() == '') {
					alert('경력목표를 작성하십시오.');
					return false;
				}

				if(!$.isSelectedFirstHopeItem()) {
					alert("희망이동 1순위(직무/부서)는 반드시 작성하셔야 합니다.");
					return false;
				}

				if(!$.isSelectedHopeItemPair()) {
					alert("희망이동 (직무/부서)는 반드시 같이 작성하셔야 합니다.");
					return false;
				}

				if(!$.isHasRow('#requalTable')) {
					alert("필수개발역량을 작성하십시오.");
					return false;
				}

				if(!$.isHasRow('#hpqualTable')) {
					alert("희망직무지식을 작성하십시오.");
					return false;
				}

				if(!$.isMinimumTwoHasRow('#requalTable', 2)) {
					alert("필수개발역량을 최소 2개이상 작성하십시오.");
					return false;
				}

				if(!$.isMinimumTwoHasRow('#hpqualTable', 1)) {
					alert("희망직무지식을 최소 1개이상 작성하십시오.");
					return false;
				}

				if(!$.isAllWriteItem('#requalTable')) {
					alert("필수개발역량의 항목값을 모두 입력하십시오.");
					return false;
				}

				if(!$.isAllWriteItem('#hpqualTable')) {
					alert("희망직무지식의 항목값을 모두 입력하십시오.");
					return false;
				}

				$.beforSubmitRenameForModelAttribute();

				if(confirm("작성완료되어 합의진행됩니다.\n완료하시겠습니까?")) {
					$.businessProcess({button : 'COMP', sucessMsg : '완료하였습니다.', isCallList : true, afterSubmitFunc : function() { $.afterSubmitRenameForView(); }});
				} else {
					$.afterSubmitRenameForView();
				}
			});
		}

		$.bindButtonPrintHandler = function() {
			$('#btnPrint').click(function(e) {
				e.preventDefault();

				var $inputElem = $('<input type="hidden" />');
				var $printForm = $('#ajaxForm');

				$printForm.empty();
				$printForm.append($inputElem.clone().attr('name', 'AYEAR').val('${idplst.AYEAR}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGEH').val('${idplst.ORGEH}'));
				$printForm.append($inputElem.clone().attr('name', 'ORGTX').val('${idplst.ORGTX}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAE').val('${idplst.IDPAE}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAENM').val('${idplst.IDPAENM}'));
				$printForm.append($inputElem.clone().attr('name', 'PERSG').val('${idplst.PERSG}'));
				$printForm.append($inputElem.clone().attr('name', 'STELL').val('${idplst.STELL}'));
				$printForm.append($inputElem.clone().attr('name', 'STLTX').val('${idplst.STLTX}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK').val('${idplst.ZZJIK}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT').val('${idplst.COTXT}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR').val('${idplst.TRFGR}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPAR').val('${idplst.IDPAR}'));
				$printForm.append($inputElem.clone().attr('name', 'IDPARNM').val('${idplst.IDPARNM}'));
				$printForm.append($inputElem.clone().attr('name', 'ZZJIK1').val('${idplst.ZZJIK1}'));
				$printForm.append($inputElem.clone().attr('name', 'COTXT1').val('${idplst.COTXT1}'));
				$printForm.append($inputElem.clone().attr('name', 'TRFGR1').val('${idplst.TRFGR1}'));
				$printForm.append($inputElem.clone().attr('name', 'IDSTS').val('${idplst.IDSTS}'));
				$printForm.append($inputElem.clone().attr('name', 'IDSTSX').val('${idplst.IDSTSX}'));
				$printForm.append($inputElem.clone().attr('name', 'SRTKY').val('${idplst.SRTKY}'));
				$printForm.append($inputElem.clone().attr('name', 'MODIF').val('${idplst.MODIF}'));

				$printForm.attr("method", "post");
				$printForm.attr("action", "<c:url value='/portal/evaluation/idp/idpSettingViewPrint.do'/>");

				$.CallPrintPagePopup();

				$printForm.empty();
			});
		}

		$.fn.bindButtonTooltipHandler = function() {
			$(this).click(function(e) {
				e.preventDefault();
			})
			.hover(
				function() {
					$(this).next().show();
				}, function() {
					$(this).next().hide();
				}
			);
		}

		$.initView = function() {
			$.bindButtonBackHandler();
			$.bindButtonSaveHandler();
			$.bindButtonCompleteHandler();
			$.bindButtonPrintHandler();
			$.bindButtonRequalAddRow();
			$.bindButtonRequalDelRow();
			$.bindButtonHpqualAddRow();
			$.bindButtonHpqualDelRow();
			$('.btn_tooltip').each(function() { $(this).bindButtonTooltipHandler(); });

			$('select[name=idobj]').each(function() { $(this).bindChangeStellCombo(); });

			$('#requalTable').toggleLastColumn(false);
			$('#hpqualTable').toggleLastColumn(false);

			$('#requalTable').find('textarea:visible').each(function() { $(this).AutoHeightResizeTextarea(); });
			$('#hpqualTable').find('textarea:visible').each(function() { $(this).AutoHeightResizeTextarea(); });

			if($('#requalTable > tbody > tr').size() == 0) {
				$.requalAddLine(false);
				$.requalAddLine(false);
			}
			if($('#hpqualTable > tbody > tr').size() == 0) {
				$.hpqualAddLine(false);
			}

			<c:if test="${eModif eq 'X'}">
				$('.btn_tree_del').each(function() { $(this).bindButtonTreeDataDelete(); });

				$.hideTreeButton();
				$.fetchTreeData('DUTY');
				$.fetchTreeData('ORGANIZATION');
				$.fetchTreeData('QULITY');
				$.fetchTreeData('EDUCATION');
			</c:if>

			$.DisabledBackgroundStyleChange();
		};

		$.viewMode = function(status) {
			$.toggleDisabledAllComponent(true);

			if(status == '') {
				$('#btnSave').unbind().hide();
				$('#btnPrint').unbind().hide();

				$('#wrap').find('select').each(function() { $(this).SelectboxToInput(); });
			} else if(status == 'C') {
				$('#btnSave').unbind();
				$.bindButtonSaveForPerformanceResultHandler();

				$('#requalTable').toggleLastColumn(true);
				$('#hpqualTable').toggleLastColumn(true);
				$('[name=idedrt]').each(function() { $(this).AutoHeightResizeTextarea(); });

				$('[name=idedrt]').prop('disabled', false);

				$('#hpqualTable > tbody > tr').each(function() {
					var idobjVal = $(this).find('[name=idobj]').val();
					var idobjtVal = $(this).find('[name=idobjt]').val();
					$(this).find('[name=idobj]').remove();
					$(this).find('[name=idobjt]').remove();

					var $parentTD = $(this).find('td').eq(1);
					$parentTD.append('<input type="hidden" name="idobj" value="'+idobjVal+'" />');
					$parentTD.append('<input type="text" name="idobjt" class="width_100" value="'+idobjtVal+'" disabled />');
				});

				$('select[name=idedpd]').each(function() { $(this).SelectboxToInput(); });
			}

			$('[name=idedpd]').each(function() { $(this).css('text-align', 'center'); });

			$('#btnComplete').unbind().hide();
			$('#btnInfo').unbind().hide();
			$('#btnAddRequal').unbind().hide();
			$('#btnDelRequal').unbind().hide();
			$('#btnAddHpqual').unbind().hide();
			$('#btnDelHpqual').unbind().hide();
			$.removeButtonSearch();

			$.DisabledBackgroundStyleChange();
		};

		$.isSelectedFirstHopeItem = function() {
			var isSelected = true;
			if($('[name=hpjob1t]').val() == '' || $('[name=hporg1t]').val() == '') {
				isSelected = false;
			}

			return isSelected;
		}

		$.isSelectedHopeItemPair = function() {
			var isPair = true;
			if($('[name=hpjob2t]').val() != '' || $('[name=hporg2t]').val() != '') {
				if($('[name=hpjob2t]').val() == '' || $('[name=hporg2t]').val() == '') {
					isPair = false;
				}
			}

			if($('[name=hpjob3t]').val() != '' || $('[name=hporg3t]').val() != '') {
				if($('[name=hpjob3t]').val() == '' || $('[name=hporg3t]').val() == '') {
					isPair = false;
				}
			}

			return isPair;
		}

		$.isHasRow = function(tableId) {
			var isHasRow = true;
			if($(tableId + ' > tbody > tr').length == 0) isHasRow = false;

			return isHasRow;
		}

		$.isMinimumTwoHasRow = function(tableId, minRow) {
			var isMinHasRow = true;
			if($(tableId + ' > tbody > tr').length < minRow) isMinHasRow = false;

			return isMinHasRow;
		}

		$.isAllWriteItem = function(tableId) {
			var isAllWrite = true;
			$('[name=idobj]', tableId).each(function() { if($(this).val() == '') isAllWrite = false; });
			$('[name=iddes]', tableId).each(function() { if($(this).val() == '') isAllWrite = false; });
			$('[name=idedut]', tableId).each(function() {
				if($(this).val() == '' && $(this).parent().find('[name=idedux]').val() == '') {
					isAllWrite = false;
				}
			});
			$('[name=idedpd]', tableId).each(function() { if($(this).val() == '') isAllWrite = false; });

			return isAllWrite;
		}

		$.beforSubmitRenameForModelAttribute = function() {
			$('[name="idcgl"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].idcgl"); });
			$('[name="hpjob1"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob1"); });
			$('[name="hpjob1t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob1t").prop('disabled', false); });
			$('[name="hporg1"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg1"); });
			$('[name="hporg1t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg1t").prop('disabled', false); });
			$('[name="hpjob2"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob2"); });
			$('[name="hpjob2t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob2t").prop('disabled', false); });
			$('[name="hporg2"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg2"); });
			$('[name="hporg2t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg2t").prop('disabled', false); });
			$('[name="hpjob3"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob3"); });
			$('[name="hpjob3t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hpjob3t").prop('disabled', false); });
			$('[name="hporg3"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg3"); });
			$('[name="hporg3t"]').each(function(index) { $(this).attr("name", "jobs[" + index + "].hporg3t").prop('disabled', false); });

			$('[name="idpgb"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idpgb"); });
			$('[name="idoty"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idoty"); });
			$('[name="idobj"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idobj"); });
			$('[name="idobjt"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idobjt").prop('disabled', false); });
			$('[name="iddes"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].iddes"); });
			$('[name="idedu"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idedu"); });
			$('[name="idedut"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idedut").prop('disabled', false); });
			$('[name="idedux"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idedux"); });
			$('[name="idedpd"]', '#requalTable').each(function(index) {
				if($(this).val().indexOf('월') > -1) $(this).val($(this).val().split('월')[0]);
				$(this).attr("name", "requals[" + index + "].idedpd");
			});
			$('[name="idedrt"]', '#requalTable').each(function(index) { $(this).attr("name", "requals[" + index + "].idedrt"); });

			$('[name="idpgb"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idpgb"); });
			$('[name="idoty"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idoty"); });
			$('[name="idobj"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idobj"); });
			$('[name="idobjt"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idobjt"); });
			$('[name="iddes"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].iddes"); });
			$('[name="idedu"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idedu"); });
			$('[name="idedut"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idedut").prop('disabled', false); });
			$('[name="idedux"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idedux"); });
			$('[name="idedpd"]', '#hpqualTable').each(function(index) {
				if($(this).val().indexOf('월') > -1) $(this).val($(this).val().split('월')[0]);
				$(this).attr("name", "hpquals[" + index + "].idedpd");
			});
			$('[name="idedrt"]', '#hpqualTable').each(function(index) { $(this).attr("name", "hpquals[" + index + "].idedrt"); });
		}

		$.afterSubmitRenameForView = function() {
			$('[name^=jobs]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name^=requals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name^=hpquals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('input[name=idedpd]').each(function() { $(this).val($(this).val() + '월'); });
			$('[name="hpjob1t"]').prop('disabled', true);
			$('[name="hporg1t"]').prop('disabled', true);
			$('[name="hpjob2t"]').prop('disabled', true);
			$('[name="hporg2t"]').prop('disabled', true);
			$('[name="hpjob3t"]').prop('disabled', true);
			$('[name="hporg3t"]').prop('disabled', true);
			$('[name="idobjt"]', '#requalTable').prop('disabled', true);
			$('[name="idedut"]').prop('disabled', true);
		}

		$.afterSubmitPerformanceResultRenameForView = function() {
			$('[name^=jobs]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name^=requals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });
			$('[name^=hpquals]').each(function(index) { $(this).attr("name", $(this).attr("name").split(".")[1]); });

			$.toggleDisabledAllComponent(true);
			$('[name=idedrt]').prop('disabled', false);
		}

		$.makeFormData = function(button) {
			var formData = $('#idpForm').serializeArray();
			formData.push({name:'button', value:button});
			formData.push({name:'ayear', value:'${idplst.AYEAR}'});
			formData.push({name:'orgeh', value:'${idplst.ORGEH}'});
			formData.push({name:'orgtx', value:'${idplst.ORGTX}'});
			formData.push({name:'idpae', value:'${idplst.IDPAE}'});
			formData.push({name:'idpaenm', value:'${idplst.IDPAENM}'});
			formData.push({name:'persg', value:'${idplst.PERSG}'});
			formData.push({name:'stell', value:'${idplst.STELL}'});
			formData.push({name:'stltx', value:'${idplst.STLTX}'});
			formData.push({name:'zzjik', value:'${idplst.ZZJIK}'});
			formData.push({name:'cotxt', value:'${idplst.COTXT}'});
			formData.push({name:'trfgr', value:'${idplst.TRFGR}'});
			formData.push({name:'idpar', value:'${idplst.IDPAR}'});
			formData.push({name:'idparnm', value:'${idplst.IDPARNM}'});
			formData.push({name:'zzjik1', value:'${idplst.ZZJIK1}'});
			formData.push({name:'cotxt1', value:'${idplst.COTXT1}'});
			formData.push({name:'trfgr1', value:'${idplst.TRFGR1}'});
			formData.push({name:'idsts', value:'${idplst.IDSTS}'});
			formData.push({name:'idstsx', value:'${idplst.IDSTSX}'});
			formData.push({name:'idstsdl', value:'${idplst.IDSTSDL}'});
			formData.push({name:'srtky', value:'${idplst.SRTKY}'});
			formData.push({name:'modif', value:'${idplst.MODIF}'});

			return formData;
		}

		$.businessProcess = function(params) {
			$.callProgress();

			var funcArg = (typeof params.afterSubmitFunc === 'function') ? params.afterSubmitFunc : function() {};

			$.ajax({
				url : "<c:url value='/portal/evaluation/idp/idpSettingProc.do'/>"
				, type : "post"
				, data : $.makeFormData(params.button)
				, success : function(result) {
					if(result == null || result == '') {
						alert("ERROR!");

						$.stopProgress();
					} else if(result.ES_RETURN.MSGTY == 'E') {
						alert(result.ES_RETURN.MSGTX);

						funcArg();
						$.stopProgress();
					} else {
						alert(params.sucessMsg);

						if(params.isCallList) {
							$.CallListPage({
								SUBMIT_ACTION : "<c:url value='/portal/evaluation/idp/idpList.do'/>",
								PARAM_ACTION : "IDP_SETTING",
								PARAM_PAGE_NUM : "${params.currentPage}"
							});
						} else {
							funcArg();
							$.stopProgress();
						}
					}
				}
				, error : function(xhr, exMessage) {
					AjaxError.showAlert(xhr, exMessage);

					funcArg();
					$.stopProgress();
				}
			});
		}

		$.initView();

		<c:if test="${eModif ne 'X'}">
		$.viewMode('${eModif}');
		</c:if>

	});
})(jQuery);
</script>

<script id="requalRowTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td class="text_center">
			<input type="checkbox" name="delCheck" />
		</td>
		<td valign="top">
			<input type="hidden" name="idpgb" value="1" />
			<input type="hidden" name="idoty" value="Q" />
			<input type="hidden" name="idobj" />
			<input type="text" name="idobjt" class="width_70" disabled /> <a class="btn_search btn_tree_quli">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
		</td>
		<td>
			<textarea name="iddes" class="width_100" rows="4"></textarea>
		</td>
		<td class="padding_0">
			<p class="idp_edu1">
				<span class="edu_label">교육명</span>
				<span class="edu_input">
					<input type="hidden" name="idedu" />
					<input type="text" name="idedut" class="width_80" disabled /> <a class="btn_search btn_tree_edu">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</span>
			</p>
			<p class="idp_edu2">
				<span class="edu_label">과정명</span> <span class="edu_input">
					<input type="text" name="idedux" class="width_100" />
				</span>
			</p>
		</td>
		<td colspan="2">
			<select name="idedpd" class="width_100">
			</select>
		</td>
		<td style="display:none;">
			<textarea name="idedrt" class="width_100" rows="4"></textarea>
		</td>
	</tr>
</script>

<script id="hpqualRowTemplate" type="text/x-jquery-tmpl">
	<tr>
		<td class="text_center">
			<input type="checkbox" name="delCheck" />
		</td>
		<td valign="top">
			<input type="hidden" name="idpgb" value="2" />
			<input type="hidden" name="idoty" value="C" />
			<input type="hidden" name="idobjt" />
			<select name="idobj" class="width_100"></select>
		</td>
		<td>
			<textarea name="iddes" class="width_100" rows="4"></textarea>
		</td>
		<td class="padding_0">
			<p class="idp_edu1">
				<span class="edu_label">교육명</span>
				<span class="edu_input">
					<input type="hidden" name="idedu" />
					<input type="text" name="idedut" class="width_80" disabled /> <a class="btn_search btn_tree_edu">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</span>
			</p>
			<p class="idp_edu2">
				<span class="edu_label">과정명</span> <span class="edu_input">
					<input type="text" name="idedux" class="width_100" />
				</span>
			</p>
		</td>
		<td colspan="2">
			<select name="idedpd" class="width_100">
			</select>
		</td>
		<td style="display:none;">
			<textarea name="idedrt" class="width_100" rows="4"></textarea>
		</td>
	</tr>
</script>

<div id="wrap">

	<h1 class="title">IDP (${params.AYEAR}년)</h1>

	<div class="btn_wrap left do_not_print" style="float: none;">
		<a class="btn_common" id="btnBack" title="뒤로" href="#"><span>뒤로</span></a>
		<a class="btn_common" id="btnSave" title="저장" href="#"><span>저장</span></a>
		<a class="btn_common" id="btnComplete" title="완료" href="#"><span>완료</span></a>
		<a class="btn_common" id="btnPrint" title="출력" href=""><span>출력</span></a>
	</div>

	<form id="idpForm" name="idpForm" method="post" action="<c:url value='/portal/evaluation/idp/idpProc.do'/>">
	<h2 class="title margintop_20">
		경력목표 및 희망직무
		<span class="add_tooltip">
			<a class="btn_tooltip" href="#">
				<span>manual</span>
			</a>
			<div class="tooltip_box" style="display:none;">
				<dl>
					<dt>
						경력목표 작성안내
					</dt>
					<dd>
						<ul>
							<li>경력목표는 경력개발경로의 최종부서로 작성</li>
							<li>인재개발 &gt; 표준경력경로 참조</li>
						</ul>
					</dd>
				</dl>
				<dl>
					<dt>
						희망이동직무 작성안내
					</dt>
					<dd>
						<ul>
							<li>1순위는 필수 값으로 지정</li>
							<li>직무이동을 원하는 직무와 부서를 함께 입력</li>
							<li>직무이동을 원하지 않는 경우 현재와 동일 직무 입력 가능</li>
							<li>인재개발 &gt; 부서 직무현황 참조하여 부서별 직무정보 확인</li>
						</ul>
					</dd>
				</dl>
			</div>
		</span>
	</h2>
	<table class="contents_table" id="crjobTable">
		<colgroup>
		    <col style="width:10%;">
		    <col style="width:10%;">
		    <col style="width:40%;">
		    <col style="width:40%;">
		</colgroup>
		<tbody>
			<tr>
				<th>경력목표</th>
				<td colspan="3"><input type="text" name="idcgl" class="width_100" value="${crjob.IDCGL}" /></td>
			</tr>
			<tr>
				<th rowspan="3">희망이동<br />(직무/부서)
				</th>
				<th>1순위</th>
				<td>
					<input type="hidden" name="hpjob1" value="${crjob.HPJOB1}" />
					<input type="text" name="hpjob1t" class="width_60" value="${crjob.HPJOB1T}" disabled /> <a class="btn_search btn_tree_stell">검색</a>
				</td>
				<td>
					<input type="hidden" name="hporg1" value="${crjob.HPORG1}" />
					<input type="text" name="hporg1t" class="width_60" value="${crjob.HPORG1T}" disabled /> <a class="btn_search btn_tree_orgh">검색</a>
				</td>
			</tr>
			<tr>
				<th>2순위</th>
				<td>
					<input type="hidden" name="hpjob2" value="${crjob.HPJOB2}" />
					<input type="text" name="hpjob2t" class="width_60" value="${crjob.HPJOB2T}" disabled /> <a class="btn_search btn_tree_stell">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</td>
				<td>
					<input type="hidden" name="hporg2" value="${crjob.HPORG2}" />
					<input type="text" name="hporg2t" class="width_60" value="${crjob.HPORG2T}" disabled /> <a class="btn_search btn_tree_orgh">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</td>
			</tr>
			<tr>
				<th>3순위</th>
				<td>
					<input type="hidden" name="hpjob3" value="${crjob.HPJOB3}" />
					<input type="text" name="hpjob3t" class="width_60" value="${crjob.HPJOB3T}" disabled /> <a class="btn_search btn_tree_stell">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</td>
				<td>
					<input type="hidden" name="hporg3" value="${crjob.HPORG3}" />
					<input type="text" name="hporg3t" class="width_60" value="${crjob.HPORG3T}" disabled /> <a class="btn_search btn_tree_orgh">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
				</td>
			</tr>
		</tbody>
	</table>

	<h2 class="title margintop_20">
		자기개발계획
		<span class="add_tooltip">
			<a class="btn_tooltip" href="#">
				<span>manual</span>
			</a>
			<div class="tooltip_box" style="display:none;">
				<dl>
					<dt>
						필수개발역량 작성안내
					</dt>
					<dd>
						<ul>
							<li>지난 역량평가 결과를 참고하여 육성이 필요한 역량 2개 이상 입력</li>
							<li>역량육성 계획에 대해 간단히 기술</li>
							<li>러닝센터 교육과정의 경우 검색하여 입력하고 러닝센터 이외의 활동인 경우 도서, 세미나, 기타 교육과정 등 개발 과정에 대한 명칭 입력</li>
						</ul>
					</dd>
				</dl>
				<dl>
					<dt>
						희망직무지식 작성안내
					</dt>
					<dd>
						<ul>
							<li>본인 직무와 희망이동직무에서 개발이 필요한 지식에 대해 각 1개 이상씩 입력</li>
							<li>지식 개발 계획에 대해 간단히 기술</li>
							<li>러닝센터 교육과정의 경우 검색하여 입력하며, 러닝센터 이외의 활동인 경우 도서, 세미나, 기타 교육과정 등 개발과정에 대한 명칭 입력</li>
						</ul>
					</dd>
				</dl>
			</div>
		</span>
	</h2>

	<h3 class="title">
		1. 필수개발역량
		<a id="btnAddRequal" class="btn_add">추가</a>
		<a id="btnDelRequal" class="btn_delete">삭제</a>
	</h3>
	<table class="contents_table" id="requalTable">
		<colgroup>
		    <col style="width:2%;" />
		    <col style="width:18%;" />
		    <col style="" />
		    <col class="col_change" style="width:17%;" />
		    <col style="width:5%;" />
		    <col class="col_last" style="width:18%;" />
		</colgroup>
		<thead>
			<tr>
				<th colspan="2">개발목표(역량)</th>
				<th>개발계획 및 활동내용</th>
				<th>교육명(과정명)</th>
				<th>기간</th>
				<th>수행결과</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty requalList}">
				<c:forEach var="result" items="${requalList}">
					<tr>
						<td class="text_center">
							<input type="checkbox" name="delCheck" />
						</td>
						<td valign="top">
							<input type="hidden" name="idpgb" value="1" />
							<input type="hidden" name="idoty" value="Q" />
							<input type="hidden" name="idobj" value="${result.IDOBJ}" />
							<input type="text" name="idobjt" class="width_70" value="${result.IDOBJT}" disabled /> <a class="btn_search btn_tree_quli">검색</a> <a class="btn_cancel btn_tree_del">삭제</a>
						</td>
						<td>
							<textarea name="iddes" class="width_100" rows="4"><c:out value="${result.IDDES}" /></textarea>
						</td>
						<td class="padding_0">
							<p class="idp_edu1">
								<span class="edu_label" style="width:25%;">교육명</span>
								<span class="edu_input">
									<input type="text" name="idedut" class="width_100" value="${result.IDEDUT}" disabled />
								</span>
							</p>
							<p class="idp_edu2">
								<span class="edu_label" style="width:25%;">과정명</span>
								<span class="edu_input">
									<input type="text" name="idedux" class="width_100 margintop_10" value="${result.IDEDUX}" disabled />
								</span>
							</p>
						</td>
						<td valign="top">
							<fmt:parseNumber var="idedpd_num" value="${result.IDEDPD}"/>
							<select name="idedpd" class="width_100">
								<option value="">선택</option>
								<c:forEach var="i" begin="1" end="12" step="1" >
									<option value="<fmt:formatNumber value="${i}" pattern='00'/>" <c:if test="${i == idedpd_num}">selected</c:if>>${i}월</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<textarea name="idedrt" class="width_100" rows="4"><c:out value="${result.IDEDRT}" /></textarea>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	<h3 class="title margintop_20">
		2. 희망직무지식
		<a id="btnAddHpqual" class="btn_add">추가</a>
		<a id="btnDelHpqual" class="btn_delete">삭제</a>
	</h3>
	<table class="contents_table" id="hpqualTable">
		<colgroup>
		    <col style="width:2%;" />
		    <col style="width:18%;" />
		    <col style="" />
		    <col class="col_change" style="width:17%;" />
		    <col style="width:5%;" />
		    <col class="col_last" style="width:18%;" />
		</colgroup>
		<thead>
			<tr>
				<th colspan="2">개발목표(직무)</th>
				<th>개발계획 및 활동내용</th>
				<th>교육명(과정명)</th>
				<th>기간</th>
				<th>수행결과</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${not empty hpqualList}">
				<c:forEach var="result" items="${hpqualList}">
					<tr>
						<td class="text_center">
							<input type="checkbox" name="delCheck" />
						</td>
						<td valign="top">
							<input type="hidden" name="idpgb" value="2" />
							<input type="hidden" name="idoty" value="C" />
							<input type="hidden" name="idobjt" value="${result.IDOBJT}" />
							<select name="idobj" class="width_100">
								<option value="">선택</option>
								<c:if test="${idplst.STELL ne '00000000'}">
									<option value="${idplst.STELL}" <c:if test="${result.IDOBJ eq idplst.STELL}">selected</c:if>>${idplst.STLTX}</option>
								</c:if>
								<c:if test="${crjob.HPJOB1 ne '00000000'}">
									<option value="${crjob.HPJOB1}" <c:if test="${result.IDOBJ eq crjob.HPJOB1}">selected</c:if>>${crjob.HPJOB1T}</option>
								</c:if>
								<c:if test="${crjob.HPJOB2 ne '00000000'}">
									<option value="${crjob.HPJOB2}" <c:if test="${result.IDOBJ eq crjob.HPJOB2}">selected</c:if>>${crjob.HPJOB2T}</option>
								</c:if>
								<c:if test="${crjob.HPJOB3 ne '00000000'}">
									<option value="${crjob.HPJOB3}" <c:if test="${result.IDOBJ eq crjob.HPJOB3}">selected</c:if>>${crjob.HPJOB3T}</option>
								</c:if>
							</select>
						</td>
						<td>
							<textarea name="iddes" class="width_100" rows="4"><c:out value="${result.IDDES}" /></textarea>
						</td>
						<td class="padding_0">
							<p class="idp_edu1">
								<span class="edu_label" style="width:25%;">교육명</span>
								<span class="edu_input">
									<input type="text" name="idedut" class="width_100" value="${result.IDEDUT}" disabled />
								</span>
							</p>
							<p class="idp_edu2">
								<span class="edu_label" style="width:25%;">과정명</span>
								<span class="edu_input">
									<input type="text" name="idedux" class="width_100 margintop_10" value="${result.IDEDUX}" disabled />
								</span>
							</p>
						</td>
						<td valign="top">
							<fmt:parseNumber var="idedpd_num" value="${result.IDEDPD}" />
							<select name="idedpd" class="width_100">
								<option value="">선택</option>
								<c:forEach var="i" begin="1" end="12" step="1" >
									<option value="<fmt:formatNumber value='${i}' pattern='00'/>" <c:if test="${i == idedpd_num}">selected</c:if>>${i}월</option>
								</c:forEach>
							</select>
						</td>
						<td>
							<textarea name="idedrt" class="width_100" rows="4"><c:out value="${result.IDEDRT}" /></textarea>
						</td>
					</tr>
				</c:forEach>
			</c:if>
		</tbody>
	</table>

	</form>

	<form id="ajaxForm" name="ajaxForm" method="post"></form>

</div>

<div class="mask"></div>

<div class="search_popup" id="treeStellDiv">
	<div class="popup_header">
		<span class="title" id="layer_title_txt">직무 선택</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div id="stellTreeParent" class="popup_contents">
		<div class="treeheader"><b>직무</b></div>
		<div id="stellSidetreecontrol"></div>
		<ul class="treeview" id="stellTree"></ul>
	</div>
</div>

<div class="search_popup" id="treeOrghDiv">
	<div class="popup_header">
		<span class="title" id="layer_title_txt">부서 선택</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div id="orghTreeParent" class="popup_contents">
		<div class="treeheader"><b>부서</b></div>
		<div id="orghSidetreecontrol"></div>
		<ul class="treeview" id="orghTree"></ul>
	</div>
</div>

<div class="search_popup" id="treeQuliDiv">
	<div class="popup_header">
		<span class="title" id="layer_title_txt">역량 선택</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div id="quliTreeParent" class="popup_contents">
		<div class="treeheader"><b>역량</b></div>
		<div id="quliSidetreecontrol"></div>
		<ul class="treeview" id="quliTree"></ul>
	</div>
</div>

<div class="search_popup" id="treeEduDiv">
	<div class="popup_header">
		<span class="title" id="layer_title_txt">교육과정 선택</span>
		<a href="#" class="btn_close">닫기</a>
	</div>
	<div id="eduTreeParent" class="popup_contents">
		<div class="treeheader"><b>교육과정</b></div>
		<div id="eduSidetreecontrol"></div>
		<ul class="treeview" id="eduTree"></ul>
	</div>
</div>

<script type="text/javascript">
try{
	document.domain = 'moorim.co.kr';
}catch(err){
	var console = window.console || {log:function(){}};
	console.log(err);
}
</script>