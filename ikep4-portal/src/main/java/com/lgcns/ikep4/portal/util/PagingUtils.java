package com.lgcns.ikep4.portal.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PagingUtils {
	
	public static ArrayList<Map<?, ?>> setPagingParam(ArrayList<Map<?, ?>> tempList, HashMap<String, Object> params){
		
		int totCnt = tempList.size();	

		if(totCnt == 0){
			return null;
		}

		ArrayList<Map<?, ?>> list = new ArrayList<Map<?, ?>>();
		
		int rowUnit = 35;  //한 화면에 보여줄 데이터 건수 초기 세팅
		int pageUnit = 10; //한 화면에 보여줄 페이지내비게이션의 숫자 초기세팅

		int currentPage = 1;  //현재 페이지
		
		if(params.containsKey("currentPage")){
			currentPage = Integer.parseInt((String)params.get("currentPage"));
		}
		
		params.put("currentPage", currentPage);
		
		int startRow = (currentPage - 1) * rowUnit;  //시작데이터 Row
		int endRow = startRow + rowUnit > totCnt ? totCnt : startRow + rowUnit;  //마지막데이터 Row
		
		for(int i = startRow ; i < endRow ; i++){
			list.add(tempList.get(i));
		}

		int lastPage = totCnt % rowUnit > 0 ? (totCnt / rowUnit) + 1 : totCnt / rowUnit; //마지막 페이지 수
		
		int startPage = 0;  //시작페이지
		int endPage = 0; //종료페이지
				
		//true면 화살표를 보여주고 false면 보여주지 않는다.
		boolean leftDoubleArrow = true;
		boolean rightDoubleArrow = true;
		
		boolean leftSingleArrow = true;
		boolean rightSingleArrow = true;
		
		if(currentPage - pageUnit < 1){
			startPage = 1;
			leftDoubleArrow = false;
		}else{
			startPage = ((currentPage - 1) / pageUnit) * pageUnit + 1 ;
		}
		
		if(startPage + (pageUnit - 1) > lastPage){
			endPage = lastPage;
			rightDoubleArrow = false;
		}else{
			endPage = startPage + (pageUnit - 1);
		}
		
		if(1 == currentPage){
			leftSingleArrow = false;
		}
		
		if(lastPage == currentPage){
			rightSingleArrow = false;
		}
		
		params.put("totCnt"  , totCnt);
		params.put("lastPage", lastPage);
		
		params.put("startPage", startPage);
		params.put("endPage"  , endPage);
		
		params.put("leftDoubleArrow" , leftDoubleArrow);
		params.put("rightDoubleArrow", rightDoubleArrow);
		params.put("leftSingleArrow" , leftSingleArrow);
		params.put("rightSingleArrow", rightSingleArrow);
		
		return list;
	}

}
