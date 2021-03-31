package service.activity;

import dto.ActivityDTO;
import dto.UserDTO;
import dto.builder.ActivityDTOBuilder;
import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.activity.ActivityRepository;
import repository.security.RightsRolesRepository;
import repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityServiceImpl implements ActivityService{
    private final ActivityRepository activityRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private final UserRepository userRepository;
    public ActivityServiceImpl(ActivityRepository activityRepository, RightsRolesRepository rightsRolesRepository, UserRepository userRepository) {
        this.activityRepository = activityRepository;
        this.rightsRolesRepository = rightsRolesRepository;
        this.userRepository = userRepository;
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
        List<ActivityDTO> retList = new ArrayList<>();
        List<Activity> list = activityRepository.findByUserIdBetweenDates(user.getId(),startDate,endDate);
        for(Activity a : list){
            retList.add(new ActivityDTOBuilder()
                    .setUserId(a.getUser_id())
                    .setDate(a.getExecution_date())
                    .setClientId(a.getClient_id())
                    .setRightId(a.getRight_id())
                    .setUsername(userRepository.findUserById(a.getUser_id()).getResult().getUsername())
                    .setClientUsername(userRepository.findUserById(a.getClient_id()).getResult().getUsername())
                    .setRightName(rightsRolesRepository.findRightById(a.getRight_id()).getRight())
                    .build()
            );
        }
        return retList;
    }
}
