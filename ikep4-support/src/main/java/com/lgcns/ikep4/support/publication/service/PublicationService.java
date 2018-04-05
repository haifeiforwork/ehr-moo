package com.lgcns.ikep4.support.publication.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.GenericService;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.search.PublicationSearchCondition;



/**
 * 
 * 간행물 주소록 서비스
 *
 * @author SongHeeJung
 * @version $Id$
 */
@Transactional
public interface PublicationService extends GenericService<Object, String>{

    /**
     * 간행물 주소록 리스팅
     * @param searchCondition
     * @return
     */
    SearchResult<Publication> listPublicationBySearchCondition( PublicationSearchCondition searchCondition );

    /**
     * 간행물 주소록 상세보기 및 등록, 수정 화면 출력
     * @param id
     * @return
     */
    Publication getPublicationItem( String id );

    /**
     * 간행물 주소록 수정 프로세스
     * @param publication
     * @return
     */
    void modifyPublication( Publication publication );

    /**
     * 간행물 주소록 등록 프로세스 
     * @param publication
     * @return
     */
    String createPublication( Publication publication );

    /**
     * 간행물 주소록 리스트에있는것만 가져오기
     * @param searchCondition
     * @return
     */
    List<Publication> allListPublication( PublicationSearchCondition searchCondition );
      

}
