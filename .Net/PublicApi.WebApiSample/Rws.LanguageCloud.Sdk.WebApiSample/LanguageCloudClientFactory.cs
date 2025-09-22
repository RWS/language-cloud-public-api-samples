using Rws.LanguageCloud.Sdk;
using Microsoft.Extensions.DependencyInjection;
using System.Collections.Concurrent;
using System;

namespace Rws.LanguageCloud.Sdk.WebApiSample
{
    // Container for clients for a specific region. Lazily initializes per-client instances.
    public class RegionClientContainerFactory
    {
        private readonly IServiceProvider serviceProvider;

        private IAccountClient _accountClient;

        private readonly LanguageCloudClientProvider _languageCloudClientProvider;

        public RegionClientContainerFactory(string region, IServiceProvider serviceProvider)
        {
            this.serviceProvider = serviceProvider;
            _languageCloudClientProvider = new LanguageCloudClientProvider(region);
        }

        public IAccountClient AccountClient
        {
            get
            {
                // resolve handler from provider on demand
                var handler = serviceProvider.GetService(typeof(LcHandler)) as LcHandler;
                return _accountClient ??= _languageCloudClientProvider.GetAccountClient(handler);
            }
        }
    }

    // Factory that holds RegionClientContainerFactory instances per region.
    public class LanguageCloudClientFactory
    {
        private readonly object _lock = new object();
        private readonly IServiceProvider _serviceProvider;

        private readonly ConcurrentDictionary<string, RegionClientContainerFactory> _regionClientContainerFactories = new ConcurrentDictionary<string, RegionClientContainerFactory>(StringComparer.OrdinalIgnoreCase);

        public LanguageCloudClientFactory(IServiceProvider serviceProvider)
        {
            _serviceProvider = serviceProvider;
        }

        public RegionClientContainerFactory Region(string region)
        {
            if (!_regionClientContainerFactories.TryGetValue(region, out var regionClientContainerFactory))
            {
                lock (_lock)
                {
                    if (!_regionClientContainerFactories.TryGetValue(region, out regionClientContainerFactory))
                    {
                        regionClientContainerFactory = new RegionClientContainerFactory(region, _serviceProvider);
                        _regionClientContainerFactories.TryAdd(region, regionClientContainerFactory);
                    }
                }
            }

            return regionClientContainerFactory;
        }
    }
}
