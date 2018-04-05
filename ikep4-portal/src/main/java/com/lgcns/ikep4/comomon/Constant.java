package com.lgcns.ikep4.comomon;

public class Constant {


	//휴일 근무 신청/조회 리스트 키셋
	public static final String holidayWorkListKeySet[] = { "MANDT", "APPID", "APSEQ", "APPIDT", "PERNR", "INFTY", "SUBTY", "OBJPS"
														 , "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX"
														 , "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "BEGUZ", "ENDUZ"
														 , "VTKEN", "AWART", "ABWTG", "STDAZ", "ABRTG", "ABRST", "ANRTG", "LFZED", "KRGED", "KBBEG", "RMDDA"
														 , "KENN1", "KENN2", "KALTG", "URMAN", "BEGVA", "BWGRL", "AUFKZ", "TRFGR", "TRFST", "PRAKN", "PRAKZ"
														 , "OTYPE", "PLANS", "MLDDA", "MLDUZ", "RMDUZ", "VORGS", "UMSKD", "UMSCH", "REFNR", "UNFAL", "STKRV"
														 , "STUND", "PSARB", "AINFT", "GENER", "HRSIF", "ALLDF", "WAERS", "LOGSYS", "AWTYP", "AWREF", "AWORG"
														 , "DOCSY", "DOCNR", "PAYTY", "PAYID", "BONDT", "OCRSN", "SPPE1", "SPPE2", "SPPE3", "SPPIN", "ZKMKT", "FAPRS"
														 , "TDLANGU", "TDSUBLA", "TDTYPE", "NXDFL", "OPERA", "DESC1", "DESC2", "WT_192", "WT_TIME"
														 , "CWT_192", "CWT_TIME", "PRTXT", "ATEXT", "REQNR", "REQDT", "REQTM", "ASTAT", "DFLAG", "FILEV", "ASTATT"
														 , "BTTIME", "STIME" };
	//결재자 정보 키셋
	public static final String apprKeySet[] = { "MANDT", "APPID", "APLEV", "APPNR", "ASTAT", "REQDT", "REQTM", "EFLAG", "RPERNR", "ENAME", "ORGEH"
													 	 , "ORGTX", "ZZJIK", "COTXT", "JIKWI", "APPNRT", "ASTATT", "ADATE", "ATIME" };
	//결재 가능자 정보 키셋
	public static final String apprAbleKeySet[] = { "APPNR", "APPNRT", "ENAME", "LNMHG", "FNMHG", "ORGEH", "ORGTX", "PLANS", "PLSTX", "WERKS", "EMAIL"
													 		 , "PBTXT", "PHOTO", "ICON", "HREF", "JIKWI", "ORGLV", "LOCAT", "BTRTL", "ZZJIK", "COTXT" };

	//결재 가능자 정보 키셋(팝업용)
	public static final String apprAblePopKeySet[] = { "PERNR", "PERNRT", "ENAME", "LNMHG", "FNMHG", "ORGEH", "ORGTX", "PLANS", "PLSTX", "COMTP", "HOMTP"
													 , "CELTP", "WERKS", "EMAIL", "PBTXT", "PHOTO", "ICON", "HREF", "JIKWI", "TRFGR", "COTXT" };

	//휴가 신청/조회 리스트 키셋
	public static final String holidayListKeySet[] = { "MANDT", "APPID", "APSEQ", "APPIDT", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR"
													 , "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4"
													 , "RESE1", "RESE2", "GRPVL", "BEGUZ", "ENDUZ", "VTKEN", "AWART", "ABWTG", "STDAZ", "ABRTG", "ABRST", "ANRTG"
													 , "LFZED", "KRGED", "KBBEG", "RMDDA", "KENN1", "KENN2", "KALTG", "URMAN", "BEGVA", "BWGRL", "AUFKZ", "TRFGR"
													 , "TRFST", "PRAKN", "PRAKZ", "OTYPE", "PLANS", "MLDDA", "MLDUZ", "RMDUZ", "VORGS", "UMSKD", "UMSCH", "REFNR"
													 , "UNFAL", "STKRV", "STUND", "PSARB", "AINFT", "GENER", "HRSIF", "ALLDF", "WAERS", "LOGSYS", "AWTYP", "AWREF"
													 , "AWORG", "DOCSY", "DOCNR", "PAYTY", "PAYID", "BONDT", "OCRSN", "SPPE1", "SPPE2", "SPPE3", "SPPIN", "ZKMKT"
													 , "FAPRS", "TDLANGU", "TDSUBLA", "TDTYPE", "NXDFL", "OPERA", "DESC1", "DESC2", "ATEXT", "REQNR", "REQDT", "REQTM"
													 , "ASTAT", "DFLAG", "FILEV", "ASTATT", "ABWTGT" };

	//출장 신청/조회 리스트 키셋
	public static final String businessTripListKeySet[] = { "MANDT", "APPID", "APSEQ", "APPIDT", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR"
														  , "REINR", "REQUESTVRS", "PLAN_REQUEST", "LOCATION_END", "COUNTRY_END", "REQUEST_REASON", "DATE_BEG", "TIME_BEG"
														  , "DATE_END", "TIME_END", "ACTIVITY_TYPE", "ESTIMATED_COST", "CURRENCY", "STATUS", "DATES", "TIMES", "UNAME", "REPID"
														  , "DEPAR", "OPERA", "ZPERIOD", "CNTEXT", "ZCNTEXT", "ZACTXT", "ZLOTXT", "ZASTXT", "ZSTATXT", "ASTAT", "ZCNTYP"
														  , "KOSTL", "KOSTXT", "ZTIME_BEG", "ZMINT_BEG", "ZTIME_END", "ZMINT_END", "REQNR", "REQDT", "REQTM", "FILEV" };

	public static final String aListKeySet[] = { "MANDT", "APPID", "APSEQ", "ADVANCE", "PERNR", "REINR", "REQUESTVRS", "PLAN_REQUEST", "VORSC", "WAERS", "KURSV", "FFACT", "TFACT"
											   , "VORHW", "KASSA", "DATVS", "PAYCURR", "WATXT" };

	public static final String eListKeySet[] = { "MANDT", "PERNR", "REINR", "REQUESTVRS", "PLAN_REQUEST", "LINE", "TEXT", "LTEXT" };

	public static final String bListKeySet[] = { "MANDT", "PERNR", "REINR", "REQUESTVRS", "PLAN_REQUEST", "TRANS", "TRANS_BEG", "TRANSGR_BEG", "TRANS_END", "TRANSGR_END" };

	public static final String rListKeySet[] = { "MANDT", "APPID", "APSEQ", "PERNR", "REINR", "PLANNR", "VARIANTE", "VARIANTVRS", "REQUESTVRS", "PLAN_REQUEST", "REQUEST"
										 	   , "ITINERARY_NUMBER", "DATE_BEG", "TIME_BEG", "DATE_END", "TIME_END", "LOCATION_BEG", "COUNTRY_BEG", "LOCATION_END"
										 	   , "COUNTRY_END", "REQUEST_TYPE", "DEP_ARR", "ITEM", "REASON", "ACTIVITY", "FIRST_TCODE", "ZCNTXT", "ZCNTYP", "ZTIME_BEG"
										 	   , "ZTIME_END", "ZMINT_END", "ZMINT_BEG", "ZDATE_BEG", "ACTIVITYT" };

	public static final String zListKeySet[] = { "WAERS", "SBETRG", "DBETRG", "FBETRG", "TRNGD", "EXBETRG", "KURSV", "TBETRG", "KWAERS", "ZDAY", "SDAY", "STBETG", "DTBETG", "FTBETG" };


	//휴복직 신청/조회 리스트 키셋
	public static final String leaveReinstatementKeySet[] = { "APPID", "APSEQ", "MANDT", "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM"
															, "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4"
															, "RESE1", "RESE2", "GRPVL", "MASSG", "RETDT", "MATXT", "COMET", "BEBEGDA", "BEENDDA", "MASSN", "MNTXT"
															, "MGTXT", "ZPERIOD", "ASTAT", "ZASTXT", "ZYEAR", "ZMON", "FILEV", "ZDAY" };


