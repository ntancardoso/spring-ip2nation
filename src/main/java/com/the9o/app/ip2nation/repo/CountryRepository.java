package com.the9o.app.ip2nation.repo;

import java.util.Set;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.the9o.app.ip2nation.domain.Country;

public interface CountryRepository extends PagingAndSortingRepository<Country, String> {
	
	Country findFirstByIpsIpLessThanOrderByIpsIpDesc(Long ipAddress);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Country> S save(S entity);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Country> Iterable<S> save(Iterable<S> entities);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(String id);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Country entity);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Iterable<? extends Country> entities);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void deleteAll();

}
