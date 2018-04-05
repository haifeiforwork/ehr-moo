<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />


<script type="text/javascript">
<!--
	var bbsIframe; // 부모 iframe
	var bbsIsIframe = 0; // iframe 존재 여부
	var isLayout; // 레이아웃 보기 여부
	var bbsLayout = null;
	var layoutType = "n"; // n:없음, v:가로보기, h:세로보기
	var fnCaller;
	function mouseOver(obj){
		
		//obj.className = "bgSelected";
		//obj.style.backgroundColor="#edf2f5";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).attr("style","background-color:#edf2f5;");
		});	
	}
	function mouseOut(obj){
		//obj.className = "";
		//obj.style.backgroundColor="";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).attr("style","background-color:none;");
		});	
	}

	(function($) {
		
		sendInfo = function (tmpSapId) {
			var result = {
					sapId : $("#sapId_"+tmpSapId).val(),
					partnerName : $("#partnerName_"+tmpSapId).val(),
					category : $("#category_"+tmpSapId).val(),
					sector : $("#sector_"+tmpSapId).val(),
					zipNo : $("#zipNo_"+tmpSapId).val(),
					address : $("#address_"+tmpSapId).val(),
					mainPhone : $("#mainPhone_"+tmpSapId).val(),
					email : $("#email_"+tmpSapId).val(),
					ceoName : $("#ceoName_"+tmpSapId).val(),
					businessNo : $("#businessNo_"+tmpSapId).val(),
					corporationNo : $("#corporationNo_"+tmpSapId).val(),
					keyMan : $("#keyMan_"+tmpSapId).val(),
					contacts : $("#contacts_"+tmpSapId).val(),
					fax : $("#fax_"+tmpSapId).val()
			};
			opener.sapInfoReceive(result);
			window.close();
		};
		
		
		
		/* window risize 시 Contaner 높이 조절 */
		resizeContanerHeight = function() {
			var docHeight = 0;
			var adjustHeight = 20;
			var $lefMenu;
			var $Container = $('#container');

			if (isLayout) {
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					docHeight = $(parent).height();

					$lefMenu = $("#leftMenu", parent.document);
					var leftMenuPostion = $lefMenu.offset();
					if ($lefMenu.length > 0) {
						$lefMenu.height(docHeight - leftMenuPostion.top);
					}
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height(docHeight - $Container.offset().top
							- adjustHeight);
					// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
					bbsIframe.height(docHeight - 17);
				} else {
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height($(window).height()
							- $Container.offset().top - adjustHeight);
				}
			}

			if (bbsLayout != null) {
				bbsLayout.resizeAll();
			}
		};

		/* Contaner & iframe 높이 조절 */
		setContanerHeight = function() {
			var docHeight = 0;
			var adjustHeight = 20;
			var $lefMenu;
			var $Container = $('#container');

			// layout 존재하므로 layout destroy 함수 호출시
			if (isLayout) {
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					docHeight = $(parent).height();
					$lefMenu = $("#leftMenu", parent.document);
					var leftMenuPostion = $lefMenu.offset();
					if ($lefMenu.length > 0) {
						$lefMenu.height(docHeight - leftMenuPostion.top).css({
							overflowY : "auto",
							overflowX : "hidden"
						});
					}

					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height(docHeight - $Container.offset().top
							- adjustHeight);
					// 부모 iframe 높이 조절 - 부모 윈도우 높이로 조절
					bbsIframe.height(docHeight - 19);
				} else {
					// Contaniner 높이 조절 (부모 window 높이 - Contanier 시작 높이)
					$Container.height($(window).height()
							- $Container.offset().top - adjustHeight);
				}

			} else { // layout 없으므로 layout 함수 호출시
				// iframe 안에서 호출된 경우
				if (bbsIsIframe > 0) {
					$lefMenu = $("#leftMenu", parent.document);
					$lefMenu.css({
						overflowY : "",
						overflowX : ""
					});
					bbsIframe.height($(document).height());
				}

			}

			if (bbsLayout != null) {
				bbsLayout.resizeAll();
			}

		};

		$(window).bind("unload", function() {
			bbsIframe = null;
			bbsIsIframe = null;
			isLayout = null;
			bbsLayout = null;
			layoutType = null;

			contextRoot = null;
			ZeroClipboard = null;
			cafeCkeditorConfig = null;
			fullCkeditorConfig = null;

			iKEP = null;
			iKEPLang = null;

			simpleCkeditorConfig = null;
			$jq = null;
			jQuery = null;
		});

		$(document)
				.ready(
						function() {
							<c:if test="${board.rss eq 1}">initRssClipBoard();
							</c:if>

							if (window.parent.menuMark != null) {
								window.parent.menuMark("${board.boardId}");
							}

		
							$('#searchWord').keypress( function(event) {
						    	if(event.keyCode == '13'){
						    		$("#searchBoardItemButton").trigger("click");
						    		return false;
						    	}
							});

							//search 동작
							$("#searchBoardItemButton")
									.click(
											function() {
												$(
														"#searchBoardItemForm input[name=actionType]")
														.val("search");
												
												$("#name").val($("#searchWord").val());
												$("#searchBoardItemForm")
														.submit();
												return false;
											});

							//페이지 할 row수 설정
							$("#searchBoardItemForm select[name=pagePerRecord]")
									.change(
											function() {
												$(
														"#searchBoardItemForm input[name=actionType]")
														.val("pagePerRecord");
												$("#searchBoardItemForm")
														.submit();
												return false;
											});

				
							$("#closeButton").click(function() {
								window.close();
							});

					

						});
		
		
						
		
	})(jQuery);
