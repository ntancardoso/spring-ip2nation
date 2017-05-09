package com.the9o.app.ip2nation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.the9o.app.ip2nation.domain.Country;
import com.the9o.app.ip2nation.repo.CountryRepository;
import com.the9o.app.ip2nation.repo.IpRepository;
import com.the9o.app.ip2nation.security.SecurityUtils;
import com.the9o.app.ip2nation.services.CountryService;
import com.the9o.app.ip2nation.services.IpService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	CountryService countryService;
	
	@Autowired
	IpService ipService;
	
	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}
	
	@Test
	public void rejectNoAuth() {
		try {
			countryService.createCountry("zz", "ZZ", "ZZZ", "TEST ZZZ", "ZZZZ", 13D, 122D);
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}
		
		try {
			SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");
			countryService.createCountry("ph", "PH", "PHL", "Philippines", "Philippines", 13D, 122D);
			SecurityContextHolder.clearContext();
			ipService.createIp("ph", 461127680L);
			fail("Expected a security error");
		} catch (AuthenticationCredentialsNotFoundException e) {
			// expected
		}
	}
	
	@Test
	public void allowAuth() {
		SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");
		countryService.createCountry("ph", "PH", "PHL", "Philippines", "Philippines", 13D, 122D);
		ipService.createIp("ph", 461281280L);
	}
	
	
	@Test
	public void getIpCountry() {
		SecurityUtils.runAs("system", "system", "ROLE_USER", "ROLE_ADMIN");
		countryService.createCountry("ph", "PH", "PHL", "Philippines", "Philippines", 13D, 122D);
		countryService.createCountry("us", "US", "USA", "United States of America", "United States", 38D, -97D);
		
		//ipService.createIp("us", "172.110.80.0");
		//ipService.createIp("us", 1897725952L);
		ipService.createIp("ph", 831520768L);
		Country c = countryService.findByIp("172.217.25.14");
		assertEquals(c.getCountry(), "United States");
	}
	
	
	@Configuration
	class MockServiceProvider {
		
		@Bean
		public CountryRepository countryRepository() {
			return Mockito.mock(CountryRepository.class);
		}
		
		@Bean
		public IpRepository ipRepository() {
			return Mockito.mock(IpRepository.class);
		}
		
	}
	
}
