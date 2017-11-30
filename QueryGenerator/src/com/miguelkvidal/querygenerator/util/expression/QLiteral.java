package com.miguelkvidal.querygenerator.util.expression;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;

public final class QLiteral< V > implements QExpression {

	public static < T > QLiteral< T > of( T theValue ) {
		return new QLiteral<>( theValue );
	}

	private final V value;

	private QLiteral( V theValue ) {
		value = theValue;
	}

	@Override
	public Expression< ? > build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		if ( value == null ) { return cb.nullLiteral( Object.class ); }

		return cb.literal( value );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QLiteral ) ) { return false; }
		QLiteral< ? > other = ( com.miguelkvidal.querygenerator.util.expression.QLiteral< ? > ) obj;
		if ( value == null ) {
			if ( other.value != null ) { return false; }
		} else if ( !value.equals( other.value ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + ( value == null ? 0 : value.hashCode( ) );
		return result;
	}

	@Override
	public String toString( ) {
		return "" + value;
	}

}
