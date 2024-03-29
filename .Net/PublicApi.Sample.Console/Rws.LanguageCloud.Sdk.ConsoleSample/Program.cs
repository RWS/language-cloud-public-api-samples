﻿using Rws.LanguageCloud.Sdk.Authentication;
using System;
using System.IO;
using System.Linq;

namespace Rws.LanguageCloud.Sdk.ConsoleSample
{
    internal class Program
    {
        private static void Main(string[] args)
        {
            WithCredentials().GetAwaiter().GetResult();

            WithContextScoping().GetAwaiter().GetResult();

            WithHandlers().GetAwaiter().GetResult();

            Console.ReadLine();
        }

        private static async System.Threading.Tasks.Task WithCredentials()
        {
            // define credentials
            ServiceCredentials credentials = new ServiceCredentials("client-id", "client-secret", "tenant");

            // Instantiate the client
            var projectClient = LanguageCloudClientProvider.GetProjectClient(credentials);

            // Creating a project:
            var projectCreateRequest = new ProjectCreateRequest
            {
                Name = "YOUR_PROJECT_NAME",
                DueBy = DateTime.Now.AddDays(7),
                ProjectTemplate = new ObjectIdRequest
                {
                    Id = "YOUR_PROJECT_TEMPLATE_ID"
                }
            };

            var projectCreateResponse = await projectClient.CreateProjectAsync(projectCreateRequest);

            // Attach a source file to the project via the SourceFiles client:
            var sourceFileClient = LanguageCloudClientProvider.GetSourceFileClient(credentials);

            using (FileStream SourceStream = File.Open("YOUR_TEXT_SOURCE_FILE", FileMode.Open))
            {
                FileParameter file = new FileParameter(SourceStream, "YOUR_TEXT_SOURCE_FILE", "text/plain");

                SourceFileRequest properties = new SourceFileRequest
                {      
                    Name = "YOUR_TEXT_SOURCE_FILE",
                    Role = SourceFileRequestRole.Translatable,
                    Type = SourceFileRequestType.Native,
                    Language = "en-US",
                };

                await sourceFileClient.AddSourceFileAsync("YOUR_PROJECT_ID", properties ,file);
            }

            // Start the project:
            await projectClient.StartProjectAsync("YOUR_PROJECT_ID");

            // (Optional) Supply the fields parameter and retrieve specific data about the project:
            var myProject = await projectClient.GetProjectAsync("YOUR_PROJECT_ID", "status,quote.totalAmount");

            // Listing all projects:
            var projectsResponse = await projectClient.ListProjectsAsync();
            LogToConsole(projectsResponse);
        }

        private static async System.Threading.Tasks.Task WithContextScoping()
        {
            // define credentials for your first user
            ServiceCredentials credentials_1 = new ServiceCredentials("client-id-1", "client-secret-1", "tenant-1");

            // define credentials for your second user
            ServiceCredentials credentials_2 = new ServiceCredentials("client-id-2", "client-secret-2", "tenant-2");

            // instantiate the client without credentials
            var client = LanguageCloudClientProvider.GetProjectClient();

            // create a context scope and use the client. You can also provide your own traceId
            using (Sdl.ApiClientSdk.Core.ApiClientContext.BeginScope(new LCContext(credentials_1, "trace-id-1")))
            {
                // the client will use the credentials_1 and "trace-id-1" defined in the scope
                var projectsResponse = await client.ListProjectsAsync();
                LogToConsole(projectsResponse);
            }

            // create a context scope and use the client. You can also provide your own traceId
            using (Sdl.ApiClientSdk.Core.ApiClientContext.BeginScope(new LCContext(credentials_2, "trace-id-2")))
            {
                // the client will use the credentials_2 and "trace-id-2" defined in the scope
                var projectsResponse = await client.ListProjectsAsync();
                LogToConsole(projectsResponse);
            }
        }

        private static async System.Threading.Tasks.Task WithHandlers()
        {
            // define credentials
            ServiceCredentials credentials = new ServiceCredentials("client-id", "client-secret", "tenant");

            // get an authentication handler
            ServiceAuthenticationHandler handler = new ServiceAuthenticationHandler(credentials);

            // use the factory method
            var client = LanguageCloudClientProvider.GetProjectClientNoAuth(handler);

            // use the client
            var projectsResponse = await client.ListProjectsAsync();

            LogToConsole(projectsResponse);
        }

        private static void LogToConsole(ListProjectsResponse projectsResponse)
        {
            projectsResponse.Items.ToList().ForEach(project => Console.WriteLine($"Project with id : {project.Id} and name : {project.Name}"));
        }
    }
}