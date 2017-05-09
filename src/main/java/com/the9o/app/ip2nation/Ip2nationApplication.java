package com.the9o.app.ip2nation;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.the9o.app.ip2nation.security.SecurityUtils;
import com.the9o.app.ip2nation.services.CountryService;
import com.the9o.app.ip2nation.services.IpService;

@SpringBootApplication
public class Ip2nationApplication implements CommandLineRunner {
	
	public static final Logger LOGGER = Logger.getLogger(Ip2nationApplication.class.getName());
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private IpService ipService;

	public static void main(String[] args) {
		SpringApplication.run(Ip2nationApplication.class, args);
	}
	
	
	@Override
	public void run(String... arg0) throws Exception {

		initializeData();
	}
	
	private void initializeData() throws Exception {

		SecurityUtils.runAs("system", "system", "ROLE_ADMIN");
		if(countryService.total()==0) {
			importCountries().forEach(c->countryService.createCountry(c.code,  c.iso_code_2,  c.iso_code_3,  c.iso_country, c.country, 
					c.lat, c.lon));
			LOGGER.log(Level.INFO,"Initialized with "+countryService.total()+" countries");
		}
		if(ipService.total()==0) {
			importIps().forEach(i->ipService.createIp(i.country,i.ip));
			LOGGER.log(Level.INFO,"Initialized "+ipService.total()+" IPs");
		}
		SecurityContextHolder.clearContext();
	}
	
	
	static class CountryFile {
		private String code, iso_code_2, iso_code_3, iso_country, country;
		private Double lat, lon;
	}
	
	static class IpFile {
		private Long ip;
		private String country;
	}
	
	static List<CountryFile> importCountries() throws IOException {
		return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.readValue(CountryFile.class.getResourceAsStream("/countries.json"),
						new TypeReference<List<CountryFile>>(){});
	}
	
	static List<IpFile> importIps() throws IOException {
		return new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
				.readValue(IpFile.class.getResourceAsStream("/ips.json"),
						new TypeReference<List<IpFile>>(){});
	}
}
