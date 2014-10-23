/**
 * 
 */
package com.gopivotal.ce.gemfire.controller;

import io.pivotal.ce.gemfire.fastfootshoes.model.Alert;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.AlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lshannon
 *
 */
@Controller
public class AlertsController {
	
	@Autowired
	private AlertRepository alertRepository;
	
	@RequestMapping("/alerts")
    public String listOpenOrders(Model model) {
		Iterable<Alert> alerts = alertRepository.findAll();
		model.addAttribute("alerts",alerts);
        return "alerts";
    }

}
