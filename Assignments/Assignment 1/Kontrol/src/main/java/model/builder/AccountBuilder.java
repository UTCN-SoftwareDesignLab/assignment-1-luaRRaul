package model.builder;

import model.Account;

public class AccountBuilder {
    private Account account;

    public AccountBuilder(){
        account = new Account();
    }

    public AccountBuilder setId(long id){
        account.setId(id);
        return this;
    }

    public AccountBuilder setUserId(long id){
        account.setUser_id(id);
        return this;
    }
    public AccountBuilder setIban(String iban){
        account.setIban(iban);
        return this;
    }

    public AccountBuilder setCurrency(String currency){
        account.setCurrency(currency);
        return this;
    }

    public AccountBuilder setSold(String sold){
        account.setSold(sold);
        return this;
    }

    public Account build(){
        return account;
    }
}
