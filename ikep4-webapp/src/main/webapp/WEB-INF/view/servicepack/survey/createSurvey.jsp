<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.servicepack.survey.header.create" /> 
<c:set var="preList"    value="ui.servicepack.survey.list" />
<c:set var="preButton"  value="ui.servicepack.survey.common.button" /> 
<c:set var="preMessage"  value="message.servicepack.survey" /> 

<ikep4j:message  pre='${preMessage}' key='file' var="file"/>
<ikep4j:message  pre='${preMessage}' key='contents' var="contents"/>
<ikep4j:message  pre='${preMessage}' key='endDate' var="endDate"/>
<ikep4j:message  pre='${preMessage}' key='startDate' var="startDate"/>
<ikep4j:message  pre='${preMessage}' key='surveyTarget' var="surveyTarget"/>
<ikep4j:message  pre='${preMessage}' key='targetGroupList' var="targetGroupList"/>
<ikep4j:message  pre='${preMessage}' key='title' var="title"/>
<c:set var="user" value="${sessionScope['ikep.user']}" />
<c:set var="portal" value="${sessionScope['ikep.portal']}" />
 <%-- 메시지 관련 Prefix 선언 End --%>
<script type="text/javascript">
<!--   
Number.prototype.to2 = function() { return (this > 9 ? "" : "0")+this; };
Date.MONTHS = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
Date.DAYS   = ["Sun", "Mon", "Tue", "Wed", "Tur", "Fri", "Sat"];
Date.prototype.getDateString = function(dateFormat) {
  var result = "";
  
  dateFormat = dateFormat == 8 && "YYYY.MM.DD" ||
               dateFormat == 6 && "HH:mm:ss" ||
               dateFormat ||
               "YYYY.MM.DD HH:mm:ss";
  for (var i = 0; i < dateFormat.length; i++) {
    result += dateFormat.indexOf("YYYY", i) == i ? (i+=3, this.getFullYear()                     ) :
              dateFormat.indexOf("YY",   i) == i ? (i+=1, String(this.getFullYear()).substring(2)) :
              dateFormat.indexOf("MMM",  i) == i ? (i+=2, Date.MONTHS[this.getMonth()]           ) :
              dateFormat.indexOf("MM",   i) == i ? (i+=1, (this.getMonth()+1).to2()              ) :
              dateFormat.indexOf("M",    i) == i ? (      this.getMonth()+1                      ) :
              dateFormat.indexOf("DDD",  i) == i ? (i+=2, Date.DAYS[this.getDay()]               ) :
              dateFormat.indexOf("DD",   i) == i ? (i+=1, this.getDate().to2()                   ) :
              dateFormat.indexOf("D"   , i) == i ? (      this.getDate()                         ) :
              dateFormat.indexOf("hh",   i) == i ? (i+=1, this.getHours().to2()                  ) :
              dateFormat.indexOf("h",    i) == i ? (      this.getHours()                        ) :
              dateFormat.indexOf("mm",   i) == i ? (i+=1, this.getMinutes().to2()                ) :
              dateFormat.indexOf("m",    i) == i ? (      this.getMinutes()                      ) :
              dateFormat.indexOf("ss",   i) == i ? (i+=1, this.getSeconds().to2()                ) :
              dateFormat.indexOf("s",    i) == i ? (      this.getSeconds()                      ) :
                                                   (dateFormat.charAt(i)                         ) ;
  }
  return result;
};


