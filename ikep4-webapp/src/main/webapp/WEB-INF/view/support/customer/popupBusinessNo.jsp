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
		
		fnCaller = function (params, dialog) { // dialog Controll
			
			$("a.saveButton").click(function(){
				parent.mainFrame.contentIframe.location.href="<c:url value='/support/customer/customerBasicInfo/modifyBasicInfo.do'/>";
				dialog.close();
			});

			
			$("a.closeButton").click(function() {
				dialog.close();
			});
			
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
		}

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

		}

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

		

							//search 동작
							$("#searchBoardItemButton")
									.click(
											function() {
												$(
														"#searchBoardItemForm input[name=actionType]")
														.val("search");
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

				

					

						});
		
		
						
		
	})(jQuery);
//-->
</script>

<form id="searchBoardItemForm" method="post" action="<c:url value='/support/customer/customerBasicInfo/popupBusinessNo.do'/>">
  	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>고객 찾기</h2>
		<div class="listInfo">
			<spring:bind path="searchCondition.pagePerRecord">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${boardCode.pageNumList}">
						<option value="${code.key}"
							<c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach>
				</select>
			</spring:bind>
			
			<div class="totalNum">
				<ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<span> ${searchResult.recordCount}</span>
			</div>
		</div>		
		<div class="tableSearch">
			<spring:bind path="searchCondition.searchColumn">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<option value="name"
						<c:if test="${'name' eq status.value}">selected="selected"</c:if>>
						<%-- <ikep4j:message pre='${prefix}' key='search.name' /> --%> 고객명
					</option>
					<option value="regno"
						<c:if test="${'regno' eq status.value}">selected="selected"</c:if>>
						<%-- <ikep4j:message pre='${prefix}' key='search.businessEmployee' /> --%> 사업자등록번호
					</option>
				</select>
			</spring:bind>
			<spring:bind path="searchCondition.searchWord">
				<input name="${status.expression}" value="${status.value}"
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
    <table summary="고객검색">
        <caption></caption>
        <thead>
            <tr>            	
                <th scope="col" width="10%">번호</th>
                <th scope="col" width="20%">고객ID</th>
                <th scope="col" width="*%">고객명</th>
                <th scope="col" width="30%">사업자등록번호</th>
                <th scope="col" width="15%">대표이사</th>       
            </tr>
        </thead>
        <tbody>
        	 <c:choose>
				<c:when test="${searchResult.emptyRecord}">
					<tr>
						<td colspan="5"
							class="emptyRecord">등록된 고객정보가 없습니다.
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:forEach var="basicInfo" items="${searchResult.entity}" varStatus="status">
						<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
							<td>${searchCondition.recordCount
								-((searchCondition.pageIndex - 1) *
								searchCondition.pagePerRecord) - status.index}</td>							
							<td>${basicInfo.id}</td>
							<td  class="textLeft">${basicInfo.name}</td>
							<td>${basicInfo.regno}</td>
							<td>${basicInfo.director}</td>
						</tr>
					</c:forEach>
				</c:otherwise>
			</c:choose>	   
            	            																												
        </tbody>
    </table>
    <!--Page Numbur Start-->
	 <spring:bind path="searchCondition.pageIndex">
		<ikep4j:pagination searchButtonId="searchBoardItemButton"
			pageIndexInput="${status.expression}"
			searchCondition="${searchCondition}" />
		<input name="${status.expression}" type="hidden"
			value="${status.value}"
			title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
	</spring:bind>
	<div class="blockBlank_10px"></div>
	</div>
	
	</form>
    
     <!--directive Start-->
    <div class="directive"> 
        <ul>
            <li><span> 기존에 등록된 고객은 등록하실 수 없습니다. 등록하고자 하는 고객이 있는지 확인하세요.<br/>
					   새로 등록하시겠습니까?</span></li>												
        </ul>
    </div>
    <div class="mb10"></div>
    <!--//directive End-->
    
    
    <!--blockButton Start-->
    <div class="blockButton"> 
        <ul>
            <li><a class="button saveButton" href="#a"><span>등록</span></a></li>
            <li><a class="button closeButton" href="#a"><span>취소</span></a></li>
        </ul>
    </div>
    <!--//blockButton End-->
    <div class="blockBlank_5px"></div>	
	<!--//popup_contents End-->
