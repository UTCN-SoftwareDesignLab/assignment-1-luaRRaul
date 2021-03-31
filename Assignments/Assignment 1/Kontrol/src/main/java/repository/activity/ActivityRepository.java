package repository.activity;

import model.Activity;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface ActivityRepository {
    List<Activity> findByUserIdBetweenDates(long id, Date startDate, Date endDate);
    boolean save(Activity activity);
}
