package service.account;

import dto.AccountDTO;
import dto.UserDTO;
import model.Account;
import model.User;
import model.validation.Notification;

import java.util.List;

public interface AccountService {
    Notification<Boolean> createAccount(AccountDTO account);
    List<AccountDTO> findAccountsForUser(UserDTO user);
    Notification<Boolean> updateBalance(AccountDTO account, String balance);
    Notification<Boolean> transferMoney(AccountDTO sender, AccountDTO receiver, String amount);
    Notification<Boolean> deleteAccount(AccountDTO account);
}
