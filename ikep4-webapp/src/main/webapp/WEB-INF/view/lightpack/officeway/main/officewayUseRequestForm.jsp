<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<%@ include file="/base/common/fileUploadControll.jsp"%>

<%--메시지 관련 Prefix 선언 Start--%> 
<c:set var="preEdit"  value="ui.lightpack.planner.edit.schedule" /> 
<c:set var="preLabel"    value="ui.lightpack.planner.common.label" />
<c:set var="preButton"  value="ui.lightpack.planner.common.button" /> 
<c:set var="preDialog"  value="ui.lightpack.planner.dialog.repeat" /> 
<c:set var="preView"  value="ui.lightpack.planner.view.schedule" /> 
<%--메시지 관련 Prefix 선언 End--%>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />


<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script type="text/javascript">
var realtyCnt = 1;
(function($) {
	$(document).ready(function(event) {
		$("input[name=startDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("input[name=endDate]").datepicker({dateFormat: 'yy.mm.dd'}).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		/* var today = iKEP.getCurTime();
		$("input[name=startDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=endDate]", form);
			},
		    showOn: "button",
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		}).click(function() { $(this).prev().eq(0).datepicker("show"); });   
		$("input[name=endDate]").attr("readonly", true).datepicker({
			onSelect : function(){
				var form = $(this)[0].form;
				var validator = $(form).validate();
				validator.element(this);
				validator.element("input[name=startDate]", form);
			},
		    showOn: "button",
		    minDate : today,
		    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
		    buttonImageOnly: true
		});    */
		
		
		$("a.btn_save").click(function() {
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
			
			var tempExceptOfficeWay = "";

			<c:forEach var="exceptList" items="${exceptOfficewayList}">
				for(i=0;i<realtyCnt;i++){
					if($("#productNo"+i).val() == "${exceptList.productNo}"){
						if(tempExceptOfficeWay == ""){
							tempExceptOfficeWay = $("#productNo"+i).val()+"("+$("#productName"+i).val()+")";
						}else{
							tempExceptOfficeWay = tempExceptOfficeWay+","+$("#productNo"+i).val()+"("+$("#productName"+i).val()+")";
						}
					}
				}
			</c:forEach>
			
			if(tempExceptOfficeWay != ""){
				alert(tempExceptOfficeWay+"는 신청 불가능한 상품입니다.");
				return;
			}
			
			for(i=0;i<realtyCnt;i++){
				tempproductName = $("#productName"+i).val();  
				tempproductNo = $("#productNo"+i).val();   
				//tempcategory1 = $("#category1"+i).val();
				tempcategory2 = $("#category2"+i).val();
				tempremark = $("#remark"+i).val();
				if(tempremark == ""){
					tempremark = "N/A";
				}
				tempamount1 = $("#amount1"+i).val();     
				tempunit = $("#unit"+i).val();  
				tempprice1 = $("#price1"+i).val();  
				tempprice2 = $("#price2"+i).val(); 
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
			$jq('input[name=status1]').val("S");
			$("#requestForm").trigger("submit"); 
			return false;
		});
		
		$("a.btn_temp_save").click(function() {
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
			
			var tempExceptOfficeWay = "";

			<c:forEach var="exceptList" items="${exceptOfficewayList}">
				for(i=0;i<realtyCnt;i++){
					if($("#productNo"+i).val() == "${exceptList.productNo}"){
						if(tempExceptOfficeWay == ""){
							tempExceptOfficeWay = $("#productNo"+i).val()+"("+$("#productName"+i).val()+")";
						}else{
							tempExceptOfficeWay = tempExceptOfficeWay+","+$("#productNo"+i).val()+"("+$("#productName"+i).val()+")";
						}
					}
				}
			</c:forEach>
			
			if(tempExceptOfficeWay != ""){
				alert(tempExceptOfficeWay+"는 신청 불가능한 상품입니다.");
				return;
			}
			
			for(i=0;i<realtyCnt;i++){
				tempproductName = $("#productName"+i).val();  
				tempproductNo = $("#productNo"+i).val();   
				//tempcategory1 = $("#category1"+i).val();jQuery("#category1"+i+ "option:selected").text();
				tempcategory2 = $("#category2"+i).val();jQuery("#category2"+i+ "option:selected").text();
				tempremark = $("#remark"+i).val();
				if(tempremark == ""){
					tempremark = "N/A";
				}
				tempamount1 = $("#amount1"+i).val();     
				tempunit = $("#unit"+i).val();  
				tempprice1 = $("#price1"+i).val();  
				tempprice2 = $("#price2"+i).val(); 
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
			$jq('input[name=status1]').val("T");
			$("#requestForm").trigger("submit"); 
			return false;
		});
		$("a.btn_cancel").click(function() {
			$jq("#listForm").submit();
		});
		
		$("input[name=foreverChk]").click(function() { 
			if($("input[name=foreverChk]").is(":checked")) {
				$("#endDateSpan").hide();
			} else {
				$("#endDateSpan").show();
			} 
			
		});
		
		$("a.realtyPlus").click(function(){	
			$jq("#realtyBody").append(
				"<tr>"+
					"<td style=\"text-align:center;\">${today}</td>"+
					//"<td><select name=\"category1"+realtyCnt+"\" id=\"category1"+realtyCnt+"\">"+
					//<c:forEach var="code" items="${categoryList1}">
					//"<option value=${code.categoryId}>${code.categoryName}</option>"+
					//</c:forEach>
					//"</select></td>"+
					"<td><input name=\"productName"+realtyCnt+"\" id=\"productName"+realtyCnt+"\"  class=\"inputbox w100\" type=\"text\"  /></td>"+
					"<td><input name=\"productNo"+realtyCnt+"\" id=\"productNo"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
					"<td><select name=\"category2"+realtyCnt+"\" id=\"category2"+realtyCnt+"\">"+
					<c:forEach var="code" items="${categoryList2}">
					"<option value=${code.categoryId}>${code.categoryName}</option>"+
					</c:forEach>
					"</select></td>"+
					"<td><input name=\"amount1"+realtyCnt+"\" id=\"amount1"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" onblur=\"calculation1("+realtyCnt+")\" onkeyup=\"calculation1("+realtyCnt+")\" /></td>"+
					"<td style=\"text-align:center;\"><input name=\"unit"+realtyCnt+"\" id=\"unit"+realtyCnt+"\"  class=\"inputbox w90\" type=\"hidden\" value=\"EA\" readonly=\"readonly\" />EA</td>"+
					"<td><input name=\"price1"+realtyCnt+"\" id=\"price1"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" onblur=\"calculation2("+realtyCnt+")\" onkeyup=\"calculation2("+realtyCnt+")\" /></td>"+
					"<td><input name=\"price2"+realtyCnt+"\" id=\"price2"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\" readonly=\"readonly\" /></td>"+
					"<td><input name=\"remark"+realtyCnt+"\" id=\"remark"+realtyCnt+"\"  class=\"inputbox w90\" type=\"text\"  /></td>"+
					"<td style=\"text-align:center;\">${user.userName}</td>"+
				"</tr>"
			);
			realtyCnt++;
			iKEP.iFrameContentResize();
		});
	});
	
	calculation1 = function(code){
		$jq("#price2"+code).val($jq("#price1"+code).val()*$jq("#amount1"+code).val()); 
	};
	
	calculation2 = function(code){
		$jq("#price2"+code).val($jq("#amount1"+code).val()*$jq("#price1"+code).val()); 
	};
	
})(jQuery);

