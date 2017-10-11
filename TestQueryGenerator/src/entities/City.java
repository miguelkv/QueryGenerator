package entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table( name = "city" )
public class City implements Serializable {

	private static final long	serialVersionUID	= 1L;

	@JoinColumn( name = "CountryCode", referencedColumnName = "Code" )
	@ManyToOne( optional = false, fetch = FetchType.LAZY )
	private Country				countryCode;

	@Basic( optional = false )
	@Column( name = "District" )
	private String				district;

	@Id
	@GeneratedValue( strategy = GenerationType.IDENTITY )
	@Basic( optional = false )
	@Column( name = "ID" )
	private Integer				id;

	@Basic( optional = false )
	@Column( name = "Name" )
	private String				name;

	@Basic( optional = false )
	@Column( name = "Population" )
	private int					population;

	public City( ) {
	}

	public City( Integer id ) {
		this.id = id;
	}

	public City( Integer id, String name, String district, int population ) {
		this.id = id;
		this.name = name;
		this.district = district;
		this.population = population;
	}

	@Override
	public boolean equals( Object object ) {
		if ( ! ( object instanceof City ) ) { return false; }
		City other = ( City ) object;
		if ( this.id == null && other.id != null || this.id != null && !this.id.equals( other.id ) ) { return false; }
		return true;
	}

	public Country getCountryCode( ) {
		return countryCode;
	}

	public String getDistrict( ) {
		return district;
	}

	public Integer getId( ) {
		return id;
	}

	public String getName( ) {
		return name;
	}

	public int getPopulation( ) {
		return population;
	}

	@Override
	public int hashCode( ) {
		int hash = 0;
		hash += id != null ? id.hashCode( ) : 0;
		return hash;
	}

	public void setCountryCode( Country countryCode ) {
		this.countryCode = countryCode;
	}

	public void setDistrict( String district ) {
		this.district = district;
	}

	public void setId( Integer id ) {
		this.id = id;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public void setPopulation( int population ) {
		this.population = population;
	}

	@Override
	public String toString( ) {
		return "City [countryCode=" + countryCode + ", district=" + district + ", id=" + id + ", name=" + name
				+ ", population=" + population + "]";
	}

}
