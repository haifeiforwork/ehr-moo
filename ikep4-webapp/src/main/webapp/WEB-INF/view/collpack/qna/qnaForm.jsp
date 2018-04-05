<%@ include file="/base/common/taglibs.jsp"%>
<%@ include file="/base/common/DextfileUploadInit.jsp"%><%-- 파일업로드용 Import --%> 
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>

<script type="text/javascript" src="<c:url value="/base/js/ckeditor/ckeditor.js"/>"></script>
<script type="text/javascript" src="<c:url value="/base/js/ckeditor/adapters/jquery.js"/>"></script>

<c:set var="preMessage"  value="message.collpack.qna.expert.mail" />
<c:set var="preUi"          value="ui.collpack.qna.qnaForm" />
<c:set var="preResource"    value="message.collpack.qna" />

<c:set var="portal" value="${sessionScope['ikep.portal']}" />
<c:set var="user" value="${sessionScope['ikep.user']}" />

<script type="text/javascript">
//<![CDATA[
var fileController;

//activeX editor 사용여부
var useActXEditor = "${useActiveX}";

var oldFiles;
var oldSizes;

(function($){
		$jq(document).ready(function() {
		    
		    /*
		     var uploaderOptions = {// 사용할 수 있는 옵션 항목
		        <c:if test="${!empty(fileDataListJson)}">
		            files  :  ${fileDataListJson}, 
		        </c:if>
		        // isUpdate : true,    // 등록 및 수정일때 true
		        //uploadUrl : "",    // 파일 업로드 경로가 다를때 설정 : 사용하지 않음
		        maxTotalSize : 20*1024*1024,    // 최대 업로드 가능 용량(개별 파일 사이즈임)
		        allowFileType  : "all"    
		    };    
		     
		    //파일업로드 컨트롤로 생성   
		     fileController = new iKEP.FileController("#qnaForm", "#fileUploadArea", uploaderOptions);
		    */
		    dextFileUploadInit(""+20*1024*1024 ,"1", "all");
		
		    <c:if test="${not empty fileDataListJson}"> 
		    oldFiles = ${fileDataListJson};
		    $jq.each(oldFiles,function(index){
		        var fileInfo = $.extend({index:index}, this);
		        document.getElementById("FileUploadManager").AddUploadedFile(fileInfo.fileId, fileInfo.fileRealName, fileInfo.fileSize);
		     });
		    
		    dextFileUploadRefresh(); 
		    oldSizes =document.getElementById("FileUploadManager").Size;
		    </c:if> 
		
		    //조직도 데이터 있으면 
		    $sel = $jq(":input[name=expertIds]").children().remove().end();
		    
		     <c:forEach var="expert" items="${expertList}" varStatus="loopCount">
		        var userInfo = {// 공통필수
		            type:"user",
		            empNo : "",
		            id:"${expert.expertId}",
		            jobTitleName:"${user.localeCode == portal.defaultLocaleCode ? expert.jobTitleName : expert.jobTitleEnglishName}",
		            //name:"${expert.userName}",
		            userName : "${user.localeCode == portal.defaultLocaleCode ? expert.userName : expert.userEnglishName}",
		            searchSubFlag:false,
		            teamName:"${user.localeCode == portal.defaultLocaleCode ? expert.teamName : expert.teamEnglishName}",
		            email:"${expert.mail}",
		            mobile:"${expert.mobile}"
		        };
		        
		        $jq.tmpl(iKEP.template.userQnaOption, userInfo).appendTo($sel).data("data", userInfo);
		        
		    </c:forEach>
		    
		    //콜백함수
		    setAddress = function(result) {
		
		         $sel.empty();
		         $jq.each(result, function() {
		             
		             var $option = $jq.tmpl(iKEP.template.userQnaOption, this).appendTo($sel);
		
		             $jq.data($option[0], "data", this);
		             if( this.searchSubFlag == true ){
		                 $jq('input[name=searchSubFlag]:hidden').val("true") ;
		             }
		         })
		
		    };
		    
		    //체크박스  초기설정
		    var aChannel = "${qnaForm.alarmChannel}";
		    if(aChannel){
		        for(var i = 0; i < aChannel.length; i++){
		            if(aChannel[i] == 1){
		                $jq(':input[name=alarmChannelItem]:eq('+i+')').attr('checked',true);
		            }
		        }
		    }
		    
		    var eChannel = "${qnaForm.expertChannel}";
		    if(eChannel){
		        for(var i = 0; i <eChannel.length; i++){
		            if(eChannel[i] == 1){
		                $jq(':input[name=expertChannelItem]:eq('+i+')').attr('checked',true);
		            }
		        }
		    }
		   
		    //상세화면에서 mblog한번 보내기 위해
		    $jq.cookie('isMblog', '');
		    
		    //validation
		    var validOptions = {
		        rules : {
		            title : {
		                required : true,
		                maxlength  : 500
		            }
		            //,contents : "required"
		            /*
		            ,tag :  {
		                required : true
		                ,maxlength  : 100
		                ,tagCount :10
		                ,tagDuplicate: true
		                ,tagWord :true
		            }
		            */
		        },
		        messages : {
		            title : {
		                required : "<ikep4j:message key='NotEmpty.qna.title'/>",
		                maxlength : "<ikep4j:message key='Size.qna.title' arguments='500'/>"
		            }
		            ,contents : {
		                required : "<ikep4j:message key='NotEmpty.qna.contents'/>"
		            }
		            /*
		            ,tag : {
		                required : "<ikep4j:message key='NotEmpty.qna.tag'/>"
		                ,maxlength : "<ikep4j:message key='Size.qna.tag' arguments='100'/>"
		                ,tagCount :"<ikep4j:message key='MaxCount.qna.tag' arguments='10'/>"
		                ,tagDuplicate :"<ikep4j:message key='Duplicate.qna.tag'/>"  
		                ,tagWord :"<ikep4j:message key='Check.qna.tagWord'/>"   
		            }
		            */
		        },
		        notice : {
		            title : "<ikep4j:message key='NotEmpty.qna.title'/>",
		            contents : "<ikep4j:message key='NotEmpty.qna.contents'/>"
		           // tag: "<ikep4j:message key='NotEmpty.qna.tag'/>"
		        },
		        submitHandler : function(form){
		        	  if((oldSizes !=document.getElementById("FileUploadManager").Size)||(document.all["FileUploadManager"].Count>0)){//삭제한것이 있거나 추가한것이 있으면
		                  btnTransfer_Onclick("qnaForm");
		              }else{
		                  //alert("파일업로드 없음");            
		                  oldFileSetting(oldFiles , form);
		                  writeSubmit(form);
		              }
		        }
		
		    };
		    
		    //$jq("#qnaForm").validate(validOptions); 
		    new iKEP.Validator("#qnaForm", validOptions);
		    
		    // editor 초기화
		    initEditorSet();
		    
		 });
	})(jQuery);  

    /* editor 초기화  */
    function initEditorSet() {
        // ActiveX Editor 사용 여부가 Y인 경우 이고 브라우저가 ie인 경우
        if(useActXEditor == "Y" && $jq.browser.msie){
            // div 높이, 넓이 세팅
            var cssObj = {
              'height' : '450px',
              'width' : '100%'
            };
            $jq('#editorDiv').css(cssObj);

            var editVal = 0;
            <c:if test="${!empty(qnaForm)}">
                editVal = 1;
            </c:if>

            // hidden 필드 추가(contents)
            iKEP.createActiveXEditor("#editorDiv","${user.localeCode}","#contents",editVal);

            // ie 세팅
            $jq('input[name="msie"]').val('1');
        }else{
            // ckeditor 초기화.
            $jq("#contents").ckeditor($jq.extend(fullCkeditorConfig, {"language" : "${user.localeCode}", "popupTitle" : "<ikep4j:message key='ui.support.fileupload.header.title' />" }));
            // ie 이외 브라우저 값 세팅
            $jq('input[name="msie"]').val('0');
        }
    }

    var $sel = "";              //조직도

    function submitQna(){

        $sel.children().each(function(){
            if(!this.value){
                $jq(this).remove();
            }
       });
        
        var f = $jq('#qnaForm');
        
        var urgent =  $jq('#urgentCheck:checked').val();
        
        if(urgent == null){
            urgent = 0;
        }
        $jq('#urgent').val(urgent);

        if(f.find('select[name=expertIds] option').size() > 10){
            alert('<ikep4j:message key='MaxCount.qna.expert' />');
            return;
        }
        
        //전문가
        var isExperts = false;      //전문가 목록이 있는지
        f.find('select[name=expertIds] option').each(function(i){
            this.selected = true;
            isExperts = true;
        });
        
        //전문가 채널
        var expertChannelResult = "";
        f.find('input[name=expertChannelItem]').each(function(){
            
            if(this.checked){
                expertChannelResult +=this.value;
            } else {
                expertChannelResult += "0";
            }
        });
        
        if(isExperts){
            if(expertChannelResult == "0000"){
                alert('<ikep4j:message key='Select.qna.expertChannel' />');
                return false;
            }
        }
        
        f.find('input[name=expertChannel]').val(expertChannelResult);
        
        //답변 등록 알람
        var alarmChannelResult = "";
        f.find('input[name=alarmChannelItem]').each(function(){
            
            if(this.checked){
                alarmChannelResult +=this.value;
            } else {
                alarmChannelResult +="0";
            }
        });
        
        f.find('input[name=alarmChannel]').val(alarmChannelResult);
        
      // ActiveX Editor 사용 여부가 Y인 경우
        if(useActXEditor != "Y" || !$jq.browser.msie){
            //ekeditor 데이타 업데이트
            var editor = $jq("#contents").ckeditorGet(); 
            editor.updateElement();
            //에디터 내 이미지 파일 링크 정보 세팅
            createEditorFileLink("qnaForm");
        }
      
        
        //먼저 submit을 하여 validation check로 넘어간다.
        // iKEP.Validator는 check후 submitHandler를 호출하게 된다.
        
      
         f.submit();
    }    
    
    writeSubmit = function(targetForm){
        if(useActXEditor == "Y" && $jq.browser.msie){
            //태그프리 선택 탭을 디자인으로 변경 후 저장한다.
            var tweTab = document.twe.ActiveTab;
            if ( tweTab != 1 ) {
                document.twe.ActiveTab = 1;
            }
            //태그프리 Mime 데이타 세팅
            var tweBody = document.twe.MimeValue();
            $jq('input[name="contents"]').val(tweBody);
            $jq("#twe").css("visibility","hidden");
        }    
        targetForm.submit();
    };
    
    function goList(val){
        
        var param = '<c:forEach var="ram" items="${param}" ><c:if test="${ram.value != ''}">${ram.key}=${ram.value}&</c:if></c:forEach>';

        var url = 'list'+val+'Qna.do?'+param;
        
        if(val == "Main"){
            url = 'main.do?'+param;
        }

        location.href=url;
    }
    
        
     // 조직도 팝업 
    function btnAddrControl() {

        var items = [];
        
        $sel.children().each(function(){
            if(!this.value){
                $jq(this).remove();
            } else {
                items.push($jq.data(this, "data"));
            }
       });
         
       iKEP.showAddressBook(setAddress, items, {selectType:"user", isAppend:false, tabs:{common:1}});

    }
     
    //전문가 목록 선택 삭제
    function expertListDel(){
        
        $jq.each($sel.children(), function() {
            $this = $jq(this);
            if($this.attr('selected')) {$this.remove();}
        });
    }
    
