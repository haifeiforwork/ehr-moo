<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/base/common/error.jsp"  pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
<!--
 
(function($) {
	
	// 처음 로딩시  고정 메뉴 포스팅 글 조회 
	getPostingDateList = function(searchDate) {
		
		var alarmDayList = [];
		
		$jq.ajax({
		    url : '<c:url value="/socialpack/socialblog/getSocialBlogPostingDate.do"/>',
		    data : {'blogOwnerId':'${blogOwnerId}','searchDate':searchDate},
		    type : "get",
		    success : function(result) {
		    	$.each(result, function(index, value){
		    		alarmDayList.push( value );
		    	});
		    	$("#datepicker").datepicker("addAlarmDays", alarmDayList);
		    }
		});
	};
	
	$(document).ready(function(){		
		$("#datepicker").datepicker({
			inline: true,
			dateFormat: 'yy.mm.dd',
			create: function(event, ui) { 
				getPostingDateList();
			},
			onChangeMonthYear: function(year, month, inst) {
				if( month < 10 ){
					month = "0" + month
				}
				getPostingDateList( year+"."+month );
			},
			onSelect: function(date, event) {
				$(document).scrollTop(0);
				getSearchDateBlogPosting(date);
			}
		});
		
	});
	
})(jQuery);  
//-->
</script>
<div id="datepicker"></div>