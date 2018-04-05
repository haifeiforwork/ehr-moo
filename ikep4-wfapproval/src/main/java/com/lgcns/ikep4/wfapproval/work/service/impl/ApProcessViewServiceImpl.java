/* 
 * Copyright (C) 2011 LG CNS Inc.
 * All rights reserved.
 *
 * 모든 권한은 LG CNS(http://www.lgcns.com)에 있으며,
 * LG CNS의 허락없이 소스 및 이진형식으로 재배포, 사용하는 행위를 금지합니다.
 */
package com.lgcns.ikep4.wfapproval.work.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lgcns.ikep4.wfapproval.work.dao.ApProcessViewDao;
import com.lgcns.ikep4.wfapproval.work.model.ApProcessViewData;
import com.lgcns.ikep4.wfapproval.work.service.ApProcessViewService;


/**
 * 임시저장 목록 Service 구현
 * 
 * @author 장규진(mistpoet@paran.com)
 * @version $Id: ApProcessViewServiceImpl.java 16234 2011-08-18 02:44:36Z giljae $
 */
@Service("apProcessViewService")
public class ApProcessViewServiceImpl implements ApProcessViewService {
	
	protected final Log log = LogFactory.getLog(getClass());

	@Autowired
	private ApProcessViewDao apProcessViewDao;

