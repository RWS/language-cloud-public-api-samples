using Microsoft.AspNetCore.Mvc;
using System.Collections.Generic;
using System.Security.Claims;

namespace Rws.LanguageCloud.Sdk.WebApiSample.Controllers
{
    [ApiController]
    [Route("[controller]")]
    public class LanguageCloudApiSampleController : ControllerBase
    {
        private readonly IAccountClient accountClient;

        // inject the client via constructor
        public LanguageCloudApiSampleController(IAccountClient accountClient)
        {
            this.accountClient = accountClient;
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

            // make an API call using a client and the user identity from HttpContext
            var repsonse = await accountClient.ListMyAccountsAsync();

            return Ok(repsonse);
        }
    }
}