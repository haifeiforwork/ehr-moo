<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>


<script type="text/javascript">

(function($) {
	$(document).ready(function() {

		$("#divTab2").tabs();


	$("a.basicHistory").click(function(){
		$("#tabs-2").ajaxLoadStart();
		var url = "<c:url value='/support/customer/customerBasicInfo/modifyBasicHistoryView.do'/>"
		$("#tabs-2").load(url+".#test" );
		$("#blockComment").ajaxLoadComplete();
	});
		
	$("a.saveBasicInfo").click(function(){
		$("#basicInfoForm").trigger("submit");
	});
	
	
	
	new iKEP.Validator("#basicInfoForm",{
		
		rules : {},
		messages : {},
		
		submitHandler : function(form){
			
			if(confirm("저장하시겠습니까?")){
				$("body").ajaxLoadStart("button");
				var formData = $("#basicInfoForm").serialize();
				
				$.ajax({
					url : "<c:url value='/support/customer/customerBasicInfo/saveBasicInfo.do'/>",
					data : formData,
					type : "post",
					success : function() {
						alert("삭제가 완료되었습니다.");
					
						//location.href="<c:url value = '/support/customer/customerBasicInfo/modifyBasicInfoView.do?customerId=${mainSellingItem.customerId}&customer=${mainSellingItem.customer}'/>";
						
					},
					error : function(event, request, settings) { 
						alert("error");	
					}
				});	
				
			}
		}
	});
	
		
		
		
	});
})(jQuery);

</script>


<h1 class="none">컨텐츠영역</h1>
								
<!--tableTop Start-->
<div class="tableTop">
	<div class="tableTop_bgR"></div>
	<h2>고객리스트</h2>	
	<div class="clear"></div>
</div>
<!--//tableTop End-->

        <!--tab Start-->		
        <div id="divTab2" class="iKEP_tab_poll">
            <ul>
                <li><a href="#tabs-1" >기본정보</a></li>
                <li><a href="#tabs-2" >연혁</a></li>
                <li><a href="#tabs-3" >설비현황</a></li>
                <li><a href="#tabs-4" >주요임원</a></li>
                <li><a href="#tabs-5" >KEY MAN</a></li>
                <li><a href="#tabs-6" >인원현황</a></li>
                <li><a href="#tabs-7" >주거래선</a></li>
                <li><a href="#tabs-8" >관계사현황</a></li>
            </ul>
            <div>        
                <div id="tabs-1">1</div>
                <div id="tabs-2">2</div>
                <div id="tabs-3">3</div>
                <div id="tabs-4">4</div>
                <div id="tabs-5">5</div>            
                <div id="tabs-6">6</div>               
                <div id="tabs-7">7</div>                
                <div id="tabs-8">8</div>
            </div>				
        </div>		
        <!--//tab End-->