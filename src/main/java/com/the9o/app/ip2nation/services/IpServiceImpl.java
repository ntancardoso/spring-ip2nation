package com.the9o.app.ip2nation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.the9o.app.ip2nation.domain.Country;
import com.the9o.app.ip2nation.domain.Ip;
import com.the9o.app.ip2nation.repo.CountryRepository;
import com.the9o.app.ip2nation.repo.IpRepository;
import com.the9o.app.ip2nation.utils.NetUtils;

@Service
public class IpServiceImpl implements IpService {
	
	private IpRepository ipRepository;
	private CountryRepository countryRepository;
	
	@Autowired
	public IpServiceImpl(IpRepository ipRepository, CountryRepository countryRepository) {
		this.ipRepository = ipRepository;
		this.countryRepository = countryRepository;
	}

	@Override
	public Ip createIp(String countryCode, Long ip) {
		
		Country country = countryRepository.findOne(countryCode);
		
		if (country == null) 
			throw new RuntimeException("Country code does not exist");
		
		return ipRepository.save(new Ip(country,ip));
	}

	@Override
	public Ip createIp(String countryCode, String ip) {
		Country country = countryRepository.findOne(countryCode);
		
		if (country == null) 
			throw new RuntimeException("Country code does not exist");
		
		return ipRepository.save(new Ip(country,NetUtils.inetAton(ip)));
	}

	@Override
	public long total() {
		return ipRepository.count();
	}

	@Override
	public Iterable<Ip> lookup() {
		return ipRepository.findAll();
	}

}
