package tests;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import tests.util.DBConnection;

public class TestsHibernateExceptions extends TestExceptions {

	private static DBConnection		connection;

	private static EntityManager	em;

	private static Session			s;

	@BeforeClass
	public static void setUpBeforeClass( ) throws Exception {
		TestsHibernateExceptions.connection = new DBConnection( );
		TestsHibernateExceptions.connection.getFactory( );
		TestsHibernateExceptions.s = TestsHibernateExceptions.connection.open( );
		TestsHibernateExceptions.em = TestsHibernateExceptions.s.getEntityManagerFactory( ).createEntityManager( );
	}

	@AfterClass
	public static void tearDownAfterClass( ) throws Exception {
		TestsHibernateExceptions.em.close( );
		TestsHibernateExceptions.s.close( );
		TestsHibernateExceptions.connection.close( );
	}

	@Override
	protected EntityManager getEM( ) {
		return TestsHibernateExceptions.em;
	}

}
