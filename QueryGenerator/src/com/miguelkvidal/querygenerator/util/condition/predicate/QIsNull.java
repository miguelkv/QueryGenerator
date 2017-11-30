package com.miguelkvidal.querygenerator.util.condition.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.util.expression.QExpression;

public class QIsNull implements QPredicate {

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QIsNull of( QExpression theExpression ) {
		return new QIsNull( theExpression );
	}

	private final QExpression expression;

	private QIsNull( QExpression theExpression ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QIsNull.MSG_NULL_EXPRESSION ); }

		this.expression = theExpression;
	}

	@Override
	public Predicate build( QGenerator< ? > theGenerator ) {
		Expression< ? > e = expression.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		return cb.isNull( e );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QIsNull ) ) { return false; }
		QIsNull other = ( QIsNull ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return expression + " IS NULL";
	}

}
