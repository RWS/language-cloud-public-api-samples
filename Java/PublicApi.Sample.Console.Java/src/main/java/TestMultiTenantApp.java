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
public class TestMultiTenantApp {
    private final static String CLIENT_ID = "clientId1";
    private final static String CLIENT_SECRET = "clientSecret1";
    private final static String TENANT_VALUE_1 = "tenant1";
    private final static String TENANT_VALUE_2 = "tenant2";

    public static void main(String[] args) {
        //instantiate the credentials
        ServiceCredentials serviceCredentials = new ServiceCredentials(CLIENT_ID, CLIENT_SECRET);

        // instantiate the LanguageCloudClientProvider
        LanguageCloudClientProvider languageCloudClientProvider_contextAuth = LanguageCloudClientProvider
                .builder()
                .withRegionCode("eu")
                .withServiceCredentials(serviceCredentials)
                .build();

        // instantiate the client without credentials
        UserApi userApi = languageCloudClientProvider_contextAuth.getUserClient();


        Runnable listUsersExecutable = () -> {
            ListUsersResponse usersResponse = userApi.listUsers(new UserApi.ListUsersQueryParams());
            log.info(LCContext.getFromLCContext(ContextKeys.TRACE_ID_KEY) + " >> Users for tenant " + LCContext.getFromLCContext(ContextKeys.TENANT_ID_KEY) + " are:");
            log.info(usersResponse.getItems().stream().map(User::getId).collect(Collectors.joining("\n")));
        };

        // create a context scope and execute inside with tenant 1
        LCContext.executeInScope(listUsersExecutable, TENANT_VALUE_1, "trace-id-1");

        log.info("--------------------------------------------------");
        // create a context scope and execute inside with tenant 2
        LCContext.executeInScope(listUsersExecutable, TENANT_VALUE_2, "trace-id-1");
    }
}