	//HR결재 결재중 리스트 키셋
	public static final String hrApprovalListKeySet[] = { "MANDT", "APPID", "APLEV", "APPNR", "ASTAT", "REQDT", "REQTM", "EFLAG", "RPERNR", "FASTAT", "FASTATT", "ASTATT"
													    , "REQNR", "INFTY", "RNAME", "INFTYT", "BEGDA", "DFLAG", "FREQDT", "FILEV", "HREF", "ENDDA", "APPR_BUTT", "ATEXT"
													    , "CHECKBOX", "SUBTY", "STIME" };


	//주소사항 조회/수정 리스트 키셋
	public static final String addressListKeySet[] = { "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "LAND1", "STATE", "ORT01", "ORT02", "STRAS", "ZTYPE"
													 , "ZADDRESS", "ZADD", "ZSTATE", "PSTLZ", "TELNB", "LOCAT", "ZEDITI" };


	//개인정보 키셋
	public static final String personalHeaderKeySet[] = { "PERNR", "USRID", "BEGDA", "ENDDA", "ENAME", "LNMHG", "FNMHG", "LNMCH", "FNMCH", "NACHN", "VORNA", "REGNO", "GBDAT"
														, "BIRTH", "BUKRS", "BUTXT", "WERKS", "NAME1", "BTRTL", "BTEXT", "PERSG", "PTEXT", "PERSK", "PKTXT", "A1DAT", "A2DAT"
														, "A3DAT", "A4DAT", "CONYR", "ABKRS", "CPIND", "GSBER", "KOSTL", "PLANS", "PLSTX", "STELL", "STLTX", "ORGEH", "ORGTX"
														, "ZZJIK", "COTXT", "TRFGR", "JIKWI", "OBJID", "STEXT", "DBOSS", "DBSNM", "ORGLV", "LOCAT", "LCTTX", "ENTRY", "REFNO"
														, "PTPNR", "CMAIL", "PMAIL", "TRFST", "ZZDLP", "PHOTO", "STAT1", "STAT1_T", "AGE", "PUBSH", "PUBTX", "DATUM", "JTEXT" };

	public static final String personalLst1KeySet[] = { "PERNR", "BEGDA", "ENDDA", "REGNO", "ZZBIR", "GBDAT", "ZZMAR", "FAMDT", "KONFE", "KTEXT", "TELNR", "ZCPTYP", "ZCPTEL"
													  , "ZHPTYP", "ZHPNO", "ZEMTYP", "ZEMAIL", "HOUSE", "STEXT", "CARNO", "ZZHOB", "ZSPTXT", "MYCAR", "ZZBLD", "ZZBDT"
													  , "REGNOX", "ZEMAIL2" };

