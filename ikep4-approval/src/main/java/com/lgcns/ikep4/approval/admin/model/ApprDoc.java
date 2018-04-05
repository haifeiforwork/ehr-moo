package com.lgcns.ikep4.approval.admin.model;

import java.util.List;
import java.util.Date;

import org.hibernate.validator.constraints.NotEmpty;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.approval.work.model.ApprLine;
import com.lgcns.ikep4.framework.core.model.BaseObject;

/**
 * TODO Javadoc주석작성
 *
 * @author wonchu
 * @version $Id: FormItem.java 16245 2011-08-18 04:28:59Z giljae $
 */
public class ApprDoc extends BaseObject {

	static final long serialVersionUID = -4049813450878768438L;
	
	/**
	 * PreviewApprDoc
	 */
	public interface PreviewApprDoc {
	} // Preview group을 선언
	
	/**
	 * CreateApprDoc
	 */
	public interface CreateApprDoc {
	} // Create group을 선언
	
	/**
	 * UpdateApprDoc
	 */
	public interface UpdateApprDoc {
	} // Create group을 선언
	
	/**
	 * 문서종류(품의, 보고, 통신…)
	 */
	private String apprDocCd;
	
	/**
	 * 결재문서 식별번호 (사용하는 경우 입력)
	 */
	private String apprDocNo;
	
	/**
	 * 문서상태 (0:임시저장, 1:진행중, 2:완료, 3:반려, 4:회수, 5:오류..)
	 */
	private int apprDocStatus;
	
	/**
	 * 결재문서 유형 (0:내부결재,1:협조공문-수신처가 있는 결재문서)
	 */
	private int apprDocType;
	
	/**
	 * 결재완료일
	 */
	private String apprEndDate;
	
	/**
	 * 결재부서 id
	 */
	private String apprGroupId;
	
	/**
	 * 결재부서명
	 */
	private String apprGroupName;
	
	/**
	 * 결재문서 id
	 */
	private String apprId;
	
	/**
	 * 합의유형 (0:순차합의, 1:병렬합의)
	 */
	private int apprLineType;
	
	/**
	 * 보존연한 코드
	 */
	private String apprPeriodCd;
	
	/**
	 * 보존연한 코드명
	 */
	private String apprPeriodNm;
	
	/**
	 * 기안일자
	 */
	private Date apprReqDate;
	
	/**
	 * 문서타입
	 */
	private int apprSecurityType;
	
	/**
	 * 결재문서 제목
	 */
	@NotEmpty(groups = { CreateApprDoc.class, UpdateApprDoc.class})
	private String apprTitle;
	
	/**
	 * 양식 동적 생성된 데이터 내용(json)
	 */
	private String formData;
	
	/**
	 * 양식 에디터 작성 내용 데이터(html)
	 */
	private String formEditorData;
	
	/**
	 * 양식 id
	 */
	private String formId;
	
	/**
	 * 양식 버전
	 */
	private int formVersion;
	
	/**
	 * 가이드
	 */
	private String formGuide;
	
	/**
	 * 양식 동적 생성된 데이터 layout(json)
	 */
	private String formLayoutData;
	
	/**
	 * 양식 연계 데이터(json)
	 */
	private String formLinkedData;
	
	/**
	 * 수신처 사용유무 (0:사용안함,1:사용)
	 */
	private int isApprReceive;
	
	/**
	 * 문서보안설정 (0:설정안함, 1:설정) 설정된 문서는 화면에서 검색,조회되지 않음
	 */
	private int isHidden;
	
	/**
	 * 부모 결재문서 id
	 */
	private String parentApprId;
	
	/**
	 * 결재문서 기안자 id
	 */
	private String registerId;
	
	/**
	 * /결재문서 기안자 이름
	 */
	private String registerName;
	
	/**
	 * 결재문서 기안자 직급
	 */
	private String registerJobTitle;
	
	/**
	 * 결재문서 기안날짜
	 */
	private Date registDate;
	
	/**
	 * 결재문서 수정자 id
	 */
	private String updaterId;
	
	/**
	 * 결재문서 수정자 이름
	 */
	private String updaterName;
	
	/**
	 * 결재문서 수정날짜
	 */
	private Date updateDate;
	
	/**
	 * 참조자
	 */
	private String referenceId;
	
	/**
	 * 참조자
	 */
	private String referenceSet;
	
	/**
	 * 열람권한
	 */
	private String readId;
	
	/**
	 * 열람권한
	 */
	private String readSet;
	
	/**
	 * 모드
	 */
	@NotEmpty(groups = { CreateApprDoc.class, UpdateApprDoc.class, PreviewApprDoc.class})
	private String mode;
	
	/**
	 * 결제자
	 */
	private String apprLine;
	
	/**
	 * 수신처
	 */
	private String apprReceiveLine; 
	
	/**
	 * 리스트 타입 
	 */
	private String listType; 
	
