package com.miguelkvidal.querygenerator.api.expression.aggregation;

import com.miguelkvidal.querygenerator.api.expression.QExpression;

public abstract class QAgregate implements QExpression {

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QAgregate avg( QExpression theExpression ) {
		return new QAverage( theExpression );
	}

	public static QAgregate count( QExpression theExpression ) {
		return new QCount( theExpression );
	}

	public static QAgregate countDistinct( QExpression theExpression ) {
		return new QCountDistinct( theExpression );
	}

	public static QAgregate max( QExpression theExpression ) {
		return new QMax( theExpression );
	}

	public static QAgregate min( QExpression theExpression ) {
		return new QMin( theExpression );
	}

	public static QAgregate sum( QExpression theExpression ) {
		return new QSum( theExpression );
	}

	protected final QExpression expression;

	protected QAgregate( QExpression theExpression ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QAgregate.MSG_NULL_EXPRESSION ); }

		this.expression = theExpression;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QAgregate ) ) { return false; }
		QAgregate other = ( QAgregate ) obj;
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
