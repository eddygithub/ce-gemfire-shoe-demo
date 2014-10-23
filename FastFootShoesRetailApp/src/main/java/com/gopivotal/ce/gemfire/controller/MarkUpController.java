package com.gopivotal.ce.gemfire.controller;

import io.pivotal.ce.gemfire.fastfootshoes.model.MarkUp;
import io.pivotal.ce.gemfire.fastfootshoes.repositories.MarkUpRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MarkUpController {
	
	@Autowired
	private MarkUpRepository markUpRepository;
	
	@RequestMapping("/listMarkUps")
	public String getMarkUps(Model model) {
		Iterable<MarkUp> markUps = markUpRepository.findAll();
		model.addAttribute("markUps", markUps);
		return "listMarkUps";
	}

}
