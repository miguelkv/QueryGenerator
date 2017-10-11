package geradorquery.util.expression;

import javax.persistence.criteria.Expression;

import geradorquery.api.QGenerator;

public final class QConversion< T > implements QExpression {

	private static final String	MSG_NULL_EXPRESSION	= "Expression must not be null.";

	private static final String	MSG_NULL_TYPE		= "Type must not be null.";

	public static < T > QConversion< T > to( QExpression theExpression, Class< T > theType ) {
		return new QConversion<>( theExpression, theType );
	}

	private final QExpression	expression;

	private final Class< T >	type;

	private QConversion( QExpression theExpression, Class< T > theType ) {
		if ( theType == null ) { throw new IllegalArgumentException( QConversion.MSG_NULL_TYPE ); }
		if ( theExpression == null ) { throw new IllegalArgumentException( QConversion.MSG_NULL_EXPRESSION ); }

		this.type = theType;
		this.expression = theExpression;
	}

	@Override
	public Expression< ? > build( QGenerator< ? > theGenerator ) {

		return expression.build( theGenerator ).as( type );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QConversion ) ) { return false; }
		QConversion< ? > other = ( geradorquery.util.expression.QConversion< ? > ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		if ( !type.equals( other.type ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		result = prime * result + type.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return "CAST( " + expression + " AS " + type + " )";
	}

}
