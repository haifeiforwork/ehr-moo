<%--
=====================================================
* 기능 설명 : text 설정
* 작성자    : wonchu
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

                //$("#mainForm textarea[name=header]").val(header + "</style></head><body>");
                //document.write($("#mainForm textarea[name=header]").val() + $("#mainForm input[name=contents]").val() + $("#mainForm input[name=footer]").val());
                $("#htmlDocument").val($("#mainForm textarea[name=header]").val() + $("#mainForm input[name=contents]").val() + $("#mainForm textarea[name=footer]").val());
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
    	
    		<input type="hidden" name="fileName" id="fileName" value="" title="fileName" />
    		<input type="hidden" name="htmlDocument" id="htmlDocument" value="" title="htmlDocument"/>
    		
    	    <textarea name="header" class="none">
    	     	
    	     	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
                <html xmlns="http://www.w3.org/1999/xhtml">
                <head>
                <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
                <link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/jquery_ui_custom.css"/>" />
				<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/common.css"/>" />
				<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/theme01/theme.css"/>" />
				<link rel="stylesheet" type="text/css" href="<c:url value="/base/css/approval/work/apprDoc.css"/>"/>

                <!--타이틀-->
                <title>LG CNS :: iKEP4.00 전자결재</title>
                
                <style type="text/css">
                	
                	* { margin:0; padding:0; }
					body { font:75%/1.5em Tahoma, '돋움', 'Dotum'; color:#555; background:#fff; height:100%; }
					html { height:100%; }
					ul { list-style:none; }
					a:link, a:visited, a:active { color:#3378bd; text-decoration:none; outline:none;}
					a:hover { text-decoration:underline; }
					hr { display: none; }
					h1 { font-size:1.2em; }
					img { border:none; }
					option { padding-right: 0.5em; }
					legend { display:none; }
					fieldset { border:none; }

                	#wrapper { max-width:2000px; height:100%; margin:0 auto; }
                	#blockContainer { min-width:990px; height:100%; position:relative; }
                	#blockMain { position:relative; }
                	#mainContents { position:relative; margin:0 20px 0 220px; padding-top:15px; }
					.conPadding_1 { position:relative; margin:0 20px 0 215px !important; }
					.conPadding_2 { position:relative; margin:0 20px 0 20px !important; }
					.conPadding_3 { position:relative; margin:0 20px 0 200px !important; }
					.conPadding_4 { position:relative; margin:0 10px 0 0px !important; }
					
					.Approval { position:relative; margin-bottom:10px; width:100%; border-top:none; }
					.Approval table { width:100%; border-collapse:collapse; }
					.Approval table caption { display:none; }
					.Approval table .title { height: 40px;padding:30px 15px 20px 15px;}
					.Approval table .title p { font-size:28px;font-weight:bold;}
					.Approval table .title p.t1 { font-size:20px;font-weight:bold;}
					.Approval table .title p.t1 img { vertical-align: bottom;margin-bottom: -4px;}
					.Approval table .title.tl { text-align:left;}
					.Approval table .title.tc { text-align:center;}
					.Approval table .title.tr { text-align:right;}
					.Approval table th { font-weight:bold; line-height:19px; text-align:right; color:#333; padding:3px 15px; border:0;background-color:#fff;}
					.Approval table th a:link, .blockDetail table th a:visited, .blockDetail table th a:active, .blockDetail table th a:hover { color:#333; text-decoration:none; }
					.Approval table td { line-height:19px; padding:3px 15px; word-break:break-all; border:0; background-color:#fff; }
					.Approval table td ul li { padding:0px 15px; }
					.Approval table td.content { padding: 20px 25px; }
					.Approval table td hr.line1{ display:block; border:5px solid #555;}
					*:first-child+html .Approval table td hr.line1{ display:block; height:5px; border:5px solid #555;}
					.Approval table td hr.line2{ display:block; border:1px solid #555;}
					*:first-child+html .Approval table td hr.line2{ display:block; height:2px; color:#555;}
					.Approval .bgSelected { background-color: #edf2f5; }
					.Approval table td .radio, .blockDetail table td .checkbox { vertical-align:middle; }
					.Approval table td .button_s { vertical-align:middle; margin:-1px 3px -2px 0; }
					.Approval .selectbox { vertical-align:top; }
					.Approval .listStyle li { padding:2px 0; }
					
					.clear { clear:both; }
					.none { display:none; }
					.hidden { visibility:hidden; }
                	
                	.w100 { width:98% !important; }
					.w95 { width:95% !important; }
					.w90 { width:90% !important; }
					.w80 { width:80% !important; }
					.w70 { width:70% !important; }
					.w60 { width:60% !important; }
					.w50 { width:50% !important; *width:49.9999% !important; }
					.w33 { width:33.33% !important; *width:33.2% !important; }
					.w20 { width:20% !important; }
					.margin0 { margin:0 !important; }
					.mt0 { margin-top:0px !important; }
					.mt-1 { margin-top:-1px !important; }
					.mt3 { margin-top:3px !important; }
					.mt5 { margin-top:5px !important; }
					.mt10 { margin-top:10px !important; }
					.mt15 { margin-top:15px !important; }
					.mt20 { margin-top:20px !important; }
					.mb0 { margin-bottom:0 !important; }
					.mb5 { margin-bottom:5px !important; }
					.mb10 { margin-bottom:10px !important; }
					.mb15 { margin-bottom:15px !important; }
					.mb20 { margin-bottom:20px !important; }
					.mr5 { margin-right:5px; }
					.mr10 { margin-right:10px; }
					.ml5 { margin-left:5px; }
					.ml10 { margin-left:10px; }
					.ml15 { margin-left:15px; }
					.ml20 { margin-left:15px; }
					.padding10 { padding:10px !important; }
					.padding15 { padding:15px !important; }
					.pl0 { padding-left:0px !important; }
					.pr0 { padding-right:0px !important; }
					.pb0 { padding-bottom:0px !important; }
					.pb5 { padding-bottom:5px !important; }
					.pl5 { padding-left:5px !important; }
					.pl10 { padding-left:10px !important; }
					.pl50 { padding-left:50px !important; }
					.pr10 { padding-right:10px !important; }
					.pt5 { padding-top:5px !important; }
					.pt10 { padding-top:10px !important; }
					.pt15 { padding-top:15px !important; }
					.pb10 { padding-bottom:10px !important; }
					.border { border:1px solid #000 !important; }
					.border_t1 { border-top:1px solid #e0e0e0 !important; }
					.border_b1 { border-bottom:1px solid #e0e0e0 !important; }
					.border_none { border:none !important; }
					.bg_none { background:none !important; }
					.bgWhite { background:#fff; }

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
					
                 </style>       
                </head>
				<body>   
								
								<div class="blockDetail Approval" id="guideConFrame">
						
    	    </textarea>
    	    
    	    <input type="hidden" name="contents">
    	    
    	    <textarea name="footer" class="none">
    	    					</div>
							
				</body>
				</html>
    	    </textarea>
    	    
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