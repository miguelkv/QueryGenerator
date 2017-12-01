package tests;

import javax.persistence.EntityManager;

import org.junit.Test;

import com.miguelkvidal.querygenerator.api.QConfiguration;
import com.miguelkvidal.querygenerator.api.QGeneralUseConfig;
import com.miguelkvidal.querygenerator.api.QGenerator;
import com.miguelkvidal.querygenerator.api.condition.QCondition;
import com.miguelkvidal.querygenerator.api.expression.QAttribute;
import com.miguelkvidal.querygenerator.api.expression.QConversion;
import com.miguelkvidal.querygenerator.api.expression.QFunction;
import com.miguelkvidal.querygenerator.api.expression.aggregation.QAgregate;
import com.miguelkvidal.querygenerator.api.join.QJoin;
import com.miguelkvidal.querygenerator.api.order.QOrder;

import entities.Country;

public abstract class TestExceptions {

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfAscWithNullExpression( ) {
		QOrder.asc( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfAttributeWithEmptyPath( ) {
		QAttribute.of( "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfAttributeWithNullPath( ) {
		QAttribute.of( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfAvgWithNullExpression( ) {
		QAgregate.avg( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConditionWithNullPredicate( ) {
		QCondition.of( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConfigWithNullClass( ) {
		new QConfiguration< Object >( null, null ) {
		};
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConfigWithNullOrigin( ) {
		new QConfiguration< Object >( Object.class, null ) {
		};
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConfigWithNullResultType( ) {
		new QConfiguration< Object >( null, Object.class ) {
		};
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConversionWithNullExpression( ) {
		QConversion.to( null, Object.class );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfConversionWithNullType( ) {
		QConversion.to( null, null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfCountDistinctWithNullExpression( ) {
		QAgregate.countDistinct( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfCountWithNullExpression( ) {
		QAgregate.count( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfDescWithNullExpression( ) {
		QOrder.desc( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfFunctionWithEmptyName( ) {
		QFunction.of( "", Object.class );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfFunctionWithNullName( ) {
		QFunction.of( null, Object.class );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfFunctionWithNullType( ) {
		QFunction.of( "NOW", null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfGeneratorWithNullArgs( ) {
		new QGenerator<>( null, null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfInnerJoinWithEmptyPath( ) {
		QJoin.inner( "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfInnerJoinWithNullPath( ) {
		QJoin.inner( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfInvalidConfiguration( ) {
		new QConfiguration< Object >( Object.class, Object.class ) {
		};
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfLeftJoinWithEmptyPath( ) {
		QJoin.left( "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfLeftJoinWithNullPath( ) {
		QJoin.left( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfMaxWithNullExpression( ) {
		QAgregate.max( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfMinWithNullExpression( ) {
		QAgregate.min( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfNullEM( ) {
		new QGenerator<>( null, new QGeneralUseConfig( Country.class ) );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfRightJoinWithEmptyPath( ) {
		QJoin.right( "" );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfRightJoinWithNullPath( ) {
		QJoin.right( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIfSumWithNullExpression( ) {
		QAgregate.sum( null );
	}

	@Test( expected = IllegalArgumentException.class )
	public final void errorIgConfigNull( ) {
		new QGenerator<>( getEM( ), null );
	}

	protected abstract EntityManager getEM( );

}
