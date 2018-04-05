<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.lightpack.meetingroom.header" /> 
<c:set var="preDetail"  value="ui.lightpack.meetingroom.detail" />
<c:set var="preButton"  value="ui.lightpack.meetingroom.button" /> 
<c:set var="preMessage" value="ui.lightpack.meetingroom.message" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
//<![CDATA[
(function($) {

	$jq(document).ready(function() {
		$jq("#buildingFloorForm:input:visible:enabled:first").focus();
		
		$jq("a[name=createButton]").click(function() {  
			
			if(tempCurrItem == null) {
				
				<c:choose>
				<c:when test="${type == 'BUILDING'}">
				alert("<ikep4j:message pre='${preMessage}' key='chooseNode' />");
				</c:when>
				<c:otherwise>
				alert("<ikep4j:message pre='${preMessage}' key='chooseBuilding' />");
				</c:otherwise>
				</c:choose>
				
				return;
			}
			

			$jq("#buildingFloorForm").trigger("submit");

		});
		
		$jq("a[name=updateButton]").click(function() {  
			
			$jq("#buildingFloorForm").trigger("submit");
		}); 
		
		var url = "";
		
		<c:choose>
		<c:when test="${!empty buildingFloor.buildingFloorId}">
		url = "<c:url value='buildingFloorUpdate.do' />";
		</c:when>
		<c:otherwise>
		url = "<c:url value='buildingFloorCreate.do' />";
		</c:otherwise>
		</c:choose>
		
		/**
		 * Validation Logic Start
		 */
		var validOptions = {
			rules : {
				"buildingFloorName" : {
					required : true,
					rangelength : [0, 20]
				},
				"buildingFloorEnglishName" : {
					required : true,
					terminology : true,
					rangelength : [0, 100]								
				}
			},
			<c:choose>
			<c:when test="${type == 'BUILDING'}">
			messages : {
				"buildingFloorName" : {
					required : '<ikep4j:message key="NotNull.BuildingFloor.buildingName" />',
					rangelength : '<ikep4j:message key="Size.BuildingFloor.buildingName" />'
				},
				"buildingFloorEnglishName" : {
					required : '<ikep4j:message key="NotNull.BuildingFloor.buildingEnglishName" />',
					terminology : '<ikep4j:message key="Terminology.BuildingFloor.buildingEnglishName" />',
					rangelength : '<ikep4j:message key="Size.BuildingFloor.buildingEnglishName" />'
				}
			},
			</c:when>
			<c:when test="${type == 'FLOOR'}">
			messages : {
				"buildingFloorName" : {
					required : '<ikep4j:message key="NotNull.BuildingFloor.floorName" />',
					rangelength : '<ikep4j:message key="Size.BuildingFloor.floorName" />'
				},
				"buildingFloorEnglishName" : {
					required : '<ikep4j:message key="NotNull.BuildingFloor.floorEnglishName" />',
					terminology : '<ikep4j:message key="Terminology.BuildingFloor.floorEnglishName" />',
					rangelength : '<ikep4j:message key="Size.BuildingFloor.floorEnglishName" />'
				}
			},
			</c:when>
			</c:choose>
			submitHandler : function() {
				
				$jq("#useFlag").val($jq("input[name=useFlagRadio]:checked").val());
				
				$jq.ajax({
					url : url,
					data : $jq("#buildingFloorForm").serialize(),
					type : "post",
					success : function(result) {
						
						var buildingFloorId = result;
						
						<c:choose>
						<c:when test="${!empty buildingFloor.buildingFloorId}">
						alert("<ikep4j:message pre='${preMessage}' key='resaveComplete' />");
						$jq("#buildingFloorTree").jstree("refresh", tempParent);
						$jq("#buildingFloorTree").jstree("open_node", tempParent.children(), false, false);
						</c:when>
						<c:otherwise>
						alert("<ikep4j:message pre='${preMessage}' key='saveComplete' />");
						if(($jq(tempCurrItem).attr("class")=="jstree-closed jstree-last") || ($jq(tempCurrItem).attr("class")=="jstree-closed")) {
							//노드가 한번도 열리지 않은 경우 노드 생성 수행하지 않음
							$jq("#buildingFloorTree").jstree("open_node", tempCurrItem, false, false);
						} else { 
							//노드가 한번이라도 열렸던 경우 아래 코드 수행
							$jq("#buildingFloorTree").jstree("refresh", tempCurrItem);
							$jq("#buildingFloorTree").jstree("open_node", tempCurrItem.children(), false, false);
						}
						
						//방금 생성한 노드를 바로 수정하는 경우를 대비하여 tempParent를 새로 매핑한다.
						//하지 않으면 tempParent가 undefined로 에러 발생
						tempParent = tempCurrItem;
						</c:otherwise>
						</c:choose>
						
						getBuildingFloor(buildingFloorId);
					},
					error : function(xhr, exMessage) {
						var errorItems = $.parseJSON(xhr.responseText).exception;
						validator.showErrors(errorItems);
					}
				});
			}
		};
		
		<c:if test="${type == 'BUILDING' || type == 'FLOOR'}">
		var validator = new iKEP.Validator("#buildingFloorForm", validOptions);
		</c:if>
		/**
		 * Validation Logic End
		 */
	});
})(jQuery);
//]]>
</script>

