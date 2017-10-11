package geradorquery.util.expression;

import javax.persistence.criteria.Expression;

import geradorquery.api.QGenerator;

public interface QExpression {

	public Expression< ? > build( QGenerator< ? > theGenerator );

}
