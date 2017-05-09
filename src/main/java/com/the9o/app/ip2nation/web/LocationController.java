package com.the9o.app.ip2nation.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.the9o.app.ip2nation.domain.Country;
import com.the9o.app.ip2nation.services.CountryService;

@RestController
public class LocationController {
	
	@Autowired
	private CountryService countryService;
	
    /**
     * Lookup the Country of the IP Address.
     *
     * @param IP Address
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path="/locate/{ipAddress:.+}")
    public HttpEntity<Location> getCountryByIp(@PathVariable(value = "ipAddress") String ipAddress) {
    	
    	//System.out.println(NetUtils.inetAton(ipAddress));
    	Country c = countryService.findByIp(ipAddress);
    	System.out.println(c.getCountry());
    	
    	Location loc = new Location(ipAddress, c);
    	loc.add(linkTo(methodOn(LocationController.class).getCountryByIp(ipAddress)).withSelfRel());

        return new ResponseEntity<Location>(loc, HttpStatus.OK);
        
        
    }

}
