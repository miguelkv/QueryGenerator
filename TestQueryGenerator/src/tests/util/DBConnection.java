
package tests.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import entities.City;
import entities.Country;
import entities.Countrylanguage;

public class DBConnection {

	protected SessionFactory	factory;

	private final Object		lock	= new Object( );

	public DBConnection( ) {
		java.util.logging.Logger.getLogger( "org.hibernate" ).setLevel( java.util.logging.Level.INFO );
	}

	public void close( ) {

		factory.close( );
		factory = null;
	}

	public SessionFactory getFactory( ) {

		if ( factory == null ) {
			synchronized ( lock ) {
				if ( factory == null ) {
					factory = init( );
				}
			}
		}
		return factory;
	}

	public Session open( ) {

		return getFactory( ).openSession( );
	}

	protected Configuration addClass( Configuration cfg, Class< ? > classe ) {

		return cfg.addAnnotatedClass( classe );
	}

	protected Configuration addClass( Configuration cfg, Class< ? >... classes ) {

		for ( Class< ? > c : classes ) {
			addClass( cfg, c );
		}
		return cfg;
	}

	protected Configuration build( ) {

		Configuration c = new Configuration( );

		addClass( c, City.class );
		addClass( c, Country.class, Countrylanguage.class );

		return c;
	}

	protected StandardServiceRegistryBuilder buildRegistryBuilder( Configuration configure ) {

		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder( );
		ssrb.applySetting( "hibernate.connection.autocommit", "false" );

		ssrb.applySetting( "connection.driver_class", "com.mysql.jdbc.Driver" );
		ssrb.applySetting( "hibernate.connection.url", "jdbc:mysql://localhost:3306/world?useSSL=false" );
		ssrb.applySetting( "hibernate.connection.username", "gq" );
		ssrb.applySetting( "hibernate.connection.password", "123456789" );

		ssrb.applySetting( "hibernate.show_sql", "true" );
		ssrb.applySetting( "hibernate.format_sql", "true" );
		ssrb.applySetting( "hibernate.generate_statistics", "false" );
		ssrb.applySetting( "hibernate.use_sql_comments", "false" );

		// ssrb.applySetting("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		ssrb.applySetting( "hibernate.dialect", "tests.dialects.NewMySQLDialect" );

		ssrb.applySetting( "log4j.logger.org.hibernate", "all" );

		return ssrb;
	}

	protected SessionFactory init( ) {

		Configuration cfg = build( );
		StandardServiceRegistryBuilder ssrb = buildRegistryBuilder( cfg );

		return cfg.buildSessionFactory( ssrb.build( ) );
	}

}
