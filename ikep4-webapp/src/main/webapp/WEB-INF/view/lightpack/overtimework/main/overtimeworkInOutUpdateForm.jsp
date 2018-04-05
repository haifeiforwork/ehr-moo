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

<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/gate/gate_style.css"/>" />


<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.json-2.2.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/jquery/jquery.postjson.js"/>"></script>

<script type="text/javascript">
var realtyCnt = 1;
var clickFlag = 0;
var setTime = 5;

var loadingHtml = "";
(function($) {
	$(document).ready(function(event) {
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
		
		$jq("#searchUserButton").click( function() { 
			iKEP.searchUser(event, "N", setUser);
		});
		
		
		$jq("#searchUserName").keypress( function(event) { 
			if(event.keyCode == '13'){
        		iKEP.searchUser(event, "N", setUser);
        		return false;
        	}
		});
		
		
		setUser = function(data) {
			//var $sel = $jq("#roleUserList");
			//var selectCheck;
			$jq("#userNameTd").empty();
			$jq("#jobTitleNameTd").empty();
			$jq("#teamNameTd").empty();
			$jq("#overtimeworkerId").empty();
			$jq("#pictureTd").empty();
			$jq("#registDateTd").empty();
			if(data.length > 0) {
				$jq(data).each(function(index) {
					$jq("#userNameTd").append(data[0].userName);
					if(data[0].jobDutyName != null){
						$jq("#jobTitleNameTd").append(data[0].jobDutyName);
					}else{
						$jq("#jobTitleNameTd").append(data[0].jobTitleName);
					}
					
					$jq("#teamNameTd").append(data[0].teamName);
					$jq("#overtimeworkerId").val(data[0].id);
					$jq("#pictureTd").append("<img src=\"<c:url value='' />"+data[0].profilePicturePath+"\" width=\"200px\" height=\"200px\" />");
				});
				   
				   $jq("#registDateTd").append(data[0].registDate);
			} else {
				alert("<ikep4j:message pre="${messagePrefix}" key="alert.noRetrievedUser" />");
			}
		};
		
		save = function(){
			
			$jq("#checkFlag").val("R");
			$("#requestForm").trigger("submit"); 
			return false;
		};
		
		
	});
	
	$(document).bind("keydown", function(event){

	    $("#searchUserName").focus();
	});
    
    checkCardId = function(){//0005626786
    	var tempText1 = "";
    	var tempText2 = "";
    	var tempText3 = "";
    	if($jq("#searchUserName").val().match("#")){
    		tempText1 = $jq("#searchUserName").val();
    		tempText2 = tempText1.substring(0,1); 
    		tempText3 = tempText1.substring(1,tempText1.length-1); 
    		
    		if(tempText2 == ">"){
    			$jq("#inoutFlag").val("O");
    		}else{
    			$jq("#inoutFlag").val("I");
    		}
    		$jq("#cardId").val(tempText3);
    		if(clickFlag == "0"){
    			clickFlag = "1";
    			$.ajax({
    				url : "<c:url value='/lightpack/overtimework/overtimeworkInOutAutoRegist.do' />",
    				data : $("#requestForm").serialize(),
    				type : "post",
    				success : function(data) {
    					if(data.userId == "" || data.userId == null){
    						alert('등록된 사용자 정보가 없습니다.');clickFlag = "0";
    					}else{
    						$jq("#pictureTd").empty();
    						$jq("#inoutTd").empty();
    						$jq("#inoutTd").append(data.inoutFlag);
    						$jq("#userNameTd").append(data.userName);
    						$jq("#jobTitleNameTd").append(data.jobTitleName);
    						$jq("#teamNameTd").append(data.groupName);
    						$jq("#overtimeworkerId").val(data.userId);
    						$jq("#overtimeworkId").val(data.overtimeworkId);
    						$jq("#registDateTd").append(data.registDate);
    						$jq("#pictureTd").append("<img src=\"<c:url value='' />"+data.profilePicturePath+"\" width=\"200px\" height=\"200px\" />");
    						clickFlag = "1";
    						loadingFnc();
    						
    					}
    					$jq("#searchUserName").val("");
    					
    				},
    				error : function() {alert('등록된 사용자 정보가 없습니다.');$jq("#searchUserName").val("");clickFlag = "0";}
    			});
    		}
    	}
	};
	

     viewOn = function(img){
    	img.src=img.src.replace(".png","_over.png"); 
     };
     
     viewOut = function(img){
    	 img.src=img.src.replace("_over.png",".png"); 
     };
	
})(jQuery);





</script>

