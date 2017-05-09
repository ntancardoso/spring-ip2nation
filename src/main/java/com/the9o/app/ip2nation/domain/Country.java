package com.the9o.app.ip2nation.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;


@Entity
@JsonDeserialize(builder = Country.CountryBuilder.class)
public class Country {

	@Id
	private String code;
	private String isoCode2;
	private String isoCode3;
	private String isoCountry;
	private String country;
	private Double lat;
	private Double lon;
	
	@OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
	private Set<Ip> ips;


	Country() {}

	protected Country(String code, String isoCode2, String isoCode3, String isoCountry, String country, Double lat, Double lon) {
		this.code = code;
		this.isoCode2 = isoCode2;
		this.isoCode3 = isoCode3;
		this.isoCountry = isoCountry;
		this.country = country;
		this.lat = lat;
		this.lon = lon;
	}

	public String getCode() {
		return code;
	}

	public String getIsoCode2() {
		return isoCode2;
	}

	public String getIsoCode3() {
		return isoCode3;
	}
	
	public String getIsoCountry() {
		return isoCountry;
	}

	public String getCountry() {
		return country;
	}


	public Double getLat() {
		return lat;
	}

	public Double getLon() {
		return lon;
	}
	
	public Set<Ip> getIps() {
		return ips;
	}

	
	public static CountryBuilder builder() {
		return new CountryBuilder();
	}
	
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(buildMethodName = "build", withPrefix = "set")
	public static class CountryBuilder {
		
		private String code;
		private String isoCode2;
		private String isoCode3;
		private String isoCountry;
		private String country;
		private Double lat;
		private Double lon;
		
		
		public CountryBuilder setCode(String code) {
			this.code = code;
			return this;
		}
		public CountryBuilder setIsoCode2(String isoCode2) {
			this.isoCode2 = isoCode2;
			return this;
		}
		public CountryBuilder setIsoCode3(String isoCode3) {
			this.isoCode3 = isoCode3;
			return this;
		}
		public CountryBuilder setIsoCountry(String isoCountry) {
			this.isoCountry = isoCountry;
			return this;
		}
		public CountryBuilder setCountry(String country) {
			this.country = country;
			return this;
		}
		public CountryBuilder setLat(Double lat) {
			this.lat = lat;
			return this;
		}
		public CountryBuilder setLon(Double lon) {
			this.lon = lon;
			return this;
		}
		
		public Country build() {
			return new Country(code, isoCode2, isoCode3, isoCountry, country, lat, lon);
		}
		
	}
	
}
