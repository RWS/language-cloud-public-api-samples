using Rws.LanguageCloud.Sdk.Authentication;
using System;

namespace Rws.LanguageCloud.Sdk.WebApiSample.Dal
{
    public class Repository
    {
        public ServiceCredentials GetServiceCredentialsById(int accountId)
        {
            return accountId switch
            {
                1 => new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1"),
                2 => new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2"),
                3 => new ServiceCredentials("client-id-3", "client-secret-3", "tenant-3"),
                _ => throw new Exception("Account does not exist!"),
            };
        }
    }
}