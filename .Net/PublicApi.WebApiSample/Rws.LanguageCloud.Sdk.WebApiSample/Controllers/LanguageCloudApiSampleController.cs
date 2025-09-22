using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Security.Claims;

namespace Rws.LanguageCloud.Sdk.WebApiSample.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class LanguageCloudApiSampleController : ControllerBase
    {
        private readonly LanguageCloudClientFactory _factory;

        // inject the factory via constructor
        public LanguageCloudApiSampleController(LanguageCloudClientFactory factory)
        {
            _factory = factory;
        }

        [HttpGet("{accountId}")]
        public async System.Threading.Tasks.Task<IActionResult> Get(string accountId)
        {
            // fake a user identity for example purposes
            HttpContext.User = new ClaimsPrincipal(new List<ClaimsIdentity>
            {
                new ClaimsIdentity(new List<Claim>
                {
                    new Claim("aid", accountId)
                })
            });

            // make an API call using a client (resolved per-region) and the user identity from HttpContext
           
            var accountClient = _factory.Region("eu").AccountClient;
            var response = await accountClient.ListMyAccountsAsync();

            return Ok(response);
        }
    }
}