package com.repository;

import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;

public class Repository {
    public ServiceCredentials getServiceCredentialsById(int userId) {
        switch (userId) {
            case 1:
                return new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1");
            case 2:
                return new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2");
            case 3:
                return new ServiceCredentials("client-id-3", "client-secret-3", "tenant-3");
            default:
                return new ServiceCredentials();
        }
    }

    public String getTenantForUserId(int userId) {
        switch (userId) {
            case 1:
                return "tenant-1";
            case 2:
                return "tenant-2";
            case 3:
                return "tenant-3";
            default:
                return "";
        }
    }
}
