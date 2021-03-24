package service.account;

import model.Account;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface AccountService {
    Notification<Boolean> createAccount(String iban, String currency, User user);
    List<Account> findAccountsForUser(User user);
    Notification<Boolean> updateBalance(Account account, String balance);
    Notification<Boolean> sendMoney(Account sender, Account receiver, String ammount);
}
