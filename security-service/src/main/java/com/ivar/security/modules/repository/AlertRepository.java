package com.ivar.security.modules.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ivar.security.modules.entity.SecurityAlert;

public interface AlertRepository
extends JpaRepository<SecurityAlert, Long> {

	List<SecurityAlert> findByStatus(String string);
	
}
