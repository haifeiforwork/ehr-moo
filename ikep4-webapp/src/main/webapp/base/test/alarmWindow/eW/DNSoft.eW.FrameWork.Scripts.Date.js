/************************************************************************
작성목적	: 날짜 공통 함수
작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/

/*****************************************
작성목적	: 날짜 형식을 검사한다.
		  Parameter : sDate : 날짜
		  Return : 유효성 여부 (bool)

작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_DataVaildCheck(sDate){
	var bDateCheck = false;
	try{
		var arrDate = sDate.split("-");
		// "-"으로 분리된 년월일을 확인한다.
		if ( arrDate.length < 3 )
			return bDateCheck;

		if ( (fn_GetInt(arrDate[0]) < 0 || fn_GetInt(arrDate[0]) > 9999 ) || (isNaN(arrDate[0])) ){
			return bDateCheck;
		}
		if ( (fn_GetInt(arrDate[1]) < 1 || fn_GetInt(arrDate[1]) > 12 ) || (isNaN(arrDate[1])) ){
			return bDateCheck;
		}
		if ( (fn_GetInt(arrDate[2]) < 1 || fn_GetInt(arrDate[2]) > 31 ) || (isNaN(arrDate[2])) ){
			return bDateCheck;
		}
		bDateCheck = true;
		return bDateCheck;
	}
	catch (exception){
		bDataCheck = false;
		return bDateCheck;
	}
}

/*****************************************
작성목적	: 두 날짜 사이의 간격을 리턴한다.
Parameter : sDate : 시작일자
		  	eDate : 종료일자
Return : 차이 일수 (int)

작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_CalculateDateDiff(sDate, eDate){
	var arrDate;
	var dtStart, dtEnd, dtDiff;
	var iDays;
	try{
		arrDate = sDate.split("-");
		dtStart = new Date( fn_GetInt(arrDate[0]), fn_GetInt(arrDate[1]) - 1, fn_GetInt(arrDate[2]) );
		arrDate = eDate.split("-");
		dtEnd = new Date( fn_GetInt(arrDate[0]), fn_GetInt(arrDate[1]) - 1, fn_GetInt(arrDate[2]) );
		dtDiff = dtEnd.getTime() - dtStart.getTime();
		iDays = Math.floor(dtDiff/1000/60/60/24);
		return iDays;
	}
	catch (exception) {return null;}
}

/*****************************************
작성목적	: 해당 날로부터 일수를 더한 일자를 반환한다.
Parameter : sDate : 일자
		  	eDate : 더할 일자
Return : 계산된 일자 ( string : YYYY-MM-DD )

작 성 자	: 유승호( (주) 인터데브 )
최초작성일	: 2004.01.07
*****************************************/
function fn_AddDate(sDate, iDate){
	var strReturn;
	var arrDate;
	var dtOld;
	var iOld;
	var dtNew;
	try{
		arrDate = sDate.split("-");
		dtOld = new Date( fn_GetInt(arrDate[0]), fn_GetInt(arrDate[1]) - 1, fn_GetInt(arrDate[2]));
		iOld = dtOld.getTime();
		dtNew = new Date( iOld + ( iDate * 1000 * 60 * 60 * 24));
		strReturn = dtNew.getYear() + "-" + fn_LeadingZero(dtNew.getMonth() + 1) + "-" + fn_LeadingZero(dtNew.getDate());
	}
	catch ( exception ) {}
	return strReturn;
}