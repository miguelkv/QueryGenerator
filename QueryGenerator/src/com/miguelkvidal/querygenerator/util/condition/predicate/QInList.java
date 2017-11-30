package com.miguelkvidal.querygenerator.util.condition.predicate;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.util.expression.QExpression;

public class QInList implements QPredicate {

	private static final String		MSG_NULL_EXPRESSION	= "Expression must not be null.";

	private static final String		MSG_NULL_VALUE		= "Value must not be null.";

	private final QExpression[ ]	expressions;

	private final QExpression		value;

	private final Collection< ? >	valuesList;

	private QInList( QExpression theValue, QExpression[ ] theExpressions, Collection< ? > theValuesList ) {
		if ( theValue == null ) { throw new IllegalArgumentException( QInList.MSG_NULL_VALUE ); }
		if ( theExpressions == null
				&& theValuesList == null ) { throw new IllegalArgumentException( QInList.MSG_NULL_EXPRESSION ); }

		this.value = theValue;
		this.expressions = theExpressions;
		this.valuesList = theValuesList;
	}

	@Override
	public Predicate build( QGenerator< ? > theGenerator ) {

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		if ( expressions != null ) {
			if ( expressions.length == 0 ) { return cb.or( ); }
			Expression< ? >[ ] e = new Expression[ expressions.length ];
			int i = 0;
			for ( QExpression qe : expressions ) {
				e[ i ] = qe.build( theGenerator );
				i++;
			}
			return value.build( theGenerator ).in( e );
		} else {
			if ( valuesList.isEmpty( ) ) { return cb.or( ); }
			return value.build( theGenerator ).in( valuesList );
		}
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QInList ) ) { return false; }
		QInList other = ( QInList ) obj;
		if ( !Arrays.equals( expressions, other.expressions ) ) { return false; }
		if ( valuesList == null ) {
			if ( other.valuesList != null ) { return false; }
		} else if ( !valuesList.equals( other.valuesList ) ) { return false; }
		if ( value == null ) {
			if ( other.value != null ) { return false; }
		} else if ( !value.equals( other.value ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode( expressions );
		result = prime * result + ( valuesList == null ? 0 : valuesList.hashCode( ) );
		result = prime * result + ( value == null ? 0 : value.hashCode( ) );
		return result;
	}

	@Override
	public String toString( ) {
		return value + " IN ( " + ( expressions == null ? valuesList : expressions ) + " )";
	}

}
