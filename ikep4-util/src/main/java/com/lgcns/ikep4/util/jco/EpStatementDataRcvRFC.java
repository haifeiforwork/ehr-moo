package com.lgcns.ikep4.util.jco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ibm.icu.text.DecimalFormat;
import com.lgcns.ikep4.util.lang.DateUtil;
import com.lgcns.ikep4.util.mail.MailConstant;
import com.lgcns.ikep4.util.mail.model.Mail;
import com.lgcns.ikep4.util.mail.service.SendService;
import com.lgcns.ikep4.util.model.StatementDetail;
import com.lgcns.ikep4.util.user.member.model.User;
import com.lgcns.ikep4.util.user.member.service.UsersService;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;

/**
 * @author 최재영   
 * @version 1.0, 2012/09/05
 */
@Service("epStatementRcv")
@Transactional
public class EpStatementDataRcvRFC  {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	@Autowired
	private SendService sendService;
	
	@Autowired
	private UsersService userService;
	

	/**
	 * 승인 요청 내역 조회
	 */
    public List<StatementDetail> EpStatementDataRcvRFC(StatementDetail statement, HttpServletRequest request )  {
        
    	Connection connect = null;
    	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
    					
		try {
			
		connect = new Connection(request);	
		String tmpDate = "";
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_LIST");
		function.getImportParameterList().setValue("I_P100", statement.getIp100());
		function.getImportParameterList().setValue("I_S100", statement.getIs100());
		function.getImportParameterList().setValue("I_M100", statement.getIm100());
		function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
		function.getImportParameterList().setValue("I_REQ_T", statement.getIreqt());
		function.getImportParameterList().setValue("I_REQ_N", statement.getIreqn());
		function.getImportParameterList().setValue("I_FNAME", statement.getIfname());
		function.getImportParameterList().setValue("I_STATE", statement.getIstate());
		connect.executeSAP(function);
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("IT_CARD");        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
    			StatementDetail statementDetailData = new StatementDetail();
    			//statementDetailData.setIp100(tb.getString("I_P100"));
    			//statementDetailData.setIs100(tb.getString("I_S100"));
    			//statementDetailData.setIm100(tb.getString("I_M100"));
    			//statementDetailData.setIconffdate(tb.getString("I_CONF_FDATE"));
    			//statementDetailData.setIconftdate(tb.getString("I_CONF_TDATE"));
    			//statementDetailData.setIsettlefdate(tb.getString("I_SETTLE_FDATE"));
    			//statementDetailData.setIsettletdate(tb.getString("I_SETTLE_TDATE"));
    			//statementDetailData.setIuname(tb.getString("I_UNAME"));
    			//statementDetailData.setIreqgb(tb.getString("I_REQ_GB"));
    			statementDetailData.setBukrs(tb.getString("BUKRS"));
    			statementDetailData.setReceivedate(tb.getString("RECEIVE_DATE"));
    			statementDetailData.setDocunumc(tb.getString("DOCU_NUMC"));
    			statementDetailData.setCardgubun(tb.getString("CARD_GUBUN"));
    			statementDetailData.setCardform(tb.getString("CARD_FORM"));
    			statementDetailData.setCardtype(tb.getString("CARD_TYPE"));
    			statementDetailData.setCardno(tb.getString("CARD_NO"));
    			statementDetailData.setSettledate(tb.getString("SETTLE_DATE"));
    			statementDetailData.setConfno(tb.getString("CONF_NO"));
    			statementDetailData.setConfdate(tb.getString("CONF_DATE"));
    			tmpDate = tb.getString("CONF_TIME");
    			statementDetailData.setConftime(tmpDate.substring(0,2)+":"+tmpDate.substring(2,4)+":"+tmpDate.substring(4,6));
    			statementDetailData.setOrdConftime(tb.getString("CONF_TIME"));
    			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
    			statementDetailData.setWrbtr(tb.getString("WRBTR"));
    			statementDetailData.setTaxamt(tb.getInt("TAX_AMT"));
    			statementDetailData.setSeramt(tb.getInt("SER_AMT"));
    			//statementDetailData.setStcd2(tb.getString("STCD2"));
    			statementDetailData.setName(tb.getString("NAME"));
    			statementDetailData.setKrrepres(tb.getString("KR_REPRES"));
    			//statementDetailData.setKrbustype(tb.getString("KR_BUSTYPE"));
    			statementDetailData.setKrindtype(tb.getString("KR_INDTYPE"));
    			//statementDetailData.setKrtaxoff(tb.getString("KR_TAXOFF"));
    			//statementDetailData.setAdd1(tb.getString("ADD1"));
    			//statementDetailData.setAdd2(tb.getString("ADD2"));
    			//statementDetailData.setTel(tb.getString("TEL"));
    			statementDetailData.setGubun(tb.getString("GUBUN"));
    			//statementDetailData.setZtaxtype(tb.getString("ZTAX_TYPE"));
    			//statementDetailData.setLinkyn(tb.getString("LINK_YN"));
    			statementDetailData.setZsendtext(tb.getString("ZSEND_TEXT"));
    			//statementDetailData.setZsend(tb.getString("ZSEND"));
    			//statementDetailData.setZreceive(tb.getString("ZRECEIVE"));
    			//statementDetailData.setZreceivetext(tb.getString("ZRECEIVE_TEXT"));
    			statementDetailData.setReqnam(tb.getString("REQNAM"));
    			statementDetailData.setReqdate(tb.getString("REQ_DATE"));
    			//statementDetailData.setApprnam(tb.getString("APPRNAM"));
    			//statementDetailData.setApprdate(tb.getString("APPR_DATE"));
    			//statementDetailData.setPernr(tb.getString("PERNR"));
    			//statementDetailData.setZbelnryn(tb.getString("ZBELNR_YN"));
    			statementDetailData.setBelnr(tb.getString("BELNR"));
    			//statementDetailData.setBelnrr(tb.getString("BELNR_R"));
    			statementDetailData.setGjahr(tb.getString("GJAHR"));
    			statementDetailData.setBelnr2(tb.getString("BELNR2"));
    			//statementDetailData.setBelnr2r(tb.getString("BELNR2_R"));
    			//statementDetailData.setBelnrsb(tb.getString("BELNR_SB"));
    			//statementDetailData.setBelnr2sb(tb.getString("BELNR2_SB"));
    			//statementDetailData.setCardgubunt(tb.getString("CARD_GUBUN_T"));
    			//statementDetailData.setCardformt(tb.getString("CARD_FORM_T"));
    			//statementDetailData.setCardtypet(tb.getString("CARD_TYPE_T"));
    			//statementDetailData.setZtaxtypet(tb.getString("ZTAX_TYPE_T"));
    			statementDetailData.setReqnamt(tb.getString("REQNAM_T"));
    			//statementDetailData.setApprnamt(tb.getString("APPRNAM_T"));
    			//statementDetailData.setGubunt(tb.getString("GUBUN_T"));
    			statementDetailData.setHkontt(tb.getString("HKONT_T"));
    			statementDetailData.setBukrst(tb.getString("BUKRS_T"));
    			statementDetailData.setDmbtrd(tb.getInt("DMBTR_D"));
    			statementDetailData.setBktxt(tb.getString("BKTXT"));
    			statementDetailData.setApprtype(tb.getString("APPR_TYPE"));
    			statementDetailData.setReviewnam(tb.getString("REVIEWNAM"));
    			statementDetailData.setReviewnamt(tb.getString("REVIEWNAM_T"));
    			statementDetailData.setDmbtrds(tb.getInt("DMBTR_D")+tb.getInt("SER_AMT"));
    			statementDetailData.setApprkostl(tb.getString("APPR_KOSTL"));
    			statementDetailData.setRevs(tb.getString("REVS"));
    			statementDetailData.setZrevstext(tb.getString("ZREVS_TEXT"));
    			statementDetailData.setBelnrrd(tb.getString("BELNR_R_D"));
    			statementDetailData.setBelnr2rd(tb.getString("BELNR2_R_D"));
    			statementDetailList.add(statementDetailData);
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		return statementDetailList;
    }
    
    public Map<String, Object> EpStatementPageDataRcvRFC(StatementDetail statement, HttpServletRequest request )  {     
    	Connection connect = null;
    	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
    	Map<String, Object> item = new HashMap<String, Object>();
    	int zcnt = 0;
    	int allCnt = 0;				
		try {
		
		connect = new Connection(request);	
		String tmpDate = "";
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_LIST");
		function.getImportParameterList().setValue("I_P100", statement.getIp100());
		function.getImportParameterList().setValue("I_S100", statement.getIs100());
		function.getImportParameterList().setValue("I_M100", statement.getIm100());
		function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
		function.getImportParameterList().setValue("I_REQ_T", statement.getIreqt());
		function.getImportParameterList().setValue("I_REQ_N", statement.getIreqn());
		function.getImportParameterList().setValue("I_FNAME", statement.getIfname());
		function.getImportParameterList().setValue("I_STATE", statement.getIstate());
		connect.executeSAP(function);
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("IT_CARD");        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
    			StatementDetail statementDetailData = new StatementDetail();
    			//statementDetailData.setIp100(tb.getString("I_P100"));
    			//statementDetailData.setIs100(tb.getString("I_S100"));
    			//statementDetailData.setIm100(tb.getString("I_M100"));
    			//statementDetailData.setIconffdate(tb.getString("I_CONF_FDATE"));
    			//statementDetailData.setIconftdate(tb.getString("I_CONF_TDATE"));
    			//statementDetailData.setIsettlefdate(tb.getString("I_SETTLE_FDATE"));
    			//statementDetailData.setIsettletdate(tb.getString("I_SETTLE_TDATE"));
    			//statementDetailData.setIuname(tb.getString("I_UNAME"));
    			//statementDetailData.setIreqgb(tb.getString("I_REQ_GB"));
    			statementDetailData.setBukrs(tb.getString("BUKRS"));
    			statementDetailData.setReceivedate(tb.getString("RECEIVE_DATE"));
    			statementDetailData.setDocunumc(tb.getString("DOCU_NUMC"));
    			statementDetailData.setCardgubun(tb.getString("CARD_GUBUN"));
    			statementDetailData.setCardform(tb.getString("CARD_FORM"));
    			statementDetailData.setCardtype(tb.getString("CARD_TYPE"));
    			statementDetailData.setCardno(tb.getString("CARD_NO"));
    			statementDetailData.setSettledate(tb.getString("SETTLE_DATE"));
    			statementDetailData.setConfno(tb.getString("CONF_NO"));
    			statementDetailData.setConfdate(tb.getString("CONF_DATE"));
    			tmpDate = tb.getString("CONF_TIME");
    			statementDetailData.setConftime(tmpDate.substring(0,2)+":"+tmpDate.substring(2,4)+":"+tmpDate.substring(4,6));
    			statementDetailData.setOrdConftime(tb.getString("CONF_TIME"));
    			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
    			statementDetailData.setWrbtr(tb.getString("WRBTR"));
    			statementDetailData.setTaxamt(tb.getInt("TAX_AMT"));
    			statementDetailData.setSeramt(tb.getInt("SER_AMT"));
    			//statementDetailData.setStcd2(tb.getString("STCD2"));
    			statementDetailData.setName(tb.getString("NAME"));
    			statementDetailData.setKrrepres(tb.getString("KR_REPRES"));
    			//statementDetailData.setKrbustype(tb.getString("KR_BUSTYPE"));
    			statementDetailData.setKrindtype(tb.getString("KR_INDTYPE"));
    			//statementDetailData.setKrtaxoff(tb.getString("KR_TAXOFF"));
    			//statementDetailData.setAdd1(tb.getString("ADD1"));
    			//statementDetailData.setAdd2(tb.getString("ADD2"));
    			//statementDetailData.setTel(tb.getString("TEL"));
    			statementDetailData.setGubun(tb.getString("GUBUN"));
    			//statementDetailData.setZtaxtype(tb.getString("ZTAX_TYPE"));
    			//statementDetailData.setLinkyn(tb.getString("LINK_YN"));
    			statementDetailData.setZsendtext(tb.getString("ZSEND_TEXT"));
    			//statementDetailData.setZsend(tb.getString("ZSEND"));
    			//statementDetailData.setZreceive(tb.getString("ZRECEIVE"));
    			//statementDetailData.setZreceivetext(tb.getString("ZRECEIVE_TEXT"));
    			statementDetailData.setReqnam(tb.getString("REQNAM"));
    			statementDetailData.setReqdate(tb.getString("REQ_DATE"));
    			//statementDetailData.setApprnam(tb.getString("APPRNAM"));
    			//statementDetailData.setApprdate(tb.getString("APPR_DATE"));
    			//statementDetailData.setPernr(tb.getString("PERNR"));
    			//statementDetailData.setZbelnryn(tb.getString("ZBELNR_YN"));
    			statementDetailData.setBelnr(tb.getString("BELNR"));
    			//statementDetailData.setBelnrr(tb.getString("BELNR_R"));
    			statementDetailData.setGjahr(tb.getString("GJAHR"));
    			statementDetailData.setBelnr2(tb.getString("BELNR2"));
    			//statementDetailData.setBelnr2r(tb.getString("BELNR2_R"));
    			//statementDetailData.setBelnrsb(tb.getString("BELNR_SB"));
    			//statementDetailData.setBelnr2sb(tb.getString("BELNR2_SB"));
    			//statementDetailData.setCardgubunt(tb.getString("CARD_GUBUN_T"));
    			//statementDetailData.setCardformt(tb.getString("CARD_FORM_T"));
    			//statementDetailData.setCardtypet(tb.getString("CARD_TYPE_T"));
    			//statementDetailData.setZtaxtypet(tb.getString("ZTAX_TYPE_T"));
    			statementDetailData.setReqnamt(tb.getString("REQNAM_T"));
    			//statementDetailData.setApprnamt(tb.getString("APPRNAM_T"));
    			//statementDetailData.setGubunt(tb.getString("GUBUN_T"));
    			statementDetailData.setHkontt(tb.getString("HKONT_T"));
    			statementDetailData.setBukrst(tb.getString("BUKRS_T"));
    			statementDetailData.setDmbtrd(tb.getInt("DMBTR_D"));
    			statementDetailData.setBktxt(tb.getString("BKTXT"));
    			statementDetailData.setApprtype(tb.getString("APPR_TYPE"));
    			statementDetailData.setReviewnam(tb.getString("REVIEWNAM"));
    			statementDetailData.setReviewnamt(tb.getString("REVIEWNAM_T"));
    			statementDetailData.setDmbtrds(tb.getInt("DMBTR_D")+tb.getInt("SER_AMT"));
    			statementDetailData.setApprkostl(tb.getString("APPR_KOSTL"));
    			statementDetailData.setRevs(tb.getString("REVS"));
    			statementDetailData.setZrevstext(tb.getString("ZREVS_TEXT"));
    			statementDetailData.setBelnrrd(tb.getString("BELNR_R_D"));
    			statementDetailData.setBelnr2rd(tb.getString("BELNR2_R_D"));
				if(!statement.getSessionUser().equals(tb.getString("REVIEWNAM"))){
					zcnt++;
					allCnt++;
					//if(statement.getStartRowIndex()<=zcnt && zcnt<=statement.getEndRowIndex()){
						statementDetailList.add(statementDetailData);
					//}
				}
				
				
				
        	}
        	//log.debug(userDetailData);
        	if(allCnt < 1){
    			statement.setEmptyRecord(true);
    			statement.setRecordCount(0);
    			statement.setPageCount(0);
    		}else{
    			statement.setEmptyRecord(false);
    	    	if(allCnt%statement.getPagePerRecord() != 0){
    	    		statement.setPageCount(allCnt/statement.getPagePerRecord()+1);//페이지수
    	    	}else{
    	    		statement.setPageCount(allCnt/statement.getPagePerRecord());//페이지수
    	    	}
    	    	
    			statement.setRecordCount(allCnt);//전체갯수
    		}
        	
        	
        	item.put("statementDetailList", statementDetailList);
        	item.put("statementPage",statement);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return item;
    }

    /**
	 * 전표 내역 조회
	 */
public Map<String, Object> EpStatementAllDataRcvRFC(StatementDetail statement, HttpServletRequest request )  {    
		Connection connect = null;
    	Map<String, Object> item = new HashMap<String, Object>();
    	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
    	List<StatementDetail> costCenterList = new ArrayList<StatementDetail>();
    	String ikostl = statement.getZzkostl();
    	String costCenterCode = "";
    	String [] costCenterCodes = new String[30];	
    	costCenterCodes[0] = "";
    	int costCneterCnt = 0;
    	int costCneterFlag = 0;		
		try {
			
		connect = new Connection(request);	
		String tmpDate = "";
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_LIST");
		function.getImportParameterList().setValue("I_FNAME", statement.getIfname());
		function.getImportParameterList().setValue("I_STATE", statement.getIstate());
		function.getImportParameterList().setValue("I_P100", statement.getIp100());
		function.getImportParameterList().setValue("I_S100", statement.getIs100());
		function.getImportParameterList().setValue("I_M100", statement.getIm100());
		function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
		function.getImportParameterList().setValue("I_REQ_S", statement.getIreqs());
		function.getImportParameterList().setValue("I_REQ_R", statement.getIreqr());
		function.getImportParameterList().setValue("I_REQ_D", statement.getIreqd());
		function.getImportParameterList().setValue("I_REQ_N", statement.getIreqn());
		function.getImportParameterList().setValue("I_REQ_T", "");
		function.getImportParameterList().setValue("I_CONF_FDATE", statement.getIconffdate());
		function.getImportParameterList().setValue("I_CONF_TDATE", statement.getIconftdate());
		function.getImportParameterList().setValue("I_APPR_FDATE", statement.getIapprfdate());
		function.getImportParameterList().setValue("I_APPR_TDATE", statement.getIapprtdate());
		//function.getImportParameterList().setValue("I_HKONT", statement.getIracct());	
		//function.getImportParameterList().setValue("I_CARD_NO", statement.getCardno());
		connect.executeSAP(function);
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("IT_CARD");       
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
    			StatementDetail statementDetailData = new StatementDetail();
    			//statementDetailData.setIp100(tb.getString("I_P100"));
    			//statementDetailData.setIs100(tb.getString("I_S100"));
    			//statementDetailData.setIm100(tb.getString("I_M100"));
    			//statementDetailData.setIconffdate(tb.getString("I_CONF_FDATE"));
    			//statementDetailData.setIconftdate(tb.getString("I_CONF_TDATE"));
    			//statementDetailData.setIsettlefdate(tb.getString("I_SETTLE_FDATE"));
    			//statementDetailData.setIsettletdate(tb.getString("I_SETTLE_TDATE"));
    			//statementDetailData.setIuname(tb.getString("I_UNAME"));
    			//statementDetailData.setIreqgb(tb.getString("I_REQ_GB"));
    			statementDetailData.setBukrs(tb.getString("BUKRS"));
    			statementDetailData.setReceivedate(tb.getString("RECEIVE_DATE"));
    			statementDetailData.setDocunumc(tb.getString("DOCU_NUMC"));
    			statementDetailData.setCardgubun(tb.getString("CARD_GUBUN"));
    			statementDetailData.setCardform(tb.getString("CARD_FORM"));
    			statementDetailData.setCardtype(tb.getString("CARD_TYPE"));
    			statementDetailData.setCardno(tb.getString("CARD_NO"));
    			statementDetailData.setSettledate(tb.getString("SETTLE_DATE"));
    			statementDetailData.setConfno(tb.getString("CONF_NO"));
    			statementDetailData.setConfdate(tb.getString("CONF_DATE"));
    			tmpDate = tb.getString("CONF_TIME");
    			statementDetailData.setConftime(tmpDate.substring(0,2)+":"+tmpDate.substring(2,4)+":"+tmpDate.substring(4,6));
    			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
    			statementDetailData.setWrbtr(tb.getString("WRBTR"));
    			statementDetailData.setTaxamt(tb.getInt("TAX_AMT"));
    			statementDetailData.setSeramt(tb.getInt("SER_AMT"));
    			//statementDetailData.setStcd2(tb.getString("STCD2"));
    			statementDetailData.setName(tb.getString("NAME"));
    			statementDetailData.setKrrepres(tb.getString("KR_REPRES"));
    			//statementDetailData.setKrbustype(tb.getString("KR_BUSTYPE"));
    			statementDetailData.setKrindtype(tb.getString("KR_INDTYPE"));
    			//statementDetailData.setKrtaxoff(tb.getString("KR_TAXOFF"));
    			//statementDetailData.setAdd1(tb.getString("ADD1"));
    			//statementDetailData.setAdd2(tb.getString("ADD2"));
    			//statementDetailData.setTel(tb.getString("TEL"));
    			statementDetailData.setGubun(tb.getString("GUBUN"));
    			//statementDetailData.setZtaxtype(tb.getString("ZTAX_TYPE"));
    			statementDetailData.setLinkyn(tb.getString("LINK_YN"));
    			statementDetailData.setZsendtext(tb.getString("ZSEND_TEXT"));
    			statementDetailData.setZsend(tb.getString("ZSEND"));
    			statementDetailData.setZreceive(tb.getString("ZRECEIVE"));
    			statementDetailData.setZreceivetext(tb.getString("ZRECEIVE_TEXT"));
    			statementDetailData.setReqnam(tb.getString("REQNAM"));
    			statementDetailData.setReqdate(tb.getString("REQ_DATE"));
    			//statementDetailData.setApprnam(tb.getString("APPRNAM"));
    			statementDetailData.setApprdate(tb.getString("APPR_DATE"));
    			statementDetailData.setApprdater(tb.getString("APPR_DATE_R"));
    			//statementDetailData.setPernr(tb.getString("PERNR"));
    			//statementDetailData.setZbelnryn(tb.getString("ZBELNR_YN"));
    			statementDetailData.setBelnr(tb.getString("BELNR"));
    			//statementDetailData.setBelnrr(tb.getString("BELNR_R"));
    			statementDetailData.setGjahr(tb.getString("GJAHR"));
    			statementDetailData.setBelnr2(tb.getString("BELNR2"));
    			//statementDetailData.setBelnr2r(tb.getString("BELNR2_R"));
    			//statementDetailData.setBelnrsb(tb.getString("BELNR_SB"));
    			//statementDetailData.setBelnr2sb(tb.getString("BELNR2_SB"));
    			//statementDetailData.setCardgubunt(tb.getString("CARD_GUBUN_T"));
    			//statementDetailData.setCardformt(tb.getString("CARD_FORM_T"));
    			//statementDetailData.setCardtypet(tb.getString("CARD_TYPE_T"));
    			//statementDetailData.setZtaxtypet(tb.getString("ZTAX_TYPE_T"));
    			statementDetailData.setReqnamt(tb.getString("REQNAM_T"));
    			statementDetailData.setApprnamt(tb.getString("APPRNAM_T"));
    			//statementDetailData.setGubunt(tb.getString("GUBUN_T"));
    			statementDetailData.setHkontt(tb.getString("HKONT_T"));
    			statementDetailData.setHkont(tb.getString("HKONT"));
    			statementDetailData.setBukrst(tb.getString("BUKRS_T"));
    			statementDetailData.setDmbtrd(tb.getInt("DMBTR_D"));
    			statementDetailData.setBktxt(tb.getString("BKTXT"));
    			statementDetailData.setDmbtrds(tb.getInt("DMBTR_D")+tb.getInt("SER_AMT"));
    			statementDetailData.setGubseqt(tb.getString("GUB_SEQ_T"));
    			statementDetailData.setZreceivenamt(tb.getString("ZRECEIVE_NAM_T"));
    			statementDetailData.setApprtype(tb.getString("APPR_TYPE"));
    			statementDetailData.setReviewnam(tb.getString("REVIEWNAM"));
    			statementDetailData.setReviewnamt(tb.getString("REVIEWNAM_T"));
    			statementDetailData.setZzkostl(tb.getString("ZZKOSTL"));
    			statementDetailData.setZzkostlt(tb.getString("ZZKOSTL_T"));
    			statementDetailData.setRevs(tb.getString("REVS"));
    			statementDetailData.setZrevstext(tb.getString("ZREVS_TEXT"));
    			statementDetailData.setBelnrrd(tb.getString("BELNR_R_D"));
    			statementDetailData.setBelnr2rd(tb.getString("BELNR2_R_D"));
    			if(ikostl != null && !ikostl.equals("")){
					if(!tb.getString("ZZKOSTL").equals("")){
						if(ikostl.substring(1).equals(tb.getString("ZZKOSTL").substring(1))){
							statementDetailList.add(statementDetailData);
						}
					}
				}else{
					statementDetailList.add(statementDetailData);
				}
    			
    			
    			if(!tb.getString("ZZKOSTL").equals("")){
    				costCneterFlag = 0;
    				if(costCenterCodes[0] == ""){
    					costCenterCodes[0] = tb.getString("ZZKOSTL").substring(1);
    					costCneterCnt++;
    					costCenterList.add(statementDetailData);
    				}else{
    					for(int iii=0;iii<costCneterCnt;iii++){
    						if(costCenterCodes[iii].equals(tb.getString("ZZKOSTL").substring(1))){
    							costCneterFlag = 1;
    						}
    					}
    					if(costCneterFlag < 1){
    						costCenterCodes[costCneterCnt] = tb.getString("ZZKOSTL").substring(1);
    						costCenterList.add(statementDetailData);
    						costCneterCnt++;
    					}
    				}
    			}
        	}
        	item.put("statementDetailList", statementDetailList);
        	item.put("costCenterList", costCenterList);
        	//log.debug(userDetailData);
		}
		//log.debug("");			
		

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		return item;
    }

public Map<String, Object> EpStatementAllPageDataRcvRFC(StatementDetail statement, HttpServletRequest request )  {    
	Connection connect = null;
	Map<String, Object> item = new HashMap<String, Object>();
	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
	List<StatementDetail> costCenterList = new ArrayList<StatementDetail>();
	String ikostl = statement.getZzkostl();
	String costCenterCode = "";
	String [] costCenterCodes = new String[30];	
	costCenterCodes[0] = "";
	int costCneterCnt = 0;
	int costCneterFlag = 0;		
	int zcnt = 0;
	int allCnt = 0;
	try {
		
	connect = new Connection(request);	
	String tmpDate = "";
	JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_LIST");
	function.getImportParameterList().setValue("I_FNAME", statement.getIfname());
	function.getImportParameterList().setValue("I_STATE", statement.getIstate());
	function.getImportParameterList().setValue("I_P100", statement.getIp100());
	function.getImportParameterList().setValue("I_S100", statement.getIs100());
	function.getImportParameterList().setValue("I_M100", statement.getIm100());
	function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
	function.getImportParameterList().setValue("I_REQ_S", statement.getIreqs());
	function.getImportParameterList().setValue("I_REQ_R", statement.getIreqr());
	function.getImportParameterList().setValue("I_REQ_D", statement.getIreqd());
	function.getImportParameterList().setValue("I_REQ_N", statement.getIreqn());
	function.getImportParameterList().setValue("I_REQ_T", "");
	function.getImportParameterList().setValue("I_CONF_FDATE", statement.getIconffdate());
	function.getImportParameterList().setValue("I_CONF_TDATE", statement.getIconftdate());
	function.getImportParameterList().setValue("I_APPR_FDATE", statement.getIapprfdate());
	function.getImportParameterList().setValue("I_APPR_TDATE", statement.getIapprtdate());
	//function.getImportParameterList().setValue("I_HKONT", statement.getIracct());	
	//function.getImportParameterList().setValue("I_CARD_NO", statement.getCardno());
	connect.executeSAP(function);
	if (function.getTableParameterList() != null) {
    	JCoTable tb = function.getTableParameterList().getTable("IT_CARD");       
    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
    	{
			StatementDetail statementDetailData = new StatementDetail();
			//statementDetailData.setIp100(tb.getString("I_P100"));
			//statementDetailData.setIs100(tb.getString("I_S100"));
			//statementDetailData.setIm100(tb.getString("I_M100"));
			//statementDetailData.setIconffdate(tb.getString("I_CONF_FDATE"));
			//statementDetailData.setIconftdate(tb.getString("I_CONF_TDATE"));
			//statementDetailData.setIsettlefdate(tb.getString("I_SETTLE_FDATE"));
			//statementDetailData.setIsettletdate(tb.getString("I_SETTLE_TDATE"));
			//statementDetailData.setIuname(tb.getString("I_UNAME"));
			//statementDetailData.setIreqgb(tb.getString("I_REQ_GB"));
			statementDetailData.setBukrs(tb.getString("BUKRS"));
			statementDetailData.setReceivedate(tb.getString("RECEIVE_DATE"));
			statementDetailData.setDocunumc(tb.getString("DOCU_NUMC"));
			statementDetailData.setCardgubun(tb.getString("CARD_GUBUN"));
			statementDetailData.setCardform(tb.getString("CARD_FORM"));
			statementDetailData.setCardtype(tb.getString("CARD_TYPE"));
			statementDetailData.setCardno(tb.getString("CARD_NO"));
			statementDetailData.setSettledate(tb.getString("SETTLE_DATE"));
			statementDetailData.setConfno(tb.getString("CONF_NO"));
			statementDetailData.setConfdate(tb.getString("CONF_DATE"));
			tmpDate = tb.getString("CONF_TIME");
			statementDetailData.setConftime(tmpDate.substring(0,2)+":"+tmpDate.substring(2,4)+":"+tmpDate.substring(4,6));
			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
			statementDetailData.setWrbtr(tb.getString("WRBTR"));
			statementDetailData.setTaxamt(tb.getInt("TAX_AMT"));
			statementDetailData.setSeramt(tb.getInt("SER_AMT"));
			//statementDetailData.setStcd2(tb.getString("STCD2"));
			statementDetailData.setName(tb.getString("NAME"));
			statementDetailData.setKrrepres(tb.getString("KR_REPRES"));
			//statementDetailData.setKrbustype(tb.getString("KR_BUSTYPE"));
			statementDetailData.setKrindtype(tb.getString("KR_INDTYPE"));
			//statementDetailData.setKrtaxoff(tb.getString("KR_TAXOFF"));
			//statementDetailData.setAdd1(tb.getString("ADD1"));
			//statementDetailData.setAdd2(tb.getString("ADD2"));
			//statementDetailData.setTel(tb.getString("TEL"));
			statementDetailData.setGubun(tb.getString("GUBUN"));
			//statementDetailData.setZtaxtype(tb.getString("ZTAX_TYPE"));
			statementDetailData.setLinkyn(tb.getString("LINK_YN"));
			statementDetailData.setZsendtext(tb.getString("ZSEND_TEXT"));
			statementDetailData.setZsend(tb.getString("ZSEND"));
			statementDetailData.setZreceive(tb.getString("ZRECEIVE"));
			statementDetailData.setZreceivetext(tb.getString("ZRECEIVE_TEXT"));
			statementDetailData.setReqnam(tb.getString("REQNAM"));
			statementDetailData.setReqdate(tb.getString("REQ_DATE"));
			//statementDetailData.setApprnam(tb.getString("APPRNAM"));
			statementDetailData.setApprdate(tb.getString("APPR_DATE"));
			statementDetailData.setApprdater(tb.getString("APPR_DATE_R"));
			//statementDetailData.setPernr(tb.getString("PERNR"));
			//statementDetailData.setZbelnryn(tb.getString("ZBELNR_YN"));
			statementDetailData.setBelnr(tb.getString("BELNR"));
			//statementDetailData.setBelnrr(tb.getString("BELNR_R"));
			statementDetailData.setGjahr(tb.getString("GJAHR"));
			statementDetailData.setBelnr2(tb.getString("BELNR2"));
			//statementDetailData.setBelnr2r(tb.getString("BELNR2_R"));
			//statementDetailData.setBelnrsb(tb.getString("BELNR_SB"));
			//statementDetailData.setBelnr2sb(tb.getString("BELNR2_SB"));
			//statementDetailData.setCardgubunt(tb.getString("CARD_GUBUN_T"));
			//statementDetailData.setCardformt(tb.getString("CARD_FORM_T"));
			//statementDetailData.setCardtypet(tb.getString("CARD_TYPE_T"));
			//statementDetailData.setZtaxtypet(tb.getString("ZTAX_TYPE_T"));
			statementDetailData.setReqnamt(tb.getString("REQNAM_T"));
			statementDetailData.setApprnamt(tb.getString("APPRNAM_T"));
			//statementDetailData.setGubunt(tb.getString("GUBUN_T"));
			statementDetailData.setHkontt(tb.getString("HKONT_T"));
			statementDetailData.setHkont(tb.getString("HKONT"));
			statementDetailData.setBukrst(tb.getString("BUKRS_T"));
			statementDetailData.setDmbtrd(tb.getInt("DMBTR_D"));
			statementDetailData.setBktxt(tb.getString("BKTXT"));
			statementDetailData.setDmbtrds(tb.getInt("DMBTR_D")+tb.getInt("SER_AMT"));
			statementDetailData.setGubseqt(tb.getString("GUB_SEQ_T"));
			statementDetailData.setZreceivenamt(tb.getString("ZRECEIVE_NAM_T"));
			statementDetailData.setApprtype(tb.getString("APPR_TYPE"));
			statementDetailData.setReviewnam(tb.getString("REVIEWNAM"));
			statementDetailData.setReviewnamt(tb.getString("REVIEWNAM_T"));
			statementDetailData.setZzkostl(tb.getString("ZZKOSTL"));
			statementDetailData.setZzkostlt(tb.getString("ZZKOSTL_T"));
			statementDetailData.setRevs(tb.getString("REVS"));
			statementDetailData.setZrevstext(tb.getString("ZREVS_TEXT"));
			statementDetailData.setBelnrrd(tb.getString("BELNR_R_D"));
			statementDetailData.setBelnr2rd(tb.getString("BELNR2_R_D"));
			if(ikostl != null && !ikostl.equals("")){
				if(!tb.getString("ZZKOSTL").equals("")){
					if(ikostl.substring(1).equals(tb.getString("ZZKOSTL").substring(1))){
						zcnt++;
						allCnt++;
						if(statement.getStartRowIndex()<=zcnt && zcnt<=statement.getEndRowIndex()){
							statementDetailList.add(statementDetailData);
						}
					}
				}
			}else{
				allCnt++;
				if(statement.getStartRowIndex()<=i+1 && i+1<=statement.getEndRowIndex()){
					statementDetailList.add(statementDetailData);
				}
			}
			
			
			if(!tb.getString("ZZKOSTL").equals("")){
				costCneterFlag = 0;
				if(costCenterCodes[0] == ""){
					costCenterCodes[0] = tb.getString("ZZKOSTL").substring(1);
					costCneterCnt++;
					costCenterList.add(statementDetailData);
				}else{
					for(int iii=0;iii<costCneterCnt;iii++){
						if(costCenterCodes[iii].equals(tb.getString("ZZKOSTL").substring(1))){
							costCneterFlag = 1;
						}
					}
					if(costCneterFlag < 1){
						costCenterCodes[costCneterCnt] = tb.getString("ZZKOSTL").substring(1);
						costCenterList.add(statementDetailData);
						costCneterCnt++;
					}
				}
			}
    	}
    	item.put("statementDetailList", statementDetailList);
    	item.put("costCenterList", costCenterList);
    	statement.setEmptyRecord(false);
    	if(allCnt%statement.getPagePerRecord() != 0){
    		statement.setPageCount(allCnt/statement.getPagePerRecord()+1);//페이지수
    	}else{
    		statement.setPageCount(allCnt/statement.getPagePerRecord());//페이지수
    	}
    	
		statement.setRecordCount(allCnt);//전체갯수
		if(allCnt < 1){
			statement.setEmptyRecord(true);
			statement.setRecordCount(0);
			//statement.setPageIndex(0);
			statement.setPageCount(0);
		}
		item.put("statementPage",statement);
    	//log.debug(userDetailData);
	}else{
		statement.setEmptyRecord(true);
		statement.setRecordCount(0);
		//statement.setPageIndex(0);
		statement.setPageCount(0);
		item.put("statementPage",statement);
	}
	//log.debug("");			


	} catch (JCoException e) {

		log.debug("e.getMessage():"+e.getMessage());		
		log.debug("e.getGroup():"+e.getGroup());
		log.debug("e.getKey():"+e.getKey());
		e.printStackTrace();
	}finally{

	}
	return item;
}

/**
 * 코스트센터 조회
 */
public List<StatementDetail> EpStatementAllDataCostCenterRcvRFC(StatementDetail statement, HttpServletRequest request )  {
    
	Connection connect = null;
	List<StatementDetail> zzkostlList = new ArrayList<StatementDetail>();
	String [] zzkostlCodes = new String[50];	
	zzkostlCodes[0] = "";
	int zzkostlCnt = 0;
	int zzkostlFlag = 0;
					
	try {
		
	connect = new Connection(request);	
	String tmpDate = "";
	JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_LIST");
	function.getImportParameterList().setValue("I_FNAME", statement.getIfname());
	function.getImportParameterList().setValue("I_STATE", statement.getIstate());
	function.getImportParameterList().setValue("I_P100", statement.getIp100());
	function.getImportParameterList().setValue("I_S100", statement.getIs100());
	function.getImportParameterList().setValue("I_M100", statement.getIm100());
	function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
	function.getImportParameterList().setValue("I_REQ_S", statement.getIreqs());
	function.getImportParameterList().setValue("I_REQ_R", statement.getIreqr());
	function.getImportParameterList().setValue("I_REQ_D", statement.getIreqd());
	function.getImportParameterList().setValue("I_REQ_N", statement.getIreqn());
	function.getImportParameterList().setValue("I_REQ_T", "");
	function.getImportParameterList().setValue("I_CONF_FDATE", statement.getIconffdate());
	function.getImportParameterList().setValue("I_CONF_TDATE", statement.getIconftdate());
	function.getImportParameterList().setValue("I_APPR_FDATE", "");
	function.getImportParameterList().setValue("I_APPR_TDATE", "");
	//function.getImportParameterList().setValue("I_HKONT", statement.getIracct());	
	//function.getImportParameterList().setValue("I_CARD_NO", statement.getCardno());
	connect.executeSAP(function);
	if (function.getTableParameterList() != null) {
    	JCoTable tb = function.getTableParameterList().getTable("IT_CARD");       
    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
    	{
			StatementDetail statementDetailData = new StatementDetail();
			//statementDetailData.setIp100(tb.getString("I_P100"));
			//statementDetailData.setIs100(tb.getString("I_S100"));
			//statementDetailData.setIm100(tb.getString("I_M100"));
			//statementDetailData.setIconffdate(tb.getString("I_CONF_FDATE"));
			//statementDetailData.setIconftdate(tb.getString("I_CONF_TDATE"));
			//statementDetailData.setIsettlefdate(tb.getString("I_SETTLE_FDATE"));
			//statementDetailData.setIsettletdate(tb.getString("I_SETTLE_TDATE"));
			//statementDetailData.setIuname(tb.getString("I_UNAME"));
			//statementDetailData.setIreqgb(tb.getString("I_REQ_GB"));
			statementDetailData.setBukrs(tb.getString("BUKRS"));
			statementDetailData.setReceivedate(tb.getString("RECEIVE_DATE"));
			statementDetailData.setDocunumc(tb.getString("DOCU_NUMC"));
			statementDetailData.setCardgubun(tb.getString("CARD_GUBUN"));
			statementDetailData.setCardform(tb.getString("CARD_FORM"));
			statementDetailData.setCardtype(tb.getString("CARD_TYPE"));
			statementDetailData.setCardno(tb.getString("CARD_NO"));
			statementDetailData.setSettledate(tb.getString("SETTLE_DATE"));
			statementDetailData.setConfno(tb.getString("CONF_NO"));
			statementDetailData.setConfdate(tb.getString("CONF_DATE"));
			tmpDate = tb.getString("CONF_TIME");
			statementDetailData.setConftime(tmpDate.substring(0,2)+":"+tmpDate.substring(2,4)+":"+tmpDate.substring(4,6));
			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
			statementDetailData.setWrbtr(tb.getString("WRBTR"));
			statementDetailData.setTaxamt(tb.getInt("TAX_AMT"));
			statementDetailData.setSeramt(tb.getInt("SER_AMT"));
			//statementDetailData.setStcd2(tb.getString("STCD2"));
			statementDetailData.setName(tb.getString("NAME"));
			statementDetailData.setKrrepres(tb.getString("KR_REPRES"));
			//statementDetailData.setKrbustype(tb.getString("KR_BUSTYPE"));
			statementDetailData.setKrindtype(tb.getString("KR_INDTYPE"));
			//statementDetailData.setKrtaxoff(tb.getString("KR_TAXOFF"));
			//statementDetailData.setAdd1(tb.getString("ADD1"));
			//statementDetailData.setAdd2(tb.getString("ADD2"));
			//statementDetailData.setTel(tb.getString("TEL"));
			statementDetailData.setGubun(tb.getString("GUBUN"));
			//statementDetailData.setZtaxtype(tb.getString("ZTAX_TYPE"));
			statementDetailData.setLinkyn(tb.getString("LINK_YN"));
			statementDetailData.setZsendtext(tb.getString("ZSEND_TEXT"));
			statementDetailData.setZsend(tb.getString("ZSEND"));
			statementDetailData.setZreceive(tb.getString("ZRECEIVE"));
			statementDetailData.setZreceivetext(tb.getString("ZRECEIVE_TEXT"));
			statementDetailData.setReqnam(tb.getString("REQNAM"));
			statementDetailData.setReqdate(tb.getString("REQ_DATE"));
			//statementDetailData.setApprnam(tb.getString("APPRNAM"));
			statementDetailData.setApprdate(tb.getString("APPR_DATE"));
			//statementDetailData.setPernr(tb.getString("PERNR"));
			//statementDetailData.setZbelnryn(tb.getString("ZBELNR_YN"));
			statementDetailData.setBelnr(tb.getString("BELNR"));
			//statementDetailData.setBelnrr(tb.getString("BELNR_R"));
			statementDetailData.setGjahr(tb.getString("GJAHR"));
			statementDetailData.setBelnr2(tb.getString("BELNR2"));
			//statementDetailData.setBelnr2r(tb.getString("BELNR2_R"));
			//statementDetailData.setBelnrsb(tb.getString("BELNR_SB"));
			//statementDetailData.setBelnr2sb(tb.getString("BELNR2_SB"));
			//statementDetailData.setCardgubunt(tb.getString("CARD_GUBUN_T"));
			//statementDetailData.setCardformt(tb.getString("CARD_FORM_T"));
			//statementDetailData.setCardtypet(tb.getString("CARD_TYPE_T"));
			//statementDetailData.setZtaxtypet(tb.getString("ZTAX_TYPE_T"));
			statementDetailData.setReqnamt(tb.getString("REQNAM_T"));
			statementDetailData.setApprnamt(tb.getString("APPRNAM_T"));
			//statementDetailData.setGubunt(tb.getString("GUBUN_T"));
			statementDetailData.setHkontt(tb.getString("HKONT_T"));
			statementDetailData.setHkont(tb.getString("HKONT"));
			statementDetailData.setBukrst(tb.getString("BUKRS_T"));
			statementDetailData.setDmbtrd(tb.getInt("DMBTR_D"));
			statementDetailData.setBktxt(tb.getString("BKTXT"));
			statementDetailData.setDmbtrds(tb.getInt("DMBTR_D")+tb.getInt("SER_AMT"));
			statementDetailData.setGubseqt(tb.getString("GUB_SEQ_T"));
			statementDetailData.setZreceivenamt(tb.getString("ZRECEIVE_NAM_T"));
			statementDetailData.setApprtype(tb.getString("APPR_TYPE"));
			statementDetailData.setReviewnam(tb.getString("REVIEWNAM"));
			statementDetailData.setReviewnamt(tb.getString("REVIEWNAM_T"));
			statementDetailData.setZzkostl(tb.getString("ZZKOSTL"));
			statementDetailData.setZzkostlt(tb.getString("ZZKOSTL_T"));
			zzkostlFlag = 0;
			if(zzkostlCodes[0] == ""){
				zzkostlCodes[0] = tb.getString("ZZKOSTL");
				zzkostlCnt++;
				zzkostlList.add(statementDetailData);
			}else{
				for(int iii=0;iii<zzkostlCnt;iii++){
					if(zzkostlCodes[iii].equals(tb.getString("ZZKOSTL"))){
						zzkostlFlag = 1;
					}
				}
				if(zzkostlFlag < 1){
					zzkostlCodes[zzkostlCnt] = tb.getString("ZZKOSTL");
					zzkostlList.add(statementDetailData);
					zzkostlCnt++;
				}
			}
    	}
    	//log.debug(userDetailData);
	}
	//log.debug("");			


	} catch (JCoException e) {

		log.debug("e.getMessage():"+e.getMessage());		
		log.debug("e.getGroup():"+e.getGroup());
		log.debug("e.getKey():"+e.getKey());
		e.printStackTrace();
	}finally{

	}
	
	return zzkostlList;
}


/**
 * 전표 상세 조회
 */    
public List<StatementDetail> EpStatementDetailDataRcvRFC(StatementDetail statement, HttpServletRequest request )  {
        
		Connection connect = null;
    	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
    					
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_DOC_ITEM");
		function.getImportParameterList().setValue("I_BUKRS", statement.getBukrs());
		function.getImportParameterList().setValue("I_GJAHR", statement.getGjahr());
		function.getImportParameterList().setValue("I_BELNR", statement.getBelnr());
		connect.executeSAP(function);
		int tmpDmbtrs = 0;
		int tmpDmbtrh = 0;
		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("IT_DOC_ITEM");        	
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
    			StatementDetail statementDetailData = new StatementDetail();
    			//statementDetailData.setIbukrs(tb.getString("I_BUKRS"));
    			//statementDetailData.setIgjahr(tb.getString("I_GJAHR"));
    			//statementDetailData.setIbelnr(tb.getString("I_BELNR"));
    			//statementDetailData.setBukrs(tb.getString("BUKRS"));
    			//statementDetailData.setGjahr(tb.getString("GJAHR"));
    			statementDetailData.setBelnr(tb.getString("BELNR"));
    			statementDetailData.setBuzei(tb.getString("BUZEI"));
    			//statementDetailData.setBlart(tb.getString("BLART"));
    			//statementDetailData.setBktxt(tb.getString("BKTXT"));
    			//statementDetailData.setBudat(tb.getString("BUDAT"));
    			//statementDetailData.setBldat(tb.getString("BLDAT"));
    			//statementDetailData.setCpudt(tb.getString("CPUDT"));
    			statementDetailData.setHkont(tb.getString("HKONT"));
    			statementDetailData.setZztxt50(tb.getString("ZZTXT50"));
    			statementDetailData.setDmbtr(tb.getInt("DMBTR"));
    			statementDetailData.setMwskz(tb.getString("MWSKZ"));
    			statementDetailData.setZuonr(tb.getString("ZUONR"));
    			statementDetailData.setSgtxt(tb.getString("SGTXT"));
    			statementDetailData.setShkzg(tb.getString("SHKZG"));
    			if(tb.getString("SHKZG").equals("H")){
    				tmpDmbtrh = tmpDmbtrh + tb.getInt("DMBTR");
    			}
    			if(tb.getString("SHKZG").equals("S")){
    				tmpDmbtrs = tmpDmbtrs + tb.getInt("DMBTR");
    			}
    			statementDetailData.setDmbtrh(tmpDmbtrh);
    			statementDetailData.setDmbtrs(tmpDmbtrs);
    			//statementDetailData.setKostl(tb.getString("KOSTL"));
    			//statementDetailData.setZzkostl(tb.getString("ZZKOSTL"));
    			statementDetailList.add(statementDetailData);
        	}
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return statementDetailList;
    }

	/**
	 * 승인or반려
	 */
	public void EpStatementConfirmDataRcvRFC(StatementDetail statement, String [] stdIds, String confm, String [] ztexts, String [] revs, HttpServletRequest request )  {
	   
	
		String sapid = statement.getIuname();
		List<StatementDetail> statementList =  EpStatementDataRcvRFC(statement, null);
		
		String today = DateUtil.getToday("yyyyMMdd");
		
		String tmpDate = "";
		String complete = "";
		String ctypecomplete = "";
		String reversecomplete = "";
		String sender = "";
		String receiver = "";
		String confirmDiv = "";
		String confirmDate = "";
		String ctypesender = "";
		String ctypeconfirmDate = "";
		String revssender = "";
		String revsreceiver = "";
		String revsconfirmDiv = "";
		String revsconfirmDate = "";
		String eapprnam = "";
		String eapprnam2 = "";
		List<StatementDetail> mailList1 = new ArrayList();
		List<StatementDetail> ctypemailList1 = new ArrayList();
		List<StatementDetail> revsmailList1 = new ArrayList();
		DecimalFormat df = new DecimalFormat("#,###");
		for (StatementDetail statements : statementList) {//승인요청 내역 리스트 반복
			for(int i=0;i<stdIds.length;i++){//리스트에서 체크한 전표 반복
				if(stdIds[i].equals(statements.getBelnr()) || stdIds[i].equals(statements.getBelnrrd())){//체크한 전표와 승인요청 내역리스트의 전표가 같을경우
					if(!revs[i].equals("X")){
						if(statements.getApprtype().equals("C") && statements.getReviewnam().equals("") && !"D".equals(confm)){//검토이거나 반려가 아닐경우
							StatementDetail ctypemailList2 = new StatementDetail();
							Connection ctypeconnect = null;	
							ctypeconnect = new Connection(request);	
							JCoFunction ctypefunction = ctypeconnect.getSAPFunction("ZFB_IF_EP_REVIEWER_UPDATE");
							ctypefunction.getImportParameterList().setValue("I_BUKRS", statements.getBukrs());
							ctypefunction.getImportParameterList().setValue("I_RECEIVE_DATE", statements.getReceivedate().replaceAll("-",""));
							ctypefunction.getImportParameterList().setValue("I_DOCU_NUMC", statements.getDocunumc());
							ctypefunction.getImportParameterList().setValue("I_CARD_GUBUN", statements.getCardgubun());
							ctypefunction.getImportParameterList().setValue("I_CARD_FORM", statements.getCardform());
							if(statements.getCardtype() == null){
								ctypefunction.getImportParameterList().setValue("I_CARD_TYPE", "");
							}else{
								ctypefunction.getImportParameterList().setValue("I_CARD_TYPE", statements.getCardtype());
							}
							
							ctypefunction.getImportParameterList().setValue("I_CARD_NO", statements.getCardno());
							if(statements.getSettledate() == null){
								ctypefunction.getImportParameterList().setValue("I_SETTLE_DATE", "");
							}else{
								ctypefunction.getImportParameterList().setValue("I_SETTLE_DATE", statements.getSettledate().replaceAll("-",""));
							}
							ctypefunction.getImportParameterList().setValue("I_CONF_NO", statements.getConfno());
							ctypefunction.getImportParameterList().setValue("I_CONF_DATE", statements.getConfdate().replaceAll("-",""));
							ctypefunction.getImportParameterList().setValue("I_CONF_TIME", statements.getOrdConftime());
							ctypefunction.getImportParameterList().setValue("I_ZZKOSTL", statements.getApprkostl());
							ctypefunction.getImportParameterList().setValue("I_UNAME", sapid);
							ctypemailList2.setCardno(statements.getCardno());
							ctypemailList2.setBelnr(statements.getBelnr());
							ctypemailList2.setDmbtr(statements.getDmbtr());
							ctypemailList2.setReqnamt(statements.getReqnamt());
							ctypemailList2.setReqdate(statements.getReqdate());
							ctypemailList2.setConfdate(statements.getConfdate());
							
							ctypesender = sapid.toLowerCase();
							ctypeconfirmDate = DateUtil.getToday("yyyy.MM.dd");
							try {
								ctypeconnect.executeSAP(ctypefunction);	
								eapprnam = ctypefunction.getExportParameterList().getString("E_APPRNAM");
								eapprnam2 = ctypefunction.getExportParameterList().getString("E_APPRNAM2");
								ctypemailList2.setEapprnam(eapprnam);
								ctypemailList2.setEapprnam2(eapprnam2);
								ctypecomplete = ctypefunction.getExportParameterList().getString("E_TYPE");
								ctypemailList1.add(ctypemailList2);
							}catch (JCoException e) {
								log.debug("e.getMessage():"+e.getMessage());		
								log.debug("e.getGroup():"+e.getGroup());
								log.debug("e.getKey():"+e.getKey());
								e.printStackTrace();
							}finally{
						
							}
							
							
						}else{//검토가 아니거나 반려인경우
							StatementDetail mailList2 = new StatementDetail();
							Connection connect = null;	
							connect = new Connection(request);	
							JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_RECEIVE");//승인or반려RFC
							function.getImportParameterList().setValue("I_BUKRS", statements.getBukrs());
							function.getImportParameterList().setValue("I_RECEIVE_DATE", statements.getReceivedate().replaceAll("-",""));
							function.getImportParameterList().setValue("I_DOCU_NUMC", statements.getDocunumc());
							function.getImportParameterList().setValue("I_CARD_GUBUN", statements.getCardgubun());
							function.getImportParameterList().setValue("I_CARD_FORM", statements.getCardform());
							function.getImportParameterList().setValue("I_ZRECEIVE_GB", "A");
							if(statements.getCardtype() == null){
								function.getImportParameterList().setValue("I_CARD_TYPE", "");
							}else{
								function.getImportParameterList().setValue("I_CARD_TYPE", statements.getCardtype());
							}
							
							function.getImportParameterList().setValue("I_CARD_NO", statements.getCardno());
							if(statements.getSettledate() == null){
								function.getImportParameterList().setValue("I_SETTLE_DATE", "");
							}else{
								function.getImportParameterList().setValue("I_SETTLE_DATE", statements.getSettledate().replaceAll("-",""));
							}
							function.getImportParameterList().setValue("I_CONF_NO", statements.getConfno());
							function.getImportParameterList().setValue("I_CONF_DATE", statements.getConfdate().replaceAll("-",""));
							function.getImportParameterList().setValue("I_CONF_TIME", statements.getOrdConftime());
							function.getImportParameterList().setValue("I_ZRECEIVE", confm);
							function.getImportParameterList().setValue("I_APPR_DATE", today);
							if("D".equals(confm)){
								function.getImportParameterList().setValue("I_ZRECEIVE_TEXT", ztexts[i]);
								mailList2.setZreceivetext(ztexts[i]);
							}
							function.getImportParameterList().setValue("I_ZRECEIVE_NAM", sapid);
							mailList2.setCardno(statements.getCardno());
							mailList2.setBelnr(statements.getBelnr());
							mailList2.setDmbtr(statements.getDmbtr());
							mailList2.setReqnam(statements.getReqnam().toLowerCase());
							tmpDate = statements.getConftime();
							mailList2.setConfdate(statements.getConfdate());
							mailList1.add(mailList2);
							sender = sapid.toLowerCase();
							receiver = statements.getReqnam().toLowerCase();
							confirmDiv = confm;
							confirmDate = DateUtil.getToday("yyyy.MM.dd");
							try {
								connect.executeSAP(function);	
								complete = function.getExportParameterList().getString("E_TYPE");
							}catch (JCoException e) {
								
								log.debug("e.getMessage():"+e.getMessage());		
								log.debug("e.getGroup():"+e.getGroup());
								log.debug("e.getKey():"+e.getKey());
								e.printStackTrace();
							}finally{
						
							}
						}
					}else{
						if(statements.getRevs().equals("X")){
							StatementDetail mailList3 = new StatementDetail();
							Connection connect = null;	
							connect = new Connection(request);	
							JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_CARD_RECEIVE");//승인or반려RFC
							function.getImportParameterList().setValue("I_BUKRS", statements.getBukrs());
							function.getImportParameterList().setValue("I_RECEIVE_DATE", statements.getReceivedate().replaceAll("-",""));
							function.getImportParameterList().setValue("I_DOCU_NUMC", statements.getDocunumc());
							function.getImportParameterList().setValue("I_CARD_GUBUN", statements.getCardgubun());
							function.getImportParameterList().setValue("I_CARD_FORM", statements.getCardform());
							function.getImportParameterList().setValue("I_ZRECEIVE_GB", "R");
							if(statements.getCardtype() == null){
								function.getImportParameterList().setValue("I_CARD_TYPE", "");
							}else{
								function.getImportParameterList().setValue("I_CARD_TYPE", statements.getCardtype());
							}
							
							function.getImportParameterList().setValue("I_CARD_NO", statements.getCardno());
							if(statements.getSettledate() == null){
								function.getImportParameterList().setValue("I_SETTLE_DATE", "");
							}else{
								function.getImportParameterList().setValue("I_SETTLE_DATE", statements.getSettledate().replaceAll("-",""));
							}
							
							function.getImportParameterList().setValue("I_CONF_NO", statements.getConfno());
							function.getImportParameterList().setValue("I_CONF_DATE", statements.getConfdate().replaceAll("-",""));
							function.getImportParameterList().setValue("I_CONF_TIME", statements.getOrdConftime());
							function.getImportParameterList().setValue("I_ZRECEIVE", confm);
							function.getImportParameterList().setValue("I_APPR_DATE", today);
							function.getImportParameterList().setValue("I_ZRECEIVE_NAM", sapid);
							mailList3.setCardno(statements.getCardno());
							mailList3.setBelnr(statements.getBelnr());
							mailList3.setBelnrrd(statements.getBelnrrd());
							mailList3.setDmbtr(statements.getDmbtr());
							mailList3.setReqnam(statements.getReqnam().toLowerCase());
							tmpDate = statements.getConftime();
							mailList3.setConfdate(statements.getConfdate());
							revsmailList1.add(mailList3);
							revssender = sapid.toLowerCase();
							revsreceiver = statements.getReqnam().toLowerCase();
							revsconfirmDiv = confm;
							revsconfirmDate = DateUtil.getToday("yyyy.MM.dd");
							try {
								connect.executeSAP(function);	
								reversecomplete = function.getExportParameterList().getString("E_TYPE");
							}catch (JCoException e) {
								
								log.debug("e.getMessage():"+e.getMessage());		
								log.debug("e.getGroup():"+e.getGroup());
								log.debug("e.getKey():"+e.getKey());
								e.printStackTrace();
							}finally{
						
							}
						}
					}
				}
			}
		}
		if(complete.equals("S")){//승인or반려 성공시 요청자에게 메일링
			String contents = "";
			String receiverUser = "";
			int tmpCnt = 0;
			int tmpAllCnt = 0;
			User user1 = this.userService.read(sender);
			User user2 = new User();
			Mail mail = new Mail();
			mail.setMailType(MailConstant.MAIL_TYPE_HTML);
			HashMap<String, String> recip= new HashMap<String, String>();
			if(confirmDiv.equals("S")){//승인시 요청자에게 메일링
				mail.setTitle("법인카드 전표 승인");
				for (StatementDetail state : mailList1){//승인내역 리스트 반복
					if(!receiverUser.equals(state.getReqnam()) && receiverUser != ""){//승인요청자가 바뀔때 이전 내용 메일링
						contents = "<div style='font-size:15px;'><strong>[법인카드 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
						mail.setContent(contents);
						List recipients = new ArrayList();
						user2 = this.userService.read(receiverUser);
						recip.put("email", user2.getMail());
						recipients.add(recip);
						if(recipients.size() > 0) {
							mail.setToEmailList(recipients);
							Map dataMap = new HashMap();
							sendService.sendMailNotice(mail, dataMap, user1);
						}
						tmpCnt = 1;
						contents = "";
						receiverUser = state.getReqnam();
						contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td></tr>";
						tmpAllCnt++;
						if(tmpAllCnt == mailList1.size()){//승인내역 리스트 마지막일때 메일링
							contents = "<div style='font-size:15px;'><strong>[법인카드 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients1 = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients1.add(recip);
							if(recipients1.size() > 0) {
								mail.setToEmailList(recipients1);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}
					}else{
						tmpAllCnt++;
						tmpCnt++;
						if(tmpAllCnt == mailList1.size()){//승인내역 리스트 마지막일때 메일링
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td></tr>";
							contents = "<div style='font-size:15px;'><strong>[법인카드 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients.add(recip);
							if(recipients.size() > 0) {
								mail.setToEmailList(recipients);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}else{//승인요청자가 동일할때 내용 합치기
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td></tr>";
							receiverUser = state.getReqnam();
						}
					}
				}
			}else{//반려시 요청자에게 메일링
				mail.setTitle("법인카드 전표 반려");
				for (StatementDetail state : mailList1){
					if(!receiverUser.equals(state.getReqnam()) && receiverUser != ""){
						contents = "<strong>[법인카드 전표 반려]</strong><br/><br/>1) 반려 건수:"+tmpCnt+"건<br/><br/>2) 반려자:"+user1.getUserName()+"<br/><br/>3) 반려일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>반려사유</td></tr>"+contents+"</table></div>";
						mail.setContent(contents);
						List recipients = new ArrayList();
						user2 = this.userService.read(receiverUser);
						recip.put("email", user2.getMail());
						recipients.add(recip);
						if(recipients.size() > 0) {
							mail.setToEmailList(recipients);
							Map dataMap = new HashMap();
							sendService.sendMailNotice(mail, dataMap, user1);
						}
						tmpCnt = 1;
						contents = "";
						receiverUser = state.getReqnam();
						contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td><td style='BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getZreceivetext()+"</td></tr>";
						tmpAllCnt++;
						if(tmpAllCnt == mailList1.size()){
							contents = "<strong>[법인카드 전표 반려]</strong><br/><br/>1) 반려 건수:"+tmpCnt+"건<br/><br/>2) 반려자:"+user1.getUserName()+"<br/><br/>3) 반려일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>반려사유</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients1 = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients1.add(recip);
							if(recipients.size() > 0) {
								mail.setToEmailList(recipients1);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}
					}else{
						tmpAllCnt++;
						tmpCnt++;
						if(tmpAllCnt == mailList1.size()){
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td><td style='BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getZreceivetext()+"</td></tr>";
							contents = "<strong>[법인카드 전표 반려]</strong><br/><br/>1) 반려 건수:"+tmpCnt+"건<br/><br/>2) 반려자:"+user1.getUserName()+"<br/><br/>3) 반려일:"+confirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>반려사유</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients.add(recip);
							if(recipients.size() > 0) {
								mail.setToEmailList(recipients);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}else{
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td><td style='BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getZreceivetext()+"</td></tr>";
							receiverUser = state.getReqnam();
						}
					}
				}
			}
		}
		if(ctypecomplete.equals("S")){//검토성공시 결재자에게 메일링
			String contents = "";
			String tempEapprnam = "";
			String tempEapprnam2 = "";
			User user3 = this.userService.read(ctypesender);
			
			Mail mail2 = new Mail();
			mail2.setMailType(MailConstant.MAIL_TYPE_HTML);
			mail2.setTitle("법인카드 승인 요청-검토완료");
			//contents = "<div style='font-size:15px;'><strong>[법인카드 승인 요청-검토완료]</strong><br/><br/>1) 승인 요청 건수:"+ctypemailList1.size()+"건<br/><br/>2) 검토자:"+user3.getUserName()+"<br/><br/>3) 검토일:"+ctypeconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>";
			contents = "<div style='font-size:15px;'><strong>[법인카드 승인 요청-검토완료]</strong><br/><br/>1) 검토자:"+user3.getUserName()+"<br/><br/>2) 검토일:"+ctypeconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>";
			for (StatementDetail state : ctypemailList1){
				if(!tempEapprnam.equals("") && !tempEapprnam.equals(state.getEapprnam())){
					contents = contents+"</table></div>";
					User user4 = new User();
					user4 = this.userService.read(tempEapprnam.toLowerCase());
					
					mail2.setContent(contents);
					mail2.setToEmail(user4.getMail());
					Map dataMap = new HashMap();
					sendService.sendMailSimple(mail2, dataMap, user3);
					
					contents = "<div style='font-size:15px;'><strong>[법인카드 승인 요청-검토완료]</strong><br/><br/>1) 검토자:"+user3.getUserName()+"<br/><br/>2) 검토일:"+ctypeconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>";
					contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td></tr>";
				}else{
					contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state.getDmbtr())+"</td></tr>";
				}
				tempEapprnam = state.getEapprnam();
			}
			contents = contents+"</table></div>";
			
			User user4 = new User();
			user4 = this.userService.read(tempEapprnam.toLowerCase());
			
			mail2.setContent(contents);
			mail2.setToEmail(user4.getMail());
			Map dataMap = new HashMap();
			sendService.sendMailSimple(mail2, dataMap, user3);
			contents = "<div style='font-size:15px;'><strong>[법인카드 승인 요청-검토완료]</strong><br/><br/>1) 검토자:"+user3.getUserName()+"<br/><br/>2) 검토일:"+ctypeconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>";
			for (StatementDetail state1 : ctypemailList1){
				if(!tempEapprnam2.equals("") && !tempEapprnam2.equals(state1.getEapprnam2())){
					contents = contents+"</table></div>";
					User user5 = new User();
					user5 = this.userService.read(tempEapprnam2.toLowerCase());
					
					mail2.setContent(contents);
					mail2.setToEmail(user5.getMail());
					Map dataMap1 = new HashMap();
					sendService.sendMailSimple(mail2, dataMap1, user3);
					contents = "<div style='font-size:15px;'><strong>[법인카드 승인 요청-검토완료]</strong><br/><br/>1) 검토자:"+user3.getUserName()+"<br/><br/>2) 검토일:"+ctypeconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>";
					contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state1.getDmbtr())+"</td></tr>";
				}else{
					contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getBelnr()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state1.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+df.format(state1.getDmbtr())+"</td></tr>";
				}
				tempEapprnam2 = state1.getEapprnam2();
			}
			contents = contents+"</table></div>";
			User user5 = new User();
			user5 = this.userService.read(tempEapprnam2.toLowerCase());
			
			mail2.setContent(contents);
			mail2.setToEmail(user5.getMail());
			Map dataMap1 = new HashMap();
			sendService.sendMailSimple(mail2, dataMap1, user3);
			
		}
		
		
		if(reversecomplete.equals("S")){//승인or반려 성공시 요청자에게 메일링
			String contents = "";
			String receiverUser = "";
			int tmpCnt = 0;
			int tmpAllCnt = 0;
			User user1 = this.userService.read(revssender);
			User user2 = new User();
			Mail mail = new Mail();
			mail.setMailType(MailConstant.MAIL_TYPE_HTML);
			HashMap<String, String> recip= new HashMap<String, String>();
			if(revsconfirmDiv.equals("S")){//승인시 요청자에게 메일링
				mail.setTitle("법인카드 역분개 전표 승인");
				for (StatementDetail state : revsmailList1){//승인내역 리스트 반복
					if(!receiverUser.equals(state.getReqnam()) && receiverUser != ""){//승인요청자가 바뀔때 이전 내용 메일링
						contents = "<div style='font-size:15px;'><strong>[법인카드 역분개 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+revsconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
						mail.setContent(contents);
						List recipients = new ArrayList();
						user2 = this.userService.read(receiverUser);
						recip.put("email", user2.getMail());
						recipients.add(recip);
						if(recipients.size() > 0) {
							mail.setToEmailList(recipients);
							Map dataMap = new HashMap();
							sendService.sendMailNotice(mail, dataMap, user1);
						}
						tmpCnt = 1;
						contents = "";
						receiverUser = state.getReqnam();
						contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnrrd()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>-"+df.format(state.getDmbtr())+"</td></tr>";
						tmpAllCnt++;
						if(tmpAllCnt == revsmailList1.size()){//승인내역 리스트 마지막일때 메일링
							contents = "<div style='font-size:15px;'><strong>[법인카드 역분개 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+revsconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients1 = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients1.add(recip);
							if(recipients1.size() > 0) {
								mail.setToEmailList(recipients1);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}
					}else{
						tmpAllCnt++;
						tmpCnt++;
						if(tmpAllCnt == revsmailList1.size()){//승인내역 리스트 마지막일때 메일링
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnrrd()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>-"+df.format(state.getDmbtr())+"</td></tr>";
							contents = "<div style='font-size:15px;'><strong>[법인카드 역분개 전표 승인]</strong><br/><br/>1) 승인 건수:"+tmpCnt+"건<br/><br/>2) 승인자:"+user1.getUserName()+"<br/><br/>3) 승인일:"+revsconfirmDate+"<br/><br/>[상세내역]<br/><table style='WIDTH: 700px; BORDER-COLLAPSE: collapse'><tr><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>카드사용일자</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>전표번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>법인카드번호</td><td style='background-color:#E0FFFF;font-weight:bolder;text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>금액</td></tr>"+contents+"</table></div>";
							mail.setContent(contents);
							List recipients = new ArrayList();
							user2 = this.userService.read(state.getReqnam());
							recip.put("email", user2.getMail());
							recipients.add(recip);
							if(recipients.size() > 0) {
								mail.setToEmailList(recipients);
								Map dataMap = new HashMap();
								sendService.sendMailNotice(mail, dataMap, user1);
							}
						}else{//승인요청자가 동일할때 내용 합치기
							contents = contents+"<tr><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getConfdate()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getBelnrrd()+"</td><td style='text-align:center;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>"+state.getCardno()+"</td><td style='text-align:right;BORDER-BOTTOM: #000000 1pt solid; BORDER-LEFT: #000000 1pt solid; BORDER-TOP: #000000 1pt solid; BORDER-RIGHT: #000000 1pt solid'>-"+df.format(state.getDmbtr())+"</td></tr>";
							receiverUser = state.getReqnam();
						}
					}
				}
			}
		}
	}
	
	
/** 예산 계획/실적 리스트 조회*/	
public List<StatementDetail> EpStatementBudgetListDataRcvRFC(StatementDetail statementDetail, HttpServletRequest request )  {
		Connection connect = null;
    	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
    		
		try {
			
		connect = new Connection(request);	

		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_BUDGET");

		function.getImportParameterList().setValue("I_M100", statementDetail.getIm100()); 
		function.getImportParameterList().setValue("I_S100", statementDetail.getIs100());
		function.getImportParameterList().setValue("I_P100", statementDetail.getIp100());
		function.getImportParameterList().setValue("I_FSPMON", statementDetail.getIfspmon());				//검색 시작월
		function.getImportParameterList().setValue("I_TSPMON", statementDetail.getItspmon());				//검색 종료월
		function.getImportParameterList().setValue("I_RACCT", statementDetail.getIracct());					//계정과목
		function.getImportParameterList().setValue("I_UNAME", statementDetail.getIuname());					//사용자 마스터 레코드의 사용자 이름
		function.getImportParameterList().setValue("I_NEONET_ACC", statementDetail.getIneonetacc());		//네오넷 주요 예산계정
		
		connect.executeSAP(function);

		if (function.getTableParameterList() != null) {
        	JCoTable tb = function.getTableParameterList().getTable("IT_DATA");
        	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
        	{
    			StatementDetail statementDetailData = new StatementDetail();
    			
    			statementDetailData.setIbukrs(tb.getString("BUKRS"));
    			statementDetailData.setVersi(tb.getString("VERSI"));
    			statementDetailData.setKostl(tb.getString("KOSTL"));
    			statementDetailData.setKtext(tb.getString("KTEXT"));
    			statementDetailData.setKostla(tb.getString("KOSTLA"));
    			statementDetailData.setKtexta(tb.getString("KTEXTA"));
    			statementDetailData.setKstar(tb.getString("KSTAR"));
    			statementDetailData.setLtext(tb.getString("LTEXT"));
    			statementDetailData.setZcmon(tb.getInt("ZCMON"));
    			statementDetailData.setPlnors0(tb.getInt("PLNOR_S0"));
    			statementDetailData.setPlnchs0(tb.getInt("PLNCH_S0"));
    			statementDetailData.setPlnorr1(tb.getInt("PLNOR_R1"));
    			statementDetailData.setPlnchr1(tb.getInt("PLNCH_R1"));
    			statementDetailData.setPlncu(tb.getInt("PLNCU"));
    			statementDetailData.setPlche(tb.getInt("PLCHE"));
    			statementDetailData.setPlchec(tb.getInt("PLCHE_C"));
    			statementDetailData.setActcu(tb.getInt("ACTCU"));
    			statementDetailData.setBalcu(tb.getInt("BALCU"));
    			statementDetailData.setWaers(tb.getString("WAERS"));
    			statementDetailData.setBapct(tb.getString("BAPCT"));
    			statementDetailData.setDescript(tb.getString("DESCRIPT"));
    			
    			//화면에서의 시작년도와 받아온 월을 조합해서 YYYYMM 6자리를 만든다.
    			statementDetailData.setIspmon(statementDetail.getIfspmon().substring(0, 4)
    					+tb.getString("ZCMON").substring(1,3));
    			
    			
    			statementDetailList.add(statementDetailData);
        	}
        	
        	//log.debug(userDetailData);
		}
		//log.debug("");			
	

		} catch (JCoException e) {

			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{

		}
		
		return statementDetailList;
    }
/**
 * 계정과목 조회
 */
public List<StatementDetail> EpStatementKstarListDataRcvRFC(StatementDetail statementDetail, HttpServletRequest request )  {
	Connection connect = null;
		
	List<StatementDetail> kstarList = new ArrayList<StatementDetail>();
	
	int kstarCnt = 0;
	int kstarFlag = 0;
	try {
		
	connect = new Connection(request);	

	JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_BUDGET");

	function.getImportParameterList().setValue("I_M100", statementDetail.getIm100()); 
	function.getImportParameterList().setValue("I_S100", statementDetail.getIs100());
	function.getImportParameterList().setValue("I_P100", statementDetail.getIp100());
	function.getImportParameterList().setValue("I_FSPMON", statementDetail.getIfspmon());				//검색 시작월
	function.getImportParameterList().setValue("I_TSPMON", statementDetail.getItspmon());				//검색 종료월
	function.getImportParameterList().setValue("I_RACCT", "");					//계정과목
	function.getImportParameterList().setValue("I_UNAME", statementDetail.getIuname());					//사용자 마스터 레코드의 사용자 이름
	function.getImportParameterList().setValue("I_NEONET_ACC", statementDetail.getIneonetacc());		//네오넷 주요 예산계정
	
	connect.executeSAP(function);

	if (function.getTableParameterList() != null) {
    	JCoTable tb = function.getTableParameterList().getTable("IT_DATA");
    	
    	String [] kstarCodes = new String[tb.getNumRows()];	
    	if(tb.getNumRows() > 0){
    		kstarCodes[0] = "";
    	}
    	
    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
    	{
			StatementDetail statementDetailData = new StatementDetail();
			
			statementDetailData.setIbukrs(tb.getString("BUKRS"));
			statementDetailData.setVersi(tb.getString("VERSI"));
			statementDetailData.setKostl(tb.getString("KOSTL"));
			statementDetailData.setKtext(tb.getString("KTEXT"));
			statementDetailData.setKostla(tb.getString("KOSTLA"));
			statementDetailData.setKtexta(tb.getString("KTEXTA"));
			statementDetailData.setKstar(tb.getString("KSTAR"));
			statementDetailData.setLtext(tb.getString("LTEXT"));
			statementDetailData.setZcmon(tb.getInt("ZCMON"));
			statementDetailData.setPlnors0(tb.getInt("PLNOR_S0"));
			statementDetailData.setPlnchs0(tb.getInt("PLNCH_S0"));
			statementDetailData.setPlnorr1(tb.getInt("PLNOR_R1"));
			statementDetailData.setPlnchr1(tb.getInt("PLNCH_R1"));
			statementDetailData.setPlncu(tb.getInt("PLNCU"));
			statementDetailData.setPlche(tb.getInt("PLCHE"));
			statementDetailData.setPlchec(tb.getInt("PLCHE_C"));
			statementDetailData.setActcu(tb.getInt("ACTCU"));
			statementDetailData.setBalcu(tb.getInt("BALCU"));
			statementDetailData.setWaers(tb.getString("WAERS"));
			statementDetailData.setBapct(tb.getString("BAPCT"));
			statementDetailData.setDescript(tb.getString("DESCRIPT"));
			statementDetailData.setIspmon(statementDetail.getIfspmon().substring(0, 4)
					+tb.getString("ZCMON").substring(1,3));
			
			
			kstarFlag = 0;
			if(kstarCodes[0] == ""){
				kstarCodes[0] = tb.getString("KSTAR");
				kstarCnt++;
				kstarList.add(statementDetailData);
			}else{
				for(int iii=0;iii<kstarCnt;iii++){
					if(kstarCodes[iii].equals(tb.getString("KSTAR"))){
						kstarFlag = 1;
					}
				}
				if(kstarFlag < 1){
					kstarCodes[kstarCnt] = tb.getString("KSTAR");
					kstarList.add(statementDetailData);
					kstarCnt++;
				}
			}
    	}
    	
    	//log.debug(userDetailData);
	}
	//log.debug("");			


	} catch (JCoException e) {

		log.debug("e.getMessage():"+e.getMessage());		
		log.debug("e.getGroup():"+e.getGroup());
		log.debug("e.getKey():"+e.getKey());
		e.printStackTrace();
	}finally{

	}
	
	return kstarList;
}
/**
 * 예산 내역 조회(승인요청화면)
 */
public Map<String, Object> EpStatementBudgetSimpleListDataRcvRFC(StatementDetail statementDetail, HttpServletRequest request )  {
    
	Connection connect = null;
	Map<String, Object> item = new HashMap<String, Object>();
	List<StatementDetail> statementDetailList1 = new ArrayList<StatementDetail>();
	List<StatementDetail> statementDetailList2 = new ArrayList<StatementDetail>();
	List<StatementDetail> statementDetailList3 = new ArrayList<StatementDetail>();
	List<StatementDetail> costCenterList = new ArrayList<StatementDetail>();
	String ikostl = statementDetail.getIkostl();
	String costCenterCode = "";
	String [] costCenterCodes = new String[30];	
	costCenterCodes[0] = "";
	int costCneterCnt = 0;
	int costCneterFlag = 0;
	try {
		
	connect = new Connection(request);	

	JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_BUDGET");

	function.getImportParameterList().setValue("I_M100", statementDetail.getIm100()); 
	function.getImportParameterList().setValue("I_S100", statementDetail.getIs100());
	function.getImportParameterList().setValue("I_P100", statementDetail.getIp100());
	function.getImportParameterList().setValue("I_FSPMON", statementDetail.getIfspmon());				//검색 시작월
	function.getImportParameterList().setValue("I_TSPMON", statementDetail.getItspmon());				//검색 종료월
	function.getImportParameterList().setValue("I_RACCT", statementDetail.getIracct());					//계정과목
	function.getImportParameterList().setValue("I_UNAME", statementDetail.getIuname());					//사용자 마스터 레코드의 사용자 이름
	function.getImportParameterList().setValue("I_NEONET_ACC", statementDetail.getIneonetacc());		//네오넷 주요 예산계정
	
	connect.executeSAP(function);

	if (function.getTableParameterList() != null) {
    	JCoTable tb = function.getTableParameterList().getTable("IT_DATA");
    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
    	{
			StatementDetail statementDetailData = new StatementDetail();
			
			statementDetailData.setIbukrs(tb.getString("BUKRS"));
			statementDetailData.setVersi(tb.getString("VERSI"));
			if(!tb.getString("KOSTL").equals("")){
				statementDetailData.setKostl(tb.getString("KOSTL").substring(1));
			}
			statementDetailData.setKtext(tb.getString("KTEXT"));
			statementDetailData.setKostla(tb.getString("KOSTLA"));
			statementDetailData.setKtexta(tb.getString("KTEXTA"));
			statementDetailData.setKstar(tb.getString("KSTAR"));
			statementDetailData.setLtext(tb.getString("LTEXT"));
			statementDetailData.setZcmon(tb.getInt("ZCMON"));
			statementDetailData.setPlnors0(tb.getInt("PLNOR_S0"));
			statementDetailData.setPlnchs0(tb.getInt("PLNCH_S0"));
			statementDetailData.setPlnorr1(tb.getInt("PLNOR_R1"));
			statementDetailData.setPlnchr1(tb.getInt("PLNCH_R1"));
			statementDetailData.setPlncu(tb.getInt("PLNCU"));
			statementDetailData.setPlche(tb.getInt("PLCHE"));
			statementDetailData.setPlchec(tb.getInt("PLCHE_C"));
			statementDetailData.setActcu(tb.getInt("ACTCU"));
			statementDetailData.setBalcu(tb.getInt("BALCU"));
			statementDetailData.setWaers(tb.getString("WAERS"));
			statementDetailData.setBapct(tb.getString("BAPCT"));
			statementDetailData.setDescript(tb.getString("DESCRIPT"));
			
			//화면에서의 시작년도와 받아온 월을 조합해서 YYYYMM 6자리를 만든다.
			statementDetailData.setIspmon(statementDetail.getIfspmon().substring(0, 4)
					+tb.getString("ZCMON").substring(1,3));
			
			if(tb.getString("BUKRS").equals("M100")){
				if(ikostl != null && !ikostl.equals("")){
					if(ikostl.equals(tb.getString("KOSTL").substring(1))){
						statementDetailList1.add(statementDetailData);
					}
				}else{
					statementDetailList1.add(statementDetailData);
				}
			}else if(tb.getString("BUKRS").equals("S100")){
				if(ikostl != null && !ikostl.equals("")){
					if(ikostl.equals(tb.getString("KOSTL").substring(1))){
						statementDetailList2.add(statementDetailData);
					}
				}else{
					statementDetailList2.add(statementDetailData);
				}
			}else{
				if(ikostl != null && !ikostl.equals("")){
					if(ikostl.equals(tb.getString("KOSTL").substring(1))){
						statementDetailList3.add(statementDetailData);
					}
				}else{
					statementDetailList3.add(statementDetailData);
				}
			}
			
			if(!tb.getString("KOSTL").equals("")){
				costCneterFlag = 0;
				if(costCenterCodes[0] == ""){
					costCenterCodes[0] = tb.getString("KOSTL").substring(1);
					costCneterCnt++;
					costCenterList.add(statementDetailData);
				}else{
					for(int iii=0;iii<costCneterCnt;iii++){
						if(costCenterCodes[iii].equals(tb.getString("KOSTL").substring(1))){
							costCneterFlag = 1;
						}
					}
					if(costCneterFlag < 1){
						costCenterCodes[costCneterCnt] = tb.getString("KOSTL").substring(1);
						costCenterList.add(statementDetailData);
						costCneterCnt++;
					}
				}
			}
    	}
    	item.put("statementDetailList1", statementDetailList1);
    	item.put("statementDetailList2", statementDetailList2);
    	item.put("statementDetailList3", statementDetailList3);
    	item.put("costCenterList", costCenterList);
	}


	} catch (JCoException e) {

		log.debug("e.getMessage():"+e.getMessage());		
		log.debug("e.getGroup():"+e.getGroup());
		log.debug("e.getKey():"+e.getKey());
		e.printStackTrace();
	}finally{

	}
	
	return item;
}

/** 예산 계획/실적 조회 상세화 */
public List<StatementDetail> EpStatementBudgetViewDataRcvRFC(StatementDetail statementDetail, HttpServletRequest request )  {
    
		Connection connect = null;
		List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
						
		try {
			
		connect = new Connection(request);	
	
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_BUDGET_DETAIL");
	
		function.getImportParameterList().setValue("I_BUKRS", statementDetail.getIbukrs()); 
		function.getImportParameterList().setValue("I_SPMON", statementDetail.getIspmon());
		function.getImportParameterList().setValue("I_RACCT", statementDetail.getIracct());
		function.getImportParameterList().setValue("I_RZZKOSTL", statementDetail.getIrzzkostl());	
		function.getImportParameterList().setValue("I_RCNTR", statementDetail.getIrcntr());				
		
		connect.executeSAP(function);
	
		if (function.getTableParameterList() != null) {
	    	JCoTable tb = function.getTableParameterList().getTable("T_DATA");
	    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
	    	{
				StatementDetail statementDetailData = new StatementDetail();
				
				statementDetailData.setPoper(tb.getInt("POPER"));       
				statementDetailData.setDocnr(tb.getString("DOCNR"));        
				statementDetailData.setDocct(tb.getString("DOCCT"));        
				statementDetailData.setRcntr(tb.getString("RCNTR"));        
				statementDetailData.setRacct(tb.getString("RACCT"));      
				statementDetailData.setHsl(tb.getInt("HSL"));      
				statementDetailData.setRzzkostl(tb.getString("RZZKOSTL"));        
				statementDetailData.setRtcur(tb.getString("RTCUR"));        
				statementDetailData.setBudat(tb.getString("BUDAT"));
				statementDetailData.setSgtxt(tb.getString("SGTXT"));
				statementDetailData.setUsnam(tb.getString("USNAM"));
				statementDetailData.setRacctt(tb.getString("RACCT_T"));
				statementDetailData.setRcntrt(tb.getString("RCNTR_T"));
				statementDetailData.setRzkostlt(tb.getString("RZKOSTL_T"));
				statementDetailData.setUsnamt(tb.getString("USNAM_T"));
				statementDetailData.setZuonr(tb.getString("ZUONR"));
				statementDetailData.setZterm(tb.getString("ZTERM"));
				
				statementDetailList.add(statementDetailData);
	    	}
    	//log.debug(userDetailData);
		}
		//log.debug("");			
	
	
		} catch (JCoException e) {
	
			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{
	
		}
		
		return statementDetailList;
	}

/** 위임자 조회 */
public List<StatementDetail> EpStatementMandateDataRcvRFC(StatementDetail statementDetail, HttpServletRequest request )  {
    
	Connection connect = null;
	List<StatementDetail> statementDetailList = new ArrayList<StatementDetail>();
					
	try {
		
		connect = new Connection(request);	
	
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_APPR_LIST");
	
		function.getImportParameterList().setValue("I_UNAME", statementDetail.getIuname()); 
		function.getImportParameterList().setValue("I_UNAME2", statementDetail.getIuname2());
		function.getImportParameterList().setValue("I_REQ_GUBUN", statementDetail.getIreqgubun());
		function.getImportParameterList().setValue("I_ZAPP", statementDetail.getZapp());
		
		connect.executeSAP(function);
	
		if (function.getTableParameterList() != null) {
	    	JCoTable tb = function.getTableParameterList().getTable("IT_APPR");
	    	for(int i=0;i<tb.getNumRows();i++, tb.nextRow()) 
	    	{
				StatementDetail statementDetailData = new StatementDetail();
				
				statementDetailData.setUname(tb.getString("UNAME"));       
				statementDetailData.setBukrs(tb.getString("BUKRS"));        
				statementDetailData.setKostl(tb.getString("KOSTL"));        
				statementDetailData.setKostlt(tb.getString("KOSTL_T"));        
				statementDetailData.setUname2date(tb.getString("UNAME2_DATE"));      

				statementDetailList.add(statementDetailData);
	    	}
		//log.debug(userDetailData);
		}else{
			statementDetailList = null;
		}
		//log.debug("");			
	
	
		} catch (JCoException e) {
	
			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{
	
		}
	
		return statementDetailList;
	}
	
/**
 * 위임
 */
public void EpStatementMandateEditDataRcvRFC(StatementDetail statement ,HttpServletRequest request )  {
	
		Connection connect = null;	
		connect = new Connection(request);	
		JCoFunction function = connect.getSAPFunction("ZFB_IF_EP_APPR_UPDATE");
		function.getImportParameterList().setValue("I_UNAME", statement.getIuname());
		function.getImportParameterList().setValue("I_UNAME2", statement.getIuname2());
		function.getImportParameterList().setValue("I_GUBUN", statement.getIreqgubun());
		
		try {
			connect.executeSAP(function);	
		}catch (JCoException e) {
			
			log.debug("e.getMessage():"+e.getMessage());		
			log.debug("e.getGroup():"+e.getGroup());
			log.debug("e.getKey():"+e.getKey());
			e.printStackTrace();
		}finally{
	
		}
	
	}
}