	/**
	 * 기안자 결재요청 시 등록 의견
	 */
	private String registerMessage;
	
	/**
	 * 문서 버전
	 */
	private int docVersion;
	
	/**
	 * 변경사유
	 */
	private String changeReason;
	
	/**
	 * 사용자아이디
	 */
	private String userId;
	
	/**
	 * 그룹아이디
	 */
	private String groupId;
	
	/**
	 * 포탈아이디
	 */
	private String portalId;
	
	/**
	 * 시스템 아이디
	 */
	private String systemId;
	
	/**
	 * 참조 결재문서 ID
	 */
	private String apprRefId;
	
	
	/**
	 * 위임자 아이디
	 */
	private String entrustUserId;
	
	/**
	 * 첨부파일 링크 리스트
	 */
	private List<FileLink> fileLinkList;

	/**
	 * 에디터내의 파일링크 리스트
	 */
	private List<FileLink> editorFileLinkList;

	/**
	 * 첨부파일 리스트
	 */
	private List<FileData> fileDataList;

	/**
	 * 에디터내의 파일 리스트
	 */
	private List<FileData> editorFileDataList;
	
	/**
	 * 결재선 정보
	 */
	private List<ApprLine> apprLineList;
	
	/**
	 * 진행현황 - 결재자
	 */
	private	String approverId;
	
	/**
	 * 진행현황 - 결재자이름
	 */
	private	String	approverName;
	
	/**
	 * 진행현황 - 결재자 직위
	 */
	private String	approverJobTitle;
	
	/**
	 * 위임,수임 여부
	 */
	private String	entrustType;
	
	/**
	 * linkType
	 */
	private String	linkType;
	
	/**
	 * 폴더 ID
	 */
	private String	folderId;
	
	
	private int lineCount;
	
	public int getLineCount() {
		return lineCount;
	}

	public void setLineCount(int lineCount) {
		this.lineCount = lineCount;
	}

	public String getApprDocCd() {
		return apprDocCd;
	}

	public void setApprDocCd(String apprDocCd) {
		this.apprDocCd = apprDocCd;
	}

	public String getApprDocNo() {
		return apprDocNo;
	}

	public void setApprDocNo(String apprDocNo) {
		this.apprDocNo = apprDocNo;
	}

	public int getApprDocStatus() {
		return apprDocStatus;
	}

	public void setApprDocStatus(int apprDocStatus) {
		this.apprDocStatus = apprDocStatus;
	}

	public int getApprDocType() {
		return apprDocType;
	}

	public void setApprDocType(int apprDocType) {
		this.apprDocType = apprDocType;
	}

	public String getApprEndDate() {
		return apprEndDate;
	}

	public void setApprEndDate(String apprEndDate) {
		this.apprEndDate = apprEndDate;
	}

	public String getApprGroupId() {
		return apprGroupId;
	}

	public void setApprGroupId(String apprGroupId) {
		this.apprGroupId = apprGroupId;
	}
	
	public String getApprGroupName() {
		return apprGroupName;
	}

	public void setApprGroupName(String apprGroupName) {
		this.apprGroupName = apprGroupName;
	}

	public String getApprId() {
		return apprId;
	}

	public void setApprId(String apprId) {
		this.apprId = apprId;
	}

	public int getApprLineType() {
		return apprLineType;
	}

	public void setApprLineType(int apprLineType) {
		this.apprLineType = apprLineType;
	}

	public String getApprPeriodCd() {
		return apprPeriodCd;
	}
	
	public String getApprPeriodNm() {
		return apprPeriodNm;
	}

	public void setApprPeriodNm(String apprPeriodNm) {
		this.apprPeriodNm = apprPeriodNm;
	}

	public void setApprPeriodCd(String apprPeriodCd) {
		this.apprPeriodCd = apprPeriodCd;
	}

	public Date getApprReqDate() {
		return apprReqDate;
	}

	public void setApprReqDate(Date apprReqDate) {
		this.apprReqDate = apprReqDate;
	}
	
	public int getApprSecurityType() {
		return apprSecurityType;
	}

	public void setApprSecurityType(int apprSecurityType) {
		this.apprSecurityType = apprSecurityType;
	}

	public String getApprTitle() {
		return apprTitle;
	}

	public void setApprTitle(String apprTitle) {
		this.apprTitle = apprTitle;
	}

	public String getFormData() {
		return formData;
	}

	public void setFormData(String formData) {
		this.formData = formData;
	}

	public String getFormEditorData() {
		return formEditorData;
	}

	public void setFormEditorData(String formEditorData) {
		this.formEditorData = formEditorData;
	}

	public String getFormId() {
		return formId;
	}
	
	public int getFormVersion() {
		return formVersion;
	}
	
	public String getFormGuide() {
		return formGuide;
	}

	public void setFormGuide(String formGuide) {
		this.formGuide = formGuide;
	}

