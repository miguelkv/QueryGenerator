package com.miguelkvidal.querygenerator.api.expression;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;

public final class QConcat implements QExpression {

	private static final String MSG_NULL_EXPRESSION = "Expression must not be null.";

	public static QExpression of( QExpression s0, QExpression s1, QExpression... others ) {
		if ( s0 == null ) { throw new IllegalArgumentException( QConcat.MSG_NULL_EXPRESSION ); }
		if ( s1 == null ) { throw new IllegalArgumentException( QConcat.MSG_NULL_EXPRESSION ); }

		QExpression[ ] s = new QExpression[ 2 + others.length ];
		s[ 0 ] = s0;
		s[ 1 ] = s1;

		for ( int i = 0; i < others.length; i++ ) {
			QExpression x = others[ i ];
			if ( x == null ) { throw new IllegalArgumentException( QConcat.MSG_NULL_EXPRESSION ); }
			s[ i + 2 ] = x;
		}

		return new QConcat( s );
	}

	public static QExpression of( QExpression s0, String s1, String... others ) {
		QExpression[ ] s = new QExpression[ 2 + others.length ];
		s[ 0 ] = s0;
		s[ 1 ] = QLiteral.of( s1 );

		for ( int i = 0; i < others.length; i++ ) {
			s[ i + 2 ] = QLiteral.of( others[ i ] );
		}

		return new QConcat( s );
	}

	public static QExpression of( String s0, QExpression s1, String... others ) {
		QExpression[ ] s = new QExpression[ 2 + others.length ];
		s[ 0 ] = QLiteral.of( s0 );
		s[ 1 ] = s1;

		for ( int i = 0; i < others.length; i++ ) {
			s[ i + 2 ] = QLiteral.of( others[ i ] );
		}

		return new QConcat( s );
	}

	public static QExpression of( String s0, String s1, String... others ) {
		QExpression[ ] s = new QExpression[ 2 + others.length ];
		s[ 0 ] = QLiteral.of( s0 );
		s[ 1 ] = QLiteral.of( s1 );

		for ( int i = 0; i < others.length; i++ ) {
			s[ i + 2 ] = QLiteral.of( others[ i ] );
		}

		return new QConcat( s );
	}

	private final QExpression[ ] values;

	public QConcat( QExpression[ ] theValues ) {
		this.values = theValues;
	}

	@Override
	@SuppressWarnings( "unchecked" )
	public Expression< ? > build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		Expression< String > e0 = ( Expression< String > ) values[ 0 ].build( theGenerator );
		Expression< String > e1 = ( Expression< String > ) values[ 1 ].build( theGenerator );

		Expression< String > e = cb.concat( e0, e1 );
		for ( int i = 2; i < values.length; i++ ) {
			QExpression qe = values[ i ];
			Expression< String > x = ( Expression< String > ) qe.build( theGenerator );
			e = cb.concat( e, x );
		}

		return e;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QConcat ) ) { return false; }
		QConcat other = ( QConcat ) obj;
		if ( !Arrays.equals( values, other.values ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode( values );
		return result;
	}

	@Override
	public String toString( ) {
		return "CONCAT( " + Arrays.toString( values ) + " )";
	}

}
