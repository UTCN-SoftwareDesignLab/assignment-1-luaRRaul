package service.account;

import dto.AccountDTO;
import dto.UserDTO;
import dto.builder.AccountDTOBuilder;
import model.Account;
import model.builder.AccountBuilder;
import model.validation.AccountValidator;
import model.validation.Notification;
import model.validation.Validator;
import repository.account.AccountRepository;

import java.util.ArrayList;
import java.util.List;

public class AccountServiceImpl implements AccountService{
    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Notification<Boolean> createAccount(AccountDTO accountDTO) {
        Account account = new AccountBuilder()
                .setUserId(accountDTO.getUser_id())
                .setIban(accountDTO.getIban())
                .setCurrency(accountDTO.getCurrency())
                .setSold("0")
                .build();
        AccountValidator accountValidator = new AccountValidator(account);
        boolean accountValid = accountValidator.validate();
        Notification<Boolean> accountCreationNotification = new Notification<>();
        if(!accountValid){
            accountValidator.getErrors().forEach(accountCreationNotification::addError);
            accountCreationNotification.setResult(Boolean.FALSE);
        }else{
            accountCreationNotification.setResult(accountRepository.save(account));
        }
        return accountCreationNotification;
    }

    @Override
    public List<AccountDTO> findAccountsForUser(UserDTO user) {
        List<AccountDTO> accountsDTO = new ArrayList<>();
        for (Account acc : accountRepository.findByUserId(user.getId())){
            accountsDTO.add(new AccountDTOBuilder().setUserId(acc.getUser_id()).setIban(acc.getIban()).setCurrency(acc.getCurrency()).setSold(acc.getSold()).build());
        }
        return accountsDTO;
    }

    @Override
    public Notification<Boolean> transferMoney(AccountDTO senderDTO, AccountDTO receiverDTO, String amount) {
        Notification<Boolean> sendMoneyNotif = new Notification<>();
        Account sender = new AccountBuilder().setCurrency(senderDTO.getCurrency())
                .setUserId(senderDTO.getUser_id())
                .setIban(senderDTO.getIban())
                .build();
        Account receiver = new AccountBuilder().setCurrency(receiverDTO.getCurrency())
                .setUserId(receiverDTO.getUser_id())
                .setIban(receiverDTO.getIban())
                .build();
        AccountValidator accountValidator = new AccountValidator(sender);
        Validator validator = new Validator();
        boolean validBalance = validator.validateBalance(amount);
        boolean validNewBalance = validator.validateBalance(String.valueOf(Integer.parseInt(sender.getSold())-Integer.parseInt(amount)));
        if(validBalance & validNewBalance) {
            sendMoneyNotif.setResult(accountRepository.updateBalance(sender, String.valueOf(Integer.parseInt(sender.getSold())-Integer.parseInt(amount))));
            sendMoneyNotif.setResult(accountRepository.updateBalance(receiver, String.valueOf(Integer.parseInt(sender.getSold())+Integer.parseInt(amount))));
        }else {
            accountValidator.getErrors().forEach(sendMoneyNotif::addError);
            sendMoneyNotif.setResult(Boolean.FALSE);
        }
        return sendMoneyNotif;

    }

    @Override
    public Notification<Boolean> deleteAccount(AccountDTO accountDTO) {
        Notification<Boolean> deleteAccountNotif = new Notification<>();
        Account account = accountRepository.findByIban(accountDTO.getIban());
        deleteAccountNotif.setResult(accountRepository.deleteAccount(account));
        return deleteAccountNotif;
    }

    @Override
    public Notification<Boolean> updateBalance(AccountDTO accountDTO, String balance) {
        Notification<Boolean> updateBalanceNotif = new Notification<>();
        Account account = new AccountBuilder().setCurrency(accountDTO.getCurrency())
                .setUserId(accountDTO.getUser_id())
                .setIban(accountDTO.getIban())
                .build();
        AccountValidator accountValidator = new AccountValidator(account);
        Validator validator = new Validator();
        boolean validBalance = validator.validateBalance(balance);
        if(validBalance) {
            updateBalanceNotif.setResult(accountRepository.updateBalance(account, balance));
        }else {
            accountValidator.getErrors().forEach(updateBalanceNotif::addError);
            updateBalanceNotif.setResult(Boolean.FALSE);
        }
        return updateBalanceNotif;
    }
}