</script>
<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequestMyList.do'/>" ></form>
<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/officeway/officewayUseRequest.do'/>" >
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

<!-- Modal window Start -->
<!-- 사용시class="none"으로 설정 -->
<div id="mgmt-panel" title="">

<!-- div id="calendar" class="hidden"></div -->
<div style="height:10px;"></div>
	<div id="pageTitle" class="btnline"> 
		<h2>사무용품 신청</h2>
	</div>
	<div class="blockDetail">
	<table>
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
		<tbody id="realtyBody">
			<tr>
				<th style="text-align:center;">등록월</th>
				<!-- <th style="text-align:center;">분류</th> -->
				<th style="text-align:center;">품목</th>
				<th style="text-align:center;">제품번호</th>
				<th style="text-align:center;">사유</th>
				<th style="text-align:center;">수량</th>
				<th style="text-align:center;">단위</th>
				<th style="text-align:center;">단가</th>
				<th style="text-align:center;">금액</th>
				<th style="text-align:center;">비고</th>
				<th style="text-align:center;">사용자</th>
			</tr>
			<tr>
				<td style="text-align:center;">${today}</td>
				<%-- <td>
					<select id="category10" name="category10">
						<c:forEach var="code" items="${categoryList1}">
							<option value="${code.categoryId}">${code.categoryName}</option>
						</c:forEach>
					</select>
				</td> --%>
				<td><input name="productName0" id="productName0" title="" class="inputbox w100" type="text"  /></td>
				<td><input name="productNo0" id="productNo0" title="" class="inputbox w90" type="text"  /></td>
				<td>
					<select id="category20" name="category20">
						<c:forEach var="code" items="${categoryList2}">
							<option value="${code.categoryId}">${code.categoryName}</option>
						</c:forEach>
					</select>
				</td>
				<td><input name="amount10" id="amount10" title="" class="inputbox w90" type="text" onblur="calculation1('0');" onkeyup="calculation1('0');" /></td>
				<td style="text-align:center;"><input name="unit0" id="unit0" title="" class="inputbox w90" type="hidden" value="EA" readonly="readonly" />EA</td>
				<td><input name="price10" id="price10" title="" class="inputbox w90" type="text" onblur="calculation2('0');" onkeyup="calculation2('0');" /></td>
				<td><input name="price20" id="price20" title="" class="inputbox w90" type="text" readonly="readonly" /></td>
				<td><input name="remark0" id="remark0" title="" class="inputbox w90" type="text"  /></td>
				<td style="text-align:center;">${user.userName}</td>
			</tr>
		</tbody>
	</table>
	<table><tr><td style="text-align:right;border:0 0 0 0;">
		<div class="nblockButton"> 
			<a class="button_img05 realtyPlus" href="#a"><span>품목추가</span></a>
	</div>	
	</td></tr></table>
</div>	
	<div class="nblockButton"> 
		
			<c:if test="${periodCheck}">
				<a class="button_img01 btn_save" href="#a"><span>신청</span></a>
			</c:if>
			<a class="button_img01 btn_temp_save" href="#a"><span>임시저장</span></a>
			<a class="button_img01 btn_cancel" href="#a"><span><ikep4j:message pre="${preButton}" key="cancel" /></span></a>
		
	</div>	
</div>
</form>