using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Rws.LanguageCloud.Sdk.Authentication;
using Rws.LanguageCloud.Sdk.WebApiSample.Dal;
using System;
using System.Linq;

namespace Rws.LanguageCloud.Sdk.WebApiSample
{
    /// <summary>
    /// A custom handler
    /// </summary>
    public class LcHandler : LCCustomAuthenticationHandler
    {
        private readonly IHttpContextAccessor context;
        private readonly ILogger<LcHandler> logger;
        private readonly Repository repository;

        public LcHandler(IHttpContextAccessor httpContext, ILogger<LcHandler> logger, Repository repository) : base(logger)
        {
            context = httpContext;
            this.logger = logger;
            this.repository = repository;
        }

        /// <summary>
        /// Example of a <see cref="ServiceCredentials"/> provider.
        /// </summary>
        protected override ServiceCredentials GetServiceCredentials()
        {
            // this is just an example of how we get the user identity
            int accountId = int.Parse(context.HttpContext.User.Claims.Single(x => x.Type.Equals("aid")).Value);
            ServiceCredentials credentials = repository.GetServiceCredentialsById(accountId);

            return credentials;
        }

        /// <summary>
        /// Example of a Trace Id provider.
        /// </summary>
        protected override string GetTraceId()
        {
            return DateTimeOffset.UtcNow.ToString();
        }
    }
}