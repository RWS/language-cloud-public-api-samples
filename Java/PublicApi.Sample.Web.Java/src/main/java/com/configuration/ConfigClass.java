package com.configuration;

import com.authentication.CustomAuthenticationHandler;
import com.rws.lt.lc.publicapi.sdk.api.*;
import com.rws.lt.lc.publicapi.sdk.auth.AuthenticationService;
import com.rws.lt.lc.publicapi.sdk.client.LanguageCloudClientProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ConfigClass {

    @Bean
    public LanguageCloudClientProvider getPublicApiClient(CustomAuthenticationHandler customAuthenticationHandler) {
        return LanguageCloudClientProvider.builder().withRegionCode("eu").withRequestInterceptors(List.of(customAuthenticationHandler)).build();
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
    public QuoteApi getQuotesApi(LanguageCloudClientProvider languageCloudClientProvider) {
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
        return AuthenticationService.getInstance();
    }

}
