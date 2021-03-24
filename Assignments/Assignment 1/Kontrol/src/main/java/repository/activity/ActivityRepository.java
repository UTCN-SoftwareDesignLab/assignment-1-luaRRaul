package repository.activity;

import model.Activity;

import java.util.List;

public interface ActivityRepository {
    List<Activity> findAll();

    List<Activity> findByUserId(long id);

    boolean save(Activity activity);

    void removeAll();
}
