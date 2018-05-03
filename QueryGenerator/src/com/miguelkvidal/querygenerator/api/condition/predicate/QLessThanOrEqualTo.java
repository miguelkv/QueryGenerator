package com.miguelkvidal.querygenerator.api.condition.predicate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;
import com.miguelkvidal.querygenerator.api.expression.QLiteral;

public class QLessThanOrEqualTo implements QPredicate {

	private final QExpression	leftOperator;

	private final QExpression	rightOperator;

	public QLessThanOrEqualTo( QExpression theLeftOperator, Object theValue ) {
		this( theLeftOperator, QLiteral.of( theValue ) );
	}

	public QLessThanOrEqualTo( QExpression theLeftOperator, QExpression theRightOperator ) {
		this.leftOperator = theLeftOperator;
		this.rightOperator = theRightOperator;
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	@Override
	public Predicate build( QGenerator< ? > theGenerator ) {

		Expression< Comparable > e;
		Expression< Comparable > d;

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		e = ( Expression< Comparable > ) leftOperator.build( theGenerator );
		d = ( Expression< Comparable > ) rightOperator.build( theGenerator );

		return cb.lessThanOrEqualTo( e, d );
	}

	@Override
	public String toString( ) {
		return leftOperator + " <= " + rightOperator;
	}

}
