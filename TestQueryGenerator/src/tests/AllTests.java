package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith( Suite.class )
@SuiteClasses( { TestsEclipseLink.class, TestsHibernate.class, TestsEclipseLinkExceptions.class,
		TestsHibernateExceptions.class } )
public class AllTests {

}
