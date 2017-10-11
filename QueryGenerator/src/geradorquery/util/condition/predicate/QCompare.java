package geradorquery.util.condition.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import geradorquery.api.QGenerator;
import geradorquery.util.expression.QExpression;
import geradorquery.util.expression.QLiteral;

public class QCompare implements QPredicate {

	public static enum QComparisonType {
		EQUAL ( "=" ), GREATER_THAN ( ">" ), GREATER_THAN_OR_EQUAL_TO ( ">=" ), LESS_THAN (
				"<" ), LESS_THAN_OR_EQUAL_TO ( "<=" ), NOT_EQUALS ( "<>" );

		private final String symbol;

		private QComparisonType( String s ) {
			symbol = s;
		}

		public String getSymbol( ) {
			return symbol;
		}
	}

	private static final String MSG_NULL_COMPARISON_TYPE = "Comparison type must not be null.";

	public static QCompare equal( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.EQUAL, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare equal( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.EQUAL, theLeftOperator, theRightOperator );
	}

	public static QCompare greaterThan( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.GREATER_THAN, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare greaterThan( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.GREATER_THAN, theLeftOperator, theRightOperator );
	}

	public static QCompare greaterThanOrEqualTo( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.GREATER_THAN_OR_EQUAL_TO, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare greaterThanOrEqualTo( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.GREATER_THAN_OR_EQUAL_TO, theLeftOperator, theRightOperator );
	}

	public static QCompare lessThan( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.LESS_THAN, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare lessThan( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.LESS_THAN, theLeftOperator, theRightOperator );
	}

	public static QCompare lessThanOrEqualTo( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.LESS_THAN_OR_EQUAL_TO, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare lessThanOrEqualTo( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.LESS_THAN_OR_EQUAL_TO, theLeftOperator, theRightOperator );
	}

	public static QCompare notEquals( QExpression theExpression, Object theValue ) {
		return QCompare.of( QComparisonType.NOT_EQUALS, theExpression, QLiteral.of( theValue ) );
	}

	public static QCompare notEquals( QExpression theLeftOperator, QExpression theRightOperator ) {
		return QCompare.of( QComparisonType.NOT_EQUALS, theLeftOperator, theRightOperator );
	}

	private static QCompare of( QComparisonType theComparisonType, QExpression theLeftOperator,
			QExpression theRightOperator ) {
		return new QCompare( theComparisonType, theLeftOperator, theRightOperator );
	}

	private final QComparisonType	comparisonType;

	private final QExpression		leftOperator;

	private final QExpression		rightOperator;

	private QCompare( QComparisonType theComparisonType, QExpression theLeftOperator, QExpression theRightOperator ) {
		if ( theComparisonType == null ) { throw new IllegalArgumentException( QCompare.MSG_NULL_COMPARISON_TYPE ); }

		this.comparisonType = theComparisonType;
		this.leftOperator = theLeftOperator;
		this.rightOperator = theRightOperator;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Override
	public Predicate build( QGenerator< ? > theGenerator ) {

		Expression< Comparable > e;
		Expression< Comparable > d;

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		if ( leftOperator == null ) {
			e = cb.nullLiteral( Comparable.class );
		} else {
			e = ( Expression< Comparable > ) leftOperator.build( theGenerator );
		}

		if ( rightOperator == null ) { return e.isNull( ); }

		d = ( Expression< Comparable > ) rightOperator.build( theGenerator );

		switch ( comparisonType ) {
			case NOT_EQUALS:
				return cb.notEqual( e, d );

			case GREATER_THAN:
				return cb.greaterThan( e, d );

			case GREATER_THAN_OR_EQUAL_TO:
				return cb.greaterThanOrEqualTo( e, d );

			case LESS_THAN:
				return cb.lessThan( e, d );

			case LESS_THAN_OR_EQUAL_TO:
				return cb.lessThanOrEqualTo( e, d );

			case EQUAL:
			default:
				return cb.equal( e, d );
		}
	}

	@Override
	public String toString( ) {
		return leftOperator + " " + comparisonType.getSymbol( ) + " " + rightOperator;
	}

}
