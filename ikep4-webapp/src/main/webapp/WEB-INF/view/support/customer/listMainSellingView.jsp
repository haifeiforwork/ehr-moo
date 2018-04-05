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

						});
	})(jQuery);
//-->
</script>






<h1 class="none">컨텐츠영역</h1>
<form id="searchBoardItemForm"	method="post" action="<c:url value='/support/customer/customerMainSelling/mainSellingList.do'/>" >					
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
					<h2>주요 판매처</h2>
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
						<spring:bind path="searchCondition.searchColumn">
							<select name="${status.expression}"
								title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
								<option value="customer"
									<c:if test="${'customer' eq status.value}">selected="selected"</c:if>>
									<ikep4j:message pre='${prefix}' key='search.customer' />
								</option>
								<option value="chargeEmployee"
									<c:if test="${'subject' eq status.value}">selected="selected"</c:if>>
									담당자
								</option>
								<option value="registerName"
									<c:if test="${'counselor' eq status.value}">selected="selected"</c:if>>
									게시자
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
						  주요 판매처 정보를 공유합니다.
						</td>
						</tr>
					</tbody>
					</table>
		      <!--blockButton Start-->
                     <div class="blockButton" style="position:absolute; right:0; bottom:0px; padding-top:10px;"> 
                         <ul>
                             <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/createMainSellingFirstView.do'/>"><span>등록</span></a></li>
                         </ul>
                     </div>
                     <div class="blockBlank_10px"></div>	
				</div>
				<div class="blockListTable">
					<table summary="게시판설명">
						<caption></caption>
                        <thead>
                            <tr>
                                <th scope="col" width="8%">번호</th>
                                <th scope="col" width="*">
                                 <a onclick="sort('NAME', '<c:if test="${searchCondition.sortColumn eq 'NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  고객명
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                                <th scope="col" width="15%">
                                <a onclick="sort('CHARGE', '<c:if test="${searchCondition.sortColumn eq 'CHARGE'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  담당자
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'CHARGE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'CHARGE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>                                                                
                                <th scope="col" width="10%">주요판매처</th>                                
                                <th scope="col" width="15%">
                                 <a onclick="sort('REGISTER_NAME', '<c:if test="${searchCondition.sortColumn eq 'REGISTER_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  등록자
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'REGISTER_NAME' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								  </c:choose>
                                </th>
                                <th scope="col" width="15%">
	                             <a onclick="sort('REGIST_DATE', '<c:if test="${searchCondition.sortColumn eq 'REGIST_DATE'}">${searchCondition.sortType}</c:if>');"  href="#a">
	                                  등록일
	                             </a>
	                              <c:choose>
										<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'DESC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="desc" />" src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
										<c:when test="${searchCondition.sortColumn eq 'REGIST_DATE' and searchCondition.sortType eq 'ASC'}"><img alt="<ikep4j:message pre="${preSearch}" key="sortType" post="asc" />" src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
							      </c:choose>
                                </th>
                                <th scope="col" width="5%">조회수</th>
                            </tr>
                        </thead>
                        <tbody>
                        	<c:choose>
								<c:when test="${searchResult.emptyRecord}">
									<tr>
										<td colspan="${permission.isSystemAdmin ? 8 : 7}"
											class="emptyRecord">등록된 고객정보가 없습니다.
										</td>
									</tr>
								</c:when>
								<c:otherwise>
									<c:forEach var="mainSelling" items="${searchResult.entity}"
										varStatus="status">
										<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
											<td>${searchCondition.recordCount
												-((searchCondition.pageIndex - 1) *
												searchCondition.pagePerRecord) - status.index}</td>
											
											<td class="textLeft"><a href="<c:url value='/support/customer/customerMainSelling/detailMainSellingView.do?customerId=${mainSelling.customerId}&registerId=${mainSelling.registerId}&customer=${mainSelling.customer}'/>">${mainSelling.customer}</a></td>
											<td>${mainSelling.chargeEmployee}</td>
											<td>${mainSelling.sellingCnt}건</td>
											<td>${mainSelling.registerName}</td>
											<td><ikep4j:timezone date="${mainSelling.registDate}"/></td>
											<td>${mainSelling.hitCount}</td>
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
                                <li><a class="button" href="<c:url value='/support/customer/customerMainSelling/createMainSellingFirstView.do'/>"><span>등록</span></a></li>
                            </ul>
                        </div>
                        <!--//blockButton End-->	
                        <div class="blockBlank_10px"></div>		
                			
						</div>
						<!--//blockListTable End-->	
					</div>
					
</form>		