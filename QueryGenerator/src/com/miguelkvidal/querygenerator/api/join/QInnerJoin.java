package com.miguelkvidal.querygenerator.api.join;

import java.util.Map;

import javax.persistence.criteria.From;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

public final class QInnerJoin extends QJoin {

	public QInnerJoin( String thePath ) {
		super( thePath );
	}

	@Override
	public void build( Map< String, Path< ? > > theAttributes, Root< ? > root ) {
		if ( theAttributes.containsKey( path ) ) { return; }

		String[ ] partes = path.split( "\\." );
		String aux = "";
		From< ?, ? > j = root;
		for ( String s : partes ) {
			aux = ( aux.isEmpty( ) ? "" : aux + "." ) + s;
			if ( theAttributes.containsKey( aux ) ) {
				j = ( From< ?, ? > ) theAttributes.get( aux );
			} else {
				j = j.join( s, JoinType.INNER );
				theAttributes.put( aux, j );
			}
		}
	}

	@Override
	public String toString( ) {
		return "INNER JOIN ON " + path;
	}

}
