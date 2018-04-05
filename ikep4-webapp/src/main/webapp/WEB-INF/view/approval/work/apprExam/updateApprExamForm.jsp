<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.approval.work.exam.form.header" />
<c:set var="preForm"  	value="ui.approval.work.exam.form" />
<c:set var="preValidation" value="ui.approval.work.exam.validation" />
<c:set var="preButton"  value="ui.approval.common.button" />
<c:set var="preMessage" value="ui.approval.common.message" />
 <%-- 메시지 관련 Prefix 선언 End --%>
 
<script type="text/javascript">
	<!--
	var dialogWindow = null;
	var fnCaller;
	
	var	DOC2_BACK	="문서가 승인완료되어 결재 회수를 할수 없습니다.";
	var DOC3_BACK	="문서가 반려되어 결재 회수를 할수 없습니다.";
	var	DOC4_BACK	="문서가 회수되어 결재 회수를 할수 없습니다.";
	var	LINE0_BACK	="문서가 결재취소로 결재 회수를 진행할수 없습니다.";
	var	LINE2_BACK	="이미 결재처리 되었습니다.";
	var	LINE3_BACK	="이미 반려처리 되었습니다.";
	var	LINE4_BACK	="이미 합의처리 되었습니다.";	
	
	(function($){
		
		fnCaller = function (params, dialog) {
			if(params) {
			}

			dialogWindow = dialog;
			$("#btnClose").click(function() {
				dialogWindow.close();
			});
		};
		// 문서 현재 상태 체크
		var entrustUserId = "${apprLine.entrustUserId}";
        getDocStatus = function(apprId) {

			// 문서 정보 확인
			var status = false;			
			$jq.ajax({     
				url : '<c:url value="/approval/work/apprLine/getDocStatus.do"/>',     
				data :  { apprId : apprId, entrustUserId : entrustUserId },     
				type : "get", 
				async : false,
				success : function(result) {   			
					if(result=="OK") {
						status = true;
					} else {
					
						var str = eval(result+'_BACK');
						alert(str);
						
						$("body").ajaxLoadComplete();
					}
				},
				error : function(event, request, settings){
					 alert("error");
					 $("body").ajaxLoadComplete();
				}
			});			
			return status;
		};
		
		setUser = function(data) {
			if(data=="")			{
				alert('<ikep4j:message  pre='${preMessage}' key='search.user.nodata'/>');
			} else {
				var $select = $jq("#examUserSet");
				var duplicationUsers = [];
				$jq.each(data, function() {
					var hasOption = $select.find('option[value="'+this.id+'"]');

					if(hasOption.length > 0){
						duplicationUsers.push(this.userName);
					} else {
						$.tmpl(iKEP.template.userOption, this).appendTo($select)
							.data("data", this);
					}	
				})
				
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				
				$jq('#examUserName').val('');
				//$jq('#examUserName').focus();
			}
		};
		
		setAddress = function(data) {
			var name="";
			var userId="";
			$.each(data, function() {
				name = $.trim(this.name);
				userId = $.trim(this.id);	
			})
			
			$("#examUserName").val(name);
			$("#examUserId").val(userId);
			
			$jq("select[name=examUserSet] option").remove();
		};
		
		/* select option을 스트링으로 변환 */
		setExamUserSet = function(form) {
			var result="", receiveType="";
		    $jq("select[name=examUserSet] option").each(function() {
		    	if(result!="") result += ";";
		        receiveType = $jq(this).data("data").type=="group"?"0":"1";
		        result += $jq(this).val(); 
		    });
		    $jq("input[name=examUserId]").val(result);
		};
		
		//입력값 확인
		var validOptions = {
			submitHandler	: function(form) {
				var apprId = $("#myForm input[name=apprId]").val();
				$("body").ajaxLoadStart("button");
				
				if($jq("select[name=examUserSet] option").size() == 0) {
					//$('#requiredMsg').text("<ikep4j:message pre='${preValidation}' key='examUserSet'/>");
					$("body").ajaxLoadComplete();
					return false;
				}
				
				if (!confirm("<ikep4j:message pre='${preValidation}' key='confirm'/>")) {
					$("body").ajaxLoadComplete();
					return false;
				}
				setExamUserSet(form);
				
				// 문서 상태 확인
				//if( getDocStatus(apprId)) {
					$jq.ajax({     
						url : '<c:url value="/approval/work/apprExam/updateApprExam.do" />',     
						data :  $jq("#myForm").serialize(),
						type : "post",   
						success : function(result) {      
							if(result == "OK") {
								 $("body").ajaxLoadComplete();
								 dialogWindow.callback(result);
				                 dialogWindow.close();
							}else if(result == "EXIST") { // 하나의 결재 문서에 같은 사람을 요청할수 없음.
								alert("<ikep4j:message pre='${preValidation}' key='existUserId'/>");
								$("body").ajaxLoadComplete();
							}
						},
						error : function(event, request, settings){
							 alert("error");
							 $("body").ajaxLoadComplete();
						}
				    });
				 //} else {
				//	dialogWindow.close();
				//} 
                  
            },
            rules			: {
            		examReqContent	: { required: true, maxlength: 500 }
            		
            },
            messages		: {
            	    examReqContent	: {
						direction 		: "bottom",
                        required		: "<ikep4j:message pre='${preValidation}' key='mustinput.reasonComment'/>",
                        maxlength		: "<ikep4j:message pre='${preValidation}' key='maxinput.reasonComment' arguments='500'/>"
     				}
            }
		}; 
		
		$(document).ready(function(){
			
			new iKEP.Validator("#myForm", validOptions);
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $('#addrBtn').click( function() {
				//파라미터(콜백함수, 전송파라미터, 팝업옵션)
				iKEP.showAddressBook(setAddress, [], {selectType:"user", isAppend:true, selectMaxCnt:1});
			});
			
			$("#saveReqButton").click(function() {   
				$("#myForm").submit(); 
				return false; 
			});
			
			//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
	        $jq('#examUserName').keypress( function(event) {
	            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
				iKEP.searchUser(event, "Y", setUser);		
				//return false; 
			});
			
		    //검색버튼을 클릭했을때 이벤트 바인딩
			$jq('#addrSearchBtn').click( function() {
			    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
				$jq('#examUserName').trigger("keypress");
			});
		    
			//조직도 팝업
	        //주소록 버튼에 이벤트 바인딩
            $('#addrBtn').click( function() {
            	var $container = $(this).parent();
    			var $select = $("select", $container);
    			var isUser = $select.attr("id") == "examUserSet";

    			var items = [];
    			$jq.each($select.children(), function() {
    				items.push($.data(this, "data"));
    			});

    			iKEP.showAddressBook(function(result){
    				/*$select.empty();
    				$jq.each(result, function() {
    					$.tmpl(isUser ? iKEP.template.userOption : iKEP.template.groupOption, this).appendTo($select)
    						.data("data", this);
    				})*/
    				
    			}, items, {selectElement:$select, selectType:isUser ? "user" : "group",selectMaxCnt:30, tabs:{common:0, personal:1, collaboration:0, sns:0}});
			});
		    
			$jq('#btnDeleteControl').click( function() {
				$jq("select option:selected").each(function () { 
		            $jq(this).remove();
		        }); 
			});
			
		});
	})(jQuery);
	//-->
