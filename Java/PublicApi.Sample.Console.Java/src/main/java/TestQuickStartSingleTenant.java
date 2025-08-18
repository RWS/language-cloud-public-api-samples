import com.rws.lt.lc.publicapi.sdk.api.GroupApi;
import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;
import com.rws.lt.lc.publicapi.sdk.client.LanguageCloudClientProvider;
import com.rws.lt.lc.publicapi.sdk.model.Group;
import com.rws.lt.lc.publicapi.sdk.model.ListGroupsResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.stream.Collectors;

@Slf4j
public class TestQuickStartSingleTenant {
    private final static String CLIENT_ID = "clientId";
    private final static String CLIENT_SECRET = "clientSecret";
    private final static String TENANT_VALUE = "tenant1";

    public static void main(String[] args) {
        //instantiate the credentials
        ServiceCredentials serviceCredentials = new ServiceCredentials(CLIENT_ID, CLIENT_SECRET, TENANT_VALUE);

        // instantiate the LanguageCloudClientProvider
        LanguageCloudClientProvider languageCloudClientProvider = LanguageCloudClientProvider.builder()
                .withServiceCredentials(serviceCredentials).build();

        // instantiate the client
        GroupApi groupApi = languageCloudClientProvider.getGroupClient();

        // use the client
        ListGroupsResponse groupsResponse = groupApi.listGroups(new GroupApi.ListGroupsQueryParams());
        log.info("Groups: {}", groupsResponse.getItems().stream().map(Group::getName).collect(Collectors.joining(", ")));
    }
}
