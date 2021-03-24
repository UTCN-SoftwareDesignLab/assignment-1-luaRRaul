package repository.activity;

import model.Activity;
import repository.security.RightsRolesRepository;

import java.sql.Connection;
import java.util.List;

public class ActivityRepositoryMySQL implements  ActivityRepository{
    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public ActivityRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }
    @Override
    public List<Activity> findAll() {
        return null;
    }

    @Override
    public List<Activity> findByUserId(long id) {
        return null;
    }

    @Override
    public boolean save(Activity activity) {
        return false;
    }

    @Override
    public void removeAll() {

    }
}
