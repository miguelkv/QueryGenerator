package tests;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import tests.util.DBConnection;

public class TestsHibernate extends TestQueries {

	private static DBConnection		connection;

	private static EntityManager	em;

	private static Session			s;

	@BeforeClass
	public static void setUpBeforeClass( ) throws Exception {
		TestsHibernate.connection = new DBConnection( );
		TestsHibernate.connection.getFactory( );
		TestsHibernate.s = TestsHibernate.connection.open( );
		TestsHibernate.em = TestsHibernate.s.getEntityManagerFactory( ).createEntityManager( );
	}

	@AfterClass
	public static void tearDownAfterClass( ) throws Exception {
		TestsHibernate.em.close( );
		TestsHibernate.s.close( );
		TestsHibernate.connection.close( );
	}

	@Override
	protected EntityManager getEM( ) {
		return TestsHibernate.em;
	}

}
