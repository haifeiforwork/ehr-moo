<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="preAdmin"    value="ui.support.poll.admin" />
<c:set var="preSub"    value="ui.support.poll.sub" />
<c:set var="preList"    value="ui.support.poll.list" />
<c:set var="preForm"    value="ui.support.poll.form" />
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 
<%pageContext.setAttribute("crlf", "\r\n"); %>

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[

(function($) { 

	$(document).ready(function() {
		var btnFlag = "submit";
		
		var $answerTitles = $("#answerTitles").children().remove().end();
		<c:forEach var="answer" items="${poll.answerList}" varStatus="loopCount">
			$('<option value="${answer.answerTitle}">${answer.answerTitle}<\/option>').appendTo($answerTitles);
		</c:forEach>
		
		var $addrUserList = $("select[name=addrUserList]").children().remove().end();
		<c:forEach var="item" items="${poll.targetUserList}">
			<c:if test="${item.targetType == '0'}">
				var item = {
					type : "user",
					empNo : "",
					id : "${item.userId}",
					userName : "${user.localeCode == portal.defaultLocaleCode ? item.userName : item.userEnglishName}",
					jobTitleName : "${user.localeCode == portal.defaultLocaleCode ? item.jobTitleName : item.jobTitleEnglishName}",
					groupId : "${item.groupId}",
					teamName : "${user.localeCode == portal.defaultLocaleCode ? item.teamName : item.teamEnglishName}",
					email : "${item.mail}",
					mobile : ""
				};

				$.tmpl(iKEP.template.userOption, item).appendTo($addrUserList)
					.data("data", item);
			</c:if>
		</c:forEach>
		
		var $addrGroupTypeList = $("select[name=addrGroupTypeList]").children().remove().end();
		<c:forEach var="item" items="${poll.targetGroupTypeList}">
			<c:if test="${item.targetType == '1'}">
				var item = {
					type : "group",
					code : "${item.groupId}",
					name : "${user.localeCode == portal.defaultLocaleCode ? item.groupName : item.groupEnglishName}",
					parent : ""
				};
				
				$.tmpl(iKEP.template.groupOption, item).appendTo($addrGroupTypeList)
					.data("data", item);
			</c:if>
		</c:forEach>

		selectRemove("addrUserList", "userNameCount");	
		selectRemove("addrGroupTypeList", "groupTypeNameCount");	
			
    	
		$jq("#insertButton").click(function() {
			btnFlag = "submit";
			$("#searchForm").trigger("submit");
		});    	
		//default
		var startDate = "<spring:bind path='poll.startDate'>${status.value}</spring:bind>";
		var endDate = "<spring:bind path='poll.endDate'>${status.value}</spring:bind>";
		
		startDate = "<ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>";
		endDate = "<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>";
		
		var chartType = "${poll.chartType}";
		if ( chartType == '' ) {
			$jq("input[name='chartType']").filter('input[value=0]').attr("checked", true);
		}
		var permissionType = "${poll.permissionType}";
		if ( permissionType == '' ) {
			$jq("input[name='permissionType']").filter('input[value=0]').attr("checked", true);
		} else {
			$jq("input[name='permissionType']").filter('input[value='+permissionType+']').attr("checked", true);
		}	
		changeRadio(permissionType);
		
		//default
    	$("#startDate").datepicker({
    		onSelect: function(date, event) {
    	        var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	        $("#endDate").datepicker("option", "minDate", $(this).val());
    	    }
        })
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });

    	
    	$("#endDate").datepicker({
        	 dateFormat: "yy.mm.dd"
        	, minDate:$("#startDate").val(),
    		onSelect: function(date, event) {
    	        var arrDate = date.split("-");
    	        var objDate = new Date(arrDate[0], (arrDate[1]-1), parseInt(arrDate[2], 10) + 6);
    	        event.input.next("input.datepicker:eq(0)").val(objDate.getFullYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate());
    	    }
        	})
        .next().eq(0).click(function() { $(this).prev().eq(0).datepicker("show"); });


        $jq('#answer').keypress( function(event) {
        	var smsPhoneno = $jq('#smsPhoneno').val();
        		if(event.which == '13') {
        			setAnswer();
        		}
    	});	
        
		$("#addAnswer").click(function() {
			setAnswer();
		}); 
		//문항 추가
		setAnswer = function() {
			var answer = $jq("input[name='answer']").val();
			if ( answer == "" ) {
				alert("<ikep4j:message key='NotEmpty.poll.answer' />");
				return;
			}
			var $sel = $jq("#answerTitles");
			var answerCnt = $sel.children().length;
			if ( answerCnt > 9 ) {
				alert("<ikep4j:message key='Max.poll.answerCount' />");
				return;
			}

			$jq("<option>"+answer+"<\/option>").appendTo($sel);
			$jq("input[name='answer']").val('');			
		};

		$jq('#answerDelete').click(function(event) { 
			selectRemove("answerTitles", "");	
		});
		
		$jq('#btnPreview').click(function(event) { 
			btnFlag = "preview";
			$jq("#searchForm").submit();
		});		
		
		$jq('#btnMyList').click(function(event) { 
			if ( "${mode}" == "admin" ) {
				document.location.href="pollAdminList.do";
			} else {
				document.location.href="pollList.do?isComplete=0";	
			}
		});			
		
		$jq("#btnAddrControl").click(function() {
			var items = [];
			var $sel = $jq("select[name=addrUserList]");
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});
			iKEP.showAddressBook(showAddressBookCallback, items, {selectElement:$sel,selectType:"user",isAppend:false});
		});		

        $jq('#userName').keypress( function(event) { 
			iKEP.searchUser(event, "Y", setUser); 
		});
		
		$jq('#userSearchBtn').click( function() { 
			$jq('#userName').trigger("keypress");
		});
        
		
		$jq('#groupTypeName').keypress( function(event) { 
			iKEP.searchGroup(event, "Y", setGroupType); 
		});
		
		$jq('#groupTypeSearchBtn').click( function() { 
			$jq('#groupTypeName').trigger("keypress");
		});	
		
		$jq('#groupTypeSearchBtnAll').click(function(event) {
			var items = [];
			var $sel = $jq("select[name=addrGroupTypeList]");
			$jq.each($sel.children(), function() {
				items.push($jq.data(this, "data"));
			});
			
			var callback = function(data) {
		    	$jq("#groupTypeNameCount").html($jq("select[name=addrGroupTypeList]").children().length);
		    };
			iKEP.showAddressBook(callback, items, {selectElement:$sel, selectType:"GROUP", isAppend:false});
		});	
		
		//삭제 이벤트
		$jq('#userNameDelete').click(function(event) { 
			selectRemove("addrUserList", "userNameCount");	
		});
		
		$jq('#groupTypeNameDelete').click(function(event) { 
			selectRemove("addrGroupTypeList", "groupTypeNameCount");
		});
		
	 	// 조직도 popup후 callback
	    var showAddressBookCallback = function(data) {
	    	var $sel = $jq("select[name=addrUserList]");
	    	$jq("#userNameCount").html($sel.children().length);
	    };		
		
	    setUser = function(data) {	// data = {type, id, empNo, userName, jobTitleName, group, teamName, email, mobile}
			var $sel = $jq("select[name=addrUserList]");
			var selectCheck;
			
			if(data.length > 0) {
				$jq(data).each(function(index) {
					var userData = this;
					selectCheck = true;
					$jq.each($sel.children(), function() {
						if(this.value == userData.id) {
							selectCheck = false;
						}
					});
					
					if(selectCheck) {
						userData.searchSubFlag = false;
						$.tmpl(iKEP.template.userOption, userData).appendTo($sel)
							.data("data", userData);
					} 
				});
				
				$jq("#userNameCount").html($sel.children().length);
				$jq("#userName").val('');
			} else {
				alert("<ikep4j:message pre='${preForm}' key='userEmpty' />");
			}
		};
		
		setGroupType = function(data) {	// data = {type, code, name, parent}
			var $sel = $jq("select[name=addrGroupTypeList]");
			var str = "";
			var selectCheck;
			
			if(data.length > 0) {
				$jq(data).each(function(index) {
					var groupData = this;
					
					selectCheck = true;
					$jq.each($sel.children(), function() {
						if(this.value == groupData.code) {
							selectCheck = false;
						}
					});
					
					if(selectCheck) {
						$.tmpl(iKEP.template.groupOption, this).appendTo($sel)
							.data("data", groupData);
					}
				});
				
				$jq("#groupTypeNameCount").html($sel.children().length);
				$jq("#groupTypeName").val('');
			} else {
				alert("<ikep4j:message pre='${preForm}' key='groupEmpty' />");
			}
		};
		
		selectRemove = function (selectName, countName){
			var $sel = $jq("select[name="+selectName+"]");
			
			if($sel) {
				$jq.each($sel.children(), function() {
					if(this.selected) {
						$(this).remove();
					}
				});
				if ( countName != '' ) {
					$jq("#"+countName).html($sel.children().length);
				}
			}
		};

		var answerFlag = false;
	    //validation
	    var validOptions = {
			rules : {
				question :	{
					required : true,
					maxlength  : 100
				}
	    		,startDate : { required:true }
	    		,endDate : { required:true }
				,answer :	{
					maxlength  : 100
				}
			},
			messages : {
				question : {
					required : "<ikep4j:message key='NotEmpty.poll.question' />",
					maxlength : "<ikep4j:message key='Max.poll.question' arguments='100'/>",
					direction:"top"
				}	
				,startDate   : {
		            required  : "<ikep4j:message key='NotEmpty.poll.startDate' />"
		        }
		        ,endDate : {
		            required  : "<ikep4j:message key='NotEmpty.poll.endDate' />"
		        }			
				,answer : {
					maxlength : "<ikep4j:message key='Max.poll.answerTitle' arguments='100'/>",
					direction:"top"
				}	
			},
			notice : {
				question : "<ikep4j:message key='NotEmpty.poll.question'/>"
				,answer : "<ikep4j:message key='NotEmpty.poll.answerTitle'/>"
			},
			submitHandler : function(form) {
				if($jq('#answerTitles option').length < 1) {
					alert("<ikep4j:message key='NotEmpty.poll.answerTitle' />");
					return;
				} 
				var $sel = $jq("select[name='answerTitles']");
				var answerCount = $sel.children().length;				
				var chartType = $jq("input[name='chartType']:radio:checked").val();
				if ( chartType == '2' ) {
					if ( answerCount > 2 ) {			
						alert("<ikep4j:message pre='${preForm}' key='NotValidanswerCount' />");
						return false;
					}
				}	
				var permissionType = $jq("input[name=permissionType]:radio:checked").val();
				var userNameCount = $jq("#userNameCount").text();
				var groupTypeNameCount = $jq("#groupTypeNameCount").text();
				if ( permissionType == '1' ) {
					if (userNameCount == 0 && groupTypeNameCount == 0) {
						alert("<ikep4j:message pre='${preForm}' key='permissionTypeError' />");
						return false;
					}
				}				
							
				var requestChannel = "0";
				$jq("input[name=requestChannel]").val(requestChannel);
				if ( btnFlag == "submit" ) {
					$jq('#searchForm').find('select[name=answerTitles] option').each(function(){
						this.selected = true;
					});
					$jq('#searchForm').find('select[name=addrUserList] option').each(function(){
						this.selected = true;
					});	
					$jq('#searchForm').find('select[name=addrGroupTypeList] option').each(function(){
						this.selected = true;
					});
					
					form.submit();
				} else { //preview
					$("#previewPollBlock").show();
					previewPoll();						
				}
			}
		};	      
	    new iKEP.Validator("#searchForm", validOptions);		
		
	});
		
	previewPoll = function () {
		$jq("#previewAnswerList").children().remove();
		var question = $jq("input[name=question]").val();
		var startDate = $jq("input[name=startDate]").val();
		var endDate = $jq("input[name=endDate]").val();
		var permissionType = $jq("input[name=permissionType]:radio:checked").val();
		startDate = replaceAll(startDate,"-",".");
		endDate = replaceAll(endDate,"-",".");
		$jq("#previewQuestion").text(question);
		$jq("#previewStartDate").text(startDate);
		$jq("#previewEndDate").text(endDate);

		if ( permissionType == '0' ) {
			$jq("#previewPermissionType").text("<ikep4j:message pre='${preForm}' key='previewPermissionType01' />");
		} else {
			$jq("#previewPermissionType").text("<ikep4j:message pre='${preForm}' key='previewPermissionType02' />");
		}
		var $sel = $jq("select[name=answerTitles]");
		var index = 1;
		$jq.each($sel.children(), function() {
			var answer = $jq(this).val();
			$jq("#previewAnswerList").append("<li>("+index+") "+answer+"<\/li>");
			index++;
		});			
		//graph
		var $sel = $jq("select[name='answerTitles']");
		var answerCount = $sel.children().length;
		var barData = [];
	    var pieData = [];		
	    var ticks = []; 
	    
	    for ( i =0; i< answerCount; i++ ) {
	    	ticks[i] = "("+eval(i+1)+")";
	    	barData[i] = eval(i*2+1);
	    	pieData[i] = [ticks[i],eval(i*2+1)];
	    }
	    var chartType = $jq("input[name='chartType']:radio:checked").val();        
		if ( chartType == '0' ) {	 
			$("#previewEtcChart").hide();
			$("#previewPieChart").hide();
			$("#previewBarChart").show();
			barChart("chart0", barData, ticks);
		} else if ( chartType == '1' ) {
			$("#previewEtcChart").hide();
			$("#previewBarChart").hide();
			$("#previewPieChart").show();
			pieChart("chart1", pieData);
		} else {
			if ( answerCount > 2 ) {			
				alert("<ikep4j:message pre='${preForm}' key='NotValidanswerCount' />");
				return false;
			}			
			
			$("#previewBarChart").hide();
			$("#previewPieChart").hide();
			$("#previewEtcChart").show();
			etcChart("2", barData);
		}		
	};
	
	selectRemove = function (selectName, countName){
		var $sel = $jq("select[name="+selectName+"]");
		
		if($sel) {
			$jq.each($sel.children(), function() {
				if(this.selected) {
					$(this).remove();
				}
			});
			
			if ( countName != '' ) {
				$jq("#"+countName).html($sel.children().length);
			}
		}
	};
	
	barChart = function(el, data, ticks) {
		$jq("#"+el).empty();
		$jq.jqplot(el, [data], {
			grid: {
   	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false	
   	    	},   	    	
   	    	seriesColors: ["#363636"],
   	    	seriesDefaults:{
   	    		shadow: true,   // show shadow or not.
		        shadowAngle: 70,    // angle (degrees) of the shadow, clockwise from x axis.
   	    		renderer:$.jqplot.BarRenderer,  // 차트 형태
   	    		rendererOptions: {
   	    			barWidth: 20
   				},
   	    		pointLabels: { show: true }     // 포인트 표시 여부
   	    	},
   	    	axes: {
   	    		xaxis: {
   	    			renderer: $.jqplot.CategoryAxisRenderer,  // x축 표시
   	    			ticks: ticks,                // x축 범주 표시
   	    			tickOptions:{
		                markSize:0
		            }
   	    		},
   	    		yaxis: {
   	    			min:0,
	                showTicks: false,
	                max:getMax(data),
		    		tickOptions:{formatString:'%.0f'}
	        	}
   	    	}
        });
		
	};
	
	pieChart = function(el, data) {
		$jq("#"+el).empty();
		$jq.jqplot(el, [data], {
			grid: {
	    		drawGridlines: false,
   	    		background: '#ffffff',
				borderColor: '#555',
		        borderWidth: 0,
		        shadow: false
	    	},
	    	title:'',   // 차트 타이틀
	    	seriesColors:['#93c8fb', '#ff66fd', '#fdf701', '#99cc66', '#ffcc66','#d099fe','#2dc8fe','#fe9798','#d3ca6b'],
	    	seriesDefaults:{
	    		shadow: false,   // show shadow or not.
		        shadowAngle: 45,    // angle (degrees) of the shadow, clockwise from x axis.
	    		renderer:$.jqplot.PieRenderer,
	    		rendererOptions: {
	    			sliceMargin:2,   // 파이 조각 표시 시 마진값
	    			showDataLabels: true,
	    			dataLabelThreshold: 1,
	                dataLabelFormatString: '%.0f%%'
	    		}
	    	},
	    	legend: {
	    		show: true,
	    		location: 'e',
	    		escapeHTML:true
	    	}
        });
	};	

	etcChart = function(el, data) {  	
		var yesPercent = 0;
		var noPercent = 0;
		var totalCnt = 0;
		for(var i=0; i<data.length; i++) {
			totalCnt += data[i];
		}		
		yesPercent = data[0]/totalCnt*100;
		yesPercent = Math.round(yesPercent*10)/10;
		noPercent = 100 - yesPercent;

		$("#chart"+el).progressbar({  
  			value: yesPercent 
 		});			
		$("#yes"+el).text( "<ikep4j:message pre='${preList}' key='yes' /> : "+yesPercent + "%");
		$("#no"+el).text( "<ikep4j:message pre='${preList}' key='no' /> : "+noPercent+"%");
	};	
	
	changeRadio = function (value) {
		if(value == 1) { 		
			$jq("#permissionDetailTr").css({display:""});
			<c:if test="${poll.pollId == null}">
				$jq('#searchForm').find('select[name=addrUserList] option').each(function(){
					this.selected = true;
				});		
				selectRemove("addrUserList", "userNameCount");				
			</c:if>
			<c:if test="${poll.pollId == null}">			
				$jq('#searchForm').find('select[name=addrGroupTypeList] option').each(function(){
					this.selected = true;
				});	
				selectRemove("addrGroupTypeList", "groupTypeNameCount");	
			</c:if>			
		} else if(value == 0){
			$jq("#permissionDetailTr").css({display:"none"});
		}
	};
})(jQuery); 
	function getMax(arr) {
		 var max = arr[0]; 
		 for(var i=1; i<arr.length; i++) 
		  if(max < arr[i]) 
		   max = arr[i]; 
		 return (max+max*30/100); 
	}
	function createPoll() {
	
			$jq('#searchForm').find('select[name=answerTitles] option').each(function(){
				this.selected = true;
			});
			$jq('#searchForm').find('select[name=addrUserList] option').each(function(){
				this.selected = true;
			});	
			$jq('#searchForm').find('select[name=addrGroupTypeList] option').each(function(){
				this.selected = true;
			});			
			var requestChannel = "0";
	
			$jq("input[name=requestChannel]").val(requestChannel);
	
			$jq("#searchForm").submit();
	
	}
	
	function replaceAll(str, sep, pad) {
		while(str.indexOf(sep)>-1)
		{
			str = str.replace(sep, pad);
		}
		return str;
	}

