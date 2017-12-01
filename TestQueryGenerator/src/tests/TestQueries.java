package tests;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.junit.Assert;
import org.junit.Test;

import com.miguelkvidal.querygenerator.api.QEntityConfig;
import com.miguelkvidal.querygenerator.api.QGeneralUseConfig;
import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.condition.QCondition;
import com.miguelkvidal.querygenerator.api.condition.predicate.QBetween;
import com.miguelkvidal.querygenerator.api.condition.predicate.QCompare;
import com.miguelkvidal.querygenerator.api.condition.predicate.QIsNull;
import com.miguelkvidal.querygenerator.api.expression.QAttribute;
import com.miguelkvidal.querygenerator.api.expression.QConcat;
import com.miguelkvidal.querygenerator.api.expression.QConversion;
import com.miguelkvidal.querygenerator.api.expression.QExpression;
import com.miguelkvidal.querygenerator.api.expression.QFunction;
import com.miguelkvidal.querygenerator.api.expression.QLiteral;
import com.miguelkvidal.querygenerator.api.expression.aggregation.QAgregate;
import com.miguelkvidal.querygenerator.api.join.QJoin;
import com.miguelkvidal.querygenerator.api.order.QOrder;

import entities.City;
import entities.Country;

public abstract class TestQueries {

	protected static String		LOG_QUERIES		= "ALL";

	private static final int	FIRST_RESULT	= 10;

	private static final int	MAX_RESULTS		= 10;

	@SuppressWarnings( "unused" )
	private static final int	TOTAL_CITIES	= 4079;

	private static final int	TOTAL_COUNTRIES	= 239;

	@Test
	public final void testCountQuery( ) {
		QEntityConfig< Country > cfg = new QEntityConfig<>( Country.class );

		QGenerator< Country > g = new QGenerator<>( getEM( ), cfg );
		TypedQuery< Long > tq = g.buildCountQuery( );

		Long total = tq.getSingleResult( );
		Assert.assertEquals( TestQueries.TOTAL_COUNTRIES, total.intValue( ) );
	}

	@Test
	public final void testCriteriaBuilderdWithoutQGenerator( ) {
		CriteriaBuilder cb = getEM( ).getCriteriaBuilder( );

		CriteriaQuery< Object[ ] > cq = cb.createQuery( Object[ ].class );

		Root< City > r = cq.from( City.class );

		cq.multiselect( r.get( "countryCode" ).get( "continent" ), r.get( "countryCode" ).get( "name" ), r );

		cq.distinct( true );

		TypedQuery< Object[ ] > tq = getEM( ).createQuery( cq );

		tq.setMaxResults( TestQueries.MAX_RESULTS );

		List< Object[ ] > list = tq.getResultList( );

		Assert.assertEquals( 10, list.size( ) );
	}

	@Test
	public final void testEntityConfig( ) {
		QEntityConfig< Country > cfg = new QEntityConfig<>( Country.class );

		cfg.setMaxResults( TestQueries.MAX_RESULTS );
		cfg.setFirstResult( TestQueries.FIRST_RESULT );

		QGenerator< Country > g = new QGenerator<>( getEM( ), cfg );
		TypedQuery< Country > tq = g.buildQuery( );

		List< Country > list = tq.getResultList( );
		Assert.assertEquals( TestQueries.MAX_RESULTS, list.size( ) );
	}

	@Test
	public final void testFirstResulAndMaxResults( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		config.setFirstResult( TestQueries.FIRST_RESULT );
		config.setMaxResults( TestQueries.MAX_RESULTS );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "name" ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( TestQueries.MAX_RESULTS, list.size( ) );
	}