<form id="listForm" name="listForm" method="post" action="<c:url value='/lightpack/overtimework/overtimeworkUseRequestMyList.do'/>" onsubmit="return false;"></form>
<form id="requestForm" name="requestForm" method="post" action="<c:url value='/lightpack/overtimework/overtimeworkInOutUpdate.do'/>">
<input type="hidden" name="productName" value=""/>    
<input type="hidden" name="productNo" value=""/> 
<!-- <input type="hidden" name="category1" value=""/>  -->
<input type="hidden" name="category2" value=""/> 
<input type="hidden" name="remark" value=""/> 
<input type="hidden" name="amount1" value=""/> 
<input type="hidden" name="unit" value=""/> 
<input type="hidden" name="price1" value=""/>    
<input type="hidden" name="price2" id="price2" value=""/> 
<input type="hidden" id="overtimeworkerId" name="overtimeworkerId" value=""/> 
<input type="hidden" id="overtimeworkId" name="overtimeworkId" value="${tempOvertimeWork.overtimeworkId}"/> 
<input type="hidden" id="inoutFlag" name="inoutFlag" value=""/> 
<input type="hidden" id="regFlag" name="regFlag" value=""/> 
<input type="hidden" id="checkFlag" name="checkFlag" value=""/> 
<input type="hidden" id="cardId" name="cardId" value=""/> 
<body><table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-image:url(<c:url value="/base/images/gate/bg_02.jpg"/>); padding:50px 0 0 60px;">
  <tbody>
    <tr>
      <td height="750" valign="top" >
        <table border="0" cellspacing="0" cellpadding="0">
          <tbody>
            <tr>
              <td height="45" colspan="2" valign="top"><span class="f_title">미확인 출입등록</span></td>
            </tr>
            <tr>
              <td valign="top"><table border="0" cellspacing="0" cellpadding="0" style="background-color:#016bb7; height:480px;">
                <tbody>
                  <tr>
                    <td style="padding:30px" id="pictureTd"><img src="<c:url value="${tempOvertimeWork.profilePicturePath}"/>" width="200" height="200" alt=""/></td>
                  </tr>
                  <tr>
                    <td align="left" valign="top" style="padding: 0 20px;"><table border="0" cellspacing="0" cellpadding="0">
                      <tbody>
                        <tr>
                          <td height="36" class="f_blue">이름</td>
                          <td width="30" class="f_fff_18">&nbsp;</td>
                          <td class="f_fff_24" id="userNameTd">${tempOvertimeWork.userName}</td>
                        </tr>
                        <tr>
                          <td height="36" class="f_blue">직책</td>
                          <td>&nbsp;</td>
                          <td class="f_fff_24" id="jobTitleNameTd">${tempOvertimeWork.jobTitleName}</td>
                          </tr>
                        <tr>
                          <td height="36" class="f_blue">부서</td>
                          <td>&nbsp;</td>
                          <td class="f_fff_24" id="teamNameTd">${tempOvertimeWork.groupName}</td>
                          </tr>
                          <tr>
                          <td height="36" class="f_blue">전화번호</td>
                          <td>&nbsp;</td>
                          <td class="f_fff_24" id="teamNameTd">${tempOvertimeWork.officePhoneNo}</td>
                          </tr>
                          <tr>
                          <td height="36" class="f_blue">휴대폰</td>
                          <td>&nbsp;</td>
                          <td class="f_fff_24" id="teamNameTd">${tempOvertimeWork.mobile}</td>
                          </tr>
                      </tbody>
                    </table></td>
                  </tr>
                  <tr>
                    <td height="45" align="center">&nbsp;</td>
                  </tr>
                  </tbody>
              </table></td>
              <td valign="top" style="padding-left:20px">
              <table border="0" cellspacing="1" cellpadding="0" style="background-color:#aebcc6;  height:480px;">
                <tbody>
                  <tr>
                    <td width="145" height="120" align="center" bgcolor="#333" class="f_gray_30">출입자</td>
                    <td bgcolor="#CCCCCC" style="padding:0 20px">
                    <table border="0" cellspacing="0" cellpadding="0">
                      <tbody>
                          <tr>
                            <td class="f_333_30">
                             ${tempOvertimeWork.userName}
                            </td>
                            <td></td>
                          </tr>
                        </tbody>
                    </table>
                    </td>
                  </tr>
                  <tr>
                    <td width="145" height="120" align="center" bgcolor="#333" class="f_gray_30">구분</td>
                    <td bgcolor="#E5E5E5" style="padding:0 20px;" id="inoutTd" class="f_333_30">
                     ${tempOvertimeWork.inoutFlag}
                   </td>
                  </tr>
                  <tr>
                    <td width="145" height="120" align="center" bgcolor="#333" class="f_gray_30">시간</td>
                    <td bgcolor="#CCCCCC" class="f_333_30" style="padding:0 20px" id="registDateTd">${tempOvertimeWork.registDate}</td>
                    </tr>
                  <tr>
                    <td width="145" height="120" align="center" bgcolor="#333" class="f_gray_30">담당자</td>
                    <td bgcolor="#E5E5E5" style="padding:0 20px;"><img src="<c:url value="/base/images/gate/btn_ok.png"/>" width="180" height="65" alt="" onclick="javascript:save();" style="cursor:hand;" onmouseover="viewOn(this);" onmouseout="viewOut(this);" >
                    </td>
                    </tr>
                </tbody>
              </table>
              </td>
            </tr>
          </tbody>
      </table>
      
      </td>
    </tr>
  </tbody>
</table>

</body>
</form>