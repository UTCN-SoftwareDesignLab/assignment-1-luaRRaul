package repository.account;

import model.Account;
import model.validation.Notification;

import java.util.AbstractCollection;
import java.util.List;

public interface AccountRepository {
    List<Account> findByUserId(long user_id);
    Account findByIban(String iban);
    boolean deleteAccount(Account account);

    boolean save(Account account);

    boolean updateBalance(Account account, String sold);

    void removeAll();
}
