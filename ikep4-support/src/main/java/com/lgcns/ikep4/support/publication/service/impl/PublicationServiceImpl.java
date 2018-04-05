package com.lgcns.ikep4.support.publication.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lgcns.ikep4.framework.core.service.impl.GenericServiceImpl;
import com.lgcns.ikep4.framework.web.SearchResult;
import com.lgcns.ikep4.support.publication.dao.PublicationDao;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.search.PublicationSearchCondition;
import com.lgcns.ikep4.support.publication.service.PublicationService;
import com.lgcns.ikep4.util.idgen.service.IdgenService;



@Service
@Transactional
public class PublicationServiceImpl extends GenericServiceImpl<Object, String> implements PublicationService  {

    @Autowired
    private PublicationDao publicationDao;
    
    @Autowired
    private IdgenService idgenService;
    
    
    public String create( Object arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete( String id ) {
       
        this.publicationDao.remove( id );
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object read( String arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public void update( Object arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public SearchResult<Publication> listPublicationBySearchCondition( PublicationSearchCondition searchCondition ) {
       
        
        Integer count = this.publicationDao.countBySearchCondition(searchCondition);
        
        searchCondition.terminateSearchCondition( count );
        
        SearchResult<Publication> searchResult = null;
        
        if(searchCondition.isEmptyRecord()){
            searchResult = new SearchResult<Publication>( searchCondition );
        }else{
            List<Publication> publicationList = this.publicationDao.listPublicationBySearchCondition( searchCondition );
            
            searchResult = new SearchResult<Publication>( publicationList , searchCondition );
        }
        return searchResult;
       
    }

    public Publication getPublicationItem( String id ) {
              
        return (Publication)this.publicationDao.get( id );
    }

    public void modifyPublication( Publication publication ) {
        
       this.publicationDao.modifyPublication(publication);
    }

    public String createPublication( Publication publication ) {
        
        String id = idgenService.getNextId();
        publication.setId( id );     
        this.publicationDao.createPublication(publication);
        
        return id;
    }

    public List<Publication> allListPublication( PublicationSearchCondition searchCondition ) {
        Integer count = publicationDao.countBySearchCondition( searchCondition );
        searchCondition.terminateSearchCondition( count );
        searchCondition.setEndRowIndex( count );
        
        List<Publication> listAllPublication = publicationDao.listPublicationBySearchCondition(searchCondition);
        
        
       for (Publication publication : listAllPublication) {
            
            if( publication.getType().equals( "1" )){
                publication.setType( "아이엠 무림" );
            }else{
                publication.setType( "좋은종이" );
            }
            
            publication.setZip1No( publication.getZip1No() +"-"+publication.getZip2No() );
                       
        }
        
        return listAllPublication;
    }

    
    
    
    
}
