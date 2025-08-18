import com.rws.lt.lc.publicapi.sdk.api.UserApi;
import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;
import com.rws.lt.lc.publicapi.sdk.client.LanguageCloudClientProvider;
import com.rws.lt.lc.publicapi.sdk.context.ContextKeys;
import com.rws.lt.lc.publicapi.sdk.context.LCContext;
import com.rws.lt.lc.publicapi.sdk.model.ListUsersResponse;
import com.rws.lt.lc.publicapi.sdk.model.User;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class TestMultiTenantApplications {
    private final static String CLIENT_ID_1 = "clientId1";
    private final static String CLIENT_SECRET_1 = "clientSecret1";
    private final static String CLIENT_ID_2 = "clientId2";
    private final static String CLIENT_SECRET_2 = "clientSecret2";

    private final static String TENANT_VALUE_1 = "tenant1";
    private final static String TENANT_VALUE_2 = "tenant2";

    public static void main(String[] args) {
        //instantiate the credentials
        ServiceCredentials serviceCredentials1 = new ServiceCredentials(CLIENT_ID_1, CLIENT_SECRET_1, TENANT_VALUE_1);
        ServiceCredentials serviceCredentials2 = new ServiceCredentials(CLIENT_ID_2, CLIENT_SECRET_2, TENANT_VALUE_2);

        // instantiate the LanguageCloudClientProvider
        LanguageCloudClientProvider languageCloudClientProvider_contextAuth = LanguageCloudClientProvider
                .builder()
                .withRegionCode("eu")
                .build();

        // instantiate the client without credentials
        UserApi userApi = languageCloudClientProvider_contextAuth.getUserClient();


        Runnable listUsersExecutable = () -> {
            ListUsersResponse usersResponse = userApi.listUsers(new UserApi.ListUsersQueryParams());
            log.info(LCContext.getFromLCContext(ContextKeys.TRACE_ID_KEY) + " >> Users for tenant " + LCContext.getFromLCContext(ContextKeys.TENANT_ID_KEY) + " are:");
            log.info(usersResponse.getItems().stream().map(User::getId).collect(Collectors.joining("\n")));
        };

        // create a context scope and execute inside with tenant 1
        LCContext.executeInScope(listUsersExecutable, serviceCredentials1, "trace-id-1");

        log.info("--------------------------------------------------");
        // create a context scope and execute inside with tenant 2
        LCContext.executeInScope(listUsersExecutable, serviceCredentials2, "trace-id-2");
    }
}
