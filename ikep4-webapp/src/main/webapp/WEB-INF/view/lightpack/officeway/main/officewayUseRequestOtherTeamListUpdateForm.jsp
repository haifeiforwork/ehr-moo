<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="officewayCnt" value="0"/>
<c:forEach var="officeway" items="${searchResult.entity}" varStatus="status">
<c:set var="officewayCnt" value="${officewayCnt+1}"></c:set>
</c:forEach>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;
	var realtyCnt = "${officewayCnt}";

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamList.do?teamId=${teamId}' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(officewayId) {
		
		$jq("#officewayId").val(officewayId);
		
		var url = "<c:url value='/lightpack/officeway/officewayUseRequestView.do' />";
		
		$jq.ajax({     
			
			url : url,    
			data : $jq("#searchForm").serialize(),
			type : "post",     
			loadingElement : {
				
				container : "#reserveDiv"
			},
			success : function(result) {       
				
				$jq("#reserveDiv").html(result);
			},
			error : function(event, request, settings) { 
				
				alert("error"); 
			}
		}); 
	}
	
	// 소팅을 위한 함수
	function sort(sortColumn, sortType) {
		
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			
			$jq("input[name=sortType]").val('DESC');	
		} else {
			
			$jq("input[name=sortType]").val('ASC');
		}
		
		getList();
	};
	
	// 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
	$jq(document).ready(function() {
		//left menu setting
		//$jq("#callManageOfLeft").click();
		
		$jq("#newFormButton").click(function() {
			
		});
		
		$jq("#deleteButton").click(function() {
			
			deleteForm();
		});
		
		$jq("#searchBoardItemButton").click(function() {  
			
			getList();
		});
		
		// 백스페이스 방지(input, select 제외)
		$jq(document).keydown(function(e) {
			
			var element = e.target.nodeName.toLowerCase();
			
			if (element != 'input') {
				
			    if (e.keyCode === 8) {
			    	
			        return false;
			    }
			}
		});
		
		$jq("#startPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("#endPeriod").datepicker().next().eq(0).click(function() { 
			
			$jq(this).prev().eq(0).datepicker("show"); 
		});
		
		$jq("a.btn_save").click(function(){
			var tempproductName = "";    
			var tempproductNo = ""; 
			//var tempcategory1 = ""; 
			var tempcategory2 = "";
			var tempremark = "";
			var tempamount1 = ""; 
			var tempunit = "";
			var tempprice1 = "";    
			var tempprice2 = ""; 
			var productName = ""; 
			var productNo = "";
			//var category1 = ""; 
			var category2 = ""; 
			var remark = ""; 
			var amount1 = ""; 
			var unit = "";
			var price1 = "";    
			var price2 = ""; 
			var tempofficewayId = "";
			var officewayId = "";
			
			var tempExceptOfficeWay = "";
			
			<c:forEach var="exceptList" items="${exceptOfficewayList}">
				for(i=0;i<realtyCnt;i++){
					if($jq("#productNo"+i).val() == "${exceptList.productNo}"){
						if(tempExceptOfficeWay == ""){
							tempExceptOfficeWay = $jq("#productNo"+i).val()+"("+$jq("#productName"+i).val()+")";
						}else{
							tempExceptOfficeWay = tempExceptOfficeWay+","+$jq("#productNo"+i).val()+"("+$jq("#productName"+i).val()+")";
						}
					}
				}
			</c:forEach>
			if(tempExceptOfficeWay != ""){
				alert(tempExceptOfficeWay+"는 신청 불가능한 상품입니다.");
				return;
			}
			
			for(i=0;i<realtyCnt;i++){
				tempproductName = $jq("#productName"+i).val();  
				tempproductNo = $jq("#productNo"+i).val();   
				tempcategory = $jq("#category"+i).val();    
				tempamount1 = $jq("#amount1"+i).val();     
				tempunit = $jq("#unit"+i).val();  
				tempprice1 = $jq("#price1"+i).val();  
				tempprice2 = $jq("#price2"+i).val(); 
				//tempcategory1 = $jq("#category1"+i).val();//jQuery("#category1"+i+ "option:selected").text();
				tempcategory2 = $jq("#category2"+i).val();//jQuery("#category2"+i+ "option:selected").text();
				tempremark = $jq("#remark"+i).val();
				if(tempremark == ""){
					tempremark = "N/A";
				}
				tempofficewayId = $jq("#officewayId"+i).val();
				if(tempofficewayId == ""){
					tempofficewayId = "newcode";
				}
				if(i == 0){
					productName = tempproductName;
					productNo = tempproductNo;
					//category1 = tempcategory1;
					category2 = tempcategory2;
					remark = tempremark;
					amount1 = tempamount1;
					unit = tempunit;
					price1 = tempprice1;
					price2 = tempprice2;
					officewayId = tempofficewayId;
				}else{
					productName = productName+"|"+tempproductName;
					productNo = productNo+"|"+tempproductNo;
					//category1 = category1+"|"+tempcategory1;
					category2 = category2+"|"+tempcategory2;
					remark = remark+"|"+tempremark;
					amount1 = amount1+"|"+tempamount1;
					unit = unit+"|"+tempunit;
					price1 = price1+"|"+tempprice1;
					price2 = price2+"|"+tempprice2;
					officewayId = officewayId+"|"+tempofficewayId;
				}
			}
		
			$jq('input[name=productName]').val(productName); 
			$jq('input[name=productNo]').val(productNo);  
			//$jq('input[name=category1]').val(category1);    
			$jq('input[name=category2]').val(category2);    
			$jq('input[name=remark]').val(remark);   
			$jq('input[name=amount1]').val(amount1);    
			$jq('input[name=unit]').val(unit); 
			$jq('input[name=price1]').val(price1);   
			$jq('input[name=price2]').val(price2);
			$jq('input[name=officewayId]').val(officewayId);
			$jq('input[name=status1]').val("S");
			$jq("#requestForm").trigger("submit"); 
			return false;
		});
		
		$jq("a.btn_confirm").click(function(){
			var tempproductName = "";    
			var tempproductNo = ""; 
			//var tempcategory1 = ""; 
			var tempcategory2 = "";
			var tempremark = "";
			var tempamount1 = ""; 
			var tempunit = "";
			var tempprice1 = "";    
			var tempprice2 = ""; 
			var productName = ""; 
			var productNo = "";
			//var category1 = ""; 
			var category2 = ""; 
			var remark = ""; 
			var amount1 = ""; 
			var unit = "";
			var price1 = "";    
			var price2 = ""; 
			var tempofficewayId = "";
			var officewayId = "";
			
			var tempExceptOfficeWay = "";
			
			<c:forEach var="exceptList" items="${exceptOfficewayList}">
				for(i=0;i<realtyCnt;i++){
					if($jq("#productNo"+i).val() == "${exceptList.productNo}"){
						if(tempExceptOfficeWay == ""){
							tempExceptOfficeWay = $jq("#productNo"+i).val()+"("+$jq("#productName"+i).val()+")";
						}else{
							tempExceptOfficeWay = tempExceptOfficeWay+","+$jq("#productNo"+i).val()+"("+$jq("#productName"+i).val()+")";
						}
					}
				}
			</c:forEach>
			if(tempExceptOfficeWay != ""){
				alert(tempExceptOfficeWay+"는 신청 불가능한 상품입니다.");
				return;
			}
			
			for(i=0;i<realtyCnt;i++){
				tempproductName = $jq("#productName"+i).val();  
				tempproductNo = $jq("#productNo"+i).val();   
				tempcategory = $jq("#category"+i).val();    
				tempamount1 = $jq("#amount1"+i).val();     
				tempunit = $jq("#unit"+i).val();  
				tempprice1 = $jq("#price1"+i).val();  
				tempprice2 = $jq("#price2"+i).val(); 
				//tempcategory1 = $jq("#category1"+i).val();//jQuery("#category1"+i+ "option:selected").text();
				tempcategory2 = $jq("#category2"+i).val();//jQuery("#category2"+i+ "option:selected").text();
				tempremark = $jq("#remark"+i).val();
				if(tempremark == ""){
					tempremark = "N/A";
				}
				tempofficewayId = $jq("#officewayId"+i).val();
				if(tempofficewayId == ""){
					tempofficewayId = "newcode";
				}
				if(i == 0){
					productName = tempproductName;
					productNo = tempproductNo;
					//category1 = tempcategory1;
					category2 = tempcategory2;
					remark = tempremark;
					amount1 = tempamount1;
					unit = tempunit;
					price1 = tempprice1;
					price2 = tempprice2;
					officewayId = tempofficewayId;
				}else{
					productName = productName+"|"+tempproductName;
					productNo = productNo+"|"+tempproductNo;
					//category1 = category1+"|"+tempcategory1;
					category2 = category2+"|"+tempcategory2;
					remark = remark+"|"+tempremark;
					amount1 = amount1+"|"+tempamount1;
					unit = unit+"|"+tempunit;
					price1 = price1+"|"+tempprice1;
					price2 = price2+"|"+tempprice2;
					officewayId = officewayId+"|"+tempofficewayId;
				}
			}
		
			$jq('input[name=productName]').val(productName); 
			$jq('input[name=productNo]').val(productNo);  
			//$jq('input[name=category1]').val(category1);    
			$jq('input[name=category2]').val(category2);    
			$jq('input[name=remark]').val(remark);   
			$jq('input[name=amount1]').val(amount1);    
			$jq('input[name=unit]').val(unit); 
			$jq('input[name=price1]').val(price1);   
			$jq('input[name=price2]').val(price2);
			$jq('input[name=officewayId]').val(officewayId);
			$jq('input[name=status1]').val("C");
			$jq("#requestForm").trigger("submit"); 
			return false;
		});
		$jq("a.btn_cancel").click(function() {
			$jq("#listForm").submit();
		});
		
		
		$jq("a.realtyPlus").click(function(){	
			$jq("#realtyBody").append(
					"<tr>"+
					"<td style=\"text-align:center;\">${today}</td>"+
					//"<td style=\"border-left:1px solid #e0e0e0;\"><select name=\"category1"+realtyCnt+"\" id=\"category1"+realtyCnt+"\">"+
					//<c:forEach var="code" items="${categoryList1}">
					//"<option value=${code.categoryId}>${code.categoryName}</option>"+
					//</c:forEach>
					//"</select></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"productName"+realtyCnt+"\" id=\"productName"+realtyCnt+"\"  class=\"inputbox w100\" type=\"text\"  /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"productNo"+realtyCnt+"\" id=\"productNo"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><select name=\"category2"+realtyCnt+"\" id=\"category2"+realtyCnt+"\">"+
					<c:forEach var="code" items="${categoryList2}">
					"<option value=${code.categoryId}>${code.categoryName}</option>"+
					</c:forEach>
					"</select></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"amount1"+realtyCnt+"\" id=\"amount1"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" onblur=\"calculation1("+realtyCnt+")\" onkeyup=\"calculation1("+realtyCnt+")\" /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;text-align:center;\"><input name=\"unit"+realtyCnt+"\" id=\"unit"+realtyCnt+"\"  class=\"inputbox w90\" type=\"hidden\" value=\"EA\" readonly=\"readonly\" />EA</td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"price1"+realtyCnt+"\" id=\"price1"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" onblur=\"calculation2("+realtyCnt+")\" onkeyup=\"calculation2("+realtyCnt+")\" /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"price2"+realtyCnt+"\" id=\"price2"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" readonly=\"readonly\" /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\"><input name=\"remark"+realtyCnt+"\" id=\"remark"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
					"<td style=\"border-left:1px solid #e0e0e0;\">${user.userName}</td>"+
				"</tr><input name=\"officewayId"+realtyCnt+"\" id=\"officewayId"+realtyCnt+"\"  type=\"hidden\"  />"
			);
			
			realtyCnt++;
			iKEP.iFrameContentResize();
		});
		
		calculation1 = function(code){
			$jq("#price2"+code).val($jq("#price1"+code).val()*$jq("#amount1"+code).val()); 
		};
		
		calculation2 = function(code){
			$jq("#price2"+code).val($jq("#amount1"+code).val()*$jq("#price1"+code).val()); 
		};
	});
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamListPayment.do'/>" ><input type="hidden" name="teamId" value="${teamId}"/> </form>
			<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamUpdate.do'/>" >
				<input type="hidden" id="officewayId" name="officewayId"/>
				<input type="hidden" name="productName" value=""/>    
				<input type="hidden" name="productNo" value=""/> 
				<!-- <input type="hidden" name="category1" value=""/>  -->
				<input type="hidden" name="category2" value=""/> 
				<input type="hidden" name="remark" value=""/> 
				<input type="hidden" name="amount1" value=""/> 
				<input type="hidden" name="unit" value=""/> 
				<input type="hidden" name="price1" value=""/>    
				<input type="hidden" name="price2" value=""/> 
				<input type="hidden" name="status1" value=""/> 
				<input type="hidden" name="teamId" value="${teamId}"/> 
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>팀 신청 현황</h2>
					<div class="listInfo"> 
						<%-- <spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind> --%>
						<%-- <div class="totalNum">
							${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
							( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
						</div> --%>
					</div>
					<%-- <div class="tableSearch"> 
						<input id="startPeriod" name="startPeriod" type="text" class="inputbox datepicker" value="${startPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" />
						~
						<input id="endPeriod" name="endPeriod" type="text" class="inputbox datepicker" value="${endPeriod}" size="10" title="<ikep4j:message pre='${preDetail}' key='date' />" readonly="readonly" /> 
						<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="<ikep4j:message pre='${preDetail}' key='calendar' />" /> --%>
						<%-- <spring:bind path="searchCondition.searchType">  
							<select name="${status.expression}" title="<ikep4j:message pre='${preDetail}' key='classification' />">
								<option value="ALL" <c:if test="${'ALL' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='all' />
								</option>
								<option value="PERS" <c:if test="${'PERS' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='directReserve' />
								</option>
								<option value="PART" <c:if test="${'PART' eq status.value}">selected="selected"</c:if> >
									<ikep4j:message pre='${preDetail}' key='relationReserve' />
								</option>
							</select>	
						</spring:bind> --%>
						<%-- <a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
							<span><ikep4j:message pre='${preSearch}' key='search' /></span>
						</a>
					</div> --%>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				
				<table summary="<ikep4j:message pre='${preDetail}' key='myReserveList' />">
					<caption></caption>
						<colgroup>
							<col width="6%" />
							<!-- <col width="14%" /> -->
							<col width="*" />
							<col width="7%" />
							<col width="10%" />
							<col width="7%" />
							<col width="6%" />
							<col width="6%" />
							<col width="6%" />
							<col width="10%" />
							<col width="7%" />
						</colgroup>
					<thead>
						<tr>
							<th style="text-align:center;">등록월</th>
							<!-- <th style="text-align:center;border-left:1px solid #e0e0e0;">분류</th> -->
							<th style="text-align:center;border-left:1px solid #e0e0e0;">품목</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">제품번호</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">사유</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">수량</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">단위</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">단가</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">금액</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">비고</th>
							<th style="text-align:center;border-left:1px solid #e0e0e0;">사용자</th>
						</tr>
					</thead>
					<tbody id="realtyBody">
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="10" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="officeway" items="${searchResult.entity}" varStatus="status">
									<tr>
										<td style="text-align:center;"><fmt:formatDate value="${officeway.registDate}" pattern="yyyy.MM"/></td>
										<%-- <td style="border-left:1px solid #e0e0e0;">
											<select id="category1${status.index}" name="category1${status.index}">
												<c:forEach var="code" items="${categoryList1}">
													<option value="${code.categoryId}" <c:if test="${officeway.category1 eq code.categoryId}">selected="selected"</c:if>>${code.categoryName}</option>
												</c:forEach>
											</select>
										</td> --%>
										<td style="border-left:1px solid #e0e0e0;"><input name="productName${status.index}" id="productName${status.index}" title="" class="inputbox w100" type="text" value="${officeway.productName}" /></td>
										<td style="border-left:1px solid #e0e0e0;"><input name="productNo${status.index}" id="productNo${status.index}" title="" class="inputbox w90" type="text" value="${officeway.productNo}" /></td>
										<td style="border-left:1px solid #e0e0e0;">
											<select id="category2${status.index}" name="category2${status.index}">
												<c:forEach var="code" items="${categoryList2}">
													<option value="${code.categoryId}" <c:if test="${officeway.category2 eq code.categoryId}">selected="selected"</c:if>>${code.categoryName}</option>
												</c:forEach>
											</select>
										</td>
										<td style="border-left:1px solid #e0e0e0;"><input name="amount1${status.index}" id="amount1${status.index}" title="" class="inputbox w90" type="text" value="${officeway.amount1}" onblur="calculation1(${status.index});" onkeyup="calculation1(${status.index});"/></td>
										<td style="border-left:1px solid #e0e0e0;text-align:center;"><input name="unit${status.index}" id="unit${status.index}" title="" class="inputbox w90" type="hidden" value="${officeway.unit}" />EA</td>
										<td style="border-left:1px solid #e0e0e0;"><input name="price1${status.index}" id="price1${status.index}" title="" class="inputbox w90" type="text" value="${officeway.price1}" onblur="calculation2(${status.index});" onkeyup="calculation2(${status.index});"/></td>
										<td style="border-left:1px solid #e0e0e0;"><input name="price2${status.index}" id="price2${status.index}" title="" class="inputbox w90" type="text" value="${officeway.price2}" readonly="readonly"/></td>
										<td style="border-left:1px solid #e0e0e0;"><input name="remark${status.index}" id="remark${status.index}" title="" class="inputbox w90" type="text" value="${officeway.remark}" /></td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${officeway.registerName}
											<input name="officewayId${status.index}" id="officewayId${status.index}" type="hidden" value="${officeway.officewayId}" />
										</td>
									</tr>
								</c:forEach>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<table><tr><td style="text-align:right;border:0 0 0 0;">
		<div class="nblockButton"> 
			<a class="button_img05 realtyPlus" href="#a"><span>품목추가</span></a>
	</div>	
	</td></tr></table>
					<div class="nblockButton"> 
							<a class="button_img01 btn_save" href="#a"><span>저장</span></a>
							<a class="button_img01 btn_cancel" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a>
					</div>	
	
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
