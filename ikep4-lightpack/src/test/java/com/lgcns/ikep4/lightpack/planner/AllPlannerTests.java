package com.lgcns.ikep4.lightpack.planner;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.lgcns.ikep4.lightpack.planner.dao.PlannerCategoryDaoTests;
import com.lgcns.ikep4.lightpack.planner.dao.ScheduleCRUDTests;
import com.lgcns.ikep4.lightpack.planner.service.CalendarServiceTests;
import com.lgcns.ikep4.lightpack.planner.service.DateTests;
import com.lgcns.ikep4.lightpack.planner.service.HoiydayServiceTest;
import com.lgcns.ikep4.lightpack.planner.service.MandatorServiceTests;
import com.lgcns.ikep4.lightpack.planner.service.MultiRecurrencesTest;
import com.lgcns.ikep4.lightpack.planner.service.NotiScheduleTests;
import com.lgcns.ikep4.lightpack.planner.service.PlannerCategoryServiceTests;
import com.lgcns.ikep4.lightpack.planner.service.RecurrenceDeleteTest;
import com.lgcns.ikep4.lightpack.planner.service.RecurrenceTests;
import com.lgcns.ikep4.lightpack.planner.service.RecurrenceUpdate2Test;
import com.lgcns.ikep4.lightpack.planner.service.RecurrenceUpdateTest;
import com.lgcns.ikep4.lightpack.planner.service.ScheduleServiceTests;
import com.lgcns.ikep4.lightpack.planner.web.CalendarControllerTests;
import com.lgcns.ikep4.lightpack.planner.web.CalendarEventChangeTests;
import com.lgcns.ikep4.lightpack.planner.web.PlannerCategoryControllerTests;

@RunWith(Suite.class)
@SuiteClasses( { 
	PlannerCategoryDaoTests.class, 
	ScheduleCRUDTests.class,
	
	CalendarServiceTests.class,
	DateTests.class,
	HoiydayServiceTest.class,
	MandatorServiceTests.class,
	PlannerCategoryServiceTests.class,
	RecurrenceTests.class,
	RecurrenceDeleteTest.class,
	RecurrenceUpdateTest.class,
	RecurrenceUpdate2Test.class,
	MultiRecurrencesTest.class,
	
	CalendarControllerTests.class,
	CalendarEventChangeTests.class,
	PlannerCategoryControllerTests.class,
	
	NotiScheduleTests.class,
	
	ScheduleServiceTests.class
	})
public class AllPlannerTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllPlannerTests.class.getName());

		return suite;
	}

}
