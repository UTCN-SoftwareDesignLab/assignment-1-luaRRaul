package repository.user;

import dto.UserDTO;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import repository.security.RightsRolesRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Roles.CUSTOMER;
import static database.Constants.Roles.EMPLOYEE;
import static database.Constants.Tables.USER;

/**
 * Created by Alex on 11/03/2017.
 */
public class UserRepositoryMySQL implements UserRepository {

    private final Connection connection;
    private final RightsRolesRepository rightsRolesRepository;


    public UserRepositoryMySQL(Connection connection, RightsRolesRepository rightsRolesRepository) {
        this.connection = connection;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<User> findByUsernameAndPassword(String username, String password) {
        Notification<User> findByUsernameAndPasswordNotification = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String fetchUserSql = "Select * from `" + USER + "` where `username`=\'" + username + "\' and `password`=\'" + password + "\'";
            ResultSet userResultSet = statement.executeQuery(fetchUserSql);
            if (userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();
                findByUsernameAndPasswordNotification.setResult(user);
                return findByUsernameAndPasswordNotification;
            } else {
                findByUsernameAndPasswordNotification.addError("Invalid email or password!");
                return findByUsernameAndPasswordNotification;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            findByUsernameAndPasswordNotification.addError("Something is wrong with the Database");
        }
        return findByUsernameAndPasswordNotification;
    }

    @Override
    public Notification<Boolean> changeUsername(User user, String newUsername) {
        Notification<Boolean> changeUsernameNotif = new Notification<>();
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE "+USER+" SET username = ?  WHERE username = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1,newUsername);
            updateAccountStatement.setString(2,user.getUsername());
            updateAccountStatement.executeUpdate();

            changeUsernameNotif.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            changeUsernameNotif.addError("Something went wrong.");
            changeUsernameNotif.setResult(false);
        }
        return changeUsernameNotif;
    }

    @Override
    public Notification<Boolean> deleteByUsername(String username) {
        Notification<Boolean> deleteUserNotif = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String deleteEmployeeSQL = "DELETE from `" + USER + "` where `username`='" + username +
                    "'";
            statement.executeUpdate(deleteEmployeeSQL);
            deleteUserNotif.setResult(true);
        } catch (SQLException e) {
            e.printStackTrace();
            deleteUserNotif.addError("Something went wrong.");
            deleteUserNotif.setResult(false);
        }
        return deleteUserNotif;
    }

    @Override
    public Notification<User> findUserById(long user_id) {
        Notification<User> selectUserNotif = new Notification<>();
        try {
            Statement statement = connection.createStatement();
            String selectUserSQL = "SELECT * from `" + USER + "` where `id`='" + user_id + "'";
            ResultSet userResultSet = statement.executeQuery(selectUserSQL);
            if(userResultSet.next()) {
                User user = new UserBuilder()
                        .setId(userResultSet.getLong("id"))
                        .setUsername(userResultSet.getString("username"))
                        .setPassword(userResultSet.getString("password"))
                        .setRoles(rightsRolesRepository.findRolesForUser(userResultSet.getLong("id")))
                        .build();

                selectUserNotif.setResult(user);
                return selectUserNotif;
            }else {
                selectUserNotif.addError("User not in Database");
                return selectUserNotif;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            selectUserNotif.addError("Something is wrong with the Database");
        }
        return selectUserNotif;

    }

    @Override
    public boolean save(User user) {
        try {
            PreparedStatement insertUserStatement = connection
                    .prepareStatement("INSERT INTO user values (null, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, user.getUsername());
            insertUserStatement.setString(2, user.getPassword());
            insertUserStatement.executeUpdate();

            ResultSet rs = insertUserStatement.getGeneratedKeys();
            rs.next();
            long userId = rs.getLong(1);
            user.setId(userId);

            rightsRolesRepository.addRolesToUser(user, user.getRoles());

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
            String sql = "DELETE from user where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAllCustomers() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            //rightsRolesRepository.findRolesForUser(userResultSet.getLong("id"))
            String fetchAll = "Select * from " + USER;
            ResultSet usersResultSet = statement.executeQuery(fetchAll);
            while(usersResultSet.next()){
                if (rightsRolesRepository.findRolesForUser(usersResultSet.getLong("id")).get(0).getRole().equals(CUSTOMER)){
                    User user = new UserBuilder()
                            .setId(usersResultSet.getLong("id"))
                            .setUsername(usersResultSet.getString("username"))
                            .setPassword(usersResultSet.getString("password"))
                            .setRoles(rightsRolesRepository.findRolesForUser(usersResultSet.getLong("id")))
                            .build();
                    result.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public List<User> findAllEmployees() {
        List<User> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            //rightsRolesRepository.findRolesForUser(userResultSet.getLong("id"))
            String fetchAll = "Select * from " + USER;
            ResultSet usersResultSet = statement.executeQuery(fetchAll);
            while(usersResultSet.next()){
                if (rightsRolesRepository.findRolesForUser(usersResultSet.getLong("id")).get(0).getRole().equals(EMPLOYEE)){
                    User user = new UserBuilder()
                            .setId(usersResultSet.getLong("id"))
                            .setUsername(usersResultSet.getString("username"))
                            .setPassword(usersResultSet.getString("password"))
                            .setRoles(rightsRolesRepository.findRolesForUser(usersResultSet.getLong("id")))
                            .build();
                    result.add(user);
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }
}
