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
    public ModelAndView login(HttpServletRequest request) {
        ModelAndView vm = new ModelAndView();
      
        
        if(request.getSession().getAttribute("role") != null){
        	if(request.getSession().getAttribute("role").equals("ADMINISTRADOR")) {
        		// TODO: redirect to main administrador
        		vm.setViewName("layout");
        	}else {
        		if(request.getSession().getAttribute("role").equals("COORDINADOR")) {
        			vm.setViewName("layout");
        			// TODO: redirect to main coordinador
            	}else {
            		vm.setViewName("login");
            		vm.addObject("loginRequest", new UserLogin());
            	}
        	}
        }else {
        	  vm.setViewName("login");
        	   vm.addObject("loginRequest", new UserLogin());
        }
     
        return vm;
    }
    
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView vm = new ModelAndView();
        System.out.println(request.getSession().getAttribute("usuario"));
        System.out.println(request.getSession().getAttribute("role"));
        try {
        	 User us = (User) service.<String>getOneByOneField("user_name", request.getSession().getAttribute("usuario").toString());
             us.setActive(false);
             this.service.save(us);
             vm.addObject("msg", "El usuario cerro sesion");
		} catch (Exception e) {
			
			// TODO: handle exception
		}
       
        request.getSession().invalidate();
        vm.setViewName("login");
        vm.addObject("loginRequest", new UserLogin());
        
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
             		System.out.println(" esta vacio");
             		User us = (User) service.<String>getOneByOneField("user_name", log.getUserName());
             		
             		if(us.getPassword().equals(log.getPassword()) ) {
             			System.out.println("hay un usurio");
             			//main
             			if(us.getActive()) {
             				System.out.println("hay un usuario con role en otra cuenta" + request.getSession().getAttribute("role"));
                    		 vm.setViewName("login");
                    		 vm.addObject("loginRequest", new UserLogin());
                    		 vm.addObject("msg", "Hay una sesion activa");
                    		 
            				
             			}else {
             				request.getSession().setAttribute("usuario", us.getUserName());
                 			request.getSession().setAttribute("role", us.getRole().getName());
                 			us.setActive(true);
                 			service.save(us);
                 			 vm.setViewName("login");
                 			vm.addObject("loginRequest", new UserLogin());
                 			 vm.addObject("msg", "El usuario se logueo");
                 			//TODO: Redirect here to main page
             			}
             			
             			
             			
             		}else {
             			System.out.println("error datos");
                		 vm.setViewName("login");
                		 vm.addObject("loginRequest", new UserLogin());
         				 vm.addObject("msg", "El usuario o la contraseña son incorrectos");
             		}
             		
             	}else {
             		System.out.println("hay un usuario con role" + request.getSession().getAttribute("role"));
             		 vm.setViewName("login");
             		 vm.addObject("loginRequest", new UserLogin());
             		 vm.addObject("msg", "ya hay una sesion activa");
     				
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
			  vm.addObject("msg", "algo sucedio");
			// TODO: handle exception
		}
       
        
        return vm;
    }
    
}
