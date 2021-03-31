package dto.builder;


import dto.ActivityDTO;

import java.util.Date;

public class ActivityDTOBuilder {
    private ActivityDTO activity;
    public ActivityDTOBuilder(){
        activity = new ActivityDTO();
    }

    public ActivityDTOBuilder setId(long id){
        activity.setId(id);
        return this;
    }

    public ActivityDTOBuilder setUserId(long user_id) {
        activity.setUser_id(user_id);
        return this;
    }

    public ActivityDTOBuilder setRightId(long right_id){
        activity.setRight_id(right_id);
        return this;
    }

    public ActivityDTOBuilder setClientId(long client_id) {
        activity.setClient_id(client_id);
        return this;
    }

    public ActivityDTOBuilder setDate(Date date){
        activity.setExecution_date(date);
        return this;
    }

    public ActivityDTO build(){
        return activity;
    }
}
