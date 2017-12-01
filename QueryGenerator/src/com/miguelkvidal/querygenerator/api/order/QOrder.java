package com.miguelkvidal.querygenerator.api.order;

import javax.persistence.criteria.Order;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.expression.QExpression;

public abstract class QOrder {

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QOrder asc( QExpression theExpression ) {
		return new QAscending( theExpression );
	}

	public static QOrder desc( QExpression theExpression ) {
		return new QDescending( theExpression );
	}

	protected final QExpression expression;

	protected QOrder( QExpression theExpression ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QOrder.MSG_NULL_EXPRESSION ); }

		this.expression = theExpression;
	}

	public abstract Order build( QGenerator< ? > theGenerator );

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QOrder ) ) { return false; }
		QOrder other = ( QOrder ) obj;
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

}
