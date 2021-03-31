package repository.activity;

import model.Activity;
import model.builder.ActivityBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static database.Constants.Tables.ACTIVITY;

public class ActivityRepositoryMySQL implements  ActivityRepository{
    private final Connection connection;


    public ActivityRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Activity> findByUserIdBetweenDates(long id, Date startDate, Date endDate) {
        List<Activity> returnList = new ArrayList<>();
        try {
            PreparedStatement findByUserIdBetweenDates = connection
                    .prepareStatement("SELECT * FROM "+ ACTIVITY +" WHERE executeDate <= ? AND executeDate >= ? AND user_id = ?", Statement.RETURN_GENERATED_KEYS);
            findByUserIdBetweenDates.setDate(1, new java.sql.Date(endDate.getTime()));
            findByUserIdBetweenDates.setDate(2, new java.sql.Date(startDate.getTime()));
            findByUserIdBetweenDates.setLong(3, id);
            ResultSet rs = findByUserIdBetweenDates.executeQuery();
            while(rs.next()){
                returnList.add(new ActivityBuilder()
                        .setId(rs.getLong("id"))
                        .setUserId(rs.getLong("user_id"))
                        .setClientId(rs.getLong("client_id"))
                        .setRightId(rs.getLong("right_id"))
                        .setDate(rs.getDate("executeDate"))
                        .build());

            }
            return returnList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(Activity activity) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO "+ ACTIVITY +" values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setLong(1, activity.getUser_id());
            insertUserStatement.setLong(2, activity.getRight_id());
            insertUserStatement.setLong(3, activity.getClient_id());
            insertUserStatement.setDate(4, (java.sql.Date) activity.getExecution_date());
            insertUserStatement.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void removeAll() {
        try {
            Statement statement = connection.createStatement();
            String sql = "DELETE from "+ ACTIVITY+" where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
