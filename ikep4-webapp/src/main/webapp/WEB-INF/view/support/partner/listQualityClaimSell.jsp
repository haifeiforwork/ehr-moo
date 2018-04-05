<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<c:set var="prefix" value="ui.support.partner.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.partner.manInfoItemList" />

<script type="text/javascript">
<!--
	var bbsIframe; // 부모 iframe
	var bbsIsIframe = 0; // iframe 존재 여부
	var isLayout; // 레이아웃 보기 여부
	var bbsLayout = null;
	var layoutType = "n"; // n:없음, v:가로보기, h:세로보기

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
						
							if (window.parent.menuMark != null) {
								window.parent.menuMark("${board.boardId}");
							}

							sort = function(sortColumn, sortType) {
								$("#searchBoardItemForm input[name=actionType]")
										.val("sort");
								$("#searchBoardItemForm input[name=sortColumn]")
										.val(sortColumn);
								$("#searchBoardItemForm input[name=sortType]")
										.val(sortType == 'ASC' ? 'DESC' : 'ASC');
								$("#searchBoardItemButton").click();
							};

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

							/* 기본 layout 설정 여부 */
							isLayout = false;
							layoutType = "n";

							/* 윈도우 resize 이벤트 */
							$(window).bind("resize", resizeContanerHeight);

							$("a.boardItem")
									.click(
											function() {
												$("*.boardItemLine")
														.removeClass(
																"bgSelected");
												$(this).parents(
														"*.boardItemLine")
														.addClass("bgSelected");
												if (!isLayout) {
													<c:choose>
													<c:when test="${popupYn}">
													iKEP
															.popupOpen(
																	$(this)
																			.attr(
																					"href")
																			+ "&popupYn=true&searchConditionString=${searchConditionString}&popupYn=${popupYn}",
																	{
																		width : 1000,
																		height : 600,
																		resizable : false,
																		callback : function(
																				result) {
																			location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do'/>?boardId="
																					+ result[0]
																					+ "&searchConditionString="
																					+ result[1]
																					+ "&popupYn=${popupYn}";
																		}
																	}, "param");

													return false;
													</c:when>
													<c:otherwise>
													return true;
													</c:otherwise>
													</c:choose>

												} else if (layoutType == "v") {
													$("#layoutContent")
															.html("");
													$("#layoutContent")
															.load(
																	$(this)
																			.attr(
																					"href")
																			+ "&layoutType=layoutVertical");
													return false;

												} else if (layoutType == "h") {
													$("#layoutContent")
															.html("");
													$("#layoutContent")
															.load(
																	$(this)
																			.attr(
																					"href")
																			+ "&layoutType=layoutHorizental");
													return false;
												} else {
													return true;
												}

												return false;
											});

							$("a.boardItem").each(function() {
								$(this).attr("title", $(this).html());
							});

							$("*.boardItem", "span.deletedItem").css("color",
									"red");

							$("#excelFormButton").click(function() {
								$("#excelDiv").show();
							});
							
							$("#excelCancelButton").click(function() {
								$("#excelDiv").hide();
							});
							
							$("#excelDownloadButton").click(function() {
								$jq("#searchBoardItemForm").attr("action","<c:url value='/support/partner/partnerQualityClaimSell/excelQualityClaimSellList.do'/>");
								$jq("#searchBoardItemForm").submit();
								$("#excelDiv").hide();
							});
						});
	})(jQuery);
//-->
</script>