	@Test
	public final void testGeneralUseCase( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		config.getSelection( ).add( QAttribute.of( "continent" ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 7, list.size( ) );
	}

	@Test
	public final void testQAgregate( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );
		selection.add( QAttribute.of( "region" ) );
		selection.add( QAgregate.count( QAttribute.of( "name" ) ) );
		selection.add( QAgregate.countDistinct( QAttribute.of( "governmentForm" ) ) );
		selection.add( QAgregate.max( QAttribute.of( "population" ) ) );
		selection.add( QAgregate.min( QAttribute.of( "population" ) ) );
		selection.add( QAgregate.avg( QAttribute.of( "lifeExpectancy" ) ) );
		selection.add( QAgregate.sum( QAttribute.of( "surfaceArea" ) ) );
		selection.add( QAgregate.sum( QAttribute.of( "governmentForm" ) ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 25, list.size( ) );
	}

	@Test
	public final void testQConcat( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );
		selection.add( QConcat.of( "a", "b" ) );
		selection.add( QConcat.of( "a", "b", "c" ) );
		selection.add( QConcat.of( "a", "b", "c", "d", "e", "f", "g", "h", "i" ) );

		QCondition.of( QCompare.notEquals( QLiteral.of( 1 ), QAttribute.of( "populacao" ) ) );

		config.setMaxResults( 1 );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 1, list.size( ) );
		Assert.assertEquals( "ab", list.get( 0 )[ 1 ] );
		Assert.assertEquals( "abc", list.get( 0 )[ 2 ] );
		Assert.assertEquals( "abcdefghi", list.get( 0 )[ 3 ] );
	}

	@Test
	public final void testQCondition( ) {
		QAttribute name = QAttribute.of( "name" );
		QAttribute localName = QAttribute.of( "localName" );
		QAttribute indepYear = QAttribute.of( "indepYear" );
		QAttribute gnp = QAttribute.of( "gnp" );

		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( name );
		selection.add( localName );
		selection.add( indepYear );
		selection.add( QConversion.to( gnp, Integer.class ) );

		QCondition condition = QCondition.of( QIsNull.of( indepYear ) );
		condition.or( QBetween.of( indepYear, QLiteral.of( 0 ), QLiteral.of( 1000 ) ) );

		config.where( condition );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 51, list.size( ) );
	}

	@Test
	public final void testQConditionNegated( ) {
		QAttribute name = QAttribute.of( "name" );
		QAttribute localName = QAttribute.of( "localName" );
		QAttribute indepYear = QAttribute.of( "indepYear" );
		QAttribute gnp = QAttribute.of( "gnp" );

		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( name );
		selection.add( localName );
		selection.add( indepYear );
		selection.add( QConversion.to( gnp, Integer.class ) );

		QCondition condition = QCondition.of( QIsNull.of( indepYear ), true );
		condition.or( QBetween.of( indepYear, QLiteral.of( 0 ), QLiteral.of( 1000 ) ) );

		config.where( condition );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 188, list.size( ) );
	}

	@Test
	public void testQFunction( ) {
		Class< Date > cDate = Date.class;
		Class< Integer > cInt = Integer.class;
		Class< BigDecimal > cBigD = BigDecimal.class;
		Class< String > cStr = String.class;

		QLiteral< String > format = QLiteral.of( "%Y%M%m%d" );
		QLiteral< String > spacedText = QLiteral.of( "  spaced text  " );
		QLiteral< String > apst = QLiteral.of( "'" );

		QAttribute indepYear = QAttribute.of( "indepYear" );
		QAttribute gnp = QAttribute.of( "gnp" );
		QAttribute localName = QAttribute.of( "localName" );

		QFunction fNow = QFunction.of( "NOW", cDate );
		QFunction fYearNow = QFunction.of( "YEAR", cDate, fNow );
		QFunction fSysdate = QFunction.of( "SYSDATE", cDate );

		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( indepYear );
		selection.add( fNow );
		selection.add( fYearNow );
		selection.add( QFunction.of( "COALESCE", cInt, indepYear, fYearNow ) );
		selection.add( QFunction.of( "COALESCE", cInt, QLiteral.of( 1 ), QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "COALESCE", cDate, QLiteral.of( 1 ), QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "ABS", cInt, indepYear ) );
		selection.add( QFunction.of( "CEIL", cInt, gnp ) );
		selection.add( QFunction.of( "CEILING", cInt, gnp ) );
		selection.add( QFunction.of( "CHAR_LENGTH", cInt, localName ) );
		selection.add( QFunction.of( "CHARSET", cStr, localName ) );
		selection.add( QFunction.of( "CONNECTION_ID", cStr ) );
		selection.add( QFunction.of( "CONCAT", cStr, indepYear, gnp ) );
		selection.add( QFunction.of( "CONCAT_WS", cStr, QLiteral.of( "-" ), indepYear, gnp ) );
		selection.add( QFunction.of( "CONV", cStr, indepYear, QLiteral.of( 10 ), QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "CONV", cStr, indepYear, QLiteral.of( 10 ), QLiteral.of( 16 ) ) );
		selection.add( QFunction.of( "CURDATE", cDate ) );
		selection.add( QFunction.of( "CURTIME", cDate ) );
		selection.add( QFunction.of( "DATABASE", cStr ) );
		selection.add( QFunction.of( "YEAR", cInt, fNow ) );
		selection.add( QFunction.of( "QUARTER", cInt, fNow ) );
		selection.add( QFunction.of( "MONTH", cInt, fNow ) );
		selection.add( QFunction.of( "MONTHNAME", cInt, fNow ) );
		selection.add( QFunction.of( "DATE", cInt, fNow ) );
		selection.add( QFunction.of( "HOUR", cInt, fNow ) );
		selection.add( QFunction.of( "MINUTE", cInt, fNow ) );
		selection.add( QFunction.of( "SECOND", cInt, fNow ) );
		selection.add( QFunction.of( "DATE_FORMAT", cStr, fNow, format ) );
		selection.add( QFunction.of( "DATEDIFF", cInt, fSysdate, fNow ) );
		selection.add( QFunction.of( "DAY", cInt, fNow ) );
		selection.add( QFunction.of( "DAYOFYEAR", cInt, fNow ) );
		selection.add( QFunction.of( "EXP", cInt, QLiteral.of( 0 ) ) );
		selection.add( QFunction.of( "POW", cInt, QLiteral.of( 2 ), QLiteral.of( 10 ) ) );
		selection.add( QFunction.of( "POWER", cInt, QLiteral.of( 2 ), QLiteral.of( 10 ) ) );
		selection.add( QFunction.of( "FLOOR", cInt, gnp ) );
		selection.add( QFunction.of( "FORMAT", cBigD, gnp, QLiteral.of( 0 ) ) );
		selection.add( QFunction.of( "FORMAT", cBigD, gnp, QLiteral.of( 1 ) ) );
		selection.add( QFunction.of( "UPPER", cStr, localName ) );
		selection.add( QFunction.of( "LOWER", cStr, localName ) );
		selection.add( QFunction.of( "LENGTH", cInt, localName ) );
		selection.add( QFunction.of( "LOCATE", cInt, QLiteral.of( "a" ), localName ) );
		selection.add( QFunction.of( "LOCATE", cInt, QLiteral.of( "a" ), localName, QLiteral.of( 5 ) ) );
		selection.add( QFunction.of( "LOG", cInt, QLiteral.of( 512 ) ) );
		selection.add( QFunction.of( "LOG10", cInt, QLiteral.of( 512 ) ) );
		selection.add( QFunction.of( "LOG2", cInt, QLiteral.of( 512 ) ) );
		selection.add( QFunction.of( "CONCAT", cStr, apst, QFunction.of( "LTRIM", cInt, spacedText ), apst ) );
		selection.add( QFunction.of( "CONCAT", cStr, apst, QFunction.of( "RTRIM", cInt, spacedText ), apst ) );
		selection.add( QFunction.of( "CONCAT", cStr, apst, QFunction.of( "TRIM", cInt, spacedText ), apst ) );
		selection.add( QFunction.of( "MD5", cStr, localName ) );
		selection.add( QFunction.of( "PI", cInt ) );
		selection.add( QFunction.of( "REPEAT", cStr, localName, QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "REPLACE", cStr, localName, QLiteral.of( "a" ), QLiteral.of( "Xyz" ) ) );
		selection.add( QFunction.of( "REVERSE", cStr, localName ) );
		selection.add( QFunction.of( "ROUND", cInt, gnp ) );
		selection.add( QFunction.of( "SQRT", cInt, gnp ) );
		selection.add( QFunction.of( "SUBSTR", cStr, localName, QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "SUBSTRING", cStr, localName, QLiteral.of( 2 ) ) );
		selection.add( QFunction.of( "USER", cStr ) );
		selection.add( QFunction.of( "TIME", cDate, fNow ) );
		selection.add( QFunction.of( "UUID", cStr ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( TestQueries.TOTAL_COUNTRIES, list.size( ) );
	}

	@Test
	public final void testQJoinExplicit( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QJoin > listJoins = config.getJoinsList( );
		listJoins.add( null );
		listJoins.add( QJoin.left( "cityCollection" ) );
		listJoins.add( QJoin.left( "countrylanguageCollection" ) );
		listJoins.add( QJoin.inner( "countrylanguageCollection" ) );
		listJoins.add( QJoin.inner( "cityCollection.countryCode" ) );
		listJoins.add( QJoin.left( "cityCollection.countryCode.cityCollection" ) );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 6, list.size( ) );
	}

	@Test
	public final void testQLiteral( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "region" ) );
		// TODO: a literal cannot be the first item of a select clause
		// (EclipseLink Limitation)
		// Reference:
		// https://stackoverflow.com/questions/45698891/queryexception-with-eclipselink-when-selecting-literal-as-first-argument
		selection.add( QLiteral.of( "test" ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 25, list.size( ) );
	}

	@Test
	public final void testQOrder( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		QAttribute continente = QAttribute.of( "continent" );
		selection.add( continente );
		selection.add( QLiteral.of( "test" ) );
		selection.add( QAgregate.count( QAttribute.of( "region" ) ) );
		selection.add( QAgregate.sum( QAttribute.of( "population" ) ) );

		config.getJoinsList( ).add( QJoin.left( "cityCollection" ) );

		config.getOrdering( ).add( QOrder.asc( continente ) );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );

		Assert.assertEquals( 7, list.size( ) );
		Assert.assertEquals( "Asia", list.get( 0 )[ 0 ] );
	}

	@Test
	public final void testQOrderAsc( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );
		selection.add( QAttribute.of( "region" ) );

		List< QOrder > ordering = config.getOrdering( );
		ordering.add( null );
		ordering.add( QOrder.asc( QAttribute.of( "continent" ) ) );

		config.setMaxResults( 1 );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 1, list.size( ) );
		Assert.assertEquals( "Asia", list.get( 0 )[ 0 ] );
	}

	@Test
	public final void testQOrderDesc( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );
		selection.add( QAttribute.of( "region" ) );

		List< QOrder > ordering = config.getOrdering( );
		ordering.add( QOrder.desc( QAttribute.of( "continent" ) ) );

		config.setMaxResults( 1 );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 1, list.size( ) );
		Assert.assertEquals( "South America", list.get( 0 )[ 0 ] );
	}

	@Test
	public final void testQOrderNull( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( Country.class );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "continent" ) );
		selection.add( QAttribute.of( "region" ) );

		List< QOrder > ordering = config.getOrdering( );
		ordering.add( null );

		config.setMaxResults( 1 );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( 1, list.size( ) );
	}

	@Test
	public final void testSimpleSelect( ) {
		QGeneralUseConfig config = new QGeneralUseConfig( City.class );

		config.setDistinct( false );

		List< QExpression > selection = config.getSelection( );
		selection.add( QAttribute.of( "countryCode.continent" ) );
		selection.add( QAttribute.of( "countryCode.region" ) );
		selection.add( QAttribute.of( "name" ) );
		selection.add( QAttribute.of( "population" ) );
		selection.add( QAttribute.of( "countryCode.continent" ) );

		config.setMaxResults( TestQueries.MAX_RESULTS );

		QGenerator< Object[ ] > g = new QGenerator<>( getEM( ), config );

		TypedQuery< Object[ ] > tq = g.buildQuery( );

		List< Object[ ] > list = tq.getResultList( );
		Assert.assertEquals( TestQueries.MAX_RESULTS, list.size( ) );
	}

	protected abstract EntityManager getEM( );

}
