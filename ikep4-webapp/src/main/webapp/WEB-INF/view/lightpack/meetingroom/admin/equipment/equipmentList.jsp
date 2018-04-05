<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<%-- 메시지 관련 Prefix 선언 End --%>

<script type="text/javascript" language="javascript">
//<!--

	// 상단에 보이게 될 리스트를 가져오는 함수
	function getList() {
		$jq("#searchForm").attr("action", "<c:url value='getEquipmentList.do' />");
		$jq("#searchForm")[0].submit();
	}

	// 하단에 보이게 될 상세정보를 가져오는 함수
	function getView(equipmentId, fieldName, itemTypeCode, tr) {

		var selectClassName = "bgSelected";
		$jq(tr).parent().parent().children().removeClass(selectClassName).end()
				.end().end().addClass(selectClassName);

		$jq.ajax({
			url : '<c:url value="getEquipmentForm.do" />',
			data : {
				equipmentId : equipmentId, 
				fieldName : fieldName,
				itemTypeCode : itemTypeCode
			},
			type : "get",
			success : function(result) {
				$jq("#viewDiv").html(result);
			}
		});
	}
	
	// 코드를 삭제함
	function deleteForm() {
		if ($jq("#equipmentId").val() == "") {
			alert("<ikep4j:message pre='${preMessage}' key='noDelete'/>");
			return;
		}
		
		if(confirm("<ikep4j:message pre='${preMessage}' key='delete'/>")) {
			$jq.ajax({
				url : '<c:url value="deleteEquipment.do" />',
				data : $jq("#equipment").serialize(),
				type : "post",
				success : function(result) {
					alert("<ikep4j:message pre='${preMessage}' key='deleteComplete'/>");
					getList();
				},
				error:function(){
					alert("<ikep4j:message pre='${preMessage}' key='deleteFail'/>");
				}
			});
		} else {
			return;
		}
	}
	
	// 순서를 위로
	function setUp(equipmentId, sortOrder) {
		$jq.ajax({     
			url : '<c:url value="goUp.do" />',     
			data : {equipmentId: equipmentId, sortOrder: sortOrder},     
			type : "post",     
			success : function(result) {
				getList();
			} 
		});  
	}
	
	// 순서를 아래로
	function setDown(equipmentId, sortOrder) {
		
		$jq.ajax({     
			url : '<c:url value="goDown.do" />',     
			data : {equipmentId: equipmentId, sortOrder: sortOrder},     
			type : "post",     
			success : function(result) {         
				getList();
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
		$jq("#callManageOfLeft").click();
		
		
		// 페이지 로딩 직후 폼의 맨 첫번째 input 박스에 포커스
		$jq("#equipment :input:visible:enabled:first").focus().select();
		
		$jq("#newFormButton").click(function() {
			$jq("#status").val("");
			getView();
		});
		
		$jq("#submitButton").click(function() {
			$jq("#equipment").trigger("submit");
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
		
		var validOptions = {
			rules : {
				equipmentName : {
					required : true,
					rangelength : [0, 20]
				},
				equipmentEnglishName : {
					required : true,
					terminology : true,
					rangelength : [0, 100]										
				}
		    },
		    messages : {
		    	equipmentName : {
		    		required : '<ikep4j:message key="NotNull.Equipment.equipmentName" />',
					rangelength : '<ikep4j:message key="Size.Equipment.equipmentName" />'
				},
				equipmentEnglishName : {
					required : '<ikep4j:message key="NotNull.Equipment.equipmentEnglishName" />',
					terminology : '<ikep4j:message key="Terminology.Equipment.equipmentEnglishName" />',
					rangelength : '<ikep4j:message key="Size.Equipment.equipmentEnglishName" />'
				}
		    },
		    submitHandler : function(equipment) {
		    	
		    	// 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
				if (($jq("#status").val() == 'success') || ($jq("#status").val() == 'modify')) {
					$jq.ajax({
						url : '<c:url value="createEquipment.do" />',
						data : $jq("#equipment").serialize(),
						type : "post",
						success : function(result) {
							alert("<ikep4j:message pre='${preMessage}' key='saveComplete'/>");
							$jq("#tempEquipmentId").val(result);
							getList();
						},
							error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
						}
					});
				} else {
					alert("<ikep4j:message pre='${preMessage}' key='saveFail'/>");
					return;
				}
			}

		 };

		var validator = new iKEP.Validator("#equipment", validOptions);
		
	});
//-->
</script>

<!--blockListTable Start-->
<div class="blockListTable">
	<div id="listDiv">
		<form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">

			<spring:bind path="searchCondition.sortColumn">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			<spring:bind path="searchCondition.sortType">
				<input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind>
			
			<!--tableTop Start-->  
			<div class="tableTop">
				<div class="tableTop_bgR"></div>
				<h2><ikep4j:message pre='${preHeader}' key='equipment'/></h2>
				<div class="listInfo"> 
					<spring:bind path="searchCondition.pagePerRecord">  
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
							<c:forEach var="code" items="${boardCode.pageNumList}">
								<option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
							</c:forEach> 
						</select> 
					</spring:bind>
					<div class="totalNum">
						${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
						( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
					</div>
				</div>
				<div class="tableSearch"> 
					<spring:bind path="searchCondition.searchColumn">  
						<select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
							<option value="title" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >
								<ikep4j:message pre='${preDetail}' key='equipmentName'/>
							</option>
						</select>	
					</spring:bind>		
					<spring:bind path="searchCondition.searchWord">  					
						<input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
					</spring:bind>
					<a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
						<span><ikep4j:message pre='${preSearch}' key='search' /></span>
					</a>
				</div>
				<div class="clear"></div>	
			</div>
			<!--//tableTop End-->	
			
			<table summary="<ikep4j:message pre='${preHeader}' key='equipment'/>">
				<caption></caption>
					<colgroup>
						<col width="10%"/>
						<col width="40%"/>
						<col width="40%"/>
						<col width="10%"/>
					</colgroup>
				<thead>
					<tr>
						<th scope="col">
							<a onclick="javascript:sort('SORT_ORDER', '<c:if test="${searchCondition.sortColumn eq 'SORT_ORDER'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDetail}' key='sortNum'/>
							</a>
						</th>
						<th scope="col">
							<a onclick="javascript:sort('EQUIPMENT_NAME', '<c:if test="${searchCondition.sortColumn eq 'EQUIPMENT_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
								<ikep4j:message pre='${preDetail}' key='equipmentName'/>
							</a>
						</th>
						<th scope="col">
							<a onclick="javascript:sort('EQUIPMENT_ENGLISH_NAME', '<c:if test="${searchCondition.sortColumn eq 'EQUIPMENT_ENGLISH_NAME'}">${searchCondition.sortType}</c:if>');"  href="#a">
							 	<ikep4j:message pre='${preDetail}' key='equipmentEnglishName'/>
							</a>
						</th>
						<th scope="col">
							<ikep4j:message pre='${preDetail}' key='sortOrder'/>
						</th>
					</tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${searchResult.emptyRecord}">
							<tr>
								<td colspan="4" class="emptyRecord">
									<ikep4j:message pre='${preSearch}' key='emptyRecord' />
								</td> 
							</tr>				        
					    </c:when>
					    <c:otherwise>
							<c:forEach var="equipment" items="${searchResult.entity}" varStatus="status">
								<tr>
									<td class="textCenter">${equipment.num}</td>
									<td class="textLeft"><a href="#a" onclick="getView('${equipment.equipmentId}', 'equipmentName', 'PO', this)">${equipment.equipmentName}</a></td>
									<td class="textLeft"><a href="#a" onclick="getView('${equipment.equipmentId}', 'equipmentName', 'PO', this)">${equipment.equipmentEnglishName}</a></td>
									<td class="textCenter">
										<a href="#" onclick="setUp('${equipment.equipmentId}', '${equipment.sortOrder}')">
											<img src="<c:url value='/base/images/icon/ic_tablesort_up.gif' />" alt="UP" />
										</a>
										<a href="#" onclick="setDown('${equipment.equipmentId}', '${equipment.sortOrder}')">
											<img src="<c:url value='/base/images/icon/ic_tablesort_down.gif' />" alt="DOWN" />
										</a>
									</td>
								</tr>
							</c:forEach>
					    </c:otherwise>
					</c:choose>
				</tbody>
			</table>
			
			<!--Page Number Start--> 
			<spring:bind path="searchCondition.pageIndex">
				<ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
				<input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
			</spring:bind> 
			<!--//Page Number End-->
			
			<input type="hidden" name="tempEquipmentId" id="tempEquipmentId" value=""	/>

		</form>
	</div>
</div>
<!--//blockListTable End-->

<div class="blockBlank_10px"></div>

<!--subTitle_2 Start-->
<div class="subTitle_2 noline">
	<h3><ikep4j:message pre='${preHeader}' key='equipment'/></h3>
</div>
<!--//subTitle_2 End--> 

<!--blockDetail Start-->
<div class="blockDetail">
	<div id="viewDiv">
		<form id="equipment" name="equipment" method="post" onsubmit="return false;" action="">
		
			<input type="hidden" id="equipmentId" name="equipmentId" value="${equipment.equipmentId}"	/>
			<input type="hidden" id="status" name="status" value="${equipment.status}"/>
		
			<table summary="<ikep4j:message pre='${preHeader}' key='equipment'/>">
			<caption></caption>
				<colgroup>
					<col width="15%"/>
					<col width="10%"/>
					<col width="75%"/>
				</colgroup>
			<tbody>
				<tr>
					<th scope="row" rowspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='equipmentName'/>
					</th>
					<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name'/></th>
					<td>
						<div>
							<input type="text" name="equipmentName" id="equipmentName" value="${equipment.equipmentName}" size="50" class="inputbox"/>
						</div>
					</td>
				</tr>
				<tr>
					<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName'/></th>
					<td>
						<div>
							<input type="text" name="equipmentEnglishName" id="equipmentEnglishName" value="${equipment.equipmentEnglishName}" size="50" class="inputbox" />
						</div>
					</td>
				</tr>
				<tr>
					<th scope="row" colspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='useFlag'/>
					</th>
					<td>
						<div>
							<input type="radio" id="useRadio" name="useFlag" class="radio" value="1" <c:if test="${empty equipment.useFlag || equipment.useFlag == '1'}">checked="checked"</c:if> />
							<label for="useRadio"><ikep4j:message pre='${preDetail}' key='useFlagY'/></label>&nbsp;
							<input type="radio" id="unUseRadio" name="useFlag" class="radio" value="0" <c:if test="${!empty equipment.useFlag && equipment.useFlag != '1'}">checked="checked"</c:if> />
							<label for="unUseRadio"><ikep4j:message pre='${preDetail}' key='useFlagN'/></label>
						</div>
					</td>
				</tr>
			</tbody>
			
			</table>
			
			<input type="hidden" id="sortOrder" name="sortOrder" value="${equipment.sortOrder}"/>
			<input type="hidden" id="oldSortOrder" name="oldSortOrder" value="${oldSortOrder}"/>
			
		</form>
	</div>
</div>
<!--//blockDetail End--> 

<!--blockButton Start-->
<div class="blockButton">
	<ul>
		<li><a class="button" id="newFormButton" href="#a"><span><ikep4j:message pre='${preButton}' key='new'/></span></a></li>
		<li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre='${preButton}' key='resave'/></span></a></li>
		<li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre='${preButton}' key='delete'/></span></a></li>
	</ul>
</div>
<!--//blockButton End-->