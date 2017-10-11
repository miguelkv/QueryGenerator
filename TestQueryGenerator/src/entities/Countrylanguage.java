package entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "countrylanguage" )
public class Countrylanguage implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@EmbeddedId
	protected CountrylanguagePK	countrylanguagePK;

	@JoinColumn( name = "CountryCode", referencedColumnName = "Code", insertable = false, updatable = false )
	@ManyToOne( optional = false, fetch = FetchType.LAZY )
	private Country				country;

	@Basic( optional = false )
	@Column( name = "IsOfficial" )
	private String				isOfficial;

	@Basic( optional = false )
	@Column( name = "Percentage" )
	private float				percentage;

	public Countrylanguage( ) {
	}

	public Countrylanguage( CountrylanguagePK countrylanguagePK ) {
		this.countrylanguagePK = countrylanguagePK;
	}

	public Countrylanguage( CountrylanguagePK countrylanguagePK, String isOfficial, float percentage ) {
		this.countrylanguagePK = countrylanguagePK;
		this.isOfficial = isOfficial;
		this.percentage = percentage;
	}

	public Countrylanguage( String countryCode, String language ) {
		this.countrylanguagePK = new CountrylanguagePK( countryCode, language );
	}

	@Override
	public boolean equals( Object object ) {
		if ( ! ( object instanceof Countrylanguage ) ) { return false; }
		Countrylanguage other = ( Countrylanguage ) object;
		if ( this.countrylanguagePK == null && other.countrylanguagePK != null || this.countrylanguagePK != null
				&& !this.countrylanguagePK.equals( other.countrylanguagePK ) ) { return false; }
		return true;
	}

	public Country getCountry( ) {
		return country;
	}

	public CountrylanguagePK getCountrylanguagePK( ) {
		return countrylanguagePK;
	}

	public String getIsOfficial( ) {
		return isOfficial;
	}

	public float getPercentage( ) {
		return percentage;
	}

	@Override
	public int hashCode( ) {
		int hash = 0;
		hash += countrylanguagePK != null ? countrylanguagePK.hashCode( ) : 0;
		return hash;
	}

	public void setCountry( Country country ) {
		this.country = country;
	}

	public void setCountrylanguagePK( CountrylanguagePK countrylanguagePK ) {
		this.countrylanguagePK = countrylanguagePK;
	}

	public void setIsOfficial( String isOfficial ) {
		this.isOfficial = isOfficial;
	}

	public void setPercentage( float percentage ) {
		this.percentage = percentage;
	}

	@Override
	public String toString( ) {
		return "entidades.Countrylanguage[ countrylanguagePK=" + countrylanguagePK + " ]";
	}

}
