package com.miguelkvidal.querygenerator.api.expression.aggregation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;

public final class QMax extends QAgregate {

	public QMax( QExpression theExpression ) {
		super( theExpression );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Expression< ? > build( QGenerator< ? > theGenerator ) {
		Expression< Number > e = ( Expression< Number > ) expression.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		return cb.max( e );
	}

	@Override
	public String toString( ) {
		return "MAX( " + expression + " )";
	}

}