	public String getApProcessXMLData(String apprId) {
		List<ApProcessViewData> apProcessViewDataList = apProcessViewDao.listApProcessViewData(apprId);
		
		StringBuffer sb = new StringBuffer();
		boolean isParallel = false;
		String defXPos = "";
		String defYPos = "";
		
		if(apProcessViewDataList.size() > 0) {
			isParallel = ("P".equals(apProcessViewDataList.get(0).getApprLineType())) ? true : false;
			defXPos = (isParallel) ? "30" : "60";
			defYPos = (isParallel) ? "90" : "0";

			sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> \n");
			sb.append("<data> \n");
			sb.append("  <process_title> 결재 진행 현황 </process_title> \n");
			sb.append("  <refresh_time>0</refresh_time> \n");
			sb.append("  <background_color>16777215,14868962</background_color> \n");
			sb.append("  <lane_texts/> \n");
			sb.append("  <lane_sizes/> \n");
			sb.append("  <lane_position>left</lane_position> \n");
			sb.append("  <line_thickness>1</line_thickness> \n");
			sb.append("  <line_color>5526612</line_color> \n");
			sb.append("  <line_pass_color>10079232</line_pass_color> \n");
			sb.append("  <title_area_color>0xF0F7FF,0xD7EBFF</title_area_color> \n");
			//sb.append("  <activity_texts>activity_id|아이디|,activity_name|액티비티|,creation_date|시작일자|,completion_date|완료일자|,participants_id|참여자 아이디|approveHistory ,status|상태|,activity_lead_time|리드타임|</activity_texts> \n");
			sb.append("  <activity_texts/> \n");
			sb.append("  <domain>approval</domain> \n");
			sb.append("  <process_id>APPROVAL_PROCESS</process_id> \n");
			sb.append("  <revision_tag>1.0</revision_tag> \n");
			sb.append("  <business_key>1</business_key> \n");
			sb.append("  <activities> \n");
			// start
			sb.append("    <activity> \n");
			sb.append("      <id>START</id> \n");    				
			sb.append("      <label>시작</label> \n");
			sb.append("      <x>"+defXPos+"</x> \n");
			sb.append("      <y>"+defYPos+"</y> \n");
			sb.append("      <source>assets/images/icon_activity/icon_s_start.png</source> \n");
			sb.append("      <state/> \n");
			sb.append("      <tooltip/> \n");
			sb.append("      <event/> \n");
			sb.append("      <sub_status/> \n");
			sb.append("      <sub_domain/> \n");
			sb.append("      <sub_process_id/> \n");
			sb.append("      <sub_revision_tag/> \n");
			sb.append("      <sub_business_key/> \n");
			sb.append("      <sub_event/> \n");
			sb.append("      <lines> \n");
			sb.append("        <line> \n");
			sb.append("          <to_id>DRAFT</to_id> \n");
			sb.append("          <line_label/> \n");
			sb.append("          <state>normal</state> \n");
			sb.append("          <line_start_pos>2</line_start_pos> \n");
			sb.append("          <line_end_pos>0</line_end_pos> \n");
			sb.append("          <line_type>1</line_type> \n");
			sb.append("          <line_style>0</line_style> \n");
			sb.append("          <offsets> \n");
			sb.append("            <offset/> \n");
			sb.append("          </offsets> \n");
			sb.append("        </line> \n");
			sb.append("      </lines> \n");
			sb.append("    </activity> \n");
			
			int seq = 1;
    		for(int i=0; i < apProcessViewDataList.size(); i++) {
    			int appOrd = apProcessViewDataList.get(i).getApprOrder();
    			int cApprType = apProcessViewDataList.get(i).getApprType();
    			int nApprType = (i==apProcessViewDataList.size()-1) ? 0 : apProcessViewDataList.get(i+1).getApprType();
    			String id = "";
    			String apprTypeName = "";
    			String curYPos = "";
    			if(i == 0 ) {
    				id = "DRAFT";
    				apprTypeName = "기안작성";
    				curYPos = defYPos;
    			} else {
    				if(cApprType == 1 ) {
    					id = "AGREE" + appOrd;
    					curYPos = (isParallel) ? "150" : "0";
    				} else {
    					id = "APPROVAL" + appOrd;
    					curYPos = (isParallel) ? "30" : "0";
    				}
    				apprTypeName = apProcessViewDataList.get(i).getApprTypeName();
    			}
    			
    			if(isParallel && i > 0 && cApprType != apProcessViewDataList.get(i-1).getApprType()) seq = 3;
    			
    			sb.append("    <activity> \n");
    			sb.append("      <id>"+id+"</id> \n");
    			sb.append("      <label>"+apprTypeName+"/"+apProcessViewDataList.get(i).getApprUserName()+"</label> \n");
    			sb.append("      <x>"+(Integer.parseInt(defXPos)+((seq)*90))+"</x> \n");
    			sb.append("      <y>"+curYPos+"</y> \n");
    			sb.append("      <source>assets/images/bpm_activity/icon_bpm_a_human_P_adapter.png</source> \n");
    			sb.append("      <state/> \n");
    			sb.append("      <tooltip/> \n");
    			sb.append("      <event/> \n");
    			sb.append("      <sub_status/> \n");
    			sb.append("      <sub_domain/> \n");
    			sb.append("      <sub_process_id/> \n");
    			sb.append("      <sub_revision_tag/> \n");
    			sb.append("      <sub_business_key/> \n");
    			sb.append("      <sub_event/> \n");
    			sb.append("      <lines> \n");
    			sb.append("        <line> \n");
    			if(isParallel) { //병렬일때
    				if( i == 0 ) { //첫레코드(기안작성레코드) 일때
    					sb.append("          <to_id>XOR1</to_id> \n");
            			sb.append("          <line_label/> \n");
            			sb.append("          <state>normal</state> \n");
            			sb.append("          <line_start_pos>2</line_start_pos> \n");
            			sb.append("          <line_end_pos>0</line_end_pos> \n");
    				} else if( i == apProcessViewDataList.size()-1 ||
        				       ( i < apProcessViewDataList.size()-1 && cApprType != apProcessViewDataList.get(i+1).getApprType())
        			         ) // 결재의 마지막 레코드 이거나 합의의 마지막 레코드(최종레코드) 일때.
        			{ 
            			sb.append("          <to_id>XOR2</to_id> \n");
            			sb.append("          <line_label/> \n");
            			sb.append("          <state>normal</state> \n");
            			sb.append("          <line_start_pos>2</line_start_pos> \n");
            			if(cApprType == 0 ) // 결재일때는 XOR의 위로..
            				sb.append("          <line_end_pos>1</line_end_pos> \n");
            			else // 합의일때는 XOR의 아래로..
            				sb.append("          <line_end_pos>3</line_end_pos> \n");
        			} else {
            			sb.append("          <to_id>"+(((nApprType==1)?"AGREE":"APPROVAL") + apProcessViewDataList.get(i+1).getApprOrder())+"</to_id> \n");
            			sb.append("          <line_label/> \n");
            			sb.append("          <state>normal</state> \n");
            			sb.append("          <line_start_pos>2</line_start_pos> \n");
            			sb.append("          <line_end_pos>0</line_end_pos> \n");
        			}
    			} else { // 직렬(일반)
        			if( i == apProcessViewDataList.size()-1 ) { // 마지막 레코드
            			sb.append("          <to_id>END1</to_id> \n");    				
        			} else {
            			sb.append("          <to_id>"+(((nApprType==1)?"AGREE":"APPROVAL") + apProcessViewDataList.get(i+1).getApprOrder())+"</to_id> \n");    				
        			}
        			sb.append("          <line_label/> \n");
        			sb.append("          <state>normal</state> \n");
        			sb.append("          <line_start_pos>2</line_start_pos> \n");
        			sb.append("          <line_end_pos>0</line_end_pos> \n");
    			}
    			sb.append("          <line_type>1</line_type> \n");
    			sb.append("          <line_style>0</line_style> \n");
    			sb.append("          <offsets> \n");
    			sb.append("            <offset/> \n");
    			sb.append("          </offsets> \n");
    			sb.append("        </line> \n");
    			sb.append("      </lines> \n");
    			sb.append("    </activity> \n");
    			if(isParallel && i == 0 && apProcessViewDataList.size() > 1) { //병렬일때 첫 레코드 다음에 XOR1 연결
    				seq++;
        			sb.append("    <activity> \n");
        			sb.append("      <id>XOR1</id> \n");    				
        			sb.append("      <label>XOR</label> \n");
        			sb.append("      <x>"+(Integer.parseInt(defXPos)+((seq)*90))+"</x> \n");
        			sb.append("      <y>"+defYPos+"</y> \n");
        			sb.append("      <source>assets/images/flow_activity/icon_xor.png</source> \n");
        			sb.append("      <state/> \n");
        			sb.append("      <tooltip/> \n");
        			sb.append("      <event/> \n");
        			sb.append("      <sub_status/> \n");
        			sb.append("      <sub_domain/> \n");
        			sb.append("      <sub_process_id/> \n");
        			sb.append("      <sub_revision_tag/> \n");
        			sb.append("      <sub_business_key/> \n");
        			sb.append("      <sub_event/> \n");
        			sb.append("      <lines> \n");
        			sb.append("        <line> \n");
        			sb.append("          <to_id>APPROVAL1</to_id> \n");
        			sb.append("          <line_label/> \n");
        			sb.append("          <state>normal</state> \n");
        			sb.append("          <line_start_pos>1</line_start_pos> \n");
        			sb.append("          <line_end_pos>0</line_end_pos> \n");
        			sb.append("          <line_type>1</line_type> \n");
        			sb.append("          <line_style>0</line_style> \n");
        			sb.append("          <offsets> \n");
        			sb.append("            <offset/>\n");
        			sb.append("          </offsets>\n");
        			sb.append("        </line>\n");
        			sb.append("        <line> \n");
        			sb.append("          <to_id>AGREE1</to_id> \n");
        			sb.append("          <line_label/> \n");
        			sb.append("          <state>normal</state> \n");
        			sb.append("          <line_start_pos>3</line_start_pos> \n");
        			sb.append("          <line_end_pos>0</line_end_pos> \n");
        			sb.append("          <line_type>1</line_type> \n");
        			sb.append("          <line_style>0</line_style> \n");
        			sb.append("          <offsets> \n");
        			sb.append("            <offset/>\n");
        			sb.append("          </offsets>\n");
        			sb.append("        </line>\n");
        			sb.append("      </lines>\n");
        			sb.append("    </activity> \n");
    			}
    			seq++;
    		}
    		if(isParallel) { //병렬일때 종료 전 XOR
    			sb.append("    <activity> \n");
    			sb.append("      <id>XOR2</id> \n");    				
    			sb.append("      <label>XOR</label> \n");
    			sb.append("      <x>"+(Integer.parseInt(defXPos)+((seq)*90))+"</x> \n");
    			sb.append("      <y>"+defYPos+"</y> \n");
    			sb.append("      <source>assets/images/flow_activity/icon_xor.png</source> \n");
    			sb.append("      <state/> \n");
    			sb.append("      <tooltip/> \n");
    			sb.append("      <event/> \n");
    			sb.append("      <sub_status/> \n");
    			sb.append("      <sub_domain/> \n");
    			sb.append("      <sub_process_id/> \n");
    			sb.append("      <sub_revision_tag/> \n");
    			sb.append("      <sub_business_key/> \n");
    			sb.append("      <sub_event/> \n");
    			sb.append("      <lines> \n");
    			sb.append("        <line> \n");
    			sb.append("          <to_id>END1</to_id> \n");
    			sb.append("          <line_label/> \n");
    			sb.append("          <state>normal</state> \n");
    			sb.append("          <line_start_pos>2</line_start_pos> \n");
    			sb.append("          <line_end_pos>0</line_end_pos> \n");
    			sb.append("          <line_type>1</line_type> \n");
    			sb.append("          <line_style>0</line_style> \n");
    			sb.append("          <offsets> \n");
    			sb.append("            <offset/>\n");
    			sb.append("          </offsets>\n");
    			sb.append("        </line>\n");
    			sb.append("      </lines>\n");
    			sb.append("    </activity> \n");
    			seq++;
    		}
    		// 종료 노드
			sb.append("    <activity> \n");
			sb.append("      <id>END1</id> \n");    				
			sb.append("      <label>종료</label> \n");
			sb.append("      <x>"+(Integer.parseInt(defXPos)+((seq)*90))+"</x> \n");
			sb.append("      <y>"+defYPos+"</y> \n");
			sb.append("      <source>assets/images/icon_activity/icon_s_end.png</source> \n");
			sb.append("      <state/> \n");
			sb.append("      <tooltip/> \n");
			sb.append("      <event/> \n");
			sb.append("      <sub_status/> \n");
			sb.append("      <sub_domain/> \n");
			sb.append("      <sub_process_id/> \n");
			sb.append("      <sub_revision_tag/> \n");
			sb.append("      <sub_business_key/> \n");
			sb.append("      <sub_event/> \n");
			sb.append("      <lines/> \n");
			sb.append("    </activity> \n");
    		sb.append("  </activities>\n");
    		sb.append("</data> \n");    		
		}
		
		if(log.isDebugEnabled()) {
			log.debug("apProcessViewXMLData : "+sb.toString());
		}
		
		return sb.toString();
	}
	
}
