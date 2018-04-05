<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="prefix" value="ui.support.customer.board" />
<c:set var="preSearch" value="ui.ikep4.common.searchCondition" />
<c:set var="preButton" value="ui.lightpack.common.button" />
<c:set var="preList" value="ui.support.customer.manInfoItemList" />
<c:set var="statementTmpCnt" value="0" />
<c:forEach var="statement" items="${statementList}" varStatus="status">
<c:set var="statementCnt" value="${status.count}" />
<c:if test="${user.sapId == statement.reviewnam}">
<c:set var="statementTmpCnt" value="${statementTmpCnt+1}" />
</c:if>
</c:forEach>
<c:forEach var="budget" items="${budgetList}" varStatus="status">
<c:set var="budgetCnt" value="${status.count}" />
</c:forEach>

<script type="text/javascript">
<!--
	var bbsIframe; // 부모 iframe
	var bbsIsIframe = 0; // iframe 존재 여부
	var isLayout; // 레이아웃 보기 여부
	var bbsLayout = null;
	var layoutType = "n"; // n:없음, v:가로보기, h:세로보기
	var tmpWindowSize = 0;

	function mouseOver(obj){
		
		//obj.className = "bgSelected";
		//obj.style.backgroundColor="#edf2f5";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).css("background-color","#edf2f5");
		});	
	}
	function mouseOut(obj){
		//obj.className = "";
		//obj.style.backgroundColor="";
		var tds =$jq(obj).find("td");
		$jq.each( tds , function() {
			$jq(this).css("background-color","");
		});	
	}
	
	function iframeReflash() {
		location.href = "<c:url value='/lightpack/board/boardItem/listBoardItemView.do?boardId=${board.boardId}&searchConditionString=${searchConditionString}&popupYn=${popupYn}'/>";
	};

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
			refsh();
		}
		
		refsh = function() {
			if(tmpWindowSize != 0 && tmpWindowSize != $(parent).height()){ 
				$("#searchButton").trigger("click");
			}
			tmpWindowSize = $(parent).height();	
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
		
		getList = function() {
			$("#searchButton").trigger("click");
		};

		$(document)
				.ready(
						function() {
							$jq("#date1").datepicker().next().eq(0).click(
									function() {
										$jq(this).prev().eq(0).datepicker(
												"show");
									});
							$jq("#date2").datepicker().next().eq(0).click(
									function() {
										$jq(this).prev().eq(0).datepicker(
												"show");
									});
						
							//분기 radio 버튼 바인딩
							$("input[name=quarter]:radio").change(function() {
								var year = "${thisYear}";
								switch ($(this).val()) {
								case '1':
									$("#date1").val(year + ".01.01");
									$("#date2").val(year + ".03.31");
									break; //1분기
								case '2':
									$("#date1").val(year + ".04.01");
									$("#date2").val(year + ".06.30");
									break; //2분기
								case '3':
									$("#date1").val(year + ".07.01");
									$("#date2").val(year + ".09.30");
									break; //3분기
								case '4':
									$("#date1").val(year + ".10.01");
									$("#date2").val(year + ".12.31");
									break; //4분기							
								}
							});
							
							$("input[name=period]:radio").change(function() {
								var year = "${thisYear}";
								var nowQuarter = "${nowQuarter}";
								var month1 = "${month1}";
								var month2 = "${month2}";
								var month3 = "${month3}";
								
								if($(this).val() == "2"){
									if(month1 == "1"){
										$("#date1").val(year + ".01.01");
										$("#date2").val(year + ".01.31");
									}else if(month1 == "4"){
										$("#date1").val(year + ".04.01");
										$("#date2").val(year + ".04.30");
									}else if(month1 == "7"){
										$("#date1").val(year + ".07.01");
										$("#date2").val(year + ".07.31");
									}else{
										$("#date1").val(year + ".10.01");
										$("#date2").val(year + ".10.31");
									}
								}else if($(this).val() == "3"){
									if(month2 == "2"){
										$("#date1").val(year + ".02.01");
										$("#date2").val(year + ".02.28");
									}else if(month2 == "5"){
										$("#date1").val(year + ".05.01");
										$("#date2").val(year + ".05.31");
									}else if(month2 == "8"){
										$("#date1").val(year + ".08.01");
										$("#date2").val(year + ".08.31");
									}else{
										$("#date1").val(year + ".11.01");
										$("#date2").val(year + ".11.30");
									}
								}else if($(this).val() == "4"){
									if(month3 == "3"){
										$("#date1").val(year + ".03.01");
										$("#date2").val(year + ".03.31");
									}else if(month3 == "6"){
										$("#date1").val(year + ".06.01");
										$("#date2").val(year + ".06.30");
									}else if(month3 == "9"){
										$("#date1").val(year + ".09.01");
										$("#date2").val(year + ".09.30");
									}else{
										$("#date1").val(year + ".12.01");
										$("#date2").val(year + ".12.31");
									}
								}else{
									switch (nowQuarter) {
									case '1':
										$("#date1").val(year + ".01.01");
										$("#date2").val(year + ".03.31");
										break; //1분기
									case '2':
										$("#date1").val(year + ".04.01");
										$("#date2").val(year + ".06.30");
										break; //2분기
									case '3':
										$("#date1").val(year + ".07.01");
										$("#date2").val(year + ".09.30");
										break; //3분기
									case '4':
										$("#date1").val(year + ".10.01");
										$("#date2").val(year + ".12.31");
										break; //4분기							
									}
								}
							});
							
							//초기 분기 세팅
							var setQuater = function(quarter){
								//alert(quarter);
								$("input[name=quarter]:radio:[value="+quarter+"]").attr("checked",true).change();
							}
							//함수 호출
							setQuater("${quarter}");
							
							$("input[name=date1]").keydown(function(e){
								if(e.keyCode=="13"){
									$("#searchButton").trigger("click");
								}
							});
							
							$("input[name=date2]").keydown(function(e){
								if(e.keyCode=="13"){
									$("#searchButton").trigger("click");
								}
							});
							
							//검색버튼 바인딩
							$jq("#searchButton").click(function(){
								statementSearch();
							});
							
							//검색
							statementSearch = function (){
								//검색 날짜 세팅
								$ifspmon = $jq('#date1').val();
								$itspmon = $jq('#date2').val();
								
								// 조회하는 기간의 년도가 다를 경우 
								if($ifspmon.substring(0,4)!=$itspmon.substring(0,4)){
									alert("같은 년도 예산 계획/실적만 조회 할 수 있습니다.");
									return;
								}
								if($ifspmon.substring(0,7).replace(".","")>$itspmon.substring(0,7).replace(".","")){
									alert("조회 시작기간이 종료기간 보다 큽니다.");
									return;
								}
								
								if($ifspmon=="" && $itspmon=="") {
									$jq("#searchItemForm").submit();
								} else {
									$ifspmon = $jq("#date1").val();
									if($ifspmon.length < 10) {
										alert("정합한 날짜 형식이 아닙니다");
										return;
									} else {
										$jq('#searchItemForm>input[name=ifspmon]').val($jq("#date1").val());
									}
									
									$itspmon = $jq("#date2").val();
									if($itspmon.length < 10) {
										alert("");
										return;
									} else {
										$jq('#searchItemForm>input[name=itspmon]').val($jq("#date2").val());
									}
									
									$jq("#searchItemForm").submit();
								}
							};

							if (window.parent.menuMark != null) {
								window.parent.menuMark("${board.boardId}");
							}

							sort = function(ifname, istate) {
								$("#searchItemForm input[name=ifname]").val(ifname);
								$("#searchItemForm input[name=istate]").val(istate == 'A' ? 'D' : 'A');
								$("#searchButton").click();
							};

							//search 동작
							$("#searchBoardItemButton").click(
								function() {
									$("#searchItemForm input[name=actionType]").val("search");
									$("#searchItemForm").submit();
									return false;
							});

							//페이지 할 row수 설정
							$("#searchItemForm select[name=pagePerRecord]").change(function() {
								$("#searchItemForm input[name=actionType]").val("pagePerRecord");
								$("#searchItemForm input[name=pageIndex]").val("1");
								$("#searchButton").trigger("click");
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
							
							
						//등록 프로세스 
					/* 	$("a.addButton").click(function(){
				
							var url = iKEP.getContextRoot() + "/support/customer/customerBasicInfo/popupBusinessNo.do";
							var options = {
								width:700,
								height:440,
								resizable:true,						 		
							};
							iKEP.popupOpen(url, options);	 
						})
						 */

						goAdd = function() {
							
							/*var url = '<c:url value='/support/customer/customerBasicInfo/popupBusinessNo.do'/>';
							var title = '고객정보 등록'
							var width = 780;
							var height = 580;

							var dialog = iKEP.showDialog({ 
								title: title,
								url: url,
								width:width,
								height:height,
								modal: true,
								scroll: "yes",
								callback : function(result){
									alert("성공");
									
								}
							});*/
							
						};
						
						statementViewPop = function(belnr,bukrs,gjahr,belnr2,cardno,reqnamt,dmbtr,revs,belnrrd,belnr2rd) {	
							if(revs=="X"){
								var url = iKEP.getContextRoot() + '/portal/moorimess/statementCommon/statementView.do?belnr='+belnrrd+'&bukrs='+bukrs+'&gjahr='+gjahr+'&belnr2='+belnr2rd+'&cardno='+cardno+'&reqnamt='+encodeURI(reqnamt)+'&dmbtr='+dmbtr+'&revs='+revs; 
							}else{
								var url = iKEP.getContextRoot() + '/portal/moorimess/statementCommon/statementView.do?belnr='+belnr+'&bukrs='+bukrs+'&gjahr='+gjahr+'&belnr2='+belnr2+'&cardno='+cardno+'&reqnamt='+encodeURI(reqnamt)+'&dmbtr='+dmbtr+'&revs='+revs; 
							}

							iKEP.popupOpen(url, {width:1000, height:500}, "statementView");
						};	
						
						$("#checkboxAllItem").click(function() { 
							$("#searchItemForm input[name=checkboxItem]").attr("checked", $(this).is(":checked"));  
						});  
						
						goConfirm = function(confm){
							var stdIds = new Array();
							var ztexts = new Array();
							var reqnam = new Array();
							var revs = new Array();
							var flg = true;
							var rflg = true;
							var tmptext = "";
							var tmpreqnam = "";
							var tmprevs = "";
							$("#searchItemForm input[name=checkboxItem]:checked").each(function(index) { 
								tmptext = $(this).val();
								tmptext = tmptext+"_zreceivetext";
								if(confm == "D" && document.getElementById(tmptext).value == ""){
									flg = false;
								}
								
								
								
								tmpreqnam = $(this).val();
								tmprevs = $(this).val();
								tmpreqnam = tmpreqnam+"_reqnam";
								tmprevs = tmprevs+"_revs";
								stdIds.push($(this).val()); 
								ztexts.push(document.getElementById(tmptext).value); 
								reqnam.push(document.getElementById(tmpreqnam).value); 
								if(document.getElementById(tmprevs).value == ""){
									revs.push("A");
								}else{
									revs.push(document.getElementById(tmprevs).value);
								}
								
								if(confm == "D" && document.getElementById(tmprevs).value == "X"){
									rflg = false;
								}
								tmptext = "";
								tmpreqnam = "";
								tmprevs = "";
							});	
							
							if(rflg == false){
								alert("역분개는 반려가 불가능합니다.");
								return;
							}
							
							if(flg == false){
								alert("반려사유를 입력하세요.");
								return;
							}
							
							if(stdIds.length < 1){
								alert("승인 및 반려할 데이터를 선택하세요.");
							 	return;
							}
							
							/*for(i=0;i<reqnam.length;i++){
								if(reqnam[0] != reqnam[i]){
									alert("승인요청자는 여러명을 선택할수 없습니다.");
									return;
								}
							}*/
							
							if(confm == "S"){
								if(confirm("선택한 요청건을 승인하시겠습니까?")) {
									$("#searchItemForm").ajaxLoadStart(); 
									
									$.post("<c:url value='/portal/moorimess/statementCommon/statementConfirm.do'/>", {"confm" : confm,"stdId" : stdIds.toString(),"ztexts" : ztexts.toString(),"revs" : revs.toString()}) 
									.success(function(data) {  
										$("#searchButton").click();
									})
								}  
							}else{
								if(confirm("선택한 요청건을 반려하시겠습니까?")) {
									$("#searchItemForm").ajaxLoadStart(); 
									
									$.post("<c:url value='/portal/moorimess/statementCommon/statementConfirm.do'/>", {"confm" : confm,"stdId" : stdIds.toString(),"ztexts" : ztexts.toString(),"revs" : revs.toString()}) 
									.success(function(data) {  
										$("#searchButton").click();
									})
								}  
							}
							
						};

						$("#blktable").attr("style","font-size:11px;");
						$("#blktable1").attr("style","font-size:11px;");
						$("#blktable2").attr("style","font-size:11px;");
						$("#blktable3").attr("style","font-size:11px;");
						$("#blktable4").attr("style","font-size:11px;");
						$("#blktable5").attr("style","font-size:11px;");
						$("#blktable6").attr("style","font-size:11px;");

						});
		
	})(jQuery);
//-->
</script>






	<h1 class="none">컨텐츠영역</h1>
<form id="searchItemForm"	method="post" action="<c:url value='/portal/moorimess/statementCommon/statementList.do'/>" >	

<spring:bind path="statementSearchCondition.ifname">
	<input name="${status.expression}" type="hidden"
		value="${status.value}"
		title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
<spring:bind path="statementSearchCondition.istate">
	<input name="${status.expression}" type="hidden"
		value="${status.value}"
		title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
</spring:bind>
				
	<input type="hidden" name="ifspmon"/>
	<input type="hidden" name="itspmon"/> 
	<input type="hidden" name="isInit" value="no"/>
	<!--tableTop Start-->
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>전표 승인 요청 결재</h2>
		<div class="listInfo">
			<%-- <spring:bind path="statementSearchCondition.pagePerRecord">
				<select name="${status.expression}"
					title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
					<c:forEach var="code" items="${portalCode.pageNumList}">
						<option value="${code.key}"
							<c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
					</c:forEach>
				</select>
			</spring:bind> --%>
			<%-- <div class="totalNum">
				${statementResult.pageIndex}/ ${statementResult.pageCount}
				<ikep4j:message pre='${preSearch}' key='page' />
				( --%>
				<ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<span> ${statementResult.recordCount}</span>
				<!-- ) 
			</div>-->
		</div>
		<%-- <div class="listInfo">
			<div class="totalNum">
				<span><ikep4j:message pre='${preSearch}' key='pageCount.info' />
				<c:if test="${!empty statementCnt && statementTmpCnt != statementCnt}">
				 ${statementCnt-statementTmpCnt}
				 </c:if> 
				<c:if test="${empty statementCnt || statementTmpCnt == statementCnt}">
					0
				</c:if> 
				 건</span>
			</div>
		</div>	 --%>
		<div class="clear"></div>
	</div>
	<!--//tableTop End-->
	<div style="width:90%">
	<span style="float:right;font-size:10px;">(단위 : 원)</span>
	</div>
	<div class="tableDesciption" style="position:relative;">
		<table summary="게시판 설명">
		<caption></caption>
		<tbody>
			<tr>
			<td>
			  
			</td>
			</tr>
		</tbody>
		</table>
    <div class="blockBlank_10px"></div>	
	</div>
	<div class="blockDetail" style="width:90%">
	
	    <table>
	        <caption></caption>
	        <colgroup>
	            <col width="33%"/>
	            <col width="33%"/>
				<col width="34%"/>
	        </colgroup>
	        <tbody>
	        	<tr>
	        		<th style="text-align:center;" colspan="3">${nowQuarter}분기 주요 활동성 경비 예산 잔액 현황</th>
	        	</tr>
	        	<tr>
					<th style="text-align:left;border-right-width:0px;" colspan="2">
						<img src="<c:url value='/base/images/theme/theme06/basic/ic_point_08.gif'/>"alt="" style="margin-bottom:3px;"/> 기간(월)
						<input name="date1" id="date1" title="조회시작일" class="inputbox w10 datepicker" type="text" value="${statementSearchCondition.ifspmon}" size="9" /> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>" align="absmiddle" alt="calendar" style="cursor: pointer;" />&nbsp;~&nbsp;
						<input name="date2" id="date2" title="조회종료일" class="inputbox w10 datepicker" type="text" value="${statementSearchCondition.itspmon}" size="9" /> <img src="<c:url value="/base/images/icon/ic_cal.gif"/>"align="absmiddle" alt="calendar" style="cursor: pointer;" />
						 &nbsp;&nbsp;&nbsp;
						 <spring:bind path="statementSearchCondition.period">
							<input name="${status.expression}" <c:if test="${status.value == 1}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="1" style="vertical-align: middle;" />${nowQuarter}분기
							<input name="${status.expression}" <c:if test="${status.value == 2}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="2" style="vertical-align: middle;" />${month1}월 
							<input name="${status.expression}" <c:if test="${status.value == 3}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="3" style="vertical-align: middle;" />${month2}월 
							<input name="${status.expression}" <c:if test="${status.value == 4}">checked="checked"</c:if> class="radio" title="radio" type="radio" value="4" style="vertical-align: middle;" />${month3}월
						</spring:bind>
					</th>
					<th style="text-align:left;border-left-width:0px;">
					부서
						<spring:bind path="statementSearchCondition.kostl">  
						<select name="${status.expression}" style="vertical-align: bottom;">
						<option value="" <c:if test="${empty status.value}">selected="selected"</c:if>>전체</option>
						<c:forEach var="code" items="${costCenterList}">
							<option value="${code.kostl}" <c:if test="${code.kostl eq status.value}">selected="selected"</c:if>>${code.ktext}</option>
						</c:forEach> 
						</select>
						</spring:bind>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<a href="#a" id="searchButton"><img src="<c:url value='/base/images/theme/theme01/basic/btn_search.gif'/>"alt=""style="vertical-align: bottom;" /></a>
					</th>
				</tr>
				<tr>
					<th style="text-align:center;">
						무림 페이퍼
					</th>
					<th style="text-align:center;">
						무림 P&P
					</th>
					<th style="text-align:center;">
						무림 SP
					</th>
				</tr>
				<c:choose>
					<c:when test="${empty budgetList1 && empty budgetList2 && empty budgetList3}">
						<tr>
							<td colspan="3" class="emptyRecord" style="text-align:center;">
								설정해놓은 현황이 없습니다.
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						
						<c:if test="${empty statementSearchCondition.kostl}">
							<tr>
								<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
									<table id="blktable1">
										<c:forEach var="item1" items="${budgetList2}" varStatus="status">
											<c:if test="${status.first}">
												<c:set var="balcu1" value="0" />
												<c:set var="sumGroup1" value="${item1.kstar}" />	
												<c:set var="sumGroupTxt1" value="${item1.ltext}" />	
												<c:set var="costCenter1" value="${item1.ktext}" />												
											</c:if>
											
											<c:choose>
												<c:when test="${costCenter1!=item1.ktext}">
													<tr>
													<td style="width:30%;">${costCenter1}</td>
													<td style="width:47%;">${sumGroupTxt1}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu1==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu1}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
													</tr>
													<c:set var="balcu1" value="0" />
													<c:set var="balcu1" value="${balcu1+item1.balcu}" />
													<c:set var="sumGroup1" value="${item1.kstar}" />
													<c:set var="sumGroupTxt1" value="${item1.ltext}" />	
													<c:set var="costCenter1" value="${item1.ktext}" />	
													<c:if test="${status.last}">
														<tr>
															<td style="width:30%;">${costCenter1}</td>
															<td style="width:47%;">${sumGroupTxt1}</td>
															<td style="text-align:right;width:23%;">
																<c:choose>
																	<c:when test="${balcu1==0}">
																		-
																	</c:when>
																	<c:otherwise>
																		<fmt:formatNumber value="${balcu1}" type="number" />
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
													</c:if>	
												</c:when>
												<c:otherwise>
													<c:choose>
														<c:when test="${sumGroup1!=item1.kstar}">
															<tr>
															<td style="width:30%;">${costCenter1}</td>
															<td style="width:47%;">${sumGroupTxt1}</td>
															<td style="text-align:right;width:23%;">
																<c:choose>
																	<c:when test="${balcu1==0}">
																		-
																	</c:when>
																	<c:otherwise>
																		<fmt:formatNumber value="${balcu1}" type="number" />
																	</c:otherwise>
																</c:choose>
															</td>
															</tr>
															<c:set var="balcu1" value="0" />
															<c:set var="balcu1" value="${balcu1+item1.balcu}" />
															<c:set var="sumGroup1" value="${item1.kstar}" />
															<c:set var="sumGroupTxt1" value="${item1.ltext}" />	
															<c:set var="costCenter1" value="${item1.ktext}" />	
															<c:if test="${status.last}">
																<tr>
																	<td style="width:30%;">${costCenter1}</td>
																	<td style="width:47%;">${sumGroupTxt1}</td>
																	<td style="text-align:right;width:23%;">
																		<c:choose>
																			<c:when test="${balcu1==0}">
																				-
																			</c:when>
																			<c:otherwise>
																				<fmt:formatNumber value="${balcu1}" type="number" />
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</c:if>
														</c:when>
														<c:otherwise>
															<c:set var="balcu1" value="${balcu1+item1.balcu}" />
															<c:if test="${status.last}">
																<tr>
																	<td style="width:30%;">${costCenter1}</td>
																	<td style="width:47%;">${sumGroupTxt1}</td>
																	<td style="text-align:right;width:23%;">
																		<c:choose>
																			<c:when test="${balcu1==0}">
																				-
																			</c:when>
																			<c:otherwise>
																				<fmt:formatNumber value="${balcu1}" type="number" />
																			</c:otherwise>
																		</c:choose>
																	</td>
																</tr>
															</c:if>
														</c:otherwise>
													</c:choose>
												</c:otherwise>
											</c:choose>
									</c:forEach>
								</table>
							</td>
							<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
								<table id="blktable2">
									<c:forEach var="item2" items="${budgetList3}" varStatus="status">
								
										<c:if test="${status.first}">
											<c:set var="balcu2" value="0" />
											<c:set var="sumGroup2" value="${item2.kstar}" />	
											<c:set var="sumGroupTxt2" value="${item2.ltext}" />	
											<c:set var="costCenter2" value="${item2.ktext}" />	
										</c:if>
										<c:choose>
											<c:when test="${costCenter2!=item2.ktext}">
												<tr>
												<td style="width:30%;">${costCenter2}</td>
												<td style="width:47%;">${sumGroupTxt2}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu2==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu2}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<c:set var="balcu2" value="0" />
												<c:set var="balcu2" value="${balcu2+item2.balcu}" />
												<c:set var="sumGroup2" value="${item2.kstar}" />
												<c:set var="sumGroupTxt2" value="${item2.ltext}" />	
												<c:set var="costCenter2" value="${item2.ktext}" />	
												<c:if test="${status.last}">
													<tr>
														<td style="width:30%;">${costCenter2}</td>
														<td style="width:47%;">${sumGroupTxt2}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${item2.balcu==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${item2.balcu}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${sumGroup2!=item2.kstar}">
														<tr>
														<td style="width:30%;">${costCenter2}</td>
														<td style="width:47%;">${sumGroupTxt2}</td>
															<td style="text-align:right;width:23%;">
																<c:choose>
																	<c:when test="${balcu2==0}">
																		-
																	</c:when>
																	<c:otherwise>
																		<fmt:formatNumber value="${balcu2}" type="number" />
																	</c:otherwise>
																</c:choose>
															</td>
														</tr>
														<c:set var="balcu2" value="0" />
														<c:set var="balcu2" value="${balcu2+item2.balcu}" />
														<c:set var="sumGroup2" value="${item2.kstar}" />
														<c:set var="sumGroupTxt2" value="${item2.ltext}" />	
														<c:set var="costCenter2" value="${item2.ktext}" />	
														<c:if test="${status.last}">
															<tr>
																<td style="width:30%;">${costCenter2}</td>
																<td style="width:47%;">${sumGroupTxt2}</td>
																<td style="text-align:right;width:23%;">
																	<c:choose>
																		<c:when test="${item2.balcu==0}">
																			-
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber value="${item2.balcu}" type="number" />
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:set var="balcu2" value="${balcu2+item2.balcu}" />
														<c:if test="${status.last}">
															<tr>
																<td style="width:30%;">${costCenter2}</td>
																<td style="width:47%;">${sumGroupTxt2}</td>
																<td style="text-align:right;width:23%;">
																	<c:choose>
																		<c:when test="${item2.balcu==0}">
																			-
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber value="${balcu2}" type="number" />
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</table>
							</td>
							<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
								<table id="blktable3">
									<c:forEach var="item3" items="${budgetList1}" varStatus="status">
									
										<c:if test="${status.first}">
											<c:set var="balcu3" value="0" />
											<c:set var="sumGroup3" value="${item3.kstar}" />	
											<c:set var="sumGroupTxt3" value="${item3.ltext}" />		
											<c:set var="costCenter3" value="${item3.ktext}" />	
										</c:if>
										
										<c:choose>
											<c:when test="${costCenter3!=item3.ktext}">
												<tr>
												<td style="width:30%;">${costCenter3}</td>
												<td style="width:47%;">${sumGroupTxt3}</td>
												<td style="text-align:right;width:23%;">
													<c:choose>
														<c:when test="${balcu3==0}">
															-
														</c:when>
														<c:otherwise>
															<fmt:formatNumber value="${balcu3}" type="number" />
														</c:otherwise>
													</c:choose>
												</td>
												</tr>
												<c:set var="balcu3" value="0" />
												<c:set var="balcu3" value="${balcu3+item3.balcu}" />
												<c:set var="sumGroup3" value="${item3.kstar}" />
												<c:set var="sumGroupTxt3" value="${item3.ltext}" />	
												<c:set var="costCenter3" value="${item3.ktext}" />	
												<c:if test="${status.last}">
													<tr>
														<td style="width:30%;">${costCenter3}</td>
														<td style="width:47%;">${sumGroupTxt3}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${balcu3==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${balcu3}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:choose>
													<c:when test="${sumGroup3!=item3.kstar}">
														<tr>
														<td style="width:30%;">${costCenter3}</td>
														<td style="width:47%;">${sumGroupTxt3}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${balcu3==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${balcu3}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
														</tr>
														<c:set var="balcu3" value="0" />
														<c:set var="balcu3" value="${balcu3+item3.balcu}" />
														<c:set var="sumGroup3" value="${item3.kstar}" />
														<c:set var="sumGroupTxt3" value="${item3.ltext}" />	
														<c:set var="costCenter3" value="${item3.ktext}" />	
														<c:if test="${status.last}">
															<tr>
																<td style="width:30%;">${costCenter3}</td>
																<td style="width:47%;">${sumGroupTxt3}</td>
																<td style="text-align:right;width:23%;">
																	<c:choose>
																		<c:when test="${balcu3==0}">
																			-
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber value="${balcu3}" type="number" />
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</c:if>
													</c:when>
													<c:otherwise>
														<c:set var="balcu3" value="${balcu3+item3.balcu}" />
														<c:if test="${status.last}">
															<tr>
																<td style="width:30%;">${costCenter3}</td>
																<td style="width:47%;">${sumGroupTxt3}</td>
																<td style="text-align:right;width:23%;">
																	<c:choose>
																		<c:when test="${balcu3==0}">
																			-
																		</c:when>
																		<c:otherwise>
																			<fmt:formatNumber value="${balcu3}" type="number" />
																		</c:otherwise>
																	</c:choose>
																</td>
															</tr>
														</c:if>
													</c:otherwise>
												</c:choose>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</table>
							</td>
						</tr>
						</c:if>
						<c:if test="${!empty statementSearchCondition.kostl}">
							<tr>
							<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
								<table id="blktable4">
									<c:forEach var="item1" items="${budgetList2}" varStatus="status">
										<c:if test="${status.first && statementSearchCondition.kostl == item1.kostl}">
											<c:set var="balcu1" value="0" />
											<c:set var="sumGroup1" value="${item1.kstar}" />	
											<c:set var="sumGroupTxt1" value="${item1.ltext}" />		
											<c:set var="costCenter1" value="${item1.ktext}" />
										</c:if>
										<c:if test="${sumGroup1==item1.kstar && statementSearchCondition.kostl == item1.kostl}">
										<c:set var="balcu1" value="${balcu1+item1.balcu}" />
										</c:if>
										<c:choose>
											<c:when test="${sumGroup1!=item1.kstar && statementSearchCondition.kostl == item1.kostl}">
												<tr>
												<td style="width:30%;">${costCenter1}</td>
												<td style="width:47%;">${sumGroupTxt1}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu1==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu1}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<c:set var="balcu1" value="0" />
												<c:set var="balcu1" value="${balcu1+item1.balcu}" />
												<c:set var="sumGroup1" value="${item1.kstar}" />
												<c:set var="sumGroupTxt1" value="${item1.ltext}" />	
												<c:set var="costCenter1" value="${item1.ktext}" />
												<c:if test="${status.last && statementSearchCondition.kostl == item1.kostl}">
													<tr>
														<td style="width:30%;">${costCenter1}</td>
														<td style="width:47%;">${sumGroupTxt1}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${item1.balcu==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${item1.balcu}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${status.last && statementSearchCondition.kostl == item1.kostl}">
													<tr>
														<td style="width:30%;">${costCenter1}</td>
														<td style="width:47%;">${sumGroupTxt1}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${balcu1==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${balcu1}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</table>
							</td>
							<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
								<table id="blktable5">
									<c:forEach var="item2" items="${budgetList3}" varStatus="status">
									
										<c:if test="${status.first && statementSearchCondition.kostl == item2.kostl}">
											<c:set var="balcu2" value="0" />
											<c:set var="sumGroup2" value="${item2.kstar}" />	
											<c:set var="sumGroupTxt2" value="${item2.ltext}" />		
											<c:set var="costCenter2" value="${item2.ktext}" />
										</c:if>
										<c:if test="${sumGroup2==item2.kstar && statementSearchCondition.kostl == item2.kostl}">
										<c:set var="balcu2" value="${balcu2+item2.balcu}" />
										</c:if>
										<c:choose>
											<c:when test="${sumGroup2!=item2.kstar && statementSearchCondition.kostl == item2.kostl}">
												<tr>
												<td style="width:30%;">${costCenter2}</td>
												<td style="width:47%;">${sumGroupTxt2}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu2==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu2}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<c:set var="balcu2" value="0" />
												<c:set var="balcu2" value="${balcu2+item2.balcu}" />
												<c:set var="sumGroup2" value="${item2.kstar}" />
												<c:set var="sumGroupTxt2" value="${item2.ltext}" />	
												<c:set var="costCenter2" value="${item2.ktext}" />
												<c:if test="${status.last && statementSearchCondition.kostl == item2.kostl}">
													<tr>
														<td style="width:30%;">${costCenter2}</td>
														<td style="width:47%;">${sumGroupTxt2}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${item2.balcu==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${item2.balcu}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${status.last && statementSearchCondition.kostl == item2.kostl}">
													<tr>
														<td style="width:30%;">${costCenter2}</td>
														<td style="width:47%;">${sumGroupTxt2}</td>
														<td style="text-align:right;width:23%;">
															<c:choose>
																<c:when test="${balcu2==0}">
																	-
																</c:when>
																<c:otherwise>
																	<fmt:formatNumber value="${balcu2}" type="number" />
																</c:otherwise>
															</c:choose>
														</td>
													</tr>
												</c:if>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</table>
							</td>
							<td style="padding-left:0px;padding-right:0px;padding-top:0px;padding-bottom:0px;vertical-align:top;">
								<table id="blktable6">
									<c:forEach var="item3" items="${budgetList1}" varStatus="status">
									
										<c:if test="${status.first && statementSearchCondition.kostl == item3.kostl}">
											<c:set var="balcu3" value="0" />
											<c:set var="sumGroup3" value="${item3.kstar}" />	
											<c:set var="sumGroupTxt3" value="${item3.ltext}" />			
											<c:set var="costCenter3" value="${item3.ktext}" />
										</c:if>
										<c:if test="${sumGroup3==item3.kstar && statementSearchCondition.kostl == item3.kostl}">
										<c:set var="balcu3" value="${balcu3+item3.balcu}" />
										</c:if>
										<c:choose>
											<c:when test="${sumGroup3!=item3.kstar && statementSearchCondition.kostl == item3.kostl}">
												<tr>
												<td style="width:30%;">${costCenter3}</td>
												<td style="width:47%;">${sumGroupTxt3}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu3==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu3}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
												</tr>
												<c:set var="balcu3" value="0" />
												<c:set var="balcu3" value="${balcu3+item3.balcu}" />
												<c:set var="sumGroup3" value="${item3.kstar}" />
												<c:set var="sumGroupTxt3" value="${item3.ltext}" />	
												<c:set var="costCenter3" value="${item3.ktext}" />
												<c:if test="${status.last && statementSearchCondition.kostl == item3.kostl}">
													<tr>
														<td style="width:30%;">${costCenter3}</td>
												<td style="width:47%;">${sumGroupTxt3}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu3==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu3}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
													</tr>
												</c:if>
											</c:when>
											<c:otherwise>
												<c:if test="${status.last && statementSearchCondition.kostl == item3.kostl}">
													<tr>
														<td style="width:30%;">${costCenter3}</td>
												<td style="width:47%;">${sumGroupTxt3}</td>
													<td style="text-align:right;width:23%;">
														<c:choose>
															<c:when test="${balcu3==0}">
																-
															</c:when>
															<c:otherwise>
																<fmt:formatNumber value="${balcu3}" type="number" />
															</c:otherwise>
														</c:choose>
													</td>
													</tr>
												</c:if>
											</c:otherwise>
										</c:choose>
										
									</c:forEach>
								</table>
							</td>
						</tr>
					</c:if>
					</c:otherwise>
				</c:choose>
	        </tbody>
	    </table>
	</div>
	<table width="100%">
		<tr>
			<td style="text-align:left;" width="50%">
				<span style="font-size:11px;"><strong>결재타입</strong> A: 임원 or 팀장 전결, B: 파트장 전결, C:파트장 검토 후 팀장 전결</span>
			</td>
			<td style="text-align:right;" width="50%">
				<div class="blockButton">
			        <ul>		
					<span style="font-size:10px;">(단위 : 원)</span>
			            <li><a class="button" href="javascript:goConfirm('S');"><span>승인</span>
						</a></li>
						<li><a class="button" href="javascript:goConfirm('D');"><span>반려</span>
						</a></li>
			        </ul>
			 	</div>
			</td>
		</tr>
	</table>
	<div class="blockListTable">
		<table summary="게시판설명" id="blktable">
			<caption></caption>
                     <thead>
                         <tr>
                         	 <th scope="col" width="2%"><input id="checkboxAllItem" class="checkbox" title="checkbox" type="checkbox" value="" /></th>
                             <th scope="col" width="3%">
                             	<a onclick="sort('BUKRS', '<c:if test="${statementSearchCondition.ifname eq 'BUKRS'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								회사
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'BUKRS' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'BUKRS' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('REQ_DATE', '<c:if test="${statementSearchCondition.ifname eq 'REQ_DATE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								승인<br/>요청일
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'REQ_DATE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'REQ_DATE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="5%">
                             	<a onclick="sort('REQNAM_T', '<c:if test="${statementSearchCondition.ifname eq 'REQNAM_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								승인<br/>요청자
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'REQNAM_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'REQNAM_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
							 <th scope="col" width="5%">
							 	<a onclick="sort('APPR_TYPE', '<c:if test="${statementSearchCondition.ifname eq 'APPR_TYPE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								결재<br/>타입
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'APPR_TYPE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'APPR_TYPE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
							 </th>
                             <th scope="col" width="4%">
                             	<a onclick="sort('REVIEWNAM_T', '<c:if test="${statementSearchCondition.ifname eq 'REVIEWNAM_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								검토자
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'REVIEWNAM_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'REVIEWNAM_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('CARD_NO', '<c:if test="${statementSearchCondition.ifname eq 'CARD_NO'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								카드<br/>번호
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'CARD_NO' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'CARD_NO' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('CONF_DATE', '<c:if test="${statementSearchCondition.ifname eq 'CONF_DATE'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								카드<br/>사용일
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'CONF_DATE' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'CONF_DATE' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose> 
                             </th>
                             <th scope="col" width="6%">
                             	<a onclick="sort('HKONT_T', '<c:if test="${statementSearchCondition.ifname eq 'HKONT_T'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								계정<br/>과목
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'HKONT_T' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'HKONT_T' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="6%">공급가액</th>
                             <th scope="col" width="6%">
                             	<a onclick="sort('TAX_AMT', '<c:if test="${statementSearchCondition.ifname eq 'TAX_AMT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								세액
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'TAX_AMT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'TAX_AMT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="6%">
                             	<a onclick="sort('DMBTR', '<c:if test="${statementSearchCondition.ifname eq 'DMBTR'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								총액
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'DMBTR' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'DMBTR' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="4%">불공제<br/>여부</th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('NAME', '<c:if test="${statementSearchCondition.ifname eq 'NAME'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								거래처
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'NAME' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'NAME' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('BKTXT', '<c:if test="${statementSearchCondition.ifname eq 'BKTXT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								전표적요
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'BKTXT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'BKTXT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="7%">
                             	<a onclick="sort('ZSEND_TEXT', '<c:if test="${statementSearchCondition.ifname eq 'ZSEND_TEXT'}">${statementSearchCondition.istate}</c:if>');"  href="#a">
								비고
								</a>
								<c:choose>
									<c:when test="${statementSearchCondition.ifname eq 'ZSEND_TEXT' and statementSearchCondition.istate eq 'D'}"><img src="<c:url value='/base/images/icon/ic_tablesort_down.gif'/>"></c:when>
									<c:when test="${statementSearchCondition.ifname eq 'ZSEND_TEXT' and statementSearchCondition.istate eq 'A'}"><img src="<c:url value='/base/images/icon/ic_tablesort_up.gif'/>"></c:when>
								</c:choose>
                             </th>
                             <th scope="col" width="13%">반려사유</th>
                         </tr>
                     </thead>
                     <tbody>
	                     <c:choose>
							<c:when test="${empty statementList}">
								<tr>
									<td colspan="17" class="emptyRecord">
									 요청내역이 없습니다.
									 
									</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach var="statement" items="${statementList}" varStatus="status">
									<tr onmouseover="mouseOver(this);" onmouseout="mouseOut(this);">
										<td>
										<c:if test="${statement.revs=='X'}">
											<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${statement.belnrrd}" />
										</c:if>
										<c:if test="${statement.revs!='X'}">
											<input name="checkboxItem" class="checkbox" title="checkbox" type="checkbox" value="${statement.belnr}" />
										</c:if>
										</td>
										<span onclick="javascript:statementViewPop('${statement.belnr}','${statement.bukrs}','${statement.gjahr}','${statement.belnr2}','${statement.cardno}','${statement.reqnamt}','${statement.dmbtr}','${statement.revs}','${statement.belnrrd}','${statement.belnr2rd}');" style="cursor:pointer;vertical-align:middle;">
										<td>
											<c:choose>
												<c:when test="${statement.bukrs=='S100'}">
													MP
												</c:when>
												<c:when test="${statement.bukrs=='M100'}">
													SP
												</c:when>
												<c:otherwise>
													P&P
												</c:otherwise>
											</c:choose>
										</td>
										<td>${statement.reqdate}</td>
										<td>${statement.reqnamt}
										<c:if test="${statement.revs=='X'}">
											<input id="${statement.belnrrd}_reqnam" type="hidden" value="${statement.reqnam}" />
										</c:if>
										<c:if test="${statement.revs!='X'}">
											<input id="${statement.belnr}_reqnam" type="hidden" value="${statement.reqnam}" />
										</c:if>
										</td>
										<td>
										<c:if test="${statement.revs=='X'}">
											역분개
											<input id="${statement.belnrrd}_revs" type="hidden" value="${statement.revs}" />
										</c:if>
										<c:if test="${statement.revs!='X'}">
											${statement.apprtype}
											<input id="${statement.belnr}_revs" type="hidden" value="${statement.revs}" />
										</c:if>
										</td>
										<td>${statement.reviewnamt}</td>
										<td style="text-align:left;">${statement.cardno}</td>
										<td>${statement.confdate} ${statement.conftime}</td>
										<td style="text-align:left;">${statement.hkontt}</td>
										<td style="text-align:right;">
												<fmt:formatNumber value="${statement.dmbtrds}" />
										</td>
										<td style="text-align:right;">
											<c:if test="${statement.revs=='X'&&statement.taxamt!='0'&&statement.taxamt>0}">-</c:if><fmt:formatNumber value="${statement.taxamt}" />
										</td>
										<td style="text-align:right;">
											<c:if test="${statement.revs=='X'}">-</c:if><fmt:formatNumber value="${statement.dmbtr}" />
										</td>
										<td>
											<c:if test="${!empty statement.belnr2}">Y</c:if>
										</td>
										<td style="text-align:left;">${statement.name}</td>
										<td style="text-align:left;">${statement.bktxt}</td>
										</span>
										<td style="text-align:left;">
										<c:if test="${statement.revs!='X'}">
											${statement.zsendtext}
										</c:if>
										<c:if test="${statement.revs=='X'}">
											${statement.zrevstext}
										</c:if>
										</td>
										<td title="${statement.zreceivetext}">
											<c:if test="${statement.revs=='X'}">
												<input id="${statement.belnrrd}_zreceivetext" type="text" value="${statement.zreceivetext}" style="width:90px;"/>
											</c:if>
											<c:if test="${statement.revs!='X'}">
												<input id="${statement.belnr}_zreceivetext" type="text" value="${statement.zreceivetext}" style="width:90px;"/>
											</c:if>
										</td>
									</tr>
								</c:forEach>
							</c:otherwise>
						</c:choose>	     																														
                     </tbody>
				</table>
				<%-- <spring:bind path="statementSearchCondition.pageIndex">
					<ikep4j:pagination searchFormId="searchItemForm"
						ajaxEventFunctionName="getList"
						pageIndexInput="${status.expression}"
						searchCondition="${statementSearchCondition}" />
					<input name="${status.expression}" type="hidden"
						value="${status.value}"
						title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind> --%>
				  <div class="blockBlank_10px"></div>			
			   	 <div class="blockButton"> 
				        <ul>
				            <li><a class="button" href="javascript:goConfirm('S');"><span>승인</span>
							</a></li>
							<li><a class="button" href="javascript:goConfirm('D');"><span>반려</span>
							</a></li>
				        </ul>
				 </div>
				    <div class="blockBlank_10px"></div>			
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>		
				    <div class="blockBlank_10px"></div>					
			</div>
			<!--//blockListTable End-->	
		</div>
		
	</div>			
	<!--//splitterBox End-->						
</form>
		