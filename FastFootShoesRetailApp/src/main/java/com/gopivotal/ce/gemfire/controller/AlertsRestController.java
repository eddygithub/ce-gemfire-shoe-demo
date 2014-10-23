package com.gopivotal.ce.gemfire.controller;

import io.pivotal.ce.gemfire.fastfootshoes.repositories.AlertRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlertsRestController {
	
	@Autowired
	AlertRepository alertsRepository;
	
	@RequestMapping("/alertCount")
	public long count() {
		return alertsRepository.count();
	}
	

}
