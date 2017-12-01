package com.miguelkvidal.querygenerator.api.join;

import java.util.Map;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public abstract class QJoin {

	private static final String	MSG_EMPTY_PATH	= "Path must not be empty.";

	private static final String	MSG_NULL_PATH	= "Path must not be null.";

	public static QJoin inner( String thePath ) {
		return new QInnerJoin( thePath );
	}

	public static QJoin left( String thePath ) {
		return new QLeftJoin( thePath );
	}

	public static QJoin right( String thePath ) {
		return new QRightJoin( thePath );
	}

	protected final String path;

	protected QJoin( String thePath ) {
		if ( thePath == null ) { throw new IllegalArgumentException( QJoin.MSG_NULL_PATH ); }
		if ( thePath.trim( ).isEmpty( ) ) { throw new IllegalArgumentException( QJoin.MSG_EMPTY_PATH ); }

		this.path = thePath;
	}

	public abstract void build( Map< String, Path< ? > > theAttributes, Root< ? > root );

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QJoin ) ) { return false; }
		QJoin other = ( QJoin ) obj;
		if ( !path.equals( other.path ) ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + path.hashCode( );
		return result;
	}

}
