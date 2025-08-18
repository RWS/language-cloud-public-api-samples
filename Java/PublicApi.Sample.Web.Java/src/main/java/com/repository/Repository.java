package com.repository;

import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;

public class Repository {
    public ServiceCredentials getServiceCredentialsById(int userId) {
        return switch (userId) {
            case 1 -> new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1");
            case 2 -> new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2");
            case 3 -> new ServiceCredentials("client-id-3", "client-secret-3", "tenant-3");
            default -> new ServiceCredentials();
        };
    }

    public String getTenantForUserId(int userId) {
        return switch (userId) {
            case 1 -> "tenant-1";
            case 2 -> "tenant-2";
            case 3 -> "tenant-3";
            default -> "";
        };
    }
}
