package com.the9o.app.ip2nation;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.the9o.app.ip2nation.domain.Country;
import com.the9o.app.ip2nation.web.Location;



@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTests {
	
	static final String COUNTRY_PAYLOAD = "{\"code\": \"ph\", \"isoCode2\": \"PH\", \"isoCode3\": \"PHL\", \"isoCountry\": \"Philippines\", \"country\": \"Philippines\", \"lat\": 13, \"lon\": 122}";
	
	
	@Autowired 
	WebApplicationContext context;
	
	@Autowired 
	FilterChainProxy filterChain;

	MockMvc mvc;

	@Before
	public void setUp() {
		this.mvc = webAppContextSetup(context).addFilters(filterChain).build();
		SecurityContextHolder.clearContext();
	}

	@Test
	public void allowsAccessToRootResource() throws Exception {

		mvc.perform(get("/").
				accept(MediaTypes.HAL_JSON)).
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
				andExpect(status().isOk()).
				andDo(print());
	}

	@Test
	public void rejectsPostAccessToResource() throws Exception {

		mvc.perform(post("/countries").
				content(COUNTRY_PAYLOAD).
				accept(MediaTypes.HAL_JSON)).
				andExpect(status().isUnauthorized()).
				andDo(print());
	}
	
	@Test
	public void checkIpAddress() throws Exception {

		ObjectMapper mapper = new ObjectMapper();

		String content = mvc.perform(get("/locate/172.217.25.14")).
				andReturn().getResponse().getContentAsString();
		Location loc = mapper.readValue(content, Location.class);

		assertThat(loc.getCountry().getIsoCode2(), is("US"));
	}


	@Test
	public void allowsPostRequestForAdmin() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
		headers.set(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(("admin:password").getBytes())));

		mvc.perform(get("/countries").
				headers(headers)).
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
				andExpect(status().isOk()).
				andDo(print());

		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		String location = mvc
				.perform(post("/countries").
						content(COUNTRY_PAYLOAD).
						headers(headers)).
				andExpect(status().isCreated()).
				andDo(print()).
				andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

		ObjectMapper mapper = new ObjectMapper();

		String content = mvc.perform(get(location)).
				andReturn().getResponse().getContentAsString();
		Country country = mapper.readValue(content, Country.class);

		assertThat(country.getIsoCode2(), is("PH"));
		assertThat(country.getIsoCode3(), is("PHL"));
		assertThat(country.getCountry(), is("Philippines"));
		assertThat(country.getLat(), is(13D));
		assertThat(country.getLon(), is(122D));
	}
	
	
	@Test
	public void getCountryByIp() throws Exception {

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE);
		headers.set(HttpHeaders.AUTHORIZATION, "Basic " + new String(Base64.encode(("admin:password").getBytes())));

		mvc.perform(get("/countries").
				headers(headers)).
				andExpect(content().contentTypeCompatibleWith(MediaTypes.HAL_JSON)).
				andExpect(status().isOk()).
				andDo(print());

		headers.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

		String location = mvc
				.perform(post("/countries").
						content(COUNTRY_PAYLOAD).
						headers(headers)).
				andExpect(status().isCreated()).
				andDo(print()).
				andReturn().getResponse().getHeader(HttpHeaders.LOCATION);

		ObjectMapper mapper = new ObjectMapper();

		String content = mvc.perform(get(location)).
				andReturn().getResponse().getContentAsString();
		Country country = mapper.readValue(content, Country.class);

		assertThat(country.getIsoCode2(), is("PH"));
		assertThat(country.getIsoCode3(), is("PHL"));
		assertThat(country.getCountry(), is("Philippines"));
		assertThat(country.getLat(), is(13D));
		assertThat(country.getLon(), is(122D));
	}

}