	public static final String personalLst2KeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX"
													  , "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "EXDAT", "LXDAT", "RESUL"
													  , "DIANR", "SBJ01", "JNF01", "NMF01", "DTF01", "WTF01", "SBJ02", "JNF02", "NMF02", "DTF02", "WTF02", "SBJ03", "JNF03"
													  , "NMF03", "DTF03", "WTF03", "SBJ04", "JNF04", "NMF04", "DTF04", "WTF04", "SBJ05", "JNF05", "NMF05", "DTF05", "WTF05"
													  , "SBJ06", "JNF06", "NMF06", "DTF06", "WTF06", "SBJ07", "JNF07", "NMF07", "DTF07", "WTF07", "SBJ08", "JNF08", "NMF08"
													  , "DTF08", "WTF08", "SBJ09", "JNF09", "NMF09", "DTF09", "WTF09", "SBJ10", "JNF10", "NMF10", "DTF10", "WTF10", "SBJ11"
													  , "JNF11", "NMF11", "DTF11", "WTF11", "SBJ12", "JNF12", "NMF12", "DTF12", "WTF12", "SBJ13", "JNF13", "NMF13", "DTF13"
													  , "WTF13", "SBJ14", "JNF14", "NMF14", "DTF14", "WTF14", "SBJ15", "JNF15", "NMF15", "DTF15", "WTF15", "SBJ16", "JNF16"
													  , "NMF16", "DTF16", "WTF16", "SBJ17", "JNF17", "NMF17", "DTF17", "WTF17", "SBJ18", "JNF18", "NMF18", "DTF18", "WTF18"
													  , "SBJ19", "JNF19", "NMF19", "DTF19", "WTF19", "SBJ20", "JNF20", "NMF20", "DTF20", "WTF20", "SBJ21", "JNF21", "NMF21"
													  , "DTF21", "WTF21", "SBJ22", "JNF22", "NMF22", "DTF22", "WTF22", "SBJ23", "JNF23", "NMF23", "DTF23", "WTF23", "SBJ24"
													  , "JNF24", "NMF24", "DTF24", "WTF24", "SBJ25", "JNF25", "NMF25", "DTF25", "WTF25", "SBJ26", "JNF26", "NMF26", "DTF26"
													  , "WTF26", "SBJ27", "JNF27", "NMF27", "DTF27", "WTF27", "SBJ28", "JNF28", "NMF28", "DTF28", "WTF28", "SBJ29", "JNF29"
													  , "NMF29", "DTF29", "WTF29", "SBJ30", "JNF30", "NMF30", "DTF30", "WTF30", "EXTYP", "ZZTAB", "ZZRT1", "ZZRT2", "ZZDCN"
													  , "ZSTATC", "ZAAA", "ZBBB", "ZCCC", "ZZZ", "NMF01X", "NMF02X", "NMF05X", "NMF06X", "NMF03X", "NMF04X" };

	public static final String personalLst3KeySet[] = { "PERNR", "BEGDA", "ENDDA", "IDNUM", "MRANK", "SERTY", "SERUT", "JOBCL", "PRISK", "RSEXP", "ZZARM", "ZZMNH", "PERIOD"
													  , "RKTXT", "SERTX", "JBTXT", "ZZARTX" };


	//경조사 신청/조회 키셋
	public static final String congratulateListKeySet[] = { "MANDT", "APPID", "APSEQ", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME"
														  , "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL"
														  , "CACCD", "CACRN", "ENAME", "GARLD", "CACMT", "WAERS", "BEGDT", "ENDDT", "BELNR", "OPERA", "ATEXT", "REQNR", "REQDT"
														  , "REQTM", "ASTAT", "DFLAG", "FILEV", "ASTATT", "TODAY", "VACDT", "PERIOD", "ZCACCD", "ZCACRN", "ZGARLD", "ZVACDT" };

	//카페테리아 신청/조회 키셋
	public static final String cafeteriaListKeySet[] = { "MANDT", "PERNR", "ZYEAR", "USEDT", "SEQNM", "CRECK", "CAFCD", "CREPT", "PROPT", "USEPT", "RENPT", "CONCK"
														, "AEDTM", "UNAME", "CAFTX", "USEPTT", "RENPTT" };

	//이사비/부임비/파견비 신청/조회 키셋
	public static final String expenseListKeySet[] = { "MANDT", "APPID", "APSEQ", "OPERA", "PERNR", "MOVDT", "MOVTY", "REQGN", "SLEEP", "MOVMT", "WAERS", "ZAREA", "MARRY"
													 , "MOVTX", "REQTX", "ASTAT", "ASTATT", "DFLAG", "FILEV", "REQDT", "REQTM", "REQNR", "SLETX", "ZMODT", "ZBUDT", "BUKRS"
													 , "GJAHR", "BELNR" };

	//동호회 신청/조회 키셋
	public static final String clubListKeySet[] = { "MANDT", "APPID", "APSEQ", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO"
												  , "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "INFCD", "INFGR"
												  , "PAYDE", "PAYGN", "PAYMT", "WAERS", "OPERA", "ATEXT", "REQNR", "REQDT", "REQTM", "ASTAT", "DFLAG", "FILEV", "ASTATT", "MBAMT"
												  , "INTXT", "DDTEXT_IN", "DDTEXT_AS", "ZJOIN", "MBAMT_T", "ENDDAT" };
	//동호회 정보 키셋
	public static final String infcdKeySet[] = { "MANDT", "BAREA", "INFCD", "ENDDA", "BEGDA", "PERNR", "APPER", "BANKA", "BANKN", "MBAMT", "WAERS", "CUTOF", "INTXT" };

	//재직증명서 신청 키셋
	public static final String certificateEmpListKeySet[] = { "MANDT", "PERNR", "BEGDA", "SEQNR", "CERTI", "COPIES", "SUBMIT", "PURPS", "ADDRESS", "DETAIL", "DPPNR"
															, "DPPNM", "STATS", "DOC_NO", "WERKS", "ZCERTI", "ZCOPIES", "ZDAMG", "STATST" };
	
	//제증명서 신청 키셋
	public static final String certificateListKeySet[] = { "COPIES","REQTM","ASTAT","BEGDA","SEQNR","UNAME","MANDT","SUBMIT","PRINTBT","OPERA","FILEV","APPID","STATS","STATST","CERTI","DPPNR","PTIME","PDATE","DETAIL","DPPNM","ZCOPIES","ZDAMG","ASTATT","ADDRESS","ZCERTI","DFLAG","AEDTM","INFTY","WERKS","APSEQ","DOC_NO","CPURPS","RECEIVET","PURPS","PERNR","RECEIVE","CPURPST" };

	//근로소득 원천징수영수증 신청 키셋
	public static final String taxWithholdingListKeySet[] = { "MANDT", "PERNR", "BEGDA", "SEQNR", "CERTI", "COPIES", "SUBMIT", "PURPS", "ADDRESS", "DETAIL", "DPPNR"
															, "DPPNM", "STATS", "DOC_NO", "WERKS", "ZCERTI", "ZCOPIES", "ZDAMG", "STATST" };

	//대출내역 조회 키셋
	public static final String loanListKeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX"
												  , "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "DLART", "EXTDL", "DATBW"
												  , "DARBT", "DBTCU", "DKOND", "INDIN", "TILBG", "ANRTE", "ANRCU", "TILBT", "TILCU", "DLEND", "EFFIN", "STFBI", "STFCU"
												  , "IND_REFIN", "ZZRPC", "TIBBT", "ZDLART", "WAERS", "ZDATE", "ZWONG", "ZEJAG", "ZREST", "ZDAY", "ZWONG_S", "ZEJAG_S"
												  , "ZREST_S", "ZTEXT", "OCCAT", "OCCTX", "GRPRD" };

	//학자금 신청/조회 키셋
	public static final String eduExpenseKeySet[] = { "MANDT", "APPID", "APSEQ", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO"
											  		, "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "FEEGN", "FAMNM"
											  		, "SCHTX", "SCHGN", "SCHYR", "TERMS", "ENFEE", "REFEE", "WAERS", "PAYDT", "BELNR", "GJAHR", "REGNO", "REQENFEE", "REQREFEE"
											  		, "SCHOLAR", "EDU_INFO", "OPERA", "ATEXT", "REQNR", "REQDT", "REQTM", "ASTAT", "DFLAG", "FILEV", "ASTATT", "ABWTGT", "ZPAYDT"
											  		, "SUBNM", "PAYYN", "PAYTX", "TOFEE", "ZREGNO", "ZFASAR", "FNMHG", "LNMHG" };

	//교육승인관련 키셋
	public static final String eduApproveKeySet[] = { "MANDT", "APPID", "APSEQ", "OPERA", "PERNR", "SOBJID", "SEQNO", "WDATE", "DESC1", "DESC2", "ATEXT", "REQNR", "REQDT", "REQTM"
													, "ASTAT", "DFLAG", "FILEV", "ASTATT", "INFTY", "CTEXT_ID" };

	//교육승인관련 키셋(EX_DLIST)
	public static final String eduDListKeySet[] = { "OBJID", "SEQNO", "DSTEXT", "ORGTXT", "QGRTXT", "TARGTXT", "POINT", "SOBID", "DTYPE", "OTYPE", "LOCTXT", "CMEDU", "INEDU", "OTEDU"
												  , "ONEDU", "LAEDU", "ABEDU", "PGEDU", "RDEDU", "OJTED", "INSCK", "ESSCK", "MANGE", "OFCER", "SKILL", "EXCTE", "ZFIST", "SECND", "THIRD"
												  , "FORTH", "TLEDR", "PLEDR", "SLEDR", "BLEDR", "SDATE", "EDATE", "IKOST", "IWAER", "ETYPE", "REFND", "ZDESC", "URL", "NHOURSC", "IKOSTC" ,"EX_NEWEDU"};


	//교육결과보고서 키셋
	public static final String reportEduDlistKeySet[] = { "MANDT", "APPID", "SATIS", "PROCS", "DELOP", "QUALI", "TARGE", "EFROM", "EDUTO", "ETIME", "ELOCT", "ECONT", "EFEEL", "EBJID"
														, "DBJID", "REQNR", "REEDU", "ZATOT", "ZAUND", "ZASEL", "ZAUSI", "REEPL", "ZSATTX", "ZPROTX", "ZDELTX", "ZQUATX", "ZECOTX"
														, "ZEFETX", "DOBJID", "STEXT", "LPERIOD", "PERNR", "ENAME", "ORGTX", "JIKWI", "ZPERTXT", "BEGDA", "ENDDA", "ASTAT", "DFLAG"
														, "FILEV", "ASTATT", "REQDT", "ATCH1", "ATCH2", "ATCH3", "ZDESC1", "ZDESC2", "ZHRSU" };

	//개인별 업무분장 키셋
	public static final String personalDivisionListKeySet[] = { "BOX", "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX"
															  , "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "TAS01", "PER01"
															  , "TAS02", "PER02", "TAS03", "PER03", "TAS04", "PER04", "TAS05", "PER05", "TAS06", "PER06", "TAS07", "PER07", "TAS08"
															  , "PER08", "TAS09", "PER09", "TAS10", "PER10", "LEADR", "LEADR2", "APSTS", "TAS01_T", "TAS02_T", "TAS03_T", "TAS04_T"
															  , "TAS05_T", "TAS06_T", "TAS07_T", "TAS08_T", "TAS09_T", "TAS10_T", "WERKS", "NAME1", "LOCAT", "LCTTX", "ENAME"
															  , "LEADR_T", "LEADR2_T", "ORGEH", "ORGTX", "STELL", "STLTX", "TRFGR", "APSTS_T", "REPNR", "CHANGE" };
	//개인별 업무분장 키셋(DP_REUSLT)
	public static final String dpResultListKeySet[] = { "ZICGUN", "ZICGUN_T", "OBJ", "OBJ_T", "TASK", "TASK_T" };

	//개인별 업무분장 팝업
	public static final String jobKeySet[] = { "HREF", "OBJID", "STEXT", "SOBID", "GSVAL", "ZICGUN", "ZICGUN_T" };
	
	//근태마감 키셋
	public static final String deadlineKeySet[] = { "ORGEH", "ORGTX", "TRFGR", "JIKWI", "PERNR", "ENAME", "PERSG", "PTEXT", "PERSK", "PKTXT", "SACHZ", "ZFLAG" };
	
	//근태평가 키셋
	public static final String assessmentsKeySet[] = { "SUBRC", "MESSAGE", "ORGTX", "JIKWI", "PERNR", "ENAME" };
	
	//휴무 개별입력 키셋
	public static final String dayOffListKeySet[] = { "ORGTX", "JIKWI", "PERNR", "ENAME", "BEGDA", "ENDDA", "AWART", "ATEXT", "ABWTG", "STDAZ", "BEGUZ", "ENDUZ", "KALTG"
													, "ALLDF", "ABRTG", "SEQNR", "TA_PERNR1", "TATM1", "TA_PERNR2", "TATM2", "TA_PERNR3", "TATM3", "TA_PERNR4", "TATM4"
													, "TA_PERNR5", "TATM5", "TA_PERNR6", "TATM6", "TA_PERNR7", "TATM7", "TA_PERNR8", "TATM8", "TA_PERNR9", "TATM9", "TA_PERNR10"
													, "TATM10", "TA_PERNR11", "TATM11", "TA_PERNR12", "TATM12", "TA_PERNR13", "TATM13", "TA_PERNR14", "TATM14", "TA_PERNR15"
													, "TATM15", "TA_PERNR16", "TATM16", "TA_PERNR17", "TATM17", "TA_PERNR18", "TATM18", "TA_PERNR19", "TATM19", "TA_PERNR20"
													, "TATM20", "TA_PERNR21", "TATM21", "TA_PERNR22", "TATM22", "TA_PERNR23", "TATM23", "TA_PERNR24", "TATM24", "TA_PERNR25"
													, "TATM25", "TA_PERNR26", "TATM26", "TA_PERNR27", "TATM27", "TA_PERNR28", "TATM28", "TA_DATE1", "TA_DATE2", "TA_DATE3"
													, "TA_DATE4", "TA_DATE5", "TA_DATE6", "TA_DATE7", "TA_DATE8", "TA_DATE9", "TA_DATE10", "TA_DATE11", "TA_DATE12", "TA_DATE13"
													, "TA_DATE14", "TA_DATE15", "TA_DATE16", "TA_DATE17", "TA_DATE18", "TA_DATE19", "TA_DATE20", "TA_DATE21", "TA_DATE22"
													, "TA_DATE23", "TA_DATE24", "TA_DATE25", "TA_DATE26", "TA_DATE27", "TA_DATE28", "TA_INDEX1", "TA_INDEX2", "TA_INDEX3"
													, "TA_INDEX4", "TA_INDEX5", "TA_INDEX6", "TA_INDEX7", "TA_INDEX8", "TA_INDEX9", "TA_INDEX10", "TA_INDEX11", "TA_INDEX12"
													, "TA_INDEX13", "TA_INDEX14", "TA_INDEX15", "TA_INDEX16", "TA_INDEX17", "TA_INDEX18", "TA_INDEX19", "TA_INDEX20", "TA_INDEX21"
													, "TA_INDEX22", "TA_INDEX23", "TA_INDEX24", "TA_INDEX25", "TA_INDEX26", "TA_INDEX27", "TA_INDEX28" };
	
	//근무 개별입력 키셋
	public static final String workListKeySet[] = { "ORGTX", "JIKWI", "PERNR", "ENAME", "BEGDA", "ENDDA", "AWART", "ATEXT", "ABWTG", "STDAZ", "BEGUZ", "ENDUZ", "KALTG"
												  , "ALLDF", "ABRTG", "SEQNR", "TA_PERNR1", "TATM1", "TA_PERNR2", "TATM2", "TA_PERNR3", "TATM3", "TA_PERNR4", "TATM4", "TA_PERNR5"
												  , "TATM5", "TA_PERNR6", "TATM6", "TA_PERNR7", "TATM7", "TA_PERNR8", "TATM8", "TA_PERNR9", "TATM9", "TA_PERNR10", "TATM10"
												  , "TA_PERNR11", "TATM11", "TA_PERNR12", "TATM12", "TA_PERNR13", "TATM13", "TA_PERNR14", "TATM14", "TA_PERNR15", "TATM15"
												  , "TA_PERNR16", "TATM16", "TA_PERNR17", "TATM17", "TA_PERNR18", "TATM18", "TA_PERNR19", "TATM19", "TA_PERNR20", "TATM20"
												  , "TA_PERNR21", "TATM21", "TA_PERNR22", "TATM22", "TA_PERNR23", "TATM23", "TA_PERNR24", "TATM24", "TA_PERNR25", "TATM25"
												  , "TA_PERNR26", "TATM26", "TA_PERNR27", "TATM27", "TA_PERNR28", "TATM28", "TA_DATE1", "TA_DATE2", "TA_DATE3", "TA_DATE4"
												  , "TA_DATE5", "TA_DATE6", "TA_DATE7", "TA_DATE8", "TA_DATE9", "TA_DATE10", "TA_DATE11", "TA_DATE12", "TA_DATE13", "TA_DATE14"
												  , "TA_DATE15", "TA_DATE16", "TA_DATE17", "TA_DATE18", "TA_DATE19", "TA_DATE20", "TA_DATE21", "TA_DATE22", "TA_DATE23"
												  , "TA_DATE24", "TA_DATE25", "TA_DATE26", "TA_DATE27", "TA_DATE28", "TA_INDEX1", "TA_INDEX2", "TA_INDEX3", "TA_INDEX4", "TA_INDEX5"
												  , "TA_INDEX6", "TA_INDEX7", "TA_INDEX8", "TA_INDEX9", "TA_INDEX10", "TA_INDEX11", "TA_INDEX12", "TA_INDEX13", "TA_INDEX14"
												  , "TA_INDEX15", "TA_INDEX16", "TA_INDEX17", "TA_INDEX18", "TA_INDEX19", "TA_INDEX20", "TA_INDEX21", "TA_INDEX22", "TA_INDEX23"
												  , "TA_INDEX24", "TA_INDEX25", "TA_INDEX26", "TA_INDEX27", "TA_INDEX28" };
	
	//경조사 개별입력 키셋
	public static final String familyEventListKeySet[] = { "ORGTX", "JIKWI", "PERNR", "EMNAM", "BEGDA", "BEGDT", "ENDDT", "SEQNR", "CACCD", "CTEXT", "CACRN", "CATXT", "ENAME"
														 , "GARLD", "GTEXT", "CACMT", "WAERS", "VACDT", "BELNR", "TA_PERNR1", "TATM1", "TA_PERNR2", "TATM2", "TA_PERNR3", "TATM3"
														 , "TA_PERNR4", "TATM4", "TA_PERNR5", "TATM5", "TA_PERNR6", "TATM6", "TA_PERNR7", "TATM7", "TA_PERNR8", "TATM8", "TA_PERNR9"
														 , "TATM9", "TA_PERNR10", "TATM10", "TA_PERNR11", "TATM11", "TA_PERNR12", "TATM12", "TA_PERNR13", "TATM13", "TA_PERNR14"
														 , "TATM14", "TA_PERNR15", "TATM15", "TA_PERNR16", "TATM16", "TA_PERNR17", "TATM17", "TA_PERNR18", "TATM18", "TA_PERNR19", "TATM19"
														 , "TA_PERNR20", "TATM20", "TA_PERNR21", "TATM21", "TA_PERNR22", "TATM22", "TA_PERNR23", "TATM23", "TA_PERNR24", "TATM24", "TA_PERNR25"
														 , "TATM25", "TA_PERNR26", "TATM26", "TA_PERNR27", "TATM27", "TA_PERNR28", "TATM28", "TA_DATE1", "TA_DATE2", "TA_DATE3", "TA_DATE4"
														 , "TA_DATE5", "TA_DATE6", "TA_DATE7", "TA_DATE8", "TA_DATE9", "TA_DATE10", "TA_DATE11", "TA_DATE12", "TA_DATE13", "TA_DATE14", "TA_DATE15"
														 , "TA_DATE16", "TA_DATE17", "TA_DATE18", "TA_DATE19", "TA_DATE20", "TA_DATE21", "TA_DATE22", "TA_DATE23", "TA_DATE24", "TA_DATE25"
														 , "TA_DATE26", "TA_DATE27", "TA_DATE28", "TA_INDEX1", "TA_INDEX2", "TA_INDEX3", "TA_INDEX4", "TA_INDEX5", "TA_INDEX6", "TA_INDEX7"
														 , "TA_INDEX8", "TA_INDEX9", "TA_INDEX10", "TA_INDEX11", "TA_INDEX12", "TA_INDEX13", "TA_INDEX14", "TA_INDEX15", "TA_INDEX16", "TA_INDEX17"
														 , "TA_INDEX18", "TA_INDEX19", "TA_INDEX20", "TA_INDEX21", "TA_INDEX22", "TA_INDEX23", "TA_INDEX24", "TA_INDEX25", "TA_INDEX26", "TA_INDEX27"
														 , "TA_INDEX28" };
	
	//대체근무 개별입력 키셋
	public static final String altWorkIndividualListKeySet[] = { "ORGTX", "JIKWI", "PERNR", "ENAME", "BEGDA", "ENDDA", "VTART", "VTEXT", "ZEITY", "TTEXT", "MOFID", "MOSID"
															   , "SCHKZ", "RTEXT", "MOTPR", "TPROG", "BEGUZ", "ENDUZ", "STDAZ", "SEQNR" };
	
	//대체근무 일괄입력 키셋
	public static final String altWorkBatchListKeySet[] = { "SUBRC", "MESSAGE", "ORGTX", "JIKWI", "PERNR", "ENAME", "WTEXT", "TTEXT", "PERIOD", "BEGDA", "ENDDA", "VTART"
														  , "VTEXT", "SCHKZ", "RTEXT", "ZEITY", "MOFID", "MOSID", "SEQNR" };
	
	//연장근로 개별입력 키셋
	public static final String overtimeIndividualListKeySet[] = { "ORGTX", "JIKWI", "PERNR", "ENAME", "BEGDA", "ENDDA", "VERSL", "VTEXT", "SCHKZ", "RTEXT", "TPROG", "TTEXT"
																, "ATEXT", "BEGUZ", "ENDUZ", "STDAZ", "VTKEN", "PBEG1", "PEND1", "PBEG2", "PEND2", "PBEG3", "PEND3", "PBEG4"
																, "PEND4", "PREAS", "PRETX", "PRTXT", "SEQNR" };
	
	//연장근로 일괄입력 키셋
	public static final String overtimeBatchListKeySet[] = { "SUBRC", "MESSAGE", "ORGTX", "JIKWI", "PERNR", "ENAME", "BEGDA", "ENDDA", "VERSL", "VTEXT", "SCHKZ", "RTEXT", "TPROG"
														   , "TTEXT", "ATEXT", "BEGUZ", "ENDUZ", "STDAZ", "VTKEN", "PBEG1", "PEND1", "PBEG2", "PEND2", "PBEG3", "PEND3", "PBEG4"
														   , "PEND4", "PREAS", "PRETX", "PRTXT", "SEQNR" };
	
	
	//노조직책자 현황 조회 키셋
	public static final String laborPositionListKeySet[] = { "MANDT", "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX"
															, "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "LABGD", "LABSN", "LABNO"
															, "STEXT", "JIKWI", "ENAME", "GBDAT", "ENTDAT", "LABTX", "PLSTX", "LABNUM", "PBEGDA", "PENDDA", "SCHKZ", "RTEXT", "ZEITY"
															, "PERSG", "PERSK", "WERKS", "BTRTL", "MOSID", "MOFID" };
	
	//노조 면담 리스트 조회 키셋
	public static final String laborInterviewListKeySet[] = { "MANDT", "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX"
															, "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "ITVNR", "ITVDT", "ITVLO"
															, "RG_OPEN", "AL_OPEN", "WRITE_DT", "ITVEN", "ORGEH", "ORGTX" };
	
	//노조 면담 리스트 EX_USERINFO 키셋
	public static final String laborInterviewUserInfoKeySet[] = { "PERNR", "USRID", "BEGDA", "ENDDA", "ENAME", "LNMHG", "FNMHG", "LNMCH", "FNMCH", "NACHN", "VORNA", "REGNO", "GBDAT"
																, "BIRTH", "BUKRS", "BUTXT", "WERKS", "NAME1", "BTRTL", "BTEXT", "PERSG", "PTEXT", "PERSK", "PKTXT", "A1DAT", "A2DAT"
																, "A3DAT", "A4DAT", "CONYR", "ABKRS", "CPIND", "GSBER", "KOSTL", "PLANS", "PLSTX", "STELL", "STLTX", "ORGEH", "ORGTX"
																, "ZZJIK", "COTXT", "TRFGR", "JIKWI", "OBJID", "STEXT", "DBOSS", "DBSNM", "ORGLV", "LOCAT", "LCTTX", "ENTRY", "REFNO"
																, "PTPNR", "CMAIL", "PMAIL", "TRFST", "ZZDLP" };
	
	//노조 면담일지 저장 키셋
	public static final String laborInterviewProcessKeySet[] = { "MANDT", "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX"
															   , "ITBLD", "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "ITVNR", "ITVDT", "ITVLO", "RG_OPEN", "AL_OPEN"
															   , "WRITE_DT", "ITVEN", "ORGEH", "ORGTX", "OBJ01", "OBJ02", "OBJ03", "OBJ04", "OBJ05", "ENAME_P", "ORGEH_P", "ORGTX_P" };
	
	
	//5급평가관리 상세조회 헤더 키셋 
	public static final String raterEvaluationHeaderListKeySet[] = { "MANDT", "ZYEAR", "PERNR", "ORGEH", "PPERNR", "REVIEWER", "BS", "DOCUID",
															 "AEDTM", "UNAME", "STELL", "STLTX", "ORGTX", "AP_STATUS", "AP_STATUS_SUB", "STATUST", "BEGDA",
															 "ENDDA", "ENAME", "TRFGR", "POINT", "GRADE" };
															
	//5급평가관리 상세조회 서브 키셋 
	public static final String raterEvaluationSubListKeySet[] = { "COUNTER", "PLAN_VERSION", "APPRAISAL_ID", "PART_AP_ID", "APPRAISAL_NAME", "APPRAISAL_TYPE_TEXT", "APPRAISER_ID", "APPRAISER_TYPE", "APPRAISER_TYPE_TEXT", 
																"APPRAISER_NAME", "APPRAISER_SHORT", "APPRAISEE_ID", "APPRAISEE_TYPE", "APPRAISEE_TYPE_TEXT", "APPRAISEE_NAME", "APPRAISEE_SHORT", "AP_STATUS_NAME", 
																"AP_STATUS_SUB_NAME", "AP_START_DATE", "AP_END_DATE", "OBJ_DATE_SET", "REVIEW_DATE_SET", "AP_DATE_SET", "CHANGE_DATE", "CHANGE_TIME", "CHANGE_USER", "TEMPLATE_ID", "PART_ROLE_ID"  };
	
	
	//연말정산 인적공제 키셋
	public static final String perFuncKeySet[] = { "MANDT", "ZYEAR", "PERNR", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD"
												 , "PREAS", "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "FAMSA", "FGBDT", "FGBLD", "FANAT", "FASEX", "FAVOR", "FANAM", "FGBOT"
												 , "FGDEP", "ERBNR", "FGBNA", "FNAC2", "FCNAM", "FKNZN", "FINIT", "FVRSW", "FVRS2", "FNMZU", "AHVNR", "KDSVH", "KDBSL", "KDUTB", "KDGBR", "KDART"
												 , "KDZUG", "KDZUL", "KDVBE", "ERMNR", "AUSVL", "AUSVG", "FASDT", "FASAR", "FASIN", "EGAGA", "FANA2", "FANA3", "BETRG", "TITEL", "EMRGN", "FNMHG"
												 , "LNMHG", "REGNO", "FAJOB", "DPTID", "LIVID", "HNDID", "CHDID", "HELID", "FAMID", "DPTYP", "INSID", "MEDID", "EDUID", "CREID", "BALID", "ADCID"
												 , "ADPDT", "OAPID", "FSTID", "NTXFC", "FRYEA", "RSNOT", "FLDEG", "HNDEE", "WOMEE", "REFID", "RECFR", "SUBFR", "PRODU", "OPTIO", "FRNMN", "YNGEE"
												 , "EMBEG", "EMEND", "SIGPR", "OLDID", "DELIV", "HNDCD", "HNDCD_T", "ZMODIFY", "UPLOAD", "ENAME", "WERKS", "BTRTL", "STCD2", "NAME", "ZREGNO"
												 , "ZADDRESS_LINE", "ZBEG01", "OFPHN", "GBDAT", "ZNAME", "DPTYP_TEXT", "ZADDRESS", "ZDELIV", "ZSIX", "ZSEVENTY", "ZHNDEE", "ZCHILD", "ZINSID"
												 , "ZMEDID", "ZEDUID", "ZCREID", "ZDONID", "ZCONID", "ZHNDID", "ZZHNDEE", "ZDPTID", "ZZSEVENTY", "ZWOMEE", "ZZSIX", "ZZDELIV", "ZZINSID", "ZZMEDID"
												 , "ZZEDUID", "ZZCREID", "ZZCONID", "ZENDDA", "ZHNDCD", "ZSIGPR", "ZCELLP", "ZORGTX", "ZORGEH", "ZEMAIL", "KDBSL" };
	
	//연말정산 이전 근무지 키셋
	public static final String last2KeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1", "FLAG2"
											   , "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "COM01", "BUS01", "SAL01", "BON01", "INT01", "RET01", "OUT01", "OVR01", "OTH01", "MED01", "NPM01", "EIM01", "FLD01"
											   , "FRI01", "FRE01", "COM02", "BUS02", "SAL02", "BON02", "INT02", "RET02", "OUT02", "OVR02", "OTH02", "MED02", "NPM02", "EIM02", "FLD02", "FRI02", "FRE02", "COM03", "BUS03"
											   , "SAL03", "BON03", "INT03", "RET03", "OUT03", "OVR03", "OTH03", "MED03", "NPM03", "EIM03", "FLD03", "FRI03", "FRE03", "COM04", "BUS04", "SAL04", "BON04", "INT04", "RET04"
											   , "OUT04", "OVR04", "OTH04", "MED04", "NPM04", "EIM04", "FLD04", "FRI04", "FRE04", "COM05", "BUS05", "SAL05", "BON05", "INT05", "RET05", "OUT05", "OVR05", "OTH05", "MED05"
											   , "NPM05", "EIM05", "FLD05", "FRI05", "FRE05", "FOTIS", "SUBIS", "FMDOH", "FMDED", "SUBME", "FCEEE", "FCECU", "SUBCE", "FLGDO", "FDSDO", "FPRDO", "SUBRD", "SAVNM", "SAVAM"
											   , "ACQDT", "REPAY", "APPDT", "CRDCD", "ABPLA", "ABJOB", "ABDUR", "ABPAY", "TAXCT", "KORCR", "GETDT", "PUTDT", "FTCHI", "FTCES", "FPERP", "FPERN", "FINVT", "FINVS", "ABN01"
											   , "SPT01", "ABN02", "SPT02", "ABN03", "SPT03", "ABN04", "SPT04", "ABN05", "SPT05", "FTCLS", "FOHIS", "FHADI", "FTCLP", "COMST", "SPCDO", "FCEWF", "FCEHD", "DRTCD", "HSINS"
											   , "HSSAV", "EEHSS", "LHSSV", "DON30", "POLDN", "CONMA", "CONRE", "CONFU", "CRDDP", "GIROE", "CASHE", "FORCT", "FORCK", "INTTL", "DUPEX", "RETPE", "TINVS", "PINVS", "OTHPI", "ONP01"
											   , "ONP02", "ONP03", "ONP04", "ONP05", "RSN01", "RSN02", "RSN03", "RSN04", "RSN05", "STK01", "STK02", "STK03", "STK04", "STK05", "FOR01", "CLD01", "FOR02", "CLD02", "FOR03", "CLD03"
											   , "FOR04", "CLD04", "FOR05", "CLD05", "REP01", "REP02", "REP03", "REP04", "REP05", "TPA01", "TPA02", "TPA03", "TPA04", "TPA05", "DESDO", "RELDO", "SMBFI", "LTSF1", "LTSF2", "LTSF3"
											   , "NINVS", "INDIF", "INSLN", "SFENP", "SFEHI", "MRNTD", "INDPL", "PDCID", "HSHLD", "VINVS", "INTFN", "INTOT", "INVST", "IRLWL", "EXTAX", "IYCIT", "BIZNO", "COMNM", "PABEG", "PAEND"
											   , "SALM", "BONM", "INTM", "RETM", "OUTM", "OVRM", "OTHM", "MEDM", "NPMM", "EIMM", "FLDM", "FRIM", "FREM", "ABNM", "SPTM", "NOW_SUM", "NOW_4ME", "NOW_4UE", "ZSUM", "ZSNPM", "ZSMED"
											   , "ZSEIM", "SALMT", "BONMT", "INTMT", "RETMT", "OUTMT", "OVRMT", "OTHMT", "MEDMT", "NPMMT", "EIMMT", "FLDMT", "FRIMT", "FREMT", "ABNMT", "SPTMT", "EXBEG", "EXEND", "PAB01", "PAB02"
											   , "PAB03", "PAB04", "PAB05", "PAE01", "PAE02", "PAE03", "PAE04", "PAE05", "EXB01", "EXB02", "EXB03", "EXB04", "EXB05", "EXE01", "EXE02", "EXE03", "EXE04", "EXE05" };
	
	//연말정산 세금 키셋
	public static final String taxKeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1"
											 , "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "COM01", "BUS01", "SAL01", "BON01", "INT01", "RET01", "OUT01", "OVR01", "OTH01", "MED01", "NPM01"
											 , "EIM01", "FLD01", "FRI01", "FRE01", "COM02", "BUS02", "SAL02", "BON02", "INT02", "RET02", "OUT02", "OVR02", "OTH02", "MED02", "NPM02", "EIM02", "FLD02"
											 , "FRI02", "FRE02", "COM03", "BUS03", "SAL03", "BON03", "INT03", "RET03", "OUT03", "OVR03", "OTH03", "MED03", "NPM03", "EIM03", "FLD03", "FRI03", "FRE03"
											 , "COM04", "BUS04", "SAL04", "BON04", "INT04", "RET04", "OUT04", "OVR04", "OTH04", "MED04", "NPM04", "EIM04", "FLD04", "FRI04", "FRE04", "COM05", "BUS05"
											 , "SAL05", "BON05", "INT05", "RET05", "OUT05", "OVR05", "OTH05", "MED05", "NPM05", "EIM05", "FLD05", "FRI05", "FRE05", "FOTIS", "SUBIS", "FMDOH", "FMDED"
											 , "SUBME", "FCEEE", "FCECU", "SUBCE", "FLGDO", "FDSDO", "FPRDO", "SUBRD", "SAVNM", "SAVAM", "ACQDT", "REPAY", "APPDT", "CRDCD", "ABPLA", "ABJOB", "ABDUR"
											 , "ABPAY", "TAXCT", "KORCR", "GETDT", "PUTDT", "FTCHI", "FTCES", "FPERP", "FPERN", "FINVT", "FINVS", "ABN01", "SPT01", "ABN02", "SPT02", "ABN03", "SPT03"
											 , "ABN04", "SPT04", "ABN05", "SPT05", "FTCLS", "FOHIS", "FHADI", "FTCLP", "COMST", "SPCDO", "FCEWF", "FCEHD", "DRTCD", "HSINS", "HSSAV", "EEHSS", "LHSSV"
											 , "DON30", "POLDN", "CONMA", "CONRE", "CONFU", "CRDDP", "GIROE", "CASHE", "FORCT", "FORCK", "INTTL", "DUPEX", "RETPE", "TINVS", "PINVS", "OTHPI", "ONP01"
											 , "ONP02", "ONP03", "ONP04", "ONP05", "RSN01", "RSN02", "RSN03", "RSN04", "RSN05", "STK01", "STK02", "STK03", "STK04", "STK05", "FOR01", "CLD01", "FOR02"
											 , "CLD02", "FOR03", "CLD03", "FOR04", "CLD04", "FOR05", "CLD05", "REP01", "REP02", "REP03", "REP04", "REP05", "TPA01", "TPA02", "TPA03", "TPA04", "TPA05"
											 , "DESDO", "RELDO", "SMBFI", "LTSF1", "LTSF2", "LTSF3", "NINVS", "INDIF", "INSLN", "SFENP", "SFEHI", "MRNTD", "INDPL", "PDCID", "HSHLD", "VINVS", "INTFN"
											 , "INTOT", "INVST", "IRLWL", "EXTAX", "IYCIT", "ZTHIRTY", "ZMTEN", "ZSUM31", "ZSUM32", "ZSUM33", "ZSUM41", "ZSUM42", "ZSUM51", "ZRET06", "ZRET07", "ZSUM52"
											 , "ZSUM53", "ZSUMFCE", "ZSUMELE", "ZSUMSCH", "ZSUMUNI", "WAERS", "ZABDUR", "NAM01", "ELV01", "EFE01", "DFC01", "NAM02", "ELV02", "EFE02", "DFC02", "NAM03"
											 , "ELV03", "EFE03", "DFC03", "NAM04", "ELV04", "EFE04", "DFC04", "NAM05", "ELV05", "EFE05", "DFC05", "NAM06", "ELV06", "EFE06", "DFC06", "NAM07", "ELV07", "EFE07"
											 , "DFC07", "NAM08", "ELV08", "EFE08", "DFC08", "LAND1", "SAL01T", "BON01T", "INT01T", "RET01T", "OUT01T", "OVR01T", "OTH01T", "MED01T", "NPM01T", "EIM01T", "FLD01T"
											 , "FRI01T", "FRE01T", "SAL02T", "BON02T", "INT02T", "RET02T", "OUT02T", "OVR02T", "OTH02T", "MED02T", "NPM02T", "EIM02T", "FLD02T", "FRI02T", "FRE02T", "SAL03T"
											 , "BON03T", "INT03T", "RET03T", "OUT03T", "OVR03T", "OTH03T", "MED03T", "NPM03T", "EIM03T", "FLD03T", "FRI03T", "FRE03T", "SAL04T", "BON04T", "INT04T", "RET04T"
											 , "OUT04T", "OVR04T", "OTH04T", "MED04T", "NPM04T", "EIM04T", "FLD04T", "FRI04T", "FRE04T", "SAL05T", "BON05T", "INT05T", "RET05T", "OUT05T", "OVR05T", "OTH05T"
											 , "MED05T", "NPM05T", "EIM05T", "FLD05T", "FRI05T", "FRE05T", "FOTIST", "FMDOHT", "FMDEDT", "FCEEET", "FCECUT", "FLGDOT", "FDSDOT", "FPRDOT", "SAVAMT", "REPAYT"
											 , "INDPLT", "CRDCDT", "ABPAYT", "KORCRT", "FTCHIT", "FTCEST", "FPERPT", "ZRET06T", "ZRET07T", "FPERNT", "FINVTT", "FINVST", "NINVST", "INVSTT", "PINVST", "VINVST"
											 , "TINVST", "INTFNT", "INTOTT", "ABN01T", "SPT01T", "ABN02T", "SPT02T", "ABN03T", "SPT03T", "ABN04T", "SPT04T", "ABN05T", "SPT05T", "FTCLST", "FOHIST", "FHADIT", "FTCLPT"
											 , "COMSTT", "INDIFT", "SMBFIT", "SPCDOT", "FCEWFT", "FCEHDT", "DRTCDT", "HSINST", "HSSAVT", "EEHSST", "LHSSVT", "DON30T", "POLDNT", "CRDDPT", "GIROET", "CASHET", "FORCTT"
											 , "INTTLT", "INSLNT", "MRNTDT", "DUPEXT", "RETPET", "NOW_SUM", "NOW_4ME", "NOW_4UE", "ZSUM", "ZSMED", "ZSEIM", "ZSNPM", "DED_PRE_PER", "DED_STU_PER", "DED_UNI_PER"
											 , "DED_HND_PER", "MECDA_SUM", "MEAMT_SUM", "CHK01", "CHK02", "CHK03", "CHK04", "CHK05", "CHK06", "CHK07", "CHK08", "LTSF1T", "LTSF2T", "LTSF3T", "ZCOM", "ZBUS", "ZSAL"
											 , "ZINT", "ZSUM_RET", "ZSUM_HOUSE", "POLDN_UP", "POLDN_UNDER", "MTHSS", "MTHSST", "PAB01", "PAB02", "PAB03", "PAB04", "PAB05", "PAE01", "PAE02", "PAE03", "PAE04", "PAE05"
											 , "EXB01", "EXB02", "EXB03", "EXB04", "EXB05", "EXE01", "EXE02", "EXE03", "EXE04", "EXE05", "RETPE01", "RETPE02", "CCRDCD", "PCRDCD", "LTAIS", "EXPOLDN", "EXFLGDO"
											 , "EXDON30", "EXDESDO", "EXRELDO", "EXPRD", "HOUTY", "HOSTX", "ZSUM31_2", "SELFCREP", "SELFCREN", "SELFCREPP", "SELFTAXP", "SELFTAXN", "SELFTAXPP", "SELFTAXM", "EXPOLDN2"
											 , "EXFLGDO2", "EXDON302", "EXDESDO2", "EXRELDO2", "INT15A", "INT15O", "INT15E", "INT10O" };
	
	//연말정산 이자상환액 키셋
	public static final String repayKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN"
											   , "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF" , "ET_LNPRD"};
	
	//연말정산 신용카드 키셋
	public static final String creditKeySet[] = { "KDSVH", "DNAME", "REGNOT", "GUBUN", "MANDT", "ZYEAR", "PERNR", "SUBTY", "OBJPS", "REGNO", "EXPTY", "SEQNR", "MENUM", "MENAM", "MEPCD", "MECDT", "MECDA", "MECNT", "MEAMT"
												, "MECUR", "DOCOD", "DONUM", "DONAM", "DOREC", "DOCNT", "DOAMT", "DOCUR", "OTNAM", "OTOAM", "OTMET", "OTMEE", "OTCUR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY"
												, "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN", "ET_FINCO", "ET_RCBEG", "ET_RCEND", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "OTHAN", "OTELV", "OLDID", "HNDID", "AUCHK", "FLNTS"
												, "AEDTM", "AETIM", "UNAME", "GLCHK", "SUCHK", "TRDMK", "CCTRA", "EXPRD", "HOUTY", "HOSTX", "SURPG", "ET_FIXRT", "ET_NODEF" };
	
	//연말정산 개인연금저축 키셋
	public static final String annuityKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN"
												 , "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF" };
	
	//연말정산 주택마련저축 키셋
	public static final String houseKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN"
											   , "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF" };
	
	//연말정산 납입금 키셋
	public static final String paymentKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN"
												 , "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF" };
	//연말정산 보험료 키셋
	public static final String premiumKeySet[] = { "KDSVH", "DNAME", "REGNOT", "GUBUN", "MANDT", "ZYEAR", "PERNR", "SUBTY", "OBJPS", "REGNO", "EXPTY", "SEQNR", "MENUM", "MENAM"
												 , "MEPCD", "MECDT", "MECDA", "MECNT", "MEAMT", "MECUR", "DOCOD", "DONUM", "DONAM", "DOREC", "DOCNT", "DOAMT", "DOCUR", "OTNAM"
												 , "OTOAM", "OTMET", "OTMEE", "OTCUR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN"
												 , "ET_PREBN", "ET_FINCO", "ET_RCBEG", "ET_RCEND", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "OTHAN", "OTELV", "OLDID", "HNDID", "AUCHK"
												 , "FLNTS", "AEDTM", "AETIM", "UNAME", "GLCHK", "SUCHK", "TRDMK", "CCTRA", "EXPRD", "HOUTY", "HOSTX", "SURPG", "ET_FIXRT", "ET_NODEF" };
	
	//연말정산 의료비 키셋
	public static final String medicalKeySet[] = { "KDSVH", "DNAME", "REGNOT", "MANDT", "ZYEAR", "PERNR", "SUBTY", "OBJPS", "REGNO", "EXPTY", "SEQNR", "MENUM", "MENAM", "MEPCD", "MECDT"
												 , "MECDA", "MECNT", "MEAMT", "MECUR", "DOCOD", "DONUM", "DONAM", "DOREC", "DOCNT", "DOAMT", "DOCUR", "OTNAM", "OTOAM", "OTMET", "OTMEE"
												 , "OTCUR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN", "ET_FINCO", "ET_RCBEG"
												 , "ET_RCEND", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "OTHAN", "OTELV", "OLDID", "HNDID", "AUCHK", "FLNTS", "AEDTM", "AETIM", "UNAME", "GLCHK"
												 , "SUCHK", "TRDMK", "CCTRA", "EXPRD", "HOUTY", "HOSTX", "SURPG", "ET_FIXRT", "ET_NODEF", "MECDTT" };
	
	//연말정산 교육비 키셋
	public static final String educationKeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS"
												   , "FLAG1", "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "NAM01", "ELV01", "EFE01", "DFC01", "NAM02", "ELV02", "EFE02", "DFC02", "NAM03"
												   , "ELV03", "EFE03", "DFC03", "NAM04", "ELV04", "EFE04", "DFC04", "NAM05", "ELV05", "EFE05", "DFC05", "NAM06", "ELV06", "EFE06", "DFC06", "NAM07"
												   , "ELV07", "EFE07", "DFC07", "NAM08", "ELV08", "EFE08", "DFC08", "ZYEAR", "KDSVH", "EXPTY", "DNAME", "REGNO", "EDULV", "DPEDF", "DPEFC", "REGNOT"
												   , "LNMHG", "FNMHG", "DPEDFT", "HNDID", "OTOAM", "OTNAM", "OTCUR", "OTELV", "AUCHK", "EXCHK", "SUCHK" };

	//연말정산 기부금 키셋
	public static final String donationKeySet[] = { "PERNR", "INFTY", "SUBTY", "OBJPS", "SPRPS", "ENDDA", "BEGDA", "SEQNR", "AEDTM", "UNAME", "HISTO", "ITXEX", "REFEX", "ORDEX", "ITBLD", "PREAS", "FLAG1"
												  , "FLAG2", "FLAG3", "FLAG4", "RESE1", "RESE2", "GRPVL", "DOCOD", "DONUM", "DONAM", "DOCNT", "DOAMT", "DOCUR", "FLNTS", "RENUM", "DEPTY", "DEPOI", "CRVIN"
												  , "CRVYR", "DEDPR", "DEDCU", "DOCODT", "DOCNTT", "REGNO", "EXPTY", "ZYEAR", "AUCHK", "DOREC", "KDSVH", "DNAME", "REGNOT", "DEAMT" };
	
	//연말정산 연금계좌 키셋
	public static final String pensionKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN"
												 , "ET_PREBN", "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF" };
	
	//연말정산 월세 키셋
	public static final String rentKeySet[] = { "ZYEAR", "PERNR", "SUBTY", "REGNO", "OBJPS", "EXPTY", "SEQNR", "ETDPT", "ETOBJ", "ETNAM", "ETOAM", "ETCUR", "ET_PNSTY", "ET_INSNM", "ET_ACCNO", "ET_PREIN", "ET_PREBN"
											  , "ET_FINCO", "ET_LDNAM", "ET_LDREG", "ET_ADDRE", "ET_RCBEG", "ET_RCEND", "SCHK", "HOUTY", "HOSTX", "ET_FIXRT", "ET_NODEF", "ET_INRAT" };
	

	//5급평가관리 상세조회 헤더 키셋1 
	public static final String raterEvaluationImListKeySet[] = { "MANDT", "ZYEAR", "PERNR", "ORGEH", "PPERNR", "REVIEWER", "BS", "DOCUID",
															 "AEDTM", "UNAME", "STELL", "STLTX", "ORGTX", "AP_STATUS", "AP_STATUS_SUB", "STATUST", "BEGDA",
															 "ENDDA", "ENAME", "TRFGR", "POINT", "GRADE" };
	//5급평가관리 상세조회 헤더 키셋2 
	public static final String raterEvaluationImdocprocessingKeySet[] = { "MODE","UI_MODE","DOCUMENT_MODE","BUTTON_MODE","ADMINISTRATOR","ADD_ON_APPLICATION","COMPLETE_DOCUMENT","TEMPLATE_MODE",
																"ONLY_ONE_ALLOWED","NO_SELFAPPRAISAL","NO_OBJECTIVE_PHASE","NO_REVIEW_PHASE","NO_APPRAISAL_PHASE","NO_DATE_CHECK","WORKFLOW_ACTIVE",
																"ATTACHMENT_ALLOW","MULTI_APPRAISERS","MULTI_APPRAISEES","ACTIONLOG_ACTIVE","ARCHIVED_PROCESS","NO_APPERS_AUTH","DYNAMIC_TEMPLATE",
																"DOC_ENQUEUE","NUMBER_OF_PART_APPERS","REAL_360","DYNAMIC_EVENT","ATTACHMENT_MODE","DRAFT_NOTES_ALLOWED","OFFLINE_DEQUEUE","SKIP_IN_PREPARATION" };
	
	//5급평가관리 상세조회 헤더 키셋3
	public static final String raterEvaluationImheadertextsKeySet[] = { "APPRAISAL_TXT","APPRAISAL_NAME","STATUS_TXT","APPER_TYPE_TXT","APPER_TYPE_TXT_M","APPEE_TYPE_TXT","APPEE_TYPE_TXT_M","P_APPER_TYPE_TXT",
																"P_APPER_TYPE_M","OTHER_TYPE_TXT","VAL_PERIOD_TXT","EXE_PERIOD_TXT","OBJ_DATE_TXT","REV_DATE_TXT","APP_DATE_TXT" };
	//5급평가관리 상세조회 헤더 키셋4
	public static final String raterEvaluationImheaderdisplayKeySet[] = { "NAME","STATUS","STATUS_SUB","APPRAISER","MULTI_APPRAISER","APPRAISEE","MULTI_APPRAISEE","PART_APPRAISERS","OTHERS","VALIDITY_PERIOD",
																"EXECUTION_PERIOD","OBJECTIVE_SETTING_DATE","REVIEW_DATE","APPRAISAL_DATE","ADD_HEADER_DATA" };
							
	//5급평가관리 상세조회 헤더 키셋5 
	public static final String raterEvaluationAppraisalIdKeySet[] = {"APPRAISAL_ID","PART_AP_ID"};
	
	//5급평가관리 상세조회 헤더 키셋6 
	public static final String raterEvaluationHeaderStatusKeySet[] = {"AP_STATUS","AP_STATUS_NAME","AP_STATUS_SUB","AP_STATUS_SUB_NAME"};
	
	//5급평가관리 상세조회 헤더 키셋7 
	public static final String raterEvaluationHeaderDateKeySet[] = {"AP_START_DATE","AP_END_DATE","AP_DATE_EARLIEST","AP_DATE_LATEST","OBJ_DATE_SET","REVIEW_DATE_SET","AP_DATE_SET"};
	
	//5급평가관리 상세조회 테이블 키셋1 
	public static final String raterEvaluationItdocumentKeySet[] = { "COUNTER", "PLAN_VERSION", "APPRAISAL_ID", "PART_AP_ID", "APPRAISAL_NAME", "APPRAISAL_TYPE_TEXT", "APPRAISER_ID", "APPRAISER_TYPE", "APPRAISER_TYPE_TEXT", 
																"APPRAISER_NAME", "APPRAISER_SHORT", "APPRAISEE_ID", "APPRAISEE_TYPE", "APPRAISEE_TYPE_TEXT", "APPRAISEE_NAME", "APPRAISEE_SHORT", "AP_STATUS_NAME", 
																"AP_STATUS_SUB_NAME", "AP_START_DATE", "AP_END_DATE", "OBJ_DATE_SET", "REVIEW_DATE_SET", "AP_DATE_SET", "CHANGE_DATE", "CHANGE_TIME", "CHANGE_USER", "TEMPLATE_ID", "PART_ROLE_ID"  };
	//5급평가관리 상세조회 테이블 키셋2 
	public static final String raterEvaluationItbodyelementsKeySet[] = {"ROW_IID","PARENT","CHILD","BROTHER","AP_LEVEL","ROW_SORT","ELEMENT_TYPE","ELEMENT_ID","FOREIGN_TYPE","FOREIGN_ID","NUMBERING","NAME",
																"FREE_INPUT","LAYOUT_TEXT","LAYOUT_VALUES","LAYOUT_NOTE_LINE","LAYOUT_MAX_CHAR","EXIT_ADD_FIX","EXIT_ADD_FREE","EXIT_DELETE","USED_COLUMNS","AVAILABILITY" };
	//5급평가관리 상세조회 테이블 키셋3 
	public static final String raterEvaluationItbodycellsKeySet[] = {"ROW_IID","COLUMN_IID","CELL_VALUE_AVAILABILITY","VALUE_TEXT_AVAILABLE","CELL_NOTE_AVAILABILITY","CELL_NOTE_LENGTH","CELL_INPUT_NOTE_5022",
																"VALUE_TXT","VALUE_NUM","VALUE_NNV","NO_VALUE","VALUE_TEXT","CELL_VALUE_CLASS","CELL_VALUE_TYPE","CELL_VALUE_EXIT","VAL_DET_OCCUR","CELL_EVENT_EXIT","DYN_EVT_OCCUR" };
	//5급평가관리 상세조회 테이블 키셋4 
	public static final String raterEvaluationItheaderpartappraiserKeySet[] = {"PLAN_VERSION","APPRAISAL_ID","PART_AP_ID","TYPE","ID","NAME","SHORT","ROLE_ID","PART_WEIGHTING","PART_SEQUENCE","AP_DATE_EARLIEST",
																"AP_DATE_LATEST","PART_AP_DATE_SET","AP_COMPLETED","MANDATORY" };
	
	//5급평가관리 상세조회 테이블 키셋5 
	public static final String raterEvaluationCellNotesKeySet[] = {"ROW_IID","COLUMN_IID","TABSEQNR","TDFORMAT","TDLINE","LINE_STATUS"};
	
	//5급평가관리 상세조회 테이블 키셋6 
	public static final String raterEvaluationHeaderAppraiserKeySet[] = {"PLAN_VERSION","TYPE","ID","NAME","SHORT"};
	
	//5급평가관리 상세조회 테이블 키셋7 
	public static final String raterEvaluationHeaderOthersKeySet[] = {"PLAN_VERSION","TYPE","ID","NAME","SHORT","ROLE_ID"};
	
	//5급평가관리 상세조회 테이블 키셋8 
	public static final String raterEvaluationBodyColumnsKeySet[] = {"COLUMN_IID","COLUMN_ID","PART_AP_ID","COLUMN_NAME","AVAILABILITY","OWNER"};

	
}