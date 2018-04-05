package com.lgcns.ikep4.supportpack.addressbook.test;



public interface GenericDaoTest<T> {
 
	void testGet();
 
	void testExists(); 
	
	void testCreate();
 
	void testUpdate();
 
	void testPhysicalDelete(); 
	
	void testLogicalDelete(); 
	
}
