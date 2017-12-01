package com.miguelkvidal.querygenerator.api.expression;

import java.util.Map;

import javax.persistence.criteria.Path;

import com.miguelkvidal.querygenerator.api.QGenerator;

public final class QAttribute implements QExpression {

	private static final String	MSG_EMPTY_PATH	= "Path must not be empty.";

	private static final String	MSG_NULL_PATH	= "Path must not be null.";

	public static QAttribute of( String thePath ) {
		return new QAttribute( thePath );
	}

	private final String path;

	public QAttribute( String thePath ) {
		if ( thePath == null ) { throw new IllegalArgumentException( QAttribute.MSG_NULL_PATH ); }
		if ( thePath.trim( ).isEmpty( ) ) { throw new IllegalArgumentException( QAttribute.MSG_EMPTY_PATH ); }

		this.path = thePath;
	}

	@Override
	public Path< ? > build( QGenerator< ? > theGenerator ) {
		Map< String, Path< ? > > attributes = theGenerator.getAttributes( );
		if ( attributes.containsKey( path ) ) { return attributes.get( path ); }

		String[ ] splitted = path.split( "\\." );
		String aux = "";
		Path< ? > p = theGenerator.getRoot( );
		for ( String s : splitted ) {
			aux = ( aux.isEmpty( ) ? "" : aux + "." ) + s;
			if ( attributes.containsKey( aux ) ) {
				p = attributes.get( aux );
			} else {
				p = p.get( s );
				attributes.put( aux, p );
			}
		}

		return p;
	}

	@Override
	public boolean equals( Object obj ) {
		if ( this == obj ) { return true; }
		if ( obj == null ) { return false; }
		if ( ! ( obj instanceof QAttribute ) ) { return false; }
		QAttribute other = ( QAttribute ) obj;
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

	@Override
	public String toString( ) {
		return path;
	}

}
