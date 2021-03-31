package repository.account;

import model.Account;
import model.builder.AccountBuilder;
import model.validation.Notification;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static database.Constants.Tables.ACCOUNT;

public class AccountRepositoryMySQL implements AccountRepository {
    private final Connection connection;


    public AccountRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }
    @Override
    public List<Account> findByUserId(long user_id) {
        Statement statement;
        List<Account> accounts = new ArrayList<>();
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT + " where `user_id`=\'" + user_id + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            while (roleResultSet.next()){
                Account acc=new AccountBuilder()
                        .setId(roleResultSet.getLong("id"))
                        .setUserId(roleResultSet.getLong("user_id"))
                        .setIban(roleResultSet.getString("iban"))
                        .setSold(roleResultSet.getString("sold"))
                        .setCurrency(roleResultSet.getString("currency"))
                        .build();
                accounts.add(acc);
            }
            return accounts;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean deleteAccount(Account account) {
        try {
            Statement statement = connection.createStatement();
            String deleteAccount = "DELETE from `" + ACCOUNT + "` where `id`=\'" + account.getId() + "\'";
            statement.executeUpdate(deleteAccount);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Account findByIban(String iban) {
        Statement statement;
        try {
            statement = connection.createStatement();
            String fetchRoleSql = "Select * from " + ACCOUNT + " where `iban`=\'" + iban + "\'";
            ResultSet roleResultSet = statement.executeQuery(fetchRoleSql);
            if (roleResultSet.next()){
                Account account=new AccountBuilder()
                        .setId(roleResultSet.getLong("id"))
                        .setUserId(roleResultSet.getLong("user_id"))
                        .setIban(roleResultSet.getString("iban"))
                        .setSold(roleResultSet.getString("sold"))
                        .setCurrency(roleResultSet.getString("currency"))
                        .build();
                return account;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean save(Account account) {
        try {
            PreparedStatement insertAccountStatement = connection
                    .prepareStatement("INSERT INTO "+ACCOUNT+" values (null, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            insertAccountStatement.setLong(1, account.getUser_id());
            insertAccountStatement.setString(2, account.getSold());
            insertAccountStatement.setString(3, account.getIban());
            insertAccountStatement.setString(4, account.getCurrency());
            insertAccountStatement.executeUpdate();

            ResultSet rs = insertAccountStatement.getGeneratedKeys();

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
            String sql = "DELETE from "+ ACCOUNT+" where id >= 0";
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean updateBalance(Account account, String sold) {
        try {
            PreparedStatement updateAccountStatement = connection
                    .prepareStatement("UPDATE "+ACCOUNT+" SET sold = ?  WHERE id = ?", Statement.RETURN_GENERATED_KEYS);
            updateAccountStatement.setString(1,sold);
            updateAccountStatement.setLong(2,account.getId());
            updateAccountStatement.executeUpdate();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
