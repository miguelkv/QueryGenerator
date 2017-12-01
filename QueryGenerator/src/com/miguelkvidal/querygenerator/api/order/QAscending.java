package com.miguelkvidal.querygenerator.api.order;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;

public final class QAscending extends QOrder {

	public QAscending( QExpression theExpression ) {

		super( theExpression );
	}

	@Override
	public Order build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		Expression< ? > e = expression.build( theGenerator );

		return cb.asc( e );
	}

	@Override
	public String toString( ) {
		return expression + " ASC";
	}

}
