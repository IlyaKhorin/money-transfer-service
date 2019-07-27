package dao.dto;

import dao.IKeyedValue;
import service.Account;

import java.math.BigDecimal;

public final class AccountDto implements IKeyedValue<Long> {
    private Long id;
    private BigDecimal balance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public static AccountDto from(Account value){
        AccountDto accountDto = new AccountDto();
        accountDto.setId(value.getId());
        accountDto.setBalance(value.getBalance());
        return accountDto;
    }
}
