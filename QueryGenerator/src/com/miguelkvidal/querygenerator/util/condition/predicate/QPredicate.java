package com.miguelkvidal.querygenerator.util.condition.predicate;

import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;

public interface QPredicate {

	public Predicate build( QGenerator< ? > theGenerator );

}
