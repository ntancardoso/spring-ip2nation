package com.the9o.app.ip2nation.web;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.the9o.app.ip2nation.domain.Country;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location extends ResourceSupport {
	
	private final String ipAddress;
	private final Country country;

    @JsonCreator
    public Location(@JsonProperty("ipAddress") String ipAddress, @JsonProperty("country") Country country) {
        this.ipAddress = ipAddress;
        this.country = country;
    }

	public String getIpAddress() {
		return ipAddress;
	}

	public Country getCountry() {
		return country;
	}

}
