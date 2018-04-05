<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/tld/ikep4j.tld" prefix="ikep4j" %> 

<c:set var="preSearch"  value="ui.communication.common.searchCondition" />
<c:set var="companyCodeUiPrefix" value="ui.portal.admin.code.companyCode"/>
<c:set var="companyCodeListPrefix" value="message.portal.admin.code.companyCode.companyCodeList"/>

        <script type="text/javascript" language="javascript">
        //<!--
        
            // 상단에 보이게 될 리스트를 가져오는 함수
            function getList() {
                $jq("#searchForm").attr("action", "<c:url value='/lightpack/officesupplies/exceptOfficesuppliesList.do' />");
                $jq("#searchForm")[0].submit();
            }
        
            // 하단에 보이게 될 상세정보를 가져오는 함수
            /* function getView(companyCode, fieldName, itemTypeCode, tr) {

                var selectClassName = "bgSelected";
                $jq(tr).parent().parent().children().removeClass(selectClassName).end()
                        .end().end().addClass(selectClassName);
        
                $jq.ajax({
                    url : '<c:url value="getExceptForm.do" />',
                    data : {
                        code : companyCode, 
                        fieldName : fieldName,
                        itemTypeCode : itemTypeCode
                    },
                    type : "get",
                    success : function(result) {
                        $jq("#viewDiv").html(result);
                    }
                });
            } */
            
            function getView(productNo, productName) {
            	$jq("#codeCheck").val("modify");
            	$jq("#productNo").val(productNo);
            	$jq("#productName").val(productName);
            }
        
            // 코드 중복을 체크함
            function checkCode() {
                if($jq("#productNo").val()=='') {
                    alert("제품번호를 입력해주세요");
                } else {
                    $jq.ajax({
                        url : '<c:url value="/lightpack/officesupplies/checkProductno.do" />',     
                        data : {productNo: $jq("#productNo").val()},     
                        type : "post",
                        success : function(result) {     
                            
                            if(result == 'duplicated') {
                                alert("제품번호가 중복되었습니다.");
                                $jq("#codeCheck").val("modify");
                            } else {
                                alert("사용가능한 제품번호입니다.");
                                $jq("#codeCheck").val("success");
                            }
                        }
                    });
                }
            }
            
            // 코드를 삭제함
            function deleteForm() {
                if ($jq("#productNo").val() == "") {
                    alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.cantDelete" />");
                    return;
                }
                
                if(confirm("<ikep4j:message pre="${companyCodeListPrefix}" key="confirm.wannaDelete" />")) {
                    $jq.ajax({
                        url : '<c:url value="/lightpack/officesupplies/deleteOfficesuppliesExcept.do" />',
                        data : $jq("#company").serialize(),
                        type : "post",
                        success : function(result) {
                            alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.deleteCompleted" />");
                            getList();
                        },
                        error:function(){
                            alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.failedToDelete" />");
                        }
                    });
                } else {
                    return;
                }
            }
            
            
            // 페이지 로딩시 실행하는 jQuery 코드로 list와 view를 불러온다.
            $jq(document).ready(function() {
                
                //left menu setting
              //  $jq("#companycodeOfLeft").click();
                
                // 페이지 로딩 직후 폼의 맨 첫번째 input 박스에 포커스
                //$jq("#companyCode :input:visible:enabled:first").focus().select();
                
                $jq("#newFormButton").click(function() {
                    $jq("#codeCheck").val("");
                    getView();
                });
                
                $jq("#submitButton").click(function() {
	                if (($jq("#codeCheck").val() == 'success') || ($jq("#codeCheck").val() == 'modify')) {
	                    $jq.ajax({
	                        url : '<c:url value="/lightpack/officesupplies/createOfficesuppliesExcept.do" />',
	                        data : $jq("#company").serialize(),
	                        type : "post",
	                        success : function(result) {
	                            alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.saveCompleted" />");
	                            getList();
	                        },
	                        error : function(xhr, exMessage) {
	                        var errorItems = $.parseJSON(xhr.responseText).exception;
	                        //validator.showErrors(errorItems);
	                        }
	                    });
	                } else {
	                    alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.checkDuplicaed" />");
	                    return;
	                }
                });
                
                $jq("#deleteButton").click(function() {
                    deleteForm();
                });
                
                // ID 중복을 체크함
                $jq("#checkCodeButton").click(function() {
                    checkCode();
                });
                
                $jq("#searchBoardItemButton").click(function() {   
                    getList();
                });
                
                // code 값이 바뀌면 중복체크를 위해 codeCheck 값을 바꿔줌
                $jq("input[name=productNo]").change(function() {
                    $jq("#codeCheck").val('changed');
                });
                
                // 백스페이스 방지(input, select 제외)
                $jq(document).keydown(function(e) {
                    var element = e.target.nodeName.toLowerCase();
                    if (element != 'input') {
                        if (e.keyCode === 8) {
                            return false;
                        }
                    }
                });
                
                var validOptions = {
                        rules : {
                            companyCode : {
                                required : true,
                                rangelength : [0, 20]
                            },
                            companyCodeName : {
                                required : true,
                                rangelength : [0, 20]
                            },
                            companyCodeEnglishName : {
                                required : true,
                                companyEnglishName : true,
                                rangelength : [0, 100]                                      
                            }
                        },
                        messages : {
                            companyCode : {
                                required : "<ikep4j:message key="NotNull.companyCode.companyCode" />",
                                rangelength : "<ikep4j:message key="Size.companyCode.companyCode" />"
                            },
                            companyCodeName : {
                                required : "<ikep4j:message key="NotNull.companyCode.companyCodeName" />",
                                rangelength : "<ikep4j:message key="Size.companyCode.companyCodeName" />"
                            },
                            companyCodeEnglishName : {
                                required : "<ikep4j:message key="NotNull.companyCode.companyCodeEnglishName" />",
                                companyEnglishName : "<ikep4j:message key="EnglishName.companyCode.companyCodeEnglishName" />",
                                rangelength : "<ikep4j:message key="Size.companyCode.companyCodeEnglishName" />"
                            }
                        },
                        /* submitHandler : function(company) {alert();
                            
                            // 코드를 등록하는 함수, 먼저 코드의 중복을 체크했는지 검사한 후 등록을 수행함
                            if (($jq("#codeCheck").val() == 'success') || 
                                    ($jq("#codeCheck").val() == 'modify')) {
                                $jq.ajax({
                                    url : '<c:url value="/createOfficesuppliesExcept.do" />',
                                    data : $jq("#company").serialize(),
                                    type : "post",
                                    success : function(result) {
                                        alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.saveCompleted" />");
                                        $jq("#tempCode").val(result);
                                        getList();
                                    },
                                    error : function(xhr, exMessage) {
                                    var errorItems = $.parseJSON(xhr.responseText).exception;
                                    //validator.showErrors(errorItems);
                                    }
                                });
                            } else {
                                alert("<ikep4j:message pre="${companyCodeListPrefix}" key="alert.checkDuplicaed" />");
                                return;
                            }
                        } */

                     };

                //var validator = new iKEP.Validator("#company", validOptions);
                
            });
        //-->
        </script>
        
            <!--blockListTable Start-->
            <div class="blockListTable">
                <div id="listDiv">
                    <form id="searchForm" name="searchForm" method="post" onsubmit="getList(); return false;" action="">
    
                        <spring:bind path="searchCondition.sortColumn">
                            <input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                        </spring:bind>
                        <spring:bind path="searchCondition.sortType">
                            <input name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                        </spring:bind>
                        
                        <!--tableTop Start-->  
                        <div class="tableTop">
                            <div class="tableTop_bgR"></div>
                            <h2>신청 제외 품목 관리</h2>
                            <div class="listInfo"> 
                                <spring:bind path="searchCondition.pagePerRecord">  
                                    <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />' onchange="javascript:getList()">
                                        <c:forEach var="code" items="${boardCode.pageNumList}">
                                            <option value="${code.key}" <c:if test="${code.key eq status.value}">selected="selected"</c:if>>${code.value}</option>
                                        </c:forEach> 
                                    </select> 
                                </spring:bind>
                                <div class="totalNum">
                                    ${searchResult.pageIndex}/ ${searchResult.pageCount} <ikep4j:message pre='${preSearch}' key='page' /> 
                                    ( <ikep4j:message pre='${preSearch}' key='pageCount.info' /><span> ${searchResult.recordCount}</span>)
                                </div>
                            </div>
                            <div class="tableSearch"> 
                                <spring:bind path="searchCondition.searchColumn">  
                                    <select name="${status.expression}" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'>
                                        <option value="productNo" <c:if test="${'code' eq status.value}">selected="selected"</c:if> >제품번호</option>
                                        <option value="productName" <c:if test="${'title' eq status.value}">selected="selected"</c:if> >품목</option>
                                    </select>   
                                </spring:bind>      
                                <spring:bind path="searchCondition.searchWord">                     
                                    <input name="${status.expression}" value="${status.value}" type="text" class="inputbox" title='<ikep4j:message pre='${preSearch}' key='${status.expression}' />'  size="20" />
                                </spring:bind>
                                <a id="searchBoardItemButton" name="searchBoardItemButton" href="#a" class="ic_search">
                                    <span><ikep4j:message pre='${preSearch}' key='search' /></span>
                                </a>
                            </div>
                            <div class="clear"></div>   
                        </div>
                        <!--//tableTop End-->   
                        
                        <table>
                            <caption></caption>
                                <colgroup>
                                    <col width="20%"/>
                                    <col width="*"/>
                                </colgroup>
                            <thead>
                                <tr>
                                    <th scope="col">제품번호</th>
                                    <th scope="col">품목</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <c:when test="${searchResult.emptyRecord}">
                                        <tr>
                                            <td colspan="2" class="emptyRecord"><ikep4j:message pre='${companyCodeListPrefix}' key='emptyRecord' /></td> 
                                        </tr>                       
                                    </c:when>
                                    <c:otherwise>
                                        <c:forEach var="companyCode" items="${searchResult.entity}" varStatus="status">
                                            <tr>
                                                <td class="textCenter"><a href="#a" onclick="getView('${companyCode.productNo}', '${companyCode.productName}')">${companyCode.productNo}</a></td>
                                                <td class="textCenter"><a href="#a" onclick="getView('${companyCode.productNo}', '${companyCode.productName}')">${companyCode.productName}</a></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>
                        
                        <!--Page Number Start--> 
                        <spring:bind path="searchCondition.pageIndex">
                            <ikep4j:pagination searchButtonId="searchBoardItemButton" pageIndexInput="${status.expression}" searchCondition="${searchCondition}" />
                            <input id="${status.expression}" name="${status.expression}" type="hidden" value="${status.value}" title="<ikep4j:message pre='${preSearch}' key='${status.expression}'/>" />
                        </spring:bind> 
                        <!--//Page Number End-->
                        
                        <input type="hidden" name="tempCode" id="tempCode" value="" />

                    </form>
                </div>
            </div>
            <!--//blockListTable End-->
        
        <div class="blockBlank_10px"></div>
        
            <!--blockDetail Start-->
            <div class="blockDetail">
                <div id="viewDiv">
                    <form id="company" name="company" method="post" onsubmit="return false;" action="">
                    
                        <input type="hidden" id="codeCheck" name="codeCheck" value=""/>
                    
                        <table>
                        <caption></caption>
                            <colgroup>
                                <col width="20%"/>
                                <col width="*"/>
                            </colgroup>                             
                        <tbody>
                            <tr>
                                <th>제품번호</th>
                                <td>
                                    <div>
                                        <input type="text" name="productNo" id="productNo" value="" size="50" class="inputbox" />
                                        &nbsp;<a class="button_ic" id="checkCodeButton" href="#a"><span><ikep4j:message pre="${companyCodeUiPrefix}" key="checkDuplicated" /></span></a>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <th>품목</th>
                                <td>
                                    <div>
                                        <input type="text" name="productName" id="productName" value="" size="50" class="inputbox" />
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                        
                        </table>
                    </form>
                </div>
            </div>
            <!--//blockDetail End--> 
        
        <!--blockButton Start-->
        <div class="blockButton">
            <ul>
                <li><a class="button" id="submitButton" href="#a"><span><ikep4j:message pre="${companyCodeUiPrefix}" key="button.save" /></span></a></li>
                <li><a class="button" id="deleteButton" href="#a"><span><ikep4j:message pre="${companyCodeUiPrefix}" key="button.delete" /></span></a></li>
            </ul>
        </div>
                <!--//blockButton End-->