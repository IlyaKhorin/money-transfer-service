package dao.dto;

import com.google.inject.Singleton;
import com.google.inject.name.Named;
import dao.IUniqueGenerator;
import dao.Repository;

@Singleton
public class AccountRepository extends Repository<Long, AccountDto> {

    public AccountRepository(@Named("LongAtomic") IUniqueGenerator keyGenerator) {
        super(keyGenerator);
    }
}