<form name="buildingFloorForm" id="buildingFloorForm" action="" method="post" >
<input type="hidden" id="buildingFloorId" name="buildingFloorId" value="${buildingFloor.buildingFloorId}"/>
<input type="hidden" id="parentBuildingFloorId" name="parentBuildingFloorId" value="${buildingFloor.parentBuildingFloorId}"/>
<input type="hidden" id="sortOrder" name="sortOrder" value="${buildingFloor.sortOrder}" />
<input type="hidden" id="useFlag" name="useFlag" value="${buildingFloor.useFlag}" />
<input type="hidden" id="childCount" name="childCount" value="${childCount}"/>
	
	<div class="blockDetail">
		<table id="mainTable" summary="<ikep4j:message pre='${preHeader}' key='buildingFloor' />">
			<caption></caption>
			<colgroup>
				<col width="25%"/>
				<col width="15%"/>
				<col width="60%"/>
			</colgroup>
			<tbody>
				
				<!--//parentBuildingName Start-->
				<c:if test="${type == 'FLOOR'}">
				<tr>
					<th scope="row" colspan="2">
						<ikep4j:message pre='${preDetail}' key='building' />
					</th>
					<td>
						<div>
							${parentBuildingName}
						</div>
					</td>
				</tr>
				</c:if>
				<!--//parentBuildingName End-->
				
				<!--buildingName Start-->
				<tr>
					<th scope="row" rowspan="2">
						<span class="colorPoint">*</span> <c:choose><c:when test="${type == 'BUILDING'}"><ikep4j:message pre='${preDetail}' key='buildingName' /></c:when><c:when test="${type == 'FLOOR'}"><ikep4j:message pre='${preDetail}' key='floorName' /></c:when><c:otherwise><ikep4j:message pre='${preDetail}' key='buildingFloorName' /></c:otherwise></c:choose>
					</th>
					<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='name' /></th>
					<td>
						<div>
							<input type="text" name="buildingFloorName" id="buildingFloorName" value="${buildingFloor.buildingFloorName}" size="50" class="inputbox"/>
						</div>
					</td>
				</tr>
				<tr>
					<th><span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='englishName' /></th>
					<td>
						<div>
							<input type="text" name="buildingFloorEnglishName" id="buildingFloorEnglishName" value="${buildingFloor.buildingFloorEnglishName}" size="50" class="inputbox" />
						</div>
					</td>
				</tr>
				<!--//buildingName End-->
				
				<!--//useFlag Start-->
				<tr>
					<th scope="row" colspan="2">
						<span class="colorPoint">*</span> <ikep4j:message pre='${preDetail}' key='useFlag' />
					</th>
					<td>
						<div>
							<input type="radio" id="useRadio" name="useFlagRadio" class="radio" value="1" <c:if test="${empty buildingFloor.useFlag || buildingFloor.useFlag == '1'}">checked="checked"</c:if> <c:if test="${type == 'FLOOR' && parentBuildingUseFlag == '0'}">disabled="disabled"</c:if> />
							<label for="useRadio"><ikep4j:message pre='${preDetail}' key='useFlagY' /></label>&nbsp;
							<input type="radio" id="unUseRadio" name="useFlagRadio" class="radio" value="0" <c:if test="${!empty buildingFloor.useFlag && buildingFloor.useFlag != '1'}">checked="checked"</c:if> <c:if test="${type == 'FLOOR' && parentBuildingUseFlag == '0'}">disabled="disabled"</c:if> />
							<label for="unUseRadio"><ikep4j:message pre='${preDetail}' key='useFlagN' /></label>
						</div>
					</td>
				</tr>
				<!--//useFlag End-->
				
			</tbody>
		</table>
		
		<div class="blockBlank_10px"></div>
		
		<!--blockButton Start-->
		<div class="blockButton" style="clear:both;">
			<ul>
				<c:choose>
				<c:when test="${empty buildingFloor.buildingFloorId && (type == 'BUILDING' || type == 'FLOOR')}">
				<li><a name="createButton" class="button" href="#" onclick="return false;"><span>등록</span></a></li>
				</c:when>
				<c:otherwise>
				<c:if test="${!empty buildingFloor.buildingFloorId && (type == 'BUILDING' || type == 'FLOOR')}">
				<li><a name="updateButton" class="button" href="#" onclick="return false;"><span><ikep4j:message pre='${preButton}' key='update' /></span></a></li>
				</c:if>
				</c:otherwise>
				</c:choose>
			</ul>
		</div>
		<!--//blockButton End-->
	</div>
</form>