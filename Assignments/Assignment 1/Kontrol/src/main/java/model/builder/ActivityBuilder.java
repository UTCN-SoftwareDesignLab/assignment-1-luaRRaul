package model.builder;

import model.Activity;

import java.util.Date;

public class ActivityBuilder {
    private Activity activity;
    public ActivityBuilder(){
        activity = new Activity();
    }

    public ActivityBuilder setId(long id){
        activity.setId(id);
        return this;
    }

   public ActivityBuilder setUserId(long user_id) {
        activity.setUser_id(user_id);
        return this;
   }

    public ActivityBuilder setRightId(long right_id){
        activity.setRight_id(right_id);
        return this;
    }

    public ActivityBuilder setClientId(long client_id) {
        activity.setClient_id(client_id);
        return this;
    }

    public ActivityBuilder setDate(Date date){
        activity.setExecution_date(date);
        return this;
    }
}
