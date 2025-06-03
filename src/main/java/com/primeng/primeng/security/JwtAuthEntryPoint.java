package com.primeng.primeng.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primeng.primeng.models.response.HttpError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint  {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException {

        System.out.println("🔒 Auth Entry Point ejecutado");

        HttpError error = new HttpError(
                "No autenticado",
                authException.getMessage(),
                request.getRequestURI(),
                HttpServletResponse.SC_UNAUTHORIZED
        );

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), error);
    }
}
