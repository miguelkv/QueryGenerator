package geradorquery.api;

public class QEntityConfig< T > extends QConfiguration< T > {

	public QEntityConfig( Class< T > aClasseEntidade ) {
		super( aClasseEntidade, aClasseEntidade );
	}

}