package com.example.skul5.controllers;

import com.example.skul5.domain.User;
import com.example.skul5.domain.UserLogin;
import com.example.skul5.domain.Student;
import com.example.skul5.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Component
public class MainController {

	private final Service<User> service;

	@Autowired
	public MainController(Service<User> service) {
		this.service = service;
		this.service.ConfigureType(User.class);
	}

	@GetMapping(value = { "/" })
	public ModelAndView index() {
		ModelAndView vm = new ModelAndView();
		vm.setViewName("index");
		vm.addObject("student", new Student());
		// TODO: enable session state
		return vm;
	}

	@GetMapping(value = { "/login" })
	public ModelAndView login() {
		ModelAndView vm = new ModelAndView();
		vm.setViewName("login");
		vm.addObject("loginRequest", new UserLogin());
		// TODO: enable session state
		return vm;
	}

	@PostMapping("/login")
	public ModelAndView login(@Valid @ModelAttribute UserLogin log, BindingResult result) {
		ModelAndView vm = new ModelAndView();
		vm.setViewName("index");
		if (!result.hasErrors()) {
			vm.setViewName("login");
			// TODO: add validation
		}

		return vm;
	}

}
