import auth.ServiceCredentials;
import client.LanguageCloudClientProvider;
import com.sdl.lt.lc.publicapi.sdk.api.GroupApi;
import com.sdl.lt.lc.publicapi.sdk.api.UserApi;
import com.sdl.lt.lc.publicapi.sdk.model.ListGroupsResponse;
import com.sdl.lt.lc.publicapi.sdk.model.ListUsersResponse;
import context.LCContext;

import java.util.HashMap;

public class Test {
    private final static String CLIENT_ID_1 = "e3Pc4BC8VXOK6ahywsgIAzrAF9RdypAr";
    private final static String CLIENT_SECRET_1 = "l51KX6tp-sTENAIDOOPQtEdb-22psD25F2a2snsf0i_3SRuHaWZ4qb4gQ9DMEPtN";
    private final static String TENANT_VALUE_1 = "LC-60267fce0f0090246204e338";

    private final static String CLIENT_ID_2 = "4KkPLyLudK5MHeDhJc5SXTmKiouBmZTn";
    private final static String CLIENT_SECRET_2 = "QNFjzL3S2EsCxjbCEQpDbOjOHDF6mkwXIdCPWsJYqiXQAMPvdK62JnQ0nIRp2Y1v";
    private final static String TENANT_VALUE_2 = "LC-60b8b25de186261a8f6c780a";

    public static void main(String[] args) {
        LanguageCloudClientProvider languageCloudClientProvider_clientIdClientSecret = new LanguageCloudClientProvider(new ServiceCredentials(CLIENT_ID_1, CLIENT_SECRET_1, TENANT_VALUE_1));
        GroupApi groupApi = languageCloudClientProvider_clientIdClientSecret.getGroupClient();

        ListGroupsResponse groupsResponse = groupApi.listGroups(new HashMap<>());
        System.out.println("Groups:");
        System.out.println(groupsResponse.getItems());

        //Second call to check the token is retrieved from cache
        ListGroupsResponse groupsResponse2 = groupApi.listGroups(new HashMap<>());
        System.out.println("Groups:");
        System.out.println(groupsResponse2.getItems());

        //Context authentication
        LanguageCloudClientProvider languageCloudClientProvider_contextAuth = new LanguageCloudClientProvider();
        UserApi userApi = languageCloudClientProvider_contextAuth.getUserClient();

        try (LCContext lcContext = LCContext.beginScope(new ServiceCredentials(CLIENT_ID_1, CLIENT_SECRET_1, TENANT_VALUE_1));) {
            ListUsersResponse usersResponse = userApi.listUsers(new HashMap<>());
            System.out.println("Users:");
            System.out.println(usersResponse.getItems());
        } catch (Exception e) {
            System.out.println("Error when retrieving users with credentials saved to context.");
        }

        //Account from QA env - throw exception
        try (LCContext lcContext = LCContext.beginScope(new ServiceCredentials(CLIENT_ID_2, CLIENT_SECRET_2, TENANT_VALUE_2))) {
            ListUsersResponse usersResponse = userApi.listUsers(new HashMap<>());
            System.out.println("Users:");
            System.out.println(usersResponse.getItems());
        } catch (Exception e) {
            System.out.println("Error when retrieving users with credentials saved to context.");
            e.printStackTrace();
        }
    }
}
