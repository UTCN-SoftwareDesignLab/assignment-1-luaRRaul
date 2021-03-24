package model;

import java.util.Date;

public class Activity {
    private long id;
    private long user_id;
    private long right_id;
    private long client_id;
    private Date execution_date;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getRight_id() {
        return right_id;
    }

    public void setRight_id(long right_id) {
        this.right_id = right_id;
    }

    public long getClient_id() {
        return client_id;
    }

    public void setClient_id(long client_id) {
        this.client_id = client_id;
    }

    public Date getExecution_date() {
        return execution_date;
    }

    public void setExecution_date(Date execution_date) {
        this.execution_date = execution_date;
    }
}
