package com.ambientese.grupo5.Filters;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ambientese.grupo5.Services.UsuarioService.JWTUtil;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    public AuthFilter(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private boolean isAuthorized(String cargo, String requestURI) {
        List<String> gestorBlockedRoutes = Arrays.asList();
        List<String> consultorBlockedRoutes = Arrays.asList("/auth/Funcionario/*", "/auth/Usuarios/*", "/funcionarios");

        return switch (cargo) {
            case "Admin" -> true;
            case "Gestor" -> !matchesAny(gestorBlockedRoutes, requestURI);
            case "Consultor" -> !matchesAny(consultorBlockedRoutes, requestURI);
            default -> false;
        };
    }

    private boolean matchesAny(List<String> routes, String requestURI) {
        for (String route : routes) {
            if (requestURI.startsWith(route.replace("*", ""))) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
        throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            DecodedJWT jwt = jwtUtil.validateToken(token);
            if (jwt != null) {
                String cargo = jwt.getClaim("cargo").asString();
                String requestURI = request.getRequestURI();

                if (isAuthorized(cargo, requestURI)) {
                    filterChain.doFilter(request, response);
                    return;
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Você não tem permissão para acessar esta rota.");
                    return;
                }
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
