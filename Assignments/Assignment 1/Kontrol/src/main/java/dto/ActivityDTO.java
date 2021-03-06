package dto;

import java.util.Date;

public class ActivityDTO {
    private long id;
    private long user_id;
    private long right_id;
    private long client_id;
    private String username;
    private String rightName;
    private String clientUsername;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

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

    @Override
    public String toString() {
        return "ActivityDTO{" +
                "username='" + username + '\'' +
                ", rightName='" + rightName + '\'' +
                ", clientUsername='" + clientUsername + '\'' +
                ", execution_date=" + execution_date +
                '}';
    }

    public void setExecution_date(Date execution_date) {
        this.execution_date = execution_date;
    }
}
