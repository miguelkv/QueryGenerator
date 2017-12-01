package com.miguelkvidal.querygenerator.api.expression.aggregation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;

public final class QCount extends QAgregate {

	public QCount( QExpression theExpression ) {
		super( theExpression );
	}

	@Override
	public Expression< ? > build( QGenerator< ? > theGenerator ) {
		Expression< ? > e = expression.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		return cb.count( e );
	}

	@Override
	public String toString( ) {
		return "COUNT( " + expression + " )";
	}

}
