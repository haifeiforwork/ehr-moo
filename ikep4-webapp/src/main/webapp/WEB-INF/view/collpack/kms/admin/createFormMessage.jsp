<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/base/common/taglibs.jsp"%> 
<c:set var="preHeader"  value="ui.lightpack.board.boardAdmin.createBoardView" /> 
<c:set var="preDetail"  value="ui.lightpack.board.boardAdmin.createBoardView.detail" />
<c:set var="preMessage" value="message.lightpack.common.boardAdmin" />
<c:set var="preButton"  value="ui.lightpack.common.button" />   

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<link rel="stylesheet" type="text/css" href="<c:url value='/base/css/approval/admin/apprForm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/base/js/wceditor/css/editor.css'/>"/>
<script type="text/javascript" src="<c:url value='/base/js/units/approval/admin/apprForm.js'/>"></script>

<script  type="text/javascript">
<!--// 

	(function($) {
		var delIdList        = [];
		var updateNameList   = [];
		var updateIdList     = [];
        var alignList =[];
        
        selectedAll = function (selectName){
    		var $sel = $jq("select[name="+selectName+"]");
    		if($sel) {
    			$jq.each($sel.children(), function() {
    				this.selected = true;
    			});
    		}
    	};
        
        $(document).ready(function() {   
		    //- 버튼영역 실행  
		    
			$("#itemFormButton a").click(function(){
			    switch (this.id) {
                    //case "saveButton":
                        //$("#itemForm").trigger("submit");
                       // $("#itemForm").attr("action", "<c:url value='/lightpack/board/boardAdmin/createCategoryBoard.do'/>");
        			   // break;
                    case "closeButton":
                    	window.close();
                        break;
	                default:
	                    break;
	            }   
            });
		    
			$jq("#saveButton").click(function() {   
	    		
	    		$.ajax({
					url : "<c:url value='/collpack/kms/admin/createMessage.do' />",
					data : $("#itemForm").serialize(),
					type : "post",
					dataType : "html",
					success : function(result) {
						if(result == 'success') {
							alert("저장되었습니다.");
						} else {
							alert('error');
						}
					},
					error : function() {alert('error');}
				});
	    	});  
		    
		    $("#updateOptionButton").hide();
            $("#initOptionButton").hide();

    		//- add option  
            $("#addOptionButton").click(function() {
            	
                var optionText  = $.trim($("#itemForm input[name=optionText]").val());
                //iKEP.debug(optionText);
                if(optionText=="알림"){//[공지] 대신 [알림]으로 변경
                	alert("<ikep4j:message pre='${preMessage}' key='noticeMessage'/>");
                	return;
                }
                
                if(optionText=="") return;
        		$("#itemForm select[name=options]").append("<option value=''>"+optionText+"</option>");
        		setOptionForm("", "", false);
            });
    
    		//- remove option  
            $("#removeOptionButton").click(function() {
            	$jq.each($("#itemForm select[name=options] option:selected"),function(index){
            		if($jq(this).val() != '') {
            			delIdList.push($jq(this).val());
            		}
            		$jq(this).remove();
	    		});
            });   
    		
          //- update option  
            $("#updateOptionButton").click(function() { 	
                var optionText  = $.trim($("#itemForm input[name=optionText]").val());
   		    		
        		$jq.each($("#itemForm select[name=options] option:selected"),function(index){
            		if($jq(this).text() != '') {			
            			//iKEP.debug("3333333333333333333");
                		//iKEP.debug(optionText);              		
                		//iKEP.debug("44444444444444444");
                		//iKEP.debug($jq(this).val());      		
            			updateNameList.push(optionText);
            			updateIdList.push($jq(this).val());
            		}
            		
	    		});
                      
                if(optionText=="") return;
                
                var optionValue = $("#itemForm input[name=optionValue]").val();
                var checked     = $("#itemForm input[name=defaultValue]").attr("checked")?" class=\"selectedOption\"":"";
    
                if(checked!="") $("#itemForm select[name=options] option.selectedOption").removeClass("selectedOption");  		
 		
        		$("#itemForm select[name=options] option:eq(" + editIndex + ")").replaceWith("<option value=\""+optionValue+"\"" + checked + ">"+optionText+"</option>");
        		setOptionForm("", "", false);
        		$("#addOptionButton").show();
                $("#updateOptionButton").hide();
                $("#initOptionButton").hide();
    
            });

    		//- init option  
            $("#initOptionButton").click(function() {
                setOptionForm("", "", false);
                $("#addOptionButton").show();
                $("#updateOptionButton").hide();
                $("#initOptionButton").hide();
            });
            
    		//- edit option  
            $("#editOptionButton").click(function() {
                editIndex = $("#itemForm select[name=options] option").index($("#itemForm select[name=options] option:selected"));
                if(editIndex!=-1){
                    setOptionForm($("#itemForm select[name=options] option:selected").text(), $("#itemForm select[name=options] option:selected").val(), $("#itemForm select[name=options] option:selected").hasClass("selectedOption"));
                    $("#addOptionButton").hide();
                    $("#updateOptionButton").show();
                    $("#initOptionButton").show();
                }     
            });   

    		//- move up option 
            $("#moveUpButton").click(function() {
                if($("#itemForm select[name=options] option:selected").prevAll().size()>0){
                    $("#itemForm select[name=options] option:selected").insertBefore($("#itemForm select[name=options] option:selected").first().prev());
                }
            });   

    		//- move down option 
            $("#moveDownButton").click(function() {
               if($("#itemForm select[name=options] option:selected").nextAll().size()>0){
                    $("#itemForm select[name=options] option:selected").insertAfter($("#itemForm select[name=options] option:selected").last().next());
                }
            });   
    		
          	//- option text event   
            $("#itemForm input[name=optionText]").keypress(function(event){
                if(event.which == 13) {
                    if($("#addOptionButton").css("display")=="none")
                	    $("#updateOptionButton").click();
                    else
                        $("#addOptionButton").click();
                }
            });

    		
            //-  wonchu Edit Init 
           // wceditorMiniToolbar.height="100px";
           // wceditorMiniToolbar.miniMode = true;
            //WCEditor.fixIfrmLeft("approval.form");
           // $("#itemForm textarea[name=description]").wceditor(wceditorMiniToolbar);
            
           // $("#itemForm input[name=dataType]:input[value=" + $("#itemForm input[name=dataType]:checked").val() + "]").click();
        });
        
        
		//- option setting
        setOptionForm = function(txt, val, b){
            $("#itemForm input[name=optionText]").val(txt);
            $("#itemForm input[name=optionValue]").val(val);
            $("#itemForm input[name=defaultValue]").attr("checked", b);
            $("#itemForm input[name=optionText]").select();
        };
		
	})(jQuery);  

