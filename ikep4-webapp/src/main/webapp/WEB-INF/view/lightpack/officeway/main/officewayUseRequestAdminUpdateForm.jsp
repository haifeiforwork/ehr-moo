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
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamList.do' />");
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
			
			var tempExceptOfficeWay = "";
			
			<c:forEach var="exceptList" items="${exceptOfficewayList}">
				if($jq("#productNo").val() == "${exceptList.productNo}"){
					if(tempExceptOfficeWay == ""){
						tempExceptOfficeWay = $jq("#productNo").val()+"("+$jq("#productName").val()+")";
					}else{
						tempExceptOfficeWay = tempExceptOfficeWay+","+$jq("#productNo").val()+"("+$jq("#productName").val()+")";
					}
				}
			</c:forEach>
			if(tempExceptOfficeWay != ""){
				alert(tempExceptOfficeWay+"는 신청 불가능한 상품입니다.");
				return;
			}
			
			$jq("#requestForm").trigger("submit"); 
			return false;
		});
		
		$jq("a.btn_cancel").click(function() {
			$jq("#listForm").submit();
		});
		
		
		calculation1 = function(){
			$jq("#price2").val($jq("#price1").val()*$jq("#amount1").val()); 
		};
		
		calculation2 = function(){
			$jq("#price2").val($jq("#amount1").val()*$jq("#price1").val()); 
		};
	});
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestOtherTeamList.do'/>" ></form>
			<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestAdminUpdate.do'/>" >
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop">
					<div class="tableTop_bgR"></div>
					<h2>사무 용품 신청</h2>
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
							<tr>
								<td style="text-align:center;">${regDt}</td>
								<td style="border-left:1px solid #e0e0e0;"><input name="productName" id="productName" title="" class="inputbox w100" type="text" value="${officeway.productName}" /></td>
								<td style="border-left:1px solid #e0e0e0;"><input name="productNo" id="productNo" title="" class="inputbox w90" type="text" value="${officeway.productNo}" /></td>
								<td style="border-left:1px solid #e0e0e0;">
									<select id="category2" name="category2">
										<c:forEach var="code" items="${categoryList2}">
											<option value="${code.categoryId}" <c:if test="${officeway.category2 eq code.categoryId}">selected="selected"</c:if>>${code.categoryName}</option>
										</c:forEach>
									</select>
								</td>
								<td style="border-left:1px solid #e0e0e0;"><input name="amount1" id="amount1" title="" class="inputbox w90" type="text" value="${officeway.amount1}" onblur="calculation1();" onkeyup="calculation1();" /></td>
								<td style="border-left:1px solid #e0e0e0;text-align:center;"><input name="unit" id="unit" title="" class="inputbox w90" type="hidden" value="${officeway.unit}" />EA</td>
								<td style="border-left:1px solid #e0e0e0;"><input name="price1" id="price1" title="" class="inputbox w90" type="text" value="${officeway.price1}" onblur="calculation2();" onkeyup="calculation2();" /></td>
								<td style="border-left:1px solid #e0e0e0;"><input name="price2" id="price2" title="" class="inputbox w90" type="text" value="${officeway.price2}" readonly="readonly" /></td>
								<td style="border-left:1px solid #e0e0e0;"><input name="remark" id="remark" title="" class="inputbox w90" type="text" value="${officeway.remark}" /></td>
								<td class="textCenter" style="border-left:1px solid #e0e0e0;">
									${officeway.registerName}
									<input name="officewayId" id="officewayId" type="hidden" value="${officeway.officewayId}" />
								</td>
							</tr>
					</tbody>
				</table>
					<div class="nblockButton"> 
							<a class="button_img01 btn_save" href="#a"><span>저장</span></a>
							<a class="button_img01 btn_cancel" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a>
					</div>	
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
