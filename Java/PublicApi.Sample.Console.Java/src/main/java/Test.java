import com.rws.lt.lc.publicapi.sdk.api.GroupApi;
import com.rws.lt.lc.publicapi.sdk.api.UserApi;
import com.rws.lt.lc.publicapi.sdk.auth.ServiceCredentials;
import com.rws.lt.lc.publicapi.sdk.client.LanguageCloudClientProvider;
import com.rws.lt.lc.publicapi.sdk.context.LCContext;
import com.rws.lt.lc.publicapi.sdk.model.ListGroupsResponse;
import com.rws.lt.lc.publicapi.sdk.model.ListUsersResponse;

import java.util.HashMap;

public class Test {
    private final static String CLIENT_ID_1 = "e3Pc4BC8VXOK6ahywsgIAzrAF9RdypAr";
    private final static String CLIENT_SECRET_1 = "l51KX6tp-sTENAIDOOPQtEdb-22psD25F2a2snsf0i_3SRuHaWZ4qb4gQ9DMEPtN";
    private final static String TENANT_VALUE_1 = "LC-60267fce0f0090246204e338";

    private final static String CLIENT_ID_2 = "4KkPLyLudK5MHeDhJc5SXTmKiouBmZTn";
    private final static String CLIENT_SECRET_2 = "QNFjzL3S2EsCxjbCEQpDbOjOHDF6mkwXIdCPWsJYqiXQAMPvdK62JnQ0nIRp2Y1v";
    private final static String TENANT_VALUE_2 = "LC-60b8b25de186261a8f6c780a";

    public static void main(String[] args) {
        //instantiate the credentials
        ServiceCredentials serviceCredentials = new ServiceCredentials(CLIENT_ID_1, CLIENT_SECRET_1, TENANT_VALUE_1);

        // instantiate the LanguageCloudClientProvider
        LanguageCloudClientProvider languageCloudClientProvider_clientIdClientSecret = new LanguageCloudClientProvider(serviceCredentials);

        // instantiate the client
        GroupApi groupApi = languageCloudClientProvider_clientIdClientSecret.getGroupClient();

        // use the client
        ListGroupsResponse groupsResponse = groupApi.listGroups(new HashMap<>());
        System.out.println("Groups:");
        System.out.println(groupsResponse.getItems());

        //Second call to check the token is retrieved from cache
        ListGroupsResponse groupsResponse2 = groupApi.listGroups(new HashMap<>());
        System.out.println("Groups:");
        System.out.println(groupsResponse2.getItems());

        //Context authentication
        // define credentials for your first user
        ServiceCredentials credentials_1 = new ServiceCredentials(CLIENT_ID_1, CLIENT_SECRET_1, TENANT_VALUE_1);

        // define credentials for your second user
        ServiceCredentials credentials_2 = new ServiceCredentials(CLIENT_ID_2, CLIENT_SECRET_2, TENANT_VALUE_2);

        // instantiate the LanguageCloudClientProvider
        LanguageCloudClientProvider languageCloudClientProvider_contextAuth = new LanguageCloudClientProvider();

        // instantiate the client without credentials
        UserApi userApi = languageCloudClientProvider_contextAuth.getUserClient();

        // create a context scope and use the client
        try (LCContext lcContext = LCContext.beginScope(credentials_1, "trace-id-1");) {
            ListUsersResponse usersResponse = userApi.listUsers(new HashMap<>());
            System.out.println("Users:");
            System.out.println(usersResponse.getItems());
        } catch (Exception e) {
            System.out.println("Error when retrieving users with credentials saved to context.");
        }

        // create a context scope and use the client
        try (LCContext lcContext = LCContext.beginScope(credentials_2, "trace-id-2");) {
            ListUsersResponse usersResponse = userApi.listUsers(new HashMap<>());
            System.out.println("Users:");
            System.out.println(usersResponse.getItems());
        } catch (Exception e) {
            System.out.println("Error when retrieving users with credentials saved to context.");
            e.printStackTrace();
        }
    }
}
