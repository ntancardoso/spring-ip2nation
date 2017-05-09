package com.the9o.app.ip2nation.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Ip {

	@Id
	@GeneratedValue
	private Long id;
	private Long ip;
	
	@ManyToOne
	private Country country;
	
	Ip() {}
	
	public Ip(Country country, Long ip) {
		this.country = country;
		this.ip = ip;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIp() {
		return ip;
	}

	public void setIp(Long ip) {
		this.ip = ip;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
	
}
