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
        return repository.getServiceCredentialsById(getCurrentUser().getUserId());
    }

    /**
     * serviceCredentials may be or may not be bounded to a tenant
     */
    @Override
    public String getTenantId() {
         return repository.getServiceCredentialsById(getCurrentUser().getUserId()).getTenantId();
        // or
//        return repository.getTenantForUserId(getCurrentUser().getUserId());
    }

    @Override
    public String getTraceId() {
        return UUID.randomUUID().toString();
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }


}
