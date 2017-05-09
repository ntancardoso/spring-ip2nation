package com.the9o.app.ip2nation.repo;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import com.the9o.app.ip2nation.domain.Ip;

public interface IpRepository extends PagingAndSortingRepository<Ip, Long> {
	
	Ip findByIp(Long ip);
	
	Ip findByIpAndCountryCode(Long ip, String countryCode);
	
	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Ip> S save(S entity);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	<S extends Ip> Iterable<S> save(Iterable<S> entities);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Long id);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Ip entity);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void delete(Iterable<? extends Ip> entities);

	@Override
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void deleteAll();

}
