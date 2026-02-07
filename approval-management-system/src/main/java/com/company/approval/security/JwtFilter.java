package com.company.approval.security;

import com.company.approval.entity.User;
import com.company.approval.repository.UserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter implements Filter {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String header = httpRequest.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);

            try {
                String email = jwtUtil.extractEmail(token);

                User user = userRepository.findByEmail(email).orElse(null);
                if (user != null) {
                    httpRequest.setAttribute("email", email);
                    httpRequest.setAttribute("role", user.getRole().name());
                }
            } catch (Exception ignored) {}
        }

        chain.doFilter(request, response);
    }
}
