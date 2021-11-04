using Rws.LanguageCloud.Sdk.Authentication;
using System;
using System.Linq;

namespace Rws.LanguageCloud.Sdk.ConsoleSample
{
    internal class Program
    {
        private static void Main(string[] args)
        {
            WithCredentials().GetAwaiter().GetResult();

            WithContextScoping().GetAwaiter().GetResult();

            WithFactory().GetAwaiter().GetResult();

            Console.ReadLine();
        }

        private static async System.Threading.Tasks.Task WithCredentials()
        {
            // define credentials
            ServiceCredentials credentials = new ServiceCredentials("client-id", "client-secret", "tenant");

            // instantiate the client
            var accountClient = LanguageCloudClientProvider.GetAccountClient(credentials);

            // use the client
            var accounts = await accountClient.ListMyAccountsAsync();
            LogToConsole(accounts);
        }

        private static async System.Threading.Tasks.Task WithContextScoping()
        {
            // define credentials for your first user
            ServiceCredentials credentials_1 = new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1");

            // define credentials for your second user
            ServiceCredentials credentials_2 = new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2");

            // instantiate the client without credentials
            var client = LanguageCloudClientProvider.GetAccountClient();

            // create a context scope and use the client. You can also provide your own traceId
            using (Sdl.ApiClientSdk.Core.ApiClientContext.BeginScope(new LCContext(credentials_1, "trace-id-1")))
            {
                // the client will use the credentials_1 and "trace-id-1" defined in the scope
                var accounts = await client.ListMyAccountsAsync();
                LogToConsole(accounts);
            }

            // create a context scope and use the client. You can also provide your own traceId
            using (Sdl.ApiClientSdk.Core.ApiClientContext.BeginScope(new LCContext(credentials_2, "trace-id-2")))
            {
                // the client will use the credentials_2 and "trace-id-2" defined in the scope
                var accounts = await client.ListMyAccountsAsync();
                LogToConsole(accounts);
            }
        }

        private static async System.Threading.Tasks.Task WithFactory()
        {
            // define credentials
            ServiceCredentials credentials = new ServiceCredentials("client-id", "client-secret", "tenant");

            // use the factory method
            var client = new Sdl.ApiClientSdk.Core.ApiClientFactory<AccountClient>("https://lc-api.sdl.com")
               .AddHandler(new ServiceAuthenticationHandler(credentials))
               .AddJsonSerializerSettings(LanguageCloudClientProvider.GetJsonSettings())
               .Build();

            // use the client
            var accounts = await client.ListMyAccountsAsync();

            LogToConsole(accounts);
        }

        private static void LogToConsole(ListMyAccountsResponse accountsResponse)
        {
            accountsResponse.Items.ToList().ForEach(a => Console.WriteLine($"Account with id : {a.Id} and name : {a.Name}"));
        }
    }
}