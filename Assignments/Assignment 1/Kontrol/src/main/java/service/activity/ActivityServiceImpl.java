package service.activity;

import dto.ActivityDTO;
import dto.UserDTO;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.activity.ActivityRepository;

import java.util.Date;
import java.util.List;

public class ActivityServiceImpl implements ActivityService{
    private final ActivityRepository activityRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Notification<Boolean> save(ActivityDTO activity) {
        Notification<Boolean> saveNotification = new Notification<>();
        saveNotification.setResult(activityRepository.save(new ActivityBuilder()
                .setUserId(activity.getUser_id())
                .setRightId(activity.getRight_id())
                .setClientId(activity.getClient_id())
                .setDate(activity.getExecution_date())
                .build()));
        if(!saveNotification.getResult()){
            saveNotification.addError("Something went wrong");
        }
        return saveNotification;
    }

    @Override
    public List<ActivityDTO> getReport(UserDTO user, Date startDate, Date endDate) {
        return null;
    }
}
