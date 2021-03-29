package dto.builder;

import dto.AccountDTO;
import model.Account;

public class AccountDTOBuilder {
    private AccountDTO account;

    public AccountDTOBuilder(){
        account = new AccountDTO();
    }

    public AccountDTOBuilder setId(long id){
        account.setId(id);
        return this;
    }

    public AccountDTOBuilder setUserId(long id){
        account.setUser_id(id);
        return this;
    }
    public AccountDTOBuilder setIban(String iban){
        account.setIban(iban);
        return this;
    }

    public AccountDTOBuilder setCurrency(String currency){
        account.setCurrency(currency);
        return this;
    }

    public AccountDTOBuilder setSold(String sold){
        account.setSold(sold);
        return this;
    }

    public AccountDTO build(){
        return account;
    }
}
