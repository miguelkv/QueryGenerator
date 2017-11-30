package com.miguelkvidal.querygenerator.api.expression;

import java.util.Arrays;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;

public final class QFunction implements QExpression {

	private static final String	MSG_EMPTY_NAME	= "Name must not be empty.";

	private static final String	MSG_NULL_NAME	= "Name must not be null.";

	private static final String	MSG_NULL_TYPE	= "Type must not be null.";

	public static QFunction of( String theName, Class< ? > theType, QExpression... theParameters ) {
		return new QFunction( theName, theType, theParameters );
	}

	private final String			name;

	private final QExpression[ ]	parameters;

	private final Class< ? >		type;

	private QFunction( String theName, Class< ? > theType, QExpression... theParameters ) {
		if ( theType == null ) { throw new IllegalArgumentException( QFunction.MSG_NULL_TYPE ); }
		if ( theName == null ) { throw new IllegalArgumentException( QFunction.MSG_NULL_NAME ); }
		if ( theName.trim( ).isEmpty( ) ) { throw new IllegalArgumentException( QFunction.MSG_EMPTY_NAME ); }

		this.name = theName;
		this.type = theType;
		this.parameters = theParameters;
	}

	@Override
	public Expression< ? > build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		Expression< ? >[ ] args = new Expression[ parameters.length ];
		int i = 0;
		for ( QExpression qe : parameters ) {
			args[ i ] = qe.build( theGenerator );
			i++;
		}

		return cb.function( name, type, args );
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QFunction ) ) { return false; }
		QFunction other = ( QFunction ) obj;
		if ( !name.equals( other.name ) ) { return false; }
		if ( type != other.type ) { return false; }
		if ( !Arrays.equals( parameters, other.parameters ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode( );
		result = prime * result + Arrays.hashCode( parameters );
		result = prime * result + type.hashCode( );
		return result;
	}

	@Override
	public String toString( ) {
		return name + "( " + Arrays.toString( parameters ) + " )";
	}

}
