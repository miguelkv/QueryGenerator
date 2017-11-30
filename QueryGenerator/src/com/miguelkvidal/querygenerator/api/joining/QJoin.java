package com.miguelkvidal.querygenerator.api.joining;

import java.util.Map;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class QJoin {

	public static enum QJoinType {
		INNER, LEFT, RIGHT;
	}

	private static final String	MSG_EMPTY_PATH	= "Path must not be empty.";

	private static final String	MSG_NULL_PATH	= "Path must not be null.";

	public static QJoin inner( String thePath ) {
		return new QJoin( thePath, QJoinType.INNER );
	}

	public static QJoin left( String thePath ) {
		return new QJoin( thePath, QJoinType.LEFT );
	}

	public static QJoin right( String thePath ) {
		return new QJoin( thePath, QJoinType.RIGHT );
	}

	private final String	path;

	private final QJoinType	type;

	private QJoin( String thePath, QJoinType theType ) {
		if ( thePath == null ) { throw new IllegalArgumentException( QJoin.MSG_NULL_PATH ); }
		if ( thePath.trim( ).isEmpty( ) ) { throw new IllegalArgumentException( QJoin.MSG_EMPTY_PATH ); }

		this.path = thePath;
		this.type = theType;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QJoin ) ) { return false; }
		QJoin other = ( QJoin ) obj;
		if ( !path.equals( other.path ) ) { return false; }
		if ( type != other.type ) { return false; }
		return true;
	}

	@Override
	public int hashCode( ) {
		final int prime = 31;
		int result = 1;
		result = prime * result + path.hashCode( );
		result = prime * result + type.hashCode( );
		return result;
	}

	public void process( Map< String, Path< ? > > theAttributes, Root< ? > root ) {
		if ( theAttributes.containsKey( path ) ) { return; }

		String[ ] partes = path.split( "\\." );
		String aux = "";
		From< ?, ? > j = root;
		for ( String s : partes ) {
			aux = ( aux.isEmpty( ) ? "" : aux + "." ) + s;
			if ( theAttributes.containsKey( aux ) ) {
				j = ( From< ?, ? > ) theAttributes.get( aux );
			} else {
				switch ( type ) {
					case INNER:
						j = j.join( s, JoinType.INNER );
						break;
					case RIGHT:
						j = j.join( s, JoinType.RIGHT );
						break;
					case LEFT:
					default:
						j = j.join( s, JoinType.LEFT );
						break;
				}
				theAttributes.put( aux, j );
			}
		}
	}

	@Override
	public String toString( ) {
		return type + " JOIN ON " + path;
	}

}
