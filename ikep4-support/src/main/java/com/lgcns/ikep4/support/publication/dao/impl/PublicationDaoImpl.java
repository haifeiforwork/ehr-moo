package com.lgcns.ikep4.support.publication.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lgcns.ikep4.framework.core.dao.ibatis.GenericDaoSqlmap;
import com.lgcns.ikep4.support.publication.dao.PublicationDao;
import com.lgcns.ikep4.support.publication.model.Publication;
import com.lgcns.ikep4.support.publication.search.PublicationSearchCondition;


@Repository
public class PublicationDaoImpl extends GenericDaoSqlmap<Object, String> implements PublicationDao {

    private static final String NAMESPACE = "support.publication.dao.publication.";
    
    public String create( Object arg0 ) {
        // TODO Auto-generated method stub
        return null;
    }

    public boolean exists( String arg0 ) {
        // TODO Auto-generated method stub
        return false;
    }

    public Object get( String id ) {      
        return this.sqlSelectForObject( NAMESPACE + "getPublication", id );
    }

    public void remove( String id ) {
      this.sqlDelete( NAMESPACE + "deletePublication", id );
        
    }

    public void update( Object arg0 ) {
        // TODO Auto-generated method stub
        
    }

    public Integer countBySearchCondition( PublicationSearchCondition searchCondition ) {
       
        return (Integer)this.sqlSelectForObject( NAMESPACE +"countBySearchCondition",searchCondition );
    }

    public List listPublicationBySearchCondition( PublicationSearchCondition searchCondition ) {
       
        return this.sqlSelectForList( NAMESPACE + "publicationBySearchCondition", searchCondition );
    }

    public void modifyPublication( Publication publication ) {
         this.sqlUpdate( NAMESPACE + "modifyPublication", publication);
    }

    public void createPublication( Publication publication ) {
        
         this.sqlInsert( NAMESPACE + "createPublication", publication );
    }

    public List listPublicationForIamMoorim() {
        // TODO Auto-generated method stub
        return this.sqlSelectForListOfObject( NAMESPACE + "listPublicationForIamMoorim" );
    }

    public List listPublicationForGoodPaper() {
        // TODO Auto-generated method stub
        return this.sqlSelectForListOfObject( NAMESPACE + "listPublicationForGoodPaper" );
    }



    
    
    
    
    
}
