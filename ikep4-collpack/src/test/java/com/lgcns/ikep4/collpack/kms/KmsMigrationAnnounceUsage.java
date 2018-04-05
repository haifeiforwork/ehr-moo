package com.lgcns.ikep4.collpack.kms;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lgcns.ikep4.collpack.kms.announce.dao.AnnounceItemDao;
import com.lgcns.ikep4.collpack.kms.announce.model.AnnounceItem;
import com.lgcns.ikep4.collpack.kms.batch.dao.KmsMigrationAnnounceUsageDao;
import com.lgcns.ikep4.collpack.kms.board.dao.BoardItemDao;
import com.lgcns.ikep4.support.fileupload.dao.FileDao;
import com.lgcns.ikep4.support.fileupload.dao.FileLinkDao;
import com.lgcns.ikep4.support.fileupload.model.FileData;
import com.lgcns.ikep4.support.fileupload.model.FileLink;
import com.lgcns.ikep4.support.user.member.dao.UserDao;
import com.lgcns.ikep4.util.encrypt.EncryptUtil;
import com.lgcns.ikep4.util.idgen.service.IdgenService;
import com.lgcns.ikep4.util.lang.StringUtil;


public class KmsMigrationAnnounceUsage extends BaseKmsTestCase{
	
	@Autowired
	private SqlMapClient sqlMapClientInfo;
	
	@Autowired
	private IdgenService idgenService;
	
	/**
	 * 파일 Dao
	 */
	@Autowired
	private FileDao fileDao;

	/**
	 * 파일 링크 Dao
	 */
	@Autowired
	private FileLinkDao fileLinkDao;
	
	@Autowired
	private KmsMigrationAnnounceUsageDao migrationAnnounceUsageDao;
	
	@Autowired
	private AnnounceItemDao announceItemDao;
	
	@Autowired
	private BoardItemDao kmsBoardItemDaoImpl;
	
	@Autowired
	private UserDao userDao;
	
	private StringBuffer nonUser = new StringBuffer();
	
	@Resource(name="transactionManager")
	protected DataSourceTransactionManager transactionManager;
	
	protected TransactionStatus getTransaction(){
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
	    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	    return transactionManager.getTransaction(definition);
	}
	
