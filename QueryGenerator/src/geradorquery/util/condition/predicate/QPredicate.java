package geradorquery.util.condition.predicate;

import javax.persistence.criteria.Predicate;

import geradorquery.api.QGenerator;

public interface QPredicate {

	public Predicate build( QGenerator< ? > theGenerator );

}
