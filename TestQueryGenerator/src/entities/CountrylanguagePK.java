package entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CountrylanguagePK implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@Basic( optional = false )
	@Column( name = "CountryCode" )
	private String				countryCode;

	@Basic( optional = false )
	@Column( name = "Language" )
	private String				language;

	public CountrylanguagePK( ) {
	}

	public CountrylanguagePK( String countryCode, String language ) {
		this.countryCode = countryCode;
		this.language = language;
	}

	@Override
	public boolean equals( Object object ) {
		if ( ! ( object instanceof CountrylanguagePK ) ) { return false; }
		CountrylanguagePK other = ( CountrylanguagePK ) object;
		if ( this.countryCode == null && other.countryCode != null
				|| this.countryCode != null && !this.countryCode.equals( other.countryCode ) ) { return false; }
		if ( this.language == null && other.language != null
				|| this.language != null && !this.language.equals( other.language ) ) { return false; }
		return true;
	}

	public String getCountryCode( ) {
		return countryCode;
	}

	public String getLanguage( ) {
		return language;
	}

	@Override
	public int hashCode( ) {
		int hash = 0;
		hash += countryCode != null ? countryCode.hashCode( ) : 0;
		hash += language != null ? language.hashCode( ) : 0;
		return hash;
	}

	public void setCountryCode( String countryCode ) {
		this.countryCode = countryCode;
	}

	public void setLanguage( String language ) {
		this.language = language;
	}

	@Override
	public String toString( ) {
		return "entidades.CountrylanguagePK[ countryCode=" + countryCode + ", language=" + language + " ]";
	}

}
