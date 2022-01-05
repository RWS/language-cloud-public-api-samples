package com.authentication;

import com.model.User;
import com.repository.Repository;
import com.rws.lt.lc.publicapi.sdk.auth.AuthenticationService;
import com.rws.lt.lc.publicapi.sdk.auth.CustomServiceAuthenticationHandler;
import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CustomAuthenticationHandler extends CustomServiceAuthenticationHandler {
    Repository repository = new Repository();

    public CustomAuthenticationHandler(AuthenticationService authenticationService) {
        super(authenticationService);
    }

    @Override
    public ServiceCredentials getServiceCredentials() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        return repository.getServiceCredentialsById(user.getUserId());
    }

    @Override
    public String getTraceId() {
        return UUID.randomUUID().toString();
    }
}