//-->
</script>

<!--popup Start-->

<div class="blockListTable">
	<!--tableTop Start-->  
	<div class="tableTop">
		<div class="tableTop_bgR"></div>
		<h2>등록화면 주의사항 설정</h2> 
		<div class="clear"></div>	
	</div>
	<!--//tableTop End-->
</div>
	<!--popup_contents Start-->
	
	<form id="itemForm" name="itemForm" method="post" action="" onsubmit="return false;">	
		<input type="hidden" id="delIdList"      name="delIdList"/>
   		<input type="hidden" id="addNameList"    name="addNameList"/> 	
   		<input type="hidden" id="updateNameList" name="updateNameList"/> 	
   		<input type="hidden" id="updateIdList"   name="updateIdList"/> 	
   		<input type="hidden" id="alignList"   name="alignList"/> 	
   		<!--blockDetail Start-->
		<div class="blockDetail">
			<table summary="Message">
				<caption></caption>
				<colgroup>
					<col width="15%" />
					<col width="85%" />
				</colgroup>
				<tbody>   
    	      		<tr id="optionValueTr">
    					<th scope="row">문구</th>
    					<td style="padding-top:7px;">
    						<textarea name="contents" cols="30" rows="10" >${boardItem.caution}</textarea>                               
    					</td>
    			    </tr>
    			   
    			</tbody>
			</table>			
			
		</div>
		<!--//blockDetail End-->	
																																			
		<div class="clear"></div>
		
		<!--blockButton Start-->
		<div id="itemFormButton"  class="blockButton"> 
		<ul>
			<li><a id="saveButton"  class="button" href="#a"><span><ikep4j:message pre='${preButton}' key='save'/></span></a></li>
		</ul>
		</div>
	</form>
	<!--//popup_contents End-->
	
			

<!--//popup End-->