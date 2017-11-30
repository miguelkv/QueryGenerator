package com.miguelkvidal.querygenerator.api.condition.predicate;

import javax.persistence.criteria.Predicate;

import com.miguelkvidal.querygenerator.api.QGenerator;

public interface QPredicate {

	public Predicate build( QGenerator< ? > theGenerator );

}
