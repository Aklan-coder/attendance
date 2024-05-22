package com.softcorridor.attendance.config;

import com.softcorridor.attendance.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;

        if (authHeader != null && !authHeader.isEmpty() && authHeader.startsWith("Bearer ")) {

            jwtToken = authHeader.substring(7);
            var token = tokenRepository.getToken(jwtToken).orElse(null);
            if(token != null ){
                if(!token.isLogout()) {
                    tokenRepository.logOut(token.getId());
                }else{
                    log.error("Logout already!!!");
                }
            }
        }else{
            return;
        }
    }
}
