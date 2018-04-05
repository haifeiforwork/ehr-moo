<%@ include file="/base/common/taglibs.jsp"%>
<%@ page language="java" errorPage="/common/error.jsp" pageEncoding="UTF-8" contentType="text/html;charset=utf-8"%>

 <!--blockDetail Start-->
    <div class="blockDetail">
        <table summary="new group">
            <thead>
                <tr>
                    <th scope="col" class="textCenter" width="10%">&nbsp;</th>
                    <th scope="col" class="textCenter" width="22.5%">Target고객</th>
                    <th scope="col" class="textCenter" width="22.5%">이익개선고객</th>
                    <th scope="col" class="textCenter" width="22.5%">매출개선고객</th>
                    <th scope="col" class="textCenter" width="22.5%">관리대상고객</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <th rowspan="10" class="textCenter" scope="row">가격</th>
                    <td>물량 인센티브</td>
                    <td>소량출고 가격할인</td>
                    <td>소량출고 가격할인</td>
                    <td>물량 인센티브</td>
                   
                </tr>
                <tr>
                    <td>소량출고 가격할인</td>
                    <td>자가수송 가격할인</td>
                    <td>결제조건 기준 가격할인</td>
                    <td>결제조건 기준 가격할인</td>
                </tr>
                <tr>
                    <td>자가수송 가격할인</td>                   
                    <td>결제조건 기준 가격할인</td>
                    <td>시황 가격할인</td>
                    <td>시황 가격할인</td>                           
                </tr>
                <tr>
                    <td>결제조건 기준 가격할인</td>                                       
                    <td>시황 가격할인</td>
                    <td>Local수출판매 가격 차별화</td>
					<td>신제품 시산품 할인</td>                        
                </tr>
                <tr>
                    <td>시황 가격할인</td>                                                          
                    <td>Local수출판매 가격 차별화</td>
                    <td>장기계약 가격할인</td>
					<td>여폭 가격할인</td>                        
                </tr>
                <tr>
                    <td>고정물량 가격할인</td>                                                          
                    <td>신제품 시산품 할인</td>
                    <td></td>
					<td>장기재고품 가격할인</td>                        
                </tr>              
                <tr>
                    <td>Local수출판매 가격 차별화</td>                                                          
                    <td>시즌물량 가격할인</td>
                    <td></td>
					<td></td>                        
                </tr>
                <tr>
                    <td>시즌물량 가격할인</td>                                                          
                    <td>여폭 가격할인</td>
                    <td></td>
					<td></td>                        
                </tr>
                <tr>
                    <td>장기계약 가격할인</td>                                                          
                    <td>장기재고품 가격할인</td>
                    <td></td>
					<td></td>                        
                </tr>
                <tr>
                    <td>고정물량 특별할인</td>                                                          
                    <td></td>
                    <td></td>
					<td></td>                        
                </tr>
                
                <tr>
                    <th rowspan="8" class="textCenter" scope="row">물량공급</th>
                    <td>소량출고</td>
                    <td></td>
                    <td>소량출고</td>
                    <td>제품 공동개발</td>
                </tr>
                <tr>
                 	<td>선수판매</td>
                    <td></td>
                    <td>규격대체</td>
                    <td>신제품 개발</td>
                </tr>
                <tr>
                 	<td>규격대체</td>
                 	<td></td>
                 	<td>내수 OEM</td>
                 	<td></td>                 	
                </tr>
                <tr>
                 	<td>내수 OEM</td>
                 	<td></td>                 	
                 	<td>Spot성 물량 공급</td>                
                 	<td></td>                 	
                </tr>
                <tr>
                 	<td>Spot성 물량 공급</td>
                 	<td></td>                 	
                 	<td>변규격 물량 공급</td>                
                 	<td></td>                 	
                </tr>
                <tr>
                 	<td>변규격 물량 공급</td>
                 	<td></td>                 	
                 	<td>제품 공동개발</td>                
                 	<td></td>                 	
                </tr>
                <tr>
                 	<td>제품 공동개발</td>
                 	<td></td>                 	
                 	<td>신제품 개발</td>                
                 	<td></td>                 	
                </tr>
                <tr>
                 	<td>신제품 개발</td>
                 	<td></td>                 	
                 	<td></td>                
                 	<td></td>                 	
                </tr>
                <tr>
                    <th rowspan="5" class="textCenter" scope="row">물류(배송)</th>
                    <td>적기 배송</td>
                    <td>적기 배송</td>
                    <td>적기 배송</td>
                    <td>적기 배송</td>
                </tr>
                <tr>
                    <td>자가수송 허용</td>
                    <td>자가수송 허용</td>
                    <td>자가수송 허용</td>
                    <td>자가수송 허용</td>
                </tr>
                <tr>
                    <td>해피콜서비스</td>
                    <td>해피콜서비스</td>
                    <td>해피콜서비스</td>
                    <td>해피콜서비스</td>
                </tr>
                <tr>
                    <td>물류클레임 보상</td>
                    <td>물류클레임 보상</td>
                    <td>물류클레임 보상</td>
                    <td>물류클레임 보상</td>
                </tr>
                <tr>
                    <td>물류기사 교육</td>
                    <td>물류기사 교육</td>
                    <td>물류기사 교육</td>
                    <td>물류기사 교육</td>
                </tr>
                 <tr>
                    <th rowspan="8" class="textCenter" scope="row">기술(품질)서비스</th>
                    <td>신제품 방문 교육</td>
                    <td>신제품 방문 교육</td>
                    <td>신제품 방문 교육</td>
                    <td>무상 샘플 제공</td>
                </tr>
                <tr>
                    <td>지종별 품질정보 제공</td>
                    <td>지종별 품질정보 제공</td>
                    <td>지종별 품질정보 제공</td>
                    <td>기술세미나</td> 
                </tr>
                 <tr>
                    <td>실험장비 이용 서비스</td>
                    <td>용도별 품질 관리</td>
                    <td>용도별 품질 관리</td>                 
                    <td></td>    
                </tr>
                 <tr>
                    <td>제품 분석결과 제공</td>
                    <td>무상 샘플 제공</td> 
                    <td>무상 샘플 제공</td>    
                    <td></td>                       
                </tr>
                <tr>
                    <td>용도별 품질 관리</td>
                    <td>품질보증서비스</td> 
                    <td>품질보증서비스</td>                
                    <td></td>                       
                </tr>
                <tr>
                    <td>무상 샘플 제공</td> 
                    <td>기술세미나</td>    
                    <td>기술세미나</td>    
                    <td></td>                       
                </tr>
                <tr>
                    <td>품질보증서비스</td> 
                    <td></td>    
                    <td></td>    
                    <td></td>                       
                </tr>
               	<tr>
                    <td>기술세미나</td> 
                    <td></td>    
                    <td></td>    
                    <td></td>                       
                </tr>
                 <tr>
                    <th rowspan="6" class="textCenter" scope="row">정보제공서비스</th>
                    <td>납기알림서비스</td>
                    <td>납기알림서비스</td>
                    <td>납기알림서비스</td>
                    <td>납기알림서비스</td>
                </tr>
                <tr>
                    <td>국내 시장 정보</td>
                    <td>국내 시장 정보</td>
                    <td>국내 시장 정보</td>
                    <td>샘플북 제공</td>
                </tr>
                 <tr>
                    <td>해외 시장 정보</td>
                    <td>고객별 공급 가능량 정보 제공</td>
                    <td>고객별 공급 가능량 정보 제공</td>
                    <td>정보지 발송</td>                 
                </tr>
                 <tr>
                    <td>고객별 공급 가능량 정보 제공</td>
                    <td>샘플북 제공</td>
                    <td>샘플북 제공</td>
                    <td></td>   
                </tr>
                <tr>
                    <td>샘플북 제공</td>
                    <td>정보지 발송</td>
                    <td>정보지 발송</td>
                    <td></td>   
                </tr>
                <tr>
                    <td>정보지 발송</td>
                    <td></td>   
                    <td></td>   
                    <td></td>   
                </tr>
                 <tr>
                    <th rowspan="5" class="textCenter" scope="row">채권서비스</th>
                    <td>법률서비스</td>
                    <td>법률서비스</td>
                    <td>무담보거래</td>
                    <td></td>
                </tr>
                <tr>
                    <td>무상감정평가</td>     
                    <td>무담보거래</td>
                    <td>신용조사 대행</td>
                    <td></td>
                </tr>
                 <tr>             
                    <td>채권회수 대행</td>
                    <td>신용조사 대행</td>
                    <td></td>
                    <td></td>
                </tr>
                 <tr>
                    <td>무담보거래</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                <tr>
                    <td>신용조사 대행</td>
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                  <tr>
                    <th rowspan="10" class="textCenter" scope="row">기타</th>
                    <td>공장견학</td>
                    <td>공장견학</td>
                    <td>공장방문</td>
                    <td>지속적 방문</td>
                </tr>
                <tr>
                    <td>커뮤니티</td>
                    <td>커뮤니티</td>
                    <td>지속적 방문</td>
                    <td></td>
                </tr>
                <tr>
                    <td>고객사 행사 지원</td>               
                    <td>고객사 행사 지원</td>               
                    <td>이벤트(on/off)</td>
                    <td></td>
                </tr>
                <tr>                  
                    <td>기념일 축하</td>                    
                    <td>기념일 축하</td>                    
                    <td>당사 관계 휴양시설 제공</td>                    
                    <td></td>
                </tr>
                <tr>                  
                    <td>지속적 방문</td>                    
                    <td>지속적 방문</td>                    
                    <td>광고지원</td>                    
                    <td></td>
                </tr>
                 <tr>                  
                    <td>이벤트(on/off)</td>                    
                    <td>이벤트(on/off)</td>                    
                    <td></td>                    
                    <td></td>
                </tr>
                 <tr>                  
                    <td>당사 관계 휴양시설 제공</td>                    
                    <td>당사 관계 휴양시설 제공</td>                    
                    <td></td>
                    <td></td>
                </tr>
                 <tr>                  
                    <td>광고지원</td>                    
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                 <tr>                  
                    <td>홍보활동 지원(간판, 홈페이지)</td>                    
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
                 <tr>                  
                    <td>해외시장 개척 지원</td>                    
                    <td></td>
                    <td></td>
                    <td></td>
                </tr>
            </tbody>
        </table>
    </div>
    <!--//blockDetail End-->		
    
