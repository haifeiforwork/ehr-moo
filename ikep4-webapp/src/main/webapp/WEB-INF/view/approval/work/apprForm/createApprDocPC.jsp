<%--
=====================================================
* 기능 설명 : text 설정
* 작성자    : approval
=====================================================
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%>

<%-- 메시지 관련 Prefix 선언 Start --%> 
<c:set var="prefix"     value="ui.approval.form" />
<c:set var="prefixBtn"  value="ui.approval.form.button" />
<%-- 메시지 관련 Prefix 선언 End --%>
<script  type="text/javascript">

<!--// 

	(function($) {
		
		var dialogWindow;    
        
	    //- onload시 수행할 코드
        $(document).ready(function() {   
           
           //- caller  
           fnCaller = function (params, dialog) {
				dialogWindow = dialog;
				$("#mainForm input[name=contents]").val(params.html);
				$("#mainForm input[name=fileName]").val(params.fileName);
			};
			
    		//- save button  
            $("#saveButton").click(function(){
                
                var header = $("#mainForm textarea[name=header]").val();

                if($("#mainForm input[name=hasApprLine]:checked").val()=="0"){
                    header += " #apprLineTable{display:none;} ";
                }
                
                if($("#mainForm input[name=hasApprExam]:checked").val()=="0"){
                    header += " #ApprLineMessage{display:none;} ";
                }
                
                $("#mainForm textarea[name=header]").val(header + "</style></head><body>");
                //document.write($("#mainForm textarea[name=header]").val() + $("#mainForm input[name=contents]").val() + $("#mainForm input[name=footer]").val());
                $("#htmlDocument").val($("#mainForm textarea[name=header]").val() + $("#mainForm input[name=contents]").val() + $("#mainForm input[name=footer]").val());
                $("#mainForm").submit();
                
                dialogWindow.close();
                
            });
            
            //- close button  
            $("#closeButton").click(function(){
                dialogWindow.close();
            });
        });
        
	})(jQuery);  

//-->
</script>