//]]>
</script>
 
<script language="JScript" FOR="twe" EVENT="OnKeyDown(event)">
    /* 태그프리 에디터 줄바꿈 태그 P => br 로 변경하는 메소드 */
    if (!event.shiftKey && event.keyCode == 13){
        twe.InsertHtml("<br>");
        event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
    }
    if (event.shiftKey && event.keyCode == 13){
        twe.InsertHtml("<p>");
        event.returnValue = true; <!-- Active Designer에서의 처리를 막음 -->
    }       
</script>
<script language="JScript" for="twe" event="OnControlInit()">   
    document.twe.HtmlValue = $jq("input[name=contents]").val().replaceAll("src=\""+iKEP.getContextRoot(), "src=\""+iKEP.getWebAppPath());
    $jq("input[name=title]").focus();
</script>

<!--guideWrapper Start-->
    <h1 class="none">contents Area</h1>

    <form id="AddrControlForm" action="">
        
        <input type="hidden" name="controlTabType"  value="1:0:1:0" />
        <input type="hidden" name="controlType"     value="ORG" />
        <input type="hidden" name="selectType"      value="USER" />                             
        <input type="hidden" name="selectMaxCnt"    value="10" />
        <input type="hidden" name="searchSubFlag"   value="" />
    </form>

    <form id="editorFileUploadParameter" action="">
        <input type="hidden" name="interceptorKey"  value="ikep4.system"/>
    </form>

    <form id="qnaForm" method="post" action="createQna.do" >
        <spring:bind path="qnaForm.portalId">
            <input name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind> 
        <spring:bind path="qnaForm.listType">
            <input name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind> 
        <spring:bind path="qnaForm.urgent">
            <input id="urgent" name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind> 
        <spring:bind path="qnaForm.qnaType">
            <input name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind> 
        <spring:bind path="qnaForm.listTab">
            <input name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind> 
        <spring:bind path="qnaForm.qnaId">
            <input name="${status.expression}" type="hidden" value="${status.value}" />
        </spring:bind>
        <input name="msie" type="hidden" value="0" />
        
        <!--pageTitle Start-->
        <div id="pageTitle">
            <span style="font-weight:bold;font-size:1.2em;">
            <c:choose>
                <c:when test="${empty(param.qnaId)}">
                    <ikep4j:message pre='${preUi}' key='pageLocationTitle'/>
                </c:when>
                <c:otherwise>
                    <ikep4j:message pre='${preUi}' key='pageLocationTitle.update'/>
                </c:otherwise>
            </c:choose>
            </span>
            
          
        </div>
        <!--//pageTitle End-->
    
    <!--blockDetail Start-->
    <div class="blockDetail">
        
        <table summary="<ikep4j:message pre='${preUi}' key='formSummary'/>">
            <caption></caption>
            <tbody>
            
                <spring:bind path="qnaForm.title">
                    <tr> 
                        <th scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='formTitle'/></label></th>
                        <td colspan="3">
                            <div>
                            <input type="text" id="${status.expression}" name="${status.expression}" class="inputbox w80" value="${status.value}" size="30" />
                            <input type="checkbox" id="urgentCheck" name="urgentCheck" class="checkbox" value="1"   <c:if test="${qnaForm.urgent == 1}">checked</c:if>/>
                            <label for="urgentCheck"><ikep4j:message pre='${preUi}' key='formUrgent'/></label>
                            <label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
                            </div>
                        </td> 
                    </tr>               
                </spring:bind>
                <spring:bind path="qnaForm.categoryId">
                    <tr> 
                        <th scope="row"><label for="${status.expression}"><ikep4j:message pre='${preUi}' key='formCategory'/></label></th>
                   <td colspan="3">               
                    <select id="${status.expression}" name="${status.expression}">
                        <c:forEach var="category" items="${categoryList}">
                            <option value="${category.categoryId}" <c:if test="${category.categoryId eq status.value}">selected="selected"</c:if>>
                                ${category.categoryName}
                            </option>
                        </c:forEach>
                            <option value="" <c:if test="${empty(status.value) && !empty(qnaForm.qnaId)}">selected="selected"</c:if>>
                                <ikep4j:message key='ui.collpack.qna.qnaMenu.cetera'/>
                            </option>
                    </select>
                     </td> 
                   </tr>    
                </spring:bind>   
                <tr> 
                    <th scope="row"><ikep4j:message pre='${preUi}' key='formExpertTitle'/></th>
                    <td colspan="3" >
                        <div style="float:left;">
                            <select title="expertIds" style="width:350px;height:60px;" size="5" multiple="multiple" name="expertIds">
                                <option value=""></option>
                             </select>
                         </div>
                       <div style="float:left;padding-left:4px;">
                            <a class="button_ic"  href="#a" onclick="btnAddrControl();return false;" title="Address"><span><img src='<c:url value="/base/images/icon/ic_btn_address.gif"/>' alt="Address" />Address</span></a>
                            <a class="button_ic"  href="#a" onclick="expertListDel();return false;" title="Delete"><span><img src="<c:url value="/base/images/icon/ic_btn_delete.gif"/>" alt="Delete" />Delete</span></a>
                            
                            <p style="padding-top:20px;">
                                <input type="hidden" name="expertChannel" value="" />
                                <input type="checkbox" name="expertChannelItem" class="checkbox" value="1"  id="expertChannelItem1" /><label for="expertChannelItem1"><ikep4j:message pre='${preUi}' key='formExpertChannelItem1'/></label>&nbsp;&nbsp;
                                <!-- input type="checkbox" name="expertChannelItem" class="checkbox" value="1"  id="expertChannelItem2" /><label for="expertChannelItem2"><ikep4j:message pre='${preUi}' key='formExpertChannelItem2'/></label>&nbsp;&nbsp;-->
                                <!-- input type="checkbox" name="expertChannelItem" class="checkbox" value="1"  id="expertChannelItem3" /><label for="expertChannelItem3"><ikep4j:message pre='${preUi}' key='formExpertChannelItem3'/></label>&nbsp;&nbsp;-->
                                <!-- input type="checkbox" name="expertChannelItem" class="checkbox" value="1"  id="expertChannelItem4" /><label for="expertChannelItem4"><ikep4j:message pre='${preUi}' key='formExpertChannelItem4'/></label-->
                            </p>
                        </div>
                        <div class="clear"></div>
                    </td> 
                </tr>
                
                <spring:bind path="qnaForm.categoryId">
                <tr> 
                    <th scope="row"><ikep4j:message pre='${preUi}' key='formAlarmTitle'/>
                    </th>
                    <td colspan="3" >
                        <input type="hidden" name="alarmChannel" value="" />
                        <input type="checkbox" name="alarmChannelItem" class="checkbox" value="1"  id="alarmChannelItem1" /><label for="alarmChannelItem1"><ikep4j:message pre='${preUi}' key='formExpertChannelItem1'/></label>&nbsp;&nbsp; 
                        <!--input type="checkbox" name="alarmChannelItem" class="checkbox" value="1"  id="alarmChannelItem2" /><label for="alarmChannelItem2"><ikep4j:message pre='${preUi}' key='formExpertChannelItem2'/></label>&nbsp;&nbsp;-->
                        <!--input type="checkbox" name="alarmChannelItem" class="checkbox" value="1"  id="alarmChannelItem3" /><label for="alarmChannelItem3"><ikep4j:message pre='${preUi}' key='formExpertChannelItem3'/></label>&nbsp;&nbsp;-->
                    </td> 
                </tr>               
                </spring:bind>  
                
                <spring:bind path="qnaForm.contents">
                    <tr> 
                        <td colspan="4">
                            <div id="editorDiv">
                            <textarea id="contents" name="${status.expression}" class="tabletext" cols="" rows="20" title="${status.expression}">${status.value}</textarea>
                            <label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
                            </div>
                        </td> 
                    </tr>               
                </spring:bind> 
                
                <tr>
                    <td colspan="4">
                        <table style="width:100%;">
                            <tr>
                                <td colspan="2" style="border-color:#e5e5e5">
                                    <OBJECT id="FileUploadManager" codeBase="<c:url value="/Bin/DEXTUploadX.cab#version=2,8,2,0"/>"
                                        height="140" width="100%" classid="CLSID:DF75BAFF-7DD5-4B83-AF5E-692067C90316" VIEWASTEXT>
                                         <param name="ButtonVisible" value="FALSE" />
                                         <param name="VisibleContextMenu" value="FALSE" />
                                         <param name="StatusBarVisible" value="FALSE" />
                                         <param name="VisibleListViewFrame" value="FALSE" />
                                    </OBJECT>
                                </td>
                            <tr>
                    
                            <tr>
                                <td style="border-right:none;border-color:#e5e5e5;background-color:#e8e8e8">전체 <span id="_StatusInfo_count"></span>개 <span id="_StatusInfo_size"></span><span id="_Total_size"></span></div>
                                <td align="right" style="border-left:none;border-color:#e5e5e5;background-color:#e8e8e8;">
                                <img src="<c:url value="/base/images/dextupload/btn_fileplus_normal.gif"/>" id="imgBtn_fileAdd" name="Image2"  border="0" onclick="btnAddFile_Onclick()" style="cursor:pointer;valign:absmiddle" />
                                <img src="<c:url value="/base/images/dextupload/btn_listdelete_normal.gif"/>" name="Image4"  border="0" onclick="btnDeleteItem_Onclick()" style="cursor:pointer;valign:absmiddle" />    
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                
                <spring:bind path="qnaForm.tag">
                <tr>
                    <th scope="row"><label for="tag"><ikep4j:message pre='${preUi}' key='formTag'/></label></th>
                    <td colspan="3">
                        <div>
                        
                        <c:choose>
                            <c:when test="${!empty(status.value)}">
                                <c:set var="tagText" value="${status.value}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="tagText" ><c:forEach var="tag" items="${tagList}" varStatus="tagLoop"><c:if test="${tagLoop.count != 1}">,</c:if>${tag.tagName}</c:forEach></c:set>
                            </c:otherwise>
                        </c:choose>
                    
                        <input name="tag" id="tag" class="inputbox w100" type="text" value="${tagText }"/>
                        <div class="tdInstruction">※ <ikep4j:message pre='${preUi}' key='formTagText'/></div>
                        <label for="${status.expression}" class="serverError"> ${status.errorMessage}</label>
                        </div>
                    </td>
                </tr>   
                </spring:bind>                      
            </tbody>
        </table>
    </div>
    <!--//blockDetail End-->
    
    <!--blockButton Start-->
    <div class="blockButton"> 
        <ul>
            <li><a class="button" href="#a" onclick="submitQna();return false;" title="<ikep4j:message pre='${preUi}' key='formCreateBtn'/>"><span><ikep4j:message pre='${preUi}' key='formCreateBtn'/></span></a></li>
            <li><a class="button" href="#a" onclick="goList('${qnaForm.listType}');return false;" title="<ikep4j:message pre='${preUi}' key='formListBtn'/>"><span><ikep4j:message pre='${preUi}' key='formListBtn'/></span></a></li>
        </ul>
    </div>
    <!--//blockButton End-->
    
    </form>
    
<!--guideWrapper End -->    