(function($) {
	$jq(document).ready(function() { 
		var toDate = new Date();
		var tmpDate = toDate.getTime() + (7*24*60*60*1000);
		var fromDate = new Date();
		toDate.setTime(tmpDate);
		
		<c:if test="${empty survey.startDate}">
		$('#startDate').val( fromDate.getDateString('YYYY.MM.DD') );
		</c:if>
		<c:if test="${empty survey.endDate}">
		$('#endDate').val( toDate.getDateString('YYYY.MM.DD') );
		</c:if>
		
		function contentsType(value){
			if( value == 0 ){
				$jq('#contents').show();
				$jq('#file').hide();
			}
			else
			{	
				$jq('#contents').hide();
				$jq('#file').show();
				
			}

		}
		
		function surveyExtraView(value){
			if( value == 0 )
				$jq('#surveyExtraView').hide();
			else
				$jq('#surveyExtraView').show();
		}
		
		
		
		$jq('#fileContent').hide();
		contentsType('${survey.contentsType}');
		surveyExtraView('${survey.surveyTarget}');
		
		$("#startDate").datepicker(
		{
		    onSelect: function(dateText, inst) {
		   		var endDate = $jq("#endDate").val();
				    		
		   		if( endDate.length > 0 )
		   		{
		  			var tStartDate = $jq("#startDate").datepicker("getDate");
		   			var tEndDate = $jq("#endDate").datepicker("getDate");
				    			
		   			if( tStartDate > tEndDate )
		   			{
		   				alert("<ikep4j:message key='ui.servicepack.survey.common.lesthen' arguments='${startDate},${endDate}'/>");
		   				$jq(this).val("");
		   			}	
		   		}	
		   	
		    }
		}	
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		$("#endDate").datepicker(
				{
				    minDate: '-0d',
				    onSelect: function(dateText, inst) {
				    		var startDate = $jq("#startDate").val();
				    		
				    		if( startDate.length > 0 )
				    		{
				    			var tStartDate = $jq("#startDate").datepicker("getDate");
				    			var tEndDate = $jq("#endDate").datepicker("getDate");
					    			
				    			if( tEndDate < tStartDate )
				    			{
				    				alert("<ikep4j:message key='ui.servicepack.survey.common.grethen' arguments='${endDate},${startDate}'/>");
				    				$jq(this).val("");
				    			}	
				    		}	
				    }
				}
		).next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });
		
		
		$jq("input[name=contentsType]").click(function() {
			contentsType($jq(this).val());
		});
		
		$jq("input[name=surveyTarget]").click(function() {
			surveyExtraView($jq(this).val());
		});
		
		$jq("#btnDeleteControl,#btnGroupDeleteControl").click(function() {
	
			$jq("select option:selected").each(function () { 
	            $jq(this).remove();
	        }); 
			
			var selectTree = $jq(this).attr("id");
			
			if( selectTree == "btnDeleteControl" )
			{	
				var $sel = $jq("#surveyForm").find("#targetList");
				//$jq("#totalMember").text($sel.children().length);
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
			}
			else
			{	
				var $sel = $jq("#surveyForm").find("#targetGroupList");
				//$jq("#totalGroup").text($sel.children().length);
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$sel.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
			}
		});
		
		var validOptions = {
			    rules : {
			    	title : {required : true,maxlength:300},
			    	contents :  {required :function(element){
				    					return $("input[name=contentsType]:checked").attr("value") == '0';
				    	    	}
			    	},
			    	/**file :  {
			    				required :function(element){
			    					return $("input[name=contentsType]:checked").val() == '1';
			    	    		}
			    		},**/
			    	startDate :  {required : true},
			    	endDate :  {required : true}
			    },
			    messages : {
			    	title : {
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${title}'/>", 
			            maxlength : "<ikep4j:message  key='Size.max.survey.title' arguments='300'/>"
			        },
			        contents : {
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${contents}'/>"
			        },
			        /**file : {
			            direction : "bottom",
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${file}'/>"
			        },**/
			        startDate : {
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${startDate}'/>"
			        },
			        endDate : {
			            required : "<ikep4j:message  pre='${preMessage}' key='required' arguments='${endDate}'/>"
			        }
			    },
			    /**notice : {
			    	title : '<ikep4j:message pre='${preMessage}' key="title"/>',
			    	contents : '<ikep4j:message pre='${preMessage}' key="contents"/>',
			    	file : '<ikep4j:message pre='${preMessage}' key="file"/>',
			    	startDate : '<ikep4j:message pre='${preMessage}' key="startDate"/>',
			    	endDate : '<ikep4j:message pre='${preMessage}' key="endDate"/>'
			    },**/
			    submitHandler : function(form) {
			    	$jq("select option", form).each(function () { 
			            $jq(this).attr("selected","selected");
			        }); 
			    	if( confirm("<ikep4j:message pre='${preMessage}' key='redirectQuestionWrite'/>") ){
			    		$(form).ajaxLoadStart();
			    		form.submit();
			 		}
				}
			 };

		new iKEP.Validator("#surveyForm", validOptions);
		
		$("#saveButton").click(function() {
			if(  $("input[name=contentsType]:checked").attr("value")==1 && ( $jq("input[name=file]").val()==null || $jq("input[name=file]").val()=="") )
			{
				alert('<ikep4j:message  pre='${preMessage}' key='required' arguments='${file}'/>');
				return false;
			}
			if(  $("input[name=contentsType]:checked").attr("value")==1) {
				var fileName = $jq("input[name=file]").val();
			
				if(!fileName.match(/(.jpg|.JPG|.jpge|.JPEG|.gif|.GIF|.png|.PNG)$/)) {
					alert('<ikep4j:message pre='${preMessage}' key='file.image' />');
					return;
				}
			}
			if(  $("input[name=surveyTarget]:checked").attr("value")==1 ) {
				var $userList = $jq("#surveyForm").find("#targetList");
				var $groupList = $jq("#surveyForm").find("#targetGroupList");
				var cnt = $userList.children().length +	$groupList.children().length;
				if(cnt<1) {
					alert('<ikep4j:message  pre='${preMessage}' key='userList'/>');
					return;
				}
			}				
			$("#surveyForm").trigger("submit");
			return false; 
		});
		
		
		// 조직도 팝업 테스트
		$jq("#btnAddrControl").click(function() {
			var $container = $(this).parent();
			var $select = $("select", $container);
			var isUser = $select.attr("id") == "targetList";

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
				
				var msgTotal = "<ikep4j:message key='ui.servicepack.survey.common.total'/>" + $select.children().length + (isUser ? "<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg'/>" : "<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg'/>");
				$("span.totalNum_s", $container).text(msgTotal);
			}, items, {selectElement:$select, selectType:"user",selectMaxCnt:2000, tabs:{common:0, personal:1, collaboration:0, sns:0}});
		});
		
		$jq("#btnGroupAddrControl").click(function() {
			var $container = $(this).parent();
			var $select = $("select", $container);
			var isUser = $select.attr("id") == "targetList";

			var items = [];
			$jq.each($select.children(), function() {
				items.push($.data(this, "data"));
			});

			iKEP.showAddressSurveyBook(function(result){
				/*$select.empty();
				$jq.each(result, function() {
					$.tmpl(isUser ? iKEP.template.userOption : iKEP.template.groupOption, this).appendTo($select)
						.data("data", this);
				})*/
				
				var msgTotal = "<ikep4j:message key='ui.servicepack.survey.common.total'/>" + $select.children().length + (isUser ? "<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg'/>" : "<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg'/>");
				$("span.totalNum_s", $container).text(msgTotal);
			}, items, {selectElement:$select, selectType:"group",selectMaxCnt:2000, tabs:{common:1, personal:0, collaboration:0, sns:0}});
		});
		
		setUser = function(data) {
			if(data=="")			{
				alert('<ikep4j:message  pre='${preMessage}' key='search.user.nodata'/>');
			} else {
				var $select = $jq("#targetList");
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
				
				$jq("#totalMember").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalMemberMsg' />");
				
				if(duplicationUsers.length > 0) alert("[" + duplicationUsers.join(",") + "] " + iKEPLang.searchUserGroup.duplicateUser);
				
				$jq('#userName').val('');
			}
		};

		setGroup = function(data) {
			if(data=="") {
				alert('<ikep4j:message  pre='${preMessage}' key='search.group.nodata'/>');
			} else {
				var $select = $jq("#targetGroupList");
				var duplicationGroups = [];
				$jq.each(data, function() {
					var hasOption = $select.find('option[value="'+this.code+'"]');

					if(hasOption.length > 0){
						duplicationGroups.push(this.name);
					} else {
						$.tmpl(iKEP.template.groupOption, this).appendTo($select)
							.data("data", this);
					}	
				})
				$jq("#totalGroup").text("<ikep4j:message key='ui.servicepack.survey.common.total' />"+$select.children().length+"<ikep4j:message key='ui.servicepack.survey.common.totalGroupMsg' />");
				
				if(duplicationGroups.length > 0) alert("[" + duplicationGroups.join(",") + "] " + iKEPLang.searchUserGroup.duplicateGroup);
				
				$jq('#groupName').val('');
			}
		};
		
		$jq("input[name='title']").focus();
		
		//입력박스에 값을 입력하고 EnterKey를 눌렀을때 이벤트 바인딩
        $jq('#userName').keypress( function(event) {
            //파라미터(이벤트객체, 멀티선택팝업여부(Y/N), 콜백메소드)
			iKEP.searchUser(event, "Y", setUser);			
		});
		
	    //검색버튼을 클릭했을때 이벤트 바인딩
		$jq('#userSearchBtn').click( function() {
		    //입력박스에 이벤트를 발생시켜서, 위와 동일한 작업을 하도록 함
			$jq('#userName').trigger("keypress");
		});
		
	
		$jq('#groupName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroup); 
		});
		
		$jq('#groupSearchBtn').click( function() { 
			$jq('#groupName').trigger("keypress");
		});
	});  
})(jQuery); 
//-->
</script>
<h1 class="none"><ikep4j:message pre="${preHeader}" key="pageTitle" /></h1> 
<!-- pageTitle Start -->
<div id="pageTitle"> 
	<h2><ikep4j:message pre="${preHeader}" key="pageTitle" /></h2> 