<h1 class="none">컨텐츠영역</h1>
<form id="searchBoardItemForm"	method="post" action="<c:url value='/support/partner/partnerQualityClaimSell/qualityClaimSellList.do'/>" >					
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.actionType">
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>Contact Report</h2>
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
						<!-- div class="totalNum">${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)</div-->
						<div class="totalNum">
							<ikep4j:message pre='${preSearch}' key='pageCount.info' />
							<span> ${searchResult.recordCount}</span>
						</div>
					</div>		
					<div class="tableSearch">
					    구분:
					    <spring:bind path="searchCondition.searchPurpose">
						<select name="${status.expression}">
							<option value="" <c:if test="${'' eq status.value}">selected="selected"</c:if>>전체</option>
							<c:if test="${crRole1}">
							<option value="CR01" <c:if test="${'CR01' eq status.value}">selected="selected"</c:if>>주원료</option>
							</c:if>
							<c:if test="${crRole2}">
							<option value="CR02" <c:if test="${'CR02' eq status.value}">selected="selected"</c:if>>부원료</option>
							</c:if>
							<c:if test="${crRole3}">
							<option value="CR03" <c:if test="${'CR03' eq status.value}">selected="selected"</c:if>>기자재</option>
							</c:if>
							<c:if test="${crRole4}">
							<option value="CR04" <c:if test="${'CR04' eq status.value}">selected="selected"</c:if>>포장재</option>
							</c:if>
							<c:if test="${crRole5}">
							<option value="CR05" <c:if test="${'CR05' eq status.value}">selected="selected"</c:if>>펄프판매</option>
							</c:if>
							<c:if test="${crRole7}">
							<option value="CR07" <c:if test="${'CR07' eq status.value}">selected="selected"</c:if>>펠릿</option>
							</c:if>
							<c:if test="${crRole6}">
							<option value="CR06" <c:if test="${'CR06' eq status.value}">selected="selected"</c:if>>기타</option>
							</c:if>
						</select>
						</spring:bind>
						<spring:bind path="searchCondition.searchColumn">
							<select name="${status.expression}"
								title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<option value=""
									<c:if test="${'' eq status.value}">selected="selected"</c:if>>
									전체
								</option>
								<option value="partnerName"
									<c:if test="${'partnerName' eq status.value}">selected="selected"</c:if>>
									거래처명
								</option>
								<option value="counselor"
									<c:if test="${'counselor' eq status.value}">selected="selected"</c:if>>
									상담자
								</option>
								<option value="items"
									<c:if test="${'items' eq status.value}">selected="selected"</c:if>>
									품목
								</option>
								<option value="counselContent"
									<c:if test="${'counselContent' eq status.value}">selected="selected"</c:if>>
									상담내용
								</option>
								<option value="counselDate"
									<c:if test="${'counselDate' eq status.value}">selected="selected"</c:if>>
									최종상담일
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
				
				<div class="tableDesciption" style="position:relative;">
					<table summary="게시판 설명">
					<caption></caption>
					<tbody>
						<tr>
						<td class="valign_top">
							<div class="floatLeft"><span class="ic_desc"></span></div>
						</td>
						<td>
						  Contact Report 관련 사항을 위한 게시판입니다.
						</td>
						</tr>
					</tbody>
					</table>
		      <!--blockButton Start-->
                     <div class="blockButton" style="position:absolute; right:0; bottom:0px; padding-top:10px;"> 
                         <ul>
                             <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/createQualityClaimSellView.do'/>"><span>등록</span></a></li>
                             <li><a id='excelFormButton' class='button' href="#a"><span><img src="<c:url value='/base/images/icon/ico_bullet_excel.png'/>" /> 엑셀다운로드</span></a></li>
                             <div id="excelDiv" style="position:absolute;z-index:99;border:2px solid #5c5c5c;display: none;">
								<table style="background-color: white;width:150px;height:60px;text-align: center;vertical-align: middle;">
									<tr>
										<td>
											<spring:bind path="searchCondition.searchYear">
							                    <select title="${status.expression}" name="${status.expression}" id="${status.expression}"> >                               		
								                    <c:set var="compareYear" value="${searchCondition.searchYear}" />
								                    <c:if test="${empty compareYear}">
								                    	<c:set var="compareYear" value="${nowYear}" />
								                    </c:if>
											        <c:forEach var="year" begin="2015" end="${nowYear}" step="1">
											        	<option value="${year}" <c:if test="${year eq compareYear}">selected="selected"</c:if>>${year}</option>
											        </c:forEach>
										        </select>			                       	
					                        </spring:bind> 
					                        <spring:bind path="searchCondition.searchMonth">
							                    <select title="${status.expression}" name="${status.expression}" id="${status.expression}">                               		
								                    <c:set var="compareMonth" value="${searchCondition.searchMonth}" />
								                    <c:if test="${empty compareMonth}">
								                    	<c:set var="compareMonth" value="${nowMonth}" />
								                    </c:if>
											        <option value="01" <c:if test="${'01' eq compareMonth}">selected="selected"</c:if>>1</option>
											        <option value="02" <c:if test="${'02' eq compareMonth}">selected="selected"</c:if>>2</option>
											        <option value="03" <c:if test="${'03' eq compareMonth}">selected="selected"</c:if>>3</option>
											        <option value="04" <c:if test="${'04' eq compareMonth}">selected="selected"</c:if>>4</option>
											        <option value="05" <c:if test="${'05' eq compareMonth}">selected="selected"</c:if>>5</option>
											        <option value="06" <c:if test="${'06' eq compareMonth}">selected="selected"</c:if>>6</option>
											        <option value="07" <c:if test="${'07' eq compareMonth}">selected="selected"</c:if>>7</option>
											        <option value="08" <c:if test="${'08' eq compareMonth}">selected="selected"</c:if>>8</option>
											        <option value="09" <c:if test="${'09' eq compareMonth}">selected="selected"</c:if>>9</option>
											        <option value="10" <c:if test="${'10' eq compareMonth}">selected="selected"</c:if>>10</option>
											        <option value="11" <c:if test="${'11' eq compareMonth}">selected="selected"</c:if>>11</option>
											        <option value="12" <c:if test="${'12' eq compareMonth}">selected="selected"</c:if>>12</option>
										        </select>			                       	
					                        </spring:bind>
											<div class="blockButton" style=" right:0; bottom:0px; padding-top:10px;"> 
						                         <ul>
						                             <li><a id="excelDownloadButton" class="button" href="#a"><span>확인</span></a></li>
						                         	 <li><a id="excelCancelButton" class="button" href="#a"><span>취소</span></a></li>
						                         </ul>
						                     </div>
										</td>
									</tr>
								</table>
						 	</div>
                         </ul>
                     </div>
                     
                     <div class="blockBlank_10px"></div>	
				</div>
				<div class="blockListTable">
					<table summary="게시판설명">
						<caption></caption>
                        <thead>
                              <tr>
                                <th scope="col" width="10%">
                                <a onclick="sort('PURPOSE', '<c:if test="${searchCondition.sortColumn eq 'PURPOSE'}">${searchCondition.sortType}</c:if>');"  href="#a">
								구분
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'PURPOSE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'PURPOSE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>  
                                </th>
                                <th scope="col" width="20%">
                                <a onclick="sort('ITEMS', '<c:if test="${searchCondition.sortColumn eq 'ITEMS'}">${searchCondition.sortType}</c:if>');"  href="#a">
								품목
								</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'ITEMS' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'ITEMS' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>  
                                </th>
                                <th scope="col" width="*">
                                <a onclick="sort('PARTNER_NAME', '<c:if test="${searchCondition.sortColumn eq 'PARTNER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								거래처명
							 	</a>
								<c:choose>
									<c:when test="${searchCondition.sortColumn eq 'PARTNER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${searchCondition.sortColumn eq 'PARTNER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>  
								</th>
                                <th scope="col" width="10%">
                                 <a onclick="sort('COUNSELOR', '<c:if test="${searchCondition.sortColumn eq 'COUNSELOR'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  최종상담자
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'COUNSELOR' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'COUNSELOR' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                              
                               <!--  <th scope="col" width="8%">번호</th> -->
                                <th scope="col" width="15%">
                                 <a onclick="sort('COUNSEL_DATE', '<c:if test="${searchCondition.sortColumn eq 'COUNSEL_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  최종상담일
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'COUNSEL_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'COUNSEL_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                                <th scope="col" width="10%">
                                <a onclick="sort('COMMENT_CNT1', '<c:if test="${searchCondition.sortColumn eq 'COMMENT_CNT1'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  팀장Comment</a>
	                                  <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'COMMENT_CNT1' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'COMMENT_CNT1' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                                <th scope="col" width="10%">
                                <a onclick="sort('COMMENT_CNT2', '<c:if test="${searchCondition.sortColumn eq 'COMMENT_CNT2'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  임원Comment</a>
	                                  <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'COMMENT_CNT2' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'COMMENT_CNT2' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                                <th scope="col" width="10%">
                                <a onclick="sort('COUNSEL_CNT', '<c:if test="${searchCondition.sortColumn eq 'COUNSEL_CNT'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  상담등록수</a>
	                                  <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'COUNSEL_CNT' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'COUNSEL_CNT' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:choose>
								<c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="8"
											class="emptyRecord">등록된 상담내역이 없습니다.
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="qualityClaimSell" items="${searchResult.entity}"
										varStatus="status">
										<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">
												<c:choose>
													<c:when test="${qualityClaimSell.purpose == 'CR01'}">
													주원료
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR02'}">
													부원료
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR03'}">
													기자재
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR04'}">
													포장재
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR05'}">
													펄프판매
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR07'}">
													펠릿
													</c:when>
													<c:when test="${qualityClaimSell.purpose == 'CR06'}">
													기타
													</c:when>
													<c:otherwise></c:otherwise>
												</c:choose>
												</a>
											</td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">${qualityClaimSell.items}</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">${qualityClaimSell.partnerName}</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">
											${qualityClaimSell.counselor} 
											<c:choose>
												<c:when test="${!empty qualityClaimSell.user.jobDutyName}">
												${qualityClaimSell.user.jobDutyName}
												</c:when>
												<c:otherwise>${qualityClaimSell.user.jobTitleName}</c:otherwise>
											</c:choose>
											</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">${qualityClaimSell.counselDate}</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">
											<c:choose>
												<c:when test="${qualityClaimSell.commentCnt1 == '0'}">
												</c:when>
												<c:otherwise>${qualityClaimSell.commentCnt1}</c:otherwise>
											</c:choose>
											</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">
											<c:choose>
												<c:when test="${qualityClaimSell.commentCnt2 == '0'}">
												</c:when>
												<c:otherwise>${qualityClaimSell.commentCnt2}</c:otherwise>
											</c:choose>
											</a></td>
											<td><a href="<c:url value='/support/partner/partnerQualityClaimSell/detailQualityClaimSell.do?seqNum=${qualityClaimSell.seqNum}'/>">${qualityClaimSell.counselCnt}</a></td>
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
                        <!--//Page Numbur End-->	
                        
                        <div class="blockBlank_10px"></div>	
                        <!--blockButton Start-->
                        <div class="blockButton"> 
                            <ul>
                                <li><a class="button" href="<c:url value='/support/partner/partnerQualityClaimSell/createQualityClaimSellView.do'/>"><span>등록</span></a></li>
                            </ul>
                        </div>
                        <!--//blockButton End-->	
                        <div class="blockBlank_10px"></div>		
                			
						</div>
						<!--//blockListTable End-->	
					</div>
					
</form>		