package com.miguelkvidal.querygenerator.api;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import com.miguelkvidal.querygenerator.api.condition.QCondition;
import com.miguelkvidal.querygenerator.api.expression.QExpression;
import com.miguelkvidal.querygenerator.api.join.QJoin;
import com.miguelkvidal.querygenerator.api.order.QOrder;

public abstract class QConfiguration< T > {

	private static final String			MSG_INVALID_ORIGIN	= "Origin must be an entity annotated with @Entity.";

	private static final String			MSG_NULL_ORIGIN		= "Origin must not be null.";

	private static final String			MSG_NULL_RESULT		= "Result type must not be null.";

	private QCondition					condition;

	private int							firstResult;

	private boolean						flagDistincts;

	private final List< QJoin >			joinsList			= new ArrayList<>( );

	private int							maxResults;

	private final List< QOrder >		ordering			= new ArrayList<>( );

	private final Class< ? >			origin;

	private final Class< T >			resultType;

	private final List< QExpression >	selection			= new ArrayList<>( );

	public QConfiguration( Class< T > theResultType, Class< ? > theOrigin ) {

		if ( theResultType == null ) { throw new IllegalArgumentException( QConfiguration.MSG_NULL_RESULT ); }
		if ( theOrigin == null ) { throw new IllegalArgumentException( QConfiguration.MSG_NULL_ORIGIN ); }

		Entity e = theOrigin.getAnnotation( Entity.class );
		if ( e == null ) { throw new IllegalArgumentException( QConfiguration.MSG_INVALID_ORIGIN ); }

		firstResult = -1;
		maxResults = -1;
		flagDistincts = true;
		resultType = theResultType;
		origin = theOrigin;
	}

	public QCondition getCondition( ) {
		return condition;
	}

	public int getFirstResult( ) {
		return firstResult;
	}

	public List< QJoin > getJoinsList( ) {
		return joinsList;
	}

	public int getMaxResults( ) {
		return maxResults;
	}

	public List< QOrder > getOrdering( ) {
		return ordering;
	}

	public Class< ? > getOrigin( ) {
		return origin;
	}

	public Class< T > getResultType( ) {
		return resultType;
	}

	public List< QExpression > getSelection( ) {
		return selection;
	}

	public boolean isDistinct( ) {
		return flagDistincts;
	}

	public QConfiguration< T > setDistinct( boolean flagIsDistinct ) {
		this.flagDistincts = flagIsDistinct;

		return this;
	}

	public QConfiguration< T > setFirstResult( int firstResult ) {
		this.firstResult = firstResult;

		return this;
	}

	public QConfiguration< T > setMaxResults( int maxResults ) {
		this.maxResults = maxResults;

		return this;
	}

	public QConfiguration< T > where( QCondition qc ) {
		this.condition = qc;

		return this;
	}

}
