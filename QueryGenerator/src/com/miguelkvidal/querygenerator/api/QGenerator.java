package com.miguelkvidal.querygenerator.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import com.miguelkvidal.querygenerator.util.QJoin;
import com.miguelkvidal.querygenerator.util.QOrder;
import com.miguelkvidal.querygenerator.util.condition.QCondition;
import com.miguelkvidal.querygenerator.util.expression.QAgregate;
import com.miguelkvidal.querygenerator.util.expression.QExpression;

public class QGenerator< T > {

	private static final String				MSG_NULL_EM				= "Entity Manager must not be null.";

	private static final String				MSG_NULL_CONFIGURATION	= "Configuration must not be null.";

	private final Map< String, Path< ? > >	attributes				= new HashMap<>( );

	private final QConfiguration< T >		configuration;

	private final CriteriaBuilder			criteriaBuilder;

	private final CriteriaQuery< T >		criteriaQuery;

	private final EntityManager				em;

	private final Root< T >					root;

	@SuppressWarnings( { "unchecked", "rawtypes" } )
	public QGenerator( EntityManager em, QConfiguration theConfiguration ) {

		if ( em == null ) { throw new IllegalArgumentException( QGenerator.MSG_NULL_EM ); }
		if ( theConfiguration == null ) { throw new IllegalArgumentException( QGenerator.MSG_NULL_CONFIGURATION ); }

		this.em = em;

		criteriaBuilder = em.getCriteriaBuilder( );
		criteriaQuery = criteriaBuilder.createQuery( theConfiguration.getResultType( ) );
		root = criteriaQuery.from( theConfiguration.getOrigin( ) );

		configuration = theConfiguration;
	}

	@SuppressWarnings( "unchecked" )
	public TypedQuery< Long > buildCountQuery( ) {

		prepareJoins( );

		CriteriaQuery< Long > criteriaQuery = ( CriteriaQuery< Long > ) this.criteriaQuery;

		if ( configuration.isDistinct( ) ) {
			criteriaQuery.select( criteriaBuilder.countDistinct( root ) );
		} else {
			criteriaQuery.select( criteriaBuilder.count( root ) );
		}

		Predicate condition = buildWhere( );

		if ( condition != null ) {
			criteriaQuery.where( condition );
		}

		int maxResults = configuration.getMaxResults( );
		int firstResult = configuration.getFirstResult( );

		TypedQuery< Long > tq = em.createQuery( criteriaQuery );

		if ( maxResults > 0 ) {
			tq.setMaxResults( maxResults );
		}

		if ( firstResult > 0 ) {
			tq.setFirstResult( firstResult );
		}

		return tq;
	}

	public TypedQuery< T > buildQuery( ) {

		prepareJoins( );

		criteriaQuery.distinct( configuration.isDistinct( ) );

		buildSelect( );

		Predicate condition = buildWhere( );

		List< Order > order = buildOrderBy( );

		if ( condition != null ) {
			criteriaQuery.where( condition );
		}

		if ( !order.isEmpty( ) ) {
			criteriaQuery.orderBy( order );
		}

		int maxResults = configuration.getMaxResults( );
		int firstResult = configuration.getFirstResult( );

		TypedQuery< T > tq = em.createQuery( criteriaQuery );

		if ( maxResults > 0 ) {
			tq.setMaxResults( maxResults );
		}

		if ( firstResult > 0 ) {
			tq.setFirstResult( firstResult );
		}

		return tq;
	}

	public Map< String, Path< ? > > getAttributes( ) {
		return attributes;
	}

	public CriteriaBuilder getCriteriaBuilder( ) {
		return criteriaBuilder;
	}

	public CriteriaQuery< T > getCriteriaQuery( ) {
		return criteriaQuery;
	}

	public Root< ? > getRoot( ) {
		return root;
	}

	private List< Order > buildOrderBy( ) {
		List< QOrder > ordering = configuration.getOrdering( );

		List< Order > order = new ArrayList<>( );
		for ( QOrder qo : ordering ) {
			if ( qo == null ) {
				continue;
			}

			order.add( qo.build( this ) );
		}
		return order;
	}

	private void buildSelect( ) {

		List< Selection< ? > > select = new ArrayList<>( );

		boolean flagAggregation = false;

		List< QExpression > selection = configuration.getSelection( );

		if ( selection.isEmpty( ) ) {
			criteriaQuery.select( root );
			return;
		}

		for ( QExpression qe : selection ) {
			Expression< ? > e = qe.build( this );

			if ( !flagAggregation && qe instanceof QAgregate ) {
				flagAggregation = true;
			}

			select.add( e );
		}

		criteriaQuery.multiselect( select );

		if ( flagAggregation ) {
			List< Expression< ? > > aggregation = new ArrayList<>( );
			for ( QExpression qe : selection ) {
				if ( qe instanceof QAgregate ) {
					continue;
				}
				Expression< ? > e = qe.build( this );
				aggregation.add( e );
			}
			criteriaQuery.groupBy( aggregation );
		}
	}

	private Predicate buildWhere( ) {
		QCondition condition = configuration.getCondition( );

		if ( condition == null ) { return null; }

		Predicate p = condition.build( this );

		return p;
	}

	private void prepareJoins( ) {
		List< QJoin > joinsList = configuration.getJoinsList( );

		for ( QJoin qj : joinsList ) {
			if ( qj == null ) {
				continue;
			}

			qj.process( attributes, root );
		}
	}

}
