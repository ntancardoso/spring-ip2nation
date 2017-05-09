package com.the9o.app.ip2nation.services;

import com.the9o.app.ip2nation.domain.Country;

public interface CountryService {
	
	Country createCountry(String code, String isoCode2, String isoCode3, String isoCountry, String country, Double lat, Double lon);

	Country findByIp(String ipAddress);
	
	long total();

	Iterable<Country> lookup();
	
}
