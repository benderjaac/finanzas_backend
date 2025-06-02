package com.primeng.primeng.services;

import com.primeng.primeng.exceptions.BadRequestException;
import com.primeng.primeng.security.CustomUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService {

    public CustomUserDetails getUserLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            throw new BadRequestException("autenticacion requerida");
        }
        Object principal = authentication.getPrincipal();
        if (!(principal instanceof CustomUserDetails)) {
            throw new BadRequestException("Principal inválido");
        }

        return (CustomUserDetails) principal;
    }
}
