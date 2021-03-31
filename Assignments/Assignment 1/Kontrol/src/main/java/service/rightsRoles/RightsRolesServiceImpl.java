package service.rightsRoles;

import model.Right;
import model.Role;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

public class RightsRolesServiceImpl implements RightsRolesService{
    private final RightsRolesRepository rightsRolesRepository;

    public RightsRolesServiceImpl(RightsRolesRepository rightsRolesRepository) {
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public Role getRoleByTitle(String title) {
        return rightsRolesRepository.findRoleByTitle(title);
    }

    @Override
    public Right getRightByTitle(String title) {
        return rightsRolesRepository.findRightByTitle(title);
    }
}
