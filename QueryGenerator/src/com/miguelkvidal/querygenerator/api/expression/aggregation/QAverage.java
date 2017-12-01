package com.miguelkvidal.querygenerator.api.expression.aggregation;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;

public final class QAverage extends QAgregate {

	public QAverage( QExpression theExpression ) {
		super( theExpression );
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Expression< ? > build( QGenerator< ? > theGenerator ) {
		Expression< Number > e = ( Expression< Number > ) expression.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		return cb.avg( e );
	}

	@Override
	public String toString( ) {
		return "AVG( " + expression + " )";
	}

}