//-->
</script>




<form id="searchBoardItemForm" method="post" action="<c:url value='/support/partner/partnerQualityClaimSell/popupSAPSync.do'/>">
<input type="hidden" name="name" id="name" value="">
  	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>거래처 찾기</h2>
		<div class="tableSearch">
			<spring:bind path="searchCondition.searchColumn">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="partnerName"
						<c:if test="${'partnerName' eq status.value}">selected="selected"</c:if>>
						<%-- <ikep4j:message pre='${prefix}' key='search.name' /> --%> 거래처명
					</option>
				</select>
			</spring:bind>
			<spring:bind path="searchCondition.searchWord">
				<input name="${status.expression}" id="${status.expression}" value="${status.value}"
					type="text" class="inputbox"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'
					size="20" />
			</spring:bind>
			<a class="ic_search" id="searchBoardItemButton"  href="#a"><span>검색</span></a>
		</div>	
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
    
    <div class="blockListTable">
    <table>
        <caption></caption>
        <thead>
            <tr>            	
                <th scope="col" width="20%">SAP ID</th>
                <th scope="col" width="*%">거래처명</th>
                <th scope="col" width="30%">사업자등록번호</th>
                <th scope="col" width="15%">대표이사</th>       
            </tr>
        </thead>
        <tbody>
        	 <%-- <c:choose>
				<c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="4"
							class="emptyRecord">거래처정보가 없습니다.
						</td>
					</tr>
				</c:when>
				<c:otherwise> --%>
					<c:forEach var="basicInfo" items="${vendorList}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${basicInfo.sapId}</td>
							<td  class="textLeft"><a href="javascript:sendInfo('${basicInfo.sapId}');">${basicInfo.partnerName}</a></td>
							<td>${basicInfo.businessNo}</td>
							<td>${basicInfo.ceoName}</td>
							<input type="hidden" id="sapId_${basicInfo.sapId}" name="sapId_${basicInfo.sapId}" value="${basicInfo.sapId}" />
							<input type="hidden" id="partnerName_${basicInfo.sapId}" name="partnerName_${basicInfo.sapId}" value="${basicInfo.partnerName}" />
							<input type="hidden" id="category_${basicInfo.sapId}" name="category_${basicInfo.sapId}" value="${basicInfo.category}" />
							<input type="hidden" id="sector_${basicInfo.sapId}" name="sector_${basicInfo.sapId}" value="${basicInfo.sector}" />
							<input type="hidden" id="zipNo_${basicInfo.sapId}" name="zipNo_${basicInfo.sapId}" value="${basicInfo.zipNo}" />
							<input type="hidden" id="address_${basicInfo.sapId}" name="address_${basicInfo.sapId}" value="${basicInfo.address}" />
							<input type="hidden" id="mainPhone_${basicInfo.sapId}" name="mainPhone_${basicInfo.sapId}" value="${basicInfo.mainPhone}" />
							<input type="hidden" id="email_${basicInfo.sapId}" name="email_${basicInfo.sapId}" value="${basicInfo.email}" />
							<input type="hidden" id="ceoName_${basicInfo.sapId}" name="ceoName_${basicInfo.sapId}" value="${basicInfo.ceoName}" />
							<input type="hidden" id="businessNo_${basicInfo.sapId}" name="businessNo_${basicInfo.sapId}" value="${basicInfo.businessNo}" />
							<input type="hidden" id="corporationNo_${basicInfo.sapId}" name="corporationNo_${basicInfo.sapId}" value="${basicInfo.corporationNo}" />
							<input type="hidden" id="keyMan_${basicInfo.sapId}" name="keyMan_${basicInfo.sapId}" value="${basicInfo.keyMan}" />
							<input type="hidden" id="contacts_${basicInfo.sapId}" name="contacts_${basicInfo.sapId}" value="${basicInfo.contacts}" />
							<input type="hidden" id="fax_${basicInfo.sapId}" name="fax_${basicInfo.sapId}" value="${basicInfo.fax}" />
						</tr>
					</c:forEach>
				<%-- </c:otherwise>
			</c:choose>	    --%>
				<c:forEach var="basicInfo" items="${basicInfoList}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${basicInfo.sapId}</td>
							<td  class="textLeft"><a href="javascript:sendInfo('${basicInfo.id}');">${basicInfo.partnerName}</a></td>
							<td>${basicInfo.businessNo}</td>
							<td>${basicInfo.ceoName}</td>
							<input type="hidden" id="sapId_${basicInfo.id}" name="sapId_${basicInfo.id}" value="${basicInfo.sapId}" />
							<input type="hidden" id="partnerName_${basicInfo.id}" name="partnerName_${basicInfo.id}" value="${basicInfo.partnerName}" />
							<input type="hidden" id="category_${basicInfo.id}" name="category_${basicInfo.id}" value="${basicInfo.category}" />
							<input type="hidden" id="sector_${basicInfo.id}" name="sector_${basicInfo.id}" value="${basicInfo.sector}" />
							<input type="hidden" id="zipNo_${basicInfo.id}" name="zipNo_${basicInfo.id}" value="${basicInfo.zipNo}" />
							<input type="hidden" id="address_${basicInfo.id}" name="address_${basicInfo.id}" value="${basicInfo.address}" />
							<input type="hidden" id="mainPhone_${basicInfo.id}" name="mainPhone_${basicInfo.id}" value="${basicInfo.mainPhone}" />
							<input type="hidden" id="email_${basicInfo.id}" name="email_${basicInfo.id}" value="${basicInfo.email}" />
							<input type="hidden" id="ceoName_${basicInfo.id}" name="ceoName_${basicInfo.id}" value="${basicInfo.ceoName}" />
							<input type="hidden" id="businessNo_${basicInfo.id}" name="businessNo_${basicInfo.id}" value="${basicInfo.businessNo}" />
							<input type="hidden" id="corporationNo_${basicInfo.id}" name="corporationNo_${basicInfo.id}" value="${basicInfo.corporationNo}" />
							<input type="hidden" id="keyMan_${basicInfo.id}" name="keyMan_${basicInfo.id}" value="${basicInfo.keyMan}" />
							<input type="hidden" id="contacts_${basicInfo.id}" name="contacts_${basicInfo.id}" value="${basicInfo.contacts}" />
							<input type="hidden" id="fax_${basicInfo.id}" name="fax_${basicInfo.id}" value="${basicInfo.fax}" />
						</tr>
					</c:forEach>
            	            																												
        </tbody>
    </table>
	<div class="blockBlank_10px"></div>
	</div>
	
	</form>
    
    <div class="mb10"></div>
    <!--//directive End-->

    
    <!--blockButton Start-->
    <div class="blockButton"> 
        <ul>           
            <li><a class="button closeButton" id="closeButton" href="#a"><span>취소</span></a></li>
        </ul>
    </div>
    <!--//blockButton End-->
    <div class="blockBlank_5px"></div>	
	<!--//popup_contents End-->
