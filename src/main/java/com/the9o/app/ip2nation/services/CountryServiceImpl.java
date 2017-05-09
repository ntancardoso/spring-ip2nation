package com.the9o.app.ip2nation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.the9o.app.ip2nation.domain.Country;
import com.the9o.app.ip2nation.repo.CountryRepository;
import com.the9o.app.ip2nation.utils.NetUtils;

@Service
public class CountryServiceImpl implements CountryService {
	
	private CountryRepository countryRepository;

	@Autowired
	public CountryServiceImpl(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@Override
	public Country createCountry(String code, String isoCode2, String isoCode3, String isoCountry, String country, 
			Double lat, Double lon) {
		if (!countryRepository.exists(code))
			return countryRepository.save(Country.builder().
				setCode(code).
				setIsoCode2(isoCode2).
				setIsoCode3(isoCode3).
				setIsoCountry(isoCountry).
				setCountry(country).
				setLat(lat).
				setLon(lon).
				build());
		
		return null;
	}

	@Override
	public Country findByIp(String ipAddress) {
		return countryRepository.findFirstByIpsIpLessThanOrderByIpsIpDesc(NetUtils.inetAton(ipAddress));
	}

	@Override
	public long total() {
		return countryRepository.count();
	}

	@Override
	public Iterable<Country> lookup() {
		return countryRepository.findAll();
	}

}
