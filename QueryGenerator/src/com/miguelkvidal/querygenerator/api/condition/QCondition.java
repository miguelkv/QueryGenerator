package com.miguelkvidal.querygenerator.api.condition;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.condition.predicate.QPredicate;

public final class QCondition {

	private static final String MSG_NULL_PREDICATE = "Predicate must not be null.";

	public static QCondition of( QPredicate thePredicate ) {
		return QCondition.of( thePredicate, false );
	}

	public static QCondition of( QPredicate thePredicate, boolean aFlagNegated ) {
		return new QCondition( thePredicate, aFlagNegated );
	}

	private final List< QCondition >	andConditions;

	private final boolean				flagNegated;

	private final List< QCondition >	orConditions;

	private final QPredicate			predicate;

	private QCondition( QPredicate thePredicate, boolean aFlagNegated ) {
		if ( thePredicate == null ) { throw new IllegalArgumentException( QCondition.MSG_NULL_PREDICATE ); }

		this.andConditions = new ArrayList<>( );
		this.orConditions = new ArrayList<>( );

		this.flagNegated = aFlagNegated;
		this.predicate = thePredicate;
	}

	public QCondition and( QCondition condition ) {
		andConditions.add( condition );

		return this;
	}

	public QCondition and( QPredicate thePredicate ) {
		return and( thePredicate, false );
	}

	public QCondition and( QPredicate thePredicate, boolean aFlagNegated ) {

		return and( new QCondition( thePredicate, aFlagNegated ) );
	}

	public Predicate build( QGenerator< ? > theGenerator ) {

		Predicate p = predicate.build( theGenerator );

		CriteriaBuilder cb = theGenerator.getCriteriaBuilder( );

		for ( QCondition c : andConditions ) {
			Predicate aux = c.build( theGenerator );

			if ( aux == null ) {
				continue;
			}

			if ( c.flagNegated ) {
				aux = cb.not( aux );
			}

			p = cb.and( p, aux );
		}

		for ( QCondition c : orConditions ) {
			Predicate aux = c.build( theGenerator );

			if ( aux == null ) {
				continue;
			}

			if ( c.flagNegated ) {
				aux = cb.not( aux );
			}

			p = cb.or( p, aux );
		}

		if ( flagNegated ) {
			p = cb.not( p );
		}

		return p;
	}

	public QCondition or( QCondition condition ) {
		orConditions.add( condition );

		return this;
	}

	public QCondition or( QPredicate thePredicate ) {
		return or( thePredicate, false );
	}

	public QCondition or( QPredicate thePredicate, boolean aFlagNegated ) {
		return or( new QCondition( thePredicate, aFlagNegated ) );
	}

}