</script>
<form id="myForm" name="myForm" method="post" action="">

<spring:bind path="apprExam.apprId">
	<input type="hidden" name="${status.expression}" value="${apprId}"/>
</spring:bind>
	※ &nbsp;<ikep4j:message pre='${preForm}' key='titleMsg'/>
	<!--blockDetail Start-->
	<div class="blockDetail">
		<table summary="<ikep4j:message pre='${preForm}' key='summary'/>">
			<caption></caption>
			<tbody>
				<tr>
					<spring:bind path="apprExam.examUserName">
					<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<input type="text" class="inputbox" id="${status.expression}" name="${status.expression}" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" value="${apprExam.examUserName}" size="12"/>
						<a name="addrSearchBtn" id="addrSearchBtn" class="button_ic" href="#a">
							<span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="Search" />Search</span>
						</a>
						<a id="addrBtn" href="#a" class="button_ic">
							<span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" align="absmiddle"><ikep4j:message pre='${preForm}' key='organization'/></span>
						</a>
						<span id="requiredMsg"></span>
						<div class="blockBlank_5px"></div>
						<spring:bind path="apprExam.examUserSet">
						<select name="${status.expression}" id="${status.expression}" class="inputbox w50" size="5" multiple="multiple"></select>
						</spring:bind>
						<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl">
							<span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="Delete" />Delete</span>
						</a>
					</td>
					</spring:bind>
					<spring:bind path="apprExam.examUserId">
						<input type="hidden" id="${status.expression}" name="${status.expression}" value="${apprExam.examUserId}"/>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="apprExam.examReqContent">
					<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<textarea name="${status.expression}" class="tabletext  w100" title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>" cols="200" rows="7">${apprExam.examReqContent}</textarea>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="apprExam.isOpen">
					<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${isOpenList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq '0'}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind>
				</tr>
				<tr>
					<spring:bind path="apprExam.isRequest">
					<th scope="row" width="20%"><ikep4j:message pre='${preForm}' key='${status.expression}'/></th>
					<td>
						<c:forEach var="code" items="${isRequestList}">
							<input type="radio" class="radio" id="${status.expression}" name="${status.expression}"  value="${code.key}"
							<c:if test="${code.key eq '0'}">checked="true"</c:if>
							title="<ikep4j:message pre='${preForm}' key='${status.expression}'/>"
							/>
							<spring:message code="${code.value}" />
						</c:forEach>
					</td>
					</spring:bind>
				</tr>
			</tbody>
		</table>
		
	</div>
	<!--//blockDetail End-->
	
	<div class="clear"></div>
	
	<!--blockButton Start-->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveReqButton"   class="button" href="#a"><span><ikep4j:message pre='${preForm}' key='reqSave'/></span></a></li>
			<li><a id="btnClose"   class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='close'/></span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
	
</form>			

<!--//mainContents End-->