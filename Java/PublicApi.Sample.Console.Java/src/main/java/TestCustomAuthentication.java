import com.rws.lt.lc.publicapi.sdk.api.ProjectApi;
import com.rws.lt.lc.publicapi.sdk.auth.AuthenticationService;
import com.rws.lt.lc.publicapi.sdk.auth.CustomServiceAuthenticationHandler;
import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;
import com.rws.lt.lc.publicapi.sdk.client.LanguageCloudClientProvider;
import com.rws.lt.lc.publicapi.sdk.model.ListProjectsResponse;

import java.util.List;
import java.util.UUID;

public class TestCustomAuthentication {
    private final static String CLIENT_ID = "clientId1";
    private final static String CLIENT_SECRET = "clientSecret1";
    private final static String TENANT_VALUE = "tenant1";

    public static void main(String[] args) {

        AuthenticationService authenticationService = AuthenticationService.getInstance();

        CustomServiceAuthenticationHandler customServiceAuthenticationHandler = new CustomServiceAuthenticationHandler(authenticationService) {
            @Override
            public ServiceCredentials getServiceCredentials() {
                return new ServiceCredentials(CLIENT_ID, CLIENT_SECRET);
            }

            @Override
            public String getTraceId() {
                return UUID.randomUUID().toString();
            }

            @Override
            public String getTenantId() {
                return TENANT_VALUE;
            }
        };

        LanguageCloudClientProvider languageCloudCustomClientProvider = LanguageCloudClientProvider.builder().withRequestInterceptors(
                List.of(customServiceAuthenticationHandler)).build();

        // instantiate the client
        ProjectApi projectApi = languageCloudCustomClientProvider.getProjectClient();

        // use the client
        ListProjectsResponse projectsResponse = projectApi.listProjects(new ProjectApi.ListProjectsQueryParams());
        System.out.println("Projects:");
        System.out.println(projectsResponse.getItems());

    }
}
