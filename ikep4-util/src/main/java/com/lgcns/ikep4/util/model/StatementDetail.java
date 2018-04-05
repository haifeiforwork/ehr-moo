
package com.lgcns.ikep4.util.model;

import java.util.Date;

import com.lgcns.ikep4.framework.core.model.BaseObject;

public class StatementDetail extends BaseObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	
	private String ip100;
	private String is100;
	private String im100;
	private String iconffdate;
	private String iconftdate;
	private String iapprfdate;
	private String iapprtdate;
	private String isettlefdate;
	private String isettletdate;
	private String iuname;
	private String ireqgb;
	private String bukrs;
	private String receivedate;
	private String docunumc;
	private String cardgubun;
	private String cardform;
	private String cardtype;
	private String cardno;
	private String settledate;
	private String confno;
	private String confdate;
	private String conftime;
	private String ordConftime;
	private int dmbtr;
	private String wrbtr;
	private int taxamt;
	private int seramt;
	private String stcd2;
	private String name;
	private String krrepres;
	private String krbustype;
	private String krindtype;
	private String krtaxoff;
	private String add1;
	private String add2;
	private String tel;
	private String gubun;
	private String ztaxtype;
	private String linkyn;
	private String zsendtext;
	private String zsend;
	private String zreceive;
	private String zreceivetext;
	private String reqnam;
	private String reqdate;
	private String apprnam;
	private String apprdate;
	private String apprdater;
	private int pageCount;
	private int pageIndex;
	private int pagePerRecord;
	private int recordCount;
	private int endRowIndex;
	private int currentCount;
	private boolean emptyRecord;
	private int startRowIndex;
	
	private String eapprnam;
	private String eapprnam2;
	
	
	public String getApprdater() {
		return apprdater;
	}
	public void setApprdater(String apprdater) {
		this.apprdater = apprdater;
	}
	private String pernr;
	private String zbelnryn;
	private String belnr;
	private String belnrr;
	private String belnrrd;
	private String belnr2rd;
	public String getBelnrrd() {
		return belnrrd;
	}
	public void setBelnrrd(String belnrrd) {
		this.belnrrd = belnrrd;
	}
	public String getBelnr2rd() {
		return belnr2rd;
	}
	public void setBelnr2rd(String belnr2rd) {
		this.belnr2rd = belnr2rd;
	}
	private String gjahr;
	private String belnr2;
	private String belnr2r;
	private String belnrsb;
	private String belnr2sb;
	private String cardgubunt;
	private String cardformt;
	private String cardtypet;
	private String ztaxtypet;
	private String reqnamt;
	private String apprnamt;
	private String gubunt;
	private String hkontt;
	private String bukrst;
	private int dmbtrd;
	private int dmbtrds;
	private String ireqs;
	private String ireqr;
	private String ireqd;
	private String ireqn;
	private String ireqt;
	private String zreceivenamt;
	private String apprkostl;
	
	private String ibukrs;
	private String igjahr;
	private String ibelnr;
	private String buzei;
	private String blart;
	private String bktxt;
	private String budat;
	private String bldat;
	private String cpudt;
	private String hkont;
	private String zztxt50;
	private String mwskz;
	private String zuonr;
	private String sgtxt;
	private String kostl;
	private String ikostl;
	private String zzkostl;
	private String zzkostlt;
	private String shkzg;
	private int dmbtrs;
	private int dmbtrh;
	private String gubseqt;
	private String apprtype;
	private String reviewnam;
	private String reviewnamt;
	
	private String ifname;
	private String istate;
	private String zapp;
	private String revs;
	private String zrevstext;
	
	private String sessionUser;
	
	public String getZrevstext() {
		return zrevstext;
	}
	public void setZrevstext(String zrevstext) {
		this.zrevstext = zrevstext;
	}
	private String izreceivegb;
	
	public String getIzreceivegb() {
		return izreceivegb;
	}
	public void setIzreceivegb(String izreceivegb) {
		this.izreceivegb = izreceivegb;
	}
	public String getRevs() {
		return revs;
	}
	public void setRevs(String revs) {
		this.revs = revs;
	}
	private String zterm;
	
	public String getZterm() {
		return zterm;
	}
	public void setZterm(String zterm) {
		this.zterm = zterm;
	}
	/** 시작년월 */
	private String ifspmon;
	
	/** 종료년월 */
	private String itspmon;
	
	/** 계정과목 */
	private String iracct;
	
	/** 네오넷 주요 예산계정 */
	private String ineonetacc;
	
	/** 버전 */
	private String versi;
	
	/** 요청 코스트센터명 */
	private String ktext;
	
	/** 발생 코스트 센터 */
	private String kostla;
	
	/** 발생 코스트 센터명 */
	private String ktexta;
	
	/** 원가요소 */
	private String kstar;
	
	/** 내역 */
	private String ltext;
	
	/** 예산 변경 대상월 */
	private int zcmon;
	
	/** 예산 계획금액(S0) */
	private int plnors0;
	
	/** 예산 조정금액(S0) */
	private int plnchs0;
	
	/** 예산 계획금액(R1) */
	private int plnorr1;
	
	/** 예산 조정금액(R1) */
	private int plnchr1;
	
	/** 예산 최종금액 */
	private int plncu;
	
	/** 계획변경금액 */
	private int plche;
	
	/** 추가금액(C3) */
	private int plchec;
	
	/** 실적금액 */
	private int actcu;
	
	/** 잔액 */
	private int balcu;
	
	/** 통화 키 */
	private String waers;
	
	/** 예산대비 실적 Percentage */
	private String bapct;
	
	/** 이름 설정 */
	private String setname;
	
	/** 세트의 내역 */
	private String descript;

	/** 시작년월 */
	private String ispmon;
	
	/** 요청부서  */
	private String irzzkostl;
	
	/** 발생부서 */
	private String ircntr;
	
	/** 전기기간 */
	private int poper;
	
	/** 전표 번호 */
	private String docnr;
	
	/** 전표 유형 */
	private String docct;
	
	/** 코스트 센터 */
	private String rcntr;
	
	/** 계정 번호 */
	private String racct;
	
	/** 금액 */
	private int hsl;
	
	/** 요청부서 */
	private String rzzkostl;
	
	/** 통화 키 */
	private String rtcur;
	
	/** 사용자 이름 */
	private String usnam;
	
	/** 텍스트 */
	private String racctt;
	
	/** 내역 */
	private String rcntrt;
	
	/** 내역 */
	private String rzkostlt;
	
	/** 성 */
	private String usnamt;
	
	/** 위임대상자명 */
	private String rfcUserName;
	
	/** 위임대상자ID */
	private String rfcUserId;
	
	/** 위임대상자SAP ID */
	private String rfcUserSapId;
	
	/** 위임자ID */
	private String iuname2;
	
	/** 요청구분(1:위임자조회,2:위임대상조회) */
	private String ireqgubun;
	
	/** 사용자 이름 */
	private String uname;
	
	/** 코스트센터명 */
	private String kostlt;
	
	/** 위임일자 */
	private String uname2date;
	
	/** 위임자 부서 */
	private String mandatorDept;
	
	/** 권한존재 여부*/
	private String isRight;
	
	/** 권한 아이디 */
	private String roleId;

	
	private String mandatorId;
	private String registerId;
	private String registerName;
	private String portalId;
	private Date registDate;
	private Date startDate;
	private Date endDate;
	private String trusteeId;
	
	
	public String getIp100() {
		return ip100;
	}
	public void setIp100(String ip100) {
		this.ip100 = ip100;
	}
	public String getIs100() {
		return is100;
	}
	public void setIs100(String is100) {
		this.is100 = is100;
	}
	public String getIm100() {
		return im100;
	}
	public void setIm100(String im100) {
		this.im100 = im100;
	}
	public String getIconffdate() {
		return iconffdate;
	}
	public void setIconffdate(String iconffdate) {
		this.iconffdate = iconffdate;
	}
	public String getIconftdate() {
		return iconftdate;
	}
	public void setIconftdate(String iconftdate) {
		this.iconftdate = iconftdate;
	}
	public String getIsettlefdate() {
		return isettlefdate;
	}
	public void setIsettlefdate(String isettlefdate) {
		this.isettlefdate = isettlefdate;
	}
	public String getIsettletdate() {
		return isettletdate;
	}
	public void setIsettletdate(String isettletdate) {
		this.isettletdate = isettletdate;
	}
	public String getIuname() {
		return iuname;
	}
	public void setIuname(String iuname) {
		this.iuname = iuname;
	}
	public String getIreqgb() {
		return ireqgb;
	}
	public void setIreqgb(String ireqgb) {
		this.ireqgb = ireqgb;
	}
	public String getBukrs() {
		return bukrs;
	}
	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}
	public String getReceivedate() {
		return receivedate;
	}
	public void setReceivedate(String receivedate) {
		this.receivedate = receivedate;
	}
	public String getDocunumc() {
		return docunumc;
	}
	public void setDocunumc(String docunumc) {
		this.docunumc = docunumc;
	}
	public String getCardgubun() {
		return cardgubun;
	}
	public void setCardgubun(String cardgubun) {
		this.cardgubun = cardgubun;
	}
	public String getCardform() {
		return cardform;
	}
	public void setCardform(String cardform) {
		this.cardform = cardform;
	}
	public String getCardtype() {
		return cardtype;
	}
	public void setCardtype(String cardtype) {
		this.cardtype = cardtype;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getSettledate() {
		return settledate;
	}
	public void setSettledate(String settledate) {
		this.settledate = settledate;
	}
	public String getConfno() {
		return confno;
	}
	public void setConfno(String confno) {
		this.confno = confno;
	}
	public String getConfdate() {
		return confdate;
	}
	public void setConfdate(String confdate) {
		this.confdate = confdate;
	}
	public String getConftime() {
		return conftime;
	}
	public void setConftime(String conftime) {
		this.conftime = conftime;
	}
	public String getWrbtr() {
		return wrbtr;
	}
	public void setWrbtr(String wrbtr) {
		this.wrbtr = wrbtr;
	}
	public String getStcd2() {
		return stcd2;
	}
	public void setStcd2(String stcd2) {
		this.stcd2 = stcd2;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKrrepres() {
		return krrepres;
	}
	public void setKrrepres(String krrepres) {
		this.krrepres = krrepres;
	}
	public String getKrbustype() {
		return krbustype;
	}
	public void setKrbustype(String krbustype) {
		this.krbustype = krbustype;
	}
	public String getKrindtype() {
		return krindtype;
	}
	public void setKrindtype(String krindtype) {
		this.krindtype = krindtype;
	}
	public String getKrtaxoff() {
		return krtaxoff;
	}
	public void setKrtaxoff(String krtaxoff) {
		this.krtaxoff = krtaxoff;
	}
	public String getAdd1() {
		return add1;
	}
	public void setAdd1(String add1) {
		this.add1 = add1;
	}
	public String getAdd2() {
		return add2;
	}
	public void setAdd2(String add2) {
		this.add2 = add2;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getZtaxtype() {
		return ztaxtype;
	}
	public void setZtaxtype(String ztaxtype) {
		this.ztaxtype = ztaxtype;
	}
	public String getLinkyn() {
		return linkyn;
	}
	public void setLinkyn(String linkyn) {
		this.linkyn = linkyn;
	}
	public String getZsendtext() {
		return zsendtext;
	}
	public void setZsendtext(String zsendtext) {
		this.zsendtext = zsendtext;
	}
	public String getZsend() {
		return zsend;
	}
	public void setZsend(String zsend) {
		this.zsend = zsend;
	}
	public String getZreceive() {
		return zreceive;
	}
	public void setZreceive(String zreceive) {
		this.zreceive = zreceive;
	}
	public String getZreceivetext() {
		return zreceivetext;
	}
	public void setZreceivetext(String zreceivetext) {
		this.zreceivetext = zreceivetext;
	}
	public String getReqnam() {
		return reqnam;
	}
	public void setReqnam(String reqnam) {
		this.reqnam = reqnam;
	}
	public String getReqdate() {
		return reqdate;
	}
	public void setReqdate(String reqdate) {
		this.reqdate = reqdate;
	}
	public String getApprnam() {
		return apprnam;
	}
	public void setApprnam(String apprnam) {
		this.apprnam = apprnam;
	}
	public String getApprdate() {
		return apprdate;
	}
	public void setApprdate(String apprdate) {
		this.apprdate = apprdate;
	}
	public String getPernr() {
		return pernr;
	}
	public void setPernr(String pernr) {
		this.pernr = pernr;
	}
	public String getZbelnryn() {
		return zbelnryn;
	}
	public void setZbelnryn(String zbelnryn) {
		this.zbelnryn = zbelnryn;
	}
	public String getBelnr() {
		return belnr;
	}
	public void setBelnr(String belnr) {
		this.belnr = belnr;
	}
	public String getBelnrr() {
		return belnrr;
	}
	public void setBelnrr(String belnrr) {
		this.belnrr = belnrr;
	}
	public String getGjahr() {
		return gjahr;
	}
	public void setGjahr(String gjahr) {
		this.gjahr = gjahr;
	}
	public String getBelnr2() {
		return belnr2;
	}
	public void setBelnr2(String belnr2) {
		this.belnr2 = belnr2;
	}
	public String getBelnr2r() {
		return belnr2r;
	}
	public void setBelnr2r(String belnr2r) {
		this.belnr2r = belnr2r;
	}
	public String getBelnrsb() {
		return belnrsb;
	}
	public void setBelnrsb(String belnrsb) {
		this.belnrsb = belnrsb;
	}
	public String getBelnr2sb() {
		return belnr2sb;
	}
	public void setBelnr2sb(String belnr2sb) {
		this.belnr2sb = belnr2sb;
	}
	public String getCardgubunt() {
		return cardgubunt;
	}
	public void setCardgubunt(String cardgubunt) {
		this.cardgubunt = cardgubunt;
	}
	public String getCardformt() {
		return cardformt;
	}
	public void setCardformt(String cardformt) {
		this.cardformt = cardformt;
	}
	public String getCardtypet() {
		return cardtypet;
	}
	public void setCardtypet(String cardtypet) {
		this.cardtypet = cardtypet;
	}
	public String getZtaxtypet() {
		return ztaxtypet;
	}
	public void setZtaxtypet(String ztaxtypet) {
		this.ztaxtypet = ztaxtypet;
	}
	public String getReqnamt() {
		return reqnamt;
	}
	public void setReqnamt(String reqnamt) {
		this.reqnamt = reqnamt;
	}
	public String getApprnamt() {
		return apprnamt;
	}
	public void setApprnamt(String apprnamt) {
		this.apprnamt = apprnamt;
	}
	public String getGubunt() {
		return gubunt;
	}
	public void setGubunt(String gubunt) {
		this.gubunt = gubunt;
	}
	public String getIbukrs() {
		return ibukrs;
	}
	public void setIbukrs(String ibukrs) {
		this.ibukrs = ibukrs;
	}
	public String getIgjahr() {
		return igjahr;
	}
	public void setIgjahr(String igjahr) {
		this.igjahr = igjahr;
	}
	public String getIbelnr() {
		return ibelnr;
	}
	public void setIbelnr(String ibelnr) {
		this.ibelnr = ibelnr;
	}
	public String getBuzei() {
		return buzei;
	}
	public void setBuzei(String buzei) {
		this.buzei = buzei;
	}
	public String getBlart() {
		return blart;
	}
	public void setBlart(String blart) {
		this.blart = blart;
	}
	public String getBktxt() {
		return bktxt;
	}
	public void setBktxt(String bktxt) {
		this.bktxt = bktxt;
	}
	public String getBudat() {
		return budat;
	}
	public void setBudat(String budat) {
		this.budat = budat;
	}
	public String getBldat() {
		return bldat;
	}
	public void setBldat(String bldat) {
		this.bldat = bldat;
	}
	public String getCpudt() {
		return cpudt;
	}
	public void setCpudt(String cpudt) {
		this.cpudt = cpudt;
	}
	public String getHkont() {
		return hkont;
	}
	public void setHkont(String hkont) {
		this.hkont = hkont;
	}
	public String getZztxt50() {
		return zztxt50;
	}
	public void setZztxt50(String zztxt50) {
		this.zztxt50 = zztxt50;
	}
	public String getMwskz() {
		return mwskz;
	}
	public void setMwskz(String mwskz) {
		this.mwskz = mwskz;
	}
	public String getZuonr() {
		return zuonr;
	}
	public void setZuonr(String zuonr) {
		this.zuonr = zuonr;
	}
	public String getSgtxt() {
		return sgtxt;
	}
	public void setSgtxt(String sgtxt) {
		this.sgtxt = sgtxt;
	}
	public String getKostl() {
		return kostl;
	}
	public void setKostl(String kostl) {
		this.kostl = kostl;
	}
	public String getZzkostl() {
		return zzkostl;
	}
	public void setZzkostl(String zzkostl) {
		this.zzkostl = zzkostl;
	}
	public String getHkontt() {
		return hkontt;
	}
	public void setHkontt(String hkontt) {
		this.hkontt = hkontt;
	}
	public String getShkzg() {
		return shkzg;
	}
	public void setShkzg(String shkzg) {
		this.shkzg = shkzg;
	}
	public String getBukrst() {
		return bukrst;
	}
	public void setBukrst(String bukrst) {
		this.bukrst = bukrst;
	}
	public int getDmbtrs() {
		return dmbtrs;
	}
	public void setDmbtrs(int dmbtrs) {
		this.dmbtrs = dmbtrs;
	}
	public int getDmbtrh() {
		return dmbtrh;
	}
	public void setDmbtrh(int dmbtrh) {
		this.dmbtrh = dmbtrh;
	}
	public int getDmbtrds() {
		return dmbtrds;
	}
	public void setDmbtrds(int dmbtrds) {
		this.dmbtrds = dmbtrds;
	}
	public String getIreqs() {
		return ireqs;
	}
	public void setIreqs(String ireqs) {
		this.ireqs = ireqs;
	}
	public String getIreqr() {
		return ireqr;
	}
	public void setIreqr(String ireqr) {
		this.ireqr = ireqr;
	}
	public String getIreqd() {
		return ireqd;
	}
	public void setIreqd(String ireqd) {
		this.ireqd = ireqd;
	}
	public String getIreqn() {
		return ireqn;
	}
	public void setIreqn(String ireqn) {
		this.ireqn = ireqn;
	}
	public int getDmbtr() {
		return dmbtr;
	}
	public void setDmbtr(int dmbtr) {
		this.dmbtr = dmbtr;
	}
	public int getTaxamt() {
		return taxamt;
	}
	public void setTaxamt(int taxamt) {
		this.taxamt = taxamt;
	}
	public int getSeramt() {
		return seramt;
	}
	public void setSeramt(int seramt) {
		this.seramt = seramt;
	}
	public int getDmbtrd() {
		return dmbtrd;
	}
	public void setDmbtrd(int dmbtrd) {
		this.dmbtrd = dmbtrd;
	}
	public String getIfspmon() {
		return ifspmon;
	}
	public void setIfspmon(String ifspmon) {
		this.ifspmon = ifspmon;
	}
	public String getItspmon() {
		return itspmon;
	}
	public void setItspmon(String itspmon) {
		this.itspmon = itspmon;
	}
	public String getIracct() {
		return iracct;
	}
	public void setIracct(String iracct) {
		this.iracct = iracct;
	}
	public String getIneonetacc() {
		return ineonetacc;
	}
	public void setIneonetacc(String ineonetacc) {
		this.ineonetacc = ineonetacc;
	}
	public String getVersi() {
		return versi;
	}
	public void setVersi(String versi) {
		this.versi = versi;
	}
	public String getKtext() {
		return ktext;
	}
	public void setKtext(String ktext) {
		this.ktext = ktext;
	}
	public String getKostla() {
		return kostla;
	}
	public void setKostla(String kostla) {
		this.kostla = kostla;
	}
	public String getKtexta() {
		return ktexta;
	}
	public void setKtexta(String ktexta) {
		this.ktexta = ktexta;
	}
	public String getKstar() {
		return kstar;
	}
	public void setKstar(String kstar) {
		this.kstar = kstar;
	}
	public String getLtext() {
		return ltext;
	}
	public void setLtext(String ltext) {
		this.ltext = ltext;
	}
	
	public int getPlnors0() {
		return plnors0;
	}
	public void setPlnors0(int plnors0) {
		this.plnors0 = plnors0;
	}
	public int getPlnchs0() {
		return plnchs0;
	}
	public void setPlnchs0(int plnchs0) {
		this.plnchs0 = plnchs0;
	}
	public int getPlnorr1() {
		return plnorr1;
	}
	public void setPlnorr1(int plnorr1) {
		this.plnorr1 = plnorr1;
	}
	public int getPlnchr1() {
		return plnchr1;
	}
	public void setPlnchr1(int plnchr1) {
		this.plnchr1 = plnchr1;
	}
	public int getPlncu() {
		return plncu;
	}
	public void setPlncu(int plncu) {
		this.plncu = plncu;
	}
	public int getPlche() {
		return plche;
	}
	public void setPlche(int plche) {
		this.plche = plche;
	}
	public int getPlchec() {
		return plchec;
	}
	public void setPlchec(int plchec) {
		this.plchec = plchec;
	}
	public int getActcu() {
		return actcu;
	}
	public void setActcu(int actcu) {
		this.actcu = actcu;
	}
	public int getBalcu() {
		return balcu;
	}
	public void setBalcu(int balcu) {
		this.balcu = balcu;
	}
	public String getWaers() {
		return waers;
	}
	public void setWaers(String waers) {
		this.waers = waers;
	}
	public String getBapct() {
		return bapct;
	}
	public void setBapct(String bapct) {
		this.bapct = bapct;
	}
	public String getSetname() {
		return setname;
	}
	public void setSetname(String setname) {
		this.setname = setname;
	}
	public String getDescript() {
		return descript;
	}
	public void setDescript(String descript) {
		this.descript = descript;
	}
	public int getZcmon() {
		return zcmon;
	}
	public void setZcmon(int zcmon) {
		this.zcmon = zcmon;
	}
	public String getIspmon() {
		return ispmon;
	}
	public void setIspmon(String ispmon) {
		this.ispmon = ispmon;
	}
	public String getIrzzkostl() {
		return irzzkostl;
	}
	public void setIrzzkostl(String irzzkostl) {
		this.irzzkostl = irzzkostl;
	}
	public String getIrcntr() {
		return ircntr;
	}
	public void setIrcntr(String ircntr) {
		this.ircntr = ircntr;
	}
	public int getPoper() {
		return poper;
	}
	public void setPoper(int poper) {
		this.poper = poper;
	}
	public String getDocnr() {
		return docnr;
	}
	public void setDocnr(String docnr) {
		this.docnr = docnr;
	}
	public String getDocct() {
		return docct;
	}
	public void setDocct(String docct) {
		this.docct = docct;
	}
	public String getRcntr() {
		return rcntr;
	}
	public void setRcntr(String rcntr) {
		this.rcntr = rcntr;
	}
	public String getRacct() {
		return racct;
	}
	public void setRacct(String racct) {
		this.racct = racct;
	}
	public int getHsl() {
		return hsl;
	}
	public void setHsl(int hsl) {
		this.hsl = hsl;
	}
	public String getRzzkostl() {
		return rzzkostl;
	}
	public void setRzzkostl(String rzzkostl) {
		this.rzzkostl = rzzkostl;
	}
	public String getRtcur() {
		return rtcur;
	}
	public void setRtcur(String rtcur) {
		this.rtcur = rtcur;
	}
	public String getUsnam() {
		return usnam;
	}
	public void setUsnam(String usnam) {
		this.usnam = usnam;
	}
	public String getRacctt() {
		return racctt;
	}
	public void setRacctt(String racctt) {
		this.racctt = racctt;
	}
	public String getRcntrt() {
		return rcntrt;
	}
	public void setRcntrt(String rcntrt) {
		this.rcntrt = rcntrt;
	}
	public String getRzkostlt() {
		return rzkostlt;
	}
	public void setRzkostlt(String rzkostlt) {
		this.rzkostlt = rzkostlt;
	}
	public String getUsnamt() {
		return usnamt;
	}
	public void setUsnamt(String usnamt) {
		this.usnamt = usnamt;
	}
	public String getRfcUserName() {
		return rfcUserName;
	}
	public void setRfcUserName(String rfcUserName) {
		this.rfcUserName = rfcUserName;
	}
	public String getRfcUserId() {
		return rfcUserId;
	}
	public void setRfcUserId(String rfcUserId) {
		this.rfcUserId = rfcUserId;
	}
	public String getRfcUserSapId() {
		return rfcUserSapId;
	}
	public void setRfcUserSapId(String rfcUserSapId) {
		this.rfcUserSapId = rfcUserSapId;
	}
	public String getMandatorId() {
		return mandatorId;
	}
	public void setMandatorId(String mandatorId) {
		this.mandatorId = mandatorId;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getRegisterName() {
		return registerName;
	}
	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}
	public String getPortalId() {
		return portalId;
	}
	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}
	public String getTrusteeId() {
		return trusteeId;
	}
	public void setTrusteeId(String trusteeId) {
		this.trusteeId = trusteeId;
	}
	public Date getRegistDate() {
		return registDate;
	}
	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getIuname2() {
		return iuname2;
	}
	public void setIuname2(String iuname2) {
		this.iuname2 = iuname2;
	}
	public String getIreqgubun() {
		return ireqgubun;
	}
	public void setIreqgubun(String ireqgubun) {
		this.ireqgubun = ireqgubun;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getKostlt() {
		return kostlt;
	}
	public void setKostlt(String kostlt) {
		this.kostlt = kostlt;
	}
	public String getUname2date() {
		return uname2date;
	}
	public void setUname2date(String uname2date) {
		this.uname2date = uname2date;
	}
	public String getMandatorDept() {
		return mandatorDept;
	}
	public void setMandatorDept(String mandatorDept) {
		this.mandatorDept = mandatorDept;
	}
	public String getIreqt() {
		return ireqt;
	}
	public void setIreqt(String ireqt) {
		this.ireqt = ireqt;
	}
	public String getOrdConftime() {
		return ordConftime;
	}
	public void setOrdConftime(String ordConftime) {
		this.ordConftime = ordConftime;
	}
	public String getIfname() {
		return ifname;
	}
	public void setIfname(String ifname) {
		this.ifname = ifname;
	}
	public String getIstate() {
		return istate;
	}
	public void setIstate(String istate) {
		this.istate = istate;
	}
	public String getGubseqt() {
		return gubseqt;
	}
	public void setGubseqt(String gubseqt) {
		this.gubseqt = gubseqt;
	}
	public String getZreceivenamt() {
		return zreceivenamt;
	}
	public void setZreceivenamt(String zreceivenamt) {
		this.zreceivenamt = zreceivenamt;
	}
	public String getIsRight() {
		return isRight;
	}
	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}
	public String getApprtype() {
		return apprtype;
	}
	public void setApprtype(String apprtype) {
		this.apprtype = apprtype;
	}
	public String getReviewnam() {
		return reviewnam;
	}
	public void setReviewnam(String reviewnam) {
		this.reviewnam = reviewnam;
	}
	public String getReviewnamt() {
		return reviewnamt;
	}
	public void setReviewnamt(String reviewnamt) {
		this.reviewnamt = reviewnamt;
	}
	public String getZapp() {
		return zapp;
	}
	public void setZapp(String zapp) {
		this.zapp = zapp;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getIkostl() {
		return ikostl;
	}
	public void setIkostl(String ikostl) {
		this.ikostl = ikostl;
	}
	public String getIapprtdate() {
		return iapprtdate;
	}
	public void setIapprtdate(String iapprtdate) {
		this.iapprtdate = iapprtdate;
	}
	public String getIapprfdate() {
		return iapprfdate;
	}
	public void setIapprfdate(String iapprfdate) {
		this.iapprfdate = iapprfdate;
	}
	public String getZzkostlt() {
		return zzkostlt;
	}
	public void setZzkostlt(String zzkostlt) {
		this.zzkostlt = zzkostlt;
	}
	public String getApprkostl() {
		return apprkostl;
	}
	public void setApprkostl(String apprkostl) {
		this.apprkostl = apprkostl;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPagePerRecord() {
		return pagePerRecord;
	}
	public void setPagePerRecord(int pagePerRecord) {
		this.pagePerRecord = pagePerRecord;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getEndRowIndex() {
		return endRowIndex;
	}
	public void setEndRowIndex(int endRowIndex) {
		this.endRowIndex = endRowIndex;
	}
	public int getCurrentCount() {
		return currentCount;
	}
	public void setCurrentCount(int currentCount) {
		this.currentCount = currentCount;
	}
	public boolean isEmptyRecord() {
		return emptyRecord;
	}
	public void setEmptyRecord(boolean emptyRecord) {
		this.emptyRecord = emptyRecord;
	}
	public int getStartRowIndex() {
		return startRowIndex;
	}
	public void setStartRowIndex(int startRowIndex) {
		this.startRowIndex = startRowIndex;
	}
	public String getSessionUser() {
		return sessionUser;
	}
	public void setSessionUser(String sessionUser) {
		this.sessionUser = sessionUser;
	}
	public String getEapprnam() {
		return eapprnam;
	}
	public void setEapprnam(String eapprnam) {
		this.eapprnam = eapprnam;
	}
	public String getEapprnam2() {
		return eapprnam2;
	}
	public void setEapprnam2(String eapprnam2) {
		this.eapprnam2 = eapprnam2;
	}
	

}
