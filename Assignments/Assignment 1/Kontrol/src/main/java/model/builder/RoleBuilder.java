package model.builder;

import model.Right;
import model.Role;

import java.util.List;

public class RoleBuilder {
    private final Role role;

    public RoleBuilder() {
        role = new Role();
    }

    public RoleBuilder setRole(String new_role) {
        role.setRole(new_role);
        return this;
    }

    public RoleBuilder setId(long id) {
        role.setId(id);
        return this;
    }

    public RoleBuilder setRights( List<Right> rights) {
        role.setRights(rights);
        return this;
    }

    public  Role build() {
        return this.role;
    }

}
