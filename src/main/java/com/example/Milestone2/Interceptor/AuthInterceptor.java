package com.example.Milestone2.Interceptor;


import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthInterceptor implements HandlerInterceptor, BasicAuthInterceptor {


//    @Override
//    public boolean headerCheck(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        String authHeader = request.getHeader("X-Auth_Token");
//        if (authHeader != null) {
//            return true;
//        } else {
//            response.setStatus(401);
//            return false;
//        }
//    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("*****Inside Prehandle********");
        String authHeader = request.getHeader("X-Auth-Token");
       // if (authHeader == null||authHeader.isEmpty()||!authHeader.equalsIgnoreCase("Token")) {
        if (authHeader == null||authHeader.isEmpty()) {
            response.setContentType("application/json");
            response.setStatus(401);
            return false;
        }
        return true;
    }

    @Override
    public boolean headerCheck(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        return false;
    }
}