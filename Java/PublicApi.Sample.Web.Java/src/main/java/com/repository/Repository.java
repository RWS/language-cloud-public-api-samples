package com.repository;

import auth.ServiceCredentials;

public class Repository {
    public ServiceCredentials getServiceCredentialsById(int accountId) {
        switch (accountId) {
            case 1:
                return new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1");
            case 2:
                return new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2");
            case 3:
                return new ServiceCredentials("e3Pc4BC8VXOK6ahywsgIAzrAF9RdypAr", "l51KX6tp-sTENAIDOOPQtEdb-22psD25F2a2snsf0i_3SRuHaWZ4qb4gQ9DMEPtN", "LC-60267fce0f0090246204e338");
            default:
                return new ServiceCredentials();
        }
    }
}