//]]>
</script>

<form id="searchForm" name="searchForm" method="post" action="<c:url value='/support/poll/createPoll.do'/>">
<input type="hidden" name="pollId" value="${poll.pollId}" />
<input type="hidden" name="status" value="0" />
<input type="hidden" name="answerCount" value="0" />
<input type="hidden" name="requestChannel" value="0" />

<h1 class="none">contents</h1>

<!--pageTitle Start-->
<div id="pageTitle">
	<h2><ikep4j:message pre='${preSub}' key='addPoll' /></h2>
	<!--  div id="pageLocation">
		<ul>
			<li class="liFirst">Home</li>
			<li><ikep4j:message key='message.support.poll.list.title' /></li>
	 		<li class="liLast"><ikep4j:message pre='${preSub}' key='addPoll' /></li>
		</ul>
	</div -->
</div>
<!--//pageTitle End-->				

<!--blockDetail Start-->
<div class="blockDetail">
	<table summary="new group">
		<caption></caption>
		<tbody>
			<tr>
				<th scope="row" width="18%"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='question' /></th>
				<td width="82%"><div><input type="text" name="question" class="inputbox w100" title="" value="${poll.question }"/></div></td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='date' /></th>
				<td>
					<div><input type="text" class="inputbox datepicker" readonly="readonly" id="startDate" name="startDate" title="<ikep4j:message pre='${preForm}' key='startDate' />" value="<ikep4j:timezone date="${poll.startDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" /> ~
					<input type="text" class="inputbox datepicker" readonly="readonly" id="endDate" name="endDate" title="<ikep4j:message pre='${preForm}' key='endDate' />" value="<ikep4j:timezone date="${poll.endDate}" pattern="message.support.poll.timezone.dateformat.type" keyString="true"/>" size="9" /> <img class="dateicon" src="<c:url value="/base/images/icon/ic_cal.gif"/>" alt="datepicker" style="cursor:pointer;" /></div>								
				</td>
			</tr>
			<tr>
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='answer' /></th>
				<td>
					<div>
						<input name="answer" id="answer" title="<ikep4j:message pre='${preForm}' key='answer' />" class="inputbox" type="text" size="20" />
						<a class="button_ic" href="#a" id="addAnswer"><span><img src="<c:url value="/base/images/icon/ic_btn_download.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='add' /></span></a>
						<div style="padding-top:4px;">
							<select name="answerTitles" id="answerTitles" size="5" multiple="multiple" class="multi w80" title="<ikep4j:message pre='${preForm}' key='answer' />" >
								<option value=""><%-- 한나도 없을 경우 웹표준 에러로 인해 설정해둠. --%></option>
							</select>										
							<a class="button_ic valign_bottom" href="#a"><span id="answerDelete"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='delete' /></span></a>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preForm}' key='previewPermissionType' /></th>
				<td>
					<div class="mb5">
						<label><input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='previewPermissionType01' />" name="permissionType" value="0" onclick="changeRadio(0)"/><ikep4j:message pre='${preForm}' key='previewPermissionType01' /></label>&nbsp;
						<label><input type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='previewPermissionType02' />" name="permissionType" value="1"  onclick="changeRadio(1)"/><ikep4j:message pre='${preForm}' key='previewPermissionType02' /></label>
					</div>
				</td>
			</tr>							
			<tr id="permissionDetailTr">
				<th scope="row"><span class="colorPoint">*</span><ikep4j:message pre='${preForm}' key='previewPermissionTypeEtc' /></th>
				<td>
					<!--blockDetail Start-->
					<div class="blockDetail border_t1">
						<table summary="insert">
							<caption></caption>
							<tbody>
								<tr>
									<th width="13%" scope="row"><ikep4j:message pre='${preForm}' key='target' /></th>
									<td>
										<input name="userName" id="userName" title="userName" class="inputbox" type="text" size="20" />
										<a class="button_ic" href="#a"><span id="userSearchBtn"><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='search' /></span></a>
										<a class="button_ic" href="#a" id="btnAddrControl"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='address' /></span></a>
										<div style="padding-top:4px;">
											<select name="addrUserList" size="5" multiple="multiple" class="multi w70" title="addrUserList" >
												<option value=""><%-- 한나도 없을 경우 웹표준 에러로 인해 설정해둠. --%></option>
											</select> 
											<a class="button_ic valign_bottom" href="#a"><span id="userNameDelete"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='delete' /></span></a>
											<span class="totalNum_s"><ikep4j:message pre='${preForm}' key='total' /> <span id="userNameCount">0</span><ikep4j:message pre='${preForm}' key='people' /></span>
										</div>								
									</td>
								</tr>
								<tr>
									<th scope="row"><ikep4j:message pre='${preForm}' key='group' /></th>
									<td>
										<input name="groupTypeName" id="groupTypeName" title="groupTypeName" class="inputbox" type="text" size="20" />
										<a class="button_ic" href="#a" name="groupTypeSearchBtn" id="groupTypeSearchBtn"><span><img src="<c:url value="/base/images/icon/ic_btn_search.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='search' /></span></a>
										<a class="button_ic" href="#a" name="groupTypeSearchBtnAll" id="groupTypeSearchBtnAll"><span><img src="<c:url value="/base/images/icon/ic_btn_address.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='address' /></span></a>
										<div style="padding-top:4px;">
											<select name="addrGroupTypeList" size="5" multiple="multiple" class="multi w70" title="addrGroupTypeList" >
												<option value=""><%-- 한나도 없을 경우 웹표준 에러로 인해 설정해둠. --%></option>
											</select>	
											<a class="button_ic valign_bottom" href="#a"><span id="groupTypeNameDelete"><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="" /><ikep4j:message pre='${preForm}' key='delete' /></span></a>
											<span class="totalNum_s"><ikep4j:message pre='${preForm}' key='total' /> <span id="groupTypeNameCount">0</span><ikep4j:message pre='${preForm}' key='group' /></span>
											</div>								
										</td>
								</tr>	
								</tbody>
						</table>
					</div>
					<!--//blockDetail End-->
				
				</td>
			</tr>
			<tr>
				<th scope="row"><ikep4j:message pre='${preForm}' key='chart' /></th>
				<td>
					<input name="chartType" type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='chart01' />" value="0" <c:if test="${poll.chartType eq '0'}">checked="checked"</c:if>/> <ikep4j:message pre='${preForm}' key='chart01' />&nbsp;&nbsp;
					<input name="chartType" type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='chart02' />" value="1" <c:if test="${poll.chartType eq '1'}">checked="checked"</c:if> /> <ikep4j:message pre='${preForm}' key='chart02' />&nbsp;&nbsp;
					<input name="chartType" type="radio" class="radio" title="<ikep4j:message pre='${preForm}' key='chart03' />" value="2" <c:if test="${poll.chartType eq '2'}">checked="checked"</c:if> /> <ikep4j:message pre='${preForm}' key='chart03' />&nbsp;&nbsp;																		
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!--//blockDetail End-->

