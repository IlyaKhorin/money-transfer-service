package dao;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dao.dto.AccountDto;

@Singleton
public class AccountRepository extends Repository<Long, AccountDto> {
    @Inject
    public AccountRepository(@Named("LongAtomic") IUniqueGenerator<Long> keyGenerator) {
        super(keyGenerator);
    }
}
