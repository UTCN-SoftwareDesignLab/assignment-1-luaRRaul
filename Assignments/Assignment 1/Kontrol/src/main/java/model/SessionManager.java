package model;

import static database.Constants.Roles.ADMINISTRATOR;
import static database.Constants.Roles.EMPLOYEE;

public class SessionManager {
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user = null;

    public boolean isEmployee(){
        for(Role role : this.user.getRoles()){
            if(role.getRole().equals(EMPLOYEE))
                return true;
        }
        return false;
    }

    public boolean isAdmin(){
        for(Role role : this.user.getRoles()){
            if(role.getRole().equals(ADMINISTRATOR))
                return true;
        }
        return false;
    }
}