</div> 
<!-- pageTitle End --> 
<!-- blockDetail Start -->
<div class="directive"> 
	<ul>
		<li><span><ikep4j:message  key="ui.servicepack.survey.create.notice" /></span></li>	
	</ul>
</div>
<div class="blockDetail" style="margin-top:10px;">
	<form id="surveyForm" method="post" action="<c:url value='/servicepack/survey/createSurveySubmit.do'/>"  enctype="multipart/form-data">
	<table  summary="<ikep4j:message pre="${preList}" key="summary" />">
		<caption></caption>
		<tbody> 
			<tr> 
				<spring:bind path="survey.title">
				<th scope="row"   width="15%;"><span class="colorPoint">*</span><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td><div>
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox w100" 
					value="${status.value}" 
					size="40" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
					/>
					<label for="${status.expression}" class="serverError">${status.errorMessage}</label>
				</div></td>	
				</spring:bind>
			</tr>
			<tr>  
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preList}' key='contents' /></th>
				<td>
				<div style="padding-bottom:4px;">
				<spring:bind path="survey.contentsType">
					<c:forEach var="code" items="${contentsTypeList}"> 
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</c:forEach> 	
				</spring:bind>
				</div>
				<div  style="margin-top:10px;">
				<spring:bind path="survey.contents">
				  <textarea 
					name="${status.expression}" 
					id="${status.expression}" 
					class="w100"
					cols="" 
					rows="5" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />">${status.value}</textarea>
				</spring:bind>
				<input type="file" name="file" id="file" class="file w50" title="file"/>
				</div>	 
				</td> 
			</tr>	
			<tr>  
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preList}' key='openDate' /></th>
				<td>
					<div>
					<spring:bind path="survey.startDate">
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox datepicker" 
					value="${status.value}" 
					size="10" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
					readonly="readonly"
					/> 
					<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="" />
					<label for="${status.expression}" class="serverError">
						<c:forEach var="erro" items="${status.errorMessage}"> 
						${erro}
						</c:forEach>
					</label>
					</spring:bind> 
					~	
					<spring:bind path="survey.endDate">
					<input 
					name="${status.expression}" 
					id="${status.expression}" 
					type="text" 
					class="inputbox datepicker" 
					value="${status.value}" 
					size="10" 
					title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
					readonly="readonly"
					/> 
					<img class="dateicon" src="<c:url value='/base/images/icon/ic_cal.gif'/>" alt="" />
					<label for="${status.expression}" class="serverError">
						<c:forEach var="erro" items="${status.errorMessage}"> 
						${erro}
						</c:forEach>
					</label>
					</spring:bind>	
					</div>
				</td>  
			</tr>		
			<tr>  
				<spring:bind path="survey.anonymous">
				<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${anonymousList}"  varStatus="loopStatus"> 
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
						<c:if test="${loopStatus.last}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</c:forEach> 
					</div>
				</td>
				</spring:bind>
			</tr>	
			<tr>  
				<spring:bind path="survey.resultOpen">
				<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${resultOpenList}"> 
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</c:forEach> 
				</td>
				</spring:bind>
			</tr>
			<tr>  
				<spring:bind path="survey.rejectButton">
				<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
					<c:forEach var="code" items="${rejectButtonList}"> 
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</c:forEach> 
				</td>
				</spring:bind>
			</tr>
			<tr>  
				<spring:bind path="survey.surveyTarget">
				<th scope="row"><ikep4j:message pre='${preList}' key='${status.expression}' /></th>
				<td>
					<div>
					<c:forEach var="code" items="${surveyTargetList}"> 
						<input name="${status.expression}" 
						type="radio" 
						class="radio" 
						value="${code.key}" 
						size="40" 
						title="<ikep4j:message pre='${preList}' key='${status.expression}' />"
						<c:if test="${code.key eq status.value}">checked="checked"</c:if>
						/> 
						<ikep4j:message key='${code.value}'/>
					</c:forEach> 
					</div>
				</td>
				</spring:bind>
			</tr>
			<tr id="surveyExtraView">
			<th scope="row"><ikep4j:message pre='${preList}' key='surveyTargetGroup' /></th>
			<td>
				<!--blockDetail Start-->
				<div class="blockDetail border_t1">
					<table summary="작업등록">
						<caption></caption>
						<tbody>
							<tr>
								<th scope="row" width="20%"><ikep4j:message pre='${preList}' key='surveyTargetList' /></th>
								<td>
									<input name="userName" id="userName" type="text" class="inputbox" size="20" title="userName" />
									<a name="userSearchBtn" id="userSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
									<a id="btnAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
									<div style="padding-top:4px;"> 
										<select name="tmpTargetList" id="targetList"  size="5" multiple="multiple" class="multi w70" title="<ikep4j:message pre='${preList}' key='surveyTargetList' />">
											<c:set var="targetListCnt" value="0"/> 
											<c:forEach var="target" items="${survey.targetList}" varStatus="loopStatus">
											 	<c:set var="targetListCnt" value="${targetListCnt+1}"/>
												<option label="<c:out value="${target.targetName}"/>" value="<c:out value="${target.targetKey.targetId}"/>|<c:out value="${target.targetName}"/>"><c:out value="${target.targetName}"/></option>
											</c:forEach>
										</select>	
										<a class="button_ic valign_bottom" href="#a" id="btnDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="<ikep4j:message  key='ui.servicepack.survey.common.button.delete' />"/>Delete</span></a>
										<span id='totalMember' class='totalNum_s'><ikep4j:message key='ui.servicepack.survey.common.totalMember'  arguments="${targetListCnt}"/></span>
									</div>			
								</td>
							</tr>
							<tr>
								<th scope="row"><ikep4j:message pre='${preList}' key='targetGroupList' /></th>
								<td>
									<input 
										name="groupName" 
										id="groupName" 
										type="text" 
										class="inputbox"
										size="20" 
										title="groupName"
										/>
									<a name="groupSearchBtn" id="groupSearchBtn" class="button_ic" href="#a"><span><img src="<c:url value='/base/images/icon/ic_btn_search.gif' />" alt="" /><ikep4j:message pre='${preButton}' key='search' /></span></a>
									<a id="btnGroupAddrControl" class="button_ic" href="#a"><span><ikep4j:message  key='ui.servicepack.survey.common.button.address' /></span></a>
									<div style="padding-top:4px;">
									    <c:set var="targetGroupListCnt" value="0"/>
										<select name="tmpTargetGroupList" id="targetGroupList"   size="5" multiple="multiple" class="multi w70" title="<ikep4j:message pre='${preList}' key='targetGroupList' />">
										<c:forEach var="target" items="${survey.targetGroupList}" varStatus="loopStatus">
										    <c:set var="targetGroupListCnt" value="${targetGroupListCnt+1}"/>
											<option label="<c:out value="${target.targetName}"/>" value="<c:out value="${target.targetKey.targetId}"/>|<c:out value="${target.targetName}"/>"><c:out value="${target.targetName}"/></option>
										</c:forEach>
										</select>	
										<a class="button_ic valign_bottom" href="#a" id="btnGroupDeleteControl"><span><img src="<c:url value='/base/images/icon/ic_btn_delete.gif' />" alt="" />Delete</span></a>
										<span id='totalGroup'  class='totalNum_s'><ikep4j:message key='ui.servicepack.survey.common.totalGroup' arguments="${targetGroupListCnt}"/></span>
									</div>
									<ikep4j:message key='ui.servicepack.survey.common.subGroupSelected' />
									</td>
							</tr>	
							</tbody>
					</table>
				</div>
				<!--//blockDetail End-->
			
			</td>
		</tr>
		</tbody> 
	</table>
	</form>
</div>
<!-- blockDetail End --> 
<!-- tableBottom Start -->
<div class="tableBottom">										
	<!-- blockButton Start -->
	<div class="blockButton"> 
		<ul>
			<li><a id="saveButton" class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='create'/></span></a></li>
			<li><a id="listButton" class="button" href="<c:url value='/servicepack/survey/surveyList.do'/>"><span><ikep4j:message pre='${preButton}' key='list'/></span></a></li>
		 </ul>
	</div>
	<!-- blockButton End --> 
</div>
<!-- tableBottom End --> 

