package com.example.skul5.controllers;

import com.example.skul5.domain.UserLogin;
import com.example.skul5.domain.Student;
import com.example.skul5.domain.User;
import com.example.skul5.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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



    @GetMapping(value = {"/", "/index", "/inicio"})
    public ModelAndView index() {

        ModelAndView vm = new ModelAndView();

        vm.setViewName("login");
        vm.addObject("loginRequest", new UserLogin());
        
        return vm;
    }

    @GetMapping(value = {"/login"})
    public ModelAndView login() {
        ModelAndView vm = new ModelAndView();
        vm.setViewName("login");
        vm.addObject("loginRequest", new UserLogin());
        return vm;
    }
    
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView vm = new ModelAndView();
        request.getSession().invalidate();
        vm.setViewName("login");
        vm.addObject("loginObject", new UserLogin());
        return vm;
    }
    
    @PostMapping("/login")
    public ModelAndView login(HttpServletRequest request,@Valid @ModelAttribute UserLogin log, BindingResult result) {
        ModelAndView vm = new ModelAndView();
        
        try {
        	 if (!result.hasErrors()) {
        		 System.out.println("no tene errores");
             	String usuario = (String) request.getSession().getAttribute("usuario");
             	System.out.println(usuario);
             	if(usuario == null) {
             		System.out.println("No esta vacio");
             		User us = (User) service.<String>getOneByOneField("user_name", log.getUserName());
             		if(us.getPassword().equals(log.getPassword()) && (!us.getActive())) {
             			System.out.println("hay un usurio");
             			//main
             			request.getSession().setAttribute("usuario", us.getName());
             			request.getSession().setAttribute("role", us.getRole().getName());
             			us.setActive(true);
             			service.save(us);
             			 vm.setViewName("login");
             			vm.addObject("loginRequest", new UserLogin());
             			 vm.addObject("msg", "El usuario se logueo");
             			//TODO: Redirect here to main page
             			
             			
             		}else {
             			if(us.getActive()) {
             				 vm.setViewName("login");
             				 vm.addObject("loginRequest", new UserLogin());
             				vm.addObject("msg", "Tienes otra cuenta activa");
             				
             			}else {
             				  vm.setViewName("login");
             				 vm.addObject("loginRequest", new UserLogin());
             				 vm.addObject("msg", "El usuario o la contraseña son incorrectos");
             				  
             			}
             		}
             	}else {
             		System.out.println("esta vacio");
             		 vm.setViewName("login");
             		 vm.addObject("loginRequest", new UserLogin());
     				
             		//redirect to main
             	}
             	
             	
                 //TODO: add validation 
             }else {
            	 vm.addObject("loginRequest", new UserLogin());
 				 vm.addObject("msg", "El usuario o la contraseña son incorrectos");
             }
		} catch (Exception e) {
			System.out.println("hay errores:");
			  vm.setViewName("login");
			  e.printStackTrace();
			  vm.addObject("loginRequest", new UserLogin());
			// TODO: handle exception
		}
       
        
        return vm;
    }
    
}
