package com.configuration;

import auth.AuthenticationService;
import client.LanguageCloudClientProvider;
import com.authentication.CustomAuthenticationHandler;
import com.sdl.lt.lc.publicapi.sdk.api.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigClass {

    private static final String GRANT_TYPE = "client_credentials";
    private final String AUDIENCE = "https://api.sdl.com";
    private final String AUTH0_URL = "https://sdl-prod.eu.auth0.com/oauth/token";
    private final String BASE_URL = "https://lc-api.sdl.com/public-api/v1";

    @Bean
    public LanguageCloudClientProvider getPublicApiClient(CustomAuthenticationHandler customAuthenticationHandler) {
        return new LanguageCloudClientProvider(customAuthenticationHandler, BASE_URL);
    }

    @Bean
    public AccountApi getAccountApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getAccountClient();
    }

    @Bean
    public CustomerApi getCustomerApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getCustomerClient();
    }

    @Bean
    public CustomFieldApi getCustomFieldApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getCustomFieldClient();
    }

    @Bean
    public FileProcessingConfigurationApi getFileProcessingConfigurationApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getFileProcessingConfigurationClient();
    }

    @Bean
    public FolderApi getFolderApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getFolderClient();
    }

    @Bean
    public GroupApi getGroupApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getGroupClient();
    }

    @Bean
    public LanguageApi getLanguageApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getLanguageClient();
    }

    @Bean
    public PricingModelApi getPricingModelApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getPricingModelClient();
    }

    @Bean
    public ProjectApi getProjectApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getProjectClient();
    }

    @Bean
    public ProjectTemplateApi getProjectTemplateApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getProjectTemplateClient();
    }

    @Bean
    public QuotesApi getQuotesApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getQuotesClient();
    }

    @Bean
    public SourceFileApi getSourceFileApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getSourceFileClient();
    }

    @Bean
    public TargetFileApi getTargetFileApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getTargetFileClient();
    }

    @Bean
    public TaskApi getTaskApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getTaskClient();
    }

    @Bean
    public TaskTypeApi getTaskTypeApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getTaskTypeClient();
    }

    @Bean
    public TqaProfileApi getTqaProfileApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getTqaProfileClient();
    }

    @Bean
    public TranslationEngineApi getTranslationEngineApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getTranslationEngineClient();
    }

    @Bean
    public UserApi getUserApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getUserClient();
    }

    @Bean
    public WorkflowApi getWorkflowApi(LanguageCloudClientProvider languageCloudClientProvider) {
        return languageCloudClientProvider.getWorkflowClient();
    }

    @Bean
    public AuthenticationService getAuthenticationService() {
        return new AuthenticationService(GRANT_TYPE, AUDIENCE, AUTH0_URL);
    }

}
