package tests;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import org.eclipse.persistence.config.TargetServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class TestsEclipseLink extends TestQueries {

	private static EntityManager em;

	@BeforeClass
	public static void setUpBeforeClass( ) throws Exception {

		Map< String, String > propriedades = new HashMap<>( );

		propriedades.put( PersistenceUnitProperties.TRANSACTION_TYPE,
				PersistenceUnitTransactionType.RESOURCE_LOCAL.name( ) );

		propriedades.put( PersistenceUnitProperties.JDBC_DRIVER, "com.mysql.jdbc.Driver" );
		propriedades.put( PersistenceUnitProperties.JDBC_URL, "jdbc:mysql://localhost:3306/world?useSSL=false" );
		propriedades.put( PersistenceUnitProperties.JDBC_USER, "gq" );
		propriedades.put( PersistenceUnitProperties.JDBC_PASSWORD, "123456789" );

		propriedades.put( PersistenceUnitProperties.LOGGING_LEVEL, TestQueries.LOG_QUERIES );
		propriedades.put( PersistenceUnitProperties.LOGGING_PARAMETERS, "TRUE" );

		// Ensure that no server-platform is configured
		propriedades.put( PersistenceUnitProperties.TARGET_SERVER, TargetServer.None );

		EntityManagerFactory emf = Persistence.createEntityManagerFactory( "QueryGenerator", propriedades );
		TestsEclipseLink.em = emf.createEntityManager( );
	}

	@AfterClass
	public static void tearDownAfterClass( ) throws Exception {
		TestsEclipseLink.em.close( );
	}

	@Override
	protected EntityManager getEM( ) {
		return TestsEclipseLink.em;
	}

}
