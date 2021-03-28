package service.account;

import model.Account;
import model.User;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import repository.account.AccountRepository;
import repository.user.UserRepository;

import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification<Boolean> createAccount(String iban, String currency, User user) {
        Account account = new AccountBuilder()
                .setUserId(user.getId())
                .setIban(iban)
                .setCurrency(currency)
                .setSold("0")
                .build();
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountCreationNotification = new Notification<>();
        if(!accountValid){
            accountValidator.getErrors().forEach(accountCreationNotification::addError);
            accountCreationNotification.setResult(Boolean.FALSE);
        }else{
            accountCreationNotification.setResult(accountRepository.save(account, user.getId()));
        }
        return accountCreationNotification;
    }

    @Override
    public List<Account> findAccountsForUser(User user) {
        return accountRepository.findByUserId(user.getId());
    }

    @Override
    public Notification<Boolean> sendMoney(Account sender, Account receiver, String ammount) {
        Notification<Boolean> sendMoneyNotif = new Notification<>();
        AccountValidator accountValidator = new AccountValidator(sender);
        boolean validBalance = accountValidator.validateBalance(ammount);
        boolean validNewBalance = accountValidator.validateBalance(String.valueOf(Integer.parseInt(sender.getSold())-Integer.parseInt(ammount)));
        if(validBalance & validNewBalance) {
            sendMoneyNotif.setResult(accountRepository.updateBalance(sender, String.valueOf(Integer.parseInt(sender.getSold())-Integer.parseInt(ammount))));
            sendMoneyNotif.setResult(accountRepository.updateBalance(receiver, String.valueOf(Integer.parseInt(sender.getSold())+Integer.parseInt(ammount))));
        }else {
            accountValidator.getErrors().forEach(sendMoneyNotif::addError);
            sendMoneyNotif.setResult(Boolean.FALSE);
        }
        return sendMoneyNotif;

    }

    @Override
    public Notification<Boolean> updateBalance(Account account, String balance) {
        Notification<Boolean> updateBalanceNotif = new Notification<>();
        AccountValidator accountValidator = new AccountValidator(account);
        boolean validBalance = accountValidator.validateBalance(balance);
        if(validBalance) {
            updateBalanceNotif.setResult(accountRepository.updateBalance(account, balance));
        }else {
            accountValidator.getErrors().forEach(updateBalanceNotif::addError);
            updateBalanceNotif.setResult(Boolean.FALSE);
        }
        return updateBalanceNotif;
    }
}
