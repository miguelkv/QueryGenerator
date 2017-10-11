package entities;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table( name = "country" )
public class Country implements Serializable {

	private static final long				serialVersionUID	= 1L;

	@Column( name = "Capital" )
	private Integer							capital;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "countryCode", fetch = FetchType.LAZY )
	private Collection< City >				cityCollection;

	@Id
	@Basic( optional = false )
	@Column( name = "Code" )
	private String							code;

	@Basic( optional = false )
	@Column( name = "Code2" )
	private String							code2;

	@Basic( optional = false )
	@Column( name = "Continent" )
	private String							continent;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "country", fetch = FetchType.LAZY )
	private Collection< Countrylanguage >	countrylanguageCollection;

	@Column( name = "GNP" )
	private Float							gnp;

	@Column( name = "GNPOld" )
	private Float							gNPOld;

	@Basic( optional = false )
	@Column( name = "GovernmentForm" )
	private String							governmentForm;

	@Column( name = "HeadOfState" )
	private String							headOfState;

	@Column( name = "IndepYear" )
	private Short							indepYear;

	@Column( name = "LifeExpectancy" )
	private Float							lifeExpectancy;

	@Basic( optional = false )
	@Column( name = "LocalName" )
	private String							localName;

	@Basic( optional = false )
	@Column( name = "Name" )
	private String							name;

	@Basic( optional = false )
	@Column( name = "Population" )
	private Long							population;

	@Basic( optional = false )
	@Column( name = "Region" )
	private String							region;

	@Basic( optional = false )
	@Column( name = "SurfaceArea" )
	private float							surfaceArea;

	public Country( ) {
	}

	public Country( String code ) {
		this.code = code;
	}

	public Country( String code, String name, String continent, String region, float surfaceArea, Long population,
			String localName, String governmentForm, String code2 ) {
		this.code = code;
		this.name = name;
		this.continent = continent;
		this.region = region;
		this.surfaceArea = surfaceArea;
		this.population = population;
		this.localName = localName;
		this.governmentForm = governmentForm;
		this.code2 = code2;
	}

	@Override
	public boolean equals( Object object ) {
		if ( ! ( object instanceof Country ) ) { return false; }
		Country other = ( Country ) object;
		if ( this.code == null && other.code != null
				|| this.code != null && !this.code.equals( other.code ) ) { return false; }
		return true;
	}

	public Integer getCapital( ) {
		return capital;
	}

	public Collection< City > getCityCollection( ) {
		return cityCollection;
	}

	public String getCode( ) {
		return code;
	}

	public String getCode2( ) {
		return code2;
	}

	public String getContinent( ) {
		return continent;
	}

	public Collection< Countrylanguage > getCountrylanguageCollection( ) {
		return countrylanguageCollection;
	}

	public Float getGnp( ) {
		return gnp;
	}

	public Float getGNPOld( ) {
		return gNPOld;
	}

	public String getGovernmentForm( ) {
		return governmentForm;
	}

	public String getHeadOfState( ) {
		return headOfState;
	}

	public Short getIndepYear( ) {
		return indepYear;
	}

	public Float getLifeExpectancy( ) {
		return lifeExpectancy;
	}

	public String getLocalName( ) {
		return localName;
	}

	public String getName( ) {
		return name;
	}

	public Long getPopulation( ) {
		return population;
	}

	public String getRegion( ) {
		return region;
	}

	public float getSurfaceArea( ) {
		return surfaceArea;
	}

	@Override
	public int hashCode( ) {
		int hash = 0;
		hash += code != null ? code.hashCode( ) : 0;
		return hash;
	}

	public void setCapital( Integer capital ) {
		this.capital = capital;
	}

	public void setCityCollection( Collection< City > cityCollection ) {
		this.cityCollection = cityCollection;
	}

	public void setCode( String code ) {
		this.code = code;
	}

	public void setCode2( String code2 ) {
		this.code2 = code2;
	}

	public void setContinent( String continent ) {
		this.continent = continent;
	}

	public void setCountrylanguageCollection( Collection< Countrylanguage > countrylanguageCollection ) {
		this.countrylanguageCollection = countrylanguageCollection;
	}

	public void setGnp( Float gnp ) {
		this.gnp = gnp;
	}

	public void setGNPOld( Float gNPOld ) {
		this.gNPOld = gNPOld;
	}

	public void setGovernmentForm( String governmentForm ) {
		this.governmentForm = governmentForm;
	}

	public void setHeadOfState( String headOfState ) {
		this.headOfState = headOfState;
	}

	public void setIndepYear( Short indepYear ) {
		this.indepYear = indepYear;
	}

	public void setLifeExpectancy( Float lifeExpectancy ) {
		this.lifeExpectancy = lifeExpectancy;
	}

	public void setLocalName( String localName ) {
		this.localName = localName;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public void setPopulation( Long population ) {
		this.population = population;
	}

	public void setRegion( String region ) {
		this.region = region;
	}

	public void setSurfaceArea( float surfaceArea ) {
		this.surfaceArea = surfaceArea;
	}

	@Override
	public String toString( ) {
		return "Country [capital=" + capital + ", cityCollection=" + cityCollection + ", code=" + code + ", code2="
				+ code2 + ", continent=" + continent + ", countrylanguageCollection=" + countrylanguageCollection
				+ ", gnp=" + gnp + ", gNPOld=" + gNPOld + ", governmentForm=" + governmentForm + ", headOfState="
				+ headOfState + ", indepYear=" + indepYear + ", lifeExpectancy=" + lifeExpectancy + ", localName="
				+ localName + ", name=" + name + ", population=" + population + ", region=" + region + ", surfaceArea="
				+ surfaceArea + "]";
	}

}