	@Test
	@Rollback(false)
	public void migrateUsageLog(){
		try {
			migrationAnnounceUsageDao.setSqlMapClientInfo(sqlMapClientInfo);
			List<HashMap<String, String>> listViewLogFromNews = (List<HashMap<String, String>>)migrationAnnounceUsageDao.listViewLogFromNewsDB();
			
			
			for(HashMap<String, String> viewLogFromNews : listViewLogFromNews){
				insertKmsBdLog(viewLogFromNews);				
			}
			
			saveErrorLog();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
		
	private void insertKmsBdLog(HashMap<String, String> viewLogFromNews) {
		/* 1.emp_no로 tobe의 user_id를 가져온다.
		 * 2. ikep4_kms_bd_log에 insert한다.commit
		 * */
		try {
			
		
			String empNo = viewLogFromNews.get("EMPLOYEE");
			HashMap<String, String> userInfo = getUserInfo(empNo);
			HashMap<String, String> newMapLog = new HashMap<String, String>();
			if(userInfo != null){
				newMapLog.put("userId", userInfo.get("USER_ID"));
				newMapLog.put("groupId", userInfo.get("GROUP_ID"));
				newMapLog.put("groupName", userInfo.get("GROUP_NAME"));
				newMapLog.put("registDate", viewLogFromNews.get("VIEW_DATE"));
				newMapLog.put("viewSum", viewLogFromNews.get("VIEW_SUM"));
				
				System.out.println("emp_no:" + empNo + ", viewSum:" + viewLogFromNews.get("VIEW_SUM"));
				
				
				callInsertQuery(newMapLog);
			}else{
				nonUser.append(empNo+"\r\n");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	@Transactional(readOnly=true, isolation=Isolation.READ_UNCOMMITTED)
	private HashMap<String, String> getUserInfo(String empNo){
		return (HashMap<String, String>)userDao.empNoToUserId(empNo);
	}


	//@Transactional(propagation=Propagation.REQUIRES_NEW)
	@Rollback(false)
	private void callInsertQuery(HashMap<String, String> newMapLog) {
		
		TransactionStatus txStatus = getTransaction();
		try {
			kmsBoardItemDaoImpl.createBoardItemRefCumulative(newMapLog);
			transactionManager.commit(txStatus);
		} catch (Exception e) {
			// TODO: handle exception
			transactionManager.rollback(txStatus);
		}
		
		
		
	}



	private void saveErrorLog() {
		BufferedOutputStream bo = null;
		try {
			bo = new BufferedOutputStream(new FileOutputStream(new File("E:/업무/PROJECT/무림/KMS/nonUser_20121120.txt")));
			bo.write(nonUser.toString().getBytes());
			bo.flush();
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			try {
				bo.close();
			} catch (Exception e2) {
				// TODO: handle exception
			}
		}
		
	}



	//@Test
	//@Rollback(false)
	public void migrateAnnounce(){
		
		try {
			migrationAnnounceUsageDao.setSqlMapClientInfo(sqlMapClientInfo);
			List<HashMap<String, String>> listAnnounceFromNews = (List<HashMap<String, String>>)migrationAnnounceUsageDao.listAnnounceFromNewsDB();
			
			AnnounceItem announceItem = null;
			String itemId = null;
			
			if(listAnnounceFromNews != null){
				for(HashMap<String, String> announceFromNews : listAnnounceFromNews){
					
					announceItem = new AnnounceItem();
					itemId = idgenService.getNextId();
					announceItem.setItemId(itemId);
					announceItem.setContents(announceFromNews.get("CONTENT"));
					announceItem.setTitle(announceFromNews.get("TITLE"));
					
					Integer readCount = new Integer(announceFromNews.get("READ_COUNT"));
					announceItem.setHitCount(readCount);
					announceItem.setItemDisplay(0);
					announceItem.setItemDelete(0);
					announceItem.setRegisterId(announceFromNews.get("CREATE_ID"));
					announceItem.setRegisterName(announceFromNews.get("CREATE_NAME"));
					announceItem.setRegistDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
					announceItem.setUpdaterId(announceFromNews.get("CREATE_ID"));
					announceItem.setUpdaterName(announceFromNews.get("CREATE_NAME"));
					announceItem.setUpdateDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
					
					
					// 첨부파일 업데이트
					if (announceFromNews.get("FILE_PATH") != null) {
						announceItem.setAttachFileCount(1);
						
						FileData fileData = setFile(announceFromNews);
						setFileLink(announceFromNews, announceItem, fileData);				
	
					}
					
					announceItemDao.create(announceItem);			
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
	private Date makeDateFormat(String strDate) {
		try {
			
			DateFormat df = new SimpleDateFormat("yyyy.MM.dd");	
			Date date = df.parse(strDate);
			return date;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	private FileData setFile(HashMap<String, String> announceFromNews){
		
		try {
			
			String folder = "/ikep4jfile/kms" + announceFromNews.get("FILE_PATH");
			String fileId = StringUtil.replaceQuot(EncryptUtil
					.encryptText(idgenService.getNextId()));
			
			String fileExtension = StringUtil.getFileExtension(announceFromNews.get("FILE_ORG_NAME"));
			File saveFile = new File(folder, UUID.randomUUID()
					.toString().replace("-", "")
					+ "." + fileExtension);
			
			FileData fileData = new FileData();
			fileData.setFileId(fileId);
			fileData.setFilePath(folder);
			fileData.setFileRealName(announceFromNews.get("FILE_ORG_NAME"));
			fileData.setFileSize(0);
			//fileData.setFileName(saveFile.getName());
			fileData.setFileName(announceFromNews.get("FILE_ORG_NAME"));
			fileData.setFileContentsType(fileExtension);
			fileData.setFileExtension(fileExtension);
			fileData.setEditorAttach(0);
			fileData.setRegisterId(announceFromNews.get("CREATE_ID"));
			fileData.setRegisterName(announceFromNews.get("CREATE_NAME"));
			fileData.setRegistDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
			fileData.setUpdaterId(announceFromNews.get("CREATE_ID"));
			fileData.setUpdaterName(announceFromNews.get("CREATE_NAME"));
			fileData.setUpdateDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
			
			this.fileDao.create(fileData);
			return fileData;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
		
	}
	
	private void setFileLink(HashMap<String, String> announceFromNews, AnnounceItem announceItem, FileData fileData){
		try {
			
			FileLink fileLink = new FileLink();
			String fileLinkId = idgenService.getNextId();
			fileLink.setFileId(fileData.getFileId());
			fileLink.setItemId(announceItem.getItemId());
			fileLink.setItemTypeCode(AnnounceItem.ITEM_FILE_TYPE);
			fileLink.setFileLinkId(fileLinkId);
			fileLink.setRegisterId(announceFromNews.get("CREATE_ID"));
			fileLink.setRegisterName(announceFromNews.get("CREATE_NAME"));
			fileLink.setRegistDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
			fileLink.setUpdaterId(announceFromNews.get("CREATE_ID"));
			fileLink.setUpdaterName(announceFromNews.get("CREATE_NAME"));
			fileLink.setUpdateDate(makeDateFormat(announceFromNews.get("CREATE_DATE")));
			
			this.fileLinkDao.create(fileLink);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
