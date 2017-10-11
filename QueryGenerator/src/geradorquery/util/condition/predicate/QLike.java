package geradorquery.util.condition.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import geradorquery.api.QGenerator;
import geradorquery.util.expression.QConcat;
import geradorquery.util.expression.QExpression;

public class QLike implements QPredicate {

	private static final String	MSG_NULL_EXPRESSION	= "Expression must not be null.";

	private static final String	MSG_NULL_VALUE		= "Value must not be null.";

	public static QLike contains( QExpression theExpression, String theValue, boolean isCaseInsensitive ) {
		return QLike.of( theExpression, QConcat.of( "%", theValue, "%" ), isCaseInsensitive );
	}

	public static QLike endsWith( QExpression theExpression, String theValue, boolean isCaseInsensitive ) {
		return QLike.of( theExpression, QConcat.of( theValue, "%" ), isCaseInsensitive );
	}

	public static QLike of( QExpression theExpression, QExpression theValue, boolean isCaseInsensitive ) {
		return new QLike( theExpression, theValue, isCaseInsensitive );
	}

	public static QLike startsWith( QExpression theExpression, String theValue, boolean isCaseInsensitive ) {
		return QLike.of( theExpression, QConcat.of( "%", theValue ), isCaseInsensitive );
	}

	private final QExpression	expression;

	private final boolean		flagCaseInsensitive;

	private final QExpression	value;

	private QLike( QExpression theExpression, QExpression theValue, boolean isCaseInsensitive ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QLike.MSG_NULL_EXPRESSION ); }
		if ( theValue == null ) { throw new IllegalArgumentException( QLike.MSG_NULL_VALUE ); }

		this.expression = theExpression;
		this.value = theValue;
		this.flagCaseInsensitive = isCaseInsensitive;
	}

	@Override
	public Predicate build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		Expression< String > e = expression.build( theGenerator ).as( String.class );
		Expression< String > v = value.build( theGenerator ).as( String.class );

		if ( flagCaseInsensitive ) {
			e = cb.lower( e );
			v = cb.lower( v );
		}

		return cb.like( e, v );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QLike ) ) { return false; }
		QLike other = ( QLike ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		if ( !value.equals( other.value ) ) { return false; }
		return true;
	}

	public QExpression getExpressao( ) {
		return expression;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		result = prime * result + value.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		if ( flagCaseInsensitive ) { return "LOWER( " + expression + " ) LIKE LOWER ( " + value + " )"; }
		return expression + " LIKE " + value;
	}

}
