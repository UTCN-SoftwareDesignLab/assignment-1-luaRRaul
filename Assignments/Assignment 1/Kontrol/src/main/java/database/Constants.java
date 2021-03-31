package database;

import java.util.*;
import static database.Constants.Rights.*;
import static database.Constants.Roles.*;

public class Constants {
    public static class Schemas {
        public static final String TEST = "test_desk";
        public static final String PRODUCTION = "desk";

        public static final String[] SCHEMAS = new String[]{TEST, PRODUCTION};
    }

    public static class Tables {
        public static final String USER = "user";
        public static final String ROLE = "role";
        public static final String RIGHT = "right";
        public static final String ROLE_RIGHT = "role_right";
        public static final String USER_ROLE = "user_role";
        public static final String ACTIVITY = "activity";
        public static final String ACCOUNT = "account";

        public static final String[] ORDERED_TABLES_FOR_CREATION = new String[]{USER, ROLE, RIGHT, ROLE_RIGHT, USER_ROLE, ACTIVITY, ACCOUNT};
    }

    public static class Roles {
        public static final String ADMINISTRATOR = "administrator";
        public static final String EMPLOYEE = "employee";
        public static final String CUSTOMER = "customer";

        public static final String[] ROLES = new String[]{ADMINISTRATOR, EMPLOYEE, CUSTOMER};
    }

    public static class Rights {
        //rights for the administrator
        public static final String CREATE_EMPLOYEE = "create_employee";
        public static final String DELETE_EMPLOYEE = "delete_employee";
        public static final String UPDATE_EMPLOYEE = "delete_employee";
        public static final String VIEW_EMPLOYEE = "view_employee";
        public static final String GENERATE_REPORT = "generate_report";

        //rights for the employee
        public static final String ADD_CLIENT = "add_client";
        public static final String UPDATE_CLIENT = "update_client";
        public static final String VIEW_CLIENT = "view_client";

        public static final String CREATE_CLIENT_ACCOUNT = "create_client_account";
        public static final String UPDATE_CLIENT_ACCOUNT = "update_client_account";
        public static final String DELETE_CLIENT_ACCOUNT = "delete_client_account";
        public static final String VIEW_CLIENT_ACCOUNT = "view_client_account";

        public static final String TRANSFER_MONEY = "transfer_money";
        public static final String PROCESS_BILL = "process_bill";


        public static final String[] RIGHTS = new String[]{CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE, VIEW_EMPLOYEE, GENERATE_REPORT, ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_CLIENT_ACCOUNT, UPDATE_CLIENT_ACCOUNT, DELETE_CLIENT_ACCOUNT, VIEW_CLIENT_ACCOUNT, TRANSFER_MONEY, PROCESS_BILL};
    }

    public static Map<String, List<String>> getRolesRights() {
        Map<String, List<String>> rolesRights = new HashMap<>();
        for (String role : Roles.ROLES) {
            rolesRights.put(role, new ArrayList<>());
        }
        rolesRights.get(ADMINISTRATOR).addAll(Arrays.asList(CREATE_EMPLOYEE, DELETE_EMPLOYEE, UPDATE_EMPLOYEE, VIEW_EMPLOYEE, GENERATE_REPORT));

        rolesRights.get(EMPLOYEE).addAll(Arrays.asList(ADD_CLIENT, UPDATE_CLIENT, VIEW_CLIENT, CREATE_CLIENT_ACCOUNT, UPDATE_CLIENT_ACCOUNT, DELETE_CLIENT_ACCOUNT, VIEW_CLIENT_ACCOUNT, TRANSFER_MONEY, PROCESS_BILL));

        rolesRights.get(CUSTOMER).addAll(Arrays.asList(TRANSFER_MONEY));

        return rolesRights;
    }

}
