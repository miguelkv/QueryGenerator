package com.miguelkvidal.querygenerator.util.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;

public final class QAgregate implements QExpression {

	public static enum QFuncaoAgregacao {
		AVG, COUNT, COUNT_DISTINCT, MAX, MIN, SUM;
	}

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QAgregate avg( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.AVG );
	}

	public static QAgregate count( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.COUNT );
	}

	public static QAgregate countDistinct( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.COUNT_DISTINCT );
	}

	public static QAgregate max( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.MAX );
	}

	public static QAgregate min( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.MIN );
	}

	public static QAgregate sum( QExpression theExpression ) {
		return new QAgregate( theExpression, QFuncaoAgregacao.SUM );
	}

	private final QExpression		expression;

	private final QFuncaoAgregacao	function;

	private QAgregate( QExpression theExpression, QFuncaoAgregacao theFunction ) {
		if ( theExpression == null ) { throw new IllegalArgumentException( QAgregate.MSG_NULL_EXPRESSION ); }

		this.expression = theExpression;
		this.function = theFunction;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Expression< ? > build( QGenerator< ? > theGenerator ) {
		Expression< Number > e = ( Expression< Number > ) expression.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		switch ( function ) {
			case AVG:
				return cb.avg( e );

			case MAX:
				return cb.max( e );

			case MIN:
				return cb.min( e );

			case SUM:
				return cb.sum( e );

			case COUNT_DISTINCT:
				return cb.countDistinct( e );

			case COUNT:
			default:
				return cb.count( e );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QAgregate ) ) { return false; }
		QAgregate other = ( QAgregate ) obj;
		if ( !expression.equals( other.expression ) ) { return false; }
		if ( function != other.function ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + expression.hashCode( );
		result = prime * result + function.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return function + "( " + expression + " )";
	}

}
