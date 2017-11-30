package com.miguelkvidal.querygenerator.util;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.util.expression.QExpression;

public final class QOrder {

	public static enum QOrderType {
		ASC, DESC;
	}

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QOrder asc( QExpression theExpression ) {
		return new QOrder( theExpression, QOrderType.ASC );
	}

	public static QOrder desc( QExpression theExpression ) {
		return new QOrder( theExpression, QOrderType.DESC );
	}

	private final QExpression	expression;

	private final QOrderType	type;

	private QOrder( QExpression theExpression, QOrderType theType ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QOrder.MSG_NULL_EXPRESSION ); }

		this.type = theType;
		this.expression = theExpression;
	}

	public Order build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		Expression< ? > e = expression.build( theGenerator );

		switch ( type ) {
			case DESC:
				return cb.desc( e );

			case ASC:
			default:
				return cb.asc( e );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QOrder ) ) { return false; }
		QOrder other = ( QOrder ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		if ( type != other.type ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		result = prime * result + type.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return expression + " " + type;
	}

}