<h1 class="none">contnet area</h1>
<div id="guideConFrame">
	<div class="blockBlank_10px"></div>
	
	<!--subTitle_2 Start-->
	<div class="subTitle_2 noline">
		<h3>기본정보</h3>
	</div>
	<!--//subTitle_2 End-->
	
	<!--blockDetail Start-->
	<div class="blockDetail">
    	<form id="mainForm" name="mainForm" method="post" action="<c:url value="/support/fileupload/apprDownLoad.do" />">
    	
    		<input type="hidden" name="fileName" id="fileName" value="download" title="fileName" />
    		<input type="hidden" name="htmlDocument" id="htmlDocument" value="" title="htmlDocument"/>
    		
    	    <textarea name="header" class="none">
    	     <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <!--타이틀-->
                <title>LG CNS :: iKEP4.00 전자결재</title>
                
                <style type="text/css">
                    *{  
                        margin: 0;
                        padding: 0;
                     }
                    
                    body {  
                        font: 75%/1.5em Tahoma, '돋움', 'Dotum';
                        color: #555;
                        background: white;
                        height: 100%;
                        margin: 0 20px 0 20px;
                        padding-top: 5px;
                    }
                
                    img {
                        border: none;
                    }
                    
                    .font_style1 {
                        font-size:18px;
                    	text-decoration: underline;
                    	font-weight: bold;
                     }
                
                    .table_style th {
                    	background-color: #f3f3f3;
                    	border-top:1px solid #ccc;
                    	border-left:1px solid #ccc;
                    	padding: 2px 5px 7px 5px;
                    }
                    .table_style th.first {
                    	border-bottom:1px solid #ccc;
                    	padding: 8px;
                    }
                    .table_style th.last {
                    	border-right:1px solid #ccc;
                    }
                    .table_style td {
                    	border-top:1px solid #ccc;
                    	border-left:1px solid #ccc;
                    	border-bottom:1px solid #ccc;
                    	line-height: 18px;
                    	padding: 5px 5px 10px 5px ;
                    }
                    
                    .table_style td.status0{background-color:#ffffff;}
                    .table_style td.status1{background-color:#639cf7;}
                    .table_style td.status2{background-color:#639cf7;}
                    .table_style td.status3{background-color:#29ff28;}
                    .table_style td.status4{background-color:#ff241a;}
                    
                    .table_style td.last {
                    	border-right:1px solid #ccc;
                    }
                    
                    
                    .blockDetail {
                        position: relative;
                        margin-bottom: 10px;
                        width: 100%;
                        border-top: 2px solid #919FB2;
                    }
                    
                    .blockDetail table {
                        width: 100%;
                        border-collapse: collapse;
                    }
                    
                    table {
                        display: table;
                        border-collapse: separate;
                        border-color: gray;
                    }
                    
                    .blockDetail table th {
                        font-weight: bold;
                        line-height: 19px;
                        text-align: right;
                        color: #333;
                        padding: 2px 5px 7px 0;
                        border: 1px solid #E0E0E0;
                        border-top: none;
                        background: #F3F3F3;
                    }
                    
                    .blockDetail table td {
                        line-height: 19px;
                        padding: 2px 5px 7px 5px;
                        word-break: break-all;
                        border: 1px solid #E0E0E0;
                        border-top: none;
                        background-color: white;
                    }
                    
                    .textCenter {
                        text-align: center !important;
                    }
                    
                    .filedown_ic img {
                        vertical-align: middle;
                        padding-bottom: 2px;
                    }
                    
                    .mt10 { margin-top:10px !important; }
                    .blockBlank_10px { height: 10px; }
                    
                    #formButtonDiv{display:none;} 
                    #fileDownloadDiv{display:none;}
                    #fileListDiv{display:block;}
                    #ApprLineMessage b { display: inline-block; padding-bottom: 10px; padding-top: 10px; line-height: 30px; vertical-align: top;}

					.Approval_1 { position:relative; margin-bottom:5px; width:100%; border-top:2px solid #7e98b8; }
					.Approval_1 table { width:100%; table-layout:fixed; border-collapse:collapse; }
					.Approval_1 table caption { display:none; }
					.Approval_1 table th { font-weight:bold; line-height:19px; text-align:right; color:#333; padding:3px 5px; border:1px solid #e0e0e0; border-top:none; background:#eaf0f7; }
					.Approval_1 table th a:link, .blockDetail table th a:visited, .blockDetail table th a:active, .blockDetail table th a:hover { color:#333; text-decoration:none; }
					.Approval_1 table td { line-height:19px; padding:3px 5px; word-break:break-all; border:1px solid #e0e0e0; border-top:none; background-color:#fff; }
					
					.Approval_1 table td strong { color: #2359ad; padding: 5px 0;}
					
					.Approval_1 .bgSelected { background-color: #edf2f5; }
					.Approval_1 table td .radio, .Approval_1 table td .checkbox { vertical-align: text-bottom; margin:0 0 0 2px; }
					.Approval_1 table td .button_s { vertical-align:middle; margin:-1px 3px -2px 0; }
					.Approval_1 .selectbox { vertical-align:top; }
					.Approval_1 .inputbox { vertical-align:top; }
					.Approval_1 .listStyle li { padding:2px 0; }
					.Approval_1 .link { color:#5a86ce !important; }
					.Approval_1 a:visited.link { color:#ba31bc !important; }                    
    	    </textarea>
    	    <input type="hidden" name="contents">
    	    <input type="hidden" name="footer" value="</body></html>">
    	    
    		<table summary="기본정보">
    			<caption></caption>
    			<tbody>
    				<tr>
    					<th width="20%" scope="row">파일타입</th>
    					<td width="80%">
    						<input type="radio" class="radio" name="fileType" value="1" checked> PDF
                            <input type="radio" class="radio" name="fileType" value="0" > MHT
    					</td>
    	            </tr>
    	            <tr>
    					<th scope="row">결재선정보</th>
    					<td>
    						<input type="radio" class="radio" name="hasApprLine" value="1" checked> 결재선정보 포함
                            <input type="radio" class="radio" name="hasApprLine" value="0" > 결재선정보 미포함
    					</td>
    	            </tr>   
    	            <tr>		
    					<th scope="row">결재의견</th>
    					<td>
    						<input type="radio" class="radio" name="hasApprExam" value="1" checked> 결재의견 포함
                            <input type="radio" class="radio" name="hasApprExam" value="0" > 결재의견 미포함
    					</td>
    		        </tr>
    			</tbody>
    		</table>
    	</form>
	</div>
	<!--//blockDetail End-->
	
	<!--blockButton Start-->
	<div id="mainFormButton" class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span>저장</span></a></li>
			<li><a id="closeButton" class="button" href="#a"><span>닫기</span></a></li>
		</ul>
	</div>
	<!--//blockButton End-->
</div>