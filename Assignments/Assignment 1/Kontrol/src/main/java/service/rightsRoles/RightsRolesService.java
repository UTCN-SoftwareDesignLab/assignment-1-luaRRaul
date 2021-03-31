package service.rightsRoles;

import model.Right;
import model.Role;

public interface RightsRolesService {
    Role getRoleByTitle(String title);
    Right getRightByTitle(String title);
}