	public void setFormVersion(int formVersion) {
		this.formVersion = formVersion;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getFormLayoutData() {
		return formLayoutData;
	}

	public void setFormLayoutData(String formLayoutData) {
		this.formLayoutData = formLayoutData;
	}
	
	public String getFormLinkedData() {
		return formLinkedData;
	}

	public void setFormLinkedData(String formLinkedData) {
		this.formLinkedData = formLinkedData;
	}

	public int getIsApprReceive() {
		return isApprReceive;
	}

	public void setIsApprReceive(int isApprReceive) {
		this.isApprReceive = isApprReceive;
	}

	public int getIsHidden() {
		return isHidden;
	}

	public void setIsHidden(int isHidden) {
		this.isHidden = isHidden;
	}
	
	public String getParentApprId() {
		return parentApprId;
	}

	public void setParentApprId(String parentApprId) {
		this.parentApprId = parentApprId;
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
	
	public String getRegisterJobTitle() {
		return registerJobTitle;
	}

	public void setRegisterJobTitle(String registerJobTitle) {
		this.registerJobTitle = registerJobTitle;
	}

	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getUpdaterId() {
		return updaterId;
	}

	public void setUpdaterId(String updaterId) {
		this.updaterId = updaterId;
	}

	public String getUpdaterName() {
		return updaterName;
	}

	public void setUpdaterName(String updaterName) {
		this.updaterName = updaterName;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getReferenceSet() {
		return referenceSet;
	}

	public void setReferenceSet(String referenceSet) {
		this.referenceSet = referenceSet;
	}

	public String getReadId() {
		return readId;
	}

	public void setReadId(String readId) {
		this.readId = readId;
	}

	public String getReadSet() {
		return readSet;
	}

	public void setReadSet(String readSet) {
		this.readSet = readSet;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getApprLine() {
		return apprLine;
	}

	public void setApprLine(String apprLine) {
		this.apprLine = apprLine;
	}

	public String getApprReceiveLine() {
		return apprReceiveLine;
	}

	public void setApprReceiveLine(String apprReceiveLine) {
		this.apprReceiveLine = apprReceiveLine;
	}
	
	public String getListType() {
		return listType;
	}

	public void setListType(String listType) {
		this.listType = listType;
	}
	
	public String getRegisterMessage() {
		return registerMessage;
	}

	public void setRegisterMessage(String registerMessage) {
		this.registerMessage = registerMessage;
	}

	public int getDocVersion() {
		return docVersion;
	}

	public void setDocVersion(int docVersion) {
		this.docVersion = docVersion;
	}

	public String getChangeReason() {
		return changeReason;
	}

	public void setChangeReason(String changeReason) {
		this.changeReason = changeReason;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	public String getApprRefId() {
		return apprRefId;
	}

	public void setApprRefId(String apprRefId) {
		this.apprRefId = apprRefId;
	}

	public List<FileLink> getFileLinkList() {
		return fileLinkList;
	}

	public void setFileLinkList(List<FileLink> fileLinkList) {
		this.fileLinkList = fileLinkList;
	}

	public List<FileLink> getEditorFileLinkList() {
		return editorFileLinkList;
	}

	public void setEditorFileLinkList(List<FileLink> editorFileLinkList) {
		this.editorFileLinkList = editorFileLinkList;
	}

	public List<FileData> getFileDataList() {
		return fileDataList;
	}

	public void setFileDataList(List<FileData> fileDataList) {
		this.fileDataList = fileDataList;
	}

	public List<FileData> getEditorFileDataList() {
		return editorFileDataList;
	}

	public void setEditorFileDataList(List<FileData> editorFileDataList) {
		this.editorFileDataList = editorFileDataList;
	}

	public String getEntrustUserId() {
		return entrustUserId;
	}

	public void setEntrustUserId(String entrustUserId) {
		this.entrustUserId = entrustUserId;
	}

	/**
	 * @return the apprLineList
	 */
	public List<ApprLine> getApprLineList() {
		return apprLineList;
	}

	/**
	 * @param apprLineList the apprLineList to set
	 */
	public void setApprLineList(List<ApprLine> apprLineList) {
		this.apprLineList = apprLineList;
	}

	public String getApproverId() {
		return approverId;
	}

	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}

	public String getApproverName() {
		return approverName;
	}

	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}

	public String getApproverJobTitle() {
		return approverJobTitle;
	}

	public void setApproverJobTitle(String approverJobTitle) {
		this.approverJobTitle = approverJobTitle;
	}

	public String getEntrustType() {
		return entrustType;
	}

	public void setEntrustType(String entrustType) {
		this.entrustType = entrustType;
	}

	public String getLinkType() {
		return linkType;
	}

	public void setLinkType(String linkType) {
		this.linkType = linkType;
	}

	public String getFolderId() {
		return folderId;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}
	
}
