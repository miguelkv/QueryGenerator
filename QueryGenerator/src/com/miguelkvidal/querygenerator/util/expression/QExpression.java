package com.miguelkvidal.querygenerator.util.expression;

import javax.persistence.criteria.Expression;

import com.miguelkvidal.querygenerator.api.QGenerator;

public interface QExpression {

	public Expression< ? > build( QGenerator< ? > theGenerator );

}
