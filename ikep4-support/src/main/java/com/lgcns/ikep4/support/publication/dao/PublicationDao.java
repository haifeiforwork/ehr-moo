package com.lgcns.ikep4.support.publication.dao;

import java.util.HashMap;
import java.util.List;

import com.lgcns.ikep4.framework.core.dao.GenericDao;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.search.PublicationSearchCondition;

public interface PublicationDao extends GenericDao<Object, String> {

    /**
     * 간행물 주소록 글 갯수 조회
     * @param searchCondition
     * @return
     */
    public Integer countBySearchCondition( PublicationSearchCondition searchCondition );

    /**
     * 간행물 주소록 리스트 조회
     * @param searchCondition
     * @return
     */
    public List<Publication> listPublicationBySearchCondition( PublicationSearchCondition searchCondition );

    
    /**
     * 간행물 수정 프로세스 
     * @param publication
     * @return
     */
    public void modifyPublication( Publication publication );

    /**
     * 간행물 등록 프로세스
     * @param publication
     * @return
     */
    public void createPublication( Publication publication );

    /**
     * 마이그레이션을 위해 기존 table에서 데이터 가져옴
     */
    List<HashMap<String, String>> listPublicationForIamMoorim();
    
    List<HashMap<String, String>> listPublicationForGoodPaper();
  

}
