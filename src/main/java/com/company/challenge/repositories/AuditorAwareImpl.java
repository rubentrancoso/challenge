package com.company.challenge.repositories;

import org.springframework.data.domain.AuditorAware;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		return null;
	}

}
