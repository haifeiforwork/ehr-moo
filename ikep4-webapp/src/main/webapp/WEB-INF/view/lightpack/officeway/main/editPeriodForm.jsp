<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/fullcalendar-1.5.css"/>" />
<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/planner/calendar.css"/>" />
<script type="text/javascript" src="<c:url value="/base/js/units/lightpack/planner/fullcalendar-1.5-cust.js"/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
        
        $(document).ready(function() {   
		    //- 버튼영역 실행  
		    var today = iKEP.getCurTime();
			$jq("#saveButton").click(function() {   
				var tempStartDate = "";
				var tempEndDate = "";
				var tempYear = "";
				var tempMonth = "";
				var tempStatus = "";
				var startDate = "";
				var endDate = "";
				var year = "";
				var month = "";
				var status = "";
				for(i=1;i<13;i++){
					tempStartDate = $("#startDate"+i).val(); 
					if(tempStartDate == ""){
						tempStartDate = "N";
					}
					tempEndDate = $("#endDate"+i).val();  
					if(tempEndDate == ""){
						tempEndDate = "N";
					}
					tempStatus = $jq("input[name=statusCheck"+i+"]:checked").attr("value");
					tempMonth = i;
					if(i == 1){
						startDate = tempStartDate;
						endDate = tempEndDate;
						status = tempStatus;
						month = tempMonth;
					}else{
						startDate = startDate+"|"+tempStartDate;
						endDate = endDate+"|"+tempEndDate;
						status = status+"|"+tempStatus;
						month = month+"|"+tempMonth;
					}
				}
				$jq('input[name=startDate]').val(startDate); 
				$jq('input[name=endDate]').val(endDate);  
				$jq('input[name=status]').val(status);
				$jq('input[name=month]').val(month);
	    		$.ajax({
					url : "<c:url value='/lightpack/officeway/savePeriod.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("<ikep4j:message pre='${preMessage}' key='sendMessage'/>");
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
			
			checkRadioBox = function(tmpMth){
				for(i=1;i<13;i++){
					$jq('input:radio[name=statusCheck'+i+']:input[value=N]').attr("checked", true);
				}
				$jq('input:radio[name=statusCheck'+tmpMth+']:input[value=Y]').attr("checked", true);
			};
		    
			$jq("input[name=startDate1]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate1]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate1]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate1]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			
			$jq("input[name=startDate2]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate2]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate2]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate2]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate3]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate3]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate3]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate3]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate4]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate4]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate4]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate4]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate5]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate5]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate5]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate5]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate6]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate6]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate6]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate6]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate7]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate7]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate7]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate7]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			
			$jq("input[name=startDate8]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate8]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate8]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate8]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate9]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate9]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate9]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate9]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate10]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate10]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate10]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate10]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			
			$jq("input[name=startDate11]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate11]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate11]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate11]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
			$jq("input[name=startDate12]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=endDate12]", form);
				},
			    showOn: "button",
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			});   
			$jq("input[name=endDate12]").attr("readonly", true).datepicker({
				onSelect : function(){
					var form = $(this)[0].form;
					var validator = $(form).validate();
					validator.element(this);
					validator.element("input[name=startDate12]", form);
				},
			    showOn: "button",
			    minDate : today,
			    buttonImage: "<c:url value='/base/images/icon/ic_cal.gif'/>",
			    buttonImageOnly: true
			}); 
			
            
        });
        
        
		
	})(jQuery);  

	function getList() {
	
	$jq("#itemForm").attr("action", "<c:url value='/lightpack/officeway/editPeriodForm.do' />");
	$jq("#itemForm")[0].submit();
	}
//-->
</script>

<!--popup Start-->

<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>사무용품 신청 기간 관리</h2>
	<div class="clear"></div>
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
		<input type="hidden" name="startDate" value=""/>    
		<input type="hidden" name="endDate" value=""/> 
		<input type="hidden" name="status" value=""/> 
		<input type="hidden" name="month" value=""/> 
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table>
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>
				<tbody>   
    	      		<tr>
    					<th>년도</th>
    					<td>
		                    <select name="year" onchange="getList();">                               		
						        <c:forEach var="year" begin="2016" end="${nowYear}" step="1">
						        	<option value="${year}" <c:if test="${year eq nowYear}">selected="selected"</c:if>>${year}</option>
						        </c:forEach>
						        <option value="${nowYear+1}" <c:if test="${year eq nowYear+1}">selected="selected"</c:if>>${nowYear+1}</option>
						        <option value="${nowYear+2}" <c:if test="${year eq nowYear+2}">selected="selected"</c:if>>${nowYear+2}</option>
					        </select>			                       	
						</td>
    			    </tr>
    			    <c:forEach var="period" items="${periodList}">
    			    <tr>
    					<th>${period.month}월</th>
    					<td>
    						<label>
								<input name="statusCheck${period.month}" type="radio" class="radio" value="Y" <c:if test="${period.status eq 'Y'}">checked="checked"</c:if> onclick="checkRadioBox(${period.month});" /> 
								사용
							</label>
							<label>
								<input name="statusCheck${period.month}" type="radio" class="radio" value="N" <c:if test="${period.status eq 'N'}">checked="checked"</c:if>/> 
								미사용
							</label>
    						<input name="startDate${period.month}" id="startDate${period.month}" type="text" class="inputbox datepicker" <c:if test="${!empty period.startDate}">value="${period.startDate}"</c:if><c:if test="${empty period.startDate && period.month < 10}">value="${period.year}.0${period.month}.01"</c:if><c:if test="${empty period.startDate && period.month > 9}">value="${period.year}.${period.month}.01"</c:if> size="10" /> ~ 
							<input name="endDate${period.month}" id="endDate${period.month}" type="text" class="inputbox datepicker" <c:if test="${!empty period.endDate}">value="${period.endDate}"</c:if><c:if test="${empty period.endDate && period.month < 10}">value="${period.year}.0${period.month}.10"</c:if><c:if test="${empty period.endDate && period.month > 9}">value="${period.year}.${period.month}.10"</c:if> size="10" />
						</td>
    			    </tr>
    			    </c:forEach>
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		
		
		<!--blockButton Start-->
		
	</form>
	<!--//popup_contents End-->
		<div class="nblockButton"> 
		<a id="saveButton"  class="button_img01" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a>
		
		</div>

<!--//popup End-->