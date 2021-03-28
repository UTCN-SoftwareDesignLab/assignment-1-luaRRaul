package repository.account;

import model.Account;
import model.validation.Notification;

import java.util.List;

public interface AccountRepository {
    List<Account> findByUserId(long user_id);

    boolean save(Account account, long user_id);

    boolean updateBalance(Account account, String sold);

    void removeAll();
}
