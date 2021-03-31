package service.activity;

import dto.ActivityDTO;
import dto.UserDTO;
import model.validation.Notification;

import java.util.Date;
import java.util.List;

public interface ActivityService {
   Notification<Boolean> save(ActivityDTO activity);
   List<ActivityDTO> getReport(UserDTO user, Date startDate, Date endDate);
}
