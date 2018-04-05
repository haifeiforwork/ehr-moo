<%@ include file="/base/common/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 


<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="preHeader"  value="ui.support.rss.channelitem.header" /> 
<c:set var="preList"    value="ui.support.rss.channelitem.list" />
<c:set var="preDetail"  value="ui.support.rss.channelitem.detail" />
<c:set var="preButton"  value="ui.support.rss.button" /> 
<c:set var="preMessage" value="ui.support.rss.message" />
<c:set var="preSearch"  value="ui.communication.common.searchCondition" /> 
 <%-- 메시지 관련 Prefix 선언 End --%>

 
<script type="text/javascript" language="javascript">
<!-- 

var listType;

(function($) {
	
	getList = function() {
		
		$jq.ajax({     
			url : '<c:url value="getChannelItemList.do" />',     
			data :  $jq("#searchForm").serialize(),     
			type : "post",     
			loadingElement : {container:"#listDiv"}, 
			success : function(result) {  
				$jq("#listDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		});  
		
	};
	
   getListByCategory = function(categoryId) {
        
        if(categoryId != "") {
            $jq.ajax({     
                url : '<c:url value="/support/rss/channelCategory/getgetCategoryTitle.do" />',     
                data : {categoryId:categoryId},     
                type : "post",     
                success : function(result) {  
                    $jq("#titleSpan").html(result.channelTitle);
                    $jq("#titleSpan2").html(result.channelTitle);
                },
                error : function(event, request, settings) { alert("error"); }
            });  
        }
        else {
            $jq("#titleSpan").html('<ikep4j:message pre='${preList}' key='all' />');
            $jq("#titleSpan2").html('<ikep4j:message pre='${preList}' key='all' />');
        }
        
        $jq.ajax({     
            url : '<c:url value="getChannelItemList.do" />',     
            data : {categoryId:categoryId},     
            type : "post",     
            loadingElement : {container:"#listDiv"}, 
            success : function(result) {  
                $jq("#listDiv").html(result);
            },
            error : function(event, request, settings) { alert("error"); }
        });
        
        
        listType = 'CT'
        
    };
	
	getListByChannel = function(channelId) {
		
		if(channelId != "") {
			$jq.ajax({     
				url : '<c:url value="/support/rss/channel/getChannelTitle.do" />',     
				data : {channelId:channelId},     
				type : "post",     
				success : function(result) {  
					$jq("#titleSpan").html(result.channelTitle);
					$jq("#titleSpan2").html(result.channelTitle);
				},
				error : function(event, request, settings) { alert("error"); }
			});  
		}
		else {
			$jq("#titleSpan").html('<ikep4j:message pre='${preList}' key='all' />');
			$jq("#titleSpan2").html('<ikep4j:message pre='${preList}' key='all' />');
		}
		
		$jq.ajax({     
			url : '<c:url value="getChannelItemList.do" />',     
			data : {channelId:channelId},     
			type : "post",     
			loadingElement : {container:"#listDiv"}, 
			success : function(result) {  
				$jq("#listDiv").html(result);
			},
			error : function(event, request, settings) { alert("error"); }
		}); 
		
		listType = 'CH'
		
	};
	
   getCategoryList = function(categoryId) {
       
        $jq.ajax({     
            url : '<c:url value="/support/rss/channelCategory/listCategory.do" />',     
            data : {categoryId:categoryId},
            type : "post",     
            loadingElement : {container:"#listDiv"}, 
            success : function(result) {  
                $jq("#listDiv").html(result);
            },
            error : function(event, request, settings) { alert("error"); }
        });         
    };
	
	
	openPopupWindow = function(url, winWidth, winHeight, winName) {
		
		var windowName = winName || "_blank";
		var width = winWidth || 200;
		var height = winHeight || 200;

		var windowName = window.open(
			url,
			windowName,
			"width=" + width + ", height=" + height + ", toolbar=no, menubar=no, scrollbars=yes, resizable=yes, copyhistory=no"
		);
		
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
	
	goStart = function() {
		
		if (confirm("<ikep4j:message pre='${preMessage}' key='reload.ConfirmMessage' />")){   
		
			//$jq("#startSpan").hide();
			//$jq("#stopSpan").show();
			$jq("#startRefresh").hide();
			$jq("#stopRefresh").show();
			
			if(listType = 'CH')
			{
				$jq.ajax({     
					url : '<c:url value="/support/rss/channel/readChannel.do"/>',     
					data : {channelId:$jq("input[name=channelId]").val(),categoryId:$jq("input[name=categoryId]").val()},     
					type : "post",     
					success : function(result) {   
						$jq("#startRefresh").show();
						$jq("#stopRefresh").hide();
						getList();
					},
					error : function(event, request, settings) { alert("error"); }
				}); 
			}
			else
		    {
				$jq.ajax({     
	                url : '<c:url value="/support/rss/channel/readChannel.do"/>',     
	                data : {channelId:$jq("input[name=channelId]").val(),categoryId:$jq("input[name=categoryId]").val()},   
	                type : "post",     
	                success : function(result) {   
	                    $jq("#startRefresh").show();
	                    $jq("#stopRefresh").hide();
	                    getList();
	                },
	                error : function(event, request, settings) { alert("error"); }
	            }); 
		    }
		
		}
	
	};
	
	goStop = function() {

		$jq.ajax({     
			url : '<c:url value="/support/rss/channel/stopChannel.do"/>',     
			data : "",     
			type : "post",     
			success : function(result) {    
				
				$jq("#startRefresh").show();
				$jq("#stopRefresh").hide();
				//getList();
			},
			error : function(event, request, settings) { alert("error"); }
		});   
		
	};
	
	
	// onload시 수행할 코드
	$jq(document).ready(function() { 
		
		getList();
		
	});
	
})(jQuery);  

//-->
</script>
			
		
			
				
				<!--blockListTable Start-->
				<div class="blockListTable">
				
					
						
					<div id="listDiv"></div>
					
						
						
				</div>
				<!--//blockListTable End-->	
				
	
			
			



	