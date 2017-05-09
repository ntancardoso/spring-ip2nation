package com.the9o.app.ip2nation.services;

import com.the9o.app.ip2nation.domain.Ip;

public interface IpService {
	
	Ip createIp(String countryCode, Long ip);
	
	Ip createIp(String countryCode, String ip);
	
	long total();

	Iterable<Ip> lookup();

}
