package tests.dialects;

import org.hibernate.dialect.MySQLDialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

/*
 * Reference: https://stackoverflow.com/questions/45725573/queryexception-on-hibernate-when-using-criteriabuilder-function
 *
 */
public class NewMySQLDialect extends MySQLDialect {

	public NewMySQLDialect( ) {
		registerFunction( "POW", new StandardSQLFunction( "POW", StandardBasicTypes.BIG_DECIMAL ) );
		registerFunction( "POWER", new StandardSQLFunction( "POWER", StandardBasicTypes.BIG_DECIMAL ) );
		registerFunction( "CHARSET", new StandardSQLFunction( "CHARSET", StandardBasicTypes.STRING ) );
		registerFunction( "CONNECTION_ID", new StandardSQLFunction( "CONNECTION_ID", StandardBasicTypes.STRING ) );
		registerFunction( "CONCAT_WS", new StandardSQLFunction( "CONCAT_WS", StandardBasicTypes.STRING ) );
		registerFunction( "CONV", new StandardSQLFunction( "CONV", StandardBasicTypes.STRING ) );
		registerFunction( "DATABASE", new StandardSQLFunction( "DATABASE", StandardBasicTypes.STRING ) );
		registerFunction( "FORMAT", new StandardSQLFunction( "FORMAT", StandardBasicTypes.STRING ) );
		registerFunction( "REPEAT", new StandardSQLFunction( "REPEAT", StandardBasicTypes.STRING ) );
		registerFunction( "REPLACE", new StandardSQLFunction( "REPLACE", StandardBasicTypes.STRING ) );
		registerFunction( "SUBSTR", new StandardSQLFunction( "SUBSTR", StandardBasicTypes.STRING ) );
		registerFunction( "USER", new StandardSQLFunction( "USER", StandardBasicTypes.STRING ) );
		registerFunction( "UUID", new StandardSQLFunction( "UUID", StandardBasicTypes.STRING ) );
	}
}
