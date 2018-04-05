<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"
        codebase="<c:url value="/Bin/smsx.cab#Version=6,5,439,72"/>">
</object>
<script type="text/javascript" language="javascript">
//<!--
	var dialogWindow;

	function fnCaller(param, dialog){
	
		dialogWindow = dialog;
	}

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		
		$jq("#searchForm").attr("action", "<c:url value='/lightpack/overtimework/overtimeworkInOutMyList.do' />");
		$jq("#searchForm")[0].submit();
	}
	

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(overtimeworkId) {
		
		$jq("#overtimeworkId").val(overtimeworkId);
		
		var url = "<c:url value='/lightpack/overtimework/overtimeworkUseRequestView.do' />";
		
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
		
		$jq("#checkboxAllItem").click(function() { 
			$jq("#searchForm input[name=checkboxItem]").attr("checked", $jq(this).is(":checked"));  
		});  
		
		$jq("#saveButton").click(function() {
				
			var tempItemId = "";
			var tempCnt = 0;
			$jq("#searchForm input[name=checkboxItem]:checked").each(function(index) { 
				
				if($jq("#overtimework_"+$jq(this).val()).val() == "T"){
					if(tempItemId == ""){
						tempItemId = $jq(this).val(); 
					}else{
						tempItemId = tempItemId+"|"+$jq(this).val(); 
					}
				}
				tempCnt++;
			});		
			
		
			if(tempCnt < 1){
				alert("사무용품을 선택해주세요.");
				return;
			}
			
			if(tempItemId == ""){
				alert("이미 신청된 사무용품입니다.");
				return;
			}
			$jq('input[name=overtimeworkId]').val(tempItemId);
			$jq('input[name=status1]').val("S");
			if(confirm("선택한 사무용품을 신청하시겠습니까?")) {
				$jq("#regForm").attr("action", "<c:url value='/lightpack/overtimework/overtimeworkCheckBoxUseRequest.do' />");
				$jq("#regForm")[0].submit();
			}  
		});
		
		$jq("#deleteButton").click(function() {  
			var tempItemId = "";
			var tempCnt = 0;
			$jq("#searchForm input[name=checkboxItem]:checked").each(function(index) { 
				
				if(tempItemId == ""){
					tempItemId = $jq(this).val(); 
				}else{
					tempItemId = tempItemId+"|"+$jq(this).val(); 
				}
				tempCnt++;
			});	 
			
			if(tempCnt < 1){
				alert("사무용품을 선택해주세요.");
				return;
			}
			$jq('input[name=overtimeworkId]').val(tempItemId);
			
			if(confirm("선택한 신청건을 삭제하시겠습니까?")) {
				$jq("#searchForm").ajaxLoadStart(); 
				
				$jq.ajax({
					url : "<c:url value='/lightpack/overtimework/overtimeworkRequestDelete.do' />",
					data : $jq("#searchForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("사무용품신청이 삭제되었습니다.");
							getList();
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
				
			}  
		});   
		
		$jq("#searchForm select[name=workPlaceName]").change(function() { 
			var workPlaceName = $jq("#searchForm select[name=workPlaceName]").val();
			
			if(workPlaceName == ""){
				$jq("#teamCode").empty();
				var teamCode = "<option value=''>" + "<ikep4j:message key="message.collpack.kms.admin.permission.team.leader.info.add.teamCode" />" +"</option>";
				$jq("#teamCode").append(teamCode);
			}else{
				$jq("#searchForm").attr("action", "<c:url value='/lightpack/overtimework/overtimeworkInOutMyList.do' />");
				$jq("#searchForm")[0].submit();
				return false;
			}
		}); 
		
		$jq("#searchForm select[name=teamCode]").change(function() { 
			$jq("#teamName").val($jq("#teamCode option:selected").text());
			$jq("#searchForm").attr("action", "<c:url value='/lightpack/overtimework/overtimeworkInOutMyList.do' />");
			$jq("#searchForm")[0].submit();
			return false;
		});
		
		$jq("#excelButton").click(function() { 
			if(${searchResult.emptyRecord}){
				alert("검색 결과가 없습니다.");
				return false;
			}
			$jq("#searchForm").attr("action","<c:url value='/lightpack/overtimework/downloadExcelOvertimeworkInOutMyList.do'/>");
			$jq("#searchForm")[0].submit();
		return false; 
		});
		
		$jq("#createPrintButton").click(function() {    

			var url = iKEP.getContextRoot() + '/lightpack/overtimework/overtimeworkInOutMyListPrint.do';
			
			iKEP.popupOpen(url, {width:800, height:560}, "overtimeworkPrint");
		});
	});
	
	function printWindow() 
	{
		//factory.printing.paperSize = "A4"
	    factory.printing.header = "" //머릿글
		factory.printing.footer = getTimeStamp()+" ${user.teamName} ${user.userName}" //바닥글
		factory.printing.portrait = false //true는 세로 출력, false는 가로 출력
		factory.printing.leftMargin = 5.33 //왼쪽 여백
		factory.printing.topMargin = 5.23 //위쪽 여백
		factory.printing.rightMargin = 5.33 //오른쪽 여백
		factory.printing.bottomMargin = 4.23 //바닥 여백	
		
		//factory.printing.Print(true, window) //true는 표시함, false는 프린트 대화 상자표지 안함, window는 전체 페이지 출력
		factory.printing.Preview();
		//setTimeout("window.close()", 100);
		setTimeout("javascript:iKEP.closePop()", 100);

	}		
	
	window.onload = function()
	{
		printWindow();
	}
	
	function getTimeStamp() {
		 var d = new Date();
		 
		  var s =
		  leadingZeros(d.getFullYear(), 4) + '-' +
		  leadingZeros(d.getMonth() + 1, 2) + '-' +
		  leadingZeros(d.getDate(), 2) + ' ' +
		  
		  leadingZeros(d.getHours(), 2) + ':' +
		  leadingZeros(d.getMinutes(), 2) + ':' +
		  leadingZeros(d.getSeconds(), 2);
		 return s;
		}

		function leadingZeros(n, digits) {
		 var zero = '';
		 n = n.toString();
		 
		 if (n.length < digits) {
		  for (i = 0; i < digits - n.length; i++)
		  zero += '0';
		 }
		 return zero + n;
		}
//-->
</script>

<div id="reserveDiv">

	<!--blockListTable Start-->
	<div class="blockListTable">
		<div id="listDiv">
			<form id="regForm" name="regForm" method="post" action="">
				<input type="hidden" name="status1" value=""/> 
				<input type="hidden" name="overtimeworkId" value=""/> 
			</form>
			<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
				<input type="hidden" id="overtimeworkId" name="overtimeworkId"/>
				
				<spring:bind path="searchCondition.sortColumn">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				<spring:bind path="searchCondition.sortType">
					<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
				</spring:bind>
				
				<!--tableTop Start-->  
				<div class="tableTop" style="padding:3px;">
					<div class="tableTop_bgR"></div>
					<h2>출입이력 조회</h2>
					<div class="clear"></div>	
				</div>
				<!--//tableTop End-->	
				<div class="listInfo"> 
						<%-- <spring:bind path="searchCondition.pagePerRecord">  
							<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
								<c:forEach var="code" items="${boardCode.pageNumList}">
									<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
								</c:forEach> 
							</select> 
						</spring:bind> --%>
						<div class="totalNum">
							<ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>
						</div>
					</div>
				<table summary="<ikep4j:message pre='${preDetail}' key='myReserveList' />">
					<caption></caption>
						<colgroup>
							<col width="5%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
							<col width="10%" />
							<col width="10%" />
							<col width="10%" />
							<col width="9%" />
							<col width="9%" />
							<col width="9%" />
						</colgroup>
					<thead>
						<tr>
							<th scope="col" style="border-left:1px solid #e0e0e0;">NO</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">일자</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">시간</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">구분</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">소속</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">사업장</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">부서</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">성명</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">근무자 확인</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">확인일</th>
							<th scope="col" style="border-left:1px solid #e0e0e0;">확인시간</th>
						</tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${searchResult.emptyRecord}">
								<tr>
									<td colspan="11" class="emptyRecord">
										<ikep4j:message pre='${preSearch}' key='emptyRecord' />
									</td> 
								</tr>				        
						    </c:when>
						    <c:otherwise>
								<c:forEach var="overtimework" items="${searchResult.entity}" varStatus="status">
									<input type="hidden" id="overtimework_${overtimework.overtimeworkId}" value="${overtimework.status1}"/>
									<tr>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${overtimework.num}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<fmt:formatDate value="${overtimework.registDate}" pattern="yyyy-MM-dd" />
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<fmt:formatDate value="${overtimework.registDate}" pattern="HH:mm:ss" />
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<c:if test="${overtimework.inoutFlag=='I'}">Office In</c:if>
											<c:if test="${overtimework.inoutFlag=='O'}">Office Out</c:if>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${overtimework.companyCodeName}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${overtimework.workPlaceName}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${overtimework.groupName}
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											${overtimework.userName} <c:if test="${!empty overtimework.jobDutyName}">${overtimework.jobDutyName}</c:if><c:if test="${empty overtimework.jobDutyName}">${overtimework.jobTitleName}</c:if>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<c:if test="${overtimework.checkFlag=='Y'}">확인</c:if>
											<c:if test="${overtimework.checkFlag=='N'}">미확인</c:if>
											<c:if test="${overtimework.checkFlag=='R'}">추후확인</c:if>
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;">
											<fmt:formatDate value="${overtimework.checkDate}" pattern="yyyy-MM-dd" />
										</td>
										<td class="textCenter" style="border-left:1px solid #e0e0e0;border-right:1px solid #e0e0e0;">
											<fmt:formatDate value="${overtimework.checkDate}" pattern="HH:mm:ss" />
										</td>
									</tr>
								</c:forEach>
						    </c:otherwise>
						</c:choose>
					</tbody>
				</table>
				
				<!--Page Number Start--> 
				<!--//Page Number End-->
				 <div class="blockBlank_10px"></div>
				<input type="hidden" name="tempEquipmentId" id="tempEquipmentId" value="" />
			</form>
		</div>
	</div>
	<!--//blockListTable End-->
</div>
