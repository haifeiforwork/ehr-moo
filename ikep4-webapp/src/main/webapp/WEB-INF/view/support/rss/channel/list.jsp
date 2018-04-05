<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channel.header" /> 
<c:set var="preList"    value="ui.support.rss.channel.list" />
<c:set var="preDetail"  value="ui.support.rss.channel.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>


<script type="text/javascript" language="javascript">
<!-- 
var dialogWindow;
function fnCaller(param, dialog){
	dialogWindow = dialog;
}

(function($) {
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getChannelList.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			success : function(result) {         
				$jq("#listDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});       
		
	};
	
	getView = function(channelId, tr) {
		
		var selectClassName = "bgSelected";
		$jq(tr).parent().parent().children().removeClass(selectClassName).end().end().end().addClass(selectClassName);
		
		$jq.ajax({     
			url : '<c:url value="createChannel.do" />',     
			data : {channelId: channelId},     
			type : "get",     
			success : function(result) {     
				$jq("#viewDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});   
		
	};
	
	submitForm = function() {
		
		$jq("#channelForm").submit();
		
	};
	
	saveForm = function() {
		
		if($jq("input[name=channelCheck]").val() == "") {
			alert('<ikep4j:message pre='${preMessage}' key='channelCheck.empty' />');
			return;	
		}
				
		$jq.post('<c:url value="/support/rss/channel/createChannel.do" />', $jq("#channelForm").serialize())
		.success(function(data) {  
			
			var json = $jq.parseJSON(data);
			if(json.status=="success") { 
				newForm();
				getList();
				alert(json.message);
			}
			else {
				alert(json.message);
			}
		})
		.error(function(event, request, settings) { alert("error"); });   
			
	};
	
	deleteForm = function() {
		
		if($jq("input[name=channelId]").val() == "") {
			alert('<ikep4j:message pre='${preMessage}' key='channelId.empty' />');
			return;	
		}
		
		if(confirm('<ikep4j:message pre='${preMessage}' key='channel.delete' />')) {
			
			$jq.ajax({     
				url : '<c:url value="removeChannel.do" />',     
				data : $jq("#channelForm").serialize(),     
				type : "post",     
				success : function(result) {   
					newForm();
					getList();
				},
				error : function(event, request, settings) { alert("error"); }
			});  
		}
		
	};
	
	newForm = function() {
		
		$jq("input[name=channelId]").val("");
		$jq("input[name=channelTitle]").val("");
		$jq("input[name=channelUrl]").val("");
		$jq("input[name=channelCheck]").val("");
		
	};
	
	changeSort = function(channelId, sortOrder) {
		
		$.ajax({     
			url : '<c:url value="changeSort.do" />',     
			data : {channelId: channelId, sortOrder: sortOrder},     
			type : "post",     
			success : function(result) {         
				getList();
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};

	
	checkChannel = function() {
		
		if($jq("input[name=channelUrl]").val()==''){
			alert("<ikep4j:message key="NotEmpty.channel.channelUrl" />");
			return;
		}
		
		$.ajax({     
			url : '<c:url value="checkChannelExist.do" />',     
			data : {channelUrl: $jq("input[name=channelUrl]").val(), channelId: $jq("input[name=channelId]").val()},     
			type : "post",     
			success : function(result) {     
				
				if(result == 'dupError') {
					alert('<ikep4j:message pre='${preMessage}' key='channelCheck.duplicated' />');
				}
				else if(result == 'urlError') {
					alert('<ikep4j:message pre='${preMessage}' key='channelCheck.failed' />');
				}
				else {
					alert('<ikep4j:message pre='${preMessage}' key='channelCheck.success' />');
					$jq("input[name=channelTitle]").val(result);
					$jq("input[name=channelCheck]").val("ok");
				}
			},
			error : function(event, request, settings) { alert("error"); }
		});  	
		
	};
	
	// 주의 : 글로벌 함수는 아래와 같이 글로벌 변수에 무명 함수를 assign해서 생성한다. 
	//정렬 조건 변경 함수
	sort = function(sortColumn, sortType) {
		$jq("input[name=sortColumn]").val(sortColumn);
		
		if( sortType == 'ASC') {
			$jq("input[name=sortType]").val('DESC');	
		} else {
			$jq("input[name=sortType]").val('ASC');
		}
		getList();
	};
	
	closeForm = function() {
		alert('gg');
		dialogWindow.close();
	};
	
	setRow = function(channelId, obj) {
		
		$jq("#sortTable").children().each(function(index, item) { 
			$jq(item).removeClass();
		});
		
		var index = $jq(obj).index();
		var liObj = $jq("#sortTable").children().get(index);
		$jq(liObj).addClass("bgSelected");
		
		getView(channelId, obj);
	};
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		getList();
		getView();
	});
	
})(jQuery);  
//-->	
</script>

		<!--popup Start-->
		<div id="popup">
				
				<div class="blockBlank_10px"></div>	
				
				<!--popup_contents Start-->
				<div id="popup_contents">
				
					&nbsp;&nbsp;&nbsp;<span><ikep4j:message pre='${preMessage}' key='maxSize' /></span>
				
				
				
					<!--blockListTable Start-->
					<div class="blockListTable">
					
										
					
							<div id="listDiv" style="height:300px;overflow:auto"></div>
							
							
					</div>
					<!--//blockListTable End-->	
		
									
					
					<div class="blockBlank_10px"></div>
	 
					<!--subTitle_2 Start-->
					<div class="subTitle_2 noline">
						<h3><ikep4j:message pre='${preDetail}' key='main.title' /></h3>
					</div>
					<!--//subTitle_2 End-->
					
					<!--blockDetail Start-->
					<div class="blockDetail">
											
					
							<div id="viewDiv"></div>
							
							
					</div>
					<!--//blockDetail End-->
															
					<!--blockButton Start-->
					<div class="blockButton"> 
						<ul>
							<li><a class="button" href="#a" onclick="javascript:newForm()"><span><ikep4j:message pre='${preButton}' key='new' /></span></a></li>
							<li><a class="button" href="#a" onclick="javascript:submitForm()"><span><ikep4j:message pre='${preButton}' key='create' /></span></a></li>
							<li><a class="button" href="#a" onclick="javascript:deleteForm()"><span><ikep4j:message pre='${preButton}' key='delete' /></span></a></li>
						</ul>
					</div>
					<!--//blockButton End-->
				
				
				</div>
				<!--//popup_contents End-->
			 
				<!--popup_footer Start-->
				<div id="popup_footer"></div>
				<!--//popup_footer End-->
				
					
		
		</div>
		<!--//popup End-->
		
	