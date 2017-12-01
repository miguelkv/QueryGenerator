package com.miguelkvidal.querygenerator.api.condition.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;
import com.miguelkvidal.querygenerator.api.expression.QLiteral;

public class QBetween implements QPredicate {

	private static final String	MSG_NULL_EXPRESSION	= "Expression must not be null.";

	private static final String	MSG_NULL_MAX_VALUE	= "Max value must not be null.";

	private static final String	MSG_NULL_MIN_VALUE	= "Min value must not be null.";

	public static QBetween of( QExpression theExpression, QExpression theMinValue, QExpression theMaxValue ) {
		return new QBetween( theExpression, theMinValue, theMaxValue );
	}

	public static < T > QBetween of( QExpression theExpression, T theMinValue, T theMaxValue ) {
		if ( theMinValue == null ) { throw new IllegalArgumentException( QBetween.MSG_NULL_MIN_VALUE ); }
		if ( theMaxValue == null ) { throw new IllegalArgumentException( QBetween.MSG_NULL_MAX_VALUE ); }

		return QBetween.of( theExpression, QLiteral.of( theMinValue ), QLiteral.of( theMaxValue ) );
	}

	private final QExpression	expression;

	private final QExpression	maxValue;

	private final QExpression	minValue;

	private QBetween( QExpression theExpression, QExpression theMinValue, QExpression theMaxValue ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QBetween.MSG_NULL_EXPRESSION ); }
		if ( theMinValue == null ) { throw new IllegalArgumentException( QBetween.MSG_NULL_MIN_VALUE ); }
		if ( theMaxValue == null ) { throw new IllegalArgumentException( QBetween.MSG_NULL_MAX_VALUE ); }

		this.expression = theExpression;
		this.minValue = theMinValue;
		this.maxValue = theMaxValue;
	}

	@Override
	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public Predicate build( QGenerator< ? > theGenerator ) {
		Expression< Comparable > exp = ( Expression< Comparable > ) expression.build( theGenerator );
		Expression< Comparable > min = ( Expression< Comparable > ) minValue.build( theGenerator );
		Expression< Comparable > max = ( Expression< Comparable > ) maxValue.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		return cb.between( exp, min, max );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QBetween ) ) { return false; }
		QBetween other = ( QBetween ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		if ( !minValue.equals( other.minValue ) ) { return false; }
		if ( !maxValue.equals( other.maxValue ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		result = prime * result + minValue.hashCode( );
		result = prime * result + maxValue.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return expression + " BETWEEN " + minValue + " AND " + maxValue;
	}

}
