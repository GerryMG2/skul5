package com.example.skul5.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;

public class SesionInterceptorCoordinador implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println("entro al interceptor coordinador");
		if(request.getSession().getAttribute("role") != null) {
			if(request.getSession().getAttribute("role").equals("COORDINADOR")) {
				System.out.println("es coordinador");
				return true;
			}else {
				response.sendRedirect("/");
				return false;
			}
			
			
			
		}else {
			response.sendRedirect("/");
			return false;
		}
		
	}

}