<!--blockButton Start-->
<div class="blockButton"> 
	<ul>
		<li><a class="button" href="#a"><span id="btnPreview"><ikep4j:message pre='${preForm}' key='preview' /></span></a></li>
		<li><a class="button" id="insertButton" name="insertButton" href="#a"><span>등록</span></a></li>
		<li><a class="button" href="#a"><span id="btnMyList"><ikep4j:message pre='${preForm}' key='list' /></span></a></li>												
	</ul>
</div>
<!--//blockButton End-->	

<!--online_poll Start-->
<div class="blockTableRead online_poll" id="previewPollBlock" style="display:none;">
	<h3 class="none">Question</h3>				
	<div class="blockTableRead_t">			
		<p id="previewQuestion"></p>
		<div class="summaryViewInfo">
			<span class="ty1" id="previewPermissionType"></span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="" />
			<span class="summaryViewInfo_name"><ikep4j:message pre='${preForm}' key='userName' /> :
			     <c:choose>
			      <c:when test="${user.localeCode == portal.defaultLocaleCode}">
			       ${user.userName}
			      </c:when>
			      <c:otherwise>
			       ${user.userEnglishName}
			      </c:otherwise>
			     </c:choose>								
			</span>
			<img src="<c:url value="/base/images/theme/theme01/basic/bar_pageNum.gif"/>" alt="" />
			<span><ikep4j:message pre='${preAdmin}' key='date' /> : <strong id="previewStartDate"></strong><strong>~</strong><strong id="previewEndDate"></strong></span>
		</div>
	</div>
	
	<table width="100%" summary="" border="0">
		<caption></caption>
		<tbody>
			<tr>
				<td valign="top">						
					<div class="online_poll_list">						
						<ul  id="previewAnswerList"><li></li>																				
						</ul>
					</div>
				</td>
				<td width="250" valign="top" >
				<div class="online_poll_graph1" id="previewGraph">
					<div id="previewBarChart" style="display:none;margin-right:10px;">
						<div id="chart0" style='margin:10px;padding-bottom:10px;width:240px; height:215px;border:1px solid #dfdfdf;'></div>
					</div>	
					<div id="previewPieChart" style="display:none;">
						<div id="chart1" style='margin-top:10px;width:280px; height:250px;'></div>
					</div>																
					<div id="previewEtcChart" style="display:none;">
							<div class="online_poll_graph">											
								<div id="chart2" class="voteRange">
									<div class="bar_l"></div>
									<div class="bar_r"></div>
								</div>
								<div class="online_poll_day">
									<span id="yes2"></span>
									<span class="lastday" id="no2"></span>
								</div>
							</div>			
					</div>
																			
				</div>
				</td>
			</tr>
		</tbody>
	</table>
</div>
<!--//online_poll End-->
</form>